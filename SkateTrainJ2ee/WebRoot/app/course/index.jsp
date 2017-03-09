<%@page import="com.sprcore.fosun.utils.collection.MapUtils"%>
<%@page import="com.sprcore.fosun.utils.collection.ListUtils"%>
<%@ include  file="../../inc/json_header.inc"%><%
//http://192.168.0.3:8080/SkateTrainWar/app/course/?course_id=2&_ts=1486302639534
String course_id = req.getParameter("course_id",true);
Map model = dao.getTableRowById("td_course", new Integer(course_id));
//model.put("bigimg_file",MapUtils.get(dao.getTableRowById("td_file", (Integer)model.get("bigimg_file_id")), "file_path"));
model.put("bigimg_file",model.get("img_file"));

//td_course_daily
List list = dao.queryList("select t.* from td_course_daily t where t.is_deleted='0' and t.course_id=?",ListUtils.getList(new Integer(course_id)) );
for(int i=0,j=list.size();i<j;i++){
	Map lm = (Map)list.get(i);
	String lms = "select t.* ,t.smallpic_file from td_course_daily_event t where t.is_deleted='0' and t.course_daily_id=?";
	lm.put("events", dao.queryList(lms, ListUtils.getList((Integer)lm.get("id"))));
}


Map ret = new HashMap();
ret.put("course", model);
ret.put("course_daily", list);
out.println(Json.getString(ret)); 

%><%@ include  file="../../inc/json_footer.inc"%>