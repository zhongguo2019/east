package com.krm.ps.model.datafill.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krm.ps.model.datafill.dao.ReportRuleDAO;
import com.krm.ps.model.datafill.services.ReportRuleService;
import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.util.DateUtil;

public class ReportRuleServiceImpl implements ReportRuleService {
	ReportRuleDAO datafillReportRuleDAO;

	public ReportRuleDAO getDatafillReportRuleDAO() {
		return datafillReportRuleDAO;
	}

	public void setDatafillReportRuleDAO(ReportRuleDAO datafillReportRuleDAO) {
		this.datafillReportRuleDAO = datafillReportRuleDAO;
	}

	@Override
	public int insertReportService(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, String reportDate) {

		return datafillReportRuleDAO.insertReportService(resultablename,
				stFlag, temMap, reportTargetList, valueList, cstatusList,
				reportDate);
	}

	public List<ReportResult> getReportResultByDataId(String cstatus,
			List idList, String date, String tablename, String targetids) {
		Set set = new HashSet();
		for (int i = 0; i < idList.size(); i++) {
			set.add(idList.get(i));
		}
		String[] arr = new String[set.size()];
		int i = 0;
		for (Object o : set) {
			arr[i] = String.valueOf(o);
			i++;
		}
		return datafillReportRuleDAO.getReportResultByDataId(cstatus, arr,
				date, tablename, targetids);
	}

	public List getReportType(Integer systemcode, Integer showlevel) {
		return datafillReportRuleDAO.getReportType(systemcode, showlevel);
	}

	public List getReport(List reportType) {
		return datafillReportRuleDAO.getReport(reportType);
	}

	public int updateReportResult(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, List dtypeList, String[] repDataSort,
			String reportDate) {
		return datafillReportRuleDAO.updateReportResult(resultablename, stFlag,
				temMap, reportTargetList, valueList, cstatusList, dtypeList,
				repDataSort, reportDate);
	}

	public List getReportRuleBycode(String rulecode) {
		return datafillReportRuleDAO.getReportRuleBycode(rulecode);
	}

	public int updateReportService(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, List dtypeList, String[] repDataSort,
			String reportDate) {
		      reportDate = DateUtil.formatDate(reportDate); //处理时间格式
		return datafillReportRuleDAO.updateReport(resultablename, stFlag,
				temMap, reportTargetList, valueList, cstatusList, dtypeList,
				repDataSort, reportDate);
	}

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
			Long userId) {
		return datafillReportRuleDAO.getReportGuide(reportId, levelFlag,
				systemcode, reportDate, organId, idxid, userId);
	}

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
			String organId, int idxid, String reportDate) {
		return datafillReportRuleDAO.getReportGuideDetail(reportId,
				targettable, organId, idxid, reportDate);
	}

	/**
	 * 根据规则编码和校验状态 清洗结果
	 * 
	 * @param rulecode
	 * @param cstatus
	 */
	public int deleteReportResult(String rulecode, String cstatus,
			String tablename) {
		return datafillReportRuleDAO.deleteReportResult(rulecode, cstatus,
				tablename);
	}

	/**
	 * 调用存储过程在程序内部校验
	 * 
	 * @param rulecode
	 */
	public Object[] checkData(String rulecode, String date) {
		return datafillReportRuleDAO.checkData(rulecode, date);
	}

	@Override
	public List getRulecode(String reportid) {
		return datafillReportRuleDAO.getRulecode(reportid);
	}

	@Override
	public int getReportnum(String tergettbale, String dataDate) {
		return datafillReportRuleDAO.getReportnum(tergettbale, dataDate);
	}

	@Override
	public List getDateOrganEditReport(String replaceAll, int paramOrganType,
			int i, Long userId, String systemId, String levelFlag) {
		return datafillReportRuleDAO.getDateOrganEditReport(replaceAll,
				paramOrganType, i, userId, systemId, levelFlag);
	}
}
