package com.deertt.utils.helper;

import com.deertt.frame.base.util.IGlobalConstants;

public class DvSqlHelper {

	/**
	 * NAME='张三'
	 */
	public final static int TYPE_CHAR_EQUAL = 0;

	/**
	 * NAME like '%张三%'
	 */
	public final static int TYPE_CHAR_LIKE = 1;

	/**
	 * NAME like '张三%'
	 */
	public final static int TYPE_CHAR_LIKE_START = 6;

	/**
	 * AGE = 18
	 */
	public final static int TYPE_NUMBER_EQUAL = 7;

	/**
	 * AGE >= 18
	 */
	public final static int TYPE_NUMBER_GREATER_EQUAL = 2;

	/**
	 * AGE <= 18
	 */
	public final static int TYPE_NUMBER_LESS_EQUAL = 3;

	/**
	 * BIRTH >= 1990-01-01
	 */
	public final static int TYPE_DATE_GREATER_EQUAL = 4;

	/**
	 * BIRTH <= 1990-01-01
	 */
	public final static int TYPE_DATE_LESS_EQUAL = 5;

	/**
	 * 自定义类型 {int type(类型) [, String prefixValue(指定的查询值前缀), String
	 * affixValue(指定的查询值后缀)]}
	 */
	public final static int TYPE_CUSTOM = 99;

	/**
	 * escape转义sql输入值，防止sql注入（如   '-->''）
	 * 
	 * @param inputValue
	 *			待转义的输入值
	 * @return 转义后的sql值
	 */
	public static String escapeSqlValue(Object inputValue) {
		return escapeSqlValue(inputValue, TYPE_CHAR_EQUAL);
	}
	
	/**
	 * escape转义sql输入值，防止sql注入
	 * 
	 * @param inputValue
	 *			待转移的输入值
	 * @param type
	 *			类型
	 * @return 转义后的sql值
	 */
	public static String escapeSqlValue(Object inputValue, int type) {
		if (inputValue == null) {
			return null;
		}
		String value = inputValue.toString().trim();
		switch (type) {
		// 字符串和日期：转义'，保证输入值在''中间，就是安全的
		case TYPE_CHAR_EQUAL:
		case TYPE_CHAR_LIKE:
		case TYPE_CHAR_LIKE_START:
		case TYPE_DATE_GREATER_EQUAL:
		case TYPE_DATE_LESS_EQUAL:
			value = value.replaceAll("(['])", "'$1");//单引号转义	 '-->''
			break;
		// 数字：去掉空白类符号，就是安全的
		case TYPE_NUMBER_EQUAL:
		case TYPE_NUMBER_GREATER_EQUAL:
		case TYPE_NUMBER_LESS_EQUAL:
			// 空白符忽略
			value = value.replaceAll("(\\s+)", "");
			// 疑似函数的一律忽略
			value = value.replaceAll("([\\w_]+\\(.*\\))", "");
			break;
		default:
			break;
		}
		return value;
	}

	/**
	 * 构建SQL查询条件
	 * 
	 * @param fieldName
	 *			字段名称
	 * @param inputValue
	 *			字段值
	 * @param type
	 *			查询类型
	 * @param args
	 *			{int type(查询类型) [, String prefixValue(指定的查询值前缀), String
	 *			affixValue(指定的查询值后缀)]}
	 * @return
	 */
	public static String buildQueryStr(String fieldName, Object inputValue, Object... args) {
		int type = TYPE_CHAR_EQUAL;
		if (args.length > 0) {
			type = (Integer) args[0];
		}
		String value = escapeSqlValue(inputValue, type);
		if (value != null && value.toString().length() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(fieldName);
			sb.append(" ");
			switch (type) {
			case TYPE_CHAR_EQUAL:
				sb.append("= '");
				sb.append(value);
				sb.append("'");
				break;
			case TYPE_CHAR_LIKE:
			case TYPE_CHAR_LIKE_START:
				sb.append("like '");
				if (type == TYPE_CHAR_LIKE) { // LIKE开头加%，而LIKE_START开头不加%
					sb.append("%");
				}
				// 如果value中含有%或_，对其转义
				if (value.indexOf("%") > -1 || value.indexOf("_") > -1) {
					sb.append(value.replaceAll("([%_/])", "/$1"));
					sb.append("%'");
					sb.append(" ESCAPE '/'");
				} else {
					sb.append(value);
					sb.append("%'");
				}
				break;
			case TYPE_NUMBER_EQUAL:
				sb.append("= ");
				sb.append(value);
				break;
			case TYPE_NUMBER_GREATER_EQUAL:
				sb.append(">= ");
				sb.append(value);
				break;
			case TYPE_NUMBER_LESS_EQUAL:
				sb.append("<= ");
				sb.append(value);
				break;
			case TYPE_DATE_GREATER_EQUAL:
				String dateStr1 = getSqlDateStr(value.toString());
				if (dateStr1.length() == 0) {
					return "";
				}
				sb.append(">= ");
				sb.append(dateStr1);
				break;
			case TYPE_DATE_LESS_EQUAL:
				if (value.toString().length() == 10) {
					value = value.toString() + " 23:59:59";
				}
				String dateStr2 = getSqlDateStr(value.toString());
				if (dateStr2.length() == 0) {
					return "";
				}
				sb.append("<= ");
				sb.append(dateStr2);
				break;
			case TYPE_CUSTOM:
				sb.append(args[1]);
				sb.append(value);
				sb.append(args[2]);
				break;
			default:
				break;
			}
			return sb.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * 获得SQL中日期的查询字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String getSqlDateStr(Object value) {
		if(value == null || value.toString().trim().length() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		if(IGlobalConstants.DATABASE_PRODUCT_NAME_ORACLE.equalsIgnoreCase(DvConfig.getDatabaseProductName())) {
			String valueStr = value.toString().trim();
			sb.append("to_date('");
			switch (valueStr.length()) {
			case 4: //2013
				sb.append(valueStr);
				sb.append("', 'YYYY')");
				break;
			case 7: //2013-01
				sb.append(valueStr);
				sb.append("', 'YYYY-MM')");
				break;
			case 10: //2013-01-01
				sb.append(valueStr);
				sb.append("', 'YYYY-MM-DD')");
				break;
			case 19: //2013-01-01 01:01:01
				sb.append(valueStr);
				sb.append("', 'YYYY-MM-DD HH24:MI:SS')");
				break;
			case 23: //2013-01-01 01:01:01 001
				sb.append(valueStr.substring(0, 19));
				sb.append("', 'YYYY-MM-DD HH24:MI:SS')");
				break;
			default:
				return "";
			}
		} else {
			sb.append("'");
			sb.append(value.toString().trim());
			sb.append("'");
		}
		return sb.toString();
	}

	/**
	 * 构建完整SQL查询片段，多个查询条件用and连接，不带where
	 * 
	 * @param queryStrs
	 *			类似new String[]{"NAME like '%张三%'"}, {"AGE=18"} }
	 * @return
	 */
	public static String appendQueryStr(String[] queryStrs) {
		StringBuilder sb = new StringBuilder();
		for (String queryStr : queryStrs) {
			if (queryStr == null || queryStr.trim().length() == 0) {
				continue;
			}
			if (sb.length() == 0) {
				// 第一个条件不加and
			} else {
				sb.append(" and");
			}
			sb.append(" ");
			sb.append(queryStr);
		}
		return sb.toString();
	}

	/**
	 * 封装原SQL语句为分页查询语句（适用MySQL）
	 * 
	 * @param strsql
	 * @param startIndex
	 * @param size
	 * @return
	 */
	public static String getSqlPage4Mysql(String strsql, int startIndex, int size) {
		if(startIndex < 1) {
			startIndex = 1;
		}
		return strsql + " limit " + (startIndex - 1) + "," + size;
	}

	/**
	 * 封装原SQL语句为分页查询语句（适用Oracle）
	 * 
	 * @param strsql
	 * @param startIndex
	 * @param size
	 * @return
	 */
	public static String getSqlPage4Oracle(String strsql, int startIndex, int size) {
		return "select * from (select rownum as rmrn, a.* from(" + strsql + ") a where rownum<=" + (startIndex + size - 1) + ")where rmrn >=" + startIndex;
	}
	
	/**
	 * 封装原SQL语句为分页查询语句（适用Sqlserver）——还未实现呢
	 * 
	 * @param strsql
	 * @param startIndex
	 * @param size
	 * @return
	 */
	static String getSqlPage4Sqlserver(String strsql, int startIndex, int size) {
//		153ms-176ms
//		select * from(select row_number() over(order by uid asc) as rownumber, * from moa_user ) as tb where rownumber between 100000 and 100200
//		
//		156ms-210ms
//		select top 200 * from(select row_number() over(order by uid asc) as rownumber,* from moa_user ) as tb where rownumber>100000
//		
//		135ms
//		select top 200 * FROM moa_user WHERE (uid > (SELECT MAX(uid) FROM (SELECT TOP 100000 uid FROM moa_user ORDER BY uid) AS temp_moa_user)) ORDER BY uid
//		
//		270ms-290ms
//		select top 200 * from moa_user a  where uid  not in(select top 100000  uid  from moa_user  b order by uid)
//		
//		950ms
//		select * from ( select top 200 * from ( select TOP 100000 * from moa_user order by uid) as amoaUser ORDER BY uid DESC ) as bmoaUser ORDER BY uid ASC
		return null;
	}
	
	/**
	 * 将Object[]中的对象的字符串，以逗号分割后拼成一个字符串,带有单引号
	 * 
	 * @param strArray 输入字符串数组
	 * @return String 如果数组为空或空数组，则返回“'-1'”
	 */
	public static String parseToSQLStringApos(Object[] strArray) {
		if (strArray == null || strArray.length == 0) {
			return "'-1'"; //为了让长度为0的数组返回的sql不报错
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strArray.length - 1; i++) {
			if(strArray[i] != null) {
				sb.append("'");
				sb.append(strArray[i].toString());
				sb.append("',");
			}
		}
		if (strArray[strArray.length - 1] != null) {
			sb.append("'");
			sb.append(strArray[strArray.length - 1].toString());
			sb.append("'");
		}
		return sb.toString();
	}
	
	/**
	 * 将Object[]中的对象的字符串，以逗号分割后拼成一个字符串,不带单引号
	 * 
	 * @param strArray 输入字符串数组
	 * @return String 如果数组为空或空数组，则返回“'-1'”
	 */
	public static String parseToSQLIntegerApos(Object[] strArray) {
		if (strArray == null || strArray.length == 0) {
			return "-1"; //为了让长度为0的数组返回的sql不报错
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strArray.length - 1; i++) {
			if(strArray[i] != null) {
				sb.append(strArray[i].toString());
				sb.append(",");
			}
		}
		if (strArray[strArray.length - 1] != null) {
			sb.append(strArray[strArray.length - 1].toString());
		}
		return sb.toString();
	}
	
	/**
	 * 得到不同数据库下的常用函数
	 * @param func 函数名枚举值
	 * @param databaseProductName 数据库名称
	 * @param args 可选的参数列表
	 * @return
	 */
	public static String getFunction(Function func, String databaseProductName, Object... args) {
		if(IGlobalConstants.DATABASE_PRODUCT_NAME_MYSQL.equals(databaseProductName)) {
			return getFunctionMysql(func, args);
		} else if(IGlobalConstants.DATABASE_PRODUCT_NAME_ORACLE.equals(databaseProductName)) {
			return getFunctionOracle(func, args);
		} else if(IGlobalConstants.DATABASE_PRODUCT_NAME_SQLServer.equals(databaseProductName)) {
			return getFunctionSqlserver(func, args);
		}
		return null;
	}
	
	/**
	 * 常用函数名枚举
	 */
	public enum Function {
		TO_NUMBER,
		TO_CHAR,
		SYSDATE,
		LENGTH,
		SUBSTR,
		NVL,
		CONCAT,
		WM_CONCAT
	}
	
	/**
	 * 获取Oracle数据库的常用函数
	 * @param func
	 * @param args
	 * @return
	 */
	static String getFunctionOracle(Function func, Object... args) {
		StringBuilder result = new StringBuilder();
		if(Function.TO_NUMBER.name().equals(func.name())) {
			result.append("to_number(");
			result.append(args[0]);
			result.append(")");
			return result.toString();
		} else if(Function.TO_CHAR.name().equals(func.name())) {
			result.append("to_char(");
			result.append(args[0]);
			result.append(")");
			return result.toString();
		} else if(Function.SYSDATE.name().equals(func.name())) {
			return "sysdate";
		} else if(Function.LENGTH.name().equals(func.name())) {
			return "length";
		} else if(Function.SUBSTR.name().equals(func.name())) {
			return "substr";
		} else if(Function.NVL.name().equals(func.name())) {
			return "nvl";
		} else if(Function.CONCAT.name().equals(func.name())) {
			for(int i=0; args!=null && i<args.length; i++) {
				if(i > 0) {
					result.append("||");
				}
				result.append(args[i]);
			}
			return result.toString();
		} else if(Function.WM_CONCAT.name().equals(func.name())) {
			return "wm_concat";
		} 
		return null;
	}
	
	/**
	 * 获取Mysql数据库的常用函数
	 * @param func
	 * @param args
	 * @return
	 */
	static String getFunctionMysql(Function func, Object... args) {
		StringBuilder result = new StringBuilder();
		if(Function.TO_NUMBER.name().equals(func.name())) {
			result.append("cast(");
			result.append(args[0]);
			result.append(" as signed integer)");
			return result.toString();
		} else if(Function.TO_CHAR.name().equals(func.name())) {
			result.append("cast(");
			result.append(args[0]);
			result.append(" as char)");
			return result.toString();
		} else if(Function.SYSDATE.name().equals(func.name())) {
			return "sysdate()";
		} else if(Function.LENGTH.name().equals(func.name())) {
			return "length";
		} else if(Function.SUBSTR.name().equals(func.name())) {
			return "substring";
		} else if(Function.NVL.name().equals(func.name())) {
			return "ifnull";
		} else if(Function.CONCAT.name().equals(func.name())) {
			result.append("concat(");
			for(int i=0; args!=null && i<args.length; i++) {
				if(i > 0) {
					result.append(",");
				}
				result.append(args[i]);
			}
			result.append(")");
			return result.toString();
		} else if(Function.WM_CONCAT.name().equals(func.name())) {
			return "group_concat";
		} 
		return null;
	}
	
	/**
	 * 获取Sqlserver数据库的常用函数
	 * @param func
	 * @param args
	 * @return
	 */
	static String getFunctionSqlserver(Function func, Object... args) {
		StringBuilder result = new StringBuilder();
		if(Function.TO_NUMBER.name().equals(func.name())) {
			result.append("cast(");
			result.append(args[0]);
			result.append(" as decimal(30,2))");
			return result.toString();
		} else if(Function.TO_CHAR.name().equals(func.name())) {
			result.append("cast(");
			result.append(args[0]);
			result.append(" as char)");
			return result.toString();
		} else if(Function.SYSDATE.name().equals(func.name())) {
			return "getdate()";
		} else if(Function.LENGTH.name().equals(func.name())) {
			return "len";
		} else if(Function.SUBSTR.name().equals(func.name())) {
			return "substring";
		} else if(Function.NVL.name().equals(func.name())) {
			return "isnull";
		} else if(Function.CONCAT.name().equals(func.name())) {
			for(int i=0; args!=null && i<args.length; i++) {
				if(i > 0) {
					result.append("+");
				}
				result.append(args[i]);
			}
			return result.toString();
		} else if(Function.WM_CONCAT.name().equals(func.name())) {
			return "group_concat";
		} 
		return null;
	}

}