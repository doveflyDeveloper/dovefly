package com.deertt.module.fund.transition.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ITransitionConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "transitionController:read";
	
	public final static String PERM_WRITE = "transitionController:*";
	
	//URL
	public final static String CONTROLLER = "/transitionController";
	
	public final static String DEFAULT_REDIRECT = "/transitionController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/fund/transition";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/fund/transition/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, store_type, store_id, store_name, bill_code, transition_code, transition_type, transition_time, transition_amount, balance_amount, brief, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, store_type, store_id, store_name, bill_code, transition_code, transition_type, transition_time, transition_amount, balance_amount, brief, remark, status";

	public final static String SQL_INSERT = "INSERT INTO fund_transition ( store_type, store_id, store_name, bill_code, transition_code, transition_type, transition_time, transition_amount, balance_amount, brief, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM fund_transition WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM fund_transition";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM fund_transition WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE fund_transition SET store_type = ?, store_id = ?, store_name = ?, bill_code = ?, transition_code = ?, transition_type = ?, transition_time = ?, transition_amount = ?, balance_amount = ?, brief = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM fund_transition";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM fund_transition";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM fund_transition";
	
	/**表名*/
	public final static String TABLE_NAME = "fund_transition";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "资金交易明细";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
