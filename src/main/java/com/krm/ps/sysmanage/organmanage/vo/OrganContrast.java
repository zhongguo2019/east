package com.krm.ps.sysmanage.organmanage.vo;

import java.io.Serializable;

import com.krm.ps.util.ConvertUtil;
import com.krm.ps.framework.vo.BaseObject;


/**
 * @hibernate.class table="code_org_contrast"
 */
public class OrganContrast extends BaseObject implements Serializable{

	private static final long serialVersionUID = 234274L;

    //唯一标识
	private Long pkid;
	
	//本系统机构id 对应code_org_organ表的pkid
	private String native_org_id;
	
	//本系统机构名称
	private String organ_name;
	
    //外部系统标识
	private Integer system_code;
	  
	//外部系统机构编码
	private String outer_org_code;
	
	
	public OrganContrast(){
	}
	
	public OrganContrast(OrganContrast contrast,String organname){
        
		try {
			
			ConvertUtil.copyProperties(this, contrast, true);
			this.organ_name=organname;
			
		} catch (Exception e) {
			;
		}
	}

	/**	
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_org_contrast_seq"
	 */
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	/**
	 * @hibernate.property column="native_org_id" 
	 */
	public String getNative_org_id() {
		return native_org_id;
	}


	public void setNative_org_id(String native_org_id) {
		this.native_org_id = native_org_id;
	}

	/**
	 * @hibernate.property column="system_code" 
	 */
	public Integer getSystem_code() {
		return system_code;
	}


	public void setSystem_code(Integer system_code) {
		this.system_code = system_code;
	}

	/**
	 * @hibernate.property column="outer_org_code" 
	 */
	public String getOuter_org_code() {
		return outer_org_code;
	}


	public void setOuter_org_code(String outer_org_code) {
		this.outer_org_code = outer_org_code;
	}
	
	
	public String getOrgan_name() {
		return organ_name;
	}

	public void setOrgan_name(String organ_name) {
		this.organ_name = organ_name;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("\n机构编号:" + pkid + "对应机构id:" + outer_org_code 
				+ "\n");
		return s.toString();
		}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}

}
