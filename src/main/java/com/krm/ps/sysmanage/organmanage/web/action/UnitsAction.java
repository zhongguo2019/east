package com.krm.ps.sysmanage.organmanage.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.services.UnitService;
import com.krm.ps.sysmanage.organmanage.web.form.UnitForm;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.vo.Units;


/**
*
* @struts.action name="unitForm" path="/unitAction" scope="request" 
*  				 validate="false" parameter="method" input="addUnit"
*  
* @struts.action-forward name="list" path="/organmanage/unitList.jsp"
* @struts.action-forward name="add" path="/organmanage/addUnit.jsp"
* @struts.action-forward name="editUnit" path="/organmanage/addUnit.jsp"
* @struts.action-forward name="sort" path="/common/sortcommon.jsp"
* @struts.action-forward name="sort_list" path="/unitAction.do?method=list"
*/
public class UnitsAction extends BaseAction{
	
	//机构信息列表
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}

		if (!validateUser(mapping, request)) {
			
				return mapping.findForward("loginpage");
			}
		DictionaryService ds = this.getDictionaryService();
		
		request.setAttribute("unitList",ds.getUnitcode());
		return mapping.findForward("list");
	}
	
	
	/*
	 * 添加日期:2006年9月12日
	 * 修改人:赵鹏程
	 */
	public ActionForward entryUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'entryUnit' method");
		}
		return mapping.findForward("add");
	}
	//添加数量单位
	public ActionForward addUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{			
		if (log.isDebugEnabled()) {
			log.info("Entering 'addUnit' method");
		}
		UnitForm uf = (UnitForm) form;
		Units unit = new Units();
		UnitService unitService = this.getUnitService();
		unit.setCode(uf.getCode());
		unit.setName(uf.getName());
		unit.setModulus(uf.getModulus());		
		unit.setPkid(uf.getPkid());
		if(uf.getPkid() == null){						
			unitService.saveUnit(unit);			
		}else{			
			unitService.updateUnit(unit);			
		}
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "unitAction.do?method=list");
		return null;
	}
	//排序数量单位
	public ActionForward taxisUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		if (log.isDebugEnabled()) {
			log.info("Entering 'taxisUnit' method");
		}
		setToken(request);
		UnitService unitService = this.getUnitService();
		List units = unitService.queryUnit();
		ArrayList al = new ArrayList();
		Iterator it = units.iterator();
		while(it.hasNext()){
			Object [] o = new Object [2];
			Units u = (Units)it.next();
			o[0] = u.getPkid();
			o[1] = u.getName();
			al.add(o);
		}
		request.setAttribute("fileTitle","ϵͳ���?������λ����");
		request.setAttribute("sortList",al);
		request.setAttribute("serviceName", "UnitSortService");
		request.setAttribute("sortTitle", "����λ����");
		ActionForward forward = mapping.findForward("sort_list");
		String path = forward.getPath();
		request.setAttribute("forwardURL", path);
		return mapping.findForward("sort");
	}
	//编辑数量单位
	public ActionForward editUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		if (log.isDebugEnabled()) {
			log.info("Entering 'editUnit' method");
		}
		String strPkid = request.getParameter("pkid");
		Long pkid = Long.valueOf(strPkid);
		UnitService unitService = this.getUnitService();
		Units unit = unitService.queryUnitById(pkid);
		UnitForm unitForm = new UnitForm();
		unitForm.setPkid(unit.getPkid());
		unitForm.setCode(unit.getCode());
		unitForm.setName(unit.getName());
		unitForm.setModulus(unit.getModulus());
		this.updateFormBean(mapping,request,unitForm);
		return mapping.findForward("editUnit");
	}
	//删除数量单位
	public ActionForward deleteUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'deleteUnit' method");
		}
		String strPkid = request.getParameter("pkid");
		Long pkid = Long.valueOf(strPkid);
		UnitService unitService = this.getUnitService();
		unitService.delUnit(pkid);
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "unitAction.do?method=list");
		return null;
	}
	public UnitService getUnitService(){
		return (UnitService)this.getBean("unitService");
	}
}
