package com.deertt.module.fund.apply.vo;

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
public class ApplyVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String BILL_CODE_PREFIX = "A";//单号前缀
	
	public static final String STORE_TYPE_W = "w";//货仓
	public static final String STORE_TYPE_S = "s";//店铺
	public static final String STORE_TYPE_M = "m";//超市
	
	public static final String APPLY_TO_WXPAY = "wxpay";//付款到微信
	public static final String APPLY_TO_ALIPAY = "alipay";//付款到支付宝
	
	public static final String PAY_STATUS_UNPAY = "0";//未付款
	public static final String PAY_STATUS_DEALING = "1";//付款中
	public static final String PAY_STATUS_SUCCESS = "2";//付款成功
	public static final String PAY_STATUS_FAIL = "3";//支付付款
	
	public static final String STATUS_UNDEAL = "0";//未处理
	public static final String STATUS_DEALING = "1";//处理中
	public static final String STATUS_SUCCESS= "2";//处理成功
	public static final String STATUS_FAIL = "3";//处理失败
	public static final String STATUS_DENYED = "4";//被拒绝
	
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
	
	//申请时间
	private Timestamp apply_time;
	
	private BigDecimal apply_amount;
	
	private String apply_to;
	
	private String receive_account;
	
	private String receive_real_name;
	
	//交易概述
	private String brief;
	
	private String pay_type;
	
	private String pay_code;
	
	private BigDecimal pay_amount;
	
	private Timestamp pay_time;
	
	private String pay_status;
	
	private String pay_msg;
	
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
	
	public String getBill_code() {
		return bill_code;
	}

	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}
	
	/** 获取用户id */
	public Integer getUser_id() {
		return user_id;
	}
	
	/** 设置用户id */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	/** 获取用户账号 */
	public String getUser_name() {
		return user_name;
	}
	
	/** 设置用户账号 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	/** 获取申请时间 */
	public Timestamp getApply_time() {
		return apply_time;
	}
	
	/** 设置申请时间 */
	public void setApply_time(Timestamp apply_time) {
		this.apply_time = apply_time;
	}
	
	public BigDecimal getApply_amount() {
		return apply_amount;
	}

	public void setApply_amount(BigDecimal apply_amount) {
		this.apply_amount = apply_amount;
	}
	
	public String getApply_to() {
		return apply_to;
	}
	
	public void setApply_to(String apply_to) {
		this.apply_to = apply_to;
	}
	
	public String getReceive_account() {
		return receive_account;
	}
	
	public void setReceive_account(String receive_account) {
		this.receive_account = receive_account;
	}
	
	public String getReceive_real_name() {
		return receive_real_name;
	}
	
	public void setReceive_real_name(String receive_real_name) {
		this.receive_real_name = receive_real_name;
	}
	
	/** 获取交易概述 */
	public String getBrief() {
		return brief;
	}
	
	/** 设置交易概述 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public BigDecimal getPay_amount() {
		return pay_amount;
	}

	public void setPay_amount(BigDecimal pay_amount) {
		this.pay_amount = pay_amount;
	}

	public Timestamp getPay_time() {
		return pay_time;
	}

	public void setPay_time(Timestamp pay_time) {
		this.pay_time = pay_time;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	
	public String getPay_msg() {
		return pay_msg;
	}

	public void setPay_msg(String pay_msg) {
		this.pay_msg = pay_msg;
	}
	
	//----------结束vo的setter和getter方法----------
}