package com.deertt.module.order.bill.util;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.order.bill.vo.OrderBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IOrderBillConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "orderBillController:read";
	
	public final static String PERM_WRITE = "orderBillController:*";
	
	//URL
	public final static String CONTROLLER = "/orderBillController";
	
	public final static String DEFAULT_REDIRECT = "/orderBillController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/order/bill";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/order/bill";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, school_id, school_name, manager_id, manager_name, warehouse_id, warehouse_name, shop_id, shop_name, bill_code, bill_date, bill_time, quantity, limit_coin_quantity, send_coin_quantity, use_coin_quantity, use_coin_amount, real_amount, total_amount, income_amount, rcv_name, rcv_mobile, rcv_address, pay_type, pay_code, pay_amount, pay_time, pay_status, refund_type, refund_code, refund_amount, refund_time, refund_status, submit_time, send_time, receive_time, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, school_id, school_name, manager_id, manager_name, warehouse_id, warehouse_name, shop_id, shop_name, bill_code, bill_date, bill_time, quantity, limit_coin_quantity, send_coin_quantity, use_coin_quantity, use_coin_amount, real_amount, total_amount, income_amount, rcv_name, rcv_mobile, rcv_address, pay_type, pay_code, pay_amount, pay_time, pay_status, refund_type, refund_code, refund_amount, refund_time, refund_status, submit_time, send_time, receive_time, remark, status";

	public final static String SQL_INSERT = "INSERT INTO order_bill ( city_id, city_name, school_id, school_name, manager_id, manager_name, warehouse_id, warehouse_name, shop_id, shop_name, bill_code, bill_date, bill_time, quantity, limit_coin_quantity, send_coin_quantity, use_coin_quantity, use_coin_amount, real_amount, total_amount, income_amount, rcv_name, rcv_mobile, rcv_address, pay_type, submit_time, send_time, receive_time, remark, status, create_by, create_at) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM order_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM order_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM order_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE order_bill SET city_id = ?, city_name = ?, school_id = ?, school_name = ?, manager_id = ?, manager_name = ?, warehouse_id = ?, warehouse_name = ?, shop_id = ?, shop_name = ?, bill_code = ?, bill_date = ?, bill_time = ?, quantity = ?, limit_coin_quantity = ?, send_coin_quantity = ?, use_coin_quantity = ?, use_coin_amount = ?, real_amount = ?, total_amount = ?, income_amount = ?, rcv_name = ?, rcv_mobile = ?, rcv_address = ?, submit_time = ?, send_time = ?, receive_time = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";

	public final static String SQL_UPDATE_PAY_INFO_BY_ID = "UPDATE order_bill SET pay_type = ?, pay_code = ?, pay_amount = ?, pay_time= ?, pay_status = ? WHERE id = ?";

	public final static String SQL_UPDATE_REFUND_INFO_BY_ID = "UPDATE order_bill SET refund_type = ?, refund_code = ?, refund_amount = ?, refund_time = ?, refund_status = ? WHERE id = ?";

	public final static String SQL_UPDATE_STATUS_BY_ID = "UPDATE order_bill SET status = ? WHERE id = ?";

	public final static String SQL_SUBMIT_BY_ID = "UPDATE order_bill SET bill_date = now(), bill_time = now(), submit_time = now(), status = ? WHERE id = ?";

	public final static String SQL_DELIVER_BY_ID = "UPDATE order_bill SET send_time = now(), status = ? WHERE id = ?";

	public final static String SQL_RECEIVE_BY_ID = "UPDATE order_bill SET receive_time = now(), status = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM order_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM order_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM order_bill";
	
	public final static String SQL_REPORT_GROUP_BY_BILL_DATE = "SELECT city_id, city_name, bill_date, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM order_bill WHERE status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_DELIVERED + "','" + OrderBillVo.STATUS_RECEIVED + "') AND city_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY city_id, city_name, bill_date ORDER BY city_id ASC, bill_date ASC";
	
	public final static String SQL_REPORT_SHOP_GROUP_BY_BILL_DATE = "SELECT city_id, city_name, shop_id, shop_name, bill_date, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM order_bill WHERE status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_DELIVERED + "','" + OrderBillVo.STATUS_RECEIVED + "') AND city_id = ? AND shop_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY bill_date ORDER BY bill_date ASC";
	
	public final static String SQL_REPORT_MANAGER_GROUP_BY_BILL_DATE = "SELECT city_id, city_name, manager_id, manager_name, bill_date, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM order_bill WHERE status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_DELIVERED + "','" + OrderBillVo.STATUS_RECEIVED + "') AND city_id = ? AND manager_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY bill_date ORDER BY bill_date ASC";
	
	public final static String SQL_REPORT_GROUP_BY_CITY = "SELECT city_id, city_name, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM order_bill WHERE status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_DELIVERED + "','" + OrderBillVo.STATUS_RECEIVED + "') AND city_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY city_id ORDER BY city_id ASC";
	
	public final static String SQL_REPORT_GROUP_BY_MANAGER = "SELECT city_id, city_name, manager_id, manager_name, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM order_bill WHERE status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_DELIVERED + "','" + OrderBillVo.STATUS_RECEIVED + "') AND city_id = ? AND manager_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY manager_id";
	
	public final static String SQL_REPORT_GROUP_BY_SCHOOL = "SELECT city_id, city_name, school_id, school_name, manager_id, manager_name, shop_id, shop_name, COUNT(school_id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM order_bill WHERE status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_DELIVERED + "','" + OrderBillVo.STATUS_RECEIVED + "') AND city_id = ? AND manager_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY school_id";
	
	public final static String SQL_REPORT_GROUP_BY_SHOP = "SELECT city_id, city_name, school_id, school_name, manager_id, manager_name, warehouse_id, warehouse_name,  shop_id, shop_name, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM order_bill WHERE status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_DELIVERED + "','" + OrderBillVo.STATUS_RECEIVED + "') AND city_id = ? AND manager_id = ? AND shop_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY shop_id";
	
	public final static String SQL_REPORT_SHOPKEEPER = "SELECT city_id, city_name, school_id, school_name, manager_id, manager_name, shop_id, shop_name, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM order_bill WHERE status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_DELIVERED + "','" + OrderBillVo.STATUS_RECEIVED + "') AND city_id = ? AND manager_id = ? AND school_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY shop_id";
	
	public final static String SQL_REPORT_GROUPY_BY_GOODS = "SELECT o.city_name, d.goods_id, d.goods_name, SUM(d.quantity) quantity, SUM(d.sub_total) sub_total "
			+ "FROM order_bill o, order_detail d "
			+ "WHERE o.id = d.bill_id AND o.status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_DELIVERED + "','" + OrderBillVo.STATUS_RECEIVED + "') AND o.city_id = ? AND o.warehouse_id = ? AND o.shop_id = ? AND o.bill_date >= ? AND o.bill_date <= ? "
			+ "GROUP BY d.goods_id ORDER BY sub_total desc";
		
	public final static String SQL_LIST_DETAILS = "SELECT o.id, o.city_id, o.city_name, o.shop_id, o.shop_name, o.warehouse_id, o.warehouse_name, o.bill_code, "
			+ "o.bill_date, o.total_amount, o.real_amount, ifnull(o.use_coin_amount, 0) use_coin_amount, o.pay_type, o.pay_status, o.refund_type, o.refund_status, o.status, d.goods_id, d.goods_name, d.sale_price, d.quantity, d.sub_total "
			+ "FROM order_bill o, order_detail d WHERE o.id = d.bill_id "; 
	
	public final static String SQL_REPORT_GROUPY_BY_ORDER_GOODS = "SELECT o.id, o.city_id, o.city_name, o.shop_id, o.shop_name, o.warehouse_id, o.warehouse_name, o.bill_code, "
			+ "o.bill_date, o.pay_status, o.status, d.goods_id, d.goods_name, d.sale_price, d.quantity, d.sub_total "
			+ "FROM order_bill o, order_detail d WHERE o.id = d.bill_id  AND o.pay_status = '" + OrderBillVo.PAY_STATUS_SUCCESS + "' AND d.goods_id = ? AND bill_date >= ? AND bill_date <= ? " 
	        + "GROUP BY o.id ORDER BY o.bill_date ASC";
	        
	public final static String SQL_REPORT_GROUP_BY_STATUS = "SELECT status, count(id) bill_count FROM order_bill #WHERE# GROUP BY status WITH ROLLUP";

	/**表名*/
	public final static String TABLE_NAME = "order_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "进货订单";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
