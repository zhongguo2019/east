package com.krm.ps.sysmanage.organmanage.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
/**
 * 机构管理常量Form
 * @struts.form name="contrastForm"
 */
public class ContrastForm extends ActionForm {
	
	private static final long serialVersionUID = 14244551L;
	
	//所有下级机构对照关系
	private String organContrasts;
	
	private String areaId;
private FormFile importXlsFile;
	
	
	public FormFile getImportXlsFile() {
		return importXlsFile;
	}

	public void setImportXlsFile(FormFile importXlsFile) {
		this.importXlsFile = importXlsFile;
	}
	
	/**
	 * 
	 * @return 所有下级机构对照关系
	 */
	public String getOrganContrasts() {
		return organContrasts;
	}
	
	/**
	 * 
	 * @param organContrasts 所有下级机构对照关系
	 */
	public void setOrganContrasts(String organContrasts) {
		this.organContrasts = organContrasts;
	}	
	/**
	 * 
	 * @return 区域编码
	 */
	public String getAreaId() {
		return areaId;
	}	
	/**
	 * 
	 * @param areaId 区域编码
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	

}
