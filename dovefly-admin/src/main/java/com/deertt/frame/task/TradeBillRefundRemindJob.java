package com.deertt.frame.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.trade.bill.service.ITradeBillService;
import com.deertt.module.trade.bill.util.ITradeBillConstants;
import com.deertt.module.trade.bill.vo.TradeBillVo;

/**
 * 提醒店长及时处理申请退款和退货的订单（每天9点执行一次）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class TradeBillRefundRemindJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private ITradeBillService tradeService;
	
	@Autowired
	private INotificationService notificationService;

	@Scheduled(cron = "5 0 9 * * ?")
	public void resolveTradeBill() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		// 申请退款或退货的订单
		List<TradeBillVo> trades = tradeService.queryByCondition("status in ('" + TradeBillVo.STATUS_APPLY_FOR_REFUND + "', '" + TradeBillVo.STATUS_APPLY_FOR_RETURN + "')", null);
		if (trades != null && trades.size() > 0) {
			for (TradeBillVo bean : trades) {
				try {
//					logger.info("----------");
					if (TradeBillVo.STATUS_APPLY_FOR_REFUND.equals(bean.getStatus())) {
						//卖家，订单申请退歀消息
						String message = "用户有一笔订单申请退歀";
						notificationService.addWMNotification(bean.getShop_id(), ITradeBillConstants.TABLE_NAME + "-" + bean.getId() + "-" + TradeBillVo.STATUS_APPLY_FOR_REFUND, message);
					} else if (TradeBillVo.STATUS_APPLY_FOR_RETURN.equals(bean.getStatus())) {
						//卖家，订单申请退货消息
						String message = "用户有一笔订单申请退货";
						notificationService.addWMNotification(bean.getShop_id(), ITradeBillConstants.TABLE_NAME + "-" + bean.getId() + "-" + TradeBillVo.STATUS_APPLY_FOR_RETURN, message);
					}
//					logger.info("----------");
				} catch (Exception e) {
					logger.info(e);
					e.printStackTrace();
				}
			}
		}
	}
}
