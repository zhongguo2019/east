package com.krm.slsint.workfile.vo;

import java.io.Serializable;

import com.krm.ps.framework.vo.BaseObject;
/**
 * 
 * @hibernate.class table="info_filetransfer"
 */

public class FileTransferData extends BaseObject implements Serializable{
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 459009362344698246L;	
	private Long pkId;	
	private Long poper;	
	private String dateDate;	
	private String title;	
	private String content;	
	private String status;	
	private String userName;		
	private String businessDate;
	private Long businessReportId;
	private String businessOrganCode;
	private Long businessUserId;
	private Integer businessStatus;
	private String businessOther;
	private String itemtype;
	private String isAttachment;	
	private Integer reply;
	private Long rmailId;
	private String mailBox;
	private String unlock;

	public Integer getReply() {
		return reply;
	}

	public void setReply(Integer reply) {
		this.reply = reply;
	}
	public String getIsAttachment() {
		return isAttachment;
	}
	public void setIsAttachment(String isAttachment) {
		this.isAttachment = isAttachment;
	}
	/**
	 * @hibernate.property column="BUSINESS_OTHER"
	 */
	public String getBusinessOther() {
		return businessOther;
	}
	public void setBusinessOther(String businessOther) {
		this.businessOther = businessOther;
	}
	/**
	 * @hibernate.property column="unlock"
	 */
	public String getUnlock() {
		return unlock;
	}
	public void setUnlock(String unlock) {
		this.unlock = unlock;
	}
	/**
	 * @hibernate.id column="pkid" type="java.lang.Long"
	 *               generator-class="native"	
	 * @hibernate.generator-param name="sequence" value="info_filetransfer_pkid_seq"
	 */	
	public Long getPkId() {
		return pkId;
	}
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}		
	/**
	 * @hibernate.property column="p_oper"
	 */
	public Long getPoper() {
		return poper;
	}
	public void setPoper(Long oper) {
		poper = oper;
	}	
	/**
	 * @hibernate.property column="p_date"
	 */
	public String getDateDate() {
		return dateDate;
	}
	public void setDateDate(String date) {
		dateDate = date;
	}	
	/**
	 * @hibernate.property column="title"
	 */
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
	/**
	 * @hibernate.property type="org.springframework.orm.hibernate3.support.ClobStringType" column="content"
	 */
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	/**
	 * @hibernate.property column="status"
	 */
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String toString() {
		return null;
	}
	public boolean equals(Object o) {
		return false;
	}
	public int hashCode() {
		return 0;
	}
	
	
	
	
	
	
	/**
	 * @hibernate.property column="BUSINESS_DATE"
	 */
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	/**
	 * @hibernate.property column="BUSINESS_ORGANCODE"
	 */
	public String getBusinessOrganCode() {
		return businessOrganCode;
	}
	public void setBusinessOrganCode(String businessOrganCode) {
		this.businessOrganCode = businessOrganCode;
	}
	/**
	 * @hibernate.property column="BUSINESS_REPORTID"
	 */
	public Long getBusinessReportId() {
		return businessReportId;
	}
	public void setBusinessReportId(Long businessReportId) {
		this.businessReportId = businessReportId;
	}
	/**
	 * @hibernate.property column="BUSINESS_STATUS"
	 */
	public Integer getBusinessStatus() {
		return businessStatus;
	}
	public void setBusinessStatus(Integer businessStatus) {
		this.businessStatus = businessStatus;
	}
	/**
	 * @hibernate.property column="BUSINESS_USERID"
	 */
	public Long getBusinessUserId() {
		return businessUserId;
	}
	public void setBusinessUserId(Long businessUserId) {
		this.businessUserId = businessUserId;
	}
	/**
	 * @hibernate.property column="itemtype"
	 */
	public String getItemtype() {
		return itemtype;
	}
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}
	public Long getRmailId() {
		return rmailId;
	}
	public void setRmailId(Long rmailId) {
		this.rmailId = rmailId;
	}
	/**
	 * @hibernate.property column="MAILBOX"
	 */
	public String getMailBox() {
		return mailBox;
	}
	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}
}
