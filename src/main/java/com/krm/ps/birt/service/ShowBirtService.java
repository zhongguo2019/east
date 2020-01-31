package com.krm.ps.birt.service;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.vo.Report;

public interface ShowBirtService {
public List<Report> getReports(Long userId);
public Report getReportById(String reportId);
}
