package com.deertt.module.fund.recharge.vo;

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
public class RechargeVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String BILL_CODE_PREFIX = "R";//单号前缀
	
	public static final String STORE_TYPE_W = "w";//货仓
	public static final String STORE_TYPE_S = "s";//店铺
	public static final String STORE_TYPE_M = "m";//超市
	
	public static final String PAY_TYPE_COD = "cod";
	public static final String PAY_TYPE_TTPAY = "ttpay";
	public static final String PAY_TYPE_ALIPAY = "alipay";
	public static final String PAY_TYPE_WXPAY = "wxpay";
	
	public static final String STATUS_UNPAY = "0";//未支付
	public static final String STATUS_SUCCESS = "1";//充值成功
	public static final String STATUS_FAIL = "2";//充值失败
	
	public static final String PAY_STATUS_NO = "0";//未支付
	public static final String PAY_STATUS_SUCCESS = "1";//支付成功
	public static final String PAY_STATUS_FAIL = "2";//支付失败
	public static final String PAY_STATUS_REFUNDING = "3";//退款中
	public static final String PAY_STATUS_REFUNDED = "4";//已退款
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	private String store_type;
	
	private Integer store_id;
	
	private String store_name;
	
	//用户id
	private Integer user_id;
	
	//用户账号
	private String user_name;
	
	private String bill_code; 
	
	//充值时间
	private Timestamp recharge_time;
	
	//充值金额
	private BigDecimal recharge_amount;
	
	//交易概述
	private String brief;
	
	//支付方式
	private String pay_type;
	
	//支付回执编号
	private String pay_code;
	
	//支付金额
	private BigDecimal pay_amount;
	
	//支付时间
	private Timestamp pay_time;
	
	//支付状态
	private String pay_status;
	
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
	
	public String getStore_type() {
		return store_type;
	}
	
	public void setStore_type(String store_type) {
		this.store_type = store_type;
	}
	
	public Integer getStore_id() {
		return store_id;
	}
	
	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}
	
	public String getStore_name() {
		return store_name;
	}
	
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	/** 获取交易单号 */
	public String getBill_code() {
		return bill_code;
	}
	
	/** 设置交易单号 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}
	
	/** 获取充值时间 */
	public Timestamp getRecharge_time() {
		return recharge_time;
	}
	
	/** 设置充值时间 */
	public void setRecharge_time(Timestamp recharge_time) {
		this.recharge_time = recharge_time;
	}
	
	/** 获取充值金额 */
	public BigDecimal getRecharge_amount() {
		return recharge_amount;
	}
	
	/** 设置充值金额 */
	public void setRecharge_amount(BigDecimal recharge_amount) {
		this.recharge_amount = recharge_amount;
	}
	
	/** 获取交易概述 */
	public String getBrief() {
		return brief;
	}
	
	/** 设置交易概述 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	/** 获取支付方式 */
	public String getPay_type() {
		return pay_type;
	}
	
	/** 设置支付方式 */
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	
	/** 获取支付回执编号 */
	public String getPay_code() {
		return pay_code;
	}
	
	/** 设置支付回执编号 */
	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}
	
	/** 获取支付金额 */
	public BigDecimal getPay_amount() {
		return pay_amount;
	}
	
	/** 设置支付金额 */
	public void setPay_amount(BigDecimal pay_amount) {
		this.pay_amount = pay_amount;
	}
	
	/** 获取支付时间 */
	public Timestamp getPay_time() {
		return pay_time;
	}
	
	/** 设置支付时间 */
	public void setPay_time(Timestamp pay_time) {
		this.pay_time = pay_time;
	}
	
	/** 获取支付状态 */
	public String getPay_status() {
		return pay_status;
	}
	
	/** 设置支付状态 */
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	
	//----------结束vo的setter和getter方法----------
}