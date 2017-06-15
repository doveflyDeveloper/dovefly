package com.deertt.module.fund.refund.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IRefundConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "refundController:read";
	
	public final static String PERM_WRITE = "refundController:*";
	
	//URL
	public final static String CONTROLLER = "/refundController";
	
	public final static String DEFAULT_REDIRECT = "/refundController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/fund/refund";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/fund/refund/";
	
	//Sql语句																																															remark,status
	public final static String AFTER_SELECT_ALL   = "id, bill_type, seller_id, seller_name, buyer_id, buyer_name, refer_bill_code, bill_code, refund_time, refund_amount, refund_to, brief, pay_type, pay_code, pay_amount, pay_time, pay_status, pay_msg, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, bill_type, seller_id, seller_name, buyer_id, buyer_name, refer_bill_code, bill_code, refund_time, refund_amount, refund_to, brief, pay_type, pay_code, pay_amount, pay_time, pay_status, pay_msg, remark, status";

	public final static String SQL_INSERT = "INSERT INTO fund_refund ( bill_type, seller_id, seller_name, buyer_id, buyer_name, refer_bill_code, bill_code, refund_time, refund_amount, refund_to, brief, pay_type, pay_code, pay_amount, pay_time, pay_status, pay_msg, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM fund_refund WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM fund_refund";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM fund_refund WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE fund_refund SET bill_type = ?, seller_id = ?, seller_name = ?, buyer_id = ?, buyer_name = ?, refer_bill_code = ?, bill_code = ?, refund_time = ?, refund_amount = ?, refund_to = ?, brief = ?, pay_type = ?, pay_code = ?, pay_amount = ?, pay_time = ?, pay_status = ?, pay_msg = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM fund_refund";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM fund_refund";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM fund_refund";
	
	public final static String SQL_REPORT_GROUP_BY_STATUS = "SELECT status, count(id) bill_count FROM fund_refund #WHERE# GROUP BY status WITH ROLLUP";
	
	/**表名*/
	public final static String TABLE_NAME = "fund_refund";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "退款申请";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
