package com.krm.ps.framework.common.dictionary.services;

import java.util.List;

import com.krm.ps.framework.common.dictionary.vo.Dictionary;

public interface DictionaryManager
{
	/**
	 * �����ֵ���
	 * @param dictionary
	 */
	public void saveDictionary(Dictionary dictionary);

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
	 * ����Pkid��Parentid��ȡ�ֵ���
	 * @param pkid
	 * @param parentid
	 * @return
	 */
	public List getDictionarys(String pkid, String parentid);

	/**
	 * �����ֵ���
	 * @param dictionary
	 */
	public void saveupdateDictionary(Dictionary dictionary);

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
