package com.krm.ps.model.flexiblequery.dao;

import java.util.List;
import java.util.Map;

import com.krm.ps.framework.dao.DAO;
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

	public List<DicItem> getDicvalue(String reportdate, ReportTarget rt);

	/**
	 * 查询映射字段
	 * 
	 * @param
	 * @return
	 */
	public List getMapColumn();

	public PaginatedListHelper getReportDataByPageAll1(String cstatus,
			int isadmin, List resultablename, Long reportId, String organId,
			String reportDate, Page page, int idxid, String levelFlag, Map mapC);

	/**
	 * 通过id查找报表信息
	 */
	public Report getReport(Long reportId);

	/**
	 * 得到报表PKID
	 * 
	 * @param report
	 * @return
	 */
	public Long getReportByid(Report report);

	/**
	 * 得到报表PKID
	 * 
	 * @param report
	 * @return
	 */
	public Long getReportByid(Report report, String showlevel);

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
	 * 通过parentid查询标准化字典
	 * 
	 * @param dicPid
	 * @return
	 */
	public List<DicItem> getDicByPid(Long dicPid);

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
	public List getRelateTargets(Map<String, Long> relation);
	
	public PaginatedListHelper getReportDataByPageAllMapColumn(String cstatus,
			int isadmin, List resultablename, Long reportId, String organId,
			String reportDate, Page page, int idxid, String levelFlag,
			String zhi1,String targetField1, String zhi2,String targetField2, String zhi3,
			String targetField3, Map mapC);

}
