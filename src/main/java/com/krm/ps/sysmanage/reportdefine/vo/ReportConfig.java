package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;

/**
 * 
 * @hibernate.class table="code_rep_config"
 */
public class ReportConfig implements Serializable{
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = -613167866533066483L;

	private Long pkid;
	
	private Long reportId;
	
	private Long funId;
	
	private Long idxId;
	
	private Long defInt;
	
	private String defChar;
	
	private String description;
	
	
	public ReportConfig(){
		
	}

	/**
	 * 
	 * @hibernate.property column="def_char"
	 */
	public String getDefChar() {
		return defChar;
	}

	
	public void setDefChar(String defChar) {
		this.defChar = defChar;
	}

	/**
	 * 
	 * @hibernate.property column="def_int"
	 */
	public Long getDefInt() {
		return defInt;
	}


	public void setDefInt(Long defInt) {
		this.defInt = defInt;
	}

	/**
	 * 
	 * @hibernate.property column="description"
	 */
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @hibernate.property column="fun_id"
	 */
	public Long getFunId() {
		return funId;
	}


	public void setFunId(Long funId) {
		this.funId = funId;
	}

	/**
	 * 
	 * @hibernate.property column="idx_id"
	 */
	public Long getIdxId() {
		return idxId;
	}


	public void setIdxId(Long idxId) {
		this.idxId = idxId;
	}

	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_rep_config_seq"
	 */
	public Long getPkid() {
		return pkid;
	}


	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	/**
	 * 
	 * @hibernate.property column="report_id"
	 */
	public Long getReportId() {
		return reportId;
	}


	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	public String toString(){
		//PKID ,  REPORT_ID,  FUN_ID,  IDX_ID,  DEF_INT,  DEF_CHAR,  DESCRIPTION
		String str = "";
		str = str.concat(this.getReportId().toString());
		str = str.concat(",");
		str = str.concat(this.getFunId().toString());
		str = str.concat(",");
		str = str.concat(this.getIdxId().toString());
		str = str.concat(",");
		if(this.getDefInt() == null || "".equals(this.getDefInt())){
			str = str.concat("null");
		}else{
			str = str.concat(this.getDefInt().toString());
		}
		str = str.concat(",");
		if(this.getDefChar() == null || "".equals(this.getDefChar())){
			str = str.concat("null");
		}else{
			str = str.concat(this.getDefChar().replaceAll(",","£¬"));
		}
		str = str.concat(",");
		if(this.getDescription() == null || "".equals(this.getDescription())){
			str = str.concat("null");
		}else{
			str = str.concat(this.getDescription().replaceAll(",","£¬"));
		}
		
		return str;
		
	}
}
