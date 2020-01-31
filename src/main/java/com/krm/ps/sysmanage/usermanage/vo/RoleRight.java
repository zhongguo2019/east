package com.krm.ps.sysmanage.usermanage.vo;

import java.io.Serializable;
/*
 *  添加日期:2006年9月13日
 *  添加人:赵鹏程
 */
/**
 * 
 * @hibernate.class table="role_right"
 */
public class RoleRight implements Serializable {

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 1L;
	
	private Long pkid;
	private Long roleId;
	private Long menuId;
	
	/**
	 * 
	 * @hibernate.property column="menu_id"
	 */
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	/**
	 * 
	 * @hibernate.id column="pkid" generator-class="native"
	 */
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	/**
	 * 
	 * @hibernate.property column="role_id"
	 */
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
