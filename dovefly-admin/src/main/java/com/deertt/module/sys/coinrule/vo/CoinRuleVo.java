package com.deertt.module.sys.coinrule.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
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
public class CoinRuleVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市
	private String city_name;
	
	//发行单位类型：w-货仓，s-店铺，m-超市
	private String store_type;
	
	//发行单位id
	private Integer store_id;
	
	//发行单位
	private String store_name;
	
	//使用场景
	private String use_scene;
	
	//支付方：platform-平台，personal-个人
	private String who_pay;
	
	//单笔限用金额
	private BigDecimal reach_amount;
	
	//单笔限用汀豆数量
	private Integer limit_quantity;
	
	//返还汀豆
	private Integer send_quantity;
	
	//活动开始时间
	private Timestamp start_time;
	
	//活动结束时间
	private Timestamp end_time;
	
	//备注
	private String remark;
	
	//排序
	private Integer sort;
	
	//状态：1-有效，0-无效
	private String status;
	
	//创建人员
	private Integer create_by;
	
	//创建时间
	private Timestamp create_at;
	
	//修改人员
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
	
	/** 获取发行单位类型：w-货仓，s-店铺，m-超市 */
	public String getStore_type() {
		return store_type;
	}
	
	/** 设置发行单位类型：w-货仓，s-店铺，m-超市 */
	public void setStore_type(String store_type) {
		this.store_type = store_type;
	}
	
	/** 获取发行单位id */
	public Integer getStore_id() {
		return store_id;
	}
	
	/** 设置发行单位id */
	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}
	
	/** 获取发行单位 */
	public String getStore_name() {
		return store_name;
	}
	
	/** 设置发行单位 */
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	
	/** 获取使用场景 */
	public String getUse_scene() {
		return use_scene;
	}
	
	/** 设置使用场景 */
	public void setUse_scene(String use_scene) {
		this.use_scene = use_scene;
	}
	
	/** 获取支付方：platform-平台，personal-个人 */
	public String getWho_pay() {
		return who_pay;
	}
	
	/** 设置支付方：platform-平台，personal-个人 */
	public void setWho_pay(String who_pay) {
		this.who_pay = who_pay;
	}
	
	/** 获取单笔限用金额 */
	public BigDecimal getReach_amount() {
		return reach_amount;
	}
	
	/** 设置单笔限用金额 */
	public void setReach_amount(BigDecimal reach_amount) {
		this.reach_amount = reach_amount;
	}
	
	/** 获取单笔限用汀豆数量 */
	public Integer getLimit_quantity() {
		return limit_quantity;
	}
	
	/** 设置单笔限用汀豆数量 */
	public void setLimit_quantity(Integer limit_quantity) {
		this.limit_quantity = limit_quantity;
	}
	
	/** 获取返还汀豆 */
	public Integer getSend_quantity() {
		return send_quantity;
	}
	
	/** 设置返还汀豆 */
	public void setSend_quantity(Integer send_quantity) {
		this.send_quantity = send_quantity;
	}
	
	/** 获取活动开始时间 */
	public Timestamp getStart_time() {
		return start_time;
	}
	
	/** 设置活动开始时间 */
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	
	/** 获取活动结束时间 */
	public Timestamp getEnd_time() {
		return end_time;
	}
	
	/** 设置活动结束时间 */
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	
	/** 获取备注 */
	public String getRemark() {
		return remark;
	}
	
	/** 设置备注 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/** 获取排序 */
	public Integer getSort() {
		return sort;
	}
	
	/** 设置排序 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	/** 获取状态：1-有效，0-无效 */
	public String getStatus() {
		return status;
	}
	
	/** 设置状态：1-有效，0-无效 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/** 获取创建人员 */
	public Integer getCreate_by() {
		return create_by;
	}
	
	/** 设置创建人员 */
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
	
	/** 获取修改人员 */
	public Integer getModify_by() {
		return modify_by;
	}
	
	/** 设置修改人员 */
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