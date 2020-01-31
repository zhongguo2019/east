package com.krm.ps.model.flexiblequery.services;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

public interface ReportDefineService {

	public List<ReportTarget> getReportTargets(Long reportId);

	/**
	 * 
	 * @param pkid
	 * @return Report
	 */
	public Report getReport(Long pkid);
}
