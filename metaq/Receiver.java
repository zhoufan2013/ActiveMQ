package com.ailk.metaq;


/**
 * @author zhoufan
 * @since  2014-3-19 下午4:11:22
 */
public class Receiver {

	public static void main(String[] args) throws Exception{
		
		
//		ActiveMQConnectionFactory factory = null;
//		PooledConnectionFactory connectionFactory = null;
//		
//		factory =  new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
//		connectionFactory = new PooledConnectionFactory(factory);
//		PooledConnection connection = (PooledConnection) connectionFactory.createConnection();
//		ActiveMQConnection conn = (ActiveMQConnection) connection.getConnection();
//		ActiveMQPrefetchPolicy policy = new ActiveMQPrefetchPolicy();
//		policy.setAll(1);
//		conn.start();
//		
//		SessionPool sessionPool = new SessionPool();
//		sessionPool.setConnectionFactory(factory);
//		sessionPool.setConnection(conn);
//		Session session = sessionPool.borrowSession();
//		Session session2 = sessionPool.borrowSession();
//		Session session3 = sessionPool.borrowSession();
//		Session session4 = sessionPool.borrowSession();
//		MessageConsumer consumer = session.createConsumer(new ActiveMQQueue("FirstQueue"));
		
//		ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(new ActiveMQQueue("FirstQueue"));
		
//		ActiveMQMessageConsumer consumer2 = (ActiveMQMessageConsumer) session2.createConsumer(new ActiveMQQueue("FirstQueue"));
//		
//		ActiveMQMessageConsumer consumer3 = (ActiveMQMessageConsumer) session3.createConsumer(new ActiveMQQueue("FirstQueue"));
//		
//		ActiveMQMessageConsumer consumer4 = (ActiveMQMessageConsumer) session3.createConsumer(new ActiveMQQueue("FirstQueue"));
		
//		consumer.setMessageListener(new MessageListener() {
//			
//			@Override
//			public void onMessage(Message message) {
//				TextMessage textMessage = (TextMessage)message;
//				try {
//					System.out.println(textMessage.getText());
//				} catch (JMSException e) {
//					e.printStackTrace();
//				}
//			}
//		});
		
//		consumer2.setMessageListener(new MessageListener() {
//			
//			@Override
//			public void onMessage(Message message) {
//				TextMessage textMessage = (TextMessage)message;
//				try {
//					System.out.println(textMessage.getText() + "-----");
//				} catch (JMSException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		consumer3.setMessageListener(new MessageListener() {
//			
//			@Override
//			public void onMessage(Message message) {
//				TextMessage textMessage = (TextMessage)message;
//				try {
//					System.out.println(textMessage.getText() + "+++++");
//				} catch (JMSException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		consumer4.setMessageListener(new MessageListener() {
//			
//			@Override
//			public void onMessage(Message message) {
//				TextMessage textMessage = (TextMessage)message;
//				try {
//					System.out.println(textMessage.getText() + "+++++");
//				} catch (JMSException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		sessionPool.returnSession(session);
//		sessionPool.returnSession(session2);
//		sessionPool.returnSession(session3);
//		sessionPool.returnSession(session4);
//		System.out.println(System.currentTimeMillis() - start);
	}

}
