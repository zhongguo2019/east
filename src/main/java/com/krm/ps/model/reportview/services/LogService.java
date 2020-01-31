package com.krm.ps.model.reportview.services;

public interface LogService {

	/**
	 * 是否存在一个数据日志,如果报表ID或者报表类型ID没有值，系统要求传:0
	 * 
	 * @param organId
	 *            机构ID
	 * @param reportId
	 *            报表ID
	 * @param dataDate
	 *            数据日期
	 * @param reportType
	 *            报表类型
	 * @param logType
	 *            日志类型
	 * @return
	 */
	public boolean hasLogData(String organId, Long reportId, String dataDate,
			String logType);

}
