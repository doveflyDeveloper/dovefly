package com.deertt.module.sys.userapply.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.userapply.dao.IUserApplyDao;
import com.deertt.module.sys.userapply.vo.UserApplyVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IUserApplyService extends IDvBaseService<IUserApplyDao, UserApplyVo, Integer> {
	
	/**
	 * 获取申请及相关用户
	 * @param id
	 * @return
	 */
	public UserApplyVo findFull(Integer id);

	/**
	 * 通过申请，修改用户类型，发送成功通知
	 * @param id
	 * @return
	 */
	public int pass(Integer id);
	
	/**
	 * 拒绝申请，发送拒绝通知
	 * @param id
	 * @param reason
	 * @return
	 */
	public int deny(Integer id, String reason);

}
