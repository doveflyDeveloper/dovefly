package com.deertt.module.trade.bill.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.trade.bill.vo.TradeBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ITradeBillDao extends IDvBaseDao<TradeBillVo, Integer> {
	
	/**
	 * 更新支付信息
	 * @param vo
	 * @return
	 */
	public int updatePayInfo(TradeBillVo vo);
	
	/**
	 * 更新退款信息
	 * @param vo
	 * @return
	 */
	public int updateRefundInfo(TradeBillVo vo);

}
