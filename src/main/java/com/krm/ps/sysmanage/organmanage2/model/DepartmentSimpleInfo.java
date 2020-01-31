package com.krm.ps.sysmanage.organmanage2.model;

import java.io.Serializable;

/**
 * <p>Title: 部门基本信息</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 */
public class DepartmentSimpleInfo implements Serializable
{
	/**
	 * 部门ID
	 */
	private Long pkid;
	
	/**
	 * 部门名称
	 */
	private String deptName;
	
	public DepartmentSimpleInfo(Long pkid, String deptName)
	{
		this.pkid = pkid;
		this.deptName = deptName;
	}

	/**
	 * 部门名称
	 */
	public String getDeptName()
	{
		return deptName;
	}

	/**
	 * 部门名称
	 */
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	/**
	 * 部门ID
	 */
	public Long getPkid()
	{
		return pkid;
	}

	/**
	 * 部门ID
	 */
	public void setPkid(Long pkid)
	{
		this.pkid = pkid;
	}
}
