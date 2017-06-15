package com.deertt.module.sys.shop.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IShopConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "shopController:read";
	
	public final static String PERM_WRITE = "shopController:*";
	
	//URL
	public final static String CONTROLLER = "/shopController";
	
	public final static String DEFAULT_REDIRECT = "/shopController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/shop";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/shop/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, school_id, school_name, warehouse_id, warehouse_name, manager_id, manager_name, shopkeeper_id, shopkeeper_name, shop_name, shop_logo, shop_desc, shop_area, shop_status, shop_sort, start_amount, balance_amount, locked_amount, halfway_amount, loanable_amount, loan_amount, interest_amount, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, school_id, school_name, warehouse_id, warehouse_name, manager_id, manager_name, shopkeeper_id, shopkeeper_name, shop_name, shop_logo, shop_desc, shop_area, shop_status, shop_sort, start_amount, balance_amount, locked_amount, halfway_amount, loanable_amount, loan_amount, interest_amount, remark, status";

	public final static String SQL_INSERT = "INSERT INTO sys_shop ( city_id, city_name, school_id, school_name, warehouse_id, warehouse_name, manager_id, manager_name, shopkeeper_id, shopkeeper_name, shop_name, shop_logo, shop_desc, shop_area, shop_status, shop_sort, start_amount, balance_amount, locked_amount, halfway_amount, loanable_amount, loan_amount, interest_amount, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_shop WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_shop";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_shop WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_shop SET city_id = ?, city_name = ?, school_id = ?, school_name = ?, warehouse_id = ?, warehouse_name = ?, manager_id = ?, manager_name = ?, shopkeeper_id = ?, shopkeeper_name = ?, shop_name = ?, shop_logo = ?, shop_desc = ?, shop_area = ?, shop_status = ?, shop_sort = ?, start_amount = ?, balance_amount = ?, locked_amount = ?, halfway_amount = ?, loanable_amount = ?, loan_amount = ?, interest_amount = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";

	public final static String SQL_UPDATE_BALANCE_BY_ID = "UPDATE sys_shop SET balance_amount = ?, locked_amount = ?, modify_at = now() where id = ?";
	
	public final static String SQL_UPDATE_HALFWAY_BY_ID = "UPDATE sys_shop SET halfway_amount = ?, modify_at = now() where id = ?";
	
	public final static String SQL_UPDATE_LOAN_BY_ID = "UPDATE sys_shop SET balance_amount = ?, loan_amount = ?, interest_amount = ?, modify_at = now() where id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_shop";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_shop";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_shop";
	
	public final static String SQL_REPORT_GROUP_BY_WAREHOUSE = "SELECT warehouse_id, warehouse_name, DATE_FORMAT(create_at, '%y年第%u周') weeks, COUNT(id) shop_count FROM sys_shop "
			+ "WHERE status = '1' AND warehouse_id = ? AND create_at >= ? AND create_at <= ? "
			+ "GROUP BY warehouse_id, weeks ORDER BY warehouse_id ASC, weeks ASC"; 
	
	public final static String SQL_REPORT_GROUP_BY_MANAGER = "SELECT warehouse_id, warehouse_name, manager_id, manager_name, DATE_FORMAT(create_at, '%y年第%u周') weeks, COUNT(id) shop_count FROM sys_shop "
			+ "WHERE status = '1' AND warehouse_id = ? AND manager_name = ? AND create_at >= ? AND create_at <= ? "
			+ "GROUP BY manager_id, weeks ORDER BY manager_id ASC, weeks ASC"; 
	
	public final static String SQL_REPORT_GROUP_BY_SCHOOL = "SELECT warehouse_id, warehouse_name, school_id, school_name, DATE_FORMAT(create_at, '%y年第%u周') weeks, COUNT(id) shop_count FROM sys_shop "
			+ "WHERE status = '1' AND warehouse_id = ? AND manager_id = ? AND school_name = ? AND create_at >= ? AND create_at <= ? "
			+ "GROUP BY school_id, weeks ORDER BY school_id ASC, weeks ASC"; 
	
	/**表名*/
	public final static String TABLE_NAME = "sys_shop";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "店铺";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
