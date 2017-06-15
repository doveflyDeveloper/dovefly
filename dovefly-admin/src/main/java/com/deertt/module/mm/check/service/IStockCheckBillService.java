package com.deertt.module.mm.check.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.mm.check.dao.IStockCheckBillDao;
import com.deertt.module.mm.check.vo.StockCheckBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IStockCheckBillService extends IDvBaseService<IStockCheckBillDao, StockCheckBillVo, Integer> {

	/**
	 * 插入
	 * @param vo
	 * @return
	 */
	public int insertFull(StockCheckBillVo vo);

	/**
	 * 更新
	 * @param vo
	 * @return
	 */
	public int updateFull(StockCheckBillVo vo);

	/**
	 * 查询订单（包含订单明细）
	 * 
	 * @param id
	 * @return
	 */
	public StockCheckBillVo findFull(Integer id);
	
	/**
	 * 盘点数据确认，生成结算数据清单，并更新现有库存数据
	 * @param stock_check_bill_id
	 */
	public void generateStatisticsBill(Integer stock_check_bill_id);
		
}
