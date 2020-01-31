package com.krm.ps.sysmanage.usermanage.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;


/**
*
* @struts.action name="dictionaryForm" path="/DictionaryAction" scope="request" 
*  validate="false" parameter="method" input="dictionary"
*  
* @struts.action-forward name="dictionary" path="/usermanage/Dictionary.jsp"
* @struts.action-forward name="inanition" path="/inanition.jsp"
*/

public class DictionaryAction extends BaseAction
{
	public ActionForward diclist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'diclist' method");
		}
		DictionaryService us = (DictionaryService)this.getBean("dictionaryService");
		request.setAttribute("userList",us.getUnitcode());
		//request.setAttribute("DictionaryList1",us.getALLsystem());
        return mapping.findForward("dictionary");
		
	}
	public ActionForward inanition (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'inanition' method");
		}
        return mapping.findForward("inanition");
		
	}
	/*
	public ActionForward dictionaryedit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//ReportsService us = getReportService();
		//request.setAttribute("userList",us.getReports());
    return mapping.findForward("edit");
		
	}
	/*
	public ActionForward dictionarydel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ReportsService us = getReportService();
		Long userid=new Long(request.getParameter("userid"));
		System.out.print(userid);
		if(null!=userid){
			us.removeReports(userid);
		}
		return  dictionaryedit(mapping,form,request,response);
		
		
	}
	public ActionForward dictionarysave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ReportsService us = getReportService();
		Long userid=new Long(request.getParameter("userid"));
		System.out.print(userid);
		if(null!=userid){
			us.removeReports(userid);
		}
		return  edit(mapping,form,request,response);
		
		
	}*/
	public ActionForward save (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'save' method");
		}

		Long r=new Long(1234567);
		 
        return mapping.findForward("inanition");
		
	}

}
