package com.krm.ps.sysmanage.reportdefine.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;

import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.model.xlsinit.util.XLSBuild;
import com.krm.ps.model.xlsinit.vo.DataSet;
import com.krm.ps.model.xlsinit.vo.RowSet;
import com.krm.ps.sysmanage.organmanage.dao.OrganInfoDAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.reportdefine.bo.StatReport;
import com.krm.ps.sysmanage.reportdefine.dao.ReportConfigDAO;
import com.krm.ps.sysmanage.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportItem;
import com.krm.ps.sysmanage.reportdefine.vo.ReportOrgType;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTemplate;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.reportdefine.vo.TargetTemplate;
import com.krm.ps.sysmanage.usermanage.dao.UserDAO;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.usermanage.vo.UserReport;
import com.krm.ps.util.Constant;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.Constants;
import com.krm.ps.util.ConvertUtil;

public class ReportDefineServiceImpl implements ReportDefineService {

	private ReportDefineDAO dao;
	private ReportConfigDAO rdao;
	private OrganInfoDAO odao;
	private UserDAO udao;

	
	
	
	public void setUserDAO(UserDAO udao) {
		this.udao = udao;
	}

	public void setOrganInfoDAO(OrganInfoDAO odao) {
		this.odao = odao;
	}

	public void setReportConfigDAO(ReportConfigDAO rdao) {
		this.rdao = rdao;
	}

	public void setReportDefineDAO(ReportDefineDAO dao) {
		this.dao = dao;
	}



	public ReportType getReportType(Long typeId) {
		Object o = dao.getObject(ReportType.class, typeId);
		if (null != o) {
			return (ReportType) o;
		}
		return null;
	}

	public List getReportTypes(Long userid) {
		return dao.getReportTypes(userid);
	}
	public List getReportTypes(String typeid) {
		return dao.getReportTypes(typeid);
	}


	// 得到需要汇总的报表类型（汇总模块使用）
	public List getReportTypesForCollect(Long userid) {
		return dao.getReportTypesForCollect(userid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.reportdefine.services.ReportDefineService#
	 * getReportTypesForExport(java.lang.Long)
	 */
	public List getReportTypesForExport(Long userid) {
		return dao.getReportTypesForCollect(userid);
	}

	// ////////////////////////////////////
	public List getReportTypesForGather(Long userid) {
		return dao.getReportTypesForGather(userid);
	}

	public int saveReportType(ReportType type) {
		if (dao.typeNameRepeat(type.getPkid(), type.getName())) {
			return ReportDefineService.NAME_REPEAT;
		}
		dao.saveObject(type);
		return ReportDefineService.SAVE_OK;
	}

	public void removeReportType(Long typeId) {
		dao.removeReportType(typeId);
	}

	public Report getReport(Long pkid) {
		Object o = dao.getObject(Report.class, pkid);
		Report report = null;
		if (null != o) {
			report = (Report) o;
			List organTypes = dao.getReportOrgTypes(report.getPkid());
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

	public List getReports(Report report, String date, Long userid) {
		return dao.getReports(report, date, userid,null);
	}

	public List getReportsByType(Long typeId, String date, Long userid) {
		return dao.getReportsByType(typeId, date, userid);
	}

	// 2006.9.22
	public List getReportsByTypeFrequencyDate(Long typeId, String frequencyId,
			String beginDateId, String endDateId, String date, Long userid) {
		return dao.getReportsByTypeFrequencyDate(typeId, frequencyId,
				beginDateId, endDateId, date, userid);
	}

	public List getReportsForGather(String date, Long userid) {
		return dao.getReportsForGather(date, userid);
	}

	public List getReports(String date, Long userid) {
		return dao.getReports(date, userid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.reportdefine.services.ReportDefineService#
	 * getReportsOrderByType(java.lang.String, java.lang.Long)
	 */
	public List getReportsOrderByType(String date, Long userid) {
		return dao.getReportsOrderByType(date, userid);
	}

	public void removeReport(Long pkid) {
		dao.removeReport(pkid);
	}

	/**
	 * 保存报表信息 by ydw
	 * 
	 * @param report
	 *            报表对象
	 * @param isUpdate
	 *            保存或更新
	 * @return
	 */
	public int saveReportIsLoger(Report report, int isUpdate,
			Report beforReport, User user, String time) {
		return saveReport(report, isUpdate);
	}

	public int saveReport(Report report, int isUpdate) {
//		if (dao.sthRepeat("Report", "pkid", report.getPkid(), "name",
//				report.getName())) {
//			return ReportDefineService.NAME_REPEAT;
//		}
		Integer show = dao.getShowOrderByType(report.getReportType());
		if (isUpdate == 0) {
			dao.updateShowOrder(show);
			report.setShowOrder(new Integer(show.intValue() + 1));
		} else {
			report.setShowOrder(dao.getReportShowOrder(report.getPkid()));
		}
		if (report.getCode() != null) {
			report.setCode(report.getCode().trim());
		}
		dao.saveObject(report);
		dao.delReportOrgTypes(report.getPkid());
		String[] types = report.getOrganType();
		if (null != types) {
			for (int i = 0; i < types.length; i++) {
				ReportOrgType orgType = new ReportOrgType();
				orgType.setReportId(report.getPkid());
				orgType.setOrganType(new Integer(types[i]));
				dao.saveObject(orgType);
			}
		}
		ReportType type = dao.getReportTypeByReportId(report.getPkid());
		List userReportList = udao.getUserReport(report.getCreateId()
				.intValue(), report.getPkid().intValue());
		if (userReportList.size() == 0) {
			UserReport userReport = new UserReport();
			userReport.setOperId(new Long(report.getCreateId().intValue()));
			userReport.setTypeId(type.getPkid());
			userReport.setReportId(report.getPkid());
			dao.saveObject(userReport);
		}
		return ReportDefineService.SAVE_OK;
	}

	public ReportItem getReportItem(Long pkid) {
		Object o = dao.getObject(ReportItem.class, pkid);
		if (null != o) {
			return (ReportItem) o;
		}
		return null;
	}

	public List getReportItems(Long reportId, String date) {
		return dao.getReportItems(reportId, date);
	}

	public List getReportItems(Long reportId) {
		return dao.getReportItems(reportId);
	}

	public List getCollectReportItems(Long reportId, String date) {
		return dao.getCollectReportItems(reportId, date);
	}

	public int saveReportItem(ReportItem item, int isUpdate) {
		Integer show = dao.getItemOrderByReport(item.getReportId());
		// 防止插入重复的科目code
		// 报表id
		Long reportId = item.getReportId();
		List allItemsInReport = dao.getReportItems(reportId);
		Iterator itr = allItemsInReport.iterator();
		while (itr.hasNext()) {
			ReportItem itemInItr = (ReportItem) itr.next();
			Long pkid = itemInItr.getPkid();
			if (!pkid.equals(item.getPkid())
					&& itemInItr.getCode().equals(item.getCode())) {
				return SAVE_ERROR_DUPLICATE_ITEM;
			}
		}

		if (isUpdate == 0) {
			// 插入
			item.setItemOrder((new Integer(show.intValue() + 1)));
		} else {
			// 更新
			item.setItemOrder(dao.getItemOrder(item.getPkid()));
			Long pkid = item.getPkid();
			ReportItem oldItem = getReportItem(pkid);
//			if (!item.getCode().equals(oldItem.getCode())) {
//				formulaDefineService.updateFormulaAsItemCodeChanged(reportId,
//						oldItem.getCode(), item.getCode());
//			}
		}
		if (item.getConCode() != null) {
			item.setConCode(item.getConCode().trim());
		}
		dao.saveReportItem(item);
		// 科目联动
		List configList = rdao.getReportConfigs(null, null, new Long(
				Constant.ITEMSETTING), reportId);
		if (configList.size() > 0) {
			for (Iterator iter = configList.iterator(); iter.hasNext();) {
				ReportConfig reportConfig = (ReportConfig) iter.next();
				ReportItem reportItem = new ReportItem();
				Report report = dao.getReportById(reportConfig.getReportId());
				reportItem.setReportId(reportConfig.getReportId());
				reportItem.setCode(item.getCode());
				reportItem.setConCode(item.getConCode());
				reportItem.setCreateDate(item.getCreateDate());
				reportItem.setStatus(item.getStatus());
				reportItem.setDataType(item.getDataType());
				reportItem.setBeginDate(item.getBeginDate());
				reportItem.setEndDate(item.getEndDate());
				reportItem.setFrequency(report.getFrequency());
				reportItem.setIsCollect(item.getIsCollect());
				reportItem.setIsLeaf(item.getIsLeaf());
				reportItem.setIsOrgCollect(item.getIsOrgCollect());
				reportItem.setItemName(item.getItemName());
				reportItem.setSuperId(item.getSuperId());
				reportItem.setItemOrder(item.getItemOrder());
				saveReportItem(reportItem, isUpdate);
			}
		}
		return SAVE_OK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#removeReportItem
	 * (java.lang.Long)
	 */
	public ReportItem removeReportItem(Long itemId, Long reportId) {
		ReportItem ri = getReportItem(itemId);
//		List result = formulaDefineService.removeFormulaByItem(
//				ri.getReportId(), ri.getCode());
//		if (result == null) {
//			/*
//			 * result列出被联动删除了的公式的列表。 如果为空，则表示删除出错。 这时向调用方返回要删除地科目。由调用方进行处理。
//			 */
//			return ri;
//		}
		// 科目联动
		List removeItems = new ArrayList();
		removeItems.add(itemId);
		List configList = rdao.getReportConfigs(null, null, new Long(
				Constant.ITEMSETTING), reportId);
		if (configList.size() > 0) {
			for (Iterator iter = configList.iterator(); iter.hasNext();) {
				ReportConfig reportConfig = (ReportConfig) iter.next();
				ReportItem reportItem = dao.getItem(reportConfig.getReportId(),
						ri.getCode());
//				if (reportItem != null) {
//					List formulaList = formulaDefineService
//							.removeFormulaByItem(reportConfig.getReportId(),
//									reportItem.getCode());
//					if (formulaList == null) {
//						/*
//						 * result列出被联动删除了的公式的列表。 如果为空，则表示删除出错。
//						 * 这时向调用方返回要删除地科目。由调用方进行处理。
//						 */
//						return reportItem;
//					}
					removeItems.add(reportItem.getPkid());
					// dao.removeReportItem(reportItem.getPkid());
//				}
			}
		}
		for (Iterator itr = removeItems.iterator(); itr.hasNext();) {
			Long id = (Long) itr.next();
			dao.removeReportItem(id);
		}
		// dao.removeReportItem(itemId);
		// 删除操作正常结束
		return null;
	}

	public ReportTarget getReportTarget(Long targetId) {
		Object o = dao.getObject(ReportTarget.class, targetId);
		if (null != o) {
			return (ReportTarget) o;
		}
		return null;
	}

	/**
	 * 根据 债券，股权类型查询报表
	 * @param reportId
	 * @return
	 */
	public List getReportTargetsStock(Long reportId,String stocktype) {
		return dao.getReportTargetsStock(reportId,stocktype);
	}
	
	public List getReportTargets(Long reportId) {
		return getReportTargets(reportId, null);
	}
	public List<ReportTarget> getReportTargets(Long reportId, String tableName) {
		List<ReportTarget> result = dao.getReportTargets(reportId);
		if(tableName == null){
			return result;
		}
		List<ReportTarget> reportTarget = dao.getReportTargetsBySRC(tableName);
		for(int i = 0; i < result.size(); i++){
			ReportTarget rt = result.get(i);
			for(int j = 0; j < reportTarget.size(); j++){
				ReportTarget temp = reportTarget.get(j);
				if(rt.getTargetField().trim().equals(temp.getTargetField().trim())){
					rt.setNowsize(temp.getRulesize());
					reportTarget.remove(j);
					break;
				}
			}
		}
		// 去掉主键，机构编码和报表日期
		for(int i = 0; i < reportTarget.size();){
			ReportTarget temp = reportTarget.get(i);
			if(temp.getTargetField().trim().equals("PKID") || temp.getTargetField().trim().equals("ORGAN_ID") || temp.getTargetField().trim().equals("REPORT_DATE")){
				reportTarget.remove(i);
				continue;
			}
			i++;
		}
		result.addAll(reportTarget);
		return result;
	}
	public List getCollectTarget(Long reportId){
		return dao.getCollectTarget(reportId);
	}
	public void saveReportTarget(ReportTarget target, int isUpdate) {
		Integer show = dao.getTargetOrderByReport(target.getReportId());
		Long reportId = new Long(0);
		String oldTargetField = "";
		if (isUpdate == 0) {
			// 插入
			target.setTargetOrder((new Integer(show.intValue() + 1)));
		} else {
			// 更新
			// 修改列的列号之后，要将原列的数据拷贝到新列。
			//reportId = target.getReportId();
			//ReportTarget oldTarget = getReportTarget(target.getPkid());
//			oldTargetField = oldTarget.getTargetField();
			// 修改列的列号之后，要将各公式中引用原列的地方，更新为引用新列。
//			if (!target.getTargetField().equals(oldTarget.getTargetField())) {
//				formulaDefineService.updateFormulaAsFieldIndexChanged(
//						target.getReportId(), oldTarget.getTargetField(),
//						target.getTargetField());
//			}
			//target.setTargetOrder(dao.getTargetOrder(target.getPkid()));
			// 剪切数据到新列
//			dao.cutDataByTarget(reportId, Integer.parseInt(oldTargetField),
//					Integer.parseInt(target.getTargetField()));
		}
		// 保存列定义
		dao.saveTarget(target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#removeReportTarget
	 * (java.lang.Long, java.lang.Long)
	 */
	public ReportTarget removeReportTarget(Long reportId, Long targetId) {
		ReportTarget rt = getReportTarget(targetId);
		// 删除列后，要将所有引用该列的公式同时删除。
		//List result = formulaDefineService.removeFormulaByField(reportId,
	//			rt.getTargetField());
	//	if (result == null) {
			/*
			 * result列出被联动删除了的公式的列表。 如果为空，则表示删除出错。 这时向调用方返回要删除地列。由调用方进行处理。
			 */
	//		return rt;
	//	}
		dao.removeReportTarget(reportId, targetId);
		// 删除操作正常结束。
		return null;
	}

	public void sort(String list) {
		if (null != list) {
			Report report = null;
			String[] orders = list.split(",");
			for (int i = 0; i < orders.length; i++) {
				Object o = dao.getObject(Report.class, new Long(orders[i]));
				if (null != o) {
					report = (Report) o;
					report.setShowOrder(new Integer(i));
					dao.saveObject(report);
				}
			}
		}
	}

	public List getOptions(List orgIds, String date, Long userid) {
		return dao.getOptions(orgIds, date, userid);
	}

	public List getOrgReports(List organs, String date, Long userid) {
		return dao.getOrgReports(organs, date, userid);
	}

	// 2006.9.23
	public List getDateOrganEditReport(String paramDate,
			Integer paramOrganType, String canEdit, Long userId) {
		return dao.getDateOrganEditReport(paramDate, paramOrganType, canEdit,
				userId);
	}

	// type 1:生成；2：检验；3：汇总
	public List getReportTypes(int type, Long userid) {
		return dao.getReportTypes(type, userid);
	}

	public List getReports(int buildOrCheck, String date, Long userid) {
		return dao.getReports(buildOrCheck, date, userid);
	}

	public List getItemByCode(Long reportId, String code) {
		return dao.getItemByCode(reportId, code);
	}

	public List getReportTypesForEdit(Long userid) {
		return dao.getReportTypesForEdit(userid);
	}

	// 传入表报类新返回表报
	public List getReportsByTypes(List reptypes, String date, Long userid) {
		return dao.getReportsByTypes(reptypes, date, userid);
	}

	// 2006.10.13
	public List getReportsByTypes(Long reptypes, String date, Long userid) {
		return dao.getReportsByTypes(reptypes, date, userid);
	}

	public List getAllReportTypes(String showlevel) {
		return dao.getAllReportTypes(showlevel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#insertReportItem
	 * (com.krm.slsint.reportdefine.vo.ReportItem, java.lang.Integer)
	 */
	public int insertReportItem(ReportItem reportItem, Integer showOrder) {
		// ADD by GuoYuelong @ 2006/10/19: edit sign = gyl_061019_05
		// 防止插入重复的科目code
		// 报表id
		Long reportId = reportItem.getReportId();
		String itemCode = reportItem.getCode();
		List allItemsInReport = dao.getReportItems(reportId);
		Iterator itr = allItemsInReport.iterator();
		while (itr.hasNext()) {
			ReportItem itemInItr = (ReportItem) itr.next();
			if (itemCode.equals(itemInItr.getCode())) {
				return SAVE_ERROR_DUPLICATE_ITEM;
			}
		}

		// 重新调整所有显示序号比showOrder大的其他科目的显示顺序
		dao.updateItemShowOrder(reportItem.getReportId(), showOrder);
		reportItem.setItemOrder(showOrder);
		if (reportItem.getConCode() != null) {
			reportItem.setConCode(reportItem.getConCode().trim());
		}
		dao.saveReportItem(reportItem);
		// ADD by GuoYuelong @ 2006/10/19: edit sign = gyl_061019_06

		// 科目联动
		List configList = rdao.getReportConfigs(null, null, new Long(
				Constant.ITEMSETTING), reportId);
		if (configList.size() > 0) {
			for (Iterator iter = configList.iterator(); iter.hasNext();) {
				ReportConfig reportConfig = (ReportConfig) iter.next();
				ReportItem item = new ReportItem();
				Report report = dao.getReportById(reportConfig.getReportId());
				item.setReportId(reportConfig.getReportId());
				item.setCode(reportItem.getCode());
				item.setConCode(reportItem.getConCode());
				item.setCreateDate(reportItem.getCreateDate());
				item.setStatus(reportItem.getStatus());
				item.setDataType(reportItem.getDataType());
				item.setBeginDate(reportItem.getBeginDate());
				item.setEndDate(reportItem.getEndDate());
				item.setFrequency(report.getFrequency());
				item.setIsCollect(reportItem.getIsCollect());
				item.setIsLeaf(reportItem.getIsLeaf());
				item.setIsOrgCollect(reportItem.getIsOrgCollect());
				item.setItemName(reportItem.getItemName());
				item.setSuperId(reportItem.getSuperId());
				item.setItemOrder(showOrder);
				insertReportItem(item, item.getItemOrder());
			}
		}

		return SAVE_OK;
	}

	/**
	 * 根据机构过滤报表
	 * 
	 * @param organ
	 * @return 属于此机构的报表id 修改列表 ---------------------------
	 * @version July 04, 2008：by 郭跃龙
	 *          增加根据机构报表权限代表用户过滤报表。如果funcconfig.properties文件中配置了
	 *          机构报表权限代表角色，则不再是简单的按照机构类型报表对照关系过滤报表，而是按照机构报表权限代表用户的报表权限过滤。
	 */
	public String[] filteReport(String organ, String[] reports) {
		String userrole = FuncConfig.getProperty("organReport.role");
		Set repId = dao.getReps(organ);
		if (userrole != null) {
			repId.clear();
			User uesr = udao.getAppointRoleUser(organ, new Long(userrole));
			// ��com.krm.slsint.reportdefine.dao.hibernate.
			// ReportDefineDAOHibernate.getReports(Report, String, Long)��û���õ�
			
			List reportList = dao.getReports("", uesr.getPkid());
			for (Iterator itr = reportList.iterator(); itr.hasNext();) {
				Report rep = (Report) itr.next();
				repId.add(rep.getPkid().toString());
			}

		}
		ArrayList r = new ArrayList();
		for (int i = 0; i < reports.length; i++) {
			if (repId.contains(reports[i])) {
				r.add(reports[i]);
			} else {
				// System.out.println("excl."+reports[i]);////
			}
		}
		String[] rr = new String[r.size()];
		for (int i = 0; i < r.size(); i++) {
			rr[i] = (String) r.get(i);
		}

		return rr;
	}

	public Set getRepByOrgan(String orgCode) {
		return dao.getReps(orgCode);
	}

	public List getReportsByType(Long typeId) {
		return dao.getReportsByType(typeId);
	}

	// 2006.9.26
	public boolean checkDataByItem(Long reportId) {
		return dao.checkDataByItem(reportId);
	}

	// 2006.9.27
	public void delItemByReportId(Long reportid) {
		dao.delItemByReportId(reportid);
	}

	public void delTargetByReportId(Long reportid) {
		dao.delTargetByReportId(reportid);
	}

	public void delFormatByReportId(Long reportid) {
		dao.delFormatByReportId(reportid);
	}

	public void delXmlByReportId(Long reportid) {
		dao.delXmlByReportId(reportid);
	}

	public void delOrgtypeByReportId(Long reportid) {
		dao.delOrgtypeByReportId(reportid);
	}

	public void delRuleByReportId(Long reportid) {
		dao.delRuleByReportId(reportid);
	}

	public void delDataByReportId(Long reportid) {
		dao.delDataByReportId(reportid);
	}

	public void delData1ByReportId(Long reportid) {
		dao.delData1ByReportId(reportid);
	}

	// Edit by GuoYuelong @ 2006/10/19: edit sign = gyl_061019_01
	public void delDataByItemId(Long reportId, String itemId) {
		// 删除引用了该科目的公式
//		formulaDefineService.removeFormulaByItem(reportId, itemId);
		// dao.delDataByItemId(itemId);
		dao.delDataByItemId(reportId, itemId);
	}

	// //Edit by GuoYuelong @ 2006/10/19: edit sign = gyl_061019_02
	// public void delData1ByItemId(Long reportId, String itemId){
	
	// formulaDefineService.removeFormulaByItem(reportId, itemId);
	// //dao.delData1ByItemId(itemId);
	// dao.delData1ByItemId(reportId, itemId);
	// }

	public void delDataByTarget(Long reportid) {
		dao.delDataByTarget(reportid);
	}

	public void delData1ByTarget(Long reportid) {
		dao.delData1ByTarget(reportid);
	}

	public boolean checkTargetOccupy(Long reportId, String targetField) {
		return dao.checkTargetOccupy(reportId, targetField);
	}

	// 2006.9.28
	public List getReportsFrequency(int buildOrCheck, String date, Long type,
			Long userid) {
		return dao.getReportsFrequency(buildOrCheck, date, type, userid);
	}

	/**
	 * 取多个类型的报表
	 * 
	 * @param buildOrCheck
	 * @param date
	 * @param types
	 * @param userid
	 * @see #getReportsFrequency
	 * @return
	 */
	public List getReportsByTypes(int buildOrCheck, String date,
			String[] types, Long userid) {
		return dao.getReportsByTypes(buildOrCheck, date, types, userid);
	}

	// 得到需要导出的统计上报人行报表（因为这些报表分属于四个类型，所以单独增加一个提取方法）
	// ADD BY DSZ 2006.9.30
	public List getReportsForStatExport() {
		return dao.getReportsForStatExport();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#checkReportCode
	 * (java.lang.Long)
	 */
	public boolean checkReportCode(String code) {
		return dao.checkReportCode(code);
	}

	/**
	 * 关联删除报表（同时把此报表相关的数据也删除）
	 * 
	 * @return
	 */
	public Long deleteReportAbout(Long reportId) {

		try {

			// 1.删除公式 先删除公式是因为需要根据能否删除公式判断联动删除是否可以进行。如果公式可以被联动删除，那么，以后的诸项都可以删除。
	//		List result = formulaDefineService.removeFormulaByReport(reportId);
//			if (result == null) {
			/*
			 * result列出被联动删除了的公式的列表。 如果为空，则表示删除出错。
			 * 这时向调用方返回要删除地报表id。由调用方进行处理。
			 */
//				Exception exception = new Exception("��������ʽ�����˸ñ��?�޷�ɾ��ñ���");
//				throw exception;
				// return reportId;
//			}

			// 2.删除科目
//			delItemByReportId(reportId);
			// 3.删除栏
			delTargetByReportId(reportId);
			// 4.删除报表格式
//			delFormatByReportId(reportId);
			// 5.删除XML
//			delXmlByReportId(reportId);
			// 6.删除报表机构类型对照
//			delOrgtypeByReportId(reportId);
			// 7.删除导入规则
//			delRuleByReportId(reportId);
			// 8.删除数据rep_data(分表存储也要删除)
//			delDataByReportId(reportId);
			// 9.删除数据rep_data1(分表存储也要删除)
//			delData1ByReportId(reportId);

			removeReport(reportId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return reportId;
		}
		return null;
	}

	/**
	 * 删除报表,status = 9
	 * 
	 * @param reportId
	 * @return
	 */
	public Long deleteReport(Long reportId) {
		return dao.deleteReport(reportId);
	}

	// 2006.11.1 zhaoPC
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.reportdefine.services.ReportDefineService#
	 * getReportsForStatExport(java.lang.String, java.lang.Long)
	 */
	public List getReportsForStatExport(String date, Long userId) {
		return dao.getReportsForStatExport(date, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#getCarryReportId
	 * (java.lang.Long)
	 */
	public Long getCarryReportId(Long reportId) {
		return dao.getCarryReportId(reportId);
	}

	public void updateUserReport(int reportId, int newType) {// wsx 12-21
		dao.updateUserReport(reportId, newType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#getStatReports
	 * (java.util.List)
	 */
	public List getStatReports(List reportList) {
		String repsId = "";
		for (Iterator itr = reportList.iterator(); itr.hasNext();) {
			Report rep = (Report) itr.next();
			if (repsId.equals("")) {
				repsId += rep.getPkid().toString();
			} else {
				repsId += "," + rep.getPkid().toString();
			}
		}
		// 根据报表id得到报表配置信息
		List repConfigList = rdao.getStatRepConfig(repsId, new Long(11));
		// 封装统计报表BO,如配置表中缺少个别报表的批次定义,则该报表将不被显示和导出数据
		List repsList = new ArrayList();
		for (Iterator itr = reportList.iterator(); itr.hasNext();) {
			StatReport statRep = new StatReport();
			Report rep = (Report) itr.next();
			for (Iterator it = repConfigList.iterator(); it.hasNext();) {
				Object[] l = (Object[]) it.next();
				if (rep.getPkid().equals(l[0])) {
					statRep.setPkid(rep.getPkid().toString());
					statRep.setName(rep.getName());
					statRep.setCode(rep.getCode());
					statRep.setBatchId(((Long) l[1]).toString());
					statRep.setBatchName((String) l[2]);
					statRep.setFrequency(rep.getFrequency());
					statRep.setReportType(rep.getReportType());
					repsList.add(statRep);
					break;
				}
			}
		}
		return repsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#getBatchList
	 * (java.util.List)
	 */
	public List getBatchList(List statRepList) {
		List batchList = new ArrayList();
		for (Iterator itr = statRepList.iterator(); itr.hasNext();) {
			StatReport statRep = (StatReport) itr.next();
			String[] str = new String[2];
			str[0] = statRep.getBatchId().toString();
			str[1] = statRep.getBatchName();
			batchList.add(str);
		}
		List list = this.removeRepeatBatch(batchList);
		return list;
	}

	// 去除重复
	private List removeRepeatBatch(List batchList) {
		List lst1 = new ArrayList();
		for (Iterator itr = batchList.iterator(); itr.hasNext();) {
			String[] aObj = (String[]) itr.next();
			boolean found = false;
			for (Iterator itr1 = lst1.iterator(); itr1.hasNext();) {
				String[] aObj1 = (String[]) itr1.next();
				if (aObj[0].equals(aObj1[0])) {
					found = true;
					break;
				}
			}
			if (!found) {
				lst1.add(aObj);
			}
		}
		String[] str = new String[2];
		str[0] = "ALL";
		str[1] = "全部";
		lst1.add(str);
		// 排序
		Collections.sort(lst1, new PsObjId());
		return lst1;
	}

	// 排序规则类
	class PsObjId implements Comparator {
		public int compare(Object obj1, Object obj2) {
			String[] o1 = (String[]) obj1;
			String[] o2 = (String[]) obj2;
			if (o1[0].charAt(0) > o2[0].charAt(0)) {
				return 1;
			}
			if (o1[0].charAt(0) < o2[0].charAt(0)) {
				return -1;
			}
			return 0;
		}
	}

	// 2007.3.24
	public List getReportTarget(Long reportId, String targetField) {
		return dao.getReportTarget(reportId, targetField);
	}

	// 2007.3.29
	public List getExtItem(String reportId, String itemCode, String num) {
		return dao.getExtItem(reportId, itemCode, num);
	}

	/**
	 * add by zhaoyi _20070331 得到所有的报表
	 * 
	 * @return
	 */
	public List getAllReports() {
		return dao.getAllReports();
	}

	public List getSelectReports(String selectOrgId, String selectRepTypeId,
			Long userId, String date) {
		String[] orgsId = selectOrgId.split(",");
		String[] repTypesId = selectRepTypeId.split(",");
		List expReps = new ArrayList();
		for (int i = 0; i < orgsId.length; i++) {
			OrganInfo orgInfo = odao.getOrganByCode(orgsId[i]);
			if (orgInfo != null) {
				for (int j = 0; j < repTypesId.length; j++) {
					List reps = dao.getReports(orgInfo.getOrgan_type()
							.toString(), userId, date, repTypesId[j]);
					if (reps != null) {
						for (Iterator itr = reps.iterator(); itr.hasNext();) {
							Report repInfo = (Report) itr.next();
							String[] expInfo = new String[4];
							expInfo[0] = orgInfo.getCode();
							expInfo[1] = orgInfo.getShort_name();
							expInfo[2] = repInfo.getPkid().toString();
							expInfo[3] = repInfo.getName();
							expReps.add(expInfo);
						}
					}
				}
			}
		}
		return expReps;
	}

	public List getReportsByTypes(Long reptypes, String date, Long userid,
			Long organType) {
		return dao.getReportsByTypes(reptypes, date, userid, organType);
	}

	public List getCurrencyReportTypes(int type, Long userid) {
		return dao.getCurrencyReportTypes(type, userid);
	}

	public List getCurrencyReportsFrequency(int buildOrCheck, String date,
			Long type, Long userid) {
		return dao
				.getCurrencyReportsFrequency(buildOrCheck, date, type, userid);
	}

	// add by zh
	public Long getNewReportId(Long leastId, Long maximalId, Long reportType) {
		return dao.getNewReportId(leastId, maximalId, reportType);
	}

	public void updatePkidByCode(String code, Long pkid) {
		dao.updatePkidByCode(code, pkid);
	}

	public Report getReportByCode(String code) {
		return dao.getReportByCode(code);
	}

	public void copyItems(Long newRepid, Long oldRepid) {
		dao.copyItems(newRepid, oldRepid);
	}

	public void copyTargets(Long newRepid, Long oldRepid) {
		dao.copyTargets(newRepid, oldRepid);
	}

	/**
	 *得到数据表表名
	 */
	public List<String> getDataTable() {
		List<String> dataTableList = dao.getDataTable();
		return dataTableList;
	}

	/**
	 * 根据表名得到数据列名称
	 * 
	 * @param tabName
	 * @return
	 */
	public List getDataField(String tabName) {
		return dao.getDataField(tabName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#getFreqReport
	 * (java.lang.String, java.lang.String) 2007.07.09 lxk 用于黑龙江统计分析查询调用
	 */
	public List getFreqReport(String freq, String reporttype) {
		return dao.getFreqReport(freq, reporttype);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#getReportItems
	 * (java.lang.Long, java.lang.String��java.lang.String) 2007.07.09 lxk
	 * 用于黑龙江统计分析查询调用
	 */
	public List getReportItems(Long reportId, String curDate, String datePB) {
		return dao.getReportItems(reportId, curDate, datePB);
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#getReportTarget
	 * (java.lang.String) 
	 */
	public List getReportTarget(String tableName) {
		return dao.getReportTarget(tableName);
	}

	/**
	 * 得到智能流程显示报表(关联日期,频度,用户,机构)
	 * 
	 * @param date
	 * @param userId
	 * @param organId
	 * @return
	 */
	public List getFlowTipReport(String date, Long userId, String organId) {
		return dao.getFlowTipReport(date, userId, organId);
	}

	/**
	 * 智能流程提醒定义步骤显示报表
	 */
	public List getAddStepReportList(String date, Long userId) {
		return dao.getAddStepReportList(date, userId);
	}

	public List getPdfReportsByOrganType(OrganInfo organInfo, String date) {
		return dao.getPdfReportsByOrganType(organInfo, date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.reportdefine.services.ReportDefineService#
	 * getAutoBuildReportsByOrganType(java.lang.String, java.lang.String)
	 */
	public List getAutoBuildReportsByOrganType(String organType, String date) {
		return dao.getAutoBuildReportsByOrganType(organType, date);
	}

	public List getAutoPdfByOrganType(String organType, String date) {
		return dao.getAutoPdfByOrganType(organType, date);
	}

	// 查询报表,按照创建者过滤报表
	public List getReportByAuthor(String date, Long userId, Long reportType) {
		return dao.getReportByAuthor(date, userId, reportType);
	}

	public List getReportsByFunction(String userId, String date, String funId,
			String def) {
		return dao.getReportsByFunction(userId, date, funId, def);
	}

	/**
	 * 根据条件判断是否有符合条件的报表
	 * 
	 * @param paramDate
	 *            日期
	 * @param organ
	 *            机构编码
	 * @param canEdit
	 * @param userId
	 * @return boolean
	 */
	public boolean isReportExist(String paramDate, String organ,
			String canEdit, Long userId) {
		return dao.isReportExist(paramDate, organ, canEdit, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#getRepMapByCode
	 * ()
	 */
	public Map getRepMapByCode() {
		Map map = new HashMap();
		List reportList=dao.getReports(new Report(), null, null, null);
		for (Iterator itr = reportList.iterator(); itr.hasNext();) {
			Report r = (Report) itr.next();
			map.put(r.getCode(), r.getName());
		}
		return map;
	}

	/**
	 * 根据报表ID取其所有的\有序的可用科目
	 * 
	 * @param reportId
	 * @return
	 */
	public List getItemByRepId(Long reportId) {
		return dao.getItemByRepId(reportId);
	}

	/**
	 * @see com.krm.slsint.reportdefine.services.ReportDefineService#getReport(java.lang.String,
	 *      java.lang.Long, java.lang.String)
	 */
	public Report getReport(String code, Long reportType, String date) {
		return dao.getReport(code, reportType, date);
	}

	/**
	 * @see com.krm.slsint.reportdefine.services.ReportDefineService#getCanInputReportIdList(java.lang.String)
	 */
	public List getCanInputReportIdList(String reportIds) {
		return dao.getCanInputReportIdList(reportIds);
	}

	public List getSelectReports2(String selectOrgId, String selectRepTypeId,
			Long userId, String date) {
		String[] orgsId = selectOrgId.split(",");
		String[] repTypesId = selectRepTypeId.split(",");
		List expReps = new ArrayList();
		for (int i = 0; i < orgsId.length; i++) {
			OrganInfo orgInfo = odao.getOrganByCode(orgsId[i]);
			if (orgInfo != null) {
				for (int j = 0; j < repTypesId.length; j++) {
					List reps = dao.getReports(orgInfo.getOrgan_type()
							.toString(), userId, date, repTypesId[j]);
					if (reps != null) {
						for (Iterator itr = reps.iterator(); itr.hasNext();) {
							Report repInfo = (Report) itr.next();
							// String [] expInfo = new String[4];
							Object[] expInfo = new Object[] { orgInfo, repInfo };
							/*
							 * expInfo[0] = orgInfo.getCode(); expInfo[1] =
							 * orgInfo.getShort_name(); expInfo[2] =
							 * repInfo.getPkid().toString(); expInfo[3] =
							 * repInfo.getName();
							 */
							expReps.add(expInfo);
						}
					}
				}
			}
		}
		return expReps;
	}

	public List getSelectReportsByrepId(String selectOrgId, String selectRepId,
			Long userId, String date) {
		String[] orgsId = selectOrgId.split(",");
		String[] repsId = selectRepId.split(",");
		List expReps = new ArrayList();
		for (int i = 0; i < orgsId.length; i++) {
			OrganInfo orgInfo = odao.getOrganByCode(orgsId[i]);
			if (orgInfo != null) {
				for (int j = 0; j < repsId.length; j++) {
					List reps = dao.getReportsByCodes(repsId[j], date, userId);
					if (reps != null) {
						for (Iterator itr = reps.iterator(); itr.hasNext();) {
							Report repInfo = (Report) itr.next();
							// String [] expInfo = new String[4];
							Object[] expInfo = new Object[] { orgInfo, repInfo };
							/*
							 * expInfo[0] = orgInfo.getCode(); expInfo[1] =
							 * orgInfo.getShort_name(); expInfo[2] =
							 * repInfo.getPkid().toString(); expInfo[3] =
							 * repInfo.getName();
							 */
							expReps.add(expInfo);
						}
					}
				}
			}
		}
		return expReps;
	}

	/**
	 * 根据配置表得到（上期，年初，上年同期审核）列值
	 * 
	 * @param checkMode
	 * @param reportId
	 * @return
	 */
	public List getTargetsByConfig(int checkMode, Long reportId, String idx) {
		return dao.getTargetsByConfig(checkMode, reportId, idx);
	}

	public Map getReportMap() {
		return dao.getReportMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.krm.slsint.reportdefine.services.ReportDefineService#getItem(java
	 * .lang.Long, java.lang.String)
	 */
	public ReportItem getItem(Long reportId, String code) {
		return dao.getItem(reportId, code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.reportdefine.services.ReportDefineService#
	 * getReportsFrequencyBalanceOK(int, java.lang.String, java.lang.Long,
	 * java.lang.Long, java.lang.String)
	 */
	public List getBalancedReportsForDataBuild(int buildOrCheck, String date,
			Long reportType, Long userId, String organCode) {
		return dao.getBalancedReportsForDataBuild(buildOrCheck, date,
				reportType, userId, organCode);
	}

	public List getBalancedReportForCollect(Long type, String date, Long pkid,
			String organ) {
		return dao.getBalancedReportForCollect(type, date, pkid, organ);
	}

	public List getAuditCollectReportsByTypes(Long reptypes, String date,
			Long userid, String organ) {
		return dao.getAuditCollectReportsByTypes(reptypes, date, userid, organ);
	}

	public List getTypes(StringBuffer typeid) {
		return dao.getTypes(typeid);
	}

	public List getReportsToListByIds(String reportIds, String reportDate) {
		return dao.getReportsToListByIds(reportIds, reportDate);
	}

	public Report getFirstReportOrderByReportType(String dataDate, Long pkid) {
		return dao.getFirstReportOrderByReportType(dataDate, pkid);
	}

	public Report getReportByReportName(String ReportName) {
		return dao.getReportByReportName(ReportName);
	}

	public List getDateOrganEditReport(String paramDate,
			Integer paramOrganType, String canEdit, Long userId,
			String systemId, String levelFlag) {
		return dao.getDateOrganEditReportForStandard(paramDate, paramOrganType,
				canEdit, userId, systemId, levelFlag);
	}
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
				String systemId, String levelFlag){
			return dao.getDateOrganEditReportForYJH(paramDate, paramOrganType,
					canEdit, userId, systemId, levelFlag);
		}
	public List getReportByYJH(String ismodel) {
		return dao.getReportByYJH(ismodel);
	}
	/**
	 * 通过报表ID 获得列值
	 */
	public List   getTargetsByReportId( Long reportId){
		return dao.getTargetsByReportId(reportId);
	}
	
	public Map getTargetRelation(Long templateId, Long modelId) {
		List targetRelation = dao.getTargetRelation(templateId, modelId);
		Map<String,String> targetMap = new HashMap<String,String>();
		for(int i=0;i<targetRelation.size();i++) {
			Object[] arr = (Object[]) targetRelation.get(i);
			String modelTarget = (String) arr[0];
			String templateTarget = (String) arr[1];
			targetMap.put(modelTarget, templateTarget);
		}
		return targetMap;
	}


	@Override
	public List<ReportTarget> getTemplateTargets(Long templateId, Long modelId) {
		return dao.getTemplateTargets(templateId, modelId);
	}
	/**
	 * 保存修改的模板
	 */
	@Override
	public void saveTemplateTargets(Long templateId, Long modelId,
			String[] targetIds1, String[] targetIds2) {
		 //以下要删除的指标的逻辑
		if (targetIds1 != null) {
			for (String targetId : targetIds1) {
				dao.delTemplateTargets(templateId, modelId,
						targetId); //删除模板指标，以及对应关系 
			}
		}
		
	    //int order = updateTargetFieldAndOrder(templateId,modelId);//删除模板指标后重新排序，返回排序值（为下面插入做准备），并且 更新template_model表
		//以下是要增加指标的逻辑 
		if (targetIds2 != null) {
			Map<String,String> targetMap = new HashMap<String,String>();

			for (String targetField : targetIds2) {
				List reportTargets = getReportTarget(modelId,targetField);
				ReportTarget rt = (ReportTarget) reportTargets.get(0); 
				ReportTarget rt1 = new ReportTarget();
				try {
					ConvertUtil.copyProperties(rt1, rt, true);
//					if(charCount.equals(new Integer(21))) {
//						charCount = 51;
//					}
					if(1==rt1.getDataType()) {
						rt1.setTargetField( targetField );
					} else {
						rt1.setTargetField( targetField );
					}
					rt1.setPkid(null);
					rt1.setReportId(templateId);
				
					Long pkid = dao.insertTarget(rt1); //保存指标
					targetMap.put(rt1.getTargetField(),rt.getTargetField());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			dao.updateTargetRelation(templateId, modelId, targetMap);
		}
	}

	/**
	 * 删除模板一部分指标后，对模板剩余指标进行排序整理,防止再对补录模板添加指标时，大于80列
	 * 注意：在排序时，在及时更新template_model表
	 * @param templateId
	 * @return
	 */
	private int updateTargetFieldAndOrder(Long templateId,Long modelId) {
		List reportTargets = getReportTargets(templateId); //获得补录模板对应的所有指标
		int order = 1 ;
		int charOrder = 1;
		int numOrder = 21;
		for(int i=0;i<reportTargets.size(); i++)  {
			ReportTarget rt = (ReportTarget) reportTargets.get(i);
			String oldTargetField = rt.getTargetField();  //由于要调itemvalue值，所以要先记录一下老值
			if(1==rt.getDataType()) {
				rt.setTargetField(String.valueOf(numOrder++));
			}else {
				if(charOrder == 21) {
					charOrder =51;
				}
				rt.setTargetField(String.valueOf(charOrder++));
			}
			rt.setTargetOrder(order++);
			saveReportTarget(rt, 1); //更新指标，把更新过的指标，保存
			dao.updateTemplateTargetField(templateId,modelId,oldTargetField,rt.getTargetField()); //����template_model��
		}
		return order;
	}

	@Override
	public void delTemplateTarget(Long templateId, Long modelId, String targetField) {
		dao.delTemplateTarget(templateId, modelId, targetField);
		//updateTargetFieldAndOrder(templateId,modelId);
	}
	
	public void saveTemplate(ReportTemplate reportTemp,String updatetemp){
		dao.saveTemplate(reportTemp,updatetemp);
	}

	@Override
	public List getallTemplate(Long userid) {
		// TODO Auto-generated method stub
		List alltemp = dao.getallTemplate(userid);
		return updaTempTypesname(alltemp);
	}
	//将模板类型的id转为name
	private List updaTempTypesname(List temlist){
		String typename = "";
		ReportTemplate re;
		for(int i = 0;i<temlist.size();i++){
			re = (ReportTemplate) temlist.get(i);
			typename = dao.getTypesname(Long.toString(re.getReporttype()));
			re.setRetypename(typename);
			temlist.set(i, re);
		}
		return temlist;
	}
	@Override
	public List getalltypes(Long userid) {
		// TODO Auto-generated method stub
		return dao.allReportTypes(userid);
	}

	@Override
	public Map getTemplate(Long reportid) {
//		// TODO Auto-generated method stub
//		List<TemplateModel> list=dao.getTemplates(reportid);
//		Map temMap=new HashMap();
//		for(TemplateModel tt:list){
//			temMap.put(tt.getTemplateTarget(),tt.getModelTarget()+";"+tt.getModelid());
//		}
//		return  temMap;
		return null;
				
	}

	@Override
	public boolean delTemplate(String reportid) {
		// TODO Auto-generated method stub
		return dao.delTemplates(reportid);
	}


	@Override
	public ReportTemplate getTempReport(Long reportid) {
		// TODO Auto-generated method stub
		
		List reporttemp = dao.getTemplates(reportid);
			
		return (ReportTemplate)reporttemp.get(0);
	}

	@Override
	public List getTempTarget(String reportid, String reportid1,String flag) {
		// TODO Auto-generated method stub
		return dao.getTempTarget(reportid, reportid1,flag);
	}

	@Override
	public void saveTempTargets(String reportid, String reportid1,
			String[] targetIds1, String[] targetIds2) {
		// TODO Auto-generated method stub
		
		String targetId1 = null;
		String targetId2 = null;
		TargetTemplate target = null;
		if(targetIds1 != null && targetIds1.length > 0 ){
			for(int i = 0;i < targetIds1.length;i++){
				targetId1 = targetIds1[i];
				target = getTarget(reportid, reportid1, targetId1);
				dao.deleTempTarget(reportid, reportid1,target);
			}
		}
		
		if(targetIds2 != null && targetIds2.length > 0){
			for(int i = 0;i < targetIds2.length;i++){
				targetId2 = targetIds2[i];
				target = getTarget(reportid, reportid1, targetId2);
				dao.saveTempTarget(reportid, reportid1, target);
			}
		}	
	}
	private TargetTemplate getTarget(String reportid,String reportid1,String targetid){
		return dao.getTarget(reportid, reportid1, targetid);
	}
	


	@Override
	public void saveReportTarget(List<ReportTarget> targetlist) {
		int maxOrderNum = 0;
		for(int i = 0; i < targetlist.size(); i++){
			ReportTarget rt = targetlist.get(i);
			if(rt.getTargetOrder() == null && maxOrderNum == 0){
				maxOrderNum = dao.getMaxOrderNum(rt.getReportId()) + 1;
				rt.setTargetOrder(maxOrderNum);
			} else {
				rt.setTargetOrder(++maxOrderNum);
			}
			dao.saveReportTarget(rt);
		}
	}

	@Override
	public int XLSInit(FormFile data, String filename, String organId, String dataDate)
			 {
		 //读入excel数据，构造数据集
		try{
			 DataSet ds = XLSBuild.constructData(data);
			   
			   String version  = FuncConfig.getProperty("east.version");
			    if (version.equals("2")) {
			    	createSql1(ds,filename, organId,dataDate);
				}else if(version.equals("1")){
					createSql(ds,filename, organId);		
				}
			      
			   checkDataXLS(filename,organId);
			   return 0;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return 11;
		}
		  
	   }
	
	public int XLSInit(FormFile data)
	 {
		//读入excel数据，构造数据集
		try{
			 DataSet ds = XLSBuild.constructDataTotitle(data);
		
			 List xlsdata=ds.getXlsData();
			if(xlsdata.size()==0){
				return -1;
			}
			int a = 0;
			for(int i=0;i<xlsdata.size();i++){
				RowSet rowDatas=(RowSet) xlsdata.get(i);
				Map datas=rowDatas.getRowData(); 
				String cwyu ="" ;
				if( !"null".equals(datas.get("错误数据原因解释")) && datas.get("错误数据原因解释") != null ){
					cwyu =datas.get("错误数据原因解释").toString();
				}
				//{错误条数=0, 检核规则描述=银行机构代码字符长度必须为12,且不为空, 总量=100149, 报表名称=机构信息表, 机构名称=滨海农商行(全辖汇总), 检核规则序号=JHGZ00001, 检核规则=格式校验, 数据批次=20191031, 错误率=0%, 指标名称=银行机构代码, 错误数据原因解释=null}
				 String qsb="update CODE_REP_JHGZ set REASONS='"+cwyu+"' where JHGZ='"+datas.get("检核规则序号")+"' and DATABATCH='"+datas.get("数据批次")+"'  and  ERRORRATE<>'0' "   ;
				
				 a +=dao.insertDataF(qsb);
			}
			return a;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return -1;
		}
		 
	}
	
	public int XLSInitZf(FormFile data)
	 {
		//读入excel数据，构造数据集
		try{
			 DataSet ds = XLSBuild.constructDataTotitle(data);
		
			 List xlsdata=ds.getXlsData();
			 if(xlsdata.size()==0){
					return -1;
			 }
			 int a = 0;
			for(int i=0;i<xlsdata.size();i++){
				RowSet rowDatas=(RowSet) xlsdata.get(i);
				Map datas=rowDatas.getRowData(); 
				String cwyu ="" ;
				if( !"null".equals(datas.get("差异原因解释")) && datas.get("差异原因解释") != null ){
					cwyu =datas.get("差异原因解释").toString();
				}
				//{错误条数=0, 检核规则描述=银行机构代码字符长度必须为12,且不为空, 总量=100149, 报表名称=机构信息表, 机构名称=滨海农商行(全辖汇总), 检核规则序号=JHGZ00001, 检核规则=格式校验, 数据批次=20191031, 错误率=0%, 指标名称=银行机构代码, 错误数据原因解释=null}
				 String qsb="update CODE_REP_JHGZZF set REASONS='"+cwyu+"' where  ERRORRATE<>'0' and  pkid="+datas.get("pkid")   ;
				
				 a += dao.insertDataF(qsb);
			}
			return a;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return -1;
		}
		 
	}
	
	//组织sql  filename格式：信贷资产转让_1047_20140131.xlsx
	   public String[] createSql(DataSet ds,String filename, String organId){
		String[] names=filename.split("_");
		List xlsdata=ds.getXlsData();
		//String[] sbu=new String[xlsdata.size()];
		String reportdate=names[2].substring(0,6)+"00";
		//获取f表表名
		List<?> list=rdao.getdefChar(new Long(names[1]),new Long(33));
	    ReportConfig rc=(ReportConfig)list.get(0);
	    String tablename=rc.getDefChar();
	    tablename=tablename.replaceAll("\\{M\\}",names[2].substring(4,6));
	    tablename=tablename.replaceAll("\\{D\\}",names[2].substring(6,8));
	    tablename=tablename.replaceAll("\\{Y\\}",names[2].substring(0,4));
	 // 获得模板的栏
		List <ReportTarget> reportTargetList=dao.getReportTargets(new Long(names[1]));
		for(int i=0;i<xlsdata.size();i++){
			String qsb="insert into "+tablename+" (pkid,REPORT_DATE " ;
		//	String hsb=" values( REP_DATAM_PKID_SEQ.NEXTVAL,'"+names[2].substring(0,8)+"'";
			String hsb=" values( REP_DATAM_PKID_SEQ.NEXTVAL,'"+reportdate+"'";
			RowSet rowDatas=(RowSet) xlsdata.get(i);
			Map datas=rowDatas.getRowData(); //���б������ݵ�map
			boolean flag  = false;
			for(ReportTarget rt:reportTargetList){
				if("内部机构号".equals(rt.getTargetName().trim())){
					flag = true;
					qsb+=",ORGAN_ID,"+rt.getTargetField();
					hsb+=",'"+datas.get(rt.getTargetName().trim())+"','"+datas.get(rt.getTargetName().trim())+"'";
				}else if(rt.getDataType()==1){
					qsb+=","+rt.getTargetField();
					hsb+=","+datas.get(rt.getTargetName().trim());
				}else if(rt.getDataType()==3){
					qsb+=","+rt.getTargetField();
					hsb+=",'"+datas.get(rt.getTargetName().trim())+"'";
				}				
			}
			if(!flag){
				qsb += ",ORGAN_ID ";
				hsb += ",'"+organId+"'";
			}
			qsb+=") ";
			hsb+=") ";
			String sqlsb=qsb+hsb;
			
			dao.insertDataF(sqlsb);
		}
		return null;
		
	   }
	 //组织sql  filename格式：信贷资产转让_1047_20140131.xlsx
	   public String[] createSql1(DataSet ds,String filename, String organId,String dataDate){
		String[] names=filename.split("_");
		List xlsdata=ds.getXlsData();
		//String[] sbu=new String[xlsdata.size()];
		String reportdate=names[2].substring(0,8);
		//获取f表表名
		List<?> list=rdao.getdefChar(new Long(names[1]),new Long(33));
	    ReportConfig rc=(ReportConfig)list.get(0);
	    String tablename=rc.getDefChar();
	    tablename=tablename.replaceAll("\\{M\\}",names[2].substring(4,6));
	    tablename=tablename.replaceAll("\\{D\\}",names[2].substring(6,8));
	    tablename=tablename.replaceAll("\\{Y\\}",names[2].substring(0,4));
	 // 获得模板的栏
		List <ReportTarget> reportTargetList=dao.getReportTargets(new Long(names[1]));
		for(int i=0;i<xlsdata.size();i++){
			String qsb="insert into "+tablename+" (pkid" ;
		//	String hsb=" values( REP_DATAM_PKID_SEQ.NEXTVAL,'"+names[2].substring(0,8)+"'";
			String hsb=" values( REP_DATAM_PKID_SEQ.NEXTVAL";
			RowSet rowDatas=(RowSet) xlsdata.get(i);
			Map datas=rowDatas.getRowData(); 
			boolean flag  = false;
			String qhdelcjrq = FuncConfig.getProperty("qh.delcjrq", "1");
			for(ReportTarget rt:reportTargetList){
				if("内部机构号".equals(rt.getTargetName().trim())){
					flag = true;
					qsb+=","+rt.getTargetField();
					hsb+=",'"+datas.get(rt.getTargetName().trim())+"'";
				}else 	if("CJRQ".equals(rt.getTargetField())){
					if ("yes".equals(qhdelcjrq)) {  // #青海→数据补录→导入数据模板去掉 cjrq（采集日期）字段
						qsb+=",cjrq";
						hsb+=",'"+dataDate+"'";
					}else{
						if (datas.get(rt.getTargetName().trim())==null || "".equals(datas.get(rt.getTargetName().trim()))) {
							qsb+=","+rt.getTargetField();
							hsb+=",'"+dataDate+"'";
						}else{
							qsb+=","+rt.getTargetField();
							hsb+=",'"+datas.get(rt.getTargetName().trim())+"'";
						}
					}
				}else if(rt.getDataType()==1){
					qsb+=","+rt.getTargetField();
					hsb+=","+datas.get(rt.getTargetName().trim());
				}else if(rt.getDataType()==3){
					qsb+=","+rt.getTargetField();
					hsb+=",'"+datas.get(rt.getTargetName().trim())+"'";
				}				
			}
			qsb+=") ";
			hsb+=") ";
			String sqlsb=qsb+hsb;
			
			dao.insertDataF(sqlsb);
		}
		return null;
		
	   }
	   public void checkDataXLS(String filename,String organId){
		   String[] names=filename.split("_");
		 //获取结果表表名
		   List<?> list=rdao.getdefChar(new Long(names[1]),new Long(34));
		   if(list!=null&&list.size()>0){
		    ReportConfig rc=(ReportConfig)list.get(0);
		    String tablename=rc.getDefChar();
		    tablename=tablename.replaceAll("\\{M\\}",names[2].substring(4,6));
		    tablename=tablename.replaceAll("\\{D\\}",names[2].substring(6,8));
		    tablename=tablename.replaceAll("\\{Y\\}",names[2].substring(0,4));
		   List<ReportRule> reportruleList=dao.getReportRuleByReportId(names[1]);
		   for(ReportRule rr:reportruleList){
			   dao.deleteReportResult("'"+rr.getRulecode()+"'","0",tablename,organId);
			 //  dao.checkData(rr.getRulecode(),names[2].substring(0,8));
		   }
		   }
	   }

	@Override
	public String getReportXML_temp(List reportList, Set set,String paramDate,Integer paramOrganType,String canEdit,Long userId,String systemId,String levelFlag) {
		return dao.getReportXML_temp( reportList, set,  paramDate,  paramOrganType,  canEdit,  userId,  systemId,  levelFlag);
	}
	 
}
