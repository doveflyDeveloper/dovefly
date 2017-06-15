package com.deertt.module.sys.warehouse.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.fund.transition.service.ITransitionService;
import com.deertt.module.sys.warehouse.dao.IWarehouseDao;
import com.deertt.module.sys.warehouse.service.IWarehouseService;
import com.deertt.module.sys.warehouse.util.IWarehouseConstants;
import com.deertt.module.sys.warehouse.vo.WarehouseVo;
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
public class WarehouseService extends DvBaseService<IWarehouseDao, WarehouseVo, Integer> implements IWarehouseService, IWarehouseConstants {
	
	@Autowired
	private ITransitionService transitionService;
	
	@Transactional
	public int addHalfway_amount(Integer warehouse_id, BigDecimal change_amount) {
		WarehouseVo warehouse = this.find(warehouse_id);
		BigDecimal new_halfway_amount = warehouse.getHalfway_amount().add(change_amount);
		if (new_halfway_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户待收款金额不足！");
		}
		
		return this.update(SQL_UPDATE_HALFWAY_BY_ID, new Object[] { new_halfway_amount, warehouse_id });
	}
	
	@Transactional
	public int reduceHalfway_amount(Integer warehouse_id, BigDecimal change_amount) {
		WarehouseVo warehouse = this.find(warehouse_id);
		BigDecimal new_halfway_amount = warehouse.getHalfway_amount().subtract(change_amount);
		if (new_halfway_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户待收款金额不足！");
		}
		
		return this.update(SQL_UPDATE_HALFWAY_BY_ID, new Object[] { new_halfway_amount, warehouse_id });
	}
	
	/**
	 * 遵义经理专用，代他人垫付优惠券金额
	 * @param warehouse_id
	 * @param bill_code
	 * @param pay_code
	 * @param transition_type
	 * @param transition_amount 优惠金额，正数
	 * @param brief
	 * @return
	 */
	@Transactional
	public String payForCoupon(Integer warehouse_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief) {
		
		//用此信息标识为代付优惠券类型，以便在提现时检查代付金额
		return this.reduceBalance_amount(warehouse_id, bill_code, pay_code, transition_amount, brief, "COUPON");
	}
	
	@Transactional
	public String addBalance_amount(Integer warehouse_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief, String remark) {
		WarehouseVo warehouse = this.find(warehouse_id);
		BigDecimal new_balance_amount = warehouse.getBalance_amount().add(transition_amount);
		if (new_balance_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户余额不足！");
		}

		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, warehouse.getLocked_amount(), warehouse_id });
		
		transitionService.addInTransitionW(warehouse.getId(), warehouse.getWarehouse_name(), bill_code, pay_code, transition_amount, new_balance_amount, brief, remark);
		//如果是入账，且余额大于零，且利息大于零，则需继续偿还利息
//		if (TransitionVo.TRANSITION_TYPE_IN.equals(transition_type) 
//				&& warehouse.getBalance_amount().compareTo(BigDecimal.ZERO) > 0 
//				&& warehouse.getInterest_amount().compareTo(BigDecimal.ZERO) > 0) {
//			
//			BigDecimal turn_interest_amount = BigDecimal.ZERO;
//			if (warehouse.getBalance_amount().compareTo(warehouse.getInterest_amount()) >= 0) {//本金大于利息
//				turn_interest_amount = warehouse.getInterest_amount();
//				warehouse.setBalance_amount(warehouse.getBalance_amount().subtract(warehouse.getInterest_amount()));
//				warehouse.setInterest_amount(BigDecimal.ZERO);
//			} else {
//				turn_interest_amount = warehouse.getBalance_amount();
//				warehouse.setBalance_amount(BigDecimal.ZERO);
//				warehouse.setInterest_amount(warehouse.getInterest_amount().subtract(warehouse.getBalance_amount()));
//			}
//			
//    		String transition_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
//			TransitionVo transition_interest = new TransitionVo(warehouse.getId(), warehouse.getWarehouse_name(), bill_code, 
//					transition_code, TransitionVo.TRANSITION_TYPE_OUT,
//					transition_time, turn_interest_amount.negate(),
//					warehouse.getBalance_amount(), "偿还利息" + new DecimalFormat("0.00").format(turn_interest_amount) + "元", "", "1");
//			
//			transition_interest.setRemark("INTEREST");//用此信息标识为还息类型，以便在提现时检查还款记录
//			trasitionDao.insert(transition_interest);
//			getDao().changeBalance_amount(warehouse_id, warehouse.getBalance_amount(), warehouse.getLocked_amount(), warehouse.getInterest_amount());
//		}
		
		return pay_code;
	}
	
	@Transactional
	public String reduceBalance_amount(Integer warehouse_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief, String remark) {
		WarehouseVo warehouse = this.find(warehouse_id);
		BigDecimal new_balance_amount = warehouse.getBalance_amount().subtract(transition_amount);
		if (new_balance_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户余额不足！");
		}

		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, warehouse.getLocked_amount(), warehouse_id });
		
		transitionService.addOutTransitionW(warehouse.getId(), warehouse.getWarehouse_name(), bill_code, pay_code, transition_amount, new_balance_amount, brief, remark);

		return pay_code;
	}

	@Transactional
	public String lockBalance_amount(Integer warehouse_id, String bill_code, BigDecimal locked_amount) {
		if (locked_amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("锁定金额必须大于零！");
		}
		WarehouseVo warehouse = this.find(warehouse_id);
		BigDecimal new_balance_amount = warehouse.getBalance_amount().subtract(locked_amount);
		BigDecimal new_locked_amount = warehouse.getLocked_amount().add(locked_amount);
		if (new_balance_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户可用余额不足！");
		}
		
		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, new_locked_amount, warehouse_id });
		
		String pay_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
		String brief = "提现申请单【" + bill_code + "】提交成功，待提款金额：" + locked_amount + "元。";
		transitionService.addOutTransitionW(warehouse.getId(), warehouse.getWarehouse_name(), bill_code, pay_code, locked_amount, new_balance_amount, brief, "");

		return pay_code;
	}
	
	/**
	 * 
	 * @param warehouse_id
	 * @param bill_code
	 * @param locked_amount
	 * @return
	 */
	@Transactional
	public String turnbackLockedAmount(Integer warehouse_id, String bill_code, BigDecimal locked_amount) {
		if (locked_amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("返还锁定金额必须大于零！");
		}
		WarehouseVo warehouse = this.find(warehouse_id);
		BigDecimal new_balance_amount = warehouse.getBalance_amount().add(locked_amount);
		BigDecimal new_locked_amount = warehouse.getLocked_amount().subtract(locked_amount);
		if (new_locked_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("待提款金额不足！");
		}

		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, new_locked_amount, warehouse_id });
		
		String pay_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
		String brief = "提现申请单【" + bill_code + "】取消成功，回流带提款金额：" + locked_amount + "元。";
		transitionService.addInTransitionW(warehouse.getId(), warehouse.getWarehouse_name(), bill_code, pay_code, locked_amount, new_balance_amount, brief, "");

		return pay_code;
	}
	
	@Transactional
	public int useLockedAmount(Integer warehouse_id, String bill_code, BigDecimal locked_amount) {
		if (locked_amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("锁定金额必须大于零！");
		}
		WarehouseVo warehouse = this.find(warehouse_id);
		
		BigDecimal new_balance_amount = warehouse.getBalance_amount();
		BigDecimal new_locked_amount = warehouse.getLocked_amount().subtract(locked_amount);
		if (new_locked_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("待提款金额不足！");
		}
		
		return this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, new_locked_amount, warehouse_id });

	}

}
