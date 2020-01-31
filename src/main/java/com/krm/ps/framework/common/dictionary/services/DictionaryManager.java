package com.krm.ps.framework.common.dictionary.services;

import java.util.List;

import com.krm.ps.framework.common.dictionary.vo.Dictionary;

public interface DictionaryManager
{
	/**
	 * 保存字典项
	 * @param dictionary
	 */
	public void saveDictionary(Dictionary dictionary);

	/**
	 * 获取所有字典项（根）
	 *
	 * @return
	 */
	public List getDictionaryRoots();

	/**
	 * 根据Pkid获取字典项
	 * @param pkid
	 * @return
	 */
	public List getDictionaryById(String pkid);

	/**
	 * 更具Pkid和Parentid获取字典项
	 * @param pkid
	 * @param parentid
	 * @return
	 */
	public List getDictionarys(String pkid, String parentid);

	/**
	 * 更新字典项
	 * @param dictionary
	 */
	public void saveupdateDictionary(Dictionary dictionary);

	/**
	 *获取已pkid开始的字典列表 
	 * @param pkid
	 * @return
	 */
	public List getDictionargBeginWitchId(String pkid);

	/**
	 * 得到所有组XML树
	 * 
	 * @return
	 */
	public String getDictionaryTreeXML();
}
