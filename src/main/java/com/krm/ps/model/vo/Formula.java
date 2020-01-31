package com.krm.ps.model.vo;

import java.io.Serializable;

import com.krm.ps.framework.vo.BaseObject;

/**
 * @hibernate.class table="code_formula"
 */
public class Formula extends BaseObject implements Serializable {

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 6154281696601728148L;

	private Long pkId;

	private Long reportId;

	private String itemId;

	private String fieldName;

	private String formulaName;

	private String explain;

	private String formula;

	private String formulaType;

	private Integer formulaNum;

	private Integer sectId;

	private String beginDate;

	private String endDate;

	private String createDate;

	private String editDate;

	private String frequency;

	private String reportName;

	private String itemName;

	private String targetName;

	private String sign;
	
	private Integer repCreateId;
	
	private String [] AttrName = new String []{"起始日期","终止日期","科目号","公式名称","公式解释","公式","公式类型","公式频度","符号"};
	private String [] compareAttr = new String []{"BeginDate","EndDate","ItemId","FormulaName","Explain","Formula","FormulaType","Frequency","Sign"};
	
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @hibernate.property column="begin_date"
	 */
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
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

	/**
	 * @hibernate.property column="end_date"
	 */
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @hibernate.property column="item_id"
	 */
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @hibernate.property column="name"
	 */
	public String getFormulaName() {
		return formulaName;
	}

	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}

	/**
	 * @hibernate.property column="formula_num"
	 */
	public Integer getFormulaNum() {
		return formulaNum;
	}

	public void setFormulaNum(Integer formulaNum) {
		this.formulaNum = formulaNum;
	}

	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_formula_pkid_seq"
	 */
	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	/**
	 * @hibernate.property column="report_id"
	 */
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	/**
	 * @hibernate.property type="org.springframework.orm.hibernate3.support.ClobStringType" column="explain"
	 */
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	/**
	 * @hibernate.property column="formula"
	 */
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	/**
	 * @hibernate.property column="formula_type"
	 */
	public String getFormulaType() {
		return formulaType;
	}

	public void setFormulaType(String formulaType) {
		this.formulaType = formulaType;
	}

	/**
	 * @hibernate.property column="sect_id"
	 */
	public Integer getSectId() {
		return sectId;
	}

	public void setSectId(Integer sectId) {
		this.sectId = sectId;
	}

	/**
	 * @hibernate.property column="field_name"
	 */
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String toString() {
		return pkId.toString();
	}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}

	/**
	 * @hibernate.property column="frequency"
	 */
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getLeftExp() {
		return reportId + "." + itemId + "." + fieldName;
	}

	public String getFormulaExp() {
		return getLeftExp() + " " + sign + " " + formula;
	}

	/**
	 * @hibernate.property column="sign"
	 */
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getMainType() {
		if(formulaType!=null) {
			return formulaType.substring(0,1);
		}
		return "";
	}

	public Integer getRepCreateId()
	{
		return repCreateId;
	}

	public void setRepCreateId(Integer repCreateId)
	{
		this.repCreateId = repCreateId;
	}
	
	public String[] getAttrName()
	{
		return AttrName;
	}
	
	public String[] getCompareAttr()
	{
		return compareAttr;
	}



}
