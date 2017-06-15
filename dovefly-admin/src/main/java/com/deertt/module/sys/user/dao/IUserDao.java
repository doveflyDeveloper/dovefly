package com.deertt.module.sys.user.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.sys.user.vo.UserVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IUserDao extends IDvBaseDao<UserVo, Integer> {

	/**
	 * 为用户重新授予角色
	 * @param user_id
	 * @param role_ids
	 * @return
	 */
	public int authorizeRoles(Integer user_id, Integer[] role_ids);

	/**
	 * 修改用户部分信息
	 * @param vo
	 * @return
	 */
	public int change(UserVo vo);
	
	/**
	 * 修改用户密码
	 * @param vo
	 * @return
	 */
	public int changePwd(UserVo vo);
	
	/**
	 * 启用
	 * 
	 * @param ids 用于启用的记录的ids
	 * @return 成功启用的记录数
	 */
	public int enable(Integer[] ids);
	
	/**
	 * 禁用
	 * 
	 * @param ids 用于禁用的记录的ids
	 * @return 成功禁用的记录数
	 */
	public int disable(Integer[] ids);

	public int changeCoin_quantity(Integer user_id, Integer new_coin_quantity);

}
