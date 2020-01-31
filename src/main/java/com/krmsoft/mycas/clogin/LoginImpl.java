package com.krmsoft.mycas.clogin;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.krm.slsint.organmanage.services.OrganService;
import com.krm.slsint.organmanage.vo.Department;
import com.krm.slsint.organmanage.vo.OrganInfo;
import com.krm.slsint.organmanage2.services.OrganService2;
import com.krm.slsint.usermanage.services.UserService;
import com.krm.slsint.usermanage.vo.User;
import com.krm.slsint.util.FuncConfig;
import com.krm.util.*;


public class LoginImpl implements Login {

	private static final Logger logger = Logger.getLogger(LoginImpl.class);
	
	public String doLogin(HttpServletRequest request, HttpServletResponse res) throws Exception {
		 
		String rs = "1";
		String logonname = (String) request.getAttribute("logonname");
		String password = (String) request.getAttribute("password");
		String jsessionIdstr = (String) request.getAttribute("jsessionIdstr");
		String logdate = (String) request.getAttribute("logindate");
		String msg = "01"; //01 没有该用户 //密码错误 //权限错误  //机构信息错误
		UserService us = (UserService) getBean(request, "userService");
		User user = us.getUser(logonname, password);
		if (user != null) {

			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("jsessionIdstr", jsessionIdstr);   //登录成功设置标识
			initUserDeptInfo(request, user);
			//Long roleType = ((UserRoleService) getBean(request,"userRoleService")).getRoleType(user.getPkid());
			//user.setRoleType(roleType);
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("username", user.getName());

			OrganService os = (OrganService) getBean(request, "organService");
			String topStyle = FuncConfig.getProperty("top.style", "standard");
			OrganInfo currOrganInfo = os.getOrganByCode(user.getOrganId());
			if (currOrganInfo == null) {

			}
			request.getSession().setAttribute("topStyle", topStyle);
			request.getSession().setAttribute("curorgan", currOrganInfo);
			request.getSession().setAttribute("logindate", logdate);
			OrganService2 os2 = (OrganService2) getBean(request,"organService2");
			request.getSession().setAttribute("min_tree_id",os2.getMinOrganTreeId(logdate) + "");
			request.getSession().setAttribute("system_id",FuncConfig.getProperty("system.sysflag"));

		} else {
			throw new Exception(msg);
		}
		return rs;

	}

	
	public boolean checkLogin(HttpServletRequest request, HttpServletResponse res) {
		
	
		String jsessionId = (String) request.getAttribute("jsessionIdstr");
		User user = (User) request.getSession().getAttribute("user");
		String jsessionIdstrlast = (String) request.getSession().getAttribute("jsessionIdstr");
		logger.info("jsessionId:"+jsessionId+";"+jsessionIdstrlast+"");
		if(null==user)
		{
			return false;
		}
		else if(jsessionId!=null&&jsessionIdstrlast!=null&&!jsessionId.equals(jsessionIdstrlast)) //判断是否再次登录
		{
			return false;
		}
		
		
		return true;
		
	}

	
	public String logout(HttpServletRequest request, HttpServletResponse res) throws Exception {
		
		String msg = "02";
		request.getSession().invalidate();//是让SESSION失效.
		if("02".equals(msg)) {
			throw new Exception(msg);
		}
		return msg;
		
	}
	
    public boolean checkLogout(HttpServletRequest request, HttpServletResponse res) {
    	//1、System.out.println(request.getContextPath()); 打印结果：/east 
    	//2、System.out.println(request.getServletPath()); 打印结果：/loginAction.do 
    	//3、System.out.println(request.getRequestURI());  打印结果：http://127.0.0.1:8080/east/loginAction.do
    	Map  map= request.getParameterMap();
    	String []method=(String[]) map.get("method");
    	String methStr="";
    	if (method!=null) {
    		 methStr=method[0];
		}
		String culr =request.getServletPath().toString()+"?method="+methStr;
		logger.info(culr);
		String logoupath = (String) request.getAttribute("logouturl");
		if(culr.equals(logoupath)){//比较当前登录路径 是否和 web.xml 拦截器配置的路径是否相同
			return false;
		}
		return true;
		
	}
	protected void initUserDeptInfo(HttpServletRequest request,User user) {
		OrganService organService = (OrganService) getBean(request,"organService");
		Department dept = organService.getDepartmentForUser(user.getPkid());
		if (dept != null) {
			user.setDepartmentId(dept.getPkid());
			user.setDepartmentName(user.getDepartmentName());
		}
	}
	
	
	protected Object getBean(HttpServletRequest request,String beanname )
	{
		return WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext()).getBean(beanname);
			
	}
	

}
