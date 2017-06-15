package com.deertt.module.sys.coinrule.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.coinrule.dao.ICoinRuleDao;
import com.deertt.module.sys.coinrule.service.ICoinRuleService;
import com.deertt.module.sys.coinrule.util.ICoinRuleConstants;
import com.deertt.module.sys.coinrule.vo.CoinRuleVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class CoinRuleService extends DvBaseService<ICoinRuleDao, CoinRuleVo, Integer> implements ICoinRuleService, ICoinRuleConstants {
	
}
