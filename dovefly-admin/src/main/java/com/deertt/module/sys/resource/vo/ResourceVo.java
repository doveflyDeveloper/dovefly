package com.deertt.module.sys.resource.vo;

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
public class ResourceVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//资源编号
	private String code;
	
	//资源名称
	private String name;
	
	//父级id
	private Integer parent_id;
	
	//父级资源编号
	private String parent_code;
	
	//父级资源名称
	private String parent_name;
	
	//资源类型
	private String type;
	
	//资源地址
	private String url;
	
	//资源权限
	private String permission;
	
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
	
	//选中状态
	private boolean checked;
	
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
	
	/** 获取资源编号 */
	public String getCode() {
		return code;
	}
	
	/** 设置资源编号 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/** 获取资源名称 */
	public String getName() {
		return name;
	}
	
	/** 设置资源名称 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 获取父级id */
	public Integer getParent_id() {
		return parent_id;
	}
	
	/** 设置父级id */
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	
	/** 获取父级资源编号 */
	public String getParent_code() {
		return parent_code;
	}
	
	/** 设置父级资源编号 */
	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}
	
	/** 获取父级资源名称 */
	public String getParent_name() {
		return parent_name;
	}
	
	/** 设置父级资源名称 */
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	
	/** 获取资源类型 */
	public String getType() {
		return type;
	}
	
	/** 设置资源类型 */
	public void setType(String type) {
		this.type = type;
	}
	
	/** 获取资源地址 */
	public String getUrl() {
		return url;
	}
	
	/** 设置资源地址 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/** 获取资源权限 */
	public String getPermission() {
		return permission;
	}
	
	/** 设置资源权限 */
	public void setPermission(String permission) {
		this.permission = permission;
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
	
	/** 设置选中状态 */
	public boolean isChecked() {
		return checked;
	}
	
	/** 设置选中状态 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	//----------结束vo的setter和getter方法----------
}