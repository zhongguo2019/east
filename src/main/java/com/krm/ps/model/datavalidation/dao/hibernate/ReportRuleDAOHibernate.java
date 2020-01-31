package com.krm.ps.model.datavalidation.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.datavalidation.dao.ReportRuleDAO;
import com.krm.ps.model.vo.ReportComType;
import com.krm.ps.sysmanage.reportdefine.vo.Report;

public class ReportRuleDAOHibernate extends BaseDAOHibernate implements
		ReportRuleDAO {

	@Override
	/**
	 * 获报表或都数据模型的类型
	 * @param systemcode  系统标识 （比如人行标准化和客户风险）
	 * @param showlevel   0  代表目标层模板;1  代表目标层模型;2  代表报送层模板  
	 * @return
	 */
	public List getReportType(Integer systemcode, Integer showlevel) {
		String hql = "from ReportType r where r.systemCode=" + systemcode
				+ " and r.showlevel=" + showlevel;
		return this.list(hql);
	}

	public List getDateOrganEditReportForStandard(String paramDate,
			Long userId, String systemId, String levelFlag) {
		String freqStr = "";
		StringBuffer hql = new StringBuffer(
				"select {r.*} from code_rep_report r ")
				.append("where r.status<>9 ");
		if (paramDate != null && !"".equals(paramDate)) {
			hql.append("and ").append("r.begin_date <= '").append(paramDate)
					.append("' and ").append("r.end_date >= '")
					.append(paramDate).append("'");
		}
		hql.append(
				" and r.pkid in (select u.repid from rep_oper_contrast u where u.operid = ")
				.append(userId).append(" ) ");
		if (systemId != null && !"".equals(systemId)) {
			hql = hql
					.append(" and r.report_type in (select t.pkid from code_rep_types t where t.system_code=")
					.append(systemId);
		}
		if (levelFlag != null && !"".equals(levelFlag)) {
			hql = hql.append(" and t.showlevel=").append(levelFlag)
					.append(" ) ");
		} else {
			hql = hql.append(" ) ");
		}
		hql = hql.append("  order by r.report_type,r.show_order");
		List ls = list(hql.toString(),
				new Object[][] { { "r", Report.class } }, null);

		return ls;
	}

	/**
	 * 获得基本规则类型
	 * 
	 * @return
	 */
	public List getBasicRuleType(String flag, String systemcode) {
		// String hql="from ReportComType r where r.pkid in (1,2)";
		String hql = "from ReportComType  r where r.flag='" + flag
				+ "' and r.systemcode='" + systemcode + "'";
		return this.list(hql);
	}

	/**
	 * 根据报表id，规则类型 获取报表对应的规则
	 * 
	 * @param reportList
	 * @param ruleType
	 * @return
	 */

	public List getReportRule(List reportList, List ruleType, String cstatus) {
		String reportid = "";
		String flag = "";
		for (int i = 0; i < reportList.size(); i++) {
			Report rt = (Report) reportList.get(i);
			reportid += "'" + rt.getPkid() + "'" + ",";
		}
		reportid = reportid.substring(0, reportid.length() - 1);
		for (int i = 0; i < ruleType.size(); i++) {
			ReportComType rt = (ReportComType) ruleType.get(i);
			flag += "'" + rt.getPkid() + "'" + ",";
		}
		flag = flag.substring(0, flag.length() - 1);
		String hql = "from ReportRule r where r.modelid in ( " + reportid
				+ ") and r.rtype in (" + flag + ") ";
		if (cstatus != null) {
			hql += "and r.cstatus='" + cstatus + "'";
		}
		return this.list(hql);
	}

	/**
	 * 根据机构、规则编码和校验状态 清洗结果
	 * 
	 * @param rulecode
	 * @param cstatus
	 */
	public int deleteReportResult(String rulecode, String organTree,
			String tablename,int idx,int isAdmin) {
		
		
		//支行删除待机构，总行校验删除所有的。
		if(isAdmin==2){
			String sql = "delete from " + tablename + " where RULECODE in ("
					+ rulecode + ")  and CSTATUS in ('0','2', '4','6','7')";
			return jdbcUpdate(sql, null);
		}else{
		
			String sql = "delete from " + tablename + " where RULECODE in ("
					+ rulecode + ")  and CSTATUS in ('0','2', '4','6','7') and organ_Id in ("+organTree+")";
			return jdbcUpdate(sql, null);
		}
		
	}

	/**
	 * 调用存储过程在程序内部校验
	 * 
	 * @param rulecode
	 */
	public Object[] checkData(String rulecode, String date,String organTree,int isAdmin,String userOrganid) {
		
			
			
		    if(isAdmin==2){
				String sql = "{CALL P_M_DORULE(?,?)}";
				int[] arr = { 1 };
				return jdbcCall(sql, new Object[] {rulecode, date}, null, arr, null);
			}else{
				
				String sql = "{CALL P_M_DORULE1(?,?,?,?)}";
				int[] arr = { 1 };
				return jdbcCall(sql, new Object[] {rulecode,date,organTree,userOrganid}, null, arr, null);
			}
			 
	}

	@Override
	public List getResultfull(String dataDate, String tabName) {
		    String sql="select t.tab_desc as tabdesc ,t.tab_name as tabname,t.except_desc as exceptdesc,t.data_date as datadate  from east_dq_check_result_full t  where 1=1 " ;
		       if (tabName!=null && !"".equals(tabName)) {
		    	   sql=sql   + " AND t.tab_name like '%"+tabName+"%' " ;
			   }
		       if (dataDate!=null && !"".equals(dataDate)) {
			 	sql=sql+" AND substr(T.DATA_DATE,1,6)='"+dataDate+"'";
			   }
		       
		       Object[][] scalaries = new Object[][] {
						{ "tabdesc", Hibernate.STRING },
						{ "tabname", Hibernate.STRING },
						{ "exceptdesc", Hibernate.STRING },
						{ "datadate", Hibernate.STRING }};
				return list(sql, null, scalaries);
				
	}

	public long getTotalRecord(String sql) {
		return getTotalRecord(sql, "total");
	}
	
	public long getTotalRecord(String sql, String totalColumn) {
		List list = getListOfMapFromSQL(sql);
		if (list.size() <= 0) {
			return 0;
		}
		Map totalMap = (Map) list.get(0);
		Integer total = Integer.valueOf(String.valueOf(
				totalMap.get(totalColumn)).trim());		
		if (total == null) {
			return 0;
		}
		return total.longValue();
	}

	@Override
	public int getRuleCheckProgress(String organId) {
		 String sql="select count(1) as total   from CODE_RULECHECKPROGRESS t  where ORGANID ='"+organId+"'" ;
		 int totalRecord = (int) getTotalRecord(sql.toString());
		 return totalRecord;
	}

	@Override
	public List getRuleCheckProgressList(String organId ,String reportid) {
		 String sql="select r.RULECODE as rulecode ,rp.NAME as tabname,r.TARGET_NAME as targetname,p.CREATEDATE as createdate from code_rep_report rp, code_rep_rule r left join CODE_RULECHECKPROGRESS p on r.RULECODE=p.RULECODE and ORGANID='"+organId+"'  where   rtype in (select pkid from code_com_type reportcomt0_ where reportcomt0_.FLAG='0' and reportcomt0_.systemcode='2') and rp.pkid=r.MODEL_ID  " ;
	       if (StringUtils.isNotBlank(reportid)) {
	    	   sql=sql   + " AND  MODEL_ID='" + reportid + "' " ;
		   }
	       
	       sql=sql   + " order by p.CREATEDATE ,rp.NAME  " ;
	       
	       Object[][] scalaries = new Object[][] {
					{ "rulecode", Hibernate.STRING },
					{ "tabname", Hibernate.STRING },
					{ "targetname", Hibernate.STRING },
					{ "createdate", Hibernate.STRING }};
		return list(sql, null, scalaries);
	}

	@Override
	public int deleteRuleCheckProgress(String organId) {
		String sql=" delete  CODE_RULECHECKPROGRESS  where ORGANID = '"+organId+"'";
		return jdbcUpdate(sql, null);
	}

	@Override
	public String selectOrganTreeSql(String organId, int isAdmin, int idx) {

		String sqlq = "select tt.SUBTREETAG ttlab from code_org_organ t,CODE_ORG_TREE tt where t.PKID=tt.NODEID and t.CODE='"
				+ organId + "' and  tt.IDXID=" + idx + "";
		List resultL = list(sqlq, null, new Object[][] { { "ttlab",
				Hibernate.STRING } });
		String organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
				+ resultL.get(0) + "%' ";
		return organTree;
	}

}
