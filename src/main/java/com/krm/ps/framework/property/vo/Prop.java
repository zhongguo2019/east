package com.krm.ps.framework.property.vo;

import java.io.Serializable;

/**
 * ��ϵͳ��������
 * @author Administrator
 * @hibernate.class table="code_property"
 */
public class Prop  implements Serializable {

	private static final long serialVersionUID = -3128408673794427862L;

	/**
	 * ����ID
	 */
	private Long proId;
	
	/**
	 * ��������
	 */
	private String proName;  
	
	/**
	 * ����ֵ
	 */
	private String proValue;
	
	/**
	 * ����ע��
	 */
	private String proComment;
	
	/**
	 * ����ʱ��
	 */
	private String createDate; 
	
	/**
	 * �޸�ʱ��
	 */
	private String editDate; 
	
	/**
	 * ����״̬
	 */
	private char status;
	
	/**
	 * @hibernate.id column="pro_id" type="java.lang.Long" generator-class="native"
	 */
	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

	/**
	 * @hibernate.property column="pro_name" 
	 */
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	/**
	 * @hibernate.property column="pro_value" 
	 */
	public String getProValue() {
		return proValue;
	}

	public void setProValue(String proValue) {
		this.proValue = proValue;
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
	 * @hibernate.property column="pro_comment" 
	 */
	public String getProComment() {
		return proComment;
	}

	public void setProComment(String proComment) {
		this.proComment = proComment;
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
	 * @hibernate.property column="edit_date" 
	 */
	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

}
