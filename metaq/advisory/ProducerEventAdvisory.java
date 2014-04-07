package com.ailk.metaq.advisory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.Service;
import org.apache.activemq.advisory.ProducerEvent;
import org.apache.activemq.advisory.ProducerEventSource;
import org.apache.activemq.advisory.ProducerListener;
import org.apache.activemq.advisory.ProducerStartedEvent;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ProducerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * advisory 监听生产者事件（待完成）
 *
 * @author zhoufan
 * @since  2014-3-28 下午5:03:59
 */
public class ProducerEventAdvisory extends ProducerEventSource{
	
	private final Connection connection = null;
    private Session session = null;
	
	public ProducerEventAdvisory(Connection connection, Destination destination)
			throws JMSException {
		super(connection, destination);
	}

	private static final Logger LOG = LoggerFactory.getLogger(ProducerEventSource.class);
	
    
	@Override
	public void start() throws Exception {
		
	}

	@Override
	public void stop() throws Exception {
		
	}
	
	
}
