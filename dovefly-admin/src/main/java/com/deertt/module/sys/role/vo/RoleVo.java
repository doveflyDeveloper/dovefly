package com.deertt.module.sys.role.vo;

import java.sql.Timestamp;
import java.util.List;

import com.deertt.frame.base.vo.DvBaseVo;
import com.deertt.module.sys.resource.vo.ResourceVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RoleVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final Integer ROLE_SUPER_ADMIN = 15;//超级管理员
	
	public static final Integer ROLE_HEADQUARTERS = 1;//总部人员
	
	public static final Integer ROLE_CITY_MANAGER = 7;//城市经理
	
	public static final Integer ROLE_OPERATION_MANAGER = 17;//营运主管
	
	public static final Integer ROLE_MARKET_SELLER = 18;//超市商家
	
	public static final Integer ROLE_CASHIER = 16;//财务出纳
	
	public static final Integer ROLE_SHOPKEEPER = 12;//店长
	
//	public static final Integer ROLE_CUSTOMER = 0;//消费者
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//角色名称
	private String name;
	
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
	
	// 选中状态
	private boolean checked;
	
	// 访问资源
	private List<ResourceVo> resources;
	
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
	
	/** 获取角色名称 */
	public String getName() {
		return name;
	}
	
	/** 设置角色名称 */
	public void setName(String name) {
		this.name = name;
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
	
	/** 设置选中状态 */
	public boolean isChecked() {
		return checked;
	}

	/** 设置选中状态 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/** 获得访问资源 */
	public List<ResourceVo> getResources() {
		return resources;
	}

	/** 设置访问资源 */
	public void setResources(List<ResourceVo> resources) {
		this.resources = resources;
	}
	//----------结束vo的setter和getter方法----------
}