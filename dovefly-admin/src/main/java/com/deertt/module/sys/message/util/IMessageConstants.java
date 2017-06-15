package com.deertt.module.sys.message.util;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.sys.message.vo.MessageVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMessageConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "messageController:read";
	
	public final static String PERM_WRITE = "messageController:*";
	
	//URL
	public final static String CONTROLLER = "/messageController";
	
	public final static String DEFAULT_REDIRECT = "/messageController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/message";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/message/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, user_id, user_name, title, type, content, link_url, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, user_id, user_name, title, type, content, link_url, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String SQL_INSERT = "INSERT INTO sys_message ( user_id, user_name, title, type, content, link_url, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_message WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_message";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_message WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_message SET user_id = ?, user_name = ?, title = ?, type = ?, content = ?, link_url = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";

	public final static String SQL_READ_BY_ID = "UPDATE sys_message SET status = '" + MessageVo.STATUS_READ + "', modify_at = now() WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_message";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_message";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_message";
	
	/**表名*/
	public final static String TABLE_NAME = "sys_message";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "系统消息";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
