package com.krm.ps.model.flexiblequery.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.krm.ps.model.flexiblequery.dao.ReportRuleDAO;
import com.krm.ps.model.flexiblequery.services.ReportRuleService;
import com.krm.ps.model.vo.ReportResult;

public class ReportRuleServiceImpl implements ReportRuleService {

	ReportRuleDAO flexiblequeryReportRuleDAO;

	public ReportRuleDAO getFlexiblequeryReportRuleDAO() {
		return flexiblequeryReportRuleDAO;
	}

	public void setFlexiblequeryReportRuleDAO(
			ReportRuleDAO flexiblequeryReportRuleDAO) {
		this.flexiblequeryReportRuleDAO = flexiblequeryReportRuleDAO;
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
		return flexiblequeryReportRuleDAO.getReportResultByDataId(cstatus, arr,
				date, tablename, targetids);
	}

	public List getReportRuleBycode(String rulecode) {
		return flexiblequeryReportRuleDAO.getReportRuleBycode(rulecode);
	}

	public List getReportType(Integer systemcode, Integer showlevel) {
		return flexiblequeryReportRuleDAO.getReportType(systemcode, showlevel);
	}

	public List getReport(List reportType) {
		return flexiblequeryReportRuleDAO.getReport(reportType);
	}

	@Override
	public List getReportType(Integer systemcode, Integer showlevel, Long userId) {
		return flexiblequeryReportRuleDAO.getReportType(systemcode, showlevel,userId);
	}

}
