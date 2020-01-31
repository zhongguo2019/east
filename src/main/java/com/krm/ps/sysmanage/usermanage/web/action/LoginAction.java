package com.krm.ps.sysmanage.usermanage.web.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import webclient.subsys.util.SystemConfigReader;

import com.krm.common.logger.KRMLogger;
import com.krm.common.logger.KRMLoggerUtil;
import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.usermanage.bo.LoginUserInfo;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.services.LoginSecurityService;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.services.UserSubSysLoginService;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.Role;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.usermanage.vo.UserRole;
import com.krm.ps.sysmanage.usermanage.web.form.LogForm;
import com.krm.ps.util.ActionUtil;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.FuncconfigUtil;
import com.krm.ps.util.SysConfig;
import com.krm.ps.util.DateUtil;
import com.krm.ps.util.FileUtil;

import com.krm.ps.sysmanage.usermanage.web.action.LoginAction;
import com.krm.ps.util.Constant;
import com.krm.ps.util.Util;
import com.krm.ps.util.DateUtils;
import com.krm.ps.util.FileUtils;
import com.krmsoft.mycas.bean.Loginbean;

/**
 * 
 * @struts.action name="logForm" path="/loginAction" scope="request"
 *                validate="false" parameter="method" input="edit"
 * 
 * @struts.action-forward name="logpage" path="/logpage.jsp"
 * @struts.action-forward name="logpage2" path="/logpage2.jsp"
 * @struts.action-forward name="pass" path="/frMain.jsp" *
 * @struts.action-forward name="home" path="/home.jsp"
 * @struts.action-forward name="to_job" path="/job.do?method=dojob"
 * @struts.action-forward name="newFile" path="/newWorkFile.jsp"
 * @struts.action-forward name="taskListHomePage"
 *                        path="/taskList/taskListHomePage.jsp"
 * @struts.action-forward name="taskList" path="/taskList/taskList.jsp"
 * @struts.action-forward name="taskListContent"
 *                        path="/taskList/taskListContent.jsp"
 * @struts.action-forward name="updatePassword" path="/fmDialog.jsp"
 * @struts.action-forward name="BMJYsearchmain" path="/plat/bmjy/krm_bmjy.jsp"
 * @struts.action-forward name="zhbslogtop" path="/plat/zhbs/top.jsp"
 * @struts.action-forward name="logtop" path="/top.jsp"
 * @struts.action-forward name="searchmain" path="/frMain.jsp"
 * @struts.action-forward name="ZHBSsearchmain" path="/plat/zhbs/krm_zhbs.jsp"
 * @struts.action-forward name="JYJCsearchmain" path="/plat/jyjc/krm_jyjc.jsp"
 * @struts.action-forward name="CWBBsearchmain" path="/plat/cwbb/krm_cwbb.jsp"
 * @struts.action-forward name="LDJCsearchmain" path="/plat/ldjc/index.jsp"
 * @struts.action-forward name="searchmainnew"
 *                        path="/plat/report_frame_total.jsp"
 * @struts.action-forward name="error1" path="/plat/error.jsp"
 * @struts.action-forward name="loginSwitch" path="/usermanage/loginSwitch.jsp"
 */

public class LoginAction extends BaseAction
{

	KRMLogger logger = KRMLoggerUtil.getLogger(LoginAction.class); 
	
	UserSubSysLoginService getUserSubSysLoginService()
	{
		return (UserSubSysLoginService) getBean("userSubSysLoginService");
	}
	
	public ActionForward enterLogin(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'enterLogin' method");
		}
		// request.getSession().invalidate();//add by lxk 20080508
		// 在重新登陆系统时，清除session中的信息
		OrganService os = getOrganService();

		// 从数据库中初始化系统功能配置项目（系统功能配置:parentid = 1012）
		FuncConfig.setPropertiesFromDB(this.getDictionaryService());

		if (os.getOrganCount() == 0)
		{// 系统初始化
			response.sendRedirect(request.getContextPath()
				+ "/sysInitAction.do?method=initform");
			return null;
		}

		// 登陆时间查询--make over zhaoPC
		DictionaryService dictionaryService = (DictionaryService) getBean("dictionaryService");
		Dictionary dic = dictionaryService.getDictionary(1102, 31);
		String date = DateUtil.getDateTime("yyyy-MM-dd", new Date());
		if (dic != null)
		{
			// TODO 和默认值相同,可不判断
			// if(dic.getDescription().toUpperCase().equals("C")){date =
			// DateUtil.getDateTime("yyyy-MM-dd",new Date());}
			if (dic.getDescription().toUpperCase().equals("PM"))
			{
				date = headFileconverDate(date.replaceAll("-", ""));
			}
		}
		request.setAttribute("date", date);

		// 天津报表系统页面 zh
		if (FuncConfig.getProperty("slsint.homepage", "frMain.jsp")
			.toLowerCase().equals("home.jsp"))
		{
			return mapping.findForward("logpage2");
			//标准化登录页面
		}else if(FuncConfig.getProperty("plat.sysflag", "frMain.jsp").equals("rhbz")){
			return mapping.findForward("logpagerhbz");
			//客户风险登录页面
		}else if(FuncConfig.getProperty("plat.sysflag", "frMain.jsp").equals("khfx")){
			return mapping.findForward("logpagekhfx");
		}else if(FuncConfig.getProperty("plat.sysflag", "frMain.jsp").equals("yjh")){
			return mapping.findForward("logpageyjh");
		}else{
			return mapping.findForward("logpage");
		}

	}
	
	public ActionForward skipTopPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		if (log.isDebugEnabled()) {
			log.info("Entering 'skipTopPage' method");
		}
		String loginFromPlatFlag = null;
		//判断用户是从平台登陆还是单独登陆 
		//如果loginFromPlatFlag不等于空 则跳转到综合报送top页面
		//否则跳转到原报表系统top页面
		if(request.getSession().getAttribute("loginFromPlatFlag")!=null){
			loginFromPlatFlag = (String) request.getSession().getAttribute("loginFromPlatFlag");
			request.setAttribute("loginFromPlatFlag", loginFromPlatFlag);
			getServlet().getServletContext().setAttribute("loginFromPlatFlag", loginFromPlatFlag);
			return mapping.findForward("zhbslogtop");
		}else{
			loginFromPlatFlag = "false";
			request.setAttribute("loginFromPlatFlag", loginFromPlatFlag);
			getServlet().getServletContext().setAttribute("loginFromPlatFlag", loginFromPlatFlag);
			return mapping.findForward("logtop");
		}
	}

	public ActionForward krmSSOLogin(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'krmSSOLogin' method");
		}
		HttpSession session = request.getSession(true);
		// String loginName = request.getParameter("loginName");
		String loginName = (String) session.getAttribute("loginId");
		String password = request.getParameter("password");
		// String loginDate = request.getParameter("date");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String loginDate = df.format(new Date());
		LogForm logForm = (LogForm) form;
		logForm.setLogindate(loginDate);
		logForm.setPassword(password);
		logForm.setLogonname(loginName);
		FuncConfig.setPropertiesFromDB(this.getDictionaryService());

		UserService us = getUserService();
		// User user = us.getUser(loginName, password);
		User user = us.getUser(loginName);
    
		// 取得用户的部门信息
		 initUserDeptInfo(user);
		session.setAttribute("user", user);
		OrganService os = getOrganService();
		OrganInfo currOrganInfo = os.getOrganByCode(user.getOrganId());
		if (currOrganInfo == null)
		{
			log.warn("没有找到机构ID为[" + user.getOrganId()
					+ "]的机构信息，请注意查询数据库确认是否存在！");
		}
		session.setAttribute("curorgan", currOrganInfo);
		session.setAttribute("logindate", loginDate);
		OrganService2 os2 = (OrganService2) getBean("organService2");
		session.setAttribute("min_tree_id", os2.getMinOrganTreeId(loginDate)
			+ "");
		log.info(this.getClass().getName() + "#login");
		log.info("登录时设置的appBaseDir = ["
			+ session.getServletContext().getRealPath("") + "]");
		// 为各应用设置根路径：
		
		String appBasePath="" ;
		if (session.getServletContext().getRealPath("") != null){
			appBasePath =session.getServletContext().getRealPath("/");
		}
		log.info("登陆时的appBasePath=========================="+appBasePath);
		log.info("zhaozulong only for test =============");
		if(session.getServletContext().getRealPath("")==null){
			if ("/".equals(System.getProperty("user.home")))
			{
				appBasePath = System.getProperty("user.home")
					+ "slsintAppFolder";
			}
			else
			{
				appBasePath = System.getProperty("user.home") + File.separator
					+ "slsintAppFolder";
			}
		}
		log.info("zhaozulong only for test =============");
		
		if (SysConfig.APPSERVER.equals("websphere"))
		{
			if ("/".equals(System.getProperty("user.home")))
			{
				appBasePath = System.getProperty("user.home")
					+ "slsintAppFolder";
			}
			else
			{
				appBasePath = System.getProperty("user.home") + File.separator
					+ "slsintAppFolder";
			}
		}
		appBasePath = FileUtil.makeNormalDirName(appBasePath);
		//getImportExportService().setAppBasePath(appBasePath);

		LoginUserInfo loginUser = new LoginUserInfo();
		loginUser.setUserId(user.getPkid().toString());
		loginUser.setUserAddr(request.getRemoteAddr());
		loginUser.setSessionId(request.getSession().getId());
		Date now = new Date();
		loginUser.setLoginTime(DateFormat.getDateTimeInstance(DateFormat.LONG,
			DateFormat.LONG).format(now));
		// 为解决登陆用户的organ_id和机构表未关联导致登陆白屏添加判断
		if (os.getOrganByCode(user.getOrganId()) != null)
			loginUser.setOrganName(os.getOrganByCode(user.getOrganId())
				.getShort_name());
		loginUser.setUserName(user.getName());
		request.getSession().setAttribute("loginUser", loginUser);

	    /*  登录成功后
	     *  为frMain.jsp中system_id,topStyle赋值*/
	    String topStyle = FuncConfig.getProperty("top.style", "standard");
	    session.setAttribute("topStyle", topStyle);
	    session.setAttribute("system_id",FuncConfig.getProperty("system.sysflag"));
		return mapping.findForward("pass");
	}

	private String headFileconverDate(String date)
	{
		String month = date.substring(4, 6);
		String converDate = "";
		// TODO 判断频度是否为月,季,半年,年.判断是否是闰年.日期取月末最后一天.
		if (((Integer.parseInt(date.substring(0, 4)) % 4 == 0) && (Integer
			.parseInt(date.substring(0, 4)) % 100 != 0))
			|| (Integer.parseInt(date.substring(0, 4)) % 400 == 0))
		{
			String[] LAST_DAY_OF_MONTH = {"31", "29", "31", "30", "31", "30",
				"31", "31", "30", "31", "30", "31"};
			if (month.equals("01"))
			{
				converDate = String.valueOf(Integer.parseInt(date.substring(0,
					4)) - 1)
					+ "-12-" + LAST_DAY_OF_MONTH[11];
			}
			else
			{
				converDate = date.substring(0, 4)
					+ "-"
					+ getMonth(Integer.parseInt(date.substring(4, 6)) - 1)
					+ "-"
					+ LAST_DAY_OF_MONTH[Integer.parseInt(date.substring(4, 6)) - 2];
			}
		}
		else
		{
			String[] LAST_DAY_OF_MONTH = {"31", "28", "31", "30", "31", "30",
				"31", "31", "30", "31", "30", "31"};
			if (month.equals("01"))
			{
				converDate = String.valueOf(Integer.parseInt(date.substring(0,
					4)) - 1)
					+ "-12-" + LAST_DAY_OF_MONTH[11];
			}
			else
			{
				converDate = date.substring(0, 4)
					+ "-"
					+ getMonth(Integer.parseInt(date.substring(4, 6)) - 1)
					+ "-"
					+ LAST_DAY_OF_MONTH[Integer.parseInt(date.substring(4, 6)) - 2];
			}
		}
		return converDate;
	}

	private String getMonth(int month)
	{
		if (month >= 10)
		{
			return String.valueOf(month);
		}
		else
		{
			return "0" + String.valueOf(month);
		}
	}
	
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'login' method");
		}
		//判断是否从平台登陆，在平台中定义了plat_flag标记，如果值为true，则从平台登陆，页面上方为后退按钮
		//否则为子系统直接登陆，页面上方为退出按钮
		//初始化菜单
		String loginFromPlatFlag = null;
		//设置页面转向变量
		String forWard = "searchmain";
		if(request.getSession().getAttribute("loginFromPlatFlag")!=null){
			loginFromPlatFlag = (String) request.getSession().getAttribute("loginFromPlatFlag");
			request.getSession().setAttribute("loginFromPlatFlag", loginFromPlatFlag);
		}else{
			loginFromPlatFlag = "false";
			request.getSession().setAttribute("loginFromPlatFlag", loginFromPlatFlag);
		}
		log.info(loginFromPlatFlag);
		getServlet().getServletContext().setAttribute("loginFromPlatFlag", loginFromPlatFlag);
		//executeOrgan(request,response);platloginswitch.properties
		//读取配置文件判断设置一个应用运行开关，
		//决定当前报表子系统是走平台开发后的业务逻辑，还是走以前的MO的通用逻辑
		String subSysOrMO = FuncConfig.getProperty("plat.flag"); 
		log.info("plat.flag"+subSysOrMO);
		login1(mapping, form, request, response);
		String a = (String) request.getAttribute("a");
		String b = (String) request.getAttribute("b");
		if(b != null||a != null)
		{
			return mapping.findForward("logpage");
		}
		if (subSysOrMO != null && subSysOrMO.equals("zixitong")) {
			login1(mapping, form, request, response);
			User user = (User) getUser(request);
			// 获得机构
			String organId =user.getOrganId();
			request.getSession().setAttribute("organId", organId);
			// 得到子系统flag名称
			String subsys_Flag = ActionUtil.getSubSystemFlag(request);
			String userName=user.getName();
			//插入系统日志
			//getSysLogService().insertLog(user.getPkid()+"", userName, "-1", LogUtil.LogType_User_Operate, "", "用户登录", "-1");
			// 获取用户在当前子系统中拥有的组权限，即用户关联的分组
			/*List platUserGpList = getUserService().getPlatUserGpInSubSys(
					userId, subsys_Flag);
			*/
			//如果用户有分组，即用户在该子系统下有权限
			if ("bmjy".equals(subsys_Flag)) {
				forWard = "BMJYsearchmain";
			} else if ("jyrb".equals(subsys_Flag)) {
				forWard = "JYJCsearchmain";
			} else if ("cwbb".equals(subsys_Flag)) {
				forWard = "CWBBsearchmain";
			} else if ("zhbs".equals(subsys_Flag)) {
				forWard = "ZHBSsearchmain";
			} else if ("ldjc".equals(subsys_Flag)) {
				forWard = "LDJCsearchmain";
			} 
			return mapping.findForward(forWard);
		} else {
			//数据抽取时用户登录限制  add ydw 20111120
			String loginSwitch = FuncConfig.getProperty("login.switch","yes");
			//String loginSwitch = FuncconfigUtil.getProperty("login.switch","yes");
		//	System.out.println("loginSwitch=="+loginSwitch);
		//	String aaa=FuncconfigUtil.getProperty("report.217.filter","yes");
		//	System.out.println("aaa=="+aaa);
			if("yes".equals(loginSwitch)){
				log.info("已配置数据抽取时系统是否登录");
				int dicid=Integer.parseInt(FuncConfig.getProperty("login.switch.dicid","9999"));
				int parentid=Integer.parseInt(FuncConfig.getProperty("login.switch.parentid","0"));
				DictionaryService dictionaryService = (DictionaryService)getBean("dictionaryService");
				Dictionary dict=dictionaryService.getDictionary(parentid,dicid);
				if("yes".equals(dict.getDescription())){
					log.info("数据库状态为【"+dict.getDescription()+"】可以登录");
//					ExcelMonitorJob monitorTask = new ExcelMonitorJob();
//					try {
//						monitorTask.executeInternal(null);
//					} catch (JobExecutionException e) {
//					
//						e.printStackTrace();
//					}
					return login1(mapping, form, request, response);
				}else{
					log.info("数据库状态为【"+dict.getDescription()+"】正在进行数据入库");
					// 查询出管理员用户如果是管理员在抽数时可以登录
					UserService us = getUserService();
					//User admin = us.getAdminUser();
					String loginname=request.getParameter("logonname");
					Long isLogin=new Long(FuncConfig.getProperty("isLogin.Role.id","50"));
					User user=us.getUser(loginname);
					
					if (("2").equals(user.getIsAdmin().toString()) || isLogin.equals(us.getUserRole(user.getPkid()).getRoleType()))//�ж��ǲ���ϵͳ����Ա�������û�ָ���Ľ�ɫ���Ե�¼
					{
						log.info("数据库状态为【"+dict.getDescription()+"】正在进行数据入库，登录用户为管理员可以登录");
						return login1(mapping, form, request, response);
					}
					request.setAttribute("loginswitch","1");
					if (FuncConfig.getProperty("slsint.homepage", "frMain.jsp")
							.toLowerCase().equals("home.jsp"))
						{
							return mapping.findForward("logpage2");
						}
						else
						{
							return mapping.findForward("logpage");
						}
				}
			}else{
				log.info("没有配置数据抽取时系统是否登录，登陆正常");
				return login1(mapping, form, request, response);
			}
		}
	}
	
	/**
	 * 用户登录验证并初始化用户参数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward login1(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'login1' method");
		}

 		String loginsecurity = FuncConfig.getProperty("login.security.isurser",
			"false");

		LogForm logForm = (LogForm) form;
		String loginmethod = request.getParameter("loginmethod");
		String logonname = null;
		String password = null;
		String logindate = null;
		String ipaddr = null;//IP地址
		HttpSession session = request.getSession(true);
		if (logForm == null && loginmethod == null)
		{
			if (FuncConfig.getProperty("slsint.homepage", "frMain.jsp")
				.toLowerCase().equals("home.jsp"))
			{
				return mapping.findForward("logpage2");
			}
			else
			{
				return mapping.findForward("logpage");
			}
		}
		if (request.getParameter("loginmethod") != null)
		{
			if (request.getParameter("logonname") != null)
			{
				logonname = request.getParameter("logonname");
				request.setAttribute("usenamee", logonname);
			}
			if (request.getParameter("password") != null)
			{
				password = request.getParameter("password");
			}
			if (request.getParameter("logindate") != null)
			{
				logindate = request.getParameter("logindate");
			}
			
			
		}
		else {
			
			//获得登陆用户的IP地址
			if (request.getHeader("x-forwarded-for") == null)
			{
				ipaddr = request.getRemoteAddr();
			}
			else
			{
				ipaddr = request.getHeader("x-forwarded-for");
			}
			
			logonname = logForm.getLogonname().trim();
			password = logForm.getPassword();
			request.setAttribute("usenamee", logonname);
			if(logForm.getLogindate()==null||logForm.getLogindate().equals("")){
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String time = df.format(date);
				logindate = time;
				session.setAttribute("logindate1", logindate);
			}else{
				logindate = logForm.getLogindate();
				session.setAttribute("logindate1", logindate);
			}
			
			logindate = logForm.getLogindate();
			request.setAttribute("date", logindate);
		}

		// 查询出管理员用户
		UserService us = getUserService();
		User admin = us.getAdminUser();
		if (!logonname.equals(admin.getLogonName()))
		{
			if ("true".equals(loginsecurity))
			{
				return login_new(mapping, form, request, response);
			}
		}
		DictionaryService ds = getDictionaryService();
		String dicname = ds.getIpStatus();//查询字典项IP校验状态״̬
		User user1= null;
		if(dicname.equals("1"))
		{
			//根据登录名和IP地址查询对象
			// user1 = us.getUserByIp(logonname, "192.168.2.186");
			   user1 = us.getUserByIp(logonname, ipaddr);
			 if(user1 == null)
			 {
				 request.setAttribute("b", "1");
				 return mapping.findForward("logpage");
			 }
		}
		User user = us.getUser(logonname, password);
		if (user == null)
		{
			request.setAttribute("a", "1");
			if (FuncConfig.getProperty("slsint.homepage", "frMain.jsp")
				.toLowerCase().equals("home.jsp"))
			{
				return mapping.findForward("logpage2");
			}
			else
			{
				return mapping.findForward("logpage");
			}
		}else{
//			     PrintWriter out = response.getWriter();
				 request.getSession().setAttribute("UserInfo", user);
				 request.getSession().setAttribute("loginname", user.getLogonName());
				 request.setAttribute("sysUser", user);
				 Loginbean lb = new Loginbean(request, response,logonname, user.getName(), password, logindate, null);
				 lb.setStatus("1");
//				 response.getWriter().write(lb.jsonStr());
				
		}
	
		// 取得用户的部门信息
		initUserDeptInfo(user);
		Long roleType = getUserRoleService().getRoleType(user.getPkid());
	    user.setRoleType(roleType);
		session.setAttribute("user", user);
		session.setAttribute("username", user.getName());
		if(FuncConfig.isOpenFun("report.oper.logger.enable")){
			System.out.println("Logintime"+DateUtils.thisDate(DateUtils.TIME_PATTERN));
			session.setAttribute("logintime", DateUtils.thisDate(DateUtils.TIME_PATTERN));
		}
		OrganService os = getOrganService();
		String topStyle = FuncConfig.getProperty("top.style", "standard");
		OrganInfo currOrganInfo = os.getOrganByCode(user.getOrganId());
		if (currOrganInfo == null)
		{
			log.warn("没有找到机构ID为[" + user.getOrganId()
					+ "]的机构信息，请注意查询数据库确认是否存在！");
		}
		session.setAttribute("topStyle", topStyle);
		session.setAttribute("curorgan", currOrganInfo); 
		session.setAttribute("logindate", logindate);   
		OrganService2 os2 = (OrganService2) getBean("organService2");
		session.setAttribute("min_tree_id", os2.getMinOrganTreeId(logindate)
			+ "");
		log.info(this.getClass().getName() + "#login");
		log.info("登录时设置的appBaseDir = ["
				+ session.getServletContext().getRealPath("") + "]");
		
		// 为各应用设置根路径：
		String appBasePath = session.getServletContext().getRealPath(
			"/");
		if(session.getServletContext().getRealPath("")==null){
			if ("/".equals(System.getProperty("user.home")))
			{
				appBasePath = System.getProperty("user.home")
					+ "slsintAppFolder";
			}
			else
			{
				appBasePath = System.getProperty("user.home") + File.separator
					+ "slsintAppFolder";
			}
		}
			
			
		if (SysConfig.APPSERVER.equals("websphere"))
		{
			if ("/".equals(System.getProperty("user.home")))
			{
				appBasePath = System.getProperty("user.home")
					+ "slsintAppFolder";
			}
			else
			{
				appBasePath = System.getProperty("user.home") + File.separator
					+ "slsintAppFolder";
			}
		}
		appBasePath = FileUtil.makeNormalDirName(appBasePath);
//		getImportExportService().setAppBasePath(appBasePath);

		
//		 getImportExportService().setUploadPath(appBasePath);

		// 记录登陆信息(Session)
		LoginUserInfo loginUser = new LoginUserInfo();
		loginUser.setUserId(user.getPkid().toString());
		loginUser.setUserAddr(request.getRemoteAddr());
		loginUser.setSessionId(request.getSession().getId());
		Date now = new Date();
		loginUser.setLoginTime(DateFormat.getDateTimeInstance(DateFormat.LONG,
			DateFormat.LONG).format(now));
		// 为解决登陆用户的organ_id和机构表未关联导致登陆白屏添加判断
		if (os.getOrganByCode(user.getOrganId()) != null)
			loginUser.setOrganName(os.getOrganByCode(user.getOrganId())
				.getShort_name());
		loginUser.setUserName(user.getName());
		request.getSession().setAttribute("loginUser", loginUser);
		/*
		 * ***********统计用户登录次数***********
		 */
		String ip = request.getRemoteAddr();
		if (!"shutdown".equals(FuncConfig.getProperty("login.logmode",
			"shutdown")))
		{
			// 在没有关闭的登录统计功能的情况下
			if ("debug".equals(FuncConfig.getProperty("login.logmode",
				"shutdown"))
				|| "console".equals(FuncConfig.getProperty("login.logmode",
					"shutdown")))
			{
				// 在控制台打印
				log.info("�û��� [" + ip + "]��¼ϵͳ��ѡ���������[" + logindate + "]");
			}
			if ("debug".equals(FuncConfig.getProperty("login.logmode",
				"shutdown"))
				|| "db".equals(FuncConfig.getProperty("login.logmode",
					"shutdown")))
			{
				// 在数据库中记录
//				LogService ls = (LogService) getBean("logService");
				Random random = new Random();
				String pseudReportId = String.valueOf(random.nextInt());

			}
		}
		/*
		 * ***********登录成功***********
		 */
		request.getSession().setAttribute("system_id",FuncConfig.getProperty("system.sysflag"));
		if (request.getParameter("loginmethod") != null)
		{
			if("true".equals(FuncConfig.getProperty("autoExcel.autologin","true"))){
				log.info("用户启用Excel自动导入功能所以不传递autologin参数");
			}else{
				session.setAttribute("autologin", "true");
			}
			return mapping.findForward("to_job");
		}
		if (FuncConfig.getProperty("slsint.homepage", "frMain.jsp")
			.toLowerCase().equals("home.jsp"))
		{
			request.setAttribute("login_date", logindate);
			return mapping.findForward("home");
		}
		return mapping.findForward("pass");
	}

	@SuppressWarnings("rawtypes")
	public ActionForward login_new(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'login_new' method");
		}
		LogForm logForm = (LogForm) form;
		String loginmethod = request.getParameter("loginmethod");
		String logonname = null;
		String password = null;
		String logindate = null;

		if (logForm == null && loginmethod == null)
		{
			if (FuncConfig.getProperty("slsint.homepage", "frMain.jsp")
				.toLowerCase().equals("home.jsp"))
			{
				return mapping.findForward("logpage2");
			}
			else
			{
				return mapping.findForward("logpage");
			}
		}
		if (request.getParameter("loginmethod") != null)
		{
			if (request.getParameter("logonname") != null)
			{
				logonname = request.getParameter("logonname");
			}
			if (request.getParameter("password") != null)
			{
				password = request.getParameter("password");
			}
			if (request.getParameter("logindate") != null)
			{
				logindate = request.getParameter("logindate");
			}
		}
		else
		{
			logonname = logForm.getLogonname().trim();
			password = logForm.getPassword();
			logindate = logForm.getLogindate();
		}

		HttpSession session = request.getSession(true);
		// 得到上下文对象，这里 用session处理不了，如果客户重开一个浏览器，就不属于同一个会话。
		ServletContext sc = session.getServletContext();
		// 得到LoginSecurityService服务对象
		LoginSecurityService lss = (LoginSecurityService) getBean("loginSecurity");
		// 得到用户的主机地址。
		String ipString = request.getRemoteAddr();
		// 每一个保存在servletcontext中的信息的标志就是IP地址+登录名来标记，
		String saveName = ipString + ";" + logonname;
		// 保存<ip+":"+logonname,list<date>>信息。
		Map datamap = (Map) sc.getAttribute(saveName);
		if (datamap == null)
		{
			datamap = new HashMap();
		}
		// 存储时间栈。
		List list = (List) sc.getAttribute(saveName + "list");
		if (list == null)
		{
			list = new ArrayList();
		}
		// 加锁对象全部放在几个集合中。
		Map lockuserMap = (Map) sc.getAttribute("lockuserList");
		if (lockuserMap == null)
		{
			log.info("没有加锁用户信息！");
			lockuserMap = new HashMap();
		}
		else
		{
			// 打印加锁用户的Map集合里的信息
			log.info("打印加锁用户的Map集合里的信息 lockuserMap = " + lockuserMap);
			if (lockuserMap.containsKey(logonname))
			{
				// 取出锁住用户是的时间。
				Date lockedDate = (Date) lockuserMap.get(logonname);
				// 判断用户的加锁时间是否已经过了24小时。
				boolean islocked = lss.isLocked(lockedDate);
				if (islocked)
				{
					// 还是 加锁状态
					log.info("该用户已被加锁，请于24小时后在登录！");
					request.setAttribute(ALERT_MESSAGE, "用户：" + logonname
						+ "已经被加锁，请于24小时候在登录！");
					return mapping.findForward("logpage");
				}
				else
				{
					//System.out.println("24小时已过了，该用户已经解锁了!");
					// 解锁之后，删除该用户信息。
					lockuserMap.remove(logonname);
					log.info("打印加锁用户的Map集合里的信息 lockuserMap = " + lockuserMap);
				}
			}
		}
		UserService us = getUserService();
		User user = us.getUser(logonname, password);
		// new 出登录的时间
		Date date = new Date();
		// ***** 计算时间
		// 当前时间减去5分钟。
		Date lateDate = new Date(date.getTime() - 5 * 60 * 1000);
		if (user == null)
		{
			list.add(date);
			datamap.put(saveName, list);
			// 将信息保存在servletcontext中
			sc.setAttribute(saveName, datamap);
			sc.setAttribute(saveName + "list", list);
			// 调用算法逻辑，统计5min 登录3此的逻辑
			String ipLoginname = lss.findWrongName(datamap, lateDate);
			log.info("用户登录错误后，调用5min算法获得返回的 ipLoginname = " + ipLoginname);
			if (!"".equals(ipLoginname))
			{
				// 拆分登录名和IP地址字符串。
				String logonName = ipLoginname.split(";")[1];
				// 判断用户是否在数据库中存在。
				boolean isuser = lss.isUser(logonName);
				if (isuser)
				{
					log.info("用户名 : " + logonName
							+ " 存在库中！锁住该用户,并对该用户提示两种解锁方式。");
						// 将登录名和加锁时间保存。
						lockuserMap.put(logonname, new Date());
						sc.setAttribute("lockuserList", lockuserMap);
						// 用户加锁后删除用户之前的登录信息。
						datamap.remove(ipLoginname);
						request.setAttribute(ALERT_MESSAGE, "用户 : " + logonName
							+ " 5分钟内登录3次错误,用户已被锁住！");
				}
				else
				{
					log.info("您输入的用户：" + logonName + " 不存在数据库中,返回登录页面！");
					request.setAttribute(ALERT_MESSAGE, "您输入的用户：" + logonName
						+ " 不存在,返回登录页面！");
				}
				// 当某个用户5min登录三次了，则要清楚他之前保存的一些信息。
				sc.removeAttribute(saveName);
				sc.removeAttribute(saveName + "list");
				// 返回到登录错误页面。
				return mapping.findForward("logpage");
			}
			request.setAttribute("a", "1");
			if (FuncConfig.getProperty("slsint.homepage", "frMain.jsp")
				.toLowerCase().equals("home.jsp"))
			{
				return mapping.findForward("logpage2");
			}
			else
			{
				return mapping.findForward("logpage");
			}
		}

		// 取得用户的部门信息
		 initUserDeptInfo(user);
		 Long roleType = getUserRoleService().getRoleType(user.getPkid());
		 user.setRoleType(roleType);
		session.setAttribute("user", user);
		OrganService os = getOrganService();
		String topStyle = FuncConfig.getProperty("top.style", "standard");
		OrganInfo currOrganInfo = os.getOrganByCode(user.getOrganId());
		if (currOrganInfo == null)
		{
			log.warn("没有找到机构ID为[" + user.getOrganId()
					+ "]的机构信息，请注意查询数据库确认是否存在！");
		}
		session.setAttribute("topStyle", topStyle);
		session.setAttribute("curorgan", currOrganInfo);
		session.setAttribute("logindate", logindate);
		OrganService2 os2 = (OrganService2) getBean("organService2");
		session.setAttribute("min_tree_id", os2.getMinOrganTreeId(logindate)
			+ "");
		log.info(this.getClass().getName() + "#login");
		log.info("登录时设置的appBaseDir = ["
			+ session.getServletContext().getRealPath("") + "]");
		// 为各应用设置根路径：
		String appBasePath = session.getServletContext().getRealPath(
			File.separator);
		if (SysConfig.APPSERVER.equals("websphere"))
		{
			if ("/".equals(System.getProperty("user.home")))
			{
				appBasePath = System.getProperty("user.home")
					+ "slsintAppFolder";
			}
			else
			{
				appBasePath = System.getProperty("user.home") + File.separator
					+ "slsintAppFolder";
			}
		}
		appBasePath = FileUtil.makeNormalDirName(appBasePath);
//		getImportExportService().setAppBasePath(appBasePath);

		
	//	getImportExportService().setUploadPath(appBasePath);

		
		LoginUserInfo loginUser = new LoginUserInfo();
		loginUser.setUserId(user.getPkid().toString());
		loginUser.setUserAddr(request.getRemoteAddr());
		loginUser.setSessionId(request.getSession().getId());
		Date now = new Date();
		loginUser.setLoginTime(DateFormat.getDateTimeInstance(DateFormat.LONG,
			DateFormat.LONG).format(now));
		// 为解决登陆用户的organ_id和机构表未关联导致登陆白屏添加判断
		if (os.getOrganByCode(user.getOrganId()) != null)
			loginUser.setOrganName(os.getOrganByCode(user.getOrganId())
				.getShort_name());
		loginUser.setUserName(user.getName());
		request.getSession().setAttribute("loginUser", loginUser);

		/*
		 * ***********统计用户登录次数***********
		 */
		
		String ip = request.getRemoteAddr();
		if (!"shutdown".equals(FuncConfig.getProperty("login.logmode",
			"shutdown")))
		{
			// 在没有关闭的登录统计功能的情况下
			if ("debug".equals(FuncConfig.getProperty("login.logmode",
				"shutdown"))
				|| "console".equals(FuncConfig.getProperty("login.logmode",
					"shutdown")))
			{
				// 在控制台打印
				log.info("用户从 [" + ip + "]登录系统，选择的日期是[" + logindate + "]");
			}
			if ("debug".equals(FuncConfig.getProperty("login.logmode",
				"shutdown"))
				|| "db".equals(FuncConfig.getProperty("login.logmode",
					"shutdown")))
			{
				// 在数据库中记录

			}
		}

		/*
		 * ***********登录成功***********
		 */
		if (request.getParameter("loginmethod") != null)
		{
			session.setAttribute("autologin", "true");
			return mapping.findForward("to_job");
		}
		if (FuncConfig.getProperty("slsint.homepage", "frMain.jsp")
			.toLowerCase().equals("home.jsp"))
		{
			request.setAttribute("login_date", logindate);
			return mapping.findForward("home");
		}
		// 处理，如果登录成功了，判断密码更新时间的问题，如果某个用户的密码30没有更新了，
		// 则该用户在登入系统的时候强制的进行密码更新。
		Date updateDate = lss.getPasswordUpdateDate(logonname, password);
		boolean isUpdate = lss.compareDate(new Date(), updateDate);
		if (isUpdate)
		{
			// 强直转换到秘密更新页面
			log.info("用户：" + logonname + "密码已过期，请点击确定更改！");
			request.setAttribute(ALERT_MESSAGE, "用户：" + logonname
				+ "密码已过期，请点击确定更改！");
			// 保存密码过期更新的信息，方便后面的使用在userAction中使用。
			session.setAttribute("isupdatepassword", "ok");
			return mapping.findForward("updatePassword");
		}

		return mapping.findForward("pass");
	}

	public ActionForward logout(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'logout' method");
		}
		HttpSession session = request.getSession();
		//当用户退出后，清除其机构树缓存
		ServletContext servletContext = servlet.getServletContext();
		User user = getUser(request);
		if(servletContext.getAttribute("userDefineTree")!=null){
			Map userDefineTree = (Map)servletContext.getAttribute("userDefineTree");
			userDefineTree.remove(user.getPkid());
		}
		/**
		 * Logger
		 */
		if(FuncConfig.isOpenFun("report.oper.logger.enable")){
	    	String logouttime = DateUtils.thisDate(DateUtils.TIME_PATTERN);
	    	UserService us = getUserService();
	    	StringBuffer logininfo = getUserLoginInfo(request, logouttime, us);
	    	//System.out.println(logininfo);
	    	if(logininfo.length() != 0) 
	    		FileUtils.write(logininfo, new File(FileUtils.getLogFile(user.getSystemType())));
		}
    	/**loggerEnd**/
	
		session.removeAttribute("user");
		session.removeAttribute("loginUser");
		// 2010-7-7 下午01:47:08 皮亮修改
		// 偶尔会报异常，看源码后，发现是在session已经失效后调用此方法就会
		// 报此异常，这里把异常捕捉住，认为执行成功了
		try
		{
			session.invalidate();
		}
		catch (IllegalStateException e)
		{
			log.error(
				"session is already invalide! can not invalidate it again!!!",
				e);
			e.printStackTrace();
		}
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "loginAction.do?method=enterLogin");
		return null;
	}

	public ActionForward close(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'close' method");
		}
    	
    	User user = getUser(request);
    	String logouttime = DateUtils.thisDate(DateUtils.TIME_PATTERN);
    	UserService us = getUserService();
    	StringBuffer logininfo = getUserLoginInfo(request, logouttime, us);
    	//System.out.println(logininfo);
    	if(logininfo.length() != 0) FileUtils.write(logininfo, new File(FileUtils.getLogFile(user.getSystemType())));
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		session.invalidate();
		return null;
	}
	
	 @SuppressWarnings("rawtypes")
	private StringBuffer getUserLoginInfo(HttpServletRequest request, String logouttime, UserService us) {
	    	StringBuffer sb = new StringBuffer();
	    	String logintime = (String)request.getSession().getAttribute("logintime");
	    	User user = getUser(request);
	    	OrganInfo oi = (OrganInfo)request.getSession().getAttribute("curorgan");
	    	String timediff = "";
	    	if(logintime != null) {
	    		sb.append("@l@t" + logintime + " @i登录时间:" + logintime + " @o退出时间:" + logouttime + " @o" + oi.getShort_name());
	    		timediff = DateUtils.getInterval(logintime, logouttime, 1);
	    	}
	    	UserRole ur = us.getUserRole(user.getPkid());
	    	List roleList = us.getRolesAll();
	    	for(Iterator itr = roleList.iterator(); itr.hasNext();) {
	    		Role r = (Role)itr.next();
	    		if(r.getRoleType().intValue() == ur.getRoleType().intValue()) {
	    			sb.append(" @r" + r.getName() + " @u" + user.getLogonName() + " @在线时间:" + timediff);
	    			break;
	    		}
	    	}
	    	return sb;
	    }

	
	public ActionForward getTopInfo(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'getTopInfo' method");
		}
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 E");
		String date = f.format(now);

		String roleName = "无角色";
		User user = getUser(request);
		/**
		 * UserService us = (UserService)getBean("userService");
		 * 
		 * UserRole ur = us.getUserRole(user.getPkid()); List roleList =
		 * us.getRolesAll(); for(Iterator itr = roleList.iterator();
		 * itr.hasNext();) { Role role = (Role)itr.next();
		 * if(ur.getRoleType().intValue() == role.getRoleType().intValue()) {
		 * roleName = role.getName(); break; } }
		 */
		Map map = new HashMap();
		map.put("nowDate", date);
		map.put("roleName", user.getLogonName());
		List list = new ArrayList();
		list.add(map);

		JSONArray myJson = JSONArray.fromObject(list);
		System.out.println(myJson.toString());
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// System.out.println(myJson);
		out.print(myJson);
		out.flush();
		out.close();

		return null;
	}
	/**
	  * 平台登录新增方法
	  * 
	  * */
	 public ActionForward loginFromPlat(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception
			{
		 if (log.isDebugEnabled()) {
				log.info("Entering 'loginFromPlat' method");
			}
		    String loginFromPlatFlag = "true";		 	//FuncConfig.getProperty("plat.login");
			logger.info("loginFromPlat��"+loginFromPlatFlag);
			request.getSession().setAttribute("loginFromPlatFlag", loginFromPlatFlag);
			getServlet().getServletContext().setAttribute("loginFromPlatFlag", loginFromPlatFlag);

			//从配置文件中得到需要连接的子系统标识
				String sysflag = ActionUtil.getSubSystemFlag(request);
				SystemConfigReader ssocfg = SystemConfigReader.getInstance(sysflag);
		        String linkUrl =  ActionUtil.getSubSystemMainPageURLStr(request);
		        String sysFlag = ActionUtil.getSubSystemFlag(request);
				StringBuffer strb = new StringBuffer();
				strb.append(linkUrl);
				// String strSessionID = request.getSession().getId();
				strb.append("&sessionId=" + ActionUtil.getSessionID(request));
				// response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=GBK");
				String sRedirectUrl = strb.toString();
		        String sToUrl = "";
		        if(request.getParameter("redirecturl")!=null){
		        	sToUrl = (String)request.getParameter("redirecturl");
		        	if(request.getSession().getAttribute("redirecturl")!=null){
		        		request.getSession().removeAttribute("redirecturl");
		        	}
		        	request.getSession().setAttribute("redirecturl", sToUrl);
		        }
		      
				PrintWriter out = response.getWriter();
				out.print("<script type=\"text/javascript\">");
				out.print("window.location.href = '" + sRedirectUrl + "'");
				out.print("</script>");
				out.flush();
				out.close();
				return null;
			}
	 
	 /**
	  * 从子系统返回平台页面
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public ActionForward logoutToPlat(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		 if (log.isDebugEnabled()) {
				log.info("Entering 'logoutToPlat' method");
			}
		 String url = ActionUtil.getReLonginPlatURL(request);
		 System.out.println(url);
		 return new ActionForward(url, true);
		 
	       /* SystemConfigReader ssocfg = SystemConfigReader.getInstance("aispro");
	        String linkUrl =  ssocfg.getSubLoginLink("aispro");
			StringBuffer strb = new StringBuffer();
			strb.append(linkUrl);
			String strSessionID = request.getSession().getId();
			strb.append("&sessionId=" + strSessionID);
//			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String sRedirectUrl = strb.toString();
	        String sToUrl = "";
	        if(request.getParameter("redirecturl")!=null){
	        	sToUrl = (String)request.getParameter("redirecturl");
	        	if(request.getSession().getAttribute("redirecturl")!=null){
	        		request.getSession().removeAttribute("redirecturl");
	        	}
	        	log.info(sToUrl);
	        	request.getSession().setAttribute("redirecturl", sToUrl);
	        }
			PrintWriter out = response.getWriter();
			out.print("<script type=\"text/javascript\">");
			out.print("window.location.href = '" + sRedirectUrl + "'");
			out.print("</script>");
			out.flush();
			out.close();
			return null;*/
		}
	 
	 /**
	  * 从子系统返回平台页面并返回错误信息
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public ActionForward logoutErrorToPlat(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		 if (log.isDebugEnabled()) {
				log.info("Entering 'logoutErrorToPlat' method");
			}
	        SystemConfigReader ssocfg = SystemConfigReader.getInstance("aispro");
	        String linkUrl =  ssocfg.getSubLoginLink("aispro");
			StringBuffer strb = new StringBuffer();
			strb.append(linkUrl);
			String strSessionID = request.getSession().getId();
			strb.append("&sessionId=" + strSessionID);
//			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String sRedirectUrl = strb.toString();
	        String sToUrl = "";
	        if(request.getParameter("redirecturl")!=null){
	        	sToUrl = (String)request.getParameter("redirecturl");
	        	if(request.getSession().getAttribute("redirecturl")!=null){
	        		request.getSession().removeAttribute("redirecturl");
	        	}
	        	log.info(sToUrl);
	        	request.getSession().setAttribute("redirecturl", sToUrl);
	        }
			PrintWriter out = response.getWriter();
			out.print("<script type=\"text/javascript\">");
			out.print("window.location.href = '" + sRedirectUrl + "'");
			out.print("</script>");
			out.flush();
			out.close();
			return null;
		}

	 public ActionForward loginError(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		 if (log.isDebugEnabled()) {
				log.info("Entering 'loginError' method");
			}
		 String message = "您没有权限进入子系统";
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write("<script type=\"text/javascript\">alert(\""+ message + "\");self.close()</script>"); 		
			response.flushBuffer();
			return mapping.findForward("error1");
	 }
	 public ActionForward loginSwitch(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		 if (log.isDebugEnabled()) {
				log.info("Entering 'loginSwitch' method");
			}
		 log.info("进入用户登录控制开关功能");
		 	int dicid=Integer.parseInt(FuncConfig.getProperty("login.switch.dicid","9999"));
			int parentid=Integer.parseInt(FuncConfig.getProperty("login.switch.parentid","0"));
			DictionaryService dictionaryService = (DictionaryService)getBean("dictionaryService");
			Dictionary dict=dictionaryService.getDictionary(parentid,dicid);
			 log.info("dict==="+dict.getDescription());
			request.setAttribute("switch", dict.getDescription());			
			return mapping.findForward("loginSwitch");
	 }
	 public ActionForward updateLoginSwitch(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		 if (log.isDebugEnabled()) {
				log.info("Entering 'updateLoginSwitch' method");
			}
		 log.info("进入修改用户登录控制开关功能");
		 	int dicid=Integer.parseInt(FuncConfig.getProperty("login.switch.dicid","9999"));
			int parentid=Integer.parseInt(FuncConfig.getProperty("login.switch.parentid","0"));
			DictionaryService dictionaryService = (DictionaryService)getBean("dictionaryService");
			String switchValue=request.getParameter("switchvalue");
			dictionaryService.updateVersion(parentid, dicid,switchValue);
			Dictionary dict=dictionaryService.getDictionary(parentid,dicid);
			 log.info("switchValue==="+dict.getDescription());
			request.setAttribute("switch",dict.getDescription());			
			return mapping.findForward("loginSwitch");
	 }
	 
	 public ActionForward getlogin(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws IOException{
		 String logonname  = request.getParameter("logname").trim();
		 String password = request.getParameter("logpasd").trim();
		 String logdate = request.getParameter("logdate").trim();
		 UserService us = getUserService();
		 User user = us.getUser(logonname, password);
		 JSONObject json = new JSONObject();
			if(user == null){
				 json.put("status", 2);
			 }else{
				 json.put("status", 1); 
			 }
			 String dataJson = JSONArray.fromObject(json).toString();
			 response.setContentType("text/html;charset=UTF-8");
			 response.setHeader("Cache-Control", "no-cache");
			  PrintWriter pw = response.getWriter();
			  pw.write(dataJson);
			  pw.flush();
			  pw.close();
			 return null;
	 }
	 public ActionForward updatedate(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws IOException{
		 
		 
		 //System.out.println("===="+request.getSession().getAttribute("logindate1"));
		 request.getSession().removeAttribute("logindate1");
		 request.getSession().setAttribute("logindate", request.getParameter("date97"));
		// System.out.println("===="+request.getSession().getAttribute("logindate"));
		 return null;
	 }
}