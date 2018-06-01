package com.ismyblue.entity;

import java.util.Date;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：Post   
* 类描述： Post文章实体类  
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月16日 下午11:26:07   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月16日 下午11:26:07   
* 修改备注：   
* @version    
*
 */
public class Post {

	/**
	 * 文章id
	 */
	private int id;
	/**
	 * 文章的属主用户id
	 */
	private int userId;
	/**
	 * 文章属于的分类id
	 */
	private int categoryId;
	/**
	 * 文章的标题
	 */
	private String postTitle;
	/**
	 * 文章的内容
	 */
	private String postContent;
	/**
	 * 文章的发布日期
	 */
	private Date postDate;
	/**
	 * 文章的摘要
	 */
	private String postExcerpt;
	/**
	 * 文章发布状态，可选值在PostStatusField中
	 */
	private String postStatus;
	/**
	 * 评论状态，可选值在CommentStatusField中
	 */
	private String commentStatus;
	/**
	 * 文章的修改日期
	 */
	private Date postModified;
	/**
	 * 文章评论数量
	 */
	private int commentCount;
	/**
	 * 文章访问量
	 */
	private int visitCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getPostExcerpt() {
		return postExcerpt;
	}
	public void setPostExcerpt(String postExcerpt) {
		this.postExcerpt = postExcerpt;
	}
	public String getPostStatus() {
		return postStatus;
	}
	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}
	public String getCommentStatus() {
		return commentStatus;
	}
	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}
	public Date getPostModified() {
		return postModified;
	}
	public void setPostModified(Date postModified) {
		this.postModified = postModified;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	public Post() {
		super();
	}
	public Post(int id, int userId, int categoryId, String postTitle, String postContent, Date postDate,
			String postExcerpt, String postStatus, String commentStatus, Date postModified, int commentCount,
			int visitCount) {
		super();
		this.id = id;
		this.userId = userId;
		this.categoryId = categoryId;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postDate = postDate;
		this.postExcerpt = postExcerpt;
		this.postStatus = postStatus;
		this.commentStatus = commentStatus;
		this.postModified = postModified;
		this.commentCount = commentCount;
		this.visitCount = visitCount;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[id:");				sb.append(getId());
		sb.append(", userId:");			sb.append(getUserId());
		sb.append(", categoryId:");		sb.append(getCategoryId());
		sb.append(", postTitle:");		sb.append(getPostTitle());
		sb.append(", postContent:");	sb.append(getPostContent());
		sb.append(", postDate:");		sb.append(getPostDate());
		sb.append(", postExcerpt:");	sb.append(getPostExcerpt());	
		sb.append(", postStatus:");		sb.append(getPostStatus());
		sb.append(", commentStatus:");	sb.append(getCommentStatus());
		sb.append(", postModified:");	sb.append(getPostModified());
		sb.append(", commentCount:");	sb.append(getCommentCount());
		sb.append(", visitCount:");		sb.append(getVisitCount());
		sb.append("]\n");
		return sb.toString();
	}
		
	
}
