package com.krm.ps.sysmanage.organmanage2.vo;

import java.io.Serializable;
import com.krm.ps.framework.vo.BaseObject;

/**
 * @hibernate.class table="code_areas"
 */
public class Area extends BaseObject implements Serializable {

	private static final long serialVersionUID = -437332796937403385L;

	private int pkid;

	private String code;

	private int superId;

	private String superCode;

	private String fullName;

	private String shortName;

	private int isDefult;

	private int status;

	private int showOrder;

	private String beginDate;

	private String endDate;

	private String description;
	
	private Integer areaAdminId;

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
	 * @hibernate.property column="code"
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		superCode = code.replaceAll("(00)+$", "");
		int len = superCode.length();
		if (len == 0) {
			return;
		}
		superCode = superCode.substring(0, len - 2);
		while (superCode.length() < code.length()) {
			superCode += "00";
		}
	}

	/**
	 * @hibernate.property column="description"
	 */
	public String getdescription() {
		return description;
	}

	public void setdescription(String description) {
		this.description = description;
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
	 * @hibernate.property column="full_name"
	 */
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @hibernate.property column="isdefult"
	 */
	public int getIsDefult() {
		return isDefult;
	}

	public void setIsDefult(int isDefult) {
		this.isDefult = isDefult;
	}

	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_areas_seq"
	 */
	public int getPkid() {
		return pkid;
	}

	public void setPkid(int pkid) {
		this.pkid = pkid;
	}

	/**
	 * @hibernate.property column="short_name"
	 */
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @hibernate.property column="status"
	 */
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @hibernate.property column="super_id"
	 */
	public int getSuperId() {
		return superId;
	}

	public void setSuperId(int superId) {
		this.superId = superId;
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

	public String toString() {
		StringBuffer s = new StringBuffer();
		// TODO
		return s.toString();
	}

	public boolean equals(Object o) {
		return false;// TODO
	}

	public int hashCode() {
		return pkid;
	}

	public String getSuperCode() {
		return superCode;
	}

	public Integer getAreaAdminId()
	{
		return areaAdminId;
	}

	public void setAreaAdminId(Integer areaAdminId)
	{
		this.areaAdminId = areaAdminId;
	}
}
