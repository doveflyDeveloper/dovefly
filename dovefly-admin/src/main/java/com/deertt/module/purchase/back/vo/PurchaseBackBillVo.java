package com.deertt.module.purchase.back.vo;

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
public class PurchaseBackBillVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String BILL_CODE_PREFIX = "K";//单号前缀
	
	public static final String STATUS_UNCONFIRM = "0";//未确认
	public static final String STATUS_CONFIRM = "1";//已确认
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市名称
	private String city_name;
	
	//买家
	private Integer warehouse_id;
	
	//买家
	private String warehouse_name;
	
	//供应商id
	private Integer supplier_id;
	
	//供应商
	private String supplier_name;
	
	//订单编号
	private String bill_code;
	
	//订单日期
	private Date bill_date;
	
	//下单时间
	private Timestamp bill_time;
	
	//订单金额
	private BigDecimal amount;
	
	//订单明细列表
	private List<PurchaseBackDetailVo> details;
	
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
	
	/** 获取买家名称 */
	public Integer getWarehouse_id() {
		return warehouse_id;
	}
	
	/** 设置买家名称 */
	public void setWarehouse_id(Integer warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	
	/** 获取买家名称 */
	public String getWarehouse_name() {
		return warehouse_name;
	}
	
	/** 设置买家名称 */
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	
	/** 获取供应商id */
	public Integer getSupplier_id() {
		return supplier_id;
	}
	
	/** 设置供应商id */
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	
	/** 获取供应商 */
	public String getSupplier_name() {
		return supplier_name;
	}
	
	/** 设置供应商 */
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	
	/** 获取订单编号 */
	public String getBill_code() {
		return bill_code;
	}
	
	/** 设置订单编号 */
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
	
	/** 获取下单时间 */
	public Timestamp getBill_time() {
		return bill_time;
	}
	
	/** 设置下单时间 */
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
	
	public List<PurchaseBackDetailVo> getDetails() {
		return details;
	}
	
	public void setDetails(List<PurchaseBackDetailVo> details) {
		this.details = details;
	}
	
	//----------结束vo的setter和getter方法----------
}