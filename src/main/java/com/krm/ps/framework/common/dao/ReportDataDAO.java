package com.krm.ps.framework.common.dao;

import java.util.List;

/**
 * <p>Title: ReportDataDAO</p>
 *
 * <p>Description: 数据存取处理接口<br>
 * 该类中的操作只针对rep_data/rep_data1封装了<br>
 * com.krm.dao.jdbc.BaseDAOJdbc中的三个基本方法，<br>
 * 其中加入了对分表操作</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: KRM Soft</p>
 *
 * @author Guo Yuelong
 */
public interface ReportDataDAO {
	
	/**
	 * (针对rep_data/rep_data1的)数据库查询操作
	 * @param sql 要执行的sql语句
	 * @param values 要执行的sql语句中变量的值
	 * @return 查询结果数组。数组的每一个元素是一个对象数组，对应查询结果二维结构的行的结构。
	 * @notice 不要用本方法执行嵌套sql的查询操作。
	 */
	public List list(String sql, Object[] values);
	

	//public int insert(String sql, Object[] values, int[] argTypes,String date);//insert操作，暂时这样处理，TODO

		
	/**
	 * (针对rep_data/rep_data1的)数据库变更操作（包括插入、删除、更新）
	 * @param sql 要旨行的sql
	 * @param values 要执行的sql语句中变量的值
	 * @return 变更操作成功的记录条数
	 * @notice 不要用本方法执行嵌套sql的更新操作。
	 */
	public int update(String sql, Object[] values);
	
	/**
	 * (针对rep_data/rep_data1的)数据库变更操作（包括插入、删除、更新）
	 * @param sql 要旨行的sql
	 * @param values 要执行的sql语句中变量的值
	 * @param argTypes 要执行的sql语句中变量的数据类型
	 * @return 变更操作成功的记录条数
	 * @notice 不要用本方法执行嵌套sql的更新操作。
	 */
	public int update(String sql, Object[] values,int[] argTypes);
		
	/**
	 * 将简单的sql语句翻译成可以直接执行的分表应用sql语句。
	 * 该方法作为临时解决方案，针对简单的jdbc模式的hibernate操作使用
	 * @param sql 要执行的sql（不能是hql模式的sql语句）
	 * @param values 参数数组
	 * @return 可以针对分表执行的sql语句
	 */
	public String translateSql(String sql, Object[] values);
	

	/**
	 * 查询单个结果
	 * @param sql (eg. select count(*) from rep_data where ...)
	 * @param values
	 * @param clazz
	 * @return
	 */
	public Object queryForObject(String sql,Object[] values,Class clazz);
	
}
