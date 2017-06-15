package com.deertt.module.sys.banner.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.sys.banner.vo.BannerVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IBannerDao extends IDvBaseDao<BannerVo, Integer> {
	
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
}
