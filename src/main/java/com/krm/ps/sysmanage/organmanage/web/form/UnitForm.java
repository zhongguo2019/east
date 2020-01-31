package com.krm.ps.sysmanage.organmanage.web.form;

import com.krm.ps.framework.common.web.form.BaseForm;

/**
 * 机构管理单位Form
 * @struts.form name="unitForm"
 */

public class UnitForm extends BaseForm{
		
	private static final long serialVersionUID = 1L;
	
	private Long pkid;
	private String code;
	private String name;
	private Long modulus;
	
	/**
	 * 
	 * @return 编码
	 */
	public String getCode() {
		return code;
	}	
	/**
	 *  
	 * @param code 编码
	 */
	public void setCode(String code) {
		this.code = code;
	}	
	/**
	 * 
	 * @return 系数
	 */
	public Long getModulus() {
		return modulus;
	}	
	/**
	 * 
	 * @param modulus 系数
	 */
	public void setModulus(Long modulus) {
		this.modulus = modulus;
	}	
	/**
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}	
	/**
	 * 
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}	
	/**
	 * 
	 * @return 主键编码
	 */
	public Long getPkid() {
		return pkid;
	}	
	/**
	 * 
	 * @param pkid 主键编码
	 */
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

}
