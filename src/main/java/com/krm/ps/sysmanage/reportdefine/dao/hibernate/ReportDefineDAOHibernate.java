package com.krm.ps.sysmanage.reportdefine.dao.hibernate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.krm.common.logger.KRMLogger;
import com.krm.common.logger.KRMLoggerUtil;
import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.common.dao.ReportDataDAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;
import com.krm.ps.sysmanage.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;
import com.krm.ps.sysmanage.reportdefine.vo.ReportItem;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTemplate;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.reportdefine.vo.TableField;
import com.krm.ps.sysmanage.reportdefine.vo.TargetTemplate;
import com.krm.ps.sysmanage.usermanage.vo.UserReport;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.SysConfig;
import com.krm.ps.util.Util;
import com.krm.ps.util.Constants;
import com.krm.ps.util.DateUtil;

public class ReportDefineDAOHibernate extends BaseDAOHibernate implements
		ReportDefineDAO {
	
	private ReportDataDAO rdd;
	KRMLogger logger = KRMLoggerUtil.getLogger(ReportDefineDAOHibernate.class); 
	public void setReportDataDAO(ReportDataDAO obj) {
		this.rdd = obj;
	}
	private boolean isNull(Object o) {
		if (null != o && o.toString().trim().length() > 0) {
			return false;
		}
		return true;
	}

	public List getReportTypesForExport1104(Long userid) {
		String hql = "select new ReportType(r) from ReportType r where r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and r.pkid in (113,114) and r.dataSource='2' and r.isShowRep ='0' and r.pkid in(select ur.typeId from UserReport ur where ur.operId =:userid) order by r.pkid , r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		return list(hql, map);
	}
	public List allReportTypes(Long userid){
		String hql = "select new ReportType(r) from ReportType r where r.dataSource <>'9' and r.systemCode <>8 and r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and r.isCollect in ('1','2') and r.pkid in(select ur.typeId from UserReport ur where ur.operId =:userid) order by r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		List ls = list(hql, map);

		return ls;
	}
	public List get1104ExportReportByReportType(Long userid, Long reportTypeId) {
		String hql = "Select r from Report r, ReportType t where r.reportType=t.pkid and r.reportType=:typeid and r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and r.pkid in(select ur.reportId from UserReport ur where ur.operId =:userid) and t.dataSource='2' order by r.reportType,r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		map.put("typeid", reportTypeId);
		return list(hql, map);
	}

	public List get1104ExportReport(Long userid) {
		String hql = "Select r from Report r, ReportType t where r.reportType=t.pkid and r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and r.pkid in(select ur.reportId from UserReport ur where ur.operId =:userid) and t.dataSource='2' order by r.reportType,r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		return list(hql, map);
	}

	// 2006.9.29
	public List get1104ExportReportFrequency(String date, Long type, Long userid) {
		String hql = "Select r from Report r, ReportType t where r.reportType=t.pkid and r.status <> "
				+ Constants.STATUS_DEL
				+ " and r.pkid in(select ur.reportId from UserReport ur where ur.operId =:userid) and t.dataSource='2' ";
		hql += "and r.reportType = " + type + " ";
		hql += "and r.beginDate < '" + date + "' and r.endDate > '" + date
				+ "' ";

		// 加入频度限制
		String freqStr = "";
		if ("03".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("09".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("12".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		// 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。
		freqStr = "(" + freqStr + ",'5','6','7','8') ";
		hql += "and r.frequency in " + freqStr;

		hql += "order by r.reportType,r.showOrder ";

		Map map = new HashMap();
		map.put("userid", userid);
		return list(hql, map);
	}

	public List get1104ReportByLogType(String date, Long type, Long userid,
			String logType, String organId) {
		String sql = "select {r.*} from code_rep_report r, code_rep_types t where r.report_type=t.pkid and r.status<> "
				+ Constants.STATUS_DEL
				+ " and r.pkid in(select ur.repid from rep_oper_contrast ur where ur.operid ="
				+ userid + ") and t.data_source='2' ";
		sql += "and r.report_type = " + type + " ";
		sql += "and r.begin_date < '" + date + "' and r.end_date > '" + date
				+ "' ";

		// 加入频度限制
		String freqStr = "";
		if ("03".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("09".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("12".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		// 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。
		freqStr = "(" + freqStr + ",'5','6','7','8') ";
		sql += "and r.frequency in " + freqStr;

		String month = date.substring(4, 6);
		String logdate = date.substring(0, 6) + "00";
		sql += "and exists(select l.id_rep from log_data_" + month
				+ " l where l.id_rep=r.pkid and l.cd_organ='" + organId
				+ "' and l.date_data='" + logdate + "' and l.mk_type='"
				+ logType + "')";

		sql += "order by r.report_type,r.show_order ";

		System.out.println(sql);
		List result = list(sql, new Object[][] { { "r", Report.class } }, null);
		return result;
	}

	public List getReportTypesForGather(Long userid) {
		String hql = "select new ReportType(r) from ReportType r where r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and r.dataSource='1' and r.pkid in(select ur.typeId from UserReport ur where ur.operId =:userid) order by r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		List ls = list(hql, map);
		return ls;
	}

	public List getReportTypesForEdit(Long userid) {
		String hql = "select new ReportType(r) from ReportType r where r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and (r.dataSource='3' or r.isInput='1')" // add "or
				// r.isInput=1",wsx 8-31
				+ " and r.pkid in(select ur.typeId from UserReport ur"
				+ " where ur.operId =:userid) order by r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		List ls = list(hql, map);
		return ls;
	}

	public List getReportTypesForVise(Long userid) {
		String hql = "select new ReportType(r) from ReportType r where r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and r.isUpdate='1' and r.pkid in(select ur.typeId from UserReport ur where ur.operId =:userid) order by r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		List ls = list(hql, map);
		return ls;
	}

	public List getReportTypes(Long userid) {
		String hql = "select new ReportType(r) from ReportType r where r.dataSource <>'9' and r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and r.pkid in(select ur.typeId from UserReport ur where ur.operId =:userid) order by r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		List ls = list(hql, map);

		return ls;
	}
	public List getReportTypes(String typeid) {
		String hql="from ReportType r where r.pkid in("+typeid+") order by r.showOrder";
    	return  this.list(hql);
	}
	public List getReportTypesForCollect(Long userid) {
		String hql = "select new ReportType(r) from ReportType r where r.dataSource <>'9' and r.systemCode <>8 and r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and r.isCollect in ('1','2') and r.pkid in(select ur.typeId from UserReport ur where ur.operId =:userid) order by r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		List ls = list(hql, map);

		return ls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getReportTypesForExport
	 * (java.lang.Long)
	 */
	public List getReportTypesForExport(Long userid) {
		String hql = "select new ReportType(r) from ReportType r where r.dataSource <>'9' and r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and r.upReport = '1' and r.pkid in(select ur.typeId from UserReport ur where ur.operId =:userid) order by r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		List ls = list(hql, map);

		return ls;
	}

	public List getAllReportTypes(String showLevel) {
		String hql = "from ReportType t where  t.status <>'"
				+ Constants.STATUS_DEL + "' ";
		if(showLevel!=null&&!"".equals(showLevel)){
			   hql+="and t.showlevel="+new Long(showLevel);
		}
		hql+="order by t.showOrder , t.pkid";
		return list(hql);
	}

	// type 1:生成；2：检验；3：汇总
	public List getReportTypes(int type, Long userid) {
		String hql = "from ReportType t where t.status <> '"
				+ Constants.STATUS_DEL
				+ "' and t.pkid in(select ur.typeId from UserReport ur where ur.operId =:userid)";
		if (type == 1) {
			hql += " and t.dataSource = '2' ";
		} else if (type == 2) {
			hql += " and t.isBalance = '1' and t.dataSource <> '9' ";
		} else if (type == 3) {// wsx 8-16
			hql += " and t.isCollect = '1' ";
		}
		hql += " order by t.showOrder,t.pkid ";
		Map map = new HashMap();
		map.put("userid", userid);
		List ls = list(hql, map);
		return ls;
	}

	public void removeReportType(Long typeId) {
		Object type = this.getObject(ReportType.class, typeId);
		if (null != type) {
			((ReportType) type).setStatus(Constants.STATUS_DEL);
			this.saveObject(type);
		}
	}

	public boolean typeNameRepeat(Long pkid, String name) {
		Long spkid = pkid;
		if (null == spkid) {
			spkid = new Long(-1);
		}
		String hql = "from ReportType t where t.status <> '"
				+ Constants.STATUS_DEL
				+ "' and t.pkid<>:pkid and t.name = :name";
		Map map = new HashMap();
		map.put("pkid", spkid);
		map.put("name", name);
		List ls = list(hql, map);
		if (ls.size() > 0)
			return true;
		return false;
	}

	public List getReports(Report report, String date, Long userid,String showlevel) {

		/*Map map = new HashMap();
		String hql = "";
		
		
		if (date != null) {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.pkid in(select ur.reportId from UserReport ur where ur.operId =:userid) and r.status <>"
					+ Constants.STATUS_DEL;
			map.put("userid", userid);

			
			if (date.matches("^\\d{8}$")) {
				// piliang add comment for reading 2010-3-26 ����05:38:28
				
				hql += " and r.beginDate<=:date and r.endDate>=:date";
				map.put("date", date);
			}
			hql += " and r.reportType = t.pkid";
		}
		
		else if (userid == null) {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL
					+ " and r.reportType = t.pkid and r.pkid <>0";
		}
		
		else {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL + " and r.reportType = t.pkid ";
		}
		if(showlevel!=null&&!"".equals(showlevel)){
			hql+=" and t.showlevel="+showlevel;
		}
		String and = " and ";

		if (null == report) {
			System.out.println("\n=========================================="
					+ hql + " order by r.reportType,r.showOrder");
			System.out.println("==========================================");
			return list(hql + " order by r.reportType,r.showOrder", map);
		}

		StringBuffer conditions = new StringBuffer();

		if (!isNull(report.getIsSum())) {
			conditions.append("r.isSum = :isSum");
			map.put("isSum", report.getIsSum());
			conditions.append(and);
		}

		if (!isNull(report.getReportType())) {
			conditions.append("r.reportType = :reportType");
			map.put("reportType", report.getReportType());
			conditions.append(and);
		}
		// 2006.9.22
		if (!isNull(report.getFrequency())) {
			conditions.append("r.frequency = :frequency");
			map.put("frequency", report.getFrequency());
			conditions.append(and);
		}

		if (!isNull(report.getRol())) {
			conditions.append("r.rol = :rol");
			map.put("rol", report.getRol());
			conditions.append(and);
		}

		if (!isNull(report.getMoneyunit())) {
			conditions.append("r.moneyunit = :moneyunit");
			map.put("moneyunit", report.getMoneyunit());
			conditions.append(and);
		}

		if (!isNull(report.getBeginDate())) {
			conditions.append("r.beginDate >= :beginDate");
			map.put("beginDate", report.getBeginDate());
			conditions.append(and);
		}

		if (!isNull(report.getEndDate())) {
			conditions.append("r.endDate <= :endDate");
			map.put("endDate", report.getEndDate());
			conditions.append(and);
		}

		if (!isNull(report.getName())) {
			conditions.append("r.name like :name");
			map.put("name", "%" + report.getName() + "%");
			conditions.append(and);
		}

		if (!isNull(report.getConCode())) {
			conditions.append("r.conCode = :conCode");
			map.put("conCode", report.getConCode());
			conditions.append(and);
		}

		if (!isNull(report.getCode())) {
			conditions.append("r.code = :code");
			map.put("code", report.getCode());
			conditions.append(and);
		}

		String condition = conditions.toString();
		if (condition.length() > 0) {
			hql = hql + and + condition.substring(0, condition.length() - 5);
		}
		hql = hql + " order by r.reportType,r.showOrder";

		return list(hql, map);*/
		String hql = "";

		if (date != null) {
			hql = "select {r.*},{t.*} from code_rep_report r,code_rep_types t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.pkid in(select ur.repid from rep_oper_contrast ur where ur.operId ='"+userid+"') and r.status <>"
					+ Constants.STATUS_DEL;

			if (date.matches("^\\d{8}$")) {
				hql += " and r.begin_Date<='"+date+"' and r.end_Date>='"+date+"'";
			}
			hql += " and r.report_Type = t.pkid";
		}
		else if (userid == null) {
			hql = "select {r.*},{t.*} from code_rep_report r,code_rep_types t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL
					+ " and r.report_Type = t.pkid and r.pkid <>0";
		}
		else {
			hql = "select {r.*},{t.*} from code_rep_report r,code_rep_types t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL + " and r.report_Type = t.pkid ";
		}
		if (showlevel != null && !"".equals(showlevel)) {
			hql += " and t.showlevel=" + showlevel;
		}
		String and = " and ";

		if (null == report) {
			List l = list(hql + " order by r.report_Type,r.show_Order", new Object[][]{{"r",Report.class},{"t",ReportType.class}},null);
		}

		/*StringBuffer conditions = new StringBuffer();

		if (!isNull(report.getIsSum())) {
			conditions.append("r.is_Sum = '"+report.getIsSum()+"'");
			conditions.append(and);
		}

		if (!isNull(report.getReportType())) {
			conditions.append("r.report_Type = '"+report.getReportType()+"'");
			conditions.append(and);
		}
		// 2006.9.22
		if (!isNull(report.getFrequency())) {
			conditions.append("r.frequency = '"+report.getFrequency()+"'");
			conditions.append(and);
		}

		if (!isNull(report.getRol())) {
			conditions.append("r.rol = '"+report.getRol()+"'");
			conditions.append(and);
		}

		if (!isNull(report.getMoneyunit())) {
			conditions.append("r.moneyunit = '"+report.getMoneyunit()+"'");
			conditions.append(and);
		}

		if (!isNull(report.getBeginDate())) {
			conditions.append("r.begin_Date >= '"+report.getBeginDate()+"'");
			conditions.append(and);
		}

		if (!isNull(report.getEndDate())) {
			conditions.append("r.end_Date <= '"+report.getEndDate()+"'");
			conditions.append(and);
		}

		if (!isNull(report.getName())) {
			conditions.append("r.name like '%"+report.getName()+"%'");
			conditions.append(and);
		}

		if (!isNull(report.getConCode())) {
			conditions.append("r.con_Code = '"+report.getConCode()+"'");
			conditions.append(and);
		}

		if (!isNull(report.getCode())) {
			conditions.append("r.code = '"+report.getCode()+"'");
			conditions.append(and);
		}

		String condition = conditions.toString();
		if (condition.length() > 0) {
			hql = hql + and + condition.substring(0, condition.length() - 5);
		}*/
		//hql = hql + " order by r.report_Type,r.show_Order";

		List l =  list(hql, new Object[][]{{"r",Report.class},{"t",ReportType.class}},null);
		List list = new ArrayList();
		Report o1 = null;
		ReportType o2 = null;
		for (int i = 0; i < l.size(); i++) {
			Object[] obj = (Object[]) l.get(i);
			o1 = (Report) obj[0];
			o2 = (ReportType)obj[1];
			o1.setRepname(o2.getName());
			list.add(o1);
		}
		
		return list;
	}
	
	public List getReportsByType(Long typeId, String date, Long userid) {
		Report report = new Report();
		if(typeId != 0)
			 report.setReportType(typeId);
		return getReports(report, date, userid,null);
	}

	// 2006.9.22
	public List getReportsByTypeFrequencyDate(Long typeId, String frequencyId,
			String beginDateId, String endDateId, String date, Long userid) {
		Report report = new Report();
		if (typeId != null)
			report.setReportType(typeId);
		if (frequencyId != null)
			report.setFrequency(frequencyId);
		if (beginDateId != null)
			report.setBeginDate(beginDateId);
		if (endDateId != null)
			report.setEndDate(endDateId);
		return getReports(report, date, userid,null);
	}

	public List getReportsByType(Long typeId) {
		Report report = new Report();
		report.setReportType(typeId);
		return getReports(report, null, null,null);
	}

	public Long[] getReportidsByType(Long typeId, String date, Long userid) {
		Report report = new Report();
		if(typeId != 0)
		report.setReportType(typeId);
		List reports = getReports(report, date, userid,null);
		if (reports != null) {
			Long ids[] = new Long[reports.size()];
			for (int i = 0; i < reports.size(); i++) {
				Report r = (Report) reports.get(i);
				ids[i] = r.getPkid();
			}
			return ids;
		}
		return null;
	}

	public List getReportsForGather(String date, Long userid) {
		String hql = "Select r from Report r, ReportType t where r.reportType=t.pkid and r.pkid in(select ur.reportId from UserReport ur where ur.operId =:userid) and t.dataSource='1' and r.beginDate < :date and r.endDate > :date order by r.reportType,r.showOrder";
		Map map = new HashMap();
		map.put("userid", userid);
		map.put("date", date);
		return list(hql, map);
	}

	public List getReportsForStatExport() {
		String hql = "Select r from Report r where r.reportType in ('109','110','111','112') order by r.showOrder";
		return list(hql);
	}

	public List getReports(String date, Long userid) {
		return getReports(null, date, userid,null);
	}

	public void removeReport(Long pkid) {
		Object report = this.getObject(Report.class, pkid);
		if (null != report) {
			this.removeObject(report);
		}
	}

	public Long deleteReport(Long pkid) {
		Long result = null;
		String sql = "update code_rep_report set status = 9 where pkid = "
				+ pkid;
		try {
			jdbcUpdate(sql, null);
		} catch (Exception e) {
			result = new Long(0);
		}
		return result;
	}

	public List getReportOrgTypes(Long reportId) {
		String hql = "from ReportOrgType t where t.reportId = " + reportId;
		return list(hql);
	}

	public void delReportOrgTypes(Long reportId) {
		String sql = "delete from code_orgtype_report where reportid =?";
		jdbcUpdate(sql, new Object[] { reportId });
	}

	public List getReportItems(Long reportId, String date) {
		String hql = "";
		date = date.replaceAll("-", "");
		if (reportId.longValue() != 0) {
			hql = "from ReportItem t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and t.beginDate < :date and t.endDate > :date and t.reportId = "
					+ reportId + " order by t.itemOrder,t.pkid";
		} else {
			hql = "select i from ReportItem i,Report r,ReportType t where i.status<>'9' and r.status<>9 and t.status<>'9' and i.beginDate < :date and i.endDate > :date and i.reportId=r.pkid and r.reportType=t.pkid and r.pkid="
					+ reportId + " order by i.itemOrder";
		}
		Map map = new HashMap();
		map.put("date", date);
		return list(hql, map);
	}

	public List getReportItemsByFrequency(Long reportId, String date,
			String frequency) {

		String hql = "select i from ReportItem i,Report r,ReportType t where i.status<>'9' and r.status<>9 "
				+ "and t.status<>'9' and i.beginDate < :date and i.endDate > :date "
				+ "and i.reportId=r.pkid and r.reportType=t.pkid and i.frequency='"
				+ frequency
				+ "'  and r.pkid="
				+ reportId
				+ " order by i.itemOrder";
		Map map = new HashMap();
		map.put("date", date);
		return list(hql, map);
	}

	public List getReportItems(Long reportId) {
		String hql = "";
		if (reportId.longValue() != 0) {
			hql = "from ReportItem t where t.status<>'" + Constants.STATUS_DEL
					+ "' and t.reportId = " + reportId
					+ " order by t.itemOrder,t.pkid";
		} else {
			hql = "select i from ReportItem i,Report r,ReportType t where i.status<>'9' and r.status<>9 and t.status<>'9' and i.reportId=r.pkid and r.reportType=t.pkid and r.pkid="
					+ reportId + " order by i.itemOrder";
		}
		Map map = new HashMap();
		return list(hql, map);
	}

	public List getCollectReportItems(Long reportId, String date) {
		String hql = "from ReportItem t where t.status<>'"
				+ Constants.STATUS_DEL
				+ "' and t.beginDate < :date and t.endDate > :date and t.isCollect='1' and t.reportId="
				+ reportId + " order by t.itemOrder,t.pkid";
		Map map = new HashMap();
		map.put("date", date);
		return list(hql, map);
	}

	public void saveReportItem(ReportItem item) {
//		if (null != item.getSuperId()) {
//			Object o = getObject(ReportItem.class, item.getSuperId());
//			if (null != o) {
//				ReportItem superItem = (ReportItem) o;
//				if (1 == superItem.getIsLeaf().intValue()) {
//					superItem.setIsLeaf(new Integer(0));
//					saveObject(superItem);
//					getHibernateTemplate().merge(superItem);
//				}
//			}
//		}
//		item.setIsLeaf(new Integer(0));
//		if (item.getPkid() != null) {
//			
//			String oldItemCode = "";
//			List itemCodeList = getReportItems(item.getReportId());
//			Iterator itr = itemCodeList.iterator();
//			while (itr.hasNext()) {
//				ReportItem ri = (ReportItem) itr.next();
//				if (item.getPkid().equals(ri.getPkid())) {
//					oldItemCode = ri.getCode();
//				}
//			}
//			if (!item.getCode().equals(oldItemCode)) {
//				String tableName = FormulaHelp.getPhyTable((item.getReportId())
//						.intValue());
//				String sql = "UPDATE " + tableName + " SET item_id = ?"
//						+ " WHERE report_id = ? and item_id = ?";
//				rdd.update(sql,
//						new Object[] { item.getCode(), item.getReportId(),
//								oldItemCode });
//			}
//		}
		getHibernateTemplate().merge(item);
	}

	private boolean hasOtherChildItem(Long superId) {
		String hql = "from ReportItem t where t.status<>'"
				+ Constants.STATUS_DEL + "' and t.superId='" + superId + "'";
		List ls = list(hql);
		if (ls.size() > 1) {
			return true;
		}
		return false;
	}

	public void removeReportItem(Long itemId) {
		Object obj = getObject(ReportItem.class, itemId);
		if (null != obj) {
			ReportItem item = (ReportItem) obj;
			if (null != item.getSuperId()) {
				Object o = getObject(ReportItem.class, item.getSuperId());
				if (null != o) {
					ReportItem superItem = (ReportItem) o;
					if (!hasOtherChildItem(superItem.getPkid())) {
						superItem.setIsLeaf(new Integer(0));
						saveObject(superItem);
					}
				}
			}
			
			this.removeObject(item);
			String sql = "UPDATE code_rep_item SET super_id = '-1', edit_date = '"
					+ DateUtil.getDateTime("yyyyMMdd", new Date())
					+ "' WHERE super_id = '" + itemId + "'";

			jdbcUpdate(sql, null);
		}
	}

	public List<ReportTarget> getReportTargets(Long reportId) {
		String hql = "from ReportTarget t where t.status <> '"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " order by t.targetOrder";
		Map map = new HashMap();
		return list(hql, map);
	}
	public List getCollectTarget(Long reportId){
		String hql = "from TargetTemplate t where  t.reportId = " + reportId;
		Map map = new HashMap();
		return list(hql, map);
	}
	/**
	 * 根据 债券，股权类型查询报表
	 * @param reportId
	 * @return
	 */
	public List getReportTargetsStock(Long reportId,String stocktype) {
		String hql = "from ReportTarget t where t.status <> '"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " and t.stocktype='"+stocktype+"' order by t.targetOrder,t.pkid";
		Map map = new HashMap();
		return list(hql, map);
	}

	public List<ReportTarget> getTemplateTargets(Long templateId, Long modelId) {
		String sql = "SELECT {t.*} FROM code_rep_target t WHERE t.target_field IN (SELECT model_target FROM template_model WHERE template_id=? and model_id=?) and report_id=?";
		return list(sql, new Object[][] { { "t", ReportTarget.class } }, null,
				new Object[] {templateId, modelId,modelId});
	}

	public int removeReportTarget(Long reportId, Long targetId) {
		Object o = getObject(ReportTarget.class, targetId);
		if (null != o) {
			ReportTarget target = (ReportTarget) o;
			this.removeObject(target);
			//this.delDataByTarget(reportId,
			//		Integer.parseInt(target.getTargetField()));
		}
		return ReportDefineService.DEL_OK;
	}

	public List getOptions(List orgIds, String date, Long userid) {
		String hql = "select new com.krm.slsint.reportdefine.vo.Options(r.frequency,r.reportType,o.code,r.name,r.pkid) from Report r,OrganInfo o,ReportOrgType ro where "
				+ "r.status<>9 and o.status<>'9' and r.beginDate < '"
				+ date
				+ "' and r.endDate > '"
				+ date
				+ "' and r.pkid in(select ur.reportId from UserReport ur where ur.operId ="
				+ userid
				+ ") and r.pkid = ro.reportId and ro.organType = o.organ_type";
		List ls = list(hql);
		return ls;
	}

	public List getOrgReports(List organs, String date, Long userid) {
		String hql = "select distinct r from Report r"
				+ " where r.status<>9 and r.beginDate < '"
				+ date
				+ "' and r.endDate > '"
				+ date
				+ "' and r.pkid in(select ur.reportId from UserReport ur where ur.operId ="
				+ userid + ")" + " order by r.reportType,r.showOrder";
		List ls = list(hql);
		return ls;
	}

	// 2006.9.23
	public List getDateOrganEditReport(String paramDate,
			Integer paramorgan_type, String canEdit, Long userId) {
		String freqStr = "";
		// 2006.12.4查询速度优化：本表条件、数据量由大到小表限制条件
		StringBuffer hql = new StringBuffer(
				"select {r.*} from code_rep_report r ")
				.append("where r.status<>9 and ").append("r.begin_date <= '")
				.append(paramDate).append("' and ").append("r.end_date >= '")
				.append(paramDate).append("'");

		// 频度的判断
		if ("03".equals(paramDate.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(paramDate.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("09".equals(paramDate.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("12".equals(paramDate.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		freqStr = "'0'," + freqStr;
		// 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。
		freqStr = "(" + freqStr + ",'5','6','7','8') ";
		hql = hql.append(" and r.frequency in ").append(freqStr);
		// 加入操作员、机构限制
		hql = hql
				.append(" and r.pkid in (select u.repid from rep_oper_contrast u where u.operid = ")
				.append(userId)
				.append(" ) ")
				.append(" and r.pkid in (select t.reportid from code_orgtype_report t where t.organ_type = ")
				.append(paramorgan_type).append(" ) ");

		// 判断编辑标识
		if ("0".equals(canEdit)) {// 查询
			hql = hql
					.append("and r.report_type in (select rt.pkid from code_rep_types rt where rt.data_source <>'9' and ")
					.append("rt.status <> '").append(Constants.STATUS_DEL)
					.append("') ");
			// 判断报表格式是否存在
			hql = hql
					.append("and r.pkid in (select rf.report_id from rep_format rf where rf.report_id=r.pkid and ")
					.append("rf.begin_date < '").append(paramDate)
					.append("' and ").append("rf.end_date >= '")
					.append(paramDate).append("' ) ");
		}

		if ("1".equals(canEdit)) {// 录入
			hql = hql
					.append("and r.report_type in (select rt.pkid from code_rep_types rt where (rt.data_source='3' or rt.is_input='1') and ")
					.append("rt.status <> '").append(Constants.STATUS_DEL)
					.append("') ");
			// 判断报表格式是否存在
			hql = hql
					.append("and r.pkid in (select rf.report_id from rep_format rf where rf.report_id=r.pkid and ")
					.append("rf.begin_date < '").append(paramDate)
					.append("' and ").append("rf.end_date >= '")
					.append(paramDate).append("' ) ");
		}
		// 排序
		hql = hql.append("  order by r.report_type,r.show_order");
		List ls = list(hql.toString(),
				new Object[][] { { "r", Report.class } }, null);

		return ls;
	}

	public List getDateOrganEditReportForStandard(String paramDate,
			Integer paramorgan_type, String canEdit, Long userId,
			String systemId, String levelFlag) {
		String freqStr = "";
		// 2006.12.4查询速度优化：本表条件、数据量由大到小表限制条件
		StringBuffer hql = new StringBuffer(
				"select {r.*} from code_rep_report r ")
				.append("where r.status<>9 ");
		if(paramDate!=null&& !"".equals(paramDate)){
				hql.append("and ").append("r.begin_date <= '")
				.append(paramDate).append("' and ").append("r.end_date >= '")
				.append(paramDate).append("'");
			}
		//if("0".equals(levelFlag)){
			 hql.append(" and r.pkid in (select u.repid from rep_oper_contrast u where u.operid = ")
				.append(userId)
				.append(" ) ");
		//}

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
	 * 查出不在日志表的数据	
	 * @param paramDate
	 * @param paramorgan_type
	 * @param canEdit
	 * @param userId
	 * @param systemId
	 * @param levelFlag
	 * @return
	 */
		public List getDateOrganEditReportForYJH(String paramDate,
				Integer paramorgan_type, String canEdit, Long userId,
				String systemId, String levelFlag) {
			String freqStr = "";
			// 2006.12.4查询速度优化：本表条件、数据量由大到小表限制条件
			StringBuffer hql = new StringBuffer(
					"select {r.*} from code_rep_report r ")
					.append("where r.status<>9 ");
			if(paramDate!=null&& !"".equals(paramDate)){
					hql.append("and ").append("r.begin_date <= '")
					.append(paramDate).append("' and ").append("r.end_date >= '")
					.append(paramDate).append("'");
				}

			if (systemId != null && !"".equals(systemId)) {
				hql = hql
						.append(" and r.report_type in (select t.pkid from code_rep_types t where t.system_code=")
						.append(systemId);
				if (levelFlag != null && !"".equals(levelFlag)) {
					hql = hql.append(" and t.showlevel=").append(levelFlag+")");
				} 
			}else{
				if (levelFlag != null && !"".equals(levelFlag)) {
					hql = hql
							.append(" and r.report_type in (select t.pkid from code_rep_types t where ");
					hql = hql.append(" t.showlevel=").append(levelFlag+")");
				} 
			}

		
			//报表必须是通过审核的
			/**  青海取消审核功能  **/
				//hql = hql.append(" and  r.pkid in (select b.id_rep from LOG_DATA_"+paramDate.substring(4,6)+" b where b.MK_TYPE='1'  and b.status='1') ");

			/*
			 * // 频度的判断 if ("03".equals(paramDate.substring(4, 6))) { freqStr =
			 * "'1','2'"; } else if ("06".equals(paramDate.substring(4, 6))) {
			 * freqStr = "'1','2','3'"; } else if
			 * ("09".equals(paramDate.substring(4, 6))) { freqStr = "'1','2'"; }
			 * else if ("12".equals(paramDate.substring(4, 6))) { freqStr =
			 * "'1','2','3','4'"; } else { freqStr = "'1'"; } freqStr = "'0'," +
			 * freqStr; // 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。 freqStr = "(" +
			 * freqStr + ",'5','6','7','8') "; hql =
			 * hql.append(" and r.frequency in ").append(freqStr); // 加入操作员、机构限制 hql
			 * = hql .append(
			 * " and r.pkid in (select u.repid from rep_oper_contrast u where u.operid = "
			 * ) .append(userId) .append(" ) ") .append(
			 * " and r.pkid in (select t.reportid from code_orgtype_report t where t.organ_type = "
			 * ) .append(paramorgan_type).append(" ) "); // 判断编辑标识 if
			 * ("0".equals(canEdit)) {// 查询 hql = hql .append(
			 * "and r.report_type in (select rt.pkid from code_rep_types rt where rt.data_source <>'9' and "
			 * ) .append("rt.status <> '").append(Constants.STATUS_DEL).append(
			 * "') "); // 判断报表格式是否存在 hql = hql .append(
			 * "and r.pkid in (select rf.report_id from rep_format rf where rf.report_id=r.pkid and "
			 * ) .append("rf.begin_date < '").append(paramDate).append("' and ")
			 * .append("rf.end_date >= '").append(paramDate).append("' ) "); }
			 * 
			 * if ("1".equals(canEdit)) {// 录入 hql = hql .append(
			 * "and r.report_type in (select rt.pkid from code_rep_types rt where (rt.data_source='3' or rt.is_input='1') and "
			 * ) .append("rt.status <> '").append(Constants.STATUS_DEL).append(
			 * "') "); // 判断报表格式是否存在 hql = hql .append(
			 * "and r.pkid in (select rf.report_id from rep_format rf where rf.report_id=r.pkid and "
			 * ) .append("rf.begin_date < '").append(paramDate).append("' and ")
			 * .append("rf.end_date >= '").append(paramDate).append("' ) "); }
			 */
			// 排序
		hql = hql.append("  order by r.report_type,r.show_order");
		List ls = list(hql.toString(),
				new Object[][] { { "r", Report.class } }, null);

		return ls;
	}

		// 传入报表类型列表返回报表列表
		// wsx 8-16 此方法只供汇总模块调用
	public List getReportsByTypes(List reptypes, String date, Long userid) {
		if (null != reptypes) {
			StringBuffer org = new StringBuffer();
			for (int i = 0; i < reptypes.size(); i++) {
				org.append(((ReportType) reptypes.get(i)).getPkid());
				org.append(",");
			}
			String pkids = "";
			if (org.length() > 1) {
				pkids = org.substring(0, org.length() - 1).toString();
			}

			// wsx 8-16,只取可以汇总的报表（报表类型表code_rep_types的is_collect为1）
			String hql = "select r from Report r,ReportType t"
					+ " where r.status<>9 and r.beginDate < '" + date
					+ "' and r.endDate > '" + date
					+ "' and r.reportType = t.pkid and t.isCollect = '1'"
					+ " and r.pkid in(select ur.reportId"
					+ " from UserReport ur where ur.operId =" + userid
					+ ") and r.reportType in(" + pkids
					+ ") order by r.reportType,r.showOrder";

			List ls = list(hql);
			return ls;
		}
		return null;
	}

	// 2006.10.13加入频度的限制
	public List getReportsByTypes(Long reptypes, String date, Long userid) {
		if (null != reptypes) {

			String hql = "select r from Report r,ReportType t"
					+ " where r.status<>9 and r.beginDate < '"
					+ date
					+ "' and r.endDate > '"
					+ date
					+ "' and r.reportType = t.pkid and t.isCollect in ('1','2')"
					+ " and r.pkid in(select ur.reportId"
					+ " from UserReport ur where ur.operId =" + userid
					+ ") and r.reportType = " + reptypes + " ";

			// 加入频度限制
			String freqStr = "";
			if ("03".equals(date.substring(4, 6))) {
				freqStr = "'1','2'";
			} else if ("06".equals(date.substring(4, 6))) {
				freqStr = "'1','2','3'";
			} else if ("09".equals(date.substring(4, 6))) {
				freqStr = "'1','2'";
			} else if ("12".equals(date.substring(4, 6))) {
				freqStr = "'1','2','3','4'";
			} else {
				freqStr = "'1'";
			}
			// 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。
			freqStr = "(" + freqStr + ",'5','6','7','8') ";

			hql += "and r.frequency in " + freqStr;
			hql += "order by r.reportType,r.showOrder";

			List ls = list(hql);
			return ls;
		}
		return null;
	}

	public List getBalancedReportForCollect(Long reptypes, String date,
			Long userid, String organCode) {
//		if (null != reptypes) {
//
//			String sql = "SELECT {r.*} FROM code_rep_report r ,code_rep_types t"
//					+ " where r.status<>9 and r.begin_date < '"
//					+ date
//					+ "' and r.end_date > '"
//					+ date
//					+ "' and r.report_type = t.pkid and t.is_collect in ('1','2')"
//					+ " and r.pkid in(select ur.repid"
//					+ " from rep_oper_contrast ur where ur.operid ="
//					+ userid
//					+ ") and r.report_type = " + reptypes + " ";
//
//			
//			String freqStr = "";
//			if ("03".equals(date.substring(4, 6))) {
//				freqStr = "'1','2'";
//			} else if ("06".equals(date.substring(4, 6))) {
//				freqStr = "'1','2','3'";
//			} else if ("09".equals(date.substring(4, 6))) {
//				freqStr = "'1','2'";
//			} else if ("12".equals(date.substring(4, 6))) {
//				freqStr = "'1','2','3','4'";
//			} else {
//				freqStr = "'1'";
//			}
//			
//			freqStr = "(" + freqStr + ",'5','6','7','8') ";
//			sql += "and r.frequency in " + freqStr;
//			String month = date.substring(4, 6);
//			String logdate = date.substring(0, 6) + "00";
//			sql += "and exists(select l.id_rep from log_data_" + month
//					+ " l where l.id_rep=r.pkid and l.cd_organ='" + organCode
//					+ "' and l.date_data='" + logdate + "' and l.mk_type='"
//					+ LogUtil.LogType_ReportBalanceOK + "')";
//
//			sql += "order by r.report_type,r.show_order";
//			List result = list(sql, new Object[][] { { "r", Report.class } },
//					null);
//
//			return result;
//		}
		return null;
	}

	public List getAuditCollectReportsByTypes(Long reptypes, String date,
			Long userid, String organCode) {
//		if (null != reptypes) {
//
//			String sql = "SELECT {r.*} FROM code_rep_report r ,code_rep_types t"
//					+ " where r.status<>9 and r.begin_date < '"
//					+ date
//					+ "' and r.end_date > '"
//					+ date
//					+ "' and r.report_type = t.pkid and t.is_collect in ('1','2')"
//					+ " and r.pkid in(select ur.repid"
//					+ " from rep_oper_contrast ur where ur.operid ="
//					+ userid
//					+ ") and r.report_type = " + reptypes + " ";
//
//			
//			String freqStr = "";
//			if ("03".equals(date.substring(4, 6))) {
//				freqStr = "'1','2'";
//			} else if ("06".equals(date.substring(4, 6))) {
//				freqStr = "'1','2','3'";
//			} else if ("09".equals(date.substring(4, 6))) {
//				freqStr = "'1','2'";
//			} else if ("12".equals(date.substring(4, 6))) {
//				freqStr = "'1','2','3','4'";
//			} else {
//				freqStr = "'1'";
//			}
//			
//			freqStr = "(" + freqStr + ",'5','6','7','8') ";
//			sql += "and r.frequency in " + freqStr;
//			String month = date.substring(4, 6);
//			String logdate = date.substring(0, 6) + "00";
//			StringBuffer logType = new StringBuffer();
//			logType.append("'" + LogUtil.LogType_DeptHeaderAuditOK + "' ,");
//			logType.append("'" + LogUtil.LogType_Report_Lock + "'");
//			sql += "and exists(select l.id_rep from log_data_" + month
//					+ " l where l.id_rep=r.pkid and l.cd_organ='" + organCode
//					+ "' and l.date_data='" + logdate + "' and l.mk_type in ("
//					+ logType + "))";
//
//			sql += " order by r.report_type,r.show_order";
//			List result = list(sql, new Object[][] { { "r", Report.class } },
//					null);
//
//			return result;
//		}
		return null;
	}

	public List getReportsByTypes(Long reptypes, String date, Long userid,
			Long organType) {
		String sql = "SELECT {r.*} FROM code_rep_report r "
				+ "JOIN code_orgtype_report ot ON ot.reportid = r.pkid AND ot.organ_type = ? "
				+ "JOIN code_rep_types rt ON r.report_type = rt.pkid AND rt.is_collect = '1' "
				+ "JOIN rep_oper_contrast c ON c.repid = r.pkid AND c.operid = ? "
				+ "WHERE r.status <> 9 AND r.begin_date < ? AND r.end_date > ? AND "
				+ "r.report_type = ?";
		// 加入频度限制
		String freqStr = "";
		if ("03".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("09".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("12".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		// 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。
		freqStr = "(" + freqStr + ",'5','6','7','8') ";
		sql += "AND r.frequency IN " + freqStr;
		sql += "ORDER BY r.report_type,r.show_order";
		Object[] values = new Object[] { organType, userid, date, date,
				reptypes };
		return list(sql, new Object[][] { { "r", Report.class } }, null, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.reportdefine.dao.ReportDefineDAO#getReports(int)
	 */
	public List getReports(int buildOrCheck, String date, Long userid) {
		String sql = "select {t.*} from"
				+ " code_rep_report t ,code_rep_types rt"
				+ " where t.report_type = rt.pkid and t.begin_date < '" + date
				+ "' and t.end_date > '" + date
				+ "' and t.pkid in(select ur.repid"
				+ " from rep_oper_contrast ur where ur.operid =" + userid
				+ ") and t.status < 9 ";
		if (buildOrCheck == 1) {
			sql += "and rt.data_source = '2' ";
		} else {
			sql += "and rt.is_balance = '1' ";
		}
		sql += "order by t.report_type,t.show_order";
		List result = list(sql, new Object[][] { { "t", Report.class } }, null);

		return result;
	}

	// 2006.9.28
	public List getReportsFrequency(int buildOrCheck, String date, Long type,
			Long userid) {
		String sql = "select {t.*} from"
				+ " code_rep_report t ,code_rep_types rt"
				+ " where t.report_type = rt.pkid and t.begin_date < '"
				+ date
				+ "' and t.end_date > '"
				+ date
				+ "' and t.pkid in(select ur.repid"
				+ " from rep_oper_contrast ur where ur.operid ="
				// 2010-7-15 上午09:33:21 皮亮修改
				// 此处进行了修改，如果传入的userid为null认为不对人员
				// 权限进行过滤
				+ (userid == null ? "ur.operid" : userid.toString())
				+ ") and t.status < 9 "
				// 如下仅一行为周浩添加,目的:不显示由外汇转换生成的报表
				+ " and t.pkid not in(select distinct rc.def_int from code_rep_config rc where rc.fun_id = 31)"
				+ " and t.report_type = " + type + " ";
		if (buildOrCheck == 1) {
			sql += "and rt.data_source = '2' ";
		} else {
			sql += "and rt.is_balance = '1' ";
		}
		// 加入频度限制
		String freqStr = "";
		if ("03".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("09".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("12".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		// 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。
		freqStr = "(" + freqStr + ",'5','6','7','8') ";

		sql += "and t.frequency in " + freqStr;

		sql += "order by t.report_type,t.show_order";
		List result = list(sql, new Object[][] { { "t", Report.class } }, null);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getReportsFrequencyBalanceOK
	 * (int, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public List getBalancedReportsForDataBuild(int buildOrCheck, String date,
			Long type, Long userid, String organId) {
//		String sql = "select {t.*} from"
//				+ " code_rep_report t ,code_rep_types rt"
//				+ " where t.report_type = rt.pkid and t.begin_date < '"
//				+ date
//				+ "' and t.end_date > '"
//				+ date
//				+ "' and t.pkid in(select ur.repid"
//				+ " from rep_oper_contrast ur where ur.operid ="
//				
//				+ (userid == null ? "ur.operid" : userid.toString())
//				+ ") and t.status < 9 "
//				
//				+ " and t.pkid not in(select distinct rc.def_int from code_rep_config rc where rc.fun_id = 31)"
//				+ " and t.report_type = " + type + " ";
//		if (buildOrCheck == 1) {
//			sql += "and rt.data_source = '2' ";
//		} else {
//			sql += "and rt.is_balance = '1' ";
//		}
//		
//		String freqStr = "";
//		if ("03".equals(date.substring(4, 6))) {
//			freqStr = "'1','2'";
//		} else if ("06".equals(date.substring(4, 6))) {
//			freqStr = "'1','2','3'";
//		} else if ("09".equals(date.substring(4, 6))) {
//			freqStr = "'1','2'";
//		} else if ("12".equals(date.substring(4, 6))) {
//			freqStr = "'1','2','3','4'";
//		} else {
//			freqStr = "'1'";
//		}
//		
//		freqStr = "(" + freqStr + ",'5','6','7','8') ";
//
//		sql += "and t.frequency in " + freqStr;
//		String month = date.substring(4, 6);
//		String logdate = date.substring(0, 6) + "00";
//		sql += "and exists(select l.id_rep from log_data_" + month
//				+ " l where l.id_rep=t.pkid and l.cd_organ='" + organId
//				+ "' and l.date_data='" + logdate + "' and l.mk_type='"
//				+ LogUtil.LogType_ReportBalanceOK + "')";
//
//		sql += "order by t.report_type,t.show_order";
//		List result = list(sql, new Object[][] { { "t", Report.class } }, null);
//
//		return result;
		return null;
	}

	// 2007-4-24 wsx
	public List getReportsByTypes(int buildOrCheck, String date,
			String[] types, Long userid) {
		String typeCond = "";
		for (int i = 0; i < types.length; i++) {
			if (i > 0) {
				typeCond += ",";
			}
			typeCond += types[i];
		}
		String sql = "select {t.*} from"
				+ " code_rep_report t ,code_rep_types rt"
				+ " where t.report_type = rt.pkid and t.begin_date < '" + date
				+ "' and t.end_date > '" + date
				+ "' and t.pkid in(select ur.repid"
				+ " from rep_oper_contrast ur where ur.operid =" + userid
				+ ") and t.status < 9 " + " and t.report_type in (" + typeCond
				+ ") ";
		if (buildOrCheck == 1) {
			sql += "and rt.data_source = '2' ";
		} else {
			sql += "and rt.is_balance = '1' ";
		}
		// 加入频度限制
		String freqStr = "";
		if ("03".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("09".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("12".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		// 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。
		freqStr = "(" + freqStr + ",'5','6','7','8') ";

		sql += "and t.frequency in " + freqStr;

		sql += "order by t.report_type,t.show_order";

		List result = list(sql, new Object[][] { { "t", Report.class } }, null);

		return result;
	}

	public List getItemByCode(Long reportId, String code) {
		String sql = "select {t.*} from code_rep_item t where t.report_id ="
				+ reportId + " and t.code like '%" + code
				+ "%' order by item_order,pkid";
		List result = list(sql, new Object[][] { { "t", ReportItem.class } },
				null);
		return result;
	}

	// 将报表列表中大于传入SHOWORDER值的记录的SHOWORDER值加1
	public void updateShowOrder(Integer showOrder) {
		String sql = "update code_rep_report set show_order = show_order+1 where show_order >"
				+ showOrder.intValue();
		this.jdbcUpdate(sql, null);
	}

	// 根据报表类型得到该类型下的SHOWORDER最大值
	public Integer getShowOrderByType(Long typeId) {
		String sql = "select MAX(r.showOrder) from Report r where r.reportType = "
				+ typeId.longValue();
		List show = list(sql);
		Iterator i = show.iterator();
		Integer temp = (Integer) i.next();
		if (temp != null) {
			return (temp);
		}

		// 若该类型下尚无报表时得到当前所有报表中的最大SHOWORDER值
		sql = "select MAX(r.showOrder) from Report r";
		show = list(sql);
		i = show.iterator();
		temp = (Integer) i.next();
		if (temp != null) {
			return (temp);
		}
		return (new Integer(0));
	}

	// 根据报表ID得到该报表中所有科目的ITEMORDER最大值
	public Integer getItemOrderByReport(Long reportId) {
		String sql = "select MAX(r.itemOrder) from ReportItem r where r.reportId = "
				+ reportId.longValue();
		List show = list(sql);
		Iterator i = show.iterator();
		Integer temp = (Integer) i.next();
		if (temp != null) {
			return (temp);
		}

		// 若该报表下尚无科目时返回0
		return (new Integer(0));
	}

	public Report getReportById(Long reportId) {
		String hql = "from Report t where t.pkid =" + reportId;
		List ls = list(hql);
		return (Report) ls.get(0);
	}

	public Integer getReportShowOrder(Long reportId) {
		String hql = "select t.showOrder from Report t where t.pkid ="
				+ reportId;
		List ls = list(hql);
		return (Integer) ls.get(0);
	}

	public Integer getItemOrder(Long itemId) {
		String hql = "select t.itemOrder from ReportItem t where t.pkid ="
				+ itemId;
		List ls = list(hql);
		return (Integer) ls.get(0);
	}

	public Integer getTargetOrderByReport(Long reportId) {
		String sql = "select MAX(r.targetOrder) from ReportTarget r where r.reportId = "
				+ reportId.longValue();
		List show = list(sql);
		Iterator i = show.iterator();
		Integer temp = (Integer) i.next();
		if (temp != null) {
			return (temp);
		}

		// 若该报表下尚无科目时返回0
		return (new Integer(0));
	}

	public Integer getTargetOrder(Long targetId) {
		String hql = "select t.targetOrder from ReportTarget t where t.pkid ="
				+ targetId;
		List ls = list(hql);
		return (Integer) ls.get(0);
	}

	public void updateItemShowOrder(Long reportId, Integer showOrder) {
		// gyl@20060829:在sql语句中增加修改日期项目.
		String sql = "update code_rep_item set item_order = item_order + 1, edit_date = '"
				+ DateUtil.getDateTime("yyyyMMdd", new Date())
				+ "' where report_id = ? and item_order >= ?";
		this.jdbcUpdate(sql, new Object[] { reportId, showOrder });
	}

	public Set getReps(String organ) {
		String sql = "SELECT reportid FROM code_orgtype_report WHERE organ_type ="
				+ " (SELECT organ_type FROM code_org_organ WHERE code = ?)";

		List ls = list(sql, null, new Object[][] { { "reportid",
				Hibernate.STRING } }, new Object[] { organ });

		Set repId = new HashSet();
		Iterator it = ls.iterator();
		for (int i = 0; it.hasNext(); i++) {
			Object o = it.next();
			repId.add(o);
		}
		return repId;
	}

	// 2006.9.26
	public boolean checkDataByItem(Long reportId) {
		boolean flag = false;
//		/*
//		 * �ֱ��޸� by ��Ծ�� @ 2006/10/31 StringBuffer sb = new StringBuffer("select
//		 * {t.*} from rep_data t where t.report_id = ") .append(reportId); List
//		 * ls = list(sb.toString(), new Object[][] { { "t", RepData.class } },
//		 * null);
//		 */
//
//		StringBuffer sb = new StringBuffer(
//				"select {t.*} from rep_data t where t.report_id = ")
//				.append(reportId);
//		List ls = rdd.list(sb.toString(), null);
//		if (ls.size() > 0)
//			flag = true;
//
//		if (!flag) {
//			sb = new StringBuffer(
//					"select {t.*} from rep_data1 t where t.report_id = ")
//					.append(reportId);
//			List lss = list(sb.toString(), new Object[][] { { "t",
//					RepData.class } }, null);
//			if (lss.size() > 0)
//				flag = true;
//		}
		return flag;
	}

	// 2006.9.27
	public void delItemByReportId(Long reportid) {
		StringBuffer sb = new StringBuffer(
				"delete from code_rep_item where report_id =?");
		jdbcUpdate(sb.toString(), new Object[] { reportid });
	}

	public void delTargetByReportId(Long reportid) {
		StringBuffer sb = new StringBuffer(
				"delete from code_rep_target where report_id =?");
		jdbcUpdate(sb.toString(), new Object[] { reportid });
	}

	public void delFormatByReportId(Long reportid) {
		StringBuffer sb = new StringBuffer(
				"delete from rep_format where report_id =?");
		jdbcUpdate(sb.toString(), new Object[] { reportid });
	}

	public void delXmlByReportId(Long reportid) {
		StringBuffer sb = new StringBuffer(
				"delete from cbrc_xml_rep where rep_id =?");
		jdbcUpdate(sb.toString(), new Object[] { reportid });
	}

	public void delOrgtypeByReportId(Long reportid) {
		StringBuffer sb = new StringBuffer(
				"delete from code_orgtype_report where reportid =?");
		jdbcUpdate(sb.toString(), new Object[] { reportid });
	}

	public void delRuleByReportId(Long reportid) {
		StringBuffer sb = new StringBuffer(
				"delete from import_rule where report_id =?");
		jdbcUpdate(sb.toString(), new Object[] { reportid });
	}

	public void delDataByReportId(Long reportid) {
		StringBuffer sb = new StringBuffer(
				"delete from rep_data where report_id =?");
		rdd.update(sb.toString(), new Object[] { reportid });
	}

	public void delData1ByReportId(Long reportid) {
		StringBuffer sb = new StringBuffer(
				"DELETE FROM rep_data1 WHERE report_id =?");
		jdbcUpdate(sb.toString(), new Object[] { reportid });

	}

	public void delDataByItemId(Long reportId, String itemId) {
//		String tableName = FormulaHelp.getPhyTable(reportId.intValue());
//		String sql = "DELETE FROM " + tableName
//				+ " WHERE report_id = ? AND item_id =?";
//		rdd.update(sql, new Object[] { reportId, itemId });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#delData1ByItemId(java
	 * .lang.Long, java.lang.String)
	 */
	public void delData1ByItemId(Long reportId, String itemId) {
		StringBuffer sb = new StringBuffer(
				"delete from rep_data1 where reportId = ? and item_id =?");
		jdbcUpdate(sb.toString(), new Object[] { reportId, itemId });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#delDataByTarget(java.
	 * lang.Long, int)
	 */
	public void delDataByTarget(Long reportId, int targetId) {
//		String tableName = FormulaHelp.getPhyTable(reportId.intValue());
//		StringBuffer sb = new StringBuffer("update " + tableName + " set ");
//		sb.append("itemvalue" + targetId + " = null ");
//		sb.append("where report_id = ? ");
//		rdd.update(sb.toString(), new Object[] { reportId });
	}

	public void delDataByTarget(Long reportid) {
		StringBuffer sb = new StringBuffer("update rep_data set ");
		for (int i = 1; i < 50; i++) {
			sb.append("itemvalue").append(i).append("= null , ");
		}
		sb.append("itemvalue50 = null ").append("where report_id = ? ");
		rdd.update(sb.toString(), new Object[] { reportid });
	}

	public void delData1ByTarget(Long reportid) {
		StringBuffer sb = new StringBuffer("update rep_data1 set ");
		for (int i = 1; i < 22; i++) {
			sb.append("itemvalue").append(i).append("= null , ");
		}
		sb.append("itemvalue22 = null ").append("where report_id = ? ");
		jdbcUpdate(sb.toString(), new Object[] { reportid });
	}

	public void saveTarget(ReportTarget target) {
		 getHibernateTemplate().merge(target);
		// 更新报表定义的列数。
		Object[][] scalaries = { { "cnt", Hibernate.INTEGER } };

		Integer maxColNum = new Integer(0);
		List result;
		if (target.getPkid() == null) {
			String sqlStr = "SELECT MAX(target_field) as cnt "
					+ "FROM code_rep_target WHERE report_id = ?";
			result = list(sqlStr, null, scalaries,
					new Object[] { target.getReportId() });
		} else {
			String sqlStr = "SELECT MAX(target_field) as cnt "
					+ "FROM code_rep_target WHERE report_id = ? and pkid <> ?";
			result = list(sqlStr, null, scalaries,
					new Object[] { target.getReportId(), target.getPkid() });
		}

		if (result.get(0) != null) {
			maxColNum = (Integer) result.get(0);
		}
		maxColNum = maxColNum.intValue() < Integer.parseInt(target
				.getTargetField()) ? new Integer(target.getTargetField())
				: maxColNum;

		String sql = "UPDATE code_rep_report SET rol = ? WHERE pkid = ?";
		jdbcUpdate(sql, new Object[] { maxColNum, target.getReportId() });
	}
	
	public Long insertTarget(ReportTarget target) {
		Long pkid =  (Long) getHibernateTemplate().save(target);//��ȡid
//		// 更新报表定义的列数。
//		Object[][] scalaries = { { "cnt", Hibernate.INTEGER } };
//
//		Integer maxColNum = new Integer(0);
//		List result;
//		if (target.getPkid() == null) {
//			String sqlStr = "SELECT MAX(target_field) as cnt "
//					+ "FROM code_rep_target WHERE report_id = ?";
//			result = list(sqlStr, null, scalaries,
//					new Object[] { target.getReportId() });
//		} else {
//			String sqlStr = "SELECT MAX(target_field) as cnt "
//					+ "FROM code_rep_target WHERE report_id = ? and pkid <> ?";
//			result = list(sqlStr, null, scalaries,
//					new Object[] { target.getReportId(), target.getPkid() });
//		}
//
//		if (result.get(0) != null) {
//			maxColNum = (Integer) result.get(0);
//		}
//		maxColNum = maxColNum.intValue() < Integer.parseInt(target
//				.getTargetField()) ? new Integer(target.getTargetField())
//				: maxColNum;
//
//		String sql = "UPDATE code_rep_report SET rol = ? WHERE pkid = ?";
//		jdbcUpdate(sql, new Object[] { maxColNum, target.getReportId() });
		return pkid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#cutDataByTarget(java.
	 * lang.Long, int, int)
	 */
	public void cutDataByTarget(Long reportId, int fromTargetId, int toTargetId) {
//		if (fromTargetId != toTargetId) {
//			String tableName = FormulaHelp.getPhyTable(reportId.intValue());
//			StringBuffer sb = new StringBuffer("UPDATE " + tableName + " SET ");
//			sb.append("itemvalue" + toTargetId + " = ");
//			sb.append("itemvalue" + fromTargetId + ",");
//			sb.append(" itemvalue" + fromTargetId + " = null");
//			sb.append(" WHERE report_id = ? ");
//			rdd.update(sb.toString(), new Object[] { reportId });
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#updateReportColNum(java
	 * .lang.Long, int)
	 */
	public void updateReportColNum(Long reportId, Integer num) {
		StringBuffer sb = new StringBuffer(
				"update code_rep_report set rol = ? where pkid = ?");
		jdbcUpdate(sb.toString(), new Object[] { num, reportId });
	}

	public boolean checkTargetOccupy(Long reportId, String targetField) {
		boolean flag = false;
		StringBuffer sb = new StringBuffer(
				"select {t.*} from code_rep_target t where t.report_id = ")
				.append(reportId).append(" and ").append("t.target_field = '")
				.append(targetField).append("' ");
		List ls = list(sb.toString(), new Object[][] { { "t",
				ReportTarget.class } }, null);
		if (ls.size() > 0)
			flag = true;

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#checkReportCode(java.
	 * lang.Long)
	 */
	public boolean checkReportCode(String code) {
		String hql = "from Report r where code = '" + code + "' ";
		List list = this.list(hql);
		if (list.size() > 0)
			return true;
		else
			return false;
	}

	// 2006.11.1 zhaoPC
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getReportsForStatExport
	 * (java.lang.String, java.lang.Long)
	 */
	public List getReportsForStatExport(String date, Long userId) {
		String hql = "Select r from Report r, ReportType t where r.reportType=t.pkid and r.status <> 9 "
				+ "and r.beginDate < '"
				+ date
				+ "' and r.endDate > '"
				+ date
				+ "' and r.pkid in (select ur.reportId from UserReport ur where ur.operId =:userId) "
				+ "and r.reportType IN (109,31) ";
		String freqStr = "";
		if ("03".equals(date.subSequence(4, 6))
				|| "09".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("12".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		freqStr = "'0'," + freqStr;
		// 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。
		freqStr = "(" + freqStr + ",'5','6','7','8') ";

		hql += " and r.frequency in " + freqStr;
		hql += "order by r.reportType,r.showOrder ";
		Map map = new HashMap();
		map.put("userId", userId);
		List list = this.list(hql, map);
		return list;
	}

	public List getReportsByLogType(String date, Long userId, String logType) {
		String sql = "select {r.*} from code_rep_report r, code_rep_types t where r.report_type=t.pkid and r.status <>9 "
				+ "and r.begin_date < '"
				+ date
				+ "' and r.end_date > '"
				+ date
				+ "' and r.pkid in (select ur.repid from rep_oper_contrast ur where ur.operid ="
				+ userId + ") " + "and r.report_type IN (109,31) ";
		String freqStr = "";
		if ("03".equals(date.subSequence(4, 6))
				|| "09".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("12".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		freqStr = "'0'," + freqStr;
		// 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。
		freqStr = "(" + freqStr + ",'5','6','7','8') ";

		sql += " and r.frequency in " + freqStr;

		String month = date.substring(4, 6);
		sql += "and exists(select l.id_rep from log_data_" + month
				+ " l where l.id_rep=r.pkid and l.mk_type='" + logType + "')";

		sql += "order by r.report_type,r.show_order";

		List result = list(sql, new Object[][] { { "r", Report.class } }, null);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getCarryReportId(java
	 * .lang.Long)
	 */
	public Long getCarryReportId(Long reportId) {
		String sql = "select r.pkid as pkId from code_rep_report r where r.con_code = "
				+ reportId + " and r.report_type = 30";
		Object[][] scalaries = { { "pkId", Hibernate.LONG } };
		List list = this.list(sql, null, scalaries);
		if (list.size() > 0) {
			return (Long) list.get(0);
		}
		return null;
	}

	public void updateUserReport(int reportId, int newType) {//  修改了报表类型，同时更新用户-报表关联表，
																// wsx 12-21

		String sql = "update rep_oper_contrast set typeid = " + newType
				+ " where repid = " + reportId;
		jdbcUpdate(sql, null);
	}

	/**
	 * 根据报表编号和日期来从“存放报表科目的列放在数据表的哪个物理列上”的表取出 有效的科目列表
	 */
	public List getReportTargetsByDate(Long reportId, String date) {
		String hql = "from ReportTarget t where t.status <> '"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " and t.beginDate <= '" + date + "' and t.endDate >= '"
				+ date + "'" + " order by t.targetOrder,t.pkid";
		Map map = new HashMap();
		return list(hql, map);
	}

	/**
	 * 根据报表编号，上期报表和当期报表的日期取得这个区间之内所有的科目列表 将科目启用日期 早于 上期报表日期 并且 科目注销日期 晚于
	 * 当期报表日期，并且报表编号相同的科目列表得到
	 * 
	 * @param reportId
	 * @param lastDate
	 * @param currDate
	 * @return
	 */
	public List getItemByCondition(Long reportId, String lastDate,
			String currDate) {
		String sql = "select {t.*} from code_rep_item t where t.report_id ="
				+ reportId + " and t.begin_date <= '" + lastDate
				+ "' and t.end_date >= '" + currDate + "'"
				+ " and t.status <> '" + Constants.STATUS_DEL
				+ "' order by code";
		List result = list(sql, new Object[][] { { "t", ReportItem.class } },
				null);
		return result;
	}

	public List getReportItemsExpCol(Long reportId, String date,
			String frequency, String col) {
		String sql = "SELECT I.CODE AS CODE,"
				+ col
				+ " AS EXPCOL FROM CODE_REP_ITEM I,CODE_REP_REPORT R,CODE_REP_TYPES T "
				+ "WHERE I.STATUS <> '9' AND R.STATUS <> 9 "
				+ "AND T.STATUS <> '9'  AND I.BEGIN_DATE < '"
				+ date
				+ "' AND I.END_DATE > '"
				+ date
				+ "' "
				+ "AND I.REPORT_ID = R.PKID AND R.REPORT_TYPE = T.PKID AND I.FREQUENCY = '"
				+ frequency + "' " + "AND R.PKID = " + reportId
				+ " ORDER BY I.ITEM_ORDER";
		Object[][] scalaries = { { "CODE", Hibernate.STRING },
				{ "EXPCOL", Hibernate.STRING } };

		return this.list(sql, null, scalaries);
	}

	// 2007.3.24
	public List getReportTarget(Long reportId, String targetField) {
		String hql = "from ReportTarget t where t.status <> '"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " and t.targetField ='" + targetField + "'"
				+ " order by t.targetOrder,t.pkid";
		Map map = new HashMap();
		return list(hql, map);
	}

	// 2007.3.29
	public List getExtItem(String reportId, String itemCode, String num) {
		List lst = new ArrayList();
		String hql = "from ReportItem t where t.status<>'"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " and t.code = '" + itemCode + "'";
		Map map = new HashMap();
		List temp = list(hql, map);
		if (temp.size() > 0) {
			ReportItem ri = (ReportItem) temp.get(0);
			Integer order = ri.getItemOrder();
			String sql = "from ReportItem t where t.status<>'"
					+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
					+ " and t.itemOrder > " + order
					+ " order by t.itemOrder asc ";
			Map map1 = new HashMap();
			List temp1 = list(sql, map1);
			if (temp1.size() >= Integer.parseInt(num)) {
				for (int i = 0; i < Integer.parseInt(num); i++) {
					lst.add((ReportItem) temp1.get(i));
				}
			}
		}

		return lst;
	}

	/**
	 * add by zhaoyi _20070331得到所有的报表
	 * 
	 * @return
	 */
	public List getAllReports() {
		String sql = "select {t.*} from code_rep_report t where t.status <> "
				+ Constants.STATUS_DEL + " order by show_order";
		List result = list(sql, new Object[][] { { "t", Report.class } }, null);
		return result;
	}

	public List getReports(String orgType, Long userId, String date,
			String repType) {
		String month = date.substring(4, 6);
		String freq = "1";
		if (month.equals("03") || month.equals("09"))
			freq = "2";
		else if (month.equals("06"))
			freq = "3";
		else if (month.equals("12"))
			freq = "4";
		String sql = "SELECT {r.*} FROM code_rep_report r "
				+ "WHERE r.pkid IN (SELECT t.reportid FROM code_orgtype_report t "
				+ "WHERE t.organ_type = " + orgType + " AND t.reportid IN "
				+ "(SELECT o.repid FROM rep_oper_contrast o "
				+ "WHERE o.operid = " + userId + " AND o.typeid = " + repType
				+ ")) " + "AND r.begin_date < '" + date
				+ "' AND r.end_date > '" + date + "' " + "AND r.frequency <= '"
				+ freq + "' AND r.status <> 9";
		List list = list(sql, new Object[][] { { "r", Report.class } }, null);
		if (list.size() > 0)
			return list;
		else
			return null;
	}

	public List getCurrencyReportTypes(int type, Long userid) {
		String hql = "from ReportType t where t.status <> '"
				+ Constants.STATUS_DEL
				// + " and t.pkid in(select r.reportType from Report r where
				// r.pkid
				// in(select distinct rc.reportId from ReportConfig rc where
				// rc.funId = 31))"
				+ "' and t.pkid in(select ur.typeId from UserReport ur where ur.operId =:userid)";

		if (type == 1) {
			hql += " and t.dataSource = '2' ";
		} else if (type == 2) {
			hql += " and t.isBalance = '1' and t.dataSource <>'9' ";
		} else if (type == 3) {// wsx 8-16
			hql += " and t.isCollect = '1' ";
		}
		hql += " order by t.showOrder,t.pkid ";
		Map map = new HashMap();
		map.put("userid", userid);
		List ls = list(hql, map);
		return ls;
	}

	public List getCurrencyReportsFrequency(int buildOrCheck, String date,
			Long type, Long userid) {
		String sql = "select {t.*} from"
				+ " code_rep_report t ,code_rep_types rt"
				+ " where t.report_type = rt.pkid and t.begin_date < '"
				+ date
				+ "' and t.end_date > '"
				+ date
				+ "' and t.pkid in(select ur.repid"
				+ " from rep_oper_contrast ur where ur.operid ="
				+ userid
				+ ") and t.status < 9 "
				+ " and t.pkid in(select distinct rc.report_id from code_rep_config rc where rc.fun_id = 31)"
				+ " and t.report_type = " + type + " ";
		/*
		 * if (buildOrCheck == 1) { sql += "and rt.data_source = '2' "; } else {
		 * sql += "and rt.is_balance = '1' "; } ; if
		 * ("03".equals(date.substring(4,6))){ freqStr = "'1','2'"; } else if
		 * ("06".equals(date.substring(4,6))){ freqStr = "'1','2','3'"; } else
		 * if ("09".equals(date.substring(4,6))){ freqStr = "'1','2'"; } else if
		 * ("12".equals(date.substring(4,6))){ freqStr = "'1','2','3','4'"; }
		 * else { freqStr = "'1'"; } 
		 * freqStr = "(" + freqStr + ",'5','6','7','8') ";
		 * 
		 * sql += "and t.frequency in " + freqStr;
		 */

		sql += "order by t.report_type,t.show_order";
		List result = list(sql, new Object[][] { { "t", Report.class } }, null);

		return result;
	}

	public ReportType getReportTypeByReportId(Long reportId) {
		String sql = "select {t.*} from code_rep_types t where t.pkid =("
				+ " select rt.report_type from code_rep_report rt where pkid="
				+ reportId + ")";
		List result = list(sql, new Object[][] { { "t", ReportType.class } },
				null);
		return (ReportType) result.get(0);
	}

	// add by zh
	public Long getNewReportId(Long leastId, Long maximalId, Long reportType) {
		String sql = "select "
				+ DBDialect.getCoalesce("max(t.pkid)+1", leastId.toString())
				+ " as newId from code_rep_report t " + "where t.status <>"
				+ Constants.STATUS_DEL + " and t.report_type=" + reportType
				+ " and " + "" + leastId + "<=t.pkid and t.pkid<=" + maximalId;
		Object[][] scalaries = { { "newId", Hibernate.LONG } };
		List list = this.list(sql, null, scalaries);
		if (list.size() > 0) {
			return (Long) list.get(0);
		}
		return null;
	}

	public void updatePkidByCode(String code, Long pkid) {
		String sql = "update code_rep_report set pkid = " + pkid
				+ " where code = '" + code + "'";
		jdbcUpdate(sql, null);
	}

	public Report getReportByCode(String code) {
		String hql = "from Report t where t.code ='" + code + "'";
		List ls = list(hql);
		if (ls.isEmpty()) {
			return null;
		}
		return (Report) ls.get(0);
	}

	public void copyItems(Long newRepid, Long oldRepid) {
		String Sql = null;
		if ('s' == SysConfig.DB) {
			Sql = "insert into code_rep_item(report_id, code, item_name, item_order, frequency,"
					+ "is_collect, is_org_collect, super_id, is_leaf, begin_date, end_date, create_date,"
					+ " edit_date, status, con_code, data_type) select "
					+ newRepid
					+ ", code, item_name, item_order,"
					+ "frequency, is_collect, is_org_collect, super_id, is_leaf, begin_date, end_date, create_date,"
					+ " edit_date, status, con_code, data_type from code_rep_item where report_id="
					+ oldRepid;
		} else {
			Sql = "insert into code_rep_item(pkid, report_id, code, item_name, item_order, frequency,"
					+ "is_collect, is_org_collect, super_id, is_leaf, begin_date, end_date, create_date,"
					+ " edit_date, status, con_code, data_type) select "
					+ DBDialect.genSequence("code_rep_item_seq")
					+ ","
					+ ""
					+ newRepid
					+ ", code, item_name, item_order,"
					+ "frequency, is_collect, is_org_collect, super_id, is_leaf, begin_date, end_date, create_date,"
					+ " edit_date, status, con_code, data_type from code_rep_item where report_id="
					+ oldRepid;
		}
		jdbcUpdate(Sql, null);
	}

	public void copyTargets(Long newRepid, Long oldRepid) {
		String Sql = null;
		if ('s' == SysConfig.DB) {
			Sql = "insert into code_rep_target(report_id, target_name, target_field, target_order,"
					+ "begin_date, end_date, create_date, edit_date, status,data_type)"
					+ " select "
					+ newRepid
					+ ", target_name, target_field, target_order,"
					+ "begin_date, end_date, create_date, edit_date, status,data_type "
					+ "from code_rep_target where report_id=" + oldRepid;
		} else {
			Sql = "insert into code_rep_target(pkid, report_id, target_name, target_field, target_order,"
					+ "begin_date, end_date, create_date, edit_date, status,data_type)"
					+ " select "
					+ DBDialect.genSequence("code_rep_target_seq")
					+ ","
					+ newRepid
					+ ", target_name, target_field, target_order,"
					+ "begin_date, end_date, create_date, edit_date, status,data_type "
					+ "from code_rep_target where report_id=" + oldRepid;
		}
		jdbcUpdate(Sql, null);
	}

	/**
	 * 得到该机构下统计员用户时候有权限查看该报表
	 * 
	 * @param organId
	 * @param reportId
	 * @return
	 */
	public boolean isRepUser(String organId, Long reportId, Long reportType) {
		String pro = FuncConfig.getCNProperty("statExport.role", "3");
		String sql = "select {c.*} from rep_oper_contrast c where c.operid in ( "
				+ "select u.pkid from umg_user u , umg_user_role r "
				+ "where u.organ_id = '"
				+ organId
				+ "' and u.pkid = r.user_id and r.role_type = "
				+ pro
				+ ") and c.typeid = "
				+ reportType
				+ " and c.repid = "
				+ reportId;
		List result = list(sql, new Object[][] { { "c", UserReport.class } },
				null, null);
		if (result.size() == 0)
			return false;
		else
			return true;
	}

	/**
	 * 得到该机构下统计员用户时候有权限查看该报表
	 * 
	 * @param organId
	 * @param reportId
	 * @return
	 */
	public boolean isRepUser1(String organId, Long reportId, Long reportType,
			String transferflag) {

		String pro = FuncConfig.getCNProperty("statExport.role", "3");
		if ("new".equals(transferflag)) {
			pro = FuncConfig.getProperty("statExport.role.new");
		}
		String sql = "select {c.*} from rep_oper_contrast c where c.operid in ( "
				+ "select u.pkid from umg_user u , umg_user_role r "
				+ "where u.organ_id = '"
				+ organId
				+ "' and u.pkid = r.user_id and r.role_type = "
				+ pro
				+ ") and c.typeid = "
				+ reportType
				+ " and c.repid = "
				+ reportId;
		List result = list(sql, new Object[][] { { "c", UserReport.class } },
				null, null);
		if (result.size() == 0)
			return false;
		else
			return true;
	}

	/**
	 *得到数据表表名
	 */
	public List<String> getDataTable() {
//		String sql = "select distinct f.tab_name AS tableName from code_table_field f";
//		Object[][] scalaries = { { "tableName", Hibernate.STRING } };
//		List result = list(sql, null, scalaries, null);
		List<String> result = new ArrayList<String>();
		Session session = null;
		ResultSet rs = null;
		String schema = FuncConfig.getProperty("jdbc.connection.schema");
		String tablename = FuncConfig.getProperty("jdbc.connection.tablename");
		try {
			session = this.getSession();
			DatabaseMetaData md = session.connection().getMetaData();
			rs = md.getTables(null, schema, tablename, new String [] {"TABLE"});
			while (rs.next())
			{
			    String tableName = rs.getString(3);
			    result.add(tableName);
			}
		} catch (Exception e) {
			logger.error("查询错误：", e);
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("查询错误：", e);
				}
			}
		}
		return result;
	}

	/**
	 * 根据表名得到数据列名称
	 * 
	 * @param tabName
	 * @return
	 */
	public List getDataField(String tabName) {
		String sql = "select {f.*} from code_table_field f where tab_name = '"
				+ tabName + "'";
		List result = list(sql, new Object[][] { { "f", TableField.class } },
				null, null);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getFreqReport(java.lang
	 * .String, java.lang.String)
	 */
	public List getFreqReport(String freq, String reporttype) {
		StringBuffer hql = new StringBuffer(
				"select {r.*} from code_rep_report r ")
				.append("where r.status<>9 and ").append(" r.frequency = '")
				.append(freq + "' ");

		// 频度的判断有页面选项给传过来
		String reptype = "";
		reporttype = Util.coverFilterQuotesForDB2(reporttype);
		reptype = "(" + reporttype + ")";
		hql = hql.append(" and r.report_type in ").append(reptype);

		// 排序
		hql = hql.append(" order by r.report_type,r.show_order");
		List ls = list(hql.toString(),
				new Object[][] { { "r", Report.class } }, null);
		return ls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getReportItems(java.lang
	 * .Long, java.lang.String,java.lang.String) 2007.07.09 lxk ���ں���ͳ�Ʒ����ѯ����
	 */
	public List getReportItems(Long reportId, String curDate, String datePB) {
		String hql = "";
		if (reportId.longValue() != 0) {
			hql = "from ReportItem t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and t.beginDate < :curDate and t.endDate > :datePB and t.reportId = "
					+ reportId + " order by t.itemOrder,t.pkid";
		} else {
			hql = "select i from ReportItem i,Report r,ReportType t where i.status<>'9' and r.status<>9 and t.status<>'9' and i.beginDate < :date and i.endDate > :date and i.reportId=r.pkid and r.reportType=t.pkid and r.pkid="
					+ reportId + " order by i.itemOrder";
		}
		Map map = new HashMap();
		map.put("curDate", curDate);
		map.put("datePB", datePB);
		return list(hql, map);
	}

	public List getReportByOrganType(OrganInfo organInfo) {

		/*
		 * String sql = "select {format.*} from rep_format
		 * format,code_rep_report report,code_orgtype_report
		 * org_type,code_org_organ organ where
		 * org_type.reportid=format.report_id and format.report_id=report.pkid
		 * and org_type.organ_type=organ.organ_type and org_type.organ_type="
		 * +organInfo.getOrgan_type().intValue() + " and organ.pkid=" +
		 * organInfo.getPkid().longValue(); List l = list(sql, new Object[][] {
		 * { "format", ReportFormat.class } }, null); return l;
		 */

		String sql = "select  format.pkid,format.report_id,format.report_format,format.begin_date,format.end_date,format.create_date,format.edit_date,report.name,report.frequency from rep_format format,code_rep_report report,code_orgtype_report org_type,code_org_organ organ where org_type.reportid=format.report_id and format.report_id=report.pkid and org_type.organ_type=organ.organ_type and org_type.organ_type="
				+ organInfo.getOrgan_type().intValue()
				+ " and organ.pkid="
				+ organInfo.getPkid().longValue();

		Object[][] scalaries = { { "pkid", Hibernate.LONG },
				{ "report_id", Hibernate.LONG },
				{ "report_format", Hibernate.STRING },
				{ "begin_date", Hibernate.STRING },
				{ "end_date", Hibernate.STRING },
				{ "create_date", Hibernate.STRING },
				{ "edit_date", Hibernate.STRING },
				{ "name", Hibernate.STRING }, { "frequency", Hibernate.STRING } };
		List fl = list(sql, null, scalaries);
		/*
		 * String hql = "select new ReportFormat(rf,r.name,r.frequency)" + "
		 * from ReportFormat rf, Report r where rf.reportId = r.pkid" + " and
		 * " + " order by r.reportType,r.showOrder";
		 */
		List formatList = new ArrayList();
		Iterator it = fl.iterator();
		while (it.hasNext()) {
			Object[] row = (Object[]) it.next();
			ReportFormat rf = new ReportFormat();
			rf.setPkId((Long) row[0]);
			rf.setReportId((Long) row[1]);
			rf.setReportFormat((String) row[2]);
			rf.setBeginDate((String) row[3]);
			rf.setEndDate((String) row[4]);
			rf.setCreateDate((String) row[5]);
			rf.setEditDate((String) row[6]);
			rf.setReportName((String) row[7]);
			rf.setFrequency((String) row[8]);
			formatList.add(rf);
		}
		return formatList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getPdfReportsByOrganType
	 * (com.krm.slsint.organmanage.vo.OrganInfo)
	 */
	public List getPdfReportsByOrganType(OrganInfo organInfo, String date) {

		String sql = "SELECT {r.*} FROM code_rep_report r, code_orgtype_report otr, code_rep_config cfg"
				+ "      WHERE r.pkid = otr.reportid and otr.organ_type = ? and "
				+ "            r.begin_date <= ? and r.end_date >= ? and r.status <> ? "
				+ "            and cfg.report_id = r.pkid and cfg.fun_id = ? and cfg.idx_id = ? "
				+ "            and cfg.def_int = ? ";
		String freq = "";
		String month = date.substring(4, 6);
		if (month.equals("12")) {
			freq = "'1','2','3','4'";
		} else if (month.equals("06")) {
			freq = "'1','2','3'";
		} else if (month.equals("03") || month.equals("09")) {
			freq = "'1','2'";
		} else {
			freq = "'1'";
		}
		sql += "and r.frequency in (" + freq + ")";
		Object[][] entity = new Object[][] { { "r", Report.class } };
		Object[] values = new Object[] { organInfo.getOrgan_type(), date, date,
				Constants.STATUS_DEL, "32", "1", "0" };
		return list(sql, entity, null, values);
	}

	public List getAutoPdfByOrganType(String organType, String date) {

		String sql = "SELECT {r.*} FROM code_rep_report r, code_orgtype_report otr, code_rep_config cfg"
				+ "      WHERE r.pkid = otr.reportid and otr.organ_type = ? and "
				+ "            r.begin_date <= ? and r.end_date >= ? and r.status <> ? "
				+ "            and cfg.report_id = r.pkid and cfg.fun_id = ? and cfg.idx_id = ? "
				+ "            and cfg.def_int = ? ";
		String freq = "";
		String month = date.substring(4, 6);
		if (month.equals("12")) {
			freq = "'1','2','3','4'";
		} else if (month.equals("06")) {
			freq = "'1','2','3'";
		} else if (month.equals("03") || month.equals("09")) {
			freq = "'1','2'";
		} else {
			freq = "'1'";
		}
		sql += "and r.frequency in (" + freq + ")";
		Object[][] entity = new Object[][] { { "r", Report.class } };
		Object[] values = new Object[] { organType, date, date,
				Constants.STATUS_DEL, "32", "1", "0" };
		return list(sql, entity, null, values);
	}

	public List getAutoBuildReportsByOrganType(String organType, String date) {
		String sql = "SELECT {r.*} FROM code_rep_report r, code_orgtype_report otr, code_rep_types t"
				+ "      WHERE r.pkid = otr.reportid and otr.organ_type = ? and "
				+ "            r.begin_date <= ? and r.end_date >= ? and r.status <> ? and"
				+ "            t.data_source = ? and t.pkid = r.report_type";
		Object[][] entity = new Object[][] { { "r", Report.class } };
		Object[] values = new Object[] { new Integer(organType), date, date,
				Constants.STATUS_DEL, "2" };
		return list(sql, entity, null, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getReportTarget(java.
	 * lang.String)
	 */
	public List getReportTarget(String tableName) {
		String sql = "select {r1.*} from code_rep_report r1 where r1.pkid in ( "
				+ "select t.report_id from code_rep_target t "
				+ "where report_id in ( select pkid from code_rep_report r "
				+ "where r.phy_table = '" + tableName + "'))";
		List result = list(sql, new Object[][] { { "r1", Report.class } },
				null, null);
		return result;
	}

	/**
	 * 得到智能流程显示报表(关联日期,频度,用户,机构)
	 * 
	 * @param date
	 * @param userId
	 * @param organId
	 * @return
	 */
	public List getFlowTipReport(String date, Long userId, String organId) {
		String sql = "select {r.*} from umg_user u, rep_oper_contrast c, code_rep_report r "
				+ "where u.pkid = "
				+ userId
				+ " and c.operid = u.pkid and c.repid in ( "
				+ "select reportid from code_org_organ o, code_orgtype_report t "
				+ "where o.code = '"
				+ organId
				+ "' and o.organ_type = t.organ_type) "
				+ "and r.pkid = c.repid and r.status <> 9  "
				+ "and r.begin_date < '"
				+ date
				+ "' and r.end_date > '"
				+ date
				+ "'";
		String freq = "";
		String month = date.substring(4, 6);
		if (month.equals("12")) {
			freq = "'1','2','3','4'";
		} else if (month.equals("06")) {
			freq = "'1','2','3'";
		} else if (month.equals("03") || month.equals("09")) {
			freq = "'1','2'";
		} else {
			freq = "'1'";
		}
		sql += "and r.frequency in (" + freq + ")";
		List result = list(sql, new Object[][] { { "r", Report.class } }, null,
				null);
		return result;
	}

	/**
	 * 智能流程提醒定义步骤显示报表
	 */
	public List getAddStepReportList(String date, Long userId) {
		String sql = "select {r.*} from umg_user u, rep_oper_contrast c, code_rep_report r "
				+ "where u.pkid = "
				+ userId
				+ " and c.operid = u.pkid "
				+ "and r.pkid = c.repid and r.status <> 9  "
				+ "and r.begin_date <= '"
				+ date
				+ "' and r.end_date >= '"
				+ date + "'";
		List result = list(sql, new Object[][] { { "r", Report.class } }, null,
				null);
		return result;
	}

	// 查询报表,按照创建者过滤报表
	public List getReportByAuthor(String date, Long userId, Long reportType) {
		String sql = "select {r.*} from code_rep_report r where r.begin_date < '"
				+ date
				+ "' and r.end_date > '"
				+ date
				+ "' and r.status <> 9 "
				+ "and pkid in (select c.repid from rep_oper_contrast c where operid = "
				+ userId
				+ ") and r.createid = "
				+ userId
				+ " and r.report_type = " + reportType;
		return this.list(sql, new Object[][] { { "r", Report.class } }, null,
				null);
	}

	public ReportItem getItem(Long reportId, String code) {
		String sql = "select {i.*} from code_rep_item i where report_id = "
				+ reportId + " and code = '" + code + "'";
		List result = list(sql, new Object[][] { { "i", ReportItem.class } },
				null, null);
		if (result.size() > 0) {
			return (ReportItem) result.get(0);
		} else {
			return null;
		}
	}

	/**
	 *得到admin用户创建的报表
	 * 
	 * @param userId
	 * @return
	 */
	public List getAdminCreateReport(String userId) {
		String sql = "select {r.*} from code_rep_report r where (r.createid is NULL or r.createid in ("
				+ userId + ")) and status <> 9";
		// System.out.println(sql);
		List result = list(sql, new Object[][] { { "r", Report.class } }, null,
				null);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getReportsByIds(java.
	 * lang.String)
	 */
	public Map getReportsByIds(String reportIds) {
		HashMap repMap = new HashMap();
		reportIds = Util.coverFilterQuotesForDB2(reportIds);
		String sql = "SELECT {r.*} FROM code_rep_report r "
				+ "WHERE r.status <> 9 AND r.pkid IN (" + reportIds + ")";
		List repList = list(sql, new Object[][] { { "r", Report.class } }, null);
		for (Iterator it = repList.iterator(); it.hasNext();) {
			Report rep = (Report) it.next();
			repMap.put(rep.getPkid().toString(), rep);
		}
		return repMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getReportsForOrgan(java
	 * .lang.String, java.lang.String)
	 */
	public List getReportsForOrgan(String organCode, String reportDate) {
		String sql = "select {r.*} from code_rep_report r, code_org_organ o, "
				+ "code_orgtype_report ot where r.begin_date < ? and "
				+ "r.end_date > ? and r.status <> ? and o.code = ? and "
				+ "ot.organ_type = o.organ_type and r.pkid = ot.reportid and "
				+ "r.frequency in (" + guessTerms(reportDate) + ")";
		List resultList = list(sql, new Object[][] { { "r", Report.class } },
				null, new Object[] { reportDate, reportDate, new Integer(9),
						organCode });
		return resultList;
	}

	public List getReportsForOrgan(String organCode, String reportDate,
			String restrictList) {
		String sql = "select {r.*} from code_rep_report r, code_org_organ o, "
				+ "code_orgtype_report ot where r.begin_date < ? and "
				+ "r.end_date > ? and r.status <> ? and o.code = ? and "
				+ "ot.organ_type = o.organ_type and r.pkid = ot.reportid and "
				+ "r.frequency in (" + guessTerms(reportDate)
				+ ") and r.pkid in (" + restrictList + ")";
		List resultList = list(sql, new Object[][] { { "r", Report.class } },
				null, new Object[] { reportDate, reportDate, new Integer(9),
						organCode });
		return resultList;
	}

	/**
	 * 根据日期判断频度范围
	 * 
	 * @param date
	 *            日期（格式：yyyyMMdd）
	 * @return 符合该日期的所有频度的列表（只考虑月以上的频度）
	 */
	private String guessTerms(String date) {
		String freq;
		String month = date.substring(4, 6);
		if (month.equals("12")) {
			freq = "'1','2','3','4'";
		} else if (month.equals("06")) {
			freq = "'1','2','3'";
		} else if (month.equals("03") || month.equals("09")) {
			freq = "'1','2'";
		} else {
			freq = "'1'";
		}
		return freq;
	}

	/**
	 * add by lxk 2008.02.23 用于广东二期数据采集时，报表id的对照 根据报表con_code得到报表对象 返回Report vo
	 */
	public Report getReportByConCode(String conCode) {
		System.out.println("conCode is : " + conCode);
		String hql = "from Report t where t.conCode = '" + conCode + "'";
		List ls = list(hql);
		if (ls.size() > 0) {
			return (Report) ls.get(0);
		} else {
			return (Report) ls.get(0);
		}

	}

	public List getReportsByFunction(String userId, String date, String funId,
			String def) {
		String sql = "select {t.*} from"
				+ " code_rep_report t "
				+ " where  t.begin_date < '"
				+ date
				+ "' and t.end_date > '"
				+ date
				+ "' and t.pkid in(select ur.repid"
				+ " from rep_oper_contrast ur where ur.operid ="
				+ userId
				+ ") and t.status < 9 "
				+ " and t.pkid  in(select distinct rc.report_id from code_rep_config rc where rc.fun_id = "
				+ funId + " and rc.def_int = " + def + " )";

		// 加入频度限制
		String freqStr = "";
		if ("03".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("09".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("12".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		sql += "and t.frequency in (" + freqStr + ")";
		sql += "order by t.report_type,t.show_order";
		List result = list(sql, new Object[][] { { "t", Report.class } }, null);

		return result;
	}

	/**
	 * 根据条件判断是否有符合条件的报表
	 * 
	 * @param paramDate
	 *            日期
	 * @param organ
	 *            机构编码
	 * @param canEdit
	 * @param userId
	 * @return boolean
	 */
	public boolean isReportExist(String paramDate, String organ,
			String canEdit, Long userId) {
		String freqStr = "";
		// 2006.12.4查询速度优化：本表条件、数据量由大到小表限制条件
		StringBuffer hql = new StringBuffer(
				"select r.pkid as pkID from code_rep_report r ")
				.append("where r.status<>9 and ").append("r.begin_date < '")
				.append(paramDate).append("' and ").append("r.end_date > '")
				.append(paramDate).append("'");

		// 频度的判断
		if ("03".equals(paramDate.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(paramDate.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("09".equals(paramDate.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("12".equals(paramDate.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		freqStr = "'0'," + freqStr;
		// 2007.4.11修改，5：日报；6：旬报；7：周报；8：5日报，全部显示。
		freqStr = "(" + freqStr + ",'5','6','7','8') ";
		hql = hql.append(" and r.frequency in ").append(freqStr);
		// 加入操作员、机构限制
		hql = hql
				.append(" and r.pkid in (select u.repid from rep_oper_contrast u where u.operid = ")
				.append(userId)
				.append(" ) ")
				.append(" and r.pkid in (select t.reportid from code_orgtype_report t where t.organ_type = (")
				.append(" select organ_type from code_org_organ where code = '")
				.append(organ).append("' )) ");

		// 判断编辑标识
		if ("0".equals(canEdit)) {// 查询
			hql = hql
					.append("and r.report_type in (select rt.pkid from code_rep_types rt where rt.data_source <>'9' and ")
					.append("rt.status <> '").append(Constants.STATUS_DEL)
					.append("') ");
			// 判断报表格式是否存在
			hql = hql
					.append("and r.pkid in (select rf.report_id from rep_format rf where rf.report_id=r.pkid and ")
					.append("rf.begin_date < '").append(paramDate)
					.append("' and ").append("rf.end_date > '")
					.append(paramDate).append("' ) ");
		}

		if ("1".equals(canEdit)) {// 录入
			hql = hql
					.append("and r.report_type in (select rt.pkid from code_rep_types rt where (rt.data_source='3' or rt.is_input='1') and ")
					.append("rt.status <> '").append(Constants.STATUS_DEL)
					.append("') ");
			// 判断报表格式是否存在
			hql = hql
					.append("and r.pkid in (select rf.report_id from rep_format rf where rf.report_id=r.pkid and ")
					.append("rf.begin_date < '").append(paramDate)
					.append("' and ").append("rf.end_date > '")
					.append(paramDate).append("' ) ");
		}
		// 排序
		hql = hql.append("  order by r.report_type,r.show_order");
		List ls = list(hql.toString(), null, new Object[][] { { "pkID",
				Hibernate.STRING } });

		return (ls != null && ls.size() > 0);
	}

	public String getMaxCodeByReport(Long reportId) {
		/*
		 * String sql =
		 * "select MAX(r.code) from ReportItem r where r.reportId = " +
		 * reportId;
		 */
		String sql = "select r.code from ReportItem r where r.reportId = "
				+ reportId
				+ " and length(r.code) < 4 order by length(r.code) desc,r.code desc";// ע��:ֻ��codeС��4λ�������
		List show = list(sql);
		String temp = null;
		if (show.size() > 0) {
			temp = (String) show.get(0);
		}

		if (temp != null) {
			return (temp);
		}
		return (0 + "");
	}

	public List getItemByRepId(Long reportId) {
		String sql = "select {t.*} from code_rep_item t where t.report_id ="
				+ reportId + "  and t.status <> '" + Constants.STATUS_DEL
				+ "' order by item_order";
		List result = list(sql, new Object[][] { { "t", ReportItem.class } },
				null);
		return result;
	}

	/**
	 * @see com.krm.slsint.reportdefine.dao.ReportDefineDAO#getReport(java.lang.String,
	 *      java.lang.Long, java.lang.String)
	 */
	public Report getReport(String code, Long reportType, String date) {
		String sql = "SELECT {r.*} FROM code_rep_report r WHERE code = '"
				+ code + "' AND report_type = " + reportType
				+ " AND status <> 9 AND begin_date < '" + date
				+ "' AND end_date > '" + date + "'";
		log.info("SQL===============" + sql);
		List result = list(sql, new Object[][] { { "r", Report.class } }, null);
		if (result.size() > 0) {
			return (Report) result.get(0);
		} else {
			return null;
		}
	}

	/**
	 * @see com.krm.slsint.reportdefine.dao.ReportDefineDAO#getCanInputReportIdList(java.lang.String)
	 */
	public List getCanInputReportIdList(String reportIds) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select r.pkid from code_rep_report r")
				.append(" where r.report_type "
						+ " in (select rt.pkid from code_rep_types rt where "
						+ " rt.is_input='1' " + // 注意:只对code小于4位的情况处理
						" and ").append("rt.status <> '")
				.append(Constants.STATUS_DEL).append("') ")
				.append(" and r.pkid in (").append(reportIds).append(")");
		Object[][] scalars = new Object[][] { { "pkid", Hibernate.LONG } };
		log.info("查询是否可录入的SQL为:" + buffer.toString());
		return list(buffer.toString(), null, scalars);
	}

	public List getReportsByCodes(String pkids, String date, Long userid) {

		String freqStr = "";
		if ("03".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("06".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3'";
		} else if ("09".equals(date.substring(4, 6))) {
			freqStr = "'1','2'";
		} else if ("12".equals(date.substring(4, 6))) {
			freqStr = "'1','2','3','4'";
		} else {
			freqStr = "'1'";
		}
		freqStr = "(" + freqStr + ",'5','6','7','8') ";
		if (!pkids.equals("")) {

			String hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.pkid in("
					+ pkids
					+ ") and r.frequency in "
					+ freqStr
					+ " and r.pkid in(select ur.reportId from UserReport ur where ur.operId ="
					+ userid
					+ ") and r.status <>"
					+ Constants.STATUS_DEL
					+ " and r.reportType = t.pkid and r.pkid <>0 ";
			List ls = list(hql);
			return ls;
		} else {
			return null;
		}

	}

	/**
	 * 根据配置表得到（上期，年初，上年同期审核）列值
	 * 
	 * @param checkMode
	 * @param reportId
	 * @return
	 */
	public List getTargetsByConfig(int checkMode, Long reportId, String idx) {
		/*
		 * Object[][] scalaries = {{"targetId",Hibernate.INTEGER},
		 * {"targetName",Hibernate.STRING}}; String sql =
		 * "select ct.target_field as targetId,ct.target_name as targetName from code_rep_target ct where ct.pkid in (select t.def_int from code_rep_config t where t.report_id="
		 * +reportId +" and t.fun_id="+checkMode+")";
		 */
		Object[][] scalaries = { { "formulaCheck", Hibernate.STRING } };

		String sql = "select t.description as formulaCheck from code_rep_config t where t.report_id="
				+ reportId + " and t.fun_id=" + checkMode;
		if (idx != null && !idx.equals("")) {
			sql = "select t.description as formulaCheck from code_rep_config t where t.report_id="
					+ reportId
					+ " and t.fun_id="
					+ checkMode
					+ " and t.idx_id=" + idx;
		}
		List resultList = list(sql, null, scalaries);
		return resultList;
	}

	public Map getReportMap() {
		Map map = new HashMap();
		String sql = "select {r.*} from code_rep_report r";
		List result = list(sql, new Object[][] { { "r", Report.class } }, null,
				null);
		for (Iterator itr = result.iterator(); itr.hasNext();) {
			Report r = (Report) itr.next();
			map.put(r.getPkid(), r);
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.dao.ReportDefineDAO#getReportsOrderByType
	 * (java.lang.String, java.lang.Long)
	 */
	public List getReportsOrderByType(String date, Long userid) {
		Map map = new HashMap();
		String hql = "";
		// 根据日期及用户权限过滤报表
		if (date != null) {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.pkid in(select ur.reportId from UserReport ur where ur.operId =:userid) and r.status <>"
					+ Constants.STATUS_DEL;
			map.put("userid", userid);
			if (date.matches("^\\d{8}$")) {
				// piliang add comment for reading 2010-3-26 下午05:38:28
				// 这里加入日期的过滤
				hql += " and r.beginDate<:date and r.endDate>:date";
				map.put("date", date);
			}
			hql += " and r.reportType = t.pkid and r.pkid <>0 ";
		}
		// 不过滤的情况(在报表、科目、公式定义时使用)
		else if (userid == null) {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL
					+ " and r.reportType = t.pkid and r.pkid <>0";
		}
		// 得到所有报表（当前只在为用户分配权限时使用该方法）
		else {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL + " and r.reportType = t.pkid ";
		}
		return list(hql + " order by t.showOrder", map);
	}

	public List getTypes(StringBuffer typeid) {
		String hql = null;

		if (typeid == null || "".equals(typeid.toString().trim())) {

			hql = "select {t.*} from code_rep_types t where t.pkid in(select r.report_type from code_rep_report r where r.status<>9) and t.status<>'9'";

		} else {

			hql = "select {t.*} from code_rep_types t where t.pkid in(select r.report_type from code_rep_report r where r.pkid in("
					+ typeid + ") and r.status<>9) and t.status<>'9'";
		}

		List ls = list(hql, new Object[][] { { "t", ReportType.class } }, null);
		return ls;
	}

	public List getReportsToListByIds(String reportIds, String reportDate) {
		reportIds = Util.coverFilterQuotesForDB2(reportIds);
		String sql = "SELECT {r.*} FROM code_rep_report r "
				+ "WHERE r.status <> 9 AND r.end_date >='" + reportDate
				+ "' AND r.pkid IN (" + reportIds + ") order by r.name asc";
		List repList = list(sql, new Object[][] { { "r", Report.class } }, null);

		return repList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.reportdefine.dao.ReportDefineDAO#
	 * getFirstReportOrderByReportType(java.lang.String, java.lang.Long)
	 */
	public Report getFirstReportOrderByReportType(String dataDate, Long userId) {
		String date = dataDate.replaceAll("-", "");
		String sql = "";
		if (SysConfig.DB == 'd') {
			sql = "SELECT {r.*} FROM code_rep_report r "
					+ "WHERE r.status <>9 and r.report_type ="
					+ "(select t.pkid from code_rep_types t where t.data_source <>'9' and t.status <> '9' and t.pkid in"
					+ "(select ur.typeid from rep_oper_contrast  ur where ur.operid ="
					+ userId
					+ ") order by t.show_order  FETCH FIRST 1 ROWS ONLY)"
					+ " and r.begin_date<'" + date + "' and r.end_date>'"
					+ date + "' order by r.show_order  FETCH FIRST 1 ROWS ONLY";
		} else {
			sql = "SELECT top 1 {r.*} FROM code_rep_report r "
					+ "WHERE r.status <>9 and r.report_type ="
					+ "(select top 1 t.pkid from code_rep_types t where t.data_source <>'9' and t.status <> '9' and t.pkid in"
					+ "(select ur.typeid from rep_oper_contrast  ur where ur.operid ="
					+ userId + ") order by t.show_order )"
					+ " and r.begin_date<'" + date + "' and r.end_date>'"
					+ date + "' order by r.show_order ";
		}

		List repList = list(sql, new Object[][] { { "r", Report.class } }, null);
		Report report = (Report) repList.get(0);
		return report;
	}

	public Report getReportByReportName(String ReportName) {
		System.out.println("++++++++++++++++传到sql中的+" + ReportName);
		Report report = null;
		String hql = "from Report t where t.name ='" + ReportName + "'";
		List ls = list(hql);
		if (ls.size() <= 0) {
			report = null;
		} else {
			report = (Report) ls.get(0);
		}
		return report;
	}

	public List getTargetsByReportId(Long reportId){
		String sql="select pkid,target_name as name,target_field as field ,report_id as  reportid from code_rep_target where  report_id = "+reportId ;
		 Object[][] obj=new Object[][]{{"pkid", Hibernate.LONG},{"name", Hibernate.STRING},{"field", Hibernate.STRING},{"reportid", Hibernate.LONG}};
		 return list(sql,null,obj);
	}
	public List getReportByYJH(String ismodel){
		String hql="from ReportTemplate r where r.ismodel='"+ismodel+"'";
    	return  this.list(hql);
	}
	
	public void clearTemplateTargets(Long templateId, Long modelId) {
		String sql1 = "delect from code_rep_target where pkid in (select template_id from template_model where template_id="
				+ templateId + " and model_id=" + modelId + ")";
		String sql2 = "delect from template_model where template_id="
				+ templateId + " and model_id=" + modelId;
		batchJdbcUpdate(new String[] { sql1, sql2 });
	}

	public void delTemplateTargets(Long templateId, Long modelId,
			String modelTarget) {
//		String sql1 = "delete from "+DBDialect.getSchema()+"code_rep_target where target_field in (select template_target  from "+DBDialect.getSchema()+"template_model where template_id="
//				+ templateId
//				+ " and model_id="
//				+ modelId
//				+ " and model_target='" + modelTarget + "') and report_id="+templateId;
		String sql1 = "delete from code_rep_target where target_field in (select template_target  from template_model where template_id="
				+ templateId
				+ " and model_id="
				+ modelId
				+ " and model_target='" + modelTarget + "') and report_id="+templateId;
		//String sql1 = "delete from "+DBDialect.getSchema()+"code_rep_target where report_id="+templateId+" and target_field='"+modelTarget+"'";
//		String sql2 = "delete from "+DBDialect.getSchema()+"template_model where template_id="
//				+ templateId + " and model_id=" + modelId + " and model_target='"
//				+ modelTarget +"'"; 
		//适用于East标准化oracle
		String sql2 = "delete from template_model where template_id="
				+ templateId + " and model_id=" + modelId + " and model_target='"
				+ modelTarget +"'";
		batchJdbcUpdate(new String[] { sql1, sql2 });
	}
	
	
	public List getTargetRelation(Long templateId, Long modelId) {
		String sql = "select  model_target  mt,template_target tt from "+DBDialect.getSchema()+"template_model where template_id="
		+ templateId + " and model_id=" + modelId;
		return list(sql,null,new Object[][]{{"mt",Hibernate.STRING},{"tt",Hibernate.STRING}});
	}
	
	public void delTemplateTarget(Long templateId, Long modelId,
			String targetField) {
	//	String sql1 = "delete from "+DBDialect.getSchema()+"code_rep_target where report_id="+templateId+" AND target_field='"+targetField +"'";
		String sql1 = "delete from code_rep_target where report_id="+templateId+" AND target_field='"+targetField +"'";
		//String sql2 = "delete from "+DBDialect.getSchema()+"template_model where template_id="
			//	+ templateId + " and model_id=" + modelId + " and template_target='"
			//	+ targetField +"'";
//		String sql2 = "delete from "+DBDialect.getSchema()+"template_model where template_id="
//			+ templateId + " and template_target='"
//			+ targetField +"'";
		String sql2 = "delete from template_model where template_id="
				+ templateId + " and template_target='"
				+ targetField +"'";
		batchJdbcUpdate(new String[] { sql1, sql2 });
	}
	
	public Integer getNextTargetField(Long reportId, String dataType) {
		Integer maxColNum = new Integer(0);
		Object[][] scalaries = { { "cnt", Hibernate.INTEGER} };
//		String sqlStr = "SELECT MAX(Integer(target_field)) as cnt "
//				+ "FROM "+DBDialect.getSchema()+"code_rep_target WHERE report_id = ? and data_type=?";
		//�����д��ֻ��db2�����ã������}�߶����Լ���
//		String sqlStr = "SELECT MAX(target_field) as cnt "
//				+ "FROM "+DBDialect.getSchema()+"code_rep_target WHERE report_id = ? and data_type=?";
//		������East��׼��oracle
		String sqlStr = "SELECT MAX(to_number(target_field)) as cnt "
				+ "FROM code_rep_target WHERE report_id = ? and data_type=?";
		List result = list(sqlStr, null, scalaries, new Object[] { reportId,
				dataType});
		if (result != null&&result.size()>0) {
			if (result.get(0) != null) {
				maxColNum = (Integer) result.get(0);
			}
			if(!"1".equals(dataType)&&maxColNum.equals(new Integer(20))) {
				return new Integer(51);
			}
			if("1".equals(dataType) && maxColNum==0) {
				return new Integer(21);
			}
			return new Integer(maxColNum + 1);
		}else {
			if("1".equals(dataType)) {
				return new Integer(21);
			} else {
				return new Integer(1);
			}
		}
	}

	public void updateTargetRelation(Long templateId, Long modelId,
			Map<String, String> targetMap) {
		String[] sqls = new String[targetMap.size()];
		int i = 0;
		for(Entry<String,String> entry :targetMap.entrySet()) {
//			da2的sql
		//	String sql = "INSERT INTO "+DBDialect.getSchema()+"template_model(pkid,template_id,model_id,template_target,model_target) values("+DBDialect.genSequence("CODE_TEMPLATE_SEQ")+","+templateId+","+modelId+",'"+entry.getKey()+"','"+entry.getValue()+"') ";
			String sql = "INSERT INTO template_model(pkid,template_id,model_id,template_target,model_target) values("+DBDialect.genSequence("CODE_TEMPLATE_SEQ")+","+templateId+","+modelId+",'"+entry.getKey()+"','"+entry.getValue()+"') ";
			sqls[i] = sql;
			i ++;
		}
		batchJdbcUpdate(sqls);
	}


	public void updateTemplateTargetField(Long templateId, Long modelId,
			String oldTargetField, String targetField) {
		//String sql = "UPDATE "+DBDialect.getSchema()+"template_model SET template_target='" +targetField+ "' WHERE template_id="+templateId+" AND model_id=" +modelId+" AND template_target='"+oldTargetField+"'";
	//	String sql = "UPDATE "+DBDialect.getSchema()+"template_model SET template_target='" +targetField+ "' WHERE template_id="+templateId+"  AND template_target='"+oldTargetField+"'";
		String sql = "UPDATE template_model SET template_target='" +targetField+ "' WHERE template_id="+templateId+"  AND template_target='"+oldTargetField+"'";
		batchJdbcUpdate(new String[]{sql});
	}
	@Override
	
	public void saveTemplate(ReportTemplate reportTemp,String updatetemp) {
		// TODO Auto-generated method stub
		if("updatetemp".equals(updatetemp)){
			getHibernateTemplate().update(reportTemp);
		}
		getHibernateTemplate().save(reportTemp);
	}
	
	public List getallTemplate(Long userid){
	
		String hql  = "from ReportTemplate r where r.ismodel = '0'"; 
		return list(hql);
	}
	@Override
	public List getTemplates(Long reportid) {
		// TODO Auto-generated method stub
		String hql = "from TemplateModel rt where rt.templateId=" + reportid;
		List ls = getHibernateTemplate().find(hql);
		return ls;
	}
	@Override
	public boolean delTemplates(String reportid) {
		// TODO Auto-generated method stub
		ReportTemplate re = new ReportTemplate();
		re.setPkid(Long.parseLong(reportid));
		String hql = "delete from CUSTRISK.COLLECT_REPORT rt where rt.pkid=" + reportid;
		getHibernateTemplate().delete(re);
		return true;
	}
	@Override
	public List getTempTarget(String reportid, String reportid1,String flag) {
		// TODO Auto-generated method stub 
		String hql = "from TargetTemplate ta where 1=1 "; //reportId模板id，phytableid模型id
		if("1".equals(flag)){
			hql += " and  ta.reportId = " + reportid;
		}else if("2".equals(flag)){
			hql += " and  ta.reportId = " + reportid + " and ta.phytableid = " + reportid1;
		}else if("3".equals(flag)){
			hql += " and  ta.reportId = " + reportid1 + " and ta.phytableid = " + reportid1 + "and ";
			hql += "ta.targetName not in (select a.targetName from TargetTemplate a where a.reportId=" + reportid+")";
		}else{
			return null;
		}
		
		return getHibernateTemplate().find(hql);
	}
	@Override
	public String getTypesname(String typesid) {
		// TODO Auto-generated method stub
		String hql = "from ReportType rt where rt.pkid=" + typesid;
		List ls = getHibernateTemplate().find(hql);
		ReportType ty = (ReportType)ls.get(0);
		return ty.getName();
	}
	@Override
	public void saveTempTarget(String reportid, String reportid1, TargetTemplate target) {
		// TODO Auto-generated method stub
		TargetTemplate temptargetr = new TargetTemplate();
		System.out.println(DBDialect.genSequence("COLLECT_TARGET_seq"));
//		temptargetr.setPkid(Long.parseLong(DBDialect.genSequence("COLLECT_TARGET_seq")));
		temptargetr.setBeginDate(target.getBeginDate());
		temptargetr.setCreateDate(target.getCreateDate());
		temptargetr.setDicPid(target.getDicPid());
		temptargetr.setEditDate(target.getEditDate());
		temptargetr.setEndDate(target.getEndDate());
		temptargetr.setPhytable(target.getPhytable());
		temptargetr.setPhytableid(target.getPhytableid());
		temptargetr.setReportId(Long.parseLong(reportid));
		temptargetr.setTargetName(target.getTargetName());
		temptargetr.setTargetField(target.getTargetField());
		temptargetr.setTargetOrder(target.getTargetOrder());
		getHibernateTemplate().save(temptargetr);
	}
	@Override
	public void deleTempTarget(String reportid, String reportid1, TargetTemplate target) {
		// TODO Auto-generated method stub
		
		getHibernateTemplate().delete(target);
	}
	@Override
	public TargetTemplate getTarget(String reportid, String reportid1,
			String targetid) {
		// TODO Auto-generated method stub
		String hql = "from TargetTemplate t where t.phytableid = " + reportid1 + " and t.pkid = " +  targetid;
		List targetlist = getHibernateTemplate().find(hql);
		return (TargetTemplate)targetlist.get(0);
	}
	
	
	//将状态status1修改为状态status2
	private void reportUpdaStatus(String tablename,String status1,String status2,List lsresult) {
		// TODO Auto-generated method stub
		String sql =""; 
//		ReportResult reportResult;
//		for(int i = 0;i < lsresult.size();i++){
//			sql = "update " + tablename;
//			sql += " c set c.CSTATUS = " + status2;
//			reportResult = (ReportResult)lsresult.get(i);
//			//校验后修改状态  
//			sql += " where c.CSTATUS = " + status1 + "  and c.keyvalue =" + reportResult.getKeyvalue();
//			this.jdbcUpdate(sql, null);
//		}
	}
	@Override
	public void reportSeleMove(String status1){
//		// TODO Auto-generated method stub		
//		String sqlcon = "select {r.*} from code_rep_config r where r.fun_id = 34";    //查询结果表的表名
//		List conlist = list(sqlcon, new Object[][] { { "r", ReportConfig.class } }, null);
//		logger.info("要更新的结果表数："+conlist.size());
//		String[] moda = getResultdate();
//		ReportConfig reportConfig;
//		String resulttablename = "";
//		String sqlresult = "";
//		List listtest;
//		for(int i=0;i<conlist.size();i++){
//			sqlresult = "select {r.*} from ";
//			reportConfig = (ReportConfig) conlist.get(i);
//			resulttablename = reportConfig.getDefChar();  
//			resulttablename = resulttablename.replaceAll("\\{M\\}", moda[0]);
//			resulttablename = resulttablename.replaceAll("\\{D\\}", moda[1]);
//			resulttablename = resulttablename.replaceAll("\\{Y\\}", moda[2]);
//			sqlresult += resulttablename + " r where r.cstatus = '" + status1 +"' and rownum <= 1000"; 
//			listtest = list(sqlresult, new Object[][] { { "r", ReportResult.class } }, null);
//			logger.info("迁移数据的大小为:" + listtest.size()+"--"+resulttablename);
//			if(listtest != null ){
//				String idstr="";
//				String updatesql="";
//				try{
//				for(int x=0;x<listtest.size();x++){
//					ReportResult reportResult = (ReportResult)listtest.get(x);
//					idstr+=reportResult.getPkid()+",";
//				}
//				if(!"".equals(idstr)){
//					//7状态为正在处理
//					updatesql = "update " + resulttablename+" c set c.CSTATUS ='7'"+" where c.pkid in ("+idstr.substring(0,idstr.length()-1)+")";
//					this.jdbcUpdate(updatesql, null);
//					logger.error("【"+resulttablename+"】修改状态为正处理成功！："+idstr);
//				}
//				Map targetMap=new HashMap();
//				Map targetidMap=new HashMap();
//				String defchar = getstyle(reportConfig.getReportId());  //获得数据表的后缀
//				String dataftablename = defchar.replaceAll("\\{M\\}", moda[0]);
//				dataftablename = dataftablename.replaceAll("\\{D\\}", moda[1]);
//				dataftablename = dataftablename.replaceAll("\\{Y\\}", moda[2]);
//				for(int j=0;j<listtest.size();j++){
//					ReportResult reportResult = (ReportResult)listtest.get(j);
//					Long keyvalues=reportResult.getKeyvalue();
//					String item = reportResult.getTargetid();
//					if(targetidMap.containsKey(keyvalues)){
//						targetidMap.put(keyvalues, targetidMap.get(keyvalues)+"'"+reportResult.getTargetid()+"',");
//					}else{
//						targetidMap.put(keyvalues, "'"+reportResult.getTargetid()+"',");
//					}
//					if(targetMap.containsKey(keyvalues)){
//						String sql=targetMap.get(keyvalues).toString();
//						sql+=item +"=";
////						if(item > 20 && item < 51){
////							if("".equals(reportResult.getNewvalue())){
////								sql+=0;
////							}else if(null!=reportResult.getNewvalue()){
////							    sql += Double.parseDouble(reportResult.getNewvalue());//��Ҫ����޸��������
////							}else if(null==reportResult.getNewvalue()){
////								sql+=null;
////							}
////						}else{
////							sql += "'" + reportResult.getNewvalue() + "'";
////						}
//						sql += "'" + reportResult.getNewvalue() + "'";
//						sql+=",";
//						targetMap.put(keyvalues, sql);
//					}else{
//						String sql=item +"=";
////						if(item > 20 && item < 51){
////							if("".equals(reportResult.getNewvalue())){
////								sql+=0;
////							}else if(null!=reportResult.getNewvalue()){
////							    sql += Double.parseDouble(reportResult.getNewvalue());//需要添加修改数据类型
////							}else if(null==reportResult.getNewvalue()){
////								sql+=null;
////							}
////						}else{
////							sql += "'" + reportResult.getNewvalue() + "'";
////						}
//						sql += "'" + reportResult.getNewvalue() + "'";
//						sql+=",";
//						targetMap.put(keyvalues, sql);
//					}
//					
//				}
//				reportMove(resulttablename,targetMap,targetidMap,status1,dataftablename);//需要添加修改数据类型
//				//reportUpdaStatus(tablename,status1,status2,listtest)״̬
//				//if("1".equals(status1)){
//					
//				//	reportVerify(tablename,listtest);
//				//}
//			}catch (RuntimeException re) {
//				
//				updatesql = "update " + resulttablename+" c set c.CSTATUS ='2'"+" where c.pkid in ("+idstr.substring(0,idstr.length()-1)+")";
//				this.jdbcUpdate(updatesql, null);
//			logger.error("【"+resulttablename+"】修改状态为正处理出错："+idstr+"--错误原因:"+re.toString()); //e.printStackTrace();
//	        }finally{
//	        	continue;
//	        }
//			}
//				
//		}
	}
	// 数据迁移
	private void reportMove(String tablename,Map targetMap,Map targetidMap,String status1,String dataftablename){
		// TODO Auto-generated method stub
		Set set=targetMap.keySet();
		Iterator targetIt=set.iterator();
		while(targetIt.hasNext()){
			//batchJdbcUpdate
			String sqls="";
			Long keyvalue=Long.parseLong(String.valueOf(targetIt.next()));
			String targetids=String.valueOf(targetidMap.get(keyvalue));
			if(keyvalue != null){
				String sqltar=targetMap.get(keyvalue).toString();
				//itemvalue80为状态列，刚抽入的数据，默认为0，当回写成功时，改为1
				//sqls="update "+dataftablename+" r set "+sqltar.substring(0,sqltar.length()-1)+",itemvalue80='1'";
				sqls="update "+dataftablename+" r set "+sqltar.substring(0,sqltar.length()-1);
				sqls+="  where r.pkid = " + keyvalue;
			}
//			updResultF(reportResult);   //更新f表的值
			try{
					logger.info("sqls:"+sqls);
					int iflag=this.jdbcUpdate(sqls, null);  //批量更新数据表
					//当数据在数据表时更新成功后，再修改结果表的状态״̬
					if(iflag>0){
						//把正在处理的数据改为同步完成
						String sql = "update " + tablename+" c set c.CSTATUS ='4'"+" where c.CSTATUS ='7'  and  c.keyvalue =" + keyvalue+" and c.target_id in ("+targetids.substring(0,targetids.length()-1)+")";
						logger.info("sql:"+sql);
						this.jdbcUpdate(sql, null);
						logger.info("数据表：【"+dataftablename+"】同步【"+keyvalue+"】记录成功！");
						
						//青海修改状态，当填报完成后，itemvalue80置状态为1
							/*String qusql="select count(pkid) as total from "+tablename+" c where c.CSTATUS !='4' and c.keyvalue =" + keyvalue;
							Object[][] scalaries = new Object[][] { {"total", Hibernate.INTEGER}};
							List numlist=this.list(qusql, null, scalaries);
							int num =  Integer.parseInt(numlist.get(0).toString());
							if(num==0){
								String upsqls="update "+dataftablename+" r set r.itemvalue80='1'  where r.pkid = " + keyvalue;
								this.jdbcUpdate(upsqls, null);
							}*/
						
					}
				} catch (RuntimeException re) {
					//6为提交失败的数据，目前为测试阶段，先改状态״̬
					String sql = "update " + tablename+" c set c.CSTATUS ='6'"+" where c.CSTATUS = '7'  and  c.keyvalue =" + keyvalue+" and c.target_id in ("+targetids.substring(0,targetids.length()-1)+")";
					this.jdbcUpdate(sql, null);
					logger.error("数据表：【"+dataftablename+"】同步【"+keyvalue+"】记录时出错，请查看:"+re.toString());
		        }finally{
		        	continue;
		        }
			}
		
			//else{
			//	insResultF(reportResult,tablename);   //结果表原来没有的数据进行insert
			//}
		}
	//补录数据在F表存在对原数据进行update操作
//	private void updResultF(ReportResult reportResult){
//		String style = ""; //数据类型
//		String editdate = "";//数据日期
//		String sql = "";
//		editdate = reportResult.getEtldate(); //获取数据日期
//		editdate = editdate.substring(4, 6);//截取月份
//		//style = getstyle(reportResult.getModelid());
//		int item  = Integer.parseInt(reportResult.getTargetid());
//		sql = "update EAST.REP_DATAF_"+ editdate + "_"+ style + " r set itemvalue" + reportResult.getTargetid() + " = ";
//		if(item > 20 && item < 51){
//			if("".equals(reportResult.getNewvalue())){
//				sql+=0;
//			}else if(null!=reportResult.getNewvalue()){
//			    sql += Double.parseDouble(reportResult.getNewvalue());//需要添加修改数据类型
//			}else if(null==reportResult.getNewvalue()){
//				sql+=null;
//			}
//		}else{
//			sql += "'" + reportResult.getNewvalue() + "'";
//		}
//		
//		sql += "  where r.pkid = " + reportResult.getKeyvalue();
//		try{
//			this.jdbcUpdate(sql, null);
//		} catch (RuntimeException re) {
//			logger.error("");
//        }
//	}
	//补录数据在F表不存在由于关联关系需要要对原数据进行insert操作
//	private void insResultF(ReportResult reportResult,String tablename){
//		ReportResult resu;
//		String editdate = reportResult.getEtldate(); //获取数据日期
//		String datemoth = editdate.substring(4, 6);//截取月份
//		String style = getstyle(Long.parseLong(reportResult.getModelid()));//数据类型
//		String sqlresu = "select {r.*} from code_rep_result_t_" +datemoth+" r where r.model_id=" +reportResult.getModelid();  //��ѯ��Ҫinsertͬһ������
//		if(reportResult.getKeyvalue1() != null){
//			sqlresu += " and r.keyvalue1='" +reportResult.getKeyvalue1()+"'";
//		}
//		if(reportResult.getKeyvalue2() != null){
//			sqlresu += " and r.keyvalue2='"  +reportResult.getKeyvalue2()+ "'";
//		}
//		if(reportResult.getKeyvalue3() != null){
//			sqlresu += " and r.keyvalue3='"  +reportResult.getKeyvalue3()+"'";
//		}
//		List listre = list(sqlresu, new Object[][] { { "r", ReportResult.class } }, null); //将属于该行的所有数据查出
//		String sql = "insert into rep_dataf_" + datemoth + "_" + style + " (pkid,Organ_id,report_id,item_id,report_date,create_date";  //ƴ��insert���
//		String sql1 = "";
//		Long intem = null;
//		for(int i = 0;i < listre.size();i++){
//			resu = (ReportResult)listre.get(i);
//			sql +=",itemvalue" + resu.getTargetid(); //将要插入的列放入sql
//			intem = Long.parseLong(resu.getTargetid());
//			if(intem > 20 && intem <51){
//				sql1 += "," + resu.getNewvalue();//将要插入的列值放入sql    整型   
//			}else{
//				sql1 += ",'" + resu.getNewvalue() + "'";//将要插入的列值放入sql    字符型
//			}
//		}
//		sql += ") values (";
//		sql += DBDialect.genSequence("REP_DATAF_PKID_SEQ") +"," + reportResult.getOrganid()+","+reportResult.getModelid()+",1,"+editdate+","+editdate+sql1+")";
//		sql += "returning id into ? ";
//		Connection conn = null;
//		int id = 0; 
//		 try {
//			PreparedStatement sta = conn.prepareStatement(sql); 
//			sta.execute();
//			ResultSet rset = sta.getResultSet();
//			while(rset.next()){
//			    id = rset.getInt(1);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		updatekeyvalue(listre,id,tablename);
//	}
//	
//	private void updatekeyvalue(List listre,int fid,String tablename){
//		ReportResult resu;
//		String sql = "update "+tablename + " r set r.keyvalue = " + fid + " where r.pkid = ";
//		for(int i = 0;i < listre.size();i++){
//			resu = (ReportResult)listre.get(i);
//			sql += resu.getPkid();
//			this.jdbcUpdate(sql, null);
//		}
//	}
//	private void reportVerify(String tablename,List list) {
//		// TODO Auto-generated method stub
//		
//		boolean verifresult;
//		String sql = "";
//		ReportResult reportResult;
//		ReportRule reportRule;
//		List lierrid = null;
//		List listrule;  
//		List vierfyresult = new ArrayList(); 
//		String status1="4";String status2 = "3";
//		for(int i = 0;i < list.size();i++){
//			reportResult = (ReportResult)list.get(i);
//			sql = "from ReportRule c where c.modelid='" + reportResult.getModelid() + "'  and c.targetid='" + reportResult.getTargetid()+"'";
//			listrule = getHibernateTemplate().find(sql);
//			lierrid = verify(listrule,reportResult.getKeyvalue()); 
//			if(lierrid != null){
//				reportUpdaStatus(tablename,status1,status2,lierrid);״̬
//			}
//		}
//	}
	
//	private List verify(List listrule,Long keyvalue){
//		ReportRule reportRule;
//		String rule = "";
//		List list;
//		List liserid = new ArrayList();
//		for(int i=0;i < listrule.size();i++){
//			reportRule = (ReportRule)listrule.get(i);
//			rule = reportRule.getContent();
//			String[] mothd = getResultdate();
//			String moth = mothd[1] + "";
//			//rule = rule.replaceAll("\\$\\{M\\}", moth);
//			rule = rule.replaceAll("\\{M\\}", moth);
//			rule += " AND r.PKID in ("+keyvalue+")";
//			list = this.getSession().createSQLQuery(rule).addScalar("pkid",Hibernate.LONG).list();
//			if(list == null){
//				liserid.add(keyvalue);
//			}
//		}
//		return liserid;
//	}
	//查询数据类型
	private String getstyle(Long modelid){
		String sql = "select c.DEF_CHAR from CODE_REP_CONFIG c where c.fun_id=33 and c.REPORT_ID=" + modelid ;
		Session session = this.getSession();
		List list = session.createSQLQuery(sql).addScalar("DEF_CHAR",Hibernate.STRING).list();
		session.close();
		return (String)list.get(0);
	}
	//获取补录数据月和日
	private String[] getResultdate(){
		String sql="select currentdate as currentDate from SYSPARAM";
		Object[][] scalaries = new Object[][] { 
				{ "currentDate", Hibernate.STRING }
				};
		List datelist=list(sql, null, scalaries);
		String dater =  datelist.get(0).toString();
		String year=dater.substring(0,4);
		String month=dater.substring(4,6);
		String day=dater.substring(6,8);
		String[] moda = {month,day,year};
		return moda;
	}
	

	public List getOrgan(){
		String sql="select full_name as name,code, SUBSTR(SYS_CONNECT_BY_PATH(pkid, ','), 2)||',' REMARK " +
				"  from code_org_organ" +
				" start with code ='001102' connect by  INST_PARENT_NO= prior code ";
		Object[][] scalaries=new Object[][]{{"name",Hibernate.STRING},{"code",Hibernate.STRING},{"REMARK",Hibernate.STRING}};
		return list(sql,null,scalaries,null);
	}
	public void saveOrgan(OrganTreeNode otn){
		saveObject(otn);
	}

	public int insertDataF( String sql){
		return insertDataF(sql, null);
	}
	
	protected int insertDataF(final String sql, final Object[] values) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				PreparedStatement ps = session.connection().prepareStatement(
						sql);
				setPreparedStatementParameters(ps, values);
				int aa=ps.executeUpdate();
				ps.close();
				return new Integer(aa);
			}
		};
		return ((Integer) getHibernateTemplate().execute(callback)).intValue();
	}
	@Override
	public List<ReportTarget> getReportTargetsBySRC(String tableName) {
		List<ReportTarget> result = new ArrayList<ReportTarget>();
		Connection conn = null;
		ResultSet rs = null;
		String driverclass = FuncConfig.getProperty("jdbc.connection.driver_class");
		String url = FuncConfig.getProperty("jdbc.connection.url");
		String username = FuncConfig.getProperty("jdbc.connection.username");
		String password = FuncConfig.getProperty("jdbc.connection.password");
		String schema = FuncConfig.getProperty("jdbc.connection.schema");
		String DBType = FuncConfig.getProperty("jdbc.database.type");
		try { 
			Class.forName(driverclass);
			Properties props =new Properties();
			if(DBType.toUpperCase().equals("ORACLE")){
				props.put("remarksReporting","true");
			}
			props.put("user", username);
			props.put("password", password);
			conn = DriverManager.getConnection(url, props);
			DatabaseMetaData dbmd=conn.getMetaData(); 
			rs = dbmd.getColumns(null, schema, tableName, "%"); 
			while (rs.next())
			{
				ReportTarget rt = new ReportTarget();
				String columnName = rs.getString("COLUMN_NAME");
				String columnCNName = rs.getString("REMARKS");
				rt.setTargetField(columnName);
				if(columnCNName != null && !columnCNName.equals("")){
					rt.setTargetName(columnCNName);
				} else {
					rt.setTargetName(columnName);
				}
				rt.setRulesize(rs.getString("COLUMN_SIZE"));
				String temp = rs.getString("TYPE_NAME");
				if(temp.equals("VARCHAR") || temp.equals("VARCHAR2")){
					rt.setDataType(3);
				} else {
					rt.setDataType(1);
				}
				rt.setStatus(0);
				result.add(rt);
			}
		} catch (Exception e) {
			logger.error("查询错误：", e);
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) { }
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {	}
			}
		}
		return result;
	}
	 
//	public List<DicItem> queryDicAll() {
//		String hql = "Select d from DicItem d where d.parentId = '0'";
//		return list(hql);
//	}
	 
	public Integer getMaxOrderNum(Long reportId) {
		// 更新报表定义的列数。
			Object[][] scalaries = { { "cnt", Hibernate.INTEGER } };
			Integer maxColNum = new Integer(0);
			List result;
			String sqlStr = "SELECT MAX(target_order) as cnt "
					+ "FROM code_rep_target WHERE report_id = ?";
			result = list(sqlStr, null, scalaries,	new Object[] { reportId });
			maxColNum = (Integer) result.get(0);
			if(maxColNum == null){
				maxColNum = 0;
			}
			return maxColNum;
		}
	@Override
	public void saveReportTarget(ReportTarget rt) {
		saveObject(rt);
	}
	@Override
	public List getReportRuleByReportId(String reportid){
		String hql="from ReportRule r where r.modelid='"+reportid+"'";
		return this.list(hql);
	}
	@Override
	public int deleteReportResult(String  rulecode ,String cstatus ,String tablename ,String organId){
		 String sql="delete from "+tablename+" where RULECODE in ("+rulecode+") and CSTATUS='"+cstatus+"' and organ_id='"+organId+"'";
		 return jdbcUpdate(sql,null);
	 }
	@Override
	public Object[] checkData(String rulecode,String date){
		String sql="{CALL P_M_DORULE(?,?)}";
		int[] arr= {1};
		
		return jdbcCall(sql, new Object[]{rulecode,date},null, arr, null);
	}
	@Override
	public String getReportXML_temp(List reportList, Set set,String paramDate,Integer paramOrganType,String canEdit,Long userId,String systemId,String levelFlag) {
		StringBuffer sbtree = new StringBuffer();
		sbtree.append("[{");
		sbtree.append("\"id\":\"" + ((Report)reportList.get(0)).getPkid() + "\",");
		sbtree.append("\"text\":\"" + ((Report)reportList.get(0)).getName().trim() + "\"");
		Iterator it = set.iterator();
		List replist = new ArrayList();
		while(it.hasNext()){
			replist.add(it.next());
		}
		if(reportList.size()>0){
			sbtree.append(",\"state\":\"closed\"");
			sbtree.append(",\"children\":[");
			for (int i = 0; i < replist.size(); i++) {
				if (i == 0) {
					sbtree.append("{");
				} else {
					sbtree.append(",{");
				}
				for (int j = 0; j < reportList.size(); j++) {
					Report r = (Report)reportList.get(j);
					if(Integer.parseInt(replist.get(i).toString())+10000==Integer.parseInt(r.getPkid().toString())){
						sbtree.append("\"id\":\"" + r.getPkid() + "\",");
						sbtree.append("\"text\":\"" + r.getName().trim()+ "\"");
						List lista = getDateOrganEditReportForStandard_temp(paramDate,paramOrganType,canEdit,userId,systemId,levelFlag,replist.get(i).toString());
						if(lista.size()>0){
							sbtree.append(",\"state\":\"closed\"");
							sbtree.append(",\"children\":[");
						
							for (int k = 0; k < lista.size(); k++) {
								
									if (k == 0) {
										sbtree.append("{");
									} else {
										sbtree.append(",{");
									}
									sbtree.append("\"id\":\"" + ((Report)lista.get(k)).getPkid() + "\",");
									sbtree.append("\"text\":\"" + ((Report)lista.get(k)).getName().trim()+ "\"");
									sbtree.append("}");
							}
							sbtree.append("]");
						}
					}
				}
				sbtree.append("}");
			}
			sbtree.append("]");
		}
		sbtree.append("}]");
		System.out.println(sbtree.toString());
		return sbtree.toString();
	}
	
	public List getDateOrganEditReportForStandard_temp(String paramDate,
			Integer paramorgan_type, String canEdit, Long userId,
			String systemId, String levelFlag,String reporttype) {
		String freqStr = "";
		
		StringBuffer hql = new StringBuffer(
				"select {r.*} from code_rep_report r ")
				.append("where r.status<>9 ");
		if(paramDate!=null&& !"".equals(paramDate)){
				hql.append("and ").append("r.begin_date <= '")
				.append(paramDate).append("' and ").append("r.end_date >= '")
				.append(paramDate).append("'");
			}
			 hql.append(" and r.pkid in (select u.repid from rep_oper_contrast u where u.operid = ")
				.append(userId)
				.append(" ) ");

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
		hql = hql.append(" and r.report_type='"+reporttype+"' order by r.report_type,r.show_order");
		List ls = list(hql.toString(),
				new Object[][] { { "r", Report.class } }, null);

		return ls;
	}
}
