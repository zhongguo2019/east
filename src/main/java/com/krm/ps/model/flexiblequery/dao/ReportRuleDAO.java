package com.krm.ps.model.flexiblequery.dao;

import java.util.List;

import com.krm.ps.model.vo.ReportResult;

public interface ReportRuleDAO {

	/**
	 * 根据数据id查询校验结果
	 * 
	 * @param dataId
	 * @return
	 */
	public List<ReportResult> getReportResultByDataId(String cstatus,
			String[] idArr, String date, String levelFlag, String targetids);

	/**
	 * 根据规则编码获得规则列表
	 * 
	 * @param modelid
	 * @return
	 */
	public List getReportRuleBycode(String rulecode);

	/**
	 * 获报表或都数据模型的类型
	 * 
	 * @param systemcode
	 *            系统标识 （比如人行标准化和客户风险）
	 * @param showlevel
	 *            不同层次的模型标识（采集层为1 目标层为2 levelflag）
	 * @return
	 */
	public List getReportType(Integer systemcode, Integer showlevel);

	/**
	 * 获得某一类型下的所有报表
	 * 
	 * @param reportType
	 *            报表类型
	 * @return
	 */
	public List getReport(List reportType);

	public List getReportType(Integer systemcode, Integer showlevel, Long userId);
}
