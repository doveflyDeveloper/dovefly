package com.deertt.module.mm.statistics.vo;

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
public class StatisticsDetailVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//结算单id
	private Integer bill_id;
	
	//商品id
	private Integer goods_id;
	
	//商品名称
	private String goods_name;
	
	//图片
	private String goods_image;
	
	//期初库存量
	private BigDecimal origin_quantity;
	
	//期初库存金额
	private BigDecimal origin_amount;
	
	//进货数量
	private BigDecimal purchase_quantity;
	
	//进货金额
	private BigDecimal purchase_amount;
	
	private BigDecimal purchase_back_quantity;
	
	private BigDecimal purchase_back_amount;
	
	//售出数量
	private BigDecimal order_quantity;
	
	//售出金额
	private BigDecimal order_amount;
	
	private BigDecimal order_back_quantity;
	
	private BigDecimal order_back_amount;
	
	private BigDecimal out_quantity;
	
	private BigDecimal out_amount;
	
	//库存数量
	private BigDecimal stock_quantity;
	
	//库存金额
	private BigDecimal stock_amount;
	
	//盘点数量
	private BigDecimal check_quantity;
	
	//盘点总金额
	private BigDecimal check_amount;
	
	//差异数量
	private BigDecimal dif_quantity;
	
	//差异金额
	private BigDecimal dif_amount;
	
	//结转库存数量
	private BigDecimal final_quantity;
	
	//结转库存金额
	private BigDecimal final_amount;
	
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
	
	/** 获取结算单id */
	public Integer getBill_id() {
		return bill_id;
	}
	
	/** 设置结算单id */
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
	
	/** 获取期初库存量 */
	public BigDecimal getOrigin_quantity() {
		return origin_quantity;
	}
	
	/** 设置期初库存量 */
	public void setOrigin_quantity(BigDecimal original_quantity) {
		this.origin_quantity = original_quantity;
	}
	
	/** 获取期初库存金额 */
	public BigDecimal getOrigin_amount() {
		return origin_amount;
	}
	
	/** 设置期初库存金额 */
	public void setOrigin_amount(BigDecimal original_amount) {
		this.origin_amount = original_amount;
	}
	
	/** 获取进货数量 */
	public BigDecimal getPurchase_quantity() {
		return purchase_quantity;
	}
	
	/** 设置进货数量 */
	public void setPurchase_quantity(BigDecimal in_quantity) {
		this.purchase_quantity = in_quantity;
	}
	
	/** 获取进货金额 */
	public BigDecimal getPurchase_amount() {
		return purchase_amount;
	}
	
	/** 设置进货金额 */
	public void setPurchase_amount(BigDecimal in_amount) {
		this.purchase_amount = in_amount;
	}
	
	public BigDecimal getPurchase_back_quantity() {
		return purchase_back_quantity;
	}

	public void setPurchase_back_quantity(BigDecimal purchase_back_quantity) {
		this.purchase_back_quantity = purchase_back_quantity;
	}

	public BigDecimal getPurchase_back_amount() {
		return purchase_back_amount;
	}

	public void setPurchase_back_amount(BigDecimal purchase_back_amount) {
		this.purchase_back_amount = purchase_back_amount;
	}
	
	/** 获取售出数量 */
	public BigDecimal getOrder_quantity() {
		return order_quantity;
	}
	
	/** 设置售出数量 */
	public void setOrder_quantity(BigDecimal out_quantity) {
		this.order_quantity = out_quantity;
	}
	
	/** 获取售出金额 */
	public BigDecimal getOrder_amount() {
		return order_amount;
	}
	
	/** 设置售出金额 */
	public void setOrder_amount(BigDecimal out_amount) {
		this.order_amount = out_amount;
	}
	
	public BigDecimal getOrder_back_quantity() {
		return order_back_quantity;
	}

	public void setOrder_back_quantity(BigDecimal order_back_quantity) {
		this.order_back_quantity = order_back_quantity;
	}

	public BigDecimal getOrder_back_amount() {
		return order_back_amount;
	}

	public void setOrder_back_amount(BigDecimal order_back_amount) {
		this.order_back_amount = order_back_amount;
	}

	public BigDecimal getOut_quantity() {
		return out_quantity;
	}

	public void setOut_quantity(BigDecimal out_quantity) {
		this.out_quantity = out_quantity;
	}

	public BigDecimal getOut_amount() {
		return out_amount;
	}

	public void setOut_amount(BigDecimal out_amount) {
		this.out_amount = out_amount;
	}
	
	/** 获取库存数量 */
	public BigDecimal getStock_quantity() {
		return stock_quantity;
	}
	
	/** 设置库存数量 */
	public void setStock_quantity(BigDecimal stock_quantity) {
		this.stock_quantity = stock_quantity;
	}
	
	/** 获取库存金额 */
	public BigDecimal getStock_amount() {
		return stock_amount;
	}
	
	/** 设置库存金额 */
	public void setStock_amount(BigDecimal stock_amount) {
		this.stock_amount = stock_amount;
	}
	
	/** 获取盘点数量 */
	public BigDecimal getCheck_quantity() {
		return check_quantity;
	}
	
	/** 设置盘点数量 */
	public void setCheck_quantity(BigDecimal check_quantity) {
		this.check_quantity = check_quantity;
	}
	
	/** 获取盘点总金额 */
	public BigDecimal getCheck_amount() {
		return check_amount;
	}
	
	/** 设置盘点总金额 */
	public void setCheck_amount(BigDecimal check_amount) {
		this.check_amount = check_amount;
	}
	
	/** 获取差异数量 */
	public BigDecimal getDif_quantity() {
		return dif_quantity;
	}
	
	/** 设置差异数量 */
	public void setDif_quantity(BigDecimal dif_quantity) {
		this.dif_quantity = dif_quantity;
	}
	
	/** 获取差异金额 */
	public BigDecimal getDif_amount() {
		return dif_amount;
	}
	
	/** 设置差异金额 */
	public void setDif_amount(BigDecimal dif_amount) {
		this.dif_amount = dif_amount;
	}
	
	/** 获取结转库存数量 */
	public BigDecimal getFinal_quantity() {
		return final_quantity;
	}
	
	/** 设置结转库存数量 */
	public void setFinal_quantity(BigDecimal final_quantity) {
		this.final_quantity = final_quantity;
	}
	
	/** 获取结转库存金额 */
	public BigDecimal getFinal_amount() {
		return final_amount;
	}
	
	/** 设置结转库存金额 */
	public void setFinal_amount(BigDecimal final_amount) {
		this.final_amount = final_amount;
	}
	
	//----------结束vo的setter和getter方法----------
}