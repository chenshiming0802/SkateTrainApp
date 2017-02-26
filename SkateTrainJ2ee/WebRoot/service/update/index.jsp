<%@ include  file="../../inc/json_header.inc"%><%
 
//String SERVER_URL = "http://192.168.0.3:82/";
String SERVER_URL="http://114.55.110.94/SkateTrainFile/release/";

String SYS_VERSION = "1.4.1";
String note = "    ";

String SYS_APPNAME = "SkateTrainApp";
Map ret = new HashMap();
ret.put("version",SYS_VERSION);
ret.put("title","New Versions");
ret.put("note",note);
ret.put("url",SERVER_URL+SYS_APPNAME+"."+SYS_VERSION+"_signed.apk");
ret.put("wgt",SERVER_URL+SYS_APPNAME+".wgt");
out.println(Json.getString(ret)); 

%><%@ include  file="../../inc/json_footer.inc"%>