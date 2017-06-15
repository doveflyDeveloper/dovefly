package com.deertt.module.order.back.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.order.back.vo.OrderBackBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IOrderBackBillDao extends IDvBaseDao<OrderBackBillVo, Integer> {
	/**
	 * 更新支付信息
	 * @param vo
	 * @return
	 */
	public int updatePayInfo(OrderBackBillVo vo);
}
