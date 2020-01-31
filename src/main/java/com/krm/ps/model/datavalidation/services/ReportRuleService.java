package com.krm.ps.model.datavalidation.services;

import java.util.List;

public interface ReportRuleService {

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

	public List getDateOrganEditReportForStandard(String paramDate,
			Long userId, String systemId, String levelFlag);

	/**
	 * 获得基本规则类型
	 * 
	 * @return
	 */
	public List getBasicRuleType(String flag, String systemcode);

	/**
	 * 根据报表id，规则类型 获取报表对应的规则
	 * 
	 * @param reportList
	 * @param ruleType
	 * @return
	 */

	public List getReportRule(List reportList, List ruleType, String cstatus);

	/**
	 * 根据规则编码和校验状态 清洗结果
	 * 
	 * @param rulecode
	 * @param cstatus
	 */
	public int deleteReportResult(String rulecode, String organId,
			String tablename,int idx,int isAdmin);

	/**
	 * 调用存储过程在程序内部校验
	 * 
	 * @param rulecode
	 */
	public Object[] checkData(String rulecode, String date,String organId,int isAdmin,String userOrganid);
	
	/*
	 * 查询校验规则信息
	 */
	public List getResultfull(String dataDate, String tabName);
	
	/**
	 * 查询规则执行进度
	 * @param organId
	 * @return
	 */
	public int getRuleCheckProgress(String organId);
	
	/**
	 * 查询每个规则执行进度情况
	 * @param organId
	 * @return
	 */
	public List getRuleCheckProgressList(String organId,String reportid);

	/**
	 * 清洗校验进度结果
	 * 
	 * @param rulecode
	 * @param cstatus
	 */
	public int deleteRuleCheckProgress(String organId);
	
	/**
	 * 通过机构号查询下级机构
	 * @param organId
	 * @param isAdmin
	 * @param idx
	 * @return
	 */
	public String selectOrganTreeSql(String organId,int isAdmin,int idx);
}
