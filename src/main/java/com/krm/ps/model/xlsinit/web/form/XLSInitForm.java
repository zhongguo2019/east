package com.krm.ps.model.xlsinit.web.form;

import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.form.BaseForm;


/**
 * @struts.form name="xlsInitForm"
 */
public class XLSInitForm extends BaseForm implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5219588548933260729L;
	private FormFile dataFile;
	private FormFile xmlFile;
	public FormFile getDataFile() {
		return dataFile;
	}
	public void setDataFile(FormFile dataFile) {
		this.dataFile = dataFile;
	}
	public FormFile getXmlFile() {
		return xmlFile;
	}
	public void setXmlFile(FormFile xmlFile) {
		this.xmlFile = xmlFile;
	}	
}
