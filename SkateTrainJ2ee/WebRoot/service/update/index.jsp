<%@ include  file="../../inc/json_header.inc"%><%
 
//String SERVER_URL = "http://192.168.0.3:82/";
String SERVER_URL="http://114.55.110.94/SkateTrainFile/release/";

String SYS_VERSION = "1.3.6";
String note = "增加发现的未读文章数";

String SYS_APPNAME = "SkateTrainApp";
Map ret = new HashMap();
ret.put("version",SYS_VERSION);
ret.put("title","版本更新");
ret.put("note",note);
ret.put("url",SERVER_URL+SYS_APPNAME+"."+SYS_VERSION+"_signed.apk");
ret.put("wgt",SERVER_URL+SYS_APPNAME+".wgt");
out.println(Json.getString(ret)); 

%><%@ include  file="../../inc/json_footer.inc"%>