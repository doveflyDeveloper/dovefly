package com.deertt.module.sc.blog.vo;

import java.sql.Timestamp;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class BlogVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String ISSUE_STATUS_DRAFT = "0";//草稿
	public static final String ISSUE_STATUS_ISSUE = "1";//发布
	public static final String ISSUE_STATUS_CANCEL = "2";//撤销
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市
	private Integer city_id;
	
	private String city_name;
	
	//学校
	private Integer school_id;
	
	private String school_name;
	
	//标题
	private String title;
	
	//类别
	private String type;
	
	private String image;
	
	private String brief;
	
	//内容
	private String description;
	
	//推荐商品
	private String link_goods;
	
	//发表状态
	private String issue_status;
	
	//发表时间
	private Timestamp issue_time;
	
	//发布范围
	private String issue_to;
	
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
	
	/** 获取城市 */
	public Integer getCity_id() {
		return city_id;
	}
	
	/** 设置城市 */
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	public String getCity_name() {
		return city_name;
	}
	
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	/** 获取学校 */
	public Integer getSchool_id() {
		return school_id;
	}
	
	/** 设置学校 */
	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}
	
	public String getSchool_name() {
		return school_name;
	}
	
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	
	/** 获取标题 */
	public String getTitle() {
		return title;
	}
	
	/** 设置标题 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 获取类别 */
	public String getType() {
		return type;
	}
	
	/** 设置类别 */
	public void setType(String type) {
		this.type = type;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	/** 获取内容 */
	public String getDescription() {
		return description;
	}
	
	/** 设置内容 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLink_goods() {
		return link_goods;
	}

	public void setLink_goods(String link_goods) {
		this.link_goods = link_goods;
	}
	
	/** 获取发表状态 */
	public String getIssue_status() {
		return issue_status;
	}
	
	/** 设置发表状态 */
	public void setIssue_status(String issue_status) {
		this.issue_status = issue_status;
	}
	
	/** 获取发表时间 */
	public Timestamp getIssue_time() {
		return issue_time;
	}
	
	/** 设置发表时间 */
	public void setIssue_time(Timestamp issue_time) {
		this.issue_time = issue_time;
	}
	
	/** 获取发布范围 */
	public String getIssue_to() {
		return issue_to;
	}
	
	/** 设置发布范围 */
	public void setIssue_to(String issue_to) {
		this.issue_to = issue_to;
	}
	
	//----------结束vo的setter和getter方法----------
}