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
	 * 保存字典项
	 * @param dictionary
	 */
	public void saveDictionary(Dictionary dictionary)
	{
		dao.save(dictionary);
	}

	/**
	 * 更新字典项
	 * @param dictionary
	 */
	public void saveupdateDictionary(Dictionary dictionary)
	{

		dao.update(dictionary);
	}

	/**
	 * 获取所有字典项（根）
	 *
	 * @return
	 */
	public List getDictionaryRoots()
	{
		return dao.getDictionaryRoots();
	}

	/**
	 * 根据Pkid获取字典项
	 * @param pkid
	 * @return
	 */
	public List getDictionaryById(String pkid)
	{
		return dao.getDictionaryById(pkid);
	}

	/**
	 * 更具Pkid和Parentid获取字典项
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
	 *获取已pkid开始的字典列表 
	 * @param pkid
	 * @return
	 */
	public List getDictionargBeginWitchId(String pkid)
	{
		return dao.getDictionargBeginWitchId(pkid);
	}

	/**
	 * 得到所有组XML树
	 * 
	 * @return
	 */
	public String getDictionaryTreeXML()
	{
		return dao.getDictionaryTreeXML();
	}

}
