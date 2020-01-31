package com.krm.ps.sysmanage.reportdefine.services;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;
import com.krm.ps.sysmanage.usermanage.vo.Units;

public interface ReportFormatService {

	/**
	 * 得到报表的所有格式，如果reportId=0得到所有表的格式
	 * 
	 * @param reportId
	 * @return
	 */
	public List getReportFormats(Long reportId);

	/**
	 * 得到报表的货币单位
	 * 
	 * @param reportId
	 * @return
	 */
	public Units getReportMoneyUnit(Long reportId);

	/**
	 * 删除一个报表格式
	 * 
	 * @param reportFormatId
	 */
	public void removeReportFormat(Long reportFormatId);

	/**
	 * 根据唯一标识得到一个报表格式
	 * 
	 * @param reportFormatId
	 * @return
	 */
	public ReportFormat getReportFormat(Long reportFormatId);

	/**
	 * 根据报表ID和日期得到相应的报表格式
	 * 
	 * @param reportId
	 * @param date
	 * @param frequency
	 *            频度
	 * @return
	 */
	public ReportFormat getReportFormat(Long reportId, String date,
			String frequency);

	/**
	 * 保存定义的报表格式
	 * 
	 * @param reportFormat
	 */
	public void saveReportFormat(ReportFormat reportFormat);

	/**
	 * 根据报表类型ID得到对应的报表格式列表
	 * 
	 * @param reporttype
	 * @param needFormatXml
	 *            是否需要加载模板xml，如果不加载可以快一些//wsx 4-9
	 * @return
	 */
	public List getReportFormatsByType(Long reporttype, boolean needFormatXml);
	
	/**
	 * 根据报表类型ID得到对应的报表格式列表,关联报表权限
	 * 
	 * @param reporttype
	 * @param userId
	 * @param needFormatXml
	 *            是否需要加载模板xml，如果不加载可以快一些//wsx 4-9
	 * @return
	 */
	public List getReportFormatsByType(Long reporttype, Long userId,boolean needFormatXml);

	/**
	 * 处理广西地区分析表特殊模板转换
	 * @param reportFormat
	 * @param organCodes
	 * @param organNames
	 */
	public ReportFormat processSpecial(ReportFormat reportFormat, String organCodes, String date);
}
