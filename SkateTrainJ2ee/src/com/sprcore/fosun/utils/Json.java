package com.sprcore.fosun.utils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.sprcore.fosun.utils.depend.DateJsonValueProcessor;

public class Json {
 
	/**
	 * @deprecated
	 */
	public static String getString(List list,Map map){
		if(map==null){
			map = new HashMap();
		}
		if(!map.containsKey("resultFlag")){			
			map.put("resultFlag", "0");
		}
		map.put("datas", list);		
		return getString(map);
	}
	
	private static JsonConfig getJsonConfig(String format) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor(format));
		config.registerJsonValueProcessor(Timestamp.class,
				new DateJsonValueProcessor(format));
		return config;
	}

	public static String getString(Map map) {	
		return getString(map, "yyyy-MM-dd");
	}
	public static String getString(List list) {	
		return getString(list, "yyyy-MM-dd");
	}
	public static String getString(Map map, String format) {
		JSONObject jsonObject = JSONObject.fromObject(map,
				getJsonConfig(format));
		return jsonObject.toString();
	}
	public static String getString(List list, String format) {
		
//		JSONArray array = new JSONArray();
//		for(int i=0,j=list.size();i<j;i++)		{
//			array.add(list.get(i));
//		}
		
		JSONArray jsonObject = JSONArray.fromObject(list,
				getJsonConfig(format));
		return jsonObject.toString();
	}

	public static String getString(Error e) {
		Map map = new HashMap();
		map.put("resultFlag", "1");
		map.put("resultMessage", e.getClass().toString() + ":" + e.getMessage());
		map.put("datas", e.getMessage());
		return getString(map);
	}	
  

	public static String getString(Exception e) {
		Map map = new HashMap();
		map.put("resultFlag", "1");
		map.put("resultMessage", e.getClass().toString() + ":" + e.getMessage());
		map.put("datas", e.getMessage());
		return getString(map);
	}

}
