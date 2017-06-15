package com.deertt.frame.base.web.converter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;
public class SqlDateConverter implements Converter<String, Date> {	
	public Date convert(String source) {
		Date result = null;
		if(StringUtils.isNotEmpty(source)) {
			try{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				result = new Date(df.parse(source).getTime());
			} catch (Exception e){
				
			}
		}
		return result;
	}
}
