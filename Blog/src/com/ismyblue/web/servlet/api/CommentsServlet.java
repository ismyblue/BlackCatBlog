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

import com.ismyblue.entity.Comment;
import com.ismyblue.entity.Post;
import com.ismyblue.entity.User;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.field.tbfdvalue.CommentDeleteTbField;
import com.ismyblue.field.tbfdvalue.CommentVisibleTbField;
import com.ismyblue.field.tbfdvalue.UserPrivilegeTbField;
import com.ismyblue.service.CommentService;
import com.ismyblue.service.PostService;
import com.ismyblue.util.JsonConvertUtil;
import com.ismyblue.util.MyConvert;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CommentsServlet extends HttpServlet {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获得当前登陆用户，如果没有登陆，那么响应错误信息
	 * @param request
	 * @param response
	 * @return User
	 * @throws IOException
	 */
	private User getLoginedUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		if(loginedUser == null){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:用户未登录！"));
			return null;
		}
		return loginedUser;
	}
	
	/**
	 * 增加一篇评论
	 * {
			"id": 59434,
			"parentId": 59814,
			"commentId": 59925,
			"commentContent": "JDppA19WZD",
			"commentAuthorEmail": "u2l0i4uY3@8MJH0.poc",
			"commentAuthorUrl": "http://nor.mcgeqiaa.shklf"
		}
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Comment comment = new Comment();
		try {
			ConvertUtils.register(new MyConvert(), Date.class);
			BeanUtils.populate(comment, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		comment.setCommentAuthorIp(request.getRemoteAddr());
		comment.setCommentDate(new Date());
		comment.setCommentDelete(CommentDeleteTbField.NODELETE_STRING);
		comment.setCommentVisible(CommentVisibleTbField.VISIBLE_STRING);
		CommentService commentService = new CommentService();
		//没有登陆用户也能评论。	
		if(commentService.addComment(comment)){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success"));
		}else {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:添加失败"));
		}	
		
	}	
	
	/**
	 * 删除一篇评论
	 */
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User loginedUser = getLoginedUser(request, response);
		String idString = request.getParameter("id");
		if(idString == null){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:没有id参数"));return;
		}
		int id = Integer.parseInt(idString);
		CommentService commentService = new CommentService();
		Comment findComment = commentService.findComment(id);
		PostService postService = new PostService();
		Post findPost = postService.findPost(findComment.getPostId());
		//如果用户不是管理员，并且这个评论不是指定用户的文章下的评论，那么可以删除
		if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING) 
				&& findPost.getUserId() != loginedUser.getId()){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "falied:没有权限，此评论没有评论在你的文章下"));return ;
		}
		if(commentService.removeComment(id)){
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success"));
		}else {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:删除失败"));
		}
		
	}

	/**
	 * 更新一篇评论
	 * {
			"id": 100124,
			
			"parentId": 101034,
			"commentId": 101072,
			"commentContent": "JuJeBtekty",
			"commentAuthorEmail": "VcC5Rrq@9KLXi.kyu",
			"commentAuthorUrl": "http://jvi.gxyqsizgp.zyu",
			"commentAuthorIp": "4lBwJDGoGQ",
			"commentDate": 52658,
			"commentVisible": "rEXbduAORs",
			"commentDelete": "mMTCyqazCb"
		}		
	 */
	@Deprecated
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		User loginedUser = getLoginedUser(request, response);
//		Comment comment = new Comment();
//		try {
//			ConvertUtils.register(new DateConvert() , Date.class);
//			BeanUtils.populate(comment, request.getParameterMap());
//		} catch (IllegalAccessException | InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		System.out.println(comment);
//		CommentService commentService = new CommentService();
//		Comment findComment = commentService.findComment(comment.getId());

	}
	
	/**
	 * 获得一个评论
	 * {
			"id": "8DxykWUM5n",
			"postId": "70648"
		}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loginedUser = getLoginedUser(request, response);
		if(request.getServletPath().equals("/api/comments/amount")){
			doGetAmount(request, response);
			return ;
		}
		
		String idString = request.getParameter("id");
		String postIdString = request.getParameter("postId");
		String pageString = request.getParameter("page");
		String countString = request.getParameter("count");	
		
		CommentService commentService = new CommentService();
		if(idString != null){
			Comment findComment = commentService.findComment(Integer.parseInt(idString));
			//如果用户不是管理员，并且评论不属于登陆用户的文章下
			if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)
					&& new PostService().findPost(findComment.getPostId()).getUserId() == loginedUser.getId()){
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:评论不属于登陆用户的文章"));return ;
			}
			response.getWriter().print(getCommentsJsonObject(findComment));
		}else if(postIdString != null && pageString != null && countString != null){
			if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)
					&& new PostService().findPost(Integer.parseInt(postIdString)).getUserId() == loginedUser.getId()){
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:评论不属于登陆用户的文章"));return ;
			}
			Comment[] findComments = commentService.getComments(Integer.parseInt(postIdString),
					Integer.parseInt(pageString), Integer.parseInt(countString));
			response.getWriter().print(getCommentsJsonObject(findComments));
		}
	}

	/**
	 * 获得评论数量
	 * @param request
	 * @param response
	 * {
			"id": "83578",
			"postId": "83734"
		}
	 * @throws IOException 
	 */
	private void doGetAmount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User loginedUser = getLoginedUser(request, response);
		String idString = request.getParameter("id");
		String postIdString = request.getParameter("postId");
		CommentService commentService = new CommentService();
		//查询某一评论的子评论的数量
		if(idString != null){
			int id = Integer.parseInt(idString);
			Comment findComment = commentService.findComment(id);
			Post findPost = new PostService().findPost(findComment.getPostId());
			//如果当前登陆用户不是管理员，并且这个评论不属于用户的文章下的评论，那么返回错误信息
			if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)
					&& findPost.getUserId() != loginedUser.getId()){
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:没有权限，这个评论不属于当前用户的文章"));
				return ;
			}
			long amount = commentService.getAmountByCommentId(id);
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", amount));
		//查询某一文章的子评论的数量
		}else if(postIdString != null){
			int postId = Integer.parseInt(postIdString);
			long amount = commentService.getAmountByPostId(postId);
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", amount));
		}
		
	}

	private JSONObject commentToJsonObject(Comment comment){
		JSONObject jsonObject = JSONObject.fromObject(comment);	
		SimpleDateFormat f = new SimpleDateFormat("yyy-MM-dd HH:mm:ss:SS");
		jsonObject.put("commentDate", f.format(comment.getCommentDate()));		
		return jsonObject;
	}

	/**
	 * 获得comment的json对象
	 * @param findComments
	 * @return
	 */
	private JSONObject getCommentsJsonObject(Comment[] findComments) {
		if(findComments == null)
			return null;
		JSONObject json = new JSONObject();
		JSONArray comments = new JSONArray();
		for(int i = 0;i < findComments.length;i++){
			comments.add(commentToJsonObject(findComments[i]));
		}
		json.put("comments", comments);
		return json;		
	}

	private JSONObject getCommentsJsonObject(Comment findComment) {
		if(findComment == null)
			return null;
		JSONObject json = new JSONObject();
		JSONArray comments = new JSONArray();		
		comments.add(commentToJsonObject(findComment));		
		json.put("comments", comments);
		return json;		
	}
	
	
}
