package com.ismyblue.dao.impl;

import java.util.Map;

import com.ismyblue.dao.CategoryDAO;
import com.ismyblue.entity.Category;
import com.ismyblue.field.tbfdname.CategoryFN;
import com.ismyblue.util.SqlUtil;

public class CategoryDAOImpl implements CategoryDAO {
	
	@Override
	public int addCategory(Category category) {
		String sql = CategoryFN.INSERT_STRING;
		Object[] params = {category.getParentId(),category.getUserId(),category.getCategoryName()};

		return SqlUtil.executeNonQuery(sql, params);
	}


	/**
	 * 不建议使用
	 */
	@Override	
	public int addCategorys(Category[] categorys) {
		int r = -1;
		for(int i = 0;i < categorys.length;i++){
			if(addCategory(categorys[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public int removeCategory(Category category) {
		String sql = CategoryFN.DELETEPX_STRING + "where id = ?";
		Object param = category.getId();
		return SqlUtil.executeNonQuery(sql, param);
	}

	
	/**
	 * 不建议使用
	 */
	@Override
	public int removeCategorys(Category[] categorys) {
		int r = -1;
		for(int i = 0;i < categorys.length;i++){
			if(removeCategory(categorys[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public int updateCategory(Category category) {		
		String sql = CategoryFN.UPDATEPX_STRING + " where id=? ";
		
		Object[] params = {category.getParentId(),category.getUserId(),category.getCategoryName(),category.getId()};		
		return SqlUtil.executeNonQuery(sql, params);
	}

	
	/**
	 * 返回成功的条数
	 */
	@Override
	public int updateCategorys(Category[] categorys) {
		int r = -1;
		for(int i = 0;i < categorys.length;i++){
			if(updateCategory(categorys[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	/**
	 * 没有查到就返回null
	 */
	@Override
	public Category findCategoryById(int id) {
		String sql = CategoryFN.SELECTPX_STRING + " where id = ?";
		Map<String, Object>[] entitys = SqlUtil.executeQueryReturnMapArray(sql, id);	
		if(entitys.length > 0)
			return MapArrayToCategorys(entitys)[0];
		else {
			return null;
		}
	}

	@Override
	public Category[] findCategorys(Map<String, Object> paramsMap) {		
		StringBuffer sqlsb = new StringBuffer(CategoryFN.SELECTPX_STRING + " where ");
		Object[] params = null;
		int size = paramsMap.size();
		if(size > 0)
			params = new Object[size];
		
		int i = 0;		
		for(Map.Entry<String, Object> e : paramsMap.entrySet()){
			params[i] = e.getValue();
			if(i != 0){
				sqlsb.append(" and ");				
			}
			i++;
			sqlsb.append(e.getKey());
			sqlsb.append("=?");			
		}
		Map<String, Object>[] mapArry = SqlUtil.executeQueryReturnMapArray(sqlsb.toString(), params);
		return  MapArrayToCategorys(mapArry);
		
		
	}

	private Category[] MapArrayToCategorys(Map<String, Object>[]  entitys){		
		if(entitys == null)	return null;		
		int len = entitys.length;
		if(len == 0) return null;
		
		Category[] categorys = new Category[len];		
		Map<String, Object> entity = null;
		for(int i = 0 ;i < len;i++){			
			//处理一个实体转化成Category
			entity = entitys[i];	
			categorys[i] = new Category();
			for(Map.Entry<String, Object> e : entity.entrySet()){
				
				switch (e.getKey()) {
					case CategoryFN.ID_STRING: categorys[i].setId((int) e.getValue()); break;
					case CategoryFN.PARENTID_STRING: categorys[i].setParentId((int) e.getValue());  break;
					case CategoryFN.USERID_STRING: categorys[i].setUserId((int) e.getValue()); break;
					case CategoryFN.CATEGORYNAME_STRING: categorys[i].setCategoryName((String) e.getValue()); break;					
					default:
						System.out.println("Category实体未匹配到" + e.getKey() + "字段");
						break;
				}			
				
			}
		}
		return categorys;		
		
	}


	
	@Override
	public Category[] findCategoriesByParentId(int parentId) {
		String sql = CategoryFN.SELECTPX_STRING + " where "+ CategoryFN.PARENTID_STRING +" = ?";
		Map<String, Object>[] entitys = SqlUtil.executeQueryReturnMapArray(sql, parentId);	
		if(entitys.length > 0)
			return MapArrayToCategorys(entitys);
		else {
			return null;
		}		
	}



	@Override
	public Category[] findCategoriesByUserId(int userId) {
		String sql = CategoryFN.SELECTPX_STRING + " where "+ CategoryFN.USERID_STRING +" = ?";
		Map<String, Object>[] entitys = SqlUtil.executeQueryReturnMapArray(sql, userId);	
		if(entitys.length > 0)
			return MapArrayToCategorys(entitys);
		else {
			return null;
		}	
	}
	
}
