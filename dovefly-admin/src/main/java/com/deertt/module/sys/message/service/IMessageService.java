package com.deertt.module.sys.message.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.message.dao.IMessageDao;
import com.deertt.module.sys.message.vo.MessageVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMessageService extends IDvBaseService<IMessageDao, MessageVo, Integer> {

	/**
	 * 新增系统消息
	 * @param user_id
	 * @param user_name
	 * @param title
	 * @param type
	 * @param content
	 * @param link_url
	 * @return
	 */
	public int addMessage(Integer user_id, String user_name, String title, String type, String content, String link_url);
	
	/**
	 * 新增系统消息
	 * @param store_id
	 * @param store_name
	 * @param title
	 * @param type
	 * @param content
	 * @param link_url
	 * @return
	 */
	public int addMessage(String store_type, Integer store_name, String title, String type, String content, String link_url);
	
	/**
	 * 消息标识为已读
	 * @param id
	 * @return
	 */
	public int readMessage(Integer id);

}
