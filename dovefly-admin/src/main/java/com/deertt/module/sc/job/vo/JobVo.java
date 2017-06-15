package com.deertt.module.sc.job.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
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
public class JobVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	public static final String ISSUE_STATUS_DRAFT = "0";//未发布
	public static final String ISSUE_STATUS_ISSUE = "1";//发布
	public static final String ISSUE_STATUS_CANCEL = "2";//撤销
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;

	// 城市
	private Integer city_id;

	// 城市
	private String city_name;

	// 学校
	private Integer school_id;

	// 学校
	private String school_name;
	
	//处理人id
    private Integer user_id;
    
    //处理人名字
    private String  user_name;
    
	// 标题
	private String title;

	// 兼职类型
	private String job_type;

	// 招聘机构
	private String company;

	// 联系电话
	private String contact_phone;

	// 薪资
	private BigDecimal salary;

	// 薪资单位
	private String salary_unit;

	// 工作起始日期
	private Date begin_date;

	// 工作结束日期
	private Date end_date;

	// 工作地点
	private String work_place;

	// 招聘人数
	private Integer need_num;

	// 已报名人数
	private Integer enroll_num;

	// 工作内容
	private String description;
	
    //兼职发布状态
	private String issue_status;
	
	// 发布时间
	private Timestamp issue_time;

	// 排序
	private Integer sort;

	// 状态
	private String status;

	// 创建人
	private Integer create_by;

	// 创建时间
	private Timestamp create_at;

	// 修改人
	private Integer modify_by;

	// 修改时间
	private Timestamp modify_at;

	//兼职明细
	private List<JobApplyVo> details;

	// ----------结束vo的属性----------
	@Override
	public boolean isNew() {
		return id == null || id == 0;
	}

	// ----------开始vo的setter和getter方法----------
	/** 获取id */
	public Integer getId() {
		return id;
	}

	/** 设置id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 获取城市 */
	public Integer getCity_id() {
		return city_id;
	}

	/** 设置城市 */
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	/** 获取城市 */
	public String getCity_name() {
		return city_name;
	}

	/** 设置城市 */
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	/** 获取学校 */
	public Integer getSchool_id() {
		return school_id;
	}

	/** 设置学校 */
	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}

	/** 获取学校 */
	public String getSchool_name() {
		return school_name;
	}

	/** 设置学校 */
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	
    /** 获取用户id*/
	public Integer getUser_id() {
		return user_id;
	}
	
	/** 设置用户id*/
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	/** 获取用户姓名*/
	public String getUser_name() {
		return user_name;
	}
	
	/** 设置用户姓名*/ 
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/** 获取标题 */
	public String getTitle() {
		return title;
	}

	/** 设置标题 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 获取兼职类型 */
	public String getJob_type() {
		return job_type;
	}

	/** 设置兼职类型 */
	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}

	/** 获取招聘机构 */
	public String getCompany() {
		return company;
	}

	/** 设置招聘机构 */
	public void setCompany(String company) {
		this.company = company;
	}

	/** 获取联系电话 */
	public String getContact_phone() {
		return contact_phone;
	}

	/** 设置联系电话 */
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	/** 获取薪资 */
	public BigDecimal getSalary() {
		return salary;
	}

	/** 设置薪资 */
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	/** 获取薪资单位 */
	public String getSalary_unit() {
		return salary_unit;
	}

	/** 设置薪资单位 */
	public void setSalary_unit(String salary_unit) {
		this.salary_unit = salary_unit;
	}

	/** 获取工作起始日期 */
	public Date getBegin_date() {
		return begin_date;
	}

	/** 设置工作起始日期 */
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}

	/** 获取工作结束日期 */
	public Date getEnd_date() {
		return end_date;
	}

	/** 设置工作结束日期 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	/** 获取工作地点 */
	public String getWork_place() {
		return work_place;
	}

	/** 设置工作地点 */
	public void setWork_place(String work_place) {
		this.work_place = work_place;
	}

	/** 获取招聘人数 */
	public Integer getNeed_num() {
		return need_num;
	}

	/** 设置招聘人数 */
	public void setNeed_num(Integer need_num) {
		this.need_num = need_num;
	}

	/** 获取已报名人数 */
	public Integer getEnroll_num() {
		return enroll_num;
	}

	/** 设置已报名人数 */
	public void setEnroll_num(Integer enroll_num) {
		this.enroll_num = enroll_num;
	}

	/** 获取工作内容 */
	public String getDescription() {
		return description;
	}

	/** 设置工作内容 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	/** 获取兼职发布状态 */
	public String getIssue_status() {
		return issue_status;
	}
    
	/** 设置兼职发布状态*/
	public void setIssue_status(String issue_status) {
		this.issue_status = issue_status;
	}

	/** 获取发布时间 */
	public Timestamp getIssue_time() {
		return issue_time;
	}

	/** 设置发布时间 */
	public void setIssue_time(Timestamp issue_time) {
		this.issue_time = issue_time;
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
    
	/** 获取兼职明细     */
	public List<JobApplyVo> getDetails() {
		return details;
	}
    /** 设置兼职明细*/ 
	public void setDetails(List<JobApplyVo> details) {
		this.details = details;
	}
	

	//----------结束vo的setter和getter方法----------
}