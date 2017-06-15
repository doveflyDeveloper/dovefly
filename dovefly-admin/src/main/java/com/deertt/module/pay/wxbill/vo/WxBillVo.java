package com.deertt.module.pay.wxbill.vo;

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
public class WxBillVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//交易时间
	private Timestamp trade_time;
	
	//公众账号id
	private String gh_id;
	
	//商户号
	private String mch_id;
	
	//子商户号
	private String sub_mch;
	
	//设备号
	private String device_id;
	
	//微信订单号
	private String wx_order;
	
	//商户订单号
	private String bz_order;
	
	//用户标识
	private String open_id;
	
	//交易类型
	private String trade_type;
	
	//交易状态
	private String trade_status;
	
	//付款银行
	private String bank;
	
	//货币种类
	private String currency;
	
	//总金额
	private String total_money;
	
	//企业红包金额
	private String red_packet_money;
	
	//微信退款单号
	private String wx_refund;
	
	//商户退款单号
	private String bz_refund;
	
	//退款金额
	private String refund_money;
	
	//企业红包退款金额
	private String red_packet_refund;
	
	//退款类型
	private String refund_type;
	
	//退款状态
	private String refund_status;
	
	//商品名称
	private String product_name;
	
	//商户数据包
	private String bz_data_packet;
	
	//手续费
	private String fee;
	
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
	
	/** 获取交易时间 */
	public Timestamp getTrade_time() {
		return trade_time;
	}
	
	/** 设置交易时间 */
	public void setTrade_time(Timestamp trade_time) {
		this.trade_time = trade_time;
	}
	
	/** 获取公众账号id */
	public String getGh_id() {
		return gh_id;
	}
	
	/** 设置公众账号id */
	public void setGh_id(String gh_id) {
		this.gh_id = gh_id;
	}
	
	/** 获取商户号 */
	public String getMch_id() {
		return mch_id;
	}
	
	/** 设置商户号 */
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	
	/** 获取子商户号 */
	public String getSub_mch() {
		return sub_mch;
	}
	
	/** 设置子商户号 */
	public void setSub_mch(String sub_mch) {
		this.sub_mch = sub_mch;
	}
	
	/** 获取设备号 */
	public String getDevice_id() {
		return device_id;
	}
	
	/** 设置设备号 */
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	
	/** 获取微信订单号 */
	public String getWx_order() {
		return wx_order;
	}
	
	/** 设置微信订单号 */
	public void setWx_order(String wx_order) {
		this.wx_order = wx_order;
	}
	
	/** 获取商户订单号 */
	public String getBz_order() {
		return bz_order;
	}
	
	/** 设置商户订单号 */
	public void setBz_order(String bz_order) {
		this.bz_order = bz_order;
	}
	
	/** 获取用户标识 */
	public String getOpen_id() {
		return open_id;
	}
	
	/** 设置用户标识 */
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	
	/** 获取交易类型 */
	public String getTrade_type() {
		return trade_type;
	}
	
	/** 设置交易类型 */
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	
	/** 获取交易状态 */
	public String getTrade_status() {
		return trade_status;
	}
	
	/** 设置交易状态 */
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	
	/** 获取付款银行 */
	public String getBank() {
		return bank;
	}
	
	/** 设置付款银行 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	/** 获取货币种类 */
	public String getCurrency() {
		return currency;
	}
	
	/** 设置货币种类 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	/** 获取总金额 */
	public String getTotal_money() {
		return total_money;
	}
	
	/** 设置总金额 */
	public void setTotal_money(String total_money) {
		this.total_money = total_money;
	}
	
	/** 获取企业红包金额 */
	public String getRed_packet_money() {
		return red_packet_money;
	}
	
	/** 设置企业红包金额 */
	public void setRed_packet_money(String red_packet_money) {
		this.red_packet_money = red_packet_money;
	}
	
	/** 获取微信退款单号 */
	public String getWx_refund() {
		return wx_refund;
	}
	
	/** 设置微信退款单号 */
	public void setWx_refund(String wx_refund) {
		this.wx_refund = wx_refund;
	}
	
	/** 获取商户退款单号 */
	public String getBz_refund() {
		return bz_refund;
	}
	
	/** 设置商户退款单号 */
	public void setBz_refund(String bz_refund) {
		this.bz_refund = bz_refund;
	}
	
	/** 获取退款金额 */
	public String getRefund_money() {
		return refund_money;
	}
	
	/** 设置退款金额 */
	public void setRefund_money(String refund_money) {
		this.refund_money = refund_money;
	}
	
	/** 获取企业红包退款金额 */
	public String getRed_packet_refund() {
		return red_packet_refund;
	}
	
	/** 设置企业红包退款金额 */
	public void setRed_packet_refund(String red_packet_refund) {
		this.red_packet_refund = red_packet_refund;
	}
	
	/** 获取退款类型 */
	public String getRefund_type() {
		return refund_type;
	}
	
	/** 设置退款类型 */
	public void setRefund_type(String refund_type) {
		this.refund_type = refund_type;
	}
	
	/** 获取退款状态 */
	public String getRefund_status() {
		return refund_status;
	}
	
	/** 设置退款状态 */
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}
	
	/** 获取商品名称 */
	public String getProduct_name() {
		return product_name;
	}
	
	/** 设置商品名称 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	/** 获取商户数据包 */
	public String getBz_data_packet() {
		return bz_data_packet;
	}
	
	/** 设置商户数据包 */
	public void setBz_data_packet(String bz_data_packet) {
		this.bz_data_packet = bz_data_packet;
	}
	
	/** 获取手续费 */
	public String getFee() {
		return fee;
	}
	
	/** 设置手续费 */
	public void setFee(String fee) {
		this.fee = fee;
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