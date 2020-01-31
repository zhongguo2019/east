package com.krm.ps.model.datafill.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public interface ReportConfigDAO extends DAO{
	
	
	public List<ReportConfig> getdefCharByTem(Long reportid, Long funid);
	
	/**
	 * 根据功能编码获取报表配置信息
	 * @param funcId 功能编码
	 * @return List
	 */
	public List getReportConfigsByFunc(Long funcId);
	
}
