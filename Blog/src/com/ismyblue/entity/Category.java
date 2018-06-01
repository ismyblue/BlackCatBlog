package com.ismyblue.entity;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：Category   
* 类描述：category文章分类实体类   
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月16日 下午11:23:55   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月16日 下午11:23:55   
* 修改备注：   
* @version    
*
 */
public class Category {

	/*
	 * 文章分类的id
	 */
	private int id;
	/**
	 * 文章分类的父分类的id
	 */
	private int parentId;
	/**
	 * 用户id，这个分类属主的id
	 */
	private int userId;
	/**
	 * 分类名字
	 */
	private String categoryName;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Category() {
		super();
	}
	public Category(int id, int parentId, int userId, String categoryName) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.userId = userId;
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[id:");				sb.append(getId());
		sb.append(", parentId:");	sb.append(getParentId());
		sb.append(", userId:");		sb.append(getUserId());
		sb.append(", categoryName:");		sb.append(getCategoryName());
		sb.append("]\n");
		return sb.toString();
	}	
		
	
}
