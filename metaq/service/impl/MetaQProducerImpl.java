package com.ailk.metaq.service.impl;

import java.io.Serializable;
import java.util.Map;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQMessageTransformation;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.jms.pool.PooledProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ailk.metaq.engine.MetaQConnection;
import com.ailk.metaq.engine.MetaQProducerPool;
import com.ailk.metaq.service.interfaces.IMetaQProducerSV;

/**
 * @author zhoufan
 * @param <T>
 * @since 2014-4-5 下午1:27:13
 */
public class MetaQProducerImpl implements IMetaQProducerSV {

	private static final Log LOG = LogFactory.getLog(MetaQProducerImpl.class);

	private Session session;

	private MetaQProducerPool pool;

	private String queueName;

	private boolean isPersistent;

	private ActiveMQQueue queue;

	private PooledProducer producer;

	public MetaQProducerImpl(Session session, String queue,
			boolean isPersistent, MetaQProducerPool pool) {
		this.session = session;
		this.queueName = queue;
		this.isPersistent = isPersistent;
		this.pool = pool;
		init();
	}

	private void init() {

		try {
			Queue qu =  session.createQueue(queueName);
			queue = (ActiveMQQueue) ActiveMQMessageTransformation.transformDestination(qu);
		} catch (JMSException e) {
			LOG.error("Failed to create destination.", e);
		}

		try {
			producer = (PooledProducer) session.createProducer(queue);
		} catch (JMSException e) {
			LOG.error("Failed to create producer.", e);
		}
	}

	public <T> void sendMsg(T msg) {

		try {
			producer.setDeliveryMode(isPersistent ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT);
			Message message = getMessage(msg);
			ActiveMQConnection amqConnection = (ActiveMQConnection) MetaQConnection.getConnection().getConnection();
			ActiveMQMessage ampMessage = ActiveMQMessageTransformation.transformMessage(message, amqConnection);
			producer.send(ampMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * transform the transmitted message to javax.jms.Message
	 * @param msg the transmitted message
	 * @return {@link Message}
	 */
	private <T> Message getMessage(T msg) {
		
		if(msg instanceof String){
			try {
				return session.createTextMessage((String) msg);
			} catch (JMSException e) {
				LOG.error("Failed to create JMS text message.", e);
				return null;
			}
		}else if(msg instanceof Object){
			try {
				return session.createObjectMessage((Serializable) msg);
			} catch (JMSException e) {
				LOG.error("Failed to create JMS object message.", e);
				return null;
			}
		}else if (msg instanceof Map) {
			
			try {
				MapMessage mapMessage = session.createMapMessage();
				//TODO
				return mapMessage;
			} catch (JMSException e) {
				LOG.error("Failed to create JMS map message", e);
				return null;
			}
		}
		return null;
	}
	
	/**
	 * return this producer to the producer pool.
	 */
	public void close() {
		this.pool.returnProducer(this);
	}
	
	/**
	 * @see ActiveMQMessageProducer#close()
	 */
	public void shutDown() {
		try {
			this.producer.close();
		} catch (JMSException e) {
			LOG.error("Failed to close the ActiveMQ Producer.", e);
		}
	}
}
