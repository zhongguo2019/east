package com.krm.ps.model.east1104dz.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.east1104dz.dao.QueryDzDao;
import com.krm.ps.model.east1104dz.vo.East1104Dz;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.util.Constants;

public class QueryDzDAOHibernate extends BaseDAOHibernate implements QueryDzDao {

	public List getReports(String date, Long userid) {
		return getReports(null, date, userid, null);
	}

	public List getReports(Report report, String date, Long userid,
			String showlevel) {

		Map map = new HashMap();
		String hql = "";

		if (date != null) {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.pkid in(select ur.reportId from UserReport ur where ur.operId =:userid) and r.status <>"
					+ Constants.STATUS_DEL;
			map.put("userid", userid);

			if (date.matches("^\\d{8}$")) {
				hql += " and r.beginDate<=:date and r.endDate>=:date";
				map.put("date", date);
			}
			hql += " and r.reportType = t.pkid";
		} else if (userid == null) {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL
					+ " and r.reportType = t.pkid and r.pkid <>0";
		} else {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL + " and r.reportType = t.pkid ";
		}
		if (showlevel != null && !"".equals(showlevel)) {
			hql += " and t.showlevel=" + showlevel;
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

		return list(hql, map);
	}

	private boolean isNull(Object o) {
		if (null != o && o.toString().trim().length() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public List getQuerydz(String date, String organid,int idx) {
		String lab = "";
		List labL = getOrganidx(organid, idx);
		if (labL.size() > 0) {
			lab = (String) labL.get(0);
		}
		String sql = "select {t.*} from EAST_YWZB_DZ t where t.ORGAN_ID in (select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
				    + lab 
				    + "%' )"
				    + " AND  t.report_date=" 
					+ date;
		logger.debug(sql);
		return list(sql, new Object[][] { { "t", East1104Dz.class } }, null,
				null);
	}
	
	public List getOrganidx(String organcode, int idx) {
		String sql = "select tt.SUBTREETAG ttlab from code_org_organ t,CODE_ORG_TREE tt where t.PKID=tt.NODEID and t.CODE='"
				+ organcode + "' and  tt.IDXID=" + idx + "";
		List resultL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.STRING } });
		return resultL;
	}
}
