package com.deertt.module.purchase.back.service;

import java.util.List;
import java.util.Map;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.purchase.back.dao.IPurchaseBackBillDao;
import com.deertt.module.purchase.back.vo.PurchaseBackBillVo;


/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IPurchaseBackBillService extends IDvBaseService<IPurchaseBackBillDao, PurchaseBackBillVo, Integer> {
	
	/**
	 * 保存订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int saveFull(PurchaseBackBillVo vo);

	/**
	 * 新增订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int insertFull(PurchaseBackBillVo vo);

	/**
	 * 更新订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int updateFull(PurchaseBackBillVo vo);
	
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
	public PurchaseBackBillVo findFull(Integer id);

	/**
	 * 根据订单编号查询订单
	 * @param bill_code
	 * @return
	 */
	public PurchaseBackBillVo findByCode(String bill_code);

	/**
	 * 确认
	 * @param id
	 * @return
	 */
	public int confirm(Integer id);
	
	/**
	 * 撤销确认
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
	 * 
	 * @param queryCondition
	 * @return
	 */
	public List<Map<String, Object>> queryListDetails(String queryCondition);
}
