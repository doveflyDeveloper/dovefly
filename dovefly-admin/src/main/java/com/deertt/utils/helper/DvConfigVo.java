package com.deertt.utils.helper;

public class DvConfigVo {
	
	/**
	 * 获得默认数据源的数据库类型，默认数据库类型是NULL
	 * IGlobalConstants.DATABASE_PRODUCT_NAME_...
	 */
	private String databaseProductName = null;
	
	/**
	 * 系统是否开发调试状态(性能很低，便利于开发调试。同时sql的?替换输出，日志记录了sql真实数据)
	 * 默认不是开发调试状态，是正式运行状态
	 */
	private boolean systemDebugMode = true;

	/**
	 * 系统用户是否唯一登录，同时登录会强制踢出第一个用户
	 */
	private boolean userUniqueLogin = true;
	
	/**
	 * 默认的分页条数
	 */
	private int defaultPageSize = 15;
	
	/**
	 * 默认的最大导出记录数。
	 */
	private int defaultMaxExportSize = 5000;
	
	public int getDefaultMaxExportSize() {
		return defaultMaxExportSize;
	}

	public void setDefaultMaxExportSize(int defaultMaxExportSize) {
		this.defaultMaxExportSize = defaultMaxExportSize;
	}
	
	/**
	 * 是否记录request的执行时间和SQL数量
	 */
	private boolean logRequest = true;
	
	/**
	 * 是否记住当前url列表的在第几行
	 */
	private boolean rememberPage = false;

	/**
	 * @return the databaseProductName
	 */
	String getDatabaseProductName() {
		return databaseProductName;
	}

	/**
	 * @param databaseProductName the databaseProductName to set
	 */
	void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}

	/**
	 * @return the systemDebugMode
	 */
	boolean isSystemDebugMode() {
		return systemDebugMode;
	}

	/**
	 * @param systemDebugMode the systemDebugMode to set
	 */
	void setSystemDebugMode(boolean systemDebugMode) {
		this.systemDebugMode = systemDebugMode;
	}

	/**
	 * @return the userUniqueLogin
	 */
	boolean isUserUniqueLogin() {
		return userUniqueLogin;
	}

	/**
	 * @param userUniqueLogin the userUniqueLogin to set
	 */
	void setUserUniqueLogin(boolean userUniqueLogin) {
		this.userUniqueLogin = userUniqueLogin;
	}

	/**
	 * @return the defaultPageSize
	 */
	int getDefaultPageSize() {
		return defaultPageSize;
	}

	/**
	 * @param defaultPageSize the defaultPageSize to set
	 */
	void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	/**
	 * @return the logRequest
	 */
	boolean isLogRequest() {
		return logRequest;
	}

	/**
	 * @param logRequest the logRequest to set
	 */
	void setLogRequest(boolean logRequest) {
		this.logRequest = logRequest;
	}

	public boolean isRememberPage() {
		return rememberPage;
	}

	public void setRememberPage(boolean rememberPage) {
		this.rememberPage = rememberPage;
	}

}