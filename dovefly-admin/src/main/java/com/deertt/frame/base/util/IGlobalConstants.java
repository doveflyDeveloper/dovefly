package com.deertt.frame.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存全局的一些常量
 * 
 * @author
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IGlobalConstants {
	
	// 分页信息
	public final static String DV_PAGE_VO = "dv_page_vo";
	public final static String DV_PAGE_SIZE = "dv_page_size";
	public final static String DV_CURRENT_PAGE = "dv_current_page";

	// request处理中的key值
	public final static String REQUEST_ID = "id";
	public final static String REQUEST_IDS = "ids";
	public final static String REQUEST_BEAN = "bean";
	public final static String REQUEST_BEANS = "beans";
	public final static String REQUEST_QUERY_CONDITION = "queryCondition";
	public final static String REQUEST_IS_MODIFY = "isModify";
	public final static String REQUEST_IS_READONLY = "isReadonly";
	public final static String REQUEST_RADIO_BUTTON_DATA_MAP = "RADIO_BUTTON_DATA_MAP";
	
	// 默认属性名称定义
	public final static String[] DESC_CREATE_TIME = new String[] { "create_time", "create_date", "create_at" }; // 描述创建时间
	public final static String[] DESC_CREATE_IP = new String[] { "create_ip" }; // 描述创建IP
	public final static String[] DESC_CREATE_USER_ID = new String[] { "create_user_id", "insert_user_id", "create_by" }; // 描述创建用户ID
	public final static String[] DESC_MODIFY_TIME = new String[] { "modify_time", "modify_date", "update_time", "update_date", "modify_at" }; // 描述修改时间
	public final static String[] DESC_MODIFY_IP = new String[] { "modify_ip", "update_ip" }; // 描述修改IP
	public final static String[] DESC_MODIFY_USER_ID = new String[] { "modify_user_id", "update_user_id", "modify_by" }; // 描述修改用户ID
	public final static String DESC_USABLE_STATE = "status"; // 单据启用状态
	public final static String DESC_ORDER_CODE = "sort"; // 单据排序字段
	
	// 系统常用定值定义
	public final static String DV_YES = "1"; // 是的定义
	public final static String DV_NO = "0"; // 否的定义
	public final static boolean DV_TRUE = true; // 是的定义
	public final static boolean DV_FALSE = false; // 否的定义
	
	// 数据库定义
	public final static String DATABASE_PRODUCT_NAME_MYSQL = "MySQL";
	public final static String DATABASE_PRODUCT_NAME_ORACLE = "Oracle";
	public final static String DATABASE_PRODUCT_NAME_DB2 = "DB2";
	public final static String DATABASE_PRODUCT_NAME_SQLServer = "Microsoft SQL Server";
	public final static String DATABASE_PRODUCT_NAME_H2 = "H2";
	public final static String DATABASE_PRODUCT_NAME_HSQL = "HSQL Database Engine";
	public final static Map<String, String> DATABASE_PRODUCT_MAP = new HashMap<String, String>() {
		{
			this.put("com.mysql.jdbc.Driver", DATABASE_PRODUCT_NAME_MYSQL);
			this.put("org.gjt.mm.mysql.Driver", DATABASE_PRODUCT_NAME_MYSQL);
			this.put("oracle.jdbc.driver.OracleDriver", DATABASE_PRODUCT_NAME_ORACLE);
			this.put("net.sourceforge.jtds.jdbc.Driver", DATABASE_PRODUCT_NAME_SQLServer);
			this.put("org.h2.Driver", DATABASE_PRODUCT_NAME_MYSQL);
		}
	};

	public static final String SORT_SYMBOL_ASC = "ASC";
	public static final String SORT_SYMBOL_DESC = "DESC";
	public static final String ORDER_BY_SYMBOL = " ORDER BY ";

	// login session
	public final static String DV_USER_VO = "DV_USER_VO";

	// 工作流参数
	public final static String WF_FORM_ID = "wf_form_id";
	public final static String REQUEST_WF_PROCESSID = "REQUEST_WF_PROCESSID";// 工作流实例Id参数
	public final static String WF_LAST_PERSON = "WF_LAST_PERSON";
	public final static String WF_OWNER = "WF_OWNER";
	public final static String WF_LAST_DATE = "WF_LAST_DATE";
	public final static String WF_PROCESS_TYPE = "WF_PROCESS_TYPE";
	public final static String WF_DESC_ORDER_CODE = "WF_DESC_ORDER_CODE";
	public final static String WF_FORM_ID_P = "WF_FORM_ID_P";

}