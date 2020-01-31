package com.krm.ps.sysmanage.reportdefine.dao.hibernate;


import java.util.List;

import org.hibernate.Hibernate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.dao.jdbc.BaseDAOJdbc;
import com.krm.ps.sysmanage.reportdefine.dao.ReportConfigDAO;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.SysConfig;

public class ReportConfigDAOHibernate extends BaseDAOHibernate implements
		ReportConfigDAO {
	 
	protected BaseDAOJdbc jdbc;

	public void setDAOJdbc(BaseDAOJdbc jdbc) {
		this.jdbc = jdbc;
	}
	
	public List getReportConfigs(Long reportId) {
		String hql = "from ReportConfig t where t.reportId = " + reportId
				+ " order by t.funId,t.idxId";
		Map map = new HashMap();
		return list(hql, map);
	}
	
	public ReportConfig getReportConfig(Long reportId,int funId,Long idxId) {
		String hql = "from ReportConfig t where t.reportId = " + reportId
				+ " and t.funId = " + funId 
				+ " and t.idxId = " + idxId;		
		Map map = new HashMap();
	    if (list(hql, map).size()>0) {
	    	return (ReportConfig)list(hql, map).get(0);
	    }else{
	    	return null;
	    }
	}	
	
	public List getReportConfigs(Long reportId,Long funcId) {
		String hql = "";
		List configs = new ArrayList();
		if (funcId.intValue()==21||funcId.intValue()==22){
			hql = "from ReportConfig t where t.reportId = " + reportId
		    + " and t.funId in (21,22) "
			+ " order by t.idxId,t.funId";
		}else if (funcId.intValue()==12){
			List repConfigs = new ArrayList();
			List list = getReportConfig(reportId,new Long(12));
			if(list.size()>0){
				for(Iterator itr = list.iterator();itr.hasNext();){
					ReportConfig repConfig = (ReportConfig)itr.next();
					hql = "from ReportConfig t where t.reportId = " + reportId
				    + " and t.funId in (14,17,18) and idx_id =  "+repConfig.getIdxId()
					+ " order by t.idxId,t.funId";
					Map map = new HashMap();
					List l = list(hql,map);
					repConfigs.add(repConfig);
					for(Iterator iter = l.iterator();iter.hasNext();){
						ReportConfig config = (ReportConfig)iter.next();
						repConfigs.add(config);
					}
					
				}
			}
			return repConfigs;
		}else if (funcId.intValue()==13){
			List repConfigs = new ArrayList();
			List list = getReportConfig(reportId,new Long(13));
			if(list.size()>0){
				for(Iterator itr = list.iterator();itr.hasNext();){
					ReportConfig repConfig = (ReportConfig)itr.next();
					hql = "from ReportConfig t where t.reportId = " + reportId
				    + " and t.funId in (14,17,18) and idx_id =  "+repConfig.getIdxId()
					+ " order by t.idxId,t.funId";
					Map map = new HashMap();
					List l = list(hql,map);
					repConfigs.add(repConfig);
					for(Iterator iter = l.iterator();iter.hasNext();){
						ReportConfig config = (ReportConfig)iter.next();
						repConfigs.add(config);
					}
					
				}
			}
			return repConfigs;
		}else{
			hql = "from ReportConfig t where t.reportId = " + reportId
			    + " and t.funId = " + funcId
				+ " order by t.idxId,t.funId";
		}
		Map map = new HashMap();
		if(!hql.equals(""))
			configs = list(hql, map);
		return configs;
	}
	
	public List getReportConfig(Long reportId,Long funcId){
		String hql = "from ReportConfig t where t.reportId = " + reportId
	    + " and t.funId = " + funcId
		+ " order by t.idxId,t.funId";
		Map map = new HashMap();
		return list(hql,map);
	}
	
	public List getReportConfigs(Long reportId,Long funcId,Long defInt) {
		String hql = "from ReportConfig t where t.reportId = " + reportId
		    + " and t.funId = " + funcId 
		    + " and t.defInt = " + defInt;	
		Map map = new HashMap();
		return list(hql, map);
	}
	
	public List getReportConfigs(Long pkId,Long reportId,Long funcId,Long defInt) {
		String sql = "select {t.*} from code_rep_config t where 1=1";
		if(pkId != null) {
			sql += " and t.pkid = " + pkId;
		}
		if(reportId != null) {
			sql += " and t.report_id = " + reportId;
		}
		if(funcId != null) {
			sql += " and t.fun_id = "+ funcId;
		}
		if(defInt != null) {
			sql += " and t.def_int = " + defInt;
		}
		List result = list(sql, new Object[][]{{"t", ReportConfig.class}}, null, null);
		return result;
		/**下面是原来的方法,不通用********/
//		String hql = "from ReportConfig t where t.pkid = " + pkId
//			+ " and t.reportId = " + reportId
//		    + " and t.funId = " + funcId 
//		    + " and t.defInt = " + defInt;		
//		Map map = new HashMap();
//		return list(hql, map);
	}
	
	public List getReportConfigsByFunIdAndOrgIdsAndDate(String funId, String orgIds){
		String sql = "select {t.*} from code_rep_config t where t.def_char in (" + orgIds + ")"
					+ " and t.fun_id=" + funId;
		List result = list(sql, new Object[][]{{"t", ReportConfig.class}}, null, null);
		return result;
	}
	
	public void removeReportConfig(Long pkid) {
		Object reportConfig = this.getObject(ReportConfig.class, pkid);
		if (null != reportConfig) {
			this.removeObject(reportConfig);
		}
	}
	
	public void saveConfig(ReportConfig config,int isUpdate){
		if (isUpdate==0){
			//新增,设置索引
			Object[][] scalaries = { { "cnt", Hibernate.INTEGER }};
			Integer maxIdxNum = new Integer(1);
			List result;
			String sqlStr = "SELECT MAX(idx_id) + 1 as cnt "
					+ "FROM code_rep_config WHERE report_id = ? and fun_id = ?";
			result = list(sqlStr, null, scalaries, new Object[] {
					config.getReportId(), config.getFunId() });
			if (result.get(0) != null) {
				maxIdxNum = (Integer) result.get(0);
			}
			config.setIdxId(new Long(String.valueOf(maxIdxNum)));
		}
		getHibernateTemplate().merge(config);
    }
	
	public void saveConfig(ReportConfig config,ReportConfig config1,int isUpdate){
		if (isUpdate==0){
			//新增,设置索引
			Object[][] scalaries = { { "cnt", Hibernate.INTEGER }};
			Integer maxIdxNum = new Integer(1);
			List result;
			String sqlStr = "SELECT MAX(idx_id) + 1 as cnt "
					+ "FROM code_rep_config WHERE report_id = ? and fun_id = ?";
			result = list(sqlStr, null, scalaries, new Object[] {
					config.getReportId(), config.getFunId() });
			if (result.get(0) != null) {
				maxIdxNum = (Integer) result.get(0);
			}
			config.setIdxId(new Long(String.valueOf(maxIdxNum)));
			config1.setIdxId(new Long(String.valueOf(maxIdxNum)));
		}
		getHibernateTemplate().merge(config);
		getHibernateTemplate().merge(config1);
    }

	public void saveConfig(ReportConfig config,ReportConfig config1,ReportConfig config2,ReportConfig config3,int isUpdate){
		if (isUpdate==0){
			//新增,设置索引
			Object[][] scalaries = { { "cnt", Hibernate.INTEGER }};
			Integer maxIdxNum = new Integer(1);
			List result;
			String sqlStr = "SELECT MAX(idx_id) + 1 as cnt "
					+ "FROM code_rep_config WHERE report_id = ? and fun_id IN (12,13)";
			result = list(sqlStr, null, scalaries, new Object[] {
					config.getReportId() });
			if (result.get(0) != null) {
				maxIdxNum = (Integer) result.get(0);
			}
			config.setIdxId(new Long(String.valueOf(maxIdxNum)));
			config1.setIdxId(new Long(String.valueOf(maxIdxNum)));
			config2.setIdxId(new Long(String.valueOf(maxIdxNum)));
			config3.setIdxId(new Long(String.valueOf(maxIdxNum)));
		}
		getHibernateTemplate().merge(config);
		getHibernateTemplate().merge(config1);
		getHibernateTemplate().merge(config2);
		getHibernateTemplate().merge(config3);
    }
	/**
	 * 根据reportid和funid得到需要的物理表的列名
	 * @param reportId
	 * @param funId
	 * @return
	 */
	public String[] getColumnByRepIdAndFunId(String reportId,String funId){
		
		String sql = "select def_int as di from code_rep_config t where t.report_id = " + reportId 
		+ " and t.fun_id = " + funId + " order by t.idx_id";
		Object[][] scalaries = { { "di", Hibernate.LONG }};
		List list = this.list(sql,null,scalaries);
		Iterator it = list.iterator();
		String returnStr[] = new String[list.size()];
		int i = 0;
		while(it.hasNext()){
			returnStr[i++] = ((Long)it.next()).toString();
		}
		return returnStr;
	}
	
	/**
	 * 是否出现上期结转的按钮
	 * @param reportId
	 * @return
	 */
	public boolean isLastTermDataCarry(String reportId,String funId){
		
		String sql = "select def_int as di from code_rep_config t where t.report_id = " + reportId + " and fun_id = " + funId;
		Object[][] scalaries = { { "di", Hibernate.LONG }};
		List list = this.list(sql,null,scalaries);
		boolean returnIs = false;
		if(list.size() == 0){
			returnIs = false;
		}else if(list.size() != 0){
			returnIs = true;
		}
		return returnIs;
	}

	
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.reportdefine.dao.ReportConfigDAO#getStatRepConfig(java.lang.String, java.lang.Long)
	 */
	public List getStatRepConfig(String repsId, Long conf) {
		Object [][] scalaries = {{"repId",Hibernate.LONG},{"Def_int",Hibernate.LONG},{"DICNAME",Hibernate.STRING}};		
		String sql = "SELECT r.report_id as repId,r.def_int as Def_int,dicname as DICNAME FROM code_rep_config r ,code_dictionary d "+
		"WHERE REPORT_ID IN ("+repsId+") AND FUN_ID = "+conf+" AND IDX_ID = 1 AND D.DICID = R.DEF_INT AND D.PARENTID = 1202 AND d.STATUS <> '9'";
		return this.list(sql,null,scalaries);		
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.reportdefine.dao.ReportConfigDAO#getRepConfig(java.lang.Long, java.lang.Long)
	 */
	public List getRepConfig(Long repId,Long conf) {
		Object [][] scalaries = {{"repId",Hibernate.LONG},{"idxId",Hibernate.LONG},{"defInt",Hibernate.LONG},{"defChar",Hibernate.STRING}};
		String sql = "SELECT C.REPORT_ID AS repId,C.IDX_ID AS idxId,C.DEF_INT AS defInt,C.DEF_CHAR AS defChar "+
					 "FROM CODE_REP_CONFIG C "+
					 "WHERE C.REPORT_ID = "+repId+" AND C.FUN_ID = "+conf+" ORDER BY C.IDX_ID";
		return this.list(sql,null,scalaries);
	}
	
	public void copyConfigs(Long newRepid, Long oldRepid){
		String Sql = null;
		if('s'==SysConfig.DB){
			Sql = "insert into code_rep_config(report_id, fun_id, idx_id," +
					"def_int, def_char, description)" +
					" select "+newRepid+", fun_id, idx_id," +
					"def_int, def_char, description " +
					"from code_rep_config where report_id="+oldRepid+" and fun_id not in(31,41)";
		}else{
			Sql = "insert into code_rep_config(pkid, report_id, fun_id, idx_id," +
					"def_int, def_char, description)" +
					" select "+DBDialect.genSequence("code_rep_config_seq")+","+newRepid+", fun_id, idx_id," +
					"def_int, def_char, description " +
					"from code_rep_config where report_id="+oldRepid+" and fun_id not in(31,41)";
		}
		jdbcUpdate(Sql,null);
	}

	public List getReportConfigsByFunc(Long funcId) {
		String hql = "from ReportConfig t where t.funId = " + funcId
		+ " order by t.idxId,t.funId";
		Map map = new HashMap();
		return list(hql,map);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.reportdefine.dao.ReportConfigDAO#removeReportConfig(java.lang.Long, java.lang.Long)
	 */
	public void removeReportConfig(Long repId, Long funId)
	{
		String sql = "delete from code_rep_config where report_id =" + repId +" and fun_id = " + funId;
		jdbcUpdate(sql, null);
	}
	public List<?> getdefChar(Long reportid, Long funid) {
		// TODO Auto-generated method stub
		String hql = "from ReportConfig t where t.reportId = " + reportid
				+ " and t.funId = "+ funid;
		Map map = new HashMap();
		return list(hql, map);
	} 
	
	public List < ReportConfig > getdefCharByTem(Long reportid, Long funid) {
		// TODO Auto-generated method stub
		String sql = "select {t.*} from code_rep_config t where t.report_Id in ( select te.model_id  " +
				"from  template_model te where te.template_id = "+reportid+" )" 
				+ " and t.fun_Id = "+ funid;
		return list(sql,new Object[][]{{"t", ReportConfig.class}}, null, null);
	}

	
	public List getRepConfigList() {
		String hql = "from ReportConfig t where 1=1 " 
				+ " order by t.reportId desc";
		Map map = new HashMap();
		return list(hql, map);
	}

	@Override
	public void saveConfig(ReportConfig config) {
		
		this.getHibernateTemplate().saveOrUpdate(config);
		
	}

	@Override
	public ReportConfig getReportConfig(Long pkid) {
		return (ReportConfig)this.getHibernateTemplate().get(ReportConfig.class, pkid);
	}

	@Override
	public void setReportConfig(ReportConfig reportConfig) {
		
		this.getHibernateTemplate().update(reportConfig);
		
	} 
	
}
