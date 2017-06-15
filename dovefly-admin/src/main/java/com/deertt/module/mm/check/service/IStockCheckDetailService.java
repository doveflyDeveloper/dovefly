package com.deertt.module.mm.check.service;

import java.util.List;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.mm.check.dao.IStockCheckDetailDao;
import com.deertt.module.mm.check.vo.StockCheckDetailVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IStockCheckDetailService extends IDvBaseService<IStockCheckDetailDao, StockCheckDetailVo, Integer> {
	public int[] insertUpdateBatch(List<StockCheckDetailVo> voList, Integer bill_id);
}
