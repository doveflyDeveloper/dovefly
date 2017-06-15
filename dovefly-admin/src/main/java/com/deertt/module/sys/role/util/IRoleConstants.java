package com.deertt.module.sys.role.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IRoleConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "roleController:read";
	
	public final static String PERM_WRITE = "roleController:*";
	
	//URL
	public final static String CONTROLLER = "/roleController";
	
	public final static String DEFAULT_REDIRECT = "/roleController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/role";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/role/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, name, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, name, remark, status";

	public final static String SQL_INSERT = "INSERT INTO sys_role ( name, remark, status, create_by, create_at, modify_by, modify_at ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_role WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_role";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_role WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_role SET name = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_role";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_role";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_role";
	
	public final static String SQL_QUERY_ROLES_BY_USER_ID = "SELECT r.id, r.name FROM sys_role r, sys_authority a WHERE r.id = a.role_id AND a.user_id = ?";

	public final static String SQL_DELETE_AUTHORIZE_RES = "DELETE FROM sys_permission WHERE role_id = ?";

	public final static String SQL_INSERT_AUTHORIZE_RES = "INSERT INTO sys_permission ( role_id, res_id ) VALUES ( ?, ? )";

	/**表名*/
	public final static String TABLE_NAME = "sys_role";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "角色";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
