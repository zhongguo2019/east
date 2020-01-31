package com.krm.ps.sysmanage.reportdefine.web.form;

import org.apache.struts.action.ActionForm;
/**
 * 报表指标Form
 * @struts.form name="reportTargetForm"
 */
public class ReportTargetForm extends ActionForm {
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 654423243234L;

	private Long pkid;
	
	private Long reportId;
	
	private String targetName;
	
	private String targetField;
	
	private Integer dataType;
	
	private String beginDate;
	
	private String endDate;
	
	private String createDate;
	
	private String editDate;
	 private String outrule;//格式化类型
     private String rulesize;//保留小数位数或指标长度
     
     private String targetOrder;
	
	
	public ReportTargetForm(){
		
	}
	
	public String getOutrule() {
		return outrule;
	}

	public void setOutrule(String outrule) {
		this.outrule = outrule;
	}

	public String getRulesize() {
		return rulesize;
	}


	public void setRulesize(String rulesize) {
		this.rulesize = rulesize;
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
	 * @return 指标字段
	 */
	public String getTargetField() {
		return targetField;
	}
	
	/**
	 * 
	 * @param targetField 指标字段
	 */
	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}
	
	/**
	 * 
	 * @return 指标名称
	 */ 
	public String getTargetName() {
		return targetName;
	}
	
	/**
	 * 
	 * @param targetName 指标名称
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetOrder() {
		return targetOrder;
	}

	public void setTargetOrder(String targetOrder) {
		this.targetOrder = targetOrder;
	}

	
	
}
