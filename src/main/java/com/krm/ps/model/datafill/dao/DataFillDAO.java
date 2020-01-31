package com.krm.ps.model.datafill.dao;

import java.util.List;
import java.util.Map;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.model.vo.Page;
import com.krm.ps.model.vo.PaginatedListHelper;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.model.datafill.vo.StatusForm;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company: KRM
 * </p>
 * 
 * @author
 */
public interface DataFillDAO extends DAO {

	public List getMapColumn();

	public Report getReport(Long reportId);

	public Long getReportByid(Report report);

	public Long getReportByid(Report report, String showlevel);


	public PaginatedListHelper editReportDataByPageMapColumn(String cstatus,
			int isadmin, List resultablename, Long reportId, String organId,
			String reportDate, Page page, int idxid, String levelFlag,
			String pkid, Map mapC);

	public PaginatedListHelper getReportDataByPageAll1(String cstatus,
			int isadmin, List resultablename, Long reportId, String organId,
			String reportDate, Page page, int idxid, String levelFlag,String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3, Map mapC);

	public PaginatedListHelper getReportDataByPageAllMapColumn(String cstatus,
			int isadmin, List resultablename, Long reportId, String organId,
			String reportDate, Page page, int idxid, String levelFlag,
			String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3, Map mapC);

	public List<DicItem> getDicByPid(Long dicPid);

	public List<DicItem> getDicvalue(String reportdate, ReportTarget rt);

	public List getRelateTargets(Map<String, Long> relation);

	public Object[] getRelateTarget(String modelid, String targetid,
			Long reportId);
	
	public int delreport(String pkid,String reportId, String date,String organId);
	/**
	 * 
	 * @param statusForm
	 * @return
	 */
	public int getDataStatus(StatusForm statusForm);
	/**
	 * 
	 * @param statusForm
	 * @return
	 */
	public int unLockDataStatus(StatusForm statusForm, String isLock);
	/**
	 * 
	 * @param statusForm
	 * @return
	 */
	public int commitDataStatus(StatusForm statusForm);

	public Long getReportByModid(Report report);

	public List getReportOrgTypes(Long reportId);

	public List<ReportTarget> getReportTargets(Long reportId);

	public List<ReportTarget> getReportTargetsBySRC(String tableName);

	public int insertDataF( String sql);

	public List<?> getdefChar(Long reportid, Long funid);

	public List getReportRuleByReportId(String reportid);

	public int deleteReportResult(String  rulecode ,String cstatus ,String tablename);

	public Object[] checkData(String rulecode,String date);

	public String getModelId(String reportId);

}
