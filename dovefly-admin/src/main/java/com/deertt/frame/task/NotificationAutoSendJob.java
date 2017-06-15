package com.deertt.frame.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.notification.vo.NotificationVo;
import com.deertt.module.sys.userapply.service.IUserApplyService;

/**
 * 消息发送定时任务（每十秒钟执行一次）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class NotificationAutoSendJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private INotificationService notificationService;
	
	@Autowired
	private IUserApplyService userApplyService;
	
	@Scheduled(cron = "0/10 * * * * ?")
	public void sendMessage() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		List<NotificationVo> list = notificationService.queryUnSendMsgs();
		if (list != null && list.size() > 0) {
			for (NotificationVo bean : list) {
				try {
					notificationService.sendNotification(bean.getId());
//					logger.info("----------");
//					logger.info(bean);
//					logger.info("----------");
				} catch (Exception e) {
					logger.info(e);
					e.printStackTrace();
				}
			}
		}
		
		List<NotificationVo> retryList = notificationService.queryCanRetrySendMsgs();
		if (retryList != null && retryList.size() > 0) {
			for (NotificationVo bean : retryList) {
				try {
					notificationService.sendNotification(bean.getId());
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
