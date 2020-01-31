package com.krm.ps.framework.common.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import com.krm.ps.framework.dao.jdbc.BaseDAOJdbc;
import com.krm.ps.framework.common.dao.ReportDataDAO;
import com.krm.ps.util.SysConfig;

/**
 * <p>Title: ReportDataDAOImpl</p>
 *
 * <p>Description: 数据存取处理接口实现类<br>
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
public class ReportDataDAOImpl extends BaseDAOJdbc implements ReportDataDAO {
	public static final String REP_DATA = "REP_DATA";
	public static final String REP_DATA1 = "REP_DATA1";

	/* (non-Javadoc)
	 * @see com.krm.slsint.common.dao.ReportDataDAO#list(java.lang.String, java.lang.Object[])
	 */
	public List list(String sql, Object[] values) {
		List resultList = new ArrayList();
		String tableName = getTableNameFromSql(sql).toUpperCase();
		if(isExecuteable(tableName)){
			if(REP_DATA.equals(tableName)){
				String sqlStatement = "";
				//需要分表
				if(isContainCondition(sql)){
					sqlStatement = translateSql(sql, values);
					return super.list(sqlStatement, values);
				}else{
					//对所有子表进行的数据库查询操作
					for(int i = 1; i <= 12; i++){
						String physicalTableName = generateTableName(i);
						if(sql.indexOf(REP_DATA) >= 0){
							sqlStatement = sql.replaceAll(REP_DATA+"\\b", physicalTableName);
						}else{
							sqlStatement = sql.replaceAll(REP_DATA.toLowerCase()+"\\b", physicalTableName);
						}
						resultList.addAll(super.list(sqlStatement, values));
					}
					return resultList;
				}
			}
		}
		//不需要分表或不是针对rep_data/rep_data1的数据库查询操作（容错性考虑设置这个分支）
		return super.list(sql, values);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.common.dao.ReportDataDAO#update(java.lang.String, java.lang.Object[], int[])
	 */
	public int update(String sql, Object[] values, int[] argTypes) {		
		int resultRecorder = 0;
		String tableName = getTableNameFromSql(sql).toUpperCase();
		if(isExecuteable(tableName)){
			if(REP_DATA.equals(tableName)){
				String sqlStatement = sql;
				//需要分表
				if(isContainCondition(sql)){
					sqlStatement = translateSql(sql, values);
					return super.update(sqlStatement, values, argTypes);
				}else{
					//对所有子表进行的数据库查询操作
					for(int i = 1; i <= 12; i++){
						String physicalTableName = generateTableName(i);
						if(sql.indexOf(REP_DATA) >= 0){
							sqlStatement = sql.replaceAll(REP_DATA+"\\b", physicalTableName);
						}else{
							sqlStatement = sql.replaceAll(REP_DATA.toLowerCase()+"\\b", physicalTableName);
						}
						resultRecorder = resultRecorder + super.update(sqlStatement, values, argTypes);
					}
					return resultRecorder;
				}
			}
		}
		//不需要分表或不是针对rep_data/rep_data1的数据库更新操作（容错性考虑设置这个分支）
		return super.update(sql, values, argTypes);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.common.dao.ReportDataDAO#update(java.lang.String, java.lang.Object[])
	 */
	public int update(String sql, Object[] values) {
		//return update(sql, values, null);		
		int resultRecorder = 0;
		String tableName = getTableNameFromSql(sql).toUpperCase();
		if(isExecuteable(tableName)){
			if(REP_DATA.equals(tableName)){
				String sqlStatement = sql;
				//需要分表
				if(isContainCondition(sql)){
					sqlStatement = translateSql(sql, values);
					return super.update(sqlStatement, values);
				}else{
					//对所有子表进行的数据库查询操作
					for(int i = 1; i <= 12; i++){
						String physicalTableName = generateTableName(i);
						if(sql.indexOf(REP_DATA) >= 0){
							sqlStatement = sql.replaceAll(REP_DATA+"\\b", physicalTableName);
						}else{
							sqlStatement = sql.replaceAll(REP_DATA.toLowerCase()+"\\b", physicalTableName);
						}
						resultRecorder = resultRecorder + super.update(sqlStatement, values);
					}
					return resultRecorder;
				}
			}
		}
		//不需要分表或不是针对rep_data/rep_data1的数据库更新操作（容错性考虑设置这个分支）
		return super.update(sql, values);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.common.dao.ReportDataDAO#translateSql(java.lang.String, java.lang.Object[])
	 */
	public String translateSql(String sql, Object[] values){
		
		if(values==null){
			values=new Object[0];
		}
		String tableName = getTableNameFromSql(sql).toUpperCase();
		String sqlStatement = sql;
		if(isExecuteable(tableName)){
			if(REP_DATA.equals(tableName)){
				//需要分表
				if(isContainCondition(sql)){
					//对单一子表进行的数据库操作
					String physicalTableName = "";
					if(sql.trim().toUpperCase().startsWith("INSERT")){
						//插入操作的sql语句中没有where条件，需要从插入的值中判断记录应该插入到哪一个子表中。
						sqlStatement = sql.trim().toUpperCase();
						int vi = sqlStatement.indexOf(SysConfig.DISTRIBUTE_DATA_BY_COLUM.toUpperCase());
						//从头截取到分表条件列，以判断在此之前有多少个参数。
						sqlStatement = sqlStatement.substring(0, vi);
						String[] prePart = sqlStatement.split(",");//? TODO
						
						//2007.4.10 兼容SqlServer数据库
						String conditionValue = "";
						if ('s'==SysConfig.DB){
							conditionValue = (String)values[prePart.length];
						}else{
							conditionValue = (String)values[prePart.length - 1];
						}
										
						String postFix = getPostFix(conditionValue);
						physicalTableName = generateTableName(postFix);
					}else{
						String conditionValue = getConditionValue(sql, values);
						String postFix = getPostFix(conditionValue);
						physicalTableName = generateTableName(postFix);
					}
					//用子表名称替换原sql语句中的物理表名称
					if(sql.indexOf(REP_DATA) >= 0){
						sqlStatement = sql.replaceAll(REP_DATA+"\\b", physicalTableName);
					}else{
						sqlStatement = sql.replaceAll(REP_DATA.toLowerCase()+"\\b", physicalTableName);
					}
				}
			}
			
		}
		//如果不需要分表或者不是针对rep_data的操作，则直接返回传入的sql语句。
		return sqlStatement;
	}
		
	/**
	 * 从sql中判断操作针对的物理表名称
	 * @param sql 要执行的sql语句
	 * @return 要执行的sql语句针对的物理表名称。如果sql不是针对rep_data或rep_data1的操作，返回为null；
	 */
	private String getTableNameFromSql(String sql){
		String sqlStatement = sql.toUpperCase();
		if(sqlStatement.indexOf(REP_DATA) >= 0){
			if(sqlStatement.indexOf(REP_DATA1) >= 0){
				return REP_DATA1;
			}else{
				return REP_DATA;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 是否含有分表条件
	 * @param sql 要执行的sql语句
	 * @return 要执行的sql语句的where子句中是否包含分表条件.true 包含； false 不包含
	 */
	private boolean isContainCondition(String sql){
		boolean isContain = false;
		String sqlStatement = sql.toUpperCase();
		if(sqlStatement.indexOf("WHERE") >= 0){
			//判断where子句中是否含有分表条件列。
			sqlStatement = sqlStatement.substring(sqlStatement.indexOf("WHERE"));
			if(sqlStatement.indexOf(SysConfig.DISTRIBUTE_DATA_BY_COLUM.toUpperCase()) >= 0){
				isContain = true;
			}
		}else if(sql.trim().toUpperCase().startsWith("INSERT")){
			sqlStatement = sql.trim().toUpperCase();
			int vi = sqlStatement.indexOf(SysConfig.DISTRIBUTE_DATA_BY_COLUM.toUpperCase());
			isContain = (vi >= 0);
		}
		return isContain;
	}
	
	/**
	 * 是否是针对rep_data或rep_data1的操作
	 * @param tableName sql语句针对的物理表名称
	 * @return 布尔值:= true(是针对rep_data或rep_data1的操作)/false(不是针对rep_data或rep_data1的操作)
	 */
	private boolean isExecuteable(String tableName){
		return "true".equals(SysConfig.IS_DISTRIBUTE_DATA) && getTableNameFromSql(tableName) != null;
	}
	
	/**
	 * 根据后缀生成物理子表名称
	 * @param postFix 子表后缀
	 * @return
	 */
	private String generateTableName(String postFix){		
		return REP_DATA.concat("_").concat(postFix);
	}

	
	/**
	 * 根据后缀生成物理子表名称
	 * @param postFix
	 * @return
	 */
	private String generateTableName(int postFix){
		if(postFix < 10){
			return generateTableName("0".concat(String.valueOf(postFix)));
		}else{
			return generateTableName(String.valueOf(postFix));
		}
	}
	
	/**
	 * 根据分表条件的变量值取得子表后缀
	 * @param distributeConditionValue
	 * @return
	 */
	private String getPostFix(String distributeConditionValue){
		/*
		 * 根据分表条件不同，需要变动
		 * 这里的解决方案是针对根据报表日期的月份，将rep_data的数据分在12个子表中
		 */		
		String conditionValue = distributeConditionValue.toUpperCase().replaceAll("'","");
		conditionValue = conditionValue.replaceAll("%","");
		conditionValue = conditionValue.trim();
		return distributeConditionValue.substring(4, 6);
	}
	
	private String getConditionValue(String sql, Object[] values){
		/*
		 * 以下各行注释表示程序执行到该行的时候，sql语句的形式的例子。
		 sqlStatement = " select * from rep_data where report_id = '165' and report_date = '20060600' "
		 */
		String sqlStatement = sql.trim().toUpperCase();
		/*
		 sqlStatement = "SELECT * FROM REP_DATA WHERE REPORT_ID = '165' AND REPORT_DATE = '20060600'"
		 */
		sqlStatement = sqlStatement.substring(sqlStatement.indexOf("WHERE"));
		/*
		 sqlStatement = "WHERE REPORT_ID = '165' AND REPORT_DATE = '20060600'"
		 */
		if(sqlStatement.indexOf(SysConfig.DISTRIBUTE_DATA_BY_COLUM.toUpperCase()) >= 0){
			sqlStatement = sqlStatement.substring(sqlStatement.indexOf(SysConfig.DISTRIBUTE_DATA_BY_COLUM.toUpperCase()));
			/*
			 sqlStatement = "REPORT_DATE = '20060600'"
			 */			
			sqlStatement = sqlStatement.substring(SysConfig.DISTRIBUTE_DATA_BY_COLUM.length()).trim();
			/*
			 sqlStatement = "= '20060600'"
			 sqlStatement = "LIKE '20060600'"
			 */
			//取得查询条件的值。条件的值有可能是具体的值（'20060600'），也有可能是通过参数传入的（？）。
			while(sqlStatement.length() >= 1 && (sqlStatement.subSequence(0,1).equals("<")||
					sqlStatement.subSequence(0,1).equals(">")||
					sqlStatement.subSequence(0,1).equals("=")||
					sqlStatement.subSequence(0,1).equals("'")||
					sqlStatement.subSequence(0,1).equals(" "))
					){
				if(sqlStatement.length() > 1){
					sqlStatement = sqlStatement.substring(1);				
				}
			}
			/*
			 sqlStatement = "20060600'" 或 sqlStatement = "?…" 或 "LIKE '200606%'" 或 "LIKE ?"
			 */
			/*
			 * ★注意：
			 * 为兼容SQL模糊查询的LIKE语法，分步骤将其涉及的语法符号删除。
			 * 但是只兼容到对“日”（最后两位上的任意一位或者这两位）的LIKE，不能对月（第3、4位）或者年（第1、2位的）LIKE。
			 */		
			sqlStatement = sqlStatement.toUpperCase().replaceAll("LIKE","");
			sqlStatement = sqlStatement.toUpperCase().replaceAll("'","");
			sqlStatement = sqlStatement.toUpperCase().replaceAll("%","");
			sqlStatement = sqlStatement.trim();
			//如果分表条件是以参数的形式传入的，从参数数组中取得该值
			if("?".equals(sqlStatement) || "?".equals(sqlStatement.subSequence(0,1))){
				//查看values数组中，where条件以前的参数的个数（operPart.length - 1）。
				sqlStatement = sql.toUpperCase();
				String[] operPart = sqlStatement.substring(0, sqlStatement.indexOf("WHERE")).split("\\?");
				//查看values数组中，where条件以后到分表条件列以前的参数的个数（wherePart.length - 1）。
				sqlStatement = sqlStatement.substring(sqlStatement.indexOf("WHERE"));
				sqlStatement = sqlStatement.substring(0,sqlStatement.indexOf(SysConfig.DISTRIBUTE_DATA_BY_COLUM.toUpperCase()));
				String[] wherePart = sqlStatement.split("\\?");
				sqlStatement = (String)values[(operPart.length - 1) + (wherePart.length - 1)];
			}
		}
		return sqlStatement;
	}
	
	public Object queryForObject(String sql,Object[] values,Class clazz){
		
		sql=translateSql(sql,values);

		return getJdbcTemplate().queryForObject(sql,values,clazz);		
	}
	
}
