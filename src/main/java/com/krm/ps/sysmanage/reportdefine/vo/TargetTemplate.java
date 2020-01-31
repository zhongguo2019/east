package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;

/**
 * gaozhongbo
 * @hibernate.class table="collect_target"
 */
public class TargetTemplate implements Serializable{
	private Long pkid;//唯一标识
	private Long reportId;//模型或者模板ID(code_rep_report.id)
	private String targetName;//列名称
	private String targetField;//物理表字段名称
	private Integer targetOrder;//序号
	private String beginDate;//开始日期
	private String endDate;//失效日期
	private String createDate;//创建日期
	private String editDate;//修改日期
	private Integer status;//状态
	private String phytable;//物理表名称
	private Long phytableid;//物理表id
	private Long dicPid;
	
	public TargetTemplate(Long pkid, Long reportId, String targetName,
			String targetField, Integer targetOrder, String beginDate,
			String endDate, String createDate, String editDate, Integer status,
			String phytable, Long phytableid, Long dicPid) {
		super();
		this.pkid = pkid;
		this.reportId = reportId;
		this.targetName = targetName;
		this.targetField = targetField;
		this.targetOrder = targetOrder;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.createDate = createDate;
		this.editDate = editDate;
		this.status = status;
		this.phytable = phytable;
		this.phytableid = phytableid;
		this.dicPid = dicPid;
	}
	public TargetTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getTargetField() {
		return targetField;
	}
	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}
	public Integer getTargetOrder() {
		return targetOrder;
	}
	public void setTargetOrder(Integer targetOrder) {
		this.targetOrder = targetOrder;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getDicPid() {
		return dicPid;
	}
	public void setDicPid(Long dicPid) {
		this.dicPid = dicPid;
	}
	public String getPhytable() {
		return phytable;
	}
	public void setPhytable(String phytable) {
		this.phytable = phytable;
	}
	public Long getPhytableid() {
		return phytableid;
	}
	public void setPhytableid(Long phytableid) {
		this.phytableid = phytableid;
	}
	
	
}
