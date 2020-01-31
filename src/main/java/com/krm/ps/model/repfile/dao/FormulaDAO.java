package com.krm.ps.model.repfile.dao;

import java.util.List;

public interface FormulaDAO {

	/**
	 * 获取物理表名
	 * 
	 * @param reportID
	 * @param date
	 * @return
	 */
	String getPhyTableName(String reportID, String date);

	List getReportInfo();

}
