package com.deertt.module.sys.warehouse.vo;

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
public class WarehouseVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市
	private String city_name;
	
	//管理员id
	private Integer manager_id;
	
	//管理员
	private String manager_name;
	
	//货仓名称
	private String warehouse_name;
	
	//货仓描述
	private String warehouse_desc;
	
	//配送区域说明
	private String warehouse_area;
	
	//起送价
	private BigDecimal start_amount;
	
	//账户金额
	private BigDecimal balance_amount;
	
	//待提款金额
	private BigDecimal locked_amount;
	
	//待收款金额
	private BigDecimal halfway_amount;
	
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
	
	/** 获取管理员id */
	public Integer getManager_id() {
		return manager_id;
	}
	
	/** 设置管理员id */
	public void setManager_id(Integer manager_id) {
		this.manager_id = manager_id;
	}
	
	/** 获取管理员 */
	public String getManager_name() {
		return manager_name;
	}
	
	/** 设置管理员 */
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	
	/** 获取货仓名称 */
	public String getWarehouse_name() {
		return warehouse_name;
	}
	
	/** 设置货仓名称 */
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	
	/** 获取货仓描述 */
	public String getWarehouse_desc() {
		return warehouse_desc;
	}
	
	/** 设置货仓描述 */
	public void setWarehouse_desc(String warehouse_desc) {
		this.warehouse_desc = warehouse_desc;
	}
	
	/** 获取配送区域说明 */
	public String getWarehouse_area() {
		return warehouse_area;
	}
	
	/** 设置配送区域说明 */
	public void setWarehouse_area(String warehouse_area) {
		this.warehouse_area = warehouse_area;
	}
	
	/** 获取起送价 */
	public BigDecimal getStart_amount() {
		return start_amount;
	}
	
	/** 设置起送价 */
	public void setStart_amount(BigDecimal start_amount) {
		this.start_amount = start_amount;
	}
	
	/** 获取账户金额 */
	public BigDecimal getBalance_amount() {
		return balance_amount;
	}
	
	/** 设置账户金额 */
	public void setBalance_amount(BigDecimal balance_amount) {
		this.balance_amount = balance_amount;
	}
	
	/** 获取待提款金额 */
	public BigDecimal getLocked_amount() {
		return locked_amount;
	}
	
	/** 设置待提款金额 */
	public void setLocked_amount(BigDecimal locked_amount) {
		this.locked_amount = locked_amount;
	}
	
	/** 获取待收款金额 */
	public BigDecimal getHalfway_amount() {
		return halfway_amount;
	}
	
	/** 设置待收款金额 */
	public void setHalfway_amount(BigDecimal halfway_amount) {
		this.halfway_amount = halfway_amount;
	}
	
	//----------结束vo的setter和getter方法----------
}