package com.krm.ps.framework.common.web.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BeanUtilServlet extends HttpServlet
{
	private static ApplicationContext appContext;
	
	
	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException
	{
		super.init();
		System.out.println("-------------init slsint beanutil " +
		"by which we can get bean in the management of springframework---------");
		if (appContext == null)
			appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
	}
	
	public static Object getBean(String beanName)
	{
		return appContext.getBean(beanName);
	}
	
}
