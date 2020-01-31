package com.krm.ps.model.flexiblequery.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.krm.ps.model.flexiblequery.dao.DataFillDAO;
import com.krm.ps.model.flexiblequery.services.DataFillService;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.model.vo.Page;
import com.krm.ps.model.vo.PaginatedListHelper;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

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
 * Copyright: Copyright (c) 2013
 * </p>
 * 
 * <p>
 * Company: KRM
 * </p>
 * 
 * @author
 */
public class DataFillServiceImpl implements DataFillService {
	private DataFillDAO flexiblequeryDataFillDAO;

	public DataFillDAO getFlexiblequeryDataFillDAO() {
		return flexiblequeryDataFillDAO;
	}

	public void setFlexiblequeryDataFillDAO(DataFillDAO flexiblequeryDataFillDAO) {
		this.flexiblequeryDataFillDAO = flexiblequeryDataFillDAO;
	}

	/**
	 * 根据模板，关联查出模板下报表的数据 添加传参Map字段映射
	 * 
	 * @param reportId
	 * @param organId
	 * @param reportDate
	 * @param reportTargetList
	 * @param page
	 * @param bondstock
	 * @param idxid
	 * @param mapC
	 * @return
	 */
	public List getReportDataAll1(Map<Long, List<DicItem>> dicMap,
			String cstatus, int isadmin, List resultablename, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, int idxid, String levelFlag, Map mapC) {
		PaginatedListHelper plh = flexiblequeryDataFillDAO
				.getReportDataByPageAll1(cstatus, isadmin, resultablename,
						reportId, organId, reportDate, page, idxid, levelFlag,
						mapC);
		List list = buildDataAll(dicMap, reportId, organId, reportDate,
				reportTargetList, page, plh, levelFlag, cstatus);
		return list;
	}

	/**
	 * 组织页面上展示的数据
	 * 
	 * @param reportId
	 * @param organId
	 * @param reportDate
	 * @param reportTargetList
	 * @param page
	 * @param plh
	 * @return
	 */
	private List buildDataAll(Map<Long, List<DicItem>> dicMap, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, PaginatedListHelper plh, String levelFlag, String cstatus) {
		List list1 = new ArrayList<Map>();
		Map pkidMap = new HashMap();
		Map repDataMap = new HashMap();
		List list = plh.getList(); // 存入数据的list，list里放的是map
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
								.getTargetField().toLowerCase()); // 获得列值;
						List<DicItem> dicItems = null;
						if (value != null && !("null".equals(value))) {
							// 如果是报送模板查询的话，要把字典项目直接映射进去
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
						System.out.println(e.getMessage());
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
	public Long getreportId(Long reportId) {
		Report report = flexiblequeryDataFillDAO.getReport(reportId);
		Long reportpkid = null;
		if (report != null) {
			reportpkid = flexiblequeryDataFillDAO.getReportByid(report);
		}

		return reportpkid;
	}

	@Override
	public Long getreportId(Long reportId, String showlevel) {
		Report report = flexiblequeryDataFillDAO.getReport(reportId);
		Long reportpkid = null;
		if (report != null) {
			reportpkid = flexiblequeryDataFillDAO.getReportByid(report,
					showlevel);
		}

		return reportpkid;
	}

	/**
	 * 重置数据状态
	 * 
	 * @param resulttablename
	 * @param repdatafname
	 * @param dataId
	 * @param reportId
	 * @return
	 */
	public int resetResultData(String resulttablename, String repdatafname,
			String dataId, String reportId) {
		return flexiblequeryDataFillDAO.resetResultData(resulttablename,
				repdatafname, dataId, reportId);
	}

	@Override
	public List<DicItem> getDicItems(Long dicPid) {
		return flexiblequeryDataFillDAO.getDicByPid(dicPid);
	}

	@Override
	public Object[] getRelateTarget(String modelid, String targetid,
			Long reportId) {
		Object[] o = flexiblequeryDataFillDAO.getRelateTarget(modelid,
				targetid, reportId);
		return o;
	}

	@Override
	public Map<String, String> getRelateTargets(Map<String, Long> relation) {
		List list = flexiblequeryDataFillDAO.getRelateTargets(relation);
		Iterator iterator = list.iterator();
		Map<String, String> targetMap = new HashMap<String, String>();
		while (iterator.hasNext()) {
			Object[] arr = (Object[]) iterator.next();
			targetMap.put(arr[1] + "_" + arr[3], arr[0] + "_" + arr[2]);
		}
		return targetMap;
	}

	public List<DicItem> getDicvalue(String reportdate, ReportTarget rt) {
		return flexiblequeryDataFillDAO.getDicvalue(reportdate, rt);
	}

	@Override
	public List getMapColumn() {
		return flexiblequeryDataFillDAO.getMapColumn();
	}
	

	public List getReportDataAllMapColumn(Map<Long, List<DicItem>> dicMap,
			String cstatus, int isadmin, List resultablename, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, int idxid, String levelFlag, String zhi1,
			String targetField1, String zhi2,
			String targetField2, String zhi3,
			String targetField3, Map mapC) {
		PaginatedListHelper plh = flexiblequeryDataFillDAO
				.getReportDataByPageAllMapColumn(cstatus, isadmin,
						resultablename, reportId, organId, reportDate, page,
						idxid, levelFlag, zhi1, targetField1, zhi2, targetField2, zhi3, targetField3, mapC);
		List list = buildDataAll(dicMap, reportId, organId, reportDate,
				reportTargetList, page, plh, levelFlag, cstatus);
		return list;
	}
}
