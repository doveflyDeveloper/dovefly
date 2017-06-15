package com.deertt.module.sys.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.market.service.IMarketService;
import com.deertt.module.sys.message.dao.IMessageDao;
import com.deertt.module.sys.message.service.IMessageService;
import com.deertt.module.sys.message.util.IMessageConstants;
import com.deertt.module.sys.message.vo.MessageVo;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.vo.UserVo;
import com.deertt.module.sys.warehouse.service.IWarehouseService;
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
public class MessageService extends DvBaseService<IMessageDao, MessageVo, Integer> implements IMessageService, IMessageConstants {

	@Autowired
	protected IUserService userService;
	
	@Autowired
	private IWarehouseService warehouseService;
	
	@Autowired
	private IShopService shopService;
	
	@Autowired
	private IMarketService marketService;
	
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
	public int addMessage(String store_type, Integer store_id, String title, String type, String content, String link_url) {
		UserVo user = this.getManagerByStoreTypeAndId(store_type, store_id);
		return this.addMessage(user.getId(), user.getReal_name(), title, type, content, link_url);
	}
	
	@Transactional
	public int addMessage(Integer user_id, String user_name, String title, String type, String content, String link_url) {
		MessageVo message = new MessageVo();
		message.setUser_id(user_id);
		message.setUser_name(user_name);
		message.setTitle(title);
		message.setType(type);
		message.setContent(content);
		message.setLink_url(link_url);
		message.setStatus(MessageVo.STATUS_UNREAD);
		message.setCreate_at(DvDateHelper.getSysTimestamp());
		return this.insert(message);
	}
	
	@Transactional
	public int readMessage(Integer id) {
		return getDao().update(SQL_READ_BY_ID, new Object[]{ id });
	}
}
