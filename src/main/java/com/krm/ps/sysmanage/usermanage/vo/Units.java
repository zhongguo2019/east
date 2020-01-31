package com.krm.ps.sysmanage.usermanage.vo;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.krm.ps.framework.vo.BaseObject;
/**
 * 
 * @hibernate.class table="code_unit_units"
 */

public class Units extends BaseObject implements Serializable{
	
	private static final long serialVersionUID = 77L;
	
	private Long pkid;
	
	private String code;
	
	private String name;
	
	private Long modulus;
	
	private Integer display_order;
    
	public Units(){
		
	}
    /**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_unit_seq"
	 */
    public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	
	
	/**
	 * 
	 * @hibernate.property column="code"
	 */	
    public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 
	 * @hibernate.property column="display_order"
	 */	
	public Integer getDisplay_order() {
		return display_order;
	}
	public void setDisplay_order(Integer display_order) {
		this.display_order = display_order;
	}
	
	/**
	 * 
	 * @hibernate.property column="modulus"
	 */	
	public Long getModulus() {
		return modulus;
	}
	public void setModulus(Long modulus) {
		this.modulus = modulus;
	}
	
	/**
	 * 
	 * @hibernate.property column="name"
	 */	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
    	return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("pkid", pkid).toString();
    }
	    public boolean equals(Object o) {
	    	if (this == o)
			{
				return true;
			}
			if (!(o instanceof Units))
			{
				return false;
			}

			final Units dictionaryObject = (Units) o;

			if (pkid == null ? !pkid.equals(dictionaryObject.getPkid())
				: dictionaryObject.getPkid() != null)
			{
				return false;
			}
			return true;
	}
	public int hashCode() {
		return (pkid != null ? pkid.hashCode() : 0);
	}

}
