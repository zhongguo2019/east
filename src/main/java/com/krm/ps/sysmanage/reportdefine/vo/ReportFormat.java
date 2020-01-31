package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;

import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.DateUtil;
import com.krm.ps.framework.vo.BaseObject;

/**
 * @hibernate.class table="rep_format"
 */
public class ReportFormat extends BaseObject implements Serializable{

	private static final long serialVersionUID = -246505833722120708L;
	private Long pkId;
	private Long reportId;
	private String reportFormat;
	private String beginDate;
	private String endDate;
	private String createDate;
	private String editDate;
	private String frequency;
	private String reportName;
	private Integer createId;
	
	/**
	 * 默认构造函数
	 */
	public ReportFormat() {		
	}
	
	/**
	 * 构造函数
	 * @param formateInfoStr 逗号分割的，与数据库中REP_FORMAT表各字段对应的（不含report_format字段）字符串
	 */
	public ReportFormat(String formateInfoStr) {
		String[] fieldList = formateInfoStr.split(",");
		this.setReportId(new Long(fieldList[1]));
		this.setBeginDate(fieldList[2]);
		this.setEndDate(fieldList[3]);
		this.setCreateDate(fieldList[4]);
		this.setEditDate(fieldList[5]);
		this.setFrequency(fieldList[6]);
	}
	
	public ReportFormat(ReportFormat reportFormat,String reportName)
	{
		ConvertUtil.copyProperties(this,reportFormat);
		this.reportName = reportName;
	}
	
	/**
	 * 左少杰增加，把报表格式的频度改为报表频度显示
	 * @param reportFormat
	 * @param reportName
	 * @param frequency
	 */
	public ReportFormat(ReportFormat reportFormat,String reportName,String frequency)
	{
		ConvertUtil.copyProperties(this,reportFormat);
		this.reportName = reportName;
		this.frequency = frequency;
		if(reportFormat.getBeginDate() != null)
			this.beginDate = DateUtil.formatDate2(reportFormat.getBeginDate());//左少杰增加，适应日期格式调整
		if(reportFormat.getEndDate() != null)
			this.endDate = DateUtil.formatDate2(reportFormat.getEndDate());
	}
	
	/**
	 * @hibernate.property column="begin_date"
	 */
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * @hibernate.property column="create_date"
	 */
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * @hibernate.property column="edit_date"
	 */
	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	
	/**
	 * @hibernate.property column="end_date"
	 */
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @hibernate.property column="report_id"
	 */
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="rep_format_pkid_seq"
	 */
	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	//增加映射，也用hibernate处理，wsx 10-12
	/**
	 * @hibernate.property type="org.springframework.orm.hibernate3.support.ClobStringType" column="report_format"
	 */
	public String getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	/**
	 * @hibernate.property column="frequency"
	 */
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String toString() {
		String reportInfoStr = "";
		reportInfoStr = reportInfoStr.concat(String.valueOf(this.pkId) + ",");
		reportInfoStr = reportInfoStr.concat(String.valueOf(this.reportId) + ",");
		reportInfoStr = reportInfoStr.concat(this.beginDate.replaceAll("-","") + ",");
		reportInfoStr = reportInfoStr.concat(this.endDate.replaceAll("-","") + ",");
		reportInfoStr = reportInfoStr.concat(this.createDate + ",");
		reportInfoStr = reportInfoStr.concat(this.editDate + ",");
		reportInfoStr = reportInfoStr.concat(this.frequency);
		return reportInfoStr;
	}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}	

	public static void copyProperties(ReportFormat t,ReportFormat another){
		t.setPkId(another.getPkId());
		t.setReportId(another.getReportId());
		t.setReportFormat(another.getReportFormat());
		t.setBeginDate(another.getBeginDate());
		t.setEndDate(another.getEndDate());
		t.setCreateDate(another.getCreateDate());
		t.setEditDate(another.getEditDate());
		t.setFrequency(another.getFrequency());
		t.setReportName(another.getReportName());
	}

	public Integer getCreateId()
	{
		return createId;
	}

	public void setCreateId(Integer createId)
	{
		this.createId = createId;
	}
	
}
