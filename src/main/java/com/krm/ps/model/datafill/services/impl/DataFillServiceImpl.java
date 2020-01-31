package com.krm.ps.model.datafill.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts.upload.FormFile;
import org.jfree.util.Log;

import com.krm.ps.model.datafill.bo.XLSBuild;
import com.krm.ps.model.datafill.dao.DataFillDAO;
import com.krm.ps.model.datafill.services.DataFillService;
import com.krm.ps.model.datafill.vo.StatusForm;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.model.vo.Page;
import com.krm.ps.model.vo.PaginatedListHelper;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.model.xlsinit.vo.DataSet;
import com.krm.ps.model.xlsinit.vo.RowSet;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportOrgType;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.util.FuncConfig;

public class DataFillServiceImpl implements DataFillService {
	private DataFillDAO datafillDataFillDAO;

	public DataFillDAO getDatafillDataFillDAO() {
		return datafillDataFillDAO;
	}

	public void setDatafillDataFillDAO(DataFillDAO datafillDataFillDAO) {
		this.datafillDataFillDAO = datafillDataFillDAO;
	}

	@Override
	public Long getreportId(Long reportId) {
		Report report = datafillDataFillDAO.getReport(reportId);
		Long reportpkid = null;
		if (report != null) {
			reportpkid = datafillDataFillDAO.getReportByid(report);
		}

		return reportpkid;
	}

	@Override
	public Long getreportId(Long reportId, String showlevel) {
		Report report = datafillDataFillDAO.getReport(reportId);
		Long reportpkid = null;
		if (report != null) {
			reportpkid = datafillDataFillDAO.getReportByid(report, showlevel);
		}

		return reportpkid;
	}

	@Override
	public List getMapColumn() {
		return datafillDataFillDAO.getMapColumn();
	}

	public List getReportDataAll1(Map<Long, List<DicItem>> dicMap,
			String cstatus, int isadmin, List resultablename, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, int idxid, String levelFlag,String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3, Map mapC) {
		PaginatedListHelper plh = datafillDataFillDAO.getReportDataByPageAll1(
				cstatus, isadmin, resultablename, reportId, organId,
				reportDate, page, idxid, levelFlag,  zhi1,targetField1, zhi2,targetField2, zhi3,targetField3, mapC);
		List list = buildDataAll(dicMap, reportId, organId, reportDate,
				reportTargetList, page, plh, levelFlag, cstatus);
		return list;
	}

	public List getReportDataAllMapColumn(Map<Long, List<DicItem>> dicMap,
			String cstatus, int isadmin, List resultablename, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, int idxid, String levelFlag,String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3, Map mapC) {
		PaginatedListHelper plh = datafillDataFillDAO
				.getReportDataByPageAllMapColumn(cstatus, isadmin,
						resultablename, reportId, organId, reportDate, page,
						idxid, levelFlag, zhi1, targetField1, zhi2, targetField2, zhi3, targetField3, mapC);
		List list = buildDataAll(dicMap, reportId, organId, reportDate,
				reportTargetList, page, plh, levelFlag, cstatus);
		return list;
	}

	public List getReportDataAllMapColumn(Map<Long, List<DicItem>> dicMap,
			String cstatus, int isadmin, List resultablename, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, int idxid, String levelFlag, String pkid, Map mapC) {
		PaginatedListHelper plh = datafillDataFillDAO
				.editReportDataByPageMapColumn(cstatus, isadmin,
						resultablename, reportId, organId, reportDate, page,
						idxid, levelFlag, pkid, mapC);
		List list = buildDataAll(dicMap, reportId, organId, reportDate,
				reportTargetList, page, plh, levelFlag, cstatus);
		return list;
	}

	@Override
	public List<DicItem> getDicItems(Long dicPid) {
		return datafillDataFillDAO.getDicByPid(dicPid);
	}

	public List<DicItem> getDicvalue(String reportdate, ReportTarget rt) {
		return datafillDataFillDAO.getDicvalue(reportdate, rt);
	}

	@Override
	public Map<String, String> getRelateTargets(Map<String, Long> relation) {
		List list = datafillDataFillDAO.getRelateTargets(relation);
		Iterator iterator = list.iterator();
		Map<String, String> targetMap = new HashMap<String, String>();
		while (iterator.hasNext()) {
			Object[] arr = (Object[]) iterator.next();
			targetMap.put(arr[1] + "_" + arr[3], arr[0] + "_" + arr[2]);
		}
		return targetMap;
	}

	@Override
	public Object[] getRelateTarget(String modelid, String targetid,
			Long reportId) {
		Object[] o = datafillDataFillDAO.getRelateTarget(modelid, targetid,
				reportId);
		return o;
	}

	private List buildDataAll(Map<Long, List<DicItem>> dicMap, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, PaginatedListHelper plh, String levelFlag, String cstatus) {
		List list1 = new ArrayList<Map>();
		Map pkidMap = new HashMap();
		Map repDataMap = new HashMap();
		List list = plh.getList();
		if (list.size() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				Map modelMap = (Map) it.next();
				pkidMap.put(modelMap.get("pkid"), modelMap.get("item_id"));
				for (Iterator it2 = reportTargetList.iterator(); it2.hasNext();) {

					try {
						ReportTarget reportTarget = (ReportTarget) it2.next();
						String strDataKey = modelMap.get("pkid") + "_"
								+ reportTarget.getTargetField();
						Object value = modelMap.get(reportTarget
								.getTargetField().toLowerCase());
						List<DicItem> dicItems = null;
						if (value != null && !("null".equals(value))) {

							if (dicMap != null
									&& "2".equals(levelFlag)
									|| (cstatus != null && cstatus.length() < 4)) {
								dicItems = dicMap.get(reportTarget.getPkid());
								if (dicItems != null) {
									for (DicItem di : dicItems) {
										if (di.getDicId().equals(
												value.toString())) {
											repDataMap.put(strDataKey,
													di.getDicName());
										}
									}
								} else {
									repDataMap
											.put(strDataKey, value.toString());
								}
							} else {
								repDataMap.put(strDataKey, value.toString());
							}

						} else {
							repDataMap.put(strDataKey, "");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		list1.add(repDataMap);
		list1.add(plh);
		list1.add(pkidMap);
		list1.add(plh.getSortCriterion());
		return list1;
	}

	@Override
	public int delreport(String pkid, String reportId, String date,
			String organId) {

		String rid = getreportId(new Long(reportId), "1").toString();

		return datafillDataFillDAO.delreport(pkid, rid, date, organId);
	}

	public int commitDataStatus(StatusForm statusForm) {
		int result;
		if (datafillDataFillDAO.getDataStatus(statusForm) > 0) {
			result = datafillDataFillDAO.unLockDataStatus(statusForm, "1");
		} else {
			result = datafillDataFillDAO.commitDataStatus(statusForm);
		}
		return result;
	}

	public int unLockDataStatus(StatusForm statusForm) {
		int result = datafillDataFillDAO.unLockDataStatus(statusForm, "0");
		return result;
	}

	@Override
	public Object getreportIdmod(Long reportId) {
		Report report = datafillDataFillDAO.getReport(reportId);
		Long reportpkid = null;
		if (report != null) {
			reportpkid = datafillDataFillDAO.getReportByModid(report);
		}

		return reportpkid;
	}

	@Override
	public Report getReport(long pkid) {
		Object o = datafillDataFillDAO.getObject(Report.class, pkid);
		Report report = null;
		if (null != o) {
			report = (Report) o;
			List organTypes = datafillDataFillDAO.getReportOrgTypes(report
					.getPkid());
			int length = organTypes.size();
			String[] types = new String[length];
			for (int i = 0; i < length; i++) {
				types[i] = ((ReportOrgType) organTypes.get(i)).getOrganType()
						.toString();
			}
			report.setOrganType(types);
			return report;
		}
		return null;
	}

	public HSSFWorkbook getXlsWork(Report report, String organId,
			String dataDate) {
		List reportTargets = getReportTargets(report.getPkid());
		HSSFWorkbook wd = new HSSFWorkbook();
		HSSFSheet sheet = wd.createSheet(report.getName());
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for (int i = 0; i < reportTargets.size(); i++) {
			ReportTarget rt = (ReportTarget) reportTargets.get(i);
				cell = row.createCell(i);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(rt.getTargetName());
			
		}
		return wd;
	}

	public List getReportTargets(Long reportId) {
		return getReportTargets(reportId, null);
	}

	public List<ReportTarget> getReportTargets(Long reportId, String tableName) {
		List<ReportTarget> result = datafillDataFillDAO
				.getReportTargets(reportId);
		List<ReportTarget> result2=new ArrayList<ReportTarget>(); 
		String qhdelcjrq = FuncConfig.getProperty("qh.delcjrq", "1");
		if ("yes".equals(qhdelcjrq)) {  // #青海→数据补录→导入数据模板去掉 cjrq（采集日期）字段
			for (int i = 0; i < result.size(); i++) {
				ReportTarget temp = result.get(i);
				if (temp.getTargetField().trim().equals("CJRQ")) {
					continue;
				}
				result2.add(temp);
			}
		}else{
			if (tableName == null) {
				return result;
			}
		}
		if (tableName == null) {
			return result2;
		}
		List<ReportTarget> reportTarget = datafillDataFillDAO
				.getReportTargetsBySRC(tableName);
		for (int i = 0; i < result.size(); i++) {
			ReportTarget rt = result.get(i);
			for (int j = 0; j < reportTarget.size(); j++) {
				ReportTarget temp = reportTarget.get(j);
				if (rt.getTargetField().trim()
						.equals(temp.getTargetField().trim())) {
					rt.setNowsize(temp.getRulesize());
					reportTarget.remove(j);
					break;
				}
			}
		}
		// 去掉主键，机构编码和报表日期
		for (int i = 0; i < reportTarget.size();) {
			ReportTarget temp = reportTarget.get(i);
			if (temp.getTargetField().trim().equals("PKID")
					|| temp.getTargetField().trim().equals("ORGAN_ID")
					|| temp.getTargetField().trim().equals("REPORT_DATE")) {
				reportTarget.remove(i);
				continue;
			}
			i++;
		}
		result.addAll(reportTarget);
		return result;
	}

	public int XLSInit(FormFile data, String filename, String organId,
			String resultablename) throws Exception {
		// 读入excel数据，构造数据集
		DataSet ds = XLSBuild.constructData(data);
		// 数据插入数据库
		createSql(ds, filename, organId, resultablename);
		// dao.batchJdbcUpdate(sql);
		// checkDataXLS(filename);
		checkDataXLS(filename, resultablename);
		return 0;
	}

	public void checkDataXLS(String filename, String resultablename) {
		String[] names = filename.split("_");
		/*
		 * List<?> list=datafillDataFillDAO.getdefChar(new Long(names[1]),new
		 * Long(34)); if(list!=null&&list.size()>0){ ReportConfig
		 * rc=(ReportConfig)list.get(0); String tablename=rc.getDefChar();
		 * tablename=tablename.replaceAll("\\{M\\}",names[2].substring(4,6));
		 * tablename=tablename.replaceAll("\\{D\\}",names[2].substring(6,8));
		 * tablename=tablename.replaceAll("\\{Y\\}",names[2].substring(0,4));
		 */
		String tablename = resultablename;
		List<ReportRule> reportruleList = datafillDataFillDAO
				.getReportRuleByReportId(names[1]);
		for (ReportRule rr : reportruleList) {
			datafillDataFillDAO.deleteReportResult(
					"'" + rr.getRulecode() + "'", "0", tablename);
			datafillDataFillDAO.checkData(rr.getRulecode(),
					names[2].substring(0, 8));
			/* } */
		}
	}

	public String[] createSql(DataSet ds, String filename, String organId,
			String resultablename) {
		String[] names = filename.split("_");
		List xlsdata = ds.getXlsData();
		String reportdate = names[2].substring(0, 6) + "00";
		// String tablename = resultablename;
		// 获取f表表名
		List<?> list = datafillDataFillDAO.getdefChar(new Long(names[1]),
				new Long(33));
		ReportConfig rc = (ReportConfig) list.get(0);
		String tablename = rc.getDefChar();
		tablename = tablename.replaceAll("\\{M\\}", names[2].substring(4, 6));
		tablename = tablename.replaceAll("\\{D\\}", names[2].substring(6, 8));
		tablename = tablename.replaceAll("\\{Y\\}", names[2].substring(0, 4));
		// 获得模板的栏
		List<ReportTarget> reportTargetList = datafillDataFillDAO
				.getReportTargets(new Long(names[1]));
		for (int i = 0; i < xlsdata.size(); i++) {
			String qsb = "insert into " + tablename + " (pkid,REPORT_DATE ";
			String hsb = " values( REP_DATAM_PKID_SEQ.NEXTVAL,'" + reportdate
					+ "'";
			RowSet rowDatas = (RowSet) xlsdata.get(i);
			Map datas = rowDatas.getRowData(); // 放有标题和数据的map
			boolean flag = false;
			for (ReportTarget rt : reportTargetList) {
				if ("内部机构号".equals(rt.getTargetName().trim())) {
					flag = true;
					qsb += ",ORGAN_ID," + rt.getTargetField();
					hsb += ",'" + datas.get(rt.getTargetName().trim()) + "','"
							+ datas.get(rt.getTargetName().trim()) + "'";
				} else if (rt.getDataType() == 1) {
					qsb += "," + rt.getTargetField();
					hsb += "," + datas.get(rt.getTargetName().trim());
				} else if (rt.getDataType() == 3) {
					qsb += "," + rt.getTargetField();
					hsb += ",'" + datas.get(rt.getTargetName().trim()) + "'";
				}
			}
			if (!flag) {
				qsb += ",ORGAN_ID ";
				hsb += ",'" + organId + "'";
			}
			qsb += ") ";
			hsb += ") ";
			String sqlsb = qsb + hsb;
			datafillDataFillDAO.insertDataF(sqlsb);
		}
		return null;

	}

	@Override
	public String getModelId(String reportId) {

		return datafillDataFillDAO.getModelId(reportId);
	}

}
