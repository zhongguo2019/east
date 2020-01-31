package com.krm.slsint.workfile.vo;

import java.io.Serializable;

import com.krm.ps.framework.vo.BaseObject;
/**
 * 
 * @hibernate.class table="info_workdirect"
 */
public class WorkDirectData extends BaseObject implements Serializable{
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = -3330880857140617421L;	
	private Long pkId;	
	private Long kindId;	
	private String issDate;	
	private Long issOper;	
	private String operName;	
	private Long departId;	
	private String departName;	
	private String organId;	
	private String organName;	
	private String title;	
	private Long showOrder;	
	private String content;	
	private Long fileSourceId;	
	private String fileSourceName;	
	private String status;	
	private Long delOper;	
	private String delOperName;	
	private String delDate;
	public WorkDirectData(){
		super();
	}	
	/**
	 * @hibernate.id column="pkid" type="java.lang.Long"
	 *               generator-class="native"	
	 * @hibernate.generator-param name="sequence" value="info_workdirect_pkid_seq"
	 */
	public Long getPkId() {
		return pkId;
	}
	public void setPkId(Long pkId) {
		this.pkId = pkId;
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
	 * @hibernate.property column="del_date"
	 */
	public String getDelDate() {
		return delDate;
	}
	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	/**
	 * @hibernate.property column="del_oper"
	 */
	public Long getDelOper() {
		return delOper;
	}
	public void setDelOper(Long delOper) {
		this.delOper = delOper;
	}
	/**
	 * @hibernate.property column="del_opername"
	 */
	public String getDelOperName() {
		return delOperName;
	}
	public void setDelOperName(String delOperName) {
		this.delOperName = delOperName;
	}
	/**
	 * @hibernate.property column="depart_id"
	 */
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	/**
	 * @hibernate.property column="depart_name"
	 */
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	/**
	 * @hibernate.property column="file_source_id"
	 */
	public Long getFileSourceId() {
		return fileSourceId;
	}
	public void setFileSourceId(Long fileSourceId) {
		this.fileSourceId = fileSourceId;
	}
	/**
	 * @hibernate.property column="file_source_name"
	 */
	public String getFileSourceName() {
		return fileSourceName;
	}
	public void setFileSourceName(String fileSourceName) {
		this.fileSourceName = fileSourceName;
	}
	/**
	 * @hibernate.property column="iss_date"
	 */
	public String getIssDate() {
		return issDate;
	}
	public void setIssDate(String issDate) {
		this.issDate = issDate;
	}
	/**
	 * @hibernate.property column="iss_oper"
	 */
	public Long getIssOper() {
		return issOper;
	}
	public void setIssOper(Long issOper) {
		this.issOper = issOper;
	}
	/**
	 * @hibernate.property column="kind_id"
	 */
	public Long getKindId() {
		return kindId;
	}
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}
	/**
	 * @hibernate.property column="oper_name"
	 */
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	/**
	 * @hibernate.property column="organ_id"
	 */
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	/**
	 * @hibernate.property column="organ_name"
	 */
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	/**
	 * @hibernate.property column="show_order"
	 */
	public Long getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(Long showOrder) {
		this.showOrder = showOrder;
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
	/**
	 * @hibernate.property column="title"
	 */
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
}
