package com.deertt.module.purchase.out.service;

import java.util.List;
import java.util.Map;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.purchase.out.dao.IPurchaseOutBillDao;
import com.deertt.module.purchase.out.vo.PurchaseOutBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IPurchaseOutBillService extends IDvBaseService<IPurchaseOutBillDao, PurchaseOutBillVo, Integer> {
	
	/**
	 * 保存订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int saveFull(PurchaseOutBillVo vo);

	/**
	 * 新增订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int insertFull(PurchaseOutBillVo vo);

	/**
	 * 更新订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int updateFull(PurchaseOutBillVo vo);
	
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
	public PurchaseOutBillVo findFull(Integer id);

	/**
	 * 根据订单编号查询订单
	 * @param bill_code
	 * @return
	 */
	public PurchaseOutBillVo findByCode(String bill_code);
	
	/**
	 * 确认出库
	 * @param id
	 * @return
	 */
	public int checkout(Integer id);
	
	/**
	 * 撤销出库
	 * 
	 * @param id
	 * @return
	 */
	public int turnback(Integer id);
	
	/**
	 * 查询订单及明细
	 * @param queryCondition
	 * @return
	 */
	public List<Map<String, Object>> queryListDetails(String queryCondition);
}
