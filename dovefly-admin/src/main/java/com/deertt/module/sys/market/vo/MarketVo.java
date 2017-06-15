package com.deertt.module.sys.market.vo;

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
public class MarketVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String WORK_STATUS_N = "n";//未开张
	public static final String WORK_STATUS_W = "w";//营业中
	public static final String WORK_STATUS_S = "s";//休息中
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市
	private String city_name;
	
	//店长id
	private Integer shopkeeper_id;
	
	//店长
	private String shopkeeper_name;
	
	private String market_name;
	
	//店铺描述
	private String market_desc;
	
	//配送区域说明
	private String market_area;
	
	//开店状态：n-未开店,o-已开通,w-营业中,s-休息中,c-已关店
	private String market_status;
	
	//店铺排序
	private Integer market_sort;
	
	//起送价
	private BigDecimal start_amount;
	
	//账号余额
	private BigDecimal balance_amount;
	
	//冻结资金
	private BigDecimal locked_amount;
	
	//待收金额
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
	
	/** 获取店长id */
	public Integer getShopkeeper_id() {
		return shopkeeper_id;
	}
	
	/** 设置店长id */
	public void setShopkeeper_id(Integer shopkeeper_id) {
		this.shopkeeper_id = shopkeeper_id;
	}
	
	/** 获取店长 */
	public String getShopkeeper_name() {
		return shopkeeper_name;
	}
	
	/** 设置店长 */
	public void setShopkeeper_name(String shopkeeper_name) {
		this.shopkeeper_name = shopkeeper_name;
	}
	
	public String getMarket_name() {
		return market_name;
	}
	
	public void setMarket_name(String market_name) {
		this.market_name = market_name;
	}
	
	public String getMarket_desc() {
		return market_desc;
	}
	
	public void setMarket_desc(String market_desc) {
		this.market_desc = market_desc;
	}
	
	public String getMarket_area() {
		return market_area;
	}
	
	public void setMarket_area(String market_area) {
		this.market_area = market_area;
	}
	
	public String getMarket_status() {
		return market_status;
	}
	
	public void setMarket_status(String market_status) {
		this.market_status = market_status;
	}
	
	public Integer getMarket_sort() {
		return market_sort;
	}
	
	public void setMarket_sort(Integer market_sort) {
		this.market_sort = market_sort;
	}
	
	/** 获取起送价 */
	public BigDecimal getStart_amount() {
		return start_amount;
	}
	
	/** 设置起送价 */
	public void setStart_amount(BigDecimal start_amount) {
		this.start_amount = start_amount;
	}
	
	/** 获取账号余额 */
	public BigDecimal getBalance_amount() {
		return balance_amount;
	}
	
	/** 设置账号余额 */
	public void setBalance_amount(BigDecimal balance_amount) {
		this.balance_amount = balance_amount;
	}
	
	/** 获取冻结资金 */
	public BigDecimal getLocked_amount() {
		return locked_amount;
	}
	
	/** 设置冻结资金 */
	public void setLocked_amount(BigDecimal locked_amount) {
		this.locked_amount = locked_amount;
	}
	
	/** 获取待收金额 */
	public BigDecimal getHalfway_amount() {
		return halfway_amount;
	}
	
	/** 设置待收金额 */
	public void setHalfway_amount(BigDecimal halfway_amount) {
		this.halfway_amount = halfway_amount;
	}
	
	//----------结束vo的setter和getter方法----------
}