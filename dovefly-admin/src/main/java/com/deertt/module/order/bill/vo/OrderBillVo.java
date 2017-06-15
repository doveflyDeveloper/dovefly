package com.deertt.module.order.bill.vo;

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
public class OrderBillVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String BILL_CODE_PREFIX = "O";//单号前缀
	
	public static final String PAY_TYPE_ONLINE = "online";
	public static final String PAY_TYPE_COD = "cod";
	public static final String PAY_TYPE_TTPAY = "ttpay";
	public static final String PAY_TYPE_ALIPAY = "alipay";
	public static final String PAY_TYPE_WXPAY = "wxpay";
	
	public static final String PAY_STATUS_NO = "0";//未支付
	public static final String PAY_STATUS_SUCCESS = "1";//支付成功
	public static final String PAY_STATUS_FAIL = "2";//支付失败
	public static final String PAY_STATUS_REFUNDING = "3";//退款中
	public static final String PAY_STATUS_REFUNDED = "4";//已退款
	
	public static final String REFUND_TYPE_TTPAY = "ttpay";
	public static final String REFUND_TYPE_ALIPAY = "alipay";
	public static final String REFUND_TYPE_WXPAY = "wxpay";
	
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
	
	//城市ID
	private Integer city_id;
		
	//城市名称
	private String city_name;
	
	private Integer school_id;
	
	private String school_name;
	
	private Integer manager_id;
	
	private String manager_name;
	
	//卖家ID
	private Integer warehouse_id;
		
    //卖家名称
	private String warehouse_name;
	
	//买家id
	private Integer shop_id;
	
	//买家名称
	private String shop_name;
	
	//订单编号
	private String bill_code;
	
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
	
	//应付金额
	private BigDecimal real_amount;
	
	private BigDecimal total_amount;
	
	private BigDecimal income_amount;
	
	private String rcv_name;
	
	private String rcv_mobile;
	
	private String rcv_address;
	
	private String pay_type;
	
	private String pay_code;
	
	private BigDecimal pay_amount;
	
	private Timestamp pay_time;
	
	private String pay_status;
	
	private String refund_type;
	
	private String refund_code;
	
	private BigDecimal refund_amount;
	
	private Timestamp refund_time;
	
	private String refund_status;
	
	private Timestamp submit_time;
	
	private Timestamp send_time;
	
	private Timestamp receive_time;
	
	//订单明细列表
	private List<OrderDetailVo> details;
	
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
	
	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	public Integer getWarehouse_id() {
		return warehouse_id;
	}
	
	public void setWarehouse_id(Integer warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	
	public String getWarehouse_name() {
		return warehouse_name;
	}
	
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
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
		
	/** 获取订单编号 */
	public String getBill_code() {
		return bill_code;
	}
	
	/** 设置订单编号 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}
	
	/** 获取买家id */
	public Integer getShop_id() {
		return shop_id;
	}
	
	/** 设置买家id */
	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}
	
	/** 获取买家名称 */
	public String getShop_name() {
		return shop_name;
	}
	
	/** 设置买家名称 */
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
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

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}
	
	public BigDecimal getIncome_amount() {
		return income_amount;
	}

	public void setIncome_amount(BigDecimal income_amount) {
		this.income_amount = income_amount;
	}
	public String getRcv_name() {
		return rcv_name;
	}

	public void setRcv_name(String rcv_name) {
		this.rcv_name = rcv_name;
	}

	public String getRcv_mobile() {
		return rcv_mobile;
	}

	public void setRcv_mobile(String rcv_mobile) {
		this.rcv_mobile = rcv_mobile;
	}

	public String getRcv_address() {
		return rcv_address;
	}

	public void setRcv_address(String rcv_address) {
		this.rcv_address = rcv_address;
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
	
	public String getRefund_type() {
		return refund_type;
	}

	public void setRefund_type(String refund_type) {
		this.refund_type = refund_type;
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
	
	public List<OrderDetailVo> getDetails() {
		return details;
	}
	
	public void setDetails(List<OrderDetailVo> details) {
		this.details = details;
	}
	
	//----------结束vo的setter和getter方法----------
	
	public BigDecimal calculateAmount() {
		BigDecimal result = BigDecimal.ZERO;
		if (details != null) {
			for (OrderDetailVo detail : details) {
				result = result.add(detail.getSub_total());
			}
		}
		return result;
	}
	
}