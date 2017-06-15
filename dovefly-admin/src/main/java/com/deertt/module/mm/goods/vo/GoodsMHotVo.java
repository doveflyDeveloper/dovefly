package com.deertt.module.mm.goods.vo;

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
public class GoodsMHotVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市
	private String city_name;
	
	//拥有人id
	private Integer market_id;
	
	//拥有人
	private String market_name;
	
	//商品ID
	private Integer goods_id;
	
	//商品名称
	private String goods_name;
	
	//缩略图
	private String goods_image;
	
	//成本价
	private BigDecimal cost_price;
	
	//销售价
	private BigDecimal sale_price;
	
	//市场价
	private BigDecimal market_price;
	
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
	
	/** 获取拥有人id */
	public Integer getMarket_id() {
		return market_id;
	}
	
	/** 设置拥有人id */
	public void setMarket_id(Integer market_id) {
		this.market_id = market_id;
	}
	
	/** 获取拥有人 */
	public String getMarket_name() {
		return market_name;
	}
	
	/** 设置拥有人 */
	public void setMarket_name(String market_name) {
		this.market_name = market_name;
	}
	
	/** 获取商品ID */
	public Integer getGoods_id() {
		return goods_id;
	}
	
	/** 设置商品ID */
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
	
	/** 获取缩略图 */
	public String getGoods_image() {
		return goods_image;
	}
	
	/** 设置缩略图 */
	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}
	
	/** 获取成本价 */
	public BigDecimal getCost_price() {
		return cost_price;
	}
	
	/** 设置成本价 */
	public void setCost_price(BigDecimal cost_price) {
		this.cost_price = cost_price;
	}
	
	/** 获取销售价 */
	public BigDecimal getSale_price() {
		return sale_price;
	}
	
	/** 设置销售价 */
	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}
	
	/** 获取市场价 */
	public BigDecimal getMarket_price() {
		return market_price;
	}
	
	/** 设置市场价 */
	public void setMarket_price(BigDecimal market_price) {
		this.market_price = market_price;
	}
	
	//----------结束vo的setter和getter方法----------
}