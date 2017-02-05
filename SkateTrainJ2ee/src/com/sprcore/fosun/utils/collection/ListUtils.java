package com.sprcore.fosun.utils.collection;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
	public static List getList(Object obj){
		List list = new ArrayList();
		list.add(obj);
		return list;
	}
	public static List getList(Object obj,Object obj2){
		List list = new ArrayList();
		list.add(obj);
		list.add(obj2);
		return list;
	}	
	public static List getList(Object obj,Object obj2,Object obj3){
		List list = new ArrayList();
		list.add(obj);
		list.add(obj2);
		list.add(obj3);
		return list;
	}
}
