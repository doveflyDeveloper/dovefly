package com.deertt.module.mm.statistics.vo;

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
public class StatisticsBillVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String BILL_CODE_PREFIX = "S";//单号前缀
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市名称
	private String city_name;
	
	//货仓id
	private Integer warehouse_id;
	
	//货仓
	private String warehouse_name;
	
	//结算单号
	private String bill_code;
	
	//结算日期
	private Date bill_date;
	
	//结算时间
	private Timestamp bill_time;
	
	//期初库存总金额
	private BigDecimal origin_amount;
	
	//进货总金额
	private BigDecimal purchase_amount;
	
	private BigDecimal purchase_back_amount;
	
	//售出总金额
	private BigDecimal order_amount;
	
	private BigDecimal order_back_amount;
	
	private BigDecimal out_amount;
	
	//剩余库存总金额
	private BigDecimal stock_amount;
	
	//盘点库存总金额
	private BigDecimal check_amount;
	
	private BigDecimal dif_amount;
	
	//结转总金额
	private BigDecimal final_amount;
	
	private List<StatisticsDetailVo> details;
	
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
	
	/** 获取结算单号 */
	public String getBill_code() {
		return bill_code;
	}
	
	/** 设置结算单号 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}
	
	/** 获取结算日期 */
	public Date getBill_date() {
		return bill_date;
	}
	
	/** 设置结算日期 */
	public void setBill_date(Date bill_date) {
		this.bill_date = bill_date;
	}
	
	/** 获取结算时间 */
	public Timestamp getBill_time() {
		return bill_time;
	}
	
	/** 设置结算时间 */
	public void setBill_time(Timestamp bill_time) {
		this.bill_time = bill_time;
	}
	
	/** 获取期初库存总金额 */
	public BigDecimal getOrigin_amount() {
		return origin_amount;
	}
	
	/** 设置期初库存总金额 */
	public void setOrigin_amount(BigDecimal origin_amount) {
		this.origin_amount = origin_amount;
	}
	
	/** 获取进货总金额 */
	public BigDecimal getPurchase_amount() {
		return purchase_amount;
	}
	
	/** 设置进货总金额 */
	public void setPurchase_amount(BigDecimal purchase_amount) {
		this.purchase_amount = purchase_amount;
	}
	
	public BigDecimal getPurchase_back_amount() {
		return purchase_back_amount;
	}

	public void setPurchase_back_amount(BigDecimal purchase_back_amount) {
		this.purchase_back_amount = purchase_back_amount;
	}
	
	/** 获取售出总金额 */
	public BigDecimal getOrder_amount() {
		return order_amount;
	}
	
	/** 设置售出总金额 */
	public void setOrder_amount(BigDecimal order_amount) {
		this.order_amount = order_amount;
	}
	
	public BigDecimal getOrder_back_amount() {
		return order_back_amount;
	}

	public void setOrder_back_amount(BigDecimal order_back_amount) {
		this.order_back_amount = order_back_amount;
	}

	public BigDecimal getOut_amount() {
		return out_amount;
	}

	public void setOut_amount(BigDecimal out_amount) {
		this.out_amount = out_amount;
	}
	
	/** 获取剩余库存总金额 */
	public BigDecimal getStock_amount() {
		return stock_amount;
	}
	
	/** 设置剩余库存总金额 */
	public void setStock_amount(BigDecimal stock_amount) {
		this.stock_amount = stock_amount;
	}

	public BigDecimal getCheck_amount() {
		return check_amount;
	}

	public void setCheck_amount(BigDecimal check_amount) {
		this.check_amount = check_amount;
	}
	
	public BigDecimal getDif_amount() {
		return dif_amount;
	}

	public void setDif_amount(BigDecimal dif_amount) {
		this.dif_amount = dif_amount;
	}

	public BigDecimal getFinal_amount() {
		return final_amount;
	}

	public void setFinal_amount(BigDecimal final_amount) {
		this.final_amount = final_amount;
	}
	
	public List<StatisticsDetailVo> getDetails() {
		return details;
	}
	
	public void setDetails(List<StatisticsDetailVo> details) {
		this.details = details;
	}
	
	//----------结束vo的setter和getter方法----------
}