package com.deertt.module.mm.goods.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.mm.goods.vo.GoodsMVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IGoodsMDao extends IDvBaseDao<GoodsMVo, Integer> {
	
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
	
	/**
	 * 对商品进行重新排序（排序规则依据goods_ids的顺序）
	 * @param goods_ids 由逗号分隔的一系列主键组成的字符串
	 * @return
	 */
	public int sort(String goods_ids);

}
