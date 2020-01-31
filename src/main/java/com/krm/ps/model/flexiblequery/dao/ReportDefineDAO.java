package com.krm.ps.model.flexiblequery.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.reportdefine.vo.ReportOrgType;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

public interface ReportDefineDAO extends DAO {

	/**
	 * 根据报表id得到报表列信息
	 * 
	 * @param reportId
	 *            报表id
	 * @return {@link ReportTarget)对象列表
	 */
	public List<ReportTarget> getReportTargets(Long reportId);

	public List getReportTargetsBySRC(String tableName);

	/**
	 * 根据报表ID得到报表中的机构类型列表
	 * 
	 * @param reportId
	 *            报表id
	 * @return {@link ReportOrgType}对象列表
	 */
	public List getReportOrgTypes(Long reportId);

}
