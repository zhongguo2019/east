package com.krm.ps.model.vo;

import java.io.Serializable;

import com.krm.ps.framework.vo.BaseObject;
import com.krm.ps.util.ConvertUtil;


public class ReportFile extends BaseObject implements Serializable{
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = -1621181961074611070L;
	private Long pkId;
	private Long formatId;
	private Long reportId;
	private String organId;
	private String dataDate;
	private String createDate;
	private String status;
	private String reportData;
	
	private String organName;
	private String reportName;
	private String reportType;
	private Result updateResult;
	
	public ReportFile()
	{
		
	}
	
	public ReportFile(ReportFile reportFile,String reportName,String reportType)
	{
		ConvertUtil.copyProperties(this,reportFile);
		this.reportName = reportName;
		this.reportType = reportType;
	}
	
	
	/**
	 * @hibernate.id column="pkid" generator-class="native" 
	 * @hibernate.generator-param name="sequence" value="rep_file_pkid_seq" 
	 */
	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}
	
	/**
	 * @hibernate.property column="data_date"
	 */
	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	/**
	 * @hibernate.property column="organ_id"
	 */
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	/**
	 * @hibernate.property column="create_date"
	 */
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @hibernate.property column="report_id"
	 */
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	/**
	 * @hibernate.property column="status"
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}
	
	/**
	 * @hibernate.property column="format_id"
	 */
	public Long getFormatId() {
		return formatId;
	}

	public void setFormatId(Long formatId) {
		this.formatId = formatId;
	}

	/**
	 * @hibernate.property column="report_data"
	 */
	public String getReportData() {
		return reportData;
	}

	public void setReportData(String reportData) {
		this.reportData = reportData;
	}

	public Result getUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(Result updateResult) {
		this.updateResult = updateResult;
	}

	public String toString() {
		return null;
	}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}

}
