package com.ismyblue.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class MyConvert implements Converter {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object convert(Class type, Object value) {
		if ((!(value instanceof String)) || (value == null)) {
			return null;
		}
		String dateValue = (String) value;
		if ("".equals(dateValue.trim())) {
			return null;
		}
		// 自定义时间的格式为yyyy-MM-dd HH:mm:ss:SS
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
		// 创建日期类对象
		Date dt = null;
		try {
			// 使用自定义日期的格式转化value参数为yyyy-MM-dd格式
			dt = sdf.parse((String) value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回dt日期对象
		return dt;
	}

}
