package com.krm.ps.sysmanage.reportdefine.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportItem;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTemplate;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.reportdefine.vo.TargetTemplate;

public interface ReportDefineDAO extends DAO
{

	/**
	 * 得到需要采集的报表类型（采集模块使用）
	 * @param userid 用户Id
	 * @return {@link ReportType}对象列表
	 */
	public List getReportTypesForGather(Long userid);
	public int insertDataF( String sql);
	/**
	 * 得到导出1104XML的报表类型
	 * 
	 * @param userid 用户Id
	 * @return {@link Report} 对象列表
	 */
	public List getReportTypesForExport1104(Long userid);
	/**
	 * 查出不在日志表的数据	
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
		 * 得到导出1104XML的报表
		 * @param userid 用户Id
		 * @return {@link Report} 对象列表
		 */
	public List get1104ExportReport(Long userid);
	/**
	 * 根据报表类型,日期,用户权限得到导出1104XML的报表
	 * @param date 日期
	 * @param type 报表类型
	 * @param userid 用户id
	 * @return {@link Report} 对象列表
	 */
	public List get1104ExportReportFrequency(String date, Long type, Long userid);

	/**
	 * 根据报表类型得到导出1104XML的报表
	 * 
	 * @param userid 用户Id
	 * @param reportTypeId 报表类型id
	 * @return {@link Report} 对象列表
	 */
	public List get1104ExportReportByReportType(Long userid, Long reportTypeId);

	/**
	 * 得到需要签报的报表类型（审签模块使用）
	 * @param userid 用户Id
	 * @return {@link ReportType}对象列表
	 */
	public List getReportTypesForVise(Long userid);

	/**
	 * 得到需要汇总的报表类型（汇总模块使用）
	 * @param userid 用户Id
	 * @return {@link ReportType}对象列表
	 */
	public List getReportTypesForCollect(Long userid);

	/**
	 * 得到所有报表类型
	 * @param userid 用户Id
	 * @return {@link ReportType}对象列表
	 */
	public List getReportTypesForExport(Long userid);

	/**
	 * 得到所有报表类型
	 * @param userid 用户Id
	 * @return {@link ReportType}对象列表
	 */
	public List getReportTypes(Long userid);

	/**
	 * 根据类型ID删除报表类型
	 * @param typeId 报表类型id
	 */
	public void removeReportType(Long typeId);

	/**
	 * 检查报表类型名称是否重复
	 * @param pkid 报表pkid
	 * @param name 报表名称
	 * @return
	 */
	public boolean typeNameRepeat(Long pkid, String name);

	/**
	 * 根据参数得到报表列表
	 * @param report 报表对象
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReports(Report report, String date, Long userid,String showlevel);

	/**
	 * 得到需要采集的报表列表（采集模块使用）
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReportsForGather(String date, Long userid);
	
	public List getReportTypes(String typeid);

	/**
	 * 得到所有报表的列表
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReports(String date, Long userid);

	/**
	 * 根据报表类型得到该类型下的报表列表
	 * @param typeId 报表类型id
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReportsByType(Long typeId, String date, Long userid);

	/**
	 * 根据报表类型id,频度,日期,用户id得到报表列表
	 * @param typeId 报表类型id
	 * @param frequencyId 频度
	 * @param beginDateId 起始日期
	 * @param endDateId 终止日期
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReportsByTypeFrequencyDate(Long typeId, String frequencyId,
		String beginDateId, String endDateId, String date, Long userid);

	/**
	 * 根据报表类型得到该类型下的报表ID列表
	 * @param typeId 报表类型id
	 * @param date 日期
	 * @param userid 用户id
	 * @return 报表id数组
	 */
	public Long[] getReportidsByType(Long typeId, String date, Long userid);

	/**
	 * 删除报表
	 * @param pkid
	 */
	public void removeReport(Long pkid);
	/**
	 * 删除报表(将报表状态更新为9)
	 * @param pkid
	 * @return
	 */
	public Long deleteReport(Long pkid);

	/**
	 * 根据报表ID得到报表中的机构类型列表
	 * @param reportId 报表id
	 * @return {@link ReportOrgType}对象列表
	 */
	public List getReportOrgTypes(Long reportId);

	/**
	 * 根据报表ID得到报表对象
	 * @param reportId 报表id
	 * @return Report
	 */
	public Report getReportById(Long reportId);

	/**
	 * 根据报表id删除报表机构对照
	 * @param reportId 报表id
	 */
	public void delReportOrgTypes(Long reportId);

	/**
	 * 根据报表ID和截止日期得到该报表中的科目列表
	 * @param reportId 报表id
	 * @param date 日期
	 * @return {@link Report}对象列表
	 */
	public List getReportItems(Long reportId, String date);

	/**
	 * 根据报表ID得到该报表中的科目列表
	 * @param reportId 报表id
	 * @return {@link ReportItem)对象列表
	 */
	public List getReportItems(Long reportId);

	/**
	 * 根据报表ID得到需要汇总的科目列表
	 * @param reportId 报表id
	 * @param date 日期
	 * @return {@link ReportItem)对象列表
	 */
	public List getCollectReportItems(Long reportId, String date);

	/**
	 * 报表科目信息
	 * @param item 科目信息
	 */
	public void saveReportItem(ReportItem item);

	// public Map getReport4DataBuild(String[] orgIds,String[] typeIds,String
	// date,Long userid);

	/**
	 * 删除报表科目
	 * @param itemId 科目id
	 */
	public void removeReportItem(Long itemId);

	/**
	 * 根据报表id得到报表列信息
	 * @param reportId 报表id
	 * @return {@link ReportTarget)对象列表
	 */
	public List<ReportTarget> getReportTargets(Long reportId);
	
	public List getCollectTarget(Long reportId);
	
	/**
	 * 根据 债券，股权类型查询报表
	 * @param reportId
	 * @return
	 */
	public List getReportTargetsStock(Long reportId,String stocktype) ;

	/**
	 * 根据报表id,列id删除报表列信息
	 * @param reportId 报表id
	 * @param targetId 列id
	 * @return 
	 */
	public int removeReportTarget(Long reportId, Long targetId);

	public List getOptions(List orgIds, String date, Long userid);

	/**
	 * 根据机构,日期,用户id得到报表列表
	 * @param organs 机构
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report)对象列表
	 */
	public List getOrgReports(List organs, String date, Long userid);

	/**
	 * 根据日期,机构类型,是否录入,用户id得到报表列表
	 * @param paramDate 日期
	 * @param paramOrganType 机构类型
	 * @param canEdit 是否录入
	 * @param userId 用户id
	 * @return {@link Report)对象列表
	 */
	public List getDateOrganEditReport(String paramDate,
		Integer paramOrganType, String canEdit, Long userId);

	/**
	 * 根据操作类型,用户id得到报表列表
	 * @param type 1:生成；2：检验；3：汇总
	 * @param userid 用户id
	 * @return {@link Report)对象列表
	 */
	public List getReportTypes(int type, Long userid);

	/**
	 * 得到需要生成或者需要平衡的报表
	 * 
	 * @param buildOrCheck
	 * @return
	 */
	public List getReports(int buildOrCheck, String date, Long userid);

	/**
	 * 根据报表类型得到该类型下的SHOWORDER最大值
	 * @param typeId 报表类型id
	 * @return
	 */
	public Integer getShowOrderByType(Long typeId);

	/**
	 * 将报表列表中大于传入SHOWORDER值的记录的SHOWORDER值加1
	 * @param showOrder
	 */
	public void updateShowOrder(Integer showOrder);

	/**
	 * TODO 这个接口有必要么?(getReportById(Long reportId)可代替)
	 * 根据报表id得到报表显示序号
	 * @param reportId
	 * @return
	 */
	public Integer getReportShowOrder(Long reportId);

	/**
	 * 根据报表id,科目code得到科目列表(本级及下级科目)
	 * @param reportId 报表id
	 * @param code 科目code
	 * @return {@link ReportItem)对象列表
	 */
	public List getItemByCode(Long reportId, String code);
	
	/**
	 * 根据报表Id,科目code得到科目信息
	 * @param reportId
	 * @param code
	 * @return
	 */
	public ReportItem getItem(Long reportId, String code);

	/**
	 * 根据用户id得到报表类型列表
	 * @param userid 用户id
	 * @return {@link ReportType)对象列表
	 */
	public List getReportTypesForEdit(Long userid);

	/**
	 * 根据报表ID得到该报表中所有科目的ITEMORDER最大值
	 * @param reportId 报表id
	 * @return
	 */
	public Integer getItemOrderByReport(Long reportId);

	/**
	 * 根据科目id得到科目显示序号
	 * @param itemId
	 * @return
	 */
	public Integer getItemOrder(Long itemId);

	/**
	 * 根据报表id得到报表列显示序号
	 * @param reportId 报表id
	 * @return
	 */
	public Integer getTargetOrderByReport(Long reportId);

	/**
	 * 根据列id得到列显示序号
	 * @param targetId 列序号
	 * @return
	 */
	public Integer getTargetOrder(Long targetId);

	/**
	 * 传入报表类型返回报表
	 * @param reptypes 报表类型
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report)对象列表
	 */
	public List getReportsByTypes(List reptypes, String date, Long userid);

	/**
	 * 根据报表类型,日期,用户id得到报表列表
	 * @param reptypes 报表类型
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReportsByTypes(Long reptypes, String date, Long userid);

	/**
	 * 得到所有报表类型
	 * @return {@link ReportType)对象列表
	 */
	public List getAllReportTypes(String showlevel);

	/**
	 * 根据报表类型id得到报表列表
	 * @param typeId 报表类型id
	 * @return {@link Report)对象列表
	 */
	public List getReportsByType(Long typeId);

	/**
	 * 把大于等于传入showOrder值的科目记录的显示序号自动加一
	 * 
	 * @param reportId
	 * @param showOrder
	 */
	public void updateItemShowOrder(Long reportId, Integer showOrder);

	/**
	 * 取得指定机构拥有的报表（不按日期，用户过滤）
	 * 
	 * @param organ
	 * @return
	 */
	public Set getReps(String organ);

	/**
	 * 查看报表是否有数据
	 * @param reportId 报表id
	 * @return
	 */
	public boolean checkDataByItem(Long reportId);

	/**
	 * 根据报表id删除科目
	 * @param reportid
	 */
	public void delItemByReportId(Long reportid);

	/**
	 * 根据报表id删除列
	 * @param reportid
	 */
	public void delTargetByReportId(Long reportid);
	/**
	 * 根据报表id删除模板
	 * @param reportid
	 */
	public void delFormatByReportId(Long reportid);
	/**
	 * 根据报表id删除上报xml
	 * @param reportid
	 */

	public void delXmlByReportId(Long reportid);
	
	/**
	 * 根据报表id删除机构报表对照
	 * @param reportid
	 */
	public void delOrgtypeByReportId(Long reportid);

	/**
	 * 根据报表id删除导入信息
	 * @param reportid
	 */
	public void delRuleByReportId(Long reportid);

	/**
	 * 根据报表id删除rep_data数据
	 * @param reportid
	 */
	public void delDataByReportId(Long reportid);

	/**
	 * 根据报表id删除rep_data1数据
	 * @param reportid
	 */
	public void delData1ByReportId(Long reportid);
	
	/**
	 * 从REP_DATA表中删除指定报表的指定科目的所有（日期条件、机构条件的）数据。
	 * 
	 * @param reportid 报表id
	 * @param itemId 科目id
	 */
	public void delDataByItemId(Long reportid, String itemId);

	// Edit by GuoYuelong @ 2006/10/19: edit sign = gyl_061019_02
	// public void delData1ByItemId(String itemId);
	/**
	 * 从REP_DATA1表中删除指定报表的指定科目的所有（日期条件、机构条件的）数据。
	 * 
	 * @param reportid 报表id
	 * @param itemId 科目id
	 * @deprecated This method is to be replaced by delDataByItemId(Long,
	 *             String)
	 */
	public void delData1ByItemId(Long reportid, String itemId);

	/**
	 * 删除列数据 该方法内部判断数据存在哪张物理表中
	 * 
	 * @param reportId 报表id
	 * @param targetId 列号
	 */
	public void delDataByTarget(Long reportId, int targetId);

	/**
	 * 根据报表id删除数据列数据(rep_data)
	 * @param reportid
	 */
	public void delDataByTarget(Long reportid);

	/**
	 * 根据报表id删除数据列数据(rep_data1)
	 */
	public void delData1ByTarget(Long reportid);

	/**
	 * 删除列数据 该方法内部判断数据存在哪张物理表中
	 * 
	 * @param reportId 报表id
	 * @param targetId 列号
	 */
	public void cutDataByTarget(Long reportId, int fromTargetId, int toTargetId);

	/**
	 * 保存列定义
	 * 
	 * @param target 列对象
	 */
	public void saveTarget(ReportTarget target);

	/**
	 * 更新报表定义中的报表列数信息
	 * 
	 * @param reportId 报表id
	 * @param num 列数
	 */
	public void updateReportColNum(Long reportId, Integer num);

	/**
	 * 根据报表id,列信息查看时候有列信息
	 * @param reportId
	 * @param targetField
	 * @return
	 */
	public boolean checkTargetOccupy(Long reportId, String targetField);

	/**
	 * 根据频度,日期,类型,用户id,操作标示得到报表列表
	 * @param buildOrCheck 生成或效验
	 * @param date 日期
	 * @param type 类型
	 * @param userid 用户id
	 * @return {@link Report)对象列表
	 */
	public List getReportsFrequency(int buildOrCheck, String date, Long type,
		Long userid);

	/**
	 * 取多个类型的报表
	 * 
	 * @param buildOrCheck
	 * @param date
	 * @param types
	 * @param userid
	 * @return
	 */
	public List getReportsByTypes(int buildOrCheck, String date,
		String[] types, Long userid);

	/**
	 * 得到需要导出的统计上报人行报表（因为这些报表分属于四个类型，所以单独增加一个提取方法）
	 * @return {@link Report)对象列表
	 */
	public List getReportsForStatExport();

	/**
	 * 根据报表id,日期,频度得到报表科目信息
	 * @param reportId 报表id
	 * @param date 日期
	 * @param frequency 频度
	 * @return {@link ReportItem)对象列表
	 */
	public List getReportItemsByFrequency(Long reportId, String date,
		String frequency);

	/**
	 * 根据字典表配置,导出科目表对照列
	 * 
	 * @param reportId
	 * @param date
	 * @param frequency
	 * @return
	 */
	public List getReportItemsExpCol(Long reportId, String date,
		String frequency, String col);

	/**
	 * 检验报表编码是否存在
	 * @param code 报表编码
	 * @return
	 */
	public boolean checkReportCode(String code);

	/**
	 * 得到导出统计报表
	 * 
	 * @param date 日期
	 * @param userId
	 * @return {@link Report)对象列表
	 */
	public List getReportsForStatExport(String date, Long userId);

	/**
	 * 得到结转报表ID
	 * @param reportId 报表id
	 * @return 报表id
	 */
	public Long getCarryReportId(Long reportId);

	/**
	 * 更新用户报表对照
	 * @param reportId
	 * @param newType
	 */
	public void updateUserReport(int reportId, int newType);

	/**
	 * 根据报表编号和日期来从“存放报表科目的列放在数据表的哪个物理列上”的表取出 有效的科目列表
	 */
	public List getReportTargetsByDate(Long reportId, String date);

	/**
	 * 根据报表编号，上期报表和当期报表的日期取得这个区间之内所有的科目列表
	 * 
	 * @param reportId
	 * @param lastDate
	 * @param currDate
	 * @return
	 */
	public List getItemByCondition(Long reportId, String lastDate,
		String currDate);

	/**
	 * 根据报表id,列信息得到报表列
	 * @param reportId
	 * @param targetField
	 * @return {@link ReportTarget}对象列表
	 */
	public List getReportTarget(Long reportId, String targetField);

	/**
	 * 得到报表科目信息
	 * @param reportId
	 * @param itemCode 
	 * @param num
	 * @return {@link ReportItem}对象列表
	 */
	public List getExtItem(String reportId, String itemCode, String num);

	/**
	 * 得到所有的报表
	 * @return {@link ReportItem}对象列表
	 */
	public List getAllReports();

	/**
	 * 根据机构类型,用户id,日期,报表类型得到报表列表
	 * @param orgType 机构类型
	 * @param userId 用户id 
	 * @param date 日期
	 * @param repType 报表类型
	 * @return {@link Report)对象列表
	 */
	public List getReports(String orgType, Long userId, String date,
		String repType);
	/**
	 * 根据机构类型,用户id,日期,报表类型得到报表列表
	 * @param orgType 机构类型
	 * @param userId 用户id 
	 * @param date 日期
	 * @param repType 报表类型
	 * @return {@link Report)对象列表
	 */
	public List getReportsByTypes(Long reptypes, String date, Long userid,
		Long organType);

	/**
	 * 得到需要转换汇率的报表类型
	 * 
	 * @param type
	 * @param userid
	 * @return
	 */
	public List getCurrencyReportTypes(int type, Long userid);

	/**
	 * 得到某类型下的需要转换汇率的报表列表
	 * 
	 * @param buildOrCheck
	 * @param date
	 * @param type
	 * @param userid
	 * @return
	 */
	public List getCurrencyReportsFrequency(int buildOrCheck, String date,
		Long type, Long userid);

	/**
	 * 根据报表id得到报表类型对象
	 * 
	 * @param reportId
	 * @return
	 */
	public ReportType getReportTypeByReportId(Long reportId);

	/**
	 * 得到最新的报表id by zhouhao
	 * 
	 * @param leastId 范围内最小的id
	 * @param maximalId 范围内最大的id
	 * @return
	 */
	public Long getNewReportId(Long leastId, Long maximalId, Long reportType);

	/**
	 * 2007-8-25 周浩 用于"报表克隆" 根据报表编码更改报表pkid
	 * 
	 * @param code
	 */
	public void updatePkidByCode(String code, Long pkid);

	/**
	 * 根据报表编码得到报表信息 周浩 070825
	 * 
	 * @param code
	 * @return
	 */
	public Report getReportByCode(String code);

	/**
	 * 2007-8-25 周浩 用于"报表克隆" 将旧报表的科目拷贝到新的报表下
	 * 
	 * @param newRepid
	 * @param oldRepid
	 */
	public void copyItems(Long newRepid, Long oldRepid);

	/**
	 * 2007-8-25 周浩 用于"报表克隆" 将旧报表的列拷贝到新的报表下
	 * 
	 * @param newRepid
	 * @param oldRepid
	 */
	public void copyTargets(Long newRepid, Long oldRepid);

	/**
	 * 得到该机构下统计员用户时候有权限查看该报表
	 * 
	 * @param organId
	 * @param reportId
	 * @return
	 */
	public boolean isRepUser(String organId, Long reportId, Long reportType);
	
	/**
	 * 得到该机构下统计员用户时候有权限查看该报表,增加了新旧接口的判断
	 * 
	 * @param organId
	 * @param reportId
	 * @param transferflag
	 * @return
	 */
	public boolean isRepUser1(String organId, Long reportId, Long reportType,String transferflag);

	/**
	 * 得到数据表表名
	 */
	public List<String> getDataTable();

	/**
	 * 根据表名得到数据列名称
	 * 
	 * @param tabName
	 * @return
	 */
	public List getDataField(String tabName);

	/**
	 * 根据报表的频度和日期查询报表列表
	 * 
	 * @param freq 报表的频度
	 * @param reporttype 报表类型
	 * @return 报表对象列表
	 * 
	 * add by lxk _20070709 用于黑龙江统计分析查询调用
	 */
	public List getFreqReport(String freq, String reporttype);

	/**
	 * 根据报表和日期(本期\年初日期)查询科目列表
	 * 
	 * @param reportId 报表ID
	 * @param curDate 本期
	 * @param datePB 年初日期
	 * @return 报表对象列表
	 * 
	 * add by lxk _20070709 用于黑龙江统计分析查询调用
	 */
	public List getReportItems(Long reportId, String curDate, String datePB);

	/**
	 * 根据机构类型、机构的ID查询报表的格式定义文件
	 * 
	 * @param organInfo
	 * @return 包含ReportFormat对象的列表 <br/>
	 *  add by dxp 用于生成PDF报表调用.....
	 */
	public List getReportByOrganType(OrganInfo organInfo);
	
	/**
	 * 根据机构类型、查询报表，并按照生成pdf配置过滤结果
	 * @param organInfo	要查询的机构
	 * @return
	 */
	public List getPdfReportsByOrganType(OrganInfo organInfo, String date);
	public List getAutoPdfByOrganType(String organType, String date);
	/**
	 * 根据机构类型、查询报表，并按照该报表是否需要生成过滤结果
	 * @param organType	机构类型
	 * @param date	报表日期
	 * @return	
	 */
	public List getAutoBuildReportsByOrganType(String organType, String date);
	
	/**
	 * 根据存储物理表名得到报表列表
	 * @param tableName
	 * @return {@ like Report}
	 */
	public List getReportTarget(String tableName);
	/**
	 * 得到智能流程显示报表(关联日期,频度,用户权限,机构)
	 * @param date
	 * @param userId
	 * @param organId
	 * @return 返回Report vo List
	 */
	public List getFlowTipReport(String date, Long userId, String organId);
	/**
	 * 智能流程提醒定义步骤显示报表(关联日期,频度,用户权限)
	 * 返回Report vo List
	 */
	public List getAddStepReportList(String date, Long userId);
	

	/**
	 * 查询报表,按照创建者过滤报表
	 */
	public List getReportByAuthor(String date, Long userId, Long reportType);
	/**
	 * 得到admin用户创建的报表
	 * @param userId
	 * @return
	 */
	public List getAdminCreateReport(String userId);
	
	
	/**
	 * 根据报表Id得到报表
	 * @param reportIds
	 * @return
	 */
	public Map getReportsByIds(String reportIds);

	/**
	 * 根据报表日期返回某指定机构应处理的所有报表列表。
	 * @param organCode	指定机构的机构代号
	 * @param reportDate	报表日期
	 * @return	报表-机构类型关联表(code_orgtype_report)中的定义报表列表，经过频度过滤
	 */
	public List getReportsForOrgan(String organCode, String reportDate);

	/**
	 * 从给定的报表列表中判断，根据报表日期返回某指定机构应处理的所有报表列表中有多少包含在给定列表中。
	 * @param organCode	指定机构的机构代号
	 * @param reportDate	报表日期
	 * @param restrictList	给定报表id列表，逗号分割不带单引号
	 * @return	报表-机构类型关联表(code_orgtype_report)中的定义报表列表，经过频度过滤
	 */
	public List getReportsForOrgan(String organCode, String reportDate, String restrictList);
	/**
	 * add by lxk 2008.02.23 用于广东二期数据采集时，报表id的对照
	 * 根据报表con_code得到报表对象
	 * 返回Report vo
	 */
	public Report getReportByConCode(String conCode);
	
	/**
	 * 根据用户ID、报表日期、功能代码得到报表。
	 * @param userId	指定用户ID
	 * @param reportDate	报表日期
	 * @param funId	指定报表的功能
	 * @param def	指定报表的功能
	 * @return	报表列表
	 */
	public List getReportsByFunction(String userId, String date, String funId, String def);
	
	/**
	 * 根据条件判断是否有符合条件的报表
	 * @param paramDate 日期
	 * @param organ 机构编码
	 * @param canEdit 
	 * @param userId
	 * @return boolean
	 */
	public boolean isReportExist(String paramDate,
			String organ, String canEdit, Long userId);
	

	/**
	 * 得到科目的最大code
	 * @param reportId 报表ID
	 * @return String
	 */
	public String getMaxCodeByReport(Long reportId);
	/**
	 * 根据报表ID取其所有的\有序的可用科目
	 * @param reportId
	 * @return
	 */
	public List getItemByRepId(Long reportId);

	/**
	 * 根据报表编码,类型,日期得到报表
	 * @param code 报表编码
	 * @param reportType 报表类型id
	 * @param date 日期
	 * @return
	 */
	public Report getReport(String code, Long reportType, String date);

	/**
	 * <p>根据报表ID查询可录入的报表ID</p> 
	 *
	 * 传入下一个报表ID串，如135,145，过滤此串，得到可以进行报表录入的报表ID
	 *
	 * @param reportIds
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-6-18 上午03:35:55
	 */
	public List getCanInputReportIdList(String reportIds);
	
	/**根据报表code串，时间，人员得到报表
	 * @param codes
	 * @param date
	 * @param userid
	 * @return
	 */
	public List getReportsByCodes(String pkids,String date,Long userid);
	
	/**根据配置表得到（上期，年初，上年同期审核）列值
	 * @param checkMode
	 * @param reportId
	 * @return
	 */
	public List getTargetsByConfig(int checkMode,Long reportId,String idx);
	 
	public Map getReportMap();
	
	public List getTypes(StringBuffer typeid);

	/**
	 * 2011-11-23周石磊
	 * 通过日志类型查询系统允许上报的1104报表集合
	 * @param date				上报日期
	 * @param reportTypeId	报表类型id
	 * @param userid				用户
	 * @param LogType			日志类型：主人签报通过或科处审签通过
	 * @return
	 */
	public List get1104ReportByLogType(String date, Long reportTypeId,
			Long userid, String logType,String organId);

	/**
	 * 2011-12-06周石磊
	 * 山西省数据生成列表将调平的报表进行着色处理
	 * 获取调平通过的报表列表
	 * @param buildOrCheck	 数据生成
	 * @param date			日期
	 * @param reportType	报表类型
	 * @param userId			用户id
	 * @param organCode	当前所选机构
	 * @return
	 */
	public List getBalancedReportsForDataBuild(int buildOrCheck, String date,
			Long reportType, Long userId, String organCode);

	/**
	 * 2011-12-06周石磊
	 * 山西省数据汇总列表将调平的报表进行着色处理
	 * 获取调平通过的报表列表
	 * @param date			日期
	 * @param reportType	报表类型
	 * @param userId			用户id
	 * @param organCode	当前所选机构
	 * @return
	 */
	public List getBalancedReportForCollect(Long type, String date, Long pkid,
			String organ);

	/**
	 *  2011-12-08周石磊
	 * 为了获取第一个类型下的第一张报表
	 * 按照报表类型排列
	 * @param dataDate
	 * @param pkid
	 * @return
	 */
	public List getReportsOrderByType(String date, Long userid); 
	
	
	/**
	 * 2011-12-09
	 * 根据传入的报表ID在数据库中排序得到报表List
	 * @param reportIds
	 * @return
	 */
	public List getReportsToListByIds(String reportIds,String reportDate);

	/**
	 * 获取第一个类型下的第一张报表
	 * @param dataDate
	 * @param userId
	 * @return
	 */
	public Report getFirstReportOrderByReportType(String dataDate, Long userId);

	/**
	 * 根据报表名称获取报表对象
	 * @param ReportName
	 * @return
	 */
	public Report getReportByReportName(String ReportName);

	public List getAuditCollectReportsByTypes(Long reptypes, String date,
			Long userid, String organ);
	/**
	 * ��׼�� ��ѯ������
	 * @param paramDate
	 * @param paramorgan_type
	 * @param canEdit
	 * @param userId
	 * @return
	 */
	List getDateOrganEditReportForStandard(String paramDate,
			Integer paramorgan_type, String canEdit, Long userId,String systemId,String levelFlag);

	public List getTargetsByReportId(Long reportId);
	/**
	 * 标准化 查询报表树
	 * @param paramDate
	 * @param paramorgan_type
	 * @param canEdit
	 * @param userId
	 * @return
	 */
	 List<ReportTarget> getTemplateTargets(Long templateId, Long modelId);
	 
	 public List getReportByYJH(String ismodel);
	 
	 /**
		 * 标准化 查询模版指标
		 * @param templateId
		 * @param modelId
		 * @return
		 */
	public void clearTemplateTargets(Long templateId, Long modelId);
	 /**
	  * 清空与该模型相关的模版指标
	  * @param templateId
	  * @param modelId
	  */
	void delTemplateTargets(Long templateId, Long modelId,String modelTarget);
	/**
	 * 删除模版指标
	 * @param templateId
	 * @param modelId
	 */
	Integer getNextTargetField(Long reportId, String dataType);
	/**
	 * 查询生成的TargetField
	 * @param reportId
	 * @param dataType
	 * @return
	 */
	void updateTargetRelation(Long templateId, Long modelId,
			Map<String, String> targetMap);
	/**
	 * 维护模版指标关系
	 * @param templateId
	 * @param modelId
	 * @param targetMap
	 */
	Long insertTarget(ReportTarget target);

	/**
	 * 用于添加模版指标并返回id
	 * @param target
	 * @return
	 */
	void delTemplateTarget(Long templateId, Long modelId, String targetId);
	/**
	 * 单条删除模版指标
	 * @param templateId
	 * @param modelId
	 * @param targetId
	 */
	public void updateTemplateTargetField(Long templateId, Long modelId,
			String oldTargetField, String targetField);
	/**
	 * 更新关联表中模版targetField
	 * @param templateId
	 * @param modelId
	 * @param oldTargetField
	 * @param targetField
	 */
	List getTargetRelation(Long templateId, Long modelId);
	/**
	 * 适用于EAST标准化
	 *  添加模版 gaozhognbo
	 * @param parseLong
	 */
	public void saveTemplate(ReportTemplate reportTemp,String updatetemp);
	
	/**
	 * 适用于EAST标准化
	 *  查询所有模版  gaozhongbo
	 * @param parseLong
	 */
	public List getallTemplate(Long userid);
	
	public String getTypesname(String typesid);
	/**
	 * 适用于EAST标准化
	 *  查询所有模版类型 gaozhongbo
	 * @param parseLong
	 */
	public List allReportTypes(Long userid);
	
	/**
	 * 适用于EAST标准化
	 *  通过模板id查询有模版信息 gaozhongbo
	 * @param parseLong
	 */
	public List getTemplates(Long reportid);
	/**
	 * 适用于EAST标准化
	 *  通过模板id删除该模版 gaozhongbo
	 * @param parseLong
	 */
	public boolean delTemplates(String reportid);
	/**
	 * 适用于EAST标准化
	 *  通过模板id查询该模版指标 gaozhongbo
	 * @param parseLong
	 */
	public List getTempTarget(String reportid, String reportid1,String flag);
	/**
	 * 适用于EAST标准化
	 *  保存模板指标 gaozhongbo
	 * @param parseLong
	 */
	public void saveTempTarget(String reportid, String reportid1,TargetTemplate target);
	/**
	 * 适用于EAST标准化
	 * 删除模板指标 gaozhongbo
	 * @param parseLong
	 */
	public void deleTempTarget(String reportid, String reportid1,TargetTemplate target);
	/**
	 * 适用于EAST标准化
	 * 通过指标物理表的字段名查询指标 gaozhongbo
	 * @param parseLong
	 */
	public TargetTemplate getTarget(String reportid, String reportid1,String target);
	
	/**
	 * 将要需要迁移到的数据 gaozhognbo
	 */
	public void reportSeleMove(String status);

	
	public List getOrgan();
	public void saveOrgan(OrganTreeNode otn);
	
	
	public List getReportTargetsBySRC(String tableName);
	/**
	 * 查询字典
	 * @return
	 */
//	public List<DicItem> queryDicAll();
	
	/**
	 * 
	 * @return
	 */
	public Integer getMaxOrderNum(Long reportId);
	
	/**
	 * 
	 * @param rt
	 */
	public void saveReportTarget(ReportTarget rt);
	
	
	public List getReportRuleByReportId(String reportid);
	public int deleteReportResult(String  rulecode ,String cstatus ,String tablename,String organId);
	public Object[] checkData(String rulecode,String date);
	public String getReportXML_temp(List reportList, Set set,String paramDate,Integer paramOrganType,String canEdit,Long userId,String systemId,String levelFlag);

} 
