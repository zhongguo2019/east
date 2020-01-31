package com.krm.ps.sysmanage.usermanage.vo;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @hibernate.class table="umg_user_role"
 */
public class UserRole implements Serializable{

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 66543322L;

	private Long pkid;
	
	private Long userId;
	
	private Long roleType;
	
	public UserRole(){
		
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
	 * @hibernate.property column="user_id"
	 */
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="umg_user_role_seq"
	 */
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("UserRole", getPkid())
            .toString();
    }
}
