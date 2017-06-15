package com.deertt.module.sc.job.vo;

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
public class JobApplyVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//兼职id
	private Integer job_id;
	
	//兼职名称
	private String job_name;
	
	//申请人id
	private Integer user_id;
	
	//申请人
	private String user_name;
	
	//报名时填写的名称
	private String name;
	
	//联系方式
	private String mobile;
	
	//申请人地址
	private String address;
	
	//申请人学校
	private String school;
	
	//是否已联系应聘者
	private String deal_status;
	
	//应聘者是否同意参加
	private String accept_status;
	
	//应聘者是否完成任务
	private String finish_status;
	
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
	
	/** 获取兼职id */
	public Integer getJob_id() {
		return job_id;
	}
	
	/** 设置兼职id */
	public void setJob_id(Integer job_id) {
		this.job_id = job_id;
	}
	
	/** 获取兼职名称 */
	public String getJob_name() {
		return job_name;
	}
	
	/** 设置兼职名称 */
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	
	/** 获取申请人id */
	public Integer getUser_id() {
		return user_id;
	}
	
	/** 设置申请人id */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	/** 获取申请人 */
	public String getUser_name() {
		return user_name;
	}
	
	/** 设置申请人 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	/** 获取报名时填写的名称  */
	public String getName() {
		return name;
	}
	
	/** 设置报名时填写的名称  */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 获取联系方式 */
	public String getMobile() {
		return mobile;
	}
	
	/** 设置联系方式 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/** 获取申请人地址 */
	public String getAddress() {
		return address;
	}
	
	/** 设置申请人地址 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/** 获取申请人学校 */
	public String getSchool() {
		return school;
	}
	
	/** 设置申请人学校 */
	public void setSchool(String school) {
		this.school = school;
	}
	
	/** 获取是否已联系应聘者*/
	public String getDeal_status() {
		return deal_status;
	}
	
	/** 设置是否已联系应聘者*/
	public void setDeal_status(String deal_status) {
		this.deal_status = deal_status;
	}
	
	/** 获取应聘者是否同意参加*/
	public String getAccept_status() {
		return accept_status;
	}
	
	/** 设置应聘者是否同意参加*/
	public void setAccept_status(String accept_status) {
		this.accept_status = accept_status;
	}
	
	/** 获取finish_status */
	public String getFinish_status() {
		return finish_status;
	}
	
	/** 设置finish_status*/
	public void setFinish_status(String finish_status) {
		this.finish_status = finish_status;
	}
	
	
	
	//----------结束vo的setter和getter方法----------
}