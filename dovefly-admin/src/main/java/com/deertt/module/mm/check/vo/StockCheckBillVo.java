package com.deertt.module.mm.check.vo;

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
public class StockCheckBillVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String BILL_CODE_PREFIX = "C";//单号前缀
	
	public static final String STATUS_EDIT = "0";
	
	public static final String STATUS_CONFIRM = "1";
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//仓库id
	private Integer city_id;
	
	//仓库编码
	private String city_name;
	
	//货仓id
	private Integer warehouse_id;
	
	//货仓
	private String warehouse_name;
	
	//盘点单号
	private String bill_code;
	
	//盘点日期
	private Date bill_date;
	
	//盘点时间
	private Timestamp bill_time;
	
	//库存金额
	private BigDecimal stock_amount;
	
	//盘点金额
	private BigDecimal check_amount;
	
	//盈亏金额
	private BigDecimal dif_amount;
	
	private List<StockCheckDetailVo> details;
	
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
	
	/** 获取仓库id */
	public Integer getCity_id() {
		return city_id;
	}
	
	/** 设置仓库id */
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	/** 获取仓库编码 */
	public String getCity_name() {
		return city_name;
	}
	
	/** 设置仓库编码 */
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
	
	/** 获取盘点单号 */
	public String getBill_code() {
		return bill_code;
	}
	
	/** 设置盘点单号 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}
	
	/** 获取盘点日期 */
	public Date getBill_date() {
		return bill_date;
	}
	
	/** 设置盘点日期 */
	public void setBill_date(Date bill_date) {
		this.bill_date = bill_date;
	}
	
	/** 获取盘点时间 */
	public Timestamp getBill_time() {
		return bill_time;
	}
	
	/** 设置盘点时间 */
	public void setBill_time(Timestamp bill_time) {
		this.bill_time = bill_time;
	}
	
	/** 获取库存金额 */
	public BigDecimal getStock_amount() {
		return stock_amount;
	}
	
	/** 设置库存金额 */
	public void setStock_amount(BigDecimal stock_amount) {
		this.stock_amount = stock_amount;
	}
	
	/** 获取盘点金额 */
	public BigDecimal getCheck_amount() {
		return check_amount;
	}
	
	/** 设置盘点金额 */
	public void setCheck_amount(BigDecimal check_amount) {
		this.check_amount = check_amount;
	}
	
	public BigDecimal getDif_amount() {
		return dif_amount;
	}

	public void setDif_amount(BigDecimal dif_amount) {
		this.dif_amount = dif_amount;
	}
	
	public List<StockCheckDetailVo> getDetails() {
		return details;
	}
	
	public void setDetails(List<StockCheckDetailVo> details) {
		this.details = details;
	}
	
	//----------结束vo的setter和getter方法----------
}