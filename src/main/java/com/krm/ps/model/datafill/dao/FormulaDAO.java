package com.krm.ps.model.datafill.dao;

import java.util.List;

public interface FormulaDAO {

	public String getPhyTableNameByTemp(String reportID, String date);

	public List getReportInfo();
}
