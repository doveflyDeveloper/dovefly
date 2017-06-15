package com.deertt.module.mm.item.vo;

import java.math.BigDecimal;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class MmItemVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//货品条形码
	private String barcode;
	
	//条形码检测结果
	private String barcode_check;
	
	//货品名称
	private String name;
	
	//货品规格
	private BigDecimal spec;
	
	//重量
	private BigDecimal weight;
	
	//保质期
	private String shelf_life;
	
	//存储方式
	private String storage_type;
	
	//生产商
	private String producer;
	
	//缩略图
	private String image;
	
	//图片
	private String images;
	
	//描述
	private String description;
	
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
	
	/** 获取货品条形码 */
	public String getBarcode() {
		return barcode;
	}
	
	/** 设置货品条形码 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	/** 获取条形码检测结果 */
	public String getBarcode_check() {
		return barcode_check;
	}
	
	/** 设置条形码检测结果 */
	public void setBarcode_check(String barcode_check) {
		this.barcode_check = barcode_check;
	}
	
	/** 获取货品名称 */
	public String getName() {
		return name;
	}
	
	/** 设置货品名称 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 获得货品规格 */
	public BigDecimal getSpec() {
		return spec;
	}
	
	/** 设置货品规格*/
	public void setSpec(BigDecimal spec) {
		this.spec = spec;
	}
	
	/** 获取重量 */
	public BigDecimal getWeight() {
		return weight;
	}
	
	/** 设置重量 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	/** 获取保质期 */
	public String getShelf_life() {
		return shelf_life;
	}
	
	/** 设置保质期 */
	public void setShelf_life(String shelf_life) {
		this.shelf_life = shelf_life;
	}
	
	/** 获取存储方式 */
	public String getStorage_type() {
		return storage_type;
	}
	
	/** 设置存储方式 */
	public void setStorage_type(String storage_type) {
		this.storage_type = storage_type;
	}
	
	/** 获取生产商 */
	public String getProducer() {
		return producer;
	}
	
	/** 设置生产商 */
	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	/** 获取缩略图 */
	public String getImage() {
		return image;
	}
	
	/** 设置缩略图 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/** 获取图片 */
	public String getImages() {
		return images;
	}
	
	/** 设置图片 */
	public void setImages(String images) {
		this.images = images;
	}
	
	/** 获取描述 */
	public String getDescription() {
		return description;
	}
	
	/** 设置描述 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	//----------结束vo的setter和getter方法----------
}