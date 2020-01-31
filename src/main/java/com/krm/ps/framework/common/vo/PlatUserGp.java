package com.krm.ps.framework.common.vo;
import java.io.Serializable;


/**
 * 
 * @hibernate.class table="plat_user_gp"
 */
public class PlatUserGp implements Serializable {

	private static final long serialVersionUID = -399924862718362266L;
	
	/**
	 * 关联条目ID
	 */
	private Long pkId;
	
	/**
	 * 用户Id
	 */
	private Long userId;
	
	/**
	 * 用户关联的分组或角色
	 */
	private Long gpId;
	
	/**
	 * 区分gpId是否关联的是组还是角色，1为分组，2为角色
	 */
	private char flag;

	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="umg_user_seq"
	 */
	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	/**
	 * @hibernate.property column="gp_id" 
	 */
	public Long getGpId() {
		return gpId;
	}

	public void setGpId(Long gpId) {
		this.gpId = gpId;
	}

	/**
	 * @hibernate.property column="flag"
	 */
	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	/**
	 * @hibernate.property column="user_id"
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
