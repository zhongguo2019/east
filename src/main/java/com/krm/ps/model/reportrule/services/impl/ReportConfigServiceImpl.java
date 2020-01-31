package com.krm.ps.model.reportrule.services.impl;

import java.util.List;

import com.krm.ps.model.reportrule.dao.ReportConfigDAO;
import com.krm.ps.model.reportrule.services.ReportConfigService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public class ReportConfigServiceImpl implements ReportConfigService {

	private ReportConfigDAO reportrulerReportConfigDAO;

	public ReportConfigDAO getReportrulerReportConfigDAO() {
		return reportrulerReportConfigDAO;
	}

	public void setReportrulerReportConfigDAO(
			ReportConfigDAO reportrulerReportConfigDAO) {
		this.reportrulerReportConfigDAO = reportrulerReportConfigDAO;
	}

	public List getReportConfigs(Long reportId) {
		return reportrulerReportConfigDAO.getReportConfigs(reportId);
	}

	public List getReportConfigs(Long reportId, Long funcId) {
		return reportrulerReportConfigDAO.getReportConfigs(reportId, funcId);
	}

	public List getReportConfigs(Long reportId, Long funcId, Long defInt) {
		return reportrulerReportConfigDAO.getReportConfigs(reportId, funcId,
				defInt);
	}

	public List getReportConfigs(Long pkId, Long reportId, Long funcId,
			Long defInt) {
		return reportrulerReportConfigDAO.getReportConfigs(pkId, reportId,
				funcId, defInt);
	}

	public String defChar(Long reportid, Long funid) {
		List<?> list = reportrulerReportConfigDAO.getdefChar(reportid, funid);
		ReportConfig rc = (ReportConfig) list.get(0);
		return rc.getDefChar();

	}

}
