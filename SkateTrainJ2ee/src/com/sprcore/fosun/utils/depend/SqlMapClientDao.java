package com.sprcore.fosun.utils.depend;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SqlMapClientDao extends SqlMapClientDaoSupport {
	public List<Map> queryList(String statementName,Map parameterObject){
		return getSqlMapClientTemplate().queryForList(statementName, parameterObject);
	}
}
