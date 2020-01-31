package com.krm.ps.sysmanage.reportdefine.web.form;

import org.apache.struts.action.ActionForm;
/**
 * 报表配置Form
 * @struts.form name="reportConfigForm"
 */
public class ReportConfigForm extends ActionForm {
	private static final long serialVersionUID = -9211116679244249021L;
	
	/**
	 * <code></code>
	 */
	public ReportConfigForm() {
		
	}
	
	private Long pkid;
	
	private Long pkid1;
	
	private Long reportId;
	
	private Long funId;
	
	private Long idxId;
	
	private Long defInt;
	
	private Long defInt1;
	
	private String defChar;
	
	private String description;
	
	private String newDataAttribute;
	
	private String oldDataAttribute;
	
	private String reportName;
	
	private String repId;
	
	private String organs;
	
	private String organMode;
	
	private String organLevel;
	
	private int [] role;

	/**
	 * 
	 * @return 默认字符串
	 */
	public String getDefChar() {
		return defChar;
	}

	/**
	 * 
	 * @param defChar 默认字符串
	 */
	public void setDefChar(String defChar) {
		this.defChar = defChar;
	}

	/**
	 * 
	 * @return 默认整数
	 */
	public Long getDefInt() {
		return defInt;
	}

	/**
	 * 
	 * @param defInt 默认整数
	 */
	public void setDefInt(Long defInt) {
		this.defInt = defInt;
	}

	/**
	 * 
	 * @return 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return 功能编码
	 */
	public Long getFunId() {
		return funId;
	}

	/**
	 * 
	 * @param funId 功能编码
	 */
	public void setFunId(Long funId) {
		this.funId = funId;
	}

	/**
	 * 
	 * @return 索引编码
	 */
	public Long getIdxId() {
		return idxId;
	}

	/**
	 *  
	 * @param idxId 索引编码
	 */
	public void setIdxId(Long idxId) {
		this.idxId = idxId;
	}

	/**
	 * 
	 * @return 主键编码
	 */
	public Long getPkid() {
		return pkid;
	}

	/**
	 * 
	 * @param pkid 主键编码
	 */
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	/**
	 * 
	 * @return 报表编码
	 */
	public Long getReportId() {
		return reportId;
	}

	/**
	 *  
	 * @param reportId 报表编码
	 */
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	/**
	 * 
	 * @return 默认整数1
	 */
	public Long getDefInt1() {
		return defInt1;
	}

	/**
	 * 
	 * @param defInt1 默认整数
	 */
	public void setDefInt1(Long defInt1) {
		this.defInt1 = defInt1;
	}

	/**
	 * 
	 * @return 主键编码1
	 */
	public Long getPkid1() {
		return pkid1;
	}

	/**
	 * 
	 * @param pkid1 主键编码1
	 */
	public void setPkid1(Long pkid1) {
		this.pkid1 = pkid1;
	}

	/**
	 * 
	 * @return 新数据属性
	 */
	public String getNewDataAttribute() {
		return newDataAttribute;
	}

	/**
	 * 
	 * @param newDataAttribute 新数据属性
	 */
	public void setNewDataAttribute(String newDataAttribute) {
		this.newDataAttribute = newDataAttribute;
	}

	/**
	 * 
	 * @return 原数据属性
	 */
	public String getOldDataAttribute() {
		return oldDataAttribute;
	}

	/**
	 * 
	 * @param oldDataAttribute 原数据属性
	 */
	public void setOldDataAttribute(String oldDataAttribute) {
		this.oldDataAttribute = oldDataAttribute;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName()
	{
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName)
	{
		this.reportName = reportName;
	}

	/**
	 * @return the repId
	 */
	public String getRepId()
	{
		return repId;
	}

	/**
	 * @param repId the repId to set
	 */
	public void setRepId(String repId)
	{
		this.repId = repId;
	}

	/**
	 * @return the organLevel
	 */
	public String getOrganLevel()
	{
		return organLevel;
	}

	/**
	 * @param organLevel the organLevel to set
	 */
	public void setOrganLevel(String organLevel)
	{
		this.organLevel = organLevel;
	}

	/**
	 * @return the organMode
	 */
	public String getOrganMode()
	{
		return organMode;
	}

	/**
	 * @param organMode the organMode to set
	 */
	public void setOrganMode(String organMode)
	{
		this.organMode = organMode;
	}

	/**
	 * @return the organs
	 */
	public String getOrgans()
	{
		return organs;
	}

	/**
	 * @param organs the organs to set
	 */
	public void setOrgans(String organs)
	{
		this.organs = organs;
	}

	/**
	 * @return the role
	 */
	public int[] getRole()
	{
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(int[] role)
	{
		this.role = role;
	}
	
	
	
}
