package com.deertt.module.sys.help.vo;

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
public class HelpVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//模块
	private String module;
	
	//功能
	private String operation;
	
	//内容
	private String description;
	
	//排序
	private Integer sort;
	
	//状态
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
	
	/** 获取模块 */
	public String getModule() {
		return module;
	}
	
	/** 设置模块 */
	public void setModule(String module) {
		this.module = module;
	}
	
	/** 获取功能 */
	public String getOperation() {
		return operation;
	}
	
	/** 设置功能 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	/** 获取内容 */
	public String getDescription() {
		return description;
	}
	
	/** 设置内容 */
	public void setDescription(String description) {
		this.description = description;
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