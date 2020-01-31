package com.krm.ps.sysmanage.organmanage.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * 系统初始化Form
 * @struts.form name="sysInitForm"
 */
public class SysInitForm extends ActionForm {

	private static final long serialVersionUID = 152523L;

	private String logonName;

	private String userName;

	private String password;

	private String areaCode;

	private String areaName;

//	private int organLevel;
//
//	private int superOrganLevel;

	private String organCode;

	private String organShortName;

	private FormFile backupFile;
	
	/**
	 * 左少杰在2006/06/29增加代码
	 * <code>系统部署模式deployMode=0为分布部署系统，deployMode=1为大集中部署系统</code>
	 */
	private Integer deployMode;

	/**
	 * 
	 * @return 备份文件
	 */
	public FormFile getBackupFile() {
		return backupFile;
	}

	/**
	 * 
	 * @param backupFile 备份文件
	 */
	public void setBackupFile(FormFile backupFile) {
		this.backupFile = backupFile;
	}

	/**
	 * 
	 * @return 登录名称
	 */
	public String getLogonName() {
		return logonName;
	}

	/**
	 * 
	 * @param logonName 登录名称
	 */
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	/**
	 * 
	 * @return 区域编码
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * 
	 * @param areaCode 区域编码
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * 
	 * @return 机构编码
	 */
	public String getOrganCode() {
		return organCode;
	}

	/**
	 * 
	 * @param organCode 机构编码
	 */
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	/**
	 * 
	 * @return 区域名称
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * 
	 * @param areaName 区域名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

//	public int getOrganLevel() {
//		return organLevel;
//	}
//
//	public void setOrganLevel(int organLevel) {
//		this.organLevel = organLevel;
//	}

	/**
	 * 机构简称
	 */
	public String getOrganShortName() {
		return organShortName;
	}

	/**
	 * 
	 * @param organShortName 机构简称
	 */
	public void setOrganShortName(String organShortName) {
		this.organShortName = organShortName;
	}

	/**
	 * 
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

//	public int getSuperOrganLevel() {
//		return superOrganLevel;
//	}
//
//	public void setSuperOrganLevel(int superOrganLevel) {
//		this.superOrganLevel = superOrganLevel;
//	}

	/**
	 * @return 用户名称
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName 用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return 发布模式
	 */
	public Integer getDeployMode() {
		return deployMode;
	}

	/**
	 * 
	 * @param deployMode 发布模式
	 */
	public void setDeployMode(Integer deployMode) {
		this.deployMode = deployMode;
	}

}
