package com.krm.ps.model.reportview.dao;


import com.krm.ps.framework.dao.DAO;


public interface ReportChartDAO extends DAO {
	/**
	 * return the count of defined chart related to the format, onlyEnabled used to define if judge the Enabled state.
	 * @param format a format id.
	 * @param onlyEnabled define whether to judge the Enabled state.
	 * @return return the count.
	 */
	public int getChartCount(long format, boolean onlyEnabled);
	
	
}
