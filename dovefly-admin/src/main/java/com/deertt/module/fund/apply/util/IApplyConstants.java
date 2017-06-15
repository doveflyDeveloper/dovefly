package com.deertt.module.fund.apply.util;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.order.back.vo.OrderBackBillVo;
import com.deertt.module.order.bill.vo.OrderBillVo;
import com.deertt.module.trade.bill.vo.TradeBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IApplyConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "applyController:read";
	
	public final static String PERM_WRITE = "applyController:*";
	
	//URL
	public final static String CONTROLLER = "/applyController";
	
	public final static String DEFAULT_REDIRECT = "/applyController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/fund/apply";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/fund/apply/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, store_type, store_id, store_name, user_id, user_name, bill_code, apply_time, apply_amount, apply_to, receive_account, receive_real_name, brief, pay_type, pay_code, pay_amount, pay_time, pay_status, pay_msg, remark, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, store_type, store_id, store_name, user_id, user_name, bill_code, apply_time, apply_amount, apply_to, receive_account, receive_real_name, brief, pay_type, pay_code, pay_amount, pay_time, pay_status, pay_msg, remark, status";

	public final static String SQL_INSERT = "INSERT INTO fund_apply ( store_type, store_id, store_name, user_id, user_name, bill_code, apply_time, apply_amount, apply_to, receive_account, receive_real_name, brief, pay_type, pay_code, pay_amount, pay_time, pay_status, pay_msg, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM fund_apply WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM fund_apply";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM fund_apply WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE fund_apply SET store_type = ?, store_id = ?, store_name = ?, user_id = ?, user_name = ?, bill_code = ?, apply_time = ?, apply_amount = ?, apply_to = ?, receive_account = ?, receive_real_name = ?, brief = ?, pay_type = ?, pay_code = ?, pay_amount = ?, pay_time = ?, pay_status = ?, pay_msg = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";
	
	public final static String SQL_COUNT = "SELECT COUNT(id) FROM fund_apply";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM fund_apply";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM fund_apply";
	
	public final static String SQL_CHECK_AMOUNT_CITYMANAGER = 
		"select t0.balance_amount + t0.locked_amount real_amount, t0.balance_amount, t0.locked_amount, t1.transition_amount, "
		+ "(t2.order_amount + t3.recharge_amount - t4.withdraw_amount + t5.trade_amount - t6.order_back_amount - t7.interest_amount - t8.coupon_amount ) all_amount, "
		+ "t2.order_amount, t3.recharge_amount, t4.withdraw_amount, t5.trade_amount, t6.order_back_amount, t7.interest_amount, t8.coupon_amount from "
		+ "(select ifnull(sum(balance_amount), 0) balance_amount, ifnull(sum(locked_amount), 0) locked_amount from sys_user where id = #user_id#) t0, "
		+ "(select ifnull(sum(transition_amount), 0) transition_amount from fund_transition where user_id = #user_id#) t1, "
		+ "(select ifnull(sum(income_amount), 0) order_amount from order_bill where seller_id = #user_id# and pay_status = '1' and status = '" + OrderBillVo.STATUS_RECEIVED + "') t2, "
		+ "(select ifnull(sum(recharge_amount), 0) recharge_amount from fund_recharge where receive_id = #user_id# and pay_status = '1') t3, "
		+ "(select ifnull(sum(apply_amount), 0) withdraw_amount from fund_apply where user_id = #user_id# and pay_status = '2') t4, "
		+ "(select ifnull(sum(total_amount), 0) trade_amount from trade_bill where seller_id = #user_id# and pay_status = '1' and status = '" + TradeBillVo.STATUS_RECEIVED + "') t5, "
		+ "(select ifnull(sum(amount), 0) order_back_amount from order_back_bill where warehouse_id = #warehouse_id# and pay_status = '1' and status = '" + OrderBackBillVo.STATUS_RECEIVED + "') t6, "
		+ "(select ifnull(sum(-transition_amount), 0) interest_amount from fund_transition where user_id = #user_id# and remark = 'INTEREST') t7, "
		+ "(select ifnull(sum(-transition_amount), 0) coupon_amount from fund_transition where user_id = #user_id# and remark = 'COUPON') t8";
		
	public final static String SQL_CHECK_AMOUNT_SHOP = 
		"select t0.balance_amount + t0.locked_amount real_amount, t0.balance_amount, t0.locked_amount, t1.transition_amount, "
		+ "(-t2.order_amount + t3.recharge_amount - t4.withdraw_amount + t5.trade_amount + t6.order_back_amount - t7.interest_amount + t8.order_refund_amount) all_amount, "
		+ "t2.order_amount, t3.recharge_amount, t4.withdraw_amount, t5.trade_amount, t6.order_back_amount, t7.interest_amount, t8.order_refund_amount from "
		+ "(select ifnull(sum(balance_amount), 0) balance_amount, ifnull(sum(locked_amount), 0) locked_amount from sys_shop where id = #shop_id#) t0, "
		+ "(select ifnull(sum(transition_amount), 0) transition_amount from fund_transition where store_id = #shop_id#) t1, "
		+ "(select ifnull(sum(pay_amount), 0) order_amount from order_bill where shop_id = #shop_id# and pay_type = 'ttpay' and pay_status = '1' "
		+ "and status in ('" + OrderBillVo.STATUS_SUBMIT + "','" + OrderBillVo.STATUS_DELIVERED + "','" + OrderBillVo.STATUS_RECEIVED
		+ "','" + OrderBillVo.STATUS_APPLY_FOR_REFUND + "','" + OrderBillVo.STATUS_APPLY_FOR_RETURN + "','" + OrderBillVo.STATUS_AGREE_TO_REFUND + "','" + OrderBillVo.STATUS_AGREE_TO_RETURN + "')) t2, "
		+ "(select ifnull(sum(recharge_amount), 0) recharge_amount from fund_recharge where store_id = #shop_id# and pay_status = '1') t3, "
		+ "(select ifnull(sum(apply_amount), 0) withdraw_amount from fund_apply where store_id = #shop_id# and pay_status = '2') t4, "
		+ "(select ifnull(sum(total_amount), 0) trade_amount from trade_bill where shop_id = #shop_id# and pay_status = '1' and status = '" + TradeBillVo.STATUS_RECEIVED + "') t5, "
		+ "(select ifnull(sum(amount), 0) order_back_amount from order_back_bill where shop_id = #shop_id# and pay_status = '1' and status = '" + OrderBackBillVo.STATUS_RECEIVED + "') t6, "
		+ "(select ifnull(sum(-transition_amount), 0) interest_amount from fund_transition where store_id = #shop_id# and remark = 'INTEREST') t7, "
		+ "(select ifnull(sum(transition_amount), 0) order_refund_amount from fund_transition where store_id = #shop_id# and remark = 'ORDER_REFUND') t8 ";
		
	public final static String SQL_REPORT_GROUP_BY_STATUS = "SELECT status, count(id) bill_count FROM fund_apply #WHERE# GROUP BY status WITH ROLLUP";
	
	/**表名*/
	public final static String TABLE_NAME = "fund_apply";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "提现申请";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
