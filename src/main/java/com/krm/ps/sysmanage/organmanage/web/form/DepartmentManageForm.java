package com.krm.ps.sysmanage.organmanage.web.form;

import org.apache.struts.action.ActionForm;

/**
 * <p>Title: 部门管理相关功能的form</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 * @struts.form name="departmentManageForm"
 */
public class DepartmentManageForm extends ActionForm
{

	private static final long serialVersionUID = -5651217365851774121L;
	
	/**
	 * 操作标识，0：删除，1：保存
	 */
	private String operFlag;
	
	/**
	 * 部门ID
	 */
	private Long deptId;
	
	/**
	 * 部门名称
	 */
	private String deptName;
	
	/**
	 * 要保存的用户ID
	 */
	private String userIdStr;
	
	/**
	 * 要保存的用户名称
	 */
	private String userNameStr;
	
	/**
	 * 要保存的机构编码
	 */
	private String organCode;
	
	/**
	 * 要保存的机构名称
	 */
	private String organName;

	/**
	 * 部门ID
	 */
	public Long getDeptId()
	{
		return deptId;
	}

	/**
	 * 部门ID
	 */
	public void setDeptId(Long deptId)
	{
		this.deptId = deptId;
	}

	/**
	 * 操作标识，0：删除，1：保存
	 */
	public String getOperFlag()
	{
		return operFlag;
	}

	/**
	 * 操作标识，0：删除，1：保存
	 */
	public void setOperFlag(String operFlag)
	{
		this.operFlag = operFlag;
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
	 * 要保存的机构编码
	 */
	public String getOrganCode()
	{
		return organCode;
	}

	/**
	 * 要保存的机构编码
	 */
	public void setOrganCode(String organCode)
	{
		this.organCode = organCode;
	}

	/**
	 * 要保存的机构名称
	 */
	public String getOrganName()
	{
		return organName;
	}

	/**
	 * 要保存的机构名称
	 */
	public void setOrganName(String organName)
	{
		this.organName = organName;
	}

	/**
	 * 要保存的用户ID
	 */
	public String getUserIdStr()
	{
		return userIdStr;
	}

	/**
	 * 要保存的用户ID
	 */
	public void setUserIdStr(String userIdStr)
	{
		this.userIdStr = userIdStr;
	}

	/**
	 * 要保存的用户名称
	 */
	public String getUserNameStr()
	{
		return userNameStr;
	}

	/**
	 * 要保存的用户名称
	 */
	public void setUserNameStr(String userNameStr)
	{
		this.userNameStr = userNameStr;
	}


}
