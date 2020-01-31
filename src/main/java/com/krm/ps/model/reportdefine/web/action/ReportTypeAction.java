package com.krm.ps.model.reportdefine.web.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.reportdefine.services.DictionaryService;
import com.krm.ps.model.reportdefine.services.ReportDefineService;
import com.krm.ps.model.reportdefine.web.form.ReportTypeForm;
import com.krm.ps.model.sysLog.util.LogUtil;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.Constants;
import com.krm.ps.util.ConvertUtil;

/**
 * 报表定义--报表类型维护
 * 
 * @author Chm
 * 
 *         2014-7-22
 */
public class ReportTypeAction extends BaseAction {

	private static String TYPE_LIST = "typeList";
	private static String SYSTEM_CODE = "systemCodes";
	private static String TYPE_ID = "typeid";

	/**
	 * 报表类型维护--列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'list' method");
		}

		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		request.setAttribute(TYPE_LIST, rs.getAllReportTypes(null));
		User user = getUser(request);
		request.setAttribute("createId", user.getPkid());
		request.setAttribute("isAdmin", user.getIsAdmin());
		return mapping.findForward("list");
	}

	/**
	 * 报表类型维护--增加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'toEdit' method");
		}

		setToken(request);
		DictionaryService ds = (DictionaryService) getBean("rddictionaryService");
		request.setAttribute("showlevel", ds.getDics(2002));
		request.setAttribute(SYSTEM_CODE, ds.getALLsystem());
		System.out.println(ds.getALLsystem());
		return mapping.findForward("edit");
	}

	/**
	 * 报表类型维护--排序
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taxisType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'taxisType' method");
		}
		setToken(request);
		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		List reportTypes = rs.getAllReportTypes(null);
		List repType = new ArrayList();
		for (Iterator itr = reportTypes.iterator(); itr.hasNext();) {
			ReportType rt = (ReportType) itr.next();
			Object[] o = new Object[2];
			o[0] = rt.getPkid();
			o[1] = rt.getName();
			repType.add(o);
		}

		request.setAttribute("fileTitle", "报表定义－》报表类型排序");
		request.setAttribute("sortList", repType);
		request.setAttribute("serviceName", "rdreportDefineService");
		request.setAttribute("sortTitle", "报表类型排序");
		ActionForward forward = mapping.findForward("sort_list");
		String path = forward.getPath();
		request.setAttribute("forwardURL", path);
		request.setAttribute("flag111", request.getParameter("flag111"));
		return mapping.findForward("sort");
	}

	/**
	 * 报表类型维护--编辑
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
			log.debug("Entering 'edit' method");
		}
		ReportTypeForm reportTypeForm = (ReportTypeForm) form;
		ReportType type = new ReportType();
		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		Long typeid = getTypeId(request);
		if (null != typeid) {
			type = rs.getReportType(typeid);
		}
		ConvertUtil.copyProperties(reportTypeForm, type, true);
		return toEdit(mapping, reportTypeForm, request, response);
	}

	private Long getTypeId(HttpServletRequest request) {
		String typeid = request.getParameter(TYPE_ID);
		if (null != typeid) {
			return new Long(typeid);
		}
		return null;
	}

	/**
	 * 报表类型维护--删除
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
			log.debug("Entering 'del' method");
		}
		User user = getUser(request);
		final String userName = user.getName();

		// 插入系统日志־
		getSysLogService().insertLog(user.getPkid() + "", userName, "-1",
				LogUtil.LogType_User_Operate, "", "报表定义－》报表类型维护-》删除", "-1");

		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		Long typeid = getTypeId(request);
		if (null != typeid) {
			rs.removeReportType(typeid);
		}
		return list(mapping, form, request, response);
	}

	/**
	 * 报表类型维护--保存
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
			log.debug("Entering 'save' method");
		}
		if (!tokenValidatePass(request)) {
			return list(mapping, form, request, response);
		}
		User user = getUser(request);
		final String userName = user.getName();

		// 插入系统日志־
		getSysLogService().insertLog(user.getPkid() + "", userName, "-1",
				LogUtil.LogType_User_Operate, "", "报表定义－》报表类型维护", "-1");

		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		ReportTypeForm typeForm = (ReportTypeForm) form;
		ReportType type = new ReportType();
		Integer showlevel = typeForm.getShowlevel();
		type.setShowlevel(showlevel);
		ConvertUtil.copyProperties(type, typeForm, true);
		type.setCreateId(new Integer(user.getPkid().intValue()));
		type.setStatus(Constants.STATUS_AVAILABLE);
		rs.saveReportType(type);

		return list(mapping, form, request, response);
	}

	/**
	 * 报表类型维护--取消
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
			log.debug("Entering 'cancel' method");
		}
		return list(mapping, form, request, response);
	}

}
