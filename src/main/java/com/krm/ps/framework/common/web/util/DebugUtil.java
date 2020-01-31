package com.krm.ps.framework.common.web.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DebugUtil
{
	public static void printRequestParameters(Log log,
		HttpServletRequest request)
	{
		if (log.isDebugEnabled())
		{
			Enumeration e = request.getParameterNames();

			while (e.hasMoreElements())
			{
				String paramName = (String) e.nextElement();
				log.debug(paramName + "=" + request.getParameter(paramName));
			}
		}
	}

	public static void debug(Class clazz, Object message)
	{
		Log log = LogFactory.getLog(clazz);
		if (log.isDebugEnabled())
		{
			log.debug(message);
		}
	}
}
