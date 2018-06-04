package com.ismyblue.service;

import com.ismyblue.dao.CategoryDAO;
import com.ismyblue.dao.impl.CategoryDAOImpl;
import com.ismyblue.entity.Category;

public class CategoryService {

	/**
	 * 添加分类到数据库是否成功
	 * 
	 * @param category
	 * @return
	 */
	public boolean addCategory(Category category) {
		CategoryDAO categoryDAO = new CategoryDAOImpl();
		if (categoryDAO.addCategory(category) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除一个分类
	 * 
	 * @param id
	 * @return
	 */
	public boolean removeCategory(int id) {
		CategoryDAO categoryDAO = new CategoryDAOImpl();
		Category category = new Category();
		category.setId(id);
		if (categoryDAO.removeCategory(category) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查找一个分类
	 * 
	 * @param id
	 * @return
	 */
	public Category findCategory(int id) {
		CategoryDAO categorydao = new CategoryDAOImpl();
		Category category = categorydao.findCategoryById(id);
		return category;
	}

	/**
	 * 更新一个分类
	 * 
	 * @param category
	 * @return
	 */
	public boolean updateCategory(Category category) {
		CategoryDAO categorydao = new CategoryDAOImpl();
		if (categorydao.updateCategory(category) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获得分类总个数
	 * 
	 * @return
	 */
//	public long getAmount() {
//		CategoryDAO categorydao = new CategoryDAOImpl();
//		return categorydao.getAmount();
//	}

	/**
	 * 通过限制每一页多少个，第几页来获取分类的数组
	 * 
	 * @param page
	 *            第几页
	 * @param count
	 *            每一页多少个分类
	 * @return categorys Array
	 */

//	public Category[] getCategorys(int page, int count) {
//		CategoryDAO categoryDAO = new CategoryDAOImpl();
//		return categoryDAO.findCategorysByPage(page, count);
//	}

}
