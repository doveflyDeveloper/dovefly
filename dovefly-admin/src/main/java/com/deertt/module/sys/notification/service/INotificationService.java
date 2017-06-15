package com.deertt.module.sys.notification.service;

import java.sql.Timestamp;
import java.util.List;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.notification.dao.INotificationDao;
import com.deertt.module.sys.notification.vo.NotificationVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface INotificationService extends IDvBaseService<INotificationDao, NotificationVo, Integer> {
	
	/**
	 * 查询未为发送消息列表
	 * @return
	 */
	public List<NotificationVo> queryUnSendMsgs();
	
	/**
	 * 查询发送失败但仍有尝试机会的记录
	 * @return
	 */
	public List<NotificationVo> queryCanRetrySendMsgs();
	
	/**
	 * 添加消息(微信)
	 * @param user_id
	 * @param notify_type
	 * @param message
	 * @return
	 */
	public int addWNotification(Integer user_id, String notify_type, String message);
	
	/**
	 * 添加消息(短信)
	 * @param user_id
	 * @param notify_type
	 * @param message
	 * @return
	 */
	public int addMNotification(Integer user_id, String notify_type, String message);
	
	/**
	 * 添加消息(微信或短信)
	 * @param user_id
	 * @param notify_type
	 * @param message
	 * @return
	 */
	public int addWMNotification(Integer user_id, String notify_type, String message);
	
	/**
	 * 添加消息(微信)
	 * @param user_id
	 * @param user_name
	 * @param wechat_id
	 * @param mobile
	 * @param notify_type
	 * @param message
	 * @return
	 */
	public int addWNotification(Integer user_id, String user_name, String wechat_id, String mobile, String notify_type, String message);
	
	/**
	 * 添加消息(短信)
	 * @param user_id
	 * @param user_name
	 * @param wechat_id
	 * @param mobile
	 * @param notify_type
	 * @param message
	 * @return
	 */
	public int addMNotification(Integer user_id, String user_name, String wechat_id, String mobile, String notify_type, String message);
	
	/**
	 * 添加消息(微信或短信)
	 * @param user_id
	 * @param user_name
	 * @param wechat_id
	 * @param mobile
	 * @param notify_type
	 * @param message
	 * @return
	 */
	public int addWMNotification(Integer user_id, String user_name, String wechat_id, String mobile, String notify_type, String message);
	
	/**
	 * 添加消息
	 * @param user_id
	 * @param user_name
	 * @param wechat_id
	 * @param mobile
	 * @param notify_type
	 * @param notify_way
	 * @param message
	 * @return
	 */
	public int addNotification(Integer user_id, String user_name, String wechat_id, String mobile, String notify_type, String notify_way, String message);
	
	/**
	 * 添加消息
	 * @param user_id
	 * @param user_name
	 * @param wechat_id
	 * @param mobile
	 * @param notify_type
	 * @param notify_way
	 * @param message
	 * @param expect_notify_time
	 * @param notify_limit_times
	 * @return
	 */
    public int addNotification(Integer user_id, String user_name, String wechat_id, String mobile, String notify_type, String notify_way, String message, Timestamp expect_notify_time, Integer notify_limit_times);
	
	/**
	 * 
	 * @param store_type
	 * @param store_id
	 * @param notify_type
	 * @param message
	 * @return
	 */
	public int addWNotification(String store_type, Integer store_id, String notify_type, String message);
	
	/**
	 * 
	 * @param store_type
	 * @param store_id
	 * @param notify_type
	 * @param message
	 * @return
	 */
	public int addMNotification(String store_type, Integer store_id, String notify_type, String message);
	
	/**
	 * 
	 * @param store_type
	 * @param store_id
	 * @param notify_type
	 * @param message
	 * @return
	 */
	public int addWMNotification(String store_type, Integer store_id, String notify_type, String message);
    
	/**
	 * 发送一条消息
	 * @param id
	 * @return
	 */
	public int sendNotification(Integer id);

	public int removeOldNotification(int beforeDays);

}
