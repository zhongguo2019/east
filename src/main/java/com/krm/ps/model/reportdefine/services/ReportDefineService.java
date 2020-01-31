package com.krm.ps.model.reportdefine.services;

import java.util.List;

import com.krm.ps.framework.common.sort.service.SortService;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.usermanage.vo.User;

public interface ReportDefineService extends SortService {

	public static int NAME_REPEAT = 1;

	public static int SAVE_OK = 2;

	public static int DEL_OK = 0;

	public List getAllReportTypes(String showlevel);

	public ReportType getReportType(Long typeId);

	public void removeReportType(Long typeId);

	public int saveReportType(ReportType type);

	public List getReportTypes(Long userid);

	public List getReports(Report report, String date, Long userid);

	public List getReportsByTypeFrequencyDate(Long typeId, String frequencyId,
			String beginDateId, String endDateId, String date, Long userid);

	public List getReports(String date, Long userid);

	public Report getReport(Long pkid);

	public List<String> getDataTable();

	public void updateUserReport(int reportId, int newType);

	public int saveReportIsLoger(Report report, int isUpdate,
			Report beforReport, User user, String time);

	public Long deleteReportAbout(Long reportId);

	public Long deleteReport(Long reportId);

	public List getReportTargets(Long reportId, String tableName);

	public List<DicItem> getDicAll();

	public List getReportsByType(Long typeId, String date, Long userid);

	public void saveReportTarget(List<ReportTarget> targetlist);

	public ReportTarget removeReportTarget(Long reportId, Long targetId);

	public List<ReportTarget> getTemplateTargets(Long templateId, Long modelId);

	public void saveTemplateTargets(Long templateId, Long modelId,
			String[] targetIds1, String[] targetIds2);

	public List getReportTarget(Long reportId, String targetField);

	public List getDateOrganEditReportForYJH(String paramDate,
			Integer paramOrganType, String canEdit, Long userId,
			String systemId, String levelFlag);

	public void delTemplateTarget(Long templateId, Long modelId, String targetId);

}
