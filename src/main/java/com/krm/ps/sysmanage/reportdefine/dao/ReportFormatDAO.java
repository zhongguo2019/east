package com.krm.ps.sysmanage.reportdefine.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;
import com.krm.ps.sysmanage.usermanage.vo.Units;

public interface ReportFormatDAO extends DAO {

	/**
	 * 得到报表的所有格式，如果reportId=0得到所有表的格式
	 * 
	 * @param reportId
	 * @return
	 */
	public List getReportFormats(Long reportId);

	/**
	 * 根据报表ID和日期得到相应的报表格式
	 * 
	 * @param reportId
	 * @param date
	 * @param frequency
	 *            频度
	 * @return 满足条件的所有报表的清单。按照日期的降序排列。
	 */
	public ReportFormat getReportFormat(Long reportId, String date,
			String frequency);

	/**
	 * 得到报表的货币单位
	 * 
	 * @param reportId
	 * @return
	 */
	public Units getReportMoneyUnit(Long reportId);

	// /**
	// * 删除一个报表格式
	// * @param reportFormatId
	// */
	// public void removeReportFormat(Long reportFormatId);

	// /**
	// * 根据唯一标识得到一个报表格式
	// * @param reportFormatId
	// * @return
	// */
	// public ReportFormat getReportFormat(Long reportFormatId);

	/**
	 * 根据报表类型ID得到对应的报表格式列表
	 * 
	 * @param reporttype
	 * @return
	 */
	public List getReportFormatsByType(Long reporttype);
	
	/**根据报表类型ID得到对应的报表格式列表，关联报表权限
	 * @param reporttype
	 * @param userId
	 * @return
	 */
	public List getReportFormatsByType(Long reporttype,Long userId);

	/**
	 * 根据报表类型ID得到对应的报表格式列表，不取模板xml，提高效率
	 * 
	 * @param reporttype
	 * @return
	 */
	public List getReportFormatsByTypeWithoutFormatXML(Long reporttype);// wsx
																		// 4-9
	
	/**根据报表类型ID得到对应的报表格式列表，不取模板xml,关联报表权限
	 * @param reporttype
	 * @param userId
	 * @return
	 */
	public List getReportFormatsByTypeWithoutFormatXML(Long reporttype,Long userId);

	// public void saveReportFormat(ReportFormat reportFormat);

	/**
	 * 获取报表的所有单位信息列表
	 * @return List
	 */
	public List getReportUnitList();
	
	/**
	 * 判断是否是流水表
	 * @param reportID
	 * @return boolean
	 */
	public boolean isFlowTable(String reportID);
}
