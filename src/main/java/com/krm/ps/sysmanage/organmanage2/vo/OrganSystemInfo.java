package com.krm.ps.sysmanage.organmanage2.vo;

import java.io.Serializable;
import com.krm.ps.framework.vo.BaseObject;

/**
 * 机构体系信息，包含创建此树的用户id，地区等
 * 
 * @hibernate.class table="code_user_org_idx"
 */
public class OrganSystemInfo extends BaseObject implements Serializable {

	private static final long serialVersionUID = 2360918644434961L;

	private Integer pkid;

	private String name;

	private int userId;

	private int showOrder;

	private String areaId;

	private int groupId;

	private int isPublic;

	private String beginDate;

	private String endDate;

	private String status;

	private String description;

	/**	
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_user_org_idx_seq"
	 */
	public Integer getPkid() {
		return pkid;
	}

	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}

	/**
	 * @hibernate.property column="name"
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property column="userid"
	 */
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @hibernate.property column="areaid"
	 */
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	/**
	 * @hibernate.property column="groupid"
	 */
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @hibernate.property column="ispublic"
	 */
	public int getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
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
	 * @hibernate.property column="end_date"
	 */
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	 * @hibernate.property column="show_order"
	 */
	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	/**
	 * @hibernate.property column="description"
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		StringBuffer s=new StringBuffer();
		s.append("organSystemInfo ").append(pkid).append(" ").append(name).append(" ").append(isPublic).append("\n");		
		return s.toString();		
	}

	public boolean equals(Object o) {
		//TODO
		return false;
	}

	public int hashCode() {
		return (pkid==null)? 0:pkid.intValue();
	}

}
