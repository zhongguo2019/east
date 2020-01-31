package com.krm.ps.model.datavalidation.web.form;

import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.form.BaseForm;

public class ReportViewForm extends BaseForm implements java.io.Serializable {
	private static final long serialVersionUID = -911908330382597377L;
	private Long pkId;
	private String organId;
	private Long reportId;
	private Long reportId1;
	private Long formatId;
	private String dataDate;
	private Long reportType;
	private String reportData;
	private String frequency;
	private String logType;
	private String unitCode;

	private String cellRowCol;
	private String cellFormula;
	private String cellValue;
	private String reportName;
	private String organName;
	private FormFile importFile;
	private FormFile importXlsFile;

	private String customerCode;
	private String contractCode;
	private String mode;

	public static final String OPER_MODE_DISP = "display";
	public static final String OPER_MODE_EDIT = "edit";
	private String sumType = "";

	public FormFile getImportFile() {
		return importFile;
	}

	public Long getReportId1() {
		return reportId1;
	}

	public void setReportId1(Long reportId1) {
		this.reportId1 = reportId1;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}

	public FormFile getImportXlsFile() {
		return importXlsFile;
	}

	public void setImportXlsFile(FormFile importXlsFile) {
		this.importXlsFile = importXlsFile;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getCellFormula() {
		return cellFormula;
	}

	public void setCellFormula(String cellFormula) {
		this.cellFormula = cellFormula;
	}

	public String getCellRowCol() {
		return cellRowCol;
	}

	public void setCellRowCol(String cellRowCol) {
		this.cellRowCol = cellRowCol;
	}

	public String getCellValue() {
		return cellValue;
	}

	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public Long getReportType() {
		return reportType;
	}

	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}

	public String getReportData() {
		return reportData;
	}

	public void setReportData(String reportData) {
		this.reportData = reportData;
	}

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public Long getFormatId() {
		return formatId;
	}

	public void setFormatId(Long formatId) {
		this.formatId = formatId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSumType() {
		return sumType;
	}

	public void setSumType(String sumType) {
		this.sumType = sumType;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

}
