package com.krm.ps.sysmanage.usermanage.web.action;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.usermanage.bo.Menu;
import com.krm.ps.sysmanage.usermanage.services.UserRoleService;
import com.krm.ps.sysmanage.usermanage.vo.Role;
import com.krm.ps.sysmanage.usermanage.web.form.roleForm;
import com.krm.ps.util.ConvertUtil;
/*
 *  添加日期:2006年9月13日
 *  添加人:赵鹏程
 */

/**
*
* @struts.action name="roleForm" path="/userRoleAction" scope="request" 
*                validate="false" parameter="method"
*  
* @struts.action-forward name="list" path="/usermanage/userrole.jsp"
* @struts.action-forward name ="roleForm" path="/usermanage/roleForm.jsp"
*/
public class UserRoleAction extends BaseAction{
	//查询用户权限
	public ActionForward queryUserRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'queryUserRole' method");
		}
		try{
			UserRoleService userRoleService = this.getUserRoleService();
			//userRoleService.getUserRole();
			List role = userRoleService.getUserRole();
			request.setAttribute("role",role);
			return mapping.findForward("list");
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}
	//进入添加/维护页面
	public ActionForward entryUserRole1(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'entryUserRole1' method");
		}
		roleForm rf = (roleForm)form;
		UserRoleService userRoleService = this.getUserRoleService();
		//userRoleService.getMenu();
		ArrayList menu = userRoleService.getMenu();
		String rType = userRoleService.getRoleType();		
		String flag = request.getParameter("flag");
		//查询出系统菜单
//		List systemMenus = getSystemMenuService().getAllSystemMenu();
//		request.setAttribute("sysMenus",systemMenus);
		if(flag.equals("1")){
			request.setAttribute("menu",menu);
			request.setAttribute("flag",flag);
			request.setAttribute("roleType",rType);
			return mapping.findForward("roleForm");
		}
		if(flag.equals("2")){
			String strRoleType = request.getParameter("roleType");
			String strPkid = request.getParameter("pkid");
			Long roleType = Long.valueOf(strRoleType);
			Long pkid = Long.valueOf(strPkid);
			//userRoleService.getRoleInfo(pkid);
			Role role = userRoleService.getRoleInfo(pkid);
			//userRoleService.getRoleMenu(roleType);
			List menuList = userRoleService.getRoleMenu(roleType);
			ConvertUtil.copyProperties(rf,role);		
			this.updateFormBean(mapping,request,rf);
//			request.setAttribute("role",role);
			for(int i =0;i<menuList.size();i++) {
				Menu m1 =  (Menu)menuList.get(i);
				for(int j=0;j<menu.size();j++) {
					Menu m2 = (Menu)menu.get(j);
					if(m1.getId().equals(m2.getId())) {
						menu.remove(j);
						break;
					}
				}
			}
			request.setAttribute("menuList",menuList);
			request.setAttribute("menu",menu);
			request.setAttribute("flag",flag);
			request.setAttribute("roleType",rType);
			return mapping.findForward("roleForm");
		}
		return null;
	}
	//添加用户权限
	public ActionForward addUserRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'addUserRole' method");
		}
		roleForm rf = (roleForm)form;
		UserRoleService userRoleService = this.getUserRoleService();
//		String pkid = request.getParameter("pkid");
//		String menu = request.getParameter("menuId");
		String menu = rf.getMenuId();
		String [] menuId = menu.split(",");
		String des = " ";
		Role role = new Role();
		role.setName(rf.getName());
		role.setRoleType(rf.getRoleType());
		role.setRoleLevel(rf.getRoleLevel());//添加用户级别
		if(rf.getDescription().equals("")){
			role.setDescription(des);
		}else{
			role.setDescription(rf.getDescription());
		}
		if(rf.getPkid()==null||rf.getPkid().equals("")){
			//userRoleService.addRole(role,menuId);
			if(!"".equals(menuId[0])) {
				userRoleService.addRole(role,menuId);
			}
			String conPath = request.getContextPath() + File.separatorChar;
			response.sendRedirect(conPath + "userRoleAction.do?method=queryUserRole");
		}else{
			String systemId = request.getParameter("systemId");
			role.setPkid(rf.getPkid());
			//userRoleService.updateRole(role);
			userRoleService.updateRole(role);
			userRoleService.delRoleRight(Long.parseLong(systemId),role.getRoleType());
			if(!"".equals(menuId[0])) {
				userRoleService.addRoleRight(role,menuId);
			}
			String conPath = request.getContextPath() + File.separatorChar;
			response.sendRedirect(conPath + "userRoleAction.do?method=queryUserRole");
		}
		return null;
	}
	//删除用户权限
	public ActionForward delUserRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'delUserRole' method");
		}
		UserRoleService userRoleService = this.getUserRoleService();
		String strPkid = request.getParameter("pkid");
		String strRoleType = request.getParameter("roleType");
		Long pkid = Long.valueOf(strPkid);
		Long roleType = Long.valueOf(strRoleType);
		//userRoleService.delUserRole(pkid,roleType);
		userRoleService.delUserRole(pkid,roleType);
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "userRoleAction.do?method=queryUserRole");
		return null;
	}
	

	//新 进入添加/维护页面,查询不通系统的权限列表
	public ActionForward entryUserRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'entryUserRole' method");
		}
		//String id = request.getParameter("systemId");
		String id = (String) request.getSession().getAttribute("system_id");
		roleForm rf = (roleForm)form;
		UserRoleService userRoleService = this.getUserRoleService();
		//userRoleService.getMenu();
		ArrayList menu = userRoleService.getMenuBySystem(Long.parseLong(id));
		String rType = userRoleService.getRoleType();		
		String flag = request.getParameter("flag");
		//查询出系统菜单
//		List systemMenus = getSystemMenuService().getAllSystemMenu();
//		request.setAttribute("sysMenus",systemMenus);
		request.setAttribute("selectedSys",id);
		if(flag.equals("1")){
			request.setAttribute("menu",menu);
			request.setAttribute("flag",flag);
			request.setAttribute("roleType",rType);
			return mapping.findForward("roleForm");
		}
		if(flag.equals("2")){
			String strRoleType = request.getParameter("roleType");
			String strPkid = request.getParameter("pkid");
			Long roleType = Long.valueOf(strRoleType);
			Long pkid = Long.valueOf(strPkid);
			//userRoleService.getRoleInfo(pkid);
			Role role = userRoleService.getRoleInfo(pkid);
			//userRoleService.getRoleMenu(roleType);
			List menuList = userRoleService.getRoleMenuBySystem(Long.parseLong(id),roleType);
			ConvertUtil.copyProperties(rf,role);		
			this.updateFormBean(mapping,request,rf);
//			request.setAttribute("role",role);
			List mList = new ArrayList();
			for(int i =0;i<menuList.size();i++) {
				Menu m1 =  (Menu)menuList.get(i);
				for(int j=0;j<menu.size();j++) {
					Menu m2 = (Menu)menu.get(j);
					if(m1.getId().equals(m2.getId())) {
						menu.remove(j);
						break;
					}
				}
			}
			request.setAttribute("menuList",menuList);
			request.setAttribute("menu",menu);
			request.setAttribute("flag",flag);
			request.setAttribute("roleType",rType);
			return mapping.findForward("roleForm");
		}
		return null;
	}
	
}
