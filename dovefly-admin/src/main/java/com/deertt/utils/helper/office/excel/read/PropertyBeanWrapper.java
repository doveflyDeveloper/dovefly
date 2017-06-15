package com.deertt.utils.helper.office.excel.read;

import java.util.Map;

public class PropertyBeanWrapper extends BeanWrapper{

	public PropertyBeanWrapper(Class clazz) {
		super(clazz);
	}

	@Override
	public Object mapRow(Map<String, String> data) throws Exception {
		Object bean = BeanToMapUtil.convertMap(this.getMapClass(), data);
		return bean;
	}

}
