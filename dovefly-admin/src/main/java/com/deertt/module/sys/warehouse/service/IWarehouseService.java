package com.deertt.module.sys.warehouse.service;

import java.math.BigDecimal;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.warehouse.dao.IWarehouseDao;
import com.deertt.module.sys.warehouse.vo.WarehouseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IWarehouseService extends IDvBaseService<IWarehouseDao, WarehouseVo, Integer> {

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
	public String payForCoupon(Integer warehouse_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief);
	
	/**
	 * 增加待收款金额
	 * @param warehouse_id
	 * @param change_amount 增加的金额（正数）
	 * @return
	 */
	public int addHalfway_amount(Integer warehouse_id, BigDecimal change_amount);
	
	/**
	 * 减少待收款金额
	 * @param warehouse_id
	 * @param change_amount 减少的金额（正数）
	 * @return
	 */
	public int reduceHalfway_amount(Integer warehouse_id, BigDecimal change_amount);
	
	/**
	 * 
	 * @param warehouse_id
	 * @param bill_code
	 * @param pay_code
	 * @param transition_amount
	 * @param brief
	 * @return
	 */
	public String addBalance_amount(Integer warehouse_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief, String remark);
	
	/**
	 * 
	 * @param warehouse_id
	 * @param bill_code
	 * @param pay_code
	 * @param transition_amount
	 * @param brief
	 * @return
	 */
	public String reduceBalance_amount(Integer warehouse_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief, String remark);
		
	/**
	 * 提现申请，锁定余额
	 * @param warehouse_id
	 * @param bill_code
	 * @param locked_amount
	 * @return
	 */
	public String lockBalance_amount(Integer warehouse_id, String bill_code, BigDecimal locked_amount);
	
	/**
	 * 提现申请失败或拒绝，退回锁定余额
	 * @param warehouse_id
	 * @param bill_code
	 * @param locked_amount
	 * @return
	 */
	public String turnbackLockedAmount(Integer warehouse_id, String bill_code, BigDecimal locked_amount);
	
	/**
	 * 提现成功，减少锁定余额
	 * @param warehouse_id
	 * @param bill_code
	 * @param locked_amount
	 * @return
	 */
	public int useLockedAmount(Integer warehouse_id, String bill_code, BigDecimal locked_amount);

}
