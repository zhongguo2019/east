package com.krm.ps.model.reportdefine.services.impl;

import com.krm.ps.model.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.model.reportdefine.services.ReportItemSortService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportItem;

public class ReportItemSortServiceImpl implements ReportItemSortService {

	private ReportDefineDAO rdreportDefineDAO;

	public ReportDefineDAO getRdreportDefineDAO() {
		return rdreportDefineDAO;
	}

	public void setRdreportDefineDAO(ReportDefineDAO rdreportDefineDAO) {
		this.rdreportDefineDAO = rdreportDefineDAO;
	}

	public void sort(String list) {
		if (null != list) {
			ReportItem item = null;
			String[] orders = list.split(",");
			for (int i = 0; i < orders.length; i++) {
				Object o = rdreportDefineDAO.getObject(ReportItem.class,
						new Long(orders[i]));
				if (null != o) {
					item = (ReportItem) o;
					item.setItemOrder(new Integer(i));
					rdreportDefineDAO.saveObject(item);
				}
			}
		}
	}

	@Override
	public void setReportDefineDAO(ReportDefineDAO dao) {

	}

}
