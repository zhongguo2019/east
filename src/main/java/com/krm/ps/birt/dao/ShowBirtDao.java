package com.krm.ps.birt.dao;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.vo.Report;

public interface ShowBirtDao {
public List<Report> getReports(Long userId);
public Report getReport(Long reportId);
}
