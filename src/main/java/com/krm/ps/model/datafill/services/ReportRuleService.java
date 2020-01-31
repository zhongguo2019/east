package com.krm.ps.model.datafill.services;

import java.util.List;
import java.util.Map;

import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.model.datafill.vo.StatusForm;

public interface ReportRuleService {

	public int insertReportService(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, String reportDate);

	List<ReportResult> getReportResultByDataId(String cstatus, List idList,
			String date, String levelFlag, String targetids);

	public List getReportType(Integer systemcode, Integer showlevel);

	public List getReport(List reportType);

	public int updateReportResult(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, List dtypeList, String[] repDataSort,
			String reportDate);

	public List getReportRuleBycode(String rulecode);

	public int updateReportService(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, List dtypeList, String[] repDataSort,
			String reportDate);// 存数据状态״̬

	/**
	 * 查询哪些机构有哪些表要补录
	 * 
	 * @param reportId
	 * @param reportType
	 * @param reportDate
	 * @param organId
	 * @param idxid
	 * @return
	 */
	public List getReportGuide(Long reportId, String levelFlag,
			String systemcode, String reportDate, String organId, int idxid,
			Long userId);

	/**
	 * 查询机构详细补录信息
	 * 
	 * @param reportId
	 * @param reportType
	 * @param reportDate
	 * @param organId
	 * @param idxid
	 * @return
	 */
	public List getReportGuideDetail(Long reportId, String targettable,
			String organId, int idxid, String reportDate);

	/**
	 * 根据规则编码和校验状态 清洗结果
	 * 
	 * @param rulecode
	 * @param cstatus
	 */
	public int deleteReportResult(String rulecode, String cstatus,
			String tablename);

	/**
	 * 调用存储过程在程序内部校验
	 * 
	 * @param rulecode
	 */
	public Object[] checkData(String rulecode, String date);

	/**
	 * 查询规则id
	 * 
	 * @param systemcode
	 * @param showlevel
	 * @return
	 */
	public List getRulecode(String reportid);

	/**
	 * 查询未补录数据条数
	 * 
	 * @param systemcode
	 * @param showlevel
	 * @return
	 */
	public int getReportnum(String tergettbale, String dataDate);

	public List getDateOrganEditReport(String replaceAll, int paramOrganType,
			int i, Long userId, String systemId, String levelFlag);
}
