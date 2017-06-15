package com.deertt.module.sc.job.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IJobConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "jobController:read";
	
	public final static String PERM_WRITE = "jobController:*";
	
	//URL
	public final static String CONTROLLER = "/jobController";
	
	public final static String DEFAULT_REDIRECT = "/jobController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/sc/job";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/sc/job/";

	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, school_id, school_name, user_id, user_name, title, job_type, company, contact_phone, salary, salary_unit, begin_date, end_date, work_place, need_num, description, issue_status, issue_time, sort, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = " id, city_id, city_name, school_id, school_name, user_id, user_name, title, job_type, company, contact_phone, salary, salary_unit, begin_date, end_date, work_place, need_num, description, issue_status, issue_time, sort, status";

	public final static String SQL_INSERT = "INSERT INTO sc_job ( id, city_id, city_name, school_id, school_name, user_id, user_name, title, job_type, company, contact_phone, salary, salary_unit, begin_date, end_date, work_place, need_num, description, issue_status, issue_time, sort, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM sc_job WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM sc_job";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM sc_job WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE sc_job SET city_id = ?, city_name = ?, school_id = ?, school_name  = ?, user_id = ?, user_name = ?, title = ?, job_type = ?, company = ?, contact_phone = ?, salary = ?, salary_unit = ?, begin_date = ?, end_date = ?, work_place = ?, need_num = ?, description = ?, issue_status = ?, issue_time = ?, sort = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM sc_job";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM sc_job";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM sc_job";
	
	/**表名*/
	public final static String TABLE_NAME = "sc_job";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "兼职招聘";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
