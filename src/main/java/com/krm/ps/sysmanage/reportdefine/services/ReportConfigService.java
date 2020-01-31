package com.krm.ps.sysmanage.reportdefine.services;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public interface ReportConfigService
{
	/**
	 * 根据报表Id得到报表配置列表
	 * @param reportId
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long reportId);
	/**
	 * 根据报表Id,功能Id得到报表配置列表
	 * @param reportId
	 * @param funcId
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long reportId,Long funcId);
	/**
	 * 根据报表Id, 功能Id, 值得到报表配置列表
	 * @param reportId
	 * @param funcId
	 * @param defInt
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long reportId,Long funcId,Long defInt);
	/**
	 * 根据pkid, 报表Id, 功能Id, 值得到报表配置
	 * @param pkId
	 * @param reportId
	 * @param funcId
	 * @param defInt
	 * @return {@link ReportConfig}对象列表
	 */
	public List getReportConfigs(Long pkId,Long reportId,Long funcId,Long defInt);
	
	/**
	 * <p>查询所有机构对应的功能配置信息</p>
	 * 
	 * 现在我知道了一些机构，我想知道这些机构的某个功能的所有的配置信息
	 * 
	 * @param funId 功能ID
	 * @param orgIds 机构ID
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-3-29 上午09:43:01
	 */
	public List getReportConfigsByFunIdAndOrgIdsAndDate(String funId, String orgIds);
	
	/**
	 * 过滤报表配置
	 * @param configs 报表配置对象列表
	 * @return {@link ReportConfig)对象列表
	 */
	public List getReportConfigFun(List configs);
	/**
	 * 根据报表配置id得到报表配置
	 * @param configId
	 * @return ReportConfig
	 */
	public ReportConfig getReportConfig(Long configId);
	/**
	 * 根据报表Id,功能Id,索引得到报表配置
	 * @param reportId
	 * @param funId
	 * @param idxId
	 * @return ReportConfig
	 */
	public ReportConfig getReportConfig(Long reportId,int funId,Long idxId);
	/**
	 * 删除报表配置
	 * @param pkid
	 */
	public void removeReportConfig(Long pkid);
	/**
	 * 删除报表配置
	 * @param pkid
	 */
	public void removeReportConfig(Long repId, Long funId);
	/**
	 * 保存报表配置
	 * @param config
	 * @param isUpdate
	 */
	public void saveReportConfig(ReportConfig config,int isUpdate);
	/**
	 * 保存报表配置
	 * @param config
	 * @param isUpdate
	 */
	public void saveReportConfig(ReportConfig config,ReportConfig config1,int isUpdate);
	/**
	 * 保存报表配置
	 * @param config
	 * @param isUpdate
	 */
	public void saveReportConfig(ReportConfig config,ReportConfig config1,ReportConfig config2,ReportConfig config3,int isUpdate);
	/**
	 * 拷贝报表配置
	 * @param newRepid
	 * @param oldRepid
	 */
	public void copyConfigs(Long newRepid, Long oldRepid);
	/**
	 * 根据功能编码获取报表配置信息
	 * @param funcId 功能编码
	 * @return List
	 */
	public List getReportConfigsByFunc(Long funcId);
    public String defChar(Long reportid,Long funid);
    public List<ReportConfig> getdefCharByTem(Long reportid, Long funid);
   
    /**
	 * 查询所有报表配置信息
	 * @return
	 */
    public List getRepConfigList();
    
    /**
	 * 保存报表配置
	 * @param config
	 * @param isUpdate
	 */
	public void saveConfig(ReportConfig config);
	
	/**
	 * 根据报表Id得到报表配置列表
	 * @param pkid
	 * @return {@link ReportConfig}对象列表
	 */
	public ReportConfig getReportConfigList(Long pkid);
	
	/**
	 * 根据报表reportConfig 修改配置信息
	 * @param   reportConfig
	 * @return {@link ReportConfig}对象列表
	 */
	public void setReportConfig(ReportConfig reportConfig);

}
