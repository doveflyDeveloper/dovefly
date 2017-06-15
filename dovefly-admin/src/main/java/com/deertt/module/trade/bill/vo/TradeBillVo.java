package com.deertt.module.trade.bill.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class TradeBillVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String BILL_CODE_PREFIX = "T";//单号前缀
	
	public static final String BILL_TYPE_WX = "WX";//微信公众平台
	public static final String BILL_TYPE_MO = "MO";//手机浏览器
	public static final String BILL_TYPE_XS = "XS";//PC网站
	public static final String BILL_TYPE_MAKE = "MAKE";//后台补单
	
	public static final String PAY_TYPE_ONLINE = "online";//选择线上支付（但未支付完成前）
	public static final String PAY_TYPE_COD = "cod";//货到付款
	public static final String PAY_TYPE_TTPAY = "ttpay";//
	public static final String PAY_TYPE_ALIPAY = "alipay";//支付宝（支付完成）
	public static final String PAY_TYPE_WXPAY = "wxpay";//微信（支付完成）
	
	public static final String PAY_STATUS_NO = "0";//未支付
	public static final String PAY_STATUS_SUCCESS = "1";//支付成功
	public static final String PAY_STATUS_FAIL = "2";//支付失败
	public static final String PAY_STATUS_REFUNDING = "3";//退款中
	public static final String PAY_STATUS_REFUND_SUCCESS = "4";//退款成功
	public static final String PAY_STATUS_REFUND_FAIL = "5";//退款失败
	
	public static final String REFUND_STATUS_NO = "0";//未退款
	public static final String REFUND_STATUS_ING = "3";//退款中
	public static final String REFUND_STATUS_SUCCESS = "4";//退款成功
	public static final String REFUND_STATUS_FAIL = "5";//退款失败
	
	public static final String STATUS_EDIT = "edit";//未提交
	public static final String STATUS_SUBMIT = "submit";//已提交 
	public static final String STATUS_DELIVERED = "delivered";//已发货
	public static final String STATUS_RECEIVED = "received";//已收货
	public static final String STATUS_APPLY_FOR_REFUND = "apply_for_refund";//申请退款中
	public static final String STATUS_APPLY_FOR_RETURN = "apply_for_return";//申请退货中
	public static final String STATUS_AGREE_TO_REFUND = "agree_to_refund";//同意退款
	public static final String STATUS_AGREE_TO_RETURN = "agree_to_return";//同意退货
	public static final String STATUS_REFUNDED = "refunded";//已退款
	public static final String STATUS_RETURNED = "returned";//已退货
	public static final String STATUS_CLOSED = "closed";//交易关闭

	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市名称
	private String city_name;
	
	private Integer school_id;
	
	private String school_name;
	
	private Integer manager_id;
	
	private String manager_name;
	
	//店铺ID
	private Integer shop_id;
	
	//店铺名称
	private String shop_name;
	
	//买家ID
	private Integer buyer_id;
	
	//买家名称
	private String buyer_name;
	
	//订单编号
	private String bill_code;
	
	//订单类型
	private String bill_type;
	
	//订单日期
	private Date bill_date;
	
	private Timestamp bill_time;
	
	//商品数量
	private BigDecimal quantity;
	
	//单笔限用汀豆数量
	private Integer limit_coin_quantity;
	
	//返还汀豆数量
	private Integer send_coin_quantity;
	
	//使用汀豆代付数量
	private Integer use_coin_quantity;
	
	//使用汀豆代付金额
	private BigDecimal use_coin_amount;
	
	//金额
	private BigDecimal real_amount;
	
	//总金额
	private BigDecimal total_amount;
	
	//卖家收入金额
	private BigDecimal income_amount;
	
	private String pay_type;
	
	private String pay_code;
	
	private BigDecimal pay_amount;
	
	private Timestamp pay_time;
	
	private String pay_status;
	
	private String refund_code;
	
	private BigDecimal refund_amount;
	
	private Timestamp refund_time;
	
	private String refund_status;
	
	//收货人
	private String ship_name;
	
	//联系电话
	private String ship_mobile;
	
	//收货地址
	private String ship_addr;
	
	private Timestamp submit_time;
	
	private Timestamp send_time;
	
	private Timestamp receive_time;
	
	private List<TradeDetailVo> details;
	
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
	
	/** 获取城市id */
	public Integer getCity_id() {
		return city_id;
	}
	
	/** 设置城市id */
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	/** 获取城市名称 */
	public String getCity_name() {
		return city_name;
	}
	
	/** 设置城市名称 */
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	public Integer getSchool_id() {
		return school_id;
	}

	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

	public Integer getManager_id() {
		return manager_id;
	}

	public void setManager_id(Integer manager_id) {
		this.manager_id = manager_id;
	}

	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	
	/** 获取所属店铺id */
	public Integer getShop_id() {
		return shop_id;
	}
	
	/** 设置所属店铺id */
	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}
	
	/** 获取所属店铺名称 */
	public String getShop_name() {
		return shop_name;
	}
	
	/** 设置所属店铺名称 */
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	
	/** 获取买家id */
	public Integer getBuyer_id() {
		return buyer_id;
	}
	
	/** 设置买家id */
	public void setBuyer_id(Integer buyer_id) {
		this.buyer_id = buyer_id;
	}
	
	/** 获取买家名称 */
	public String getBuyer_name() {
		return buyer_name;
	}
	
	/** 设置买家名称 */
	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}
	
	/** 获取订单编号 */
	public String getBill_code() {
		return bill_code;
	}
	
	/** 设置订单编号 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}
	
	/** 获取订单类型 */
	public String getBill_type() {
		return bill_type;
	}
	
	/** 设置订单类型 */
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	
	/** 获取订单日期 */
	public Date getBill_date() {
		return bill_date;
	}
	
	/** 设置订单日期 */
	public void setBill_date(Date bill_date) {
		this.bill_date = bill_date;
	}
	
	public Timestamp getBill_time() {
		return bill_time;
	}
	
	public void setBill_time(Timestamp bill_time) {
		this.bill_time = bill_time;
	}
	
	/** 获取商品数量 */
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	/** 设置商品数量 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	public Integer getLimit_coin_quantity() {
		return limit_coin_quantity;
	}
	
	public void setLimit_coin_quantity(Integer limit_coin_quantity) {
		this.limit_coin_quantity = limit_coin_quantity;
	}
	
	public Integer getUse_coin_quantity() {
		return use_coin_quantity;
	}
	
	public void setUse_coin_quantity(Integer use_coin_quantity) {
		this.use_coin_quantity = use_coin_quantity;
	}
	
	public Integer getSend_coin_quantity() {
		return send_coin_quantity;
	}
	
	public void setSend_coin_quantity(Integer send_coin_quantity) {
		this.send_coin_quantity = send_coin_quantity;
	}

	public BigDecimal getUse_coin_amount() {
		return use_coin_amount;
	}

	public void setUse_coin_amount(BigDecimal use_coin_amount) {
		this.use_coin_amount = use_coin_amount;
	}
	
	/** 获取金额 */
	public BigDecimal getReal_amount() {
		return real_amount;
	}
	
	/** 设置金额 */
	public void setReal_amount(BigDecimal real_amount) {
		this.real_amount = real_amount;
	}
	
	/** 获取总金额 */
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	
	/** 设置总金额 */
	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public BigDecimal getIncome_amount() {
		return income_amount;
	}

	public void setIncome_amount(BigDecimal income_amount) {
		this.income_amount = income_amount;
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
	
	public String getRefund_code() {
		return refund_code;
	}

	public void setRefund_code(String refund_code) {
		this.refund_code = refund_code;
	}

	public BigDecimal getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(BigDecimal refund_amount) {
		this.refund_amount = refund_amount;
	}

	public Timestamp getRefund_time() {
		return refund_time;
	}

	public void setRefund_time(Timestamp refund_time) {
		this.refund_time = refund_time;
	}

	public String getRefund_status() {
		return refund_status;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}
	
	/** 获取收货人 */
	public String getShip_name() {
		return ship_name;
	}
	
	/** 设置收货人 */
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	
	/** 获取联系电话 */
	public String getShip_mobile() {
		return ship_mobile;
	}
	
	/** 设置联系电话 */
	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}
	
	/** 获取收货地址 */
	public String getShip_addr() {
		return ship_addr;
	}
	
	/** 设置收货地址 */
	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}
	
	public Timestamp getSubmit_time() {
		return submit_time;
	}

	public void setSubmit_time(Timestamp submit_time) {
		this.submit_time = submit_time;
	}

	public Timestamp getSend_time() {
		return send_time;
	}

	public void setSend_time(Timestamp send_time) {
		this.send_time = send_time;
	}

	public Timestamp getReceive_time() {
		return receive_time;
	}

	public void setReceive_time(Timestamp receive_time) {
		this.receive_time = receive_time;
	}

	public List<TradeDetailVo> getDetails() {
		return details;
	}
	
	public void setDetails(List<TradeDetailVo> details) {
		this.details = details;
	}
	
	//----------结束vo的setter和getter方法----------
}