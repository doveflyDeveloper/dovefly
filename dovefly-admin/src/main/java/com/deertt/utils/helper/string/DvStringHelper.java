/*
 * 创建日期 2005-5-29
 */
package com.deertt.utils.helper.string;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 功能、用途、现存BUG: 帮助实现一些通用的字符串处理
 * 
 * @author
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class DvStringHelper {
	

	/**
	 * 功能: 把"123,234,567"转为new String[]{"123", "234", "567"}
	 * 
	 * @param str
	 * @param splitKey
	 * @return
	 */
	public static String[] parseStringToArray(String str, String splitKey) {
		String[] returnStrArray = null;
		if (str != null && str.length() > 0) {
			returnStrArray = str.split(splitKey, -1);
		}
		if (returnStrArray == null) {
			returnStrArray = new String[0];
		}
		return returnStrArray;
	}
	
	/**
	 * 功能: 把"123,234,567"转为new String[]{123, 234, 567}
	 * 
	 * @param str
	 * @param splitKey
	 * @return
	 */
	public static Integer[] parseStringToIntegerArray(String str, String splitKey) {
		String[] returnStrArray = null;
		if (str != null && str.length() > 0) {
			returnStrArray = str.split(splitKey, -1);
		}
		if (returnStrArray == null) {
			returnStrArray = new String[0];
		}
		Integer[] results = new Integer[returnStrArray.length];
		for (int i = 0; i < returnStrArray.length; i++) {
			results[i] = Integer.valueOf(returnStrArray[i]);
		}
		return results;
	}

	/**
	 * 将String[]中字符串以","分割后拼成一个字符串
	 * 
	 * @param strArray-->输入字符串数组
	 * @return String
	 */
	public static String parseToString(String[] strArray) {
		if (strArray == null || strArray.length == 0) {
			return "";
		} else if (strArray.length == 1) {
			return strArray[0];
		}
		
		return parseToSQLString(strArray);
	}

	/**
	 * 将字符串以指定字符串切割后,分配到List中
	 * 
	 * @param strValue
	 * @param delim
	 * @return
	 */
	public static List getTokenizerList(String strValue, String delim) {
		return Arrays.asList(strValue.split(delim));
	}

	/**
	 * 将String[]中字符串以逗号分割后拼成一个字符串,不带有单引号
	 * 
	 * @param strArray
	 *			输入字符串数组
	 * @return String 形式如：xxx,yyy,zzz的字符串， 如果strArray==null 或者
	 *		 strArray.length==0 则返回字符串：notExistId
	 */
	public static String parseToSQLString(String[] strArray) {
		if (strArray == null || strArray.length == 0)
			return "notExistId";
		String myStr = "";
		for (int i = 0; i < strArray.length - 1; i++) {
			myStr += strArray[i] + ",";
		}
		myStr += strArray[strArray.length - 1];
		return myStr;
	}

	/**
	 * 将String[]中字符串以逗号分割后拼成一个字符串,带有单引号
	 * 
	 * @param strArray
	 *			输入字符串数组
	 * @return String 形式如：'xxx','yyy','zzz'的字符串， 如果strArray==null 或者
	 *		 strArray.length==0 则返回字符串：'notExistId'
	 */
	public static String parseToSQLStringWithComma(String[] strArray) {
		return parseToSQLStringWithComma((Object[]) strArray);
	}

	/**
	 * 将Object[]中字符串以逗号分割后拼成一个字符串,带有单引号
	 * 
	 * @param strArray
	 *			输入对象数组，调用对象的toString()方法
	 * @return String
	 */
	public static String parseToSQLStringWithComma(Object[] strArray) {
		if (strArray == null || strArray.length == 0) {
			return "'notExistId'";
		}
		String myStr = "";
		for (int i = 0; i < strArray.length - 1; i++) {
			myStr += "'" + strArray[i] + "',";
		}
		myStr += "'" + strArray[strArray.length - 1] + "'";
		return myStr;
	}

	/**
	 * 组拼SQL语句的in语句
	 * 
	 * @param collection
	 *			要拼接的字符串列表
	 * @param columnName
	 *			SQL语句中的列名
	 * @Exception 处理in的数量过多导致sql编译错误的情况，例如：Oracle数据库in语句中的个数最多为1000，超过将报错
	 * @return 返回SQL语句中in部分的and条件，形式如：(1=1 and columnName in
	 *		 ('xxx','yyy','zzz')) 如果collection==null 或者 collection.size()==0
	 *		 则返回字符串：(1=1)
	 */
	public static String joinInSQLWithComma(Collection<String> collection,
			String columnName) {
		StringBuffer buffer = new StringBuffer(" ( 1=1 ");
		if (collection != null && collection.size() > 0) {
			buffer.append(" and ");
			buffer.append(columnName).append(" in (");
			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				String str = (String) iterator.next();
				buffer.append("'").append(str).append("'");
				if (iterator.hasNext()) {
					buffer.append(",");
				}
			}
			buffer.append(") ");
		}
		buffer.append(") ");
		return buffer.toString();
	}

	/**
	 * 组拼SQL语句的in语句
	 * 
	 * @param arrays
	 *			要拼接的字符串数组
	 * @param columnName
	 *			SQL语句中的列名
	 * @Exception 处理in的数量过多导致sql编译错误的情况，例如：Oracle数据库in语句中的个数最多为1000，超过将报错
	 * @return 返回SQL语句中in部分的and条件，形式如：(1=1 and columnName in
	 *		 ('xxx','yyy','zzz')) 如果arrays==null 或者 arrays.length()==0
	 *		 则返回字符串：(1=1)
	 */
	public static String joinInSQLWithComma(String[] arrays, String columnName) {
		StringBuffer buffer = new StringBuffer(" ( 1=1 ");
		if (arrays != null && arrays.length > 0) {
			buffer.append(" and ");
			buffer.append(columnName).append(" in (");
			for (int i = 0; i < arrays.length; i++) {
				String str = arrays[i];
				buffer.append("'").append(str).append("'");
				if (i != arrays.length - 1) {
					buffer.append(",");
				}
			}
			buffer.append(") ");
		}
		buffer.append(") ");
		return buffer.toString();
	}

	/**
	 * 把"id1:zhangsan;id2:lisi"转为Map<String,String>
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> parseStringToMap(String str) {
		Map<String, String> map = new HashMap<String, String>();
		String[] strArray = null;
		if (str != null && str.length() > 0) {
			strArray = str.split(";", -1);
			if (strArray != null && strArray.length > 0) {
				String[] strArray2 = null;
				for (int i = 0; i < strArray.length; i++) {
					strArray2 = strArray[i].split(":", -1);
					if (strArray2 != null && strArray2.length == 2) {
						map.put(strArray2[0], strArray2[1]);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 功能: 把指定字符串original 从字符编码charsetName1 转化到charsetName2
	 * 
	 * @param original
	 *			原字符串
	 * @param charsetName1
	 *			原编码
	 * @param charsetName2
	 *			新编码
	 * @return
	 */
	public static String encode2Encode(String original, String charsetName1,
			String charsetName2) {
		if (original != null) {
			try {
				return new String(original.getBytes(charsetName1), charsetName2);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 功能: 过滤Html页面中的敏感字符,用于在script脚本中显示
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringToScript(String value) {
		return replaceStringByRule(value, new String[][] { { "'", "\\'" },
				{ "\"", "\\\"" }, { "\\", "\\\\" }, { "\r", "\\r" },
				{ "\n", "\\n" }, { "\t", "\\t" }, { "\f", "\\f" },
				{ "\b", "\\b" } });
	}

	/**
	 * 过滤Html页面中的敏感字符
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringToHtml(String value) {
		return replaceStringByRule(value, new String[][] { { "<", "&lt;" },
				{ ">", "&gt;" }, { "&", "&amp;" }, { "\"", "&quot;" },
				{ "'", "&#39;" }, { "\n", "<BR>" }, { "\r", "<BR>" } });
	}

	/**
	 * 过滤Html页面中的敏感字符，接受过滤的字符列表和转化后的值
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringByRule(String value, String[][] aString) {
		if (value == null) {
			return ("");
		}
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);

		for (int i = 0; i < content.length; i++) {
			boolean isTransct = false;
			for (int j = 0; j < aString.length; j++) {
				if (String.valueOf(content[i]).equals(aString[j][0])) {
					result.append(aString[j][1]);
					isTransct = true;
					break;
				}
			}
			if (!isTransct) {
				result.append(content[i]);
			}
		}
		return result.toString();
	}

	/**
	 * 显示数据前过滤掉null
	 * 
	 * @param str
	 * @return
	 */
	public static String prt(String str) {
		return prt(str, -1);
	}

	/**
	 * 显示数据前过滤掉null，截取一定位数
	 * 
	 * @param str
	 *			原字符串
	 * @param length
	 *			截断长度，即原字符串要保留的长度
	 * @return
	 */
	public static String prt(String str, int length) {
		return prt(str, length, null);
	}

	/**
	 * 显示数据前过滤掉null，截取一定位数，并加上表示，如省略号
	 * 
	 * @param str
	 *			原字符串
	 * @param length
	 *			截断长度，即原字符串要保留的长度
	 * @param tail
	 *			截断后在新字符串后添加的字符串
	 * @return 如果cutLength<0,则原字符串不截断并在尾部添加tailStr字串
	 */
	public static String prt(String str, int length, String tail) {
		String str1 = (str == null || "null".equals(str)) ? "" : str;
		;
		String tail1 = (tail == null || "null".equals(tail)) ? "" : tail;
		;
		if (length >= 0 && length <= str1.length()) {
			return str1.substring(0, length) + tail1;
		} else {
			return str1 + tail1;
		}
	}

	/**
	 * 判断一个数组是否包含一个字符串
	 * 
	 * @param arrays
	 * @param str
	 * @return
	 */
	public static boolean arrayContainString(String[] arrays, String str) {
		if (arrays == null || arrays.length == 0) {
			return false;
		}
		for (int i = 0; i < arrays.length; i++) {
			if (arrays[i].equals(str))
				return true;
		}
		return false;
	}
	
	/**
	 * 判断一个数组是否包含一个字符串
	 * 
	 * @param arrays
	 * @param str
	 * @return
	 */
	public static boolean arrayContainObject(Object[] arrays, String str) {
		if (arrays == null || arrays.length == 0) {
			return false;
		}
		for (int i = 0; i < arrays.length; i++) {
			if (arrays[i].toString().equals(str))
				return true;
		}
		return false;
	}

	/**
	 * 对url进行GBK编码
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeUrl(String url) {
		String rtStr = "";
		if (url != null && url.length() >= 0) {
			try {
				rtStr = URLEncoder.encode(url, "GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rtStr;
	}

	/**
	 * 功能: 把Map中的值依次取出来，以URL传值的方式拼成字符串
	 * 
	 * @param mValue
	 * @return
	 */
	public static String encodeUrlParameter(Map mValue) {
		return encodeUrlParameter(mValue, new String[0]);
	}

	/**
	 * 功能: 过滤掉指定的参数，把Map中的值依次取出来，以URL传值的方式拼成字符串，
	 * 
	 * @param mValue
	 * @param ignoreName
	 *			忽略的参数名数组
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeUrlParameter(Map mValue, String[] ignoreName) {
		String str = "";
		for (Iterator itMValue = mValue.keySet().iterator(); itMValue.hasNext();) {
			String tempKey = String.valueOf(itMValue.next());
			String tempValue = (mValue.get(tempKey) == null) ? "" : String
					.valueOf(mValue.get(tempKey));
			if (tempKey.startsWith("VENUS") || tempKey.startsWith("RANMIN")) {
				continue;
			}
			if (arrayContainString(ignoreName, tempKey)) {
				continue;
			}
			if (str.length() > 0) {
				str += "&";
			}
			str += tempKey + "=" + encodeUrl(tempValue);
		}
		return str;
	}

	/**
	 * 首字母变大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toFirstUpperCase(String str) {
		if (str == null || str.length() == 0) {
			return str;
		} else {
			String firstStr = str.substring(0, 1);
			return firstStr.toUpperCase() + str.substring(1);
		}
	}

	/**
	 * 功能: 把15位的身份证号码升级为18位
	 * 
	 * @param oldIdCard
	 * @return
	 */
	public static String updateIdCard(String oldIdCard) {
		String newIdCard = "";
		StringBuffer tempStrOld = new StringBuffer();
		tempStrOld.append(oldIdCard);
		int cOld[] = new int[17];
		int iSum = 0;
		oldIdCard = oldIdCard.substring(0, 6) + "19"
				+ oldIdCard.substring(6, oldIdCard.length());
		try {
			if (oldIdCard.length() != 17) {
				throw new Exception();
			}
			for (int i = 0; i < 17; i++) {
				cOld[i] = Integer.parseInt(String.valueOf(oldIdCard.charAt(i)));
			}
			int wi[] = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
					8, 4, 2 };
			iSum = 0;
			for (int i = 0; i < 17; i++) {
				iSum = iSum + cOld[i] * wi[i];
			}
		} catch (Exception e) {
			throw new RuntimeException("请输入正确格式的身份证号码!");
		}
		int y = iSum % 11;
		String strVer = new String("10X98765432");
		newIdCard = oldIdCard + strVer.substring(y, y + 1);
		return newIdCard;
	}

	/**
	 * 
	 * empty(对象为null判断)
	 * 
	 * @param obj
	 * @return 参数中文名
	 * @return 列出方法的返回值列表（如果需要返回值的话）
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static boolean empty(Object obj) {
		if (obj == null)
			return true;
		return false;
	}

	public static float getExcelCellAutoHeight2(String str,
			float fontCountInline) {
		float defaultRowHeight = 14.75f;// 每一行的高度指定
		float defaultCount = 0.00f;
		for (int i = 0; i < str.length(); i++) {
			float ff = getregex(str.substring(i, i + 1));
			defaultCount = defaultCount + ff;
		}
		return ((int) (defaultCount / fontCountInline) + 1) * defaultRowHeight;// 计算
	}

	public static float getExcelCellAutoHeight(float defaultRowHeight,
			String str, float fontCountInline, float defaultOnlyOneRowHeight) {
		// float defaultRowHeight = 14.75f;//每一行的高度指定
		str = replaceBlankAndSpace(str);
		float defaultCount = 0.00f;
		for (int i = 0; i < str.length(); i++) {
			float ff = getregex(str.substring(i, i + 1));
			defaultCount = defaultCount + ff;
		}
		if (defaultOnlyOneRowHeight >= ((int) (defaultCount / fontCountInline) + 1)
				* defaultRowHeight) {
			return defaultOnlyOneRowHeight;
		} else {
			return ((int) (defaultCount / fontCountInline) + 1)
					* defaultRowHeight;// 计算
		}
	}

	/**
	 * 
	 * setExcelRowAutoHeight(EXCEL设置自适应行高)
	 * 
	 * @param defaultRowHeight
	 *			默认的行高
	 * @param str
	 *			单元格中的字符串
	 * @param fontCountInline
	 *			一行中字符的个数
	 * @return 返回行高
	 * @return 列出方法的返回值列表（如果需要返回值的话）
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static float setExcelRowAutoHeight(float defaultRowHeight,
			String str, float fontCountInline) {
		// float defaultRowHeight = 14.75f;//每一行的高度指定
		str = replaceBlankAndSpace(str);
		float defaultCount = 0.00f;
		for (int i = 0; i < str.length(); i++) {
			float ff = getregex(str.substring(i, i + 1));
			defaultCount = defaultCount + ff;
		}
		return ((int) (defaultCount / fontCountInline) + 1) * defaultRowHeight;// 计算
	}

	/**
	 * 去掉换行回车
	 * 
	 * @param src
	 * @return
	 */
	public static String replaceBlankAndSpace(String src) {
		src = src.replaceAll("<br>", "");
		return src.replaceAll("\r\n", "");

	}

	/**
	 * 添加空格
	 * 
	 * @param src
	 * @param count
	 * @return
	 */
	public static String addSpace(String src, int count) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(src);
		for (int index = 0; index < count; index++) {
			buffer.append("\u0000");
		}
		return buffer.toString();
	}

	/**
	 * addAdditionalSpaceForFillCharactor(通过比较字数长度，填充额外的空格)
	 * 
	 * @param src
	 * @return 参数中文名
	 * @return 列出方法的返回值列表（如果需要返回值的话）
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static String addAdditionalSpaceForFillCharactor(String src) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(src);
		int length = src.length();
		if (length == 2) {
			buffer.append("\u0000\u0000");
		}
		return buffer.toString();
	}

	public static float getregex(String charStr) {

		if (charStr == " ") {
			return 0.5f;
		}

		// 判断是否为字母或字符
		if (Pattern.compile("^[A-Za-z0-9]+$").matcher(charStr).matches()) {
			return 0.5f;
		}
		// 判断是否为全角

		if (Pattern.compile("[\u4e00-\u9fa5]+$").matcher(charStr).matches()) {
			return 1.00f;
		}
		// 全角符号 及中文
		if (Pattern.compile("[^x00-xff]").matcher(charStr).matches()) {
			return 1.00f;
		}
		return 0.5f;

	}

	public static boolean isEmpty(String str){
		return StringUtils.isEmpty(str);
	}
	
	/**
	 * 生成随机串
	 * @param type 类型：char:字母,number:数字,both:字母或数字
	 * @param length 字符串长度
	 */
	public static String generateRandomStr(String type, int len) {
		String str = "";
		String[] seeds = null;
		if ("char".equals(type)) {
			seeds = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		} else if ("number".equals(type)) {
			seeds = new String[] {"1","2","3","4","5","6","7","8","9","0"};
		} else if ("both".equals(type)) {
			seeds = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9","0"};
		} else {
			seeds = new String[] {};
		}
		
		Random random = new java.util.Random();// 定义随机类
		while (str.length() < len) {
			int idx = random.nextInt(seeds.length);
			str += seeds[idx];
		}
		
		return str;
	}
}