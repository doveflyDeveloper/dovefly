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
public interface IGoodsMHotConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "goodsController:read";
	
	public final static String PERM_WRITE = "goodsController:*";
	
	//URL
	public final static String CONTROLLER = "/goodsMHotController";
	
	public final static String DEFAULT_REDIRECT = "/goodsMHotController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/mm/goods";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/mm/goods/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, market_id, market_name, goods_id, goods_name, goods_image, cost_price, sale_price, market_price, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, market_id, market_name, goods_id, goods_name, goods_image, cost_price, sale_price, market_price, remark, sort, status";

	public final static String SQL_INSERT = "INSERT INTO mm_goods_m_hot ( city_id, city_name, market_id, market_name, goods_id, goods_name, goods_image, cost_price, sale_price, market_price, remark, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM mm_goods_m_hot WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM mm_goods_m_hot";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM mm_goods_m_hot WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE mm_goods_m_hot SET city_id = ?, city_name = ?, market_id = ?, market_name = ?, goods_id = ?, goods_name = ?, goods_image = ?, cost_price = ?, sale_price = ?, market_price = ?, remark = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM mm_goods_m_hot";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM mm_goods_m_hot";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM mm_goods_m_hot";
	
	/**表名*/
	public final static String TABLE_NAME = "mm_goods_m_hot";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "特荐商品";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
