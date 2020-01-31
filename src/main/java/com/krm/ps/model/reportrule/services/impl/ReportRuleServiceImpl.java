package com.krm.ps.model.reportrule.services.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krm.ps.model.reportrule.dao.ReportRuleDAO;
import com.krm.ps.model.reportrule.services.ReportRuleService;
import com.krm.ps.model.vo.Page;
import com.krm.ps.model.vo.PaginatedListHelper;
import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.util.FileUtil;

public class ReportRuleServiceImpl implements ReportRuleService {

	ReportRuleDAO reportrulerRportRuledao;

	private static final String ENCODING = "GBK";

	public ReportRuleDAO getReportrulerRportRuledao() {
		return reportrulerRportRuledao;
	}

	public void setReportrulerRportRuledao(ReportRuleDAO reportrulerRportRuledao) {
		this.reportrulerRportRuledao = reportrulerRportRuledao;
	}

	public boolean testSql(String sql) {
		return reportrulerRportRuledao.testSql(sql);
	}

	public List getNum(String sql) {
		return reportrulerRportRuledao.getNum(sql);
	}

	public List getReportType(Integer systemcode, Integer showlevel) {
		return reportrulerRportRuledao.getReportType(systemcode, showlevel);
	}

	public List getReport(List reportType) {
		return reportrulerRportRuledao.getReport(reportType);
	}

	public List getBasicRuleType(String flag, String systemcode) {
		return reportrulerRportRuledao.getBasicRuleType(flag, systemcode);
	}

	public List getDateOrganEditReportForStandard(String paramDate,
			Long userId, String systemId, String levelFlag) {
		return reportrulerRportRuledao.getDateOrganEditReportForStandard(
				paramDate, userId, systemId, levelFlag);
	}

	public List getReportRule(List reportList, List ruleType, String cstatus) {
		return reportrulerRportRuledao.getReportRule(reportList, ruleType,
				cstatus);
	}

	public List getReportRule(String modelid) {
		return reportrulerRportRuledao.getReportRule(modelid);
	}

	public List getReportRuleBycode(String rulecode) {
		return reportrulerRportRuledao.getReportRuleBycode(rulecode);
	}

	public List getReportRuleFlag(String modelid, String flag) {
		return reportrulerRportRuledao.getReportRuleFlag(modelid, flag);
	}

	public List getReportRule(String modelid, String targetid, String rtype) {
		return reportrulerRportRuledao.getReportRule(modelid, targetid, rtype);
	}

	public boolean saveReportRule(ReportRule reportrule) {
		return reportrulerRportRuledao.saveReportRule(reportrule);
	}

	public Object[] checkData(String rulecode, String date) {
		return reportrulerRportRuledao.checkData(rulecode, date);
	}

	public Object[] checkData(String rulecode, String date, String lastdate) {
		return reportrulerRportRuledao.checkData(rulecode, date, lastdate);
	}

	public List getReportResult(String date, String modelid, String ruletype,
			List organIdList, String tablename) {
		return reportrulerRportRuledao.getReportResult(date, modelid, ruletype,
				organIdList, tablename);
	}

	public List getReportResult(String rulecode, String date, String tablename) {
		return reportrulerRportRuledao.getReportResult(rulecode, date,
				tablename);
	}

	public int getReportData(Set set, String datatablename) {
		return reportrulerRportRuledao.getReportData(set, datatablename);
	}

	public List getRuleType(String pkids) {
		return reportrulerRportRuledao.getRuleType(pkids);
	}

	public List saveReportData(List datalist, String datatablename) {
		return reportrulerRportRuledao.saveReportData(datalist, datatablename);
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
		return reportrulerRportRuledao.getReportResultByDataId(cstatus, arr,
				date, tablename, targetids);
	}

	public int getReportData(String rulecode, String dataDate,
			String targettable, String datatablename) {
		return reportrulerRportRuledao.getReportData(rulecode, dataDate,
				targettable, datatablename);
	}

	@Override
	public PaginatedListHelper getReportResultByPage(String date,
			String modelid, String ruletype, List organIdList,
			String tablename, Page page) {
		return reportrulerRportRuledao.getReportResultByPage(date, modelid,
				ruletype, organIdList, tablename, page);
	}

	public List getTemplateByModelid(Long pkid, String targetid) {
		return reportrulerRportRuledao.getTemplateByModelid(pkid, targetid);
	}

	public void deleteReportRule(String rulecode) {
		ReportRule rr = new ReportRule();
		rr.setRulecode(rulecode);
		reportrulerRportRuledao.deleteReportRule(rr);
	}

	public List getReport(Long reportId, List reportType, String reportDate,
			String organId, int idxid, Long userid, String levelFlag,
			String systemcode) {
		return reportrulerRportRuledao.getReport(reportId, reportType,
				reportDate, organId, idxid, userid, levelFlag, systemcode);
	}

	public List getReportGuide(Long reportId, String levelFlag,
			String systemcode, String reportDate, String organId, int idxid,
			Long userId) {
		return reportrulerRportRuledao.getReportGuide(reportId, levelFlag,
				systemcode, reportDate, organId, idxid, userId);
	}

	public List getReportGuideDetail(Long reportId, String targettable,
			String organId, int idxid, String reportDate) {
		return reportrulerRportRuledao.getReportGuideDetail(reportId,
				targettable, organId, idxid, reportDate);
	}

	public List getReport(String reportids) {
		return reportrulerRportRuledao.getReport(reportids);
	}

	@Override
	public int getErrorReportData(String reportId, String reportDate,
			String temptablename) {
		return reportrulerRportRuledao.getErrorReportData(reportId, reportDate,
				temptablename);
	}

	public void exportSystemData(String tg, String flag, String realExportedDir) {
		List rulelist = reportrulerRportRuledao.getReportRuleByTg(tg, flag);
		StringBuffer reportrule = new StringBuffer();
		reportrule = reportrule.append(makeExportStringBufferRule(rulelist));
		String fileName = null;
		if ("0".equals(flag)) {
			fileName = realExportedDir + "CODE_REP_RULE_S.crr";
		} else if ("1".equals(flag)) {
			fileName = realExportedDir + "CODE_REP_RULE_T.crr";
		} else {
			fileName = realExportedDir + "CODE_REP_RULE_W.crr";
		}
		if (reportrule.length() > 0) {
			try {
				FileUtil.makeFile(reportrule.toString(), fileName, ENCODING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				reportrule = new StringBuffer("");
			}
		}
	}

	public StringBuffer makeExportStringBufferRule(List rulelist) {
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < rulelist.size(); i++) {
			ReportRule rr = (ReportRule) rulelist.get(i);
			strBuffer.append(replaceNR(rr.getRulecode()) + "@");
			strBuffer.append(replaceNR(rr.getModelid()) + "@");
			strBuffer.append(replaceNR(rr.getTargetid()) + "@");
			strBuffer.append(replaceNR(rr.getTargetname()) + "@");
			strBuffer.append(replaceNR(rr.getRtype()) + "@");
			strBuffer.append(replaceNR(rr.getContent()) + "@");
			strBuffer.append(replaceNR(rr.getContentdes()) + "@");
			strBuffer.append(replaceNR(rr.getKeyid()) + "@");
			strBuffer.append(replaceNR(rr.getRulemsg()) + "@");
			strBuffer.append(replaceNR(rr.getTargettable()) + "@");
			strBuffer.append(replaceNR(rr.getCstatus()) + "@");
			strBuffer.append(replaceNR(rr.getGroupid()) + "@");
			strBuffer.append(replaceNR(rr.getFlag()) + "@");
			strBuffer.append(replaceNR(rr.getSystemcode()) + "@");
			strBuffer.append(replaceNR(rr.getRangelevel()));
			strBuffer.append("\r\n");
		}
		return strBuffer;
	}

	private String replaceNR(String str) {
		if ("".equals(str)) {
			str = null;
		}
		if (str != null) {
			str.replaceAll(" {2,}", " ").replaceAll("\n", "")
					.replaceAll("\r", "");
		}
		return str;
	}

	public int updateReportResult(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, List dtypeList, String[] repDataSort,
			String reportDate) {
		return reportrulerRportRuledao.updateReportResult(resultablename,
				stFlag, temMap, reportTargetList, valueList, cstatusList,
				dtypeList, repDataSort, reportDate);
	}

	public int deleteReportResult(String rulecode, String cstatus,
			String tablename) {
		return reportrulerRportRuledao.deleteReportResult(rulecode, cstatus,
				tablename);
	}

	public String getRvalue(String reportDate, String sql) {
		// String tablename="CODE_REP_RESULT_T_"+reportDate.substring(5,7);
		return reportrulerRportRuledao.getRvalue(sql, null);

	}

	public int updateReportService(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, List dtypeList, String[] repDataSort,
			String reportDate) {
		return reportrulerRportRuledao.updateReport(resultablename, stFlag,
				temMap, reportTargetList, valueList, cstatusList, dtypeList,
				repDataSort, reportDate);
	}

	@Override
	public int insertReportService(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, String reportDate) {

		return reportrulerRportRuledao.insertReportService(resultablename,
				stFlag, temMap, reportTargetList, valueList, cstatusList,
				reportDate);
	}
}
