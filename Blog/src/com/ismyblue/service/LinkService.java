package com.ismyblue.service;

import com.ismyblue.dao.LinkDAO;
import com.ismyblue.dao.impl.LinkDAOImpl;
import com.ismyblue.entity.Link;

public class LinkService {
	

	/**
	 * 添加链接到数据库是否成功
	 * @param link
	 * @return
	 */
	public boolean addLink(Link link){
		LinkDAO linkDAO = new LinkDAOImpl();
		if(linkDAO.addLink(link) > 0){
			return true;
		}
		return false;		
	}

	/**
	 * 删除一个链接
	 * @param id
	 * @return
	 */
	public boolean removeLink(int id){
		LinkDAO linkDAO = new LinkDAOImpl();
		Link link = new Link();
		link.setId(id);
		if(linkDAO.removeLink(link) > 0){
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 查找一个链接
	 * @param id
	 * @return
	 */
	public Link findLink(int id){
		LinkDAO linkdao = new LinkDAOImpl();
		Link link = linkdao.findLinkById(id);
		return link;
	}
	
	/**
	 * 更新一个链接
	 * @param link
	 * @return
	 */
	public boolean updateLink(Link link){
		LinkDAO linkdao = new LinkDAOImpl();
		if(linkdao.updateLink(link) > 0){
			return true;
		}
		return false;		
	}

	
	/**
	 * 查找某个用户下的所有链接
	 * @param userId
	 */
	public Link[] findLinksByUserId(int userId) {
		LinkDAO linkdao = new LinkDAOImpl();
		Link[] links = linkdao.findLinksByUserId(userId);
		return links;
	}
	
	/**
	 * 分页查找某个用户的所有链接link
	 * @param userId 用户id
	 * @param page 页码数
	 * @param count 每页的link数量
	 * @return Link[]
	 */
	public Link[] getLinks(int userId, int page, int count) {
		LinkDAO linkDAO = new LinkDAOImpl();		
		return linkDAO.getLinksByPage(userId, page, count);
	}

	
	/*
	 * 通过用户id获得链接个数,没有则返回0
	 */
	public long getAmount(int userId) {
		LinkDAO linkDAO = new LinkDAOImpl();		
		return linkDAO.getAmount(userId);
	}


	
}
