package com.krm.ps.model.sysLog.util;



public class LogUtil
{
	// 调用日志外界接口使用的常量定义
	public static final String LogType_LOGIN = "10";// 用户登录
	public static final String LogType_User_Operate = "90";// 用户操作日志
	public static final String LogType_GatherData = "11";// 数据采集

	public static final String LogType_GatherDel = "12";// 采集数据删除

	public static final String LogType_GatherCheckOK = "13";// 采集校验通过

	public static final String LogType_GatherCheckNO = "14";// 采集校验不通过

	public static final String LogType_GatherAuditOK = "15";// 采集审核通过

	public static final String LogType_GatherAuditNO = "16";// 采集审核不通过
	
	public static final String LogType_OfflineLock = "17";// 离线录入报表锁定

	public static final String LogType_CollectData = "21";// 数据汇总

	public static final String LogType_BuildData = "31";// 数据生成

	public static final String LogType_CyrrencyBuild = "32";// 外汇转换

	public static final String LogType_ReportCheckOK = "41";// 表内校验通过

	public static final String LogType_ReportCheckNO = "42";// 表内校验不通过

	public static final String LogType_ReportAuditOK = "43";// 表间审核通过

	public static final String LogType_ReportAuditNO = "44";// 表间审核不通过

	public static final String LogType_ReportBalanceOK = "45";// 调平通过

	public static final String LogType_ReportBalanceNO = "46";// 调平不通过

	public static final String LogType_ReportFormat = "51";// 录入修改报表数据
	
	public static final String LogType_StudentLoanFilled = "52";//助学贷款违约填报完成

	/**
	 * 2010-6-13 上午05:23:29 皮亮修改
	 * 报表加锁日志
	 */
	public static final String LogType_Report_Lock = "55";	// 报表加锁日志
	
	/**
	 * <code>主任数据签报通过</code>
	 */
	public static final String LogType_DirectorAuditOK = "61";// 主任数据签报通过

	/**
	 * <code>主任数据签报不通过</code>
	 */
	public static final String LogType_DirectorAuditNO = "62";// 主任数据签报不通过

	/**
	 * <code>科处长审核通过</code>
	 */
	public static final String LogType_DeptHeaderAuditOK = "63";// 科处长审核通过

	/**
	 * <code>科处长审核没有通过</code>
	 */
	public static final String LogType_DeptHeaderAuditNO = "64";// 科处长审核没有通过
	
	public static final String LogType_slsAuditOk = "69";//省联社审核通过

	public static final String LogType_AgainReport = "71";// 数据重报申请

	public static final String LogType_PassReport = "72";// 批准重报

	public static final String LogType_ReportYJH = "81";// 数据上报银监会

	public static final String LogType_ReportYH = "82";// 数据上报央行

	public static final String LogType_ReportDataProduce = "91";// 数据系统上报生成

	public static final String LogType_ReportDataReceive = "92";// 上报数据接收

	public static final String LogType_ReportDataReceiveApply = "93";// 重新上报数据申请

	public static final String LogType_ReportDataReceiveAgree = "94";// 重新上报数据同意

	public static final String LogType_ReportDataReceiveRefuse = "95";// 重新上报数据驳回

	public static final String LogType_XmlDownLoadAgree = "96";// 下载XML审核通过

	public static final String LogType_XmlDownLoadRefuse = "97";// 下载XML审核驳回

	public static final String  LogType_StandardReportVerify = "1";// 人行标准化报表审核
	public static final String LogType_REP_PDF_OK = "98";// 综合业务系统pdf文件正常生成

	public static final String LogType_StatReportExport = "a1";// 统计报表导出

	public static final String ATTR_LOG_LIST = "logList";

	public static final String ATTR_BACKLIST_URL = "backUrl";
	

	
	/**
	 * 广东Excel自动导入功能，需要进行日志的记录，分别代表导入成功、导入失败、未导入
	 */
	public static final String EXPORT_AUTO_SUCCESS = "e1";
	
	public static final String EXPORT_AUTO_FAILURE = "e0";
	
	public static final String EXPORT_AUTO_NOEXPORT = "en";

	
	/**
	 * <code>重新生成数据时需要删除的日志类型</code>
	 */
	public static final String LogTypes_FOR_DEL_IN_BUILD = LogType_BuildData + "," +LogType_CyrrencyBuild  + "," 
		+ LogType_ReportCheckOK  + "," + LogType_ReportCheckNO  + "," +
		LogType_ReportAuditOK  + "," + LogType_ReportAuditNO  + "," + LogType_ReportBalanceOK  + "," +
		LogType_ReportBalanceNO  + "," + LogType_ReportFormat  + "," + LogType_DirectorAuditOK  + "," +
		LogType_DirectorAuditNO  + "," + LogType_DeptHeaderAuditOK  + "," + LogType_DeptHeaderAuditNO  + "," +
		LogType_AgainReport  + "," + LogType_PassReport  + "," + LogType_ReportYJH  + "," + 
		LogType_ReportYH  + "," + LogType_ReportDataProduce  + "," + LogType_ReportDataReceive  + "," +
		LogType_ReportDataReceiveApply  + "," + LogType_ReportDataReceiveAgree  + "," +
		LogType_ReportDataReceiveRefuse  + "," + LogType_XmlDownLoadAgree  + "," +
		LogType_XmlDownLoadRefuse  + "," + LogType_StatReportExport  + "," + LogType_REP_PDF_OK;
	
	/**
	 * <code>重新汇总数据时需要删除的日志类型</code>
	 */
	public static final String LogTypes_FOR_DEL_IN_COLLECT = LogType_CollectData + "," +LogType_CyrrencyBuild  + "," 
		+ LogType_ReportCheckOK  + "," + LogType_ReportCheckNO  + "," +
		LogType_ReportAuditOK  + "," + LogType_ReportAuditNO  + "," + LogType_ReportBalanceOK  + "," +
		LogType_ReportBalanceNO  + "," + LogType_ReportFormat  + "," + LogType_DirectorAuditOK  + "," +
		LogType_DirectorAuditNO  + "," + LogType_DeptHeaderAuditOK  + "," + LogType_DeptHeaderAuditNO  + "," +
		LogType_AgainReport  + "," + LogType_PassReport  + "," + LogType_ReportYJH  + "," + 
		LogType_ReportYH  + "," + LogType_ReportDataProduce  + "," + LogType_ReportDataReceive  + "," +
		LogType_ReportDataReceiveApply  + "," + LogType_ReportDataReceiveAgree  + "," +
		LogType_ReportDataReceiveRefuse  + "," + LogType_XmlDownLoadAgree  + "," +
		LogType_XmlDownLoadRefuse  + "," + LogType_StatReportExport  + "," + LogType_REP_PDF_OK;
	
	
}
