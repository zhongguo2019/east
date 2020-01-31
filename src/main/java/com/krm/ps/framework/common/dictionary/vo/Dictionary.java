package com.krm.ps.framework.common.dictionary.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.krm.ps.framework.vo.BaseObject;

/**
 * Dictionary class
 * 
 * This class is used to generate the Struts Validator Form as well as the
 * Hibernate mapping file.
 * 
 * <p><a href="Dictionary.java.html"><i>View Source</i></a></p>
 * 
 * @author
 *         
 * @hibernate.class table="code_dictionary"
 */
public class Dictionary extends BaseObject implements Serializable
{

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = -3439177684232609688L;

	private Long dicId;

	private String dicName;

	private Long parentId;

	private Integer layer;

	private String isLeaf;

	private String dispOrder;

	private String description;

	private String modifyTime;

	private Integer status;

	/** full constructor */
	public Dictionary(Long dicId, String dicName, Long parentId, Integer layer,
		String isLeaf, String dispOrder, String description, String modifyTime,
		Integer status)
	{
		this.dicId = dicId;
		this.dicName = dicName;
		this.parentId = parentId;
		this.layer = layer;
		this.isLeaf = isLeaf;
		this.dispOrder = dispOrder;
		this.description = description;
		this.modifyTime = modifyTime;
		this.status = status;
	}

	/** default constructor */
	public Dictionary()
	{
	}

	/** minimal constructor */
	public Dictionary(Long dicId)
	{
		this.dicId = dicId;
	}

	/**
	 * @hibernate.id column="dicid" type="java.lang.Long" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_dictionary_seq"
	 * 
	 */
	public Long getDicId()
	{
		return this.dicId;
	}

	public void setDicId(Long dicId)
	{
		this.dicId = dicId;
	}

	/**
	 * @hibernate.property column="dicname"
	 */
	public String getDicName()
	{
		return this.dicName;
	}

	public void setDicName(String dicName)
	{
		this.dicName = dicName;
	}

	/**
	 * @hibernate.property column="parentid"
	 */
	public Long getParentId()
	{
		return this.parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * @hibernate.property column="layer"
	 */
	public Integer getLayer()
	{
		return this.layer;
	}

	public void setLayer(Integer layer)
	{
		this.layer = layer;
	}

	/**
	 * @hibernate.property column="isleaf"
	 */
	public String getIsLeaf()
	{
		return this.isLeaf;
	}

	public void setIsLeaf(String isLeaf)
	{
		this.isLeaf = isLeaf;
	}

	/**
	 * @hibernate.property column="disporder"
	 */
	public String getDispOrder()
	{
		return this.dispOrder;
	}

	public void setDispOrder(String dispOrder)
	{
		this.dispOrder = dispOrder;
	}

	/**
	 * @hibernate.property column="description" 
	 */
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @hibernate.property column="modifytime"
	 */
	public String getModifyTime()
	{
		return this.modifyTime;
	}

	public void setModifyTime(String modifyTime)
	{
		this.modifyTime = modifyTime;
	}

	/**
	 * @hibernate.property column="status" 
	 */
	public Integer getStatus()
	{
		return this.status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String toString()
	{
		return new ToStringBuilder(this).append("dicId", getDicId()).toString();
	}

	public boolean equals(Object o)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public int hashCode()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
