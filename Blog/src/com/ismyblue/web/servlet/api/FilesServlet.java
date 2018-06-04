package com.ismyblue.web.servlet.api;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ismyblue.entity.User;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.field.tbfdvalue.UserPrivilegeTbField;
import com.ismyblue.service.UserService;
import com.ismyblue.util.FileUploadUtil;

public class FilesServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	/**
	 * 上传文件 所需参数userId,
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		FileUploadUtil fileUploadUtil;
		try {
			fileUploadUtil = new FileUploadUtil(request);		
			User loginedUser = (User) request.getSession().getAttribute(SessionAttr.USER_STRING);
			if(loginedUser == null){
				response.getWriter().write("failed: 用户未登录");
				return ;
			}
			String userId = fileUploadUtil.getParamter("userId");
			//只有管理员才能指定用户上传文件
			if(userId != null && !loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)){
				response.getWriter().write("failed: 权限不够");
				return ;
			}
			if(new UserService().findUser(Integer.parseInt(userId)) == null){
				response.getWriter().write("failed:指定用户不存在");
				return ;
			}
			int id = (userId == null)?loginedUser.getId():Integer.parseInt(userId);
			try {			
				fileUploadUtil.saveInToDir("upload" + File.separator + id);
				//添加link到数据库			
				response.getWriter().write("success:" + fileUploadUtil.getParamter("file"));
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("failed: " + e.getMessage());
				return ;
			}		
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("failed: " + e.getMessage());
		}
	}

}
