package com.deertt.module.sys.notification.util;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.sys.notification.vo.NotificationVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface INotificationConstants extends IGlobalConstants {
	
	public final static int NOTIFY_LIMIT_TIMES = 2;//最多尝试发送次数
	
	//权限
	public final static String PERM_READ = "notificationController:read";
	
	public final static String PERM_WRITE = "notificationController:*";
	
	//URL
	public final static String CONTROLLER = "/notificationController";
	
	public final static String DEFAULT_REDIRECT = "/notificationController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/notification";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/notification/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, user_id, user_name, wechat_id, mobile, notify_type, notify_way, message, expect_notify_time, notify_time, notify_limit_times, notify_times, notify_status, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, user_id, user_name, wechat_id, mobile, notify_type, notify_way, message, expect_notify_time, notify_time, notify_limit_times, notify_times, notify_status, remark, status";

	public final static String SQL_INSERT = "INSERT INTO sys_notification ( user_id, user_name, wechat_id, mobile, notify_type, notify_way, message, expect_notify_time, notify_time, notify_limit_times, notify_times, notify_status, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_notification WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_notification";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_notification WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_notification SET user_id = ?, user_name = ?, wechat_id = ?, mobile = ?, notify_type = ?, notify_way = ?, message = ?, expect_notify_time = ?, notify_time = ?, notify_limit_times = ?, notify_times = ?, notify_status = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_notification";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_notification";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_notification";
	
	public final static String SQL_REMOVE_OLD_NOTIFICATION = "INSERT INTO sys_notification_bak SELECT * FROM sys_notification n WHERE n.status != '" + NotificationVo.NOTIFY_STATUS_NO + "' AND n.create_at < DATE_SUB(now(), INTERVAL ? DAY)";

	public final static String SQL_DELETE_OLD_NOTIFICATION = "DELETE FROM sys_notification WHERE status != '" + NotificationVo.NOTIFY_STATUS_NO + "' AND create_at < DATE_SUB(now(), INTERVAL ? DAY)";

	/**表名*/
	public final static String TABLE_NAME = "sys_notification";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "系统消息通知";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
