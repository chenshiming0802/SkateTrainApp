<%@page pageEncoding="UTF-8" contentType="text/json; charset=UTF-8" %><%@ page import="java.util.*" %><%@page import="java.math.*"%><%@ page import="java.util.regex.*" %><%@ page import="com.sprcore.fosun.utils.*" %><%@ page import="com.sprcore.fosun.app.*" %><%
response.setHeader("Access-Control-Allow-Origin", "*");  
response.setHeader("Access-Control-Allow-Methods", "*");  
response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");  
response.setHeader("Charset","UTF-8");
String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
Debug.p("----------"+url);
Enumeration enu=request.getParameterNames(); 
while(enu.hasMoreElements())  { 
 	String name=(String)enu.nextElement();
 	Debug.p("            "+name+"="+request.getParameter(name));
 }

String sessionUserId = "1";//Session.getUserId(session)

Request req = new Request(request);
//out.println("xx");
Dao dao = new Dao();
try{	
	dao.beginTrancation();
%>