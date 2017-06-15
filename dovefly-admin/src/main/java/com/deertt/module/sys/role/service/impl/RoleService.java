package com.deertt.module.sys.role.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.resource.dao.IResourceDao;
import com.deertt.module.sys.role.dao.IRoleDao;
import com.deertt.module.sys.role.service.IRoleService;
import com.deertt.module.sys.role.util.IRoleConstants;
import com.deertt.module.sys.role.vo.RoleVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class RoleService extends DvBaseService<IRoleDao, RoleVo, Integer> implements IRoleService, IRoleConstants {
	
	@Autowired
	private IResourceDao resourceDao;

	public RoleVo find(Integer id) {
		RoleVo vo = super.find(id);
		vo.setResources(resourceDao.queryResourcesByRoleId(id));
		return vo;
	}
	
	@Transactional
	public int executeAuthorize(Integer role_id, Integer[] res_ids) {
		return getDao().authorizeRes(role_id, res_ids);
	}
	
}
