package com.deertt.module.mm.spec.service.impl;

import org.springframework.stereotype.Service;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.spec.dao.ISpecDao;
import com.deertt.module.mm.spec.service.ISpecService;
import com.deertt.module.mm.spec.util.ISpecConstants;
import com.deertt.module.mm.spec.vo.SpecVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class SpecService extends DvBaseService<ISpecDao, SpecVo, Integer> implements ISpecService, ISpecConstants {
	
}
