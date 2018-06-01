package com.ismyblue.dao;

import java.util.Map;

import com.ismyblue.entity.User;

/**
 * 用户dao操作
*    
* 项目名称：Blog   
* 类名称：UserDao 
* 类描述：   用户数据访问操作接口
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月18日 下午1:11:05   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月18日 下午1:11:05   
* 修改备注：   
* @version    
*
 */
public interface UserDAO {	
	
	/**
	 * 增加一个用户到数据库中
	 * @param user
	 * @return
	 */
	public int addUser(User user);
	
	/**
	 * 增加多个用户到数据库中
	 * @param users
	 * @return
	 */
	public int addUsers(User[] users);
	
	/**
	 * 删除一个用户
	 * @param user
	 * @return
	 */
	public int removeUser(User user);
	
	/**
	 * 删除多个用户，返回成功的条数
	 * @param users
	 * @return
	 */
	public int removeUsers(User[] users);
	
	/**
	 * 更新一个用户	 
	 * @param newUser
	 * @return
	 */
	public int updateUser(User user);
	
	/**
	 * 更新多个用户
	 * @param users
	 * @return
	 */
	public int updateUsers(User[] users);
	
	/**
	 * 通过id查找用户,没有查到返回null
	 * @param id
	 * @return
	 */
	public User findUserById(int id);
	
	/**
	 * 通过一个参数的键值对map来查询同时满足这些条件的user
	 * @param paramsMap
	 * @return
	 */
	public User[] findUsers(Map<String, Object> paramsMap);

	public User findUserByUserLogin(String userLogin);

	/**
	 * 获得用户总数
	 * @return
	 */
	public long getAmount();

	/**
	 * 分页查找用户
	 * @param page
	 * @param count
	 * @return
	 */
	public User[] findUsersByPage(int page, int count);

	
	
}

