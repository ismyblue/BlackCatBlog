package com.ismyblue.dao;

import java.util.Map;

import com.ismyblue.entity.Comment;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：CommentDao 
* 类描述：   评论数据访问操作接口
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月18日 下午1:11:05   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月18日 下午1:11:05   
* 修改备注：   
* @version    
*
 */
public interface CommentDAO {	
	
	/**
	 * 增加一个评论到数据库中
	 * @param comment
	 * @return
	 */
	public int addComment(Comment comment);
	
	/**
	 * 增加多个评论到数据库中
	 * @param comments
	 * @return
	 */
	public int addComments(Comment[] comments);
	
	/**
	 * 删除一个评论
	 * @param comment
	 * @return
	 */
	public int removeComment(Comment comment);
	
	/**
	 * 删除多个评论
	 * @param comments
	 * @return
	 */
	public int removeComments(Comment[] comments);
	
	/**
	 * 更新一个评论	 
	 * @param newComment
	 * @return
	 */
	public int updateComment(Comment comment);
	
	/**
	 * 更新多个评论
	 * @param comments
	 * @return
	 */
	public int updateComments(Comment[] comments);
	
	/**
	 * 通过id查找评论
	 * @param id
	 * @return
	 */
	public Comment findCommentById(int id);
	
	/**
	 * 通过一个参数的键值对map来查询同时满足这些条件的comment
	 * @param paramsMap
	 * @return
	 */
	public Comment[] findComments(Map<String, Object> paramsMap);

	/**
	 *  通过文章id，页码数，每页评论数量，来获得一些评论
	 * @param postId
	 * @param page
	 * @param count
	 * @return
	 */
	public Comment[] findCommentsByPage(int postId, int page, int count);

	/**
	 * 通过父亲id，获得这个评论下的所有评论
	 * @param parentId
	 * @return
	 */
	public Comment[] findCommentsByParentId(int parentId);

	/**
	 * 通过文章id，获得此篇文章下的评论的个数,没有则返回0
	 * @param postId
	 * @return
	 */
	public long getAmountByPostId(int postId);
	
	/**
	 * 通过评论id，获得这个评论下的评论的个数,没有则返回0
	 * @param commentId
	 * @return
	 */
	public long getAmountByCommentId(int commentId);
	
	
	
}

