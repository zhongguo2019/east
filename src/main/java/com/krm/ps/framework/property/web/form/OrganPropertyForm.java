package com.krm.ps.framework.property.web.form;

import org.apache.struts.action.ActionForm;

/**
 * »ú¹¹ÊôÐÔForm
 * @struts.form name="organPropertyForm"
 */
public class OrganPropertyForm extends ActionForm{

	private String organCode;
	
	private String proId;
	
	private String value;

	private String organName;
	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}
	
}
