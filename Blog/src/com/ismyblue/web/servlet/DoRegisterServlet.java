package com.ismyblue.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.ismyblue.entity.User;
import com.ismyblue.field.http.RequestAttr;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.field.tbfdvalue.UserPrivilegeTbField;
import com.ismyblue.field.tbfdvalue.UserStatusTbField;
import com.ismyblue.service.UserService;

public class DoRegisterServlet extends HttpServlet {

	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(!request.getParameter("emailCaptcha").equals(session.getAttribute(SessionAttr.EMAILCAPTCHA_STRING))){
			request.setAttribute(RequestAttr.REGISTERMSG_STRING, "Email verification code error!");
			request.getRequestDispatcher("/register.html").forward(request, response);
		}
		
		//获取表单数据
		Map<String, String[]> parameterMap = request.getParameterMap();		
		User user = new User();
		//处理业务逻辑
		try {
			BeanUtils.populate(user, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {			
			e.printStackTrace();
		}		
		
		user.setUserPrivilege(UserPrivilegeTbField.USER_STRING);
		user.setUserRegistered(new Date());
		user.setLoginIp(request.getRemoteAddr());
		user.setUserStatus(UserStatusTbField.ENABLE_STRING);	
		
		System.out.println(user);
		//分发转向
		UserService userService = new UserService();
		if(userService.addUser(user)){			
			response.setHeader("refresh", "3;url=login.html");
			response.sendRedirect("/success.html");
		}else {			
			request.setAttribute(RequestAttr.REGISTERMSG_STRING,"用户注册失败");
			request.getRequestDispatcher("/register.html").forward(request, response);
		}
		
	}

}
