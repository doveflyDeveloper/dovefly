package com.deertt.module.sys.school.vo;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class SchoolVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	private Integer city_id;
	
	private String city_name;
	
	private Integer warehouse_id;
	
	private String warehouse_name;
	
	private Integer manager_id;
	
	private String manager_name;
	
	//学校名称
	private String school_name;
	
	private String baidu_uid;
	
	private String baidu_title;
	
	private String baidu_address;
	
	private Double baidu_longtitude;
	
	private Double baidu_latitude;
	
	private Integer search_times;
	
	private Integer shop_count;
	
	private Integer user_count;
	
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
	
	public Integer getCity_id() {
		return city_id;
	}
	
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	public String getCity_name() {
		return city_name;
	}
	
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	public Integer getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(Integer warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	public String getWarehouse_name() {
		return warehouse_name;
	}

	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}

	public Integer getManager_id() {
		return manager_id;
	}

	public void setManager_id(Integer manager_id) {
		this.manager_id = manager_id;
	}

	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	
	/** 获取学校名称 */
	public String getSchool_name() {
		return school_name;
	}
	
	/** 设置学校名称 */
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	
	public String getBaidu_uid() {
		return baidu_uid;
	}
	
	public void setBaidu_uid(String baidu_uid) {
		this.baidu_uid = baidu_uid;
	}

	public String getBaidu_title() {
		return baidu_title;
	}

	public void setBaidu_title(String baidu_title) {
		this.baidu_title = baidu_title;
	}

	public String getBaidu_address() {
		return baidu_address;
	}
	
	public void setBaidu_address(String baidu_address) {
		this.baidu_address = baidu_address;
	}
	
	public Double getBaidu_longtitude() {
		return baidu_longtitude;
	}

	public void setBaidu_longtitude(Double baidu_longtitude) {
		this.baidu_longtitude = baidu_longtitude;
	}

	public Double getBaidu_latitude() {
		return baidu_latitude;
	}

	public void setBaidu_latitude(Double baidu_latitude) {
		this.baidu_latitude = baidu_latitude;
	}
	
	public Integer getSearch_times() {
		return search_times;
	}
	
	public void setSearch_times(Integer search_times) {
		this.search_times = search_times;
	}

	public Integer getShop_count() {
		return shop_count;
	}

	public void setShop_count(Integer shop_count) {
		this.shop_count = shop_count;
	}

	public Integer getUser_count() {
		return user_count;
	}

	public void setUser_count(Integer user_count) {
		this.user_count = user_count;
	}

	//----------结束vo的setter和getter方法----------
}