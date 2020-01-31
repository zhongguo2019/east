package com.krm.ps.framework.property.web.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.krm.ps.framework.property.service.PropertyService;
import com.krm.ps.framework.property.vo.Prop;
import com.krm.ps.framework.property.web.form.PropertyForm;

/**
 * 
 * @struts.action name="propertyForm" path="/propertyAction" scope="request" 
 *  validate="false" parameter="method"
 * 
 * @struts.action-forward name="list" path="/property/property_list.jsp"
 * @struts.action-forward name="saveUI" path="/property/property_edit.jsp"
 * @struts.action-forward name="message" path="/property/message.jsp"
 * @struts.action-forward name="toList" path="/propertyAction.do?method=list" redirect="true"
 */
public class PropertyAction extends BaseAction{

	KRMLogger logger = KRMLoggerUtil.getLogger(PropertyAction.class); 
	
	/**
	 * 显示属性列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("list method....");
		List propertyList = getPropertyService().getAllProperty(); 
		System.out.println(propertyList.size()); 
		request.setAttribute("propertyList", propertyList);
		return mapping.findForward("list"); 
	}
	
	/*
	 * 添加页面 
	 */
	public ActionForward addUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("saveUI");
	}
	
	/** 添加 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//获取actionform
		PropertyForm actionForm = (PropertyForm) form;
		//新建property
		Prop property = new Prop();

		String proName = actionForm.getProName();
		String proValue = actionForm.getProValue();
		String proComment = actionForm.getProComment();

		property.setProName(proName);
		property.setProValue(proValue);
		property.setProComment(proComment);

		property.setStatus('1');
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String time = df.format(date);
		property.setCreateDate(time);

		/*//判断属性名称是否唯一
		List proNameList = getPropertyService().getProNameList();
		if(proNameList.contains(proName)){
			log.info("属性名称不唯一");
			StringBuffer sb = new StringBuffer();
			request.setAttribute(ALERT_MESSAGE, "属性名称不唯一");
			return mapping.findForward("saveUI");
		}else{
			getPropertyService().addProperty(property);
			String message = "保存成功";
			request.setAttribute("message", message);
			return mapping.findForward("message");
		}
		*/
		getPropertyService().addProperty(property);
		String message = "保存成功";
		request.setAttribute("message", message);
		return mapping.findForward("message");
	}
	
	/**
	 * 修改页面
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
		//获取actionform
		PropertyForm actionForm = (PropertyForm) form;
		String id = request.getParameter("id");
		Prop property = getPropertyService().getProperty(new Long(id));
		
		actionForm.setProName(property.getProName());
		actionForm.setProValue(property.getProValue());
		actionForm.setProComment(property.getProComment());
		
		request.setAttribute("id", id);
		request.setAttribute("property", property);
		return mapping.findForward("saveUI");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//获取actionform
		PropertyForm actionForm = (PropertyForm) form;
		String id = request.getParameter("id");
		Prop property = getPropertyService().getProperty(new Long(id));
		
		property.setProName(actionForm.getProName());
		property.setProValue(actionForm.getProValue());
		property.setProComment(actionForm.getProComment());
		
		//保存修改日期
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String time = df.format(date);
		property.setEditDate(time);
		
		getPropertyService().updateProperty(property);
		String message = "修改成功";
		request.setAttribute("message", message);
		return mapping.findForward("message");
	}
	
	/**
	 * 删除
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
		String id = request.getParameter("id");
		//判断属性id是否存与机构关联，即organ_property中有无pro_id的记录，如果有就不让删除
		List list = getOrganPropertyService().getList(new Long(id));
		if(list.size()>0){
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter printWriter = response.getWriter(); 
			String message = "此属性与机构有关联,禁止删除";
			printWriter.write("<script type=\"text/javascript\">alert(\""+ message + "\");</script>"); 		
			printWriter.write("<script type=\"text/javascript\">window.location.href='propertyAction.do?method=list';</script>"); 		
			response.flushBuffer();
			return null;
		}else{
			getPropertyService().deleteProperty(new Long(id));
			return mapping.findForward("toList");
		}
	}
	
	/**
	 * ajax异步处理删除时属性与机构的关联
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String id = request.getParameter("id");
		//判断属性id是否存与机构关联，即organ_property中有无pro_id的记录，如果有就不让删除
		List list = getOrganPropertyService().getList(new Long(id));
		if(list.size()>0){
			logger.info("属性id是与机构关联,禁止删除");
			PrintWriter out = response.getWriter(); 
			out.print("false");
			response.flushBuffer();
			return null;
		}else{
			logger.info("属性id没有与机构关联，可以删除");
			getPropertyService().deleteProperty(new Long(id));
			PrintWriter out = response.getWriter(); 
			out.print("true");
			response.flushBuffer();
			return null;
		}
	}
	
	/**
	 * 批量删除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchdelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String ids = request.getParameter("ids".trim());
		logger.info(ids);
		String[] idss = ids.split(",");
		for (int i = 0; i < idss.length; i++) {
			Long l = new Long(idss[i]);
			logger.info("属性id是"+l.toString());
			List list = getOrganPropertyService().getList(l);
			if(list.size()>0){
				String name = getPropertyService().getProperty(l).getProName();
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter printWriter = response.getWriter(); 
				String message = "属性名称为"+name+"的属性与机构有关联,禁止删除";
				printWriter.write("<script type=\"text/javascript\">alert(\""+ message + "\");</script>"); 		
				printWriter.write("<script type=\"text/javascript\">window.location.href='propertyAction.do?method=list';</script>"); 		
				response.flushBuffer();
				return null;
			}else{
				logger.info("属性id没有与机构关联，可以删除");
				getPropertyService().deleteProperty(l);
			}
		}
		return mapping.findForward("toList");
	}
	
	/**
	 * ajax验证属性名称唯一
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getUniqueProName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String proName = request.getParameter("proName");
		List proNameList = getPropertyService().getProNameList();
		//属性名称不唯一
		if(proNameList.contains(proName)){
			logger.info("属性名称不唯一");
			PrintWriter out = response.getWriter();
			out.print("false");
			out.flush();
			return null;
		}else{
			logger.info("属性名称可用");
			PrintWriter out = response.getWriter();
			out.print("true");
			out.flush();
			return null;
		}
	}
	public PropertyService getPropertyService(){
		return (PropertyService) getBean("propertyService");
	}
	
	public OrganPropertyService getOrganPropertyService(){
		return (OrganPropertyService) getBean("organPropertyService");
	}
}
