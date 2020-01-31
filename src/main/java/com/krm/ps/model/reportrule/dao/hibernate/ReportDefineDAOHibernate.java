package com.krm.ps.model.reportrule.dao.hibernate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.krm.common.logger.KRMLogger;
import com.krm.common.logger.KRMLoggerUtil;
import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.reportrule.dao.ReportDefineDAO;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.util.Constants;
import com.krm.ps.util.FuncConfig;

public class ReportDefineDAOHibernate extends BaseDAOHibernate implements
		ReportDefineDAO {

	KRMLogger logger = KRMLoggerUtil.getLogger(ReportDefineDAOHibernate.class);

	@Override
	public List<ReportTarget> getReportTargetsBySRC(String tableName) {
		List<ReportTarget> result = new ArrayList<ReportTarget>();
		Connection conn = null;
		ResultSet rs = null;
		String driverclass = FuncConfig
				.getProperty("jdbc.connection.driver_class");
		String url = FuncConfig.getProperty("jdbc.connection.url");
		String username = FuncConfig.getProperty("jdbc.connection.username");
		String password = FuncConfig.getProperty("jdbc.connection.password");
		String schema = FuncConfig.getProperty("jdbc.connection.schema");
		String DBType = FuncConfig.getProperty("jdbc.database.type");
		try {
			Class.forName(driverclass);
			Properties props = new Properties();
			if (DBType.toUpperCase().equals("ORACLE")) {
				props.put("remarksReporting", "true");
			}
			props.put("user", username);
			props.put("password", password);
			conn = DriverManager.getConnection(url, props);
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getColumns(null, schema, tableName, "%");
			while (rs.next()) {
				ReportTarget rt = new ReportTarget();
				String columnName = rs.getString("COLUMN_NAME");
				String columnCNName = rs.getString("REMARKS");
				rt.setTargetField(columnName);
				if (columnCNName != null && !columnCNName.equals("")) {
					rt.setTargetName(columnCNName);
				} else {
					rt.setTargetName(columnName);
				}
				rt.setRulesize(rs.getString("COLUMN_SIZE"));
				String temp = rs.getString("TYPE_NAME");
				if (temp.equals("VARCHAR") || temp.equals("VARCHAR2")) {
					rt.setDataType(3);
				} else {
					rt.setDataType(1);
				}
				rt.setStatus(0);
				result.add(rt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("错误信息:", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("错误信息:", e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("错误信息:", e);
				}
			}
		}
		return result;
	}

	public List<ReportTarget> getReportTargets(Long reportId) {
		String hql = "from ReportTarget t where t.status <> '"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " order by t.targetOrder";
		Map map = new HashMap();
		return list(hql, map);
	}

	public List getReportOrgTypes(Long reportId) {
		String hql = "from ReportOrgType t where t.reportId = " + reportId;
		return list(hql);
	}

	public List getReportItems(Long reportId) {
		String hql = "";
		if (reportId.longValue() != 0) {
			hql = "from ReportItem t where t.status<>'" + Constants.STATUS_DEL
					+ "' and t.reportId = " + reportId
					+ " order by t.itemOrder,t.pkid";
		} else {
			hql = "select i from ReportItem i,Report r,ReportType t where i.status<>'9' and r.status<>9 and t.status<>'9' and i.reportId=r.pkid and r.reportType=t.pkid and r.pkid="
					+ reportId + " order by i.itemOrder";
		}
		Map map = new HashMap();
		return list(hql, map);
	}

	public List getReportTargetsStock(Long reportId, String stocktype) {
		String hql = "from ReportTarget t where t.status <> '"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " and t.stocktype='" + stocktype
				+ "' order by t.targetOrder,t.pkid";
		Map map = new HashMap();
		return list(hql, map);
	}
}
