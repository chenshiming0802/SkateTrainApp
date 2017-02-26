<%@ include  file="../../inc/json_header.inc"%><%
 

//http://127.0.0.1:8080/sprcore_json_war/test/
List list = dao.iQueryList("mp3_list",null);
Debug.p("list",list);



Map ret = new HashMap();
ret.put("list", list);

out.println(Json.getString(ret));

%><%@ include  file="../../inc/json_footer.inc"%>