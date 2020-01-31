package com.krm.ps.sysmanage.usermanage.vo;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.krm.ps.util.ConvertUtil;


/**
 * 
 * @hibernate.class table="umg_user"
 */
public class User implements Serializable{
	

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = -8925404447226657028L;

	private Long pkid;
	
	private String name;
	
	private String logonName;
	
	private String password;
	
	private String organId;
	
	private Integer isAdmin;
	
	private Integer status;
	
	private String createtime;
	
	private Long creator;
	
	private Long roleType;
	
	private String roleName;
	
	private String enUserid;//图表加密用户ID zsj增加
	
	private String enPassword;//图表加密用户密码 zsj增加
	
	private String systemType;
	
	private String ipAddr;//登陆IP地址
	
	
	
	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	/**
	 * 用户所在部门ID
	 */
	private Long departmentId;
	
	/**
	 * 用户所在部门名称
	 */
	private String departmentName;
	
	/**
	 * <code>机构树结构索引id(CODE_ORG_TREE.IDXID)</code>
	 */
	private int organTreeIdxid;
		
	/**
	 * <code>表示机构树的XML</code>
	 */
//	private String organTreeXML;
	
	/**
	 * <code>本用户所属机构在当前机构树下的直接下级机构的code的列表</code>
	 */
//	private List suborganList;
	
	/**
	 * <code>本用户所属机构在当前机构树下的所有各层下级机构的code的列表</code>
	 */
//	private List allLevelSuborganList;

	public User(){
		
	}
	
	public User(User user,Long roleType,String roleName){
		try {
			ConvertUtil.copyProperties(this, user, true);
			this.roleType=roleType;
			this.roleName=roleName;
			
		} catch (Exception e) {
			;
		}
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleType() {
		return roleType;
	}

	public void setRoleType(Long role) {
		this.roleType = role;
	}

	/**
	 * 
	 * @hibernate.property column="creator"
	 */
	public Long getCreator() {
		return creator;
	}



	public void setCreator(Long creator) {
		this.creator = creator;
	}


	/**
	 * 
	 * @hibernate.property column="createtime"
	 */
	public String getCreatetime() {
		return createtime;
	}



	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}


	/**
	 * 
	 * @hibernate.property column="isadmin"
	 */
	public Integer getIsAdmin() {
		return isAdmin;
	}



	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}


	/**
	 * 
	 * @hibernate.property column="logon_name"
	 */
	public String getLogonName() {
		return logonName;
	}



	public void setLogonName(String logonName) {
		this.logonName = logonName;
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
	 * 
	 * @hibernate.property column="organ_id"
	 */
	public String getOrganId() {
		return organId;
	}



	public void setOrganId(String organId) {
		this.organId = organId;
	}


	/**
	 * 
	 * @hibernate.property column="password"
	 */
	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="umg_user_seq"
	 */
	public Long getPkid() {
		return pkid;
	}



	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}


	/**
	 * 
	 * @hibernate.property column="status"
	 */
	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public String toString() {
        return new ToStringBuilder(this)
            .append("User", getPkid())
            .toString();
    }

	/**
	 * 取得本用户所属机构在当前机构树下的所有各层下级机构的code的列表
	 * @return 本用户所属机构在当前机构树下的所有各层下级机构的code的列表
	 */
//	public List getAllLevelSuborganList() {
//		return allLevelSuborganList;
//	}

	/**
	 * 设置本用户所属机构在当前机构树下的所有各层下级机构的code的列表
	 * @param allLevelSuborganList 机构的code的列表
	 */
//	public void setAllLevelSuborganList(List allLevelSuborganList) {
//		this.allLevelSuborganList = allLevelSuborganList;
//	}

	/**
	 * 取得机构树结构索引id(CODE_ORG_ORGAN.IDXID)
	 * @return 用户当前的机构树结构索引id(CODE_ORG_ORGAN.IDXID)
	 */
	public int getOrganTreeIdxid() {
		return 1;//organTreeIdxid;
	}

	/**
	 * 设置机构树结构索引id(CODE_ORG_ORGAN.IDXID)
	 * @param organTreeIdxid 机构树结构索引id(CODE_ORG_ORGAN.IDXID)
	 */
	public void setOrganTreeIdxid(int organTreeIdxid) {
		this.organTreeIdxid = organTreeIdxid;
	}

	public String getEnUserid() {
		return enUserid;
	}

	public void setEnUserid(String enUserid) {
		this.enUserid = enUserid;
	}

	public String getEnPassword() {
		return enPassword;
	}

	public void setEnPassword(String enPassword) {
		this.enPassword = enPassword;
	}

	/**
	 * 用户所在部门ID
	 */
	public Long getDepartmentId()
	{
		return departmentId;
	}

	/**
	 * 用户所在部门ID
	 */
	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	/**
	 * 用户所在部门名称
	 */
	public String getDepartmentName()
	{
		return departmentName;
	}

	public String getSystemType()
	{
		return systemType;
	}

	public void setSystemType(String systemType)
	{
		this.systemType = systemType;
	}
	/**
	 * 用户所在部门名称
	 */
	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	/**
	 * 取得表示机构树的XML
	 * @return 表示机构树的XML
	 */
//	public String getOrganTreeXML() {
//		return organTreeXML;
//	}

	/**
	 * 设置表示机构树的XML
	 * @param organTreeXML 表示机构树的XML
	 */
//	public void setOrganTreeXML(String organTreeXML) {
//		this.organTreeXML = organTreeXML;
//	}

	/**
	 * 取得本用户所属机构在当前机构树下的直接下级机构的code的列表
	 * @return 本用户所属机构在当前机构树下的直接下级机构的code的列表
	 */
//	public List getSuborganList() {
//		return suborganList;
//	}

	/**
	 * 设置本用户所属机构在当前机构树下的直接下级机构的code的列表
	 * @param suborganList 机构的code的列表
	 */
//	public void setSuborganList(List suborganList) {
//		this.suborganList = suborganList;
//	}
}
