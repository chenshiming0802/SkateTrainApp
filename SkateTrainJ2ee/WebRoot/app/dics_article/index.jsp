<%@page import="com.sprcore.fosun.utils.collection.ListUtils"%>
<%@ include  file="../../inc/json_header.inc"%><%

String id = req.getParameter("id", true);
 
Map pp = new HashMap();pp.put("id",id);
Map map = dao.iQueryRow("dics_article",pp);
 
Map ret = new HashMap();
ret.put("model", map);

out.println(Json.getString(ret)); 

%><%@ include  file="../../inc/json_footer.inc"%>