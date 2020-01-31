package com.krm.slsint.directorEnquiries.vo;

import java.io.Serializable;
import java.util.List;

import com.krm.ps.framework.vo.BaseObject;

/**
 * �����е�{@link #rightFlag}��ʶ��ʾ��Ȩ�޼�¼�����ʲô���в���ģ�����ɲο�����
 * U����Ա��D������
 * 
 * ����Ŀǰ������ͨ�õģ�Ҳ����˵ͬһ���ſ��Զ�Ӧ����������ڱ����Ȩ��ʱ��ͬʱҪ������Ϣ��Ӧ�Ļ���Ϣ
 * ���ԣ�������ʱ������ͨ��Ȩ�ޱ�ʶ4�������ã��ڱ���Ҫ���Ȩ�޶�Ӧ���û�ID������ID����ID������Ȩ����Ϣ������Ȩ��
 * ��Ϣ����ɲο�{@link DocRight}
 * 
 * @hibernate.class table="umg_file_share"
 */

public class FileShareData extends BaseObject implements Serializable{

	private static final long serialVersionUID = 3810621891537720159L;
	
	//Ψһ��ʶ
	private Long pkid;
	
	//�ļ�ID
	private Long mpkid;
	
	//�û�ID	
	private Long user_id;

	//��ʾ˳��	
	private Long show_order;
	
	//�ļ�״̬
	private Long status;	
	
	/**
	 * �ĵ������Ա��Ȩ�޵���ԱID������Ĭ��Ϊ-1
	 */
	private Long userIdRight;
	
	/**
	 * ����ID����ݿ���Ĭ��Ϊ-1
	 */
	private Long deptId;
	
	/**
	 * ����룬�˻�ID��ָ���Ӧ����Ա�����������ĸ������Ĭ��Ϊ-1
	 */
	private String organCode;
	
	/**
	 * �ĵ�����Ȩ�ޣ���ϸ�ɲο�{@link DocRight}���ĵ�Ȩ�޳�����
	 */
	private String docRight;
	
	/**
	 * Ȩ�ޱ�ʶ��U����ԱȨ�ޣ�D������Ȩ�ޣ�O����Ȩ�ޣ�
	 */
	private Character rightFlag;
	
	/**
	 * Ȩ�����༭ʱ��
	 * yyyyMMddhhmmss
	 */
	private String editTime;

	public String toString() {
		return null;
	}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}
	
	/**
	 * @hibernate.property column="mpkid" 
	 */
	public Long getMpkid() {
		return mpkid;
	}

	public void setMpkid(Long mpkid) {
		this.mpkid = mpkid;
	}

	/**
	 * @hibernate.id column="pkid" type="java.lang.Long"
	 *               generator-class="native"	
	 * @hibernate.generator-param name="sequence" value="umg_file_share_pkid_seq"
	 */
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	
	/**
	 * @hibernate.property column="show_order" 
	 */
	public Long getShow_order() {
		return show_order;
	}

	public void setShow_order(Long show_order) {
		this.show_order = show_order;
	}
	
	/**
	 * @hibernate.property column="status" 
	 */
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	/**
	 * @hibernate.property column="user_id" 
	 */
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	/**
	 * ��ID���˻�ID��ָ���Ӧ����Ա�����������ĸ��
	 * @hibernate.property column = "organ_code"
	 */
	public String getOrganCode()
	{
		return organCode;
	}

	/**
	 * ��ID���˻�ID��ָ���Ӧ����Ա�����������ĸ��
	 */
	public void setOrganCode(String organCode)
	{
		this.organCode = organCode;
	}

	/**
	 * ����ID����ݿ���Ĭ��Ϊ-1
	 * @hibernate.property column = "dept_id"
	 */
	public Long getDeptId()
	{
		return deptId;
	}

	/**
	 * ����ID����ݿ���Ĭ��Ϊ-1
	 */
	public void setDeptId(Long deptId)
	{
		this.deptId = deptId;
	}

	/**
	 * �ĵ������Ա��Ȩ�޵���ԱID
	 * @hibernate.property column = "userid_right"
	 */
	public Long getUserIdRight()
	{
		return userIdRight;
	}

	/**
	 * �ĵ������Ա��Ȩ�޵���ԱID
	 */
	public void setUserIdRight(Long userIdRight)
	{
		this.userIdRight = userIdRight;
	}

	/**
	 * �ĵ�����Ȩ�ޣ���ϸ�ɲο�{@link DocRight}���ĵ�Ȩ�޳�����
	 * @hibernate.property column = "doc_right"
	 */
	public String getDocRight()
	{
		return docRight;
	}

	/**
	 * �ĵ�����Ȩ�ޣ���ϸ�ɲο�{@link DocRight}���ĵ�Ȩ�޳�����
	 */
	public void setDocRight(String docRight)
	{
		this.docRight = docRight;
	}

	/**
	 * Ȩ�����༭ʱ��
	 * yyyyMMddhhmmss
	 * @hibernate.property column = "edit_time"
	 */
	public String getEditTime()
	{
		return editTime;
	}

	/**
	 * Ȩ�����༭ʱ��
	 * yyyyMMddhhmmss
	 */
	public void setEditTime(String editTime)
	{
		this.editTime = editTime;
	}

	/**
	 * Ȩ�ޱ�ʶ��U����ԱȨ�ޣ�D������Ȩ�ޣ�O����Ȩ�ޣ�
	 * @hibernate.property column = "right_flag"
	 */
	public Character getRightFlag()
	{
		return rightFlag;
	}

	/**
	 * Ȩ�ޱ�ʶ��U����ԱȨ�ޣ�D������Ȩ�ޣ�O����Ȩ�ޣ�
	 */
	public void setRightFlag(Character rightFlag)
	{
		this.rightFlag = rightFlag;
	}

}
