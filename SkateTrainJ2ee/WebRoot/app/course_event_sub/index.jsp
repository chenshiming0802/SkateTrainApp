<%@page import="com.sprcore.fosun.utils.collection.MapUtils"%>
<%@page import="com.sprcore.fosun.utils.collection.ListUtils"%>
<%@ include  file="../../inc/json_header.inc"%><%
//http://192.168.0.3:8080/SkateTrainWar/app/course_event_sub?course_event_id=10&_ts=1486305208532
String course_event_id = req.getParameter("course_event_id",true);

Map model = dao.getTableRowById("td_course_daily_event", new Integer(course_event_id));
model.put("film",dao.getTableRowById("td_file", (Integer)model.get("film_file_id")));
model.put("smallpic_file_file",MapUtils.get(dao.getTableRowById("td_file", (Integer)model.get("smallpic_file_id")), "file_path"));

//td_event_point
String sql = "SELECT t.*,(SELECT a.file_path FROM td_file a WHERE t.img_file_id=a.id AND a.is_deleted='0') img_file FROM td_event_point t WHERE t.is_deleted='0' and t.event_id=?";
List list = dao.queryList(sql,ListUtils.getList(new Integer(course_event_id)) );
 

Map ret = new HashMap(); 
ret.put("model", model);
ret.put("list", list);
out.println(Json.getString(ret)); 

%><%@ include  file="../../inc/json_footer.inc"%>