package com.ailk.metaq.engine;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.jms.JMSException;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.jms.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 连接工厂
 * 
 * @author zhoufan
 * @since  2014-3-26 上午10:02:00
 */
public class MetaQConnection {
	
	private static final Log log = LogFactory.getLog(MetaQConnection.class);
	
	private MetaQConnection(){}
	
	private static ActiveMQConnectionFactory amqConnFactory;
	private static PooledConnection pooledConnection;
	
	static {
		//String url = "failover:(tcp://localhost:61616?jms.prefetchPolicy.all=2)?initialReconnectDelay=1000&timeout=3000&startupMaxReconnectAttempts=3";
		//String url = "failover:(tcp://localhost:61616?jms.prefetchPolicy.all=2)?initialReconnectDelay=1000&timeout=3000&startupMaxReconnectAttempts=3";


		amqConnFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
		ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
		//鉴于消息组件在UPC中的应用主要是提供元数据和数据库操作之间的管理桥梁，入库操作一般耗时较大并且消息数较小，所以将prefetch value的值调小。
		prefetchPolicy.setQueuePrefetch(2);
		amqConnFactory.setPrefetchPolicy(prefetchPolicy);
		PooledConnectionFactory pooledConnFactory = new PooledConnectionFactory(amqConnFactory);
		try {
			//pooledConnection = (PooledConnection) pooledConnFactory.createConnection();
			pooledConnection = (PooledConnection) pooledConnFactory.createConnection(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD);
			pooledConnection.start();
		} catch (JMSException e) {
			log.error("创建MQ连接异常", e);
		}
	}
	
	public static PooledConnection getConnection(){
		return pooledConnection;
	}
	
	/**
	 * PooledConnection 内维护了一个会话线程安全队列{@link CopyOnWriteArrayList}，这里实际是从ConnectionPool的会话对象池里获得一个会话。
	 * 
	 * @return {@link ActiveMQSession}
	 */
	public static Session createSession(){
		
		try {
			return pooledConnection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			log.error("Failed to create session.", e);
			return null;
		}
	}
	
	/**
	 * 关闭连接，以及这个连接创建的会话对象池
	 */
	public static void closeConnection(){
		try {
			pooledConnection.close();
		} catch (JMSException e) {
			log.error("Failed to close the connection pool.", e);
		}
	}

}
