package com.deertt.utils.helper;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.number.RmNumberHelper;


public class DvPopulateParser implements IPopulateParser {

	public Object parse(Class<?> clazz, Object value) {
		if(value == null) {
			return null;
		}
		Object result = value;
		if(clazz.equals(value.getClass())) {
			return result;
		}
		if (String.class.equals(clazz)) { //字符串
			result = value.toString();
		} else if (Timestamp.class.equals(clazz)) { //时间戳创建对象
			result = DvDateHelper.getTimestamp(value.toString());
		} else if (Date.class.equals(clazz)) { //SQL日期创建对象
			result = DvDateHelper.getSqlDate(value.toString());
		} else if (BigDecimal.class.equals(clazz)) { //BigDecimal
			//将千分位的","全部替换为""
			result = RmNumberHelper.formatFromThousandth(value.toString());
		} else if (value instanceof Boolean){//Boolean类型
			if(value.equals(true)){
				result = 1;
			}else{
				result = 0;
			}
			return result;
		} else if (short.class.equals(clazz)
				|| int.class.equals(clazz)
				|| long.class.equals(clazz)
				|| float.class.equals(clazz)
				|| double.class.equals(clazz)) {
			//如果空字符串-->基本类型,忽略
			if(value.toString().length() == 0) {
				result = null;
			}
		}
		return result;
	}

}
