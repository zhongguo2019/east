package com.krm.ps.model.reportdefine.services;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public interface ReportConfigService {
	public List getReportConfigs(Long reportId);

	public List getReportConfigs(Long reportId, Long funcId);

	public List getReportConfigs(Long reportId, Long funcId, Long defInt);

	public List getReportConfigs(Long pkId, Long reportId, Long funcId,
			Long defInt);

	public List getReportConfigsByFunIdAndOrgIdsAndDate(String funId,
			String orgIds);

	public List getReportConfigFun(List configs);

	public ReportConfig getReportConfig(Long configId);

	public ReportConfig getReportConfig(Long reportId, int funId, Long idxId);

	public void removeReportConfig(Long pkid);

	public void removeReportConfig(Long repId, Long funId);

	public void saveReportConfig(ReportConfig config, int isUpdate);

	public void saveReportConfig(ReportConfig config, ReportConfig config1,
			int isUpdate);

	public void saveReportConfig(ReportConfig config, ReportConfig config1,
			ReportConfig config2, ReportConfig config3, int isUpdate);

	public void copyConfigs(Long newRepid, Long oldRepid);

	public List getReportConfigsByFunc(Long funcId);

	public String defChar(Long reportid, Long funid);

	public List<ReportConfig> getdefCharByTem(Long reportid, Long funid);

	public List getRepConfigList();

	public void saveConfig(ReportConfig config);

	public ReportConfig getReportConfigList(Long pkid);

	public void setReportConfig(ReportConfig reportConfig);

}
