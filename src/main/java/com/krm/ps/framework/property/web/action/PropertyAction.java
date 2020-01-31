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
	 * ��ʾ�����б�
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
	 * ���ҳ�� 
	 */
	public ActionForward addUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("saveUI");
	}
	
	/** ��� */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//��ȡactionform
		PropertyForm actionForm = (PropertyForm) form;
		//�½�property
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

		/*//�ж����������Ƿ�Ψһ
		List proNameList = getPropertyService().getProNameList();
		if(proNameList.contains(proName)){
			log.info("�������Ʋ�Ψһ");
			StringBuffer sb = new StringBuffer();
			request.setAttribute(ALERT_MESSAGE, "�������Ʋ�Ψһ");
			return mapping.findForward("saveUI");
		}else{
			getPropertyService().addProperty(property);
			String message = "����ɹ�";
			request.setAttribute("message", message);
			return mapping.findForward("message");
		}
		*/
		getPropertyService().addProperty(property);
		String message = "����ɹ�";
		request.setAttribute("message", message);
		return mapping.findForward("message");
	}
	
	/**
	 * �޸�ҳ��
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
		//��ȡactionform
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
		//��ȡactionform
		PropertyForm actionForm = (PropertyForm) form;
		String id = request.getParameter("id");
		Prop property = getPropertyService().getProperty(new Long(id));
		
		property.setProName(actionForm.getProName());
		property.setProValue(actionForm.getProValue());
		property.setProComment(actionForm.getProComment());
		
		//�����޸�����
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String time = df.format(date);
		property.setEditDate(time);
		
		getPropertyService().updateProperty(property);
		String message = "�޸ĳɹ�";
		request.setAttribute("message", message);
		return mapping.findForward("message");
	}
	
	/**
	 * ɾ��
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
		//�ж�����id�Ƿ���������������organ_property������pro_id�ļ�¼������оͲ���ɾ��
		List list = getOrganPropertyService().getList(new Long(id));
		if(list.size()>0){
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter printWriter = response.getWriter(); 
			String message = "������������й���,��ֹɾ��";
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
	 * ajax�첽����ɾ��ʱ����������Ĺ���
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
		//�ж�����id�Ƿ���������������organ_property������pro_id�ļ�¼������оͲ���ɾ��
		List list = getOrganPropertyService().getList(new Long(id));
		if(list.size()>0){
			logger.info("����id�����������,��ֹɾ��");
			PrintWriter out = response.getWriter(); 
			out.print("false");
			response.flushBuffer();
			return null;
		}else{
			logger.info("����idû�����������������ɾ��");
			getPropertyService().deleteProperty(new Long(id));
			PrintWriter out = response.getWriter(); 
			out.print("true");
			response.flushBuffer();
			return null;
		}
	}
	
	/**
	 * ����ɾ��
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
			logger.info("����id��"+l.toString());
			List list = getOrganPropertyService().getList(l);
			if(list.size()>0){
				String name = getPropertyService().getProperty(l).getProName();
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter printWriter = response.getWriter(); 
				String message = "��������Ϊ"+name+"������������й���,��ֹɾ��";
				printWriter.write("<script type=\"text/javascript\">alert(\""+ message + "\");</script>"); 		
				printWriter.write("<script type=\"text/javascript\">window.location.href='propertyAction.do?method=list';</script>"); 		
				response.flushBuffer();
				return null;
			}else{
				logger.info("����idû�����������������ɾ��");
				getPropertyService().deleteProperty(l);
			}
		}
		return mapping.findForward("toList");
	}
	
	/**
	 * ajax��֤��������Ψһ
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
		//�������Ʋ�Ψһ
		if(proNameList.contains(proName)){
			logger.info("�������Ʋ�Ψһ");
			PrintWriter out = response.getWriter();
			out.print("false");
			out.flush();
			return null;
		}else{
			logger.info("�������ƿ���");
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
