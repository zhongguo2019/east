package com.krm.ps.model.vo;

import java.io.Serializable;

public class CodeRepSubmitalist implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long pkid	 ;
	private String	reportname_cn      	;
	private String	reportname_en      	;
	private Long	record_number      	;
	private String	reflecting_business	;
	private String	files_name         	;
	private String	files_bytes        	;
	private String	ischecked          	;
	private String	remarks            	;
	private String organid             ;	
	private String information         ;
	
	
	
	
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getReportname_cn() {
		return reportname_cn;
	}
	public void setReportname_cn(String reportname_cn) {
		this.reportname_cn = reportname_cn;
	}
	public String getReportname_en() {
		return reportname_en;
	}
	public void setReportname_en(String reportname_en) {
		this.reportname_en = reportname_en;
	}
	public Long getRecord_number() {
		return record_number;
	}
	public void setRecord_number(Long record_number) {
		this.record_number = record_number;
	}
	public String getReflecting_business() {
		return reflecting_business;
	}
	public void setReflecting_business(String reflecting_business) {
		this.reflecting_business = reflecting_business;
	}
	public String getFiles_name() {
		return files_name;
	}
	public void setFiles_name(String files_name) {
		this.files_name = files_name;
	}
	public String getFiles_bytes() {
		return files_bytes;
	}
	public void setFiles_bytes(String files_bytes) {
		this.files_bytes = files_bytes;
	}
	public String getIschecked() {
		return ischecked;
	}
	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOrganid() {
		return organid;
	}
	public void setOrganid(String organid) {
		this.organid = organid;
	}
	
	
}
