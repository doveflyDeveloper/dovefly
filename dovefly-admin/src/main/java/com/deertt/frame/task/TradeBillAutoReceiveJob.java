package com.deertt.frame.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.trade.bill.service.ITradeBillService;
import com.deertt.module.trade.bill.vo.TradeBillVo;

/**
 * 发货时间超过24小时的销售订单自动确认收货（每小时执行一次）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class TradeBillAutoReceiveJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private ITradeBillService tradeService;

	@Scheduled(cron = "0 0 * * * ?")
	public void resolveTradeBill() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		// 查询发货时间超过24小时但仍未确认收货的订单
		List<TradeBillVo> trades = tradeService.queryByCondition("status = '" + TradeBillVo.STATUS_DELIVERED + "' AND send_time < DATE_SUB(now(), INTERVAL 1 DAY)", null);
		if (trades != null && trades.size() > 0) {
			for (TradeBillVo bean : trades) {
				try {
					tradeService.receive(bean.getId(), true);
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
