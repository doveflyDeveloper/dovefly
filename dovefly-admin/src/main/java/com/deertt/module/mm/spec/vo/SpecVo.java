package com.deertt.module.mm.spec.vo;

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
public class SpecVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//规格名称
	private String name;
	
	//对等数量
	private BigDecimal quantity;
	
	//备注
	private String remark;
	
	//排序
	private Integer sort;
	
	//状态
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
	
	/** 获取规格名称 */
	public String getName() {
		return name;
	}
	
	/** 设置规格名称 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 获取对等数量 */
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	/** 设置对等数量 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
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
	
	/** 获取状态 */
	public String getStatus() {
		return status;
	}
	
	/** 设置状态 */
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