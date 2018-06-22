package com.ismyblue.service;

import com.ismyblue.dao.CommentDAO;
import com.ismyblue.dao.impl.CommentDAOImpl;
import com.ismyblue.entity.Comment;

public class CommentService {

	/**
	 * 添加评论到数据库是否成功
	 * 
	 * @param comment
	 * @return
	 */
	public boolean addComment(Comment comment) {
		CommentDAO commentDAO = new CommentDAOImpl();
		if (commentDAO.addComment(comment) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除一个评论
	 * 
	 * @param id
	 * @return
	 */
	public boolean removeComment(int id) {
		CommentDAO commentDAO = new CommentDAOImpl();
		Comment comment = new Comment();
		comment.setId(id);
		if (commentDAO.removeComment(comment) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查找一个评论
	 * 
	 * @param id
	 * @return
	 */
	public Comment findComment(int id) {
		CommentDAO commentdao = new CommentDAOImpl();
		Comment comment = commentdao.findCommentById(id);
		return comment;
	}

	/**
	 * 更新一个评论
	 * 
	 * @param comment
	 * @return
	 */
	public boolean updateComment(Comment comment) {
		CommentDAO commentdao = new CommentDAOImpl();
		if (commentdao.updateComment(comment) > 0) {
			return true;
		}
		return false;
	}

	
	/**
	 * 通过父亲id获得此评论下的所有评论
	 * @param parentId
	 * @return
	 */
	public Comment[] findCommentsByParentId(int parentId) {
		CommentDAO commentdao = new CommentDAOImpl();		
		return commentdao.findCommentsByParentId(parentId);
	}

	/**
	 * 通过文章Id获得子评论的数量
	 * 
	 * @return
	 */
	public long getAmountByPostId(int postId) {
		CommentDAO commentdao = new CommentDAOImpl();
		return commentdao.getAmountByPostId(postId);
	}

	/**
	 * 通过一个评论的id查找子评论的数量
	 * @param commentId
	 * @return
	 */
	public long getAmountByCommentId(int commentId) {
		CommentDAO commentdao = new CommentDAOImpl();
		return commentdao.getAmountByCommentId(commentId);
	}
	
	/**
	 * 通过文章id，页码数，每页评论数量，来获得一些评论
	 * @param postId
	 * @param page
	 * @param count
	 * @return
	 */
	public Comment[] getComments(int postId,int page, int count) {
		CommentDAO commentDAO = new CommentDAOImpl();
		return commentDAO.findCommentsByPage(postId, page, count);
	}

}
