package com.deertt.module.sys.region.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.sys.region.vo.RegionVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IRegionDao extends IDvBaseDao<RegionVo, Integer> {
	
	/**
	 * 上级区域更新名称后，需同步更新所有下级区域的全名称，上级名称等信息
	 * @param vo
	 * @return
	 */
	public int updateChildrenNames(RegionVo vo);
	
	/**
	 * 更新是否为叶子节点状态
	 * @param ids
	 * @param is_leaf ‘0’不是叶子节点， ‘1’是叶子节点
	 * @return
	 */
	public int updateIs_leaf(Integer[] ids, String is_leaf);
	
	/**
	 * 查询当前节点下的所有子节点的最大编号
	 * @param id
	 * @return
	 */
	public String queryChildNodeMaxCode(Integer id);
	
	/**
	 * 对所选数据进行重新排序（排序规则依据sort_ids的顺序）
	 * @return
	 */
	public int sort(String sort_ids);
	
}
