package com.sprcore.fosun.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sprcore.fosun.app.AppException;
import com.sprcore.fosun.utils.depend.SqlMapClientDao;

public class Dao {
	private Log logger = LogFactory.getLog(getClass()); 
	private static DataSource dataSource;
	private Connection conn;

	public void beginTrancation(){
		try {
			if( this.dataSource==null){				
				this.dataSource = (DataSource)Spring.getBean("dataSource");
			}
			
			Asserts.notNull(dataSource, "dataSource is null");
			conn = dataSource.getConnection();
			
			conn.setAutoCommit(false);
			logger.info("Open database connection");
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	public void commit() throws SQLException{
		conn.commit();
	}

	public void endTrancation(boolean isCommit) {
		try {
			if (isCommit) {
				conn.commit();
			} else {
				conn.rollback();
			}
			if(conn!=null){
				conn.close();
 
			}
			logger.info("Close database connection - " + isCommit);
			System.out.println("Close database connection - " + isCommit);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	public List<Map> getTableList(String tableName,Map whereParam,String orderby){
		Object[] values = new Object[whereParam.size()];
		int index = 0;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+tableName+" t where 1=1");
		Iterator<String> it = whereParam.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			sql.append(" and ").append(key).append("=?");
			
			values[index++] = whereParam.get(key);
		}
		if(orderby!=null){
			sql.append(" ").append(orderby);
		}
		return getList(sql.toString(), values);		
	}
	/**
	 * 查询单表的简化写法
	 * @param tableName
	 * @param whereParam
	 * @return
	 */
	public Map getTableRow(String tableName,Map whereParam,String orderby){
		if(orderby==null){
			orderby = "";
		}
		orderby += " limit 0,1";
		List<Map> list = getTableList(tableName,whereParam,orderby);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据ID查询单表
	 * @param tableName
	 * @param id
	 * @return
	 */
	public Map getTableRowById(String tableName,Integer id){
		return getTableRow(tableName,new AppMap().add("id", id),null);
	}
	private List<Map> getList(String sql,Object[] param){
		return getList(sql, convertToList(param));
	}
	private List<Map> getList(String sql,List param) {
		List<Map> list = queryList(sql, param);
		return list;
	}
	public Map getRow(String sql,List param) {
		sql += " limit 0,1";
		List<Map> list = queryList(sql, param);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}	
	}
	
	private Object getDbCell(Object obj){
		if (obj instanceof Timestamp) {
			Timestamp t = (Timestamp)obj;
			return new Date(t.getTime());
		}
		return obj;
	}
	private Object setDbCell(Object obj){
		if (obj instanceof Date) {
			Date t = (Date)obj;
			return new Timestamp(t.getTime());
		}
		return obj;		
	}
	
	private List<Map> queryList(String sql,Object[] param) {
		return queryList(sql, convertToList(param));
	}
	
	
 
	public List<Map> queryList(String sql,List param) {
		if(param==null){
			param = new ArrayList();
		}
		List<Map> sr = new ArrayList<Map>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			Asserts.notNull(conn, "Conn is null��please call beginTrancation");
			stmt = conn.prepareStatement(sql);

			printSql("queryList",sql,param);
			stmt = setPreparedStatement(stmt,param);
			rs = stmt.executeQuery();
			Map data = null;
			while (rs.next()) {
				data = new HashMap();
				ResultSetMetaData meta = rs.getMetaData();
				for(int i=0,j=meta.getColumnCount();i<j;i++){
					data.put(meta.getColumnName(i+1),getDbCell(rs.getObject(i+1)));
					//logger.info(meta.getColumnName(i+1)+"/"+rs.getObject(i+1));
				}
				sr.add(data);
			}
		} catch (Exception e) {
			throw new AppException(e);
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
				if(stmt!=null){
					stmt.close();
				}
			} catch (Exception e2) {
				throw new AppException(e2);
			}
		}
		return sr;
	}

	public String insert2(String table_name,Map map) {
		map.put("is_deleted", "0");
		map.put("rec_create_time", new Date());
		map.put("rec_update_time",new Date());
		return insert(table_name,map);
	}
	public String insert(String table_name,Map map) {
		String uuid = UUID.randomUUID().toString();
		PreparedStatement stmt = null;
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer sbValues = new StringBuffer();
//			map.put("id",uuid);
			sb.append("insert into "+table_name+" (");
			String sep = "";
			Iterator<String> it = map.keySet().iterator();
			List param = new ArrayList();			
			while(it.hasNext()){
				String key = it.next();
				Object val = map.get(key);
				if(val!=null){					
					sb.append(sep).append(key);
					sbValues.append(sep).append("?");
					sep = ",";
					param.add(setDbCell(val));
				}
			}
			sb.append(") values(");
			sb.append(sbValues).append(")");
			
			printSql("insert",sb.toString(),param);
			stmt = conn.prepareStatement(sb.toString());
			stmt = setPreparedStatement(stmt, param);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally{
			try {
				if(stmt!=null){
					stmt.close();
				}			
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return uuid;
	}
	public void update2(String table_name,Map map, Integer id) {
		map.put("rec_update_time",new Date());
		update(table_name,map,id);
	}
	public void update(String table_name,Map map, Integer id) {
		PreparedStatement stmt = null;
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer sbValues = new StringBuffer();
			sb.append("update "+table_name+" set ");
			String sep = "";
			Iterator<String> it = map.keySet().iterator();
			List param = new ArrayList();
			while(it.hasNext()){
				String key = it.next();
				Object val = map.get(key);
				if(val!=null){					
					sb.append(sep).append(key);
					sb.append("=?");
					sep = ",";
					param.add(setDbCell(val));
				}
			}
			sb.append(" where id=?");
			param.add(id);
			
			printSql("update",sb.toString(),param);
			stmt = conn.prepareStatement(sb.toString());
			stmt = setPreparedStatement(stmt, param);
			stmt.executeUpdate();
		} catch (Exception e) {
			throw new AppException(e);
		} finally{
			try {
				if(stmt!=null){
					stmt.close();
				}			
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
	}
	
	public void executeSql(String sql,List param){
		if(param==null){
			param = new ArrayList();
		}
		
		PreparedStatement stmt = null;
		try {
			
			printSql("execute",sql,param);
			stmt = conn.prepareStatement(sql);
			stmt = setPreparedStatement(stmt, param);
			stmt.executeUpdate();
		} catch (Exception e) {
			throw new AppException(e);
		} finally{
			try {
				if(stmt!=null){
					stmt.close();
				}			
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}		
	}

	private List<Map> queryOffsetList(String sql, int begin, int end) {
		//TODO
		return null;
	}
	
	private void printSql(String type,String sql,List param){
		StringBuffer sb = new StringBuffer("["+type+"]");
		sb.append(sql+"      [");
		for(int i=0,j=param.size();i<j;i++){
			sb.append(param.get(i)+",");
		}
		sb.append("]");
		logger.info(sb.toString());
	}

	private List convertToList(Object[] param){
		List plist = new ArrayList();
		if(param!=null){
			for(int i=0,j=param.length;i<j;i++){
				plist.add(param[i]);
			}
		}
		return plist;
	}

	
	private PreparedStatement setPreparedStatement(PreparedStatement stmt,List param) throws SQLException{
		for(int i=0,j=param.size();i<j;i++){
			Object item = param.get(i);
			if (item instanceof String) {
				stmt.setString(i+1, (String)item);
			}else if(item instanceof Integer) {
				stmt.setInt(i+1, ((Integer)item).intValue());
			}else if(item instanceof Float) {
				stmt.setFloat(i+1, ((Float)item).floatValue());
			}else if(item instanceof Timestamp) {
				stmt.setTimestamp(i+1, (Timestamp)item);
			}else{
				throw new AppException("DB2Java object error"+item);
			}
		}
		return stmt;
	}
	
	public List<Map> iQueryList(String statementName,Map parameterObject){
		SqlMapClientDao idao = new SqlMapClientDao();
		idao.setDataSource(this.dataSource);
		idao.setSqlMapClient((SqlMapClient)Spring.getBean("sqlMapClient"));
		
		return idao.getSqlMapClientTemplate().queryForList(statementName, parameterObject);
	}

	public Map iQueryRow(String statementName,Map parameterObject){
		List<Map> list = iQueryList(statementName, parameterObject);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 增加分页信息，sql中增加 limit #offset_begin#,#offset_end#
	 * @param map
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	public Map addPageInfo(Map map,String pageno,String pagesize){
		int pagesizeInt = 0;
		int pagenoInt = 0;
		try{
			pagesizeInt = new Integer(pagesize);
		}catch(Exception e){
			pagesizeInt = 5;
		}
		try{
			pagenoInt = new Integer(pageno);
		}catch(Exception e){
			pagenoInt = 1;
		}
		int offset_begin = (pagenoInt-1) * pagesizeInt;
		int offset_end = pagenoInt * pagesizeInt;
		map.put("offset_begin", offset_begin);
		map.put("offset_end", offset_end);
		
		return map;
	}
}
