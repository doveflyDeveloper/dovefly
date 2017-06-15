package com.deertt.frame.base.web.el;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * @project XXX
 * @author : XXX
 * @version 1.0
 * @Create:2011 5:41:27 PM
 * @Update:
 * @describe:
 */
public class NumberFormatEL {
	
	public static String commafy(Object source) {
		String dest = DecimalFormat.getNumberInstance().format(source);
		return dest;
	}
	
	public static String currency(Object source) {
		String currecy = NumberFormat.getCurrencyInstance().format(source);
		return currecy;
	}
	
	public static String currency_CN(Object source) {
		String currecy = NumberFormat.getCurrencyInstance(Locale.CHINA).format(source);
		return currecy;
	}
	
	public static String currency_US(Object source) {
		String currecy = NumberFormat.getCurrencyInstance(Locale.US).format(source);
		return currecy;
	}
	
//	public static String reverse(String name){   
//		try {
//			String className = "";
//			String fieldName = "";
//			Class<?> clazz = Class.forName(className);
//			Field field = clazz.getField("TABLE_COLUMN_CHINESE");
//			if (field != null) {
//				Map<String, String> map = (Map<String, String>)field.get(clazz);
//				if (map != null) {
//					return map.get(fieldName);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new StringBuffer(name).reverse().toString();   
//	}
	
	public static void main(String[] args) {
		System.out.println(commafy(33434.00));
		System.out.println(currency(33434.00));
		System.out.println(currency_CN(33434.00));
		System.out.println(currency_US(33434.00));
	}
}