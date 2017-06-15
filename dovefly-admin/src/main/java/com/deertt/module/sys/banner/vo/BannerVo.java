package com.deertt.module.sys.banner.vo;

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
public class BannerVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//标题
	private String title;
	
	//类型
	private String device;
	
	//图片地址
	private String image;
	
	//跳转地址
	private String url;
	
	//有效起始时间
	private Timestamp start_time;
	
	//有效终止时间
	private Timestamp end_time;
	
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
	
	/** 获取标题 */
	public String getTitle() {
		return title;
	}
	
	/** 设置标题 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 获取设备类型 */
	public String getDevice() {
		return device;
	}
	
	/** 设置设备类型 */
	public void setDevice(String device) {
		this.device = device;
	}
	
	/** 获取图片地址 */
	public String getImage() {
		return image;
	}
	
	/** 设置图片地址 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/** 获取跳转地址 */
	public String getUrl() {
		return url;
	}
	
	/** 设置跳转地址 */
	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	//----------结束vo的setter和getter方法----------
}