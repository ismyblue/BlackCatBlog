package com.ismyblue.field.tbfdname;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：UserFN   
* 类描述：t_users表字段名   
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月19日 下午2:59:10   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月19日 下午2:59:10   
* 修改备注：   
* @version    
*
 */
public class UserFN {	
	
	public static final String TABLENAME_STRING = "t_users";
	
	/**
	 * 用户id
	 */
	public static final String ID_STRING = "id";
	/**
	 * 用户权限，可选值在UserPrivilegeField中
	 */
	public static final String USERPRIVILEGE_STRING = "user_privilege";	
	public static final String USERLOGIN_STRING = "user_login";
	public static final String USERPASS_STRING = "user_pass";
	public static final String USERNICENAME_STRING = "user_nicename";
	public static final String USERAVATARURL_STRING = "user_avatar_url";
	public static final String USEREMAIL_STRING = "user_email";
	public static final String USERURL_STRING = "user_url";
	public static final String USERREGISTERED_STRING = "user_registered";
	public static final String USERSTATUS_STRING = "user_status";
	public static final String SITENAME_STRING = "site_name";
	public static final String LOGINIP_STRING = "login_ip";	
	public static final String LOGINTIMES_STRING = "login_times";
	
	/**
	 * 插入语句
	 */
	public static final String INSERT_STRING = "insert into " +TABLENAME_STRING+"("+USERPRIVILEGE_STRING+","+USERLOGIN_STRING+","
				+USERPASS_STRING+","+USERNICENAME_STRING+","+USERAVATARURL_STRING+","+USEREMAIL_STRING+","+USERURL_STRING+","+USERREGISTERED_STRING+","
				+USERSTATUS_STRING+","+SITENAME_STRING+","+LOGINIP_STRING+") values(?,?,?,?,?,?,?,?,?,?,?);";
	
	 /** 删除语句前缀
	 */
	public static final String DELETEPX_STRING = "delete from "+TABLENAME_STRING+" ";
	
	/**
	 * 选择语句前缀
	 */
	public static final String SELECTPX_STRING = "select " + ID_STRING +","+USERPRIVILEGE_STRING+","+USERLOGIN_STRING+","
			+USERPASS_STRING+","+USERNICENAME_STRING +","+USERAVATARURL_STRING+","+USEREMAIL_STRING+","+USERURL_STRING+","+USERREGISTERED_STRING+","
			+USERSTATUS_STRING+","+SITENAME_STRING+","+LOGINIP_STRING+","+LOGINTIMES_STRING+" from "+TABLENAME_STRING + " "; 
	
	/**
	 * 更新语句前缀
	 */
	public static final String UPDATEPX_STRING = "update " + TABLENAME_STRING +" set "+USERPRIVILEGE_STRING+"=?,"+USERLOGIN_STRING+"=?,"
			+USERPASS_STRING+"=?,"+USERNICENAME_STRING +"=?,"+USERAVATARURL_STRING+"=?,"+USEREMAIL_STRING+"=?,"+USERURL_STRING+"=?,"+USERREGISTERED_STRING+"=?,"
			+USERSTATUS_STRING+"=?,"+SITENAME_STRING+"=?,"+LOGINIP_STRING+"=?,"+LOGINTIMES_STRING + "=? ";
	
	public static final String GETCOUNT_STRING = "select count(?)" + " from " + TABLENAME_STRING;
}


	

