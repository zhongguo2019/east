package com.krm.ps.sysmanage.usermanage.vo;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
/*
 * 修改日期:2006年9月13日
 * 修改人:赵鹏程
 */
/**
 * 
 * @hibernate.class table="umg_role"
 */
public class Role implements Serializable{

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 999333221L;

	private Long pkid;
	
	private String name;
	
	private Long roleType;
	
	private String description;
	
	private String roleLevel;
	
	
	
	/**
	 * 
	 * 角色级别
	 */

	public String getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

	public Role(){
		
	}

	/**
	 * 
	 * @hibernate.property column="description"
	 */
	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * 
	 * @hibernate.property column="role_type"
	 */
	public Long getRoleType() {
		return roleType;
	}



	public void setRoleType(Long roleType) {
		this.roleType = roleType;
	}



	/**
	 * 
	 * @hibernate.property column="name"
	 */
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="umg_role_seq"
	 */
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("Role", getPkid())
            .toString();
    }
}
