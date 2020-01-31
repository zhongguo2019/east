package com.krm.ps.model.flexiblequery.services.impl;

import java.util.List;

import com.krm.ps.model.flexiblequery.dao.ReportDefineDAO;
import com.krm.ps.model.flexiblequery.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportOrgType;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

public class ReportDefineServiceImpl implements ReportDefineService {

	private ReportDefineDAO flexiblequeryReportDefineDAO;

	public ReportDefineDAO getFlexiblequeryReportDefineDAO() {
		return flexiblequeryReportDefineDAO;
	}

	public void setFlexiblequeryReportDefineDAO(
			ReportDefineDAO flexiblequeryReportDefineDAO) {
		this.flexiblequeryReportDefineDAO = flexiblequeryReportDefineDAO;
	}

	public List getReportTargets(Long reportId) {
		return getReportTargets(reportId, null);
	}

	public List<ReportTarget> getReportTargets(Long reportId, String tableName) {
		List<ReportTarget> result = flexiblequeryReportDefineDAO
				.getReportTargets(reportId);
		if (tableName == null) {
			return result;
		}
		List<ReportTarget> reportTarget = flexiblequeryReportDefineDAO
				.getReportTargetsBySRC(tableName);
		for (int i = 0; i < result.size(); i++) {
			ReportTarget rt = result.get(i);
			for (int j = 0; j < reportTarget.size(); j++) {
				ReportTarget temp = reportTarget.get(j);
				if (rt.getTargetField().trim()
						.equals(temp.getTargetField().trim())) {
					rt.setNowsize(temp.getRulesize());
					reportTarget.remove(j);
					break;
				}
			}
		}
		for (int i = 0; i < reportTarget.size();) {
			ReportTarget temp = reportTarget.get(i);
			if (temp.getTargetField().trim().equals("PKID")
					|| temp.getTargetField().trim().equals("ORGAN_ID")
					|| temp.getTargetField().trim().equals("REPORT_DATE")) {
				reportTarget.remove(i);
				continue;
			}
			i++;
		}
		result.addAll(reportTarget);
		return result;
	}

	public Report getReport(Long pkid) {
		Object o = flexiblequeryReportDefineDAO.getObject(Report.class, pkid);
		Report report = null;
		if (null != o) {
			report = (Report) o;
			List organTypes = flexiblequeryReportDefineDAO
					.getReportOrgTypes(report.getPkid());
			int length = organTypes.size();
			String[] types = new String[length];
			for (int i = 0; i < length; i++) {
				types[i] = ((ReportOrgType) organTypes.get(i)).getOrganType()
						.toString();
			}
			report.setOrganType(types);
			return report;
		}
		return null;
	}
}
