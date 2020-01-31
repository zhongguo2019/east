package com.krm.ps.birt.service.impl;
import java.util.List;

import com.krm.ps.birt.dao.ShowBirtDao;
import com.krm.ps.birt.service.ShowBirtService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
public class ShowBirtServiceImpl implements ShowBirtService {
private ShowBirtDao sbd;
	public ShowBirtDao getSbd() {
	return sbd;
}
public void setSbd(ShowBirtDao sbd) {
	this.sbd = sbd;
}
	@Override
	public List<Report> getReports(Long userId) {
		return sbd.getReports(userId);
	}
	@Override
	public Report getReportById(String reportId) {
		return sbd.getReport(new Long(reportId));
	}

}
