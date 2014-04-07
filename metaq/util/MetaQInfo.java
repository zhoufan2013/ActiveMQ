package com.ailk.metaq.util;

import java.util.Properties;

/**
 * @author zhoufan
 * @since  2014-3-25 下午4:19:59
 */
public class MetaQInfo {
	
	static final String CFG_FILE_PATH = "metaq.properties";
	private static String broker_url ;
	private static String broker_user;
	private static String broker_password;
	
	static {
		
		try {
			
			Properties properties = ResourceUtil.loadPropertiesFromFilepath(CFG_FILE_PATH);
			broker_url = properties.getProperty("url");
			broker_user = properties.getProperty("user");
			broker_password = properties.getProperty("password");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @return the broker_url
	 */
	public static String getBroker_url() {
		return broker_url;
	}
	
	/**
	 * @return the broker_user
	 */
	public static String getBroker_user() {
		return broker_user;
	}
	
	/**
	 * @return the broker_password
	 */
	public static String getBroker_password() {
		return broker_password;
	}
	
}
