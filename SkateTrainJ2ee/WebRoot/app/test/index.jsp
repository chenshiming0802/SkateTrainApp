<%@ include  file="../../inc/json_header.inc"%><%
 

//http://127.0.0.1:8080/sprcore_json_war/test/
List list = dao.iQueryList("index_sub",null);
Debug.p("list",list);
 
//out.println(Json.getString(list));

%><%@ include  file="../../inc/json_footer.inc"%>