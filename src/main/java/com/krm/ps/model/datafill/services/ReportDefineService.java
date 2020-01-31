package com.krm.ps.model.datafill.services;

import java.util.List;
import java.util.Map;

import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

public interface ReportDefineService {

	public List getReports(String date, Long userid);

	public List<ReportTarget> getReportTargets(Long reportId);

	public Report getReport(Long pkid);

	public Map getTemplate(Long reportid);
}
