package com.ismyblue.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ismyblue.dao.UserDAO;
import com.ismyblue.entity.User;
import com.ismyblue.field.tbfdname.UserFN;
import com.ismyblue.field.tbfdvalue.UserPrivilegeTbField;
import com.ismyblue.field.tbfdvalue.UserStatusTbField;

public class UserDAOImplTest {

	
	public void testAddUser(){	
		UserDAO userDAO = new UserDAOImpl();		
//		User user = userDAO.findUserById(14);
		User user = new User(1, UserPrivilegeTbField.USER_STRING,"avatar", "loginpass", "loginname","avatar_url", "114134963@qq.com", "www.simyl.com", new Date()
				,UserStatusTbField.DISABLE_STRING,"fasfsad","fadsf",0);
			
		if(userDAO.addUser(user)>0)
			System.out.println("成功");
		else {
			System.out.println("失败");
		}
					
	}
	
	public void testRemoveUser(){
		User user = new User();
		user.setId(11);
		UserDAO userDAO = new UserDAOImpl();
		if(userDAO.removeUser(user)>0)
			System.out.println("成功");
		else {
			System.out.println("失败");
		}
	}
	
	
	public void testFindUser(){
		UserDAO userDAO = new UserDAOImpl();
		User user = userDAO.findUserById(13);
		System.out.println(user);
	}
	

	public void testUpdateUser(){
		UserDAO userDAO = new UserDAOImpl();		
		User user = userDAO.findUserById(13);
		System.out.println(user);
		int len = user.getId();
		for(int i = 0;i < len;i++){			
			user.setLoginTimes(user.getLoginTimes() + 1);
			System.out.println("updated, affected rows:" + userDAO.updateUser(user));
			System.out.println(user);
		}
	}
		
	@Test
	public void testFindUsers(){
		UserDAO userdao = new UserDAOImpl();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(UserFN.USERPRIVILEGE_STRING, UserPrivilegeTbField.ADMIN_STRING );
		
		User[] users = userdao.findUsers(m);		
		for(int i = 0;i < users.length;i++){
			System.out.println(users[i]);			
		}
	}

	
}
