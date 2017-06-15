package com.deertt.module.order.back.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IOrderBackBillConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "orderBackBillController:read";
	
	public final static String PERM_WRITE = "orderBackBillController:*";
	
	//URL
	public final static String CONTROLLER = "/orderBackBillController";
	
	public final static String DEFAULT_REDIRECT = "/orderBackBillController/query";
	
	public final static String JSP_PREFIX = "jsp/module/order/back";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/order/back";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, school_id, school_name, manager_id, manager_name, warehouse_id, warehouse_name, shop_id, shop_name, bill_code, bill_date, bill_time, amount, pay_type, pay_code, pay_amount, pay_time, pay_status, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, school_id, school_name, manager_id, manager_name, warehouse_id, warehouse_name, shop_id, shop_name, bill_code, bill_date, bill_time, amount, pay_type, pay_code, pay_amount, pay_time, pay_status, remark, status";

	public final static String SQL_INSERT = "INSERT INTO order_back_bill ( city_id, city_name, school_id, school_name, manager_id, manager_name, warehouse_id, warehouse_name, shop_id, shop_name, bill_code, bill_date, bill_time, amount, pay_type, pay_code, pay_amount, pay_time, pay_status, remark, status, create_by, create_at) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM order_back_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM order_back_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM order_back_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE order_back_bill SET city_id = ?, city_name = ?, school_id = ?, school_name = ?, manager_id = ?, manager_name = ?, warehouse_id = ?, warehouse_name = ?, shop_id = ?, shop_name = ?, bill_code = ?, bill_date = ?, bill_time = ?, amount = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";

	public final static String SQL_UPDATE_PAY_INFO_BY_ID = "UPDATE order_back_bill SET pay_type = ?, pay_code = ?, pay_amount = ?, pay_time= ?, pay_status = ? WHERE id = ?";

	public final static String SQL_UPDATE_REFUND_INFO_BY_ID = "UPDATE order_back_bill SET pay_type = ?, pay_code = ?, pay_amount = ?, pay_time= ?, pay_status = ? WHERE id = ?";

	public final static String SQL_UPDATE_STATUS_BY_ID = "UPDATE order_back_bill SET status = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM order_back_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM order_back_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM order_back_bill";
	
	public final static String SQL_LIST_DETAILS = "SELECT o.id, o.city_id, o.city_name, o.school_id, o.school_name, o.manager_id, o.manager_name, o.warehouse_id, "
			+ "o.warehouse_name, o.shop_id, o.shop_name, o.bill_code, o.bill_date, o.bill_time, o.amount, o.pay_type, o.pay_code, o.pay_amount, o.pay_time, o.pay_status, o.pay_time, o.status, d.goods_id, d.goods_name, d.unit_price, d.quantity, d.sub_total "
			+ "FROM order_back_bill o, order_back_detail d WHERE o.id = d.bill_id "; 
	
	/**表名*/
	public final static String TABLE_NAME = "order_back_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "退货订单";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
