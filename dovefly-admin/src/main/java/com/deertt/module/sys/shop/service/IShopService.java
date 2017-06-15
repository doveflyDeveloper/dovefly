package com.deertt.module.sys.shop.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.shop.dao.IShopDao;
import com.deertt.module.sys.shop.vo.ShopVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IShopService extends IDvBaseService<IShopDao, ShopVo, Integer> {
		
	/**
	 * 增加待收款金额
	 * @param shop_id
	 * @param change_amount 增加的金额（正数）
	 * @return
	 */
	public int addHalfway_amount(Integer shop_id, BigDecimal change_amount);
	
	/**
	 * 减少待收款金额
	 * @param shop_id
	 * @param change_amount 减少的金额（正数）
	 * @return
	 */
	public int reduceHalfway_amount(Integer shop_id, BigDecimal change_amount);
	
	/**
	 * 
	 * @param shop_id
	 * @param bill_code
	 * @param pay_code
	 * @param transition_amount
	 * @param brief
	 * @return
	 */
	public String addBalance_amount(Integer shop_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief, String remark);
	
	/**
	 * 
	 * @param shop_id
	 * @param bill_code
	 * @param pay_code
	 * @param transition_amount
	 * @param brief
	 * @return
	 */
	public String reduceBalance_amount(Integer shop_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief, String remark);
	
	/**
	 * 
	 * @param shop_id
	 * @param bill_code
	 * @param pay_code
	 * @param transition_amount
	 * @param brief
	 * @return
	 */
	public String addOrderTurnbackAmount(Integer shop_id, String bill_code, String pay_code, BigDecimal transition_amount, String brief);
	
	/**
	 * 提现申请，锁定余额
	 * @param shop_id
	 * @param bill_code
	 * @param locked_amount
	 * @return
	 */
	public String lockBalance_amount(Integer shop_id, String bill_code, BigDecimal locked_amount);
	
	/**
	 * 提现申请失败或拒绝，退回锁定余额
	 * @param shop_id
	 * @param bill_code
	 * @param locked_amount
	 * @return
	 */
	public String turnbackLockedAmount(Integer shop_id, String bill_code, BigDecimal locked_amount);
	
	/**
	 * 提现成功，减少锁定余额
	 * @param shop_id
	 * @param bill_code
	 * @param locked_amount
	 * @return
	 */
	public int useLockedAmount(Integer shop_id, String bill_code, BigDecimal locked_amount);
	
	/**
	 * 计算店铺当日利息（如果有贷款的话）
	 * @param id
	 * @return
	 */
	public int addTodayInterestAmount(Integer id);
	
	/**
	 * 根据货仓分组统计
	 * @param city_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByWarehouse(Integer warehouse_id, String from_date, String to_date);
	
	/**
	 * 根据运营主管分组统计
	 * @param warehouse_id
	 * @param manager_name
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByManager(Integer warehouse_id, String manager_name, String from_date, String to_date);
	
	/**
	 * 根据学校分组统计
	 * @param warehouse_id
	 * @param manager_id
	 * @param school_name
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupBySchool(Integer warehouse_id, Integer manager_id, String school_name, String from_date, String to_date);
	
	/**
	 * 关停店
	 * @param ids
	 */
	public void closeShop(Integer[] ids);

}
