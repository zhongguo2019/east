package com.krm.ps.framework.property.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.framework.property.vo.OrganProperty;

public interface OrganPropertyDAO extends DAO{

	/**
	 * ��ӻ�������
	 * @param op
	 */
	public void addOrganProperty(OrganProperty op);

	/**
	 * ɾ����������
	 * @param op
	 */
	public void deleteOrganProperty(OrganProperty op);
	
	/**
	 * ���ݻ��������ȡ�����б�(����������������δ����������)
	 * @param organCode
	 * @return
	 */
	public List getOp(String organCode);

	/**
	 * ���ݻ������������id��ȡΨһ�Ļ�������
	 * @param organCode
	 * @param proId
	 * @return
	 */
	public OrganProperty getOrganProperty(String organCode, Long proId);

	/**
	 * ��������id��ȡ���������б�
	 * @param proId
	 * @return
	 */
	public List getList(Long proId);

	/**
	 * ���»�������
	 * @param op
	 */
	public void updateOrganProperty(OrganProperty op);  
}
