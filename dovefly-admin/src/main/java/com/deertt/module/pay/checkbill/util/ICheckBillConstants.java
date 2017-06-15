package com.deertt.module.pay.checkbill.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ICheckBillConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "checkBillController:read";
	
	public final static String PERM_WRITE = "checkBillController:*";
	
	//URL
	public final static String CONTROLLER = "/checkBillController";
	
	public final static String DEFAULT_REDIRECT = "/checkBillController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/pay/checkbill";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/pay/checkbill/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, pay_table_name, pay_bill_id, bill_type, bill_table_name, bill_id, bill_code, check_time, check_msg, check_status, deal_status";

	public final static String AFTER_SELECT_SHORT = "id, pay_table_name, pay_bill_id, bill_type, bill_table_name, bill_id, bill_code, check_time, check_msg, check_status, deal_status";

	public final static String SQL_INSERT = "INSERT INTO pay_check_bill ( pay_table_name, pay_bill_id, bill_type, bill_table_name, bill_id, bill_code, check_time, check_msg, check_status, deal_status ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM pay_check_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM pay_check_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM pay_check_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE pay_check_bill SET pay_table_name = ?, pay_bill_id = ?, bill_type = ?, bill_table_name = ?, bill_id = ?, bill_code = ?, check_time = ?, check_msg = ?, check_status = ?, deal_status = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM pay_check_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM pay_check_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM pay_check_bill";
	
	/**表名*/
	public final static String TABLE_NAME = "pay_check_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "对账信息";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
