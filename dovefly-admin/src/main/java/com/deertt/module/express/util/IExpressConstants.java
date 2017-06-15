package com.deertt.module.express.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IExpressConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "expressController:read";
	
	public final static String PERM_WRITE = "expressController:*";
	
	//URL
	public final static String CONTROLLER = "/expressController";
	
	public final static String DEFAULT_REDIRECT = "/expressController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/purchase/express";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/express/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, exp_company, exp_tracking_no, exp_date, exp_amount, sender_name, sender_mobile, sender_address, receiver_name, receiver_mobile, receiver_address, deliver_progress, deliver_status, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, exp_company, exp_tracking_no, exp_date, exp_amount, sender_name, sender_mobile, sender_address, receiver_name, receiver_mobile, receiver_address, deliver_progress, deliver_status, remark, status";

	public final static String SQL_INSERT = "INSERT INTO express_bill ( exp_company, exp_tracking_no, exp_date, exp_amount, sender_name, sender_mobile, sender_address, receiver_name, receiver_mobile, receiver_address, deliver_progress, deliver_status, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM express_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM express_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM express_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE express_bill SET exp_company = ?, exp_tracking_no = ?, exp_date = ?, exp_amount = ?, sender_name = ?, sender_mobile = ?, sender_address = ?, receiver_name = ?, receiver_mobile = ?, receiver_address = ?, deliver_progress = ?, deliver_status = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM express_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM express_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM express_bill";
	
	/**表名*/
	public final static String TABLE_NAME = "express_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "快递物流";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
