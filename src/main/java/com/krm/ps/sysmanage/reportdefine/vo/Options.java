package com.krm.ps.sysmanage.reportdefine.vo;

public class Options {

	private String key;

	private String reportName;

	private Long reportId;
	
	public Options(String frequency, Long type, String organId, String name, Long reportId) {
		this.reportName = name;
		this.reportId = reportId;
		this.key = frequency+","+type+","+organId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

}
