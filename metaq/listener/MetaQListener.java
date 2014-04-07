package com.ailk.metaq.listener;

import javax.jms.JMSException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ailk.metaq.engine.MetaQConnection;

/**
 * 启动客户端消息连接监听器
 * 
 * @author zhoufan
 * @since  2014-3-25 下午3:13:16
 */
public class MetaQListener implements ServletContextListener{

	private static final Log log = LogFactory.getLog(MetaQListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent destroyContextEvent) {
		
//		try {
//			SessionPoolFactory.closeConn();
//          ConnectionFactory.closeSessionPool();
//		} catch (JMSException e) {
//			log.error("session 对象池关闭失败", e);
//		}
	}

	@Override
	public void contextInitialized(ServletContextEvent startContextEvent) {
		
//		try {
//			SessionPoolFactory.startConn();
//		} catch (JMSException e) {
//			log.error("session 对象池启动失败", e);
//		}
	}
}
