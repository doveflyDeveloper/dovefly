package com.deertt.module.mm.goods.vo;


/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class GoodsWVo extends GoodsVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	
	private Integer warehouse_id;
	
	private String warehouse_name;
	
	public Integer getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(Integer warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	public String getWarehouse_name() {
		return warehouse_name;
	}

	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	
	//----------结束vo的setter和getter方法----------
}