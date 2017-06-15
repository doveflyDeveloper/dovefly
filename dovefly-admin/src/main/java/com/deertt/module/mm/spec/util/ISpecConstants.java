package com.deertt.module.mm.spec.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ISpecConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "specController:read";
	
	public final static String PERM_WRITE = "specController:*";
	
	//URL
	public final static String CONTROLLER = "/specController";
	
	public final static String DEFAULT_REDIRECT = "/specController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/mm/spec";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/mm/spec/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, name, quantity, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, name, quantity, remark, sort, status";

	public final static String SQL_INSERT = "INSERT INTO mm_spec ( name, quantity, remark, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM mm_spec WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM mm_spec";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM mm_spec WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE mm_spec SET name = ?, quantity = ?, remark = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM mm_spec";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM mm_spec";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM mm_spec";
	
	/**表名*/
	public final static String TABLE_NAME = "mm_spec";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "包装规格";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
