package com.deertt.module.purchase.bill.service;

import java.util.List;
import java.util.Map;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.purchase.bill.dao.IPurchaseBillDao;
import com.deertt.module.purchase.bill.vo.PurchaseBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IPurchaseBillService extends IDvBaseService<IPurchaseBillDao, PurchaseBillVo, Integer> {
	
	/**
	 * 保存订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int saveFull(PurchaseBillVo vo);

	/**
	 * 新增订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int insertFull(PurchaseBillVo vo);

	/**
	 * 更新订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int updateFull(PurchaseBillVo vo);
	
	/**
	 * 删除订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int deleteFull(Integer id);
	
	/**
	 * 删除订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int deleteFull(Integer[] ids);
	
	/**
	 * 查询订单（包含订单明细，快递信息）
	 * @param id
	 * @return
	 */
	public PurchaseBillVo findFull(Integer id);

	/**
	 * 根据订单编号查询订单
	 * @param bill_code
	 * @return
	 */
	public PurchaseBillVo findByCode(String bill_code);

	/**
	 * 入库
	 * @param id
	 * @return
	 */
	public int checkin(Integer id);
	
	/**
	 * 撤销入库（即再出库）
	 * 
	 * @param id
	 * @return
	 */
	public int turnback(Integer id);

	/**
	 * 统计各城市每天的采购订单量（已入库单量，总金额）
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByBillDate(String from_date, String to_date);
	
	/**
	 * 统计各个城市的采购情况（单量，总金额）
	 * @param city_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByCity(Integer city_id, String from_date, String to_date);
	
	/**
	 * 统计各个经理的采购情况（单量，总金额）
	 * @param warehouse_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByWarehouse(Integer warehouse_id, String from_date, String to_date);
	
	/**
	 * 统计商品采购数量及金额
	 * @param warehouse_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByGoods(Integer warehouse_id, String from_date, String to_date);
	
	/**
	 * 
	 * @param queryCondition
	 * @return
	 */
	public List<Map<String, Object>> queryListDetails(String queryCondition);
}
