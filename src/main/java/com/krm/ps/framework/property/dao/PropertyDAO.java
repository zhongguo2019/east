package com.krm.ps.framework.property.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.framework.property.vo.Prop;

public interface PropertyDAO extends DAO{

	/**
	 * ��ȡ��������
	 * @return
	 */
	public List getAllProperty();
	
	/**
	 * ��ȡ����
	 * @param id
	 * @return
	 */
	public Prop getProperty(Long id);
	
	/**
	 * ��ȡ�������Ƶ�list���ϣ������ж����������Ƿ�Ψһ
	 * @return
	 */
	public List getProNameList();
	
	/**
	 * ��������
	 * @param property
	 */
	public void addProperty(Prop property);
	
	/**
	 * ��������
	 * @param property
	 */
	public void updateProperty(Prop property);
	
	/**
	 * ɾ������
	 * @param id
	 */
	public void deleteProperty(Long id);
	
	/**
	 * ����ɾ��
	 * ͨ��ids�ַ���ɾ����ids=2,3 ��ɾ��idΪ2��3����������
	 * @param ids
	 */
	public void batchdelete(String ids);
	
	/**
	 * �����������ƻ�ȡ����ֵ
	 * @param proName
	 * @return
	 */
	public String getProValue(String proName);
	
	/**
	 * ��ѯ���Ե�ֵ����δ��������ԣ�����Ĭ��ֵ
	 * @param proName
	 * @return
	 */
	public String getProValue(String proName,String defaultValue);

	/**
	 * �����������ƻ�ȡ���Զ���
	 * @param proName
	 * @return
	 */
	public Prop getProperty(String proName); 
}
