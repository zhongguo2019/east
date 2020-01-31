package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.krm.ps.util.ConvertUtil;
/**
 * 
 * @hibernate.class table="code_rep_report"
 */
public class Report implements Serializable{
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 77544322L;

	private Long pkid;
	
	private String code;
	
	private String conCode;
	
	private Long reportType;
	
	private String reportTypeName;
	
	private String name;
	
	private Integer rol;
	
	private String frequency;
	
	private Integer moneyunit;
	
	private String phyTable;
	
	private String beginDate;
	
	private String endDate;
	
	private Integer status;
	
	private String description;
	
	private Integer showOrder = new Integer(0);
	
	private Integer isSum;
	
	private String[] organType;
	
	private Long superId;
	
	private Integer createId;
	private Integer isIncrement;//是否增量 0代表增量 1代表全量
	
	/**
	 * <code>指定的调平精度</code>
	 */
	private String precision;
	
	private String balanced;
	
	private String repname;
	
	
	
	public String getRepname() {
		return repname;
	}
	public void setRepname(String repname) {
		this.repname = repname;
	}
	public Report(){
		
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

	public Integer getIsIncrement() {
		return isIncrement;
	}
	public void setIsIncrement(Integer isIncrement) {
		this.isIncrement = isIncrement;
	}
	public String[] getOrganType() {
		return organType;
	}

	public void setOrganType(String[] organType) {
		this.organType = organType;
	}

	public Report(Report report,String reportTypeName){
		ConvertUtil.copyProperties(this,report);
		this.reportTypeName = reportTypeName;
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
	 * @hibernate.property column="description"
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	 * 
	 * @hibernate.property column="moneyunit"
	 */
	public Integer getMoneyunit() {
		return moneyunit;
	}

	public void setMoneyunit(Integer moneyunit) {
		this.moneyunit = moneyunit;
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
	 * 
	 * @hibernate.property column="phy_table"
	 */
	public String getPhyTable() {
		return phyTable;
	}

	public void setPhyTable(String phyTable) {
		this.phyTable = phyTable;
	}
	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_rep_report_seq"
	 */
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	/**
	 * 
	 * @hibernate.property column="report_type"
	 */
	public Long getReportType() {
		return reportType;
	}

	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}

	public String getReportTypeName() {
		return reportTypeName;
	}

	public void setReportTypeName(String reportTypeName) {
		this.reportTypeName = reportTypeName;
	}
	/**
	 * 
	 * @hibernate.property column="rol"
	 */
	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = rol;
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
	 * @hibernate.property column="is_sum"
	 */
	public Integer getIsSum() {
		return isSum;
	}

	public void setIsSum(Integer isSum) {
		this.isSum = isSum;
	}
	/**
	 * 
	 * @hibernate.property column="show_order"
	 */
	public Integer getShowOrder() {
		return showOrder;
	}
	
	/**
	 * 
	 * @hibernate.property column="precision"
	 */
	public String getPrecision() {
		return precision;
	}
	
	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("Report", getPkid())
            .toString();
    }
	public Long getSuperId() {
		return superId;
	}
	public void setSuperId(Long superId) {
		this.superId = superId;
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
	public String getBalanced() {
		return balanced;
	}
	public void setBalanced(String balanced) {
		this.balanced = balanced;
	}
	
}
