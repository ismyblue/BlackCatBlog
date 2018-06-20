package com.ismyblue.dao;

import java.util.Map;

import com.ismyblue.entity.Post;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：PostDao 
* 类描述：   文章数据访问操作接口
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月18日 下午1:11:05   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月18日 下午1:11:05   
* 修改备注：   
* @version    
*
 */
public interface PostDAO {	
	
	/**
	 * 增加一个文章到数据库中
	 * @param post
	 * @return
	 */
	public int addPost(Post post);
	
	/**
	 * 增加多个文章到数据库中
	 * @param posts
	 * @return
	 */
	public int addPosts(Post[] posts);
	
	/**
	 * 删除一个文章
	 * @param post
	 * @return
	 */
	public int removePost(Post post);
	
	/**
	 * 删除多个文章
	 * @param posts
	 * @return
	 */
	public int removePosts(Post[] posts);
	
	/**
	 * 更新一个文章	 
	 * @param newPost
	 * @return
	 */
	public int updatePost(Post post);
	
	/**
	 * 更新多个文章
	 * @param posts
	 * @return
	 */
	public int updatePosts(Post[] posts);
	
	/**
	 * 通过id查找文章
	 * @param id
	 * @return
	 */
	public Post findPostById(int id);
	
	/**
	 * 通过一个参数的键值对map来查询同时满足这些条件的post
	 * @param paramsMap
	 * @return
	 */
	public Post[] findPosts(Map<String, Object> paramsMap);

	/**
	 * 通过用户id查找此用户下的所有文章
	 * @param userId
	 * @return
	 */
	public Post[] findPostsByUserId(int userId);

	/**
	 * 通过分类id查找此分类下的所有文章
	 * @param categoryId
	 * @return
	 */
	public Post[] findPostsByCategoryId(int categoryId);
	
	
	
}

