package com.krm.ps.model.reportrule.services.impl;

import java.util.List;

import com.krm.ps.model.reportrule.dao.ReportDefineDAO;
import com.krm.ps.model.reportrule.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportOrgType;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

public class ReportDefineServiceImpl implements ReportDefineService {
	private ReportDefineDAO reportrulerReportDefineDAO;

	public ReportDefineDAO getReportrulerReportDefineDAO() {
		return reportrulerReportDefineDAO;
	}

	public void setReportrulerReportDefineDAO(
			ReportDefineDAO reportrulerReportDefineDAO) {
		this.reportrulerReportDefineDAO = reportrulerReportDefineDAO;
	}

	public List getReportTargets(Long reportId) {
		return getReportTargets(reportId, null);
	}

	public List<ReportTarget> getReportTargets(Long reportId, String tableName) {
		List<ReportTarget> result = reportrulerReportDefineDAO
				.getReportTargets(reportId);
		if (tableName == null) {
			return result;
		}
		List<ReportTarget> reportTarget = reportrulerReportDefineDAO
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
		Object o = reportrulerReportDefineDAO.getObject(Report.class, pkid);
		Report report = null;
		if (null != o) {
			report = (Report) o;
			List organTypes = reportrulerReportDefineDAO
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

	public List getReportItems(Long reportId) {
		return reportrulerReportDefineDAO.getReportItems(reportId);
	}

	public List getReportTargetsStock(Long reportId, String stocktype) {
		return reportrulerReportDefineDAO.getReportTargetsStock(reportId,
				stocktype);
	}

}
