package com.deertt.frame.base.web.el;

public class EscapeXml {
	
	public static String escapeXml(String str) {
		if(str == null) {
			return null;
		}
		str = str.replace("&", "&amp;");
		str = str.replace(">", "&gt;");
		str = str.replace("<", "&lt;");
		str = str.replace(" ", "&nbsp;");
		str = str.replace("\"", "&quot;");
		str = str.replace("\'", "&#39;");
		str = str.replace("\n", "<br/> ");
		return str;
	}

	public static String htmlDecode(String str) {
		if(str == null) {
			return null;
		}
		str = str.replace("&amp;", "&");
		str = str.replace("&gt;", ">");
		str = str.replace("&lt;", "<");
		str = str.replace("&nbsp;", " ");
		str = str.replace("&quot;", "\"");
		str = str.replace("&#39;", "\'");
		str = str.replace("<br/> ", "\n");
		return str;
	}
	
}
