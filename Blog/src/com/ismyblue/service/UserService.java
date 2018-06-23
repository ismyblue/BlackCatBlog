package com.ismyblue.service;

import com.ismyblue.dao.UserDAO;
import com.ismyblue.dao.impl.UserDAOImpl;
import com.ismyblue.entity.User;
import com.ismyblue.util.MD5Util;

public class UserService {

	/**
	 * 添加用户到数据库是否成功
	 * @param user
	 * @return
	 */
	public boolean addUser(User user){
		UserDAO userDAO = new UserDAOImpl();
		if(userDAO.addUser(user) > 0){
			return true;
		}
		return false;		
	}

	/**
	 * 删除一个用户
	 * @param id
	 * @return
	 */
	public boolean removeUser(int id){
		UserDAO userDAO = new UserDAOImpl();
		User user = new User();
		user.setId(id);
		if(userDAO.removeUser(user) > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 登陆成功返回一个User实体，不成功返回null.验证方法md5(md5(userPassword)+captcha)) == md5(md5(targetPassword)+captcha))
	 * user.getUserPass() = md5(md5(userPassword)+captcha))
	 * dbuser.getUserPass() = md5(userPassword)
	 * @param user
	 * @param captcha 
	 * @return
	 */
	public User login(User user, String captcha) {
		UserDAO userDao = new UserDAOImpl();
		User dbuser = userDao.findUserByUserLogin(user.getUserLogin());
		if(dbuser == null)
			return null;
		
		if(user.getUserPass().equals(MD5Util.encode(dbuser.getUserPass() + captcha))){
			return dbuser;
		}
		return null;
	}
	
	/**
	 * 查找一个用户
	 * @param id
	 * @return
	 */
	public User findUser(int id){
		UserDAO userdao = new UserDAOImpl();
		User user = userdao.findUserById(id);
		return user;
	}
	
	/**
	 * 更新一个用户
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user){
		UserDAO userdao = new UserDAOImpl();
		if(userdao.updateUser(user) > 0){
			return true;
		}
		return false;		
	}

	/**
	 * 获得用户总个数
	 * @return
	 */
	public long getAmount() {
		UserDAO userdao = new UserDAOImpl();
		return userdao.getAmount();
	}
	
	/**
	 * 通过限制每一页多少个，第几页来获取用户的数组
	 * @param page 第几页
	 * @param count 每一页多少个用户
	 * @return users  Array
	 */

	public User[] getUsers(int page, int count) {
		UserDAO userDAO = new UserDAOImpl();		
		return userDAO.findUsersByPage(page,count);
	}
	
	
	
}
