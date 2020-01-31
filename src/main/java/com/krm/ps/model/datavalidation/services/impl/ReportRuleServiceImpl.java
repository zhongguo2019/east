package com.krm.ps.model.datavalidation.services.impl;

import java.util.List;

import com.krm.ps.model.datavalidation.dao.ReportRuleDAO;
import com.krm.ps.model.datavalidation.services.ReportRuleService;


public class ReportRuleServiceImpl implements ReportRuleService {

	ReportRuleDAO reportdao;

	

	

	

	public ReportRuleDAO getReportdao() {
		return reportdao;
	}

	public void setReportdao(ReportRuleDAO reportdao) {
		this.reportdao = reportdao;
	}
	
	/**
	 * 获报表或都数据模型的类型
	 * 
	 * @param systemcode
	 *            系统标识 （比如人行标准化和客户风险）
	 * @param showlevel
	 *            不同层次的模型标识（采集层为1 目标层为2 levelflag）
	 * @return
	 */
	public List getReportType(Integer systemcode, Integer showlevel) {
		return reportdao.getReportType(systemcode, showlevel);
	}

	public List getDateOrganEditReportForStandard(String paramDate,
			Long userId, String systemId, String levelFlag) {
		return reportdao.getDateOrganEditReportForStandard(paramDate, userId,
				systemId, levelFlag);
	}
	
	/**
	 * 获得基本规则类型
	 * 
	 * @return
	 */
	public List getBasicRuleType(String flag, String systemcode) {
		return reportdao.getBasicRuleType(flag, systemcode);
	}


	/**
	 * 根据报表id，规则类型 获取报表对应的规则
	 * 
	 * @param reportList
	 * @param ruleType
	 * @return
	 */

	public List getReportRule(List reportList, List ruleType, String cstatus) {
		return reportdao.getReportRule(reportList, ruleType, cstatus);
	}


	/**
	 * 根据规则编码和校验状态 清洗结果
	 * 
	 * @param rulecode
	 * @param cstatus
	 */
	public int deleteReportResult(String rulecode, String organId,
			String tablename,int idx,int isAdmin) {
		return reportdao.deleteReportResult(rulecode, organId, tablename,idx,isAdmin);
	}

	/**
	 * 调用存储过程在程序内部校验
	 * 
	 * @param rulecode
	 */
	public Object[] checkData(String rulecode, String date,String organId,int isAdmin,String userOrganid) {
		return reportdao.checkData(rulecode, date,organId,isAdmin,userOrganid);
	}

	@Override
	public List getResultfull(String dataDate, String tabName) {
		return reportdao.getResultfull(dataDate,tabName);
	}

	@Override
	public int getRuleCheckProgress(String organId) {
		return reportdao.getRuleCheckProgress(organId);
	}

	@Override
	public List getRuleCheckProgressList(String organId, String reportid) {
		return reportdao.getRuleCheckProgressList(organId, reportid);
	}

	@Override
	public int deleteRuleCheckProgress(String organId) {
		return reportdao.deleteRuleCheckProgress(organId);
	}

	@Override
	public String selectOrganTreeSql(String organId, int isAdmin, int idx) {
		return reportdao.selectOrganTreeSql(organId, isAdmin, idx);
	}
}
