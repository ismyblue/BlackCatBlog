package com.ismyblue.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ismyblue.dao.PostDAO;
import com.ismyblue.entity.Post;
import com.ismyblue.field.tbfdname.PostFN;

public class PostDAOImplTest {

	@Test
	public void testAddPost(){	
		PostDAO postDAO = new PostDAOImpl();		
//		Post post = postDAO.findPostById(14);
		Post post = new Post(1, 14, 0, "fasdf", "fasfs", new Date(), "fasdf", "fsadf", "fasdfa", new Date(), 1, 3);
			
		if(postDAO.addPost(post)>0)
			System.out.println("成功");
		else {
			System.out.println("失败");
		}
					
	}
	
	public void testRemovePost(){
		Post post = new Post();
		post.setId(11);
		PostDAO postDAO = new PostDAOImpl();
		if(postDAO.removePost(post)>0)
			System.out.println("成功");
		else {
			System.out.println("失败");
		}
	}
	
	
	public void testFindPost(){
		PostDAO postDAO = new PostDAOImpl();
		Post post = postDAO.findPostById(13);
		System.out.println(post);
	}
	

	public void testUpdatePost(){
		PostDAO postDAO = new PostDAOImpl();		
		Post post = postDAO.findPostById(13);
		System.out.println(post);
		int len = post.getId();
		for(int i = 0;i < len;i++){			
			post.setVisitCount(post.getVisitCount()+1);
			System.out.println("updated, affected rows:" + postDAO.updatePost(post));
			System.out.println(post);
		}
	}
		
	
	public void testFindPosts(){
		PostDAO postdao = new PostDAOImpl();
		Map<String, Object> m = new HashMap<String, Object>();		
		m.put(PostFN.USERID_STRING, 1);
		Post[] posts = postdao.findPosts(m);
		for(Post u : posts){
			System.out.println(u);
		}
	}

	
}
