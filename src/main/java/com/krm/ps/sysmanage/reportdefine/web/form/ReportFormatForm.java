package com.krm.ps.sysmanage.reportdefine.web.form;

import com.krm.ps.framework.common.web.form.BaseForm;

/**
 * 报表模板Form
 * @struts.form name="reportFormatForm"
 */
public class ReportFormatForm extends BaseForm implements java.io.Serializable
{
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 8305058380678062875L;

	private Long reporttype;
	private Long reportId;
	private Long pkId;
	private String reportFormat;
	private String beginDate;
	private String endDate;
	private String frequency;
	
	/**
	 * 
	 * @return 报表编码
	 */
	public Long getReportId() {
		return reportId;
	}
	
	/**
	 * 
	 * @param reportId 报表编码
	 */
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	/**
	 * 
	 * @return 报表类型
	 */
	public Long getReporttype() {
		return reporttype;
	}
	
	/**
	 * 
	 * @param reporttype 报表类型
	 */
	public void setReporttype(Long reporttype) {
		this.reporttype = reporttype;
	}
	
	/**
	 *  
	 * @return 主键编码
	 */
	public Long getPkId() {
		return pkId;
	}
	
	/**
	 * 
	 * @param pkId 主键编码
	 */ 
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}
	
	/**
	 * 
	 * @return 报表模板
	 */
	public String getReportFormat() {
		return reportFormat;
	}
	
	/**
	 * 
	 * @param reportFormat 报表模板
	 */
	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}
	
	/**
	 * 
	 * @return 开始日期
	 */
	public String getBeginDate() {
		return beginDate;
	}
	
	/**
	 * 
	 * @param beginDate 开始日期
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 
	 * @return 结束日期
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * 
	 * @param endDate 结束日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 
	 * @return 频度
	 */
	public String getFrequency() {
		return frequency;
	}
	
	/**
	 * 
	 * @param frequency 频度
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}
