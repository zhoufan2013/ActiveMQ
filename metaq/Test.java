package com.ailk.metaq;

/**
 * @author zhoufan
 * @since  2014-4-7 上午10:15:52
 */
public class Test {

	public static void main(String[] args) {
		
		asd(Integer.parseInt("123"));
		
	}
	
	private static <T> void asd(T msg){
		
		if(msg instanceof String){
			System.out.println("string");
		}else if(msg instanceof Object){
			System.out.println("object");
		}
		
	}

}
