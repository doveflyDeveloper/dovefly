package com.deertt.module.fund.interest.util;

import java.math.BigDecimal;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IInterestConstants extends IGlobalConstants {
	
	public final static BigDecimal DAY_RATE = new BigDecimal(0.00065);
	
	//权限
	public final static String PERM_READ = "interestController:read";
	
	public final static String PERM_WRITE = "interestController:*";
	
	//URL
	public final static String CONTROLLER = "/interestController";
	
	public final static String DEFAULT_REDIRECT = "/interestController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/fund/interest";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/fund/interest/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, shop_id, shop_name, interest_date, capital_amount, interest_amount, all_interest_amount, interest_rate, brief, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, shop_id, shop_name, interest_date, capital_amount, interest_amount, all_interest_amount, interest_rate, brief, remark";

	public final static String SQL_INSERT = "INSERT INTO fund_interest ( shop_id, shop_name, interest_date, capital_amount, interest_amount, all_interest_amount, interest_rate, brief, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM fund_interest WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM fund_interest";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM fund_interest WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE fund_interest SET shop_id = ?, shop_name = ?, interest_date = ?, capital_amount = ?, interest_amount = ?, all_interest_amount = ?, interest_rate = ?, brief = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM fund_interest";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM fund_interest";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM fund_interest";
	
	/**表名*/
	public final static String TABLE_NAME = "fund_interest";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "利息流水明细";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
