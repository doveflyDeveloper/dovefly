package com.deertt.module.mm.goods.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IGoodsWConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "goodsController:read";
	
	public final static String PERM_WRITE = "goodsController:*";
	
	//URL
	public final static String CONTROLLER = "/goodsWController";
	
	public final static String DEFAULT_REDIRECT = "/goodsWController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/mm/goods";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/mm/goods/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, warehouse_id, warehouse_name, item_id, barcode, name, title, tips, type, tag, weight, shelf_life, storage_type, producer, cost_price, sale_price, market_price, limit_coin_quantity, send_coin_quantity, buy_rule, spec, category_id, category_code, sold_volume, stock_sum, stock_locked, safe_line, max_limit, cmt_point, cmt_times, image, images, description, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, warehouse_id, warehouse_name, item_id, barcode, name, title, tips, type, tag, weight, shelf_life, storage_type, producer, cost_price, sale_price, market_price, limit_coin_quantity, send_coin_quantity, buy_rule, spec, category_id, category_code, sold_volume, stock_sum, stock_locked, safe_line, max_limit, cmt_point, cmt_times, image, images, description, remark, sort, status";

	public final static String SQL_INSERT = "INSERT INTO mm_goods_w ( city_id, city_name, warehouse_id, warehouse_name, item_id, barcode, name, title, tips, type, tag, weight, shelf_life, storage_type, producer, cost_price, sale_price, market_price, limit_coin_quantity, send_coin_quantity, buy_rule, spec, category_id, category_code, safe_line, max_limit, cmt_point, cmt_times, image, images, description, remark, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM mm_goods_w WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM mm_goods_w";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM mm_goods_w WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE mm_goods_w SET city_id = ?, city_name = ?, warehouse_id = ?, warehouse_name = ?, item_id = ?, barcode = ?, name = ?, title = ?, tips = ?, type = ?, tag = ?, weight = ?, shelf_life = ?, storage_type = ?, producer = ?, cost_price = ?, sale_price = ?, market_price = ?, limit_coin_quantity = ?, send_coin_quantity = ?, buy_rule = ?, spec = ?, category_id = ?, category_code = ?, safe_line = ?, max_limit = ?, cmt_point = ?, cmt_times = ?, image = ?, images = ?, description = ?, remark = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM mm_goods_w";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM mm_goods_w";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM mm_goods_w";
	
	public final static String SQL_UPDATE_STOCK_SUM_BY_ID = "UPDATE mm_goods_w SET stock_sum = ?, stock_locked = ?, sold_volume = ? WHERE id = ?";

	public final static String SQL_UPDATE_PRICE_BY_ID = "UPDATE mm_goods_w SET cost_price = ?, sale_price = ? WHERE id = ?";
	
	public final static String SQL_LOW_STOCK_REMIND = "SELECT warehouse_id, GROUP_CONCAT(CONCAT('【', name, '__', safe_line, '__', stock_sum, '】') SEPARATOR '\r\n') goods_info FROM mm_goods_w WHERE status = '1' AND stock_sum < safe_line GROUP BY warehouse_id";
	
	public final static String SQL_INSERT_RECORD = "INSERT INTO mm_goods_w_record ( goods_id, stock_sum_change, stock_sum, stock_locked_change, stock_locked, refer_bill_code, system, remark, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/**表名*/
	public final static String TABLE_NAME = "mm_goods_w";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "货仓商品";
	
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
