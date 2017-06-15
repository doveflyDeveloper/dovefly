package com.deertt.utils.helper;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ITransctVoField {
	public int transctVo(BeanWrapper bw, PropertyDescriptor pd);
}
