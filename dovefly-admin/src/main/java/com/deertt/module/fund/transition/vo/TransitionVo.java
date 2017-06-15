package com.deertt.module.fund.transition.vo;

import java.math.BigDecimal;
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
public class TransitionVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String STORE_TYPE_W = "w";//货仓
	public static final String STORE_TYPE_S = "s";//店铺
	public static final String STORE_TYPE_M = "m";//超市
	
	public static final String TRANSITION_TYPE_IN = "IN";//收入
	public static final String TRANSITION_TYPE_OUT = "OUT";//支出
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//交易单位类型：w-货仓，s-店铺，m-超市
	private String store_type;
	
	//交易单位ID
	private Integer store_id;
	
	//交易单位
	private String store_name;
	
	private String bill_code;
	
	//交易编码
	private String transition_code;
	
	//交易类型
	private String transition_type;
	
	//交易时间
	private Timestamp transition_time;
	
	//交易金额
	private BigDecimal transition_amount;
	
	//当前余额
	private BigDecimal balance_amount;
	
	//交易概述
	private String brief;
	
	//----------结束vo的属性----------
	
	public TransitionVo() {
		super();
	}
	
	public TransitionVo(String store_type, Integer store_id, String store_name, String bill_code,
			String transition_code, String transition_type,
			Timestamp transition_time, BigDecimal transition_amount,
			BigDecimal balance_amount, String brief, String remark) {
		super();
		this.store_type = store_type;
		this.store_id = store_id;
		this.store_name = store_name;
		this.bill_code = bill_code;
		this.transition_code = transition_code;
		this.transition_type = transition_type;
		this.transition_time = transition_time;
		this.transition_amount = transition_amount;
		this.balance_amount = balance_amount;
		this.brief = brief;
		this.setRemark(remark);
		this.setStatus("1");
	}

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
	
	/** 获取交易单位类型 */
	public String getStore_type() {
		return store_type;
	}

	/** 设置交易单位类型 */
	public void setStore_type(String store_type) {
		this.store_type = store_type;
	}

	/** 获取交易单位ID */
	public Integer getStore_id() {
		return store_id;
	}
	
	/** 设置交易单位ID */
	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}
	
	/** 获取交易单位 */
	public String getStore_name() {
		return store_name;
	}
	
	/** 设置交易单位 */
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	
	public String getBill_code() {
		return bill_code;
	}

	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}

	/** 获取交易编码 */
	public String getTransition_code() {
		return transition_code;
	}
	
	/** 设置交易编码 */
	public void setTransition_code(String transition_code) {
		this.transition_code = transition_code;
	}
	
	/** 获取交易类型 */
	public String getTransition_type() {
		return transition_type;
	}
	
	/** 设置交易类型 */
	public void setTransition_type(String transition_type) {
		this.transition_type = transition_type;
	}
	
	/** 获取交易时间 */
	public Timestamp getTransition_time() {
		return transition_time;
	}
	
	/** 设置交易时间 */
	public void setTransition_time(Timestamp transition_time) {
		this.transition_time = transition_time;
	}
	
	/** 获取交易金额 */
	public BigDecimal getTransition_amount() {
		return transition_amount;
	}
	
	/** 设置交易金额 */
	public void setTransition_amount(BigDecimal transition_amount) {
		this.transition_amount = transition_amount;
	}
	
	/** 获取当前余额 */
	public BigDecimal getBalance_amount() {
		return balance_amount;
	}
	
	/** 设置当前余额 */
	public void setBalance_amount(BigDecimal balance_amount) {
		this.balance_amount = balance_amount;
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