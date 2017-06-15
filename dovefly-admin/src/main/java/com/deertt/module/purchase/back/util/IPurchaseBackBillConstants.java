package com.deertt.module.purchase.back.util;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.purchase.back.vo.PurchaseBackBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IPurchaseBackBillConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "purchaseBackBillController:read";
	
	public final static String PERM_WRITE = "purchaseBackBillController:*";
	
	//URL
	public final static String CONTROLLER = "/purchaseBackBillController";
	
	public final static String DEFAULT_REDIRECT = "/purchaseBackBillController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/purchase/back";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/purchase/back";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, warehouse_id, warehouse_name, supplier_id, supplier_name, bill_code, bill_date, bill_time, amount, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, warehouse_id, warehouse_name, supplier_id, supplier_name, bill_code, bill_date, bill_time, amount, remark, status";

	public final static String SQL_INSERT = "INSERT INTO purchase_back_bill ( city_id, city_name, warehouse_id, warehouse_name, supplier_id, supplier_name, bill_code, bill_date, bill_time, amount, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM purchase_back_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM purchase_back_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM purchase_back_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE purchase_back_bill SET city_id = ?, city_name = ?, warehouse_id = ?, warehouse_name = ?, supplier_id = ?, supplier_name = ?, bill_code = ?, bill_date = ?, bill_time = ?, amount = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM purchase_back_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM purchase_back_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM purchase_back_bill";

	public final static String SQL_REPORT_GROUP_BY_BILL_DATE = "SELECT city_id, city_name, bill_date, COUNT(id) bill_count, SUM(amount) bill_amount "
			+ "FROM purchase_back_bill WHERE status = '" + PurchaseBackBillVo.STATUS_CONFIRM + "' AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY city_id, city_name, bill_date ORDER BY city_id ASC, bill_date ASC";
	
	public final static String SQL_REPORT_GROUP_BY_WAREHOUSE = "SELECT city_name, warehouse_name user_name, COUNT(id) bill_count, SUM(amount) amount "
			+ "FROM purchase_back_bill WHERE status = '" + PurchaseBackBillVo.STATUS_CONFIRM + "' AND warehouse_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY city_name, user_name";
	
	public final static String SQL_REPORT_GROUPY_BY_GOODS = "SELECT d.goods_id, d.goods_name, SUM(d.quantity) quantity, SUM(d.sub_total) sub_total "
			+ "FROM purchase_back_bill p, purchase_back_detail d "
			+ "WHERE p.id = d.bill_id AND p.status = '" + PurchaseBackBillVo.STATUS_CONFIRM + "' AND p.warehouse_id = ? AND p.bill_date >= ? AND p.bill_date <= ? "
			+ "GROUP BY d.goods_id ORDER BY sub_total DESC";
	
	public final static String SQL_LIST_DETAILS = "SELECT p.id, p.city_id, p.city_name, p.warehouse_id, p.warehouse_name, p.supplier_id, p.supplier_name, p.bill_code, "
			+ "p.bill_date, p.amount, p.status, d.goods_id, d.goods_name, d.unit_price, d.quantity, d.sub_total "
			+ "FROM purchase_back_bill p, purchase_back_detail d WHERE p.id = d.bill_id "; 
	
	/**表名*/
	public final static String TABLE_NAME = "purchase_back_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "采购退货单";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
