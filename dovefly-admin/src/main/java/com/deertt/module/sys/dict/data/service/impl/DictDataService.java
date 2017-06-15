package com.deertt.module.sys.dict.data.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.dict.data.dao.IDictDataDao;
import com.deertt.module.sys.dict.data.service.IDictDataService;
import com.deertt.module.sys.dict.data.util.IDictDataConstants;
import com.deertt.module.sys.dict.data.vo.DictDataVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class DictDataService extends DvBaseService<IDictDataDao, DictDataVo, Integer> implements IDictDataService, IDictDataConstants {
	
	public List<DictDataVo> queryAllEnabledDictDatas(String queryCondition, String orderStr) {
		return getDao().queryAllEnabledDictDatas(queryCondition, orderStr);
	}
	
	@Transactional
	public int enable(Integer[] ids) {
		return getDao().enable(ids);
	}
	
	@Transactional
	public int disable(Integer[] ids) {
		return getDao().disable(ids);
	}

	@Transactional
	public int sort(String sort_ids) {
		return getDao().sort(sort_ids);
	}

}
