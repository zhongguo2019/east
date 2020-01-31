package com.krm.ps.sysmanage.organmanage2.web.form;

import com.krm.ps.framework.common.web.form.BaseForm;

/**
 * 区域用户Form
 * @struts.form name="areaUserForm"
 */
public class AreaUserForm extends BaseForm{
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 1L;

	private int pkid;
	
	private String name;
	
	private String loginName;
	
	private String password;
	
	private String organ;

	/**
	 * 
	 * @return 登录名称
	 */
	public String getLoginName()
	{
		return loginName;
	}

	/**
	 * 
	 * @param loginName 登录名称
	 */
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	/**
	 * 
	 * @return 名称
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 
	 * @param name 名称
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 
	 * @return 机构编码
	 */
	public String getOrgan()
	{
		return organ;
	}

	/**
	 * 
	 * @param organ 机构编码
	 */
	public void setOrgan(String organ)
	{
		this.organ = organ;
	}

	/**
	 * 
	 * @return 密码
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * 
	 * @param password 密码
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * 
	 * @return 主键编码
	 */
	public int getPkid()
	{
		return pkid;
	}

	/**
	 *  
	 * @param pkid 主键编码
	 */
	public void setPkid(int pkid)
	{
		this.pkid = pkid;
	}
}
