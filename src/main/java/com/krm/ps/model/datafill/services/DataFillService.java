package com.krm.ps.model.datafill.services;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.krm.ps.model.datafill.vo.StatusForm;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.model.vo.Page;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

public interface DataFillService {

	public Long getreportId(Long reportId);

	public Long getreportId(Long reportId, String showlevel);

	public List getMapColumn();

	public List getReportDataAll1(Map<Long, List<DicItem>> dicMap,
			String cstatus, int isadmin, List resultablename, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, int idxid, String levelFlag, String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3, Map mapC);

	public List getReportDataAllMapColumn(Map<Long, List<DicItem>> dicMap,
			String cstatus, int isadmin, List resultablename, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, int idxid, String levelFlag,String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3, Map mapC);

	public List getReportDataAllMapColumn(Map<Long, List<DicItem>> dicMap,
			String cstatus, int isadmin, List resultablename, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, int idxid, String levelFlag, String pkid, Map mapC);

	public List<DicItem> getDicItems(Long dicPid);

	public List<DicItem> getDicvalue(String reportdate, ReportTarget rt);

	public Map<String, String> getRelateTargets(Map<String, Long> relation);

	public Object[] getRelateTarget(String modelid, String targetid,
			Long reportId);

	public int delreport(String pkid, String reportId, String date,
			String organId);

	public int commitDataStatus(StatusForm statusForm);

	public int unLockDataStatus(StatusForm statusForm);

	public Object getreportIdmod(Long reportId);

	public Report getReport(long pkid);

	public HSSFWorkbook getXlsWork(Report report, String organId,
			String dataDate);

	public int XLSInit(FormFile data, String filename, String organId,
			String resultablename) throws Exception;

	public String getModelId(String reportId);
}
