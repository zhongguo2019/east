package com.krm.ps.model.reportdefine.web.form;

import org.apache.struts.action.ActionForm;

public class ReportTypeForm extends ActionForm {

	private static final long serialVersionUID = 666655533332L;

	private Long pkid;

	private String name;

	private Integer dataSource;

	private Integer isBalance;

	private Integer isCollect;

	private Integer isInput;

	private Integer isUpdate;

	private Integer systemCode;

	private Integer isShowRep;

	private Integer upReport;
	private Integer showlevel;

	public Integer getShowlevel() {
		return showlevel;
	}

	public void setShowlevel(Integer showlevel) {
		this.showlevel = showlevel;
	}

	public ReportTypeForm() {

	}

	/**
	 * 
	 * @return ��ƽ����
	 */
	public Integer getUpReport() {
		return upReport;
	}

	/**
	 * 
	 * @param upReport
	 *            ��ƽ����
	 */
	public void setUpReport(Integer upReport) {
		this.upReport = upReport;
	}

	/**
	 * 
	 * @return �����Ƿ�չʾ
	 */
	public Integer getIsShowRep() {
		return isShowRep;
	}

	/**
	 * 
	 * @param isShowRep
	 *            �����Ƿ�չʾ
	 */
	public void setIsShowRep(Integer isShowRep) {
		this.isShowRep = isShowRep;
	}

	/**
	 * 
	 * @return ϵͳ����
	 */
	public Integer getSystemCode() {
		return systemCode;
	}

	/**
	 * 
	 * @param systemCode
	 *            ϵͳ����
	 */
	public void setSystemCode(Integer systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * 
	 * @return ����Դ
	 */
	public Integer getDataSource() {
		return dataSource;
	}

	/**
	 * 
	 * @param dataSource
	 *            ����Դ
	 */
	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 
	 * @return �Ƿ��ƽ
	 */
	public Integer getIsBalance() {
		return isBalance;
	}

	/**
	 * 
	 * @param isBalance
	 *            �Ƿ��ƽ
	 */
	public void setIsBalance(Integer isBalance) {
		this.isBalance = isBalance;
	}

	/**
	 * 
	 * @return �Ƿ񼯺�
	 */
	public Integer getIsCollect() {
		return isCollect;
	}

	/**
	 * 
	 * @param isCollect
	 *            �Ƿ񼯺�
	 */
	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	/**
	 * 
	 * @return �Ƿ�����
	 */
	public Integer getIsInput() {
		return isInput;
	}

	/**
	 * 
	 * @param isInput
	 *            �Ƿ�����
	 */
	public void setIsInput(Integer isInput) {
		this.isInput = isInput;
	}

	/**
	 * 
	 * @return �Ƿ����
	 */
	public Integer getIsUpdate() {
		return isUpdate;
	}

	/**
	 * 
	 * @param isUpdate
	 *            �Ƿ����
	 */
	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}

	/**
	 * 
	 * @return ����
	 */
	public String getName() {
		return name;
	}

	/**
	 * @struts.validator type="required"
	 * @struts.validator-args arg0resource="reportdefine.reportType.name"
	 * @param name
	 *            ����
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return ��������
	 */
	public Long getPkid() {
		return pkid;
	}

	/**
	 * 
	 * @param pkid
	 *            ��������
	 */
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

}
