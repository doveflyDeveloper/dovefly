package com.deertt.module.pay.alipaybill.vo;

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
public class AlipayBillVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//余额
	private String balance;
	
	//收入金额
	private String income;
	
	//支出金额
	private String outcome;
	
	//交易付款时间
	private Timestamp trans_date;
	
	//业务类型
	private String trans_code_msg;
	
	//子业务类型
	private String sub_trans_code_msg;
	
	//商户订单号
	private String merchant_out_order_no;
	
	//备注
	private String memo;
	
	//买家支付宝人民币资金账号
	private String buyer_account;
	
	//卖家支付宝人民币资金账号
	private String seller_account;
	
	//卖家姓名
	private String seller_fullname;
	
	//货币代码156（人民币）
	private String currency;
	
	//充值网银流水号
	private String deposit_bank_no;
	
	//商品名称
	private String goods_title;
	
	//账务序列号
	private String iw_account_log_id;
	
	//合作者身份id
	private String partner_id;
	
	//交易服务费
	private String service_fee;
	
	//交易服务费率
	private String service_fee_ratio;
	
	//交易总金额
	private String total_fee;
	
	//支付宝交易号
	private String trade_no;
	
	//累积退款金额
	private String trade_refund_amount;
	
	//签约产品
	private String sign_product_name;
	
	//费率
	private String rate;
	
	private String check_status;
	
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
	
	/** 获取余额 */
	public String getBalance() {
		return balance;
	}
	
	/** 设置余额 */
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	/** 获取收入金额 */
	public String getIncome() {
		return income;
	}
	
	/** 设置收入金额 */
	public void setIncome(String income) {
		this.income = income;
	}
	
	/** 获取支出金额 */
	public String getOutcome() {
		return outcome;
	}
	
	/** 设置支出金额 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	
	/** 获取交易付款时间 */
	public Timestamp getTrans_date() {
		return trans_date;
	}
	
	/** 设置交易付款时间 */
	public void setTrans_date(Timestamp trans_date) {
		this.trans_date = trans_date;
	}
	
	/** 获取业务类型 */
	public String getTrans_code_msg() {
		return trans_code_msg;
	}
	
	/** 设置业务类型 */
	public void setTrans_code_msg(String trans_code_msg) {
		this.trans_code_msg = trans_code_msg;
	}
	
	/** 获取子业务类型 */
	public String getSub_trans_code_msg() {
		return sub_trans_code_msg;
	}
	
	/** 设置子业务类型 */
	public void setSub_trans_code_msg(String sub_trans_code_msg) {
		this.sub_trans_code_msg = sub_trans_code_msg;
	}
	
	/** 获取商户订单号 */
	public String getMerchant_out_order_no() {
		return merchant_out_order_no;
	}
	
	/** 设置商户订单号 */
	public void setMerchant_out_order_no(String merchant_out_order_no) {
		this.merchant_out_order_no = merchant_out_order_no;
	}
	
	/** 获取备注 */
	public String getMemo() {
		return memo;
	}
	
	/** 设置备注 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	/** 获取买家支付宝人民币资金账号 */
	public String getBuyer_account() {
		return buyer_account;
	}
	
	/** 设置买家支付宝人民币资金账号 */
	public void setBuyer_account(String buyer_account) {
		this.buyer_account = buyer_account;
	}
	
	/** 获取卖家支付宝人民币资金账号 */
	public String getSeller_account() {
		return seller_account;
	}
	
	/** 设置卖家支付宝人民币资金账号 */
	public void setSeller_account(String seller_account) {
		this.seller_account = seller_account;
	}
	
	/** 获取卖家姓名 */
	public String getSeller_fullname() {
		return seller_fullname;
	}
	
	/** 设置卖家姓名 */
	public void setSeller_fullname(String seller_fullname) {
		this.seller_fullname = seller_fullname;
	}
	
	/** 获取货币代码156（人民币） */
	public String getCurrency() {
		return currency;
	}
	
	/** 设置货币代码156（人民币） */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	/** 获取充值网银流水号 */
	public String getDeposit_bank_no() {
		return deposit_bank_no;
	}
	
	/** 设置充值网银流水号 */
	public void setDeposit_bank_no(String deposit_bank_no) {
		this.deposit_bank_no = deposit_bank_no;
	}
	
	/** 获取商品名称 */
	public String getGoods_title() {
		return goods_title;
	}
	
	/** 设置商品名称 */
	public void setGoods_title(String goods_title) {
		this.goods_title = goods_title;
	}
	
	/** 获取账务序列号 */
	public String getIw_account_log_id() {
		return iw_account_log_id;
	}
	
	/** 设置账务序列号 */
	public void setIw_account_log_id(String iw_account_log_id) {
		this.iw_account_log_id = iw_account_log_id;
	}
	
	/** 获取合作者身份id */
	public String getPartner_id() {
		return partner_id;
	}
	
	/** 设置合作者身份id */
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	
	/** 获取交易服务费 */
	public String getService_fee() {
		return service_fee;
	}
	
	/** 设置交易服务费 */
	public void setService_fee(String service_fee) {
		this.service_fee = service_fee;
	}
	
	/** 获取交易服务费率 */
	public String getService_fee_ratio() {
		return service_fee_ratio;
	}
	
	/** 设置交易服务费率 */
	public void setService_fee_ratio(String service_fee_ratio) {
		this.service_fee_ratio = service_fee_ratio;
	}
	
	/** 获取交易总金额 */
	public String getTotal_fee() {
		return total_fee;
	}
	
	/** 设置交易总金额 */
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	
	/** 获取支付宝交易号 */
	public String getTrade_no() {
		return trade_no;
	}
	
	/** 设置支付宝交易号 */
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	
	/** 获取累积退款金额 */
	public String getTrade_refund_amount() {
		return trade_refund_amount;
	}
	
	/** 设置累积退款金额 */
	public void setTrade_refund_amount(String trade_refund_amount) {
		this.trade_refund_amount = trade_refund_amount;
	}
	
	/** 获取签约产品 */
	public String getSign_product_name() {
		return sign_product_name;
	}
	
	/** 设置签约产品 */
	public void setSign_product_name(String sign_product_name) {
		this.sign_product_name = sign_product_name;
	}
	
	/** 获取费率 */
	public String getRate() {
		return rate;
	}
	
	/** 设置费率 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getCheck_status() {
		return check_status;
	}

	public void setCheck_status(String check_status) {
		this.check_status = check_status;
	}

	//----------结束vo的setter和getter方法----------
}