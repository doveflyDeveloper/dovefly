package com.deertt.module.sys.dict.type.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.dict.data.dao.IDictDataDao;
import com.deertt.module.sys.dict.type.dao.IDictTypeDao;
import com.deertt.module.sys.dict.type.service.IDictTypeService;
import com.deertt.module.sys.dict.type.util.IDictTypeConstants;
import com.deertt.module.sys.dict.type.vo.DictTypeVo;
import com.deertt.utils.helper.DvSqlHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class DictTypeService extends DvBaseService<IDictTypeDao, DictTypeVo, Integer> implements IDictTypeService, IDictTypeConstants {
	
	@Autowired
	private IDictDataDao dicDataDao;
	
	@Transactional
	public int delete(Integer id) {
		dicDataDao.deleteByCondition("type_id = " + id);
		return super.delete(id);
	}

	@Transactional
	public int delete(Integer[] ids) {
		dicDataDao.deleteByCondition("type_id in (" + DvSqlHelper.parseToSQLIntegerApos(ids) + ")");
		return super.delete(ids);
	}

	@Transactional
	public int enable(Integer[] ids) {
		return getDao().enable(ids);
	}
	
	@Transactional
	public int disable(Integer[] ids) {
		return getDao().disable(ids);
	}

}
