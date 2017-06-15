package com.deertt.module.mm.category.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.mm.category.vo.CategoryVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ICategoryDao extends IDvBaseDao<CategoryVo, Integer> {
	
	/**
	 * 更新父级分类名称
	 * @param id
	 * @param parent_name 新父级分类名称
	 * @return
	 */
	public int updateParentName(Integer id, String parent_name);
	
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
