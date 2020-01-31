package com.krm.ps.model.datafill.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

public interface ReportDefineDAO extends DAO
{
	
	public List getReports(String date, Long userid);

	
	public List<ReportTarget> getReportTargets(Long reportId);
	
	public List getReportTargetsBySRC(String tableName);

	
	public List getReportOrgTypes(Long reportId);
	
	public List getTemplates(Long reportid);
    
	/**
	 * 将要需要迁移到的数据 gaozhognbo
	 */
	public void reportSeleMove(String status,String reportid);

} 
