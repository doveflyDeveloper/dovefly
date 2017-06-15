package com.deertt.module.sys.school.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.module.sys.school.vo.SchoolVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ISchoolDao extends IDvBaseDao<SchoolVo, Integer> {
	
	/**
	 * 对所选数据进行重新排序（排序规则依据sort_ids的顺序）
	 * @return
	 */
	public int sort(String sort_ids);
	
}
