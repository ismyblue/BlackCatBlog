package com.ismyblue.web.servlet.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.ismyblue.entity.User;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.field.tbfdvalue.UserPrivilegeTbField;
import com.ismyblue.field.tbfdvalue.UserStatusTbField;
import com.ismyblue.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class UsersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 增加一个用户
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			response.getWriter().write("failed: " + e.getMessage());
		}
		HttpSession session = request.getSession();
		User loginedUser = (User) session.getAttribute(SessionAttr.USER_STRING);
		//如果当前没有用户登陆，或者当前登陆用户不是管理员，新增的用户只能是普通用户
		if(loginedUser == null || !loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)){
			user.setUserPrivilege(UserPrivilegeTbField.USER_STRING);			
		}
		user.setUserAvatarUrl("/");
		user.setUserRegistered(new Date());
		user.setUserStatus(UserStatusTbField.ENABLE_STRING);
		user.setLoginIp(request.getRemoteAddr());
		user.setLoginTimes(0);
		
		UserService userService = new UserService();		
		if(userService.addUser(user)){
			response.getWriter().write("success");
		}else{
			response.getWriter().write("failed: 添加失败");
		}		
		
	}
	

	/**
	 * 删除一个用户
	 */
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String idString = request.getParameter("id");
		if(idString == null){
			response.getWriter().write("failed: 没有给定id参数");
			return ;
		}		
		int id = Integer.parseInt(idString);
		
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);		
		//如果没有用户登录或者登录用户是普通用户，不能删除一个用户
		if(loginedUser == null || loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.USER_STRING)){			
			response.getWriter().write("failed: 您没有权限进行此操作");
			return ;
		}
		
		UserService userService = new UserService();
		
		if(userService.removeUser(id)){
			response.getWriter().write("success");
		}else{
			response.getWriter().write("failed: 删除失败！可能原因：用户不存在。");
		}		
		
			
	}


	/**
	 * 更新一个用户
	 */
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = new User();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.populate(user, request.getParameterMap());						
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			response.getWriter().write("failed: " + e.getMessage());
		}
		
		HttpSession session = request.getSession();
		User loginedUser = (User) session.getAttribute(SessionAttr.USER_STRING);
		//如果当前没有用户登陆，或者当前登陆用户不是管理员，新增的用户只能是普通用户
		if(loginedUser == null){
			response.getWriter().write("failed: 您没有登录，无法修改用户信息");
			return ;
		}		
		
		UserService userService = new UserService();		
		User dbUser = userService.findUser(user.getId());
				
		if(loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)){
			dbUser.setUserPrivilege(user.getUserPrivilege());
			dbUser.setUserStatus(user.getUserStatus());
			dbUser.setUserUrl(user.getUserUrl());
			dbUser.setLoginIp(user.getLoginIp());
			dbUser.setLoginTimes(user.getLoginTimes());
			dbUser.setUserRegistered(user.getUserRegistered());
		}
		dbUser.setUserLogin(user.getUserLogin());
		dbUser.setUserPass(user.getUserPass());
		dbUser.setUserNicename(user.getUserNicename());
		dbUser.setUserAvatarUrl(user.getUserAvatarUrl());
		dbUser.setUserEmail(user.getUserEmail());
		dbUser.setSiteName(user.getSiteName());		
				
		if(userService.updateUser(dbUser)){
			response.getWriter().write("success");
		}else{
			response.getWriter().write("failed: 更新失败！可能原因：用户不存在。");
		}		
				
	}
	
	/**
	 * 删除一个用户
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//处理获取用户个数请求		
		UserService userService1 = new UserService();
		request.getSession().setAttribute(SessionAttr.USER_STRING, userService1.findUser(14));
		
		if(request.getRequestURI().endsWith("/api/users/amount")){
			doGetAmount(request, response);			
			return ;
		}
		//如果请求/api/users
		
		String idString = request.getParameter("id");		
		String page = request.getParameter("page");
		String count = request.getParameter("count");
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);		
		if(loginedUser == null){
			response.getWriter().write("failed: 没有登陆信息");return ;
		}		
		UserService userService = new UserService();
		//如果没有指定用户信息也不分页输出，那么返回登陆用户的信息
		if(idString == null && page == null){			
			response.getWriter().print(getUsersJsonObjetct(loginedUser));
			return ;
		//输出指定用户
		}else if(idString != null && page == null){
			int id = Integer.parseInt(idString);			
			User findUser = userService.findUser(id);
			if(findUser == null){
				response.getWriter().write("failed: 用户不存在");return ;
			}
			response.getWriter().print(getUsersJsonObjetct(findUser));
			return ;
		//如果有页数和每一页限制数
		}else if(page != null && count != null){
			//如果不是管理员
			if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)){
				response.getWriter().write("failed: 没有权限");return ;
			}			
			//分页输出
			User[] users = userService.getUsers(Integer.parseInt(page), Integer.parseInt(count));			
			response.getWriter().print(getUsersJsonObjetct(users));
			return ;
		}else{
			response.getWriter().write("failed: 参数传递错误");return ;
		}				
	}

	/**
	 * 处理获取用户个数请求
	 * @param request
	 * @param response
	 */
	private void doGetAmount(HttpServletRequest request, HttpServletResponse response){
		UserService userService = new UserService();		
		try {
			response.getWriter().write(String.valueOf(userService.getAmount()));
		} catch (IOException e) {
			e.printStackTrace();			
		}
	}
	
	/**
	 * User转成User的Json对象
	 * @param user
	 * @return
	 */
	private JSONObject ToJsonObject(User user){
		JSONObject jsonUser = new JSONObject();
		jsonUser = JSONObject.fromObject(user);
		SimpleDateFormat f = new SimpleDateFormat("yyy-MM-dd HH:mm:ss:SS");
		jsonUser.put("userRegistered", f.format(user.getUserRegistered()));
		jsonUser.remove("userPass");	
		return jsonUser;
	}
	
	/**
	 * 获得Users数组的json对象,没有用户返回null
	 * @param user
	 * @return
	 */
	private JSONObject getUsersJsonObjetct(User user){
		if(user == null)
			return null;
		return ToJsonObject(user);
	}
	
	/**
	 * 获得Users数组的json对象,没有用户返回null
	 * @param users
	 * @return
	 */
	private JSONObject getUsersJsonObjetct(User[] users){
		if(users == null){
			return null;
		}
		JSONObject usersObject = new JSONObject();
		JSONArray jsonUsers = new JSONArray();
		for(int i = 0;i < users.length;i++){				
			jsonUsers.add(i, ToJsonObject(users[i]));			
		}
		usersObject.put("users", jsonUsers);
		return usersObject;
	}
}
