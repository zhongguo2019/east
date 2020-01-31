package com.krm.slsint.workfile.vo;

import java.io.Serializable;

import com.krm.ps.framework.vo.BaseObject;

/**
 * 
 * @hibernate.class table="info_acceptoper"
 */

public class AcceptoperData extends BaseObject implements Serializable{
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = -3534536054374870554L;	
	private Long pkId;	
	private Long mailId;	
	private Long aoperId;	
	private String adate;	
	private String status;	
	private Integer reply;
	private Integer replyDate;
	private Long rmailId;
	/**
	 * @hibernate.property column="rmail_id"
	 */
	public Long getRmailId() {
		return rmailId;
	}
	public void setRmailId(Long rmailId) {
		this.rmailId = rmailId;
	}
	/**
	 * @hibernate.property column="reply"
	 */
	public Integer getReply() {
		return reply;
	}
	public void setReply(Integer reply) {
		this.reply = reply;
	}
	public AcceptoperData(){
		super();
	}	
	/**
	 * @hibernate.id column="pkid" type="java.lang.Long"
	 *               generator-class="native"	
	 * @hibernate.generator-param name="sequence" value="info_acceptoper_pkid_seq"
	 */	
	public Long getPkId() {
		return pkId;
	}
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}
	/**
	 * @hibernate.property column="mail_id"
	 */
	public Long getMailId() {
		return mailId;
	}
	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}		
	/**
	 * @hibernate.property column="a_operid"
	 */
	public Long getAoperId() {
		return aoperId;
	}
	public void setAoperId(Long operId) {
		aoperId = operId;
	}	
	/**
	 * @hibernate.property column="a_date"
	 */
	public String getAdate() {
		return adate;
	}	
	public void setAdate(String date) {
		adate = date;
	}
	/**
	 * ʹ��״̬��0�����ʼ���1���Ѷc�9��ɾ��7������ɾ��8���ݸ��䣻9������վ
	 * 
	 * @hibernate.property column="status"
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * ʹ��״̬��0�����ʼ���1���Ѷc�9��ɾ��7������ɾ��8���ݸ��䣻9������վ
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @hibernate.property column="replydate"
	 */
	public Integer getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(Integer replyDate) {
		this.replyDate = replyDate;
	}
}
