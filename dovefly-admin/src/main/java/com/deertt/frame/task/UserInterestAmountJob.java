package com.deertt.frame.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.shop.vo.ShopVo;

/**
 * 用户每日贷款利息计算（每天23点执行一次）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class UserInterestAmountJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private IShopService shopService;

	@Scheduled(cron = "0 0 23 * * ?")
	public void calculateTodayInterestAmount() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		// 查询有贷款的用户
		List<ShopVo> shops = shopService.queryByCondition("loan_amount > 0", null);
		if (shops != null && shops.size() > 0) {
			for (ShopVo bean : shops) {
				try {
					shopService.addTodayInterestAmount(bean.getId());
				} catch (Exception e) {
					logger.info(e);
					e.printStackTrace();
				}
			}
		}
	}
}
