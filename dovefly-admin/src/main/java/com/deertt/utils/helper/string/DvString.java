package com.deertt.utils.helper.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class DvString {
	private String str;
	private Map attributes;
	/**
	 * 构造函数:
	 * 
	 */
	public DvString() {
		this.str = "";
		this.attributes = new HashMap();
	}

	/**
	 * 构造函数:
	 * @param str
	 */
	public DvString(String str) {
		this();
		this.str = str;
	}
	
	public String toString() {
		return str;
	}
	
	public void addAttribute(String key, Object obj) {
		attributes.put(key, obj);
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	
	public void setValue(String str) {
		this.str = str;
	}
	
}
