package com.ismyblue.entity;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：Link   
* 类描述：数据库中t_links表链接实体类   
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月16日 下午11:56:09   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月16日 下午11:56:09   
* 修改备注：   
* @version    
*
 */
public class Link {

	/**
	 * 链接id
	 */
	private int id;
	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 链接名称
	 */
	private String linkName;
	/**
	 * 链接的url
	 */
	private String linkUrl;
	/**
	 * 链接的描述
	 */
	private String linkDescription;
	/**
	 * 链接是否删除
	 */
	private String linkDelete;

	
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


	public String getLinkName() {
		return linkName;
	}


	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}


	public String getLinkUrl() {
		return linkUrl;
	}


	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}


	public String getLinkDescription() {
		return linkDescription;
	}


	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}


	public String getLinkDelete() {
		return linkDelete;
	}


	public void setLinkDelete(String linkDelete) {
		this.linkDelete = linkDelete;
	}


	public Link() {
		super();
	}


	public Link(int id, int userId, String linkName, String linkUrl, String linkDescription, String linkDelete) {
		super();
		this.id = id;
		this.userId = userId;
		this.linkName = linkName;
		this.linkUrl = linkUrl;
		this.linkDescription = linkDescription;
		this.linkDelete = linkDelete;
	}


	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[id:");				sb.append(getId());
		sb.append(", userId:");			sb.append(getUserId());
		sb.append(", linkName:");		sb.append(getLinkName());
		sb.append(", linkUrl:");		sb.append(getLinkUrl());
		sb.append(", linkDescription:");	sb.append(getLinkDescription());
		sb.append(", linkDelete:");		sb.append(getLinkDelete());
		sb.append("]\n");
		return sb.toString();
	}
	
	
}
