package com.deertt.module.sys.dict.data.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IDictDataConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "dictController:read";
	
	public final static String PERM_WRITE = "dictController:*";
	
	//URL
	public final static String CONTROLLER = "/dictDataController";
	
	public final static String DEFAULT_REDIRECT = "/dictDataController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/dict/data";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/dict/data/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, type_id, dic_key, dic_value, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, type_id, dic_key, dic_value, remark, sort, status";

	public final static String SQL_INSERT = "INSERT INTO sys_dict_data ( type_id, dic_key, dic_value, remark, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_dict_data WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_dict_data";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_dict_data WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_dict_data SET type_id = ?, dic_key = ?, dic_value = ?, remark = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_dict_data";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_dict_data";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_dict_data";

	public final static String SQL_QUERY_ALL_ENABLED = "SELECT t_data.id, t_data.type_id, t_data.dic_key, t_data.dic_value, t_data.sort, t_type.keyword FROM sys_dict_data t_data, sys_dict_type t_type WHERE t_data.type_id = t_type.id AND t_type.status = '1' AND t_data.status = '1'";	
	
	/**表名*/
	public final static String TABLE_NAME = "sys_dict_data";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "字典数据";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
