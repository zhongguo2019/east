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
	
	private String enUserid;//ͼ������û�ID zsj����
	
	private String enPassword;//ͼ������û����� zsj����
	
	private String systemType;
	
	private String ipAddr;//��½IP��ַ
	
	
	
	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	/**
	 * �û����ڲ���ID
	 */
	private Long departmentId;
	
	/**
	 * �û����ڲ�������
	 */
	private String departmentName;
	
	/**
	 * <code>�������ṹ����id(CODE_ORG_TREE.IDXID)</code>
	 */
	private int organTreeIdxid;
		
	/**
	 * <code>��ʾ��������XML</code>
	 */
//	private String organTreeXML;
	
	/**
	 * <code>���û����������ڵ�ǰ�������µ�ֱ���¼�������code���б�</code>
	 */
//	private List suborganList;
	
	/**
	 * <code>���û����������ڵ�ǰ�������µ����и����¼�������code���б�</code>
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
	 * ȡ�ñ��û����������ڵ�ǰ�������µ����и����¼�������code���б�
	 * @return ���û����������ڵ�ǰ�������µ����и����¼�������code���б�
	 */
//	public List getAllLevelSuborganList() {
//		return allLevelSuborganList;
//	}

	/**
	 * ���ñ��û����������ڵ�ǰ�������µ����и����¼�������code���б�
	 * @param allLevelSuborganList ������code���б�
	 */
//	public void setAllLevelSuborganList(List allLevelSuborganList) {
//		this.allLevelSuborganList = allLevelSuborganList;
//	}

	/**
	 * ȡ�û������ṹ����id(CODE_ORG_ORGAN.IDXID)
	 * @return �û���ǰ�Ļ������ṹ����id(CODE_ORG_ORGAN.IDXID)
	 */
	public int getOrganTreeIdxid() {
		return 1;//organTreeIdxid;
	}

	/**
	 * ���û������ṹ����id(CODE_ORG_ORGAN.IDXID)
	 * @param organTreeIdxid �������ṹ����id(CODE_ORG_ORGAN.IDXID)
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
	 * �û����ڲ���ID
	 */
	public Long getDepartmentId()
	{
		return departmentId;
	}

	/**
	 * �û����ڲ���ID
	 */
	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	/**
	 * �û����ڲ�������
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
	 * �û����ڲ�������
	 */
	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	/**
	 * ȡ�ñ�ʾ��������XML
	 * @return ��ʾ��������XML
	 */
//	public String getOrganTreeXML() {
//		return organTreeXML;
//	}

	/**
	 * ���ñ�ʾ��������XML
	 * @param organTreeXML ��ʾ��������XML
	 */
//	public void setOrganTreeXML(String organTreeXML) {
//		this.organTreeXML = organTreeXML;
//	}

	/**
	 * ȡ�ñ��û����������ڵ�ǰ�������µ�ֱ���¼�������code���б�
	 * @return ���û����������ڵ�ǰ�������µ�ֱ���¼�������code���б�
	 */
//	public List getSuborganList() {
//		return suborganList;
//	}

	/**
	 * ���ñ��û����������ڵ�ǰ�������µ�ֱ���¼�������code���б�
	 * @param suborganList ������code���б�
	 */
//	public void setSuborganList(List suborganList) {
//		this.suborganList = suborganList;
//	}
}
