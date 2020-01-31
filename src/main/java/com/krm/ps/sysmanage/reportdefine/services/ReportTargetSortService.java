package com.krm.ps.sysmanage.reportdefine.services;

import com.krm.ps.framework.common.sort.service.SortService;
import com.krm.ps.sysmanage.reportdefine.dao.ReportDefineDAO;

public interface ReportTargetSortService extends SortService{
	public void setReportDefineDAO(ReportDefineDAO dao);
}
