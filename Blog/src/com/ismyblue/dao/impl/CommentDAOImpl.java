package com.ismyblue.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ismyblue.dao.CommentDAO;
import com.ismyblue.entity.Comment;
import com.ismyblue.field.tbfdname.CommentFN;
import com.ismyblue.util.SqlUtil;

public class CommentDAOImpl implements CommentDAO {

	@Override
	public int addComment(Comment comment) {
		String sql = CommentFN.INSERT_STRING;
		Object[] params = {comment.getParentId(),comment.getPostId(),comment.getCommentContent(),
							comment.getCommentAuthorEmail(),comment.getCommentAuthorUrl(),comment.getCommentAuthorIp(),comment.getCommentDate(),
							comment.getCommentVisible(),comment.getCommentDelete()};

		return SqlUtil.executeNonQuery(sql, params);
	}


	/**
	 * 不建议使用
	 */
	@Override	
	public int addComments(Comment[] comments) {
		int r = -1;
		for(int i = 0;i < comments.length;i++){
			if(addComment(comments[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public int removeComment(Comment comment) {
		String sql = CommentFN.DELETEPX_STRING + "where id = ?";
		Object param = comment.getId();
		return SqlUtil.executeNonQuery(sql, param);
	}

	
	/**
	 * 不建议使用
	 */
	@Override
	public int removeComments(Comment[] comments) {
		int r = -1;
		for(int i = 0;i < comments.length;i++){
			if(removeComment(comments[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public int updateComment(Comment comment) {		
		String sql = CommentFN.UPDATEPX_STRING + " where id=? ";
		
		Object[] params = {comment.getParentId(),comment.getPostId(),comment.getCommentContent(),
				comment.getCommentAuthorEmail(),comment.getCommentAuthorUrl(),comment.getCommentAuthorIp(),comment.getCommentDate(),
				comment.getCommentVisible(),comment.getCommentDelete(),comment.getId()};		
		return SqlUtil.executeNonQuery(sql, params);
	}

	
	/**
	 * 返回成功的条数
	 */
	@Override
	public int updateComments(Comment[] comments) {
		int r = -1;
		for(int i = 0;i < comments.length;i++){
			if(updateComment(comments[i]) > 0){
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
	public Comment findCommentById(int id) {
		String sql = CommentFN.SELECTPX_STRING + " where id = ?";
		Map<String, Object>[] entitys = SqlUtil.executeQueryReturnMapArray(sql, id);	
		if(entitys.length > 0)
			return MapArrayToComments(entitys)[0];
		else {
			return null;
		}
	}

	@Override
	public Comment[] findComments(Map<String, Object> paramsMap) {		
		StringBuffer sqlsb = new StringBuffer(CommentFN.SELECTPX_STRING + " where ");
		Object[] params = null;
		int size = paramsMap.size();
		if(size > 0)
			params = new Object[size];
		
		int num = 0;		
		for(Map.Entry<String, Object> e : paramsMap.entrySet()){			
			if(num != 0){
				sqlsb.append(" and ");				
			}			
			sqlsb.append(e.getKey());
			sqlsb.append("=?");
			params[num++] = e.getValue();
		}
		Map<String, Object>[] mapArry = SqlUtil.executeQueryReturnMapArray(sqlsb.toString(), params);
		return  MapArrayToComments(mapArry);		
	}

	private Comment[] MapArrayToComments(Map<String, Object>[]  entitys){		
		if(entitys == null)	return null;		
		int len = entitys.length;
		if(len == 0) return null;
		
		Comment[] comments = new Comment[len];		
		Map<String, Object> entity = null;
		for(int i = 0 ;i < len;i++){			
			//处理一个实体转化成Comment
			entity = entitys[i];	
			comments[i] = new Comment();
			for(Map.Entry<String, Object> e : entity.entrySet()){
				
				switch (e.getKey()) {
					case CommentFN.ID_STRING: comments[i].setId((int) e.getValue()); break;
					case CommentFN.PARENTID_STRING: comments[i].setParentId((int) e.getValue());  break;
					case CommentFN.POSTID_STRING: comments[i].setPostId((int) e.getValue()); break;					
					case CommentFN.COMMENTCONTENT_STRING: comments[i].setCommentContent((String) e.getValue()); break;
					case CommentFN.COMMENTAUTHOREMAIL_STRING: comments[i].setCommentAuthorEmail((String) e.getValue()); break;
					case CommentFN.COMMENTAUTHORURL_STRING: comments[i].setCommentAuthorUrl((String) e.getValue()); break;
					case CommentFN.COMMENTAUTHORIP_STRING: comments[i].setCommentAuthorIp((String) e.getValue()); break;
					case CommentFN.COMMENTDATE_STRING: comments[i].setCommentDate((Date) e.getValue()); break;
					case CommentFN.COMMENTVISIBLE_STRING: comments[i].setCommentVisible((String) e.getValue()); break;
					case CommentFN.COMMENTDELETE_STRING: comments[i].setCommentDelete((String) e.getValue()); break;
					default:
						System.out.println("Comment实体未匹配到" + e.getKey() + "字段");
						break;
				}			
				
			}
		}
		return comments;		
		
	}


	

	@Override
	public Comment[] findCommentsByPage(int postId, int page, int count) {
		String sql = CommentFN.SELECTPX_STRING + " where " + CommentFN.POSTID_STRING + " = ? limit ?,?";
		Object[] params = {postId, (page-1)*count, count};
		Map<String, Object>[] r = SqlUtil.executeQueryReturnMapArray(sql,params);
		return MapArrayToComments(r);
	}


	
	@Override
	public Comment[] findCommentsByParentId(int parentId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(CommentFN.PARENTID_STRING, parentId);		
		return findComments(paramsMap);
	}


	@Override
	public long getAmountByPostId(int postId) {
		String sql = CommentFN.GETCOUNT_STRING + " where " + CommentFN.POSTID_STRING + " = ?";
		Object[] params = {CommentFN.ID_STRING, postId};
		Map<String, Object>[] r = SqlUtil.executeQueryReturnMapArray(sql, params);
		for(Map.Entry<String, Object> e : r[0].entrySet()){
			return (long) e.getValue();
		}
		return 0;
	}


	@Override
	public long getAmountByCommentId(int commentId) {
		String sql = CommentFN.GETCOUNT_STRING + " where " + CommentFN.PARENTID_STRING + " = ?";
		Object[] params = {CommentFN.ID_STRING, commentId};
		Map<String, Object>[] r = SqlUtil.executeQueryReturnMapArray(sql, params);
		for(Map.Entry<String, Object> e : r[0].entrySet()){
			return (long) e.getValue();
		}
		return 0;
	}
	


}
