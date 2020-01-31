package com.krm.ps.model.east1104dz.web.action;

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
import com.krm.ps.model.datafill.web.form.ReportViewForm;
import com.krm.ps.model.east1104dz.service.QueryDzService;
import com.krm.ps.model.east1104dz.web.from.QueryDzFrom;
import com.krm.ps.sysmanage.usermanage.vo.User;

public class QueryDzAction extends BaseAction {

	public QueryDzService getQueryDzService() {
		return (QueryDzService) getBean("queryDzService");
	}

	/**
	 * east 1104 dz
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryorgandate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			QueryDzFrom queryDzFrom = (QueryDzFrom) form;
			HttpSession session = request.getSession();
			String dataDate = (String) session.getAttribute("inputDataDate");
			if (dataDate == null) {
				dataDate = (String) session.getAttribute("logindate");
			}

			String organId = (String) session.getAttribute("inputOrganId");
			if (organId == null) {
				organId = getUser(request).getOrganId();
			}

			if (queryDzFrom.getRepdate() == null) {
				String loginDate = (String) request.getSession().getAttribute(
						"logindate");
				queryDzFrom.setRepdate(loginDate);
			}
			request.setAttribute("orgparam", "&date=" + dataDate);
			request.setAttribute("organId", organId);
			request.setAttribute("dataDate", dataDate);
			return mapping.findForward("queryorgandate");
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
	 * east 1104 dz
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryorgandateList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Entering 'queryorgandateList' method");
		}
		try{
			HttpSession session = request.getSession();
			String dataDate = (String) request.getParameter("dataDate");
			if (dataDate == null) {
				dataDate = (String) session.getAttribute("logindate");
			}
			dataDate = dataDate.replaceAll("-", "").substring(0, 6) + "00";

			String organId = (String) request.getParameter("organId");
			if (organId == null) {
				organId = getUser(request).getOrganId();
			}
			int idxid = getUser(request).getOrganTreeIdxid();
			List repList = getQueryDzService().getQuerydz(dataDate, organId, idxid);

			request.setAttribute("repList", repList);
			request.setAttribute("organId", organId);
			request.setAttribute("dataDate", dataDate);
			return mapping.findForward("east1104list");
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
