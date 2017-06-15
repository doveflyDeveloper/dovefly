package com.deertt.module.sys.notice.vo;

import java.sql.Timestamp;

import com.deertt.module.tcommonfile.vo.CommonFileAware;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class NoticeVo extends CommonFileAware {
	private static final long serialVersionUID = 1L;
	
	final public static String ISSUE_DEFAULT_STATUS = "0";
	
	final public static String ISSUE_STATUS = "1";
	
	final public static String CANCEL_ISSUE_STATUS = "2";
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//标题
	private String title;
	
	//类型
	private String type;
	
	//阅读次数
	private Integer read_times;
	
	//新闻内容
	private String content;
	
	//发布状态
	private String issue_status;
	
	//发布时间
	private Timestamp issue_at;
	
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
	
	/** 获取标题 */
	public String getTitle() {
		return title;
	}
	
	/** 设置标题 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 获取类型 */
	public String getType() {
		return type;
	}
	
	/** 设置类型 */
	public void setType(String type) {
		this.type = type;
	}
	
	/** 获取阅读次数 */
	public Integer getRead_times() {
		return read_times;
	}
	
	/** 设置阅读次数 */
	public void setRead_times(Integer read_times) {
		this.read_times = read_times;
	}
	
	/** 获取新闻内容 */
	public String getContent() {
		return content;
	}
	
	/** 设置新闻内容 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/** 获取发布状态 */
	public String getIssue_status() {
		return issue_status;
	}
	
	/** 设置发布状态 */
	public void setIssue_status(String issue_status) {
		this.issue_status = issue_status;
	}
	
	/** 获取发布时间 */
	public Timestamp getIssue_at() {
		return issue_at;
	}
	
	/** 设置发布时间 */
	public void setIssue_at(Timestamp issue_at) {
		this.issue_at = issue_at;
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