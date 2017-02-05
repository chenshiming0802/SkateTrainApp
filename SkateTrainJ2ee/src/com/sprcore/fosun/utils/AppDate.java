package com.sprcore.fosun.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sprcore.fosun.app.AppException;

public class AppDate {
	
	public static Date getDate(String str){
		return getDate(str,"yyyy-MM-dd");
	}
	
	public static Date getDate(String str,String format){
		if(str==null || str.trim().length()==0){
			return null;
		}
		
		try {
			SimpleDateFormat s = new SimpleDateFormat(format);
			return s.parse(str);		
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getDate("2015-04-10"));

	}

}
