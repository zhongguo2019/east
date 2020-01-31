package com.krm.ps.tarsk.vo;

import java.io.Serializable;

import com.krm.ps.util.ConvertUtil;

public class Tarsk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2467115297222654062L;
private Long tId;
private String createTime;
private String startTime;
private String endTime;
private String status;
private String dirPath;
public String getDirPath() {
	return dirPath;
}
public void setDirPath(String dirPath) {
	this.dirPath = dirPath;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
private String type;
private String message;
private String reportId;
private String organId;
private String dataDate;
public Long gettId() {
	return tId;
}
public void settId(Long tId) {
	this.tId = tId;
}
public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
public String getStartTime() {
	return startTime;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}
public String getEndTime() {
	return endTime;
}
public void setEndTime(String endTime) {
	this.endTime = endTime;
}

public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}

public String getReportId() {
	return reportId;
}
public void setReportId(String reportId) {
	this.reportId = reportId;
}
public String getOrganId() {
	return organId;
}
public void setOrganId(String organId) {
	this.organId = organId;
}
public String getDataDate() {
	return dataDate;
}
public void setDataDate(String dataDate) {
	this.dataDate = dataDate;
}
public Tarsk(){}
public Tarsk(Tarsk t){
	try {
		ConvertUtil.copyProperties(this, t);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
