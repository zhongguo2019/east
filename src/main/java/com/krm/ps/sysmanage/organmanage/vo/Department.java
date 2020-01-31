package com.krm.ps.sysmanage.organmanage.vo;

import java.io.Serializable;

/**
 * <p>Title: 部门实体类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 * @hibernate.class table="code_department"
 */
public class Department implements Serializable
{

	private static final long serialVersionUID = -6226884256848855354L;
	
	private Long pkid;
	
	/**
	 * 部门名称
	 */
	private String deptName;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	
	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 部门信息说明
	 */
	private String description;

	/**
	 * 创建时间
	 * @hibernate.property column="create_time"
	 */
	public String getCreateTime()
	{
		return createTime;
	}


	/**
	 * 创建时间
	 */
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}


	/**
	 * 创建人
	 * @hibernate.property column="create_user"
	 */
	public String getCreateUser()
	{
		return createUser;
	}


	/**
	 * 创建人
	 */
	public void setCreateUser(String createUser)
	{
		this.createUser = createUser;
	}


	/**
	 * 部门名称
	 * @hibernate.property column="dept_name"
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
	 * @return the pkid
	 * @hibernate.id column="pkid" type="java.lang.Long" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_department_pkid_seq"
	 */
	public Long getPkid()
	{
		return pkid;
	}


	/**
	 * @param pkid the pkid to set
	 */
	public void setPkid(Long pkid)
	{
		this.pkid = pkid;
	}


	/**
	 * 部门信息说明
	 * @hibernate.property column="description"
	 */
	public String getDescription()
	{
		return description;
	}


	/**
	 * 部门信息说明
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	
}
