package com.krm.ps.model.datafill.web.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.datafill.services.ReportDefineService;
import com.krm.ps.model.datafill.web.form.ReportViewForm;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.FuncConfig;




public class ReportViewAction extends BaseAction {
	ReportDefineService rs = (ReportDefineService)getBean("datafillReportDefineService");
	
	public ActionForward enterInputSearch1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		try{
			if (log.isDebugEnabled()) {
				log.info("Entering 'enterInputSearch1' method");
			}
			// 2007.11.30 lxk 将上次录入的日期、机构、报表选项保留到session中，作为下次录入时的默认选项
			ReportViewForm reportViewForm = (ReportViewForm) form;
			HttpSession session = request.getSession();
			String queryFlag = request.getParameter("queryFlag");
			String levelFlag = request.getParameter("levelFlag");
			String editFlag=request.getParameter("editFlag");//先加入区分进入数据修改页面还是数据查询页面的标志。
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

			// add by lxk 20080514 设置一个默认报表
			/*if (reportId == null) {
				User user = (User) request.getSession().getAttribute("user");
				List repList = rs.getReports(dataDate, user.getPkid());
			}---chm*/

			if (reportViewForm.getDataDate() == null) {
				String loginDate = (String) request.getSession().getAttribute(
						"logindate");
				reportViewForm.setDataDate(loginDate);
			}
			updateFormBean(mapping, request, reportViewForm);

			request.getSession().setAttribute("canEdit", "1");

			String newwinbutton = FuncConfig.getProperty("reportform.newwinbutton");
			
			//System.out.println(newwinbutton+"�鿴��ӡ��4������ֵ");
			request.setAttribute("newwinbutton", newwinbutton);
			// 设置组织机构
			if(levelFlag!=null&&!"".equals(levelFlag)) {
				request.setAttribute("params", "&paramdate=" + dataDate
						+ "&paramorgan=" + organId +"&levelFlag="+ levelFlag);
			} else {
				request.setAttribute("params", "&paramdate=" + dataDate
						+ "&paramorgan=" + organId);
			}
			request.setAttribute("orgparam", "&date=" + dataDate);
			request.setAttribute("organId", organId);
			request.setAttribute("dataDate", dataDate);
			request.setAttribute("reportId", reportId);

			if("1".equals(queryFlag)) {
				request.setAttribute("canEdit", "0");
			}else {
				request.getSession().setAttribute("canEdit", "1");
			}
			if("0".equals(levelFlag)) {
				return mapping.findForward("searchDatafill");
			}
			if("1".equals(editFlag)){
				//进入数据修改页面
				return mapping.findForward("search2");
			}
			//进入数据查询页
			return mapping.findForward("search1");
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}
	
	/**
	 * 数据录入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward enterInputSearch2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		try{
			if (log.isDebugEnabled()) {
				log.debug("Entering 'enterInputSearch2' method");
			}
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

			/*if (reportId == null) {
				User user = (User) request.getSession().getAttribute("user");
				List repList = rs.getReports(dataDate, user.getPkid());
			}---chm*/

			if (reportViewForm.getDataDate() == null) {
				String loginDate = (String) request.getSession().getAttribute(
						"logindate");
				reportViewForm.setDataDate(loginDate);
			}

			request.setAttribute("orgparam", "&date=" + dataDate);
			request.setAttribute("organId", organId);
			request.setAttribute("dataDate", dataDate);
			request.setAttribute("reportId", reportId);
			return mapping.findForward("searchDatafill1");
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}


}
