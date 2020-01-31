package com.krm.ps.model.datafill.services.impl;

import java.util.List;

import com.krm.ps.model.datafill.dao.ReportConfigDAO;
import com.krm.ps.model.datafill.services.ReportConfigService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public class ReportConfigServiceImpl implements ReportConfigService {

	private ReportConfigDAO datafillReportConfigDAO;

	public ReportConfigDAO getDatafillReportConfigDAO() {
		return datafillReportConfigDAO;
	}

	public void setDatafillReportConfigDAO(
			ReportConfigDAO datafillReportConfigDAO) {
		this.datafillReportConfigDAO = datafillReportConfigDAO;
	}

	@Override
	public List<ReportConfig> getdefCharByTem(Long reportid, Long funid) {
		// TODO Auto-generated method stub
		return datafillReportConfigDAO.getdefCharByTem(reportid, funid);
	}

}
