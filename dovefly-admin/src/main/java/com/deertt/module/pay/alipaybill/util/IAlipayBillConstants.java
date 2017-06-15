package com.deertt.module.pay.alipaybill.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IAlipayBillConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "alipayBillController:read";
	
	public final static String PERM_WRITE = "alipayBillController:*";
	
	//URL
	public final static String CONTROLLER = "/alipayBillController";
	
	public final static String DEFAULT_REDIRECT = "/alipayBillController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/pay/alipaybill";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/pay/alipaybill/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, balance, income, outcome, trans_date, trans_code_msg, sub_trans_code_msg, merchant_out_order_no, memo, buyer_account, seller_account, seller_fullname, currency, deposit_bank_no, goods_title, iw_account_log_id, partner_id, service_fee, service_fee_ratio, total_fee, trade_no, trade_refund_amount, sign_product_name, rate, check_status";

	public final static String AFTER_SELECT_SHORT = "id, balance, income, outcome, trans_date, trans_code_msg, sub_trans_code_msg, merchant_out_order_no, memo, buyer_account, seller_account, seller_fullname, currency, deposit_bank_no, goods_title, iw_account_log_id, partner_id, service_fee, service_fee_ratio, total_fee, trade_no, trade_refund_amount, sign_product_name, rate, check_status";

	public final static String SQL_INSERT = "INSERT INTO pay_alipay_bill ( balance, income, outcome, trans_date, trans_code_msg, sub_trans_code_msg, merchant_out_order_no, memo, buyer_account, seller_account, seller_fullname, currency, deposit_bank_no, goods_title, iw_account_log_id, partner_id, service_fee, service_fee_ratio, total_fee, trade_no, trade_refund_amount, sign_product_name, rate, check_status ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM pay_alipay_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM pay_alipay_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM pay_alipay_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE pay_alipay_bill SET balance = ?, income = ?, outcome = ?, trans_date = ?, trans_code_msg = ?, sub_trans_code_msg = ?, merchant_out_order_no = ?, memo = ?, buyer_account = ?, seller_account = ?, seller_fullname = ?, currency = ?, deposit_bank_no = ?, goods_title = ?, iw_account_log_id = ?, partner_id = ?, service_fee = ?, service_fee_ratio = ?, total_fee = ?, trade_no = ?, trade_refund_amount = ?, sign_product_name = ?, rate = ?, check_status = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM pay_alipay_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM pay_alipay_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM pay_alipay_bill";
	
	/**表名*/
	public final static String TABLE_NAME = "pay_alipay_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "支付宝支付订单";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
