package com.krm.ps.framework.common.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import com.krm.ps.framework.dao.jdbc.BaseDAOJdbc;
import com.krm.ps.framework.common.dao.ReportDataDAO;
import com.krm.ps.util.SysConfig;

/**
 * <p>Title: ReportDataDAOImpl</p>
 *
 * <p>Description: ���ݴ�ȡ����ӿ�ʵ����<br>
 * �����еĲ���ֻ���rep_data/rep_data1��װ��<br>
 * com.krm.dao.jdbc.BaseDAOJdbc�е���������������<br>
 * ���м����˶Էֱ����</p>
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
				//��Ҫ�ֱ�
				if(isContainCondition(sql)){
					sqlStatement = translateSql(sql, values);
					return super.list(sqlStatement, values);
				}else{
					//�������ӱ���е����ݿ��ѯ����
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
		//����Ҫ�ֱ�������rep_data/rep_data1�����ݿ��ѯ�������ݴ��Կ������������֧��
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
				//��Ҫ�ֱ�
				if(isContainCondition(sql)){
					sqlStatement = translateSql(sql, values);
					return super.update(sqlStatement, values, argTypes);
				}else{
					//�������ӱ���е����ݿ��ѯ����
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
		//����Ҫ�ֱ�������rep_data/rep_data1�����ݿ���²������ݴ��Կ������������֧��
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
				//��Ҫ�ֱ�
				if(isContainCondition(sql)){
					sqlStatement = translateSql(sql, values);
					return super.update(sqlStatement, values);
				}else{
					//�������ӱ���е����ݿ��ѯ����
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
		//����Ҫ�ֱ�������rep_data/rep_data1�����ݿ���²������ݴ��Կ������������֧��
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
				//��Ҫ�ֱ�
				if(isContainCondition(sql)){
					//�Ե�һ�ӱ���е����ݿ����
					String physicalTableName = "";
					if(sql.trim().toUpperCase().startsWith("INSERT")){
						//���������sql�����û��where��������Ҫ�Ӳ����ֵ���жϼ�¼Ӧ�ò��뵽��һ���ӱ��С�
						sqlStatement = sql.trim().toUpperCase();
						int vi = sqlStatement.indexOf(SysConfig.DISTRIBUTE_DATA_BY_COLUM.toUpperCase());
						//��ͷ��ȡ���ֱ������У����ж��ڴ�֮ǰ�ж��ٸ�������
						sqlStatement = sqlStatement.substring(0, vi);
						String[] prePart = sqlStatement.split(",");//? TODO
						
						//2007.4.10 ����SqlServer���ݿ�
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
					//���ӱ������滻ԭsql����е����������
					if(sql.indexOf(REP_DATA) >= 0){
						sqlStatement = sql.replaceAll(REP_DATA+"\\b", physicalTableName);
					}else{
						sqlStatement = sql.replaceAll(REP_DATA.toLowerCase()+"\\b", physicalTableName);
					}
				}
			}
			
		}
		//�������Ҫ�ֱ���߲������rep_data�Ĳ�������ֱ�ӷ��ش����sql��䡣
		return sqlStatement;
	}
		
	/**
	 * ��sql���жϲ�����Ե����������
	 * @param sql Ҫִ�е�sql���
	 * @return Ҫִ�е�sql�����Ե���������ơ����sql�������rep_data��rep_data1�Ĳ���������Ϊnull��
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
	 * �Ƿ��зֱ�����
	 * @param sql Ҫִ�е�sql���
	 * @return Ҫִ�е�sql����where�Ӿ����Ƿ�����ֱ�����.true ������ false ������
	 */
	private boolean isContainCondition(String sql){
		boolean isContain = false;
		String sqlStatement = sql.toUpperCase();
		if(sqlStatement.indexOf("WHERE") >= 0){
			//�ж�where�Ӿ����Ƿ��зֱ������С�
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
	 * �Ƿ������rep_data��rep_data1�Ĳ���
	 * @param tableName sql�����Ե����������
	 * @return ����ֵ:= true(�����rep_data��rep_data1�Ĳ���)/false(�������rep_data��rep_data1�Ĳ���)
	 */
	private boolean isExecuteable(String tableName){
		return "true".equals(SysConfig.IS_DISTRIBUTE_DATA) && getTableNameFromSql(tableName) != null;
	}
	
	/**
	 * ���ݺ�׺���������ӱ�����
	 * @param postFix �ӱ��׺
	 * @return
	 */
	private String generateTableName(String postFix){		
		return REP_DATA.concat("_").concat(postFix);
	}

	
	/**
	 * ���ݺ�׺���������ӱ�����
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
	 * ���ݷֱ������ı���ֵȡ���ӱ��׺
	 * @param distributeConditionValue
	 * @return
	 */
	private String getPostFix(String distributeConditionValue){
		/*
		 * ���ݷֱ�������ͬ����Ҫ�䶯
		 * ����Ľ����������Ը��ݱ������ڵ��·ݣ���rep_data�����ݷ���12���ӱ���
		 */		
		String conditionValue = distributeConditionValue.toUpperCase().replaceAll("'","");
		conditionValue = conditionValue.replaceAll("%","");
		conditionValue = conditionValue.trim();
		return distributeConditionValue.substring(4, 6);
	}
	
	private String getConditionValue(String sql, Object[] values){
		/*
		 * ���¸���ע�ͱ�ʾ����ִ�е����е�ʱ��sql������ʽ�����ӡ�
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
			//ȡ�ò�ѯ������ֵ��������ֵ�п����Ǿ����ֵ��'20060600'����Ҳ�п�����ͨ����������ģ�������
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
			 sqlStatement = "20060600'" �� sqlStatement = "?��" �� "LIKE '200606%'" �� "LIKE ?"
			 */
			/*
			 * ��ע�⣺
			 * Ϊ����SQLģ����ѯ��LIKE�﷨���ֲ��轫���漰���﷨����ɾ����
			 * ����ֻ���ݵ��ԡ��ա��������λ�ϵ�����һλ��������λ����LIKE�����ܶ��£���3��4λ�������꣨��1��2λ�ģ�LIKE��
			 */		
			sqlStatement = sqlStatement.toUpperCase().replaceAll("LIKE","");
			sqlStatement = sqlStatement.toUpperCase().replaceAll("'","");
			sqlStatement = sqlStatement.toUpperCase().replaceAll("%","");
			sqlStatement = sqlStatement.trim();
			//����ֱ��������Բ�������ʽ����ģ��Ӳ���������ȡ�ø�ֵ
			if("?".equals(sqlStatement) || "?".equals(sqlStatement.subSequence(0,1))){
				//�鿴values�����У�where������ǰ�Ĳ����ĸ�����operPart.length - 1����
				sqlStatement = sql.toUpperCase();
				String[] operPart = sqlStatement.substring(0, sqlStatement.indexOf("WHERE")).split("\\?");
				//�鿴values�����У�where�����Ժ󵽷ֱ���������ǰ�Ĳ����ĸ�����wherePart.length - 1����
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
