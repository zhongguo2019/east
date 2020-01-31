package com.krm.ps.sysmanage.usermanage.web.form;

import org.apache.struts.action.ActionForm;
/**
 * 用户Form
 * @struts.form name="userForm"
 */
public class UserForm extends ActionForm {
	
	private static final long serialVersionUID = 9987554454L;

	private Long pkid;
	
	private String name;
	
	private String logonName;
	
	private String password;	
	
	private Long roleType;
	
	private Long groupType;
	
	private String organId;
	
	private String areaCode;
	
	private String idCard;//身份证号
	
	private String phone;//联系电话
	
	private String tellerId;//柜员号
	
	private String ipAddr;//登陆IP地址
	
	
	
	
	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTellerId() {
		return tellerId;
	}

	public void setTellerId(String tellerId) {
		this.tellerId = tellerId;
	}

	/**
	 * 
	 * @return 用户组类型
	 */
	public Long getGroupType() {
		return groupType;
	}
	
	/**
	 * 
	 * @param groupType 用户组类型
	 */
	public void setGroupType(Long groupType) {
		this.groupType = groupType;
	}
	
	public UserForm(){
		
	}
	
	/**
	 * 
	 * @return 角色类型
	 */
	public Long getRoleType() {
		return roleType;
	}

	
	/**
	 * 
	 * @param role 角色类型
	 */
	public void setRoleType(Long role) {
		this.roleType = role;
	}
	
	/**
	 * 
	 * @return 登录用户名
	 */
	public String getLogonName() {
		return logonName;
	}
	
	/**
	 * 
	 * @param logonName 登录用户名
	 */
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}
	
	/**
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
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
	
	/**
	 * 
	 * @return 主键编码
	 */
	public Long getPkid() {
		return pkid;
	}
	
	/**
	 * 
	 * @param pkid 主键编码
	 */
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	
	/**
	 * 
	 * @return 机构编码
	 */
	public String getOrganId()
	{
		return organId;
	}
	
	/**
	 * 
	 * @param organId 机构编码
	 */
	public void setOrganId(String organId)
	{
		this.organId = organId;
	}
	
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
	
}
