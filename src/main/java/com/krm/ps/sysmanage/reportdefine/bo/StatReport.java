package com.krm.ps.sysmanage.reportdefine.bo;

import java.util.List;

public class StatReport {
	
	private String pkid;//报表Id
	private String code;//表单代码
	private String conCode;
	private Long reportType;//报表类型
	private String name;//报表名称
	private String frequency;//报表频度
	private String batchId;//报表批次
	private String batchName;//批次名称
	private List expData;//本期导出列
	private List carryData;//结转导出列
	private List moneySort;//币种
	private String repUnit;//数据单位
	private String big;//数据单位位数
	private List dataAttribute;//数据属性
	
	public String getConCode() {
		return conCode;
	}

	public void setConCode(String conCode) {
		this.conCode = conCode;
	}	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
		
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPkid() {
		return pkid;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}
	public Long getReportType() {
		return reportType;
	}

	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public List getCarryData() {
		return carryData;
	}

	public void setCarryData(List carryData) {
		this.carryData = carryData;
	}

	public List getExpData() {
		return expData;
	}

	public void setExpData(List expData) {
		this.expData = expData;
	}

	public List getMoneySort() {
		return moneySort;
	}

	public void setMoneySort(List moneySort) {
		this.moneySort = moneySort;
	}

	public String getRepUnit() {
		return repUnit;
	}

	public void setRepUnit(String repUnit) {
		this.repUnit = repUnit;
	}

	public String getBig() {
		return big;
	}

	public void setBig(String big) {
		this.big = big;
	}

	public List getDataAttribute() {
		return dataAttribute;
	}

	public void setDataAttribute(List dataAttribute) {
		this.dataAttribute = dataAttribute;
	}

	
}
