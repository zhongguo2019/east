package com.krm.ps.framework.common.dao;

import java.util.List;

/**
 * <p>Title: ReportDataDAO</p>
 *
 * <p>Description: ���ݴ�ȡ����ӿ�<br>
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
public interface ReportDataDAO {
	
	/**
	 * (���rep_data/rep_data1��)���ݿ��ѯ����
	 * @param sql Ҫִ�е�sql���
	 * @param values Ҫִ�е�sql����б�����ֵ
	 * @return ��ѯ������顣�����ÿһ��Ԫ����һ���������飬��Ӧ��ѯ�����ά�ṹ���еĽṹ��
	 * @notice ��Ҫ�ñ�����ִ��Ƕ��sql�Ĳ�ѯ������
	 */
	public List list(String sql, Object[] values);
	

	//public int insert(String sql, Object[] values, int[] argTypes,String date);//insert��������ʱ��������TODO

		
	/**
	 * (���rep_data/rep_data1��)���ݿ����������������롢ɾ�������£�
	 * @param sql Ҫּ�е�sql
	 * @param values Ҫִ�е�sql����б�����ֵ
	 * @return ��������ɹ��ļ�¼����
	 * @notice ��Ҫ�ñ�����ִ��Ƕ��sql�ĸ��²�����
	 */
	public int update(String sql, Object[] values);
	
	/**
	 * (���rep_data/rep_data1��)���ݿ����������������롢ɾ�������£�
	 * @param sql Ҫּ�е�sql
	 * @param values Ҫִ�е�sql����б�����ֵ
	 * @param argTypes Ҫִ�е�sql����б�������������
	 * @return ��������ɹ��ļ�¼����
	 * @notice ��Ҫ�ñ�����ִ��Ƕ��sql�ĸ��²�����
	 */
	public int update(String sql, Object[] values,int[] argTypes);
		
	/**
	 * ���򵥵�sql��䷭��ɿ���ֱ��ִ�еķֱ�Ӧ��sql��䡣
	 * �÷�����Ϊ��ʱ�����������Լ򵥵�jdbcģʽ��hibernate����ʹ��
	 * @param sql Ҫִ�е�sql��������hqlģʽ��sql��䣩
	 * @param values ��������
	 * @return ������Էֱ�ִ�е�sql���
	 */
	public String translateSql(String sql, Object[] values);
	

	/**
	 * ��ѯ�������
	 * @param sql (eg. select count(*) from rep_data where ...)
	 * @param values
	 * @param clazz
	 * @return
	 */
	public Object queryForObject(String sql,Object[] values,Class clazz);
	
}
