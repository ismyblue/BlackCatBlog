package com.ismyblue.dao;

import java.util.Map;

import com.ismyblue.entity.Link;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：LinkDao 
* 类描述：   链接数据访问操作接口
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月18日 下午1:11:05   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月18日 下午1:11:05   
* 修改备注：   
* @version    
*
 */
public interface LinkDAO {	
	
	/**
	 * 增加一个链接到数据库中
	 * @param link
	 * @return
	 */
	public int addLink(Link link);
	
	/**
	 * 增加多个链接到数据库中
	 * @param links
	 * @return
	 */
	public int addLinks(Link[] links);
	
	/**
	 * 删除一个链接
	 * @param link
	 * @return
	 */
	public int removeLink(Link link);
	
	/**
	 * 删除多个链接
	 * @param links
	 * @return
	 */
	public int removeLinks(Link[] links);
	
	/**
	 * 更新一个链接	 
	 * @param newLink
	 * @return
	 */
	public int updateLink(Link link);
	
	/**
	 * 更新多个链接
	 * @param links
	 * @return
	 */
	public int updateLinks(Link[] links);
	
	/**
	 * 通过id查找链接
	 * @param id
	 * @return
	 */
	public Link findLinkById(int id);
	
	/**
	 * 通过一个参数的键值对map来查询同时满足这些条件的link
	 * @param paramsMap
	 * @return
	 */
	public Link[] findLinks(Map<String, Object> paramsMap);

	/**
	 * 通过用户id来查找此用户下的所有链接
	 * @param userId
	 * @return
	 */
	public Link[] findLinksByUserId(int userId);

	/**
	 * 通过指定页码，每页链接数量来获取一定数量的链接
	 * @param userId 用户id
	 * @param page 页码数
	 * @param count 每一页的链接数量
	 * @return Link[]
	 */
	public Link[] getLinksByPage(int userId, int page, int count);

	/**
	 * 通过用户id获得用户的链接总数量，没有则返回0
	 * @param userId
	 * @return 
	 */
	public long getAmount(int userId);
		
}

