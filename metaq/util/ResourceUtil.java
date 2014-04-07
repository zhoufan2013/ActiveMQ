package com.ailk.metaq.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

public class ResourceUtil {
	
	private ResourceUtil(){};
	
	public static Properties loadPropertiesFromFilepath(String file_path) throws Exception{
		
		Properties rtn = new Properties();
		rtn.load(loadInputStreamFromFilepath(file_path));
		return rtn;
	}
	
	public static Properties loadPropertiesFromFilepath(String file_path, String prefix, boolean isDiscardPrefix) throws Exception{
		
		Properties rtn = new Properties();
		
		Properties pc = new Properties();
		pc.load(loadInputStreamFromFilepath(file_path));
		Set keySet = pc.keySet();
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()){
			
			String element = it.next();
			if(StringUtils.indexOf(element, prefix) != -1){
				
				if(isDiscardPrefix){
					rtn.put(StringUtils.replace(element, (new StringBuilder()).append(prefix).append(".").toString().trim(), ""), pc.get(element));
				}else{
					rtn.put(element, rtn.get(element));
				}
			}
		}
		return rtn;
	}
	
	/**
	 * resolve properties from file path , return the value for the key
	 * 
	 * @param file_path
	 * @param key 
	 * @return
	 * @throws Exception
	 */
	public static String resolvePropertiesFromFilepath(String file_path, String key) throws Exception{
		
		Properties rtn = new Properties();
		rtn.load(loadInputStreamFromFilepath(file_path));
		
		String value = null;
		Set keySet = rtn.keySet();
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()){
			String srcKey = it.next();
			if(StringUtils.equals(key, srcKey)){
				value = rtn.getProperty(srcKey);
			}
		}
		return value;
	}
	
	/**
	 * load file(*.ini) from file_path
	 * @param file_path
	 * @return
	 * @throws Exception
	 */
	public static PropertiesConfiguration loadPropertiesConfigurationFromFilepath(String file_path) throws Exception{
		
		PropertiesConfiguration rtn = new PropertiesConfiguration();
		rtn.load(loadInputStreamFromFilepath(file_path));
		return rtn;
	}
	
	/**
	 * load file(*.properties) from file_path
	 * @param file_path
	 * @return
	 * @throws Exception
	 */
	public static InputStream loadInputStreamFromFilepath(String file_path) throws Exception{
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(file_path);
	}
	
	public static String readFile(URI uri, String encoding) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(uri)), encoding));
		
		StringBuilder sb = new StringBuilder();
		String tmp;
		while((tmp = br.readLine()) != null){
			sb.append(tmp);
		}
		br.close();
		return sb.toString().replace("(?<=>)\\s+(?=<)", "");
	}
}
