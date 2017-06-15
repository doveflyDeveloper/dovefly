package com.deertt.module.mm.goods.service;

import java.math.BigDecimal;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.mm.goods.dao.IGoodsWDao;
import com.deertt.module.mm.goods.vo.GoodsWVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IGoodsWService extends IDvBaseService<IGoodsWDao, GoodsWVo, Integer> {
	
	/**
	 * 上架销售
	 * 
	 * @param ids 用于启用的记录的ids
	 * @return 成功启用的记录数
	 */
	public int onSale(Integer[] ids);
	
	/**
	 * 下架停售
	 * 
	 * @param ids 用于禁用的记录的ids
	 * @return 成功禁用的记录数
	 */
	public int offSale(Integer[] ids);
	
	/**
	 * 库存商品重排序
	 * @param goods_ids
	 * @return
	 */
	public int sort(String goods_ids);
	
	/**
	 * 增加库存
	 * @param goods_id
	 * @param quantity
	 * @param refer_bill_code
	 * @return
	 */
	public int addStockSum(Integer goods_id, BigDecimal quantity, String refer_bill_code);
	
	/**
	 * 增加库存同时变更采购价格，销售价格
	 * @param goods_id
	 * @param quantity
	 * @param cost_price
	 * @param sale_price
	 * @param refer_bill_code
	 * @return
	 */
	public int addStockSum(Integer goods_id, BigDecimal quantity, BigDecimal cost_price, BigDecimal sale_price, String refer_bill_code);
	
	/**
	 * 减少库存（已入库采购单撤销入库，库存回退）
	 * @param goods_id
	 * @param quantity
	 * @param refer_bill_code
	 * @return
	 */
	public int reduceStockSum(Integer goods_id, BigDecimal quantity, String refer_bill_code);
	
	/**
	 * 锁定的库存（进货单订单提交时、销售订单提交时需锁定库存）
	 * @param goods_id
	 * @param quantity
	 * @param refer_bill_code
	 * @return
	 */
	public int lockStockSumToStockLocked(Integer goods_id, BigDecimal quantity, String refer_bill_code);
	
	/**
	 * 释放锁定库存，增加销量（销售订单发货、进货订单收货时，发货数量将从锁定库存中释放）
	 * @param goods_id
	 * @param quantity
	 * @param refer_bill_code
	 * @return
	 */
	public int reduceStockLocked(Integer goods_id, BigDecimal quantity, String refer_bill_code);
	
	/**
	 * 返回锁定的库存到库存量中（进货订单、销售订单取消时返回被锁定的库存）
	 * @param goods_id
	 * @param quantity
	 * @param refer_bill_code
	 * @return
	 */
	public int turnbackStockLockedToStockSum(Integer goods_id, BigDecimal quantity, String refer_bill_code);

	/**
	 * 手动修改库存值
	 * @param goods_id
	 * @param stock_sum
	 * @return
	 */
	public int manualChangeStockSum(Integer goods_id, BigDecimal stock_sum);

}
