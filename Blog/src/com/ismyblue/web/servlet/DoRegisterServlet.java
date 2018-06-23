package com.ismyblue.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.ismyblue.entity.User;
import com.ismyblue.field.http.RequestAttr;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.field.path.ConfigPathField;
import com.ismyblue.field.tbfdvalue.UserPrivilegeTbField;
import com.ismyblue.field.tbfdvalue.UserStatusTbField;
import com.ismyblue.service.UserService;
import com.ismyblue.util.MD5Util;

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
			request.setAttribute(RequestAttr.INFOMSG_STRING, "邮箱验证码错误!");
			response.setHeader("refresh", "2;register.html");
			request.getRequestDispatcher("info.jsp").forward(request, response);
			return ;
		}
		System.out.println(request.getParameter("emailCaptcha") + " " + session.getAttribute(SessionAttr.EMAILCAPTCHA_STRING));	
		User user = new User();
		//处理业务逻辑
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {			
			e.printStackTrace();
		}		
		user.setUserPass(MD5Util.encode(user.getUserPass()));
		user.setUserNicename(user.getUserLogin());
		user.setUserUrl(ConfigPathField.DOMAINNAME_STRING+"/user/"+user.getUserLogin());
		user.setSiteName(user.getUserLogin() + "'s website");
		user.setUserAvatarUrl("/avatar");
		user.setUserPrivilege(UserPrivilegeTbField.USER_STRING);
		user.setUserRegistered(new Date());
		user.setLoginIp(request.getRemoteAddr());
		user.setUserStatus(UserStatusTbField.ENABLE_STRING);	
		
		//分发转向
		UserService userService = new UserService();
		if(userService.addUser(user)){
			request.setAttribute(RequestAttr.INFOMSG_STRING,"注册成功！2秒后跳转");
			response.setHeader("refresh", "2;url=login.html");
			request.getRequestDispatcher("info.jsp").forward(request, response);				
		}else {			
			request.setAttribute(RequestAttr.INFOMSG_STRING,"用户注册失败！2秒后跳转");
			response.setHeader("refresh", "2;url=register.html");
			request.getRequestDispatcher("info.jsp").forward(request, response);			
		}
		
	}

}
