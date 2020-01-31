package com.krm.ps.model.reportview.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.reportview.services.BillExchangeService;
import com.krm.ps.model.reportview.services.ChartDefineService;
import com.krm.ps.model.reportview.services.DataCarryService;
import com.krm.ps.model.reportview.services.LogService;
import com.krm.ps.model.reportview.services.ReportViewService;
import com.krm.ps.model.reportview.util.ReportChart;
import com.krm.ps.model.reportview.util.ReportViewUtil;
import com.krm.ps.model.reportview.web.form.ReportViewForm;
import com.krm.ps.model.sysLog.util.LogUtil;
import com.krm.ps.model.vo.DataCollect;
import com.krm.ps.model.vo.Result;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.services.ReportFormatService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.usermanage.vo.Units;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.DateUtils;
import com.krm.ps.util.FuncConfig;

public class ReportViewAction extends BaseAction {

	public ActionForward repTreeAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (log.isDebugEnabled()) {
			log.info("Entering 'repTreeAjax' method");
		}

		ReportDefineService rs = this.getReportDefineService();
		OrganService os = this.getOrganService();
		String levelFlag = request.getParameter("levelFlag");
		// 取得日期、机构、用户ID、录入查询标识(查询：0 录入：1)、频度
		String paramDate = request.getParameter("paramdate");

		String paramOrgan = request.getParameter("paramorgan");

		if (paramDate == null) {
			paramDate = (String) request.getSession().getAttribute("logindate");
		}
		if (paramOrgan == null) {
			paramOrgan = getUser(request).getOrganId();
			request.setAttribute("params", "&paramdate=" + paramDate
					+ "&paramorgan=" + getUser(request).getOrganId()
					+ "&levelFlag=" + levelFlag);
		}
		Long userId = getUser(request).getPkid();
		String canEdit = (String) request.getSession().getAttribute("canEdit");
		OrganInfo organInfo = os.getOrganByCode(paramOrgan);

		// 得到机构类型
		Integer paramOrganType = null;
		if (organInfo != null) {
			paramOrganType = organInfo.getOrgan_type();
		}

		// 标准化 获取系统id
		String systemId = (String) request.getSession().getAttribute(
				"system_id");
		// 得到报表列表,得到报表类型
		List reportList = null;
		List reportType = null;
		Set set = new HashSet();
		if (paramOrganType != null) {
			/*
			 * dxp 新增加,在智能流程提醒功能里面需要根据参数flowtip取的报表列表。
			 */
			if (request.getParameter("flowtip") != null) {
				reportList = rs.getAddStepReportList(
						paramDate.replaceAll("-", ""), userId);
			} else if (systemId != null && !"".equals(systemId)) {
				// 标准化 查询当前系统报表树
				reportList = rs.getDateOrganEditReport(
						paramDate.replaceAll("-", ""), paramOrganType, canEdit,
						userId, systemId, levelFlag);
			} else {
				// 下面是以前的........
				reportList = rs.getDateOrganEditReport(
						paramDate.replaceAll("-", ""), paramOrganType, canEdit,
						userId);
			}

			// 报表类型
			/*
			 * if ("0".equals(canEdit)) { reportType =
			 * rs.getReportTypes(userId); } else { reportType =
			 * rs.getReportTypesForEdit(userId); }---chm
			 */

			/**
			 * 2012-07-26 贡琳 以前的reportTypes里封装的是该用户可以看到的全部报表类型。 现在要做的是
			 * 根据用户可以看到的报表 显示相应的报表类型 有什么类型显示什么类型
			 */

			List replist = new ArrayList();

			Iterator it = reportList.iterator();

			while (it.hasNext()) {

				Report rp = (Report) it.next();

				set.add(rp.getReportType());

			}

			Iterator i = set.iterator();

			while (i.hasNext()) {
				Long isd = (Long) i.next();
				ReportType r = getReportDefineService().getReportType(isd);
				if (r != null) {
					replist.add(r);

				}
			}

			reportList = addRootAndType(reportList, replist);
			if(FuncConfig.isOpend("oldRepTree")){
				 if (request.getParameter("viewtree") != null) {
					 buildingReportTree(reportList, response); 
					 return null; 
				}
			}
			 
		}
		
		if(FuncConfig.isOpend("oldRepTree")){
			String[] cnames = new String[] { "pkid", "name", "showOrder","reportType" };
			request.removeAttribute("treeXml");
			request.setAttribute("treeXml",ConvertUtil.convertListToAdoXml(reportList, cnames));
			return mapping.findForward("tree");
		}
		else{
			String xml = getReportDefineService().getReportXML_temp(reportList,
					set, paramDate.replaceAll("-", ""), paramOrganType, canEdit,
					userId, systemId, levelFlag);
			JSONObject json = new JSONObject();
			json.put("reportXml", xml);
			request.setAttribute("reportXml", xml);
			// return mapping.findForward("tree");
			String dateJson = JSONArray.fromObject(json).toString();
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.write(dateJson);
			writer.close();
			return null;
		}
	}

	private List addRootAndType(List reportList, List reportType) {
		List returnList = new ArrayList();
		int order = 0;

		Report root = new Report();
		root.setPkid(new Long(10000));
		root.setName(FuncConfig.getProperty("first.allrep.name"));
		root.setShowOrder(new Integer(order++));
		root.setReportType(new Long(-1));
		returnList.add(root);

		Iterator it = reportType.iterator();
		while (it.hasNext()) {
			ReportType type = (ReportType) it.next();
			Report t = new Report();
			t.setPkid(new Long(type.getPkid().longValue() + 10000));// 閸旂姳绗�0000閺勵垯璐熸禍鍡涗缉閸忓秳绗岄幎銉ㄣ�閹躲儴銆僷kid閸愯尙鐛�
			t.setName(type.getName());
			t.setShowOrder(new Integer(order++));
			t.setReportType(new Long(10000));
			returnList.add(t);
		}

		Iterator it2 = reportList.iterator();
		while (it2.hasNext()) {
			Report r = (Report) it2.next();
			r.setShowOrder(new Integer(order++));
			r.setReportType(new Long(r.getReportType().longValue() + 10000));
			returnList.add(r);
		}

		return returnList;
	}

	private void buildingReportTree(List reportList,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer buffer = new StringBuffer("");
			buffer.append("<?xml version='1.0' encoding='gbk'?>");
			buffer.append("<tree id='0' radio='1'>");
			geneteDhtmlxtree(reportList, buffer);
			buffer.append("</tree>");
			out.println(buffer.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	}

	private void geneteDhtmlxtree(List reportList, StringBuffer buffer) {

		Report root = (Report) reportList.get(0);

		buffer.append("<item text='"
				+ root.getName()
				+ "' id='"
				+ root.getPkid()
				+ "' im0='tablel01.gif' im1='tablelx01.gif' im2='tablelx02.gif' >");
		buildingChildren(root.getPkid().longValue(), reportList, buffer);
		buffer.append("</item>");
	}

	/*
	 * 按照报表类型，查找并组织该类型下的报表
	 */
	private void buildingChildren(long parentId, List reportList,
			StringBuffer buffer) {
		for (int i = 1; i < reportList.size(); i++) {
			Report report = (Report) reportList.get(i);
			if (report.getReportType().longValue() == parentId) {
				buffer.append("<item text='"
						+ report.getName()
						+ "' id='"
						+ report.getPkid()
						+ "' im0='tablel01.gif' im1='tablel01.gif' im2='tablel02.gif' >");
				// reportList.remove(report);
				buildingChildren(report.getPkid().longValue(), reportList,
						buffer);
				buffer.append("</item>");
			}

		}
	}

	/**
	 * 输入额外查询条件信息 在这个页面输入
	 */
	public ActionForward inputExtraCondition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'inputExtraCondition' method");
		}

		return viewReportFile(mapping, form, request, response);
	}

	/**
	 * 展示一个报表
	 * 
	 */
	public ActionForward viewReportFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'viewReportFile' method");
		}
		ReportViewForm reportViewForm = (ReportViewForm) form;
		HttpSession session = request.getSession();

		String systemHomePage = FuncConfig.getProperty("slsint.homepage",
				"frmMain.jsp");
		if ("home.jsp".equals(systemHomePage)) {
			String viewOrganId = request.getParameter("viewOrganId");
			String viewDataDate = request.getParameter("viewDataDate");
			String viewReportId = request.getParameter("viewReportId");
			if (viewOrganId != null && viewDataDate != null
					&& viewReportId != null) {
				reportViewForm.setOrganId(viewOrganId);
				reportViewForm.setDataDate(viewDataDate);
				reportViewForm.setReportId(new Long(viewReportId));
				session.setAttribute("canEdit", "0");
			}
		}

		// // 报表查询参数的保存
		if (session.getAttribute("canEdit") == "0") {
			session.setAttribute("viewOrganId", reportViewForm.getOrganId());
			session.setAttribute("viewDataDate", reportViewForm.getDataDate());
			session.setAttribute("viewReportId", reportViewForm.getReportId());
			session.setAttribute("inputOrganId", reportViewForm.getOrganId());
			session.setAttribute("inputDataDate", reportViewForm.getDataDate());
			session.setAttribute("inputReportId", reportViewForm.getReportId());
			if (showUnit()) {
				session.setAttribute("viewUnitCode",
						reportViewForm.getUnitCode());
			}
		} else if (session.getAttribute("canEdit") == "1") {
			session.setAttribute("inputOrganId", reportViewForm.getOrganId());
			session.setAttribute("inputDataDate", reportViewForm.getDataDate());
			session.setAttribute("inputReportId", reportViewForm.getReportId());
		}

		// 报表录入参数的保存
		Long reportId = reportViewForm.getReportId();

		ReportDefineService rs = this.getReportDefineService();
		Report r = rs.getReport(reportId);
		String date = reportViewForm.getDataDate();

		String strDataDate = date.replaceAll("-", "");
		strDataDate = ReportViewUtil.mapDataDate(strDataDate, r.getFrequency());// 转换取数日期，2007-4-16
		reportViewForm.setFrequency(r.getFrequency());

		updateFormBean(mapping, request, reportViewForm);

		/* 编辑查看标志位 */
		String canEdit = request.getParameter("canEdit");
		if (canEdit == null) {
			canEdit = (String) session.getAttribute("canEdit");
		}

		/* 修改调平报表标志位 */
		String dellog = request.getParameter("dellog");
		if (dellog == null) {
			dellog = (String) session.getAttribute("dellog");
		}

		if ("1".equals(canEdit) && !"yes".equals(dellog)) {
			// wsx 8-31,报表已调平不允许修改
			Long repType = reportViewForm.getReportType();
			if (repType == null) {
				repType = r.getReportType();
				reportViewForm.setReportType(repType);
			}
			// 2010-6-11 上午05:04:37 皮亮修改
			// 这里加入了过滤，可以配置那些报表再通过校验审核后就不能进行数据录入了
			String configCheckReps = FuncConfig.getProperty(
					"reportView.balancedFilter", "");
			configCheckReps = "".equals(configCheckReps) ? ""
					: (configCheckReps + ",");
			String checkReps = configCheckReps + "113,114,109,115,119,31,41";
			LogService ls = (LogService) getBean("logService");
			/* 2010-7-21 下午03:29:50 皮亮修改 */
			// 如果新的加锁机制已经使用，则不用再以审核通过作为加锁日志
			log.info("对报表类型[" + checkReps + "]进行校验，"
					+ "如果想加入新的报表类型，请在[reportView.balancedFilter]"
					+ "中进行配置（以前类型程序中自动添加）。。。。。。。。。。。。。。。。");
			if (checkReps.trim().matches(
					"(^|.*,)" + String.valueOf(repType) + "(,.*|$)")) {
				log.info("报表类型设置正常，进行校验通过的审核。。。。。。。。。。");
				boolean isBlance = ls.hasLogData(reportViewForm.getOrganId(),
						reportId, strDataDate, LogUtil.LogType_ReportBalanceOK);
				if (isBlance) {
					log.info("查出已经调平，直接提示！！！！");
					String warn = "报表《" + r.getName() + "》已通过校验审核，不可以修改。";
					request.setAttribute("reportBalance", warn);
					return enterInputSearch(mapping, form, request, response);
				}
			}
			if (FuncConfig.isOpenFun("fun.report.reportlock")) {
				// 2010-6-13 上午05:15:57 皮亮修改
				// 加入了新的加锁机制，加锁类型采用新的日志类型
				if (ls.hasLogData(reportViewForm.getOrganId(), reportId,
						strDataDate, LogUtil.LogType_Report_Lock)) {
					log.info("您选择的报表已经进行了加锁！！！");
					String warn = "报表《" + r.getName() + "》已经加锁，不可以修改。";
					request.setAttribute("reportBalance", warn);
					return enterInputSearch(mapping, form, request, response);
				}
			} else {
				log.warn("没有使用新的报表加锁机制！！！*************************************");
			}
		}

		/** 在查看票据的监测表一之前，先把基期数据的报表日期改为要查询的日期。* */
		BillExchangeService bes = (BillExchangeService) getBean("billExchangeService");
		if (new Long(400).equals(reportId)) {
			bes.updateBaseData(reportViewForm.getOrganId(),
					date.replaceAll("-", ""));
		}

		ReportFormatService mgr = (ReportFormatService) getRepFormatService();
		ReportFormat reportFormat = mgr.getReportFormat(reportId,
				date.replaceAll("-", ""), reportViewForm.getFrequency());
		log.info("报表模板ID:" + reportFormat.getPkId());
		session.setAttribute("reportFormat" + reportId, reportFormat);// 保存，供页面用，wsx
		// 10-11
		request.setAttribute("formatNull", (reportFormat != null) ? "0" : "1");
		if ("1".equals(canEdit)) {
			request.setAttribute("canEdit", "1");
			setButton(request);
			// 2006.11.8设置是否显示"复制"(复制外部文本文件数据源)
			String outerdatasource = FuncConfig
					.getProperty("reportview.outerdatasource");
			request.setAttribute("outerdatasource", outerdatasource);

			// 2007.4.13设置是否显示"复制EXCEL"
			String outerxlsdatasource = FuncConfig
					.getProperty("reportview.outerxlsdatasource");
			request.setAttribute("outerxlsdatasource", outerxlsdatasource);

			setBuildasinput(reportViewForm, request, r);

			// 2008.6.6设置是否显示录入数据校验按钮
			setPrecheck(request, reportId);

			// 2008.6.7设置是否显示插入新数据行按钮
			if ("rep_dataf".equals(r.getPhyTable())) {
				request.setAttribute("isFlowTable", "true");
			} else {
				request.setAttribute("isFlowTable", "false");
			}
		} else {
			request.setAttribute("canEdit", "0");
			request.setAttribute(ReportChart.CHART_REPORT, "0");
			if (reportFormat != null) {
				ChartDefineService cs = ReportChart.getChartDefineService(this);
				if (cs.haveChart(reportFormat.getPkId().longValue(), true)) {
					request.setAttribute(ReportChart.CHART_REPORT, "1");
					request.setAttribute(ReportChart.CHART_MODEL_FORMAT,
							reportFormat.getPkId());
				}
			}
			setBuildasinput(reportViewForm, request, r);
		}
		String vidbutton = FuncConfig.getProperty("reportview.viewItemData");
		request.setAttribute("viewItemData", vidbutton);
		request.setAttribute("showret", request.getParameter("showret"));
		request.setAttribute("dellog", dellog);

		request.setAttribute("preCheckData",
				FuncConfig.getProperty("reportview.perCheckData", "no"));
		request.setAttribute("flowMaxLength",
				FuncConfig.getProperty("flowtable.cell.maxlength", "200"));

		if (canEdit.equals("1")) {
			DataCarryService dcs = (DataCarryService) getBean("dataCarryService");
			boolean needCarry = dcs.needCarry(reportId);
			if (needCarry) {
				if ("yes".equals(FuncConfig.getProperty(
						"reportview.dataCarry.release", "no"))) {
					request.setAttribute("needCarry", "yes");
				}
			} else {
				request.setAttribute("needCarry", "no");
			}

			if (request.getAttribute("carryOk") != null) {
				request.setAttribute("carryOk", "1");
			}

			if (request.getAttribute("carryOk") != null) {
				request.setAttribute("carryOk", "1");
			}
		}
		// 表项查询功能
		List reportList = rs.getReportTarget("rep_data1");
		String adhocReportId = "";
		for (Iterator itr = reportList.iterator(); itr.hasNext();) {
			Report report = (Report) itr.next();
			if (adhocReportId.equals("")) {
				adhocReportId = report.getPkid().toString();
			} else {
				adhocReportId += "," + report.getPkid().toString();
			}
		}
		request.setAttribute("adhocReport", adhocReportId);
		// 反向查询功能
		String reversequery = FuncConfig.getProperty(
				"report.reversequery.enable", "no");
		request.setAttribute("reversequery", reversequery);
		if (log.isDebugEnabled()) {
			log.info(reportViewForm);
		}
		return mapping.findForward("view");
	}

	/**
	 * 判断是否需要显示报表单位功能
	 * 
	 * @return boolean
	 */
	private boolean showUnit() {
		String flag = FuncConfig.getProperty("reportview.unitselect.release",
				"no");
		return ("yes".equals(flag.toLowerCase()));
	}

	/**
	 * 进入录入报表的界面
	 * 
	 */
	public ActionForward enterInputSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 2007.11.30 lxk 将上次录入的日期、机构、报表选项保留到session中，作为下次录入时的默认选项
		ReportViewForm reportViewForm = (ReportViewForm) form;
		HttpSession session = request.getSession();

		// 获得日期
		String dataDate = (String) session.getAttribute("inputDataDate");
		if (dataDate == null) {
			dataDate = (String) session.getAttribute("logindate");
		}

		// 获得机构
		String organId = (String) session.getAttribute("inputOrganId");
		if (organId == null) {
			organId = getUser(request).getOrganId();
		}

		// 获得报表
		Long reportId = (Long) session.getAttribute("inputReportId");

		// 设置一个默认报表
		if (reportId == null) {
			User user = (User) request.getSession().getAttribute("user");
			ReportDefineService rs = this.getReportDefineService();
			List repList = rs.getReports(dataDate, user.getPkid());
			/**
			 * 2011-12-08周石磊 获取第一个类型下的第一张报表
			 */
			Report report = (Report) repList.get(0);
			reportId = report.getPkid();
			String reportName = report.getName();
			request.setAttribute("reportName", reportName);
		}

		if (reportViewForm.getDataDate() == null) {
			String loginDate = (String) request.getSession().getAttribute(
					"logindate");
			reportViewForm.setDataDate(loginDate);
		}
		updateFormBean(mapping, request, reportViewForm);

		request.getSession().setAttribute("canEdit", "1");

		String newwinbutton = FuncConfig.getProperty("reportform.newwinbutton");

		log.info(newwinbutton + "查看打印出来的属性值");
		request.setAttribute("newwinbutton", newwinbutton);
		// 设置组织机构
		request.setAttribute("params", "&paramdate=" + dataDate
				+ "&paramorgan=" + organId);
		request.setAttribute("orgparam", "&date=" + dataDate);
		request.setAttribute("organId", organId);
		request.setAttribute("dataDate", dataDate);
		request.setAttribute("reportId", reportId);

		return mapping.findForward("search");
	}

	private void setButton(HttpServletRequest request) {// wsx 8-29
		// setSavebutton rename to setButton，wsx，2007-6-13

		String savebutton = FuncConfig.getProperty("reportview.savebutton");
		String saveupdate = "";
		String saveall = "";
		String save = "";
		if (savebutton != null) {
			saveupdate = (savebutton.indexOf("saveupdate") >= 0) ? "yes" : "no";
			saveall = (savebutton.indexOf("saveall") >= 0) ? "yes" : "no";
			save = (savebutton.indexOf("saves") >= 0) ? "yes" : "no";
		}
		request.setAttribute("saveupdate", saveupdate);
		request.setAttribute("saveall", saveall);
		request.setAttribute("save", save);

		String clearBuildedData = FuncConfig
				.getProperty("reportview.clearBuildedData");// 增加清除生成数据功能，wsx，2007-6-13
		request.setAttribute("clearBuildedData", clearBuildedData);

		String unlockbutton = FuncConfig.getProperty("reportview.unlockbutton");
		request.setAttribute("unlockbutton", unlockbutton);
	}

	private void setPrecheck(HttpServletRequest request, Long repId) {

		String precheckbutton = FuncConfig
				.getProperty("reportview.precheckbutton");
		if ("yes".equals(precheckbutton)) {
			String actReportId = getActReportId(repId);
			if (!"".equals(actReportId)) {
				request.setAttribute("precheck", "yes");
				request.setAttribute("actReportId", actReportId);
			} else {
				request.setAttribute("precheck", "");
			}
		} else {
			request.setAttribute("precheck", "");
		}
	}

	private String getActReportId(Long repId) {
		ReportDefineService rs = this.getReportDefineService();
		Report report = rs.getReport(repId);
		if (report.getReportType().intValue() != 118) {// 118 手工录入报表 TODO
			return "" + report.getPkid();
		}
		String repName = report.getName();
		String actReportId = "";
		int ind_ = repName.lastIndexOf('_');
		if (ind_ > 0) {
			actReportId = repName.substring(ind_ + 1);
			try {
				Integer.parseInt(actReportId);
			} catch (NumberFormatException e) {
				actReportId = "";
			}
		}
		return actReportId;
	}

	private void setBuildasinput(ReportViewForm f, HttpServletRequest request,
			Report r) {

		String buildasinput = FuncConfig.getProperty("reportview.buildasinput");
		if ("yes".equals(buildasinput)) {
			request.setAttribute("buildasinput", buildasinput);
		}
	}

}
