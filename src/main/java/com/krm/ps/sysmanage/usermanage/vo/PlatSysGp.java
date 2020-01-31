package com.krm.ps.sysmanage.usermanage.vo;
import java.io.Serializable;

/**
 * @hibernate.class table="plat_sys_gp"
 */
public class PlatSysGp implements Serializable {

	private static final long serialVersionUID = -399924862718362266L;
	
	/**
	 * ������ĿID
	 */
	private Long id;
	
	/**
	 * ��ϵͳ��ʶ
	 */
	private String sysFlag;
	
	/**
	 * ��ID
	 */
	private Long gpId;
	
	/**
	 * ��Ŀ��������
	 */
	private String createDate;
	
	/**
	 * ��ϵͳ���������״̬
	 */
	private char status;
	
	/**
	 * ����gpId�Ƿ���������黹�ǽ�ɫ
	 */
	private char flag;

	/**
	 * @hibernate.id column="id" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="umg_user_seq"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property column="sys_flag" 
	 */
	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
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
	 * @hibernate.property column="create_date" 
	 */
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @hibernate.property column="status" 
	 */
	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
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

}
