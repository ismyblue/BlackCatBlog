package com.ismyblue.web.servlet.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.ismyblue.entity.Link;
import com.ismyblue.entity.User;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.field.tbfdvalue.LinkDeleteTbField;
import com.ismyblue.field.tbfdvalue.UserPrivilegeTbField;
import com.ismyblue.service.LinkService;

public class LinksServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	/**
	 * 增加一个链接
	 * {
			"userId": 67657,
			"linkName": "YA95LxpXhe",
			"linkUrl": "http://psb.ssdcqtlhqkqkr.izrtnc",
			"linkDescription": "Ydb2sErIHD",
			"linkDelete": "dUxmrlTxSm"
		}
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		if(loginedUser == null){
			response.getWriter().print("failed:用户未登录！");return ;			
		}
		Link link = new Link();
		try {
			BeanUtils.populate(link, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		link.setLinkDelete(LinkDeleteTbField.NODELETE_STRING);
		System.out.println(link);
		//如果登陆用户不是管理员，那么只能为自己添加link
		if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)
				&& link.getUserId()!=loginedUser.getId()){
			response.getWriter().print("failed:要添加的链接的用户id不是登录用户id");return ;
		}
		LinkService linkService = new LinkService();
		if(linkService.addLink(link)){
			response.getWriter().print("success");
		}else {
			response.getWriter().print("failed:添加链接失败!linkUrl不能重复");
		}
		
	}

	/**
	 * 删除一个链接
	 * id
	 */
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		if(loginedUser == null){
			response.getWriter().print("failed:用户未登录！");return ;			
		}
		
		String idString = request.getParameter("id");
		if(idString == null){
			response.getWriter().print("failed:没有参数");return ;
		}
		int id = Integer.parseInt(idString);
		LinkService  linkService = new LinkService();
		Link findLink = linkService.findLink(id);
		//如果登陆用户不是管理员并且用户链接不属于登陆用户
		if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)
				&& findLink.getUserId() != loginedUser.getId()){
			response.getWriter().print("failed:此链接不属于登陆用户!");
		}
		if(linkService.removeLink(id)){
			response.getWriter().print("success");
		}else {
			response.getWriter().print("failed:删除失败!");
		}
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
	}


	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}

}
