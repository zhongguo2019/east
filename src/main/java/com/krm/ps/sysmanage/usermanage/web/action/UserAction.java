package com.krm.ps.sysmanage.usermanage.web.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.repfile.service.RepFileService;
import com.krm.ps.model.vo.CodeRepJhgz;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.usermanage.bo.LoginUserInfo;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.services.UserRoleService;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.usermanage.vo.UserDetail;
import com.krm.ps.sysmanage.usermanage.web.form.UserForm;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.Util;

public class UserAction extends BaseAction {
	private static String USERID = "userid";
	private static String USERROLES = "roles";
	private static String USER_LIST = "userList";

	private int savepro = 2;

	private Long getUserId(HttpServletRequest request) {
		if (request.getParameter(USERID) != null) {
			return new Long(request.getParameter(USERID));
		}
		return null;
	}

	public void setToken(HttpServletRequest request) {
		saveToken(request);
	}

	public boolean tokenValidatePass(HttpServletRequest request) {
		if (!isTokenValid(request)) {
			saveToken(request);
			return false;
		}
		resetToken(request);
		return true;
	}

	public ActionForward toEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'toEdit' method");
		}
		setToken(request);
		UserForm userForm = (UserForm) form;
		AreaService areaService = (AreaService) getBean("areaService");
		UserRoleService urs = (UserRoleService) getBean("userRoleService");
		LoginUserInfo loginUser = (LoginUserInfo) request.getSession().getAttribute("loginUser");
		String userId = loginUser.getUserId();

		String roleLevel = urs.getUserLevel(Long.parseLong(userId));// 获取用户角色级别

		String areaCode = "";
		String fuzzy = request.getParameter("fuzzy");
		if (request.getParameter("areaCode") != null)
			areaCode = request.getParameter("areaCode");
		else if (request.getAttribute("areaCode") != null)
			areaCode = (String) request.getAttribute("areaCode");
		else {
			areaCode = userForm.getAreaCode();
		}
		if ("yes".equals(fuzzy)) {
			areaCode = areaService.getAreaCodeByUser(Integer.parseInt(userForm.getPkid().toString()));
		}
		// List organList = areaService.getOrgansByArea(areaCode);
		List organList = areaService.getOrganName(userForm.getOrganId());
		request.setAttribute("organList", organList);

		UserService us = getUserService();


		// request.setAttribute(USERROLES, us.getRoles());
		/*if ("1".equals(roleLevel) || "2".equals(roleLevel)) {
			List sublevel = us.getSubRoleLevel(roleLevel);// 根据用户角色级别查询子角色
			request.setAttribute(USERROLES, sublevel);
		} else {*/
			//List sublevel = us.getSubRoleLevel("1");// 根据用户角色级别查询子角色
			List rolesAll = us.getRolesAll();
			request.setAttribute(USERROLES, rolesAll);
		/*}*/

		if (userForm.getPkid() == null) {
			request.setAttribute("editFlag", "1");
			request.getSession().setAttribute("addReportFlag", "1");
		} else {
			request.getSession().setAttribute("addReportFlag", "0");
		}
		if (this.savepro == 1) {
			request.setAttribute("error", "-1");
			this.savepro = 2;
		}
		request.setAttribute("rp", userForm.getPassword());

		if (userForm.getPkid() != null) {
			Long userid = userForm.getPkid();
			User user = us.getUser(userid);
			Integer status = user.getStatus();
			request.setAttribute("userStatus", String.valueOf(status));
			userForm.setIpAddr(user.getIpAddr());
			request.setAttribute("isAdmin", user.getIsAdmin());
		}
       
		DictionaryService gms = (DictionaryService) getBean("dictionaryService");
		List groupList = gms.getSelectGroups();
		request.setAttribute("groupList", groupList);

		userForm.setAreaCode(areaCode);
		updateFormBean(mapping, request, userForm);

		return mapping.findForward("edit");
	}

	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		try {
			UserService us = getUserService();
			UserForm userForm = (UserForm) form;
			User user = getUser(request);
			AreaService areaService = (AreaService) getBean("areaService");
			UserRoleService urs = (UserRoleService) getBean("userRoleService");
			String rootId = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
			String userName = request.getParameter("username");
			String loginName = request.getParameter("loginname");
			String fuzzy = request.getParameter("fuzzy");
			String areaCode = "";
			String roleLevel = urs.getUserLevel(getUser(request).getPkid());// 获取用户角色级别
	 
			if (request.getParameter("areaCode") != null)
				areaCode = request.getParameter("areaCode");
			else if (userForm.getAreaCode() != null)
				areaCode = userForm.getAreaCode();
			else {
				areaCode = user.getOrganId();//areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
			}
			HttpSession session = request.getSession();
			String dataDate = (String) session.getAttribute("logindate");
			dataDate = dataDate.replace("-", "");
		//	 List organList = areaService.getAllSubOrgans(user.getOrganTreeIdxid(), areaCode, dataDate);
			// List organList = areaService.getOrgansByArea(areaCode);
			List users = new ArrayList();
			if (("yes".equals(fuzzy)) && ((StringUtils.isNotBlank(userName)) || (StringUtils.isNotBlank(loginName)))) {
				this.log.debug("点击模糊查询进行查询");
				// users = us.queryUser("",idCard,tellerId);
			    	users = us.getUsersByOrganLogonNameUserNmae("", loginName, userName);
			 
				  request.setAttribute("fuzzy", fuzzy);
			} else {
			 
					this.log.debug("选择机构进行查询");
					users = us.getUsersByOrgan(areaCode);
			}
			request.setAttribute("username", userName);
			request.setAttribute("loginname", loginName);
			request.setAttribute(USER_LIST, users);
			request.setAttribute("areaCode", areaCode);
			request.setAttribute("rootId", rootId);
			request.setAttribute("roleLevel", roleLevel);

			return mapping.findForward("list");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			System.out.println(e.getMessage());
			return mapping.findForward("error");
		}
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'edit' method");
		}
		UserForm userForm = (UserForm) form;
		User user = new User();
		UserService us = getUserService();
		Long userid = getUserId(request);
		UserDetail ud = new UserDetail();
		if (userid != null) {
			user = us.getUser(userid);
			// ud = us.getUserDetail(userid);//获取用户详细信息
		}
		request.setAttribute("areaCode", request.getParameter("areaCode"));
		ConvertUtil.copyProperties(userForm, user, true);
		/*
		 * if(null!=ud) { userForm.setTellerId(ud.getTellerId());
		 * userForm.setIdCard(ud.getIdCard()); userForm.setPhone(ud.getPhone());
		 * request.getSession().setAttribute("udpkid",ud.getUdPkid()); }
		 */
		// ConvertUtil.copyProperties(userForm, ud, true);
		return toEdit(mapping, userForm, request, response);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'save' method");
		}
		if (!tokenValidatePass(request)) {
			return list(mapping, form, request, response);
		}
		String organId = request.getParameter("OrganId");
		UserService us = getUserService();
		UserForm userForm = (UserForm) form;
		if (organId != null) {
			userForm.setOrganId(organId);
		}
		userForm.setLogonName(userForm.getLogonName().trim());
		User user = new User();
		ConvertUtil.copyProperties(user, userForm, true);
		user.setIsAdmin(new Integer(userForm.getTellerId()));
		user.setCreator(getUser(request).getPkid());
		user.setOrganId(userForm.getOrganId());
		user.setStatus(new Integer(userForm.getGroupType().toString()));
		user.setCreatetime(Util.thisDate());
		int t = us.saveUser(user);
		if (t == 2) {
			this.savepro = 1;
			return toEdit(mapping, form, request, response);
		}
		// 获取userdetail表的pkid
		/*
		 * Long udpkid =
		 * request.getSession().getAttribute("udpkid")==null?0:Long.parseLong(
		 * request.getSession().getAttribute("udpkid").toString());
		 * //判断是增加用户详细信息还是更改用户详细信息 if(userForm.getPkid()!=null&&udpkid!=0) {
		 * //更改用户信息 UserDetail ud = new UserDetail();
		 * ConvertUtil.copyProperties(ud, userForm, true);
		 * ud.setUserId(userForm.getPkid()); ud.setUdPkid(udpkid);
		 * us.saveUserDetail(ud); } else { //保存用户详细信息 Long userId =
		 * us.getNewUserId(); UserDetail ud = new UserDetail();
		 * ConvertUtil.copyProperties(ud, userForm, true); ud.setUserId(userId);
		 * us.saveUserDetail(ud); }
		 */

		Long groupType = userForm.getGroupType();
		Long pkid = null;
		if (userForm.getPkid() != null) {
			pkid = userForm.getPkid();
		} else {
			User u = us.getUser(userForm.getLogonName(), userForm.getPassword());
			pkid = u.getPkid();
		}

		us.operUserContrast(pkid, groupType);
		request.getSession().removeAttribute("addReportFlag");

		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "userAction.do?method=list&areaCode=" + userForm.getAreaCode());
		return null;
	}

	public ActionForward del(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'del' method");
		}
		UserService us = getUserService();
		Long userid = getUserId(request);
		/*
		 * if (userid != null) { us.delUserDetail(userid); }
		 */
		us.removeUser(userid);
		response.sendRedirect(request.getContextPath() + "/userAction.do?method=list");
		return null;
	}

	public ActionForward updatepassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'updatepassword' method");
		}
		this.log.debug(request.getParameter("password"));
		String password = request.getParameter("password");
		UserService us = getUserService();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String isUpdatePassword = (String) session.getAttribute("isupdatepassword");
		this.log.debug("用户密码过期更新标志：isupdatepassword = " + isUpdatePassword);
		Long pkid = user.getPkid();
		us.updatePassword_new(pkid, password);
		request.setAttribute("ok", "1");
		if ("ok".equals(isUpdatePassword)) {
			session.removeAttribute("isupdatepassword");
			this.log.debug("用户 ： " + user.getLogonName() + " 的过期的密码已经更改。");
			return mapping.findForward("pass");
		}
		return mapping.findForward("fmDialog");
	}

	public ActionForward refreshpassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'refreshpassword' method");
		}

		try {
			User user = getUser(request);
			UserService us = getUserService();
			AreaService areaService = (AreaService) getBean("areaService");
		//	String rootId = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
			String areaCode = "";
			if (request.getParameter("areaCode") == null)
				areaCode = user.getOrganId(); // areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
			else {
				areaCode = request.getParameter("areaCode");
			}
			String loginname= request.getParameter("loginname");
			String username= request.getParameter("username");
			
			
			HttpSession session = request.getSession();
			String dataDate = (String) session.getAttribute("logindate");
			dataDate = dataDate.replace("-", "");
			//   List organList = areaService.getAllSubOrgans(user.getOrganTreeIdxid(), areaCode, dataDate);
			//   organList = areaService.getOrgansByArea(areaCode);
			//   organList = us.getAllOrgan(areaCode);
		    List<User> users = us.getUsersByOrgan(areaCode,loginname ,username);
				 
			request.setAttribute("areaCode", areaCode);
			request.setAttribute("loginname1", loginname);
			request.setAttribute("username1", username);
			request.setAttribute(USER_LIST, users);
			return mapping.findForward("userlistref");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace();
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}

	public ActionForward queryUserByName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'queryUserByName' method");
		}
		AreaService areaService = (AreaService) getBean("areaService");
		String rootId = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		String areaCode = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());

		String username = request.getParameter("username");

		UserService us = getUserService();
		User user = us.getUser(username);
		List userList = new ArrayList();
		userList.add(user);

		request.setAttribute("rootId", rootId);
		request.setAttribute("areaCode", areaCode);
		request.setAttribute(USER_LIST, userList);
		if (user == null) {
			request.setAttribute("ALERT_MESSAGE", "用户：" + username + "不存在！");
			return refreshpassword(mapping, form, request, response);
		}
		return mapping.findForward("userlistref");
	}

	public ActionForward refpassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'refpassword' method");
		}
		String pkid = request.getParameter("userid");
		request.setAttribute("pkid", pkid);
		return mapping.findForward("refuser");
	}
	
	public ActionForward refpassword2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'refpassword2' method");
		}
		String pkid = request.getParameter("userid");
		request.setAttribute("pkid", pkid);
		return mapping.findForward("refuser2");
	}

	public ActionForward updatepassword2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'updatepassword2' method");
		}
		String pkid = request.getParameter("pkid");
		String password = request.getParameter("password");
		String ypassword = request.getParameter("ypassword");
		UserService us = getUserService();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		this.log.info("用户修改密码 ：isupdatepassword is the pkid"+pkid);
		String ypwd = user.getPassword();
		if(!ypassword.equals(ypwd)){
			request.setAttribute("mag", "2");
			return mapping.findForward("refuser2");
		}
		if(StringUtils.isNotBlank(pkid)){
			us.updatePassword_new(new Long(pkid), password);
			request.setAttribute("mag", "1");
		}else{
			request.setAttribute("mag", "0");
		}
		
		return mapping.findForward("refuser2");
	}

	
	public ActionForward refupdatepassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'refupdatepassword' method");
		}
		HttpSession session = request.getSession();
		ServletContext sc = session.getServletContext();
		String password = request.getParameter("password");
		String pkid = request.getParameter("pkid");
		this.log.debug("password" + password + "   pkid" + pkid);
		this.log.debug("password" + password + "   pkid" + Long.valueOf(pkid));
		UserService us = getUserService();
		us.updatePassword_new(Long.valueOf(pkid), password);

		String loginsecurity = FuncConfig.getProperty("login.security.isurser", "false");
		if ("true".equals(loginsecurity)) {
			User user = us.getUser(Long.valueOf(pkid));
			Map lockuserMap = (Map) sc.getAttribute("lockuserList");
			if (lockuserMap.containsKey(user.getLogonName()))
				;
			lockuserMap.remove(user.getLogonName());
			this.log.debug("加锁用户 : " + user.getLogonName() + " 重置密码后解锁........");
		}

		return refreshpassword(mapping, form, request, response);
	}

	public ActionForward searchBeforeAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'searchBeforeAdd' method");
		}
		UserService us = getUserService();
		UserForm userForm = (UserForm) form;
		HttpSession session = request.getSession();
		/**
		 * 显示机构树的时候也是从登录机构级别、及下属机构的数据呀
		 */
		AreaService areaService = (AreaService) getBean("areaService");
		UserRoleService urs = (UserRoleService) getBean("userRoleService");
		String idCard = request.getParameter("idCard") == null ? "" : request.getParameter("idCard").trim();
		String tellerId = request.getParameter("tellerId") == null ? "" : request.getParameter("tellerId").trim();
		String organId = "";

		// 获得日期
		String dataDate = (String) session.getAttribute("inputDataDate");
		if (dataDate == null) {
			dataDate = (String) session.getAttribute("logindate");
		}
		if (userForm.getOrganId() != null) {
			organId = userForm.getOrganId();
		} else {
			organId = request.getParameter("organId");
		}

		if ("".equals(organId) || organId == null) {
			organId = getUser(request).getOrganId();
		}
		String beforeadd = request.getParameter("beforeadd");
		String TreeSearch = request.getParameter("TreeSearch");
		// String rootId =
		// areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		String rootId = getUser(request).getOrganId();
		String areaCode = "";
		if (request.getParameter("areaCode") != null)
			areaCode = request.getParameter("areaCode");
		else if (userForm.getAreaCode() != null)
			areaCode = userForm.getAreaCode();
		else {
			areaCode = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		}
		String roleLevel = urs.getUserLevel(getUser(request).getPkid());// 获取用户角色级别
		List userDetailList = new ArrayList();
		List userDetail = new ArrayList();

		if (("yes".equals(beforeadd))) {
			OrganService2 organService2 = (OrganService2) getBean("organService2");

			//
			int idx = getUser(request).getOrganTreeIdxid();

			String topOrgan = organService2.getnodeid(organId, idx);
			request.setAttribute("organId", organId);
			// List ls = organService2.getAllOrganCode(1,topOrgan,dataDate);
			// 获得

			userDetail = us.queryUserDetail(topOrgan, idCard, tellerId);
			/*
			 * if(userDetail.size()!=0) { request.setAttribute("organId",
			 * organId); for (Iterator iter = userDetail.iterator();
			 * iter.hasNext(); ) { UserDetail userdetail =
			 * (UserDetail)iter.next(); userDetailList.add(userdetail); } }
			 */
		}
		request.setAttribute("orgparam", "&date=" + dataDate);
		request.setAttribute("udlist", userDetail);
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("rootId", rootId);
		return mapping.findForward("beforeadd");
	}

	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'toAdd' method");
		}
		setToken(request);
		UserForm userForm = (UserForm) form;
		UserRoleService urs = (UserRoleService) getBean("userRoleService");
		AreaService areaService = (AreaService) getBean("areaService");
		UserService us = getUserService();
		// 获得用户
		String tellerId = request.getParameter("tellerId");
		List userdeallist = us.queryUserDetail("", "", tellerId);
		String name = request.getParameter("name");

		String organId = request.getParameter("organId");
		// String areaCode =
		// areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		String areaCode = request.getParameter("areaCode");
		if (userdeallist.size() > 0) {
			UserDetail userdtai = (UserDetail) userdeallist.get(0);
			name = userdtai.getName();
		}
		userForm.setName(name);
		userForm.setOrganId(organId);
		userForm.setAreaCode(areaCode);
		userForm.setLogonName(tellerId);
		// List organList = areaService.getOrgansByArea(organId);
		List organList = areaService.getOrganName(organId);
		request.setAttribute("organList", organList);

		LoginUserInfo loginUser = (LoginUserInfo) request.getSession().getAttribute("loginUser");
		String userId = loginUser.getUserId();

		String roleLevel = urs.getUserLevel(Long.parseLong(userId));// 获取用户角色级别
	/*	if ("1".equals(roleLevel) || "2".equals(roleLevel)) {
			List sublevel = us.getSubRoleLevel(roleLevel);// 根据用户角色级别查询子角色
			request.setAttribute(USERROLES, sublevel);
		} else {*/
			List sublevel = us.getSubRoleLevel("1");// 根据用户角色级别查询子角色
			request.setAttribute(USERROLES, sublevel);
		/*}*/
		DictionaryService gms = (DictionaryService) getBean("dictionaryService");
		List groupList = gms.getSelectGroups();
		request.setAttribute("groupList", groupList);
		request.setAttribute("editFlag", "1");
		return mapping.findForward("edit");
	}
	
	/**
	 * 导出检核数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		 
		User user = getUser(request);
		String organId = request.getParameter("areaCode");
		String userName = request.getParameter("username");
		String loginName = request.getParameter("loginname");
	    if(StringUtils.isBlank(organId)){
	    	organId = user.getOrganId();
	    }
		 
		int idxid = getUser(request).getOrganTreeIdxid();
		int IsAdmin = getUser(request).getIsAdmin();
		UserService us = getUserService();
		@SuppressWarnings("unchecked")
		List<User> repconfig = us.getUsersByOLUNmae(organId,loginName,userName,IsAdmin,idxid);
				;
		List<String> titles = new ArrayList<String>();
		titles.add("序号");
		titles.add("登录名称");	 
		titles.add("登录账号");	 
		 
		 
		RepFileService rpf = (RepFileService) getBean("repfileservice");
		HSSFWorkbook wb = rpf.getXlsWorkUser(repconfig, titles);
		String fileName = "";
		try {
			String filestr =  "Usre Information" ;
			fileName = new String( filestr.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setHeader("content-disposition", "attachment; filename="
				+ fileName + ".xls");
		response.setHeader("Content-Type",
				"application/vnd.ms-excel; charset=GBK");
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			wb.write(out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
}