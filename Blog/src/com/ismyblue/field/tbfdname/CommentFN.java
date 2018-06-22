package com.ismyblue.field.tbfdname;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：CommentFN   
* 类描述：   t_comments表字段名
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月19日 下午2:57:03   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月19日 下午2:57:03   
* 修改备注：   
* @version    
*
 */
public class CommentFN {

	public static final String TABLENAME_STRING = "t_comments";
	
	public static final String ID_STRING = "id";
	public static final String PARENTID_STRING = "parent_id";
	public static final String POSTID_STRING = "post_id";	
	public static final String COMMENTCONTENT_STRING = "comment_content";
	public static final String COMMENTAUTHOREMAIL_STRING = "comment_author_email";
	public static final String COMMENTAUTHORURL_STRING = "comment_author_url";
	public static final String COMMENTAUTHORIP_STRING = "comment_author_ip";
	public static final String COMMENTDATE_STRING = "comment_date";
	public static final String COMMENTVISIBLE_STRING = "comment_visible";
	public static final String COMMENTDELETE_STRING = "comment_delete";

	/** 插入语句
	 */
	public static final String INSERT_STRING = "insert into " +TABLENAME_STRING+"("+PARENTID_STRING+","+POSTID_STRING+","
				+COMMENTCONTENT_STRING+","+COMMENTAUTHOREMAIL_STRING+","+COMMENTAUTHORURL_STRING+","+COMMENTAUTHORIP_STRING+","
				+COMMENTDATE_STRING+","+COMMENTVISIBLE_STRING+","+COMMENTDELETE_STRING+") values(?,?,?,?,?,?,?,?,?);";
	
	 /** 删除语句前缀
	 */
	public static final String DELETEPX_STRING = "delete from "+TABLENAME_STRING+" ";
	
	/**
	 * 选择语句前缀
	 */
	public static final String SELECTPX_STRING = "select " + ID_STRING +","+PARENTID_STRING+","+POSTID_STRING+","
			+COMMENTCONTENT_STRING +","+COMMENTAUTHOREMAIL_STRING+","+COMMENTAUTHORURL_STRING+","+COMMENTAUTHORIP_STRING+","
			+COMMENTDATE_STRING+","+COMMENTVISIBLE_STRING+","+COMMENTDELETE_STRING+" from "+TABLENAME_STRING + " "; 
	
	/**
	 * 更新语句前缀
	 */
	public static final String UPDATEPX_STRING = "update " + TABLENAME_STRING +" set "+PARENTID_STRING+"=?,"+POSTID_STRING+"=?,"
			+COMMENTCONTENT_STRING +"=?,"+COMMENTAUTHOREMAIL_STRING+"=?,"+COMMENTAUTHORURL_STRING+"=?,"+COMMENTAUTHORIP_STRING+"=?,"
			+COMMENTDATE_STRING+"=?,"+COMMENTVISIBLE_STRING+"=?,"+COMMENTDELETE_STRING+"=? ";
	
	/**
	 * 获得评论个数
	 */
	public static final String GETCOUNT_STRING = "select count(?)" + " from " + TABLENAME_STRING;
	
}
