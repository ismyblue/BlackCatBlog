package com.ismyblue.dao.impl;

import java.util.Date;
import java.util.Map;

import com.ismyblue.dao.UserDAO;
import com.ismyblue.entity.User;
import com.ismyblue.field.tbfdname.UserFN;
import com.ismyblue.util.SqlUtil;

public class UserDAOImpl implements UserDAO {

	@Override
	public int addUser(User user) {
		String sql = UserFN.INSERT_STRING;
		Object[] params = {user.getUserPrivilege(),user.getUserLogin(),user.getUserPass(),user.getUserNicename(),
							user.getUserAvatarUrl(),user.getUserEmail(),user.getUserUrl(),user.getUserRegistered(),user.getUserStatus(),
							user.getSiteName(),user.getLoginIp()};

		return SqlUtil.executeNonQuery(sql, params);
	}


	/**
	 * 不建议使用
	 */
	@Override	
	public int addUsers(User[] users) {
		int r = -1;
		for(int i = 0;i < users.length;i++){
			if(addUser(users[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public int removeUser(User user) {
		String sql = UserFN.DELETEPX_STRING + "where id = ?";
		Object param = user.getId();
		return SqlUtil.executeNonQuery(sql, param);
	}

	
	/**
	 * 不建议使用
	 */
	@Override
	public int removeUsers(User[] users) {
		int r = -1;
		for(int i = 0;i < users.length;i++){
			if(removeUser(users[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public int updateUser(User user) {		
		String sql = UserFN.UPDATEPX_STRING + " where id=? ";
		
		Object[] params = {user.getUserPrivilege(),user.getUserLogin(),user.getUserPass(),user.getUserNicename(),
							user.getUserAvatarUrl(),user.getUserEmail(),user.getUserUrl(),user.getUserRegistered(),
							user.getUserStatus(),user.getSiteName(),user.getLoginIp(),user.getLoginTimes(),user.getId()};		
		return SqlUtil.executeNonQuery(sql, params);
	}

	
	/**
	 * 返回成功的条数
	 */
	@Override
	public int updateUsers(User[] users) {
		int r = -1;
		for(int i = 0;i < users.length;i++){
			if(updateUser(users[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	/**
	 * 没有查到就返回null
	 */
	@Override
	public User findUserById(int id) {
		String sql = UserFN.SELECTPX_STRING + " where id = ?";
		Map<String, Object>[] entitys = SqlUtil.executeQueryReturnMapArray(sql, id);	
		if(entitys.length > 0)
			return MapArrayToUsers(entitys)[0];
		else {
			return null;
		}
	}

	@Override
	public User[] findUsers(Map<String, Object> paramsMap) {		
		StringBuffer sqlsb = new StringBuffer(UserFN.SELECTPX_STRING + " where ");
		Object[] params = null;
		int size = paramsMap.size();
		if(size > 0)
			params = new Object[size];
		
		int i = 0;		
		for(Map.Entry<String, Object> e : paramsMap.entrySet()){
			params[i] = e.getValue();
			if(i != 0){
				sqlsb.append(" and ");				
			}
			i++;
			sqlsb.append(e.getKey());
			sqlsb.append("=?");			
		}
		Map<String, Object>[] mapArry = SqlUtil.executeQueryReturnMapArray(sqlsb.toString(), params);
		
		return  MapArrayToUsers(mapArry);
		
		
	}

	/**
	 * 元组转用户实体，没有元组则返回null
	 * @param entitys
	 * @return
	 */
	private User[] MapArrayToUsers(Map<String, Object>[]  entitys){		
		if(entitys == null)	return null;		
		int len = entitys.length;
		if(len == 0) return null;
		
		User[] users = new User[len];		
		Map<String, Object> entity = null;
		for(int i = 0 ;i < len;i++){			
			//处理一个实体转化成User
			entity = entitys[i];	
			users[i] = new User();
			for(Map.Entry<String, Object> e : entity.entrySet()){
				
				switch (e.getKey()) {
					case UserFN.ID_STRING: users[i].setId((int) e.getValue()); break;
					case UserFN.USERPRIVILEGE_STRING: users[i].setUserPrivilege((String) e.getValue());  break;
					case UserFN.USERLOGIN_STRING: users[i].setUserLogin((String) e.getValue()); break;
					case UserFN.USERPASS_STRING: users[i].setUserPass((String) e.getValue()); break;
					case UserFN.USERNICENAME_STRING: users[i].setUserNicename((String) e.getValue()); break;
					case UserFN.USERAVATARURL_STRING: users[i].setUserAvatarUrl((String) e.getValue()); break;
					case UserFN.USEREMAIL_STRING: users[i].setUserEmail((String) e.getValue()); break;
					case UserFN.USERURL_STRING: users[i].setUserUrl((String) e.getValue()); break;
					case UserFN.USERREGISTERED_STRING: users[i].setUserRegistered((Date) e.getValue()); break;
					case UserFN.USERSTATUS_STRING: users[i].setUserStatus((String) e.getValue()); break;
					case UserFN.SITENAME_STRING: users[i].setSiteName((String) e.getValue()); break;
					case UserFN.LOGINIP_STRING: users[i].setLoginIp((String) e.getValue()); break;
					case UserFN.LOGINTIMES_STRING: users[i].setLoginTimes((int) e.getValue()); break;
					default:
						System.out.println("User实体未匹配到" + e.getKey() + "字段");
						break;
				}			
				
			}			
		}		
		return users;
			
	}

	@Override
	public User findUserByUserLogin(String userLogin) {
		String sql = UserFN.SELECTPX_STRING + " where "+UserFN.USERLOGIN_STRING+" = ?";
		Map<String, Object>[] entitys = SqlUtil.executeQueryReturnMapArray(sql, userLogin);	
		if(entitys.length > 0)
			return MapArrayToUsers(entitys)[0];
		else {
			return null;
		}
	}

	@Override
	public long getAmount() {
		String sql = UserFN.GETCOUNT_STRING;
		Map<String, Object>[] r = SqlUtil.executeQueryReturnMapArray(sql, UserFN.ID_STRING);
		for(Map.Entry<String, Object> e : r[0].entrySet()){
			return (long) e.getValue();			
		}
		return 0;				
	}

	@Override
	public User[] findUsersByPage(int page, int count) {
		String sql = UserFN.SELECTPX_STRING + " limit ?,?";
		Object[] params = {(page-1)*count,count};
		Map<String, Object>[] r = SqlUtil.executeQueryReturnMapArray(sql,params);
		return MapArrayToUsers(r);
	}
	

}
