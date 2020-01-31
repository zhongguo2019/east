package com.krm.ps.framework.property.web.form;

import org.apache.struts.action.ActionForm;

/**
 *  Ù–‘Form
 * @struts.form name="propertyForm"
 */
public class PropertyForm extends ActionForm{

	private String proName;
	private String proValue;
	private String proComment; 
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProValue() {
		return proValue;
	}
	public void setProValue(String proValue) {
		this.proValue = proValue;
	}
	public String getProComment() {
		return proComment;
	}
	public void setProComment(String proComment) {
		this.proComment = proComment;
	}

	
}
