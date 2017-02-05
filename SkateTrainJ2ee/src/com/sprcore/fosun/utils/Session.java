package com.sprcore.fosun.utils;

import javax.servlet.http.HttpSession;

public class Session {
	public static Integer getUserId(HttpSession session){
		return (Integer)session.getAttribute("USER_ID");
	}


}
