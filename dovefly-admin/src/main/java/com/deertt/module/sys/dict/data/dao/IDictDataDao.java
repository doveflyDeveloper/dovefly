package com.deertt.module.sys.dict.data.dao;

import java.util.List;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.sys.dict.data.vo.DictDataVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IDictDataDao extends IDvBaseDao<DictDataVo, Integer> {
	
	/**
	 * 功能: 根据条件查询所有启用状态下的数据字典值	
	 *	 
	 * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
	 * @param orderStr 排序字符
	 * @return
	 */
	public List<DictDataVo> queryAllEnabledDictDatas(String queryCondition, String orderStr);
	
	/**
	 * 启用数据字典
	 * 
	 * @param ids 用于启用的记录的ids
	 * @return 成功启用的记录数
	 */
	public int enable(Integer[] ids);
	
	/**
	 * 禁用数据字典
	 * 
	 * @param ids 用于禁用的记录的ids
	 * @return 成功禁用的记录数
	 */
	public int disable(Integer[] ids);
	
	/**
	 * 对所选数据进行重新排序（排序规则依据sort_ids的顺序）
	 * @return
	 */
	public int sort(String sort_ids);
	
}
