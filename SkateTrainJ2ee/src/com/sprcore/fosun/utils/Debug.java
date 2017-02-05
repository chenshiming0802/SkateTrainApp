package com.sprcore.fosun.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Debug {
	private static Log logger = LogFactory.getLog(Debug.class); 
	public static void p(String key,Object obj){
		logger.info("["+key+"]"+obj);
	}
	public static void p(Object obj){
		logger.info(obj);
	}

}
