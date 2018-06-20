package com.ismyblue.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.ismyblue.dao.LinkDAO;
import com.ismyblue.entity.Link;
import com.ismyblue.field.tbfdname.LinkFN;
import com.ismyblue.util.SqlUtil;

public class LinkDAOImpl implements LinkDAO {

	public int addLink(Link link) {
		String sql = LinkFN.INSERT_STRING;
		Object[] params = {link.getUserId(),link.getLinkName(),link.getLinkUrl(),link.getLinkDescription(),link.getLinkDelete()};

		return SqlUtil.executeNonQuery(sql, params);
	}


	/**
	 * 不建议使用
	 */
	@Override	
	public int addLinks(Link[] links) {
		int r = -1;
		for(int i = 0;i < links.length;i++){
			if(addLink(links[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public int removeLink(Link link) {
		String sql = LinkFN.DELETEPX_STRING + "where id = ?";
		Object param = link.getId();
		return SqlUtil.executeNonQuery(sql, param);
	}

	
	/**
	 * 不建议使用
	 */
	@Override
	public int removeLinks(Link[] links) {
		int r = -1;
		for(int i = 0;i < links.length;i++){
			if(removeLink(links[i]) > 0){
				++r;
			}else {
				return (r == -1)?r:++r;
			}
		}
		return ++r;
	}

	@Override
	public int updateLink(Link link) {		
		String sql = LinkFN.UPDATEPX_STRING + " where id=? ";
		
		Object[] params = {link.getUserId(),link.getLinkName(),link.getLinkUrl(),link.getLinkDescription(),link.getLinkDelete()};		
		return SqlUtil.executeNonQuery(sql, params);
	}

	
	/**
	 * 返回成功的条数
	 */
	@Override
	public int updateLinks(Link[] links) {
		int r = -1;
		for(int i = 0;i < links.length;i++){
			if(updateLink(links[i]) > 0){
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
	public Link findLinkById(int id) {
		String sql = LinkFN.SELECTPX_STRING + " where id = ?";
		Map<String, Object>[] entitys = SqlUtil.executeQueryReturnMapArray(sql, id);	
		if(entitys.length > 0)
			return MapArrayToLinks(entitys)[0];
		else {
			return null;
		}
	}

	@Override
	public Link[] findLinks(Map<String, Object> paramsMap) {		
		StringBuffer sqlsb = new StringBuffer(LinkFN.SELECTPX_STRING + " where ");
		Object[] params = null;
		int size = paramsMap.size();
		if(size > 0)
			params = new Object[size];
		
		int num = 0;		
		for(Map.Entry<String, Object> e : paramsMap.entrySet()){
			if(num > 0){
				sqlsb.append(" and ");				
			}
			
			sqlsb.append(e.getKey());
			sqlsb.append("=?");		
			params[num++] = e.getValue();
		}
		Map<String, Object>[] mapArry = SqlUtil.executeQueryReturnMapArray(sqlsb.toString(), params);
		return  MapArrayToLinks(mapArry);
		
		
	}

	private Link[] MapArrayToLinks(Map<String, Object>[]  entitys){		
		if(entitys == null)	return null;		
		int len = entitys.length;
		if(len == 0) return null;
		
		Link[] links = new Link[len];		
		Map<String, Object> entity = null;
		for(int i = 0 ;i < len;i++){			
			//处理一个实体转化成Link
			entity = entitys[i];	
			links[i] = new Link();
			for(Map.Entry<String, Object> e : entity.entrySet()){
				
				switch (e.getKey()) {
					case LinkFN.ID_STRING: links[i].setId((int) e.getValue()); break;
					case LinkFN.USERID_STRING: links[i].setUserId( (int) e.getValue());  break;
					case LinkFN.LINKNAME_STRING: links[i].setLinkName((String) e.getValue()); break;
					case LinkFN.LINKURL_STRING: links[i].setLinkUrl((String) e.getValue()); break;
					case LinkFN.LINKDESCRIPTION_STRING: links[i].setLinkDescription((String) e.getValue()); break;
					case LinkFN.LINKDELETE_STRING: links[i].setLinkDelete( (String) e.getValue()); break;					
					default:
						System.out.println("Link实体未匹配到" + e.getKey() + "字段");
						break;
				}			
				
			}
		}
		return links;		
		
	}


	
	@Override
	public Link[] findLinksByUserId(int userId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(LinkFN.USERID_STRING, userId);		
		return findLinks(paramsMap);
	}
	

}
