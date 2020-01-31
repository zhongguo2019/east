package com.krm.ps.model.reportdefine.web.form;

import org.apache.struts.action.ActionForm;

public class ReportForm extends ActionForm {

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 2223344244566L;

	private Long pkid;

	private String code;

	private String conCode;

	private Long reportType;

	private String reportTypeName;

	private String name;

	private Integer rol;

	private String[] frequencyArray;

	private String frequency;

	private Integer moneyunit;

	private String beginDate;

	private String endDate;

	private String description;

	private Integer isSum;

	private String[] organType;

	private String phyTable;
	private Integer isIncrement;

	/**
	 * <code>ָ���ĵ�ƽ����</code>
	 */
	private String precision;

	public ReportForm() {

	}

	/**
	 * 
	 * @return �����
	 */
	public String getPhyTable() {
		return phyTable;
	}

	/**
	 * 
	 * @param phyTable
	 *            �����
	 */
	public void setPhyTable(String phyTable) {
		this.phyTable = phyTable;
	}

	/**
	 * 
	 * @return Ƶ��
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * 
	 * @param frequency
	 *            Ƶ��
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * 
	 * @return ���Ʊ���
	 */
	public String getConCode() {
		return conCode;
	}

	/**
	 * 
	 * @param conCode
	 *            ���Ʊ���
	 */
	public void setConCode(String conCode) {
		this.conCode = conCode;
	}

	/**
	 * 
	 * @return ������������
	 */
	public String[] getOrganType() {
		return organType;
	}

	/**
	 * 
	 * @param organType
	 *            ������������
	 */
	public void setOrganType(String[] organType) {
		this.organType = organType;
	}

	/**
	 * 
	 * @return ��ʼ����
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * 
	 * @param beginDate
	 *            ��ʼ����
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 
	 * @return ����
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 *            ����
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 
	 * @return ����
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            ����
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return ��������
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 
	 * @param endDate
	 *            ��������
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 
	 * @return Ƶ������
	 */
	public String[] getFrequencyArray() {
		return frequencyArray;
	}

	/**
	 * 
	 * @param frequency
	 *            Ƶ������
	 */
	public void setFrequencyArray(String[] frequency) {
		this.frequencyArray = frequency;
	}

	/**
	 * 
	 * @return ���ҵ�λ
	 */
	public Integer getMoneyunit() {
		return moneyunit;
	}

	/**
	 * 
	 * @param moneyunit
	 *            ���ҵ�λ
	 */
	public void setMoneyunit(Integer moneyunit) {
		this.moneyunit = moneyunit;
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
	 * @struts.validator-args arg0resource="reportdefine.report.name"
	 * @param ����
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

	/**
	 * 
	 * @return ��������
	 */
	public Long getReportType() {
		return reportType;
	}

	/**
	 * 
	 * @param reportType
	 *            ��������
	 */
	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}

	/**
	 * 
	 * @return ������������
	 */
	public String getReportTypeName() {
		return reportTypeName;
	}

	/**
	 * 
	 * @param reportTypeName
	 *            ������������
	 */
	public void setReportTypeName(String reportTypeName) {
		this.reportTypeName = reportTypeName;
	}

	/**
	 * 
	 * @return ��ɫ
	 */
	public Integer getRol() {
		return rol;
	}

	/**
	 * 
	 * @param rol
	 *            ��ɫ
	 */
	public void setRol(Integer rol) {
		this.rol = rol;
	}

	/**
	 * 
	 * @return �Ƿ����
	 */
	public Integer getIsSum() {
		return isSum;
	}

	/**
	 * 
	 * @param isSum
	 *            �Ƿ����
	 */
	public void setIsSum(Integer isSum) {
		this.isSum = isSum;
	}

	/**
	 * 
	 * @return ָ���ĵ�ƽ����
	 */
	public String getPrecision() {
		return precision;
	}

	public Integer getIsIncrement() {
		return isIncrement;
	}

	public void setIsIncrement(Integer isIncrement) {
		this.isIncrement = isIncrement;
	}

	/**
	 * 
	 * @param precision
	 *            ָ���ĵ�ƽ����
	 */
	public void setPrecision(String precision) {
		this.precision = precision;
	}

}
