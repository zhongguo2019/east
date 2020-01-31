package com.krm.ps.framework.common.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.krm.ps.framework.vo.BaseObject;

/**
 * @hibernate.class table="common_accessory"
 */
public class GeneralAccessory extends BaseObject implements Serializable,
		Accessory
{
	private static final long serialVersionUID = -4407249396333652334L;

	private Long accessoryId;

	private Date createTime;

	private Integer dispOrder;

	private Date editTime;

	private Long entityId;

	private String entityKind;

	private String extName;

	private String originalName;

	private String randomName;

	private Integer status;

	private String title;

	/**
	 * @hibernate.id column="accessoryid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="common_accessory_seq"
	 */
	public Long getAccessoryId()
	{
		return accessoryId;
	}

	/**
	 * 
	 * @hibernate.property column="createtime" 
	 */
	public Date getCreateTime()
	{
		return createTime;
	}

	/**
	 * 
	 * @hibernate.property column="disporder" 
	 */
	public Integer getDispOrder()
	{
		return dispOrder;
	}

	/**
	 * 
	 * @hibernate.property column="edittime" 
	 */
	public Date getEditTime()
	{
		return editTime;
	}

	/**
	 * 
	 * @hibernate.property column="entityid" 
	 */
	public Long getEntityId()
	{
		return entityId;
	}

	/**
	 * 
	 * @hibernate.property column="entitykind" 
	 */
	public String getEntityKind()
	{
		return entityKind;
	}

	/**
	 * 
	 * @hibernate.property column="extname" 
	 */
	public String getExtName()
	{
		return extName;
	}

	/**
	 * 
	 * @hibernate.property column="originalname" 
	 */
	public String getOriginalName()
	{
		return originalName;
	}

	/**
	 * 
	 * @hibernate.property column="randomname" 
	 */
	public String getRandomName()
	{
		return randomName;
	}

	/**
	 * 
	 * @hibernate.property column="status" 
	 */
	public Integer getStatus()
	{
		return status;
	}

	/**
	 * 
	 * @hibernate.property column="title" 
	 */
	public String getTitle()
	{
		return title;
	}

	public void setAccessoryId(Long accessoryId)
	{
		this.accessoryId = accessoryId;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public void setDispOrder(Integer dispOrder)
	{
		this.dispOrder = dispOrder;
	}

	public void setEditTime(Date editTime)
	{
		this.editTime = editTime;
	}

	public void setEntityId(Long entityId)
	{
		this.entityId = entityId;
	}

	public void setEntityKind(String entityKind)
	{
		this.entityKind = entityKind;
	}

	public void setExtName(String extName)
	{
		this.extName = extName;
	}

	public void setOriginalName(String originalName)
	{
		this.originalName = originalName;
	}

	public void setRandomName(String randomName)
	{
		this.randomName = randomName;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GeneralAccessory))
		{
			return false;
		}

		final GeneralAccessory accessory = (GeneralAccessory) o;

		if (accessoryId != null ? !accessoryId.equals(accessory
			.getAccessoryId()) : accessory.getAccessoryId() != null)
		{
			return false;
		}
		return true;
	}

	public int hashCode()
	{
		return (accessoryId != null ? accessoryId.hashCode() : 0);
	}

	public String toString()
	{
		return new ToStringBuilder(this).append("accessoryId",
			getAccessoryId()).toString();
	}
}
