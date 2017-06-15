package com.deertt.module.crm.supplier.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ISupplierConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "supplierController:read";
	
	public final static String PERM_WRITE = "supplierController:*";
	
	//URL
	public final static String CONTROLLER = "/supplierController";
	
	public final static String DEFAULT_REDIRECT = "/supplierController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/crm/supplier";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/crm/supplier/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, name, email, tel, address, linkman, linkman_mobile, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, name, email, tel, address, linkman, linkman_mobile, remark, status";

	public final static String SQL_INSERT = "INSERT INTO crm_supplier ( city_id, city_name, name, email, tel, address, linkman, linkman_mobile, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM crm_supplier WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM crm_supplier";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM crm_supplier WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE crm_supplier SET city_id = ?, city_name = ?, name = ?, email = ?, tel = ?, address = ?, linkman = ?, linkman_mobile = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM crm_supplier";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM crm_supplier";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM crm_supplier";
	
	/**表名*/
	public final static String TABLE_NAME = "crm_supplier";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "供应商";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
