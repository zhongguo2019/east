package com.krm.ps.model.reportdefine.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krm.ps.model.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.model.reportdefine.services.ReportDefineService;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportOrgType;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.usermanage.dao.UserDAO;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.usermanage.vo.UserReport;
import com.krm.ps.util.ConvertUtil;

public class ReportDefineServiceImpl implements ReportDefineService {

	private ReportDefineDAO rdreportDefineDAO;
	private UserDAO userDAO;

	public ReportDefineDAO getRdreportDefineDAO() {
		return rdreportDefineDAO;
	}

	public void setRdreportDefineDAO(ReportDefineDAO rdreportDefineDAO) {
		this.rdreportDefineDAO = rdreportDefineDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void sort(String list) {
	}

	@Override
	public List getAllReportTypes(String showlevel) {
		return rdreportDefineDAO.getAllReportTypes(showlevel);
	}

	@Override
	public ReportType getReportType(Long typeId) {
		Object o = rdreportDefineDAO.getObject(ReportType.class, typeId);
		if (null != o) {
			return (ReportType) o;
		}
		return null;
	}

	@Override
	public void removeReportType(Long typeId) {
		rdreportDefineDAO.removeReportType(typeId);
	}

	@Override
	public int saveReportType(ReportType type) {
		if (rdreportDefineDAO.typeNameRepeat(type.getPkid(), type.getName())) {
			return ReportDefineService.NAME_REPEAT;
		}
		rdreportDefineDAO.saveObject(type);
		return ReportDefineService.SAVE_OK;
	}

	@Override
	public List getReportTypes(Long userid) {
		return rdreportDefineDAO.getReportTypes(userid);
	}

	@Override
	public List getReports(Report report, String date, Long userid) {
		return rdreportDefineDAO.getReports(report, date, userid, "1");
	}

	@Override
	public List getReportsByTypeFrequencyDate(Long typeId, String frequencyId,
			String beginDateId, String endDateId, String date, Long userid) {
		return rdreportDefineDAO.getReportsByTypeFrequencyDate(typeId,
				frequencyId, beginDateId, endDateId, date, userid);
	}

	@Override
	public List getReports(String date, Long userid) {
		return rdreportDefineDAO.getReports(date, userid);
	}

	@Override
	public Report getReport(Long pkid) {
		Object o = rdreportDefineDAO.getObject(Report.class, pkid);
		Report report = null;
		if (null != o) {
			report = (Report) o;
			List organTypes = rdreportDefineDAO.getReportOrgTypes(report
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

	public List<String> getDataTable() {
		List<String> dataTableList = rdreportDefineDAO.getDataTable();
		return dataTableList;
	}

	@Override
	public void updateUserReport(int reportId, int newType) {
		rdreportDefineDAO.updateUserReport(reportId, newType);
	}

	public int saveReportIsLoger(Report report, int isUpdate,
			Report beforReport, User user, String time) {
		return saveReport(report, isUpdate);
	}

	public int saveReport(Report report, int isUpdate) {
		Integer show = rdreportDefineDAO.getShowOrderByType(report
				.getReportType());
		if (isUpdate == 0) {
			rdreportDefineDAO.updateShowOrder(show);
			report.setShowOrder(new Integer(show.intValue() + 1));
		} else {
			report.setShowOrder(rdreportDefineDAO.getReportShowOrder(report
					.getPkid()));
		}
		if (report.getCode() != null) {
			report.setCode(report.getCode().trim());
		}
		rdreportDefineDAO.saveObject(report);
		rdreportDefineDAO.delReportOrgTypes(report.getPkid());
		String[] types = report.getOrganType();
		if (null != types) {
			for (int i = 0; i < types.length; i++) {
				ReportOrgType orgType = new ReportOrgType();
				orgType.setReportId(report.getPkid());
				orgType.setOrganType(new Integer(types[i]));
				rdreportDefineDAO.saveObject(orgType);
			}
		}
		ReportType type = rdreportDefineDAO.getReportTypeByReportId(report
				.getPkid());
		List userReportList = userDAO.getUserReport(report.getCreateId()
				.intValue(), report.getPkid().intValue());
		if (userReportList.size() == 0) {
			UserReport userReport = new UserReport();
			userReport.setOperId(new Long(report.getCreateId().intValue()));
			userReport.setTypeId(type.getPkid());
			userReport.setReportId(report.getPkid());
			rdreportDefineDAO.saveObject(userReport);
		}
		return ReportDefineService.SAVE_OK;
	}

	public Long deleteReportAbout(Long reportId) {

		try {
			removeReport(reportId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return reportId;
		}
		return null;
	}

	public void removeReport(Long pkid) {
		rdreportDefineDAO.removeReport(pkid);
	}

	public Long deleteReport(Long reportId) {
		return rdreportDefineDAO.deleteReport(reportId);
	}

	@Override
	public List<ReportTarget> getReportTargets(Long reportId, String tableName) {
		List<ReportTarget> result = rdreportDefineDAO
				.getReportTargets(reportId);
		if (tableName == null) {
			return result;
		}
		List<ReportTarget> reportTarget = rdreportDefineDAO
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

	@Override
	public List<DicItem> getDicAll() {
		return rdreportDefineDAO.queryDicAll();
	}

	@Override
	public List getReportsByType(Long typeId, String date, Long userid) {
		return rdreportDefineDAO.getReportsByType(typeId, date, userid);
	}

	@Override
	public void saveReportTarget(List<ReportTarget> targetlist) {
		int maxOrderNum = 0;
		for (int i = 0; i < targetlist.size(); i++) {
			ReportTarget rt = targetlist.get(i);
			if (rt.getTargetOrder() == null && maxOrderNum == 0) {
				maxOrderNum = rdreportDefineDAO
						.getMaxOrderNum(rt.getReportId()) + 1;
				rt.setTargetOrder(maxOrderNum);
			} else {
				rt.setTargetOrder(++maxOrderNum);
			}
			rdreportDefineDAO.saveReportTarget(rt);
		}
	}

	@Override
	public ReportTarget removeReportTarget(Long reportId, Long targetId) {
		ReportTarget rt = getReportTarget(targetId);
		rdreportDefineDAO.removeReportTarget(reportId, targetId);
		return null;
	}

	public ReportTarget getReportTarget(Long targetId) {
		Object o = rdreportDefineDAO.getObject(ReportTarget.class, targetId);
		if (null != o) {
			return (ReportTarget) o;
		}
		return null;
	}

	@Override
	public List<ReportTarget> getTemplateTargets(Long templateId, Long modelId) {
		return rdreportDefineDAO.getTemplateTargets(templateId, modelId);
	}

	@Override
	public void saveTemplateTargets(Long templateId, Long modelId,
			String[] targetIds1, String[] targetIds2) {
		if (targetIds1 != null) {
			for (String targetId : targetIds1) {
				rdreportDefineDAO.delTemplateTargets(templateId, modelId,
						targetId);
			}
		}

		if (targetIds2 != null) {
			Map<String, String> targetMap = new HashMap<String, String>();

			for (String targetField : targetIds2) {
				List reportTargets = getReportTarget(modelId, targetField);
				ReportTarget rt = (ReportTarget) reportTargets.get(0);
				ReportTarget rt1 = new ReportTarget();
				try {
					ConvertUtil.copyProperties(rt1, rt, true);
					if (1 == rt1.getDataType()) {
						rt1.setTargetField(targetField);
					} else {
						rt1.setTargetField(targetField);
					}
					rt1.setPkid(null);
					rt1.setReportId(templateId);
					Long pkid = rdreportDefineDAO.insertTarget(rt1);
					targetMap.put(rt1.getTargetField(), rt.getTargetField());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rdreportDefineDAO.updateTargetRelation(templateId, modelId,
					targetMap);
		}
	}

	public List getReportTarget(Long reportId, String targetField) {
		return rdreportDefineDAO.getReportTarget(reportId, targetField);
	}

	public List getDateOrganEditReportForYJH(String paramDate,
			Integer paramOrganType, String canEdit, Long userId,
			String systemId, String levelFlag) {
		return rdreportDefineDAO.getDateOrganEditReportForYJH(paramDate,
				paramOrganType, canEdit, userId, systemId, levelFlag);
	}

	@Override
	public void delTemplateTarget(Long templateId, Long modelId,
			String targetField) {
		rdreportDefineDAO.delTemplateTarget(templateId, modelId, targetField);
		// updateTargetFieldAndOrder(templateId,modelId);
	}
}
