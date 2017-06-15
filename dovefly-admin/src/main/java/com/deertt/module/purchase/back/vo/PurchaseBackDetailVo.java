package com.deertt.module.purchase.back.vo;

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
public class PurchaseBackDetailVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//采购单id
	private Integer bill_id;
	
	//商品id
	private Integer goods_id;
	
	//商品名称
	private String goods_name;
	
	//商品图
	private String goods_image;
	
	//单价
	private BigDecimal unit_price;
	
	//数量
	private BigDecimal quantity;
	
	//小计
	private BigDecimal sub_total;
	
	//商品数量规格
	private BigDecimal  spec;
	
	//采购大包装数量
	private  BigDecimal  spec_quantity;
	
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
	
	/** 获取采购单id */
	public Integer getBill_id() {
		return bill_id;
	}
	
	/** 设置采购单id */
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
	
	/** 获取商品图 */
	public String getGoods_image() {
		return goods_image;
	}
	
	/** 设置商品图 */
	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}
	
	/** 获取单价 */
	public BigDecimal getUnit_price() {
		return unit_price;
	}
	
	/** 设置单价 */
	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}
	
	/** 获取数量 */
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	/** 设置数量 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	/** 获取小计 */
	public BigDecimal getSub_total() {
		return sub_total;
	}
	
	/** 设置小计 */
	public void setSub_total(BigDecimal sub_total) {
		this.sub_total = sub_total;
	}
	
	/** 获取规格 */
	public BigDecimal getSpec() {
		return spec;
	}
	
	/** 设置规格     */
	public void setSpec(BigDecimal spec) {
		this.spec = spec;
	}
	
	/** 设置采购大包装数量     */
	public BigDecimal getSpec_quantity() {
		return spec_quantity;
	}
	
	/** 获取采购大包装数量     */
	public void setSpec_quantity(BigDecimal spec_quantity) {
		this.spec_quantity = spec_quantity;
	}
	
	//----------结束vo的setter和getter方法----------
}