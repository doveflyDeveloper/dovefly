package com.deertt.module.mm.check.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IStockCheckDetailConstants extends IGlobalConstants {
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, bill_id, goods_id, goods_name, goods_image, stock_quantity, stock_amount, check_quantity, check_amount, dif_quantity, dif_amount, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, bill_id, goods_id, goods_name, goods_image, stock_quantity, stock_amount, check_quantity, check_amount, dif_quantity, dif_amount, status";

	public final static String SQL_INSERT = "INSERT INTO mm_stock_check_detail ( bill_id, goods_id, goods_name, goods_image, stock_quantity, stock_amount, check_quantity, check_amount, dif_quantity, dif_amount, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM mm_stock_check_detail WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM mm_stock_check_detail";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM mm_stock_check_detail WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE mm_stock_check_detail SET bill_id = ?, goods_id = ?, goods_name = ?, goods_image = ?, stock_quantity = ?, stock_amount = ?, check_quantity = ?, check_amount = ?, dif_quantity = ?, dif_amount = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM mm_stock_check_detail";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM mm_stock_check_detail";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM mm_stock_check_detail";
	
	/**表名*/
	public final static String TABLE_NAME = "mm_stock_check_detail";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "盘点明细";
	
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
