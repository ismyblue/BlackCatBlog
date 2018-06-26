package com.ismyblue.web.servlet.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.ismyblue.entity.Category;
import com.ismyblue.entity.CategoryInfo;
import com.ismyblue.entity.User;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.field.tbfdvalue.UserPrivilegeTbField;
import com.ismyblue.service.CategoryService;
import com.ismyblue.service.UserService;
import com.ismyblue.util.JsonConvertUtil;

import net.sf.json.JSONObject;

public class CategoriesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 增加一个分类，参数 { "userId": 73843, "parentId": 73963, "categoryName":
	 * "5LBp5w4VII" }
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Category category = new Category();
		try {
			BeanUtils.populate(category, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			response.setStatus(400);
			response.getWriter().write("failed:" + e.getMessage());
		}
		HttpSession session = request.getSession();
		User loginedUser = (User) session.getAttribute(SessionAttr.USER_STRING);
		if (loginedUser == null) {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:用户未登录"));
			return;
		}
		// 如果用户不是管理员，只能为自己添加分类
		if (category.getId() == 0 || !loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)) {
			category.setUserId(loginedUser.getId());
		}
		if(category.getUserId() == 0){
			response.setStatus(400);
			response.getWriter().write("failed: 未指定用户id");return ;
		}
		// 如果userId用户不存在，那么错误
		if (new UserService().findUser(category.getUserId()) == null) {			
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:分类属主用户不存在"));
			return;
		}

		CategoryService categoryService = new CategoryService();
		// 如果没有指定父亲分类，那么父亲分类就是自己 null
		if (category.getParentId() == 0) {
			// 添加分类
			if (categoryService.addCategory(category)) {
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success"));
				return;
			} else {
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:添加失败"));
				return;
			}
		} else {// 如果指定了父亲分类，加入数据库
			Category parentCategory = categoryService.findCategory(category.getParentId());
			if (parentCategory == null) {
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:父亲分类不存在"));
				return;
			}
			if (categoryService.addCategory(category)) {
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success"));
				return;
			} else {
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:添加失败"));
				return;
			}
		}
	}

	/**
	 * 删除一个分类参数id
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		if (idStr == null) {
			response.setStatus(400);
			response.getWriter().write("failed:没有id参数");
			return;
		}
		int id = Integer.parseInt(idStr);
		System.out.println(id);
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		if (loginedUser == null) {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:没有登陆信息"));
			return;
		}
		CategoryService categoryService = new CategoryService();
		Category findCategory = categoryService.findCategory(id);
		if (findCategory == null) {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:分类不存在"));
			return;
		}
		// 如果用户不是管理员，判断分类是否属于他
		if (!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)) {
			if (findCategory.getUserId() != loginedUser.getId()) {
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:此分类不属于登陆用户所有"));
				return;
			}
		}
		// 删除分类
		if (categoryService.removeCategory(id)) {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success"));
			return;
		} else {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:删除失败"));
			return;
		}

	}

	/**
	 * 更改一个分类 { "id": 62826, "parentId": 63616, "userId": 63931, "categoryName":
	 * "pU4DwhV2Ux" }
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		if (loginedUser == null) {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:用户未登录"));
			return;
		}
		Category category = new Category();
		try {
			BeanUtils.populate(category, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			response.setStatus(400);
			response.getWriter().print("failed:" + e.getMessage());
		}
		if (category.getId() == 0) {
			response.setStatus(400);
			response.getWriter().print("failed:未指定分类id");
			return;
		}
		if (category.getUserId() == 0) {
			category.setUserId(loginedUser.getId());
		}
		// 如果登陆用户是普通用户
		if (!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)) {
			if (loginedUser.getId() != category.getUserId()) {
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:没有权限"));
				return;
			}
		}
		System.out.println(category);
		CategoryService categoryService = new CategoryService();
		if (categoryService.findCategory(category.getParentId()).getUserId() != category.getUserId()) {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:父亲分类和子分类不属于同一用户"));
			return;
		}
		if (categoryService.updateCategory(category)) {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success"));
			return;
		} else {
			response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:可能原因，此分类不存在/父亲分类不存在/userId不存在"));
			return;
		}
	}

	/**
	 * 查找一个分类
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 处理获取用户个数请求
		if (request.getRequestURI().endsWith("/api/categories/amount")) {
			doGetAmount(request, response);
			return;
		}
		// 如果请求/api/categories
		User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
		String userIdStr = request.getParameter("userId");
		String idStr = request.getParameter("id");
		int userId;
		int id;
		if(userIdStr != null){
			userId = Integer.parseInt(userIdStr);
		}else {
			userId = loginedUser.getId();
		}
		// 如果不是管理员
		if (!loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)) {
			userId = loginedUser.getId();
		}		
		CategoryService categoryService = new CategoryService();
		if (idStr != null) {
			// 指定了分类id
			id = Integer.parseInt(idStr);
			Category[] categories = categoryService.findCategoriesByParentId(id);
			JSONObject jsonObject = new JSONObject();
			List<CategoryInfo> categoryInfoList = new LinkedList<CategoryInfo>();
			if (categories != null) {
				for (int i = 0; i < categories.length; i++) {
					CategoryInfo categoryInfo = new CategoryInfo(categories[i].getId(), categories[i].getParentId(),
							categories[i].getCategoryName(), new LinkedList<CategoryInfo>());
					categoryInfoList.add(categoryInfo);
				}
			}
			jsonObject.put("categories", categoryInfoList);
			response.getWriter().print(jsonObject);
		} else {
			// 没有指定分类id，得到全部分类
			Category[] categories = categoryService.findCategoriesByUserId(userId);
			response.getWriter().print(getJsonObject(categories));
		}

	}

	/**
	 * 处理获取分类数量请求
	 * 
	 * @param request
	 * @param response
	 */
	@Deprecated
	private void doGetAmount(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getAmount");
	}

	/**
	 * category实体转换成categoryInfo 的json对象
	 * 
	 * @param category
	 * @return
	 */
	private JSONObject getJsonObject(Category[] categories) {
		JSONObject jsonObject = new JSONObject();
		List<CategoryInfo> categoryInfoList = new LinkedList<CategoryInfo>();
		if (categories != null) {
			for (int i = 0; i < categories.length; i++) {
				CategoryInfo categoryInfo = new CategoryInfo(categories[i].getId(), categories[i].getParentId(),
						categories[i].getCategoryName(), new LinkedList<CategoryInfo>());
				addToCategoriesList(categoryInfoList, categoryInfo);
			}
		}
		jsonObject.put("categories", categoryInfoList);
		System.out.println(jsonObject);
		return jsonObject;
	}

	private void addToCategoriesList(List<CategoryInfo> list, CategoryInfo categoryInfo) {
		if (categoryInfo.getParentId() == categoryInfo.getId() || categoryInfo.getParentId() == 0) {
			list.add(categoryInfo);			
			return;
		}	
		System.out.println(categoryInfo);
		for (int i = 0; i < list.size(); i++) {
			
			if (list.get(i).getId() == categoryInfo.getParentId()) {
				list.get(i).getSubCategories().add(categoryInfo);
				return;
			}
			addToCategoriesList(list.get(i).getSubCategories(), categoryInfo);
		}
				
	}

}
