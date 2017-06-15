package com.deertt.module.mm.item.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMmItemConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "mmItemController:read";
	
	public final static String PERM_WRITE = "mmItemController:*";
	
	//URL
	public final static String CONTROLLER = "/mmItemController";
	
	public final static String DEFAULT_REDIRECT = "/mmItemController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/mm/item";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/mm/item/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, barcode, barcode_check, name, spec, weight, shelf_life, storage_type, producer, image, images, description, remark, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, barcode, barcode_check, name, spec, weight, shelf_life, storage_type, producer, image, images, description, remark, sort, status";

	public final static String SQL_INSERT = "INSERT INTO mm_item ( barcode, barcode_check, name, spec, weight, shelf_life, storage_type, producer, image, images, description, remark, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM mm_item WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM mm_item";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM mm_item WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE mm_item SET barcode = ?, barcode_check = ?, name = ?, spec = ?, weight = ?, shelf_life = ?, storage_type = ?, producer = ?, image = ?, images = ?, description = ?, remark = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM mm_item";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM mm_item";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM mm_item";
	
	/**表名*/
	public final static String TABLE_NAME = "mm_item";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "货品资料";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
