package com.ismyblue.service;

import com.ismyblue.dao.PostDAO;
import com.ismyblue.dao.impl.PostDAOImpl;
import com.ismyblue.entity.Post;

public class PostService {


	/**
	 * 添加文章到数据库是否成功
	 * @param post
	 * @return
	 */
	public boolean addPost(Post post){
		PostDAO postDAO = new PostDAOImpl();
		if(postDAO.addPost(post) > 0){
			return true;
		}
		return false;		
	}

	/**
	 * 删除一个文章
	 * @param id
	 * @return
	 */
	public boolean removePost(int id){
		PostDAO postDAO = new PostDAOImpl();
		Post post = new Post();
		post.setId(id);
		if(postDAO.removePost(post) > 0){
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 查找一个文章
	 * @param id
	 * @return
	 */
	public Post findPost(int id){
		PostDAO postdao = new PostDAOImpl();
		Post post = postdao.findPostById(id);
		return post;
	}
	
	/**
	 * 更新一个文章
	 * @param post
	 * @return
	 */
	public boolean updatePost(Post post){
		PostDAO postdao = new PostDAOImpl();
		if(postdao.updatePost(post) > 0){
			return true;
		}
		return false;		
	}

	
	/**
	 * 查找某个用户下的所有文章
	 * @param userId
	 */
	public Post[] findPostsByUserId(int userId) {
		PostDAO postdao = new PostDAOImpl();
		Post[] posts = postdao.findPostsByUserId(userId);
		return posts;
	}

	/**
	 * 通过分类id查找此分类下所有文章
	 * @param categoryId
	 * @return
	 */
	public Post[] findPostsByCategoryId(int categoryId) {
		PostDAO postdao = new PostDAOImpl();
		Post[] posts = postdao.findPostsByCategoryId(categoryId);
		return posts;
	}
	
	
}
