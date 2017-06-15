package com.deertt.module.sys.dict.type.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.dict.type.dao.IDictTypeDao;
import com.deertt.module.sys.dict.type.vo.DictTypeVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IDictTypeService extends IDvBaseService<IDictTypeDao, DictTypeVo, Integer> {

	/**
	 * 启用数据字典类型
	 * 
	 * @param ids 用于启用的记录的ids
	 * @return 成功启用的记录数
	 */
	public int enable(Integer[] ids);
	
	/**
	 * 禁用数据字典类型
	 * 
	 * @param ids 用于禁用的记录的ids
	 * @return 成功禁用的记录数
	 */
	public int disable(Integer[] ids);
}
