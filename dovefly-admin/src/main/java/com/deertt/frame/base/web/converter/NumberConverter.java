package com.deertt.frame.base.web.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

public class NumberConverter implements GenericConverter {

	@Override
	public Object convert(Object source, TypeDescriptor sourceType,	TypeDescriptor targetType) {
		Number number = 0;
		if (source == null || source.toString().length() == 0) {
			source = "0";
		}
		String sourceStr = source.toString().replaceAll(",", "");//对于千分位的数值处理
		if (targetType.getType() == int.class
				|| targetType.getType() == Integer.class) {
			try {
				number = Integer.parseInt(sourceStr);
			} catch (NumberFormatException e) {
				number = 0;
			}
		} else if (targetType.getType() == long.class
				|| targetType.getType() == Long.class) {
			try {
				number = Long.parseLong(sourceStr);
			} catch (NumberFormatException e) {
				number = 0L;
			}
		} else if (targetType.getType() == float.class
				|| targetType.getType() == Float.class) {
			try {
				number = Float.parseFloat(sourceStr);
			} catch (NumberFormatException e) {
				number = 0f;
			}
		} else if (targetType.getType() == double.class
				|| targetType.getType() == Double.class) {
			try {
				number = Double.parseDouble(sourceStr);
			} catch (NumberFormatException e) {
				number = 0d;
			}
		} else if (targetType.getType() == BigInteger.class) {
			try {
				number = new BigInteger(sourceStr);
			} catch (NumberFormatException e) {
				number = BigInteger.ZERO;
			}
		} else if (targetType.getType() == BigDecimal.class) {
			try {
				number = new BigDecimal(sourceStr);
			} catch (NumberFormatException e) {
				number = BigDecimal.ZERO;
			}
		}
		return number;
	}

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> pairs = new HashSet<ConvertiblePair>();
		pairs.add(new ConvertiblePair(String.class, int.class));
		pairs.add(new ConvertiblePair(String.class, Integer.class));
		pairs.add(new ConvertiblePair(String.class, long.class));
		pairs.add(new ConvertiblePair(String.class, Long.class));
		pairs.add(new ConvertiblePair(String.class, float.class));
		pairs.add(new ConvertiblePair(String.class, Float.class));
		pairs.add(new ConvertiblePair(String.class, double.class));
		pairs.add(new ConvertiblePair(String.class, Double.class));
		pairs.add(new ConvertiblePair(String.class, BigDecimal.class));
		pairs.add(new ConvertiblePair(String.class, BigInteger.class));

		return pairs;
	}

}
