package com.krm.ps.model.reportrule.services;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

public interface ReportDefineService {

	public List<ReportTarget> getReportTargets(Long reportId);

	public List getReportTargets(Long reportId, String tableName);

	/**
	 * 根据报表id得到报表对象
	 * 
	 * @param pkid
	 *            报表id
	 * @return Report
	 */
	public Report getReport(Long pkid);

	/**
	 * 根据报表id得到科目信息
	 * 
	 * @param reportId
	 *            报表id
	 * @return {@link ReportItem)对象列表
	 */
	public List getReportItems(Long reportId);

	/**
	 * 根据 债券，股权类型查询报表
	 * 
	 * @param reportId
	 * @return
	 */
	public List getReportTargetsStock(Long reportId, String stocktype);
}
