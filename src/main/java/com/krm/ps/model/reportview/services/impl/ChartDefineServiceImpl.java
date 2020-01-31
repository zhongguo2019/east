package com.krm.ps.model.reportview.services.impl;

import com.krm.ps.model.reportview.dao.ReportChartDAO;
import com.krm.ps.model.reportview.services.ChartDefineService;

public class ChartDefineServiceImpl implements ChartDefineService {
	private ReportChartDAO dao;

	public void setReportChartDAO(ReportChartDAO dao) {
		this.dao = dao;
	}

	public boolean haveChart(long format, boolean onlyEnabled) {
		return dao.getChartCount(format, onlyEnabled) > 0;
	}

	@Override
	public void sort(String list) {
	}

}
