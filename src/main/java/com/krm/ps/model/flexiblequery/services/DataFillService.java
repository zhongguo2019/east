package com.krm.ps.model.flexiblequery.services;

import java.util.List;
import java.util.Map;

import com.krm.ps.model.vo.DicItem;
import com.krm.ps.model.vo.Page;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: KRM
 * </p>
 * 
 * @author
 */
public interface DataFillService {

	public List<DicItem> getDicvalue(String reportdate, ReportTarget rt);

	/**
	 * 查询映射字段
	 * 
	 * @param
	 * @return
	 */
	public List getMapColumn();

	/**
	 * 根据模板，关联查出模板下报表的数据 2014-07-08 添加传参Map字段映射
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
			Page page, int idxid, String levelFlag, Map mapC);

	/**
	 * reportid 从模型转化成模板
	 * 
	 * @param reportId
	 * @return
	 */
	public Long getreportId(Long reportId);

	/**
	 * reportid 从模型转化成模板
	 * 
	 * @param reportId
	 * @return
	 */
	public Long getreportId(Long reportId, String showlevel);

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
			String dataId, String reportId);

	/**
	 * 通过dicPid获得字典项列表
	 * 
	 * @param dicPid
	 *            Standard_Dic表中的parentid
	 */
	public List<DicItem> getDicItems(Long dicPid);

	/**
	 * 将结果表中的模型对应列转化成模板对应列
	 * 
	 * @param modelid
	 * @param targetid
	 * @param reportId
	 * @return
	 */
	public Object[] getRelateTarget(String modelid, String targetid,
			Long reportId);

	/**
	 * 获取关联指标
	 * 
	 * @param relation
	 * @return
	 */
	public Map<String, String> getRelateTargets(Map<String, Long> relation);
	
	public List getReportDataAllMapColumn(Map<Long, List<DicItem>> dicMap,
			String cstatus, int isadmin, List resultablename, Long reportId,
			String organId, String reportDate, List reportTargetList,
			Page page, int idxid, String levelFlag, String zhi1,
			String targetField1, String zhi2,
			String targetField2, String zhi3,
			String targetField3, Map mapC);


}
