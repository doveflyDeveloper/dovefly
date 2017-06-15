package com.deertt.module.mm.goods.vo;


/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class GoodsMVo extends GoodsVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	
	private Integer market_id;
	
	private String market_name;
	
	public Integer getMarket_id() {
		return market_id;
	}

	public void setMarket_id(Integer market_id) {
		this.market_id = market_id;
	}

	public String getMarket_name() {
		return market_name;
	}

	public void setMarket_name(String market_name) {
		this.market_name = market_name;
	}
	
	//----------结束vo的setter和getter方法----------
}