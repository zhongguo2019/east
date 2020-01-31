package com.krm.ps.sysmanage.reportdefine.web.form;

import org.apache.struts.action.ActionForm;

/**
 * 报表类型Form
 * @struts.form name="reportTypeForm"
 */
public class ReportTypeForm extends ActionForm{

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 666655533332L;

	private Long pkid;
	
	private String name;
	
	private Integer dataSource;
	
	private Integer isBalance;
	
	private Integer isCollect;
	
	private Integer isInput;
	
	private Integer isUpdate;
	
	private Integer systemCode;
	
	private Integer isShowRep;
	
	private Integer upReport;
	private Integer showlevel;
	public Integer getShowlevel() {
		return showlevel;
	}

	public void setShowlevel(Integer showlevel) {
		this.showlevel = showlevel;
	}

	public ReportTypeForm(){
		
	}
	
	/**
	 * 
	 * @return 不平报表
	 */
	public Integer getUpReport() {
		return upReport;
	}
	
	/**
	 * 
	 * @param upReport 不平报表
	 */
	public void setUpReport(Integer upReport) {
		this.upReport = upReport;
	}
	
	/**
	 * 
	 * @return 报表是否展示
	 */
	public Integer getIsShowRep() {
		return isShowRep;
	}
	
	/**
	 * 
	 * @param isShowRep 报表是否展示
	 */
	public void setIsShowRep(Integer isShowRep) {
		this.isShowRep = isShowRep;
	}
	
	/**
	 * 
	 * @return 系统编码
	 */
	public Integer getSystemCode() {
		return systemCode;
	}
	
	/**
	 * 
	 * @param systemCode 系统编码
	 */
	public void setSystemCode(Integer systemCode) {
		this.systemCode = systemCode;
	}
	
	/**
	 * 
	 * @return 数据源
	 */
	public Integer getDataSource() {
		return dataSource;
	}
	
	/**
	 * 
	 * @param dataSource 数据源
	 */
	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 
	 * @return 是否调平
	 */
	public Integer getIsBalance() {
		return isBalance;
	}
	
	/**
	 * 
	 * @param isBalance 是否调平
	 */
	public void setIsBalance(Integer isBalance) {
		this.isBalance = isBalance;
	}
	
	/**
	 * 
	 * @return 是否集合
	 */
	public Integer getIsCollect() {
		return isCollect;
	}
	
	/**
	 * 
	 * @param isCollect 是否集合
	 */
	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}
	
	/**
	 * 
	 * @return 是否输入
	 */
	public Integer getIsInput() {
		return isInput;
	}
	
	/**
	 * 
	 * @param isInput 是否输入
	 */
	public void setIsInput(Integer isInput) {
		this.isInput = isInput;
	}
	
	/**
	 * 
	 * @return 是否更新
	 */
	public Integer getIsUpdate() {
		return isUpdate;
	}
	
	/**
	 * 
	 * @param isUpdate 是否更新
	 */
	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
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
	 * @struts.validator-args arg0resource="reportdefine.reportType.name"
	 * @param name 名称
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

	
}
