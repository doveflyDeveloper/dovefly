package com.deertt.module.sys.market.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.fund.transition.service.ITransitionService;
import com.deertt.module.sys.market.dao.IMarketDao;
import com.deertt.module.sys.market.service.IMarketService;
import com.deertt.module.sys.market.util.IMarketConstants;
import com.deertt.module.sys.market.vo.MarketVo;
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
public class MarketService extends DvBaseService<IMarketDao, MarketVo, Integer> implements IMarketService, IMarketConstants {
	
	@Autowired
	private ITransitionService transitionService;
	
	@Transactional
	public int addHalfway_amount(Integer market_id, BigDecimal change_amount) {
		MarketVo market = this.find(market_id);
		BigDecimal new_halfway_amount = market.getHalfway_amount().add(change_amount);
		if (new_halfway_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户待收款金额不足！");
		}
		
		return this.update(SQL_UPDATE_HALFWAY_BY_ID, new Object[] { new_halfway_amount, market_id });
	}
	
	@Transactional
	public int reduceHalfway_amount(Integer market_id, BigDecimal change_amount) {
		MarketVo market = this.find(market_id);
		BigDecimal new_halfway_amount = market.getHalfway_amount().subtract(change_amount);
		if (new_halfway_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户待收款金额不足！");
		}
		
		return this.update(SQL_UPDATE_HALFWAY_BY_ID, new Object[] { new_halfway_amount, market_id });
	}
	
	@Transactional
	public String addBalance_amount(Integer market_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief, String remark) {
		MarketVo market = this.find(market_id);
		BigDecimal new_balance_amount = market.getBalance_amount().add(transition_amount);
		if (new_balance_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户余额不足！");
		}

		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, market.getLocked_amount(), market_id });
		
		transitionService.addInTransitionM(market.getId(), market.getMarket_name(), bill_code, pay_code, transition_amount, new_balance_amount, brief, remark);
		//如果是入账，且余额大于零，且利息大于零，则需继续偿还利息
//		if (TransitionVo.TRANSITION_TYPE_IN.equals(transition_type) 
//				&& market.getBalance_amount().compareTo(BigDecimal.ZERO) > 0 
//				&& market.getInterest_amount().compareTo(BigDecimal.ZERO) > 0) {
//			
//			BigDecimal turn_interest_amount = BigDecimal.ZERO;
//			if (market.getBalance_amount().compareTo(market.getInterest_amount()) >= 0) {//本金大于利息
//				turn_interest_amount = market.getInterest_amount();
//				market.setBalance_amount(market.getBalance_amount().subtract(market.getInterest_amount()));
//				market.setInterest_amount(BigDecimal.ZERO);
//			} else {
//				turn_interest_amount = market.getBalance_amount();
//				market.setBalance_amount(BigDecimal.ZERO);
//				market.setInterest_amount(market.getInterest_amount().subtract(market.getBalance_amount()));
//			}
//			
//    		String transition_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
//			TransitionVo transition_interest = new TransitionVo(market.getId(), market.getMarket_name(), bill_code, 
//					transition_code, TransitionVo.TRANSITION_TYPE_OUT,
//					transition_time, turn_interest_amount.negate(),
//					market.getBalance_amount(), "偿还利息" + new DecimalFormat("0.00").format(turn_interest_amount) + "元", "", "1");
//			
//			transition_interest.setRemark("INTEREST");//用此信息标识为还息类型，以便在提现时检查还款记录
//			trasitionDao.insert(transition_interest);
//			getDao().changeBalance_amount(market_id, market.getBalance_amount(), market.getLocked_amount(), market.getInterest_amount());
//		}
		
		return pay_code;
	}
	
	@Transactional
	public String reduceBalance_amount(Integer market_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief, String remark) {
		MarketVo market = this.find(market_id);
		BigDecimal new_balance_amount = market.getBalance_amount().subtract(transition_amount);
		if (new_balance_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户余额不足！");
		}

		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, market.getLocked_amount(), market_id });
		
		transitionService.addOutTransitionM(market.getId(), market.getMarket_name(), bill_code, pay_code, transition_amount, new_balance_amount, brief, remark);

		return pay_code;
	}

	@Transactional
	public String lockBalance_amount(Integer market_id, String bill_code, BigDecimal locked_amount) {
		if (locked_amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("锁定金额必须大于零！");
		}
		MarketVo market = this.find(market_id);
		BigDecimal new_balance_amount = market.getBalance_amount().subtract(locked_amount);
		BigDecimal new_locked_amount = market.getLocked_amount().add(locked_amount);
		if (new_balance_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户可用余额不足！");
		}
		
		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, new_locked_amount, market_id });
		
		String pay_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
		String brief = "提现申请单【" + bill_code + "】提交成功，待提款金额：" + locked_amount + "元。";
		transitionService.addOutTransitionM(market.getId(), market.getMarket_name(), bill_code, pay_code, locked_amount, new_balance_amount, brief, "");

		return pay_code;
	}
	
	/**
	 * 
	 * @param market_id
	 * @param bill_code
	 * @param locked_amount
	 * @return
	 */
	@Transactional
	public String turnbackLockedAmount(Integer market_id, String bill_code, BigDecimal locked_amount) {
		if (locked_amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("返还锁定金额必须大于零！");
		}
		MarketVo market = this.find(market_id);
		BigDecimal new_balance_amount = market.getBalance_amount().add(locked_amount);
		BigDecimal new_locked_amount = market.getLocked_amount().subtract(locked_amount);
		if (new_locked_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("待提款金额不足！");
		}

		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, new_locked_amount, market_id });
		
		String pay_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
		String brief = "提现申请单【" + bill_code + "】取消成功，回流带提款金额：" + locked_amount + "元。";
		transitionService.addInTransitionM(market.getId(), market.getMarket_name(), bill_code, pay_code, locked_amount, new_balance_amount, brief, "");

		return pay_code;
	}
	
	@Transactional
	public int useLockedAmount(Integer market_id, String bill_code, BigDecimal locked_amount) {
		if (locked_amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("锁定金额必须大于零！");
		}
		MarketVo market = this.find(market_id);
		
		BigDecimal new_balance_amount = market.getBalance_amount();
		BigDecimal new_locked_amount = market.getLocked_amount().subtract(locked_amount);
		if (new_locked_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("待提款金额不足！");
		}
		
		return this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, new_locked_amount, market_id });

	}

}
