package com.krm.ps.model.reportdefine.web.form;

import org.apache.struts.action.ActionForm;

public class ReportTargetForm extends ActionForm {

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 654423243234L;

	private Long pkid;

	private Long reportId;

	private String targetName;

	private String targetField;

	private Integer dataType;

	private String beginDate;

	private String endDate;

	private String createDate;

	private String editDate;
	private String outrule;
	private String rulesize;

	private String targetOrder;

	public ReportTargetForm() {

	}

	public String getOutrule() {
		return outrule;
	}

	public void setOutrule(String outrule) {
		this.outrule = outrule;
	}

	public String getRulesize() {
		return rulesize;
	}

	public void setRulesize(String rulesize) {
		this.rulesize = rulesize;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getTargetField() {
		return targetField;
	}

	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetOrder() {
		return targetOrder;
	}

	public void setTargetOrder(String targetOrder) {
		this.targetOrder = targetOrder;
	}

}
