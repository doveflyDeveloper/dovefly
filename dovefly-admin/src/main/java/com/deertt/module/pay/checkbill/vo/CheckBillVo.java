package com.deertt.module.pay.checkbill.vo;

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
public class CheckBillVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//支付账单表
	private String pay_table_name;
	
	//支付账单id
	private Integer pay_bill_id;
	
	//单据类型
	private String bill_type;
	
	//商户订单表
	private String bill_table_name;
	
	//商户订单id
	private Integer bill_id;
	
	//商户订单号
	private String bill_code;
	
	//对账时间
	private Timestamp check_time;
	
	//对账信息
	private String check_msg;
	
	//对账结果
	private String check_status;
	
	//处理结果
	private String deal_status;
	
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
	
	/** 获取支付账单表 */
	public String getPay_table_name() {
		return pay_table_name;
	}
	
	/** 设置支付账单表 */
	public void setPay_table_name(String pay_table_name) {
		this.pay_table_name = pay_table_name;
	}
	
	/** 获取支付账单id */
	public Integer getPay_bill_id() {
		return pay_bill_id;
	}
	
	/** 设置支付账单id */
	public void setPay_bill_id(Integer pay_bill_id) {
		this.pay_bill_id = pay_bill_id;
	}
	
	/** 获取单据类型 */
	public String getBill_type() {
		return bill_type;
	}
	
	/** 设置单据类型 */
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	
	/** 获取商户订单表 */
	public String getBill_table_name() {
		return bill_table_name;
	}
	
	/** 设置商户订单表 */
	public void setBill_table_name(String bill_table_name) {
		this.bill_table_name = bill_table_name;
	}
	
	/** 获取商户订单id */
	public Integer getBill_id() {
		return bill_id;
	}
	
	/** 设置商户订单id */
	public void setBill_id(Integer bill_id) {
		this.bill_id = bill_id;
	}
	
	/** 获取商户订单号 */
	public String getBill_code() {
		return bill_code;
	}
	
	/** 设置商户订单号 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}
	
	/** 获取对账时间 */
	public Timestamp getCheck_time() {
		return check_time;
	}
	
	/** 设置对账时间 */
	public void setCheck_time(Timestamp check_time) {
		this.check_time = check_time;
	}
	
	/** 获取对账信息 */
	public String getCheck_msg() {
		return check_msg;
	}
	
	/** 设置对账信息 */
	public void setCheck_msg(String check_msg) {
		this.check_msg = check_msg;
	}
	
	/** 获取对账结果 */
	public String getCheck_status() {
		return check_status;
	}
	
	/** 设置对账结果 */
	public void setCheck_status(String check_status) {
		this.check_status = check_status;
	}
	
	/** 获取处理结果 */
	public String getDeal_status() {
		return deal_status;
	}
	
	/** 设置处理结果 */
	public void setDeal_status(String deal_status) {
		this.deal_status = deal_status;
	}
	
	//----------结束vo的setter和getter方法----------
}