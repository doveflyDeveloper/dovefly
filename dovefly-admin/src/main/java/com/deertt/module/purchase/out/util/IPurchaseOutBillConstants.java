package com.deertt.module.purchase.out.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IPurchaseOutBillConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "purchaseOutBillController:read";
	
	public final static String PERM_WRITE = "purchaseOutBillController:*";
	
	//URL
	public final static String CONTROLLER = "/purchaseOutBillController";
	
	public final static String DEFAULT_REDIRECT = "/purchaseOutBillController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/purchase/out";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/purchase/out/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, warehouse_id, warehouse_name, bill_code, bill_date, bill_time, amount, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, warehouse_id, warehouse_name, bill_code, bill_date, bill_time, amount, remark, status";

	public final static String SQL_INSERT = "INSERT INTO purchase_out_bill ( city_id, city_name, warehouse_id, warehouse_name, bill_code, bill_date, bill_time, amount, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM purchase_out_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM purchase_out_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM purchase_out_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE purchase_out_bill SET city_id = ?, city_name = ?, warehouse_id = ?, warehouse_name = ?, bill_code = ?, bill_date = ?, bill_time = ?, amount = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM purchase_out_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM purchase_out_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM purchase_out_bill";
	
	public final static String SQL_LIST_DETAILS = "SELECT o.id, o.city_id, o.city_name, o.warehouse_id, o.warehouse_name, o.bill_code, "
			+ "o.bill_date, o.amount, o.status, d.goods_id, d.goods_name, d.unit_price, d.quantity, d.sub_total "
			+ "FROM purchase_out_bill o, purchase_out_detail d WHERE o.id = d.bill_id "; 
	
	/**表名*/
	public final static String TABLE_NAME = "purchase_out_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "领料出库单";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
