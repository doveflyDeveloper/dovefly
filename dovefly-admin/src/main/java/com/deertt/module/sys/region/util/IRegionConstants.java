package com.deertt.module.sys.region.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IRegionConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "regionController:read";
	
	public final static String PERM_WRITE = "regionController:*";
	
	//URL
	public final static String CONTROLLER = "/regionController";
	
	public final static String DEFAULT_REDIRECT = "/regionController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/region";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/region/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, warehouse_id, warehouse_name, manager_id, manager_name, code, name, full_name, parent_id, parent_code, parent_name, parent_full_name, level, is_leaf, remark, baidu_uid, baidu_title, baidu_address, baidu_longtitude, baidu_latitude, search_times, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, warehouse_id, warehouse_name, manager_id, manager_name, code, name, full_name, parent_id, parent_code, parent_name, parent_full_name, level, is_leaf, remark, baidu_uid, baidu_title, baidu_address, baidu_longtitude, baidu_latitude, search_times, sort, status";

	public final static String SQL_INSERT = "INSERT INTO sys_region ( city_id, city_name, warehouse_id, warehouse_name, manager_id, manager_name, code, name, full_name, parent_id, parent_code, parent_name, parent_full_name, level, is_leaf, remark, baidu_uid, baidu_title, baidu_address, baidu_longtitude, baidu_latitude, search_times, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_region WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_region";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_region WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_region SET city_id = ?, city_name = ?, warehouse_id = ?, warehouse_name = ?, manager_id = ?, manager_name = ?, code = ?, name = ?, full_name = ?, parent_id = ?, parent_code = ?, parent_name = ?, parent_full_name = ?, level = ?, is_leaf = ?, remark = ?, baidu_uid = ?, baidu_title = ?, baidu_address = ?, baidu_longtitude = ?, baidu_latitude = ?, search_times = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_region";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_region";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_region";
	
	/**表名*/
	public final static String TABLE_NAME = "sys_region";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "区域";
	
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//编号长度，用以计算数层级深度
	public final static int REG_CODE_LENGTH = 3;
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
