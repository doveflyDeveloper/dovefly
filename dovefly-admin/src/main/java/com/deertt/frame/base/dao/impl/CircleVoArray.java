package com.deertt.frame.base.dao.impl;

public interface CircleVoArray<T> {
	
	/**
	 * 获取对象属性的数组形式
	 * 
	 * @param obj
	 * @return
	 */
	public Object[] getArgs(T obj);
}
