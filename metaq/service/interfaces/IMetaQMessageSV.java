package com.ailk.metaq.service.interfaces;

import javax.jms.Message;

/**
 * 消息处理接口
 * 
 * @author zhoufan
 * @since  2014-4-2 下午3:06:12
 */
public interface IMetaQMessageSV {
	
	//处理消息的回调方法
	public void handle(Message message);
	
}
