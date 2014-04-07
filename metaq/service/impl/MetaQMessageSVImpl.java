package com.ailk.metaq.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.activemq.command.ActiveMQObjectMessage;

import com.ai.appframe2.common.DataStructInterface;
import com.ailk.metaq.engine.UpcObjBean;
import com.ailk.metaq.service.interfaces.IMetaQMessageSV;
import com.ailk.upc.bo.BORBCharSpecBean;

/**
 * 处理消息的具体业务逻辑
 * 
 * @author zhoufan
 * @since  2014-4-2 下午3:12:02
 */
public class MetaQMessageSVImpl implements IMetaQMessageSV {

	public void handle(Message message) {
		System.out.println("---");
		if(message instanceof ActiveMQObjectMessage){
			
			ActiveMQObjectMessage objMsg = (ActiveMQObjectMessage) message;
			//TODO 入库接口
			int count = 0;
			try {
				UpcObjBean obj = (UpcObjBean) objMsg.getObject();
				DataStructInterface[] datas = obj.getDatas();
				for(DataStructInterface s : datas){
					
					BORBCharSpecBean charSpecBean = (BORBCharSpecBean) s;
					System.out.println(charSpecBean.getCharSpecName());
					
				}
				
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
