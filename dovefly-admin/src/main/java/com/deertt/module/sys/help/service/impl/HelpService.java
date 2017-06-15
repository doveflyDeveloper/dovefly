package com.deertt.module.sys.help.service.impl;

import org.springframework.stereotype.Service;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.help.dao.IHelpDao;
import com.deertt.module.sys.help.service.IHelpService;
import com.deertt.module.sys.help.util.IHelpConstants;
import com.deertt.module.sys.help.vo.HelpVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class HelpService extends DvBaseService<IHelpDao, HelpVo, Integer> implements IHelpService, IHelpConstants {

	public HelpVo findByModule(String module) {
		return getDao().findByCondition("module='" + module + "'");
	}
	
}
