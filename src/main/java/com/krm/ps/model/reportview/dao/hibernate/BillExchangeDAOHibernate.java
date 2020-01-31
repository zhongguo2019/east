package com.krm.ps.model.reportview.dao.hibernate;

import com.krm.ps.framework.common.dao.ReportDataDAO;
import com.krm.ps.framework.dao.jdbc.BaseDAOJdbc;
import com.krm.ps.model.reportview.dao.BillExchangeDAO;

public class BillExchangeDAOHibernate extends BaseDAOJdbc implements
		BillExchangeDAO {

	ReportDataDAO rdd;

	public void setReportDataDAO(ReportDataDAO obj) {
		this.rdd = obj;
	}

	public void updateBaseData(String organId, String date) {
		date = date.substring(0, 6) + "00";
		String sql = "select count(*) from rep_data where report_date = '"
				+ date + "' and report_id= 403 and organ_id='" + organId + "'";
		Integer result = (Integer) rdd.queryForObject(sql, null, Integer.class);
		if (!new Integer(0).equals(result)) {
			return;
		}
		String phyTableName = "rep_data_".concat(date.substring(4, 6));
		sql = "update " + phyTableName + " set report_date = '" + date
				+ "' where report_id = 403 and organ_id='" + organId + "'";
		update(sql, null);
	}
}
