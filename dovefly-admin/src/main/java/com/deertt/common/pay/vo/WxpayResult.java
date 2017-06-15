package com.deertt.common.pay.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class WxpayResult {
	
	//返回状态码 SUCCESSSUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看trade_state来判断
	private String return_code;
	
	//返回信息，如非空，为错误原因
	private String return_msg;
	
	//以下字段在return_code为SUCCESS的时候有返回

	//业务结果 SUCCESS/FAIL
	private String result_code;
	
	//错误代码	err_code	否	String(32)	SYSTEMERROR	错误码
	private String err_code;
	
	//错误代码描述	err_code_des	否	String(128)	系统错误	结果信息描述
	private String err_code_des;

	//以下字段在return_code 和result_code都为SUCCESS的时候有返回
	
	/*交易状态	trade_state	是	String(32)	SUCCESS	
	SUCCESS—支付成功
	REFUND—转入退款
	NOTPAY—未支付
	CLOSED—已关闭
	REVOKED—已撤销（刷卡支付）
	USERPAYING--用户支付中
	PAYERROR--支付失败(其他原因，如银行返回失败)*/
	private String trade_state;
	
	//订单金额	total_fee	是	Int	100	订单总金额，单位为分
	private BigDecimal total_fee;
	
	//微信支付订单号	transaction_id	是	String(32)	1009660380201506130728806387	微信支付订单号
	private String transaction_id;
	
	//商户订单号	out_trade_no	是	String(32)	20150806125346	商户系统的订单号，与请求一致。
	private String out_trade_no;
	
	//支付完成时间	time_end	是	String(14)	20141030133525	订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	private Timestamp time_end;

	public WxpayResult() {
		super();
	}
	
	public WxpayResult(String return_code, String return_msg) {
		super();
		this.return_code = return_code;
		this.return_msg = return_msg;
	}
	
	public WxpayResult(String return_code, String return_msg, String result_code, String err_code, String err_code_des) {
		super();
		this.return_code = return_code;
		this.return_msg = return_msg;
		this.result_code = result_code;
		this.err_code = err_code;
		this.err_code_des = err_code_des;
	}
	
	public WxpayResult(String return_code, String return_msg,
			String result_code, String err_code, String err_code_des,
			String trade_state, BigDecimal total_fee, String transaction_id,
			String out_trade_no, Timestamp time_end) {
		super();
		this.return_code = return_code;
		this.return_msg = return_msg;
		this.result_code = result_code;
		this.err_code = err_code;
		this.err_code_des = err_code_des;
		this.trade_state = trade_state;
		this.total_fee = total_fee;
		this.transaction_id = transaction_id;
		this.out_trade_no = out_trade_no;
		this.time_end = time_end;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getTrade_state() {
		return trade_state;
	}

	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}

	public BigDecimal getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public Timestamp getTime_end() {
		return time_end;
	}

	public void setTime_end(Timestamp time_end) {
		this.time_end = time_end;
	}
	
}
