package com.sprcore.fosun.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sprcore.fosun.app.AppException;


public class Request {
	private Log logger = LogFactory.getLog(getClass()); 
	private HttpServletRequest request;
	
	public Request(HttpServletRequest request){
		this.request = request;
	}
	
	public String getParameter(String key,boolean isRequired){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			throw new AppException(e);
		}
		String value = request.getParameter(key);
		try {
			//value = new String(value.getBytes("ISO8859-1"),"utf-8");
		} catch (Exception e) {
			throw new AppException(e);
		}
		logger.info("req.getParamter:"+key+"="+value+" - "+isRequired);
		if(isRequired){
			Asserts.notNullOrEmpty(value, key+" 必填");			
		}
		return value;
	}

}
