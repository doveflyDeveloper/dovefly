package com.deertt.module.fund.interest.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.fund.interest.dao.IInterestDao;
import com.deertt.module.fund.interest.service.IInterestService;
import com.deertt.module.fund.interest.util.IInterestConstants;
import com.deertt.module.fund.interest.vo.InterestVo;
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
public class InterestService extends DvBaseService<IInterestDao, InterestVo, Integer> implements IInterestService, IInterestConstants {
	
	@Transactional
	public BigDecimal addTodayInterest(Integer shop_id, String shop_name, BigDecimal loan_amount, BigDecimal base_interest_amount) {
		BigDecimal totay_interest = BigDecimal.ZERO;
		if (loan_amount.compareTo(BigDecimal.ZERO) > 0) {
			totay_interest = loan_amount.multiply(DAY_RATE).setScale(2, BigDecimal.ROUND_HALF_UP);
			if (totay_interest.compareTo(new BigDecimal(0.01)) < 0) {
				totay_interest = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			//增加利息流水记录
			InterestVo interestVo = new InterestVo();
			interestVo.setShop_id(shop_id);
			interestVo.setShop_name(shop_name);
			interestVo.setInterest_date(DvDateHelper.getSysDate());
			interestVo.setCapital_amount(loan_amount);
			interestVo.setInterest_amount(totay_interest);
			interestVo.setAll_interest_amount(base_interest_amount.add(totay_interest));
			interestVo.setInterest_rate(DAY_RATE);
			DecimalFormat df = new DecimalFormat("0.00");
			interestVo.setBrief("本日利息：" + df.format(totay_interest) + "元，共计利息：" + df.format(interestVo.getAll_interest_amount()) + "元");
			interestVo.setCreate_at(DvDateHelper.getSysTimestamp());
			this.insert(interestVo);
		}
		return totay_interest;
	}
}
