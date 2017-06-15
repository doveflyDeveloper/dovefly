package com.test;

import org.apache.commons.lang.StringEscapeUtils;

public class EscapeString {
	public static void main(String[] args) throws Exception {
		String str = "<table>中 国	</table>";
		System.out.println("用escapeJava方法转义之后的字符串为:"
				+ StringEscapeUtils.escapeJava(str));
		System.out.println("用unescapeJava方法反转义之后的字符串为:"
				+ StringEscapeUtils.unescapeJava(StringEscapeUtils
						.escapeJava(str)));

		System.out.println("用escapeHtml方法转义之后的字符串为:"
				+ StringEscapeUtils.escapeHtml(str));
		System.out.println("用unescapeHtml方法反转义之后的字符串为:"
				+ StringEscapeUtils.unescapeHtml(StringEscapeUtils
						.escapeHtml(str)));

		System.out.println("用escapeXml方法转义之后的字符串为:"
				+ StringEscapeUtils.escapeXml(str));
		System.out.println("用unescapeXml方法反转义之后的字符串为:"
				+ StringEscapeUtils.unescapeXml(StringEscapeUtils
						.escapeXml(str)));

		System.out.println("用escapeJavaScript方法转义之后的字符串为:"
				+ StringEscapeUtils.escapeJavaScript(str));
		System.out.println("用unescapeJavaScript方法反转义之后的字符串为:"
				+ StringEscapeUtils.unescapeJavaScript(StringEscapeUtils
						.escapeJavaScript(str)));
		/**
		 * 输出结果如下： 用escapeJava方法转义之后的字符串为:/u4E2D/u56FD/u5171/u4EA7/u515A
		 * 用unescapeJava方法反转义之后的字符串为:中国 用escapeHtml方法转义之后的字符串为:中国
		 * 用unescapeHtml方法反转义之后的字符串为:中国 用escapeXml方法转义之后的字符串为:中国
		 * 用unescapeXml方法反转义之后的字符串为:中国
		 * 用escapeJavaScript方法转义之后的字符串为:/u4E2D/u56FD/u5171/u4EA7/u515A
		 * 用unescapeJavaScript方法反转义之后的字符串为:中国
		 */
	}
}