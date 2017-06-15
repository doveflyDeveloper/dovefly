package com.deertt.module.sys.shop.vo;

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
public class ShopVo extends DvBaseVo {
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
	
	//学校id
	private Integer school_id;
	
	//学校
	private String school_name;
	
	//货源仓库id
	private Integer warehouse_id;
	
	//货源仓库
	private String warehouse_name;
	
	//运营主管id
	private Integer manager_id;
	
	//运营主管
	private String manager_name;
	
	//店长id
	private Integer shopkeeper_id;
	
	//店长
	private String shopkeeper_name;
	
	//店铺名称
	private String shop_name;
	
	//店铺头像
	private String shop_logo;
	
	//店铺描述
	private String shop_desc;
	
	//配送区域说明
	private String shop_area;
	
	//开店状态：n-未开店,o-已开通,w-营业中,s-休息中,c-已关店
	private String shop_status;
	
	//店铺排序
	private Integer shop_sort;
	
	//起送价
	private BigDecimal start_amount;
	
	//账号余额
	private BigDecimal balance_amount;
	
	//冻结资金
	private BigDecimal locked_amount;
	
	//待收金额
	private BigDecimal halfway_amount;
	
	//可贷款额度
	private BigDecimal loanable_amount;
	
	//已贷款金额
	private BigDecimal loan_amount;
	
	//当前利息金额
	private BigDecimal interest_amount; 
	
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
	
	/** 获取学校id */
	public Integer getSchool_id() {
		return school_id;
	}
	
	/** 设置学校id */
	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}
	
	/** 获取学校 */
	public String getSchool_name() {
		return school_name;
	}
	
	/** 设置学校 */
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	
	/** 获取货源仓库id */
	public Integer getWarehouse_id() {
		return warehouse_id;
	}
	
	/** 设置货源仓库id */
	public void setWarehouse_id(Integer warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	
	/** 获取货源仓库 */
	public String getWarehouse_name() {
		return warehouse_name;
	}
	
	/** 设置货源仓库 */
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	
	/** 获取运营主管id */
	public Integer getManager_id() {
		return manager_id;
	}
	
	/** 设置运营主管id */
	public void setManager_id(Integer manager_id) {
		this.manager_id = manager_id;
	}
	
	/** 获取运营主管 */
	public String getManager_name() {
		return manager_name;
	}
	
	/** 设置运营主管 */
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
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
	
	/** 获取店铺名称 */
	public String getShop_name() {
		return shop_name;
	}
	
	/** 设置店铺名称 */
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	
	/** 获取店铺头像 */
	public String getShop_logo() {
		return shop_logo;
	}
	
	/** 设置店铺头像 */
	public void setShop_logo(String shop_logo) {
		this.shop_logo = shop_logo;
	}
	
	/** 获取店铺描述 */
	public String getShop_desc() {
		return shop_desc;
	}
	
	/** 设置店铺描述 */
	public void setShop_desc(String shop_desc) {
		this.shop_desc = shop_desc;
	}
	
	/** 获取配送区域说明 */
	public String getShop_area() {
		return shop_area;
	}
	
	/** 设置配送区域说明 */
	public void setShop_area(String shop_area) {
		this.shop_area = shop_area;
	}
	
	/** 获取开店状态：n-未开店,o-已开通,w-营业中,s-休息中,c-已关店 */
	public String getShop_status() {
		return shop_status;
	}
	
	/** 设置开店状态：n-未开店,o-已开通,w-营业中,s-休息中,c-已关店 */
	public void setShop_status(String shop_status) {
		this.shop_status = shop_status;
	}
	
	/** 获取店铺排序 */
	public Integer getShop_sort() {
		return shop_sort;
	}
	
	/** 设置店铺排序 */
	public void setShop_sort(Integer shop_sort) {
		this.shop_sort = shop_sort;
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

	public BigDecimal getLoanable_amount() {
		return loanable_amount;
	}

	public void setLoanable_amount(BigDecimal loanable_amount) {
		this.loanable_amount = loanable_amount;
	}

	public BigDecimal getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(BigDecimal loan_amount) {
		this.loan_amount = loan_amount;
	}

	public BigDecimal getInterest_amount() {
		return interest_amount;
	}

	public void setInterest_amount(BigDecimal interest_amount) {
		this.interest_amount = interest_amount;
	}
	
	//----------结束vo的setter和getter方法----------
}