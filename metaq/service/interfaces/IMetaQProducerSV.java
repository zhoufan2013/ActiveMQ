package com.ailk.metaq.service.interfaces;

/**
 * @author zhoufan
 * @since  2014-4-5 下午1:26:36
 */
public interface IMetaQProducerSV {
	
	<T> void sendMsg(T msg);
	
	void close();
	
	public void shutDown();
	
}
