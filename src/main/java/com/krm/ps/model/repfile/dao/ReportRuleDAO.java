package com.krm.ps.model.repfile.dao;

import java.util.List;

public interface ReportRuleDAO {

	/**
	 * 根据模型id和flag获得规则,其中flag0为人行,1为客户风险，2为风险预警
	 * 
	 * @param modelid
	 * @return
	 */
	public List getReportRuleFlag(String modelid, String flag);

}
