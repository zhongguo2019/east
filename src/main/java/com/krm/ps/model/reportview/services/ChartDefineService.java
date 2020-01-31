package com.krm.ps.model.reportview.services;


import com.krm.ps.framework.common.sort.service.SortService;


public interface ChartDefineService extends SortService {
	/**
	 * return if have defined chart related to the format, onlyEnabled used to define if judge the Enabled state.
	 * @param format a format id.
	 * @param onlyEnabled define whether to judge the Enabled state.
	 * @return return whether exists a chart for the format.
	 */
	public boolean haveChart(long format, boolean onlyEnabled);
}
