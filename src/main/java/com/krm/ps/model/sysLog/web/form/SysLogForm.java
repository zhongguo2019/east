package com.krm.ps.model.sysLog.web.form;

import com.krm.ps.framework.common.web.form.BaseForm;
/**
 * 日志浏览Form
 * @struts.form name="SysLogForm"
 */
public class SysLogForm extends BaseForm implements java.io.Serializable
{
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 4725777717274446529L;
	
	private String dataDate;//时间
	private String recordDate;//日志时间
	private String userName;//用户名
	private String reportId;//报表id
	private String startDate;//开始时间	
	private String endDate;//结束时间
	private String userId;//用户id
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	/**
	 * 
	 * @return 数据日期
	 */
	public String getDataDate() {
		return dataDate;
	}	
	/**
	 * 
	 * @param dataDate 数据日期
	 */
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}	
	
}
