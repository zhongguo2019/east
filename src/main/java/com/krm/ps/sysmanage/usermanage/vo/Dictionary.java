package com.krm.ps.sysmanage.usermanage.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.krm.ps.framework.vo.BaseObject;

/**
 * 
 * @hibernate.class table="code_dictionary"
 */

public class Dictionary extends BaseObject implements Serializable {
	private static final long serialVersionUID = 14534534L;

	private Long pkid;

	private Long dicid;

	private String dicname;

	private Long parentid;

	private Long layer;

	private String isleaf;

	private Long disporder;

	private String description;

	private String status;

	private String readonly;

	public Dictionary() {

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
	 * @hibernate.property column="dicname"
	 */
	public String getDicname() {
		return dicname;
	}

	public void setDicname(String dicname) {
		this.dicname = dicname;
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
	 * @hibernate.property column="dicid"
	 */
	public Long getDicid() {
		return dicid;
	}

	public void setDicid(Long dicid) {
		this.dicid = dicid;
	}

	/**
	 * 
	 * @hibernate.property column="disporder"
	 */
	public Long getDisporder() {
		return disporder;
	}

	public void setDisporder(Long disporder) {
		this.disporder = disporder;
	}

	/**
	 * 
	 * @hibernate.property column="isleaf"
	 */
	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	/**
	 * 
	 * @hibernate.property column="layer"
	 */
	public Long getLayer() {
		return layer;
	}

	public void setLayer(Long layer) {
		this.layer = layer;
	}

	/**
	 * 
	 * @hibernate.property column="parentid"
	 */
	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	/**
	 * 
	 * @hibernate.property column="status"
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @hibernate.property column="readonly"
	 */
	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("pkid", pkid).toString();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Dictionary)) {
			return false;
		}

		final Dictionary dictionaryObject = (Dictionary) o;

		if (pkid == null ? !pkid.equals(dictionaryObject.getPkid())
				: dictionaryObject.getPkid() != null) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		return (pkid != null ? pkid.hashCode() : 0);
	}

}
