package com.krm.ps.sysmanage.reportdefine.web.form;

import org.apache.struts.action.ActionForm;

public class ReportTempForm extends ActionForm{
	
	private Long pkid;
	
	private String name;//模板名称
	
	private String code;//模板编码
	
	private Long showorder;	//显示序号
	
	private Long reportType;//模板类型
	
	private String description;//模板描述
	
	private String updatetemp;//判断是添加还是修改
	
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getShoworder() {
		return showorder;
	}

	public void setShoworder(Long showorder) {
		this.showorder = showorder;
	}

	public Long getReportType() {
		return reportType;
	}

	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUpdatetemp() {
		return updatetemp;
	}

	public void setUpdatetemp(String updatetemp) {
		this.updatetemp = updatetemp;
	}
	
	
}
