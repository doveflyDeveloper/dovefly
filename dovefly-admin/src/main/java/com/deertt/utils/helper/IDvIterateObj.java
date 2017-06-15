package com.deertt.utils.helper;

import java.util.Map;

/**
 * 注值策略，根据不同的对象类型（Map, ServletRequest, ResultSet, DvBaseVo）采取不同的注值方式
 * @author fengcm
 *
 */
public interface IDvIterateObj {
	
	/**
	 * 以源对象的信息对目标对象进行注值，根据不同的对象类型（Map, ServletRequest, ResultSet, DvBaseVo）采取不同的注值方式
	 * @param destObj
	 * @param sourceObj
	 * @param fieldMap
	 * @param ignoreField
	 * @param getValue
	 * @return
	 */
	public int iterateObj(Object destObj, Object sourceObj, Map<String, String> fieldMap, String[] ignoreField, IDvGetValue getValue);
}