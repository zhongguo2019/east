package com.krm.ps.framework.property.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.common.logger.KRMLogger;
import com.krm.common.logger.KRMLoggerUtil;
import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.framework.property.service.OrganPropertyService;
import com.krm.ps.framework.property.vo.Op;
import com.krm.ps.framework.property.vo.OrganProperty;
import com.krm.ps.framework.property.web.form.OrganPropertyForm;

/**
 * 
 * @struts.action name="organPropertyForm" path="/organPropertyAction" scope="request" 
 *  validate="false" parameter="method"
 * 
 * @struts.action-forward name="list" path="/property/op_list.jsp"
 * @struts.action-forward name="message" path="/property/message.jsp"
 * @struts.action-forward name="setUI" path="/property/op_set.jsp"
 * @struts.action-forward name="toList" path="/organPropertyAction.do?method=getProperty"  redirect="true"
 */
public class OrganPropertyAction extends BaseAction{

	KRMLogger logger = KRMLoggerUtil.getLogger(OrganPropertyAction.class); 
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("list");
	}
	
	/**
	 * 获取属性列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getProperty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String organCode = (String)request.getSession().getAttribute("organCode");
		logger.info("session中的："+organCode);
		if(organCode==null){
			organCode = request.getParameter("organCode");
			logger.info("第一次请求的:"+organCode);
		}else{
			request.getSession().removeAttribute("organCode");
		}
		List opList = getOrganPropertyService().getOp(organCode);
		List newList = new ArrayList();
		for (int i = 0; i < opList.size(); i++) {
			Op op = new Op();
			Object[] obj = (Object[]) opList.get(i);
			Long proId = new Long(obj[0].toString());
			String proName = null;
			if(obj[1]!=null){
				proName = obj[1].toString();
			}
			String proValue = null;
			if(obj[1]!=null){
				proValue = obj[2].toString();
			}
			String proComment = null;
			if(obj[3]!=null){
				proComment = obj[3].toString();
			}
			String organCode2 = null;
			if(obj[4]!=null){
				organCode2 = obj[4].toString();
			}
			Long opProId = null;
			if(obj[5]!=null){
				opProId = new Long(obj[5].toString());
			}
			String opValue = null;
			if(obj[6]!=null){
				opValue = obj[6].toString();
			}
			op.setProId(proId);
			op.setProName(proName);
			op.setProValue(proValue);
			op.setProComment(proComment);
			op.setOrganCode(organCode2);
			op.setOpProId(opProId);
			op.setOpValue(opValue);
			newList.add(op);
		}
		request.setAttribute("newList", newList);
		request.setAttribute("organCode", organCode);
		return mapping.findForward("list");
	}
	
	/**
	 * 设置关联页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String proId = request.getParameter("proId");
		String organCode = request.getParameter("organCode");
		
		OrganPropertyForm actionForm = (OrganPropertyForm) form;
		actionForm.setProId(proId);
		actionForm.setOrganCode(organCode);
		
		request.setAttribute("proId", proId);
		request.setAttribute("organCode", organCode);
		return mapping.findForward("setUI");
	}
	
	/**
	 * 设置关联 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		OrganPropertyForm actionForm = (OrganPropertyForm) form;
		String proId = actionForm.getProId();
		String organCode = actionForm.getOrganCode(); 
		String value = actionForm.getValue();
		
		OrganProperty op = new OrganProperty();
		op.setProId(new Long(proId));
		op.setOrganCode(organCode);
		op.setValue(value);
		
		//保存时判断proId、orangeCode是否确定实体唯一
		OrganProperty op2 = getOrganPropertyService().getOrganProperty(organCode, new Long(proId));
		if(op2==null){
			getOrganPropertyService().addOrganProperty(op);
//			return getProperty(mapping, actionForm, request, response);
			request.getSession().setAttribute("organCode", organCode);
			return mapping.findForward("toList");
//			request.setAttribute("message", "关联成功"); 
//			return mapping.findForward("message");
		}else{
			String message = "数据库中已存在，关联失败";
			request.setAttribute("message", message); 
			return mapping.findForward("message");
		}
	}
	
	public ActionForward add2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("进入add2方法");
		OrganPropertyForm actionForm = (OrganPropertyForm) form;
		String proId = actionForm.getProId();
		String organCode = actionForm.getOrganCode(); 
		String value = actionForm.getValue();
		
		OrganProperty op = new OrganProperty();
		op.setProId(new Long(proId));
		op.setOrganCode(organCode);
		op.setValue(value);
		
		//保存时判断proId、orangeCode是否确定实体唯一
		OrganProperty op2 = getOrganPropertyService().getOrganProperty(organCode, new Long(proId));
		if(op2==null){
			getOrganPropertyService().addOrganProperty(op);
			logger.info("保存成功");
			PrintWriter out = response.getWriter();
			out.print("success");
			out.flush();
			return null;
//			return getProperty(mapping, actionForm, request, response);
//			request.setAttribute("message", "关联成功"); 
//			return mapping.findForward("message");
		}else{
			String message = "数据库中已存在，关联失败";
			logger.info(message);
			PrintWriter out = response.getWriter();
			out.print("failed");
			out.flush();
			return null;
		}
	}
	
	/**
	 * 解除关联
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String proId = request.getParameter("proId");
		String organCode = request.getParameter("organCode");
		
		OrganProperty op = getOrganPropertyService().getOrganProperty(organCode, new Long(proId));
		getOrganPropertyService().deleteOrganProperty(op);
		request.getSession().setAttribute("organCode", organCode);
		return mapping.findForward("toList");
//		return getProperty(mapping, form, request, response);
	}
	
	/**
	 * 编辑页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String proId = request.getParameter("proId");
		String organCode = request.getParameter("organCode");
		
		OrganPropertyForm actionForm = (OrganPropertyForm) form;
		OrganProperty op = getOrganPropertyService().getOrganProperty(organCode,new Long(proId));
		
		actionForm.setProId(proId);
		actionForm.setOrganCode(organCode);
		actionForm.setValue(op.getValue());
		
		request.setAttribute("proId", proId);
		request.setAttribute("organCode", organCode);
		request.setAttribute("op", op);
		request.setAttribute("edit_flag", "true");
		return mapping.findForward("setUI");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String proId = request.getParameter("proId");
		String organCode = request.getParameter("organCode");
		
		OrganPropertyForm actionForm = (OrganPropertyForm) form;
		OrganProperty op = getOrganPropertyService().getOrganProperty(organCode,new Long(proId));
		
		op.setValue(actionForm.getValue());
		getOrganPropertyService().updateOrganProperty(op);
		request.getSession().setAttribute("organCode", organCode);
		return mapping.findForward("toList");
		//		String message = "修改成功";
//		request.setAttribute("message", message);
//		return mapping.findForward("message");
	}
	
	public ActionForward edit2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("进入edit2方法");
		OrganPropertyForm actionForm = (OrganPropertyForm) form;
		String proId = actionForm.getProId();
		String organCode = actionForm.getOrganCode(); 
		String value = actionForm.getValue();
		
		OrganProperty op = getOrganPropertyService().getOrganProperty(organCode,new Long(proId));
		op.setValue(value);
		
		getOrganPropertyService().updateOrganProperty(op);
		logger.info("更改成功");
		PrintWriter out = response.getWriter();
		out.print("success");
		out.flush();
		return null;
		//		String message = "修改成功";
//		request.setAttribute("message", message);
//		return mapping.findForward("message");
	}
	
	public ActionForward delete2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("进入delete2方法");
		String proId = request.getParameter("proId");
		String organCode = request.getParameter("organCode");
		
		OrganProperty op = getOrganPropertyService().getOrganProperty(organCode,new Long(proId));
		getOrganPropertyService().deleteOrganProperty(op);
		logger.info("删除成功");
		PrintWriter out = response.getWriter();
		out.print("deletesuccess");
		out.flush();
		return null;
		//		String message = "修改成功";
//		request.setAttribute("message", message);
//		return mapping.findForward("message");
	}
	
	public OrganPropertyService getOrganPropertyService(){
		return (OrganPropertyService) getBean("organPropertyService");
	}
}
