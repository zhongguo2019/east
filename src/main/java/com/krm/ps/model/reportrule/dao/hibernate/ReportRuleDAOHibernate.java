package com.krm.ps.model.reportrule.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.reportrule.dao.ReportRuleDAO;
import com.krm.ps.model.vo.Page;
import com.krm.ps.model.vo.PaginatedListHelper;
import com.krm.ps.model.vo.ReportComType;
import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.util.DateUtil;

public class ReportRuleDAOHibernate extends BaseDAOHibernate implements
		ReportRuleDAO {

	/**
	 * 获报表或都数据模型的类型
	 * 
	 * @param systemcode
	 *            系统标识 （比如人行标准化和客户风险）
	 * @param showlevel
	 *            0 代表目标层模板;1 代表目标层模型;2 代表报送层模板
	 * @return
	 */
	public List getReportType(Integer systemcode, Integer showlevel) {
		String hql = "from ReportType r where r.systemCode=" + systemcode
				+ " and r.showlevel=" + showlevel;
		return this.list(hql);
	}

	/**
	 * 获得基本规则类型
	 * 
	 * @return
	 */
	public List getBasicRuleType(String flag, String systemcode) {
		String hql = "from ReportComType  r where r.flag='" + flag
				+ "' and r.systemcode='" + systemcode + "'";
		return this.list(hql);
	}

	/**
	 * 根据规则类型获得规则名称
	 * 
	 * @return
	 */
	public List getRuleType(String pkids) {
		String[] pkidstr = pkids.split("\\,");
		if ("alloption".equals(pkidstr[0])) {
			pkids = pkidstr[1];
			for (int i = 2; i < pkidstr.length; i++) {
				pkids += "," + pkidstr[i];
			}
		}
		String hql = "from ReportComType r where r.pkid in (" + pkids + ")";
		return this.list(hql);
	}

	/**
	 * 获得某一类型下的所有报表
	 * 
	 * @param reportType
	 *            报表类型
	 * @return
	 */
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

	/**
	 * 根据报表id获得校验规则
	 * 
	 * @param reportid
	 * @return
	 */
	public List getReportRuleByReportId(String reportid) {
		String hql = "from ReportRule r where r.modelid='" + reportid + "'";
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
	 * 根据模型id，指标id获取规则
	 * 
	 * @param modelid
	 * @param targetid
	 * @return
	 */
	public List getReportRule(String modelid, String targetid, String rtype) {
		String hql = "from ReportRule r where r.modelid ='" + modelid
				+ "' and r.targetid ='" + targetid + "' and r.rtype='" + rtype
				+ "'";
		return this.list(hql);
	}

	/**
	 * 保存校验规则
	 * 
	 * @param
	 * @return
	 */
	public boolean saveReportRule(ReportRule reportrule) {
		try {
			this.saveObject(reportrule);
			return true;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			return false;
		}
	}

	/**
	 * 调用存储过程在程序内部校验
	 * 
	 * @param rulecode
	 */
	public Object[] checkData(String rulecode, String date) {
		String sql = "{CALL P_M_DORULE(?,?)}";
		int[] arr = { 1 };

		return jdbcCall(sql, new Object[] { rulecode, date }, null, arr, null);
	}

	/**
	 * 风险预警层调用的存储过程
	 */
	public Object[] checkData(String rulecode, String date, String lastdate) {
		String sql = "{CALL CUSTRISK.P_W_DORULE(?,?,?)}";
		int[] arr = { 1 };

		return jdbcCall(sql, new Object[] { rulecode, date, lastdate }, null,
				arr, null);
	}

	/**
	 * 根据模型id获得规则
	 * 
	 * @param modelid
	 * @return
	 */
	public List getReportRule(String modelid) {
		String hql = "from ReportRule r where r.modelid ='" + modelid + "'";
		return this.list(hql);
	}

	/**
	 * 根据系统标志取出规则列表 0代表人行标准化1代表客户风险
	 * 
	 * @param tg
	 * @return
	 */
	public List getReportRuleByTg(String tg, String flag) {
		String hql = "from ReportRule r where r.tg ='" + tg + "' and r.flag='"
				+ flag + "'";
		return this.list(hql);
	}

	/**
	 * 根据规则编码获得规则列表
	 * 
	 * @param modelid
	 * @return
	 */
	public List getReportRuleBycode(String rulecode) {
		String hql = "from ReportRule r where r.rulecode in (" + rulecode + ")";
		return this.list(hql);
	}

	/**
	 * 根据模型id和flag获得规则,其中flag0为人行,1为客户风险，2为风险预警
	 * 
	 * @param modelid
	 * @return
	 */
	public List getReportRuleFlag(String modelid, String flag) {
		String hql = "from ReportRule r where r.modelid in (" + modelid
				+ ") and r.flag='" + flag + "'";
		return this.list(hql);
	}

	/**
	 * 获得校验结果
	 * 
	 * @param date
	 *            日期
	 * @param modelid
	 *            模型id
	 * @param ruletype
	 *            规则类型
	 * @param organId
	 *            机构id
	 * @return
	 */
	public List getReportResult(String date, String modelid, String ruletype,
			List organIdList, String tablename) {
		String organStr = "";
		for (int i = 0; i < organIdList.size(); i++) {
			organStr += "'" + organIdList.get(i) + "',";
		}
		organStr = organStr.substring(0, organStr.length() - 1);
		String sql = "SELECT {r.*} FROM "
				+ tablename
				+ " r WHERE r.ETLDATE = ? AND r.MODEL_ID = ? AND r.RTYPE = ? and r.ORGAN_ID  in("
				+ organStr + ") ";
		return list(sql, new Object[][] { { "r", ReportResult.class } }, null,
				new Object[] { date, modelid, ruletype });
	}

	/**
	 * 获得报表对象
	 * 
	 * @param date
	 *            日期
	 * @param modelid
	 *            模型id
	 * @param ruletype
	 *            规则类型
	 * @param organId
	 *            机构id
	 * @return
	 */
	public List getReport(String reportids) {
		// String sql =
		// "SELECT {r.*} FROM CODE_REP_REPORT r  WHERE r.PKID in (select t.MODEL_ID from TEMPLATE_MODEL t where t.TEMPLATE_ID in ("+reportids+")) ";
		String sql = "SELECT {r.*} FROM CODE_REP_REPORT r  WHERE r.PKID in ("
				+ reportids + ")";
		return list(sql, new Object[][] { { "r", Report.class } }, null, null);
	}

	/**
	 * 根据日期，规则编码查询校验结果
	 * 
	 * @param rulecode
	 * @param date
	 * @return
	 */
	public List getReportResult(String rulecode, String date, String tablename) {
		String sql = "SELECT {r.*} FROM " + tablename
				+ " r WHERE r.ETLDATE = ? AND r.rulecode = ? AND r.CSTATUS= ? ";
		return list(sql, new Object[][] { { "r", ReportResult.class } }, null,
				new Object[] { date, rulecode, "0" });
	}

	/**
	 * 测试sql语句是否正确
	 * 
	 * @param sql
	 * @return
	 */
	public boolean testSql(String sql) {
		try {
			Object[][] obj = new Object[][] { { "num", Hibernate.INTEGER } };
			list(sql, null, obj);
			return true;
		} catch (RuntimeException re) {
			return false;
		}
	}

	/**
	 * 测试规则是不是有要预警生成的数据
	 * 
	 * @param sql
	 * @return
	 */
	public List getNum(String sql) {
		try {
			Object[][] obj = new Object[][] { { "num", Hibernate.INTEGER } };
			return list(sql, null, obj);
		} catch (RuntimeException re) {
			return null;
		}

	}

	/**
	 * 获取数据记录并且插入
	 * 
	 * @param relist
	 * @param datatablename
	 * @return
	 */
	public int getReportData(Set set, String datatablename) {
		String idstr = "";
		String tempdatatable = datatablename.substring(0,
				datatablename.length() - 1)
				+ "5";
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			idstr += iterator.next() + ",";
		}
		if (!"".equals(idstr)) {
			idstr = idstr.substring(0, idstr.length() - 1);
			String sql = "insert into CUSTRISK." + tempdatatable
					+ " ( SELECT * FROM CUSTRISK." + datatablename
					+ " r  WHERE r.PKID  in (" + idstr
					+ ") and r.PKID not in (select td.PKID from CUSTRISK."
					+ tempdatatable + " td where td.PKID in (" + idstr + ")))";
			return jdbcUpdate(sql, null);
		}
		return 0;
	}

	/**
	 * 获取数据记录并且插入,用一个sql
	 * 
	 * @param relist
	 * @param datatablename
	 * @return
	 */
	public int getReportData(String rulecode, String dataDate,
			String targettable, String datatablename) {
		String tempdatatable = datatablename.substring(0,
				datatablename.length() - 1)
				+ "5";
		String sql = "insert into CUSTRISK."
				+ tempdatatable
				+ " ( SELECT * FROM CUSTRISK."
				+ datatablename
				+ " r  WHERE r.PKID  in (SELECT distinct(tar.KEYVALUE) FROM CUSTRISK."
				+ targettable
				+ " tar WHERE tar.ETLDATE = '"
				+ dataDate
				+ "' AND tar.rulecode = '"
				+ rulecode
				+ "' AND tar.CSTATUS= '0') and r.PKID not in (select td.PKID from CUSTRISK."
				+ tempdatatable
				+ " td where td.PKID in (SELECT distinct(tar.KEYVALUE) FROM CUSTRISK."
				+ targettable + " tar WHERE tar.ETLDATE = '" + dataDate
				+ "' AND tar.rulecode = '" + rulecode
				+ "' AND tar.CSTATUS= '0')))";
		return jdbcUpdate(sql, null);
	}

	/**
	 * 保存数据
	 * 
	 * @param datalist
	 * @param datatablename
	 * @return
	 */
	public List saveReportData(List datalist, String datatablename) {
		return null;
	}

	@Override
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
			sb.append(" and  crr.cstatus in(" + cstatus + ") "); // 待补录，待修订
		}
		/**
		 * if(targetids!=null){ sb.append(
		 * "and crr.TARGET_ID in ("+targetids+")");
		 * //此处是为了，当规则比补录模板字段多时，出现提交不了的情况 //但是青海出了问题，先注释掉了 }
		 */
		list = list(sb.toString(),
				new Object[][] { { "crr", ReportResult.class } }, null, idArr);
		return list;
	}

	@Override
	public PaginatedListHelper getReportResultByPage(String date,
			String modelid, String ruletype, List organIdList,
			String tablename, Page page) {
		String organStr = "";
		for (int i = 0; i < organIdList.size(); i++) {
			organStr += "'" + organIdList.get(i) + "',";
		}
		organStr = organStr.substring(0, organStr.length() - 1);
		String sql = "SELECT {r.*} FROM "
				+ tablename
				+ " r WHERE  r.MODEL_ID = ? AND r.RTYPE = ? and r.ORGAN_ID  in ("
				+ organStr + ") ";
		// 全部
		String[] ruletypes = ruletype.split("\\,");
		if ("alloption".equals(ruletypes[0])) {
			String ruletypestr = "'" + ruletypes[1] + "'";
			for (int i = 2; i < ruletypes.length; i++) {
				ruletypestr += "," + "'" + ruletypes[i] + "'";
			}
			sql = "SELECT {r.*} FROM " + tablename
					+ " r WHERE  r.MODEL_ID = ? AND r.RTYPE in (" + ruletypestr
					+ ") and r.ORGAN_ID  in (" + organStr + ") ";
		}

		if (null != date) {
			sql += "and r.ETLDATE = '" + date + "'";
		}

		String totalsql = "select count(*) as total  FROM "
				+ tablename
				+ " r WHERE  r.MODEL_ID = ? AND r.RTYPE = ? and r.ORGAN_ID  in("
				+ organStr + ") ";
		// 全部
		if ("alloption".equals(ruletypes[0])) {
			String ruletypestr = "'" + ruletypes[1] + "'";
			for (int i = 2; i < ruletypes.length; i++) {
				ruletypestr += "," + "'" + ruletypes[i] + "'";
			}
			totalsql = "select count(*) as total  FROM " + tablename
					+ " r WHERE  r.MODEL_ID = ? AND r.RTYPE in(" + ruletypestr
					+ ") and r.ORGAN_ID  in(" + organStr + ") ";
		}
		if (null != date) {
			totalsql = "and r.ETLDATE = '" + date + "' ";
		}

		int totalRecord;
		if ("alloption".equals(ruletypes[0])) {
			totalRecord = (int) getTotalRecord(totalsql,
					new Object[] { modelid });
		} else {
			totalRecord = (int) getTotalRecord(totalsql, new Object[] {
					modelid, ruletype });
		}

		page.setTotalRecord(totalRecord);
		page.setRecordNo((page.getPageNo() - 1) * page.getPageSize());

		if ("alloption".equals(ruletypes[0])) {
			List list = page(sql,
					new Object[][] { { "r", ReportResult.class } }, null,
					new Object[] { modelid }, page.getPageNo(),
					page.getPageSize());
			return new PaginatedListHelper(list, page);
		}
		List list = page(sql, new Object[][] { { "r", ReportResult.class } },
				null, new Object[] { modelid, ruletype }, page.getPageNo(),
				page.getPageSize());
		return new PaginatedListHelper(list, page);
	}

	public long getTotalRecord(String sql, Object[] value) {
		List list = list(sql, null,
				new Object[][] { { "total", Hibernate.LONG } }, value);
		return (Long) list.get(0);
	}

	/**
	 * 根据模型id获得模板id
	 * 
	 * @param pkid
	 * @return
	 */
	public List getTemplateByModelid(Long pkid, String targetid) {
		String hql = "from TemplateModel r where r.modelid =" + pkid
				+ " and  r.modelTarget='" + targetid + "'";
		return this.list(hql);
	}

	/**
	 * 删除规则
	 * 
	 * @param rr
	 */
	public void deleteReportRule(ReportRule rr) {
		getHibernateTemplate().delete(rr);
	}

	// 获得机构树的IDx标识
	public List getOrganidx(String organcode, int idx) {
		String sql = "select tt.SUBTREETAG ttlab from code_org_organ t,CODE_ORG_TREE tt where t.PKID=tt.NODEID and t.CODE='"
				+ organcode + "' and  tt.IDXID=" + idx + "";
		List resultL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.STRING } });
		return resultL;
	}

	// 获得机构树父id
	public List getOrganPkid(String organcode, int idx) {
		String sql = "select t.pkid ttlab from code_org_organ t where  t.CODE='"
				+ organcode + "'";
		List resultL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.INTEGER } });
		return resultL;
	}

	@Override
	public List getReport(Long reportId, List reportType, String reportDate,
			String organId, int idxid, Long userid, String levelFlag,
			String systemcode) {
		List<Object[]> allList = new ArrayList<Object[]>();
		String lab = "";
		List labL = getOrganidx(organId, idxid);

		if (labL.size() > 0) {
			lab = (String) labL.get(0);
		}
		String organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
				+ lab + "%' ";
		String str = "";
		for (int i = 0; i < reportType.size(); i++) {
			ReportType rt = (ReportType) reportType.get(i);
			str += rt.getPkid() + ",";
		}
		str = str.substring(0, str.length() - 1);
		String tempTable = "rep_dataf_" + reportDate.substring(5, 7) + "_5";
		// String resultname="CODE_REP_RESULT_T_"+reportDate.substring(5, 7);
		String logtable = "LOG_DATA_" + reportDate.substring(5, 7);
		reportDate = DateUtil.formatDate(reportDate);
		String sql = "";
		String titiaosql = ""; // 已提交的数据
		String weititiaosql = ""; // 未提交的数据
		if (reportId != null) {
			List resultList = new ArrayList();
			String configsql = "select {t.*} from code_rep_config t where t.report_Id in ( select te.model_id  "
					+ "from  template_model te where te.template_id in ("
					+ str
					+ " ))" + " and t.fun_Id = 34 ";
			List<ReportConfig> reportconfigList = list(configsql,
					new Object[][] { { "t", ReportConfig.class } }, null, null);
			// for(ReportConfig rc:reportconfigList){
			ReportConfig rc = reportconfigList.get(0);
			reportDate = reportDate.replaceAll("-", "");
			String targettable = rc.getDefChar().replaceAll("\\{M\\}",
					reportDate.substring(4, 6));
			targettable = targettable.replaceAll("\\{D\\}",
					reportDate.substring(6, 8));
			sql = "select r.PKID as pkid,r.REPORT_TYPE as reportType,r.NAME as reportName,(select count(*) from "
					+ targettable
					+ " mm "
					+ " where mm.ORGAN_ID in ("
					+ organTree
					+ ") and mm.MODEL_ID ='"
					+ rc.getReportId()
					+ "'"
					+ " and mm.MODEL_ID not in (select ll.ID_REP from "
					+ logtable
					+ " ll where  ll.MK_TYPE='1'  and ll.status='1' ) and mm.CSTATUS ='4') as tCount "
					+ ",(select count(*) from "
					+ targettable
					+ " rr "
					+ " where rr.ORGAN_ID in ("
					+ organTree
					+ ") and rr.MODEL_ID ='"
					+ rc.getReportId()
					+ "'"
					+ " and rr.MODEL_ID not in (select ll.ID_REP from "
					+ logtable
					+ " ll where ll.MK_TYPE='1'  and ll.status='1' ) and rr.CSTATUS in('0','1','2','3')) as wCount "
					+ " from CODE_REP_REPORT r where r.pkid in (" + str + ")";
			Object[][] scalaries = new Object[][] {
					{ "pkid", Hibernate.STRING },
					{ "reportType", Hibernate.STRING },
					{ "reportName", Hibernate.STRING },
					{ "tCount", Hibernate.STRING },
					{ "wCount", Hibernate.STRING } };
			List relist = list(sql, null, scalaries);
			for (int p = 0; p < relist.size(); p++) {
				resultList.add(relist.get(p));
			}
			// }
			return resultList;
		} else {
			List<Report> tempList = getDateOrganEditReportForStandard(
					reportDate.replace("-", ""), userid, systemcode, levelFlag);// 获得报送模板列表
			String tempid = "";
			List resultList = new ArrayList();
			for (Report rt : tempList) {
				String configsql = "select {t.*} from code_rep_config t where t.report_Id in ( select te.model_id  "
						+ "from  template_model te where te.template_id = "
						+ rt.getPkid() + " )" + " and t.fun_Id = 34 ";
				List<ReportConfig> reportconfigList = list(configsql,
						new Object[][] { { "t", ReportConfig.class } }, null,
						null);
				if (reportconfigList != null) {
					ReportConfig rc = reportconfigList.get(0);
					// for(ReportConfig rc:reportconfigList){
					reportDate = reportDate.replaceAll("-", "");
					String targettable = rc.getDefChar().replaceAll("\\{M\\}",
							reportDate.substring(4, 6));
					targettable = targettable.replaceAll("\\{D\\}",
							reportDate.substring(6, 8));

					sql = "select r.PKID as pkid,r.REPORT_TYPE as reportType,r.NAME as reportName,(select count(*) from "
							+ targettable
							+ " mm "
							+ " where mm.ORGAN_ID in ("
							+ organTree
							+ ") and mm.MODEL_ID ='"
							+ rc.getReportId()
							+ "'"
							+ " and mm.MODEL_ID not in (select ll.ID_REP from "
							+ logtable
							+ " ll where  ll.MK_TYPE='1'  and ll.status='1' ) and mm.CSTATUS ='4') as tCount "
							+ ",(select count(*) from "
							+ targettable
							+ " rr "
							+ " where rr.ORGAN_ID in ("
							+ organTree
							+ ") and rr.MODEL_ID ='"
							+ rc.getReportId()
							+ "'"
							+ " and rr.MODEL_ID not in (select ll.ID_REP from "
							+ logtable
							+ " ll where ll.MK_TYPE='1'  and ll.status='1' ) and rr.CSTATUS in('0','1','2','3')) as wCount "
							+ "   from CODE_REP_REPORT r where r.PKID="
							+ rt.getPkid();
					Object[][] scalaries = new Object[][] {
							{ "pkid", Hibernate.STRING },
							{ "reportType", Hibernate.STRING },
							{ "reportName", Hibernate.STRING },
							{ "tCount", Hibernate.STRING },
							{ "wCount", Hibernate.STRING } };
					List relist = list(sql, null, scalaries);
					if (relist != null) {
						for (int p = 0; p < relist.size(); p++) {
							resultList.add(relist.get(p));
						}
					}
					// }
				}
			}
			return resultList;
		}
		// sql =
		// "select r.PKID as pkid,r.REPORT_TYPE as reportType,r.NAME as reportName,(select count(*) from "+resultname+" mm "
		// +
		// " where mm.ORGAN_ID in ("
		// + organTree
		// +
		// ") and mm.MODEL_ID in (select to_char(te.model_id) from TEMPLATE_MODEL te where te.template_id =r.PKID"
		// +
		// " and te.model_id not in (select ll.ID_REP from "+logtable+" ll where ll.ID_REP=te.model_id and ll.MK_TYPE='1'  and ll.status='1' )) and mm.CSTATUS ='4') as tCount "
		// +",(select count(*) from "+resultname+" rr " +
		// " where rr.ORGAN_ID in ("
		// + organTree
		// +
		// ") and rr.MODEL_ID in (select to_char(te.model_id) from TEMPLATE_MODEL te where te.template_id =r.PKID"
		// +
		// " and te.model_id not in (select ll.ID_REP from "+logtable+" ll where ll.ID_REP=te.model_id and ll.MK_TYPE='1'  and ll.status='1' )) and rr.CSTATUS in('0','1','2','3')) as wCount "+
		// "   from CODE_REP_REPORT r where r.REPORT_TYPE in ("
		// + str + ")";
		// }
		// Object[][] scalaries = new Object[][] { { "pkid", Hibernate.STRING },
		// { "reportType", Hibernate.STRING },
		// { "reportName", Hibernate.STRING },
		// { "tCount", Hibernate.STRING },
		// { "wCount", Hibernate.STRING }};

		// return this.list(sql, null, scalaries);
	}

	/**
	 * 查询哪些机构有哪些表要补录
	 * 
	 * @param reportId
	 * @param reportType
	 * @param reportDate
	 * @param organId
	 * @param idxid
	 * @return
	 */
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
		String organTree = "select /*+ parallel(dual,4) */ tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
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
			sql = "select "
					+ reportId
					+ " as reportId,'"
					+ organId
					+ "' as organId,(select count(distinct(keyvalue)) from "
					+ targettable
					+ " where organ_id in ("
					+ organTree
					+ ") and cstatus in ('0','3','5') and substr(etldate,0,6)='"
					+ reportDate.substring(0, 6)
					+ "') as tCount ,"
					+ "(select count(distinct(keyvalue)) from "
					+ targettable
					+ " where organ_id in ("
					+ organTree
					+ ") and cstatus in ('4') and substr(etldate,0,6)='"
					+ reportDate.substring(0, 6)
					+ "') as yCount ,t.name "
					+ " as reportName ,'"
					+ targettable
					+ "' as targetTable, "
					+ " (select count(pkid) from CODE_ORG_TREE where parentId="
					+ orglab
					+ " ) AS wCount ,999 as status from code_rep_report t where t.pkid="
					+ reportId;
			Object[][] scalaries = new Object[][] {
					{ "reportId", Hibernate.STRING },
					{ "organId", Hibernate.STRING },
					{ "tCount", Hibernate.STRING },
					{ "reportName", Hibernate.STRING },
					{ "targetTable", Hibernate.STRING },
					{ "wCount", Hibernate.STRING },
					{ "status", Hibernate.STRING },
					{ "yCount", Hibernate.STRING } };
			return list(sql, null, scalaries);
		} else {
			List<Report> tempList = getDateOrganEditReportForStandard(
					reportDate, userId, systemcode, levelFlag);// 获得报送模板列表
			String tempid = "";
			List resultList = new ArrayList();
			for (Report rt : tempList) {
				String hql = "from ReportConfig t where t.reportId = "
						+ rt.getPkid() + " and t.funId = 34";
				ReportConfig rc = (ReportConfig) list(hql, new HashMap())
						.get(0);
				String targettable = rc.getDefChar().replaceAll("\\{M\\}",
						reportDate.substring(4, 6));
				targettable = targettable.replaceAll("\\{D\\}",
						reportDate.substring(6, 8));
				targettable = targettable.replaceAll("\\{Y\\}",
						reportDate.substring(0, 4));
				sql = "select "
						+ rt.getPkid()
						+ " as reportId,'"
						+ organId
						+ "' as organId,(select /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('0','3','5')and substr(etldate,0,6)='"
						+ reportDate.substring(0, 6)
						+ "') as tCount ,"
						+ "(select /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
						+ targettable
						+ " where organ_id in ("
						+ organTree
						+ ") and cstatus in ('4')and substr(etldate,0,6)='"
						+ reportDate.substring(0, 6)
						+ "') as yCount ,"
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
				Object[][] scalaries = new Object[][] {
						{ "reportId", Hibernate.STRING },
						{ "organId", Hibernate.STRING },
						{ "tCount", Hibernate.STRING },
						{ "reportName", Hibernate.STRING },
						{ "targetTable", Hibernate.STRING },
						{ "wCount", Hibernate.STRING },
						{ "status", Hibernate.STRING },
						{ "yCount", Hibernate.STRING } };
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

	public List getDateOrganEditReportForStandard(String paramDate,
			Long userId, String systemId, String levelFlag) {
		String freqStr = "";
		// 2006.12.4查询速度优化：本表条件、数据量由大到小表限制条件
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
	 * 查询机构详细补录信息
	 * 
	 * @param reportId
	 * @param reportType
	 * @param reportDate
	 * @param organId
	 * @param idxid
	 * @return
	 */
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
			String sql = "select "
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
					+ ") and cstatus in ('0','3','5')and substr(etldate,0,6)='"
					+ reportDate.substring(0, 6)
					+ "') as tCount ,"
					+ "(select  /*+ parallel(dual,4) */ count(distinct(keyvalue)) from "
					+ targettable
					+ " "
					+ " where organ_id in ("
					+ organTree
					+ ") and cstatus in ('0','3','5')and substr(etldate,0,6)='"
					+ reportDate.substring(0, 6)
					+ "') as yCount ,'"
					+ targettable
					+ "' as "
					+ " targetTable ,(select  /*+ parallel(dual,4) */ count(pkid) from CODE_ORG_TREE  where parentId="
					+ oi.getPkid() + " ) AS wCount from dual";
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
					if (Integer.parseInt(obj[4].toString()) != 0) {
						resultList.add(relist.get(p));
					}
				}
			}
		}
		return resultList;

	}

	/**
	 * 获得记录条数
	 * 
	 * @param sql
	 * @return
	 */
	private String getCount(String sql) {
		Object[][] scalaries = { { "total", Hibernate.INTEGER } };
		List result = list(sql, null, scalaries, null);
		Iterator it = result.iterator();
		String value = "0";
		while (it.hasNext()) {
			value = (String) it.next();
		}
		return value;
	}

	@Override
	public int getErrorReportData(String reportId, String reportDate,
			String temptablename) {
		String sql = "select count(1) as tempReportCount from " + temptablename
				+ " where report_id=" + reportId + " and report_date='"
				+ reportDate + "'";
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		query.addScalar("tempReportCount", Hibernate.INTEGER);
		return ((Integer) query.uniqueResult()).intValue();
	}

	/**
	 * 批量更新结果表
	 */
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
					// stFlag 为0时，表示保存，但不提交 不修改结果表状态，这时还可以看到数据 ;
					// 为1时，表示提交，即要改结果表里的状态
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
						// cstatus 为5时，保存不提交
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

	/**
	 * 根据规则编码和校验状态 清洗结果
	 * 
	 * @param rulecode
	 * @param cstatus
	 */
	public int deleteReportResult(String rulecode, String cstatus,
			String tablename) {
		String sql = "delete from " + tablename + " where RULECODE in ("
				+ rulecode + ") and CSTATUS='" + cstatus + "'";
		return jdbcUpdate(sql, null);
	}

	/**
	 * 获得后台预算结果
	 * 
	 * @param sql
	 * @param tablename
	 * @return
	 */
	public String getRvalue(String sql, String tablename) {
		// SQLQuery query =
		// getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// query.addScalar("total",Hibernate.STRING);
		Object[][] scalaries = { { "total", Hibernate.STRING } };
		List result = list(sql, null, scalaries, null);
		Iterator it = result.iterator();
		String value = "";
		while (it.hasNext()) {
			value = (String) it.next();
		}
		return value;
		// return query.uniqueResult().toString();
	}

	/**
	 * 批量更新结果表
	 */
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
				// ReportConfig rc = new ReportConfig();
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
			/*
			 * if(i<valueList.size()-1){ falg+=","; }
			 */
		}
		falg += "CJRQ,PKID) values (";
		for (int i = 0; i < cstatusList.size(); i++) {
			falg += "'" + cstatusList.get(i) + "',";
			/*
			 * if(i<valueList.size()-1){ falg+=","; }
			 */
		}

		sql += falg + "'" + reportDate + "',(SELECT MAX(pkid)+1 from " + rtable
				+ "))";

		return jdbcUpdate(sql, null);
	}
}
