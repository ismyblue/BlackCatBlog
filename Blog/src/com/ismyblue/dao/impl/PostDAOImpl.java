package com.ismyblue.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ismyblue.dao.PostDAO;
import com.ismyblue.entity.Post;
import com.ismyblue.field.tbfdname.PostFN;
import com.ismyblue.util.SqlUtil;

public class PostDAOImpl implements PostDAO {

	@Override
	public int addPost(Post post) {
		String sql = PostFN.INSERT_STRING;
		Object[] params = {post.getUserId(),post.getCategoryId(),post.getPostTitle(),post.getPostContent(),
				post.getPostDate(),post.getPostExcerpt(),post.getPostStatus(),post.getCommentStatus(),
				post.getPostModified(),post.getCommentCount(),post.getVisitCount()};
		return SqlUtil.executeNonQuery(sql, params);
	}

	/**
	 * 不建议使用，返回成功影响的条数
	 */
	@Override
	public int addPosts(Post[] posts) {
		int r = -1;
		for(int i = 0;i < posts.length;i++){
			if(addPost(posts[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public int removePost(Post post) {
		String sql = PostFN.DELETEPX_STRING + "where id = ?";
		Object param = post.getId();
		return SqlUtil.executeNonQuery(sql, param);
	}

	/**
	 * 不建议使用，返回被影响的条数
	 */
	@Override
	public int removePosts(Post[] posts) {
		int r = -1;
		for(int i = 0;i < posts.length;i++){
			if(removePost(posts[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public int updatePost(Post post) {
		String sql = PostFN.UPDATEPX_STRING + " where id = ?";
		
		Object[] params = {post.getUserId(),post.getCategoryId(),post.getPostTitle(),post.getPostContent(),
				post.getPostDate(),post.getPostExcerpt(),post.getPostStatus(),post.getCommentStatus(),
				post.getPostModified(),post.getCommentCount(),post.getVisitCount(),post.getId()};		
		return SqlUtil.executeNonQuery(sql, params);
	}

	/**
	 * 不建议使用，返回被影响的条数
	 */
	@Override
	public int updatePosts(Post[] posts) {
		int r = -1;
		for(int i = 0;i < posts.length;i++){
			if(updatePost(posts[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public Post findPostById(int id) {
		String sql = PostFN.SELECTPX_STRING + " where id = ?";
		Map<String, Object>[] entitys = SqlUtil.executeQueryReturnMapArray(sql, id);	
		if(entitys.length > 0)
			return MapArrayToPosts(entitys)[0];
		else {
			return null;
		}
	}



	@Override
	public Post[] findPosts(Map<String, Object> paramsMap) {
		StringBuffer sqlsb = new StringBuffer(PostFN.SELECTPX_STRING + " where ");
		Object[] params = null;		
		int size = paramsMap.size();
		if(size > 0)
			params = new Object[size];
		
		int num = 0;		
		for(Map.Entry<String, Object> e : paramsMap.entrySet()){
			if(num > 0){
				sqlsb.append("and");				
			}
			
			sqlsb.append(e.getKey());
			sqlsb.append(" = ?");	
			params[num++] = e.getValue();
		}
		
		Map<String, Object>[] mapArry = SqlUtil.executeQueryReturnMapArray(sqlsb.toString(), params);
		return  MapArrayToPosts(mapArry);
	}
	
	private Post[] MapArrayToPosts(Map<String, Object>[] entitys) {
		if(entitys == null)	return null;		
		int len = entitys.length;
		if(len == 0) return null;
		
		Post[] posts = new Post[len];		
		Map<String, Object> entity = null;
		for(int i = 0 ;i < len;i++){			
			//处理一个实体转化成Post
			entity = entitys[i];	
			posts[i] = new Post();
			for(Map.Entry<String, Object> e : entity.entrySet()){
				
				switch (e.getKey()) {
					case PostFN.ID_STRING: posts[i].setId((int) e.getValue()); break;
					case PostFN.USERID_STRING: posts[i].setUserId((int) e.getValue());  break;
					case PostFN.CATEGORYID_STRING: posts[i].setCategoryId((int) e.getValue()); break;
					case PostFN.POSTTITLE_STRING: posts[i].setPostTitle((String) e.getValue()); break;
					case PostFN.POSTCONTENT_STRING: posts[i].setPostContent((String) e.getValue()); break;
					case PostFN.POSTDATE_STRING: posts[i].setPostDate( (Date) e.getValue()); break;
					case PostFN.POSTEXCERPT_STRING: posts[i].setPostExcerpt((String) e.getValue()); break;
					case PostFN.POSTSTATUS_STRING: posts[i].setPostStatus((String) e.getValue()); break;
					case PostFN.COMMENTSTATUS_STRING: posts[i].setCommentStatus((String) e.getValue()); break;
					case PostFN.POSTMODIFIED_STRING: posts[i].setPostModified( (Date) e.getValue()); break;
					case PostFN.COMMENTCOUNT_STRING: posts[i].setCommentCount( (int) e.getValue()); break;
					case PostFN.VISITCOUNT_STRING: posts[i].setVisitCount((int) e.getValue()); break;
					default:
						System.out.println("Post实体未匹配到" + e.getKey() + "字段");
						break;
				}			
				
			}
		}
		return posts;		
		
	}

	
	@Override
	public Post[] findPostsByUserId(int userId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(PostFN.USERID_STRING, userId);
		return findPosts(paramsMap);
	}

	@Override
	public Post[] findPostsByCategoryId(int categoryId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(PostFN.CATEGORYID_STRING, categoryId);
		return findPosts(paramsMap);
		
	}

}
