package com.deertt.frame.task;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.mm.goods.service.IGoodsWService;
import com.deertt.module.mm.goods.util.IGoodsWConstants;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.user.util.IUserConstants;
import com.deertt.module.sys.warehouse.service.IWarehouseService;
import com.deertt.module.sys.warehouse.vo.WarehouseVo;

/**
 * 城市经理库存安全线提醒(每天早上九点检查并提醒)
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class GoodsSafeLineRemindJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private IGoodsWService goodsWService;
	
	@Autowired
	private IWarehouseService warehouseService;
	
	@Autowired
	private INotificationService notificationService;

	@Scheduled(cron = "0 0 9 * * ?")
	public void checkStockSum() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		// 查询城市经理用户
		List<Map<String, Object>> datas = goodsWService.queryForMaps(IGoodsWConstants.SQL_LOW_STOCK_REMIND);
		if (datas != null && datas.size() > 0) {
			for (Map<String, Object> map : datas) {
				try {
					String message = "您有以下商品库存告警，请及时补货：\r\n（品名__告警量__库存量）\r\n" + map.get("goods_info");
					if (message.length() > 1000) {
						message = message.substring(0, 1000);
					}
					
					WarehouseVo warehouse = warehouseService.find((Integer)map.get("warehouse_id"));
					notificationService.addWNotification(warehouse.getManager_id(), IUserConstants.TABLE_NAME + "-" + warehouse.getManager_id() + "-" + "notify", message);
				} catch (Exception e) {
					logger.info(e);
					e.printStackTrace();
				}
			}
		}
	}
}
