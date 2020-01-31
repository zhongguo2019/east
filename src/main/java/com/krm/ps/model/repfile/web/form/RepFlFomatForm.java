package com.krm.ps.model.repfile.web.form;

import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.form.BaseForm;

public class RepFlFomatForm extends BaseForm implements java.io.Serializable {

	private Long pkid;
	private String repid;
	private String repfilename;
	private String repfileexte;
	private String repfilesepar;
	private String startdate;
	private String enddate;
	private String createdate;
	private String organCode;
	private String xmlFile;
	private String status;
	private String repname;
	private String[] reportId;

	private FormFile dataFile;
	// private Long pkid;
	private String ktrPath;
	private String userName;
	private String logonName;
	private String ktrTime;
	private String ktrName;
	private String ktrRemark;
	private String attribute1;
	private String attribute2;
	private String attribute3;
	private String attribute4;
	private String attribute5;

	public FormFile getDataFile() {
		return dataFile;
	}

	public void setDataFile(FormFile dataFile) {
		this.dataFile = dataFile;
	}

	public String getKtrPath() {
		return ktrPath;
	}

	public void setKtrPath(String ktrPath) {
		this.ktrPath = ktrPath;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getKtrTime() {
		return ktrTime;
	}

	public void setKtrTime(String ktrTime) {
		this.ktrTime = ktrTime;
	}

	public String getKtrName() {
		return ktrName;
	}

	public void setKtrName(String ktrName) {
		this.ktrName = ktrName;
	}

	public String getKtrRemark() {
		return ktrRemark;
	}

	public void setKtrRemark(String ktrRemark) {
		this.ktrRemark = ktrRemark;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String[] getReportId() {
		return reportId;
	}

	public void setReportId(String[] reportId) {
		this.reportId = reportId;
	}

	public String getRepname() {
		return repname;
	}

	public void setRepname(String repname) {
		this.repname = repname;
	}

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getRepid() {
		return repid;
	}

	public void setRepid(String repid) {
		this.repid = repid;
	}

	public String getRepfilename() {
		return repfilename;
	}

	public void setRepfilename(String repfilename) {
		this.repfilename = repfilename;
	}

	public String getRepfileexte() {
		return repfileexte;
	}

	public void setRepfileexte(String repfileexte) {
		this.repfileexte = repfileexte;
	}

	public String getRepfilesepar() {
		return repfilesepar;
	}

	public void setRepfilesepar(String repfilesepar) {
		this.repfilesepar = repfilesepar;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
