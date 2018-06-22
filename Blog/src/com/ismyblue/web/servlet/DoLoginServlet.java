package com.ismyblue.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.ismyblue.entity.User;
import com.ismyblue.field.http.RequestAttr;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.service.UserService;

public class DoLoginServlet extends HttpServlet {


	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map<String, String[]> parameterMap = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {			
			e.printStackTrace();
		}
		
		UserService userService = new UserService();
		String captcha = request.getParameter("captcha");
		User dbUser = userService.login(user);
		if(dbUser == null || !captcha.equalsIgnoreCase((String) request.getSession().getAttribute(SessionAttr.CAPTCHA_STRING))){			
			request.setAttribute(RequestAttr.INFOMSG_STRING, "用户登录失败!");
			response.setHeader("refresh", "2;login.html");
			request.getRequestDispatcher("info.jsp").forward(request, response);
		}else {	
			request.getSession().setAttribute(SessionAttr.USER_STRING, dbUser);
			response.sendRedirect("home.jsp");			
		}
	
	}
	
}
