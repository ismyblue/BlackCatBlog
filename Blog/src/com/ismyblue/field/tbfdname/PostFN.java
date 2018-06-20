package com.ismyblue.field.tbfdname;


/**
 * 
*    
* 项目名称：Blog   
* 类名称：PostFN   
* 类描述：   t_posts表字段名
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月19日 下午2:58:43   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月19日 下午2:58:43   
* 修改备注：   
* @version    
*
 */
public class PostFN {

	public static final String TABLENAME_STRING = "t_posts";
	
	public static final String ID_STRING = "id";
	public static final String USERID_STRING = "user_id";
	public static final String CATEGORYID_STRING = "category_id";
	public static final String POSTTITLE_STRING = "post_title";
	public static final String POSTCONTENT_STRING = "post_content";
	public static final String POSTDATE_STRING = "post_date";
	public static final String POSTEXCERPT_STRING = "post_excerpt";
	/**
	 * 文章发布状态，可选值在PostStatusField中
	 */
	public static final String POSTSTATUS_STRING = "post_status";
	/**
	 * 评论状态，可选值在CommentStatusField中
	 */
	public static final String COMMENTSTATUS_STRING = "comment_status";
	public static final String POSTMODIFIED_STRING = "post_modified";
	public static final String COMMENTCOUNT_STRING = "comment_count";
	public static final String VISITCOUNT_STRING = "visit_count";

	/**
	 * 插入语句
	 */
	public static final String INSERT_STRING = "insert into " +TABLENAME_STRING+"("+USERID_STRING+","+CATEGORYID_STRING+","
				+POSTTITLE_STRING+","+POSTCONTENT_STRING+","+POSTDATE_STRING+","+POSTEXCERPT_STRING+","+POSTSTATUS_STRING+","
				+COMMENTSTATUS_STRING+","+POSTMODIFIED_STRING+","+COMMENTCOUNT_STRING+","+VISITCOUNT_STRING+") values(?,?,?,?,?,?,?,?,?,?,?)";
	
	 /** 删除语句前缀
	 */
	public static final String DELETEPX_STRING = "delete from "+TABLENAME_STRING+" ";
	
	 /** 选择语句前缀
	 */
	public static final String SELECTPX_STRING = "select " + ID_STRING +","+USERID_STRING+","+CATEGORYID_STRING+","
			+POSTTITLE_STRING+","+POSTCONTENT_STRING +","+POSTDATE_STRING+","+POSTEXCERPT_STRING+","+POSTSTATUS_STRING+","
			+COMMENTSTATUS_STRING+","+POSTMODIFIED_STRING+","+COMMENTCOUNT_STRING+","+VISITCOUNT_STRING+" from "+TABLENAME_STRING + " "; 


	/**
	 * 更新语句前缀
	 */
	public static final String UPDATEPX_STRING = "update " + TABLENAME_STRING +" set "+USERID_STRING+"=?,"+CATEGORYID_STRING+"=?,"
			+POSTTITLE_STRING+"=?,"+POSTCONTENT_STRING +"=?,"+POSTDATE_STRING+"=?,"+POSTEXCERPT_STRING+"=?,"+POSTSTATUS_STRING+"=?,"
			+COMMENTSTATUS_STRING+"=?,"+POSTMODIFIED_STRING+"=?,"+COMMENTCOUNT_STRING+"=?,"+VISITCOUNT_STRING + "=? ";
	
	
	
	
}
