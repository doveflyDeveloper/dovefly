package com.deertt.frame.base.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import net.sf.json.JSONArray;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.deertt.utils.helper.DvVoHelper;


public abstract class DvBaseVo implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	//备注
	private String remark;
	
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

	/**
	 * override method 'equals'
	 * 
	 * @param other 与本对象比较的其它对象
	 * @return boolean 两个对象的各个属性是否都相等
	 */
	public boolean equals(Object other) {
		return DvVoHelper.voEquals(this, other);
	}

	/**
	 * override method 'hashCode'
	 * 
	 * @return int Hash码
	 */
	public int hashCode() {
		return DvVoHelper.voHashCode(this);
	}
	
	/**
	 * override method 'clone'
	 *
	 * @see java.lang.Object#clone()
	 * @return Object 克隆后对象
	 */
	public Object clone() {
		return DvVoHelper.voClone(this);
	}

	/**
	 * override method 'toString'
	 * 
	 * @return String 字符串表示
	 */
	public String toString() {
		//return ToStringBuilder.reflectionToString(this);
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	/**
	 * 将对象转换为JSON字符串格式
	 * 
	 * @return String 字符串表示
	 */
	public String toJSON() {
		return JSONArray.fromObject(this).toString();
	}
	
	/**
	 * 通过主键判断对象是否为新对象（无主键）
	 * 
	 * @return
	 */
	public abstract boolean isNew();
	
}
