package com.deertt.quickbundle.util;

import com.deertt.utils.helper.date.DvDateHelper;

public class ConfigConstants {
	
	public final static String default_driver = "com.mysql.jdbc.Driver";
	
	public final static String default_url = "jdbc:mysql://localhost:3306/dove?jdbcCompliantTruncation=false";
	
	public final static String default_userName = "root";
	
	public final static String default_password = "root";
	
	public final static String default_dbProductName = "MySQL";
	
	public final static String default_baseProjectPath = "D:/IDE/workspace/deertt-admin";
	
	public final static String default_projectName = "deertt-admin";
	
	public final static String default_webAppName = "WebContent";
	
	public final static String default_toTablenameKeyword = "TableName";
	
	public final static String default_authorName = "fengcm";
	
	public final static String default_buildDate = DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyy-MM-dd HH:mm:ss");
	
	public final static String default_javaPackageName = "com.deertt.module";
	
	public final static String default_javaFileRealPath = "src/com/deertt/module";
	
	public final static String default_jspSourcePath = "jsp/module";
	
	public final static String default_xlsTemplateBasePath = "files/templates/excel";
	
	public final static String[] default_no_build_fields = new String[]{"status", "sort", "create_by", "create_at", "modify_by", "modify_at"};
	
	public final static String[] default_keyColumn_keywords = new String[]{"title", "name"};

}