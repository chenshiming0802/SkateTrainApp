package com.sprcore.fosun.utils;

import com.sprcore.fosun.app.AppException;


/**
 * ���Ա�
 * @author chensm
 *
 */
public class Asserts {
	/**
	 * ����ΪNulls
	 * @param obj
	 * @param message
	 */
	public static void notNull(Object obj,String message){
		if(obj==null){
			throw new AppException(message);
		}
	}
	/**
	 * ����ΪNull���ַ��
	 * @param obj
	 * @param message
	 */
	public static void notNullOrEmpty(String obj,String message){
		if(obj==null || obj.trim().length()==0){
			throw new AppException(message);
		}
	}
	/**
	 * �Ƿ���ͬ
	 * @param o1
	 * @param o2
	 * @param message
	 */
	public static void equals(Object o1,Object o2,String message){
		if(o1!=null && o2==null){
			throw new AppException(message); 
		}
		if(o1==null && o2!=null){
			throw new AppException(message); 
		}
		if(!o1.equals(o2)){
			throw new AppException(message); 
		}
	}
}
