package com.krm.ps.framework.common.dictionary.dao.hibernate;

import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.common.dictionary.dao.DictionaryDAO;
import com.krm.ps.framework.common.dictionary.vo.Dictionary;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve AlertItemSet objects.
 *
 * <p>
 * <a href="AlertDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:xiaofeng265@163.com">yinxu</a>
 *  Modified by <a href="mailto:yinxu@163.com">yinxu </a>
 */
public class DictionaryDAOHibernate extends BaseDAOHibernate implements
		DictionaryDAO
{

	/**
	 * �����ֵ���
	 */
	public void save(Dictionary dictionary)
	{

		if (log.isDebugEnabled())
		{
			log.debug("dic's id: " + dictionary.getDicId());
		}
		saveObject(dictionary);
	}

	/**
	 * ��ȡ�ֵ����б�
	 */

	/**
	 * ��ȡ�����ֵ������
	 *
	 * @return
	 */
	public List getDictionaryRoots()
	{
		return getHibernateTemplate().find(
			"from Dictionary u where u.parentId=0 ");
	}

	/**
	 * ����Pkid��ȡ�ֵ���
	 * @param pkid
	 * @return
	 */
	public List getDictionaryById(String pkid)
	{
		return getHibernateTemplate().find(
			"from Dictionary u where u.dicId=" + pkid);
	}

	
	public List getDictionarys(String s)
	{
		return getHibernateTemplate().find(s);
	}

	/*
	 * �����ֵ���
	 * @see com.krm.netinfo.common.dictionary.dao.DictionaryDAO#update(com.krm.netinfo.common.dictionary.vo.Dictionary)
	 */
	public void update(Dictionary dictionary)
	{

		if (log.isDebugEnabled())
		{
			log.debug("dic's id: " + dictionary.getDicId());
		}
		getHibernateTemplate().update(dictionary);
	}

	/**
	 *��ȡ��pkid��ʼ���ֵ��б� 
	 * @param pkid
	 * @return
	 */
	public List getDictionargBeginWitchId(String pkid)
	{
		Object[][] scalaries = {{"dicId", Hibernate.LONG},
			{"isLeaf", Hibernate.STRING}};
		String sql = "SELECT dicId,isLeaf FROM code_dictionary  where status<>'9'  START WITH dicId = "
			+ pkid + " CONNECT BY PRIOR dicId = parentId";
		//System.out.println(list(sql, null, scalaries));
		return list(sql, null, scalaries);
	}

	/**
	 * �õ�������XML��
	 * 
	 * @return
	 */
	public String getDictionaryTreeXML()
	{
		Object[][] scalaries = {{"dicId", Hibernate.LONG},
			{"dicName", Hibernate.STRING}, {"status", Hibernate.INTEGER},
			{"parentId", Hibernate.LONG}};

		String sql = "select dicid,dicname,status-1 as status,parentid,disporder from code_dictionary  where status<>'9' order by disporder";

		return getTreeXML(sql, null, scalaries, new String[]{"dicId",
			"dicName", "status", "parentId"});

	}

}
