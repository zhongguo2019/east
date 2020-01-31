package com.krm.ps.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.krm.ps.framework.dao.hibernate.support.lob.LobHandler;

public interface DAO
{
	public void setLobHandler(LobHandler lobHandler);

	public List getObjects(Class clazz);

	public Object getObject(Class clazz, Serializable id);

	public void saveObject(Object o);

	public void removeObject(Object o);

	public void removeObject(Class clazz, Serializable id);
	
	public boolean sthRepeat(String className,String pkAttributeName,Long pkid,String sthAttributeName,String sth);
	
	public void batchSaveVO(List voList);
	
	/**
	 * <p>֧�ֶ���Ķ�̬��ѯ</p>
	 * 
	 * @param tableName ������
	 * @param resultColumns Ҫ��ѯ�õ����� �˲���Ϊnullʱ�õ�����������б�
	 * @param conditionMap ����Map������صĲ�ѯ������Ŀǰ��Щ��ѯ����ֻ֧���룩
	 * 					   �˲�����ĳ��key��Ӧ��ֵ���Դ���LIST��list�з���ID���������listתΪ������in�ķ�ʽȥ��ѯ
	 * @param entityClass ʵ���ָ࣬������ĸ������
	 * @return
	 * @throws Exception
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-3-31 ����11:05:55
	 */
	public List getObjects(String tableName, List resultColumns, Map conditionMap, Class entityClass) throws Exception;
	
	/**
	 * <p>ʹ��HQL���ɾ�����������</p> 
	 *
	 * @param sqlString Ҫִ�е�SQL���
	 * @return
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-4-25 ����12:28:53
	 */
	public boolean deleteOrUpdateObjectsWithHQLSQL(String sqlString);

	/**
	 * <p>��̬��ѯ�����ָ����Ϣ</p> 
	 *
	 * @param fields ָ��Ҫ��ѯ�Ķ����ֶ�
	 * 				null�����ʾ��ѯ��������
	 * @param objectClass ָ��Ҫ��ѯ�ı����
	 * @param condition ��ѯ����ʱ������
	 * @return
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-4-25 ����10:33:14
	 */
	public List getObjectSpecifiedInfo(String[] fields, Class objectClass, String condition);
	
//	public List listObjectsWithoutSessionManage(Object[][] entitis);
}
