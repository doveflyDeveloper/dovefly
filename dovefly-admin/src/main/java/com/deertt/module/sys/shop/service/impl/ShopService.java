package com.deertt.module.sys.shop.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.fund.interest.service.IInterestService;
import com.deertt.module.fund.transition.service.ITransitionService;
import com.deertt.module.sys.role.vo.RoleVo;
import com.deertt.module.sys.shop.dao.IShopDao;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.shop.util.IShopConstants;
import com.deertt.module.sys.shop.vo.ShopVo;
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
public class ShopService extends DvBaseService<IShopDao, ShopVo, Integer> implements IShopService, IShopConstants {
	
	@Autowired
	private ITransitionService transitionService;
	
	@Autowired
	protected IInterestService interestService;
	
	@Transactional
	public int addHalfway_amount(Integer shop_id, BigDecimal change_amount) {
		ShopVo shop = this.find(shop_id);
		BigDecimal new_halfway_amount = shop.getHalfway_amount().add(change_amount);
		if (new_halfway_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户待收款金额不足！");
		}
		
		return this.update(SQL_UPDATE_HALFWAY_BY_ID, new Object[] { new_halfway_amount, shop_id });
	}
	
	@Transactional
	public int reduceHalfway_amount(Integer shop_id, BigDecimal change_amount) {
		ShopVo shop = this.find(shop_id);
		BigDecimal new_halfway_amount = shop.getHalfway_amount().subtract(change_amount);
		if (new_halfway_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户待收款金额不足！");
		}
		
		return this.update(SQL_UPDATE_HALFWAY_BY_ID, new Object[] { new_halfway_amount, shop_id });
	}
	
	@Transactional
	public String addOrderTurnbackAmount(Integer shop_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief) {
		return this.addBalance_amount(shop_id, bill_code, pay_code, transition_amount, brief, "ORDER_REFUND");
	}
	
	@Transactional
	public String addBalance_amount(Integer shop_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief, String remark) {
		ShopVo shop = this.find(shop_id);
		BigDecimal new_balance_amount = shop.getBalance_amount().add(transition_amount);
		if (new_balance_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户余额不足！");
		}

		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, shop.getLocked_amount(), shop_id });
		
		transitionService.addInTransitionS(shop.getId(), shop.getShop_name(), bill_code, pay_code, transition_amount, new_balance_amount, brief, remark);
		
		//如果当前还有贷款或利息未还完且余额已大于零，则需继续偿还贷款或利息，优先偿还贷款
		if (shop.getLoan_amount().add(shop.getInterest_amount()).compareTo(BigDecimal.ZERO) > 0) {
			
			BigDecimal turn_amount = BigDecimal.ZERO;
			if (new_balance_amount.compareTo(shop.getLoan_amount()) <= 0) {//余额<=贷款本金
				shop.setBalance_amount(BigDecimal.ZERO);
				shop.setLoan_amount(shop.getLoan_amount().subtract(new_balance_amount));
			} else if (new_balance_amount.compareTo(shop.getLoan_amount().add(shop.getInterest_amount())) <= 0){//贷款本金+利息>=余额>贷款本金
				shop.setBalance_amount(BigDecimal.ZERO);
				shop.setLoan_amount(BigDecimal.ZERO);
				shop.setInterest_amount(shop.getInterest_amount().subtract(new_balance_amount.subtract(shop.getLoan_amount())));
			} else {//贷款本金+利息<余额
				shop.setBalance_amount(new_balance_amount.subtract(shop.getLoan_amount().add(shop.getInterest_amount())));
				shop.setLoan_amount(BigDecimal.ZERO);
				shop.setInterest_amount(BigDecimal.ZERO);
			}
			turn_amount = new_balance_amount.subtract(shop.getBalance_amount());
			transitionService.addOutTransitionS(shop.getId(), shop.getShop_name(), bill_code, pay_code, turn_amount, shop.getBalance_amount(), "偿还贷款/利息" + new DecimalFormat("0.00").format(turn_amount) + "元", "INTEREST");

			this.update(SQL_UPDATE_LOAN_BY_ID, new Object[] { shop.getBalance_amount(), shop.getLoan_amount(), shop.getInterest_amount(), shop_id });
		}
		
		return pay_code;
	}
	
	@Transactional
	public String reduceBalance_amount(Integer shop_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief, String remark) {
		ShopVo shop = this.find(shop_id);
		BigDecimal new_balance_amount = shop.getBalance_amount().subtract(transition_amount);
		if (new_balance_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户余额不足！");
		}

		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, shop.getLocked_amount(), shop_id });
		
		transitionService.addOutTransitionS(shop.getId(), shop.getShop_name(), bill_code, pay_code, transition_amount, new_balance_amount, brief, remark);

		return pay_code;
	}

	@Transactional
	public String lockBalance_amount(Integer shop_id, String bill_code, BigDecimal locked_amount) {
		if (locked_amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("锁定金额必须大于零！");
		}
		ShopVo shop = this.find(shop_id);
		BigDecimal new_balance_amount = shop.getBalance_amount().subtract(locked_amount);
		BigDecimal new_locked_amount = shop.getLocked_amount().add(locked_amount);
		if (new_balance_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("账户可用余额不足！");
		}
		
		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, new_locked_amount, shop_id });
		
		String pay_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
		String brief = "提现申请单【" + bill_code + "】提交成功，待提款金额：" + locked_amount + "元。";
		transitionService.addOutTransitionS(shop.getId(), shop.getShop_name(), bill_code, pay_code, locked_amount, new_balance_amount, brief, "");

		return pay_code;
	}
	
	/**
	 * 
	 * @param shop_id
	 * @param bill_code
	 * @param locked_amount
	 * @return
	 */
	@Transactional
	public String turnbackLockedAmount(Integer shop_id, String bill_code, BigDecimal locked_amount) {
		if (locked_amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("返还锁定金额必须大于零！");
		}
		ShopVo shop = this.find(shop_id);
		BigDecimal new_balance_amount = shop.getBalance_amount().add(locked_amount);
		BigDecimal new_locked_amount = shop.getLocked_amount().subtract(locked_amount);
		if (new_locked_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("待提款金额不足！");
		}

		this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, new_locked_amount, shop_id });
		
		String pay_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
		String brief = "提现申请单【" + bill_code + "】取消成功，回流带提款金额：" + locked_amount + "元。";
		transitionService.addInTransitionS(shop.getId(), shop.getShop_name(), bill_code, pay_code, locked_amount, new_balance_amount, brief, "");

		//如果当前还有贷款或利息未还完且余额已大于零，则需继续偿还贷款或利息，优先偿还贷款
		if (shop.getLoan_amount().add(shop.getInterest_amount()).compareTo(BigDecimal.ZERO) > 0) {
			
			BigDecimal turn_amount = BigDecimal.ZERO;
			if (new_balance_amount.compareTo(shop.getLoan_amount()) <= 0) {//余额<=贷款本金
				shop.setBalance_amount(BigDecimal.ZERO);
				shop.setLoan_amount(shop.getLoan_amount().subtract(new_balance_amount));
			} else if (new_balance_amount.compareTo(shop.getLoan_amount().add(shop.getInterest_amount())) <= 0){//贷款本金+利息>=余额>贷款本金
				shop.setBalance_amount(BigDecimal.ZERO);
				shop.setLoan_amount(BigDecimal.ZERO);
				shop.setInterest_amount(shop.getInterest_amount().subtract(new_balance_amount.subtract(shop.getLoan_amount())));
			} else {//贷款本金+利息<余额
				shop.setBalance_amount(new_balance_amount.subtract(shop.getLoan_amount().add(shop.getInterest_amount())));
				shop.setLoan_amount(BigDecimal.ZERO);
				shop.setInterest_amount(BigDecimal.ZERO);
			}
			turn_amount = new_balance_amount.subtract(shop.getBalance_amount());
			transitionService.addOutTransitionS(shop.getId(), shop.getShop_name(), bill_code, pay_code, turn_amount, shop.getBalance_amount(), "偿还贷款/利息" + new DecimalFormat("0.00").format(turn_amount) + "元", "INTEREST");

			this.update(SQL_UPDATE_LOAN_BY_ID, new Object[] { shop.getBalance_amount(), shop.getLoan_amount(), shop.getInterest_amount(), shop_id });
		}
		
		return pay_code;
	}
	
	@Transactional
	public int useLockedAmount(Integer shop_id, String bill_code, BigDecimal locked_amount) {
		if (locked_amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("锁定金额必须大于零！");
		}
		ShopVo shop = this.find(shop_id);
		
		BigDecimal new_balance_amount = shop.getBalance_amount();
		BigDecimal new_locked_amount = shop.getLocked_amount().subtract(locked_amount);
		if (new_locked_amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("待提款金额不足！");
		}
		
		return this.update(SQL_UPDATE_BALANCE_BY_ID, new Object[] { new_balance_amount, new_locked_amount, shop_id });

	}
	
	public int addTodayInterestAmount(Integer id) {
		int sum = 0;
		ShopVo shop = this.find(id);
		if (shop.getLoan_amount().compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal totay_interest = interestService.addTodayInterest(shop.getId(), shop.getShop_name(), shop.getLoan_amount(), shop.getInterest_amount());
			shop.setInterest_amount(shop.getInterest_amount().add(totay_interest));
			sum = this.update(shop);
		}
		return sum;
	}
	
	@Override
	public List<Map<String, Object>> reportGroupByWarehouse(Integer warehouse_id, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUP_BY_WAREHOUSE;
		if (warehouse_id == null) {
			sql = sql.replace("AND warehouse_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (warehouse_id != null) params.add(warehouse_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);

		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}

	@Override
	public List<Map<String, Object>> reportGroupByManager(Integer warehouse_id, String manager_name, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUP_BY_MANAGER;
		if (warehouse_id == null) {
			sql = sql.replace("AND warehouse_id = ? ", "");
		}
		if (manager_name == null) {
			sql = sql.replace("AND manager_name = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (warehouse_id != null) params.add(warehouse_id);
		if (manager_name != null) params.add(manager_name);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);

		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}

	@Override
	public List<Map<String, Object>> reportGroupBySchool(Integer warehouse_id, Integer manager_id, String school_name, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUP_BY_SCHOOL;
		if (warehouse_id == null) {
			sql = sql.replace("AND warehouse_id = ? ", "");
		}
		if (manager_id == null) {
			sql = sql.replace("AND manager_id = ? ", "");
		}
		if (school_name == null) {
			sql = sql.replace("AND school_name = ? ", "");
		}
		
		List<Object> params = new ArrayList<Object>();
		if (warehouse_id != null) params.add(warehouse_id);
		if (manager_id != null) params.add(manager_id);
		if (school_name != null) params.add(school_name);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);

		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	@Transactional
	public void closeShop(Integer[] ids) {
		//TODO 这里要检查一堆未完成的数据，如，是否有未完成订单，是否未清算完的资金等；
		
		for (int i = 0; i < ids.length; i++) {
			
			//TODO 设置此店禁用
			this.update("update sys_shop set status = '0' where id = " + ids[i]);
			
			//TODO 设置用户不再为此店的店长
			this.update("update sys_user set manager_shop_id = 0 where manager_shop_id = " + ids[i]);
			
			//TODO 移除店长角色身份
			this.update("delete from sys_authority where user_id in (select id from sys_user where manager_shop_id = " + ids[i]+ ") and role_id = " + RoleVo.ROLE_SHOPKEEPER);
		}
		
	}
}
