package com.ismyblue.entity;

import java.util.Date;

/**
 * 
 * 
 * 项目名称：Blog 类名称：User 类描述： 用户实体类 创建人：ismyblue@163.com 创建时间：2018年5月16日 下午11:02:04
 * 修改人：ismyblue@163.com 修改时间：2018年5月16日 下午11:02:04 修改备注：
 * 
 * @version
 *
 */
public class User {

	/**
	 * 用户id
	 */
	private int id;
	/**
	 * 用户权限，可选值在UserPrivilegeField中
	 */
	private String userPrivilege;
	/**
	 * 用户登陆名
	 */
	private String userLogin;
	/**
	 * 用户密码 
	 */
	private String userPass;	
	/**
	 * 用户昵称
	 */
	private String userNicename;
	/**
	 * 用户头像url
	 */
	private String userAvatarUrl;
	/**
	 * 用户邮箱
	 */
	private String userEmail;
	/**
	 * 用户在本网站的博客url
	 */
	private String userUrl;
	/**
	 * 用户注册时间
	 */
	private Date userRegistered;
	/**
	 * 用户账号状态
	 */
	private String userStatus;
	/**
	 * 用户的博客名称
	 */
	private String siteName;
	/**
	 * 用户最后一次登陆的ip地址
	 */
	private String loginIp;
	/**
	 * 用户的登陆总次数
	 */
	private int loginTimes;

	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUserPrivilege() {
		return userPrivilege;
	}



	public void setUserPrivilege(String userPrivilege) {
		this.userPrivilege = userPrivilege;
	}



	public String getUserLogin() {
		return userLogin;
	}



	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}



	public String getUserPass() {
		return userPass;
	}



	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}



	public String getUserNicename() {
		return userNicename;
	}



	public void setUserNicename(String userNicename) {
		this.userNicename = userNicename;
	}



	public String getUserAvatarUrl() {
		return userAvatarUrl;
	}



	public void setUserAvatarUrl(String userAvatarUrl) {
		this.userAvatarUrl = userAvatarUrl;
	}



	public String getUserEmail() {
		return userEmail;
	}



	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}



	public String getUserUrl() {
		return userUrl;
	}



	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}



	public Date getUserRegistered() {
		return userRegistered;
	}



	public void setUserRegistered(Date userRegistered) {
		this.userRegistered = userRegistered;
	}



	public String getUserStatus() {
		return userStatus;
	}



	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}



	public String getSiteName() {
		return siteName;
	}



	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}



	public String getLoginIp() {
		return loginIp;
	}



	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}



	public int getLoginTimes() {
		return loginTimes;
	}



	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}
	
	public User() {
		super();
	}

	public User(int id, String userPrivilege, String userLogin, String userPass, String userNicename,
			String userAvatarUrl, String userEmail, String userUrl, Date userRegistered, String userStatus,
			String siteName, String loginIp, int loginTimes) {
		super();
		this.id = id;
		this.userPrivilege = userPrivilege;
		this.userLogin = userLogin;
		this.userPass = userPass;
		this.userNicename = userNicename;
		this.userAvatarUrl = userAvatarUrl;
		this.userEmail = userEmail;
		this.userUrl = userUrl;
		this.userRegistered = userRegistered;
		this.userStatus = userStatus;
		this.siteName = siteName;
		this.loginIp = loginIp;
		this.loginTimes = loginTimes;
	}
	



	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[id:");				sb.append(getId());
		sb.append(", userPrivilege:");	sb.append(getUserPrivilege());
		sb.append(", userLogin:");		sb.append(getUserLogin());
		sb.append(", userPass:");		sb.append(getUserPass());
		sb.append(", userNicename:");	sb.append(getUserNicename());
		sb.append(", userAvatarUrl:");	sb.append(getUserAvatarUrl());
		sb.append(", userEmail:");		sb.append(getUserEmail());
		sb.append(", userUrl:");		sb.append(getUserUrl());
		sb.append(", userRegistered:");	sb.append(getUserRegistered());
		sb.append(", userStatus:");		sb.append(getUserStatus());
		sb.append(", siteName:");		sb.append(getSiteName());
		sb.append(", loginIp:");		sb.append(getLoginIp());
		sb.append(", loginTimes:");		sb.append(getLoginTimes());
		sb.append("]\n");
		return sb.toString();
	}
	
}
