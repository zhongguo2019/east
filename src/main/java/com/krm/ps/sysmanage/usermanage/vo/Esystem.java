package com.krm.ps.sysmanage.usermanage.vo;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.krm.ps.framework.vo.BaseObject;
/**
 * 
 * @hibernate.class table="code_system"
 */

public class Esystem extends BaseObject implements Serializable
{
	private static final long serialVersionUID = 14534534L;
	private Long pkid;	
	private String name;
	private String show_order;
    public Esystem()
    {
		
	}
    /**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_dictionary_seq"
	 */
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
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
	/**
	 * 
	 * @hibernate.property column="show_order"
	 */
	public String getShow_order() {
		return show_order;
	}
	public void setShow_order(String show_order) {
		this.show_order = show_order;
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
			if (!(o instanceof Esystem))
			{
				return false;
			}

			final Esystem systemObject = (Esystem) o;

			if (pkid == null ? !pkid.equals(systemObject.getPkid())
				: systemObject.getPkid() != null)
			{
				return false;
			}
			return true;
	}
	public int hashCode() {
		return (pkid != null ? pkid.hashCode() : 0);
	}
	
	

}
