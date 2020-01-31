package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;
/**
 * 
 * @hibernate.class table="collect_report"
 */
public class ReportTemplate implements Serializable{
	
	private Long pkid;//主键
	private String code;//报表或者模型编码
	private Long reporttype;//报表或者模型类型
	private String name;//报表或者模板名称
	private String phytable;//物理表名称
	private String description;//描述
	private Long showorder;//显示序号
	private String ismodel;//是否是模板
	private Long createid;//创建该报表的用户
	private String retypename;
	public ReportTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReportTemplate(Long pkid, String code, Long reporttype, String name,
			String phytable, String description, Long showorder,
			String ismodel, Long createid) {
		super();
		this.pkid = pkid;
		this.code = code;
		this.reporttype = reporttype;
		this.name = name;
		this.phytable = phytable;
		this.description = description;
		this.showorder = showorder;
		this.ismodel = ismodel;
		this.createid = createid;
	}
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public Long getReporttype() {
		return reporttype;
	}
	public void setReporttype(Long reporttype) {
		this.reporttype = reporttype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhytable() {
		return phytable;
	}
	public void setPhytable(String phytable) {
		this.phytable = phytable;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getShoworder() {
		return showorder;
	}
	public void setShoworder(Long showorder) {
		this.showorder = showorder;
	}
	public String getIsmodel() {
		return ismodel;
	}
	public void setIsmodel(String ismodel) {
		this.ismodel = ismodel;
	}
	public Long getCreateid() {
		return createid;
	}
	public void setCreateid(Long createid) {
		this.createid = createid;
	}
	public String getRetypename() {
		return retypename;
	}
	public void setRetypename(String retypename) {
		this.retypename = retypename;
	}
	
	
}
