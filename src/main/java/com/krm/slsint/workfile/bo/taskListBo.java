package com.krm.slsint.workfile.bo;

public class taskListBo {
	private String pkId;
	private String fpkId;
	private String reportId;
	private String isAttachment;
	private String addresser;
	private String title;
	private String date;
	private String organCode;
	private String userId;
	private String reportName;
	private String reportDate;
	private String unlock;
	public String getUnlock() {
		return unlock;
	}
	public void setUnlock(String unlock) {
		this.unlock = unlock;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getAddresser() {
		return addresser;
	}
	public void setAddresser(String addresser) {
		this.addresser = addresser;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFpkId() {
		return fpkId;
	}
	public void setFpkId(String fpkId) {
		this.fpkId = fpkId;
	}
	public String getIsAttachment() {
		return isAttachment;
	}
	public void setIsAttachment(String isAttachment) {
		this.isAttachment = isAttachment;
	}
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	public String getPkId() {
		return pkId;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

}
