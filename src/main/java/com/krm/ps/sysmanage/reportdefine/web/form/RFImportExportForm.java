package com.krm.ps.sysmanage.reportdefine.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * 报表模板输入输出Form
 * @struts.form name="RFImportExportForm"
 */
public class RFImportExportForm extends ActionForm {

	private static final long serialVersionUID = 232323L;

	private Long pkId;

	private FormFile repFormatFile;

	/**
	 * 
	 * @return 主键编码
	 */
	public Long getPkId() {
		return pkId;
	}

	/**
	 * 
	 * @param pkId 主键编码
	 */
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	/**
	 * 
	 * @return 报表模板文件
	 */
	public FormFile getRepFormatFile() {
		return repFormatFile;
	}

	/**
	 * 
	 * @param repFormatFile 报表模板文件
	 */
	public void setRepFormatFile(FormFile repFormatFile) {
		this.repFormatFile = repFormatFile;
	}

}
