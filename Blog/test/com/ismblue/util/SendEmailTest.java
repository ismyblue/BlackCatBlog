package com.ismblue.util;

import org.junit.Test;

import com.ismyblue.field.path.ConfigPathField;
import com.ismyblue.util.SendEmailUtil;

public class SendEmailTest {

	
	public void testSend(){
		SendEmailUtil emailUtil = new SendEmailUtil(8);
		try {
			if(emailUtil.send("1141345963@qq.com","验证码")){
				System.out.println("成功");
			}else {
				System.out.println("失败");
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}

	@Test
	public void testAppPath() {
		System.out.println(ConfigPathField.APPPATH_STRING);
	}
	
}
