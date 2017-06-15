package com.deertt.module.order.back.vo;

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
public class OrderBackBillVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;

	public static final String BILL_CODE_PREFIX = "B";// 单号前缀
	
	public static final String PAY_TYPE_ONLINE = "online";
	public static final String PAY_TYPE_COD = "cod";
	public static final String PAY_TYPE_TTPAY = "ttpay";
	public static final String PAY_TYPE_ALIPAY = "alipay";
	public static final String PAY_TYPE_WXPAY = "wxpay";

	public static final String PAY_STATUS_NO = "0";// 未支付
	public static final String PAY_STATUS_SUCCESS = "1";// 支付成功
	public static final String PAY_STATUS_FAIL = "2";// 支付失败
	public static final String PAY_STATUS_REFUNDING = "3";// 退款中
	public static final String PAY_STATUS_REFUNDED = "4";// 已退款
	
	public static final String STATUS_EDIT = "edit";//未提交
	public static final String STATUS_SUBMIT = "submit";//已提交
	public static final String STATUS_RECEIVED = "received";//已收货
	public static final String STATUS_REJECTED = "rejected";//拒绝

	// ----------开始vo的属性 ----------
	// id
	private Integer id;

	private Integer city_id;

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
	
	// 退货单编号
	private String bill_code;

	// 退货单日期
	private Date bill_date;

	private Timestamp bill_time;

	// 金额
	private BigDecimal amount;

	private String pay_type;

	private String pay_code;

	private BigDecimal pay_amount;

	private Timestamp pay_time;

	private String pay_status;

	// 退货单明细列表
	private List<OrderBackDetailVo> details;

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

	/** 获取退货单编号 */
	public String getBill_code() {
		return bill_code;
	}

	/** 设置退货单编号 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}

	/** 获取退货单日期 */
	public Date getBill_date() {
		return bill_date;
	}

	/** 设置退货单日期 */
	public void setBill_date(Date bill_date) {
		this.bill_date = bill_date;
	}

	public Timestamp getBill_time() {
		return bill_time;
	}

	public void setBill_time(Timestamp bill_time) {
		this.bill_time = bill_time;
	}

	/** 获取金额 */
	public BigDecimal getAmount() {
		return amount;
	}

	/** 设置金额 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public List<OrderBackDetailVo> getDetails() {
		return details;
	}

	public void setDetails(List<OrderBackDetailVo> details) {
		this.details = details;
	}

	// ----------结束vo的setter和getter方法----------

}