package com.deertt.module.sys.banner.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.banner.dao.IBannerDao;
import com.deertt.module.sys.banner.vo.BannerVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IBannerService extends IDvBaseService<IBannerDao, BannerVo, Integer> {
	
	public int enable(Integer[] ids);
	
	public int disable(Integer[] ids);
}
