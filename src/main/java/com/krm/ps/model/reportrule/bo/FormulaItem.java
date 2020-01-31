package com.krm.ps.model.reportrule.bo;

public class FormulaItem {

	private String reportId;
	private String itemId;
	private String filedName;
	private String dateExpress;
	private String organId;

	public FormulaItem() {
		super();
	}

	public FormulaItem(String strFormulaItem) {
		String[] arrFormulaItem = strFormulaItem.split("\\.");
		if (arrFormulaItem.length >= 3) {
			for (int i = 0; i < arrFormulaItem.length; i++) {
				switch (i) {
				case 0:
					this.reportId = arrFormulaItem[i];
					break;
				case 1:
					this.itemId = arrFormulaItem[i];
					break;
				case 2:
					this.filedName = arrFormulaItem[i];
					break;
				case 3:
					this.dateExpress = arrFormulaItem[i];
					break;
				case 4:
					this.organId = arrFormulaItem[i];
					break;
				}
			}
		}
	}

	public String getDateExpress() {
		return dateExpress;
	}

	public void setDateExpress(String dateExpress) {
		this.dateExpress = dateExpress;
	}

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getString() throws FormulaItemException {
		StringBuffer sb = new StringBuffer();
		if (reportId != null && !reportId.equals("")) {
			sb.append(reportId);
		} else {
			throw new FormulaItemException("FormulaItem ReportId Null");
		}
		if (itemId != null && !itemId.equals("")) {
			sb.append(".");
			sb.append(itemId);
		} else {
			throw new FormulaItemException("FormulaItem ItemId Null");
		}
		if (filedName != null && !filedName.equals("")) {
			sb.append(".");
			sb.append(filedName);
		} else {
			throw new FormulaItemException("FormulaItem FiledName Null");
		}
		if (dateExpress != null && !dateExpress.equals("")) {
			sb.append(".");
			sb.append(dateExpress);
		}
		if (organId != null && !organId.equals("")) {
			sb.append(".");
			sb.append(organId);
		}
		return sb.toString();
	}
}
