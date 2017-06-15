package com.deertt.utils.helper.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author   
 * 帮助实现一些通用的日期,时间处理
 */
/**
 * @author Administrator
 *
 */
public class DvDateHelper {
	
	/** 一天的毫秒数  */
	public final static long ONE_DAY_MILLIS = 1000 * 60 * 60 * 24L;
	
	/**
	 * 取当前系统日期
	 * @return
	 */
	public static java.sql.Date getSysDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}
	
	/**
	 * 取当前系统时间戳
	 * @return
	 */
	public static Timestamp getSysTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 系统时间如20060101010101
	 * @return
	 */
	public static String getJoinedSysDateTime() {
		return DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyyMMddHHmmss");
	}
	
	/**
	 * 格式化时间字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(java.util.Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 获取当前月份第一天 yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentMonthFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return formatDate(calendar.getTime(), "yyyy-MM-dd");
	}
    
    /**
     * 获取当前月份最后一天 yyyy-MM-dd
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static String getCurrentMonthLastDate() {
    	Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return formatDate(calendar.getTime(), "yyyy-MM-dd");
    }
    
	/**
	 * 获取所在日期月份的第一天 yyyy-MM-dd
	 * @return
	 */
	public static String getMonthFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return formatDate(calendar.getTime(), "yyyy-MM-dd");
	}
	
    /**
     * 获取所在日期月份的最后一天 yyyy-MM-dd
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static String getMonthLastDate(Date date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return formatDate(calendar.getTime(), "yyyy-MM-dd");
    }
	
	/**
	 * 根据字符串获得时间戳
	 * 
	 * @param strDate yyyy-MM-dd HH:mm:ss格式的字符串
	 * @return 时间戳
	 */
	public static Timestamp getTimestamp(String strDate) {
		if (strDate == null || strDate.trim().length() == 0) {
			return null;
		}
		return Timestamp.valueOf(formatDateStr(strDate));
	}

	/**
	 * 获得日期戳
	 * 
	 * @param strDate yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static java.sql.Date getSqlDate(String strDate) {
		Timestamp ts = getTimestamp(strDate);
		if (ts == null) {
			return null;
		} else {
			return new java.sql.Date(ts.getTime());
		}
	}
	
	/**
	 * 转化为yyyy-mm-dd hh:mm:ss[.f...]
	 * 
	 * @param str
	 * @return
	 */
	public static String formatDateStr(String str) {
		if(str == null) {
			return str;
		} if(str.trim().length() == 0 || str.trim().matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(\\.\\d+)?$")) {
			return str.trim();
		}
		//处理2010/03/01
		String strDate = str.trim().replaceAll("/", "-");
		//处理2010-01-01T00:00:00
		strDate = str.trim().replaceAll("T", " ");
		//处理'2010-03-01
		strDate = strDate.replaceAll("'", "");
		if(strDate.indexOf("-") < 0) {
			if(strDate.length() >= 16) { //处理20100301235959
				strDate = strDate.substring(0,4) + "-" + strDate.substring(4,6) + "-" + strDate.substring(6,8)
					+ " " + strDate.substring(8,10) + ":" + strDate.substring(10,12) + ":" + strDate.substring(12,14);   
			} else if(strDate.length() >= 14) { //处理201003012359
				strDate = strDate.substring(0,4) + "-" + strDate.substring(4,6) + "-" + strDate.substring(6,8)
					+ " " + strDate.substring(8,10) + ":" + strDate.substring(10,12) + ":00";   
		} else if(strDate.length() >= 8) { //处理20100301
				strDate = strDate.substring(0,4) + "-" + strDate.substring(4,6) + "-" + strDate.substring(6,8);				
			}
		}
		//处理2010-03
		if(strDate.indexOf("-") == strDate.lastIndexOf("-")) {
			strDate = strDate + "-01";
		}
		//处理03-01-2010
		if(strDate.length() - strDate.lastIndexOf("-") == 4) {
			strDate = strDate.substring(strDate.lastIndexOf("-")+1) + "-" + strDate.substring(0, strDate.indexOf("-")) + "-" + strDate.substring(strDate.indexOf("-") + 1, strDate.lastIndexOf("-"));
		}
		//处理9999-03-01
		if(strDate.startsWith("9999")) {
			strDate = "1900-01-01 00:00:00";
		}
		if(strDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
			return strDate + " 00:00:00";
		} else if(strDate.matches("^\\d{4}-(\\d{1,2})-(\\d{1,2})$")) {
			Matcher ma = Pattern.compile("^(\\d{4}-)(\\d{1,2})(-)(\\d{1,2})$").matcher(strDate);
			StringBuilder sb = new StringBuilder();
			  if(ma.find()) {
				  sb.append(ma.group(1));
				  sb.append(ma.group(2).length() == 1 ? "0" + ma.group(2) : ma.group(2));
				  sb.append(ma.group(3));
				  sb.append(ma.group(4).length() == 1 ? "0" + ma.group(4) : ma.group(4));
			  }
			  sb.append(" 00:00:00");
			return sb.toString();
		} else if(strDate.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d*)?$")) {
			Matcher ma = Pattern.compile("^(\\d{4}-)(\\d{1,2})(-)(\\d{1,2})( )(\\d{1,2})(:)(\\d{1,2})(:)(\\d{1,2})(\\.\\d*)?$").matcher(strDate);
			StringBuilder sb = new StringBuilder();
			  if(ma.find()) {
				  sb.append(ma.group(1));
				  sb.append(ma.group(2).length() == 1 ? "0" + ma.group(2) : ma.group(2));
				  sb.append(ma.group(3));
				  sb.append(ma.group(4).length() == 1 ? "0" + ma.group(4) : ma.group(4));
				  sb.append(ma.group(5));
				  sb.append(ma.group(6).length() == 1 ? "0" + ma.group(6) : ma.group(6));
				  sb.append(ma.group(7));
				  sb.append(ma.group(8).length() == 1 ? "0" + ma.group(8) : ma.group(8));
				  sb.append(ma.group(9));
				  sb.append(ma.group(10).length() == 1 ? "0" + ma.group(10) : ma.group(10));
				  sb.append(ma.group(11) != null && ma.group(11).length() > 1 ? ma.group(11) : "");
			  }
			return sb.toString();
		} else {
			//RmLogHelper.error(RmDateHelper.class, "formatDateStr(" + str + ") failed");
			return str;
		}
		
	}
}