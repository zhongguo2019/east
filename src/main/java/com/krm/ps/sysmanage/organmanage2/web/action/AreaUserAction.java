package com.krm.ps.sysmanage.organmanage2.web.action;

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
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.organmanage2.vo.Area;
import com.krm.ps.sysmanage.organmanage2.web.form.AreaUserForm;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.ConvertUtil;

/**
 * @struts.action name="areaUserForm" path="/areaUserAction" scope="request"
 *                validate="false" parameter="method"
 * 
 * @struts.action-forward name="list" path="/areamanager/AreaList.jsp"
 * @struts.action-forward name="new" path="/areamanager/AreaUserForm.jsp"
 * @struts.action-forward name="edit" path="/areamanager/AreaUserForm.jsp"
 * @struts.action-forward name="tree" path="/common/tree.jsp"
 */
public class AreaUserAction extends BaseAction{
	
	public ActionForward createUser(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("Entering 'createUser' method");
		}
		String areaCode = request.getParameter("areaCode");
		String superAreaCode = request.getParameter("superAreaCode");
		AreaUserForm areaUserForm = new AreaUserForm();
		areaUserForm.setLoginName(areaCode);
		areaUserForm.setPassword("888888");
		updateFormBean(mapping, request, areaUserForm);
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("superAreaCode", superAreaCode);
		return mapping.findForward("new");
	}
	
	public ActionForward editUser(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("Entering 'editUser' method");
		}
		String areaCode = request.getParameter("areaCode");
		String superAreaCode = request.getParameter("superAreaCode");
		String userId = request.getParameter("userId");
		UserService userService = (UserService)getBean("userService");
		User user = userService.getUser(new Long(userId));
		AreaUserForm areaUserForm = new AreaUserForm();
		areaUserForm.setLoginName(user.getLogonName());
		areaUserForm.setName(user.getName());
		areaUserForm.setOrgan(user.getOrganId());
		areaUserForm.setPassword(user.getPassword());
		areaUserForm.setPkid(user.getPkid().intValue());
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("superAreaCode", superAreaCode);
		request.setAttribute("organCode", user.getOrganId());
		updateFormBean(mapping, request, areaUserForm);
		return mapping.findForward("edit");
	}
	
	public ActionForward saveUser(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("Entering 'saveUser' method");
		}
		String superAreaCode = request.getParameter("superAreaCode");
		UserService userService = (UserService)getBean("userService");
		AreaUserForm areaUserForm = (AreaUserForm)form;
		User user = new User();
		
		user.setName(areaUserForm.getName());
		user.setLogonName(areaUserForm.getLoginName());
		user.setOrganId(areaUserForm.getOrgan());
		user.setCreator(getUser(request).getPkid());
		user.setPassword(areaUserForm.getPassword());
		user.setRoleType(new Long(2));
		user.setCreatetime(null);
		user.setStatus(new Integer(1));
		user.setIsAdmin(new Integer(0));
		if(areaUserForm.getPkid() == 0){
			userService.saveUser(user);
		}else{
			user.setPkid(new Long(areaUserForm.getPkid()));
			userService.updateUser(user);
		}
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "areaAction.do?method=listArea&areaCode="+superAreaCode);
		return null;
	}
	
	public ActionForward organTree(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("Entering 'organTree' method");
		}
		String areaCode = request.getParameter("areaCode");
		AreaService areaService = (AreaService)getBean("areaService");
		Area area = areaService.getAreaByareaCode(areaCode);
		List organList = areaService.getOrgansByArea(areaCode);
		String[] cnames = new String[] { "code", "full_name", "pkid","organOrder" };
		OrganInfo root = new OrganInfo();
		root.setPkid(new Long(10000));
		root.setFull_name(area.getFullName());
		root.setCode("10000");
		root.setOrganOrder(new Integer(-1));
		List list = new ArrayList();
		for(Iterator itr = organList.iterator();itr.hasNext();){
			OrganInfo organ = (OrganInfo)itr.next();
			organ.setOrganOrder(new Integer(10000));
			list.add(organ);
		}
		list.add(0,root);
		String xml = ConvertUtil.convertListToAdoXml(list, cnames);
		request.setAttribute("treeXml", xml);
		return mapping.findForward("tree");
	}
}
