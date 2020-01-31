package com.krm.ps.util;

//import java.sql.SQLException;
//import java.sql.Statement;
//
//import javax.naming.InitialContext;
//import javax.sql.DataSource;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.StatementCallback;

public class DBUtil {

//	static final Log log = LogFactory.getLog(DBUtil.class);

//	public synchronized static DataSource getDataSource() {
//		DataSource dataSource = null;
//		try {
//			InitialContext initialcontext = new InitialContext();
//			dataSource = (DataSource) initialcontext
//					.lookup("java:comp/env/jdbc/slsdb");
//		} catch (Exception exception) {
//			log.debug("获取数据源失败");
//		}
//		return dataSource;
//	}

//	/**
//	 * 批量删除
//	 * @param tableName
//	 * @param fieldName
//	 * @param fields
//	 * @return
//	 */
//	public synchronized static boolean batchDelete(String tableName,
//			String fieldName, String[] fields) {
//		if (fields == null)
//			return false;
//
//		JdbcTemplate jt = new JdbcTemplate(getDataSource());
//
//		StringBuffer s = new StringBuffer();
//		s.append("DELETE FROM " + tableName + " WHERE " + fieldName + " in (");
//		for (int i = 0; i < fields.length; i++) {
//			fields[i] = fields[i].trim();
//			if ("".equals(fields[i])) {
//				continue;
//			}
//			s.append(fields[i]);
//			if (i != fields.length - 1) {
//				s.append(",");
//			}
//		}
//		s.append(")");
//		final String sql = s.toString();
//		if (sql.endsWith("()")) {
//			return false;
//		}
//
//		boolean success = true;
//		try {
//			jt.execute(new StatementCallback() {
//				public Object doInStatement(Statement stmt)
//						throws SQLException, DataAccessException {
//					stmt.execute(sql);
//					return null;
//				}
//			});
//		} catch (DataAccessException e) {
//			success = false;
//			// e.printStackTrace();
//		}
//
//		return success;
//	}

}
