package com.ailk.metaq.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.jms.pool.PooledQueueSender;
import org.apache.activemq.jms.pool.PooledSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 生产者封装器
 *
 * @author zhoufan
 * @since  2014-4-2 下午12:42:12
 */
public class MetaQProduer implements ExceptionListener{
	
	private static final Log LOG = LogFactory.getLog(MetaQProduer.class);

	public MetaQProduer(Session session) {		
		this(DEFAULT_THREAD_POOL_SIZE, DEFAULT_IS_PERSITENT, session);
	}
	
	public MetaQProduer(int threadPoolSize, boolean isPersistent, Session session){
		
		this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
		this.session = (PooledSession) session;
        this.isPersistent = isPersistent;
	}
	
	public void send(final String queueName, final UpcObjBean bean){
		
		this.threadPool.execute(new Runnable() {
			
			public void run() {
				//TODO
				sendMsg(queueName, bean);
			}
		});
	}
	
	@SuppressWarnings("null")
	private void sendMsg(String queueName, UpcObjBean bean){

		try {
			//ActiveMQDestination destination = (ActiveMQDestination) session.createQueue(queueName);
			Queue queue = session.createQueue(queueName);
			PooledQueueSender pooledSender = (PooledQueueSender) session.createSender(queue);
			//PooledProducer pooledProducer = (PooledProducer) session.createProducer(destination);
			//MessageProducer producer = session.createProducer(destination);
			pooledSender.setDeliveryMode(isPersistent ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT);
			ActiveMQMessage message = getMessage(session, bean);
			pooledSender.send(queue, message);
		} catch (JMSException e) {
			//TODO handle exception
			LOG.error("Failed to send message to " + queueName, e);
			MetaQConnection.closeConnection();
		} finally {
			
        }
    }
	
	private ActiveMQMessage getMessage(PooledSession session, UpcObjBean bean) {
		
		try {
			ActiveMQObjectMessage objMsg = (ActiveMQObjectMessage) session.createObjectMessage(bean);
			return objMsg;
		} catch (JMSException e) {
			//TODO handle exception
			LOG.error("Failed to create Object Message.", e);
			return null;
		}
	}
	
	public void onException(JMSException jmsexception) {
		//TODO handle Exception
	}
	
	
	private boolean isPersistent = DEFAULT_IS_PERSITENT;
	
	private int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
	
	private PooledSession session;
	
	private ExecutorService threadPool;
	
	private static final boolean DEFAULT_IS_PERSITENT = Boolean.TRUE;
	
	private static final int DEFAULT_THREAD_POOL_SIZE = 10;
	
}
