package com.deertt.utils.helper.office.excel.read;

import java.util.Map;

public abstract class BeanWrapper {
	private Class clazz;
	
	public BeanWrapper(Class clazz) {
		this.clazz = clazz;
	}
	
	public Class getMapClass(){
		return this.clazz;
	}
	
	public abstract Object mapRow(Map<String, String> data) throws Exception;
}
