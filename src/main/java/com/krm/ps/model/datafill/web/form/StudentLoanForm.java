package com.krm.ps.model.datafill.web.form;

import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.form.BaseForm;

/**
 * <p>Title: </p>
 * 
 * <p>Description: </p>
 * 
 * <p>Copyright: Copyright (c) 2008</p>
 * 
 * <p>Company: KRM</p>
 * 
 * @author
 */
/**
 * 
 * @struts.form name="studentLoanForm"
 */
public class StudentLoanForm extends BaseForm {

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 4527834530749275985L;

	private Long reportId;

	private String dataDate;

	private String organId;

	private String organName;

	private String filledFlag;

	private String fillUser;

	private String checkUser;

	private String phone;

	private String[] dataSort;

	private FormFile importXlsFile;

	private String targetField;

	public String getTargetField() {
		return targetField;
	}

	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}

	public String[] getDataSort() {
		return dataSort;
	}

	public void setDataSort(String[] dataSort) {
		this.dataSort = dataSort;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
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

	public String getFilledFlag() {
		return filledFlag;
	}

	public void setFilledFlag(String filledFlag) {
		this.filledFlag = filledFlag;
	}

	public String getFillUser() {
		return fillUser;
	}

	public void setFillUser(String fillUser) {
		this.fillUser = fillUser;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public FormFile getImportXlsFile() {
		return importXlsFile;
	}

	public void setImportXlsFile(FormFile importXlsFile) {
		this.importXlsFile = importXlsFile;
	}

}
