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
	 * 向数据库插入字典对象
	 * @param dictionary Dictionary 字典对象
	 * 
	 */
	public void save(Dictionary dictionary);

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
	 * 获取数据库字典对象集
	 */
	public List getDictionarys(String s);

	/**
	 * 修改字典项状态为
	 */
	public void update(Dictionary dictionary);

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
