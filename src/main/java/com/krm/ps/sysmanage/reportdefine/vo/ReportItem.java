package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @hibernate.class table="code_rep_item"
 */
public class ReportItem implements Serializable{
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 556333L;

	private Long pkid;
	
	private Long reportId;
	
	private String code;
	
	private String itemName;
	
	private Integer itemOrder;
	
	private String frequency;
	
	private Integer isCollect;
	
	private Integer isOrgCollect;
	
	private Long superId;
	
	private Integer isLeaf;
	
	private String beginDate;
	
	private String endDate;
	
	private String createDate;
	
	private String editDate;
	
	private Integer status;
	
	private String conCode;
	
	private Integer dataType;
	
	public ReportItem(){
		
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
	 * @hibernate.property column="con_code"
	 */
	public String getConCode() {
		return conCode;
	}

	public void setConCode(String conCode) {
		this.conCode = conCode;
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
	 * 
	 * @hibernate.property column="frequency"
	 */
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_rep_item_seq"
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
	 * @hibernate.property column="code"
	 */
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	 * @hibernate.property column="is_collect"
	 */
	public Integer getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}
	
	/**
	 * 
	 * @hibernate.property column="is_leaf"
	 */
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	/**
	 * 
	 * @hibernate.property column="is_org_collect"
	 */
	public Integer getIsOrgCollect() {
		return isOrgCollect;
	}
	public void setIsOrgCollect(Integer isOrgCollect) {
		this.isOrgCollect = isOrgCollect;
	}
	
	/**
	 * 
	 * @hibernate.property column="item_name"
	 */
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	/**
	 * 
	 * @hibernate.property column="item_order"
	 */
	public Integer getItemOrder() {
		return itemOrder;
	}
	public void setItemOrder(Integer itemOrder) {
		this.itemOrder = itemOrder;
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
	
	/**
	 * 
	 * @hibernate.property column="super_id"
	 */
	public Long getSuperId() {
		return superId;
	}
	public void setSuperId(Long superId) {
		this.superId = superId;
	}
	
	public String toString() {
        return new ToStringBuilder(this)
            .append("ReportItem", getPkid())
            .toString();
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
}
