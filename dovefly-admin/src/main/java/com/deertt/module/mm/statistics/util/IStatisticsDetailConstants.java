package com.deertt.module.mm.statistics.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IStatisticsDetailConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "statisticsDetailController:read";
	
	public final static String PERM_WRITE = "statisticsDetailController:*";
	
	//URL
	public final static String CONTROLLER = "/statisticsDetailController";
	
	public final static String DEFAULT_REDIRECT = "/statisticsDetailController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/mm/statisticsdetail";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/mm/statisticsdetail/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, bill_id, goods_id, goods_name, goods_image, origin_quantity, origin_amount, purchase_quantity, purchase_amount, purchase_back_quantity, purchase_back_amount, order_quantity, order_amount, order_back_quantity, order_back_amount, out_quantity, out_amount, stock_quantity, stock_amount, check_quantity, check_amount, dif_quantity, dif_amount, final_quantity, final_amount, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, bill_id, goods_id, goods_name, goods_image, origin_quantity, origin_amount, purchase_quantity, purchase_amount, purchase_back_quantity, purchase_back_amount, order_quantity, order_amount, order_back_quantity, order_back_amount, out_quantity, out_amount, stock_quantity, stock_amount, check_quantity, check_amount, dif_quantity, dif_amount, final_quantity, final_amount";

	public final static String SQL_INSERT = "INSERT INTO mm_statistics_detail ( bill_id, goods_id, goods_name, goods_image, origin_quantity, origin_amount, purchase_quantity, purchase_amount, purchase_back_quantity, purchase_back_amount, order_quantity, order_amount, order_back_quantity, order_back_amount, out_quantity, out_amount, stock_quantity, stock_amount, check_quantity, check_amount, dif_quantity, dif_amount, final_quantity, final_amount, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM mm_statistics_detail WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM mm_statistics_detail";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM mm_statistics_detail WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE mm_statistics_detail SET bill_id = ?, goods_id = ?, goods_name = ?, goods_image = ?, origin_quantity = ?, origin_amount = ?, purchase_quantity = ?, purchase_amount = ?, purchase_back_quantity = ?, purchase_back_amount = ?, order_quantity = ?, order_amount = ?, order_back_quantity = ?, order_back_amount = ?, out_quantity = ?, out_amount = ?, stock_quantity = ?, stock_amount = ?, check_quantity = ?, check_amount = ?, dif_quantity = ?, dif_amount = ?, final_quantity = ?, final_amount = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM mm_statistics_detail";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM mm_statistics_detail";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM mm_statistics_detail";
	
	/**表名*/
	public final static String TABLE_NAME = "mm_statistics_detail";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "结算明细";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
