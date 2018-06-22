package com.ismyblue.web.servlet.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.util.SendEmailUtil;

public class EmailCaptchaServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");		
		String type = request.getParameter("type");
		if(type == null){
			response.getWriter().write("failed: 请求类型(type)不对!(register/recover)");
			return ;
		}
		if(email == null){
			response.getWriter().write("failed: 没有email参数");
			return ;
		}
		String message;
		if(type.equals("register")){
			message = "注册验证码：";			
		}else if(type.equals("recover")){
			message = "找回密码验证码";
		}else {
			response.getWriter().write("failed: 请求类型(type)不对!(register/recover)");
			return ;
		}		
		
		SendEmailUtil sendEmailUtil = new SendEmailUtil(6);
		try {			
			sendEmailUtil.send(email,message);
			response.getWriter().write("success");			
			request.getSession().setAttribute(SessionAttr.EMAILCAPTCHA_STRING, sendEmailUtil.getEmailCaptcha());
		} catch (Exception e) {			
			e.printStackTrace();
			response.getWriter().write("falied: " + e.getMessage());
		}	
		
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
}
