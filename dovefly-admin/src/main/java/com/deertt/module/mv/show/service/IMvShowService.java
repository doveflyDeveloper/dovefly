package com.deertt.module.mv.show.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.mv.show.dao.IMvShowDao;
import com.deertt.module.mv.show.vo.MvShowVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMvShowService extends IDvBaseService<IMvShowDao, MvShowVo, Integer> {
	
	/**
	 * 查询剧本（包含剧本明细）
	 * @param id
	 * @return
	 */
	public MvShowVo findFull(Integer id);
	
	/**
	 * 电影审核拒绝
	 * @param id
	 * @param reason
	 * @return
	 */
	public int deny(Integer id, String reason);
	
	/**
	 * 电影审核通过
	 * @param id
	 * @param reason
	 * @return
	 */
	public int agree(Integer id, String reason);
}
