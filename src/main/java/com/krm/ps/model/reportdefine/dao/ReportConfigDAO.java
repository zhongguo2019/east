package com.krm.ps.model.reportdefine.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public interface ReportConfigDAO extends DAO {
	/**
	 * 流水表分区ID
	 */
	public static String FUN_ID_SERIAL_TABLE_DISTRIBUTE_NUMBER = "33";

	/**
	 * 读取统计报表批次配置
	 * 
	 * @param repsId
	 *            报表Id
	 * @param conf
	 *            配置标示
	 * @return
	 */

	public List getStatRepConfig(String repsId, Long conf);

	public List<ReportConfig> getdefCharByTem(Long reportid, Long funid);

	/**
	 * 根据报表Id得到报表配置列表
	 * 
	 * @param reportId
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long reportId);

	/**
	 * 根据报表Id,功能Id得到报表配置列表
	 * 
	 * @param reportId
	 * @param funcId
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long reportId, Long funcId);

	/**
	 * 删除报表配置
	 * 
	 * @param pkid
	 */
	public void removeReportConfig(Long pkid);

	/**
	 * 删除报表配置
	 * 
	 * @param pkid
	 */
	public void removeReportConfig(Long repId, Long funId);

	/**
	 * 保存报表配置
	 * 
	 * @param config
	 * @param isUpdate
	 */
	public void saveConfig(ReportConfig config, int isUpdate);

	/**
	 * 保存报表配置
	 * 
	 * @param config
	 * @param isUpdate
	 */
	public void saveConfig(ReportConfig config, ReportConfig config1,
			int isUpdate);

	/**
	 * 保存报表配置
	 * 
	 * @param config
	 * @param isUpdate
	 */
	public void saveConfig(ReportConfig config, ReportConfig config1,
			ReportConfig config2, ReportConfig config3, int isUpdate);

	/**
	 * 根据reportid和funid得到需要的物理表的列名
	 * 
	 * @param reportId
	 * @param funId
	 * @return
	 */
	public String[] getColumnByRepIdAndFunId(String reportId, String funId);

	/**
	 * 是否出现上期结转的按钮
	 * 
	 * @param reportId
	 * @return
	 */
	public boolean isLastTermDataCarry(String reportId, String funId);

	/**
	 * 根据报表Id,功能Id,索引得到报表配置
	 * 
	 * @param reportId
	 * @param funId
	 * @param idxId
	 * @return ReportConfig
	 */
	public ReportConfig getReportConfig(Long reportId, int funId, Long idxId);

	/**
	 * 根据报表Id, 功能Id, 值得到报表配置列表
	 * 
	 * @param reportId
	 * @param funcId
	 * @param defInt
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long reportId, Long funcId, Long defInt);

	/**
	 * 读取报表配置
	 * 
	 * @param repId
	 *            报表Id
	 * @param conf
	 *            配置标示
	 * @return
	 */
	public List getRepConfig(Long repId, Long conf);

	/**
	 * 根据pkid, 报表Id, 功能Id, 值得到报表配置
	 * 
	 * @param pkId
	 * @param reportId
	 * @param funcId
	 * @param defInt
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long pkId, Long reportId, Long funcId,
			Long defInt);

	/**
	 * <p>
	 * 查询所有机构对应的功能配置信息
	 * </p>
	 * 
	 * 现在我知道了一些机构，我想知道这些机构的某个功能的所有的配置信息
	 * 
	 * @param funId
	 *            功能ID
	 * @param orgIds
	 *            机构ID
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-3-29 上午09:43:01
	 */
	public List getReportConfigsByFunIdAndOrgIdsAndDate(String funId,
			String orgIds);

	/**
	 * 根据报表Id, 功能Id得到报表配置
	 * 
	 * @param reportId
	 * @param funcId
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfig(Long reportId, Long funcId);

	/**
	 * 拷贝报表配置
	 * 
	 * @param newRepid
	 * @param oldRepid
	 */
	public void copyConfigs(Long newRepid, Long oldRepid);

	/**
	 * 根据功能编码获取报表配置信息
	 * 
	 * @param funcId
	 *            功能编码
	 * @return List
	 */
	public List getReportConfigsByFunc(Long funcId);

	public List<?> getdefChar(Long reportid, Long funid);

	/**
	 * 查询所有报表配置信息
	 * 
	 * @return
	 */
	public List getRepConfigList();

	/**
	 * 保存报表配置
	 * 
	 * @param config
	 * @param isUpdate
	 */
	public void saveConfig(ReportConfig config);

	/**
	 * 根据报表Id得到报表配置列表
	 * 
	 * @param pkid
	 * @return {@link ReportConfig}对象列表
	 */
	public ReportConfig getReportConfig(Long pkid);

	/**
	 * 根据报表reportConfig 修改配置信息
	 * 
	 * @param reportConfig
	 * @return {@link ReportConfig}对象列表
	 */
	public void setReportConfig(ReportConfig reportConfig);

}
