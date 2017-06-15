package com.deertt.module.mv.show.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMvShowConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "mvShowController:read";
	
	public final static String PERM_WRITE = "mvShowController:*";
	
	//URL
	public final static String CONTROLLER = "/mvShowController";
	
	public final static String DEFAULT_REDIRECT = "/mvShowController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/mv/show";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/mv/show/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, school_id, school_name, user_id, user_name, recommend_id, recommend_name, name, mobile, email, major, mv_name, mv_type, mv_brief, mv_images, mv_coin, mv_desc, mv_file, step, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, school_id, school_name, user_id, user_name, recommend_id, recommend_name, name, mobile, email, major, mv_name, mv_type, mv_brief, mv_images, mv_coin, mv_desc, mv_file, step, remark, sort, status";

	public final static String SQL_INSERT = "INSERT INTO mv_show ( city_id, city_name, school_id, school_name, user_id, user_name, recommend_id, recommend_name, name, mobile, email, major, mv_name, mv_type, mv_brief, mv_images, mv_coin, mv_desc, mv_file, step, remark, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM mv_show WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM mv_show";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM mv_show WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE mv_show SET city_id = ?, city_name = ?, school_id = ?, school_name = ?, user_id = ?, user_name = ?, recommend_id = ?, recommend_name = ?, name = ?, mobile = ?, email = ?, major = ?, mv_name = ?, mv_type = ?, mv_brief = ?, mv_images = ?, mv_coin = ?, mv_desc = ?, mv_file = ?, step = ?, remark = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM mv_show";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM mv_show";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM mv_show";
	
	/**表名*/
	public final static String TABLE_NAME = "mv_show";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "电影剧本";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
