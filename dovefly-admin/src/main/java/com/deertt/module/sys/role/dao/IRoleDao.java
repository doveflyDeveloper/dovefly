package com.deertt.module.sys.role.dao;

import java.util.List;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.sys.role.vo.RoleVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IRoleDao extends IDvBaseDao<RoleVo, Integer> {
	
	/**
	 * 查找用户拥有的角色
	 * @param user_id
	 * @return
	 */
	public List<RoleVo> queryRolesByUserId(Integer user_id);
	
	/**
	 * 给角色授予资源
	 * @param role_id
	 * @param res_ids
	 * @return
	 */
	public int authorizeRes(Integer role_id, Integer[] res_ids);
}
