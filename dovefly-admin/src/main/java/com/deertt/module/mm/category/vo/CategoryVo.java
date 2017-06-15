package com.deertt.module.mm.category.vo;

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
public class CategoryVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//分类编号
	private String code;
	
	//分类名称
	private String name;
	
	//上级分类id
	private Integer parent_id;
	
	//上级分类编号
	private String parent_code;
	
	//上级分类名称
	private String parent_name;
	
	//层级深度
	private Integer level;
	
	//是否叶子节点
	private String is_leaf;
	
	//备注
	private String remark;
	
	//排序
	private Integer sort;
	
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
	
	/** 获取分类编号 */
	public String getCode() {
		return code;
	}
	
	/** 设置分类编号 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/** 获取分类名称 */
	public String getName() {
		return name;
	}
	
	/** 设置分类名称 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 获取上级分类id */
	public Integer getParent_id() {
		return parent_id;
	}
	
	/** 设置上级分类id */
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	
	/** 获取上级分类编号 */
	public String getParent_code() {
		return parent_code;
	}
	
	/** 设置上级分类编号 */
	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}
	
	/** 获取上级分类名称 */
	public String getParent_name() {
		return parent_name;
	}
	
	/** 设置上级分类名称 */
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	
	/** 获取层级深度 */
	public Integer getLevel() {
		return level;
	}
	
	/** 设置层级深度 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	/** 获取是否叶子节点 */
	public String getIs_leaf() {
		return is_leaf;
	}
	
	/** 设置是否叶子节点 */
	public void setIs_leaf(String is_leaf) {
		this.is_leaf = is_leaf;
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