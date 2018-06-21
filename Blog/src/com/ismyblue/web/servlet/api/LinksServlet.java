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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

	/**
	 * 更新一个链接
	 */
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		if(loginedUser == null){
			response.getWriter().print("falied:用户未登录！");return ;
		}
		
		Link newLink = new Link();
		try {
			BeanUtils.populate(newLink, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}		
		LinkService linkService = new LinkService();
		Link findLink = linkService.findLink(newLink.getId());
		if(findLink == null){
			response.getWriter().print("failed:此链接不存在");return ;
		}
		//如果登陆用户不是管理员，那么只可以更新自己的link
		if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)
				&& findLink.getUserId() != loginedUser.getId()){
			response.getWriter().print("failed:此链接不属于登陆用户");return ;
		}	
		if(linkService.updateLink(newLink)){
			response.getWriter().print("success");
		}else {
			response.getWriter().print("failed:更新失败！linkUrl不能重复");
		}

	}
	
	/**
	 * 查找一个link
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		if(loginedUser == null){
			response.getWriter().print("failed:用户未登录！");return ;
		}
		
		if(request.getServletPath().equals("/api/links/amount")){
			doGetAmount(request, response);
			return ;
		}
		
		String userIdString = request.getParameter("userId");
		int userId = loginedUser.getId();		
		if(userIdString != null){
			userId = Integer.parseInt(userIdString);
		}
		//如果登陆用户不是管理员，那么只能查找自己的links
		if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING) 
				&& userId != loginedUser.getId()){
			response.getWriter().print("failed:不能查找别人的link");
		}

		String pageString = request.getParameter("page");
		String countString = request.getParameter("count");
		String idString = request.getParameter("id");
		LinkService linkService = new LinkService();		
		//分页输出
		if(pageString != null && countString != null){
			Link[] links = linkService.getLinks(userId, Integer.parseInt(pageString), Integer.parseInt(countString));
			response.getWriter().print(getLinksJsonObject(links));
		//输出这个link
		}else if(idString != null){
			Link findLink = linkService.findLink(Integer.parseInt(idString));
			if(findLink == null){
				response.getWriter().print("failed:此链接不存在！");return ;
			}
			//如果登陆用户不是管理员，那么只能查找自己的链接link
			if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)
					&& findLink.getUserId() != loginedUser.getId()){
				response.getWriter().print("failed:此链接不属于登陆用户！");return ;
			}
			response.getWriter().print(getLinksJsonObject(findLink));
		//输出用户所有的link
		}else if(idString == null){
			Link[] findLinks = linkService.findLinksByUserId(userId);
			response.getWriter().print(getLinksJsonObject(findLinks));
		}
		
	}

	/**
	 * 处理获得链接数量请求
	 * @param request
	 * @param response
	 */
	private void doGetAmount(HttpServletRequest request, HttpServletResponse response) {
		String userIdString = request.getParameter("userId");
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		int userId = loginedUser.getId();
		if(userIdString != null){
			userId = Integer.parseInt(userIdString);
		}
		LinkService linkService = new LinkService();
		try {
			response.getWriter().print(linkService.getAmount(userId));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 获得Links json对象
	 * @param links
	 * @return
	 */
	private JSONObject getLinksJsonObject(Link[] links) {
		JSONObject json = new JSONObject();
		JSONArray linksJsonArray = JSONArray.fromObject(links);
		json.put("links", linksJsonArray);
		return json;
	}

	/**
	 * 获得Links json对象，重载
	 * @param link
	 * @return
	 */	
	private JSONObject getLinksJsonObject(Link link) {
		JSONObject json = new JSONObject();
		JSONArray linksJsonArray = JSONArray.fromObject(link);
		json.put("links", linksJsonArray);
		return json;
	}

}
