<%@page import="com.sprcore.fosun.utils.collection.MapUtils"%>
<%@page import="com.sprcore.fosun.utils.collection.ListUtils"%>
<%@ include  file="../../inc/json_header.inc"%><%
//http://192.168.0.3:8080/SkateTrainWar/app/course/?course_id=2&_ts=1486302639534
String mobile_app_id = req.getParameter("mobile_app_id",true);

Map pp = new HashMap();
pp.put("mobile_app_id", mobile_app_id);
pp.put("is_deleted", "0");
 
dao.insert("td_user_disc_log", pp);

out.println(Json.getString(new HashMap())); 

%><%@ include  file="../../inc/json_footer.inc"%>