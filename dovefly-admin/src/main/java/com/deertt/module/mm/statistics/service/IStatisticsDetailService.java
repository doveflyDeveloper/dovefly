package com.deertt.module.mm.statistics.service;

import java.util.List;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.mm.statistics.dao.IStatisticsDetailDao;
import com.deertt.module.mm.statistics.vo.StatisticsDetailVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IStatisticsDetailService extends IDvBaseService<IStatisticsDetailDao, StatisticsDetailVo, Integer> {
	public int[] insertUpdateBatch(List<StatisticsDetailVo> voList, Integer bill_id);
}
