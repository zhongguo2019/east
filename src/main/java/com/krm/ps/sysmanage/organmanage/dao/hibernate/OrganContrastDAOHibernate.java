package com.krm.ps.sysmanage.organmanage.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.sysmanage.organmanage.dao.OrganContrastDAO;
import com.krm.ps.sysmanage.organmanage.dao.OrganInfoDAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganContrast;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public class OrganContrastDAOHibernate extends BaseDAOHibernate implements OrganContrastDAO{
	
	private OrganInfoDAO oiDAO;
	
	public void setOiDAO(OrganInfoDAO obj){
		this.oiDAO = obj;
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
	
	//得到所有机构对照关系
	   public List getAllContrasts(){
		   String hql = "from OrganContrast";
		   return list(hql);
	   }
	   
	 //得到对应ID的机构对照关系
	   public OrganContrast getContrast(Long pkid){
		   Object contrast = this.getObject(OrganContrast.class,pkid);
	       return((OrganContrast)contrast);	   
	   }
	   
       //��ݻ�ID�õ���Ӧ�Ļ���չ�ϵ
	   public List getContrastById(String organid){
		   String hql = "from OrganContrast t where t.native_org_id=:organid";
		   Map map = new HashMap();
		   map.put("organid",organid);
			List ls = list(hql,map);
			if(ls.size()>0)
				return ls;
			return null;
	   }
	   
	 //根据机构ID得到对应的机构对照关系
	   public List getJuniorOrganContrasts(String organid,String date){
		 //取得机构所在地区的地区代码
		   OrganInfo oi = oiDAO.getOrganByCode(organid);
		   String areaCode = oi.getSuper_id();
		 //去掉地区代码末尾的所有“00”
		   String areaCodeFormat = areaCode.replaceAll("(00)+$","") + "%";
		   String hql= "select new OrganContrast(oc,oi.short_name) from OrganInfo oi,OrganContrast oc " +
		   		"where oc.system_code <> 8 and oi.super_id like :areaCodeFormat and oi.code=oc.native_org_id " +
		   		"and oi.begin_date < :date and oi.end_date > :date";
		   Map map = new HashMap();
		   map.put("areaCodeFormat",areaCodeFormat);
		   map.put("date",date);
		   
			List ls = list(hql,map);
			if(ls.size()>0){			
				return ls;
			}
			return null;
	   }
	   
	 //根据机构ID及外部系统号得到该机构及其下级机构的所有在该外部系统下的机构对照关系
	   public List getJuniorOrganContrastsForStat(String organid,String date,Integer systemcode){
		   String level = this.getLevel(organid);
		   String hql= "select new OrganContrast(oc,oi.short_name) from OrganInfo oi,OrganContrast oc where oi.code like '%"
				+ level
				+ "%' and oc.system_code = :systemcode and oi.code=oc.native_org_id and oi.begin_date < :date and oi.end_date > :date";
		   Map map = new HashMap();
		   map.put("systemcode",systemcode);
		   map.put("date",date);
			List ls = list(hql,map);
			if(ls.size()>0){			
				return ls;
			}
			return null;
	   }
	   
	   
	 //根据外部系统号及外部机构编码得到对应的机构对照关系
	   public OrganContrast getContrastbyCode(Long system_code,String outer_org_code){
		   String hql = "from OrganContrast t where t.system_code=:scode and t.outer_org_code=:oocode";
		   Map map = new HashMap();
		   map.put("scode",system_code);
		   map.put("oocode",outer_org_code);
			List ls = list(hql,map);
			if(ls.size()>0)
				return (OrganContrast)ls.get(0);
			return null;
		   
	   }
	   
	   
	 //根据外部系统号及本机构编码得到对应的机构对照关系
	   public OrganContrast getContrastbyOrgan(Long system_code,String code){
		   String hql = "from OrganContrast t where t.system_code=:scode and t.native_org_id=:ocode";
		   Map map = new HashMap();
		   map.put("scode",system_code);
		   map.put("ocode",code);
			List ls = list(hql,map);
			if(ls.size()>0)
				return (OrganContrast)ls.get(0);
			return null;
	   }
	   
	// 根据外部系统号及机构对照编码得到本系统机构编码
		public OrganContrast getOrganbyContrast(Long system_code, String contrast){// wsx 12-14
		   String hql = "from OrganContrast t where t.system_code=:scode and t.outer_org_code=:ocode";
		   Map map = new HashMap();
		   map.put("scode",system_code);
		   map.put("ocode",contrast);
			List ls = list(hql,map);
			if(ls.size()>0)
				return (OrganContrast)ls.get(0);
			return null;
	   }
	   
		  //删除对应该机构ID的机构对照信息（不可恢复）	
	   public void removeContrast(Long pkid){
		   Object contrast = this.getObject(OrganContrast.class,pkid);
			if(null!=contrast){
				this.removeObject(contrast);
			}
	   }
	   
	 //删除一个机构的所有对照关系
	   public void removeContrastByOrgan(String organid){
		   String sql = "delete from code_org_contrast where native_org_id =?";
		   jdbcUpdate(sql,new Object[]{organid});
	   }
	   
	 //根据机构编码删除该机构所有下级对照关系
	   public void removeOrganAllContrasts(String organcode){
		   String sql = "delete from code_org_contrast where native_org_id like '%"+organcode+"%'";
		   jdbcUpdate(sql, null);
	   }

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.OrganContrastDAO#getJuniorOrganByCode(java.lang.String, java.lang.Long)
	 */
	public List getJuniorOrganByCode(String organId, Long sysCode) {
		String sql = "SELECT native_org_id AS nativeCode,outer_org_code AS outerCode FROM code_org_contrast WHERE native_org_id IN ("+organId+") AND system_code = "+sysCode+" ORDER BY native_org_id";
		Object[][] scalaries = {{"nativeCode",Hibernate.STRING}, { "outerCode", Hibernate.STRING } };
		return this.list(sql, null, scalaries);
	}
	/**
	 * 得到机构对照
	 * @param organsCode
	 * 			��code
	 * @return
	 */
	public List getOrgansContrast(String organsCode) {
		String sql = "SELECT {c.*} FROM code_org_contrast c JOIN code_system s ON "+
					 "c.system_code = s.pkid WHERE c.native_org_id IN ("+organsCode+")";
		return list(sql,new Object[][] { { "c", OrganContrast.class } },null,null);
	}
	
	public int updateRepidByRepid(Long newRepid, Long oldRepid){
		String sql="update code_orgtype_report set reportid = "
			+newRepid+" where reportid = "+oldRepid;
		return jdbcUpdate(sql,null);
	}
	/**
	 * 得到指定类型机构对照
	 * @param systemCode
	 * @return
	 */
	public List getOrganContrast(Long systemCode) {
		String sql = "SELECT {c.*} FROM code_org_contrast c WHERE c.system_code = ?";
		Object [] values = new Object[] {systemCode};
		List result =  list(sql,new Object[][] {{"c", OrganContrast.class}}, null, values);
		return result;
	}

	public List getOrganContrast(Long systemCode, String organId)
	{
		String sql = "SELECT {c.*} FROM code_org_contrast c WHERE c.system_code = "+ systemCode +" AND c.native_org_id IN (" +organId+")";
		List result = list(sql, new Object[][]{{"c", OrganContrast.class}}, null, null);
		return result;
	}
}