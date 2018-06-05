package com.ismyblue.entity;

import java.util.List;

/**
 * 返回的分类信息类
*    
* 项目名称：Blog   
* 类名称：CategoryInfo   
* 类描述：   
* 创建人：ismyblue@163.com   
* 创建时间：2018年6月5日 上午10:24:52   
* 修改人：ismyblue@163.com   
* 修改时间：2018年6月5日 上午10:24:52   
* 修改备注：   
* @version    
*
 */
public class CategoryInfo {
	
	private int id;
	private int parentId;
	private String categoryName;
	private List<CategoryInfo> subCategories;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<CategoryInfo> getSubCategories() {
		return subCategories;
	}
	public void setSubCategories(List<CategoryInfo> subCategories) {
		this.subCategories = subCategories;
	}
	public CategoryInfo() {
		super();
	}
	public CategoryInfo(int id, int parentId, String categoryName, List<CategoryInfo> subCategories) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.categoryName = categoryName;
		this.subCategories = subCategories;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[id:");				sb.append(getId());
		sb.append(", parentId:");		sb.append(getParentId());		
		sb.append(", categoryName:");	sb.append(getCategoryName());
		sb.append(", subCategories:");	sb.append(getSubCategories());
		sb.append("]\n");
		return sb.toString();
	}	
		
	
}
