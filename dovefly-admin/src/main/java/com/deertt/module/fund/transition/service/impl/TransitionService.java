package com.deertt.module.fund.transition.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.fund.transition.dao.ITransitionDao;
import com.deertt.module.fund.transition.service.ITransitionService;
import com.deertt.module.fund.transition.util.ITransitionConstants;
import com.deertt.module.fund.transition.vo.TransitionVo;
import com.deertt.utils.helper.date.DvDateHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class TransitionService extends DvBaseService<ITransitionDao, TransitionVo, Integer> implements ITransitionService, ITransitionConstants {

	private int addTransition(String store_type, Integer store_id, String store_name, String bill_code,
			String transition_code, String transition_type,	Timestamp transition_time, BigDecimal transition_amount,
			BigDecimal balance_amount, String brief, String remark) {
		TransitionVo vo = new TransitionVo(store_type, store_id, store_name, bill_code, 
				transition_code, transition_type, transition_time, transition_amount, balance_amount, brief, remark);
		return this.insert(vo);
	}
	
	@Transactional
	public int addInTransition(String store_type, Integer store_id, String store_name, String bill_code,
			String transition_code, BigDecimal transition_amount, BigDecimal balance_amount, String brief, String remark) {
		return addTransition(store_type, store_id, store_name, bill_code, 
				transition_code, TransitionVo.TRANSITION_TYPE_IN, DvDateHelper.getSysTimestamp(), 
				transition_amount, balance_amount, brief, remark);
	}
	
	@Transactional
	public int addOutTransition(String store_type, Integer store_id, String store_name, String bill_code,
			String transition_code, BigDecimal transition_amount, BigDecimal balance_amount, String brief, String remark) {
		return addTransition(store_type, store_id, store_name, bill_code, 
				transition_code, TransitionVo.TRANSITION_TYPE_OUT, DvDateHelper.getSysTimestamp(), 
				transition_amount.negate(), balance_amount, brief, remark);
	}
	
	@Transactional
	public int addInTransitionW(Integer warehouse_id, String warehouse_name, String bill_code,
			String transition_code, BigDecimal transition_amount, BigDecimal balance_amount, String brief, String remark) {
		return addInTransition(TransitionVo.STORE_TYPE_W, warehouse_id, warehouse_name, bill_code, 
				transition_code, transition_amount, balance_amount, brief, remark);
	}
	
	@Transactional
	public int addOutTransitionW(Integer warehouse_id, String warehouse_name, String bill_code,
			String transition_code, BigDecimal transition_amount, BigDecimal balance_amount, String brief, String remark) {
		return addOutTransition(TransitionVo.STORE_TYPE_W, warehouse_id, warehouse_name, bill_code, 
				transition_code, transition_amount, balance_amount, brief, remark);
	}
	
	@Transactional
	public int addInTransitionS(Integer shop_id, String shop_name, String bill_code,
			String transition_code, BigDecimal transition_amount, BigDecimal balance_amount, String brief, String remark) {
		return addInTransition(TransitionVo.STORE_TYPE_S, shop_id, shop_name, bill_code, 
				transition_code, transition_amount, balance_amount, brief, remark);
	}
	
	@Transactional
	public int addOutTransitionS(Integer shop_id, String shop_name, String bill_code,
			String transition_code, BigDecimal transition_amount, BigDecimal balance_amount, String brief, String remark) {
		return addOutTransition(TransitionVo.STORE_TYPE_S, shop_id, shop_name, bill_code, 
				transition_code, transition_amount, balance_amount, brief, remark);
	}
	
	@Transactional
	public int addInTransitionM(Integer market_id, String market_name, String bill_code,
			String transition_code, BigDecimal transition_amount, BigDecimal balance_amount, String brief, String remark) {
		return addInTransition(TransitionVo.STORE_TYPE_M, market_id, market_name, bill_code, 
				transition_code, transition_amount, balance_amount, brief, remark);
	}
	
	@Transactional
	public int addOutTransitionM(Integer market_id, String market_name, String bill_code,
			String transition_code, BigDecimal transition_amount, BigDecimal balance_amount, String brief, String remark) {
		return addOutTransition(TransitionVo.STORE_TYPE_M, market_id, market_name, bill_code, 
				transition_code, transition_amount, balance_amount, brief, remark);
	}
}
