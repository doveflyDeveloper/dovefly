package com.deertt.utils.helper;

import java.util.Map;

/**
 * 取值策略，根据不同的对象类型（Map, ServletRequest, ResultSet, DvBaseVo）采取不同的取值方式
 * 
 * @author fengcm
 *
 */
public interface IDvGetValue {
	
	/**
	 * 获取对象的值
	 * @param sourceObj 取值的源对象，可以是Map, ServletRequest, ResultSet, DvBaseVo等类型
	 * @param key Map的key或DvBaseVo的字段名称或ResultSet的字段名或ServletRequest的参数名等
	 * @return 
	 */
	public Object getValue(Object sourceObj, String key);
	
	/**
	 * 判断对象的键或方法或字段是否存在
	 * @param sourceObj 取值的源对象，可以是Map, ServletRequest, ResultSet, DvBaseVo等类型
	 * @param key Map的key或DvBaseVo的字段名称或ResultSet的字段名或ServletRequest的参数名等
	 * @return 
	 */
	public boolean containKey(Object sourceObj, String key);
	
	/**
	 * 将对象的值以Map的形式返回
	 * @param sourceObj 取值的源对象，可以是Map, ServletRequest, ResultSet, DvBaseVo等类型
	 * @return 
	 */
	public Map<Object, Object> getKeyValueMap(Object sourceObj);
}