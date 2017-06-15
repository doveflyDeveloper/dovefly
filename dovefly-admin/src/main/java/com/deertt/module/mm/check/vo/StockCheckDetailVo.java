package com.deertt.module.mm.check.vo;

import java.math.BigDecimal;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class StockCheckDetailVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//盘点单id
	private Integer bill_id;
	
	//商品id
	private Integer goods_id;
	
	//商品名称
	private String goods_name;
	
	//图片
	private String goods_image;
	
	private BigDecimal stock_quantity;
	
	private BigDecimal stock_amount;
	
	//数量
	private BigDecimal check_quantity;
	
	//金额
	private BigDecimal check_amount;
	
	//盈亏数量
	private BigDecimal dif_quantity;
	
	//盈亏金额
	private BigDecimal dif_amount;
	
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
	
	/** 获取盘点单id */
	public Integer getBill_id() {
		return bill_id;
	}
	
	/** 设置盘点单id */
	public void setBill_id(Integer bill_id) {
		this.bill_id = bill_id;
	}
	
	/** 获取商品id */
	public Integer getGoods_id() {
		return goods_id;
	}
	
	/** 设置商品id */
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	
	/** 获取商品名称 */
	public String getGoods_name() {
		return goods_name;
	}
	
	/** 设置商品名称 */
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	
	/** 获取图片 */
	public String getGoods_image() {
		return goods_image;
	}
	
	/** 设置图片 */
	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}
	
	public BigDecimal getStock_quantity() {
		return stock_quantity;
	}

	public void setStock_quantity(BigDecimal stock_quantity) {
		this.stock_quantity = stock_quantity;
	}

	public BigDecimal getStock_amount() {
		return stock_amount;
	}

	public void setStock_amount(BigDecimal stock_amount) {
		this.stock_amount = stock_amount;
	}
	
	/** 获取数量 */
	public BigDecimal getCheck_quantity() {
		return check_quantity;
	}
	
	/** 设置数量 */
	public void setCheck_quantity(BigDecimal check_quantity) {
		this.check_quantity = check_quantity;
	}
	
	public BigDecimal getCheck_amount() {
		return check_amount;
	}
	
	public void setCheck_amount(BigDecimal check_amount) {
		this.check_amount = check_amount;
	}
	
	/** 获取盈亏数量 */
	public BigDecimal getDif_quantity() {
		return dif_quantity;
	}
	
	/** 设置盈亏数量 */
	public void setDif_quantity(BigDecimal dif_quantity) {
		this.dif_quantity = dif_quantity;
	}
	
	/** 获取盈亏金额 */
	public BigDecimal getDif_amount() {
		return dif_amount;
	}
	
	/** 设置盈亏金额 */
	public void setDif_amount(BigDecimal dif_amount) {
		this.dif_amount = dif_amount;
	}
	
	//----------结束vo的setter和getter方法----------
}