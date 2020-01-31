package com.krm.ps.model.reportrule.services;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public interface ReportConfigService {
	/**
	 * 根据报表Id得到报表配置列表
	 * 
	 * @param reportId
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long reportId);

	/**
	 * 根据报表Id,功能Id得到报表配置列表
	 * 
	 * @param reportId
	 * @param funcId
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long reportId, Long funcId);

	/**
	 * 根据报表Id, 功能Id, 值得到报表配置列表
	 * 
	 * @param reportId
	 * @param funcId
	 * @param defInt
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long reportId, Long funcId, Long defInt);

	/**
	 * 根据pkid, 报表Id, 功能Id, 值得到报表配置
	 * 
	 * @param pkId
	 * @param reportId
	 * @param funcId
	 * @param defInt
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long pkId, Long reportId, Long funcId,
			Long defInt);

	public String defChar(Long reportid, Long funid);

}
