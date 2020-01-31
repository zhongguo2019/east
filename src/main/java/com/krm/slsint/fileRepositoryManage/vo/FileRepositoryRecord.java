package com.krm.slsint.fileRepositoryManage.vo;

import java.io.Serializable;

import com.krm.ps.framework.vo.BaseObject;
/**
 * <p>Title:�ļ��ֿ��ӳ����� </p>
 *
 * <p>Description: ʵ��java�������ģ�͵�ӳ�� </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: KRM Soft </p>
 *
 * @author	��Ծ�� August 25th, 2008
 * 
 * 
 * @hibernate.class table="CODE_FILE_REPOSITORY"
 */
public class FileRepositoryRecord extends BaseObject implements Serializable {

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = -6701282960176960143L;
	
	public FileRepositoryRecord(){
		super();
	}
	
	/**
	 * Ψһ��ʶ
	 */
	private Long pkid;

	/**
	 * ���ܴ���
	 */
	private String funId;

	/**
	 * �༭ʱ��
	 */
	private String editTime;

	/**
	 * �ļ����
	 */
	private String fileName;

	/**
	 * �ļ���ǰ׺
	 */
	private String namePrefix;

	/**
	 * �ļ����׺
	 */
	private String namePostfix;

	/**
	 * �ļ�·��
	 */
	private String filePath;

	/**
	 * �û�ID	
	 */
	private Long userId;

	/**
	 * ��ʾ˳��
	 */
	private Long showOrder;
	
	/**
	 * �ļ�״̬
	 */
	private Long status;
	
	/**
	 * �ļ�����
	 */
	private byte[] fileContent;

	/**
	 * ������Ϣ
	 */
	private String description;

	/**
	 * ��ע
	 */
	private String memo;
	
	/**
	 * ��ID
	 */
	private String organId;
	
	/**
	 * Ƶ�ȣ���ܱ��������ֶ�
	 */
	private String freq;
	
	/**
	 * @hibernate.property column="organ_id" 
	 */
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	/**
	 * @hibernate.property column="description" 
	 */
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @hibernate.property column="edit_time" 
	 */
	public String getEditTime() {
		return editTime;
	}

	public void setEditTime(String edit_time) {
		this.editTime = edit_time;
	}
	
	/**
	 * @hibernate.property type="org.springframework.orm.hibernate3.support.BlobByteArrayType" column="file_content"
	 */
	public byte[] getFileContent() {
		
		return fileContent;
	}
	
	public void setFileContent(byte[] file_content) {
		this.fileContent = file_content;
	}
	
	/**
	 * @hibernate.property column="file_name" 
	 */
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String file_name) {
		this.fileName = file_name;
	}
	
	/**
	 * @hibernate.property column="file_path" 
	 */
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String file_path) {
		this.filePath = file_path;
	}
	
	/**
	 * @hibernate.property column="fun_id" 
	 */
	public String getFunId() {
		return funId;
	}

	public void setFunId(String fun_id) {
		this.funId = fun_id;
	}
	
	/**
	 * @hibernate.property column="memo" 
	 */
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	/**
	 * @hibernate.property column="name_postfix" 
	 */
	public String getNamePostfix() {
		return namePostfix;
	}

	public void setNamePostfix(String name_postfix) {
		this.namePostfix = name_postfix;
	}
	
	/**
	 * @hibernate.property column="name_prefix" 
	 */
	public String getNamePrefix() {
		return namePrefix;
	}

	public void setNamePrefix(String name_prefix) {
		this.namePrefix = name_prefix;
	}
	
	/**
	 * @hibernate.id column="pkid" type="java.lang.Long"
	 *               generator-class="native"	
	 * @hibernate.generator-param name="sequence" value="code_file_repository_pkid_seq"
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
	public Long getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Long show_order) {
		this.showOrder = show_order;
	}
	
	/**
	 * @hibernate.property column="user_id" 
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long user_id) {
		this.userId = user_id;
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
	 * @hibernate.property column="freq" 
	 */
	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}
	
	

}
