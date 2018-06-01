package com.ismyblue.entity;

import java.util.Date;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：Comment   
* 类描述：t_comment评论实体类   
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月16日 下午11:40:50   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月16日 下午11:40:50   
* 修改备注：   
* @version    
*
 */
public class Comment {

	/**
	 * 评论id
	 */
	private int id;
	/**
	 * 父评论id
	 */
	private int parentId;
	/**
	 * 评论文章id
	 */
	private int postId;
	/**
	 * 评论内容
	 */
	private String commentContent;
	/**
	 * 评论作者邮箱
	 */
	private String commentAuthorEmail;
	/**
	 * 评论作者url
	 */
	private String commentAuthorUrl;
	/**
	 * 评论作者ip
	 */
	private String commentAuthorIp;
	/**
	 * 评论时间
	 */
	private Date commentDate;
	/**
	 * 评论状态
	 */
	private String commentVisible;
	/**
	 * 评论是否被删除
	 */
	private String commentDelete;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentAuthorEmail() {
		return commentAuthorEmail;
	}
	public void setCommentAuthorEmail(String commentAuthorEmail) {
		this.commentAuthorEmail = commentAuthorEmail;
	}
	public String getCommentAuthorUrl() {
		return commentAuthorUrl;
	}
	public void setCommentAuthorUrl(String commentAuthorUrl) {
		this.commentAuthorUrl = commentAuthorUrl;
	}
	public String getCommentAuthorIp() {
		return commentAuthorIp;
	}
	public void setCommentAuthorIp(String commentAuthorIp) {
		this.commentAuthorIp = commentAuthorIp;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public String getCommentVisible() {
		return commentVisible;
	}
	public void setCommentVisible(String commentVisible) {
		this.commentVisible = commentVisible;
	}
	public String getCommentDelete() {
		return commentDelete;
	}
	public void setCommentDelete(String commentDelete) {
		this.commentDelete = commentDelete;
	}
	public Comment() {
		super();
	}
	public Comment(int id, int parentId, int postId, String commentContent, String commentAuthorEmail,
			String commentAuthorUrl, String commentAuthorIp, Date commentDate, String commentVisible,
			String commentDelete) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.postId = postId;
		this.commentContent = commentContent;
		this.commentAuthorEmail = commentAuthorEmail;
		this.commentAuthorUrl = commentAuthorUrl;
		this.commentAuthorIp = commentAuthorIp;
		this.commentDate = commentDate;
		this.commentVisible = commentVisible;
		this.commentDelete = commentDelete;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[id:");				sb.append(getId());
		sb.append(", parentId:");	sb.append(getParentId());
		sb.append(", postId:");		sb.append(getPostId());
		sb.append(", commentConetent:");		sb.append(getCommentContent());
		sb.append(", commentAuthorEmail:");	sb.append(getCommentAuthorEmail());
		sb.append(", commentAuthorUrl:");		sb.append(getCommentAuthorUrl());
		sb.append(", commentAuthorIp:");		sb.append(getCommentAuthorIp());
		sb.append(", commentDate:");	sb.append(getCommentDate());
		sb.append(", commentVisible:");		sb.append(getCommentVisible());
		sb.append(", commentDelete:");		sb.append(getCommentDelete());		
		sb.append("]\n");
		return sb.toString();
	}
	

}
