package com.deertt.module.sys.notification.vo;

import java.sql.Timestamp;

import com.deertt.frame.base.vo.DvBaseVo;
import com.deertt.utils.helper.date.DvDateHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class NotificationVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String NOTIFY_STATUS_NO = "00";
	public static final String NOTIFY_STATUS_WN = "wn";
	public static final String NOTIFY_STATUS_WY = "wy";
	public static final String NOTIFY_STATUS_MN = "mn";
	public static final String NOTIFY_STATUS_MY = "my";
	public static final String NOTIFY_STATUS_NN = "nn";
	
	public static final String NOTIFY_WAY_W = "w";
	public static final String NOTIFY_WAY_M = "m";
	public static final String NOTIFY_WAY_WM = "wm";
	
	//----------开始vo的属性 ----------
	
	public NotificationVo() {
		super();
	}
	
	public NotificationVo(Integer user_id, String user_name,
			String wechat_id, String mobile, String notify_type,
			String notify_way, String message, Timestamp expect_notify_time,
			Integer notify_limit_times) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.wechat_id = wechat_id;
		this.mobile = mobile;
		this.notify_type = notify_type;
		this.notify_way = notify_way;
		this.message = message;
		this.expect_notify_time = expect_notify_time;
		this.notify_time = null;
		this.notify_limit_times = notify_limit_times;
		this.notify_times = 0;
		this.notify_status = NOTIFY_STATUS_NO;
		this.setStatus("1");
		this.setCreate_at(DvDateHelper.getSysTimestamp());
	}
	
	//id
	private Integer id;

	//用户id
	private Integer user_id;
	
	//用户名称
	private String user_name;
	
	//微信id
	private String wechat_id;
	
	//手机
	private String mobile;
	
	//消息类型
	private String notify_type;
	
	//通知渠道：w：微信，m：手机
	private String notify_way;
	
	//通知消息
	private String message;
	
	//期望通知时间
	private Timestamp expect_notify_time;
	
	//通知时间
	private Timestamp notify_time;
	
	//限定通知次数
	private Integer notify_limit_times;
	
	//通知次数
	private Integer notify_times;
	
	//通知结果状态
	private String notify_status;
	
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
	
	/** 获取用户id */
	public Integer getUser_id() {
		return user_id;
	}
	
	/** 设置用户id */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	/** 获取用户名称 */
	public String getUser_name() {
		return user_name;
	}
	
	/** 设置用户名称 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	/** 获取微信id */
	public String getWechat_id() {
		return wechat_id;
	}
	
	/** 设置微信id */
	public void setWechat_id(String wechat_id) {
		this.wechat_id = wechat_id;
	}
	
	/** 获取手机 */
	public String getMobile() {
		return mobile;
	}
	
	/** 设置手机 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/** 获取消息类型 */
	public String getNotify_type() {
		return notify_type;
	}
	
	/** 设置消息类型 */
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	
	/** 获取通知渠道：w：微信，m：手机 */
	public String getNotify_way() {
		return notify_way;
	}
	
	/** 设置通知渠道：w：微信，m：手机 */
	public void setNotify_way(String notify_way) {
		this.notify_way = notify_way;
	}
	
	/** 获取通知消息 */
	public String getMessage() {
		return message;
	}
	
	/** 设置通知消息 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/** 获取期望通知时间 */
	public Timestamp getExpect_notify_time() {
		return expect_notify_time;
	}
	
	/** 设置期望通知时间 */
	public void setExpect_notify_time(Timestamp expect_notify_time) {
		this.expect_notify_time = expect_notify_time;
	}
	
	/** 获取通知时间 */
	public Timestamp getNotify_time() {
		return notify_time;
	}
	
	/** 设置通知时间 */
	public void setNotify_time(Timestamp notify_time) {
		this.notify_time = notify_time;
	}
	
	/** 获取限定通知次数 */
	public Integer getNotify_limit_times() {
		return notify_limit_times;
	}
	
	/** 设置限定通知次数 */
	public void setNotify_limit_times(Integer notify_limit_times) {
		this.notify_limit_times = notify_limit_times;
	}
	
	/** 获取通知次数 */
	public Integer getNotify_times() {
		return notify_times;
	}
	
	/** 设置通知次数 */
	public void setNotify_times(Integer notify_times) {
		this.notify_times = notify_times;
	}
	
	/** 获取通知结果状态 */
	public String getNotify_status() {
		return notify_status;
	}
	
	/** 设置通知结果状态 */
	public void setNotify_status(String notify_status) {
		this.notify_status = notify_status;
	}
	
	public String getNotify_type_no_key() {
		return notify_type == null ? null : notify_type.replaceAll("-\\d+-", "-");
	}
	
	//----------结束vo的setter和getter方法----------
}