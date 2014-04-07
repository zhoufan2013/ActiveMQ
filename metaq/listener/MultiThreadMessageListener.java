package com.ailk.metaq.listener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Message;
import javax.jms.MessageListener;

import com.ailk.metaq.service.interfaces.IMetaQMessageSV;

/**
 * 多线程监听,实现消息监听接口
 * 
 * @author zhoufan
 * @since  2014-4-2 下午3:13:59
 */
public class MultiThreadMessageListener implements MessageListener {
	
	public MultiThreadMessageListener(IMetaQMessageSV messageHandler){
		this(DEFAULT_MAX_POOL_SIZE, messageHandler);
	}
	
	public MultiThreadMessageListener(int maxPoolSize, IMetaQMessageSV messageHandler){
		this.maxPoolSize = maxPoolSize;
		this.messageHandler = messageHandler;
		this.threadPool = Executors.newFixedThreadPool(maxPoolSize);
	}
	
	public void setMaxPoolSize(int maxPoolSize){
		this.maxPoolSize = maxPoolSize;
	}
	
	public void setMessageHandler(IMetaQMessageSV messageHandler) {
		this.messageHandler = messageHandler;
	}
	
	@Override
	public void onMessage(final Message message) {
		
		this.threadPool.execute(new Runnable() {
			
			public void run() {
				MultiThreadMessageListener.this.messageHandler.handle(message);
			}
		});
	}
	
	private int maxPoolSize = DEFAULT_MAX_POOL_SIZE;
	
	private IMetaQMessageSV messageHandler;
	
	private ExecutorService threadPool;
	
	private static final int DEFAULT_MAX_POOL_SIZE = 10;

}
