package com.krm.ps.sysmanage.reportdefine.vo;
/**
 * 
 * @hibernate.class table="code_table_field"
 */
public class TableField{
	
	private Long pkid;
	private String tableName;
	private Long showorder;
	private String fieldNameStr;
	private String fieldName;
	private String memo;
	
	/**
	 * 
	 * @hibernate.property column="fddescript"
	 */
	public String getFieldName()
	{
		return fieldName;
	}
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	/**
	 * 
	 * @hibernate.property column="fdname"
	 */
	public String getFieldNameStr()
	{
		return fieldNameStr;
	}
	public void setFieldNameStr(String fieldNameStr)
	{
		this.fieldNameStr = fieldNameStr;
	}
	/**
	 * 
	 * @hibernate.property column="memo"
	 */
	public String getMemo()
	{
		return memo;
	}
	public void setMemo(String memo)
	{
		this.memo = memo;
	}
	/**
	 * 
	 * @hibernate.property column="showorder"
	 */
	public Long getShoworder()
	{
		return showorder;
	}
	public void setShoworder(Long showorder)
	{
		this.showorder = showorder;
	}
	/**
	 * 
	 * @hibernate.property column="tab_name"
	 */
	public String getTableName()
	{
		return tableName;
	}
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}
	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="seq_code_table_field"
	 */
	public Long getPkid()
	{
		return pkid;
	}
	public void setPkid(Long pkid)
	{
		this.pkid = pkid;
	}
}
