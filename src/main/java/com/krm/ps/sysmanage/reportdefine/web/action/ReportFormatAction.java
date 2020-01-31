package com.krm.ps.sysmanage.reportdefine.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.reportdefine.services.ReportFormatService;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.util.ReportFormatUtil;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;
import com.krm.ps.sysmanage.reportdefine.vo.ReportItem;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.web.form.ReportFormatForm;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.DateUtil;
import com.krm.ps.util.StringUtil;

/**
 * @struts.action name="reportFormatForm" path="/reportFormat" scope="request"
 *                validate="false" parameter="method" input="list"
 * 
 * @struts.action-forward name="list" path="/reportformat/repformatList.jsp"
 * @struts.action-forward name="activexReport"
 *                        path="/reportformat/activexReport.jsp"
 * @struts.action-forward name="commonDefine"
 *                        path="/reportformat/commonDefine.jsp"
 * @struts.action-forward name="defineReportFormat"
 *                        path="/reportformat/defineReport.jsp"
 * @struts.action-forward name="ajaxResponseText"
 *                        path="/common/ajaxResponseText.jsp"
 */
public class ReportFormatAction extends BaseAction {

	/**
	 * 进入报表格式定义界面
	 * 
	 */
	public ActionForward enterRepFormatDefine(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'enterRepFormatDefine' method");
		}
		Long reportId = getParamReportId(request);
		request.setAttribute(ReportFormatUtil.PARAM_REPORT_ID, reportId);
		request.setAttribute(ReportFormatUtil.ATTR_REPORT_ISNEW, "1");
		ReportDefineService rds = getReportDefineService();
		Report r = rds.getReport(reportId);
		request.getSession().setAttribute("repCreateId", r.getCreateId());
		request.getSession().setAttribute("formatReportId", reportId);
		return mapping.findForward("defineReportFormat");
	}

	/**
	 * 进入模板定义的公共界面
	 * 
	 */
	public ActionForward enterCommonDefine(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// wsx
		// 8-24,模板定义页面右frame显示取数报表改成ajax方式
		if (log.isDebugEnabled()) {
			log.info("Entering 'enterCommonDefine' method");
		}
		ReportDefineService rds = getReportDefineService();
		List reports = rds.getReports(null, null);
		Long repId = (Long) request.getSession().getAttribute("formatReportId");

		Long initRep = getInitReport(reports, repId);// 初始取数报表

//		ReportTable rt = getReportTable(request, initRep, true);

		request.setAttribute("reports", reports);
//		request.setAttribute("ReportTable", rt);

		return mapping.findForward("commonDefine");
	}

	/**
	 * 得到初始化的报表
	 */
	private Long getInitReport(List reports, Long repId) {

		Map reportMap = new HashMap();
		Iterator it = reports.iterator();
		while (it.hasNext()) {
			Report r = (Report) it.next();
			reportMap.put(r.getPkid(), r);
		}

		if (repId != null) {
			Report report = (Report) reportMap.get(repId);
			if (report != null) {
				
				String rn = report.getName();
				int ind = rn.lastIndexOf('_');// 手工录入报表以_ddd结尾表示取数报表id是ddd
				if (ind > 0) {
					String ri = rn.substring(ind + 1);
					try {
						Long rl = Long.valueOf(ri);
						Report rr = (Report) reportMap.get(rl);
						if (rr != null) {
							return rl;
						}
					} catch (NumberFormatException e) {
					}
				}
				
				String conCode = report.getConCode();// 根据conCode得到取数报表
				if (conCode != null && !"".equals(conCode)) {
					try {
						Long rl = Long.valueOf(conCode);
						Report rr = (Report) reportMap.get(rl);
						if (rr != null) {
							return rr.getPkid();
						}
					} catch (NumberFormatException e) {
					}
				}
				
				return report.getPkid();
			}
		}

		Report r = (Report) reports.get(0);
		return r.getPkid();
	}

	public ActionForward getReportTableAjax(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'getReportTableAjax' method");
		}
		String reportIdStr = request.getParameter("reportId");
		Long reportId = Long.valueOf(reportIdStr);
//		ReportTable rt = getReportTable(request, reportId);
//		StringBuffer responseText = new StringBuffer();
//		responseText.append(rt.getReportId());
//		responseText.append("$");
//		responseText.append(rt.getTargetIdName());
//		responseText.append("$");
//		responseText.append(rt.getItemIdName());
//
//		request.setAttribute(FormulaDefineUtil.ATTR_MEET_RESPONSE_TEXT,
//				responseText.toString());
		return mapping.findForward("ajaxResponseText");
	}

	/**
	 * 进入报表控件界面
	 * 
	 */
	public ActionForward enterActiveXReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'enterActiveXReport' method");
		}
		ReportFormatForm reportFormatForm = new ReportFormatForm();
		Long reportId = getParamReportId(request);
		String strformatId = request
				.getParameter(ReportFormatUtil.PARAM_REPORT_FORMATID);
		request.setAttribute(ReportFormatUtil.PARAM_REPORT_ID, reportId);
		reportFormatForm.setReportId(reportId);
		log.info("strformatId" + strformatId);
		if (strformatId != null && !strformatId.equals("")) {
			ReportFormatService mgr = getRepFormatService();
			Long reportFormatId = getParamReportFormatId(request);

			ReportFormat reportFormat = mgr.getReportFormat(reportFormatId);
			// reportFormatForm.setFrequency(reportFormat.getFrequency());
			reportFormatForm.setBeginDate(reportFormat.getBeginDate());
			reportFormatForm.setEndDate(reportFormat.getEndDate());
			reportFormatForm.setReportFormat(reportFormat.getReportFormat());
			reportFormatForm.setReportId(reportFormat.getReportId());
			reportFormatForm.setPkId(reportFormat.getPkId());
		} else {
			reportFormatForm.setBeginDate("20050101");
			reportFormatForm.setEndDate("20151231");
		}
		updateFormBean(mapping, request, reportFormatForm);

		// DictionaryService mgr2 = getDictionaryService();
		// List frequencyList = mgr2.getReportfrequency();
		// request.setAttribute(FormulaDefineUtil.ATTR_FREQUENCY_LIST,
		// frequencyList);
		return mapping.findForward("activexReport");
	}

	/**
	 * 列出已经定义的报表模板
	 * 
	 */
	public ActionForward listReportFormats(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'listReportFormats' method");
		}
		User user = getUser(request);
		ReportFormatForm reportFormatForm = (ReportFormatForm) form;
		Long reporttype = reportFormatForm.getReporttype();
		if (reporttype == null) {
			reporttype = new Long(0);
		}
		ReportFormatService mgr = getRepFormatService();
		List reporFormatList = mgr.getReportFormatsByType(reporttype,user.getPkid(),false);
		ReportDefineService mgr2 = getReportDefineService();
		//List typeList = mgr2.getAllReportTypes();
		List typeList = mgr2.getReportTypes(user.getPkid());
		
		HttpSession session = request.getSession();
//		ChartDefineService cs = ReportChart.getChartDefineService(this);
//		cs.updateFormatListwithChart(reporFormatList);
		session.setAttribute("types_reportFormat", typeList);
		session.setAttribute("reportType_reportFormat", reporttype);
		session.setAttribute("reportFormatList", reporFormatList);
		session.setAttribute("createId", user.getPkid());
		session.setAttribute("isAdmin", user.getIsAdmin());
		response.sendRedirect(request.getContextPath()
				+ "/reportFormat.do?method=list");

		return null;
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		return mapping.findForward("list");
	}

	/**
	 * 修改一个已经定义的报表模板
	 * 
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'edit' method");
		}
		Long reportId = getParamReportId(request);
		Long reportFormatId = getParamReportFormatId(request);
		request.setAttribute(ReportFormatUtil.PARAM_REPORT_ID, reportId);
		request.setAttribute(ReportFormatUtil.ATTR_REPORT_ISNEW, "0");
		request.setAttribute(ReportFormatUtil.PARAM_REPORT_FORMATID,
				reportFormatId);
		ReportDefineService rds = getReportDefineService();
		Report r = rds.getReport(reportId);
		if (r != null) {
			request.getSession().setAttribute("repCreateId", r.getCreateId());
			request.getSession().setAttribute("formatReportId", reportId);
		}
		return mapping.findForward("defineReportFormat");
	}

	/**
	 * 删除一个报表模板
	 * 
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'delete' method");
		}
		ReportFormatForm reportFormatForm = (ReportFormatForm) form;
		ReportFormatService mgr = getRepFormatService();

		String formatId = request.getParameter("pkid");
		mgr.removeReportFormat(Long.valueOf(formatId));

		reportFormatForm.setReporttype(reportFormatForm.getReporttype());
		updateFormBean(mapping, request, reportFormatForm);
		return listReportFormats(mapping, reportFormatForm, request, response);
	}

	/**
	 * 保存一个定义好的报表格式
	 * 
	 */
	public ActionForward saveReportFormat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'saveReportFormat' method");
		}
		ReportFormatForm reportFormatForm = (ReportFormatForm) form;
		ReportFormatService mgr = getRepFormatService();
		Long reportId = getParamReportId(request);
		String strFormatXml = reportFormatForm.getReportFormat();

		ReportFormat reportFormat = new ReportFormat();
		if (strFormatXml != null) {
			if (reportFormatForm.getPkId() != null) {
				reportFormat.setPkId(reportFormatForm.getPkId());
			}
			reportFormat.setReportId(reportId);
			reportFormat.setReportFormat(strFormatXml.trim());
			reportFormat.setCreateDate(DateUtil.getDateTime("yyyyMMdd",
					new Date()));
			reportFormat.setEditDate(DateUtil.getDateTime("yyyyMMdd",
					new Date()));
		
			reportFormat.setFrequency("1");// ? TODO
			if (reportFormatForm.getBeginDate() != null
					&& !reportFormatForm.getBeginDate().equals("")) {
				reportFormat.setBeginDate(reportFormatForm.getBeginDate());
				reportFormat.setEndDate(reportFormatForm.getEndDate());
			}
			reportFormat.setEditDate(DateUtil.getDateTime("yyyyMMdd",
					new Date()));
			mgr.saveReportFormat(reportFormat);
			reportFormatForm.setPkId(reportFormat.getPkId());
			updateFormBean(mapping, request, reportFormatForm);
			request.setAttribute(ReportFormatUtil.PARAM_REPORT_FORMATID,
					reportFormat.getPkId());
			request.setAttribute(ReportFormatUtil.ATTR_SAVE_RESULT,
					new Integer(0));
		} else {
			reportFormatForm.setPkId(null);
			request.setAttribute(ReportFormatUtil.PARAM_REPORT_FORMATID, null);
			request.setAttribute(ReportFormatUtil.ATTR_SAVE_RESULT,
					new Integer(-1));
		}

		updateFormBean(mapping, request, reportFormatForm);

		
		// DictionaryService mgr2 = getDictionaryService();
		// List frequencyList = mgr2.getReportfrequency();
		// request.setAttribute(FormulaDefineUtil.ATTR_FREQUENCY_LIST,
		// frequencyList);
		return mapping.findForward("activexReport");
	}
	
	//2007.3.28
	/**
	 * 使用AJAX方式得到扩展公式
	 * 
	 */
	public ActionForward extendRep(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'extendRep' method");
		}
		String str = null;		
		String extNum = request.getParameter("extNum");
		extNum = StringUtil.escapeUrlString(extNum);
		String unitArray = request.getParameter("unitArray");
		unitArray = StringUtil.escapeUrlString(unitArray);
		String notExtend = request.getParameter("notExtend");
		notExtend = StringUtil.escapeUrlString(notExtend);
		String dateExtend = request.getParameter("dateExtend");
		dateExtend = StringUtil.escapeUrlString(dateExtend);
		
		String[] units =  StringUtil.divideString(unitArray,",");
		str = fetchExtStr(units,extNum,notExtend,dateExtend);
		
//		if (str != null) {
//			request.setAttribute(FormulaDefineUtil.ATTR_MEET_RESPONSE_TEXT,
//					"ok:" + str);
//		} else {
//			request.setAttribute(FormulaDefineUtil.ATTR_MEET_RESPONSE_TEXT,
//					"xx:");
//		}
		return mapping.findForward("ajaxResponseText");
	}
	
	private String fetchExtStr(String[] units,String extNum,String notExtend,String dateExtend){
		String str = null;
		Object[] lst = new Object[units.length];
		for (int i=0;i<units.length;i++){
			if(notExtend!=""&&notExtend.indexOf(","+String.valueOf(i+1)+",")==-1){
				//需要扩展
				lst[i] = fetchExt(units[i],extNum,dateExtend);
			}else{
				//不需要扩展
				lst[i] = fetchNoExt(units[i],extNum,dateExtend);
			}
		}
		if (checkExt(lst,units.length)){
			//构造返回串
			str = contactStr(lst,extNum,units.length);
		}
		
		return str;
	}
	
	private List fetchNoExt(String unit,String extNum,String dateExtend){
		List lst = new ArrayList();
		String[] elems = StringUtil.divideString(unit,".");
		for (int i=0;i<Integer.parseInt(extNum);i++){
			if (elems.length==4&&"N".equals(dateExtend)){
				lst.add(elems[0]+"."+elems[1]+"."+elems[2]);
			}else{
				lst.add(unit);
			}
		}		
		return lst;
	}
	
	private List fetchExt(String unit,String extNum,String dateExtend){
		List lst = new ArrayList();
		String[] elems = StringUtil.divideString(unit,".");
		ReportDefineService rs = getReportDefineService();
		List extUnits = rs.getExtItem(elems[0],elems[1],extNum);
		if (extUnits.size()>0){
			for (int i=0;i<Integer.parseInt(extNum);i++){
				ReportItem ri = (ReportItem)extUnits.get(i);
				if ("N".equals(dateExtend)){
					lst.add(elems[0]+"."+ri.getCode()+"."+elems[2]);
				}else{
					if (elems.length==4){
						lst.add(elems[0]+"."+ri.getCode()+"."+elems[2]+"."+elems[3]);
					}else{
						lst.add(elems[0]+"."+ri.getCode()+"."+elems[2]);
					}
				}
			}
		}
		return lst;
	}
	
	private boolean checkExt(Object[] lst,int unitLen){
		boolean flag = true;
		for (int i=0;i<unitLen;i++){
			List para = (List)lst[i];
			if (para.size()==0){
				flag = false;
				break;
			}
		}
		
		return flag;
	}
	
	private String contactStr(Object[] lst,String extNum,int unitLen){
		String str = "";
		String s = "";
		for (int i=0;i<Integer.parseInt(extNum);i++){
			s = "";
			for (int j=0;j<unitLen;j++){
				List para = (List)lst[j];
			    s = s + para.get(i)+",";
			}
			str = str + s.substring(0,s.length()-1) + "|";
		}
		
		if (!"".equals(str)){
			return str.substring(0,str.length() - 1);
		}else{
			return null;
		}		
	}

	/**
	 * 得到URL中的报表格式ID参数数值
	 * 
	 * @param request
	 * @return
	 */
	private Long getParamReportFormatId(HttpServletRequest request) {
		return Long.valueOf(request
				.getParameter(ReportFormatUtil.PARAM_REPORT_FORMATID));
	}

	/**
	 * 得到URL中的报表ID参数数值
	 * 
	 * @param request
	 * @return
	 */
	private Long getParamReportId(HttpServletRequest request) {
		return Long.valueOf(request
				.getParameter(ReportFormatUtil.PARAM_REPORT_ID));
	}

	/**
	 * 取一条报表信息
	 * 
	 * @param request
	 * @param reportId
	 * @return
	 */
//	private ReportTable getSingleReportTable(HttpServletRequest request,
//			Long reportId) {
//
//		ReportDefineService rds = getReportDefineService();
//
//		Report report = rds.getReport(reportId);
//		List reportItemsList = rds.getReportItems(reportId);
//		List reportTargetsList = rds.getReportTargets(reportId);
//
//		String[][] items = new String[reportItemsList.size()][2];
//		String[][] targets = new String[reportTargetsList.size()][2];
//		int intItems = 0;
//		int intTargets = 0;
//		for (Iterator itItems = reportItemsList.iterator(); itItems.hasNext();) {
//			ReportItem reportItem = (ReportItem) itItems.next();
//			items[intItems][0] = reportItem.getCode();
//			items[intItems][1] = reportItem.getItemName();
//			intItems++;
//		}
//		for (Iterator itTargets = reportTargetsList.iterator(); itTargets
//				.hasNext();) {
//			ReportTarget reportTarget = (ReportTarget) itTargets.next();
//			targets[intTargets][0] = reportTarget.getTargetField();
//			targets[intTargets][1] = reportTarget.getTargetName();
//			intTargets++;
//		}
//		ReportTable reportTable = new ReportTable(report.getPkid().toString(),
//				report.getName(), items, targets);
//
//		return reportTable;
//	}

//	private ReportTable getReportTable(HttpServletRequest request, Long reportId) {
//		return getReportTable(request, reportId, false);
//	}
//
//	private ReportTable getReportTable(HttpServletRequest request,
//			Long reportId, boolean newReportTable) {
//		ReportTable rt = null;
//		Map reportTableMap = (Map) request.getSession().getAttribute(
//				"reportTableMap2");// without formula
//		if (newReportTable || reportTableMap == null) {
//			reportTableMap = new HashMap();
//			request.getSession()
//					.setAttribute("reportTableMap2", reportTableMap);
//		} else {
//			rt = (ReportTable) reportTableMap.get(reportId);
//		}
//		if (rt == null) {
//			rt = getSingleReportTable(request, reportId);
//			reportTableMap.put(reportId, rt);
//		}
//		return rt;
//	}
//	
	public ActionForward r(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'r' method");
		}
		//System.out.println("-");
		ReportFormatService mgr = getRepFormatService();
		String s="一滴水123abc";
		pb(s.getBytes());///
		ReportFormat rf=mgr.getReportFormat(new Long(1275));
		rf.setReportFormat(s);
		mgr.saveReportFormat(rf);
		ReportFormat rf2=mgr.getReportFormat(new Long(1275));
		String fm=rf2.getReportFormat();
		System.out.println(fm);
		pb(fm.getBytes());///		
		
		return null;
	}
	
	private void pb(byte[] ba) {
		for(int i=0;i<ba.length;i++) {
			System.out.print(ba[i]+"\t");
		}
		System.out.println(".");
	}

	
//	public ActionForward getReportGridXml(ActionMapping mapping, ActionForm form,
//		HttpServletRequest request, HttpServletResponse response)
//		throws Exception
//	{
//		int pos = Integer.parseInt(request.getParameter("posStart"));
//		int count = Integer.parseInt(request.getParameter("count"));
//		Long reportId = getParamReportId(request);
//		ReportTable rt = getReportTable(request, reportId);
//		String[][] itemArray = rt.getItems();
//		String[][] targetsArray = rt.getTargets();
//		StringBuffer sb = new  StringBuffer();
//		sb.append("<?xml version='1.0' encoding='utf-8'?>\n");
//		sb.append("<rows pos=\"");
//		sb.append(pos);
//		sb.append("\">");
//		if(itemArray != null && itemArray.length > 0)
//		{
//			for(int i=0;i<count;i++)
//			{
//				sb.append("<row id='r");
//				sb.append(i+pos);
//				sb.append("'><cell><![CDATA[<font color=\"#000000\">");
//				sb.append(itemArray[i+pos][0]);
//				sb.append("</font>]]></cell><cell><![CDATA[<font color=\"#000000\">");
//				sb.append(itemArray[i+pos][1]);
//				sb.append("</font>]]></cell>");
//				for(int k=0;k<targetsArray.length;k++)
//				{
//					String cellValue = reportId+ "." + itemArray[i+pos][0] + "." + targetsArray[k][0];
//					sb.append("<cell>");
//					sb.append(cellValue);
//					sb.append("</cell>");
//				}
//				sb.append("</row>");
//			}
//		}
//		sb.append("</rows>");
//		request.setAttribute("treeXml", sb.toString());
//		return mapping.findForward("tree");
//	}
}
