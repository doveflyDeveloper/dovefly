package com.deertt.quickbundle.vo;

import com.deertt.quickbundle.util.ConfigConstants;

import com.deertt.utils.helper.date.DvDateHelper;

public class Project {
	private String dbProductName = ConfigConstants.default_dbProductName;
	private String driver = ConfigConstants.default_driver;
	private String url = ConfigConstants.default_url;
	private String userName = ConfigConstants.default_userName;
	private String password = ConfigConstants.default_password;
	
	private String baseProjectPath = ConfigConstants.default_baseProjectPath;
	private String projectName = ConfigConstants.default_projectName;
	private String webAppName = ConfigConstants.default_webAppName;
	private String toTablenameKeyword = ConfigConstants.default_toTablenameKeyword;
	private String authorName = "fengcm";
	private String buildDate = DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyy-MM-dd HH:mm:ss");
	private String javaPackageName = ConfigConstants.default_javaPackageName;
	private String javaFileRealPath = ConfigConstants.default_javaFileRealPath;
	private String jspSourcePath = ConfigConstants.default_jspSourcePath;
	private String xlsTemplateBasePath = ConfigConstants.default_xlsTemplateBasePath;
	
	public String getDbProductName() {
		return dbProductName;
	}
	public void setDbProductName(String dbProductName) {
		this.dbProductName = dbProductName;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBaseProjectPath() {
		return baseProjectPath;
	}
	public void setBaseProjectPath(String baseProjectPath) {
		this.baseProjectPath = baseProjectPath;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getWebAppName() {
		return webAppName;
	}
	public void setWebAppName(String webAppName) {
		this.webAppName = webAppName;
	}
	public String getToTablenameKeyword() {
		return toTablenameKeyword;
	}
	public void setToTablenameKeyword(String toTablenameKeyword) {
		this.toTablenameKeyword = toTablenameKeyword;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}
	public String getJavaPackageName() {
		return javaPackageName;
	}
	public void setJavaPackageName(String javaPackageName) {
		this.javaPackageName = javaPackageName;
	}
	public String getJavaFileRealPath() {
		return javaFileRealPath;
	}
	public void setJavaFileRealPath(String javaFileRealPath) {
		this.javaFileRealPath = javaFileRealPath;
	}
	public String getJspSourcePath() {
		return jspSourcePath;
	}
	public void setJspSourcePath(String jspSourcePath) {
		this.jspSourcePath = jspSourcePath;
	}
	public String getXlsTemplateBasePath() {
		return xlsTemplateBasePath;
	}
	public void setXlsTemplateBasePath(String xlsTemplateBasePath) {
		this.xlsTemplateBasePath = xlsTemplateBasePath;
	}

}
