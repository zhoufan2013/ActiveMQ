package com.ailk.metaq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.MessageAvailableListener;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.advisory.ConsumerEvent;
import org.apache.activemq.advisory.ConsumerEventSource;
import org.apache.activemq.advisory.ConsumerListener;
import org.apache.activemq.advisory.ConsumerStartedEvent;
import org.apache.activemq.advisory.ConsumerStoppedEvent;
import org.apache.activemq.advisory.DestinationEvent;
import org.apache.activemq.advisory.DestinationListener;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.advisory.ProducerEvent;
import org.apache.activemq.advisory.ProducerEventSource;
import org.apache.activemq.advisory.ProducerListener;
import org.apache.activemq.advisory.ProducerStartedEvent;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.jms.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.lang.StringUtils;

import com.ailk.metaq.engine.MetaQConnection;
import com.ailk.metaq.engine.SessionPool;

/**
 * @author zhoufan
 * @since  2014-3-28 下午2:41:55
 */
public class AdvisoryMessage {

	public static void main(String[] args) throws Exception {
		
//		ActiveMQConnectionFactory factory = null;
//		PooledConnectionFactory connectionFactory = null;
//		
//		factory =  new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
//		connectionFactory = new PooledConnectionFactory(factory);
//		PooledConnection connection = (PooledConnection) connectionFactory.createConnection();
//		ActiveMQConnection conn = (ActiveMQConnection) connection.getConnection();
//		
//		MetaQConnection.startConn();
//		SessionPool sessionPool = MetaQConnection.createSessionPool();
//		ActiveMQConnection conn = (ActiveMQConnection) sessionPool.getConnection();
//		final ActiveMQSession session = (ActiveMQSession) sessionPool.borrowSession();
//		conn.start();
//		SessionPool sessionPool = new SessionPool();
//		sessionPool.setConnectionFactory(factory);
//		sessionPool.setConnection(conn);
		
//		ActiveMQTopic topic = AdvisorySupport.getConsumerAdvisoryTopic(new ActiveMQQueue("FirstQueue"));
		
//		MessageProducer producer = session.createProducer(topic);
		
//		ProducerEventSource pes = new ProducerEventSource(conn, new ActiveMQQueue("FirstQueue"));
//		pes.setProducerListener(new ProducerListener() {
//			
//			@Override
//			public void onProducerEvent(ProducerEvent producerevent) {
//				
//				ProducerStartedEvent event = (ProducerStartedEvent) producerevent;
//				
//				System.out.println("是否开启: " + event.isStarted());
//				System.out.println("一共有 " + event.getProducerCount() + "生产者");
//			}
//		});
//		pes.start();
		
		
//		ProducerEventSource source = new ProducerEventSource(conn, new ActiveMQQueue("FirstQueue"));
//		
//		source.setProducerListener(new ProducerListener() {
//			
//			@Override
//			public void onProducerEvent(ProducerEvent producerevent) {
//				
//				ProducerStartedEvent event = (ProducerStartedEvent) producerevent;
//				ProducerInfo info = event.getProducerInfo();
//				if(producerevent instanceof ProducerStartedEvent){
//					
//					try {
//						ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(new ActiveMQQueue("FirstQueue"));
						
//						consumer.setMessageListener(new MessageListener() {
//							
//							@Override
//							public void onMessage(Message message) {
//								
//								TextMessage text = (TextMessage) message;
//								
//								try {
//									System.out.println(text.getText());
//								} catch (JMSException e) {
//									e.printStackTrace();
//								}
//								
//							}
////						});
//						consumer.setAvailableListener(new MessageAvailableListener() {
//							
//							@Override
//							public void onMessageAvailable(MessageConsumer messageconsumer) {
//								
//								try {
//									messageconsumer.setMessageListener(new MessageListener() {
//										
//										@Override
//										public void onMessage(Message message) {
//											
//											TextMessage text = (TextMessage) message;
//											try {
//												System.out.println(text.getText());
//												
//												
//											} catch (JMSException e) {
//												e.printStackTrace();
//											} 
//											
//										}
//									});
//								} catch (JMSException e) {
//									e.printStackTrace();
//								}
//								
//							}
//						});
//						
//						
//						ActiveMQMessage message2 = (ActiveMQMessage) consumer.receive();
//						if(message2 != null){
//							consumer.close();
//						}
//						
//					} catch (JMSException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//		
//		source.start();
		
//		ConsumerEventSource ces = new ConsumerEventSource(conn, new ActiveMQQueue("FirstQueue"));
//		ces.setConsumerListener(new ConsumerListener() {
//			
//			@Override
//			public void onConsumerEvent(ConsumerEvent consumerevent) {
//				
//				if (consumerevent instanceof ConsumerStartedEvent) {
//					//
//					ConsumerStartedEvent event = (ConsumerStartedEvent) consumerevent;
//					System.out.println("一共有 " + event.getConsumerCount() + "消费者");
//					
//				}else if(consumerevent instanceof ConsumerStoppedEvent){
//					
//					ConsumerStoppedEvent event = (ConsumerStoppedEvent) consumerevent;
//					System.out.println("一共有 " + event.getConsumerCount() + "消费者 ----");
//				}
//			}
//		});
//		ces.start();
		
		
//		DestinationSource source = conn.getDestinationSource();
		
		
//		source.setDestinationListener(new DestinationListener() {
//			
//			@Override
//			public void onDestinationEvent(DestinationEvent destinationevent) {
//				
//				System.out.println(destinationevent.isAddOperation() + "   " + destinationevent.isRemoveOperation());
//				
//				if(destinationevent.isAddOperation()){
//					//TODO create a consumer
//					
//					ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(new ActiveMQQueue("FirstQueue"));
//					
//					
//					
//				}else if(destinationevent.isRemoveOperation()){
//					
//				}
//				
//			}
//		});
		
//		ActiveMQTopic topic = AdvisorySupport.getConsumerAdvisoryTopic(new ActiveMQTopic("FirstQueue"));
//		MessageConsumer consumer = session.createConsumer(topic);
//		consumer.setMessageListener(new MessageListener() {
//			
//			@Override
//			public void onMessage(Message msg) {
//				System.out.println("----");
//				
//				if(msg instanceof ActiveMQMessage){
//					
//					ActiveMQMessage message = (ActiveMQMessage)msg;
//					ConsumerInfo consumerInfo = (ConsumerInfo) message.getDataStructure();
//					System.out.println("++++ : " + consumerInfo.getCurrentPrefetchSize());
//				}
//			}
//		});
		
	}
}
