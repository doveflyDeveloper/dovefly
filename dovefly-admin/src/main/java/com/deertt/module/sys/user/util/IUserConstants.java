package com.deertt.module.sys.user.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IUserConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "userController:read";
	
	public final static String PERM_WRITE = "userController:*";
	
	//URL
	public final static String CONTROLLER = "/userController";
	
	public final static String DEFAULT_REDIRECT = "/userController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sys/user";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sys/user/";

	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, school_id, school_name, real_name, nick_name, account, password, mobile, email, dorm_address, address, avatar, birthday, level, point, last_login_time, login_times, pwd_reset, coin_quantity, wechat_subscribe, wechat_account, wechat_id, wechat_unionid, wechat_avatar, alipay_account, df_shop, fav_shops, manage_warehouse_id, manage_shop_id, manage_market_id, remark, status, create_by, create_at, modify_by, modify_at";
	
    public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, school_id, school_name, real_name, nick_name, account, password, mobile, email, dorm_address, address, avatar, birthday, level, point, last_login_time, login_times, pwd_reset, coin_quantity, wechat_subscribe, wechat_account, wechat_id, wechat_unionid, wechat_avatar, alipay_account, df_shop, fav_shops, manage_warehouse_id, manage_shop_id, manage_market_id, remark, status, create_by, create_at, modify_by, modify_at";
	
	public final static String SQL_INSERT = "INSERT INTO sys_user ( city_id, city_name, school_id, school_name, real_name, nick_name, account, password, mobile, email, dorm_address, address, avatar, birthday, level, point, last_login_time, login_times, pwd_reset, coin_quantity, wechat_subscribe, wechat_account, wechat_id, wechat_unionid, wechat_avatar, alipay_account, df_shop, fav_shops, manage_warehouse_id, manage_shop_id, manage_market_id, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sys_user WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sys_user";
	
	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sys_user WHERE id = ?";
	
	public final static String SQL_UPDATE_BY_ID = "UPDATE sys_user SET city_id = ?, city_name = ?, school_id = ?, school_name = ?, real_name = ?, nick_name = ?, account = ?, password = ?, mobile = ?, email = ?, dorm_address = ?, address = ?, avatar = ?, birthday = ?, level = ?, point = ?, last_login_time = ?, login_times = ?, pwd_reset = ?, coin_quantity = ?, wechat_subscribe = ?, wechat_account = ?, wechat_id = ?, wechat_unionid = ?, wechat_avatar = ?, alipay_account = ?, df_shop = ?, fav_shops = ?, manage_warehouse_id = ?, manage_shop_id = ?, manage_market_id = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sys_user";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sys_user";
	
	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sys_user";
	
	public final static String SQL_UPDATE_PWD_BY_ID = "UPDATE sys_user SET password = ?, pwd_reset = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_UPDATE_LOGIN_BY_ID = "UPDATE sys_user SET last_login_time = now(), login_times = ifnull(login_times, 0) + 1 WHERE id = ?";
	
	public final static String SQL_CHANGE_BY_ID = "UPDATE sys_user SET real_name = ?, email = ?, address = ?, alipay_account = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_DELETE_AUTHORIZE_ROLE = "DELETE FROM sys_authority WHERE user_id = ?";
	
	public final static String SQL_INSERT_AUTHORIZE_ROLE = "INSERT INTO sys_authority ( user_id, role_id ) VALUES ( ?, ? )";
		
	public final static String SQL_UPDATE_COIN_QUANTITY_BY_USER = "UPDATE sys_user SET coin_quantity = ? where id = ?";
	
	/**表名*/
	public final static String TABLE_NAME = "sys_user";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "用户";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
