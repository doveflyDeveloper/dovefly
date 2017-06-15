package com.deertt.module.mv.show.vo;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class MvShowSupportVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//剧本id
	private Integer show_id;
	
	//剧本名称
	private String show_name;
	
	//参赛人id
	private Integer user_id;
	
	//参赛人
	private String user_name;
	
	//汀豆
	private Integer coin_quantity;
	
    //投票时间
	
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
	
	/** 获取剧本id */
	public Integer getShow_id() {
		return show_id;
	}
	
	/** 设置剧本id */
	public void setShow_id(Integer show_id) {
		this.show_id = show_id;
	}
	
	/** 获取剧本名称 */
	public String getShow_name() {
		return show_name;
	}
	
	/** 设置剧本名称 */
	public void setShow_name(String show_name) {
		this.show_name = show_name;
	}
	
	/** 获取参赛人id */
	public Integer getUser_id() {
		return user_id;
	}
	
	/** 设置参赛人id */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	/** 获取参赛人 */
	public String getUser_name() {
		return user_name;
	}
	
	/** 设置参赛人 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	/** 获取汀豆 */
	public Integer getCoin_quantity() {
		return coin_quantity;
	}
	
	/** 设置汀豆 */
	public void setCoin_quantity(Integer coin_quantity) {
		this.coin_quantity = coin_quantity;
	}
	
	//----------结束vo的setter和getter方法----------
}