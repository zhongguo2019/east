package com.krm.ps.model.reportview.services.impl;

import com.krm.ps.model.reportview.dao.LogDAO;
import com.krm.ps.model.reportview.services.LogService;

public class LogServiceImpl implements LogService {

	private LogDAO logDAO;

	public LogDAO getLogDAO() {
		return logDAO;
	}

	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}

	@Override
	public boolean hasLogData(String organId, Long reportId, String dataDate,
			String logType) {
		return logDAO.hasLogData(organId, reportId, dataDate, logType);
	}

}
