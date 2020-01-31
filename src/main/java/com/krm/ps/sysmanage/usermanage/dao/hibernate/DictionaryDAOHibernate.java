package com.krm.ps.sysmanage.usermanage.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.util.Constants;
import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.sysmanage.usermanage.dao.DictionaryDAO;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;

public class DictionaryDAOHibernate extends BaseDAOHibernate implements
		DictionaryDAO {

	/**
	 * 查询登陆地IP校验状态
	 * @return dicname
	 * @author LC
	 */
	public String getIpStatus()
	{
		String hql = "select dicname from Dictionary where pkid = 577";
		List ls = this.list(hql);
		String dicname = ls.get(0).toString();
		if(dicname.equals("开放验证"))
		{
			dicname="1";
		}
		else if(dicname.equals("关闭验证"))
		{
			dicname="2";
		}
		return dicname;
	}
	
	public List getDics() {
		String hql = "from Dictionary where status<>'9' order by pkid";
		return this.list(hql);
	}

	public void removeDic(Long pkid) {
		Object user = this.getObject(Dictionary.class, pkid);
		if (null != user) {
			((Dictionary) user).setStatus(Constants.STATUS_DEL.toString());
			this.saveObject(user);
		}
	}

	public List getEsystem() {
		String hql = "from Esystem where iscontrast='1' and status='1' order by show_order";
		return this.list(hql);
	}

	public List getALLsystem() {
		String hql = "from Esystem where status='1' order by show_order";
		return this.list(hql);
	}
	/*
	 * 2006年9月12日
	 * 修改人:赵鹏程
	 *  (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.DictionaryDAO#getUnits()
	 */
//	public List getUnits() {
//		String hql = "from Units order by display_order ";
//		return this.list(hql);
//	}
	public List getUnits() {
		String hql = "from Units order by display_order ";
		return this.list(hql);
	}
	/*
	 * 2006年9月12日
	 * 修改人:赵鹏程
	 *  (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.DictionaryDAO#getUnits()
	 */
	
	public List queryDataLogGetDics(int type){
		
//		String hql = "from Dictionary where parentid=" + type
		//		+ " and status='1' order by disporder";
		String sql = "SELECT {dic.*} FROM code_dictionary dic WHERE parentid = "+ type +" AND status = '1' ORDER BY disporder";
		//return this.list(hql);
		log.debug("according date set frenquence type :========"+type);
		return this.list(sql, new Object[][]{{"dic", Dictionary.class}}, null);
	}
	

	public List getDicss(int type,List lstparentid) {
//		String hql = "from Dictionary where parentid=" + type
		//		+ " and status='1' order by disporder";
		
		String strDictid="";
		for(int i=0;i<lstparentid.size();i++){
			strDictid = strDictid + lstparentid.get(i) + ",";
		}
		strDictid = strDictid.substring(0,strDictid.length()-1);
		
		String sql = "SELECT {dic.*} FROM code_dictionary dic WHERE parentid = "+ type +" and dicid in ("+ strDictid +" ) AND status = '1' ORDER BY disporder";
		//return this.list(hql);

		log.debug("according date set frenquence strDictid :========"+strDictid+"-----------"+strDictid);
		return this.list(sql, new Object[][]{{"dic", Dictionary.class}}, null);
	}
	
	
	public List getDics(int type) {
		//		String hql = "from Dictionary where parentid=" + type
		//		+ " and status='1' order by disporder";
		String sql = "SELECT {dic.*} FROM code_dictionary dic WHERE parentid = "+ type +" AND status = '1' ORDER BY disporder";
		//return this.list(hql);
		log.debug("according date set frenquence type :========"+type);
		return this.list(sql, new Object[][]{{"dic", Dictionary.class}}, null);
	}

	public int maxDicid(int type) {
		Object d = (Object) uniqueResult("SELECT MAX(d.dicid) FROM Dictionary d"
				+ " WHERE parentid = " + type);
		if (d == null) {
			return 0;
		}
		return ((Long) d).intValue();
	}

	public Dictionary getDictionary(int type, int dicid) {
		String hql = "from Dictionary where parentid=" + type + " and dicid="
				+ dicid + " and status<>'9'  order by disporder";
		List d = this.list(hql);
		if (d.iterator().hasNext()) {
			return (Dictionary) d.get(0);
		}
		return null;
	}
	public Dictionary getDictionary(int type, String description){
		String hql = "from Dictionary where parentid=" + type + " and description='"
		+ description + "' and status<>'9'  order by disporder";
		List d = this.list(hql);
		if (d.iterator().hasNext()) {
			return (Dictionary) d.get(0);
		}
		return null;
	}
	public List getStatExportLevel() {
		// piliang add comment for reading 2010-3-11 上午11:24:26
		// 1011：统计导出级别
		String sql = "select d.dicid as dicId,d.dicname as dicName from code_dictionary d where d.parentid = 1011 and status <> '9'";
		Object[][] scalaries = {{"dicId",Hibernate.LONG},{"dicName",Hibernate.STRING}};
		return this.list(sql, null, scalaries);
	}

	/**
	 * add by zhaoyi _20070328
	 * 得到分组的vo list
	 * 根据parentid获取字典项
	 * @param parentid
	 * @return
	 */
	public List getDictionaryByParentid(String parentid){
		return getHibernateTemplate().find(
				"from Dictionary u where u.parentid=" + parentid + " order by u.disporder");
	}
	
	/**
	 * add by zhaoyi _20070328
	 * 得到分组的vo list
	 * 根据parentid和dicid获取字典项
	 * @param parentid
	 * @param dicid
	 * @return
	 */
	public List getDictionaryByParentidAndDicid(String parentid,String dicid){
		return getHibernateTemplate().find(
				"from Dictionary u where u.parentid=" + parentid + " and u.dicid=" + dicid + "  order by u.disporder");
	}
	
	/**
	 * add by zhaoyi _20070329
	 * 插入一个dictionary
	 * @param dic
	 */
	public void exeAnySql(String sql){
		this.jdbcUpdate(sql,null);
	}

	/**
	 * 根据dicid去得某配置的父节点
	 * @param dicid
	 * @return
	 */
	public Dictionary getDictionary(int dicid) {
		String sql = "SELECT {d.*} FROM code_dictionary d WHERE d.dicid = "+dicid+" AND d.status <> '9' AND d.parentid = 0 ";
		List result = list(sql, new Object[][]{{"d", Dictionary.class}}, null, null);
		if(result.size() > 0) {
			return (Dictionary)result.get(0);
		}else {
			return null;
		}
	}
	/**
	 * 根据上级节点和当前节点id,删除配置(status = 9)
	 * @param parentid
	 * @param dicid
	 */
	public void deleteDictionary(int parentid, int dicid) {
		String sql = "UPDATE code_dictionary SET status = '9' WHERE parentid = "+parentid+" AND dicid = "+dicid;
		jdbcUpdate(sql, null);
	}
	/**
	 * 得到当前最大的业务分类id
	 * @param parentid
	 * @param dicid
	 * @return
	 */
	public int getMaxBusinessId(int parentid) {
		String sql = "SELECT MAX(dicid) AS dicid FROM code_dictionary WHERE parentid = "+ parentid;
		Object[][] scalaries = {{"dicid",Hibernate.LONG}};
		List result = list(sql, null, scalaries, null);
		Iterator it = result.iterator();
		Long order = (Long) it.next();
		if(order == null){
			return 0;
		}else{
			return order.intValue();
		}
	}
	/**
	 * 得到当前最大显示序号
	 * @param parentid
	 * @return
	 */
	public int getMaxDisporder(int parentid) {
		String sql = "SELECT MAX(disporder) AS disporder FROM code_dictionary WHERE parentid = "+ parentid;
		Object[][] scalaries = {{"disporder", Hibernate.LONG}};
		List result = this.list(sql, null, scalaries, null);
		Iterator it = result.iterator();
		Long order = (Long) it.next();
		if(order == null){
			return 0;
		}else{
			return order.intValue();
		}
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.DictionaryDAO#updateVersion(int, int, java.lang.String)
	 * @author zhaoPC
	 */
	public void updateVersion(int parentId, int dicId, String version)
	{
		String sql = "UPDATE code_dictionary SET description = ? WHERE parentid = ? AND dicid = ?";
		Object[] scalaries = new Object[]{version, new Integer(parentId), new Integer(dicId)};
		jdbcUpdate(sql,scalaries);
	}

	
	public Integer getDicnameCount(String dicname, String dicid){
		String sql="select count(1) as resultc from code_dictionary  where parentid=2001  and status <> '9'   and dicname=? ";
		Object[][] scalaries = {{"resultc", Hibernate.INTEGER}};
		
		if(dicid!=null&&!"".equals(dicid)){
			sql+=" and dicid!=?";
			Object[] values = {dicname,dicid};
			List result = this.list(sql, null, scalaries, values);
			
			return (Integer)result.get(0);
		}else{
			Object[]	values={dicname};
			List result = this.list(sql, null, scalaries, values);
			return (Integer)result.get(0);
		}
		
		
		
	
	}

}
