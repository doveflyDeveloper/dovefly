package com.deertt.module.sys.message.vo;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class MessageVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String STATUS_UNREAD = "0";
	public static final String STATUS_READ = "1";
	
	public static final String TYPE_NOTIFY = "1";//通知消息
	public static final String TYPE_REMIND = "2";//提醒消息
	public static final String TYPE_OPERATE = "3";//操作消息
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//用户
	private Integer user_id;
	
	//用户
	private String user_name;
	
	//消息主题
	private String title;
	
	//类型
	private String type;
	
	//内容
	private String content;

	//关联链接
	private String link_url;
	
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
	
	/** 获取用户 */
	public Integer getUser_id() {
		return user_id;
	}
	
	/** 设置用户 */
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
	
	/** 获取消息主题 */
	public String getTitle() {
		return title;
	}
	
	/** 设置消息主题 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 获取类型 */
	public String getType() {
		return type;
	}
	
	/** 设置类型 */
	public void setType(String type) {
		this.type = type;
	}
	
	/** 获取内容 */
	public String getContent() {
		return content;
	}
	
	/** 设置内容 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/** 获取关联链接 */
	public String getLink_url() {
		return link_url;
	}
	
	/** 设置关联链接 */
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	
	//----------结束vo的setter和getter方法----------
}