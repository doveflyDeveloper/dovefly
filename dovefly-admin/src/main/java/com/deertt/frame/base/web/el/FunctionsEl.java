package com.deertt.frame.base.web.el;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @project XXX
 * @author : XXX
 * @version 1.0
 * @Create:2011 5:41:27 PM
 * @Update:
 * @describe:
 */
public class FunctionsEl {

	/**
	 * 对中文进行转码
	 * 
	 * @param str 中文字符串
	 * @return 中文编码过后的的字符串
	 */

	public static String chinese(String className, String fieldName) {
		try {
			Class<?> clazz = Class.forName(className);
			Field field = clazz.getField("TABLE_COLUMN_CHINESE");
			if (field != null) {
				Map<String, String> map = (Map<String, String>)field.get(clazz);
				if (map != null) {
					return map.get(fieldName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String reverse(String name){   
		return new StringBuffer(name).reverse().toString();   
	}
}