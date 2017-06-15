package com.deertt.module.express.vo;

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
public class ExpressVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	// ----------开始vo的属性 ----------
	// id
	private Integer id;

	// 快递公司
	private String exp_company;

	// 快递单号
	private String exp_tracking_no;

	// 快递日期
	private Date exp_date;

	// 快递费用
	private BigDecimal exp_amount;

	// 寄件人姓名
	private String sender_name;

	// 寄件人电话
	private String sender_mobile;

	// 寄件人地址
	private String sender_address;

	// 收件人姓名
	private String receiver_name;

	// 收件人电话
	private String receiver_mobile;

	// 收件人地址
	private String receiver_address;
	
	// 快递进度
	private String deliver_progress;
	
	// 快递状态
	private String deliver_status;

	// ----------结束vo的属性----------
	@Override
	public boolean isNew() {
		return id == null || id == 0;
	}

	// ----------开始vo的setter和getter方法----------
	/** 获取id */
	public Integer getId() {
		return id;
	}

	/** 设置id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 获取快递公司 */
	public String getExp_company() {
		return exp_company;
	}

	/** 设置快递公司 */
	public void setExp_company(String exp_company) {
		this.exp_company = exp_company;
	}

	/** 获取快递单号 */
	public String getExp_tracking_no() {
		return exp_tracking_no;
	}

	/** 设置快递单号 */
	public void setExp_tracking_no(String exp_tracking_no) {
		this.exp_tracking_no = exp_tracking_no;
	}

	/** 获取快递日期 */
	public Date getExp_date() {
		return exp_date;
	}

	/** 设置快递日期 */
	public void setExp_date(Date exp_date) {
		this.exp_date = exp_date;
	}

	/** 获取快递费用 */
	public BigDecimal getExp_amount() {
		return exp_amount;
	}

	/** 设置快递费用 */
	public void setExp_amount(BigDecimal exp_amount) {
		this.exp_amount = exp_amount;
	}

	/** 获取寄件人姓名 */
	public String getSender_name() {
		return sender_name;
	}

	/** 设置寄件人姓名 */
	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}

	/** 获取寄件人电话 */
	public String getSender_mobile() {
		return sender_mobile;
	}

	/** 设置寄件人电话 */
	public void setSender_mobile(String sender_mobile) {
		this.sender_mobile = sender_mobile;
	}

	/** 获取寄件人地址 */
	public String getSender_address() {
		return sender_address;
	}

	/** 设置寄件人地址 */
	public void setSender_address(String sender_address) {
		this.sender_address = sender_address;
	}

	/** 获取收件人姓名 */
	public String getReceiver_name() {
		return receiver_name;
	}

	/** 设置收件人姓名 */
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	/** 获取收件人电话 */
	public String getReceiver_mobile() {
		return receiver_mobile;
	}

	/** 设置收件人电话 */
	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}

	/** 获取收件人地址 */
	public String getReceiver_address() {
		return receiver_address;
	}

	/** 设置收件人地址 */
	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}

	public String getDeliver_progress() {
		return deliver_progress;
	}

	public void setDeliver_progress(String deliver_progress) {
		this.deliver_progress = deliver_progress;
	}

	public String getDeliver_status() {
		return deliver_status;
	}

	public void setDeliver_status(String deliver_status) {
		this.deliver_status = deliver_status;
	}
	
	// ----------结束vo的setter和getter方法----------
}