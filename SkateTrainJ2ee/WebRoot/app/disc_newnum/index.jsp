<%@page import="com.sprcore.fosun.utils.collection.MapUtils"%>
<%@page import="com.sprcore.fosun.utils.collection.ListUtils"%>
<%@ include  file="../../inc/json_header.inc"%><%
//http://192.168.0.3:8080/SkateTrainWar/app/course/?course_id=2&_ts=1486302639534
String mobile_app_id = req.getParameter("mobile_app_id",true);
//Map pp = new HashMap();
//pp.put("mobile_app_id", mobile_app_id);
//pp.put("is_deleted", "0");
List pp = new ArrayList();
pp.add(mobile_app_id);
 
String sql = "SELECT COUNT(*) num FROM td_disc_article t WHERE t.publish_time > ( SELECT MAX(a.occure_time) FROM td_user_disc_log a WHERE a.mobile_app_id=?)";
Map ret = dao.getRow(sql,pp);
out.println(Json.getString(ret)); 

%><%@ include  file="../../inc/json_footer.inc"%>