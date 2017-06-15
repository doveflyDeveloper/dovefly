package com.deertt.frame.base.web.converter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;
public class TimestampConverter implements Converter<String, Timestamp> {	
	public Timestamp convert(String source) {
		Timestamp result = null;
		if(StringUtils.isNotEmpty(source)) {
			try{
				SimpleDateFormat df = null;
				if (source.length() == 19) {//yyyy-MM-dd HH:mm:ss
					df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				} else {
					df = new SimpleDateFormat("yyyy-MM-dd");
				}
				result = new Timestamp(df.parse(source).getTime());
			} catch (Exception e){
				
			}
		}
		return result;
	}
}
