package com.ismyblue.web.servlet.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.ismyblue.entity.Category;
import com.ismyblue.entity.User;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.field.tbfdvalue.UserPrivilegeTbField;
import com.ismyblue.service.CategoryService;
import com.ismyblue.service.UserService;

public class CategoriesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 增加一个分类，参数
	 * {
	 *	"userId": 73843,
	 *	"parentId": 73963,
	 *	"categoryName": "5LBp5w4VII"
	 * }
	 */	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category category = new Category();
		try {
			BeanUtils.populate(category, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			response.getWriter().write("failed:" + e.getMessage());
		}
		HttpSession session = request.getSession();
		User loginedUser = (User) session.getAttribute(SessionAttr.USER_STRING);
		if(loginedUser == null){
			response.getWriter().print("failed:用户未登录");
			return ;
		}				
		//如果用户不是管理员，只能为自己添加分类
		if(category.getId() == 0 || !loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)){
			category.setUserId(loginedUser.getId());
		}	
		//如果userId用户不存在，那么错误
		if(new UserService().findUser(category.getUserId()) == null){
			response.getWriter().print("failed:分类属主用户不存在");return ;
		}
		
		CategoryService categoryService = new CategoryService();
		//如果没有指定父亲分类，那么父亲分类就是自己 null
		if(category.getParentId() == 0){
			//添加分类			
			if(categoryService.addCategory(category)){
				response.getWriter().print("success");
				return ;
			}else{
				response.getWriter().print("failed:添加失败");
				return ;
			}			
		}else{//如果指定了父亲分类，加入数据库
			Category parentCategory = categoryService.findCategory(category.getParentId());
			if(parentCategory == null){
				response.getWriter().print("failed:父亲分类不存在");
				return ;
			}
			if(categoryService.addCategory(category)){
				response.getWriter().print("success");
				return ;
			}else{
				response.getWriter().print("failed:添加失败");
				return ;
			}			
		}				
	}
	
	/**
	 * 删除一个分类参数id
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		if(idStr == null){
			response.getWriter().print("failed:没有id参数"); return ;
		}
		int id = Integer.parseInt(idStr);
		System.out.println(id);
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		if(loginedUser == null){
			response.getWriter().print("failed:没有登陆信息");return ;
		}
		CategoryService categoryService = new CategoryService();
		Category findCategory = categoryService.findCategory(id);
		if(findCategory == null){
			response.getWriter().print("failed:分类不存在");return ;
		}
		//如果用户不是管理员，判断分类是否属于他
		if(!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)){
			if(findCategory.getUserId() != loginedUser.getId()){
				response.getWriter().print("failed:此分类不属于登陆用户所有");return ;
			}
		}
		//删除分类
		if(categoryService.removeCategory(id)){
			response.getWriter().print("success");return ;
		}else {
			response.getWriter().print("failed:删除失败");return ;
		}
		
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}


	/**
	 * 更改一个分类
	 * {
		"id": 62826,
		"parentId": 63616,
		"userId": 63931,
		"categoryName": "pU4DwhV2Ux"
		}
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		
		
	}

	
	

}
