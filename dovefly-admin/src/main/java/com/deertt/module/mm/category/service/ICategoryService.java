package com.deertt.module.mm.category.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.mm.category.dao.ICategoryDao;
import com.deertt.module.mm.category.vo.CategoryVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ICategoryService extends IDvBaseService<ICategoryDao, CategoryVo, Integer> {
	
	public int updateCascade(CategoryVo vo);
	
	public int deleteCascade(Integer id);
	
	public HandleResult checkCanDelete(Integer id);
	
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
	 * 对选定的数据进行排序
	 * @param sort_ids
	 */
	public int sort(String sort_ids);

}
