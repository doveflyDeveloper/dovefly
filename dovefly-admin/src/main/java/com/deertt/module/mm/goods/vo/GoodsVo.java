package com.deertt.module.mm.goods.vo;

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
public class GoodsVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//城市id
	private Integer city_id;
	
	//城市
	private String city_name;
	
	private Integer item_id;
	
	//商品条形码
	private String barcode;
	
	//商品名称
	private String name;
	
	//商品标题
	private String title;
	
	//商品摘要
	private String tips;
	
	//商品类型
	private String type;
	
	//商品特性：new-新品,hot-热销,recommend-推荐
	private String tag;
	
	//重量
	private BigDecimal weight;
	
	//保质期
	private String shelf_life;
	
	//存储方式
	private String storage_type;
	
	//生产商
	private String producer;
	
	//成本价
	private BigDecimal cost_price;
	
	//销售价
	private BigDecimal sale_price;
	
	//市场价
	private BigDecimal market_price;
	
	private Integer limit_coin_quantity;
	
	private Integer send_coin_quantity;
	
	private String buy_rule;
	
	//分类id
	private Integer category_id;
	
	//分类
	private String category_code;
	
	//销量
	private Integer sold_volume;
	
	//库存
	private BigDecimal stock_sum;
	
	//锁定库存
	private BigDecimal stock_locked;
	
	private BigDecimal safe_line;
	
	private BigDecimal max_limit;
	
	private BigDecimal cmt_point;
	
	private Integer cmt_times;
	
	//缩略图
	private String image;
	
	//图片
	private String images;
	
	//描述
	private String description;
	
	//产品规格
	private  BigDecimal   spec;
	
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

	public Integer getItem_id() {
		return item_id;
	}
	
	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}
	
	/** 获取商品条形码 */
	public String getBarcode() {
		return barcode;
	}
	
	/** 设置商品条形码 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	/** 获取商品名称 */
	public String getName() {
		return name;
	}
	
	/** 设置商品名称 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 获取商品标题 */
	public String getTitle() {
		return title;
	}
	
	/** 设置商品标题 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 获取商品摘要 */
	public String getTips() {
		return tips;
	}
	
	/** 设置商品摘要 */
	public void setTips(String tips) {
		this.tips = tips;
	}
	
	/** 获取商品类型 */
	public String getType() {
		return type;
	}
	
	/** 设置商品类型 */
	public void setType(String type) {
		this.type = type;
	}
	
	/** 获取商品特性：new-新品,hot-热销,recommend-推荐 */
	public String getTag() {
		return tag;
	}
	
	/** 设置商品特性：new-新品,hot-热销,recommend-推荐 */
	public void setTag(String tag) {
		this.tag = tag;
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
	
	/** 获取成本价 */
	public BigDecimal getCost_price() {
		return cost_price;
	}
	
	/** 设置成本价 */
	public void setCost_price(BigDecimal cost_price) {
		this.cost_price = cost_price;
	}
	
	/** 获取销售价 */
	public BigDecimal getSale_price() {
		return sale_price;
	}
	
	/** 设置销售价 */
	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}
	
	/** 获取市场价 */
	public BigDecimal getMarket_price() {
		return market_price;
	}
	
	/** 设置市场价 */
	public void setMarket_price(BigDecimal market_price) {
		this.market_price = market_price;
	}
	
	public Integer getLimit_coin_quantity() {
		return limit_coin_quantity;
	}

	public void setLimit_coin_quantity(Integer limit_coin_quantity) {
		this.limit_coin_quantity = limit_coin_quantity;
	}

	public Integer getSend_coin_quantity() {
		return send_coin_quantity;
	}

	public void setSend_coin_quantity(Integer send_coin_quantity) {
		this.send_coin_quantity = send_coin_quantity;
	}

	public String getBuy_rule() {
		return buy_rule;
	}
	
	public void setBuy_rule(String buy_rule) {
		this.buy_rule = buy_rule;
	}
	
	/** 获取分类id */
	public Integer getCategory_id() {
		return category_id;
	}
	
	/** 设置分类id */
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	
	/** 获取分类 */
	public String getCategory_code() {
		return category_code;
	}
	
	/** 设置分类 */
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	
	/** 获取销量 */
	public Integer getSold_volume() {
		return sold_volume;
	}
	
	/** 设置销量 */
	public void setSold_volume(Integer sold_volume) {
		this.sold_volume = sold_volume;
	}
	
	public BigDecimal getStock_sum() {
		return stock_sum;
	}
	
	public void setStock_sum(BigDecimal stock_sum) {
		this.stock_sum = stock_sum;
	}
	
	public BigDecimal getStock_locked() {
		return stock_locked;
	}
	
	public void setStock_locked(BigDecimal stock_locked) {
		this.stock_locked = stock_locked;
	}
	
	public BigDecimal getSafe_line() {
		return safe_line;
	}

	public void setSafe_line(BigDecimal safe_line) {
		this.safe_line = safe_line;
	}

	public BigDecimal getMax_limit() {
		return max_limit;
	}

	public void setMax_limit(BigDecimal max_limit) {
		this.max_limit = max_limit;
	}

	public BigDecimal getCmt_point() {
		return cmt_point;
	}

	public void setCmt_point(BigDecimal cmt_point) {
		this.cmt_point = cmt_point;
	}

	public Integer getCmt_times() {
		return cmt_times;
	}

	public void setCmt_times(Integer cmt_times) {
		this.cmt_times = cmt_times;
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
	
	/** 获取规格 */
	public BigDecimal getSpec() {
		return spec;
	}
	
	/** 设置规格 */
	public void setSpec(BigDecimal spec) {
		this.spec = spec;
	}
	

	
	
	
	
	//----------结束vo的setter和getter方法----------
}