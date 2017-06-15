package com.deertt.module.order.bill.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.order.bill.vo.OrderBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IOrderBillDao extends IDvBaseDao<OrderBillVo, Integer> {

	/**
	 * 更新支付信息
	 * @param vo
	 * @return
	 */
	public int updatePayInfo(OrderBillVo vo);
	
	/**
	 * 更新退款信息
	 * @param vo
	 * @return
	 */
	public int updateRefundInfo(OrderBillVo vo);
}
