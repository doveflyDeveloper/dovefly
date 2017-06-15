package com.deertt.module.mm.category.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ICategoryConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "categoryController:read";
	
	public final static String PERM_WRITE = "categoryController:*";
	
	//URL
	public final static String CONTROLLER = "/categoryController";
	
	public final static String DEFAULT_REDIRECT = "/categoryController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/mm/category";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/mm/category/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, code, name, parent_id, parent_code, parent_name, level, is_leaf, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, code, name, parent_id, parent_code, parent_name, level, is_leaf, remark, sort, status";

	public final static String SQL_INSERT = "INSERT INTO mm_category ( code, name, parent_id, parent_code, parent_name, level, is_leaf, remark, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM mm_category WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM mm_category";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM mm_category WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE mm_category SET code = ?, name = ?, parent_id = ?, parent_code = ?, parent_name = ?, level = ?, is_leaf = ?, remark = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM mm_category";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM mm_category";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM mm_category";
	
	public final static String SQL_QUERY_DICT = "SELECT id k, concat(REPEAT('&nbsp;', (level - 1) * 2), name) v, code, sort, level FROM mm_category WHERE status = '1' ORDER BY code, sort";
	
	/**表名*/
	public final static String TABLE_NAME = "mm_category";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "商品分类";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//编号长度，用以计算数层级深度
	public final static int CAT_CODE_LENGTH = 3;
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
