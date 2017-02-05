<%@ include  file="../../inc/json_header.inc"%><%

//
List list = dao.iQueryList("index_sub",null);
Debug.p("list",list);
 
out.println(Json.getString(list));

%><%@ include  file="../../inc/json_footer.inc"%>