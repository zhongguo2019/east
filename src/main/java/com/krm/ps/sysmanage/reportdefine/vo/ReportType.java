package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.krm.ps.util.ConvertUtil;

/**
 * 
 * @hibernate.class table="code_rep_types"
 */
public class ReportType implements Serializable{
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 775533322L;

	private Long pkid;
	
	private String name;
	
	private Integer dataSource;
	
	private Integer isBalance;
	
	private Integer isCollect;
	
	private Integer isInput;
	
	private Integer isUpdate;
	
	private Integer status;
	
	private Integer systemCode;
	
	private Integer isShowRep;
	
	private Long showOrder;
	
	private Integer upReport;
	
	private String showing;
	
	private Integer createId;
	
	private Integer showlevel;
	
	
	
	
	public Integer getShowlevel() {
		return showlevel;
	}

	public void setShowlevel(Integer showlevel) {
		this.showlevel = showlevel;
	}

	public ReportType(){   
	}
	
	public ReportType(ReportType reportType){
		try {
			
			ConvertUtil.copyProperties(this,reportType , true);
			this.showing=reportType.getPkid()+":"+reportType.getIsShowRep();		
		} catch (Exception e) {
			;
		}
	}
	/**
	 * 
	 * @hibernate.property column="up_report"
	 */
	public Integer getUpReport() {
		return upReport;
	}

	public void setUpReport(Integer upReport) {
		this.upReport = upReport;
	}

	/**
	 * 
	 * @hibernate.property column="is_showrep"
	 */
	public Integer getIsShowRep() {
		return isShowRep;
	}

	public void setIsShowRep(Integer isShowRep) {
		this.isShowRep = isShowRep;
	}
	/**
	 * 
	 * @hibernate.property column="show_order"
	 */
	public Long getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Long showOrder) {
		this.showOrder = showOrder;
	}

	/**
	 * 
	 * @hibernate.property column="system_code"
	 */
	public Integer getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(Integer systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * 
	 * @hibernate.property column="data_source"
	 */
	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 
	 * @hibernate.property column="is_balance"
	 */
	public Integer getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(Integer isBalance) {
		this.isBalance = isBalance;
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
	 * @hibernate.property column="is_input"
	 */
	public Integer getIsInput() {
		return isInput;
	}

	public void setIsInput(Integer isInput) {
		this.isInput = isInput;
	}
	
	/**
	 * 
	 * @hibernate.property column="is_update"
	 */
	public Integer getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	/**
	 * 
	 * @hibernate.property column="name"
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_rep_types_seq"
	 */
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
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
	
	public String getShowing() {
		return showing;
	}
	public void setShowing(String showing) {
		this.showing = showing;
	}
	public String toString() {
        return new ToStringBuilder(this)
            .append("ReportType", getPkid())
            .toString();
    }

	/**
	 * 
	 * @hibernate.property column="createid"
	 */
	public Integer getCreateId()
	{
		return createId;
	}

	public void setCreateId(Integer createId)
	{
		this.createId = createId;
	}

}
