package com.krm.ps.framework.common.sort.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.sort.service.SortService;
import com.krm.ps.framework.common.sort.web.form.SortForm;
import com.krm.ps.framework.common.web.action.BaseAction;

/**
 * @struts.action name="sortForm" path="/sortAction" scope="request" validate="false" parameter="method" input="view"
 * @struts.action-forward name="view" path="/common/uploadForm.jsp"
 */
public class SortAction extends BaseAction{
	

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
		{
			SortForm sortForm = (SortForm)form;
			ActionForward forward = new ActionForward(sortForm.getForwardURL());
			if(!this.isTokenValid(request)){
				return forward;
			}
			try {
				if(1==sortForm.getOrderFlag().intValue()){
					SortService service = (SortService)getBean(sortForm.getServiceName());
					service.sort(sortForm.getOrderList());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return forward;
		}

}
