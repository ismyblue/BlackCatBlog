package com.ismyblue.web.servlet.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.ismyblue.entity.Category;
import com.ismyblue.entity.Post;
import com.ismyblue.entity.User;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.field.tbfdvalue.UserPrivilegeTbField;
import com.ismyblue.service.CategoryService;
import com.ismyblue.service.PostService;
import com.ismyblue.util.JsonConvertUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PostsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * 增加一篇文章
	 *{
	"userId": 60379,
	"categoryId": 60682,
	"postTitle": "9TO2w8Fu0u",
	"postContent": "kxcCe5NHIx",
	"postDate": true,
	"postExcerpt": "rLoFgzbfrq",
	"postStatus": "wX9V3yeQAX",
	"commentStatus": "TYobYAmpt4",
	"postModified": "e2261TxYU5"
	}
	* 用户只能给自己新增，管理员可以给任何人新增 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Post post = new Post();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.populate(post, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			response.setStatus(400);
			response.getWriter().write("failed: " + e.getMessage());
		}	
		
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		//如果用户未登录
		if(loginedUser == null){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:用户未登录！"));return ;
		}
		//如果用户不是管理员，只能为自己添加文章
		if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)){
			post.setUserId(loginedUser.getId());
		}
		//设置修改时间为当前时间
		post.setPostModified(new Date());
		PostService postService = new PostService();
		if(postService.addPost(post)){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success"));
			return ;
		}else{
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:添加失败!可能原因：信息不正确"));
		}
	}

	/**
	 * 删除一篇文章，用户只能删除自己的，管理员可以删除任何人的
	 */
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("id");
		if(idString == null || idString == ""){
			response.setStatus(400);
			response.getWriter().print("failed:没有参数");return ;
		}
		int id = Integer.parseInt(idString);
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		if(loginedUser == null){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:用户未登录！"));return ;			
		}
		PostService postService = new PostService();
		Post findPost = postService.findPost(id);
		if(findPost == null){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:文章不存在！"));return ;
		}
		//如果用户不是管理员，那么只能删除自己的文章
		if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)){
			if(findPost.getUserId() != loginedUser.getId()){
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:文章不属于请求用户！"));return ;	
			}
		}
		if(postService.removePost(id)){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success"));return ;	
		}else{
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:删除失败！"));return ;	
		}
	}
	
	/**
	 * 更新一篇文章
	 */
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		//如果用户未登录
		if(loginedUser == null){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:用户未登录！"));return ;
		}
		
		Post newPost = new Post();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.populate(newPost, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			response.setStatus(400);
			response.getWriter().write("failed: " + e.getMessage());
		}

		PostService postService = new PostService();
		Post dbPost = postService.findPost(newPost.getId());
		if(dbPost == null){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:没有这个文章"));return ;
		}
		//如果登陆用户不是管理员,只能修改自己的文章
		if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)){
			if(newPost.getUserId() != loginedUser.getId()){
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:此文章不属于登陆用户!"));return ;
			}
		}
		if(postService.updatePost(newPost)){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success"));return ;
		}else {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:更新文章失败!"));return ;
		}
		
	}	
	
	/**
	 * 获得一篇文章
	 * 获得文章信息,参数可选：
		获得文章信息,参数可选：
		1.无， 获得当前用户下的指定所有文章
		2.id,    获得指定文章的信息
		3.categoryId, 获得当前用户指定分类下的所有文章
		4.userId,categoryId, 获得指定用户指定分类下的所有文章
		5.categoryId,page,count,   分页返回指定分类的文章
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		if(loginedUser == null){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:用户未登录！"));return ;
		}
		
		if(request.getServletPath().equals("/api/posts/amount")){
			doGetAmount(request, response);
			return ;
		}
		
		String idString = request.getParameter("id");
		String categoryIdString = request.getParameter("categoryId");
		String userIdString = request.getParameter("userId");
		String pageString = request.getParameter("page");
		String countString = request.getParameter("count");
		int userId = loginedUser.getId();		
		if(userIdString != null){
			userId = Integer.parseInt(userIdString);
		}
		
		PostService postService = new PostService();
		if(categoryIdString != null && pageString != null && countString != null){
			Post[] posts = postService.getPosts(Integer.parseInt(categoryIdString), Integer.parseInt(pageString),
						Integer.parseInt(countString));
			response.getWriter().print(getPostsJsonObject(posts));
		}
		//查询指定用户所有的文章信息
		if(idString == null && categoryIdString == null){
			Post[] findPosts = postService.findPostsByUserId(userId);
			response.getWriter().print(getPostsJsonObject(findPosts));
		//查询某一个指定文章的信息
		}else if(idString != null){
			Post findPost = postService.findPost(Integer.parseInt(idString));
			if(findPost == null){
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:文章不存在"));return ;	
			}
			//普通用户不能查找不是自己的文章
			if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING) 
					&& findPost.getUserId() != loginedUser.getId()){
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:此文章不属于登陆用户"));return ;	
			}
			response.getWriter().print(getPostsJsonObject(findPost));
		//查询某一分类下的所有文章信息
		}else if(categoryIdString != null){
			int categoryId = Integer.parseInt(categoryIdString);
			//普通用户不能查找不是自己的分类下的文章
			if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING) 
					&& new CategoryService().findCategory(categoryId).getUserId() != loginedUser.getId()){
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:此分类不属于登陆用户"));return ;	
			}
			Post[] findPosts = postService.findPostsByCategoryId(categoryId);
			response.getWriter().print(getPostsJsonObject(findPosts));
		//分页查找某一分类下的文章
		}
	}
	
	/**
	 * 获取某一分类下的文章数量
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void doGetAmount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);		
		String categoryIdString = request.getParameter("categoryId");
		
		if(categoryIdString == null){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "falied:没有指定categoryId"));			
		}
		int categoryId = Integer.parseInt(categoryIdString);

		CategoryService categoryService = new CategoryService();
		Category findCategory = categoryService.findCategory(categoryId);
		if(findCategory == null){			
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "falied:此categoryId不存在"));
		}
		if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING) 
				&& findCategory.getUserId() != loginedUser.getId()){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "falied:此分类不属于登陆用户"));return ;
		}
		PostService postService = new PostService();
		response.getWriter().print(JsonConvertUtil.getJsonObject("result", postService.getAmount(categoryId)));
	}

	private JSONObject postToJsonObject(Post post){
		JSONObject jsonObject = JSONObject.fromObject(post);	
		SimpleDateFormat f = new SimpleDateFormat("yyy-MM-dd HH:mm:ss:SS");
		jsonObject.put("postDate", f.format(post.getPostDate()));
		jsonObject.put("postModified", f.format(post.getPostModified()));
		return jsonObject;
	}

	/**
	 * 获得post的json对象
	 * @param findPosts
	 * @return
	 */
	private JSONObject getPostsJsonObject(Post[] findPosts) {
		JSONObject json = new JSONObject();
		JSONArray posts = new JSONArray();
		for(int i = 0;i < findPosts.length;i++){
			posts.add(postToJsonObject(findPosts[i]));
		}
		json.put("posts", posts);
		return json;		
	}

	private JSONObject getPostsJsonObject(Post findPost) {
		JSONObject json = new JSONObject();
		JSONArray posts = new JSONArray();		
		posts.add(postToJsonObject(findPost));		
		json.put("posts", posts);
		return json;		
	}
	
	
	
}
