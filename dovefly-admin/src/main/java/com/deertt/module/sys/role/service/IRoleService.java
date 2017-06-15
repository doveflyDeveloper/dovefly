package com.deertt.module.sys.role.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.role.dao.IRoleDao;
import com.deertt.module.sys.role.vo.RoleVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IRoleService extends IDvBaseService<IRoleDao, RoleVo, Integer> {
	
	/**
	 * 给角色授权（删除原先所有的资源，全部重新授权）
	 * @param role_id 角色ID
	 * @param res_ids 资源ID数组
	 * @return 重新授权的资源个数
	 */
	public int executeAuthorize(Integer role_id, Integer[] res_ids);
	
}
