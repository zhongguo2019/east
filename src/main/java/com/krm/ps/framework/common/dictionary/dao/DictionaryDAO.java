package com.krm.ps.framework.common.dictionary.dao;

//import java.util.List;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.framework.common.dictionary.vo.Dictionary;

//import java.sql.ResultSet;

/**
 * Dictionary Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="DictionaryDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:yinxu_2001@126.com">yinxu</a>
 */

public interface DictionaryDAO extends DAO
{

	/**
	 * �����ݿ�����ֵ����
	 * @param dictionary Dictionary �ֵ����
	 * 
	 */
	public void save(Dictionary dictionary);

	/**
	 * ��ȡ�����ֵ������
	 *
	 * @return
	 */
	public List getDictionaryRoots();

	/**
	 * ����Pkid��ȡ�ֵ���
	 * @param pkid
	 * @return
	 */
	public List getDictionaryById(String pkid);
	

	/**
	 * ��ȡ���ݿ��ֵ����
	 */
	public List getDictionarys(String s);

	/**
	 * �޸��ֵ���״̬Ϊ
	 */
	public void update(Dictionary dictionary);

	/**
	 *��ȡ��pkid��ʼ���ֵ��б� 
	 * @param pkid
	 * @return
	 */
	public List getDictionargBeginWitchId(String pkid);

	/**
	 * �õ�������XML��
	 * 
	 * @return
	 */
	public String getDictionaryTreeXML();

}
