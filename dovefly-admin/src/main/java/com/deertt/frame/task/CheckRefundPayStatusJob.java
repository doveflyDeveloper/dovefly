package com.deertt.frame.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.common.pay.util.AlipayUtils;
import com.deertt.common.pay.util.WxpayUtils;
import com.deertt.common.pay.vo.RefundCheckResult;
import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.frame.base.util.CheckBillTypeUtils;
import com.deertt.module.fund.refund.service.IRefundService;
import com.deertt.module.fund.refund.vo.RefundVo;
import com.deertt.module.order.bill.service.IOrderBillService;
import com.deertt.module.trade.bill.service.ITradeBillService;

/**
 * 退款状态自动检测任务（每十分钟执行一次）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class CheckRefundPayStatusJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private IRefundService refundService;
	
	@Autowired
	private ITradeBillService tradeService;
	
	@Autowired
	private IOrderBillService orderService;

	@Scheduled(cron = "50 0/10 * * * ?")
	public void checkRefundPayStatus() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		// 查询五分钟前的处理中的提现申请
		List<RefundVo> refunds = refundService.queryByCondition("status = '" + RefundVo.STATUS_DEALING + "' AND pay_time < DATE_SUB(now(), INTERVAL 10 MINUTE)", null);
		if (refunds != null && refunds.size() > 0) {
			for (RefundVo bean : refunds) {
				try {
					if (RefundVo.REFUND_TO_WXPAY.equals(bean.getRefund_to())) {
						RefundCheckResult payCheckResult = WxpayUtils.refundquery(bean.getRefer_bill_code());
						if (CheckBillTypeUtils.isTradeBill(bean.getRefer_bill_code())) {
							if (payCheckResult.isSuccess()) {
								tradeService.refundSuccessCallBack(bean.getRefer_bill_code());
							} else {
								tradeService.refundFailCallBack(bean.getRefer_bill_code(), payCheckResult.getMessage());
							}
						} else if (CheckBillTypeUtils.isOrderBill(bean.getRefer_bill_code())) {
							if (payCheckResult.isSuccess()) {
								orderService.refundSuccessCallBack(bean.getRefer_bill_code());
							} else {
								orderService.refundFailCallBack(bean.getRefer_bill_code(), payCheckResult.getMessage());
							}
						}
					} else if (RefundVo.REFUND_TO_ALIPAY.equals(bean.getRefund_to())) {
						RefundCheckResult payCheckResult = AlipayUtils.checkAlipayRefundResult(bean.getRefer_bill_code(), bean.getPay_code());
						if (CheckBillTypeUtils.isTradeBill(bean.getRefer_bill_code())) {
							if (payCheckResult.isSuccess()) {
								tradeService.refundSuccessCallBack(bean.getRefer_bill_code());
							} else {
								tradeService.refundFailCallBack(bean.getRefer_bill_code(), payCheckResult.getMessage());
							}
						} else if (CheckBillTypeUtils.isOrderBill(bean.getRefer_bill_code())) {
							if (payCheckResult.isSuccess()) {
								orderService.refundSuccessCallBack(bean.getRefer_bill_code());
							} else {
								orderService.refundFailCallBack(bean.getRefer_bill_code(), payCheckResult.getMessage());
							}
						}
					}
//					logger.info("----------");
//					logger.info(bean);
//					logger.info("----------");
					
				} catch (Exception e) {
					logger.info(e);
					e.printStackTrace();
				}
			}
		}
	}
}
