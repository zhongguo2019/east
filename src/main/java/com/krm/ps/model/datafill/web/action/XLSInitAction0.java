package com.krm.ps.model.datafill.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.datafill.services.DataFillService;
import com.krm.ps.model.datafill.web.form.XLSInitForm0;
import com.krm.ps.sysmanage.usermanage.vo.User;

public class XLSInitAction0 extends BaseAction {

	DataFillService dfs = (DataFillService) getBean("datafillDataFillService");

	public ActionForward uploadExcelYJH(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		XLSInitForm0 xlsInitForm = (XLSInitForm0) form;
		User user = getUser(request);
		FormFile data = xlsInitForm.getDataFile();
		String resultablename = request.getParameter("resultablename1");
		String filename = request.getParameter("xlsfilename");
		String organId = request.getParameter("organId");
		String reportId=request.getParameter("reportId");
		String dataDate=request.getParameter("dataDate");
		filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
		int message = dfs.XLSInit(data, filename, organId, resultablename);
		//System.out.println("message===="+message);
		request.setAttribute("organId", organId);
		request.setAttribute("reportId",reportId );
		request.setAttribute("dataDate", dataDate);
		request.setAttribute("message", message);
		return mapping.findForward("xlslsint");
	}
}
