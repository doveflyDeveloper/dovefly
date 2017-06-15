package com.deertt.utils.helper;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class DvLogHelper {
	
	private static Logger log = Logger.getLogger(DvLogHelper.class);
	
	public static void debug(String str) {
		log.debug(str);
	}
	
	public static void info(String str) {
		log.info(str);
	}
	
	public static void warn(String str) {
		log.warn(str);
	}
	
	public static void error(String str) {
		log.error(str);
	}
	
	public static void fatal(String str) {
		log.fatal(str);
	}
	
	public static Logger getLogger(String className) {
		return Logger.getLogger(className);
	}
	
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}
	
}