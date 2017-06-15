package com.deertt.module.mm.statistics.util;

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
public interface IStatisticsBillConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "statisticsBillController:read";
	
	public final static String PERM_WRITE = "statisticsBillController:*";
	
	//URL
	public final static String CONTROLLER = "/statisticsBillController";
	
	public final static String DEFAULT_REDIRECT = "/statisticsBillController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/mm/statistics";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/mm/statistics/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, warehouse_id, warehouse_name, bill_code, bill_date, bill_time, origin_amount, purchase_amount, purchase_back_amount, order_amount, order_back_amount, out_amount, stock_amount, check_amount, dif_amount, final_amount, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, warehouse_id, warehouse_name, bill_code, bill_date, bill_time, origin_amount, purchase_amount, purchase_back_amount, order_amount, order_back_amount, out_amount, stock_amount, check_amount, dif_amount, final_amount, remark, status";

	public final static String SQL_INSERT = "INSERT INTO mm_statistics_bill ( city_id, city_name, warehouse_id, warehouse_name, bill_code, bill_date, bill_time, origin_amount, purchase_amount, purchase_back_amount, order_amount, order_back_amount, out_amount, stock_amount, check_amount, dif_amount, final_amount, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM mm_statistics_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM mm_statistics_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM mm_statistics_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE mm_statistics_bill SET city_id = ?, city_name = ?, warehouse_id = ?, warehouse_name = ?, bill_code = ?, bill_date = ?, bill_time = ?, origin_amount = ?, purchase_amount = ?, purchase_back_amount = ?, order_amount = ?, order_back_amount = ?, out_amount = ?, stock_amount = ?, check_amount = ?, dif_amount = ?, final_amount = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM mm_statistics_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM mm_statistics_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM mm_statistics_bill";
	
	/**表名*/
	public final static String TABLE_NAME = "mm_statistics_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "结算清单";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
