package com.krm.ps.sysmanage.reportdefine.web.action;


import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
//import com.krm.slsint.log.util.LogUtil;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.reportdefine.web.form.ReportTypeForm;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.Constants;
import com.krm.ps.util.ConvertUtil;

/**
*
* @struts.action name="reportTypeForm" path="/reportTypeAction" scope="request" 
*  validate="false" parameter="method" input="edit"
*  
* @struts.action-forward name="edit" path="/reportdefine/reportTypeForm.jsp"
* @struts.action-forward name="list" path="/reportdefine/reportTypeList.jsp"
* @struts.action-forward name="sort" path="/common/sortcommon.jsp"
* @struts.action-forward name="sort_list" path="/reportTypeAction.do?method=list"
*/

public class ReportTypeAction extends BaseAction{
	
	private static String TYPE_ID = "typeid";
	private static String TYPE_LIST = "typeList";
	private static String SYSTEM_CODE = "systemCodes";
	
	private Long getTypeId(HttpServletRequest request){
		String typeid = request.getParameter(TYPE_ID);
		if(null!=typeid){
			return new Long(typeid);
		}
		return null;
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
		{
		if (log.isDebugEnabled()) {
			log.info("Entering 'cancel' method");
		}
			return list(mapping,form,request,response);
		}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		ReportDefineService rs = getReportDefineService();
		request.setAttribute(TYPE_LIST,rs.getAllReportTypes(null));
		User user = getUser(request);
		request.setAttribute("createId", user.getPkid());
		request.setAttribute("isAdmin", user.getIsAdmin());
		return mapping.findForward("list");
	}
	
	public ActionForward taxisType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'taxisType' method");
		}
		setToken(request);
		ReportDefineService rs = getReportDefineService();
		List reportTypes = rs.getAllReportTypes(null);
		List repType = new ArrayList();
		for(Iterator itr = reportTypes.iterator();itr.hasNext();){
			ReportType rt = (ReportType)itr.next();
			Object [] o = new Object[2];
			o[0] = rt.getPkid();
			o[1] = rt.getName();
			repType.add(o);
		}
		
		request.setAttribute("fileTitle","报表定义－》报表类型排序");
		request.setAttribute("sortList",repType);
		request.setAttribute("serviceName", "reportTypeSortService");
		request.setAttribute("sortTitle", "报表类型排序");
		ActionForward forward = mapping.findForward("sort_list");
		String path = forward.getPath();
		request.setAttribute("forwardURL", path);
		return mapping.findForward("sort");
	}
	
	public ActionForward toEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'toEdit' method");
		}
		setToken(request);
		DictionaryService ds = getDictionaryService();
		request.setAttribute("showlevel", ds.getDics(2002));
		request.setAttribute(SYSTEM_CODE,ds.getALLsystem());
		return mapping.findForward("edit");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'edit' method");
		}
		ReportTypeForm reportTypeForm = (ReportTypeForm)form;
		ReportType type = new ReportType();
		ReportDefineService rs = getReportDefineService();
		Long typeid = getTypeId(request);
		if(null!=typeid){
			type = rs.getReportType(typeid);
		}
		ConvertUtil.copyProperties(reportTypeForm,type,true);
		return toEdit(mapping,reportTypeForm,request,response);
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'save' method");
		}
		if(!tokenValidatePass(request)){
			
			return list(mapping,form,request,response);
		}
		User user = getUser(request);
        final String userName=user.getName();
       
      //插入系统日志־


		ReportDefineService rs = getReportDefineService();
		ReportTypeForm typeForm = (ReportTypeForm)form;
		ReportType type = new ReportType();
		Integer showlevel = typeForm.getShowlevel();
		type.setShowlevel(showlevel);
		ConvertUtil.copyProperties(type, typeForm, true);
		type.setCreateId(new Integer(user.getPkid().intValue()));
		type.setStatus(Constants.STATUS_AVAILABLE);
		rs.saveReportType(type);
		
		return list(mapping,form,request,response);
	}
	
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'del' method");
		}
		User user = getUser(request);
        final String userName=user.getName();
       
  

		ReportDefineService rs = getReportDefineService();
		Long typeid = getTypeId(request);
		if(null!=typeid){
			rs.removeReportType(typeid);
		}
		return list(mapping,form,request,response);
	}

}
