package com.krm.slsint.workfile.web.form;

import com.krm.ps.framework.common.web.form.BaseForm;

/**
 * 
 * @struts.form name="workMailForm"
 */
public class WorkMailForm extends BaseForm{

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = -668606836254585321L;	
	//�ռ���Id
	private String addresseeId;
	//�ռ���Name
	private String addressee;
	//�ʼ�����
	private String title;
	//�ʼ�����
	private String content;
////	����
//	private FormFile file;	
	//������Id
	private String addresserId;
	
	/**
	 * 
	 * @return �ռ���Name
	 */
	public String getAddressee() {
		return addressee;
	}	
	/**
	 * 
	 * @param addressee �ռ���Name
	 */
	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}	
	/**
	 * 
	 * @return �ռ���Id
	 */
	public String getAddresseeId() {
		return addresseeId;
	}	
	/**
	 * 
	 * @param addresseeId �ռ���Id
	 */
	public void setAddresseeId(String addresseeId) {
		this.addresseeId = addresseeId;
	}	
	/**
	 * 
	 * @return ������Id
	 */
	public String getAddresserId() {
		return addresserId;
	}	
	/**
	 * 
	 * @param addresserId ������Id
	 */
	public void setAddresserId(String addresserId) {
		this.addresserId = addresserId;
	}	
	/**
	 * 
	 * @return �ʼ�����
	 */
	public String getContent() {
		return content;
	}	
	/**
	 * 
	 * @param content �ʼ�����
	 */
	public void setContent(String content) {
		this.content = content;
	}
//	public FormFile getFile() {
//		return file;
//	}
//	public void setFile(FormFile file) {
//		this.file = file;
//	}
	
	/**
	 * @return �ʼ�����
	 */
	public String getTitle() {
		return title;
	}	
	/**
	 * 
	 * @param title �ʼ�����
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
