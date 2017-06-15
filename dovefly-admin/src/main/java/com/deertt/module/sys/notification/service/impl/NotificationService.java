package com.deertt.module.sys.notification.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.market.service.IMarketService;
import com.deertt.module.sys.notification.dao.INotificationDao;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.notification.util.INotificationConstants;
import com.deertt.module.sys.notification.vo.NotificationVo;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.vo.UserVo;
import com.deertt.module.sys.userapply.dao.IUserApplyDao;
import com.deertt.module.sys.userapply.util.IUserApplyConstants;
import com.deertt.module.sys.userapply.vo.UserApplyVo;
import com.deertt.module.sys.warehouse.service.IWarehouseService;
import com.deertt.utils.helper.WechatHelper;
import com.deertt.utils.helper.date.DvDateHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class NotificationService extends DvBaseService<INotificationDao, NotificationVo, Integer> implements INotificationService, INotificationConstants {
	
	@Autowired
	protected IUserService userService;
	
	@Autowired
	private IUserApplyDao userApplyDao;
	
	@Autowired
	private IWarehouseService warehouseService;
	
	@Autowired
	private IShopService shopService;
	
	@Autowired
	private IMarketService marketService;
	
	public List<NotificationVo> queryUnSendMsgs() {
		return getDao().queryByCondition("notify_status = '" + NotificationVo.NOTIFY_STATUS_NO + "' AND notify_times < notify_limit_times AND expect_notify_time <= now()", null, -1, -1, true);
	}
	
	public List<NotificationVo> queryCanRetrySendMsgs() {
		return getDao().queryByCondition("notify_status = '" + NotificationVo.NOTIFY_STATUS_WN + "' AND notify_times < notify_limit_times AND expect_notify_time < DATE_SUB(now(), INTERVAL 5*notify_times MINUTE)", null, -1, -1, true);
	}
	
	@Transactional
	public int addWNotification(Integer user_id, String notify_type, String message) {
		UserVo user = userService.find(user_id);
		return this.addWNotification(user.getId(), user.getReal_name(), user.getWechat_id(), user.getMobile(), notify_type, message);
	}
	
	@Transactional
	public int addMNotification(Integer user_id, String notify_type, String message) {
		UserVo user = userService.find(user_id);
		return this.addMNotification(user.getId(), user.getReal_name(), user.getWechat_id(), user.getMobile(), notify_type, message);
	}
	
	@Transactional
	public int addWMNotification(Integer user_id, String notify_type, String message) {
		UserVo user = userService.find(user_id);
		return this.addWMNotification(user.getId(), user.getReal_name(), user.getWechat_id(), user.getMobile(), notify_type, message);
	}
	
	@Transactional
	public int addWNotification(Integer user_id, String user_name, String wechat_id, String mobile, String notify_type, String message) {
		return this.addNotification(user_id, user_name, wechat_id, mobile, notify_type, NotificationVo.NOTIFY_WAY_W, message);
	}
	
	@Transactional
	public int addMNotification(Integer user_id, String user_name, String wechat_id, String mobile, String notify_type, String message) {
		return this.addNotification(user_id, user_name, wechat_id, mobile, notify_type, NotificationVo.NOTIFY_WAY_M, message);
	}
	
	@Transactional
	public int addWMNotification(Integer user_id, String user_name, String wechat_id, String mobile, String notify_type, String message) {
		return this.addNotification(user_id, user_name, wechat_id, mobile, notify_type, NotificationVo.NOTIFY_WAY_WM, message);
	}
	
	@Transactional
	public int addNotification(Integer user_id, String user_name, String wechat_id, String mobile, String notify_type, String notify_way, String message) {
		return this.addNotification(user_id, user_name, wechat_id, mobile, notify_type, notify_way, message, DvDateHelper.getSysTimestamp(), NOTIFY_LIMIT_TIMES);
	}
	
    @Transactional
    public int addNotification(Integer user_id, String user_name, String wechat_id, String mobile, String notify_type, String notify_way, String message, Timestamp expect_notify_time, Integer notify_limit_times) {
        NotificationVo vo = new NotificationVo(user_id, user_name, wechat_id, mobile, notify_type, notify_way, message, expect_notify_time, notify_limit_times);
        return getDao().insert(vo);
    }
    
    /**
     * 根据store_type查询对应的管理人员
     * @param store_type
     * @param store_id
     * @return
     */
    private UserVo getManagerByStoreTypeAndId(String store_type, Integer store_id) {
    	Integer user_id = 0;
		if ("w".equals(store_type)) {
			user_id = warehouseService.find(store_id).getManager_id();
		} else if ("s".equals(store_type)) {
			user_id = shopService.find(store_id).getShopkeeper_id();
		} else if ("m".equals(store_type)) {
			user_id = marketService.find(store_id).getShopkeeper_id();
		} 
		
		return userService.find(user_id);
    }
    
	@Transactional
	public int addWNotification(String store_type, Integer store_id, String notify_type, String message) {
		UserVo user = this.getManagerByStoreTypeAndId(store_type, store_id);
		return this.addWNotification(user.getId(), user.getReal_name(), user.getWechat_id(), user.getMobile(), notify_type, message);
	}
	
	@Transactional
	public int addMNotification(String store_type, Integer store_id, String notify_type, String message) {
		UserVo user = this.getManagerByStoreTypeAndId(store_type, store_id);
		return this.addMNotification(user.getId(), user.getReal_name(), user.getWechat_id(), user.getMobile(), notify_type, message);
	}
	
	@Transactional
	public int addWMNotification(String store_type, Integer store_id, String notify_type, String message) {
		UserVo user = this.getManagerByStoreTypeAndId(store_type, store_id);
		return this.addWMNotification(user.getId(), user.getReal_name(), user.getWechat_id(), user.getMobile(), notify_type, message);
	}
	
	@Transactional
	public int sendNotification(Integer id) {
		int sum = 0;
		String result = WechatHelper.sendNofification(id);
		JSONObject json = JSONObject.parseObject(result);
		//{"result": "success", "send_type": "wy", "msg": {"errcode": 0, "errmsg": "ok", "msgid": 403547471}}
		//{"result": "fail", "send_type": "wn", "msg": {"name": "WeChatAPIError", "code": 40003}}
		NotificationVo vo = this.find(id);
		vo.setNotify_time(DvDateHelper.getSysTimestamp());
		vo.setNotify_times(vo.getNotify_times() == null ? 1 : vo.getNotify_times() + 1);
		vo.setNotify_status(json.getString("send_type"));
		vo.setRemark(result);
		sum = this.update(vo);
		
		//如果是申请店长通知消息，通知结果回写申请表通知状态
		if (vo.getNotify_type().startsWith(IUserApplyConstants.TABLE_NAME)) {
			Integer user_apply_id = Integer.valueOf(vo.getNotify_type().split("-")[1]);
			
			UserApplyVo apply = userApplyDao.find(user_apply_id);
			if (NotificationVo.NOTIFY_STATUS_WY.equals(vo.getNotify_status()) || NotificationVo.NOTIFY_STATUS_MY.equals(vo.getNotify_status())) {
				apply.setNotify_status(UserApplyVo.NOTIFY_STATUS_SUCCESS);
			} else {
				apply.setNotify_status(UserApplyVo.NOTIFY_STATUS_FAIL);
			}
			userApplyDao.update(apply);
		}
		
		return sum;
	}
	
	@Transactional
	public int removeOldNotification(int beforeDays) {
		this.update(SQL_REMOVE_OLD_NOTIFICATION, new Object[]{ beforeDays });
		return this.update(SQL_DELETE_OLD_NOTIFICATION, new Object[]{ beforeDays });
	}
	
}
