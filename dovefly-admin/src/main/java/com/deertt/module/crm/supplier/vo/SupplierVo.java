package com.deertt.module.crm.supplier.vo;

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
/**
 * @author deertt-admin
 *
 */
public class SupplierVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市名称
	private String city_name;
	
	//供应商
	private String name;
	
	//公司邮箱
	private String email;
	
	//公司电话
	private String tel;
	
	//公司地址
	private String address;
	
	//联系人
	private String linkman;
	
	//联系人电话
	private String linkman_mobile;
	
	//备注
	private String remark;
	
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
	
	/** 获取城市id */
	public Integer getCity_id() {
		return city_id;
	}
	
	/** 设置城市id */
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	/** 获取城市名称 */
	public String getCity_name() {
		return city_name;
	}
	
	/** 设置城市名称 */
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	/** 获取供应商 */
	public String getName() {
		return name;
	}
	
	/** 设置供应商 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 获取公司邮箱 */
	public String getEmail() {
		return email;
	}
	
	/** 设置公司邮箱 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/** 获取公司电话 */
	public String getTel() {
		return tel;
	}
	
	/** 设置公司电话 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	/** 获取公司地址 */
	public String getAddress() {
		return address;
	}
	
	/** 设置公司地址 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/** 获取联系人 */
	public String getLinkman() {
		return linkman;
	}
	
	/** 设置联系人 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	/** 获取联系人电话 */
	public String getLinkman_mobile() {
		return linkman_mobile;
	}
	
	/** 设置联系人电话 */
	public void setLinkman_mobile(String linkman_mobile) {
		this.linkman_mobile = linkman_mobile;
	}
	
	/** 获取备注 */
	public String getRemark() {
		return remark;
	}
	
	/** 设置备注 */
	public void setRemark(String remark) {
		this.remark = remark;
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