package com.deertt.module.market.bill.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMarketDetailConstants extends IGlobalConstants {
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, bill_id, goods_id, goods_name, goods_image, limit_coin_quantity, send_coin_quantity, unit_price, quantity, sub_total, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, bill_id, goods_id, goods_name, goods_image, limit_coin_quantity, send_coin_quantity, unit_price, quantity, sub_total, status";

	public final static String SQL_INSERT = "INSERT INTO market_detail ( bill_id, goods_id, goods_name, goods_image, limit_coin_quantity, send_coin_quantity, unit_price, quantity, sub_total, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM market_detail WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM market_detail";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM market_detail WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE market_detail SET bill_id = ?, goods_id = ?, goods_name = ?, goods_image = ?, limit_coin_quantity = ?, send_coin_quantity = ?, unit_price = ?, quantity = ?, sub_total = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM market_detail";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM market_detail";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM market_detail";
	
	/**表名*/
	public final static String TABLE_NAME = "market_detail";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "订单明细";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
