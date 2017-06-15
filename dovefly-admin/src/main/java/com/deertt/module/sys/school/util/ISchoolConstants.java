package com.deertt.module.sys.school.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ISchoolConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "schoolController:read";
	
	public final static String PERM_WRITE = "schoolController:*";
	
	//URL
	public final static String CONTROLLER = "/schoolController";
	
	public final static String DEFAULT_REDIRECT = "/schoolController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/school";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/school/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, warehouse_id, warehouse_name, manager_id, manager_name, school_name, baidu_uid, baidu_title, baidu_address, baidu_longtitude, baidu_latitude, search_times, shop_count, user_count, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, warehouse_id, warehouse_name, manager_id, manager_name, school_name, baidu_uid, baidu_title, baidu_address, baidu_longtitude, baidu_latitude, search_times, shop_count, user_count, remark, sort, status";

	public final static String SQL_INSERT = "INSERT INTO sys_school ( city_id, city_name, warehouse_id, warehouse_name, manager_id, manager_name, school_name, baidu_uid, baidu_title, baidu_address, baidu_longtitude, baidu_latitude, search_times, shop_count, user_count, remark, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_school WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_school";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_school WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_school SET city_id = ?, city_name = ?, warehouse_id = ?, warehouse_name = ?, manager_id = ?, manager_name = ?, school_name = ?, baidu_uid = ?, baidu_title = ?, baidu_address = ?, baidu_longtitude = ?, baidu_latitude = ?, search_times = ?, shop_count = ?, user_count = ?, remark = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_UPDATE_SHOP_COUNT_BY_ID = "UPDATE sys_school sc SET sc.shop_count = (SELECT COUNT(id) FROM sys_shop WHERE school_id = sc.id AND status = '1') WHERE sc.id = ?";

	public final static String SQL_UPDATE_USER_COUNT_BY_ID = "UPDATE sys_school sc SET sc.user_count = (SELECT COUNT(id) FROM sys_user WHERE school_id = sc.id AND status = '1') WHERE sc.id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_school";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_school";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_school";
	
	/**表名*/
	public final static String TABLE_NAME = "sys_school";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "学校";
	
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
