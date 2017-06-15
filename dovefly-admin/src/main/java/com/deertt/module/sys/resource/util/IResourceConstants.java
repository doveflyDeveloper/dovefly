package com.deertt.module.sys.resource.util;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.order.back.vo.OrderBackBillVo;
import com.deertt.module.order.bill.vo.OrderBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IResourceConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "resourceController:read";
	
	public final static String PERM_WRITE = "resourceController:*";
	
	//URL
	public final static String CONTROLLER = "/resourceController";
	
	public final static String DEFAULT_REDIRECT = "/resourceController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/resource";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/resource/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, code, name, parent_id, parent_code, parent_name, type, url, permission, level, is_leaf, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, code, name, parent_id, parent_code, parent_name, type, url, permission, level, is_leaf, remark, sort, status";

	public final static String SQL_INSERT = "INSERT INTO sys_resource ( code, name, parent_id, parent_code, parent_name, type, url, permission, level, is_leaf, remark, sort, status, create_by, create_at, modify_by, modify_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_resource WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_resource";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_resource WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_resource SET code = ?, name = ?, parent_id = ?, parent_code = ?, parent_name = ?, type = ?, url = ?, permission = ?, level = ?, is_leaf = ?, remark = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_resource";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_resource";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_resource";

	public final static String SQL_QUERY_RESOURCES_BY_ROLE_ID = "SELECT res.id, res.code, res.name, res.parent_id, res.parent_code, res.parent_name, res.type, res.url, res.permission, res.level, res.is_leaf, res.sort FROM sys_resource res, sys_permission p, sys_role r WHERE res.id = p.res_id AND p.role_id = r.id AND r.id = ? order by res.level asc, res.sort asc";

	public final static String SQL_QUERY_MENUS_BY_USER_ID = "SELECT distinct res.id, res.code, res.name, res.parent_id, res.parent_code, res.parent_name, res.type, res.url, res.permission, res.level, res.is_leaf, res.sort FROM sys_resource res, sys_permission p, sys_role r, sys_authority a, sys_user u WHERE res.type != '003' AND res.status = '1' AND res.id = p.res_id AND p.role_id = r.id AND r.id = a.role_id AND a.user_id = u.id AND u.id = ? order by res.level asc, res.sort asc";
	
	public final static String SQL_QUERY_TASK_TAG = "SELECT * FROM "
			+ "(SELECT COUNT(id) message_count FROM sys_message WHERE user_id = #user_id# AND status = '0') t1 ";
	
	public final static String SQL_QUERY_TASK_TAG_CITYMANAGER = "SELECT * FROM "
			+ "(SELECT COUNT(id) message_count FROM sys_message WHERE user_id = #user_id# AND status = '0') t1, "
			+ "(SELECT COUNT(id) order_bill_count FROM order_bill WHERE warehouse_id = #warehouse_id# AND status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_APPLY_FOR_REFUND + "','" + OrderBillVo.STATUS_APPLY_FOR_RETURN + "')) t3, "
			+ "(SELECT COUNT(id) order_back_bill_count FROM order_back_bill WHERE warehouse_id = #warehouse_id# AND status = '" + OrderBackBillVo.STATUS_SUBMIT + "') t4, "
			+ "(SELECT COUNT(id) user_apply_count FROM sys_user_apply WHERE city_id = #city_id# AND status = '0') t5 ";
	
	public final static String SQL_QUERY_TASK_TAG_OPERATIONMANAGER = "SELECT * FROM "
			+ "(SELECT COUNT(id) message_count FROM sys_message WHERE user_id = #user_id# AND status = '0') t1, "
			+ "(SELECT COUNT(id) user_apply_count FROM sys_user_apply WHERE city_id = #city_id# AND school_id in (SELECT id FROM sys_school WHERE manager_id = #user_id#) AND status = '0') t3 ";
	
	public final static String SQL_QUERY_TASK_TAG_SHOPKEEPER = "SELECT * FROM "
			+ "(SELECT COUNT(id) message_count FROM sys_message WHERE user_id = #user_id# AND status = '0') t1, "
			+ "(SELECT COUNT(id) order_bill_count FROM order_bill WHERE shop_id = #shop_id# AND status in ('" + OrderBillVo.STATUS_DELIVERED + "')) t3, "
			+ "(SELECT COUNT(id) trade_bill_count FROM trade_bill WHERE shop_id = #shop_id# AND status in ('2','4','7','8')) t4 ";
	
	public final static String SQL_QUERY_TASK_TAG_CASHER = "SELECT * FROM "
			+ "(SELECT COUNT(id) message_count FROM sys_message WHERE user_id = #user_id# AND status = '0') t5, "
			+ "(SELECT COUNT(id) fund_apply_count FROM fund_apply WHERE status in ('0','1','3')) t6, "
			+ "(SELECT COUNT(id) fund_refund_count FROM fund_refund WHERE status in ('0','1','3')) t8";
	
	/**表名*/
	public final static String TABLE_NAME = "sys_resource";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "系统功能";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//编号长度，用以计算数层级深度
	public final static int RES_CODE_LENGTH = 3;
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
