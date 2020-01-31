package com.krm.ps.model.sysLog.vo;

import java.io.Serializable;

import com.krm.ps.framework.vo.BaseObject;


/**
 * @hibernate.class table="log_data"
 */
public class LogData extends BaseObject implements Serializable
{
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 8835607108588940623L;

	private String organName;//
	private String reportName;//
	private String reportTypeName;//
	private String userName;//
	private String dataDate;//
	private String logType;//
	private String memo;//
	private String logonName;

	
	


	


	public LogData(){
	}

	
	/**
	 * @hibernate.property column="date_data"
	 */
	public String getDataDate() {
		return dataDate;
	}
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	/**
	 * @hibernate.property column="mk_type"
	 */
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	/**
	 * @hibernate.property column="ass_memo"
	 */
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @hibernate.property column="nm_organ"
	 */
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	/**
	 * @hibernate.property column="nm_rep"
	 */
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	/**
	 * @hibernate.property column="nm_rep_type"
	 */
	public String getReportTypeName() {
		return reportTypeName;
	}
	public void setReportTypeName(String reportTypeName) {
		this.reportTypeName = reportTypeName;
	}
	/**
	 * @hibernate.property column="nm_oper"
	 */
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int hashCode() {
		return 0;
	}


	public String getLogonName() {
		return logonName;
	}


	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

}
