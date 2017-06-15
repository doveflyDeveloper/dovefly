package com.deertt.module.sys.resource.service;

import java.util.List;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.resource.dao.IResourceDao;
import com.deertt.module.sys.resource.vo.ResourceVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IResourceService extends IDvBaseService<IResourceDao, ResourceVo, Integer> {
	
	/**
	 * 更新是否为叶子节点状态
	 * @param ids
	 * @param is_leaf ‘0’不是叶子节点， ‘1’是叶子节点
	 * @return
	 */
	public int updateIs_leaf(Integer[] ids, String is_leaf);
	
	/**
	 * 获取当前节点下的下一个可用的子节点编号
	 * @param parent_id
	 * @return
	 */
	public String generateNextCode(Integer id);
	
	/**
	 * 获取角色资源
	 * @param role_id
	 * @return
	 */
	public List<ResourceVo> queryResourcesByRoleId(Integer role_id);

	/**
	 * 获取用户菜单
	 * @param user_id
	 * @return
	 */
	public List<ResourceVo> queryMenusByUserId(Integer user_id);
	
	/**
	 * 对选定的数据进行排序
	 * @param sort_ids
	 */
	public int sort(String sort_ids);
}
