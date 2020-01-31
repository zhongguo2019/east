package com.krm.ps.sysmanage.organmanage.vo;

import java.io.Serializable;

/**
 * <p>Title: 用户部门索引表</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 * @hibernate.class table="code_user_dept_idx"
 */
public class UserDeptIdx implements Serializable
{

	private static final long serialVersionUID = -8735579879674001988L;
	
	private Long pkid;
	
	private Long userId;
	
	private String userName;
	
	private Long deptId;
	
	private String deptName;
	
	private String organCode;
	
	private String organName;

	/**
	 * @return the pkid
	 * @hibernate.id column="pkid" type="java.lang.Long" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_user_dept_idx_seq"
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
	 * @return the deptId
	 * @hibernate.property column="dept_id"
	 */
	public Long getDeptId()
	{
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Long deptId)
	{
		this.deptId = deptId;
	}

	/**
	 * @return the deptName
	 * @hibernate.property column="dept_name"
	 */
	public String getDeptName()
	{
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	/**
	 * @return the organCode
	 * @hibernate.property column="organ_code"
	 */
	public String getOrganCode()
	{
		return organCode;
	}

	/**
	 * @param organCode the organCode to set
	 */
	public void setOrganCode(String organCode)
	{
		this.organCode = organCode;
	}

	/**
	 * @return the organName
	 * @hibernate.property column="organ_name"
	 */
	public String getOrganName()
	{
		return organName;
	}

	/**
	 * @param organName the organName to set
	 */
	public void setOrganName(String organName)
	{
		this.organName = organName;
	}

	/**
	 * @return the userId
	 * @hibernate.property column="user_id"
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	/**
	 * @return the userName
	 * @hibernate.property column="user_name"
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	

}
