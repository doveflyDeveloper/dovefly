package com.deertt.module.sys.city.vo;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class CityVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市
	private String name;
	
	private Integer df_supplier_id;
	
	private String df_supplier_name;
	
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
	public String getName() {
		return name;
	}
	
	/** 设置城市 */
	public void setName(String name) {
		this.name = name;
	}

	public Integer getDf_supplier_id() {
		return df_supplier_id;
	}

	public void setDf_supplier_id(Integer df_supplier_id) {
		this.df_supplier_id = df_supplier_id;
	}

	public String getDf_supplier_name() {
		return df_supplier_name;
	}

	public void setDf_supplier_name(String df_supplier_name) {
		this.df_supplier_name = df_supplier_name;
	}
	
	//----------结束vo的setter和getter方法----------
}