package com.deertt.module.sc.sell.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ISellConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "sellController:read";
	
	public final static String PERM_WRITE = "sellController:*";
	
	//URL
	public final static String CONTROLLER = "/sellController";
	
	public final static String DEFAULT_REDIRECT = "/sellController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sc/sell";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sc/sell/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, title, type, is_new, price, old_price, seller_name, seller_mobile, seller_qq, seller_weixin, image, images, description, issue_range, issue_date, end_date, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, title, type, is_new, price, old_price, seller_name, seller_mobile, seller_qq, seller_weixin, image, images, description, issue_range, issue_date, end_date, sort, status";

	public final static String SQL_INSERT = "INSERT INTO sc_sell ( title, type, is_new, price, old_price, seller_name, seller_mobile, seller_qq, seller_weixin, image, images, description, issue_range, issue_date, end_date, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sc_sell WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sc_sell";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sc_sell WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sc_sell SET title = ?, type = ?, is_new = ?, price = ?, old_price = ?, seller_name = ?, seller_mobile = ?, seller_qq = ?, seller_weixin = ?, image = ?, images = ?, description = ?, issue_range = ?, issue_date = ?, end_date = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sc_sell";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sc_sell";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sc_sell";
	
	/**表名*/
	public final static String TABLE_NAME = "sc_sell";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "二手买卖";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
