package com.deertt.frame.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.userapply.service.IUserApplyService;

/**
 * 消息自动移除定时任务（每天4点执行一次）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class NotificationAutoRemoveJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private INotificationService notificationService;
	
	@Autowired
	private IUserApplyService userApplyService;
	
	@Scheduled(cron = "0 0 4 * * ?")
	public void removeMessage() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
//		logger.info("----------");
		notificationService.removeOldNotification(2);//移走两天前的消息
//		logger.info("----------");
		
	}
}
