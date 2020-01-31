package com.krm.ps.sysmanage.reportdefine.web.form;

import org.apache.struts.action.ActionForm;
/**
 * 报表Form
 * @struts.form name="reportForm"
 */
public class ReportForm extends ActionForm {
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 2223344244566L;

	private Long pkid;
	
	private String code;
	
	private String conCode;
	
	private Long reportType;
	
	private String reportTypeName;
	
	private String name;
	
	private Integer rol;
	
	private String[] frequencyArray;
	
	private String frequency;
	
	private Integer moneyunit;
	
	private String beginDate;
	
	private String endDate;
	
	private String description;
	
	private Integer isSum;
	
	private String[] organType;
	
	private String phyTable;
	private Integer isIncrement;
	
	/**
	 * <code>指定的调平精度</code>
	 */
	private String precision;
	
	public ReportForm(){
		
	}
	
	/**
	 * 
	 * @return 物理表
	 */
	public String getPhyTable() {
		return phyTable;
	}
	
	/**
	 * 
	 * @param phyTable 物理表
	 */
	public void setPhyTable(String phyTable) {
		this.phyTable = phyTable;
	}
	
	/**
	 * 
	 * @return 频度
	 */
	public String getFrequency() {
		return frequency;
	}
	
	/**
	 * 
	 * @param frequency 频度
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * 
	 * @return 控制编码
	 */
	public String getConCode() {
		return conCode;
	}
	
	/**
	 * 
	 * @param conCode 控制编码
	 */
	public void setConCode(String conCode) {
		this.conCode = conCode;
	}
	
	/**
	 * 
	 * @return 机构类型数组
	 */
	public String[] getOrganType() {
		return organType;
	}
	
	/**
	 * 
	 * @param organType 机构类型数组
	 */
	public void setOrganType(String[] organType) {
		this.organType = organType;
	}
	
	/**
	 * 
	 * @return 开始日期
	 */
	public String getBeginDate() {
		return beginDate;
	}
	
	/**
	 * 
	 * @param beginDate 开始日期
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 
	 * @return 编码
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 
	 * @param code 编码
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return 结束日期
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * 
	 * @param endDate 结束日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 
	 * @return 频度数组
	 */
	public String[] getFrequencyArray() {
		return frequencyArray;
	}
	
	/**
	 * 
	 * @param frequency 频度数组
	 */
	public void setFrequencyArray(String[] frequency) {
		this.frequencyArray = frequency;
	}
	
	/**
	 * 
	 * @return 货币单位
	 */
	public Integer getMoneyunit() {
		return moneyunit;
	}
	
	/**
	 * 
	 * @param moneyunit 货币单位
	 */
	public void setMoneyunit(Integer moneyunit) {
		this.moneyunit = moneyunit;
	}
	
	/**
	 * 
	 * @return 名称
	 */ 
	public String getName() {
		return name;
	}
	/**
	 * @struts.validator type="required"
	 * @struts.validator-args arg0resource="reportdefine.report.name"
	 * @param 名称
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return 报表类型
	 */
	public Long getReportType() {
		return reportType;
	}
	
	/**
	 * 
	 * @param reportType 报表类型
	 */
	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}
	
	/**
	 * 
	 * @return 报表类型名称
	 */
	public String getReportTypeName() {
		return reportTypeName;
	}
	
	/**
	 * 
	 * @param reportTypeName 报表类型名称
	 */
	public void setReportTypeName(String reportTypeName) {
		this.reportTypeName = reportTypeName;
	}
	
	/**
	 * 
	 * @return 角色
	 */
	public Integer getRol() {
		return rol;
	}
	
	/**
	 * 
	 * @param rol 角色
	 */
	public void setRol(Integer rol) {
		this.rol = rol;
	}
	
	/**
	 * 
	 * @return 是否汇总
	 */
	public Integer getIsSum() {
		return isSum;
	}
	
	/**
	 * 
	 * @param isSum 是否汇总
	 */
	public void setIsSum(Integer isSum) {
		this.isSum = isSum;
	}
	
	/**
	 * 
	 * @return 指定的调平精度
	 */
	public String getPrecision() {
		return precision;
	}
	
	public Integer getIsIncrement() {
		return isIncrement;
	}

	public void setIsIncrement(Integer isIncrement) {
		this.isIncrement = isIncrement;
	}

	/**
	 * 
	 * @param precision 指定的调平精度
	 */
	public void setPrecision(String precision) {
		this.precision = precision;
	}

}
