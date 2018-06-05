package com.ismyblue.dao;

import java.util.Map;

import com.ismyblue.entity.Category;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：CategoryDao   
* 类描述：    文章分类数据访问操作接口
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月18日 下午11:20:49   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月18日 下午11:20:49   
* 修改备注：   
* @version    
*
 */
public interface CategoryDAO {

	 /* 增加一个文章分类到数据库中
	 * @param category
	 * @return
	 */
	public int addCategory(Category category);
	
	/**
	 * 增加多个文章分类到数据库中
	 * @param categorys
	 * @return
	 */
	public int addCategorys(Category[] categorys);
	
	/**
	 * 删除一个文章分类
	 * @param category
	 * @return
	 */
	public int removeCategory(Category category);
	
	/**
	 * 删除多个文章分类
	 * @param categorys
	 * @return
	 */
	public int removeCategorys(Category[] categorys);
	
	/**
	 * 更新一个文章分类	 
	 * @param newCategory
	 * @return
	 */
	public int updateCategory(Category category);
	
	/**
	 * 更新多个文章分类
	 * @param categorys
	 * @return
	 */
	public int updateCategorys(Category[] categorys);
	
	/**
	 * 通过id查找文章分类
	 * @param id
	 * @return
	 */
	public Category findCategoryById(int id);
	
	/**
	 * 通过一个参数的键值对map来查询同时满足这些条件的category
	 * @param paramsMap
	 * @return
	 */
	public Category[] findCategorys(Map<String, Object> paramsMap);

	/**
	 * 通过父亲id获得此分类下所有的子分类,没有则返回null
	 * @param parentId
	 * @return
	 */
	public Category[] findCategoriesByParentId(int parentId);

	/**
	 * 通过用户id获得此用户下所有的分类,没有则返回null
	 * @param userId
	 * @return
	 */
	public Category[] findCategoriesByUserId(int userId);
	
}
