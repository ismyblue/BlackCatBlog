package com.ismyblue.web.servlet.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.util.CaptchaUtil;

public class CaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CaptchaUtil captchaUtil = new CaptchaUtil(200, 100, 4, 10);		
		String captcha = captchaUtil.getCaptcha();
		request.getSession().setAttribute(SessionAttr.CAPTCHA_STRING, captcha);
		System.out.println(captcha);		
		captchaUtil.writeTo(response.getOutputStream());
		
	}

}
