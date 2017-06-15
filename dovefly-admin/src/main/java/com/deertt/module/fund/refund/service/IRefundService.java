package com.deertt.module.fund.refund.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.fund.refund.dao.IRefundDao;
import com.deertt.module.fund.refund.vo.RefundVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IRefundService extends IDvBaseService<IRefundDao, RefundVo, Integer> {
	
	/**
	 * 添加店长进货单退款记录
	 * @param bill_type
	 * @param seller_id
	 * @param seller_name
	 * @param buyer_id
	 * @param buyer_name
	 * @param refer_bill_code
	 * @param refund_to
	 * @param refund_amount
	 * @return
	 */
	public int addORefund(Integer seller_id, String seller_name, Integer buyer_id, String buyer_name, String refer_bill_code, String refund_to, BigDecimal refund_amount);

	/**
	 * 添加用户订单退款记录
	 * @param bill_type
	 * @param seller_id
	 * @param seller_name
	 * @param buyer_id
	 * @param buyer_name
	 * @param refer_bill_code
	 * @param refund_to
	 * @param refund_amount
	 * @return
	 */
	public int addTRefund(Integer seller_id, String seller_name, Integer buyer_id, String buyer_name, String refer_bill_code, String refund_to, BigDecimal refund_amount);

	/**
	 * 添加超市订单退款记录
	 * @param bill_type
	 * @param seller_id
	 * @param seller_name
	 * @param buyer_id
	 * @param buyer_name
	 * @param refer_bill_code
	 * @param refund_to
	 * @param refund_amount
	 * @return
	 */
	public int addMRefund(Integer seller_id, String seller_name, Integer buyer_id, String buyer_name, String refer_bill_code, String refund_to, BigDecimal refund_amount);
	
	/**
	 * 添加退款记录
	 * @param bill_type
	 * @param seller_id
	 * @param seller_name
	 * @param buyer_id
	 * @param buyer_name
	 * @param refer_bill_code
	 * @param refund_to
	 * @param refund_amount
	 * @return
	 */
	public int addRefund(String bill_type, Integer seller_id, String seller_name, Integer buyer_id, String buyer_name, String refer_bill_code, String refund_to, BigDecimal refund_amount);
	
	/**
	 * 查询退款申请单据
	 * @param bill_code
	 * @return
	 */
	public RefundVo findByCode(String bill_code);
	
	/**
	 * 根据原订单号查询退款申请单据
	 * @param refer_bill_code
	 * @return
	 */
	public RefundVo findByReferBillCode(String refer_bill_code);
	
	/**
	 * 统计各状态下的单量
	 * @param queryCondition
	 * @return
	 */
	public List<Map<String, Object>> reportCountGroupByStatus(String queryCondition);
	
}
