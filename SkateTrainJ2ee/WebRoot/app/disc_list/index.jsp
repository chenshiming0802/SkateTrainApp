<%@page import="com.sprcore.fosun.utils.collection.ListUtils"%>
<%@ include  file="../../inc/json_header.inc"%><%

String key_id = req.getParameter("key_id", false);
//
Map pp = new HashMap();pp.put("key_id",key_id);

List list_key = dao.queryList("SELECT * FROM td_disc_key t1 WHERE t1.is_deleted='0' AND t1.is_display_header='1'",null);

List list = dao.iQueryList("disc_list",pp);
Debug.p("list",list);
 
for(int i=0,j=list.size();i<j;i++){
	Map ml = (Map)list.get(i);
	
	String mlls = "SELECT t2.name FROM td_disc_article_key t1, td_disc_key t2 WHERE t1.key_id = t2.id AND t1.is_deleted = '0' AND t2.is_deleted = '0' AND t1.article_id=?";
	List mll = dao.queryList(mlls, ListUtils.getList(ml.get("id")));
	
	StringBuffer keys = new StringBuffer();
	for(int ii=0,jj=mll.size();ii<jj;ii++){
		Map mmll = (Map)mll.get(i);
		keys.append(" ").append(mmll.get("name"));
	}
	ml.put("keys", keys.toString());
}

Map ret = new HashMap();
ret.put("list", list);
ret.put("list_key", list_key);
out.println(Json.getString(ret)); 

%><%@ include  file="../../inc/json_footer.inc"%>