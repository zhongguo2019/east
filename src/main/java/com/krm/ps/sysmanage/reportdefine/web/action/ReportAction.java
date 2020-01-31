package com.krm.ps.sysmanage.reportdefine.web.action;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.organmanage2.util.JsonUtil;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.reportdefine.web.form.ReportForm;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.Constants;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.DateUtils;

/**
 * 
 * @struts.action name="reportForm" path="/reportAction" scope="request"
 *                validate="false" parameter="method" input="edit"
 * 
 * @struts.action-forward name="edit" path="/reportdefine/reportForm.jsp"
 * @struts.action-forward name="search"
 *                        path="/reportdefine/reportSearchForm.jsp"
 * @struts.action-forward name="list" path="/reportdefine/reportList.jsp"
* @struts.action-forward name="listUndeleteable" path="/formulaDefine.do?method=listUndeleteableFormulas"
 * @struts.action-forward name="sort" path="/common/sortcommon.jsp"
 * @struts.action-forward name="sort_list" path="/reportAction.do?method=list"
 * @struts.action-forward name="author" path="/reportdefine/reportAuthor.jsp"
 */

public class ReportAction extends BaseAction {

	private static String REPORT_ID = "reportId";

	private static String REPOET_TYPE = "types";

	private static String REPORT_LIST = "reportList";

	private static String FREQUENCYS = "frequencys";

	private static String ORGANTYPES = "organTypes";

	private static String MUNITS = "munits";
	
	private static String FUNC_IDS = "funIds";
	
	private static int FUN_DIC = 1203;

	private void repForm2Class(Report r, ReportForm rf) {
		try {
			ConvertUtil.copyProperties(r, rf);
		} catch (Throwable e) {
			;
		}
	}

	private void repClass2Form(ReportForm rf, Report r) {
		try {
			ConvertUtil.copyProperties(rf, r);
		} catch (Exception e) {
			e.printStackTrace();// //
		}
	}

	private Long getSessionReportId(HttpServletRequest request)
			throws Exception {
		Object id = request.getSession().getAttribute(REPORT_ID);
		if (null != id) {
			return (Long) id;
		}
		throw new Exception("outOfSession");
	}

	private Long getReportId(HttpServletRequest request) {
		String id = request.getParameter(REPORT_ID);
		if (null != id) {
			return new Long(id);
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

	// 2006.9.22
	private String getFrequencyId(HttpServletRequest request) {
		String id = request.getParameter("frequencyId");
		if (null != id && !"".equals(id)) {
			return id;
		}
		return null;
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

	private String getParam(HttpServletRequest request) {
		String param = request.getParameter("param");
		return param;
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'cancel' method");
		}

		return list(mapping, form, null, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		return list(mapping, form, null, request, response);
	}

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
			//对结束日期与开始日期中的-进行处理
			report.setEndDate(reportForm.getEndDate().replaceAll("-", ""));
			report.setBeginDate(reportForm.getBeginDate().replaceAll("-", ""));
			// 2006.9.22增加频度,开始时间,结束时间条件
			request.setAttribute(REPORT_LIST, rs.getReports(report, date
					.replaceAll("-", ""), user.getPkid()));
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
					typeid, frequencyid, begindateid, enddateid, date
							.replaceAll("-", ""), user.getPkid()));
		} else if (getParam(request) != null) {
			typeid = (Long) request.getSession().getAttribute("ctype");
			frequencyid = (String) request.getSession().getAttribute(
					"cfrequency");
			begindateid = (String) request.getSession().getAttribute(
					"cbegindate");
			enddateid = (String) request.getSession().getAttribute("cenddate");
			request.setAttribute(REPORT_LIST, rs.getReportsByTypeFrequencyDate(
					typeid, frequencyid, begindateid, enddateid, date
							.replaceAll("-", ""), user.getPkid()));
		} else {
			request.setAttribute(REPORT_LIST, rs.getReports(date.replaceAll(
					"-", ""), user.getPkid()));
		}

		List reports = (List) request.getAttribute(REPORT_LIST);
		if (reports != null) {
			for (int i = 0; i < reports.size(); i++) {
				Report report = (Report) reports.get(i);
				String fdate = report.getBeginDate();
				String tdate = report.getEndDate();
				String s = "-";
				if (fdate != null) {
					report
							.setBeginDate(fdate.substring(0, 4) + s
									+ fdate.substring(4, 6) + s
									+ fdate.substring(6, 8));
				}
				if (tdate != null) {
					report
							.setEndDate(tdate.substring(0, 4) + s
									+ tdate.substring(4, 6) + s
									+ tdate.substring(6, 8));
				}
			}
		}
		
		request.setAttribute(REPORT_LIST, reports);
		request.getSession().setAttribute(REPORT_LIST, reports);
		//设置报表配置下拉选项
		DictionaryService ds = getDictionaryService();
		List funList = ds.getDics(FUN_DIC);
		request.setAttribute(FUNC_IDS,funList);
		//request.setAttribute("createId", user.getPkid());
		//request.setAttribute("isAdmin", user.getIsAdmin());
		request.setAttribute("currUser", user);
		
		//回收站控制
		String pro = FuncConfig.getCNProperty("recycle.status", "no");
		if(pro.toLowerCase().equals("no")) {
			request.setAttribute("recycleStatus", "0");
		}else {
			request.setAttribute("recycleStatus", "1");
		}
		
		return mapping.findForward("list");
	}

	private ReportDefineService isOperLogger() {
		ReportDefineService rs=null;
		if(FuncConfig.isOpenFun("report.oper.logger.enable")){
			rs = (ReportDefineService) getBean("reportDefine");
		}else{
			rs = getReportDefineService();
		}
		return rs;
	}

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
			ls = rs.getReportsByType(report.getReportType(), date.replaceAll(
					"-", ""), user.getPkid());
		}
		request.setAttribute(REPORT_LIST, ls);
		//设置报表配置下拉选项
		DictionaryService ds = getDictionaryService();
		List funList = ds.getDics(FUN_DIC);
		request.setAttribute(FUNC_IDS,funList);
		return mapping.findForward("list");
	}

	public ActionForward toSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'toSearch' method");
		}
		setToken(request);
		ReportDefineService rs = isOperLogger();
		DictionaryService ds = getDictionaryService();
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

	public ActionForward toEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'toEdit' method");
		}
		setToken(request);
		ReportDefineService rs = isOperLogger();
		DictionaryService ds = getDictionaryService();
		request.setAttribute("tableName", rs.getDataTable());
		request.setAttribute(FREQUENCYS, ds.getReportfrequency());
		request.setAttribute(ORGANTYPES, ds.getOrgansort());
		request.setAttribute(REPOET_TYPE, rs.getAllReportTypes(null));
		request.setAttribute(MUNITS, ds.getUnitcode());
		return mapping.findForward("edit");
	}

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
		String time=DateUtils.thisDate(DateUtils.TIME_PATTERN);
		ReportDefineService rs = isOperLogger();
		ReportForm reportForm = (ReportForm) form;
		Report or=null;
		if (reportForm.getPkid() != null) {
			isUpdate = 1;
			or=rs.getReport(reportForm.getPkid());
			if(!or.getReportType().equals(reportForm.getReportType())) {//�޸��˱������ͣ�ͬʱ�����û�-�����j�?wsx 12-21
				rs.updateUserReport(reportForm.getPkid().intValue(), reportForm.getReportType().intValue());
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
		rs.saveReportIsLoger(report, isUpdate,or,user,time);//by ydw
		request.setAttribute("cctype", report.getReportType());
		// 2006.9.22
/*		不过滤列表，wsx 11-23
 * 		request.setAttribute("ccfrequency", report.getFrequency());
		request.setAttribute("ccbegindate", report.getBeginDate());
		request.setAttribute("ccenddate", report.getEndDate());*/
		return list(mapping, form, null, request, response);
	}

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
		// 2006.9.22
/*	不过滤列表，wsx 11-23
 * 		request.setAttribute("ccfrequency", report.getFrequency());
		request.setAttribute("ccbegindate", report.getBeginDate());
		request.setAttribute("ccenddate", report.getEndDate());
*/
		if (null != reportid) {
			String pro = FuncConfig.getCNProperty("recycle.status", "no");
			Long deleteResult = null;
			if(pro.toLowerCase().equals("no")) {
				deleteResult = rs.deleteReportAbout(reportid);
			}else {
				deleteResult = rs.deleteReport(reportid);
			}
			if(deleteResult != null){
				//操作类型为“删除报表”
				request.setAttribute("operateType","2");
				String regExp = ".*\\b" + reportid + "\\.\\w+\\.\\d+\\b.*";
				request.setAttribute("regExp", regExp);
				request.getSession().setAttribute(REPORT_ID, reportid);
				return mapping.findForward("listUndeleteable");
			}
		}
		
		return list(mapping, form, null, request, response);
	}

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
		// 2006.10.14设置频度、开始时间、结束时间
		report.setFrequency(frequency);
		report.setBeginDate(begindate);
		report.setEndDate(enddate);

		List reportList = rs.getReports(report, date.replaceAll("-", ""), user
				.getPkid());
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

		return mapping.findForward("sort");
	}

	public ActionForward checkReportCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'checkReportCode' method");
		}
		ReportDefineService rs = isOperLogger();
		String reportCode = (String) request.getParameter("reportCode");

		boolean exist = rs.checkReportCode(reportCode);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (exist) {
			out.print("true");
		}
		out.flush();
		out.close();
		return null;
	}
	public ActionForward reportAuthor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'reportAuthor' method");
		}
		String reportId = request.getParameter("reportId");
		ReportDefineService rs = isOperLogger();
		Report report = rs.getReport(new Long(reportId));
		ReportType reportType = rs.getReportType(report.getReportType());
		AreaService areaService = (AreaService)getBean("areaService");
		String areaCode = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		request.setAttribute("areaCode",areaCode);
		UserService userService = (UserService)getBean("userService");
		List userList = userService.getUserByArea(areaCode,new Long(report.getCreateId().intValue()));
//		List userJson = JsonUtil.getUserJson(userList);
		List repUserList = userService.getRepUser(reportType.getPkid(), report.getPkid(),new Long(report.getCreateId().intValue()));
//		List repUserJson = JsonUtil.getUserJson(repUserList);
		JSONArray userJson = JSONArray.fromObject(JsonUtil.getUserJson(userList));
		JSONArray repUserJson = JSONArray.fromObject(JsonUtil.getUserJson(repUserList));
		String rootId = areaService.getTopArea().getCode();
		request.setAttribute("rootId", rootId);
		request.setAttribute("userList", userList);
		request.setAttribute("repUserList", repUserList);
		request.setAttribute("userJson", userJson);
		request.setAttribute("repUserJson", repUserJson);
		request.getSession().setAttribute("authorReport", report);
		request.getSession().setAttribute("authorReportType", reportType);
		return mapping.findForward("author");
	}
	public ActionForward changeAreaTree(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'changeAreaTree' method");
		}
		String areaCode = request.getParameter("areaCode");
		UserService userService = (UserService)getBean("userService");
		Report report = (Report)request.getSession().getAttribute("authorReport");
		List userList = userService.getUserByArea(areaCode,new Long(report.getCreateId().intValue()));
		JSONArray userJson = JSONArray.fromObject(JsonUtil.getUserJson(userList));
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(userJson);
		out.flush();
		out.close();
		return null;
	}
	public ActionForward saveAuthor(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'saveAuthor' method");
		}
		Report report = (Report)request.getSession().getAttribute("authorReport");
		Long reportId = report.getPkid();
		Long reportTypeId = ((ReportType)request.getSession().getAttribute("authorReportType")).getPkid();
		String authorUsers = request.getParameter("authorUser");
		UserService userService = (UserService)getBean("userService");
		userService.saveUserReport(reportId, reportTypeId, authorUsers, new Long(report.getCreateId().intValue()));
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "reportAction.do?method=list");
		return null;
	}
}
