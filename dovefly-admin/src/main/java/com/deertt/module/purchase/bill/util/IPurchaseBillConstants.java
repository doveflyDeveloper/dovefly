package com.deertt.module.purchase.bill.util;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.purchase.bill.vo.PurchaseBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IPurchaseBillConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "purchaseBillController:read";
	
	public final static String PERM_WRITE = "purchaseBillController:*";
	
	//URL
	public final static String CONTROLLER = "/purchaseBillController";
	
	public final static String DEFAULT_REDIRECT = "/purchaseBillController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/purchase/bill";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/purchase/bill";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, warehouse_id, warehouse_name, supplier_id, supplier_name, bill_code, bill_date, bill_time, amount, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, warehouse_id, warehouse_name, supplier_id, supplier_name, bill_code, bill_date, bill_time, amount, remark, status";

	public final static String SQL_INSERT = "INSERT INTO purchase_bill ( city_id, city_name, warehouse_id, warehouse_name, supplier_id, supplier_name, bill_code, bill_date, bill_time, amount, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM purchase_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM purchase_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM purchase_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE purchase_bill SET city_id = ?, city_name = ?, warehouse_id = ?, warehouse_name = ?, supplier_id = ?, supplier_name = ?, bill_code = ?, bill_date = ?, bill_time = ?, amount = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM purchase_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM purchase_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM purchase_bill";

	public final static String SQL_REPORT_GROUP_BY_BILL_DATE = "SELECT city_id, city_name, bill_date, COUNT(id) bill_count, SUM(amount) bill_amount "
			+ "FROM purchase_bill WHERE status = '" + PurchaseBillVo.STATUS_CHECKIN + "' AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY city_id, city_name, bill_date ORDER BY city_id ASC, bill_date ASC";
	
	public final static String SQL_REPORT_GROUP_BY_CITY = "SELECT city_id, city_name, COUNT(id) bill_count, SUM(amount) bill_amount "
			+ "FROM purchase_bill WHERE status = '" + PurchaseBillVo.STATUS_CHECKIN + "' AND city_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY city_id";
	
	public final static String SQL_REPORT_GROUP_BY_WAREHOUSE = "SELECT city_id, city_name, warehouse_id, warehouse_name, COUNT(id) bill_count, SUM(amount) bill_amount "
			+ "FROM purchase_bill WHERE status = '" + PurchaseBillVo.STATUS_CHECKIN + "' AND warehouse_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY warehouse_id";
	
	public final static String SQL_REPORT_GROUPY_BY_GOODS = "SELECT d.goods_id, d.goods_name, SUM(d.quantity) quantity, SUM(d.sub_total) sub_total "
			+ "FROM purchase_bill p, purchase_detail d "
			+ "WHERE p.id = d.bill_id AND p.status = '" + PurchaseBillVo.STATUS_CHECKIN + "' AND p.warehouse_id = ? AND p.bill_date >= ? AND p.bill_date <= ? "
			+ "GROUP BY d.goods_id ORDER BY sub_total DESC";
	
	public final static String SQL_LIST_DETAILS = "SELECT p.id, p.city_id, p.city_name, p.warehouse_id, p.warehouse_name, p.supplier_id, p.supplier_name, p.bill_code, "
			+ "p.bill_date, p.amount, p.status, d.goods_id, d.goods_name, d.unit_price, d.quantity, d.spec, d.spec_quantity, d.sub_total "
			+ "FROM purchase_bill p, purchase_detail d WHERE p.id = d.bill_id ";
	
	/**表名*/
	public final static String TABLE_NAME = "purchase_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "采购订单";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
