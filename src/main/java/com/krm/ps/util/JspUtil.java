package com.krm.ps.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JspUtil
{
	public static Object getBean(ServletContext context, String beanId)
	{
		ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(context);
		return ctx.getBean(beanId);
	}
}
