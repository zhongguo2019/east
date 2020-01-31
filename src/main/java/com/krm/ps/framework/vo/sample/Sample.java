package com.krm.ps.framework.vo.sample;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import com.krm.ps.framework.vo.BaseObject;
/**
 * 
 * @hibernate.class table=""
 */
public class Sample extends BaseObject implements Serializable
{
	private static final long serialVersionUID = 618904432818592888L;

	private Long sampleId;
	
	private Long sampleField;
	
	/**
	 * @hibernate.id column="" generator-class="native"
	 * @hibernate.generator-param name="sequence" value=""
	 */
	public Long getSampleId()
	{
		return sampleId;
	}

	/**
	 * 
	 * @hibernate.property column=""
	 */
	public Long getSampleField()
	{
		return sampleField;
	}

	public void setSampleId(Long sampleId)
	{
		this.sampleId = sampleId;
	}

	public void setSampleField(Long sampleField)
	{
		this.sampleField = sampleField;
	}

	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Sample))
		{
			return false;
		}

		final Sample sampleObject = (Sample) o;

		if (sampleId == null ? !sampleId.equals(sampleObject.getSampleId())
			: sampleObject.getSampleId() != null)
		{
			return false;
		}
		return true;
	}

	public int hashCode()
	{
		return (sampleId != null ? sampleId.hashCode() : 0);
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("sampleId", sampleId).toString();
	}
}
