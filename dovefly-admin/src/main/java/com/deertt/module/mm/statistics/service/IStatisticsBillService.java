package com.deertt.module.mm.statistics.service;

import java.util.List;
import java.util.Map;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.mm.statistics.dao.IStatisticsBillDao;
import com.deertt.module.mm.statistics.vo.StatisticsBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IStatisticsBillService extends	IDvBaseService<IStatisticsBillDao, StatisticsBillVo, Integer> {

	/**
	 * 插入
	 * @param vo
	 * @return
	 */
	public int insertFull(StatisticsBillVo vo);
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	public int updateFull(StatisticsBillVo vo);

	/**
	 * 查询订单（包含订单明细，快递信息）
	 * 
	 * @param id
	 * @return
	 */
	public StatisticsBillVo findFull(Integer id);
	
}
