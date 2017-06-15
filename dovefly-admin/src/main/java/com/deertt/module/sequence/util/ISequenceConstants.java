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
public interface ISequenceConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "sequenceController:read";
	
	public final static String PERM_WRITE = "sequenceController:*";
	
	//URL
	public final static String CONTROLLER = "/sequenceController";
	
	public final static String DEFAULT_REDIRECT = "/sequenceController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/purchase/sequence";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sequence/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, sequence_name, sequence_value, sequence_step, version";

	public final static String AFTER_SELECT_SHORT = "id, sequence_name, sequence_value, sequence_step, version";

	public final static String SQL_INSERT = "INSERT INTO bus_sequence ( sequence_name, sequence_value, sequence_step, version ) VALUES ( ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM bus_sequence WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM bus_sequence";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM bus_sequence WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE bus_sequence SET sequence_name = ?, sequence_value = ?, sequence_step = ?, version = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM bus_sequence";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM bus_sequence";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM bus_sequence";
	
	public final static String SQL_UPDATE_BY_ID_AND_VERSION = "UPDATE bus_sequence SET sequence_value = ?, version = version + 1 WHERE id = ? AND version = ?";
	
	/**表名*/
	public final static String TABLE_NAME = "bus_sequence";
	
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
