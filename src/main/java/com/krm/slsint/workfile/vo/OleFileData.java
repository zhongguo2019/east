package com.krm.slsint.workfile.vo;

import java.io.Serializable;

import com.krm.ps.framework.vo.BaseObject;
/**
 * 
 * @hibernate.class table="info_olefile"
 */
public class OleFileData extends BaseObject implements Serializable{
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 8789230850096705078L;	
	private Long pkId;	
	private String kindId;	
	private Long conId;	
	private Long showOrder;	
	private String sFileName;	
	private String dFileName;
	private byte[] fileBlob;
	
	public OleFileData(){
		super();
	}	
	/**
	 * @hibernate.id column="pkid" type="java.lang.Long"
	 *               generator-class="native"	
	 * @hibernate.generator-param name="sequence" value="info_olefile_pkid_seq"
	 */	
	public Long getPkId() {
		return pkId;
	}
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}	
	/**
	 * @hibernate.property column="con_id"
	 */
	public Long getConId() {
		return conId;
	}
	public void setConId(Long conId) {
		this.conId = conId;
	}
	/**
	 * @hibernate.property column="d_filename"
	 */
	public String getDFileName() {
		return dFileName;
	}
	public void setDFileName(String fileName) {
		dFileName = fileName;
	}	
	/**
	 * @hibernate.property column="kind_id"
	 */
	public String getKindId() {
		return kindId;
	}
	public void setKindId(String kindId) {
		this.kindId = kindId;
	}
	/**
	 * @hibernate.property column="s_filename"
	 */
	public String getSFileName() {
		return sFileName;
	}
	public void setSFileName(String fileName) {
		sFileName = fileName;
	}
	/**
	 * @hibernate.property column="show_order"
	 */
	public Long getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(Long showOrder) {
		this.showOrder = showOrder;
	}	
	public String toString() {
		return null;
	}
	public boolean equals(Object o) {
		return false;
	}
	public int hashCode() {
		return 0;
	}
	/**
	 * @hibernate.property type="org.springframework.orm.hibernate3.support.BlobByteArrayType" column="filebytea" lazy="true"
	 */
	public byte[] getFileBlob()
	{
		return fileBlob;
	}
	public void setFileBlob(byte[] fileBlob)
	{
		this.fileBlob = fileBlob;
	}
}
