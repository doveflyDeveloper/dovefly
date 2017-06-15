package com.deertt.module.sys.dict.data.vo;

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
public class DictDataVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//类型id
	private Integer type_id;
	
	//字典key
	private String dic_key;
	
	//字典value
	private String dic_value;
	
	//备注
	private String remark;
	
	//排序
	private Integer sort;
	
	//状态 d:已删除, 1：启用  2：禁用
	private String status;
	
	//创建人
	private Integer create_by;
	
	//创建时间
	private Timestamp create_at;
	
	//修改人
	private Integer modify_by;
	
	//修改时间
	private Timestamp modify_at;
	
	//标识关键词
	private String keyword;
	
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
	
	/** 获取类型id */
	public Integer getType_id() {
		return type_id;
	}
	
	/** 设置类型id */
	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}
	
	/** 获取字典key */
	public String getDic_key() {
		return dic_key;
	}
	
	/** 设置字典key */
	public void setDic_key(String dic_key) {
		this.dic_key = dic_key;
	}
	
	/** 获取字典value */
	public String getDic_value() {
		return dic_value;
	}
	
	/** 设置字典value */
	public void setDic_value(String dic_value) {
		this.dic_value = dic_value;
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
	
	/** 获取状态 d:已删除, 1：启用  2：禁用 */
	public String getStatus() {
		return status;
	}
	
	/** 设置状态 d:已删除, 1：启用  2：禁用 */
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
	
	/** 获取标识关键词 */
	public String getKeyword() {
		return keyword;
	}
	
	/** 设置标识关键词 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	//----------结束vo的setter和getter方法----------
}