<%@ include  file="../../inc/json_header.inc"%><%
Integer album_id = req.getIntegerParameter("album_id",true);
 
Map album = dao.getTableRowById("v_td_mp3_album", album_id); 

Map pp = new HashMap();
pp.put("album_id", album_id);
List list = dao.iQueryList("mp3_album",pp);
Debug.p("list",list);



Map ret = new HashMap();
ret.put("list", list);
ret.put("album", album);


out.println(Json.getString(ret));

%><%@ include  file="../../inc/json_footer.inc"%>