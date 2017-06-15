package com.deertt.module.sys.userapply.vo;

import com.deertt.frame.base.vo.DvBaseVo;
import com.deertt.module.sys.user.vo.UserVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class UserApplyVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String NOTIFY_STATUS_NO = "0";//未通知
	public static final String NOTIFY_STATUS_SUCCESS = "1";//通知成功
	public static final String NOTIFY_STATUS_FAIL = "2";//通知失败
	
	public static final String STATUS_NO = "0";//未处理
	public static final String STATUS_PASS = "1";//通过
	public static final String STATUS_DENY = "2";//拒绝
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市
	private String city_name;
	
	//学校ID
	private Integer school_id;
	
	//学校
	private String school_name;
	
	//用户id
	private Integer user_id;
	
	//用户
	private String user_name;
	
	//通知状态
	private String notify_status;
	
	//通知消息
	private String notify_msg;
	
	private UserVo user;
	
	//----------结束vo的属性----------
	@Override
	public boolean isNew() {
		return id == null || id == 0;
	}
	//----------开始vo的setter和getter方法----------
	/** 获取id */
	public Integer getId() {
		return id;
	}
	
	/** 设置id */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/** 获取城市id */
	public Integer getCity_id() {
		return city_id;
	}
	
	/** 设置城市id */
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	/** 获取城市 */
	public String getCity_name() {
		return city_name;
	}
	
	/** 设置城市 */
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	public Integer getSchool_id() {
		return school_id;
	}

	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	
	/** 获取用户id */
	public Integer getUser_id() {
		return user_id;
	}
	
	/** 设置用户id */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	/** 获取用户 */
	public String getUser_name() {
		return user_name;
	}
	
	/** 设置用户 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	/** 获取通知状态 */
	public String getNotify_status() {
		return notify_status;
	}
	
	/** 设置通知状态 */
	public void setNotify_status(String notify_status) {
		this.notify_status = notify_status;
	}
	
	/** 获取通知消息 */
	public String getNotify_msg() {
		return notify_msg;
	}
	
	/** 设置通知消息 */
	public void setNotify_msg(String notify_msg) {
		this.notify_msg = notify_msg;
	}

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}
	
	//----------结束vo的setter和getter方法----------
}