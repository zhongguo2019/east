package com.krm.ps.sysmanage.organmanage.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.sysLog.vo.LogData;
import com.krm.ps.sysmanage.organmanage.dao.OrganInfoDAO;
import com.krm.ps.sysmanage.organmanage.vo.Department;
import com.krm.ps.sysmanage.organmanage.vo.OrganContrast;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage.vo.UserDeptIdx;
import com.krm.ps.sysmanage.organmanage2.vo.OrganSystemInfo;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.SysConfig;
import com.krm.ps.util.StringUtil;

public class OrganInfoDAOHibernate extends BaseDAOHibernate implements
		OrganInfoDAO {

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getAllChildOrgans(java.lang.String, java.lang.String)
	 * modified by 郭跃龙 @ Mar. 28, 2007。解决了5110的下级机构包含5112的问题！
	 */
	public List getAllChildOrgans(String organId, String date) {

		organId=organId.replaceAll("(00)+$","");
		int len=organId.length();
		if((len&1)==1) {
			len++;
		}
		String sql = "SELECT {t.*} FROM code_org_organ t WHERE "
				+ DBDialect.substring("t.code", 1, len) + " = ?"
				+ " AND t.status <> 9 and t.begin_date < '" + date
				+ "' and t.end_date > '" + date
				+ "' ORDER BY t.code,t.organ_order";
		
		log.info(sql);
		List result = list(sql, new Object[][] { { "t", OrganInfo.class } },
				null, new Object[] { organId });
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getSumTypes()
	 */
	public List getSumTypes() {
		String sql = "SELECT {t.*} FROM code_dictionary t WHERE t.parentid = 1001 order by dicid";
		
		log.info(sql);
		List result = list(sql, new Object[][] { { "t", Dictionary.class } }, null, null);
		return result;
	}

	public String getLevel(String code) {
		if (!code.substring(6, 8).equals("00")) {
			return code;
		} else if (!code.substring(4, 8).equals("0000")) {
			return code.substring(0, 6);
		} else if (!code.substring(2, 8).equals("000000")) {
			return code.substring(0, 4);
		} else {
			return code.substring(0, 2);
		}

	}

	// 得到所有机构列表
	public List getOrgans(String date) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 where o.status <> 9 and o.business_type=e2.dicid and e2.parentid=1002 "
				+ "and o.organ_type=e1.dicid and e1.parentid=1001 and o.dummy_type='1' and o.begin_date < :date and o.end_date > :date order by o.organOrder";
		Map map = new HashMap();
		map.put("date", date);
		return list(hql, map);
	}

	// 根据机构编码得到机构信息对象
	public OrganInfo getOrganByCode(String code) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 " 
		    + "where o.status <> 9 and o.business_type=e2.dicid and e2.parentid=1002 "
			+ "and o.organ_type=e1.dicid and e1.parentid=1001 and o.dummy_type='1' " 
			+ "and o.code=:code order by o.organOrder";
		Map map = new HashMap();
		map.put("code", code);
		List ls = list(hql, map);
		if (ls.size() > 0)
			return (OrganInfo) ls.get(0);
		return null;
	}

	// 得到当前机构的下级机构列表(带当前机构)
	public List getJuniorOrgans(String organid, String date) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 "
				+ "where o.status <> and o.business_type=e2.dicid "
				+ "and e2.parentid=1002 and o.organ_type=e1.dicid "
				+ "and e1.parentid=1001 and o.dummy_type='1' and super_id=:sparentid "
				+ "and o.begin_date < :date and o.end_date > :date "
				+ "order by o.organOrder";
		Map map = new HashMap();
		map.put("sparentid", organid);
		map.put("date", date);
		List ls = list(hql, map);
		ls.add(0, getOrganByCode(organid));//当前机构在前
		if (ls.size() > 0)
			return ls;
		else {
			 log.warn("根据机构编码[" + organid + "]及有效日期[" + date + "]查询机构信息时，没有" +
			    		"查询到结果，请确定是否有此机构或机构没有过期，如果仍无法找到结果，可参考如下SQL，" +
			    		"看此SQL是否存在问题，注意：此SQL为HQL，直接执行是有问题的，只供对查询条件进行" +
			    		"参考：\nSQL如下：[" + hql + "]");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getAllOrgansInArea(java.lang.String, java.lang.String)
	 */
	public List getAllOrgansInArea(String organid, String date) {
		OrganInfo oi = getOrganByCode(organid);
		String areaCode = oi.getSuper_id();
		date = date.replaceAll("-", "");
		areaCode = areaCode.replaceAll("(00)*$", "") + "%";
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 "
				+ "where o.status <> 9 and o.business_type=e2.dicid "
				+ "and e2.parentid=1002 and o.organ_type=e1.dicid "
				+ "and e1.parentid=1001 and o.dummy_type='1' and super_id like :areaCode "
				+ "and o.begin_date < :date and o.end_date > :date "
				+ "order by o.organOrder";
		Map map = new HashMap();
		map.put("areaCode", areaCode);
		map.put("date", date);
		List ls = list(hql, map);
		if(ls.size() > 0){
			return ls;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getSubOrgansInArea(java.lang.String,
	 *      java.lang.String)
	 */
	public List getSubOrgansInArea(String organid, String date) {
		OrganInfo oi = getOrganByCode(organid);
		String areaCode = oi.getSuper_id();
		areaCode = areaCode.replaceAll("(00)+$", "");
		areaCode = areaCode.concat("%");
		int i = areaCode.length();
		while(i < 10){
			areaCode = areaCode.concat("0");
			i++;
		}
		
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 "
				+ "where o.status <> 9 and o.business_type=e2.dicid "
				+ "and e2.parentid=1002 and o.organ_type=e1.dicid "
				+ "and e1.parentid=1001 and o.dummy_type='1' and super_id like :areaCode "
				+ "and o.begin_date < :date and o.end_date > :date "
				+ "order by o.organOrder";
		Map map = new HashMap();
		map.put("areaCode", areaCode);
		map.put("date", date);
		List ls = list(hql, map);
		if(ls.size() > 0){
			return ls;
		}
		return null;
	}
	
	// 得到所有机构列表
	public List getOrgans() {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 where o.status <> 9 and o.business_type=e2.dicid and e2.parentid=1002 "
				+ "and o.organ_type=e1.dicid and e1.parentid=1001 and o.dummy_type='1' order by o.organOrder";
		return list(hql);
	}

	// 得到当前机构的下级机构列表（不带当前机构）
	public List getJuniorOrgansOnly(String organid, String date) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 "
				+ "where o.status <> 9 and o.business_type=e2.dicid "
				+ "and e2.parentid=1002 and o.organ_type=e1.dicid "
				+ "and e1.parentid=1001 and o.dummy_type='1' and super_id=:sparentid and o.begin_date < :date and o.end_date > :date  order by o.organOrder";
		Map map = new HashMap();
		map.put("sparentid", organid);
		map.put("date", date);
		// System.out.println(organid+","+date+", hql:"+hql);
		List ls = list(hql, map);
		// System.out.println("size:"+ls.size());
		return ls;
	}

	// 得到当前机构的下级机构中所包含的机构类型
	public List getJuniorOrganTypes(String organid, String date) {
		String hql = "select o.organ_type from OrganInfo o where o.status <> 9 and o.dummy_type='1' and o.super_id=:sparentid and o.begin_date < :date and o.end_date > :date order by o.organOrder";
		Map map = new HashMap();
		HashSet hashset = new HashSet();
		map.put("sparentid", organid);
		map.put("date", date);
		List ls = list(hql, map);
		if (ls.size() > 0) {
			for (int j = 0; j < ls.size(); j++) {
				Object o = (Object[]) ls.get(j);
				hashset.add(o);
			}
			List tt = new ArrayList();
			tt.addAll(hashset);
			return tt;
		}
		return null;
	}

	// 判断机构编码是否重名
	public boolean codeRepeat(Long pkid, String code) {
		String hql = "from OrganInfo t where t.status <> 9 and t.code=:code";
		Map map = new HashMap();
		map.put("code", code);

		String hql1 = "";
		if (pkid != null) {
			hql1 = (" and t.pkid <> :pkid ");
			map.put("pkid", pkid);
		}
		hql = hql + hql1;
		List ls = list(hql, map);
		if (ls.size() > 0) {
			return true;
		}
		return false;
	}

	// 删除机构信息
	public void removeOrgan(Long pkid) {
		Object organ = this.getObject(OrganInfo.class, pkid);
		if (null != organ) {
			OrganInfo organ1 = (OrganInfo) organ;
			this.removeObject(organ1);
			String sql = "delete from code_org_contrast where native_org_id =?";
			jdbcUpdate(sql, new Object[] { organ1.getCode() });
		}
	}

	// 删除机构信息
	public void removeOrganByCode(String code) {
		String sql = "delete from code_org_organ where code = ?";
		jdbcUpdate(sql, new Object[] { code });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getAllChildOrgans(java.lang.String)
	 * @author lmy
	 * @2012/12/9 
	 * @机构同步时，机构编码的格式有变化,所以没有判断机构 ,直接让所有机构都 可以下载报表
	 */
	public List getAllChildOrgans(String organId) {
		String sql = "";
		String strOrganId = organId;
		/**
		if (organId.substring(6, 8).equals("00")) {
			if (organId.substring(4, 8).equals("0000")) {
				if (organId.substring(2, 8).equals("000000")) {
					strOrganId = strOrganId.substring(0, 2);
					sql = "SELECT {t.*} FROM code_org_organ t WHERE "
							+ DBDialect.substring("t.code", 1, 2)
							+ " = ? AND t.status <> 9 ORDER BY t.code,t.organ_order";
				} else {
					strOrganId = strOrganId.substring(0, 4);
					sql = "SELECT {t.*} FROM code_org_organ t WHERE "
							+ DBDialect.substring("t.code", 1, 4)
							+ " = ? AND t.status <> 9 ORDER BY t.code,t.organ_order";
				}
			} else {
				strOrganId = strOrganId.substring(0, 6);
				sql = "SELECT {t.*} FROM code_org_organ t WHERE "
						+ DBDialect.substring("t.code", 1, 6)
						+ " = ? AND t.status <> 9 ORDER BY t.code,t.organ_order";
			}
		} else {
			sql = "SELECT {t.*} FROM code_org_organ t WHERE t.code = ? AND t.status <> 9 ORDER BY t.code,t.organ_order";
		}
		**/
			sql = "SELECT {t.*} FROM code_org_organ t WHERE t.code = ? AND t.status <> 9 ORDER BY t.code,t.organ_order";
		log.info(sql);
		List result = list(sql, new Object[][] { { "t", OrganInfo.class } },
				null, new Object[] { strOrganId });
		return result;
	}
	
	// 得到当前机构的下级机构列表(带当前机构)
	public List getJuniorOrgans(String organid) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 "
				+ "where o.status <> 9 and o.business_type=e2.dicid "
				+ "and e2.parentid=1002 and o.organ_type=e1.dicid "
				+ "and e1.parentid=1001 and o.dummy_type='1' and super_id=:sparentid order by o.organOrder";
		Map map = new HashMap();
		map.put("sparentid", organid);
		List ls = list(hql, map);
		ls.add(0, getOrganByCode(organid));//wsx 9-1,当前机构在前
		if (ls.size() > 0)
			return ls;
		return null;
	}
	
	 //得到本级机构以及所有下级机构2006.9.20
	//的到本级机构的所有下级机构，2006.12.31修改
	public List getSelfJuniorOrgans(String organid) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 "
			+ "where o.status <> 9 and o.business_type=e2.dicid "
			+ "and e2.parentid=1002 and o.organ_type=e1.dicid "
			+ "and e1.parentid=1001 and o.dummy_type='1' and super_id like :sparentid order by o.organOrder";
		String organids = "";
		
		if ("000000".equals(organid.substring(2))){
			organids = organid.substring(0,2) + "%";
		}else if ("0000".equals(organid.substring(4))){
			organids = organid.substring(0,4) + "%";
		}else if ("00".equals(organid.substring(6))){
			organids = organid.substring(0,6) + "%";
		}else{
			organids = organid;
		}
		
		Map map = new HashMap();
		map.put("sparentid", organids);
		List ls = list(hql, map);
//		ls.add(0, getOrganByCode(organid));
		return ls;
	}

    //	2007.1.31
	public List getSelfJuniorOrgan(String organid) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 "
			+ "where o.status <> 9 and o.business_type=e2.dicid "
			+ "and e2.parentid=1002 and o.organ_type=e1.dicid "
			+ "and e1.parentid=1001 and o.dummy_type='1' and super_id like :sparentid order by o.organOrder";
		String organids = "";
		
		if ("000000".equals(organid.substring(2))){
			organids = organid.substring(0,2) + "%";
		}else if ("0000".equals(organid.substring(4))){
			organids = organid.substring(0,4) + "%";
		}else if ("00".equals(organid.substring(6))){
			organids = organid.substring(0,6) + "%";
		}else{
			organids = organid;
		}
		
		Map map = new HashMap();
		map.put("sparentid", organids);
		List ls = list(hql, map);
		ls.add(0, getOrganByCode(organid));		
		return ls;
	}
	// 得到当前机构的下级机构列表（不带当前机构）
	public List getJuniorOrgansOnly(String organid) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 "
				+ "where o.status <> 9 and o.business_type=e2.dicid "
				+ "and e2.parentid=1002 and o.organ_type=e1.dicid "
				+ "and e1.parentid=1001 and o.dummy_type='1' and super_id=:sparentid order by o.organOrder";
		Map map = new HashMap();
		map.put("sparentid", organid);
		List ls = list(hql, map);
		if (ls.size() > 0)
			return ls;
		return null;
	}

	// 得到当前机构的下级机构中所包含的机构类型
	public List getJuniorOrganTypes(String organid) {
		String hql = "select o.organ_type from OrganInfo o where o.status <> 9 and o.dummy_type='1' and o.super_id=:sparentid";
		Map map = new HashMap();
		HashSet hashset = new HashSet();
		map.put("sparentid", organid);
		List ls = list(hql, map);
		if (ls.size() > 0) {
			for (int j = 0; j < ls.size(); j++) {
				Object o = (Object[]) ls.get(j);
				hashset.add(o);
			}
			List tt = new ArrayList();
			tt.addAll(hashset);
			return tt;
		}
		return null;
	}

	public Integer getMaxOrganOrder() {
		String sql = "select MAX(r.organOrder) from OrganInfo r";
		List show = list(sql);
		Iterator i = show.iterator();
		Integer temp = (Integer) i.next();
		if (temp != null) {
			return (temp);
		}

		// 若尚无机构时返回0
		return (new Integer(0));
	}

	public Integer getOrganOrder(Long organId) {
		String hql = "select t.organOrder from OrganInfo t where t.pkid ="
				+ organId;
		List ls = list(hql);
		return (Integer) ls.get(0);
	}

	public void updateOrganSuperId(String codeBefore, String codeNow) {
		String sql = "update code_org_organ set super_id = '" + codeNow
				+ "' where super_id = '" + codeBefore + "'";
		jdbcUpdate(sql, null);
	}

	public int getOrganCount() {
		Integer l = (Integer) uniqueResult("SELECT COUNT(*) FROM OrganInfo");
		return l.intValue();
	}

	public String[] getDirSubOrgans(String[] organCode) {
		// long st=System.currentTimeMillis();
		String codes = StringUtil.convertArrayToSplitString2(organCode, ",");
		String sql = "SELECT code FROM code_org_organ WHERE super_id IN ("
				+ codes + ")";
		// System.out.println(sql);
		Object[][] scalaries = { { "code", Hibernate.STRING } };
		List subOrg = list(sql, null, scalaries);
		String[] subOrgCode = new String[subOrg.size()];
		Iterator it = subOrg.iterator();
		for (int i = 0; it.hasNext(); i++) {
			Object o = it.next();
			subOrgCode[i] = (String) o;
		}
		// long en=System.currentTimeMillis();
		// System.out.println(".\tgetDirSubOrgans use:"+(en-st)+"ms.");
		// System.out.println("time��" + System.currentTimeMillis());
		return subOrgCode;
	}	
	

	public List listSubOrgans(String[] organCode, int level) {

		int zl = (level - 1) * 2;
		Pattern tp = Pattern.compile("(00)+$");

		StringBuffer sql = new StringBuffer(
				"SELECT o.code,o.full_name FROM code_org_organ o WHERE");
		for (int i = 0; i < organCode.length; i++) {
			String p = tp.matcher(organCode[i]).replaceAll("");
			if (p.length() < 8) {
				int al = p.length() + zl;
				p += "%";
				if (al < 8) {
					p = p + "00000000".substring(al);
				}
			}
			if (i != 0) {
				sql.append(" OR");
			}
			sql.append(" (code LIKE '" + p + "')");
		}
		// System.out.println(sql);

		Object[][] scalaries = { { "code", Hibernate.STRING }, { "full_name", Hibernate.STRING } };
		List orgCodeName = list(sql.toString(), null, scalaries);
		
		List orgs=new ArrayList();

		Iterator it = orgCodeName.iterator();
		for (int i = 0; it.hasNext(); i++) {
			OrganInfo oi=new OrganInfo();
			Object[] o = (Object[])it.next();
			oi.setCode((String)o[0]);
			oi.setFull_name((String)o[1]);
			orgs.add(oi);
		}

		
		return orgs;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getExportOrgans(java.lang.String, java.lang.String)
	 */
	public List getExportOrgans(String organid, String date) {
		OrganInfo oi = getOrganByCode(organid);
		String areaCode = oi.getSuper_id();
		String areaCodeFormat = areaCode.replaceAll("(00)+$","");
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname) from OrganInfo o,Dictionary e1,Dictionary e2 "
			+ "where o.status <> 9 and o.business_type=e2.dicid "
			+ "and e2.parentid=1002 and o.organ_type=e1.dicid "
			+ "and e1.parentid=1001 and o.dummy_type='1' and o.super_id like '"
			+ areaCodeFormat
			+ "%' and o.begin_date < :date and o.end_date > :date  order by o.organOrder";
	
		Map map = new HashMap();
		map.put("date", date);
		List ls = list(hql, map);
		if (ls.size() > 0)
			return ls;
		return null;
	}
	
	//2006.9.28
	public String getAllChildOrganTree(String organId, String date) {
		String sql = "";
		Object[][] scalaries = { { "code", Hibernate.STRING },
				{ "short_name", Hibernate.STRING },
				{ "organ_order", Hibernate.INTEGER },
				{ "super_id", Hibernate.STRING }};
		
		String strOrganId = organId;
		if (organId.substring(6, 8).equals("00")) {
			if (organId.substring(4, 8).equals("0000")) {
				if (organId.substring(2, 8).equals("000000")) {
					strOrganId = strOrganId.substring(0, 2);
					sql = "SELECT code,short_name,super_id,organ_order FROM code_org_organ  WHERE "
							+ DBDialect.substring("code", 1, 2)
							+ " = '" +strOrganId+ "' AND status <> 9 and begin_date < '"
							+ date + "' and end_date > '" + date
							+ "' ORDER BY code,organ_order";
				} else {
					strOrganId = strOrganId.substring(0, 4);
					sql = "SELECT code,short_name,super_id,organ_order FROM code_org_organ  WHERE "
							+ DBDialect.substring("code", 1, 4)
							+ " = '"+strOrganId+"' AND status <> 9 and begin_date < '"
							+ date + "' and end_date > '" + date
							+ "' ORDER BY code,organ_order";
				}
			} else {
				strOrganId = strOrganId.substring(0, 6);
				sql = "SELECT code,short_name,super_id,organ_order FROM code_org_organ  WHERE "
						+ DBDialect.substring("code", 1, 6)
						+ " = '"+strOrganId+"' AND status <> 9 and begin_date < '" + date
						+ "' and end_date > '" + date
						+ "' ORDER BY code,organ_order";
			}
		} else {
			sql = "SELECT code,short_name,super_id,organ_order FROM code_org_organ  WHERE code = '"+strOrganId+"' AND status <> 9 and begin_date < '"
					+ date
					+ "' and end_date > '"
					+ date
					+ "' ORDER BY code,organ_order";
		}
		log.info(sql);
		String strXml = getTreeXML(sql, null, scalaries, new String[] { "code",
				"short_name","organ_order", "super_id" });
		// log.info(strXml);
		return strXml;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getOrganType(java.lang.Integer)
	 */
	public String getOrganType(Integer organType) {
		String description = "";
		String sql = "select d.description from code_dictionary d where d.dicid = "+organType;
		Object[][] scalaries = { { "description", Hibernate.STRING}};
		List list = this.list(sql, null, scalaries);
		if(list.size()!=0){
			description = (String)list.get(0);
		}
		return description;
	}

	public List getStatExoprtJuniorOrgans(String organid, String date) {
		String sql = "select {t.*} from code_org_organ t where t.super_id = "+organid+"and t.begin_date < '"+date+"' "+
					 " and end_date >"+date;
		return this.list(sql,new Object[][]{{"t", OrganInfo.class}},null);		
	}

	public List getOrganInfo(String organId) {
		String sql = "select org.short_name sn,con.outer_org_code oc from code_org_organ org , code_org_contrast con "+
					"where org.code = "+organId+" and org.status <> 9 and con.native_org_id = "+organId+" "+
						"and org.code = con.native_org_id and con.system_code = 10";
		Object[][] scalaries = {{"sn",Hibernate.STRING},{"oc",Hibernate.STRING}};
		List list = this.list(sql,null,scalaries);
		if(list.size() > 0){
			return list;
		}
		return null;
	}

	public List getStatOrgans(String organId, String date) {
		String orgId = this.getLevel(organId);		
		String sql = "select o.code as code,o.short_name as nm,o.organ_type as orgType,c.outer_org_code as orgType from code_org_organ o , code_org_contrast c "+
					 "where (o.super_id like '"+orgId+"%' or o.code = '"+organId+"') and '"+date+"'>o.begin_date and '"+date+"'<o.end_date and o.status <> 9 "+ 
                     "and o.code = c.native_org_id and c.system_code = 10";
		Object[][] scalaries = {{"code",Hibernate.STRING},{"nm",Hibernate.STRING},{"orgType",Hibernate.STRING}};
		List list = this.list(sql,null,scalaries);
		return list;
	}

	public List getStatOrgan(String organId) {
		String sql = "select o.code as code,o.short_name as nm,c.outer_org_code as orgType "+
					 "from code_org_organ o , code_org_contrast c "+ 
					 "where o.code = '"+organId+"' and o.status <> 9 and o.code = c.native_org_id and c.system_code = 10";
		Object[][] scalaries = {{"code",Hibernate.STRING},{"nm",Hibernate.STRING},{"orgType",Hibernate.STRING}};
		List list = this.list(sql,null,scalaries);
		if(list.size() > 0){
			return list;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#isOrganUsed(java.lang.String)
	 */
	public boolean isOrganUsed(String organCode){
		//String sql = "select {t.*} from log_data t where t.cd_organ = ?";
		String sql = "select pkid from log_data  where cd_organ = '"+organCode+"'";
	//	Object[] values=new Object[] {new String(organCode)};
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 2007-11-21左少杰修改代码，转换SQL语句中的日志表名以实现日志分表存储
		// 这个比较特殊：因为是没有指定数据日期的日志查询。
		// 可以分成12次调用，还可以使用Union联合但可能造成sql串过长问题
		List list = null;
		if ("true".equals(SysConfig.IS_DISTRIBUTE_LOG))
		{
			for (int k = 1; k < 13; k++)
			{
				String converOKSql = sql;
				String replaceTableName = "log_data_";
				if (k < 10)
					replaceTableName += "0" + k;
				else
					replaceTableName += k;
				converOKSql = converOKSql.replaceAll("log_data",replaceTableName);
				//list =  list(converOKSql,new Object[][] { { "t", LogData.class } }, null, values);
				list = this.list(converOKSql);
				if (list.size() > 0)
					return true;
			}
		}
		else
		{
			//list = list(sql,new Object[][] { { "t", LogData.class } }, null, values);
			list = list(sql);
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(list.size() > 0){
			return true;
		}
		//Object[][] scalaries = { { "pkid", Hibernate.INTEGER }};
		for(int i = 0; i < 13; i++){
			String tableName = "";
			if(i == 0){
				tableName = "rep_data1";
			}else if(i < 10){
				tableName = "rep_data_0" + String.valueOf(i);
			}else{
				tableName = "rep_data_" + String.valueOf(i);
			}			
			//sql = "select t.pkid as pkid from " + tableName + " t where t.organ_id = ?";
			sql = "select pkid from " + tableName + " where organ_id = '"+organCode+"'";
		//	list =  list(sql, null, scalaries, values);
			list =  list(sql);
			if(list.size() > 0){
				return true;
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#isOrganInTree(java.lang.String)
	 */
	public List isOrganInTree(String organCode){
		organCode = "'"+organCode+"'";
		String sql1 = "SELECT pkid AS pkid FROM code_org_organ o WHERE code = "+organCode;
		Object [][] scalaries1 = {{"pkid",Hibernate.LONG}};
		List list1 = this.list(sql1, null, scalaries1);
		Long nodeId = null;
		if(list1.size() > 0){
			nodeId = (Long)list1.get(0);
		}
		String sql = "select t.name as name from code_user_org_idx t, code_org_tree s " +
				"where t.pkid = s.idxid and s.nodeid = ?";
		Object[][] scalaries = { { "name", Hibernate.STRING }};
		Object[] values=new Object[] {nodeId};
		List list =  list(sql,null, 
				scalaries, values);
		
		return list;
	}
	/**
	 * 根据机构类型带到机构
	 * @param type
	 * @return
	 */
	public Map getOrganByType(Integer type, String date) {
		Map map = new HashMap();
		String sql = "SELECT {o.*} FROM code_org_organ o WHERE organ_type = " + type + " " +
				"AND begin_date < '"+date+"' AND end_date > '"+date+"' AND status <> 9";
		List result = list(sql, new Object[][]{{"o", OrganInfo.class}}, null, null);
		if(result.size() > 0){
			for(Iterator orgItr = result.iterator(); orgItr.hasNext();){
				OrganInfo organInfo = (OrganInfo)orgItr.next();
				map.put(organInfo.getCode(), organInfo);
			}
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getOrgMapByContrast(java.lang.Long)
	 */
	public List getOrgMapByContrast(Long systemId)
	//如果根据outer_org_code获取name属性，会有重复name，因为虚拟机构的outer_org_code有相同的值，故把outer_org_code 修改为 nativr_org_id by yanghui 2012/8/20
	{
		Object [][] scalaries = {{"nativecode", Hibernate.STRING},{"name",Hibernate.STRING},{"code", Hibernate.STRING}};
		String sql = "select c.native_org_id as nativecode , c.outer_org_code as outercode, o.short_name as name, o.code as code from code_org_contrast c join code_org_organ o "+
					"on c.native_org_id = o.code where c.system_code = " + systemId;
		List result = list(sql, null, scalaries, null);
		return result;
	}

	/**
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#deleteUserDeptIdxList(java.util.List)
	 */
	public boolean deleteUserDeptIdxList(List userDeptIdxList)
	{
		UserDeptIdx userDeptIdx;
		int size = userDeptIdxList.size();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < size; i++)
		{
			userDeptIdx = (UserDeptIdx)userDeptIdxList.get(i);
			// 2010-7-3 下午07:21:34 皮亮修改
			// 删除人员部门信息的时候，直接把人员的部门索引信息删除就是了
			buffer.append(" or (").append("user_id=").append(userDeptIdx.getUserId())
				// .append(" and dept_id=").append(userDeptIdx.getDeptId())  // 人员部门信息
				.append(" and organ_code='").append(userDeptIdx.getOrganCode())
				.append("')");
		}
		String result = buffer.toString();
		boolean emptyFlag = "".equals(result);
		String sql = "";
		if (!emptyFlag)
		{
			sql = "delete from code_user_dept_idx where " + result.replaceAll("^\\s*or", "");
			log.info("删除用户部门索引表的sql为：============= " + sql);
			jdbcUpdate(sql, null);
		}
		return true;
	}

	/**
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#saveUserDeptIdxList(java.util.List)
	 */
	public boolean saveUserDeptIdxList(List userDeptIdxList)
	{
		String hql = "from UserDeptIdx udx where udx.userId = :userId";
		Map parameterMap = null;
		int size = userDeptIdxList.size();
		UserDeptIdx userDeptIdx;
		UserDeptIdx tmpUserDeptIdx;
		List list;
		for (int i = 0; i < size; i++)
		{
			userDeptIdx = (UserDeptIdx)userDeptIdxList.get(i);
			parameterMap = new HashMap();
			parameterMap.put("userId", userDeptIdx.getUserId());
			list = list(hql, parameterMap);
			if (list.size() > 0)
			{
				log.info("记录：" + userDeptIdx.getUserId() + "_" + userDeptIdx.getDeptId() + "_" + userDeptIdx.getOrganCode()
						+ "已经存在，把这些记录更新");
				tmpUserDeptIdx = (UserDeptIdx)list.get(0);
				userDeptIdx.setPkid(tmpUserDeptIdx.getPkid());
				userDeptIdx.setUserName(tmpUserDeptIdx.getUserName());
				try
				{
					BeanUtils.copyProperties(tmpUserDeptIdx, userDeptIdx);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					// TODO handle it
				}
				userDeptIdx = tmpUserDeptIdx;
			}
			saveObject(userDeptIdx);
		}
		return true;
	}

	/**
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getUserDeptIdxForUser(java.lang.Long)
	 */
	public UserDeptIdx getUserDeptIdxForUser(Long userId)
	{
		String hql = "from UserDeptIdx udx where udx.userId = " + userId;
		List list = list(hql);
		if (list.size() > 0)
			return (UserDeptIdx)list.get(0);
		else
			return null;
	}

	/**
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getDepartmentForUser(java.lang.Long)
	 */
	public Department getDepartmentForUser(Long pkid)
	{
		String hql = "select dept from Department dept, UserDeptIdx udx where dept.pkid=udx.deptId and udx.userId=" + pkid;
		List list = list(hql);
		if (list.size() == 0)
			return null;
		else
			return (Department)list.get(0);
	}

	/**
	 * @see com.krm.slsint.organmanage.dao.OrganInfoDAO#getContrastMap(java.lang.Long)
	 */
	public List getContrastMap(Long systemId)
	{
		String sql = "SELECT {c.*} FROM code_org_contrast c WHERE system_code = " + systemId;
		List result = list(sql, new Object[][]{{"c", OrganContrast.class}}, null, null);
		return result;
	}
	
	public String getOrganConstrat(Long system_code,String organId)
	{
		String sql = " select {c.*} from CODE_ORG_CONTRAST c where c.SYSTEM_CODE = " + system_code +" and c.NATIVE_ORG_ID = '" + organId +"'";
		List result = list(sql,new Object[][]{{"c",OrganContrast.class}},null,null);
		String outOrgId = "";
		if(!result.isEmpty())
		{
			OrganContrast oc = (OrganContrast) result.get(0);
			outOrgId = oc.getOuter_org_code();
		}
		return outOrgId;
		
	}
	
	public Map getOrganMap()
	{
		Map map = new HashMap();;
		String sql = "select {o.*} from code_org_organ o";
		List result = list(sql, new Object[][]{{"o", OrganInfo.class}}, null, null);
		for(Iterator itr = result.iterator(); itr.hasNext();) {
			OrganInfo oi = (OrganInfo)itr.next();
			map.put(oi.getCode(), oi);
		}
		return map;
	}
	/**
	 * 从code_org_organ_all表里查出的数据插入code_org_tree表
	 */
	public void saveOrganTree(String date,Long pkid){
		String sql="select pkid, code,short_name as name  from CODE_ORG_ORGAN_ALL where INST_PARENT_NO='00001'";
		Object [][] scalaries = {{"pkid", Hibernate.LONG},{"code", Hibernate.STRING},{"name",Hibernate.STRING}};
		List result = list(sql, null, scalaries, null);
		List list =new ArrayList();
		
		for(int i=0;i<result.size();i++){
			Object[] obj=(Object[])result.get(i);
			addlistsql(obj,list,pkid,new Long ("1"),"GD"+getidx(i+1));
			String sqli="select pkid, code,short_name as name  from CODE_ORG_ORGAN_ALL where INST_PARENT_NO='"+(String)obj[1]+"'";
			List resulti = list(sqli, null, scalaries, null);
			 for(int j=0;j<resulti.size();j++){
					Object[] objj=(Object[])resulti.get(j);
					addlistsql(objj,list,pkid,(Long)obj[0],"GD"+getidx(i+1)+""+getidx(j+1));
					String sqlj="select pkid, code,short_name as name  from CODE_ORG_ORGAN_ALL where INST_PARENT_NO='"+(String)objj[1]+"'";
					List resultj = list(sqlj, null, scalaries, null);
				 	for(int k=0;k<resultj.size();k++){
				 		Object[] objk=(Object[])resultj.get(k);
						addlistsql(objk,list,pkid,(Long)objj[0],"GD"+getidx(i+1)+""+getidx(j+1)+""+getidx(k+1));
						System.out.println("k");
						String sqlk="select pkid, code,short_name as name  from CODE_ORG_ORGAN_ALL where INST_PARENT_NO='"+(String)objk[1]+"'";
						List resultk = list(sqlk, null, scalaries, null);
				 		  for(int m=0;m<resultk.size();m++){
				 			 Object[] objm=(Object[])resultk.get(m);
								addlistsql(objm,list,(Long)objm[0],(Long)objk[0],"GD"+getidx(i+1)+""+getidx(j+1)+""+getidx(k+1)+""+getidx(m+1));
				 		  }
				 	}
				 
			 }
		}
		for(int j=0;j<list.size();j++){
			System.out.println("list:"+(String)list.get(j));
		}
		batchExecuteJDBCSSQL(list, 100);
		//String sql = "insert into CODE_ORG_TREE ( SELECT "+DBDialect.genSequence("CODE_ORG_TREE_SEQ")+ ","+pkid+",r.pkid,r2.pkid,r.code,1,r.SHORT_NAME,'' FROM CODE_ORG_ORGAN_ALL r,CODE_ORG_ORGAN_ALL r2  WHERE r.CREATE_DATE='"+date+"' and r.INST_PARENT_NO=r2.code)";
         //jdbcUpdate(sql, null);
	}
	public void addlistsql(Object[] obj,List list,Long pkid,Long parentid,String code){
		String insertsql="insert into CODE_ORG_TREE (PKID, IDXID, NODEID, PARENTID, SUBTREETAG, ISSHOWCHILD, ALIASNAME, DESCRIPTION ) values ("+DBDialect.genSequence("CODE_ORG_TREE_SEQ")+","+pkid+","+obj[0]+","+parentid+",'"+code+"',1,'"+((String)obj[2]).replaceAll(" ", "")+"','')";
	list.add(insertsql);
	}
	
	public String getidx(int i){
		if(i<10){
			return "0"+i;
		}
		return ""+i;
	}
	/**
	 * 保存CODE_USER_ORG_IDX报送机构树+年月 报送机构树201307
	 * @param date
	 * @param pkid
	 */
	public long saveOrganTreeIdx(OrganSystemInfo osi){
		//String sql="insert into CODE_USER_ORG_IDX values("+DBDialect.genSequence("CODE_USER_ORG_IDX_SEQ")+",'"+name+"',"+userid+",0,'"+areaId+"',1,0,'"+startdate+"','"+enddate+"','1',null)";
		// return jdbcUpdateKey(sql, null);
		return Long.parseLong(getHibernateTemplate().save(osi).toString());
	}

	@Override
	public OrganTreeNode getNode(int nodeId) {
		OrganTreeNode organTreeNode =null;
		String  sql="select {n.*} from code_org_tree n where n.NODEID = ?  ";
		List<OrganTreeNode> tmpList = list(
				sql,
				new Object[][] { { "n", OrganTreeNode.class } },
				null,
				new Object[] {  new Integer(nodeId) });
		if (tmpList!=null&&tmpList.size()>0){
			organTreeNode = tmpList.get(0);
		}
		return organTreeNode;
	}

}