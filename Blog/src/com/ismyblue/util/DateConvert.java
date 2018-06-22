package com.ismyblue.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

//时间转换器
public class DateConvert implements Converter{
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object convert(Class type, Object value) {
		 //定义一个日期格式  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS");  
        //判断是否是要转换成日期格式  
        if(type==Date.class) {  
            String date = (String)(value);	            
          Date curDate;
			try {
				curDate = sdf.parse(date);
                return curDate;	  
			} catch (ParseException e) {
				e.printStackTrace();
			}            	              
        }  
        return null;  
	}		
}