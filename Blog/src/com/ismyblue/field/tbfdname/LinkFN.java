package com.ismyblue.field.tbfdname;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：LinkFN   
* 类描述： t_links表字段名
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月19日 下午2:58:06   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月19日 下午2:58:06   
* 修改备注：   
* @version    
*
 */
public class LinkFN {

	public static final String TABLENAME_STRING = "t_links";
	
	public static final String ID_STRING = "id";
	public static final String USERID_STRING = "user_id";
	public static final String LINKNAME_STRING = "link_name";
	public static final String LINKURL_STRING = "link_url";
	public static final String LINKDESCRIPTION_STRING = "link_description";
	public static final String LINKDELETE_STRING = "link_delete";
	
	
	/**
	 * 插入语句
	 */
	public static final String INSERT_STRING = "insert into " +TABLENAME_STRING+"("+USERID_STRING+","+LINKNAME_STRING+","
				+LINKURL_STRING+","+LINKDESCRIPTION_STRING+","+LINKDELETE_STRING + ") values(?,?,?,?,?)";
	
	 /** 删除语句前缀
	 */
	public static final String DELETEPX_STRING = "delete from "+TABLENAME_STRING+" ";
	
	/**
	 * 选择语句前缀
	 */
	public static final String SELECTPX_STRING = "select " + ID_STRING +","+USERID_STRING+","+LINKNAME_STRING+","
			+LINKURL_STRING+","+LINKDESCRIPTION_STRING +","+LINKDELETE_STRING+" from "+TABLENAME_STRING + " "; 
	
	/**
	 * 更新语句前缀
	 */
	public static final String UPDATEPX_STRING = "update " + TABLENAME_STRING +" set "+USERID_STRING+"=?,"+LINKNAME_STRING+"=?,"
			+LINKURL_STRING+"=?,"+LINKDESCRIPTION_STRING +"=?,"+LINKDELETE_STRING+"=? ";
	
	
}
