package com.deertt.module.sys.warehouse.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IWarehouseConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "warehouseController:read";
	
	public final static String PERM_WRITE = "warehouseController:*";
	
	//URL
	public final static String CONTROLLER = "/warehouseController";
	
	public final static String DEFAULT_REDIRECT = "/warehouseController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/warehouse";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/warehouse/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, manager_id, manager_name, warehouse_name, warehouse_desc, warehouse_area, start_amount, balance_amount, locked_amount, halfway_amount, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, manager_id, manager_name, warehouse_name, warehouse_desc, warehouse_area, start_amount, balance_amount, locked_amount, halfway_amount, remark, status";

	public final static String SQL_INSERT = "INSERT INTO sys_warehouse ( city_id, city_name, manager_id, manager_name, warehouse_name, warehouse_desc, warehouse_area, start_amount, balance_amount, locked_amount, halfway_amount, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_warehouse WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_warehouse";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_warehouse WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_warehouse SET city_id = ?, city_name = ?, manager_id = ?, manager_name = ?, warehouse_name = ?, warehouse_desc = ?, warehouse_area = ?, start_amount = ?, balance_amount = ?, locked_amount = ?, halfway_amount = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";

	public final static String SQL_UPDATE_BALANCE_BY_ID = "UPDATE sys_warehouse SET balance_amount = ?, locked_amount = ?, modify_at = now() where id = ?";
	
	public final static String SQL_UPDATE_HALFWAY_BY_ID = "UPDATE sys_warehouse SET halfway_amount = ?, modify_at = now() where id = ?";

	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_warehouse";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_warehouse";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_warehouse";
	
	public final static String SQL_QUERY_DICT = "SELECT id k, warehouse_name v FROM sys_warehouse order by id";
	
	/**表名*/
	public final static String TABLE_NAME = "sys_warehouse";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "货仓";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
