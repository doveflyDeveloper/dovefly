package com.deertt.module.fund.interest.vo;

import java.math.BigDecimal;
import java.sql.Date;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class InterestVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//店铺id
	private Integer shop_id;
	
	//店铺姓名
	private String shop_name;
	
	//计息日期
	private Date interest_date;
	
	//当日本金
	private BigDecimal capital_amount;
	
	//当日利息
	private BigDecimal interest_amount;
	
	//当日总利息
	private BigDecimal all_interest_amount;
	
	//当日利率
	private BigDecimal interest_rate;
	
	//交易概述
	private String brief;
	
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
	
	/** 获取店铺id */
	public Integer getShop_id() {
		return shop_id;
	}
	
	/** 设置店铺id */
	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}
	
	/** 获取店铺姓名 */
	public String getShop_name() {
		return shop_name;
	}
	
	/** 设置店铺姓名 */
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	
	public Date getInterest_date() {
		return interest_date;
	}

	public void setInterest_date(Date interest_date) {
		this.interest_date = interest_date;
	}
	
	/** 获取当日本金 */
	public BigDecimal getCapital_amount() {
		return capital_amount;
	}
	
	/** 设置当日本金 */
	public void setCapital_amount(BigDecimal capital_amount) {
		this.capital_amount = capital_amount;
	}
	
	/** 获取当日利息 */
	public BigDecimal getInterest_amount() {
		return interest_amount;
	}
	
	/** 设置当日利息 */
	public void setInterest_amount(BigDecimal interest_amount) {
		this.interest_amount = interest_amount;
	}
	
	/** 获取当日总利息 */
	public BigDecimal getAll_interest_amount() {
		return all_interest_amount;
	}
	
	/** 设置当日总利息 */
	public void setAll_interest_amount(BigDecimal all_interest_amount) {
		this.all_interest_amount = all_interest_amount;
	}
	
	/** 获取当日利率 */
	public BigDecimal getInterest_rate() {
		return interest_rate;
	}
	
	/** 设置当日利率 */
	public void setInterest_rate(BigDecimal interest_rate) {
		this.interest_rate = interest_rate;
	}
	
	/** 获取交易概述 */
	public String getBrief() {
		return brief;
	}
	
	/** 设置交易概述 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	//----------结束vo的setter和getter方法----------
}