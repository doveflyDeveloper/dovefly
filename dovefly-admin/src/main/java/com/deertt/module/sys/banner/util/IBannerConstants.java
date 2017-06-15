package com.deertt.module.sys.banner.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IBannerConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "bannerController:read";
	
	public final static String PERM_WRITE = "bannerController:*";
	
	//URL
	public final static String CONTROLLER = "/bannerController";
	
	public final static String DEFAULT_REDIRECT = "/bannerController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/banner";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/banner/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, title, device, image, url, start_time, end_time, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, title, device, image, url, start_time, end_time, sort, status";

	public final static String SQL_INSERT = "INSERT INTO sys_banner ( title, device, image, url, start_time, end_time, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_banner WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_banner";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_banner WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_banner SET title = ?, device = ?, image = ?, url = ?, start_time = ?, end_time = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_banner";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_banner";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_banner";
	
	/**表名*/
	public final static String TABLE_NAME = "sys_banner";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "网站插页";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
