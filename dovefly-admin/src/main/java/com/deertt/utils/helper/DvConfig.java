package com.deertt.utils.helper;

import java.io.File;

public final class DvConfig {
	/**
	 * 配置类的全局唯一单例
	 */
	private static DvConfigVo singleton = new DvConfigVo();
	
	/**
	 * 得到配置类的全局唯一单例
	 * @return the singleton
	 */
	public static DvConfigVo getSingleton() {
		return singleton;
	}

	/**
	 * 获得默认数据源的数据库类型
	 * IGlobalConstants.DATABASE_PRODUCT_NAME_...
	 * @return
	 */
	public static String getDatabaseProductName() {
		return singleton.getDatabaseProductName();
	}
	
	/**
	 * 系统是否开发调试状态(系统综合运行性能较低，优化了应用启动速度。同时sql的?替换输出，日志记录了sql真实数据)
	 */
	public static boolean systemDebugMode() {
		 return singleton.isSystemDebugMode();
	}
	
	/**
	 * 系统用户是否唯一登录，同时登录会强制踢出第一个用户
	 */
	public static boolean userUniqueLogin() {
		return singleton.isUserUniqueLogin();
	}
	
	/**
	 * 默认的分页条数
	 */
	public static int defaultPageSize() {
		return singleton.getDefaultPageSize();
	}
	
	/**
	 * 是否记录request的执行时间和SQL数量
	 * @return the logRequest
	 */
	public static boolean isLogRequest() {
		return singleton.isLogRequest();
	}
	
	
	//未加入rm.xml文件的配置
	
	/**
	 * 系统缓存检查周期
	 * @return
	 */
	public static long cacheCheckInterval() {
		return 1000 * 2;
	}
	
	/**
	 * 是否全局监控
	 * @return
	 */
	public static boolean globalMonitor() {
		return true;
	}
	
	/**
	 * 系统缓存刷新周期
	 * @return
	 */
	public static long cacheFlushInterval() {
		return 1000 * 60 * 5L;
	}
	
	/**
	 * 翻页是否用rs.absolute(index)的方案
	 */
	public static boolean isAbsolutePage() {
		return false;
	}
	
	/**
	 * 批处理sql的最大记录日志数量
	 */
	public static int maxLogSqlBatchSize() {
		return 100;
	}
	
	/**
	 * 系统用户登录是否DEMO状态(不校验用户数据库)
	 */
	public static boolean userDemoMode() {
		return true;
	}
	
	/**
	 * 是否给insert和update的sql语句自动加ts
	 */
	public static boolean sqlUpdateAutoAppendTs() {
		return false;
	}
	
	/**
	 * 默认的临时文件夹
	 */
	public static File defaultTempDir() {
		return new File(System.getProperty("java.io.tmpdir") + File.separator + "rm");
	}
	
	/**
	 * 默认编码
	 */
	public static String defaultEncode() {
		return "UTF-8";
	}
	
	/**
	 * 默认实数数值的精度
	 */
	public static int defaultNumberScale() {
		return 2;
	}
	
	/**
	 * 登录时是否有校验码
	 */
	public static boolean loginValidateVerifyCode() {
		return true;
	}
	
	/**
	 * 登录是持否支持cookie
	 */
	public static boolean loginCookie() {
		return true;
	}
	
	/**
	 * cookie默认值365天
	 */
	public static int defaultCookieAge() {
		return 365 * 24 * 60 * 60;
	}
		
	/**
	 * ajax提交是否已json格式，还是post表单提交？
	 */
	public static boolean isSubmitJson() {
		return false;
	}
	
	/**
	 * 默认的树形编码起始值，适用于简单的纯数字树，每个节点下最多有900个子节点
	 */
	public static String defaultTreeCodeFirst() {
		return "100";
	}
	
	/**
	 * 指定最大循环次数，防止死循环
	 */
	public static int maxCircleCount() {
		return 10000;
	}
	
	/**
	 * 定义单实例全局缓存的最大容量，防止溢出攻击，如公开的url列表
	 * @return
	 */
	public static int maxCacheSize() {
		return 10000;
	}
}