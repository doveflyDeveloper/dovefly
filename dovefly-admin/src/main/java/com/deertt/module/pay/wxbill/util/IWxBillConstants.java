package com.deertt.module.pay.wxbill.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IWxBillConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "wxBillController:read";
	
	public final static String PERM_WRITE = "wxBillController:*";
	
	//URL
	public final static String CONTROLLER = "/wxBillController";
	
	public final static String DEFAULT_REDIRECT = "/wxBillController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/pay/wxbill";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/pay/wxbill/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, trade_time, gh_id, mch_id, sub_mch, device_id, wx_order, bz_order, open_id, trade_type, trade_status, bank, currency, total_money, red_packet_money, wx_refund, bz_refund, refund_money, red_packet_refund, refund_type, refund_status, product_name, bz_data_packet, fee, rate, check_status";

	public final static String AFTER_SELECT_SHORT = "id, trade_time, gh_id, mch_id, sub_mch, device_id, wx_order, bz_order, open_id, trade_type, trade_status, bank, currency, total_money, red_packet_money, wx_refund, bz_refund, refund_money, red_packet_refund, refund_type, refund_status, product_name, bz_data_packet, fee, rate, check_status";

	public final static String SQL_INSERT = "INSERT INTO pay_wx_bill ( trade_time, gh_id, mch_id, sub_mch, device_id, wx_order, bz_order, open_id, trade_type, trade_status, bank, currency, total_money, red_packet_money, wx_refund, bz_refund, refund_money, red_packet_refund, refund_type, refund_status, product_name, bz_data_packet, fee, rate, check_status ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM pay_wx_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM pay_wx_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM pay_wx_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE pay_wx_bill SET trade_time = ?, gh_id = ?, mch_id = ?, sub_mch = ?, device_id = ?, wx_order = ?, bz_order = ?, open_id = ?, trade_type = ?, trade_status = ?, bank = ?, currency = ?, total_money = ?, red_packet_money = ?, wx_refund = ?, bz_refund = ?, refund_money = ?, red_packet_refund = ?, refund_type = ?, refund_status = ?, product_name = ?, bz_data_packet = ?, fee = ?, rate = ?, check_status = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM pay_wx_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM pay_wx_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM pay_wx_bill";
	
	/**表名*/
	public final static String TABLE_NAME = "pay_wx_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "微信支付";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
