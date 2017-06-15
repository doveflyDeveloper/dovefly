package com.deertt.module.sys.banner.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.banner.dao.IBannerDao;
import com.deertt.module.sys.banner.service.IBannerService;
import com.deertt.module.sys.banner.util.IBannerConstants;
import com.deertt.module.sys.banner.vo.BannerVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class BannerService extends DvBaseService<IBannerDao, BannerVo, Integer> implements IBannerService, IBannerConstants {
	
	@Transactional
	public int enable(Integer[] ids) {
		return getDao().enable(ids);
	}
	
	@Transactional
	public int disable(Integer[] ids) {
		return getDao().disable(ids);
	}
}
