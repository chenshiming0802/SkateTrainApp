<%@page import="com.sprcore.fosun.utils.collection.ListUtils"%>
<%@ include  file="../../inc/json_header.inc"%><%

String id = req.getParameter("id", true);
 
Map pp = new HashMap();pp.put("id",id);
Map map = dao.iQueryRow("dics_article",pp);
 
//查找 keys
String mlls = "SELECT t2.name FROM td_disc_article_key t1, td_disc_key t2 WHERE t1.key_id = t2.id AND t1.is_deleted = '0' AND t2.is_deleted = '0' AND t1.article_id=?";
List mll = dao.queryList(mlls, ListUtils.getList(map.get("id")));
StringBuffer keys = new StringBuffer();
for(int ii=0,jj=mll.size();ii<jj;ii++){
	Map mmll = (Map)mll.get(ii);
	keys.append(mmll.get("name")).append(" ");
} 
map.put("keys", keys.toString());

Map ret = new HashMap();
ret.put("model", map);

out.println(Json.getString(ret)); 

%><%@ include  file="../../inc/json_footer.inc"%>