package com.deertt.module.sequence.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ISequenceCodeConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "sequenceController:read";
	
	public final static String PERM_WRITE = "sequenceController:*";
	
	//URL
	public final static String CONTROLLER = "/sequenceController";
	
	public final static String DEFAULT_REDIRECT = "/sequenceController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/purchase/sequence";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sequence/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, sequence_name, biz_code";

	public final static String AFTER_SELECT_SHORT = "id, sequence_name, biz_code";

	public final static String SQL_INSERT = "INSERT INTO sequence_code ( sequence_name, biz_code ) VALUES ( ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sequence_code WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sequence_code";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sequence_code WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sequence_code SET sequence_name = ?, biz_code = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sequence_code";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sequence_code";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sequence_code";
	
	public final static String SQL_FIND_SEQUENCE_CODE_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sequence_code WHERE id = ?";
	
	/**表名*/
	public final static String TABLE_NAME = "sequence_code";
	
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
