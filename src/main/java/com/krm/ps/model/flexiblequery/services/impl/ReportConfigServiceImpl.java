package com.krm.ps.model.flexiblequery.services.impl;

import java.util.List;

import com.krm.ps.model.flexiblequery.dao.ReportConfigDAO;
import com.krm.ps.model.flexiblequery.services.ReportConfigService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public class ReportConfigServiceImpl implements ReportConfigService {

	private ReportConfigDAO flexiblequeryReportConfigDAO;

	public ReportConfigDAO getFlexiblequeryReportConfigDAO() {
		return flexiblequeryReportConfigDAO;
	}

	public void setFlexiblequeryReportConfigDAO(
			ReportConfigDAO flexiblequeryReportConfigDAO) {
		this.flexiblequeryReportConfigDAO = flexiblequeryReportConfigDAO;
	}

	public List<ReportConfig> getdefCharByTem(Long reportid, Long funid) {
		return flexiblequeryReportConfigDAO.getdefCharByTem(reportid, funid);
	}
}
