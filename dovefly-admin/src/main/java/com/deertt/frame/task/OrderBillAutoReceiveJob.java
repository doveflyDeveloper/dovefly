package com.deertt.frame.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.order.bill.service.IOrderBillService;
import com.deertt.module.order.bill.vo.OrderBillVo;

/**
 * 发货时间超过72小时的店长进货订单自动确认收货（每小时执行一次）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class OrderBillAutoReceiveJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private IOrderBillService orderService;

	@Scheduled(cron = "0 0 * * * ?")
	public void resolveTradeBill() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		// 查询发货时间超过72小时但仍未确认收货的订单
		List<OrderBillVo> orders = orderService.queryByCondition("status = '" + OrderBillVo.STATUS_DELIVERED + "' AND send_time < DATE_SUB(now(), INTERVAL 3 DAY)", null);
		if (orders != null && orders.size() > 0) {
			for (OrderBillVo bean : orders) {
				try {
					orderService.receive(bean.getId(), true);
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
