package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @hibernate.class table="code_rep_target"
 */
public class ReportTarget implements Serializable{
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 6664488L;

	private Long pkid;
	
	private Long reportId;
	
	private String targetName;
	
	private String targetField;
	
	private Integer dataType;
	
	private Integer targetOrder;
	
	private String beginDate;
	
	private String endDate;
	
	private String createDate;
	
	private String editDate;
	
	private Integer status;
	
	private Integer fieldType;
	
	private Long dicPid;
	
	private String stocktype;   //区分债券和股权
	
	 private String outrule;//格式化类型
	 
     private String rulesize;//保留小数位数或指标长度
     
     private String nowsize;// 数据库中的实际长度
     private String targetCode;//指标编码
     
     
     
	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public String getNowsize() {
		return nowsize;
	}

	public void setNowsize(String nowsize) {
		this.nowsize = nowsize;
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

	public String getStocktype() {
		return stocktype;
	}

	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}

	public Long getDicPid() {
		return dicPid;
	}

	public void setDicPid(Long dicPid) {
		this.dicPid = dicPid;
	}

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}

	public ReportTarget(){
		
	}
	
	/**
	 * 
	 * @hibernate.property column="data_type"
	 */
	public Integer getDataType() {
		return dataType;
	}


	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}


	/**
	 * 
	 * @hibernate.property column="edit_date"
	 */
	public String getEditDate() {
		return editDate;
	}



	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}


	/**
	 * 
	 * @hibernate.property column="target_field"
	 */
	public String getTargetField() {
		return targetField;
	}



	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}


	/**
	 * 
	 * @hibernate.property column="target_name"
	 */
	public String getTargetName() {
		return targetName;
	}



	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}


	/**
	 * 
	 * @hibernate.property column="target_order"
	 */
	public Integer getTargetOrder() {
		return targetOrder;
	}



	public void setTargetOrder(Integer targetOrder) {
		this.targetOrder = targetOrder;
	}



	/**
	 * 
	 * @hibernate.property column="end_date"
	 */
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_rep_target_seq"
	 */
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	
	/**
	 * 
	 * @hibernate.property column="begin_date"
	 */
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	
	/**
	 * 
	 * @hibernate.property column="create_date"
	 */
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 
	 * @hibernate.property column="report_id"
	 */
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	/**
	 * 
	 * @hibernate.property column="status"
	 */
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public String toString() {
        return new ToStringBuilder(this)
            .append("ReportTarget", getPkid())
            .toString();
    }
}
