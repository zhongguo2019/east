package com.krm.ps.model.reportrule.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.krm.ps.model.vo.Formula;

/**
 * 
 * 类名称: ReportTable
 * <p>
 * 功能: 实现对公式定义界面中要显示的完整表格内容的封装
 * <p>
 * 创建时间: 2006.4.27
 * <p>
 * 修改时间 修改者 修改的方法 修改的原因
 * 
 * <p>
 * Copyright: Copyright(c) 2006
 * </p>
 * 
 * <p>
 * Company: krmsoft.com
 * </p>
 * 
 */
public class ReportTable implements java.io.Serializable {

	private static final long serialVersionUID = 2243322L;

	private String[][] items;// 存放科目编码和名字

	private String[][] targets;// 存放指标列的字段和名字

	private String reportId;// 存放报表的编码

	private String reportName;// 存放报表的名称

	private String itemIdName;

	private String targetIdName;

	private List buildFormula = new ArrayList();

	private List checkFormula = new ArrayList();

	private List auditFormula = new ArrayList();

	private Map formulaMap = new HashMap();

	public ReportTable(String reportId, String reportName, String[][] items,
			String[][] targets) {
		this.reportId = reportId;
		this.items = items;
		this.targets = targets;
		this.reportName = reportName;
	}

	/**
	 * 得到报表科目数量
	 * 
	 * @return
	 */
	public int getReportItemNum() {
		return items.length;
	}

	/**
	 * 得到报表列的数量
	 * 
	 * @return
	 */
	public int getReportTargetNum() {
		return targets.length;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String[][] getItems() {
		return items;
	}

	public void setItems(String[][] items) {
		this.items = items;
	}

	public String[][] getTargets() {
		return targets;
	}

	public void setTargets(String[][] targets) {
		this.targets = targets;
	}

	public String[] getReportItemsName() {
		return getReportRowColName(items);
	}

	public String[] getReportTargetsName() {
		return getReportRowColName(targets);
	}

	public void setFormulaList(List formulaList) {
		if (formulaList == null || formulaList.size() == 0) {
			return;
		}
		Iterator it = formulaList.iterator();
		while (it.hasNext()) {
			Formula f = (Formula) it.next();
			String mtype = f.getFormulaType().substring(0, 1);
			formulaMap.put(f.getPkId(), f);
			if ("1".equals(mtype)) {
				buildFormula.add(f);
			} else if ("2".equals(mtype)) {
				checkFormula.add(f);
			} else if ("3".equals(mtype)) {
				auditFormula.add(f);
			}
		}
	}

	public void addNewFormula(Formula f) {
		formulaMap.put(f.getPkId(), f);
		String mtype = f.getFormulaType().substring(0, 1);
		if ("1".equals(mtype)) {
			buildFormula.add(f);
		} else if ("2".equals(mtype)) {
			checkFormula.add(f);
		} else if ("3".equals(mtype)) {
			auditFormula.add(f);
		}
	}

	public void updateFormula(Formula f) {
		// System.out.println(f.getReportId()+"."+f.getItemId()+"."+f.getFieldName());///
		formulaMap.put(f.getPkId(), f);
		String mtype = f.getFormulaType().substring(0, 1);
		if ("1".equals(mtype)) {
			replace(buildFormula, f);
		} else if ("2".equals(mtype)) {
			replace(checkFormula, f);
		} else if ("3".equals(mtype)) {
			replace(auditFormula, f);
		}
	}

	private void replace(List fl, Formula replacement) {
		for (int i = 0; i < fl.size(); i++) {
			Formula f = (Formula) fl.get(i);
			if (f.getPkId().equals(replacement.getPkId())) {
				fl.set(i, replacement);
				return;
			}
		}
	}

	public void removeFormula(Long formulaId) {
		Formula f = (Formula) formulaMap.remove(formulaId);
		if (f == null) {
			return;
		}
		String mtype = f.getFormulaType().substring(0, 1);
		if ("1".equals(mtype)) {
			remove(buildFormula, formulaId);
		} else if ("2".equals(mtype)) {
			remove(checkFormula, formulaId);
		} else if ("3".equals(mtype)) {
			remove(auditFormula, formulaId);
		}
	}

	private void remove(List fl, Long formulaId) {
		for (int i = 0; i < fl.size(); i++) {
			Formula f = (Formula) fl.get(i);
			if (f.getPkId().equals(formulaId)) {
				fl.remove(i);
				return;
			}
		}
	}

	public Formula getFormula(Long id) {
		return (Formula) formulaMap.get(id);
	}

	public List getAuditFormula() {
		return auditFormula;
	}

	public void setAuditFormula(List auditFormula) {
		this.auditFormula = auditFormula;
	}

	public List getBuildFormula() {
		return buildFormula;
	}

	public void setBuildFormula(List buildFormula) {
		this.buildFormula = buildFormula;
	}

	public List getCheckFormula() {
		return checkFormula;
	}

	public void setCheckFormula(List checkFormula) {
		this.checkFormula = checkFormula;
	}

	private String[] getReportRowColName(String[][] itemTargets) {
		String strItemTargetsName = "";

		for (int i = 0; i < itemTargets.length; i++) {
			String[] arrItemTarget = (String[]) itemTargets[i];
			if (arrItemTarget != null && arrItemTarget.length == 2) {
				strItemTargetsName = strItemTargetsName + arrItemTarget[1];
			}
		}
		if (strItemTargetsName.endsWith("")) {
			return null;
		}
		return strItemTargetsName.split(",");
	}

	public String getCellGridFormulaItem(int row, int col) {
		FormulaItem formulaItem = new FormulaItem();
		formulaItem.setReportId(this.reportId);
		formulaItem.setItemId(items[row][0]);
		formulaItem.setFiledName(targets[col][0]);
		try {
			return formulaItem.getString();
		} catch (FormulaItemException fie) {
			fie.printStackTrace();
			return "";
		}
	}

	public String getItemIdName() {
		if (itemIdName == null) {
			StringBuffer iin = new StringBuffer();
			for (int i = 0; i < items.length; i++) {
				if (i > 0) {
					iin.append(",");
				}
				iin.append(items[i][0]);
				iin.append(":");
				iin.append(items[i][1]);
			}
			itemIdName = iin.toString();
		}
		return itemIdName;
	}

	public String getTargetIdName() {
		if (targetIdName == null) {
			StringBuffer tin = new StringBuffer();
			for (int i = 0; i < targets.length; i++) {
				if (i > 0) {
					tin.append(",");
				}
				tin.append(targets[i][0]);
				tin.append(":");
				tin.append(targets[i][1]);
			}
			targetIdName = tin.toString();
		}
		return targetIdName;
	}

	public List getFormula(String formulaType) {
		String mtype = formulaType.substring(0, 1);
		if ("1".equals(mtype)) {
			return buildFormula;
		} else if ("2".equals(mtype)) {
			return checkFormula;
		}
		if ("3".equals(mtype)) {
			return auditFormula;
		}
		return null;
	}

	public List getFilterFormula(String formulaType, String filterType) {
		String mtype = formulaType.substring(0, 1);
		List filterList = new ArrayList();
		if ("1".equals(mtype)) {
			for (Iterator it = buildFormula.iterator(); it.hasNext();) {
				Formula f = (Formula) it.next();
				if (filterType.equals("$")) {
					if (f.getFormulaType() != null
							&& f.getFormulaType().endsWith("$"))
						filterList.add(f);
				} else if (filterType.equals("1")) {
					if (f.getFormulaType() != null
							&& f.getFormulaType().length() > 1
							&& f.getFormulaType().substring(1, 2).equals("1"))
						filterList.add(f);
				} else if (filterType.equals("2")) {
					if (f.getFormulaType() != null
							&& f.getFormulaType().length() > 1
							&& f.getFormulaType().substring(1, 2).equals("2"))
						filterList.add(f);
				} else if (filterType.equals("3")) {
					if (f.getFormulaType() != null
							&& f.getFormulaType().length() > 1
							&& f.getFormulaType().substring(1, 2).equals("3"))
						filterList.add(f);
				}
			}
		} else if ("2".equals(mtype)) {
			for (Iterator it = checkFormula.iterator(); it.hasNext();) {
				Formula f = (Formula) it.next();
				if (filterType.equals("$")) {
					if (f.getFormulaType() != null
							&& f.getFormulaType().endsWith("$"))
						filterList.add(f);
				}
			}
		}
		if ("3".equals(mtype)) {
			for (Iterator it = auditFormula.iterator(); it.hasNext();) {
				Formula f = (Formula) it.next();
				if (filterType.equals("$")) {
					if (f.getFormulaType() != null
							&& f.getFormulaType().endsWith("$"))
						filterList.add(f);
				} else if (filterType.equals("2")) {
					if (f.getFormulaType() != null
							&& f.getFormulaType().length() > 1
							&& f.getFormulaType().substring(1, 2).equals("2"))
						filterList.add(f);
				} else if (filterType.equals("3")) {
					if (f.getFormulaType() != null
							&& f.getFormulaType().length() > 1
							&& f.getFormulaType().substring(1, 2).equals("3"))
						filterList.add(f);
				}
			}
		}
		return filterList;
	}

	public StringBuffer getFormulaIdLeftExp(String formulaType) {
		StringBuffer s = new StringBuffer();
		List fl = getFormula(formulaType);
		if (fl == null || fl.size() == 0) {
			return s;
		}
		Iterator it = fl.iterator();
		for (int c = 0; it.hasNext();) {
			if (c++ > 0) {
				s.append(",");
			}
			Formula f = (Formula) it.next();
			s.append(f.getPkId());
			s.append(":");
			s.append(f.getReportId() + "." + f.getItemId() + "."
					+ f.getFieldName());
			// 9-21
			s.append(":");
			s.append(f.getFormulaType());
		}
		return s;
	}

}
