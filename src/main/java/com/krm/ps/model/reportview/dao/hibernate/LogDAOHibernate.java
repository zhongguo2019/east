package com.krm.ps.model.reportview.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.reportview.dao.LogDAO;
import com.krm.ps.model.sysLog.vo.LogData;
import com.krm.ps.util.SysConfig;

public class LogDAOHibernate extends BaseDAOHibernate implements LogDAO {

	public boolean hasLogData(String organId, Long reportId, String dataDate,
			String logType) {
		String sql = "SELECT {t.*} FROM log_data t"
				+ " WHERE t.cd_organ = ? AND t.id_rep = ?"
				+ " AND t.mk_type = ? AND t.date_data = ?"
				+ " ORDER BY t.cd_organ,t.id_rep";
		List result = list(convertLogDataTableName(dataDate, sql, false),
				new Object[][] { { "t", LogData.class } }, null, new Object[] {
						organId, reportId, logType, dataDate });
		Iterator it = result.iterator();
		if (it.hasNext()) {
			return true;
		} else {
			return false;
		}
	}

	private String convertLogDataTableName(String dataDate, String sql,
			boolean ifInsertLogData) {
		String converOKSql = sql;
		String replaceTableName = "log_data_" + dataDate.substring(4, 6);
		if ("true".equals(SysConfig.IS_DISTRIBUTE_LOG)) {
			if (ifInsertLogData)
				converOKSql = converOKSql.replaceAll("INSERT INTO log_data",
						"INSERT INTO " + replaceTableName);
			else
				converOKSql = converOKSql.replaceAll("log_data",
						replaceTableName);
		}
		return converOKSql;
	}

}