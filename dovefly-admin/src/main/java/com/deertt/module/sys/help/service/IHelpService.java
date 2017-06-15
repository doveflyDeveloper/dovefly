package com.deertt.module.sys.help.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.help.dao.IHelpDao;
import com.deertt.module.sys.help.vo.HelpVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IHelpService extends IDvBaseService<IHelpDao, HelpVo, Integer> {

	public HelpVo findByModule(String module);

}
