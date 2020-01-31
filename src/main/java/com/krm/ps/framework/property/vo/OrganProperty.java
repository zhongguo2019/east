package com.krm.ps.framework.property.vo;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 * @hibernate.class table="code_organ_property"
 */
public class OrganProperty implements Serializable {

	private static final long serialVersionUID = -5482262201061823450L;
	
	private Long opId;
	
	private String organCode;
	
	private Long proId;
	
	private String value;
	
	/**
	 * @hibernate.id column="op_id" type="java.lang.Long" generator-class="native"
	 */
	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	/**
	 * @hibernate.property column="organ_code" type="java.lang.String" 
	 */
	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	/**
	 * @hibernate.property column="pro_id" type="java.lang.Long"
	 */
	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

	/**
	 * @hibernate.property column="value" type="java.lang.String" 
	 */
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
