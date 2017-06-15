package com.deertt.module.mm.goods.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.goods.dao.IGoodsMDao;
import com.deertt.module.mm.goods.service.IGoodsMService;
import com.deertt.module.mm.goods.util.IGoodsMConstants;
import com.deertt.module.mm.goods.vo.GoodsMVo;
import com.deertt.utils.helper.date.DvDateHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class GoodsMService extends DvBaseService<IGoodsMDao, GoodsMVo, Integer> implements IGoodsMService, IGoodsMConstants {
	
	@Transactional
	public int onSale(Integer[] ids) {
		return getDao().enable(ids);
	}
	
	@Transactional
	public int offSale(Integer[] ids) {
		return getDao().disable(ids);
	}
	
	@Transactional
	public int sort(String goods_ids) {
		return getDao().sort(goods_ids);
	}
	
	@Transactional
	public int addStockSum(Integer goods_id, BigDecimal quantity, String refer_bill_code) {
		return addStockSum(goods_id, quantity, null, null, refer_bill_code);
	}
	
	@Transactional
	public int addStockSum(Integer goods_id, BigDecimal quantity, BigDecimal cost_price, BigDecimal sale_price, String refer_bill_code) {
		int sum = 0;
		GoodsMVo goods = getDao().find(goods_id);
		if (goods == null) {
			throw new RuntimeException("商品【ID:" + goods_id + "】不存在，增加库存失败！");
		}
		
		if (goods.getStock_sum() == null) goods.setStock_sum(BigDecimal.ZERO);
		if (goods.getStock_locked() == null) goods.setStock_locked(BigDecimal.ZERO);
		
		goods.setStock_sum(goods.getStock_sum().add(quantity));
		if (cost_price != null) {
			goods.setCost_price(cost_price);
		}
		if (sale_price != null) {
			goods.setSale_price(sale_price);
		}
		this.changePrice(goods.getId(), goods.getCost_price(), goods.getSale_price());
		
		sum = this.changeStockSum(goods.getId(), goods.getStock_sum(), goods.getStock_locked());
		this.addRecord(goods.getId(), quantity, goods.getStock_sum(), BigDecimal.ZERO, goods.getStock_locked(), refer_bill_code, "增加库存");
		return sum;
	}
	
	@Transactional
	public int reduceStockSum(Integer goods_id, BigDecimal quantity, String refer_bill_code) {
		GoodsMVo goods = getDao().find(goods_id);
		if (goods == null) {
			throw new RuntimeException("商品【ID:" + goods_id + "】不存在，扣减库存失败！");
		}
    	if (goods.getStock_sum() == null) goods.setStock_sum(BigDecimal.ZERO);
    	if (goods.getStock_locked() == null) goods.setStock_locked(BigDecimal.ZERO);
    	if (goods.getSold_volume() == null) goods.setSold_volume(0);
    	
//    	if (goods.getStock_sum().compareTo(quantity) >= 0) {//库存够扣减的
//    		goods.setStock_sum(goods.getStock_sum().subtract(quantity));
//		} else if (goods.getStock_sum().add(goods.getStock_locked()).compareTo(quantity) >= 0) {//库存与锁定库存之和够扣减的
//			goods.setStock_sum(BigDecimal.ZERO);
//			goods.setStock_locked(goods.getStock_sum().add(goods.getStock_locked()).subtract(quantity));
//		} else {
//			throw new RuntimeException("商品【ID:" + goods_id + "】库存不足，扣减库存失败！");
//		}
    	
    	if (goods.getStock_sum().compareTo(quantity) >= 0) {//库存够扣减的
    		goods.setStock_sum(goods.getStock_sum().subtract(quantity));
    	} else {
			throw new RuntimeException("商品【ID:" + goods_id + "】库存不足，扣减库存失败！");
		}
    	
		int sum = this.changeStockSum(goods.getId(), goods.getStock_sum(), goods.getStock_locked());
		this.addRecord(goods.getId(), quantity.negate(), goods.getStock_sum(), BigDecimal.ZERO, goods.getStock_locked(), refer_bill_code, "扣减库存");
		return sum;
	}
	
	@Transactional
	public int lockStockSumToStockLocked(Integer goods_id, BigDecimal quantity, String refer_bill_code) {
		GoodsMVo goods = getDao().find(goods_id);
		if (goods == null) {
			throw new RuntimeException("商品【" + goods_id + "】不存在！");
		} 
		if (goods.getStock_sum() == null) goods.setStock_sum(BigDecimal.ZERO);
		if (goods.getStock_locked() == null) goods.setStock_locked(BigDecimal.ZERO);
    	
    	if (goods.getStock_sum().compareTo(quantity) >= 0) {//库存够锁定的
    		goods.setStock_sum(goods.getStock_sum().subtract(quantity));
    		goods.setStock_locked(goods.getStock_locked().add(quantity));
		} else {
			throw new RuntimeException("商品【" + goods_id + "】库存不足，无法锁定库存！");
		}
    	
    	int sum = this.changeStockSum(goods.getId(), goods.getStock_sum(), goods.getStock_locked());
		this.addRecord(goods.getId(), quantity.negate(), goods.getStock_sum(), quantity, goods.getStock_locked(), refer_bill_code, "锁定库存");
    	return sum;
	}
	
	@Transactional
	public int reduceStockLocked(Integer goods_id, BigDecimal quantity, String refer_bill_code) {
		GoodsMVo goods = getDao().find(goods_id);
		if (goods == null) {
			throw new RuntimeException("商品【ID:" + goods_id + "】不存在，扣减锁定库存失败！");
		}
    	if (goods.getStock_sum() == null) goods.setStock_sum(BigDecimal.ZERO);
    	if (goods.getStock_locked() == null) goods.setStock_locked(BigDecimal.ZERO);
    	if (goods.getSold_volume() == null) goods.setSold_volume(0);
    	
//    	if (goods.getStock_locked().compareTo(quantity) >= 0) {//锁定库存够扣减的
//    		goods.setStock_locked(goods.getStock_locked().subtract(quantity));
//		} else if (goods.getStock_sum().add(goods.getStock_locked()).compareTo(quantity) >= 0) {//库存与锁定库存之和够扣减的
//			goods.setStock_locked(BigDecimal.ZERO);
//			goods.setStock_sum(goods.getStock_sum().add(goods.getStock_locked()).subtract(quantity));
//		} else {
//			throw new RuntimeException("商品【ID:" + goods_id + "】锁定库存不足，扣减锁定库存失败！");
//		}
    	
    	if (goods.getStock_locked().compareTo(quantity) >= 0) {//锁定库存够扣减的
    		goods.setStock_locked(goods.getStock_locked().subtract(quantity));
		} else {
			throw new RuntimeException("商品【ID:" + goods_id + "】锁定库存不足，扣减锁定库存失败！");
		}
    	
    	goods.setSold_volume(goods.getSold_volume() + quantity.intValue());
    	
    	int sum = this.changeStockSum(goods.getId(), goods.getStock_sum(), goods.getStock_locked(), goods.getSold_volume());
		this.addRecord(goods.getId(), BigDecimal.ZERO, goods.getStock_sum(), quantity.negate(), goods.getStock_locked(), refer_bill_code, "扣减锁定库存");
    	return sum;
	}
	
	@Transactional
	public int turnbackStockLockedToStockSum(Integer goods_id, BigDecimal quantity, String refer_bill_code) {
		GoodsMVo goods = getDao().find(goods_id);
		if (goods == null) {
			throw new RuntimeException("商品【ID:" + goods_id + "】不存在，返还锁定库存失败！");
		}
		if (goods.getStock_sum() == null) goods.setStock_sum(BigDecimal.ZERO);
    	if (goods.getStock_locked() == null) goods.setStock_locked(BigDecimal.ZERO);
    	
//    	if (goods.getStock_locked().compareTo(quantity) >= 0) {//锁定库存够扣减的
//    		goods.setStock_locked(goods.getStock_locked().subtract(quantity));
//    		goods.setStock_sum(goods.getStock_sum().add(quantity));
//		} else {
//			goods.setStock_locked(BigDecimal.ZERO);
//			goods.setStock_sum(goods.getStock_sum().add(quantity).subtract(goods.getStock_locked()));
//		}
    	
    	if (goods.getStock_locked().compareTo(quantity) >= 0) {//锁定库存够扣减的
    		goods.setStock_locked(goods.getStock_locked().subtract(quantity));
    		goods.setStock_sum(goods.getStock_sum().add(quantity));
		} else {
			throw new RuntimeException("商品【ID:" + goods_id + "】锁定库存不足，返还锁定库存失败！");
		}
    	
    	int sum = this.changeStockSum(goods.getId(), goods.getStock_sum(), goods.getStock_locked());
		this.addRecord(goods.getId(), quantity, goods.getStock_sum(), quantity.negate(), goods.getStock_locked(), refer_bill_code, "返还锁定库存");
		return sum;
	}
	
	private int changeStockSum(Integer goods_id, BigDecimal stockSum, BigDecimal stockLocked) {
		return getDao().update(SQL_UPDATE_STOCK_SUM_BY_ID.replace(", sold_volume = ?", ""), new Object[] { stockSum, stockLocked, goods_id });
	}
	
	private int changeStockSum(Integer goods_id, BigDecimal stockSum, BigDecimal stockLocked, Integer soldVolume) {
		return getDao().update(SQL_UPDATE_STOCK_SUM_BY_ID, new Object[] { stockSum, stockLocked, soldVolume, goods_id });
	}

	private int addRecord(Integer goods_id, BigDecimal stock_sum_change, BigDecimal stock_sum, BigDecimal stock_locked_change, BigDecimal stock_locked, String refer_bill_code, String remark) {
		return getDao().update(SQL_INSERT_RECORD, new Object[] { goods_id, stock_sum_change, stock_sum, stock_locked_change, stock_locked, refer_bill_code, "PC", remark, DvDateHelper.getSysTimestamp() });
	}
	
	private int changePrice(Integer goods_id, BigDecimal cost_price, BigDecimal sale_price) {
		return this.update(SQL_UPDATE_PRICE_BY_ID, new Object[] {cost_price, sale_price, goods_id});
	}
}
