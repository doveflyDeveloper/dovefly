package com.deertt.module.mv.song.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMvSongSupportConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "mvsongsupportController:read";
	
	public final static String PERM_WRITE = "mvsongsupportController:*";
	
	//URL
	public final static String CONTROLLER = "/mvsongsupportController";
	
	public final static String DEFAULT_REDIRECT = "/mvsongsupportController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/mvsongsupport/mvsongsupport";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/mv/mvsongsupport/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, song_id, song_name, user_id, user_name, coin_quantity, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, song_id, song_name, user_id, user_name, coin_quantity, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String SQL_INSERT = "INSERT INTO mv_song_support (song_id, song_name, user_id, user_name, coin_quantity, remark, sort, status, create_by, create_at ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM mv_song_support WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM mv_song_support";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM mv_song_support WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE mv_song_support SET song_id = ?, song_name = ?, user_id = ?, user_name = ?, coin_quantity = ?, remark = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM mv_song_support";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM mv_song_support";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM mv_song_support";
	
	/**表名*/
	public final static String TABLE_NAME = "mv_song_support";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "歌唱比赛记录";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
