package com.deertt.module.fund.refund.vo;

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
public class RefundVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String BILL_CODE_PREFIX = "F";//单号前缀
	
	public static final String BILL_TYPE_T = "t";//店长销售单
	public static final String BILL_TYPE_O = "o";//店长进货单
	public static final String BILL_TYPE_M = "m";//超市销售单
	
	public static final String REFUND_TO_WXPAY = "wxpay";//付款到微信
	public static final String REFUND_TO_ALIPAY = "alipay";//付款到支付宝
	public static final String REFUND_TO_TTPAY = "ttpay";//付款到汀汀支付
	
	public static final String PAY_STATUS_UNPAY = "0";//未付款
	public static final String PAY_STATUS_DEALING = "1";//付款中
	public static final String PAY_STATUS_SUCCESS = "2";//付款成功
	public static final String PAY_STATUS_FAIL = "3";//支付失败
	
	public static final String STATUS_UNDEAL = "0";//未处理
	public static final String STATUS_DEALING = "1";//处理中
	public static final String STATUS_SUCCESS= "2";//处理成功
	public static final String STATUS_FAIL = "3";//处理失败
	public static final String STATUS_DENYED = "4";//被拒绝
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//订单类型：t-店长销售单，o-店长进货单，m-超市销售单
	private String bill_type;
	
	//卖家id
	private Integer seller_id;
	
	//卖家
	private String seller_name;
	
	//买家id
	private Integer buyer_id;
	
	//买家姓名
	private String buyer_name;
	
	//关联单据
	private String refer_bill_code;
	
	//退款单号
	private String bill_code;
	
	//退款时间
	private Timestamp refund_time;
	
	//退款金额
	private BigDecimal refund_amount;
	
	//付款去向
	private String refund_to;
	
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
	
	//支付消息
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
	
	public String getBill_type() {
		return bill_type;
	}
	
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	
	public Integer getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(Integer seller_id) {
		this.seller_id = seller_id;
	}

	public String getSeller_name() {
		return seller_name;
	}

	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}
	
	/** 获取买家id */
	public Integer getBuyer_id() {
		return buyer_id;
	}
	
	/** 设置买家id */
	public void setBuyer_id(Integer buyer_id) {
		this.buyer_id = buyer_id;
	}
	
	/** 获取买家姓名 */
	public String getBuyer_name() {
		return buyer_name;
	}
	
	/** 设置买家姓名 */
	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}
	
	/** 获取关联单据 */
	public String getRefer_bill_code() {
		return refer_bill_code;
	}
	
	/** 设置关联单据 */
	public void setRefer_bill_code(String refer_bill_code) {
		this.refer_bill_code = refer_bill_code;
	}
	
	/** 获取退款单号 */
	public String getBill_code() {
		return bill_code;
	}
	
	/** 设置退款单号 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}
	
	/** 获取退款金额 */
	public BigDecimal getRefund_amount() {
		return refund_amount;
	}
	
	/** 设置退款金额 */
	public void setRefund_amount(BigDecimal refund_amount) {
		this.refund_amount = refund_amount;
	}
	
	/** 获取退款时间 */
	public Timestamp getRefund_time() {
		return refund_time;
	}
	
	/** 设置退款时间 */
	public void setRefund_time(Timestamp refund_time) {
		this.refund_time = refund_time;
	}
	
	/** 获取付款去向 */
	public String getRefund_to() {
		return refund_to;
	}
	
	/** 设置付款去向 */
	public void setRefund_to(String refund_to) {
		this.refund_to = refund_to;
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

	/** 获取支付回执编号 */
	public String getPay_code() {
		return pay_code;
	}
	
	/** 设置支付回执编号 */
	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}
	
	public BigDecimal getPay_amount() {
		return pay_amount;
	}

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
	
	/** 获取支付消息 */
	public String getPay_msg() {
		return pay_msg;
	}
	
	/** 设置支付消息 */
	public void setPay_msg(String pay_msg) {
		this.pay_msg = pay_msg;
	}
	
	//----------结束vo的setter和getter方法----------
}