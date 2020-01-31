package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @hibernate.class table="code_orgtype_report"
 */
public class ReportOrgType implements Serializable{
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 666553322999322L;

	private Long pkid;
	
	private Long reportId;
	
	private Integer organType;
	
	public ReportOrgType(){
		
	}
	/**
	 * 
	 * @hibernate.property column="organ_type"
	 */
	public Integer getOrganType() {
		return organType;
	}

	public void setOrganType(Integer organType) {
		this.organType = organType;
	}
	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_orgtype_report_seq"
	 */
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	/**
	 * 
	 * @hibernate.property column="reportid"
	 */
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("ReportOrgType", getPkid())
            .toString();
    }
}
