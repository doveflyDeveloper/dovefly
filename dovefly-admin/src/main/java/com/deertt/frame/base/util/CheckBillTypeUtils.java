package com.deertt.frame.base.util;

import com.deertt.module.fund.apply.vo.ApplyVo;
import com.deertt.module.fund.recharge.vo.RechargeVo;
import com.deertt.module.fund.refund.vo.RefundVo;
import com.deertt.module.order.bill.vo.OrderBillVo;
import com.deertt.module.purchase.bill.vo.PurchaseBillVo;
import com.deertt.module.trade.bill.vo.TradeBillVo;

public class CheckBillTypeUtils {

	/**
	 * 是否是店长进货单
	 * @param bill_code
	 * @return
	 */
	public static boolean isOrderBill(String bill_code) {
		return bill_code != null && bill_code.startsWith(OrderBillVo.BILL_CODE_PREFIX);
	}
	
	/**
	 * 是否是采购订单
	 * @param bill_code
	 * @return
	 */
	public static boolean isPurchaseBill(String bill_code) {
		return bill_code != null && bill_code.startsWith(PurchaseBillVo.BILL_CODE_PREFIX);
	}
	
	/**
	 * 是否是销售订单
	 * @param bill_code
	 * @return
	 */
	public static boolean isTradeBill(String bill_code) {
		return bill_code != null && bill_code.startsWith(TradeBillVo.BILL_CODE_PREFIX);
	}
	
	/**
	 * 是否是提现单
	 * @param bill_code
	 * @return
	 */
	public static boolean isApplyBill(String bill_code) {
		return bill_code != null && bill_code.startsWith(ApplyVo.BILL_CODE_PREFIX);
	}
	
	/**
	 * 是否是充值单
	 * @param bill_code
	 * @return
	 */
	public static boolean isRechargeBill(String bill_code) {
		return bill_code != null && bill_code.startsWith(RechargeVo.BILL_CODE_PREFIX);
	}
	
	/**
	 * 是否是退款单
	 * @param bill_code
	 * @return
	 */
	public static boolean isRefundBill(String bill_code) {
		return bill_code != null && bill_code.startsWith(RefundVo.BILL_CODE_PREFIX);
	}
	
}
