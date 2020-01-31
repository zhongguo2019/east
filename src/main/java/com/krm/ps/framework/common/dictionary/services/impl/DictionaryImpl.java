package com.krm.ps.framework.common.dictionary.services.impl;

import java.util.List;

import com.krm.ps.framework.common.dictionary.dao.DictionaryDAO;
import com.krm.ps.framework.common.dictionary.services.DictionaryManager;
import com.krm.ps.framework.common.dictionary.vo.Dictionary;
import com.krm.ps.framework.common.services.impl.BaseManager;

public class DictionaryImpl extends BaseManager implements DictionaryManager
{
	private DictionaryDAO dao;

	public void setDictionaryDAO(DictionaryDAO dao)
	{
		this.dao = dao;
	}

	/**
	 * �����ֵ���
	 * @param dictionary
	 */
	public void saveDictionary(Dictionary dictionary)
	{
		dao.save(dictionary);
	}

	/**
	 * �����ֵ���
	 * @param dictionary
	 */
	public void saveupdateDictionary(Dictionary dictionary)
	{

		dao.update(dictionary);
	}

	/**
	 * ��ȡ�����ֵ������
	 *
	 * @return
	 */
	public List getDictionaryRoots()
	{
		return dao.getDictionaryRoots();
	}

	/**
	 * ����Pkid��ȡ�ֵ���
	 * @param pkid
	 * @return
	 */
	public List getDictionaryById(String pkid)
	{
		return dao.getDictionaryById(pkid);
	}

	/**
	 * ����Pkid��Parentid��ȡ�ֵ���
	 * @param pkid
	 * @param parentid
	 * @return
	 */
	public List getDictionarys(String pkid, String parentid)
	{
		return dao.getDictionarys("from Dictionary u where u.dicid=" + pkid
			+ " or u.parentid=" + parentid);
	}

	/**
	 *��ȡ��pkid��ʼ���ֵ��б� 
	 * @param pkid
	 * @return
	 */
	public List getDictionargBeginWitchId(String pkid)
	{
		return dao.getDictionargBeginWitchId(pkid);
	}

	/**
	 * �õ�������XML��
	 * 
	 * @return
	 */
	public String getDictionaryTreeXML()
	{
		return dao.getDictionaryTreeXML();
	}

}
