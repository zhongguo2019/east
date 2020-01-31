package com.krm.slsint.workfile.web.form;


import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.form.BaseForm;


/**
 * �����ļ�Form
 * @struts.form name="workFileForm"
 */
public class WorkFileForm extends BaseForm{

	private static final long serialVersionUID = -8270166091139619448L;
	
	private Long pkId;
	
	private Long kindId;
	
	private Long fileSourceId;
	
	private String issDate;
	
	private String title;
	
	private String content;
	
	private FormFile file;
	
	private String fileName;
	
	private Long filePkId;

	/**
	 * 
	 * @return ����
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * @param content ����
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * @return �ļ�
	 */
	public FormFile getFile() {
		return file;
	}

	/**
	 * 
	 * @param file �ļ�
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}

	/**
	 * 
	 * @return �ļ�Դ����
	 */
	public Long getFileSourceId() {
		return fileSourceId;
	}

	/**
	 * 
	 * @param fileSourceId �ļ�Դ����
	 */
	public void setFileSourceId(Long fileSourceId) {
		this.fileSourceId = fileSourceId;
	}

	/**
	 * 
	 * @return �Ƿ�����
	 */
	public String getIssDate() {
		return issDate;
	}

	/**
	 * 
	 * @param issDate �Ƿ�����
	 */
	public void setIssDate(String issDate) {
		this.issDate = issDate;
	}

	/**
	 * 
	 * @return �������
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * 
	 * @param kindId �������
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * 
	 * @return ����
	 */ 
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title ����
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return ������
	 */
	public Long getPkId() {
		return pkId;
	}

	/**
	 * 
	 * @param pkId ������
	 */
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	/**
	 * 
	 * @return �ļ����
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 
	 * @param fileName �ļ����
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 
	 * @return �ļ�������
	 */
	public Long getFilePkId() {
		return filePkId;
	}

	/**
	 * 
	 * @param filePkId �ļ�������
	 */
	public void setFilePkId(Long filePkId) {
		this.filePkId = filePkId;
	}
	
	
}
