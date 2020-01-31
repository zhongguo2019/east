package com.krm.ps.model.datafill.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.datafill.dao.ReportRuleDAO;
import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.util.SysConfig;

public class ReportRuleDAOHibernate extends BaseDAOHibernate implements
		ReportRuleDAO {

	public int insertReportService(List<ReportConfig> resultablename,
			String stFlag, Map temMap, List<ReportTarget> reportTargetList,
			List valueList, List cstatusList, String reportDate) {

		String sql = null;
		String sql1 = null;
		String rtable = "";
		String falg = "";
		for (int i = 0; i < reportTargetList.size(); i++) {
			ReportTarget tt = reportTargetList.get(i);
			for (ReportConfig rc : resultablename) {
				if (temMap.get(tt.getTargetField()).toString().split(";")[1]
						.equals(rc.getReportId().toString())) {
					rtable = rc.getDefChar();
					rtable = rtable.replaceAll("\\{M\\}",
							reportDate.substring(4, 6));
					rtable = rtable.replaceAll("\\{D\\}",
							reportDate.substring(6, 8));
					rtable = rtable.replaceAll("\\{Y\\}",
							reportDate.substring(0, 4));
				}
			}

		}

		sql = "insert into " + rtable + "( ";
		for (int i = 0; i < valueList.size(); i++) {
			falg += valueList.get(i) + ",";
		}
		falg += " PKID) values (";
		for (int i = 0; i < cstatusList.size(); i++) {
			falg += "'" + cstatusList.get(i) + "',";
		}

		sql += falg + " (SELECT nvl(MAX(pkid),0) + 1 from " + rtable + "))";

		return jdbcUpdate(sql, null);
	}

	public List<ReportResult> getReportResultByDataId(String cstatus,
			String[] idArr, String date, String tablename, String targetids) {
		List list = new ArrayList();
		if (idArr.length == 0) {
			return list;
		}
		StringBuffer sb = new StringBuffer("select {crr.*} from  ");
		sb.append(tablename);
		sb.append(" crr where crr.keyvalue in (");
		for (int i = 0; i < idArr.length; i++) {
			if (i == idArr.length - 1) {
				sb.append("?)");
			} else {
				sb.append("?,");
			}
		}
		if (cstatus != null) {
			sb.append(" and  crr.cstatus in(" + cstatus + ") ");
		}
		list = list(sb.toString(),
				new Object[][] { { "crr", ReportResult.class } }, null, idArr);
		return list;
	}

	public List getReportType(Integer systemcode, Integer showlevel) {
		String hql = "from ReportType r where r.systemCode=" + systemcode
				+ " and r.showlevel=" + showlevel;
		return this.list(hql);
	}

	public List getReport(List reportType) {
		String str = "";
		for (int i = 0; i < reportType.size(); i++) {
			ReportType rt = (ReportType) reportType.get(i);
			str += rt.getPkid() + ",";
		}
		str = str.substring(0, str.length() - 1);
		String hql = "from Report  r where r.reportType in(" + str + ")";
		return this.list(hql);
	}

	public int updateReportResult(List<ReportConfig> resultablename,
			String stFlag, Map temMap, List<ReportTarget> reportTargetList,
			List valueList, List cstatusList, List dtypeList,
			String[] repDataSort, String reportDate) {
		try {
			String[] sqls = new String[reportTargetList.size()];
 			String rtable = "";
 			for (int j = 0; j < repDataSort.length; j++) {    
 				List xvalue = (List) valueList.get(j);
  				List xdtype = (List) dtypeList.get(j);
 				List xcstatus = (List) cstatusList.get(j); 
				for (int i = 0; i < reportTargetList.size(); i++) {
					ReportTarget tt = reportTargetList.get(i);
 					for (ReportConfig rc : resultablename) {
						if (temMap.get(tt.getTargetField()).toString()
								.split(";")[1].equals(rc.getReportId()
								.toString())) {
							rtable = rc.getDefChar();
							rtable = rtable.replaceAll("\\{Y\\}",
									reportDate.substring(0, 4));
							rtable = rtable.replaceAll("\\{M\\}",
									reportDate.substring(4, 6));
							rtable = rtable.replaceAll("\\{D\\}",
									reportDate.substring(6, 8));
						}
					} 
  					if ("1".equals(stFlag)) {
						sqls[i] = "update "
								+ rtable
								+ " c set c.NEWVALUE ='"
								+ xvalue.get(i)
								+ "',c.CSTATUS='"
								+ xcstatus.get(i)
								+ "',c.D_TYPE='"
								+ xdtype.get(i)
								+ "' where c.KEYVALUE="
								+ Long.parseLong(repDataSort[j])
								+ " and c.TARGET_ID='"
								+ temMap.get(tt.getTargetField()).toString()
										.split(";")[0]
								+ "' and c.MODEL_ID='"
								+ temMap.get(tt.getTargetField()).toString()
										.split(";")[1] + "'";

					} else {
						sqls[i] = "update  "
								+ rtable
								+ " c set c.NEWVALUE ='"
								+ xvalue.get(i)
								+ "',c.CSTATUS='5',c.D_TYPE='"
								+ xdtype.get(i)
								+ "' where c.KEYVALUE="
								+ Long.parseLong(repDataSort[j])
								+ " and c.TARGET_ID='"
								+ temMap.get(tt.getTargetField()).toString()
										.split(";")[0]
								+ "' and c.MODEL_ID='"
								+ temMap.get(tt.getTargetField()).toString()
										.split(";")[1] + "'";
					}
				}
				batchJdbcUpdate(sqls);
			}
			return 1;
		} catch (Exception e) {
			return 0;
		}

	}

	public List getReportRuleBycode(String rulecode) {
		String hql = "from ReportRule r where r.rulecode in (" + rulecode + ")";
		return this.list(hql);
	}

	public int updateReport(List<ReportConfig> resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, List dtypeList, String[] repDataSort,
			String reportDate) {
		try {
			String[] sqls = new String[reportTargetList.size()];
			String rtable = "";
			for (int j = 0; j < repDataSort.length; j++) {
				List xvalue = (List) valueList.get(j);
				List xdtype = (List) dtypeList.get(j);
				List xcstatus = (List) cstatusList.get(j);
				for (int i = 0; i < reportTargetList.size(); i++) {
					ReportTarget tt = reportTargetList.get(i);
					for (ReportConfig rc : resultablename) {
						if (temMap.get(tt.getTargetField()).toString()
								.split(";")[1].equals(rc.getReportId()
								.toString())) {
							rtable = rc.getDefChar();
							rtable = rtable.replaceAll("\\{M\\}",
									reportDate.substring(4, 6));
							rtable = rtable.replaceAll("\\{D\\}",
									reportDate.substring(6, 8));
							rtable = rtable.replaceAll("\\{Y\\}",
									reportDate.substring(0, 4));
						}
					}
					sqls[i] = "update "
							+ rtable
							+ " c set c."
							+ temMap.get(tt.getTargetField()).toString()
									.split(";")[0] + " ='" + xvalue.get(i)
							+ "' where c.PKID="
							+ Long.parseLong(repDataSort[j]);
				}
				batchJdbcUpdate(sqls);
			}
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	// status的状态 0待补录 1待校验，2待提交,3待修订，4已提交 5 保存未提交 6为提交失败的数据 7 同步中
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getReportGuide(Long reportId, String levelFlag,
			String systemcode, String reportDate, String organId, int idxid,
			Long userId) {
		int orglab = 0;
		List orglabL = getOrganPkid(organId, idxid);
		if (orglabL.size() > 0) {
			orglab = (Integer) orglabL.get(0);
		}
		String lab = "";
		List labL = getOrganidx(organId, idxid);

		if (labL.size() > 0) {
			lab = (String) labL.get(0);
		}
		String organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
				+ lab + "%' ";
		String sql = "";
		reportDate = reportDate.replaceAll("-", "");
		if (reportId != null) {
			String hql = "from ReportConfig t where t.reportId = " + reportId
					+ " and t.funId = 34";
			ReportConfig rc = (ReportConfig) list(hql, new HashMap()).get(0);
			String targettable = rc.getDefChar().replaceAll("\\{M\\}",
					reportDate.substring(4, 6));
			targettable = targettable.replaceAll("\\{D\\}",
					reportDate.substring(6, 8));
			targettable = targettable.replaceAll("\\{Y\\}",
					reportDate.substring(0, 4));
			
			sql=getSqlDb2orOrcl1(reportId, organId, targettable, organTree, reportDate, orglab);
			System.out.println(reportId);
			Object[][] scalaries = new Object[][] {
					{ "reportId", Hibernate.STRING },
					{ "organId", Hibernate.STRING },
					{ "tCount", Hibernate.STRING },
					{ "reportName", Hibernate.STRING },
					{ "targetTable", Hibernate.STRING },
					{ "wCount", Hibernate.STRING },
					{ "status", Hibernate.STRING },
					{ "yCount", Hibernate.STRING },
					{ "mCount", Hibernate.STRING },
					{ "iCount", Hibernate.STRING },
					{ "xCount", Hibernate.STRING }};
			return list(sql, null, scalaries);
		} else {
			List<Report> tempList = getDateOrganEditReportForStandard(
					reportDate, userId, systemcode, levelFlag);
			String tempid = "";
			List resultList = new ArrayList();
			for (Report rt : tempList) {
				String hql = "from ReportConfig t where t.reportId = "
						+ rt.getPkid() + " and t.funId = 34";
				ReportConfig rc = (ReportConfig) list(hql, new HashMap()).get(0);
				String targettable = rc.getDefChar().replaceAll("\\{M\\}",
						reportDate.substring(4, 6));
				targettable = targettable.replaceAll("\\{D\\}",
						reportDate.substring(6, 8));
				targettable = targettable.replaceAll("\\{Y\\}",
						reportDate.substring(0, 4));
				
				sql=getSqlDb2orOrcl2(rt, organId, targettable, organTree, reportDate, orglab);
				
				Object[][] scalaries = new Object[][] {
						{ "reportId", Hibernate.STRING },
						{ "organId", Hibernate.STRING },
						{ "tCount", Hibernate.STRING },
						{ "reportName", Hibernate.STRING },
						{ "targetTable", Hibernate.STRING },
						{ "wCount", Hibernate.STRING },
						{ "status", Hibernate.STRING },
						{ "yCount", Hibernate.STRING },
						{ "mCount", Hibernate.STRING },
						{ "iCount", Hibernate.STRING },
						{ "xCount", Hibernate.STRING }};
				List relist = list(sql, null, scalaries);
				if (relist != null) {
					for (int p = 0; p < relist.size(); p++) {
						resultList.add(relist.get(p));
					}
				}

			}
			return resultList;
		}

	}
    @SuppressWarnings("unused")
	  private String getSqlDb2orOrcl1 (Long reportId,String organId,String targettable ,String organTree,String reportDate,int orglab){
   	           String sql  ="select "
   	        				+ reportId
   	        				+ " as reportId,'"
   	        				+ organId
   	        				+ "' as organId,(select count(distinct(keyvalue)) from "
   	        				+ targettable
   	        				+ " where organ_id in ("
   	        				+ organTree
   	        				+ ") and cstatus in ('0','3','5') and substr(etldate,1,6)='"
   	        				+ reportDate.substring(0, 6)
   	        				+ "') as tCount ,"
   	        				+ "(select count(distinct(keyvalue)) from "
   	        				+ targettable
   	        				+ " where organ_id in ("
   	        				+ organTree
   	        				+ ") and cstatus in ('2') and substr(etldate,1,6)='"
   	        				+ reportDate.substring(0, 6)
   	        				+ "') as yCount ,"
   	        				+ "(select /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
   	        				+ targettable
   	        				+ " where organ_id in ("
   	        				+ organTree
   	        				+ ") and cstatus in ('2','0','3','5') and substr(etldate,1,6)='"
   	        				+ reportDate.substring(0, 6)
   	        				+ "') as mCount ,"
   	        				+ "(select /*+ parallel(dual,4) */  count(distinct(keyvalue)) from "
   	        				+ targettable
   	        				+ " where organ_id in ("
   	        				+ organTree
   	        				+ ") and cstatus in ('4') and substr(etldate,1,6)='"
   	        				+ reportDate.substring(0, 6)
   	        				+ "') as iCount ,"
   	        				+ "(select /*+ parallel(dual,4) */  count(distinct(keyvalue)) from "
   	        				+ targettable
   	        				+ " where organ_id in ("
   	        				+ organTree
   	        				+ ") and cstatus in ('6','7') and substr(etldate,1,6)='"
   	        				+ reportDate.substring(0, 6)
   	        				+ "') as xCount ,"
   	        				+ "t.name as reportName ,'"
   	        				+ targettable
   	        				+ "' as targetTable, "
   	        				+ " (select count(pkid) from CODE_ORG_TREE where parentId="
   	        				+ orglab
   	        				+ " ) AS wCount ,(select /*+ parallel(dual,4) */ count(1) from CODE_REP_STATUS where REPORT_ID ='"
   	        				+ reportId + "' and ORGAN_ID = '" + organId
   	        				+ "' and FREQUENCY ='" + reportDate.substring(0, 6)
   	        				+ "00' and ISLOCK = '1') AS status from code_rep_report t where t.pkid="
   	        				+ reportId;
   	        	
      	return sql;
   	
   }
    @SuppressWarnings("unused")
	private String getSqlDb2orOrcl2 (Report rt,String organId,String targettable ,String organTree,String reportDate,int orglab){
             String  db  = SysConfig.DATABASE;
             String sql  = null;
			if("oracle".equalsIgnoreCase(db)) {
				 sql = "select "
						+ rt.getPkid()
						+ " as reportId,'"
						+ organId
						+ "' as organId,(select /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('0','3','5')and substr(etldate,1,6)='"
						+ reportDate.substring(0, 6)
						+ "') as tCount ,"
						+ "(select /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('2')and substr(etldate,1,6)='"
						+ reportDate.substring(0, 6)
						+ "') as yCount ,"
						+ "(select /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('2','0','3','5') and substr(etldate,1,6)='"
						+ reportDate.substring(0, 6)
						+ "') as mCount ,"
						+ "(select /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('4') and substr(etldate,1,6)='"
						+ reportDate.substring(0, 6)
						+ "') as iCount ,"
						+ "(select /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('6','7') and substr(etldate,1,6)='"
						+ reportDate.substring(0, 6)
						+ "') as xCount ,"
						+ "'"
						+ rt.getName()
						+ "' as reportName ,'"
						+ targettable
						+ "' as targetTable , "
						+ " (select /*+ parallel(dual,4) */ count(pkid) from CODE_ORG_TREE where parentId="
						+ orglab
						+ " ) AS wCount, (select /*+ parallel(dual,4) */ count(1) from CODE_REP_STATUS where REPORT_ID ='"
						+ rt.getPkid() + "' and ORGAN_ID = '" + organId
						+ "' and FREQUENCY ='" + reportDate.substring(0, 6)
						+ "00' and ISLOCK = '1') AS status from dual";
			}else if("db2".equalsIgnoreCase(db)){
				sql = "select "
						+ rt.getPkid()
						+ " as reportId,'"
						+ organId
						+ "' as organId,(select count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('0','3','5')and substr(etldate,1,6)='"
						+ reportDate.substring(0, 6)
						+ "') as tCount ,"
						+ "(select  count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('2')and substr(etldate,1,6)='"
						+ reportDate.substring(0, 6)
						+ "') as yCount ,"
						+ "(select  count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('2','0','3','5') and substr(etldate,1,6)='"
						+ reportDate.substring(0, 6)
						+ "') as mCount ,"
						+ "(select  count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('4') and substr(etldate,1,6)='"
						+ reportDate.substring(0, 6)
						+ "') as iCount ,"
						+ "(select  count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('6','7') and substr(etldate,1,6)='"
						+ reportDate.substring(0, 6)
						+ "') as xCount ,"
						+ "'"
						+ rt.getName()
						+ "' as reportName ,'"
						+ targettable
						+ "' as targetTable , "
						+ " (select  count(pkid) from CODE_ORG_TREE where parentId="
						+ orglab
						+ " ) AS wCount, (select  count(1) from CODE_REP_STATUS where REPORT_ID ='"
						+ rt.getPkid() + "' and ORGAN_ID = '" + organId
						+ "' and FREQUENCY ='" + reportDate.substring(0, 6)
						+ "00' and ISLOCK = '1') AS status from   sysibm.sysdummy1";
			}
		    	return sql.trim();
		    	
	}
    
	public List getOrganPkid(String organcode, int idx) {
		String sql = "select t.pkid ttlab from code_org_organ t where  t.CODE='"
				+ organcode + "'";
		List resultL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.INTEGER } });
		return resultL;
	}

	public List getOrganidx(String organcode, int idx) {
		String sql = "select tt.SUBTREETAG ttlab from code_org_organ t,CODE_ORG_TREE tt where t.PKID=tt.NODEID and t.CODE='"
				+ organcode + "' and  tt.IDXID=" + idx + "";
		List resultL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.STRING } });
		return resultL;
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

	public List getReportGuideDetail(Long reportId, String targettable,
			String organId, int idxid, String reportDate) {
		String hql = "from Report t where t.pkid = " + reportId;
		Report rc = (Report) list(hql, new HashMap()).get(0);
		int orglab = 0;
		List orglabL = getOrganPkid(organId, idxid);
		if (orglabL.size() > 0) {
			orglab = (Integer) orglabL.get(0);
		}
		reportDate = reportDate.replaceAll("-", "");
		String organhql = "select new OrganInfo(tr) from OrganInfo tr,OrganTreeNode tt where tr.pkid=tt.nodeId  and tt.parentId="
				+ orglab;
		List<OrganInfo> ls = list(organhql, new HashMap());
		List resultList = new ArrayList();
		for (OrganInfo oi : ls) {
			String lab = "";
			List labL = getOrganidx(oi.getCode(), idxid);
			if (labL.size() > 0) {
				lab = (String) labL.get(0);
			}
			String organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
					+ lab + "%' ";
			
			String sql =getSqlDb2orOrcl3(rc,oi, organId, targettable, organTree, reportDate, orglab);
			
			Object[][] scalaries = new Object[][] {
					{ "reportId", Hibernate.STRING },
					{ "organId", Hibernate.STRING },
					{ "reportName", Hibernate.STRING },
					{ "organName", Hibernate.STRING },
					{ "tCount", Hibernate.STRING },
					{ "targetTable", Hibernate.STRING },
					{ "wCount", Hibernate.STRING },
					{ "yCount", Hibernate.STRING } };
			List relist = list(sql, null, scalaries);
			if (relist != null) {
				for (int p = 0; p < relist.size(); p++) {
					Object[] obj = (Object[]) relist.get(p);
					if (Integer.parseInt(obj[4].toString()) != 0 || Integer.parseInt(obj[7].toString()) != 0) {
						resultList.add(relist.get(p));
					}
				}
			}
		}
		return resultList;

	}
    
	@SuppressWarnings("unused")
	private String getSqlDb2orOrcl3(Report rc,OrganInfo oi,String organId,String targettable ,String organTree,String reportDate,int orglab){
        String  db  = SysConfig.DATABASE;
        String sql  = null;
		if("oracle".equalsIgnoreCase(db)) {
			    sql = "select "
					+ rc.getPkid()
					+ " as reportId,'"
					+ oi.getCode()
					+ "' as organId,'"
					+ rc.getName()
					+ "' as reportName, "
					+ "'"
					+ oi.getFull_name()
					+ "' as organName,(select  /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
					+ targettable
					+ " "
					+ " where organ_id in ("
					+ organTree
					+ ") and cstatus in ('0','3','5')and substr(etldate,1,6)='"
					+ reportDate.substring(0, 6)
					+ "') as tCount ,"
					+ "(select  /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
					+ targettable
					+ " "
					+ " where organ_id in ("
					+ organTree
					+ ") and cstatus in ('2')and substr(etldate,1,6)='"
					+ reportDate.substring(0, 6)
					+ "') as yCount ,'"
					+ targettable
					+ "' as "
					+ " targetTable ,(select  /*+ parallel(dual,4) */ count(pkid) from CODE_ORG_TREE  where parentId="
					+ oi.getPkid() + " ) AS wCount from dual";
		}else if("db2".equalsIgnoreCase(db)){
			    sql = "select "
					+ rc.getPkid()
					+ " as reportId,'"
					+ oi.getCode()
					+ "' as organId,'"
					+ rc.getName()
					+ "' as reportName, "
					+ "'"
					+ oi.getFull_name()
					+ "' as organName,(select  count(distinct(keyvalue)) from "
					+ targettable
					+ " "
					+ " where organ_id in ("
					+ organTree
					+ ") and cstatus in ('0','3','5')and substr(etldate,1,6)='"
					+ reportDate.substring(0, 6)
					+ "') as tCount ,"
					+ "(select  count(distinct(keyvalue)) from "
					+ targettable
					+ " "
					+ " where organ_id in ("
					+ organTree
					+ ") and cstatus in ('2')and substr(etldate,1,6)='"
					+ reportDate.substring(0, 6)
					+ "') as yCount ,'"
					+ targettable
					+ "' as "
					+ " targetTable ,(select  count(pkid) from CODE_ORG_TREE  where parentId="
					+ oi.getPkid() + " ) AS wCount from sysibm.sysdummy1";			
			
		}
	    	return sql.trim();
	    	
}
	
	
	public int deleteReportResult(String rulecode, String cstatus,
			String tablename) {
		String sql = "delete from " + tablename + " where RULECODE in ("
				+ rulecode + ") and CSTATUS='" + cstatus + "'";
		return jdbcUpdate(sql, null);
	}

	public Object[] checkData(String rulecode, String date) {
		String sql = "{CALL P_M_DORULE(?,?)}";
		int[] arr = { 1 };

		return jdbcCall(sql, new Object[] { rulecode, date }, null, arr, null);
	}

	@Override
	public List getRulecode(String reportid) {
		String sql = "select t.rulecode ttlab from code_rep_rule t where model_id="
				+ reportid + " and t.cstatus=0";
		List RulecodeL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.STRING } });
		return RulecodeL;
	}

	@Override
	public int getReportnum(String tergettbale, String dataDate) {
		String sql = "select pkid ttlab from " + tergettbale
				+ " t where substr(t.etldate,1,6)=" + dataDate
				+ " and cstatus not in(4)";
		List RulecodeL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.INTEGER } });
		return RulecodeL.size();
	}

	@Override
	public List getDateOrganEditReport(String replaceAll, int paramOrganType,
			int i, Long userId, String systemId, String levelFlag) {
		String freqStr = "";
		// 2006.12.4查询速度优化：本表条件、数据量由大到小表限制条件
		StringBuffer hql = new StringBuffer(
				"select {r.*} from code_rep_report r ")
				.append("where r.status<>9 ");
		if (replaceAll != null && !"".equals(replaceAll)) {
			hql.append("and ").append("r.begin_date <= '").append(replaceAll)
					.append("' and ").append("r.end_date >= '")
					.append(replaceAll).append("'");
		}
		// if("0".equals(levelFlag)){
		hql.append(
				" and r.pkid in (select u.repid from rep_oper_contrast u where u.operid = ")
				.append(userId).append(" ) ");
		// }

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

}
