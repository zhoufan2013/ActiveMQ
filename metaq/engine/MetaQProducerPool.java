package com.ailk.metaq.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.jms.Session;
import org.apache.activemq.jms.pool.PooledConnection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.metaq.service.impl.MetaQProducerImpl;
import com.ailk.metaq.service.interfaces.IMetaQProducerSV;
import com.ailk.metaq.util.MetaQConstants;

/**
 * 生产者线程池
 * 
 * @author zhoufan
 * @since  2014-4-5 下午1:23:03
 */
public class MetaQProducerPool {
	
	private static final Log LOG = LogFactory.getLog(MetaQProducerPool.class);
	
	private LinkedBlockingQueue<IMetaQProducerSV> producerQueue;
	
	private Semaphore semaphore = null;
	
	private ReadWriteLock lock = null;
	
	private String queue;
	
	private PooledConnection connection;
	
	private ExecutorService threadPool;
	
	private boolean isPersistent ;
	
	private int queueSize ;
	
	private int threadPoolSize ;
	
	public MetaQProducerPool(PooledConnection pooledConnection, String queue){
		
		this(pooledConnection, queue, MetaQConstants.DEFAULT_QUEUE_SIZE, MetaQConstants.DEFAULT_IS_PERSISTENT);
	}
	
	public MetaQProducerPool(PooledConnection pooledConnection, String queue, int queueSize, boolean isPersistent){
		
		this.lock = new ReentrantReadWriteLock();
		this.threadPoolSize = queueSize;
		this.semaphore = new Semaphore(queueSize);
		producerQueue = new LinkedBlockingQueue<IMetaQProducerSV>(queueSize);
		this.queue = queue;
		this.isPersistent = isPersistent;
		this.connection = pooledConnection;
		threadPool = Executors.newFixedThreadPool(threadPoolSize);
		init();
	}
	
	private void init(){
		
		List<Callable<AtomicBoolean>> list = new ArrayList<Callable<AtomicBoolean>>();
		final CountDownLatch countDownLatch = new CountDownLatch(queueSize);
		for(int i=0; i<queueSize; i++){
			list.add(new ProducerTask(countDownLatch, this));
		}
		
		try {
			threadPool.invokeAll(list);
			countDownLatch.await(MetaQConstants.POOL_AWAIT_MINUTES, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class ProducerTask implements Callable<AtomicBoolean>{
		
		private CountDownLatch count;
		private MetaQProducerPool pool;
		
		public ProducerTask(CountDownLatch count, MetaQProducerPool pool){
			this.count = count;
			this.pool = pool;
		}
		
		public AtomicBoolean call() throws Exception {
			
			Session session = MetaQConnection.createSession();
			MetaQProducerImpl producer = new MetaQProducerImpl(session, queue, isPersistent, pool);
			if(producer != null){
				producerQueue.offer(producer);
				count.countDown();
			}
			return new AtomicBoolean(true);
		}
		
	}
	
	/**
	 * borrow a producer from the pool in default await milliseconds
	 * @see MetaQConstants#PRODUCER_AWAIT_MILLISECONDS
	 * @return {@link IMetaQProducerSV}
	 */
	public IMetaQProducerSV borrowProducer(){
		
		return borrowProducer(MetaQConstants.PRODUCER_AWAIT_MILLISECONDS);
	}
	
	/**
	 * borrow a producer from the pool in specified milliseconds.
	 * @param waitTimeMillis time to wait for getting a producer instance from the pool
	 * @return {@link IMetaQProducerSV}
	 */
	public IMetaQProducerSV borrowProducer(Long waitTimeMillis){
		
		try {
			if(!semaphore.tryAcquire(waitTimeMillis, TimeUnit.MILLISECONDS)){
				throw new RuntimeException("Timeout for waiting idle producer in the pool.");
			}
		} catch (InterruptedException e) {
			throw new RuntimeException("interruped waiting for idle producer in the pool.");
		}
		
		lock.readLock().lock();
		IMetaQProducerSV producer = producerQueue.poll();
		if(producer == null){
			Session session = MetaQConnection.createSession();
			producer = new MetaQProducerImpl(session, queue, isPersistent, this);
			producerQueue.offer(producer);
		}
		lock.readLock().unlock();
		return producer;
	}
	
	/**
	 * return the producer to the producer pool
	 * @param producer 
	 */
	public void returnProducer(IMetaQProducerSV producer){
		if(!this.producerQueue.contains(producer)){
			return;
		}
		producerQueue.offer(producer);
		this.semaphore.release();
	}
	
	/**
	 * 起一个线程池关掉生产者线程池
	 */
	public synchronized void shutDown(){
		
		//Lock the thread closing the producer pool
		lock.writeLock().lock();
		List<Callable<AtomicBoolean>> list = new ArrayList<Callable<AtomicBoolean>>();
		int size = producerQueue.size();
		final CountDownLatch countDownLatch = new CountDownLatch(size);
		
		for(int i=0; i<size ;i++){
			
			list.add(new Callable<AtomicBoolean>() {

				@Override
				public AtomicBoolean call() throws Exception {
					
					IMetaQProducerSV producer = producerQueue.poll();
					if(producer != null){
						producer.shutDown();
						countDownLatch.countDown();
					}
					return new AtomicBoolean(true);
				}
			});
		}
		
		try {
			threadPool.invokeAll(list);
			countDownLatch.await(MetaQConstants.POOL_AWAIT_MINUTES, TimeUnit.MINUTES);
			threadPool.shutdownNow();
		} catch (InterruptedException e) {
			LOG.debug("Failed to close the producer pool.", e);
		}
	
		lock.writeLock().unlock();
	}
	
}
