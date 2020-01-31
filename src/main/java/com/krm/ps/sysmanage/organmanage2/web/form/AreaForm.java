package com.krm.ps.sysmanage.organmanage2.web.form;

import com.krm.ps.framework.common.web.form.BaseForm;

/**
 * 机构管理区域Form
 * @struts.form name="areaForm"
 */
public class AreaForm extends BaseForm{

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 1L;
	
	private int areaId;
	
	private String areaCode;
	
	private String fullName;
	
	private String shortName;
	
	private String beginDate;
	
	private String endDate;
	
	private String superAreaCode;
	
	private String oldCode;

	/**
	 * 
	 * @return 区域编码
	 */
	public String getAreaCode()
	{
		return areaCode;
	}

	/**
	 * 
	 * @param areaCode 区域编码
	 */
	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
	}

	/**
	 * 
	 * @return 区域ID
	 */
	public int getAreaId()
	{
		return areaId;
	}

	/**
	 * 
	 * @param areaId 区域ID
	 */
	public void setAreaId(int areaId)
	{
		this.areaId = areaId;
	}

	/**
	 * 
	 * @return 开始日期
	 */
	public String getBeginDate()
	{
		return beginDate;
	}

	/**
	 * 
	 * @param beginDate 开始日期
	 */
	public void setBeginDate(String beginDate)
	{
		this.beginDate = beginDate;
	}

	/**
	 * 
	 * @return 结束日期
	 */
	public String getEndDate()
	{
		return endDate;
	}

	/**
	 * 
	 * @param endDate 结束日期
	 */
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * 
	 * @return 全称
	 */
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * 
	 * @param fullName 全称
	 */
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	/**
	 * 
	 * @return 简称
	 */
	public String getShortName()
	{
		return shortName;
	}

	/**
	 * 
	 * @param shortName 简称
	 */
	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	/**
	 * 
	 * @return 上级区域编码
	 */
	public String getSuperAreaCode()
	{
		return superAreaCode;
	}

	/**
	 *  
	 * @param superAreaCode 上级区域编码
	 */
	public void setSuperAreaCode(String superAreaCode)
	{
		this.superAreaCode = superAreaCode;
	}

	/**
	 * 
	 * @return 原机构编码
	 */
	public String getOldCode()
	{
		return oldCode;
	}

	/**
	 * 
	 * @param oldCode 原机构编码
	 */
	public void setOldCode(String oldCode)
	{
		this.oldCode = oldCode;
	}

}
