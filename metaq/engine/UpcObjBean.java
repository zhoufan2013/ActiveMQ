package com.ailk.metaq.engine;

import java.io.Serializable;
import com.ai.appframe2.common.DataStructInterface;
import com.ai.appframe2.privilege.UserInfoInterface;

/**
 * UPC 业务大实体
 * 
 * @author zhoufan
 * @since  2014-4-2 下午1:49:14
 */
public class UpcObjBean implements Serializable{
	
	private UserInfoInterface userInfo;
	
	private DataStructInterface[] datas;
	
	public UpcObjBean(){};
	
	public UpcObjBean(UserInfoInterface userInfo, DataStructInterface[] datas){
		this.userInfo = userInfo;
		this.datas = datas;
	}
	
	/**
	 * @return the userInfo
	 */
	public UserInfoInterface getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(UserInfoInterface userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the datas
	 */
	public DataStructInterface[] getDatas() {
		return datas;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setDatas(DataStructInterface[] datas) {
		this.datas = datas;
	}
	
}