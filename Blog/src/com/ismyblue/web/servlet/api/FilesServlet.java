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
import com.ismyblue.util.JsonConvertUtil;

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
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed: 用户未登录"));
				return ;
			}
			String userId = fileUploadUtil.getParamter("userId");
			//只有管理员才能指定用户上传文件
			if((userId != null && userId != "") && !loginedUser.getUserPrivilege().equals(UserPrivilegeTbField.ADMIN_STRING)){
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed: 权限不够"));
				return ;
			}
			int id = (userId != null && userId != "")?loginedUser.getId():Integer.parseInt(userId);
			if(new UserService().findUser(id) == null){
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed:指定用户不存在"));
				return ;
			}	
			
			try {			
				fileUploadUtil.saveInToDir("upload" + File.separator + id);
				//添加link到数据库			
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "success:" + fileUploadUtil.getParamter("file")));
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().print(JsonConvertUtil.getJsonObject("result", "failed: " + e.getMessage()));
				return ;
			}		
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			response.getWriter().write("failed: " + e.getMessage());
		}
	}

}
