package com.deertt.module.sys.dict.type.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IDictTypeConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "dictController:read";
	
	public final static String PERM_WRITE = "dictController:*";
	
	//URL
	public final static String CONTROLLER = "/dictTypeController";
	
	public final static String DEFAULT_REDIRECT = "/dictTypeController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/dict/type";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/dict/type/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, keyword, name, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, keyword, name, remark, status";

	public final static String SQL_INSERT = "INSERT INTO sys_dict_type ( keyword, name, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_dict_type WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_dict_type";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_dict_type WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_dict_type SET keyword = ?, name = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_dict_type";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_dict_type";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_dict_type";
	
	/**表名*/
	public final static String TABLE_NAME = "sys_dict_type";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "字典类型";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
