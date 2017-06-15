package com.deertt.module.mv.show.vo;

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
public class MvShowVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	public static final String STATUS_UNDEAL = "0";//未处理
	public static final String STATUS_DEALING = "1";//处理中
	public static final String STATUS_SUCCESS= "2";//处理成功
	public static final String STATUS_FAIL = "3";//处理失败
	public static final String STATUS_DENYED = "4";//被拒绝
	// ----------开始vo的属性 ----------
	// id
	private Integer id;

	// 城市id
	private Integer city_id;

	// 城市
	private String city_name;

	// 学校id
	private Integer school_id;

	// 学校
	private String school_name;

	// 参赛人id
	private Integer user_id;

	// 参赛人
	private String user_name;

	// 推荐人id
	private Integer recommend_id;

	// 推荐人
	private String recommend_name;

	// 姓名
	private String name;

	// 手机号
	private String mobile;

	// 邮箱
	private String email;

	// 院系专业
	private String major;

	// 剧本名称
	private String mv_name;

	// 剧本类型
	private String mv_type;

	// 剧本梗概
	private String mv_brief;

	// 剧照图
	private String mv_images;

	// 汀豆
	private Integer mv_coin;

	// 剧本大纲
	private String mv_desc;

	// 剧本附件
	private String mv_file;
	
	private Integer step;
	
	//剧本投票明细表
	private List<MvShowSupportVo> details;

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

	/** 获取城市id */
	public Integer getCity_id() {
		return city_id;
	}

	/** 设置城市id */
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

	/** 获取学校id */
	public Integer getSchool_id() {
		return school_id;
	}

	/** 设置学校id */
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

	/** 获取参赛人id */
	public Integer getUser_id() {
		return user_id;
	}

	/** 设置参赛人id */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	/** 获取参赛人 */
	public String getUser_name() {
		return user_name;
	}

	/** 设置参赛人 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/** 获取推荐人id */
	public Integer getRecommend_id() {
		return recommend_id;
	}

	/** 设置推荐人id */
	public void setRecommend_id(Integer recommend_id) {
		this.recommend_id = recommend_id;
	}

	/** 获取推荐人 */
	public String getRecommend_name() {
		return recommend_name;
	}

	/** 设置推荐人 */
	public void setRecommend_name(String recommend_name) {
		this.recommend_name = recommend_name;
	}

	/** 获取姓名 */
	public String getName() {
		return name;
	}

	/** 设置姓名 */
	public void setName(String name) {
		this.name = name;
	}

	/** 获取手机号 */
	public String getMobile() {
		return mobile;
	}

	/** 设置手机号 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** 获取邮箱 */
	public String getEmail() {
		return email;
	}

	/** 设置邮箱 */
	public void setEmail(String email) {
		this.email = email;
	}

	/** 获取院系专业 */
	public String getMajor() {
		return major;
	}

	/** 设置院系专业 */
	public void setMajor(String major) {
		this.major = major;
	}

	/** 获取剧本名称 */
	public String getMv_name() {
		return mv_name;
	}

	/** 设置剧本名称 */
	public void setMv_name(String mv_name) {
		this.mv_name = mv_name;
	}

	/** 获取剧本类型 */
	public String getMv_type() {
		return mv_type;
	}

	/** 设置剧本类型 */
	public void setMv_type(String mv_type) {
		this.mv_type = mv_type;
	}

	/** 获取剧本梗概 */
	public String getMv_brief() {
		return mv_brief;
	}

	/** 设置剧本梗概 */
	public void setMv_brief(String mv_brief) {
		this.mv_brief = mv_brief;
	}

	/** 获取剧照图 */
	public String getMv_images() {
		return mv_images;
	}

	/** 设置剧照图 */
	public void setMv_images(String mv_images) {
		this.mv_images = mv_images;
	}

	/** 获取汀豆 */
	public Integer getMv_coin() {
		return mv_coin;
	}

	/** 设置汀豆 */
	public void setMv_coin(Integer mv_coin) {
		this.mv_coin = mv_coin;
	}

	/** 获取剧本大纲 */
	public String getMv_desc() {
		return mv_desc;
	}

	/** 设置剧本大纲 */
	public void setMv_desc(String mv_desc) {
		this.mv_desc = mv_desc;
	}

	/** 获取剧本附件 */
	public String getMv_file() {
		return mv_file;
	}

	/** 设置剧本附件 */
	public void setMv_file(String mv_file) {
		this.mv_file = mv_file;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}
	
	/** 获取投票详情*/
	public List<MvShowSupportVo> getDetails() {
		return details;
	}
	
	/** 设置投票详情*/
	public void setDetails(List<MvShowSupportVo> details) {
		this.details = details;
	}

	// ----------结束vo的setter和getter方法----------
}