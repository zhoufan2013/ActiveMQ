package com.ailk.metaq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.jms.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;

import com.ai.appframe2.common.DataStructInterface;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ailk.metaq.engine.MetaQConnection;
import com.ailk.metaq.engine.MetaQProducerPool;
import com.ailk.metaq.engine.MetaQProduer;
import com.ailk.metaq.engine.SessionPool;
import com.ailk.metaq.engine.UpcObjBean;
import com.ailk.metaq.service.impl.MetaQProducerImpl;
import com.ailk.metaq.service.interfaces.IMetaQProducerSV;
import com.ailk.upc.bo.BORBCharSpecBean;
import com.ailk.upc.ivalues.IBORBCharSpecValue;

/**
 * @author zhoufan
 * @since  2014-3-19 下午3:59:11
 */
public class SenderTest {

	public static void main(String[] args) throws Exception {
		
//		ActiveMQSession session = (ActiveMQSession) ConnectionFactory.createSessionPool().borrowSession();
//		MetaQProduer produer = new MetaQProduer(10, Boolean.TRUE, session);
		
//		MetaQProduer produer = MetaQHandler.getProduer();
		
		MetaQProducerPool pool = new MetaQProducerPool(MetaQConnection.getConnection(), "FirstQueue");
		IMetaQProducerSV producer = pool.borrowProducer();
		UserInfoInterface userInfo = ServiceManager.getNewBlankUserInfo();
		userInfo.setTenantId("01");
		DataStructInterface[] datas = new DataStructInterface[4];
		for(int i=0; i<4 ; i++){
			IBORBCharSpecValue charSpecBean = new BORBCharSpecBean();
			charSpecBean.setCharSpecName("zhoufan " + i);
			datas[i] = charSpecBean;
		}
		
		UpcObjBean bean = new UpcObjBean(userInfo, datas);
		producer.sendMsg(bean);
		producer.close();
		
//		produer.send("FirstQueue", bean);
		
		
		
		
//		ActiveMQConnectionFactory factory = null;
//		PooledConnectionFactory connectionFactory = null;
//		
//		factory =  new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
//		connectionFactory = new PooledConnectionFactory(factory);
//		PooledConnection connection = (PooledConnection) connectionFactory.createConnection();
//		ActiveMQConnection conn = (ActiveMQConnection) connection.getConnection();
//		conn.start();
//		
//		SessionPool sessionPool = new SessionPool();
//		sessionPool.setConnectionFactory(factory);
//		sessionPool.setConnection(conn);
		
//		Session session = sessionPool.borrowSession();
//		MessageProducer producer = session.createProducer(new ActiveMQQueue("FirstQueue"));
//		ConnectionFactory.startConn();
//		SessionPool sessionPool = ConnectionFactory.createSessionPool();
//		ActiveMQSession session = (ActiveMQSession) sessionPool.borrowSession();
//		
//		ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(new ActiveMQQueue("FirstQueue"));
//		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//		producer.send(session.createTextMessage("zhoufan233"));
//		sendMsg(session, producer);
//		sessionPool.returnSession(session);
		
	}
	
	public static void sendMsg(Session session, ActiveMQMessageProducer producer) throws Exception{
		
		for(int i=0; i< 5000 ;i++){
			TextMessage msg = session.createTextMessage(" Zhoufan " + i + " msg ");
			producer.send(msg);
		}
	}
	
}
