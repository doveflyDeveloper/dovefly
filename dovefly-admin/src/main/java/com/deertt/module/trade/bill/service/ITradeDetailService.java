package com.deertt.module.trade.bill.service;

import java.util.List;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.trade.bill.dao.ITradeDetailDao;
import com.deertt.module.trade.bill.vo.TradeDetailVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ITradeDetailService extends IDvBaseService<ITradeDetailDao, TradeDetailVo, Integer> {
	/**
	 * 批量更新订单明细（增删改）
	 * @param voList
	 * @param bill_id
	 * @return
	 */
	public int[] insertUpdateBatch(List<TradeDetailVo> voList, Integer bill_id);
	
	/**
	 * 删除订单的明细
	 * @param bill_id
	 */
	public int deleteByBill(Integer bill_id);
}
