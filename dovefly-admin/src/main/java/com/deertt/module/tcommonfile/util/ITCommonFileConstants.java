package com.deertt.module.tcommonfile.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ITCommonFileConstants extends IGlobalConstants {
	
	//URL
	public final static String TIP_JSP_URL = "/jsp/module/tcommonfile/tipTCommonFile.jsp";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "file_id, owner_table_name, owner_bill_id, owner_column_name, file_name, file_url, file_type, file_size, remark, usable_state, order_code, create_user_id, create_time, create_ip, modify_user_id, modify_time, modify_ip, reserved_1, reserved_2, reserved_3, reserved_4, reserved_5";

	public final static String AFTER_SELECT_SHORT = "file_id, owner_table_name, owner_bill_id, owner_column_name, file_name, file_url, file_type, file_size, remark";

	public final static String SQL_INSERT = "INSERT INTO t_common_file ( file_id, owner_table_name, owner_bill_id, owner_column_name, file_name, file_url, file_type, file_size, remark, usable_state, order_code, create_user_id, create_time, create_ip, modify_user_id, modify_time, modify_ip, reserved_1, reserved_2, reserved_3, reserved_4, reserved_5 ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM t_common_file WHERE file_id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM t_common_file";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM t_common_file WHERE file_id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE t_common_file SET owner_table_name = ?, owner_bill_id = ?, owner_column_name = ?, file_name = ?, file_url = ?, file_type = ?, file_size = ?, remark = ?, usable_state = ?, order_code = ?, modify_user_id = ?, modify_time = ?, modify_ip = ?, reserved_1 = ?, reserved_2 = ?, reserved_3 = ?, reserved_4 = ?, reserved_5 = ? WHERE file_id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(file_id) FROM t_common_file";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM t_common_file";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM t_common_file";
	
	/**表名*/
	public final static String TABLE_NAME = "t_common_file";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "附件信息";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "file_id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
