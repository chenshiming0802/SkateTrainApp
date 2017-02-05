<%@ include  file="../../inc/json_header.inc"%><%
 
String SERVER_URL = "http://192.168.0.3:82/";
String SYS_VERSION = "1.2.5";
String note = "增加发现功能，不断更新的轮滑精品视频";

String SYS_APPNAME = "SkateTrainApp";
Map ret = new HashMap();
ret.put("version",SYS_VERSION);
ret.put("title","版本更新");
ret.put("note",note);
ret.put("url",SERVER_URL+"SkateTrainWar/release/"+SYS_APPNAME+"."+SYS_VERSION+".apk");
ret.put("wgt",SERVER_URL+"SkateTrainWar/release/"+SYS_APPNAME+".wgt");
out.println(Json.getString(ret)); 

%><%@ include  file="../../inc/json_footer.inc"%>