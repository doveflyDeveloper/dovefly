package com.deertt.module.report.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IReportConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "reportController:read";
	
	public final static String PERM_WRITE = "reportController:*";
	
	//URL
	public final static String CONTROLLER = "/reportController";
	
	public final static String DEFAULT_REDIRECT = "/reportController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/report";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/report";

}
