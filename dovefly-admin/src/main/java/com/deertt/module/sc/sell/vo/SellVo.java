package com.deertt.module.sc.sell.vo;

import java.math.BigDecimal;
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
public class SellVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//标题
	private String title;
	
	//类别
	private String type;
	
	//新旧程度
	private String is_new;
	
	//出手价格
	private BigDecimal price;
	
	//购入价格
	private BigDecimal old_price;
	
	//卖家姓名
	private String seller_name;
	
	//卖家手机
	private String seller_mobile;
	
	//卖家qq
	private String seller_qq;
	
	//卖家微信
	private String seller_weixin;
	
	//宝贝首图
	private String image;
	
	//宝贝图片集
	private String images;
	
	//宝贝描述
	private String description;
	
	//发布范围
	private String issue_range;
	
	//发布日期
	private Date issue_date;
	
	//截止日期
	private Timestamp end_date;
	
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
	
	/** 获取类别 */
	public String getType() {
		return type;
	}
	
	/** 设置类别 */
	public void setType(String type) {
		this.type = type;
	}
	
	/** 获取新旧程度 */
	public String getIs_new() {
		return is_new;
	}
	
	/** 设置新旧程度 */
	public void setIs_new(String is_new) {
		this.is_new = is_new;
	}
	
	/** 获取出手价格 */
	public BigDecimal getPrice() {
		return price;
	}
	
	/** 设置出手价格 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/** 获取购入价格 */
	public BigDecimal getOld_price() {
		return old_price;
	}
	
	/** 设置购入价格 */
	public void setOld_price(BigDecimal old_price) {
		this.old_price = old_price;
	}
	
	/** 获取卖家姓名 */
	public String getSeller_name() {
		return seller_name;
	}
	
	/** 设置卖家姓名 */
	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}
	
	/** 获取卖家手机 */
	public String getSeller_mobile() {
		return seller_mobile;
	}
	
	/** 设置卖家手机 */
	public void setSeller_mobile(String seller_mobile) {
		this.seller_mobile = seller_mobile;
	}
	
	/** 获取卖家qq */
	public String getSeller_qq() {
		return seller_qq;
	}
	
	/** 设置卖家qq */
	public void setSeller_qq(String seller_qq) {
		this.seller_qq = seller_qq;
	}
	
	/** 获取卖家微信 */
	public String getSeller_weixin() {
		return seller_weixin;
	}
	
	/** 设置卖家微信 */
	public void setSeller_weixin(String seller_weixin) {
		this.seller_weixin = seller_weixin;
	}
	
	/** 获取宝贝首图 */
	public String getImage() {
		return image;
	}
	
	/** 设置宝贝首图 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/** 获取宝贝图片集 */
	public String getImages() {
		return images;
	}
	
	/** 设置宝贝图片集 */
	public void setImages(String images) {
		this.images = images;
	}
	
	/** 获取宝贝描述 */
	public String getDescription() {
		return description;
	}
	
	/** 设置宝贝描述 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** 获取发布范围 */
	public String getIssue_range() {
		return issue_range;
	}
	
	/** 设置发布范围 */
	public void setIssue_range(String issue_range) {
		this.issue_range = issue_range;
	}
	
	/** 获取发布日期 */
	public Date getIssue_date() {
		return issue_date;
	}
	
	/** 设置发布日期 */
	public void setIssue_date(Date issue_date) {
		this.issue_date = issue_date;
	}
	
	/** 获取截止日期 */
	public Timestamp getEnd_date() {
		return end_date;
	}
	
	/** 设置截止日期 */
	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
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