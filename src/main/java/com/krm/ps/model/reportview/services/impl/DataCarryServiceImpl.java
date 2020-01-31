package com.krm.ps.model.reportview.services.impl;

import com.krm.ps.model.reportview.services.DataCarryService;
import com.krm.ps.sysmanage.reportdefine.dao.ReportConfigDAO;

public class DataCarryServiceImpl implements DataCarryService {

	ReportConfigDAO reportConfigDAO;

	public ReportConfigDAO getReportConfigDAO() {
		return reportConfigDAO;
	}

	public void setReportConfigDAO(ReportConfigDAO reportConfigDAO) {
		this.reportConfigDAO = reportConfigDAO;
	}

	public boolean needCarry(Long reportId) {

		return reportConfigDAO.isLastTermDataCarry(reportId.toString(), "21");

	}

}
