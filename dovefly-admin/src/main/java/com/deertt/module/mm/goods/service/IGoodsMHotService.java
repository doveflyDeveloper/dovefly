package com.deertt.module.mm.goods.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.mm.goods.dao.IGoodsMHotDao;
import com.deertt.module.mm.goods.vo.GoodsMHotVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IGoodsMHotService extends IDvBaseService<IGoodsMHotDao, GoodsMHotVo, Integer> {
	
	/**
	 * 上架销售
	 * 
	 * @param ids 用于启用的记录的ids
	 * @return 成功启用的记录数
	 */
	public int onSale(Integer[] ids);
	
	/**
	 * 下架停售
	 * 
	 * @param ids 用于禁用的记录的ids
	 * @return 成功禁用的记录数
	 */
	public int offSale(Integer[] ids);
	
	/**
	 * 库存商品重排序
	 * @param goods_ids
	 * @return
	 */
	public int sort(String goods_ids);

}
