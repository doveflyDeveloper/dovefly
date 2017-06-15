package com.deertt.module.fund.transition.service;

import java.math.BigDecimal;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.fund.transition.dao.ITransitionDao;
import com.deertt.module.fund.transition.vo.TransitionVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ITransitionService extends IDvBaseService<ITransitionDao, TransitionVo, Integer> {
	
	/**
	 * 
	 * @param store_type
	 * @param store_id
	 * @param store_name
	 * @param bill_code
	 * @param transition_code
	 * @param transition_amount
	 * @param balance_amount
	 * @param brief
	 * @param remark
	 * @return
	 */
	public int addInTransition(String store_type, Integer store_id, String store_name, String bill_code,
			String transition_code, BigDecimal transition_amount, BigDecimal balance_amount, String brief, String remark);
	
	/**
	 * 
	 * @param store_type
	 * @param store_id
	 * @param store_name
	 * @param bill_code
	 * @param transition_code
	 * @param transition_amount
	 * @param balance_amount
	 * @param brief
	 * @param remark
	 * @return
	 */
	public int addOutTransition(String store_type, Integer store_id, String store_name, String bill_code,
			String transition_code, BigDecimal transition_amount, BigDecimal balance_amount, String brief, String remark);
	
	/**
	 * 
	 * @param warehouse_id
	 * @param warehouse_name
	 * @param bill_code
	 * @param transition_code
	 * @param transition_amount
	 * @param balance_amount
	 * @param brief
	 * @param remark
	 * @return
	 */
	public int addInTransitionW(Integer warehouse_id, String warehouse_name, String bill_code,
			String transition_code, BigDecimal transition_amount,
			BigDecimal balance_amount, String brief, String remark);
	
	/**
	 * 
	 * @param warehouse_id
	 * @param warehouse_name
	 * @param bill_code
	 * @param transition_code
	 * @param transition_amount
	 * @param balance_amount
	 * @param brief
	 * @param remark
	 * @return
	 */
	public int addOutTransitionW(Integer warehouse_id, String warehouse_name, String bill_code,
			String transition_code, BigDecimal transition_amount,
			BigDecimal balance_amount, String brief, String remark);
	
	/**
	 * 
	 * @param shop_id
	 * @param shop_name
	 * @param bill_code
	 * @param transition_code
	 * @param transition_amount
	 * @param balance_amount
	 * @param brief
	 * @param remark
	 * @return
	 */
	public int addInTransitionS(Integer shop_id, String shop_name, String bill_code,
			String transition_code, BigDecimal transition_amount,
			BigDecimal balance_amount, String brief, String remark);
	
	/**
	 * 
	 * @param shop_id
	 * @param shop_name
	 * @param bill_code
	 * @param transition_code
	 * @param transition_amount
	 * @param balance_amount
	 * @param brief
	 * @param remark
	 * @return
	 */
	public int addOutTransitionS(Integer shop_id, String shop_name, String bill_code,
			String transition_code, BigDecimal transition_amount,
			BigDecimal balance_amount, String brief, String remark);
	
	/**
	 * 
	 * @param market_id
	 * @param market_name
	 * @param bill_code
	 * @param transition_code
	 * @param transition_amount
	 * @param balance_amount
	 * @param brief
	 * @param remark
	 * @return
	 */
	public int addInTransitionM(Integer market_id, String market_name, String bill_code,
			String transition_code, BigDecimal transition_amount,
			BigDecimal balance_amount, String brief, String remark);
	
	/**
	 * 
	 * @param market_id
	 * @param market_name
	 * @param bill_code
	 * @param transition_code
	 * @param transition_amount
	 * @param balance_amount
	 * @param brief
	 * @param remark
	 * @return
	 */
	public int addOutTransitionM(Integer market_id, String market_name, String bill_code,
			String transition_code, BigDecimal transition_amount,
			BigDecimal balance_amount, String brief, String remark);
}
