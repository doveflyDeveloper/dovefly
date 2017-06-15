package com.deertt.module.order.bill.vo;

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
public class OrderDetailVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//订单id
	private Integer bill_id;
	
	//商品id
	private Integer goods_id;
	
	//商品名称
	private String goods_name;
	
	//商品图片
	private String goods_image;
	
	//售价
	private BigDecimal sale_price;
	
	//采购数量
	private BigDecimal quantity;
	
	//采购小计
	private BigDecimal sub_total;
	
	private String buy_rule;
	
	private BigDecimal stock_sum;
	
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
	
	/** 获取订单id */
	public Integer getBill_id() {
		return bill_id;
	}
	
	/** 设置订单id */
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
	
	/** 获取商品图片 */
	public String getGoods_image() {
		return goods_image;
	}
	
	/** 设置商品图片 */
	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}
	
	/** 获取售价 */
	public BigDecimal getSale_price() {
		return sale_price;
	}
	
	/** 设置售价 */
	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}
	
	/** 获取采购数量 */
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	/** 设置采购数量 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	/** 获取采购小计 */
	public BigDecimal getSub_total() {
		return sub_total;
	}
	
	/** 设置采购小计 */
	public void setSub_total(BigDecimal sub_total) {
		this.sub_total = sub_total;
	}
	
	public String getBuy_rule() {
		return buy_rule;
	}
	
	public void setBuy_rule(String buy_rule) {
		this.buy_rule = buy_rule;
	}
	
	public BigDecimal getStock_sum() {
		return stock_sum;
	}
	
	public void setStock_sum(BigDecimal stock_sum) {
		this.stock_sum = stock_sum;
	}
	
	//----------结束vo的setter和getter方法----------
}