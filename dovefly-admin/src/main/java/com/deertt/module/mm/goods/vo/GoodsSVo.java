package com.deertt.module.mm.goods.vo;


/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class GoodsSVo extends GoodsVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	private Integer origin_id;
	
	private Integer shop_id;
	
	private String shop_name;
	
	public Integer getOrigin_id() {
		return origin_id;
	}
	
	public void setOrigin_id(Integer origin_id) {
		this.origin_id = origin_id;
	}

	public Integer getShop_id() {
		return shop_id;
	}

	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	
	//----------结束vo的setter和getter方法----------
}