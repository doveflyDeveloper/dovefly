package com.deertt.common.pay.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RefundResult {
	private boolean success;
	private String out_trade_no;
	private String pay_code;
	private BigDecimal pay_amount;
	private Timestamp pay_time;
	private String pay_msg;
	private String body;

	public RefundResult() {
		super();
	}

	public RefundResult(boolean success, String out_trade_no,
			String pay_code, BigDecimal pay_amount, Timestamp pay_time,
			String pay_msg, String body) {
		super();
		this.success = success;
		this.out_trade_no = out_trade_no;
		this.pay_code = pay_code;
		this.pay_amount = pay_amount;
		this.pay_time = pay_time;
		this.pay_msg = pay_msg;
		this.body = body;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
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

	public String getPay_msg() {
		return pay_msg;
	}

	public void setPay_msg(String pay_msg) {
		this.pay_msg = pay_msg;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
