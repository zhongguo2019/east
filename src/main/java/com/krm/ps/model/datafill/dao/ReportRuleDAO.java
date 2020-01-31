package com.krm.ps.model.datafill.dao;

import java.util.List;
import java.util.Map;

import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

public interface ReportRuleDAO {

	public int insertReportService(List<ReportConfig> resultablename,
			String stFlag, Map temMap, List<ReportTarget> reportTargetList,
			List valueList, List cstatusList, String reportDate);

	public List<ReportResult> getReportResultByDataId(String cstatus,
			String[] idArr, String date, String levelFlag, String targetids);

	public List getReportType(Integer systemcode, Integer showlevel);

	public List getReport(List reportType);

	public int updateReportResult(List<ReportConfig> resultablename,
			String stFlag, Map temMap, List<ReportTarget> reportTargetList,
			List valueList, List cstatusList, List dtypeList,
			String[] repDataSort, String reportDate);

	public List getReportRuleBycode(String rulecode);

	public int updateReport(List<ReportConfig> resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, List dtypeList, String[] repDataSort,
			String reportDate);

	public List getReportGuide(Long reportId, String levelFlag,
			String systemcode, String reportDate, String organId, int idxid,
			Long userId);

	public List getReportGuideDetail(Long reportId, String targettable,
			String organId, int idxid, String reportDate);

	public int deleteReportResult(String rulecode, String cstatus,
			String tablename);

	public Object[] checkData(String rulecode, String date);

	public List getRulecode(String reportid);

	public int getReportnum(String tergettbale, String dataDate);

	public List getDateOrganEditReport(String replaceAll, int paramOrganType,
			int i, Long userId, String systemId, String levelFlag);

}
