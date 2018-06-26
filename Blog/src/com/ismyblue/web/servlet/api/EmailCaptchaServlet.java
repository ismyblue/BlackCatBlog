package com.ismyblue.web.servlet.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.util.JsonConvertUtil;
import com.ismyblue.util.SendEmailUtil;

public class EmailCaptchaServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");		
		String type = request.getParameter("type");
		if(type == null || type == ""){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed: 没有请求类型"));
			return ;
		}
		if(email == null || email == ""){
			response.setStatus(400);
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed: 没有email参数"));
			return ;
		}
		String message;
		if(type.equals("register")){
			message = "注册验证码：";			
		}else if(type.equals("recover")){
			message = "找回密码验证码";
		}else {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed: 请求类型(type)不对!(register/recover)"));
			return ;
		}		
		
		SendEmailUtil sendEmailUtil = new SendEmailUtil(6);
		try {
			sendEmailUtil.send(email,message);
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success"));			
			request.getSession().setAttribute(SessionAttr.EMAILCAPTCHA_STRING, sendEmailUtil.getEmailCaptcha());
		} catch (Exception e) {			
			e.printStackTrace();
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "falied: " + e.getMessage()));
		}	
		
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
}
