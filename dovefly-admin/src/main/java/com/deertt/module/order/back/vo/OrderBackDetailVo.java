package com.deertt.module.order.back.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class OrderBackDetailVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//退货单id
	private Integer bill_id;
	
	//原商品id
	private Integer origin_id;
	
	//商品id
	private Integer goods_id;
	
	//商品名称
	private String goods_name;
	
	//商品图片
	private String goods_image;
	
	//售价
	private BigDecimal unit_price;
	
	//退货数量
	private BigDecimal quantity;
	
	//退货小计
	private BigDecimal sub_total;
	
	//状态 d:已删除,t:启用,f:停用
	private String status;
	
	//创建人
	private Integer create_by;
	
	//创建时间
	private Timestamp create_at;
	
	//修改人
	private Integer modify_by;
	
	//修改时间
	private Timestamp modify_at;
	
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
	
	/** 获取退货单id */
	public Integer getBill_id() {
		return bill_id;
	}
	
	/** 设置退货单id */
	public void setBill_id(Integer bill_id) {
		this.bill_id = bill_id;
	}

	public Integer getOrigin_id() {
		return origin_id;
	}

	public void setOrigin_id(Integer origin_id) {
		this.origin_id = origin_id;
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
	public BigDecimal getUnit_price() {
		return unit_price;
	}
	
	/** 设置售价 */
	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}
	
	/** 获取退货数量 */
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	/** 设置退货数量 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	/** 获取退货小计 */
	public BigDecimal getSub_total() {
		return sub_total;
	}
	
	/** 设置退货小计 */
	public void setSub_total(BigDecimal sub_total) {
		this.sub_total = sub_total;
	}
	
	/** 获取状态 d:已删除,t:启用,f:停用 */
	public String getStatus() {
		return status;
	}
	
	/** 设置状态 d:已删除,t:启用,f:停用 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/** 获取创建人 */
	public Integer getCreate_by() {
		return create_by;
	}
	
	/** 设置创建人 */
	public void setCreate_by(Integer create_by) {
		this.create_by = create_by;
	}
	
	/** 获取创建时间 */
	public Timestamp getCreate_at() {
		return create_at;
	}
	
	/** 设置创建时间 */
	public void setCreate_at(Timestamp create_at) {
		this.create_at = create_at;
	}
	
	/** 获取修改人 */
	public Integer getModify_by() {
		return modify_by;
	}
	
	/** 设置修改人 */
	public void setModify_by(Integer modify_by) {
		this.modify_by = modify_by;
	}
	
	/** 获取修改时间 */
	public Timestamp getModify_at() {
		return modify_at;
	}
	
	/** 设置修改时间 */
	public void setModify_at(Timestamp modify_at) {
		this.modify_at = modify_at;
	}
	
	//----------结束vo的setter和getter方法----------
}