package com.krm.ps.model.reportview.dao;

import com.krm.ps.framework.dao.DAO;

public interface LogDAO extends DAO {

	public boolean hasLogData(String organId, Long reportId, String dataDate,
			String logType);

}
