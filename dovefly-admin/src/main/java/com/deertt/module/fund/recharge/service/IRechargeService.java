package com.deertt.module.fund.recharge.service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.fund.recharge.dao.IRechargeDao;
import com.deertt.module.fund.recharge.vo.RechargeVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IRechargeService extends IDvBaseService<IRechargeDao, RechargeVo, Integer> {
	
	/**
	 * 
	 * @param bill_code
	 * @return
	 */
	public RechargeVo findByCode(String bill_code);
	
	/**
	 * 支付成功并充值进账户
	 * @param id
	 * @param pay_type
	 * @param pay_code
	 * @param pay_amount
	 * @param pay_time
	 * @return
	 */
	public int paySuccessCallBack(Integer id, String pay_type, String pay_code, BigDecimal pay_amount, Timestamp pay_time);

}
