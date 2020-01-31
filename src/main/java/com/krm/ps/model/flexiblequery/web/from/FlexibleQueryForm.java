package com.krm.ps.model.flexiblequery.web.from;

import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.form.BaseForm;


public class FlexibleQueryForm extends BaseForm {

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

	private String levelFlag;

	private String zhi1;
	private String zhi2;
	private String zhi3;
	

	private String targetField1;
	private String targetField2;
	private String targetField3;

  
	
	public String getZhi1() {
		return zhi1;
	}

	public void setZhi1(String zhi1) {
		this.zhi1 = zhi1;
	}

	public String getZhi2() {
		return zhi2;
	}

	public void setZhi2(String zhi2) {
		this.zhi2 = zhi2;
	}

	public String getZhi3() {
		return zhi3;
	}

	public void setZhi3(String zhi3) {
		this.zhi3 = zhi3;
	}

	public String getTargetField1() {
		return targetField1;
	}

	public void setTargetField1(String targetField1) {
		this.targetField1 = targetField1;
	}

	public String getTargetField2() {
		return targetField2;
	}

	public void setTargetField2(String targetField2) {
		this.targetField2 = targetField2;
	}

	public String getTargetField3() {
		return targetField3;
	}

	public void setTargetField3(String targetField3) {
		this.targetField3 = targetField3;
	}

	public String getLevelFlag() {
		return levelFlag;
	}

	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
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
