package com.deertt.module.sc.blog.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IBlogConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "blogController:read";
	
	public final static String PERM_WRITE = "blogController:*";
	
	//URL
	public final static String CONTROLLER = "/blogController";
	
	public final static String DEFAULT_REDIRECT = "/blogController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sc/blog";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sc/blog/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, school_id, school_name, title, type, image, brief, description, link_goods, issue_status, issue_time, issue_to, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, school_id, school_name, title, type, image, brief, description, link_goods, issue_status, issue_time, issue_to, sort, status";

	public final static String SQL_INSERT = "INSERT INTO sc_blog ( city_id, city_name, school_id, school_name, title, type, image, brief, description, link_goods, issue_status, issue_time, issue_to, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sc_blog WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sc_blog";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sc_blog WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sc_blog SET city_id = ?, city_name = ?, school_id = ?, school_name = ?, title = ?, type = ?, image = ?, brief = ?, description = ?, link_goods = ?, issue_status = ?, issue_time = ?, issue_to = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sc_blog";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sc_blog";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sc_blog";
	
	/**表名*/
	public final static String TABLE_NAME = "sc_blog";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "资讯文章";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
