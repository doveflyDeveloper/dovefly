package com.deertt.module.fund.recharge.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IRechargeConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "rechargeController:read";
	
	public final static String PERM_WRITE = "rechargeController:*";
	
	//URL
	public final static String CONTROLLER = "/rechargeController";
	
	public final static String DEFAULT_REDIRECT = "/rechargeController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/fund/recharge";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/fund/recharge/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, store_type, store_id, store_name, user_id, user_name, bill_code, recharge_time, recharge_amount, brief, pay_type, pay_code, pay_amount, pay_time, pay_status, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, store_type, store_id, store_name, user_id, user_name, bill_code, recharge_time, recharge_amount, brief, pay_type, pay_code, pay_amount, pay_time, pay_status, remark, status";
	
	public final static String SQL_INSERT = "INSERT INTO fund_recharge ( store_type, store_id, store_name, user_id, user_name, bill_code, recharge_time, recharge_amount, brief, pay_type, pay_code, pay_amount, pay_time, pay_status, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM fund_recharge WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM fund_recharge";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM fund_recharge WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE fund_recharge SET store_type = ?, store_id = ?, store_name = ?, user_id = ?, user_name = ?, bill_code = ?, recharge_time = ?, recharge_amount = ?, brief = ?, pay_type = ?, pay_code = ?, pay_amount = ?, pay_time = ?, pay_status = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM fund_recharge";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM fund_recharge";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM fund_recharge";
	
	/**表名*/
	public final static String TABLE_NAME = "fund_recharge";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "充值交易";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
