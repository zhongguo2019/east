package com.krm.ps.model.reportrule.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krm.ps.model.vo.Page;
import com.krm.ps.model.vo.PaginatedListHelper;
import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

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
	 * 根据模型id和flag获得规则,其中flag0为人行,1为客户风险，2为风险预警3隐私脱敏
	 * 
	 * @param modelid
	 * @return
	 */
	public List getReportRuleFlag(String modelid, String flag);

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
	 * 获得报表对象
	 * 
	 * @param date
	 *            日期
	 * @param modelid
	 *            模型id
	 * @param ruletype
	 *            规则类型
	 * @param organId
	 *            机构id
	 * @return
	 */
	public List getReport(String reportids);

	/**
	 * 测试sql语句是否正确
	 * 
	 * @param sql
	 * @return
	 */
	public boolean testSql(String sql);

	/**
	 * 测试规则是不是有要预警生成的数据
	 * 
	 * @param sql
	 * @return
	 */
	public List getNum(String sql);

	/**
	 * 根据规则类型获得规则名称
	 * 
	 * @return
	 */
	public List getRuleType(String pkids);

	/**
	 * 获得基本规则类型
	 * 
	 * @return
	 */
	public List getBasicRuleType(String flag, String systemcode);

	/**
	 * 根据规则编码获得规则列表
	 * 
	 * @param modelid
	 * @return
	 */
	public List getReportRuleBycode(String rulecode);

	/**
	 * 获得某一类型下的所有报表
	 * 
	 * @param reportType
	 *            报表类型
	 * @return
	 */
	public List getReport(List reportType);

	/**
	 * 根据报表id，规则类型 获取报表对应的规则
	 * 
	 * @param reportList
	 * @param ruleType
	 * @return
	 */
	public List getReportRule(List reportList, List ruleType, String cstatus);

	/**
	 * 风险预警层调用的存储过程
	 */
	public Object[] checkData(String rulecode, String date, String lastdate);

	/**
	 * 根据模型id获得规则
	 * 
	 * @param modelid
	 * @return
	 */
	public List getReportRule(String modelid);

	/**
	 * 根据模型id，指标id获取规则
	 * 
	 * @param modelid
	 * @param targetid
	 * @return
	 */
	public List getReportRule(String modelid, String targetid, String rtype);

	/**
	 * 调用存储过程在程序内部校验
	 * 
	 * @param rulecode
	 */
	public Object[] checkData(String rulecode, String date);

	/**
	 * 获得校验结果
	 * 
	 * @param date
	 *            日期
	 * @param modelid
	 *            模型id
	 * @param ruletype
	 *            规则类型
	 * @param organId
	 *            机构id
	 * @return
	 */
	public List getReportResult(String date, String modelid, String ruletype,
			List organIdList, String tablename);

	/**
	 * 分页获得校验结果
	 * 
	 * @param date
	 *            日期
	 * @param modelid
	 *            模型id
	 * @param ruletype
	 *            规则类型
	 * @param organId
	 *            机构id
	 * @return
	 */
	public PaginatedListHelper getReportResultByPage(String date,
			String modelid, String ruletype, List organIdList,
			String tablename, Page page);

	/**
	 * 获取数据记录
	 * 
	 * @param relist
	 * @param datatablename
	 * @return
	 */
	public int getReportData(Set set, String datatablename);

	/**
	 * 保存数据
	 * 
	 * @param datalist
	 * @param datatablename
	 * @return
	 */
	public List saveReportData(List datalist, String datatablename);

	/**
	 * 根据日期，规则编码查询校验结果
	 * 
	 * @param rulecode
	 * @param date
	 * @return
	 */
	public List getReportResult(String rulecode, String date, String tablename);

	/**
	 * 根据数据id查询校验结果
	 * 
	 * @param dataId
	 * @return
	 */
	List<ReportResult> getReportResultByDataId(String cstatus, List idList,
			String date, String levelFlag, String targetids);

	/**
	 * 获取数据记录并且插入,用一个sql
	 * 
	 * @param relist
	 * @param datatablename
	 * @return
	 */
	public int getReportData(String rulecode, String dataDate,
			String targettable, String datatablename);

	/**
	 * 根据模型id获得模板id
	 * 
	 * @param pkid
	 * @return
	 */
	public List getTemplateByModelid(Long pkid, String targetid);

	/**
	 * 删除规则
	 * 
	 * @param rulecode
	 */
	public void deleteReportRule(String rulecode);

	/**
	 * 根据报表类型、报表日期查询出所有报表及报表数据状态（REP_DATAF_month_5表中是否有数据）
	 * 
	 * @param reportType
	 * @param reportDate
	 * @param organId
	 * @param idxid
	 * @return
	 */
	public List getReport(Long reportId, List reportType, String reportDate,
			String organId, int idxid, Long userid, String levelFlag,
			String systemcode);

	/**
	 * 判断临时表中是否有数据 验证数据校验是否通过
	 * 
	 * @param reportId
	 * @param replaceAll
	 * @param temptablename
	 * @return
	 */
	public int getErrorReportData(String reportId, String reportDate,
			String temptablename);

	/**
	 * 导出规则数据 tg 为0 代表人行标准化 为1代表客户风险
	 * 
	 * @param tg
	 */
	public void exportSystemData(String tg, String flag, String realExportedDir);

	/**
	 * 批量更新结果表
	 * 
	 * @param temMap
	 * @param reportTargetList
	 * @param valueList
	 * @param cstatusList
	 * @param dtypeList
	 * @param dataId
	 * @param reportDate
	 * @return
	 */
	public int updateReportResult(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, List dtypeList, String[] repDataSort,
			String reportDate);// 存数据状态

	/**
	 * 后台预算结果值
	 * 
	 * @param reportDate
	 * @param sql
	 * @return
	 */
	public String getRvalue(String reportDate, String sql);

	/**
	 * 批量更新數據表
	 * 
	 * @param temMap
	 * @param reportTargetList
	 * @param valueList
	 * @param cstatusList
	 * @param dtypeList
	 * @param dataId
	 * @param reportDate
	 * @return
	 */
	public int updateReportService(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, List dtypeList, String[] repDataSort,
			String reportDate);// 存数据状态

	public int insertReportService(List resultablename, String stFlag,
			Map temMap, List<ReportTarget> reportTargetList, List valueList,
			List cstatusList, String reportDate);

	/**
	 * 保存校验规则
	 * 
	 * @param rulecode
	 * @return
	 */
	public boolean saveReportRule(ReportRule reportrule);
}
