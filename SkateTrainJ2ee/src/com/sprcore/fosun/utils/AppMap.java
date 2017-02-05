package com.sprcore.fosun.utils;

import java.util.HashMap;

/**
 * 实现HashMap链式put，以方便代码写成单行
 * @author chensm
 *
 */
public class AppMap extends HashMap{
	public AppMap add(String key, Object value) {
		this.put(key, value);
		return this;
	}
}
