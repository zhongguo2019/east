package com.krm.ps.sysmanage.reportdefine.web.form;

import org.apache.struts.action.ActionForm;
/**
 * 报表项Form
 * @struts.form name="reportItemForm"
 */
public class ReportItemForm extends ActionForm {
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 888565443342L;

	private Long pkid;
	
	private Long reportId;
	
	private String code;
	
	private String itemName;
	
	private String frequency;
	
	private Integer isCollect;
	
	private Integer isOrgCollect;
	
	private Long superId;
	
	private Integer isLeaf;
	
	private String beginDate;
	
	private String endDate;
	
	private String createDate;
	
	private String editDate;
	
	private String conCode;
	
	private Integer dataType;
	
	public ReportItemForm(){
		
	}
	
	/**
	 * 
	 * @return 数据类型
	 */
	public Integer getDataType() {
		return dataType;
	}
	
	/**
	 * 
	 * @param dataType 数据类型
	 */
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
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
	 * @return 生成日期
	 */
	public String getCreateDate() {
		return createDate;
	}
	
	/**
	 * 
	 * @param createDate 生成日期
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 
	 * @return 编辑日期
	 */
	public String getEditDate() {
		return editDate;
	}
	
	/**
	 * 
	 * @param editDate 编辑日期
	 */
	public void setEditDate(String editDate) {
		this.editDate = editDate;
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
	 * @return 是否叶子
	 */
	public Integer getIsLeaf() {
		return isLeaf;
	}
	
	/**
	 * 
	 * @param isLeaf 是否叶子
	 */
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	/**
	 * 
	 * @return 是否机构集合
	 */
	public Integer getIsOrgCollect() {
		return isOrgCollect;
	}
	
	/**
	 * 
	 * @param isOrgCollect 是否机构集合
	 */
	public void setIsOrgCollect(Integer isOrgCollect) {
		this.isOrgCollect = isOrgCollect;
	}
	
	/**
	 * 
	 * @return 科目名称
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @struts.validator type="required"
	 * @struts.validator-args arg0resource="reportdefine.report.name"
	 * @param itemName 科目名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	 * @return 上级编码
	 */
	public Long getSuperId() {
		return superId;
	}
	
	/**
	 * 
	 * @param superId 上级编码
	 */
	public void setSuperId(Long superId) {
		this.superId = superId;
	}
	
	
	
}
