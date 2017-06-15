package com.deertt.common.pay.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AlipayResult {

	// code String 是 - 网关返回码,详见文档 成功：10000
	private String code;

	// msg String 是 - 网关返回码描述,详见文档 Business Failed
	private String msg;

	// sub_code String 否 - 业务返回码,详见文档 ACQ.TRADE_HAS_SUCCESS
	private String sub_code;

	// sub_msg String 否 - 业务返回码描述,详见文档 交易已被支付
	private String sub_msg;

	// trade_no String 必填 64 支付宝交易号 2013112011001004330000121536
	private String trade_no;

	// out_trade_no String 必填 64 商家订单号 6823789339978248
	private String out_trade_no;

	/*
	 * trade_status String 必填 32 交易状态： WAIT_BUYER_PAY（交易创建，等待买家付款）、
	 * TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、 TRADE_SUCCESS（交易支付成功）、
	 * TRADE_FINISHED（交易结束，不可退款） TRADE_CLOSED
	 */
	private String trade_status;

	// total_amount Price 必填 11 交易的订单金额，单位为元，两位小数。 88.88
	private BigDecimal total_amount;

	// Date 必填 32 本次交易打款给卖家的时间 2014-11-27 15:45:57
	private Timestamp send_pay_date;

	// buyer_logon_id String 必填 100 买家支付宝账号 159****5620
	private String buyer_logon_id;

	public AlipayResult() {
		super();
	}
	
	public AlipayResult(String code, String msg, String sub_code, String sub_msg) {
		super();
		this.code = code;
		this.msg = msg;
		this.sub_code = sub_code;
		this.sub_msg = sub_msg;
	}

	public AlipayResult(String code, String msg, String sub_code,
			String sub_msg, String trade_no, String out_trade_no,
			String trade_status, BigDecimal total_amount,
			Timestamp send_pay_date, String buyer_logon_id) {
		super();
		this.code = code;
		this.msg = msg;
		this.sub_code = sub_code;
		this.sub_msg = sub_msg;
		this.trade_no = trade_no;
		this.out_trade_no = out_trade_no;
		this.trade_status = trade_status;
		this.total_amount = total_amount;
		this.send_pay_date = send_pay_date;
		this.buyer_logon_id = buyer_logon_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSub_code() {
		return sub_code;
	}

	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}

	public String getSub_msg() {
		return sub_msg;
	}

	public void setSub_msg(String sub_msg) {
		this.sub_msg = sub_msg;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public Timestamp getSend_pay_date() {
		return send_pay_date;
	}

	public void setSend_pay_date(Timestamp send_pay_date) {
		this.send_pay_date = send_pay_date;
	}

	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}

	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}

}
