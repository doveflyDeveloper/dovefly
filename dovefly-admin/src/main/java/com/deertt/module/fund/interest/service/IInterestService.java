package com.deertt.module.fund.interest.service;

import java.math.BigDecimal;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.fund.interest.dao.IInterestDao;
import com.deertt.module.fund.interest.vo.InterestVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IInterestService extends IDvBaseService<IInterestDao, InterestVo, Integer> {
	
	/**
	 * 
	 * @param shop_id
	 * @param shop_name
	 * @param loan_amount
	 * @param base_interest_amount
	 * @return
	 */
	public BigDecimal addTodayInterest(Integer shop_id, String shop_name, BigDecimal loan_amount, BigDecimal base_interest_amount);
}
