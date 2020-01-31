package com.krm.ps.birt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.common.dto.DTO;
import com.krm.ps.common.exception.KRMException;
import com.krm.ps.common.exception.ParameterCheckException;
import com.krm.ps.common.process.KProcessHandler;
import com.krm.ps.framework.common.web.action.BaseAction;

public class ShowBirtAction extends BaseAction{
public ActionForward showBirt(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	try {
		EnterSearchBirtProcess esbProcess=new EnterSearchBirtProcess();
		DTO out= KProcessHandler.doProcess(request, response, esbProcess);
	} catch(ParameterCheckException e){
        mapping.findForward("exception");
        e.printStackTrace();
    }catch(KRMException e){
        mapping.findForward("error");
        e.printStackTrace();
    }
	return mapping.findForward("searchbirt");
}
public ActionForward enterShowBirt(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	try {
		
	
	ShowBirtProcess sbProcess=new ShowBirtProcess();
	DTO out=KProcessHandler.doProcess(request, response, sbProcess);
	} catch(ParameterCheckException e){
        mapping.findForward("exception");
        e.printStackTrace();
    }catch(KRMException e){
        mapping.findForward("error");
        e.printStackTrace();
    }
	return mapping.findForward("searchbirt");
}	
}
