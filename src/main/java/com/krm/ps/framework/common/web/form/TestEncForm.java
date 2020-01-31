package com.krm.ps.framework.common.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * 
 * @struts.form name="testEncForm"
 */
public class TestEncForm extends ActionForm {

	private static final long serialVersionUID = 25553L;

	private String dec;

	private FormFile content;

	public String getDec() {
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	public FormFile getContent() {
		return content;
	}

	public void setContent(FormFile content) {
		this.content = content;
	}

}
