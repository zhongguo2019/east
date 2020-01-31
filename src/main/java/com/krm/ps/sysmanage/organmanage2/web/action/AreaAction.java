package com.krm.ps.sysmanage.organmanage2.web.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.organmanage2.vo.Area;
import com.krm.ps.sysmanage.organmanage2.web.form.AreaForm;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.User;


/**
 * @struts.action name="areaForm" path="/areaAction" scope="request"
 *                validate="false" parameter="method"
 * 
 * @struts.action-forward name="list" path="/areamanager/AreaList.jsp"
 * @struts.action-forward name="new" path="/areamanager/AreaForm.jsp"
 * @struts.action-forward name="edit" path="/areamanager/AreaForm.jsp"
 * @struts.action-forward name="sort" path="/common/sortcommon.jsp"
 * @struts.action-forward name="sort_list" path="/areaAction.do?method=listArea"
 */

public class AreaAction extends BaseAction{
	
	/**
	 * 显示当前地区及下级地区列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listArea(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'listArea' method");
		}

		User user = getUser(request);
		AreaService areaService = (AreaService)getBean("areaService");
		UserService userService = (UserService)getBean("userService");
		String rootId = areaService.getAreaCodeByUser(user.getPkid().intValue());
		String areaCode = "";
		if(request.getParameter("areaCode") == null){
			areaCode = areaService.getAreaCodeByUser(user.getPkid().intValue());
		}else if(request.getAttribute("areaCode") != null){
				areaCode = (String)request.getAttribute("areaCode");
		}else{
			areaCode = request.getParameter("areaCode");
		}
		Area currArea = areaService.getAreaByareaCode(areaCode);
		List list = areaService.getSubArea(areaCode);
		list.add(0,currArea);
		List areaList = new ArrayList();
		for(Iterator itr = list.iterator();itr.hasNext();){
			Area area = (Area)itr.next();
			String beginDate = area.getBeginDate();
			String endDate = area.getEndDate();
			area.setBeginDate(beginDate.substring(0,4)+"-"+beginDate.substring(4,6)+"-"+beginDate.substring(6,8));
			area.setEndDate(endDate.substring(0,4)+"-"+endDate.substring(4,6)+"-"+endDate.substring(6,8));
			User areaAdmin = userService.getUser(area.getCode());
			if(areaAdmin != null){
				area.setAreaAdminId(new Integer(areaAdmin.getPkid().intValue()));
			}
			areaList.add(area);
		}
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("rootId", rootId);
		request.setAttribute("areaList", areaList);
		return mapping.findForward("list");
	}
	
	/**
	 * 新建地区
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newArea(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'newArea' method");
		}
		String areaCode = request.getParameter("areaCode");
		AreaForm areaForm = new AreaForm();
		AreaService areaService = (AreaService)getBean("areaService");
		Area area = areaService.getAreaByareaCode(areaCode);
		areaForm.setSuperAreaCode(area.getCode());
		setFormByArea(areaForm,area);
		updateFormBean(mapping,request,areaForm);
		return mapping.findForward("new");
	}
	
	/**
	 * 编辑地区
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editArea(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'editArea' method");
		}
		String areaCode = request.getParameter("areaCode");
		String superAreaCode = request.getParameter("superAreaCode");
		AreaService areaService  = (AreaService)getBean("areaService");
		Area area = areaService.getAreaByareaCode(areaCode);
		AreaForm areaForm = new AreaForm();
		areaForm.setAreaId(area.getPkid());
		String code = area.getCode().replaceAll("(00)+$","");
		areaForm.setAreaCode(code.substring(code.length()-2,code.length()));
		areaForm.setSuperAreaCode(superAreaCode);
		areaForm.setFullName(area.getFullName());
		areaForm.setShortName(area.getShortName());
		setFormByArea(areaForm,area);
		updateFormBean(mapping,request,areaForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 删除地区
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delArea(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'delArea' method");
		}
		String isDelete = "no";
		String areaCode = request.getParameter("areaCode");
		AreaService areaService = (AreaService)getBean("areaService");
		List organList = areaService.getOrgansByArea(areaCode);
		if(organList.size() == 0){
			List areaList = areaService.getAllSubAreas(areaCode);
			if(areaList.size() == 0){
				areaService.deleteArea(areaCode);
				isDelete = "yes";
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(isDelete);
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 保存地区信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveArea(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'saveArea' method");
		}
		AreaForm areaForm = (AreaForm)form;
		Area area = new Area();
		int status = 1;
		int isDefult = 1;
		area.setPkid(areaForm.getAreaId());
		area.setCode(areaForm.getAreaCode());
		area.setBeginDate(areaForm.getBeginDate().replaceAll("-", ""));
		area.setEndDate(areaForm.getEndDate().replaceAll("-", ""));
		area.setFullName(areaForm.getFullName());
		area.setShortName(areaForm.getShortName());
		String oldAreaCode = areaForm.getOldCode();
		AreaService areaService = (AreaService)getBean("areaService");
		Area superArea = areaService.getAreaByareaCode(areaForm.getSuperAreaCode());
		area.setSuperId(superArea.getPkid());
		area.setStatus(status);
		area.setIsDefult(isDefult);
		areaService.saveArea(oldAreaCode, area);
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "areaAction.do?method=listArea&areaCode="+"01121");
		return null;
	}
	
	public ActionForward createUser(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		return null;
	}
	
	public ActionForward sortArea(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'sortArea' method");
		}
		setToken(request);
		String areaCode = request.getParameter("areaCode");
		AreaService areaService = (AreaService)getBean("areaService");
		List subAreaList = areaService.getSubArea(areaCode);
		List list = new ArrayList();
		for(Iterator itr = subAreaList.iterator();itr.hasNext();){
			Area area = (Area)itr.next();
			Object [] obj = new Object[2];
			obj[0] = new Long(area.getPkid());
			obj[1] = area.getFullName();
			list.add(obj);
		}
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("fileTitle","系统管理－》地区排序");
		request.setAttribute("sortList",list);
		request.setAttribute("serviceName", "areaService");
		request.setAttribute("sortTitle", "地区排序");
		ActionForward forward = mapping.findForward("sort_list");
		String path = forward.getPath();
		request.setAttribute("forwardURL", path);
		return mapping.findForward("sort");
	}
	
	public ActionForward hasSubArea(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'hasSubArea' method");
		}
		String subArea = "yes";
		String areaCode = request.getParameter("areaCode");
		AreaService areaService = (AreaService)getBean("areaService");
		List subAreaList = areaService.getSubArea(areaCode);
		if(subAreaList.size() == 0){
			subArea = "no";
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(subArea);
		out.flush();
		out.close();
		return null;
	}
	/**
	 * 检查地区编码是否重复
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validateAreaCode(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'validateAreaCode' method");
		}
		String flag = "ok";
		String areaCode = request.getParameter("areaCode");
		String superAreaCode = request.getParameter("superAreaCode");
		areaCode = buildAreaCode(areaCode, superAreaCode);
		AreaService areaService = (AreaService)getBean("areaService");
		Area area = areaService.getAreaByareaCode(areaCode);
		if(area != null){flag = "no";}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(flag);
		out.flush();
		out.close();
		return null;
	}
	
	private String buildAreaCode(String areaCode, String superAreaCode){
		superAreaCode = superAreaCode.replaceAll("(00)+$","");
		areaCode = superAreaCode + areaCode;
		while(areaCode.length() < 10){
			areaCode += "00";
		}
		return areaCode;
	}
	
	
	
	
	private void setFormByArea(AreaForm areaForm, Area area){
		String beginDate = area.getBeginDate();
		if(beginDate != null || !beginDate.equals("")){
			beginDate = beginDate.substring(0,4)+"-"+beginDate.substring(4,6)+"-"+beginDate.substring(6,8);
		}else{
			beginDate = null;
			String year = beginDate.substring(0,4);
			year = new Integer(Integer.parseInt(year)-5).toString();
			beginDate = year + beginDate.substring(4);
		}
		String endDate = area.getEndDate();
		if(endDate != null || !endDate.equals("")){
			endDate = endDate.substring(0,4)+"-"+endDate.substring(4,6)+"-"+endDate.substring(6,8);
		}else{
			beginDate = null;
			String year = beginDate.substring(0,4);
			year = new Integer(Integer.parseInt(year)+5).toString();
			beginDate = year + beginDate.substring(4);
		}
		areaForm.setBeginDate(beginDate);
		areaForm.setEndDate(endDate);
	}
}
