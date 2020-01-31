package com.krm.ps.model.reportdefine.web.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.reportdefine.services.DictionaryService;
import com.krm.ps.model.reportdefine.services.ReportDefineService;
import com.krm.ps.model.reportdefine.util.DateUtils;
import com.krm.ps.model.reportdefine.web.form.ReportForm;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.Constants;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.FuncConfig;

/**
 * 报表定义--科目表维护
 * 
 * @author Chm
 * 
 *         2014-7-22
 */
public class ReportAction extends BaseAction {

	private static String REPOET_TYPE = "types";

	private static String FREQUENCYS = "frequencys";

	private static String REPORT_LIST = "reportList";

	private static String FUNC_IDS = "funIds";

	private static int FUN_DIC = 1203;

	private static String REPORT_ID = "reportId";

	private static String ORGANTYPES = "organTypes";

	private static String MUNITS = "munits";

	/**
	 * 科目表维护--列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setToken(request);
		ReportDefineService rs = isOperLogger();
		if (log.isDebugEnabled()) {
			log.info("Entering 'toSearch' method");
		}

		DictionaryService ds = (DictionaryService) getBean("rddictionaryService");
		User user = getUser(request);
		request.setAttribute(REPOET_TYPE, rs.getReportTypes(user.getPkid()));
		request.getSession().removeAttribute("ctype");
		request.getSession().removeAttribute("cfrequency");
		request.getSession().removeAttribute("cbegindate");
		request.getSession().removeAttribute("cenddate");

		// 设置报表频度2006.9.22
		request.setAttribute(FREQUENCYS, ds.getReportfrequency());

		return mapping.findForward("search");
	}

	private ReportDefineService isOperLogger() {
		ReportDefineService rs = null;
		/*
		 * if(FuncConfig.isOpenFun("report.oper.logger.enable")){ rs =
		 * (ReportDefineService) getBean("reportDefine"); }else{
		 */
		rs = (ReportDefineService) getBean("rdreportDefineService");
		/* } */
		return rs;
	}

	/**
	 * 科目表维护--查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'search' method");
		}

		try{
			ReportForm reportForm = (ReportForm) form;
			return list(mapping, null, reportForm, request, response);
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		return list(mapping, form, null, request, response);
	}

	/**
	 * 科目表维护--列表
	 * 
	 * @param mapping
	 * @param form
	 * @param searchForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			ActionForm searchForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		ReportDefineService rs = isOperLogger();
		Long typeid = getTypeId(request);
		if (typeid == null) {
			typeid = (Long) request.getAttribute("cctype");
		}
		// 取得报表频度2006.9.22
		String frequencyid = getFrequencyId(request);
		if (frequencyid == null) {
			frequencyid = (String) request.getAttribute("ccfrequency");
		}
		// 取得报表开始时间,结束时间2006.9.22
		String begindateid = getBeginDateId(request);
		if (begindateid == null) {
			begindateid = (String) request.getAttribute("ccbegindate");
		}

		String enddateid = getEndDateId(request);
		if (enddateid == null) {
			enddateid = (String) request.getAttribute("ccenddate");
		}

		User user = getUser(request);
		String date = (String) request.getSession().getAttribute("logindate");
		if (null != searchForm) {
			ReportForm reportForm = (ReportForm) searchForm;
			Report report = new Report();
			repForm2Class(report, reportForm);
			// 对结束日期与开始日期中的-进行处理
			report.setEndDate(reportForm.getEndDate().replaceAll("-", ""));
			report.setBeginDate(reportForm.getBeginDate().replaceAll("-", ""));
			// 2006.9.22增加频度,开始时间,结束时间条件

			// List l = rs.getReports(report, date.replaceAll("-", ""),
			// user.getPkid());

			request.setAttribute(
					REPORT_LIST,
					rs.getReports(report, date.replaceAll("-", ""),
							user.getPkid()));
			request.getSession().setAttribute("ctype",
					reportForm.getReportType());
			// 2006.9.22
			request.getSession().setAttribute("cfrequency",
					reportForm.getFrequency());

			request.getSession().setAttribute("cbegindate",
					reportForm.getBeginDate());
			request.getSession().setAttribute("cenddate",
					reportForm.getEndDate());

		} else if (typeid != null || frequencyid != null || begindateid != null
				|| enddateid != null) {
			request.setAttribute(REPORT_LIST, rs.getReportsByTypeFrequencyDate(
					typeid, frequencyid, begindateid, enddateid,
					date.replaceAll("-", ""), user.getPkid()));
		} else if (getParam(request) != null) {
			typeid = (Long) request.getSession().getAttribute("ctype");
			frequencyid = (String) request.getSession().getAttribute(
					"cfrequency");
			begindateid = (String) request.getSession().getAttribute(
					"cbegindate");
			enddateid = (String) request.getSession().getAttribute("cenddate");
			request.setAttribute(REPORT_LIST, rs.getReportsByTypeFrequencyDate(
					typeid, frequencyid, begindateid, enddateid,
					date.replaceAll("-", ""), user.getPkid()));
		} else {
			request.setAttribute(REPORT_LIST,
					rs.getReports(date.replaceAll("-", ""), user.getPkid()));
		}

		List reports = (List) request.getAttribute(REPORT_LIST);
		if (reports != null) {
			for (int i = 0; i < reports.size(); i++) {
				Report report = (Report) reports.get(i);
				String fdate = report.getBeginDate();
				String tdate = report.getEndDate();
				String s = "-";
				if (fdate != null) {
					report.setBeginDate(fdate.substring(0, 4) + s
							+ fdate.substring(4, 6) + s + fdate.substring(6, 8));
				}
				if (tdate != null) {
					report.setEndDate(tdate.substring(0, 4) + s
							+ tdate.substring(4, 6) + s + tdate.substring(6, 8));
				}
			}
		}

		request.setAttribute(REPORT_LIST, reports);
		request.getSession().setAttribute(REPORT_LIST, reports);
		// 设置报表配置下拉选项
		DictionaryService ds = (DictionaryService) getBean("rddictionaryService");
		List funList = ds.getDics(FUN_DIC);
		request.setAttribute(FUNC_IDS, funList);
		// request.setAttribute("createId", user.getPkid());
		// request.setAttribute("isAdmin", user.getIsAdmin());
		request.setAttribute("currUser", user);

		// 回收站控制
		String pro = FuncConfig.getCNProperty("recycle.status", "no");
		if (pro.toLowerCase().equals("no")) {
			request.setAttribute("recycleStatus", "0");
		} else {
			request.setAttribute("recycleStatus", "1");
		}
		//System.out.println(request.getParameter("name")+"--"+request.getParameter("code")+"--"+request.getParameter("reportType")+"---"+request.getParameter("frequency")+"---");
		request.setAttribute("name",request.getParameter("name"));
		request.setAttribute("code",request.getParameter("code"));
		request.setAttribute("reportType",request.getParameter("reportType"));
		request.setAttribute("frequency",request.getParameter("frequency"));
		request.setAttribute("beginDate",request.getParameter("beginDate"));
		request.setAttribute("endDate",request.getParameter("endDate"));
		
		return mapping.findForward("list");
	}

	private String getBeginDateId(HttpServletRequest request) {
		String id = request.getParameter("beginDateId");
		if (null != id && !"".equals(id)) {
			return id;
		}
		return null;
	}

	private String getEndDateId(HttpServletRequest request) {
		String id = request.getParameter("endDateId");
		if (null != id && !"".equals(id)) {
			return id;
		}
		return null;
	}

	private String getFrequencyId(HttpServletRequest request) {
		String id = request.getParameter("frequencyId");
		if (null != id && !"".equals(id)) {
			return id;
		}
		return null;
	}

	private Long getTypeId(HttpServletRequest request) {
		String id = request.getParameter("typeId");
		if (null != id && !"".equals(id)) {
			return new Long(id);
		}
		return null;
	}

	private void repForm2Class(Report r, ReportForm rf) {
		try {
			ConvertUtil.copyProperties(r, rf);
		} catch (Throwable e) {
			;
		}
	}

	private String getParam(HttpServletRequest request) {
		String param = request.getParameter("param");
		return param;
	}

	/**
	 * 科目表维护--增加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'edit' method");
		}
		ReportForm reportForm = (ReportForm) form;
		Report report = new Report();
		ReportDefineService rs = isOperLogger();
		Long reportid = getReportId(request);
		String flag = "0";
		if (null != reportid) {
			report = rs.getReport(reportid);
		} else {
			flag = "1";
		}
		repClass2Form(reportForm, report);
		String fdate = reportForm.getBeginDate();
		String tdate = reportForm.getEndDate();
		String s = "-";
		if (fdate != null) {
			reportForm.setBeginDate(fdate.substring(0, 4) + s
					+ fdate.substring(4, 6) + s + fdate.substring(6, 8));
		} else {
			reportForm.setBeginDate("1999-01-01");
		}
		if (tdate != null) {
			reportForm.setEndDate(tdate.substring(0, 4) + s
					+ tdate.substring(4, 6) + s + tdate.substring(6, 8));
		} else {
			reportForm.setEndDate("2015-01-01");
		}
		request.setAttribute("repTable", report.getPhyTable());
		request.setAttribute("flag", flag);
		this.updateFormBean(mapping, request, reportForm);
		return toEdit(mapping, reportForm, request, response);
	}

	private Long getReportId(HttpServletRequest request) {
		String id = request.getParameter(REPORT_ID);
		if (null != id) {
			return new Long(id);
		}
		return null;
	}

	private void repClass2Form(ReportForm rf, Report r) {
		try {
			ConvertUtil.copyProperties(rf, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ActionForward toEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'toEdit' method");
		}
		setToken(request);
		ReportDefineService rs = isOperLogger();
		DictionaryService ds = (DictionaryService) getBean("rddictionaryService");
		request.setAttribute("tableName", rs.getDataTable());
		request.setAttribute(FREQUENCYS, ds.getReportfrequency());
		request.setAttribute(ORGANTYPES, ds.getOrgansort());
		request.setAttribute(REPOET_TYPE, rs.getAllReportTypes(null));
		request.setAttribute(MUNITS, ds.getUnitcode());
		return mapping.findForward("edit");
	}

	/**
	 * 科目表维护--保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'save' method");
		}
		if (!tokenValidatePass(request)) {
			return list(mapping, form, null, request, response);
		}
		int isUpdate = 0;
		User user = getUser(request);
		String time = DateUtils.thisDate(DateUtils.TIME_PATTERN);
		ReportDefineService rs = isOperLogger();
		ReportForm reportForm = (ReportForm) form;
		Report or = null;
		if (reportForm.getPkid() != null) {
			isUpdate = 1;
			or = rs.getReport(reportForm.getPkid());
			if (!or.getReportType().equals(reportForm.getReportType())) {// 修改了报表类型，同时更新用户-报表关联表，wsx
																			// 12-21
				rs.updateUserReport(reportForm.getPkid().intValue(), reportForm
						.getReportType().intValue());
			}
		}
		Report report = new Report();
		repForm2Class(report, reportForm);
		if (report.getBeginDate() != null) {
			report.setBeginDate(report.getBeginDate().replaceAll("-", ""));
		}
		if (report.getEndDate() != null) {
			report.setEndDate(report.getEndDate().replaceAll("-", ""));
		}
		report.setCreateId(new Integer(user.getPkid().intValue()));
		report.setStatus(Constants.STATUS_AVAILABLE);
		rs.saveReportIsLoger(report, isUpdate, or, user, time);// by ydw
		request.setAttribute("cctype", report.getReportType());
		return list(mapping, form, null, request, response);
	}

	/**
	 * 科目表维护--排序
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sortReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'sortReport' method");
		}

		setToken(request); // 避免重复提交
		User user = getUser(request);
		String date = (String) request.getSession().getAttribute("logindate");
		ReportDefineService rs = isOperLogger();
		List ls = new ArrayList();
		Long type = (Long) request.getSession().getAttribute("ctype");
		String frequency = (String) request.getSession().getAttribute(
				"cfrequency");
		String begindate = (String) request.getSession().getAttribute(
				"cbegindate");
		String enddate = (String) request.getSession().getAttribute("cenddate");
		Report report = new Report();
		report.setReportType(type);
		// 设置频度、开始时间、结束时间
		report.setFrequency(frequency);
		report.setBeginDate(begindate);
		report.setEndDate(enddate);

		List reportList = rs.getReports(report, date.replaceAll("-", ""),
				user.getPkid());
		for (int i = 0; i < reportList.size(); i++) {
			Object[] o = new Object[2];
			o[0] = ((Report) reportList.get(i)).getPkid();
			o[1] = ((Report) reportList.get(i)).getName() + "("
					+ ((Report) reportList.get(i)).getCode() + ")";
			ls.add(o);
		}
		request.setAttribute("fileTitle", "科目表维护－》报表排序");
		request.setAttribute("sortList", ls);
		request.setAttribute("serviceName", "reportDefineService");
		request.setAttribute("sortTitle", "报表排序");
		ActionForward forward = mapping.findForward("sort_list");
		String path = forward.getPath();
		if (type != null && !"".equals(type)) {
			path = path + "&typeId=" + type;
		}

		// 2006.9.22
		if (frequency != null && !"".equals(frequency)) {
			path = path + "&frequencyId=" + frequency;
		}
		if (begindate != null && !"".equals(begindate)) {
			path = path + "&beginDateId=" + begindate;
		}
		if (enddate != null && !"".equals(enddate)) {
			path = path + "&endDateId=" + enddate;
		}

		request.setAttribute("forwardURL", path);
		request.setAttribute("name",request.getParameter("name"));
		request.setAttribute("code",request.getParameter("code"));
		request.setAttribute("reportType",request.getParameter("reportType"));
		request.setAttribute("frequency",request.getParameter("frequency"));
		request.setAttribute("beginDate",request.getParameter("beginDate"));
		request.setAttribute("endDate",request.getParameter("endDate"));
		request.setAttribute("flag111", request.getParameter("flag111"));
		return mapping.findForward("sort");
	}

	/**
	 * 科目表维护--取消
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'cancel' method");
		}
		return list(mapping, form, null, request, response);
	}

	/**
	 * 科目表维护--删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'del' method");
		}
		ReportDefineService rs = isOperLogger();
		Long reportid = getReportId(request);
		Report report = rs.getReport(reportid);
		request.setAttribute("cctype", report.getReportType());
		if (null != reportid) {
			String pro = FuncConfig.getCNProperty("recycle.status", "no");
			Long deleteResult = null;
			if (pro.toLowerCase().equals("no")) {
				deleteResult = rs.deleteReportAbout(reportid);
			} else {
				deleteResult = rs.deleteReport(reportid);
			}
			if (deleteResult != null) {
				// 操作类型为“删除报表”
				request.setAttribute("operateType", "2");
				String regExp = ".*\\b" + reportid + "\\.\\w+\\.\\d+\\b.*";
				request.setAttribute("regExp", regExp);
				request.getSession().setAttribute(REPORT_ID, reportid);
				return mapping.findForward("listUndeleteable");
			}
		}

		return list(mapping, form, null, request, response);
	}

	/**
	 * 元数据管理--返回
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showSelf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'showSelf' method");
		}
		ReportDefineService rs = isOperLogger();
		Report report = null;
		Long reportId = getSessionReportId(request);
		List ls = new ArrayList();
		User user = getUser(request);
		String date = (String) request.getSession().getAttribute("logindate");
		if (null != reportId) {
			report = rs.getReport(reportId);
			ls = rs.getReportsByType(report.getReportType(),
					date.replaceAll("-", ""), user.getPkid());
		}
		request.setAttribute(REPORT_LIST, ls);
		// 设置报表配置下拉选项
		DictionaryService ds = (DictionaryService) getBean("rddictionaryService");
		List funList = ds.getDics(FUN_DIC);
		request.setAttribute(FUNC_IDS, funList);
		return mapping.findForward("list");
	}

	private Long getSessionReportId(HttpServletRequest request)
			throws Exception {
		Object id = request.getSession().getAttribute(REPORT_ID);
		if (null != id) {
			return (Long) id;
		}
		throw new Exception("outOfSession");
	}

}
