package com.krm.ps.model.reportdefine.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.krm.ps.model.vo.DicItem;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;

public interface ReportDefineDAO {

	/**
	 * 得到所有报表类型
	 * 
	 * @return {@link ReportType)对象列表
	 */
	public List getAllReportTypes(String showlevel);

	public Object getObject(Class clazz, Serializable id);

	/**
	 * 根据类型ID删除报表类型
	 * 
	 * @param typeId
	 *            报表类型id
	 */
	public void removeReportType(Long typeId);

	/**
	 * 检查报表类型名称是否重复
	 * 
	 * @param pkid
	 *            报表pkid
	 * @param name
	 *            报表名称
	 * @return
	 */
	public boolean typeNameRepeat(Long pkid, String name);

	public void saveObject(Object o);

	/**
	 * 得到所有报表类型
	 * 
	 * @param userid
	 *            用户Id
	 * @return {@link ReportType}对象列表
	 */
	public List getReportTypes(Long userid);

	/**
	 * 根据参数得到报表列表
	 * 
	 * @param report
	 *            报表对象
	 * @param date
	 *            日期
	 * @param userid
	 *            用户id
	 * @return {@link Report}对象列表
	 */
	public List getReports(Report report, String date, Long userid,
			String showlevel);

	/**
	 * 根据报表类型id,频度,日期,用户id得到报表列表
	 * 
	 * @param typeId
	 *            报表类型id
	 * @param frequencyId
	 *            频度
	 * @param beginDateId
	 *            起始日期
	 * @param endDateId
	 *            终止日期
	 * @param date
	 *            日期
	 * @param userid
	 *            用户id
	 * @return {@link Report}对象列表
	 */
	public List getReportsByTypeFrequencyDate(Long typeId, String frequencyId,
			String beginDateId, String endDateId, String date, Long userid);

	/**
	 * 得到所有报表的列表
	 * 
	 * @param date
	 *            日期
	 * @param userid
	 *            用户id
	 * @return {@link Report}对象列表
	 */
	public List getReports(String date, Long userid);

	/**
	 * 根据报表ID得到报表中的机构类型列表
	 * 
	 * @param reportId
	 *            报表id
	 * @return {@link ReportOrgType}对象列表
	 */
	public List getReportOrgTypes(Long reportId);

	/**
	 * 得到数据表表名
	 */
	public List<String> getDataTable();

	/**
	 * 更新用户报表对照
	 * 
	 * @param reportId
	 * @param newType
	 */
	public void updateUserReport(int reportId, int newType);

	/**
	 * 根据报表类型得到该类型下的SHOWORDER最大值
	 * 
	 * @param typeId
	 *            报表类型id
	 * @return
	 */
	public Integer getShowOrderByType(Long typeId);

	/**
	 * 将报表列表中大于传入SHOWORDER值的记录的SHOWORDER值加1
	 * 
	 * @param showOrder
	 */
	public void updateShowOrder(Integer showOrder);

	/**
	 * 根据报表id得到报表显示序号
	 * 
	 * @param reportId
	 * @return
	 */
	public Integer getReportShowOrder(Long reportId);

	/**
	 * 根据报表id删除报表机构对照
	 * 
	 * @param reportId
	 *            报表id
	 */
	public void delReportOrgTypes(Long reportId);

	/**
	 * 根据报表id得到报表类型对象
	 * 
	 * @param reportId
	 * @return
	 */
	public ReportType getReportTypeByReportId(Long reportId);

	/**
	 * 删除报表
	 * 
	 * @param pkid
	 */
	public void removeReport(Long pkid);

	/**
	 * 删除报表(将报表状态更新为9)
	 * 
	 * @param pkid
	 * @return
	 */
	public Long deleteReport(Long pkid);

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
	 * 查询字典
	 * 
	 * @return
	 */
	public List<DicItem> queryDicAll();

	/**
	 * 根据报表类型得到该类型下的报表列表
	 * 
	 * @param typeId
	 *            报表类型id
	 * @param date
	 *            日期
	 * @param userid
	 *            用户id
	 * @return {@link Report}对象列表
	 */
	public List getReportsByType(Long typeId, String date, Long userid);

	public Integer getMaxOrderNum(Long reportId);

	public void saveReportTarget(ReportTarget rt);

	/**
	 * 根据报表id,列id删除报表列信息
	 * 
	 * @param reportId
	 *            报表id
	 * @param targetId
	 *            列id
	 * @return
	 */
	public int removeReportTarget(Long reportId, Long targetId);

	/**
	 * 标准化 查询模版指标
	 * 
	 * @param templateId
	 * @param modelId
	 * @return
	 */
	List<ReportTarget> getTemplateTargets(Long templateId, Long modelId);

	/**
	 * 删除模版指标
	 * 
	 * @param templateId
	 * @param modelId
	 */
	void delTemplateTargets(Long templateId, Long modelId, String modelTarget);

	/**
	 * 根据报表id,列信息得到报表列
	 * 
	 * @param reportId
	 * @param targetField
	 * @return {@link ReportTarget}对象列表
	 */
	public List getReportTarget(Long reportId, String targetField);

	/**
	 * 用于添加模版指标并返回id
	 * 
	 * @param target
	 * @return
	 */
	Long insertTarget(ReportTarget target);

	/**
	 * 维护模版指标关系
	 * 
	 * @param templateId
	 * @param modelId
	 * @param targetMap
	 */
	void updateTargetRelation(Long templateId, Long modelId,
			Map<String, String> targetMap);

	/**
	 * 查出不在日志表的数据
	 * 
	 * @param paramDate
	 * @param paramorgan_type
	 * @param canEdit
	 * @param userId
	 * @param systemId
	 * @param levelFlag
	 * @return
	 */
	public List getDateOrganEditReportForYJH(String paramDate,
			Integer paramorgan_type, String canEdit, Long userId,
			String systemId, String levelFlag);

	/**
	 * 单条删除模版指标
	 * 
	 * @param templateId
	 * @param modelId
	 * @param targetId
	 */
	void delTemplateTarget(Long templateId, Long modelId, String targetId);
}
