package com.deertt.module.purchase.out.vo;

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
public class PurchaseOutBillVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String BILL_CODE_PREFIX = "U";//单号前缀
	
	public static final String STATUS_UNCHECKOUT = "0";//未出库
	public static final String STATUS_CHECKOUT = "1";//已出库
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市
	private String city_name;
	
	//货仓id
	private Integer warehouse_id;
	
	//货仓
	private String warehouse_name;
	
	//出库单号
	private String bill_code;
	
	//订单日期
	private Date bill_date;
	
	//出库时间
	private Timestamp bill_time;
	
	//订单金额
	private BigDecimal amount;
	
	private List<PurchaseOutDetailVo> details;
	
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
	
	/** 获取城市 */
	public String getCity_name() {
		return city_name;
	}
	
	/** 设置城市 */
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	/** 获取货仓id */
	public Integer getWarehouse_id() {
		return warehouse_id;
	}
	
	/** 设置货仓id */
	public void setWarehouse_id(Integer warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	
	/** 获取货仓 */
	public String getWarehouse_name() {
		return warehouse_name;
	}
	
	/** 设置货仓 */
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	
	/** 获取出库单号 */
	public String getBill_code() {
		return bill_code;
	}
	
	/** 设置出库单号 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}
	
	/** 获取订单日期 */
	public Date getBill_date() {
		return bill_date;
	}
	
	/** 设置订单日期 */
	public void setBill_date(Date bill_date) {
		this.bill_date = bill_date;
	}
	
	/** 获取出库时间 */
	public Timestamp getBill_time() {
		return bill_time;
	}
	
	/** 设置出库时间 */
	public void setBill_time(Timestamp bill_time) {
		this.bill_time = bill_time;
	}
	
	/** 获取订单金额 */
	public BigDecimal getAmount() {
		return amount;
	}
	
	/** 设置订单金额 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<PurchaseOutDetailVo> getDetails() {
		return details;
	}

	public void setDetails(List<PurchaseOutDetailVo> details) {
		this.details = details;
	}
	
	//----------结束vo的setter和getter方法----------
}