package com.krm.ps.sysmanage.reportdefine.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.sort.service.SortService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportItem;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTemplate;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.usermanage.vo.User;

public interface ReportDefineService extends SortService{
	
	public static int NAME_REPEAT = 1;
	
	public static int SAVE_OK =2;
	
	/**保存科目信息时发生错误：科目重复*/
	public static int SAVE_ERROR_DUPLICATE_ITEM = -1;
	
	/**删除操作正常终了*/
	public static int DEL_OK = 0;
	
	/**删除操作时发生错误*/
	public static int DEL_ERROR = -1;
	
	public void setReportDefineDAO(ReportDefineDAO dao);
	
	/**
	 * 根据报表类型id得到报表类型
	 * @param typeId 类型id
	 * @return ReportType
	 */
	public ReportType getReportType(Long typeId);
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
				Integer paramOrganType, String canEdit, Long userId,
				String systemId, String levelFlag);
	
		/**
		 * 根据用户id得到报表类型列表
		 * @param userid 用户id
		 * @return {@link ReportType)对象列表
		 */
	public List getReportTypes(Long userid);
	/**
	 * 得到需要采集的报表类型（采集模块使用）
	 * @param userid 用户Id
	 * @return {@link ReportType}对象列表
	 */
	public List getReportTypesForGather(Long userid);
	
	public int XLSInit(FormFile data,String filename, String organId, String dataDate) throws Exception;
	
	
	public int XLSInit(FormFile data); 
	
	public int XLSInitZf(FormFile data); 
	/**
	 * 根据是否是模型查询 模型或者模板
	 * @param ismodel
	 * @return
	 */
	public List getReportByYJH(String ismodel);
	
	/**
	 * 保存报表类型信息
	 * @param type ReportType
	 * @return
	 */
	public int saveReportType(ReportType type);
	
	/**
	 * 根据报表类型id删除报表类型
	 * @param typeId 类型id
	 */
	public void removeReportType(Long typeId);
	
	/**
	 * 根据报表id得到报表对象
	 * @param pkid 报表id
	 * @return Report
	 */
	public Report getReport(Long pkid);
	/**
	 * 适用于 EAST标准化
	 * 根据模板或模型id得到模板或模型对象
	 * @param pkid 报表id
	 * @return ReportTemplate
	 */
	public ReportTemplate getTempReport(Long pkid);
	/**
	 * 适用于 EAST标准化
	 * 根据模板id得到模板指标
	 * @param report1 模型id，report模板id
	 * @param flag 用于区分查询指标的范围
	 * @return ReportTemplate
	 */
	public List getTempTarget(String reportid, String reportid1,String flag);
	
	/**
	 * 根据报表,日期,用户得到报表列表
	 * @param report 报表
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReports(Report report,String date,Long userid);
	/**
	 * 得到需要采集的报表列表（采集模块使用）
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReportsForGather(String date,Long userid);

	/**
	 * 得到所有报表的列表
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReports(String date,Long userid);
	/**
	 * 根据报表类型得到该类型下的报表列表
	 * @param typeId 报表类型id
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReportsByType(Long typeId,String date,Long userid);
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
	public List getReportsByTypeFrequencyDate(Long typeId,String frequencyId,String beginDateId,String endDateId,String date,Long userid);
	/**
	 * 删除报表
	 * @param pkid
	 */
	public void removeReport(Long pkid);
	
	/**
	 * 保存报表信息
	 * @param report 报表对象
	 * @param isUpdate 保存或更新
	 * @return
	 */
	public int saveReport(Report report,int isUpdate);
	/**
	 * 保存报表信息 by ydw
	 * @param report 报表对象
	 * @param isUpdate 保存或更新
	 * @return
	 */
	public int saveReportIsLoger(Report report,int isUpdate,Report beforReport,User user,String time);
	
	/**
	 * 根据科目id得到科目信息
	 * @param pkid 科目id
	 * @return ReportItem
	 */
	public ReportItem getReportItem(Long pkid);
	
	/**
	 * 根据报表id,日期得到科目信息类表
	 * @param reportId 报表id
	 * @param date 日期
	 * @return {@link ReportItem}对象列表
	 */
	public List getReportItems(Long reportId,String date);
	
	/**
	 * 根据报表id得到科目信息
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
	public List getCollectReportItems(Long reportId,String date);
		
	//Edit by GuoYuelong @ 2006/10/19: edit sign = gyl_061019_03 
	/**
	 * 保存科目信息
	 * @param item 科目对象
	 * @param isUpdate 0：insert；1：update
	 * @return 操作结果：2=保存成功；-1=保存失败（科目重复）
	 */
	public int saveReportItem(ReportItem item,int isUpdate);
	
	//public void removeReportItem(Long itemId);
	/**
	 * 带联动删除相关公式的删除报表科目
	 * @param itemId 报表科目id
	 * @return
	 */
	public ReportItem removeReportItem(Long itemId, Long reportId);
	/**
	 * 根据列id得到报表列信息
	 * @param targetId 列id
	 * @return ReportTarget
	 */
	public ReportTarget getReportTarget(Long targetId);
	/**
	 * 根据报表id得到报表列信息
	 * @param reportId 报表id
	 * @return {@link ReportTarget)对象列表
	 */
	public List getReportTargets(Long reportId, String tableName);
	/**
	 * 根据报表id得到报表列信息，适用银监会
	 * @param reportId 报表id
	 * @return {@link ReportTarget)对象列表
	 */
	public List getCollectTarget(Long reportId);
	/**
	 * 根据 债券，股权类型查询报表
	 * @param reportId
	 * @return
	 */
	public List getReportTargetsStock(Long reportId,String stocktype) ;
	
	/**
	 * 保存列信息
	 * @param target 列对象
	 * @param isUpdate 保存或更新
	 */
	
	public void saveReportTarget(ReportTarget target,int isUpdate);
	
	/**
	 * 带联动删除相关公式的删除报表列
	 * @param reportId 报表id
	 * @param targetId 列号
	 * @return 操作结果 null：正常结束； != null：异常结束
	 */
	public ReportTarget removeReportTarget(Long reportId, Long targetId);

	public List getOptions(List orgIds,String date,Long userid);
	/**
	 * 根据机构,日期,用户id得到报表列表
	 * @param organs 机构
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report)对象列表
	 */
	public List getOrgReports(List organs,String date,Long userid);
	
	/**
	 * 根据日期,机构类型,是否录入,用户id得到报表列表
	 * @param paramDate 日期
	 * @param paramOrganType 机构类型
	 * @param canEdit 是否录入
	 * @param userId 用户id
	 * @return {@link Report)对象列表
	 */
	public List getDateOrganEditReport(String paramDate,Integer paramOrganType,String canEdit,Long userId);
	/**
	 * 根据操作类型,用户id得到报表列表
	 * @param type 1:生成；2：检验；3：汇总
	 * @param userid 用户id
	 * @return {@link Report)对象列表
	 */
	public List getReportTypes(int type,Long userid);
	
	/**
	 * 得到需要生成或者需要平衡的报表
	 * 
	 * @param buildOrCheck
	 * @return
	 */
	public List getReports(int buildOrCheck,String date,Long userid);
	/**
	 * 根据报表id,科目code得到科目列表(本级及下级科目)
	 * @param reportId 报表id
	 * @param code 科目code
	 * @return {@link ReportItem)对象列表
	 */
	public List getItemByCode(Long reportId,String code);
	/**
	 * 根据用户id得到报表类型列表
	 * @param userid 用户id
	 * @return {@link ReportType)对象列表
	 */
	public List getReportTypesForEdit(Long userid);
	/**
	 * 传入报表类型返回报表
	 * @param reptypes 报表类型
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report)对象列表
	 */
	public List getReportsByTypes(List reptypes,String date,Long userid);
	/**
	 * 根据报表类型,日期,用户id得到报表列表
	 * @param reptypes 报表类型
	 * @param date 日期
	 * @param userid 用户id
	 * @return {@link Report}对象列表
	 */
	public List getReportsByTypes(Long reptypes,String date,Long userid);
	/**
	 * 得到所有报表类型
	 * @return {@link ReportType)对象列表
	 */
	public List getAllReportTypes(String showlevel) ;
	
	/**
	 * 2006-06-27 左少杰增加代码
	 * 在当前科目前插入一个新的科目，同时调整科目顺序。
	 * @param reportItem 插入的新科目对象
	 * @param showOrder 插入科目的显示序号
	 */
	public int insertReportItem(ReportItem reportItem,Integer showOrder);
	
	/**
	 * 根据机构过滤报表
	 * @param organ
	 * @return 属于此机构的报表id
	 */
	public String[] filteReport(String organ, String[] reports);
	
	/**
	 * 根据机构得到相应的报表
	 * @param orgCode
	 * @return 报表id集合
	 */
	public Set getRepByOrgan(String orgCode);

	/**
	 * 根据报表类型id得到报表列表
	 * @param typeId 报表类型id
	 * @return {@link Report)对象列表
	 */
	public List getReportsByType(Long typeId);	
	
	/**
	 * 得到需要汇总的报表类型（汇总模块使用）
	 * @param userid 用户Id
	 * @return {@link ReportType}对象列表
	 */	
	public List getReportTypesForCollect(Long userid);
	
	/**
	 * 得到需要汇总的报表类型（汇总模块使用）
	 * @param userid 用户id
	 * @return 报表类型对象列表
	 */
	public List getReportTypesForExport(Long userid);
	
	/**
	 * 查看报表是否有数据
	 * @param reportId 报表id
	 * @return
	 */
	public boolean checkDataByItem(Long reportId);
	
	/**
	 * 根据报表id删除科目
	 * @param reportid 报表id
	 */
	public void delItemByReportId(Long reportid);
	
	/**
	 * 根据报表id删除列
	 * @param reportid 报表id
	 */
	public void delTargetByReportId(Long reportid);
	
	/**
	 * 根据报表id删除报表模板
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
	 * 根据类型id，获得类型列表
	 * @param typeid
	 * @return
	 */
	public List getReportTypes(String typeid);
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
	
	//Edit by GuoYuelong @ 2006/10/19: edit sign = gyl_061019_01
	/**
	 * 从REP_DATA表中删除指定报表的指定科目的所有（日期条件、机构条件的）数据。
	 * @param reportid 报表id
	 * @param itemId 科目id
	 * @history 
	 * modify by GuoYuelong at 2006/10/19: 1、增加参数（报表id）；2、增加注释。
	 */
	/*
	 old code��
	 public void delDataByItemId(String itemId);
	 */
	public void delDataByItemId(Long reportid, String itemId);
	
//	//Edit by GuoYuelong @ 2006/10/19: edit sign = gyl_061019_02
//	/**
//	 * ��REP_DATA1����ɾ��ָ�������ָ����Ŀ�����У����������������ģ���ݡ�
//	 * @param reportid ����id
//	 * @param itemId ��Ŀid
//	 * @history 
//	 * modify by GuoYuelong at 2006/10/19: 1����Ӳ����id����2�����ע�͡�
//	 * @deprecated replaced by #delDataByItemId(Long reportid, String itemId)
//	 */
//	//public void delData1ByItemId(String itemId);
//	public void delData1ByItemId(Long reportId, String itemId);
//	//
	/**
	 * 根据报表id删除数据列数据(rep_data)
	 * @param reportid
	 */
	public void delDataByTarget(Long reportid);

	/**
	 * 根据报表id删除数据列数据(rep_data1)
	 */
	public void delData1ByTarget(Long reportid);
	
	public boolean checkTargetOccupy(Long reportId,String targetField);
	/**
	 * 根据频度,日期,类型,用户id,操作标示得到报表列表
	 * @param buildOrCheck 生成或效验
	 * @param date 日期
	 * @param type 类型
	 * @param userid 用户id
	 * @return {@link Report)对象列表
	 */
	public List getReportsFrequency(int buildOrCheck, String date,Long type ,Long userid);

	/**
	 * 取多个类型的报表
	 * 
	 * @param buildOrCheck
	 * @param date
	 * @param types
	 * @param userid
	 * @return
	 */
	public List getReportsByTypes(int buildOrCheck, String date,String[] types ,Long userid);
	
	/**
	 * 得到需要导出的统计上报人行报表（因为这些报表分属于四个类型，所以单独增加一个提取方法）
	 * @return {@link Report)对象列表
	 */
	public List getReportsForStatExport();
	/**
	 * 检验报表编码是否存在
	 * @param code 报表编码
	 * @return
	 */
	public boolean checkReportCode(String code);
	
	/**
	 * 关联删除报表（同时把此报表相关的数据也删除）
	 * @return
	 */
	public Long deleteReportAbout(Long reportId);
	/**
	 * 删除报表,status = 9
	 * @param reportId
	 * @return
	 */
	public Long deleteReport(Long reportId);
	/**
	 * 得到结转报表ID
	 * @param reportId 报表id
	 * @return 报表id
	 */
	public Long getCarryReportId(Long reportId);
	/**
	 * 得到需要导出的统计上报人行报表（因为这些报表分属于四个类型，所以单独增加一个提取方法）
	 * @return {@link Report)对象列表
	 */
	public List getReportsForStatExport(String date, Long userId);
	/**
	 * 更新用户报表对照
	 * @param reportId
	 * @param newType
	 */
	public void updateUserReport(int reportId,int newType);
		
	/**
	 * @param reportList 报表Vo List
	 * @return 统计报表Bo List
	 */
	public List getStatReports(List reportList);
	/**
	 * @param statRepList 统计报表BO
	 * @return 批次(过滤和排序后的批次List)
	 */
	public List getBatchList(List statRepList);

	
	/**
	 * 根据报表id,列信息得到报表列
	 * @param reportId
	 * @param targetField
	 * @return {@link ReportTarget}对象列表
	 */
	public List getReportTarget(Long reportId,String targetField);
	/**
	 * 得到报表科目信息
	 * @param reportId
	 * @param itemCode 
	 * @param num
	 * @return {@link ReportItem}对象列表
	 */
	public List getExtItem(String reportId,String itemCode,String num);
	
	
	/**
	 * add by zhaoyi _20070331
	 * 得到所有的报表
	 * @return
	 */
	public List getAllReports();
	
	/**
	 * 根据机构,报表类型,用户,日期得到报表列表
	 * @param selectOrgId
	 * @param selectRepTypeId
	 * @param userId
	 * @param date
	 * @return {@link Report)对象列表
	 */
	public List getSelectReports(String selectOrgId, String selectRepTypeId, Long userId, String date);
	/**
	 * 根据机构类型,用户id,日期,报表类型得到报表列表
	 * @param orgType 机构类型
	 * @param userId 用户id 
	 * @param date 日期
	 * @param repType 报表类型
	 * @return {@link Report)对象列表
	 */
	public List getReportsByTypes(Long reptypes, String date, Long userid,Long organType);

	/**
	 * 得到需要外汇生成的报表类型
	 * @param type
	 * @param userid
	 * @return
	 */
	public List getCurrencyReportTypes(int type,Long userid);
	
	/**
	 * 得到指定报表类型下需要外汇生成的报表
	 * @param buildOrCheck
	 * @param date
	 * @param type
	 * @param userid
	 * @return
	 */
	public List getCurrencyReportsFrequency(int buildOrCheck, String date,Long type ,Long userid);
	
	/**
	 * 得到类型下新的报表id
	 * @param leastId
	 * @param maximalId
	 * @param reportType
	 * @return
	 */
	public Long getNewReportId(Long leastId, Long maximalId, Long reportType);
	/**
	 * 2007-8-25 周浩 用于"报表克隆"
	 * 根据报表编码更改报表pkid
	 * @param code
	 * @param pkid
	 */
	public void updatePkidByCode(String code, Long pkid);
	
	/**
	 * 根据报表编码得到报表信息 周浩 070825
	 * @param code
	 * @return
	 */
	public Report getReportByCode(String code);
	
	/**
	 * 2007-8-25 周浩 用于"报表克隆"
	 * 将旧报表的科目拷贝到新的报表下
	 * @param newRepid
	 * @param oldRepid
	 */
	public void copyItems(Long newRepid, Long oldRepid);
	
	/**
	 * 2007-8-25 周浩 用于"报表克隆"
	 * 将旧报表的列拷贝到新的报表下
	 * @param newRepid
	 * @param oldRepid
	 */
	public void copyTargets(Long newRepid, Long oldRepid);
	/**
	 * 得到数据表表名
	 */
	public List<String> getDataTable();
	/**
	 * 根据表名得到数据列名称
	 * @param tabName
	 * @return
	 */
	public List getDataField(String tabName);
	
	/**
	 * 根据报表的频度和日期查询报表列表
	 * 
	 * @param freq
	 *            报表的频度
	 * @param reporttype
	 *            报表类型
	 * @return 报表对象列表
	 * 
	 * add by lxk _20071127 用于黑龙江统计分析查询调用 
	 */
	public List getFreqReport(String freq, String reporttype);
	
	/**
	 * 根据报表和日期(本期\年初日期)查询科目列表
	 * 
	 * @param reportId
	 *            报表ID
	 * @param curDate
	 *            本期
	 * @param datePB
	 *            年初日期
	 * @return 报表对象列表
	 * 
	 * add by lxk _20070709 用于黑龙江统计分析查询调用 
	 */
	public List getReportItems(Long reportId, String curDate, String datePB);
	/**
	 * 根据存储物理表名得到报表列表
	 * @param tableName
	 * @return {@ like Report}
	 */
	public List getReportTarget(String tableName);
	
	/**
	 * 得到智能流程显示报表(关联日期,频度,用户,机构)
	 * @param date
	 * @param userId
	 * @param organId
	 * @return
	 */
	public List getFlowTipReport(String date, Long userId, String organId);
	/**
	 * 智能流程提醒定义步骤显示报表
	 */
	public List getAddStepReportList(String date, Long userId);
	
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
	 * 查询报表,按照创建者过滤报表
	 * @param date
	 * @param userId
	 * @param reportType
	 * @return {@link Report)对象列表
	 */
	public List getReportByAuthor(String date, Long userId, Long reportType);
	
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
	 * 统计导出用
	 * @param systemId
	 * @return
	 */
	public Map getRepMapByCode();
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
	
	
	/**
	 * 根据机构,报表类型,用户,日期得到报表列表
	 * @param selectOrgId
	 * @param selectRepTypeId
	 * @param userId
	 * @param date
	 * @return {@link Object[]{OrganInfo Report})对象列表
	 */
	public List getSelectReportsByrepId(String selectOrgId, String selectRepId, Long userId, String date);
	
	/**
	 * 根据机构,报表类型,用户,日期得到报表列表
	 * @param selectOrgId
	 * @param selectRepTypeId
	 * @param userId
	 * @param date
	 * @return {@link Object[]{OrganInfo Report})对象列表
	 */
	public List getSelectReports2(String selectOrgId, String selectRepTypeId, Long userId, String date);
	
	/**根据配置表得到（上期，年初，上年同期审核）列值
	 * @param checkMode
	 * @param reportId
	 * @return
	 */
	public List getTargetsByConfig(int checkMode,Long reportId,String idx);
	
	public Map getReportMap();

	/**
	 * 根据报表Id,科目code得到科目信息
	 * @param reportId
	 * @param code
	 * @return
	 */
	public ReportItem getItem(Long reportId, String code);
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
	public List getBalancedReportForCollect(Long type, String date, 
			Long pkid, String organ);

	/**
	 *  2011-12-08周石磊
	 * 为了获取第一个类型下的第一张报表
	 * 按照报表类型排列
	 * @param dataDate
	 * @param pkid
	 * @return
	 */
	public List getReportsOrderByType(String dataDate, Long pkid); 
	
	public List getTypes(StringBuffer typeid);
	
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
	 * @param fileName
	 * @return
	 */
	public Report getReportByReportName(String ReportName);

	/**
	 * 获取审签后汇总的报表列表
	 * @param reptypes
	 * @param date
	 * @param userid
	 * @return
	 */
	public List getAuditCollectReportsByTypes(Long reptypes,String date,Long userid, String organ);
	/**
	 * 标准化 查询当前系统下报表
	 * @param replaceAll
	 * @param paramOrganType
	 * @param canEdit
	 * @param userId
	 * @param systemId
	 */
	public List getDateOrganEditReport(String paramDate,Integer paramOrganType,String canEdit,Long userId,String systemId,String levelFlag);

	/**
	 * 通过报表ID 获得列值
	 */
	public List   getTargetsByReportId( Long reportId);

	/**
	 * 标准化 查询模版指标
	 * @param parseLong
	 */
	public  List<ReportTarget> getTemplateTargets(Long templateId,Long modelId);
	
	/**
	 * 保存模版指标
	 * @param parseLong
	 * @param parseLong2
	 * @param split
	 */
	public void saveTemplateTargets(Long templateId, Long modelId,
			String[] targetIds1,String[] targetIds2);
	
	/**
	 * 适用于EAST标准化   gaozhongbo
	 * 保存模版指标
	 * @param parseLong
	 * @param parseLong2
	 * @param split
	 */
	public void saveTempTargets(String reportid, String report1,
			String[] targetIds1,String[] targetIds2);
	/**
	 * 增加模板  gaozhongbo
	 * @param parseLong
	 */
	public void saveTemplate(ReportTemplate reportTemp,String updatetemp);
	/**
	 * 获取所有模板  gaozhongbo
	 * @param parseLong
	 */
	public List getallTemplate(Long userid);
	/**
	 * 通过模板id获取模板详细信息  gaozhongbo
	 * @param parseLong
	 */
	public Map getTemplate(Long reportid);
	/**
	 * 通过模板id删除该模板  gaozhongbo
	 * @param parseLong
	 */
	public boolean delTemplate(String reportid);
	/**
	 * 获取模板类型  gaozhongbo
	 * @param parseLong
	 */
	public List getalltypes(Long userid);

	/**
	 * 删除模版指标
	 * @param parseLong
	 * @param parseLong2
	 * @param parseLong3
	 */
	public void delTemplateTarget(Long templateId, Long modelId,
			String targetId);

	/**
	 * 获取模版和模型的指标关系
	 * @param templateId
	 * @param modelId
	 * @return
	 */
	Map getTargetRelation(Long templateId, Long modelId);

	public List<ReportTarget> getReportTargets(Long reportId);

	 

	/**
	 * 
	 * @param targetlist
	 */
	public void saveReportTarget(List<ReportTarget> targetlist);

	public String getReportXML_temp(List reportList, Set set,String paramDate,Integer paramOrganType,String canEdit,Long userId,String systemId,String levelFlag);
	
	
}
