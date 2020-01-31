package com.krm.ps.sysmanage.usermanage.web.form;

import com.krm.ps.framework.common.web.form.BaseForm;
/*
 *  添加日期:2006年9月13日
 *  添加人:赵鹏程
 */
/**
 * 用户角色Form
 * @struts.form name="roleForm"
 */
public class roleForm extends BaseForm{

	private static final long serialVersionUID = 1L;
	
	private Long pkid;
	private Long roleType;
	private String name;
	private String description;
	private String menuId;
	private String roleLevel;
	
	
	/**
	 * 角色级别
	 */
	public String getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}
	/**
	 * 
	 * @return 描述
	 */
	public String getDescription() {
		return description;
	}	
	/**
	 * 
	 * @param description 描述
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return 角色类型
	 */
	public Long getRoleType() {
		return roleType;
	}	
	/**
	 * 
	 * @param roleType 角色类型
	 */
	public void setRoleType(Long roleType) {
		this.roleType = roleType;
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
	 * @return 菜单编码
	 */
	public String getMenuId() {
		return menuId;
	}	
	/**
	 * 
	 * @param menuId 菜单编码
	 */ 
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
}
