package com.deertt.module.fund.apply.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.fund.apply.dao.IApplyDao;
import com.deertt.module.fund.apply.vo.ApplyVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IApplyService extends IDvBaseService<IApplyDao, ApplyVo, Integer> {
	
	/**
	 * 新增货仓支付申请
	 * @param store_id
	 * @param store_name
	 * @param user_id
	 * @param user_name
	 * @param receive_account
	 * @param receive_real_name
	 * @param apply_to
	 * @param apply_amount
	 * @return
	 */
	public int addWApply(Integer store_id, String store_name, Integer user_id, String user_name, String receive_account, String receive_real_name, String apply_to, BigDecimal apply_amount);

	/**
	 * 新增店铺支付申请
	 * @param store_id
	 * @param store_name
	 * @param user_id
	 * @param user_name
	 * @param receive_account
	 * @param receive_real_name
	 * @param apply_to
	 * @param apply_amount
	 * @return
	 */
	public int addSApply(Integer store_id, String store_name, Integer user_id, String user_name, String receive_account, String receive_real_name, String apply_to, BigDecimal apply_amount);
	
	/**
	 * 新增超市支付申请
	 * @param store_id
	 * @param store_name
	 * @param user_id
	 * @param user_name
	 * @param receive_account
	 * @param receive_real_name
	 * @param apply_to
	 * @param apply_amount
	 * @return
	 */
	public int addMApply(Integer store_id, String store_name, Integer user_id, String user_name, String receive_account, String receive_real_name, String apply_to, BigDecimal apply_amount);
	
	/**
	 * 新增支付申请
	 * @param store_type
	 * @param store_id
	 * @param store_name
	 * @param user_id
	 * @param user_name
	 * @param receive_account
	 * @param receive_real_name
	 * @param apply_to
	 * @param apply_amount
	 * @return
	 */
	public int addApply(String store_type, Integer store_id, String store_name, Integer user_id, String user_name, String receive_account, String receive_real_name, String apply_to, BigDecimal apply_amount);
	
	/**
	 * 查询提现申请单据
	 * @param bill_code
	 * @return
	 */
	public ApplyVo findByCode(String bill_code);
	
	/**
	 * 提现申请拒绝
	 * @param id
	 * @param reason
	 * @return
	 */
	public int deny(Integer id, String reason);
	
	/**
	 * 支付处理
	 * @param bill_code
	 * @param pay_type
	 * @param pay_code
	 * @param pay_amount
	 * @param pay_time
	 * @return
	 */
	public int paying(String bill_code, String pay_type, String pay_code, BigDecimal pay_amount, Timestamp pay_time);
	
	/**
	 * 支付成功回调
	 * @param bill_code
	 * @return
	 */
	public int paySuccessCallBack(String bill_code);
	
	/**
	 * 支付失败回调
	 * @param bill_code
	 * @param pay_msg
	 * @return
	 */
	public int payFailCallBack(String bill_code, String pay_msg);
	
	/**
	 * 统计各状态下的单量
	 * @param queryCondition
	 * @return
	 */
	public List<Map<String, Object>> reportCountGroupByStatus(String queryCondition);
	
}
