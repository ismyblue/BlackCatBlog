package com.ismyblue.util.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import com.ismyblue.field.path.SrcPathField;
import com.ismyblue.util.CaptchaUtil;

public class CaptchaUtilTest {
	@Test
	public void testWriteTo() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("D:/code/javaProjects/Blog" + SrcPathField.CAPTCHWORD_STRING), "UTF-8"));
		String str ;
		int index = 0;
		while((str = reader.readLine()) != null){
//		for(int i = 0;i < 100;i++){
			CaptchaUtil captchaUtil = new CaptchaUtil(200,100,str,10);
			String code = captchaUtil.getCaptcha();		
			captchaUtil.setImageFormatName("png");
			captchaUtil.writeTo(new FileOutputStream("image/" + code +".png"));
			captchaUtil = null;
			if(index++ > 1000)
				break;
		}
		reader.close();		
		
	}
	

	
	public static void main(String[] args) {
		
		Thread[] threads = new Thread[10];
		for(int i = 0;i < 10;i++){
			threads[i] = new Thread(new Runnable() {				
				@Override
				public void run() {
					try {
						CaptchaUtilTest c = new CaptchaUtilTest();
						c.testWriteTo();
						
					} catch (IOException e) {
						e.printStackTrace();
					}				
				}
			}); 
			threads[i].start();
		}
	}
}
