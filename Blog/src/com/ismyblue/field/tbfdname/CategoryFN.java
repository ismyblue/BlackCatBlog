package com.ismyblue.field.tbfdname;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：CategoryFN   
* 类描述：   t_category表字段名
* 创建人：ismyblue@163.com   
* 创建时间：2018年5月19日 下午2:57:42   
* 修改人：ismyblue@163.com   
* 修改时间：2018年5月19日 下午2:57:42   
* 修改备注：   
* @version    
*
 */
public class CategoryFN {

	public static final String TABLENAME_STRING = "t_categories";
	
	public static final String ID_STRING = "id";
	public static final String PARENTID_STRING = "parent_id";
	public static final String USERID_STRING = "user_id";
	public static final String CATEGORYNAME_STRING = "category_name";
	

	/**
	 * 插入语句
	 */
	public static final String INSERT_STRING = "insert into " +TABLENAME_STRING+"("+PARENTID_STRING+","+USERID_STRING+","
				+CATEGORYNAME_STRING+") values(?,?,?)";
	
	 /** 删除语句前缀
	 */
	public static final String DELETEPX_STRING = "delete from "+TABLENAME_STRING+" ";
	
	/**
	 * 选择语句前缀
	 */
	public static final String SELECTPX_STRING = "select " + ID_STRING +","+PARENTID_STRING+","+USERID_STRING+","
			+CATEGORYNAME_STRING + " from "+TABLENAME_STRING + " "; 
	
	/**
	 * 更新语句前缀
	 */
	public static final String UPDATEPX_STRING = "update " + TABLENAME_STRING +" set "+PARENTID_STRING+"=?,"+USERID_STRING+"=?,"
			+CATEGORYNAME_STRING+"=? ";
	
	
}
