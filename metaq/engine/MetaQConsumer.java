package com.ailk.metaq.engine;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.jms.pool.PooledMessageConsumer;
import org.apache.activemq.jms.pool.PooledSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.metaq.listener.MultiThreadMessageListener;
import com.ailk.metaq.service.impl.MetaQMessageSVImpl;

/**
 * metaq消费者封装对象
 * 
 * @author zhoufan
 * @since  2014-4-2 下午2:34:48
 */
public class MetaQConsumer implements ExceptionListener{
	
	private static final Log LOG = LogFactory.getLog(MetaQConsumer.class);
	
	/**
	 * {@link PooledSession} 内部维护了一个线程安全的消费者队列
	 */
	private PooledSession session;
	
	public MetaQConsumer(Session session) {
		this.session = (PooledSession)session;
	}

	public void start(String queueName){
		
		try {
			
			Queue queue = session.createQueue(queueName);
			MessageConsumer consumer = session.createConsumer(queue);
			PooledMessageConsumer pooledConsumer = new PooledMessageConsumer(session, consumer);
			pooledConsumer.setMessageListener(new MultiThreadMessageListener(new MetaQMessageSVImpl()));
			
		} catch (JMSException e) {
			LOG.error("Failed to receive messages from " + queueName, e);
		}
	}
	
	public void stopConsumingMessages(){
		//TODO
	}
	
	public void onException(JMSException ex) {
		//TODO handle exception
	}

}