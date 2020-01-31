package com.krm.ps.model.datafill.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.krm.ps.framework.dao.jdbc.BaseDAOJdbc;
import com.krm.ps.model.datafill.bo.FormulaHelp;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

/**
 * 报表数据DAO的JDBC实现
 * 
 * @author chm
 * 
 */

public class FormulaDAOJDBCImpl extends BaseDAOJdbc implements FormulaDAO {

	private static Properties sepTableProTemp = null;

	public ReportConfigDAO rcd = null;

	public void setReportConfigDAO(ReportConfigDAO obj) {
		rcd = obj;
	}

	public ReportConfigDAO getRcd() {
		return rcd;
	}

	/**
	 * 获取临时表物理表名称
	 * 
	 * @param reportID
	 *            报表编码
	 * @param date
	 *            日期
	 * @return String
	 */
	public String getPhyTableNameByTemp(String reportID, String date) {
		String phyTableName = FormulaHelp.getPhyTable(Integer
				.parseInt(reportID));
		if (sepTableProTemp == null) {
			initSepTableProForTemp();
		}
		// 获取分表号
		String sepTableNo = sepTableProTemp.getProperty(reportID);
		if (sepTableNo == null || "".equals(sepTableNo)) {
			// 默认的分表号
			sepTableNo = "0";
		}
		if (phyTableName.startsWith("rep_dataf")) {
			// phyTableName = getFlowRepTableName(reportID,date);
			phyTableName = "rep_dataf_" + date.substring(4, 6) + "_"
					+ sepTableNo;
		} else if (phyTableName.startsWith("rep_datam")) {
			// 采集层数据表名生成
			// 获取分表号
			phyTableName = "rep_datam_" + date.substring(4, 6) + "_"
					+ sepTableNo;
		}
		return phyTableName;
	}

	/**
	 * 初始化临时表分表配置信息
	 */
	private void initSepTableProForTemp() {
		sepTableProTemp = new Properties();
		// 根据分表功能编码获取报表配置信息
		List repCfgList = rcd.getReportConfigsByFunc(new Long(34));
		if (repCfgList == null) {
			return;
		}
		ReportConfig rc = null;
		for (int i = 0; i < repCfgList.size(); i++) {
			rc = (ReportConfig) repCfgList.get(i);
			sepTableProTemp.put(rc.getReportId().toString(), rc.getDefChar());
		}
	}

	public List getReportInfo() {
		List reportInfoList = new ArrayList();
		getJdbcTemplate()
				.query("SELECT pkid,report_type,name,rol,phy_table,frequency,precision FROM code_rep_report",
						new FormulaDAOJDBCSupport.ReportInfoRowCallbackHandler(
								reportInfoList));
		return reportInfoList;
	}
}
