package com.krm.ps.model.datafill.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.krm.ps.model.datafill.bo.FormulaHelp;

/**
 * 公式DAO的JDBC支持类
 * 
 * @author chm
 * 
 */
public class FormulaDAOJDBCSupport {

	static class ReportInfoRowCallbackHandler implements RowCallbackHandler {
		List reportInfoList;

		public ReportInfoRowCallbackHandler(List reportInfoList) {
			this.reportInfoList = reportInfoList;
		}

		public void processRow(ResultSet rs) throws SQLException {
			int prec = -1;
			try {
				prec = Integer.parseInt(rs.getString("precision"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			reportInfoList.add(new FormulaHelp.ReportInfo(rs.getInt("pkid"), rs
					.getInt("report_type"), rs.getString("name"), rs
					.getInt("rol"), rs.getString("phy_table"), rs
					.getString("frequency"), prec));
		}
	}

}
