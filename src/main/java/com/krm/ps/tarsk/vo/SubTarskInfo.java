package com.krm.ps.tarsk.vo;

import java.io.Serializable;

public class SubTarskInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2833120153935248745L;
	private Long pkid;
	/**
	 * 主任务id
	 */
	private Long tId;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 运行状态
	 */
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 预留字段
	 */
	private String type;
	/**
	 * 执行消息
	 */
	private String message;
	/**
	 * 执行次数
	 */
	private int tCount;
	/**
	 * 业务字段报表编码
	 */
	private String reportId;
	/**
	 * 业务字段机构编码
	 */
	private String organId;
	/**
	 * 业务字段报送数据日期
	 */
	private String dataDate;
	
	/**
	 * 生成临时文件路径
	 */
	private String filePath;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Override
	public String toString() {
		return "SubTarskInfo [pkid=" + pkid + ", tId=" + tId + ", createTime="
				+ createTime + ", startTime=" + startTime + ", endTime="
				+ endTime + ", stauts=" + status + ", type=" + type
				+ ", message=" + message + ", tCount=" + tCount + ", reportId="
				+ reportId + ", organId=" + organId + ", dataDate=" + dataDate
				+ "]";
	}
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
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
	public int gettCount() {
		return tCount;
	}
	public void settCount(int tCount) {
		this.tCount = tCount;
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
	
	
}
