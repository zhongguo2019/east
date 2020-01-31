package com.krm.ps.framework.common.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.services.ReportTreeService;
import com.krm.ps.framework.common.services.impl.ReportTreeServiceImpl;
import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.model.OrganSystem;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.organmanage2.services.OrganTreeManager;
import com.krm.ps.sysmanage.organmanage2.util.JsonUtil;
import com.krm.ps.sysmanage.usermanage.bo.Menu;
import com.krm.ps.sysmanage.usermanage.services.impl.MenuManager;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.SysConfig;

/**
 * @struts.action path="/menuTree" scope="request" validate="false" parameter="method"
 * 
 * @struts.action-forward name="success" path="/plat/report/menutree.jsp"
 * @struts.action-forward name="tree" path="/common/tree.jsp"
 */
public class MenuTreeAction extends BaseAction {

	MenuManager mm = (MenuManager) getBean("menuManager");
	public ActionForward repMenuTreeAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = getUser(request);
		Long pkid = user.getPkid();
		long pkid_ = pkid.longValue();
		List menuList = mm.getMenuByUserId(pkid_);
		String treeXml = "";
		ReportTreeService rsi = new ReportTreeServiceImpl();
		treeXml = rsi.buildMenuTreeXML(menuList);
//		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(
//				new FileOutputStream("d:/menu.txt")));
//		bw1.write(treeXml, 0, treeXml.length());
//		bw1.flush();
//		bw1.close();
		request.setAttribute("treeXml", treeXml);
		return mapping.findForward("tree");
	}
	
	public ActionForward getUrlById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Menu menu = mm.getMenuByMenuId(id);
		String url = menu.getResource();
		PrintWriter out = response.getWriter(); 
		out.print(url);
		response.flushBuffer();
		return null;
	}
	
	/**
	 * 获取菜单树
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMenuTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*System.out.println("dksfjsfs");
		MenuManager mm = (MenuManager) getBean("menuManager");
		User user = getUser(request);
		Long pkid = user.getPkid();
		long pkid_ = pkid.longValue();
		List menuList = mm.getMenuByUserId(pkid_);
		System.out.println(menuList.size());
		return null;*/
		Map uDefineTreeMap = null;
		try {
			MenuManager mm = (MenuManager) getBean("menuManager");
			User user = getUser(request);
			Long pkid = user.getPkid();
			long pkid_ = pkid.longValue();
			List menuList = mm.getMenuByUserId(pkid_);

			//需要汇总,wsx 2006-10-20
			if ("shandong".equals(SysConfig.PROVINCE)) {
				// wsx 8-16,只适用于山东，山东情况：省联社、地市级机构才有汇总，县联社不需要汇总
				OrganService os = getOrganService();
				OrganInfo organ = os.getOrganByCode(user.getOrganId());
				String orgCode = organ.getCode();
				if (!orgCode.endsWith("0000")) {// 县联社
					Iterator i = menuList.iterator();
					while (i.hasNext()) {
						Menu m = (Menu) i.next();
						if ("300000".equals(m.getId())) {// 数据处理
							List sml = m.getSubMenus();
							Iterator j = sml.iterator();
							while (j.hasNext()) {
								Menu sm = (Menu) j.next();
								if ("300001".equals(sm.getId())) {// 数据汇总
									sml.remove(sm);
									break;
								}
							}
							break;
						}
					}
				}
			}

			request.setAttribute("menus", menuList);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		try {
			boolean isTreeNode = false;
			String date = (String)request.getSession().getAttribute("logindate");
			date = date.replaceAll("-","");
			User user = getUser(request);
			OrganInfo organInfo = (OrganInfo)request.getSession().getAttribute("curorgan");
			log.debug("当前的在Session中的机构为[" + organInfo + "]");
			int userId = getUser(request).getPkid().intValue();
			OrganTreeManager organTreeManager = (OrganTreeManager)this.getBean("organTreeManager");
			OrganService2 organService2 = (OrganService2)getBean("organService2");
			List organSystemList = organTreeManager.listOrganSystems(userId,date);
			if(organSystemList.size() > 0){
				//判断当前登陆用户机构是否为机构树上节点
				for(Iterator itr = organSystemList.iterator(); itr.hasNext();) {
					OrganSystem organSystem = (OrganSystem)itr.next();
					isTreeNode = organService2.isTreeNode(organSystem.getId(), organInfo.getPkid());
					if(isTreeNode){
						organSystem.setIsUse("yes");
					}else{
						organSystem.setIsUse("no");
					}
				}
				List organSystemJson = JsonUtil.getOrganSystemJson(organSystemList);
				JSONArray json = JSONArray.fromObject(organSystemJson);
				//设置第一棵有效树Id为默认机构树Id
				//OrganSystem defaultOrganSystem = (OrganSystem)organSystemList.get(0);
//				user.setOrganTreeIdxid(defaultOrganSystem.getId().intValue());
//				for(Iterator itr = organSystemList.iterator(); itr.hasNext();) {
//					OrganSystem organSystem = (OrganSystem)itr.next();
//					if(organSystem.getIsUse().equals("yes")){
//						user.setOrganTreeIdxid(organSystem.getId().intValue());
//						break;
//					}
//				}
//				添加纪录上次操作默认机构树Id
				ServletContext servletContext = servlet.getServletContext();
				if(servletContext.getAttribute("userDefineTree") == null) {
					uDefineTreeMap = new HashMap();
					for(Iterator itr = organSystemList.iterator(); itr.hasNext();) {
						OrganSystem organSystem = (OrganSystem)itr.next();
						if(organSystem.getIsUse().equals("yes")){
							user.setOrganTreeIdxid(organSystem.getId().intValue());
							break;
						}
					}
					uDefineTreeMap.put(user.getPkid(), new Integer(user.getOrganTreeIdxid()));
					request.setAttribute("defineTreeId", uDefineTreeMap.get(user.getPkid()));
					
				}else{
					uDefineTreeMap = (Map)servletContext.getAttribute("userDefineTree");
					if(uDefineTreeMap.get(user.getPkid()) == null) {
						for(Iterator itr = organSystemList.iterator(); itr.hasNext();) {
							OrganSystem organSystem = (OrganSystem)itr.next();
							if(organSystem.getIsUse().equals("yes")){
								user.setOrganTreeIdxid(organSystem.getId().intValue());
								break;
							}
						}
						uDefineTreeMap.put(user.getPkid(), new Integer(user.getOrganTreeIdxid()));
						request.setAttribute("defineTreeId", uDefineTreeMap.get(user.getPkid()));
					}else{
//						System.out.println(uDefineTreeMap.get(user.getPkid()).getClass().getName());
						user.setOrganTreeIdxid(((Integer)uDefineTreeMap.get(user.getPkid())).intValue());
						request.setAttribute("defineTreeId", uDefineTreeMap.get(user.getPkid()));
					}
				}
				servletContext.setAttribute("userDefineTree", uDefineTreeMap);
				
				//////////////////////////////////////////////////////////////
//				System.out.println(uDefineTreeMap.size());
				
				/**
				Set key = uDefineTreeMap.keySet();
				for(Iterator itr = key.iterator(); itr.hasNext();) {
					Integer treeId = (Integer)uDefineTreeMap.get(itr.next());
					System.out.println(itr.next()+":"+"treeId=["+treeId+"]");
				}
				**/
				//////////////////////////////////////////////////////////////
				request.setAttribute("json", json);
			}else{
				request.setAttribute("json", "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(request.getParameter("top")!=null){
			try {
				request.getRequestDispatcher("menubar.jsp").include(request,response);
				//response.sendRedirect(request.getContextPath()+"/menubar.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
			return null;
		}
		return mapping.findForward("success");
	}
}
