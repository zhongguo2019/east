package com.krm.ps.model.reportdefine.services;

import com.krm.ps.framework.common.sort.service.SortService;
import com.krm.ps.model.reportdefine.dao.ReportDefineDAO;

public interface ReportItemSortService extends SortService {
	public void setReportDefineDAO(ReportDefineDAO dao);
}
