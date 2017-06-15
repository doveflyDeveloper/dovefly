package com.deertt.module.market.bill.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.market.bill.vo.MarketBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMarketBillDao extends IDvBaseDao<MarketBillVo, Integer> {
	
	/**
	 * 更新支付信息
	 * @param vo
	 * @return
	 */
	public int updatePayInfo(MarketBillVo vo);
	
	/**
	 * 更新退款信息
	 * @param vo
	 * @return
	 */
	public int updateRefundInfo(MarketBillVo vo);

}
