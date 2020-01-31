package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;

public class ReportConfigFun implements Serializable{
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = -5881445234831616914L;

	private Long pkid;
	
	private Long reportId;
	
	// 对应属性reportId的报表名称
	private String reportName;
	
	private Long funId;
	
	private String funName;
	
	private Long idxId;
	
	private Long defInt;
	
	private String defIntName;
	
	private String defChar;
	
	private String defCharName;
	
	private String description;
	
	
	public ReportConfigFun(){
		
	}


	public String getDefChar() {
		return defChar;
	}


	public void setDefChar(String defChar) {
		this.defChar = defChar;
	}


	public Long getDefInt() {
		return defInt;
	}


	public void setDefInt(Long defInt) {
		this.defInt = defInt;
	}


	public String getDefIntName() {
		return defIntName;
	}


	public void setDefIntName(String defIntName) {
		this.defIntName = defIntName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Long getFunId() {
		return funId;
	}


	public void setFunId(Long funId) {
		this.funId = funId;
	}


	public String getFunName() {
		return funName;
	}


	public void setFunName(String funName) {
		this.funName = funName;
	}


	public Long getIdxId() {
		return idxId;
	}


	public void setIdxId(Long idxId) {
		this.idxId = idxId;
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


	public String getReportName()
	{
		return reportName;
	}


	public void setReportName(String reportName)
	{
		this.reportName = reportName;
	}


	/**
	 * @return the defCharName
	 */
	public String getDefCharName()
	{
		return defCharName;
	}


	/**
	 * @param defCharName the defCharName to set
	 */
	public void setDefCharName(String defCharName)
	{
		this.defCharName = defCharName;
	}
	
	

}
