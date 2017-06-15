package com.deertt.module.sys.region.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RegionVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	private Integer city_id;
	
	private String city_name;
	
	private Integer warehouse_id;
	
	private String warehouse_name;
	
	private Integer manager_id;
	
	private String manager_name;
	
	//区域编号
	private String code;
	
	//区域名称
	private String name;
	
	//区域全称
	private String full_name;
	
	//上级区域id
	private Integer parent_id;
	
	//上级区域编号
	private String parent_code;
	
	//上级区域名称
	private String parent_name;
	
	//上级区域全称
	private String parent_full_name;
	
	//区域层级
	private Integer level;
	
	//是否末级区域
	private String is_leaf;
	
	//备注
	private String remark;
	
	private String baidu_uid;
	
	private String baidu_title;
	
	private String baidu_address;
	
	private Double baidu_longtitude;
	
	private Double baidu_latitude;
	
	private Integer search_times;
	
	//排序编码
	private Integer sort;
	
	//可用状态
	private String status;
	
	//创建人员
	private Integer create_by;
	
	//创建时间
	private Timestamp create_at;
	
	//修改人员
	private Integer modify_by;
	
	//修改时间
	private Timestamp modify_at;
	
	private List<RegionVo> children = new ArrayList<RegionVo>();
	
	public List<RegionVo> getChildren() {
		return children;
	}
	public void setChildren(List<RegionVo> children) {
		this.children = children;
	}
	
	public void addChildren(RegionVo children) {
		this.children.add(children);
	}
	
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
	
	public Integer getCity_id() {
		return city_id;
	}
	
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	public String getCity_name() {
		return city_name;
	}
	
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	public Integer getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(Integer warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	public String getWarehouse_name() {
		return warehouse_name;
	}

	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}

	public Integer getManager_id() {
		return manager_id;
	}

	public void setManager_id(Integer manager_id) {
		this.manager_id = manager_id;
	}

	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	
	/** 获取区域编号 */
	public String getCode() {
		return code;
	}
	
	/** 设置区域编号 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/** 获取区域名称 */
	public String getName() {
		return name;
	}
	
	/** 设置区域名称 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 获取区域全称 */
	public String getFull_name() {
		return full_name;
	}
	
	/** 设置区域全称 */
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	
	/** 获取上级区域id */
	public Integer getParent_id() {
		return parent_id;
	}
	
	/** 设置上级区域id */
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	
	/** 获取上级区域编号 */
	public String getParent_code() {
		return parent_code;
	}
	
	/** 设置上级区域编号 */
	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}
	
	/** 获取上级区域名称 */
	public String getParent_name() {
		return parent_name;
	}
	
	/** 设置上级区域名称 */
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	
	/** 获取上级区域全称 */
	public String getParent_full_name() {
		return parent_full_name;
	}
	
	/** 设置上级区域全称 */
	public void setParent_full_name(String parent_full_name) {
		this.parent_full_name = parent_full_name;
	}
	
	/** 获取区域层级 */
	public Integer getLevel() {
		return level;
	}
	
	/** 设置区域层级 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	/** 获取是否末级区域 */
	public String getIs_leaf() {
		return is_leaf;
	}
	
	/** 设置是否末级区域 */
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
	
	public String getBaidu_uid() {
		return baidu_uid;
	}
	
	public void setBaidu_uid(String baidu_uid) {
		this.baidu_uid = baidu_uid;
	}

	public String getBaidu_title() {
		return baidu_title;
	}

	public void setBaidu_title(String baidu_title) {
		this.baidu_title = baidu_title;
	}

	public String getBaidu_address() {
		return baidu_address;
	}
	
	public void setBaidu_address(String baidu_address) {
		this.baidu_address = baidu_address;
	}
	
	public Double getBaidu_longtitude() {
		return baidu_longtitude;
	}

	public void setBaidu_longtitude(Double baidu_longtitude) {
		this.baidu_longtitude = baidu_longtitude;
	}

	public Double getBaidu_latitude() {
		return baidu_latitude;
	}

	public void setBaidu_latitude(Double baidu_latitude) {
		this.baidu_latitude = baidu_latitude;
	}
	
	public Integer getSearch_times() {
		return search_times;
	}
	
	public void setSearch_times(Integer search_times) {
		this.search_times = search_times;
	}
	
	/** 获取排序编码 */
	public Integer getSort() {
		return sort;
	}
	
	/** 设置排序编码 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	/** 获取可用状态 */
	public String getStatus() {
		return status;
	}
	
	/** 设置可用状态 */
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