package com.krm.ps.framework.common.web.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.krm.ps.framework.common.services.UploadService;
import com.krm.ps.framework.common.web.util.UploadUtil;
import com.krm.ps.util.Constants;
import com.krm.ps.util.StringUtil;

/**
 * @web.filter display-name="Accessory Filter" name="accessoryFilter"
 * @web.filter-mapping url-pattern="/temp/*"
 */
public class AccessoryFilter implements Filter
{
	private final transient Log log = LogFactory.getLog(AccessoryFilter.class);

	private FilterConfig config = null;

	private static ApplicationContext ctx = null;

	public UploadService getUploadService()
	{
		if (ctx == null)
		{
			ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		}

		return (UploadService) ctx.getBean("uploadService");
	}

	public void init(FilterConfig config) throws ServletException
	{
		this.config = config;
	}

	public void destroy()
	{
		config = null;
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
		FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (log.isDebugEnabled())
		{
			log.debug(request.getRequestURI());
		}

		//Õë¶ÔWebSphereÂ·¾¶Æ¥Åä
		if (request.getRequestURI().indexOf(Constants.UPLOAD_ROOT_DIR + "/") >= 0)
		{
			ServletContext context = config.getServletContext();
			String fileRelativeURI = request.getRequestURI().substring(
				request.getContextPath().length());
			setContentType(response, fileRelativeURI);

			File targetFile = new File(context.getRealPath(fileRelativeURI));
			String accessoryClassName = UploadUtil
				.getAccessoryClassName(fileRelativeURI);
			if (!targetFile.exists())
			{
				getUploadService().updateLocalFile(accessoryClassName,
					targetFile.getPath(), response.getOutputStream());
			}
		}
		chain.doFilter(request, response);
	}

	private void setContentType(HttpServletResponse response,
		String fileRelativeURI)
	{
		String extension = UploadUtil.getExtension(fileRelativeURI);
		String contentType = StringUtil.getResourceString(
			Constants.DOWNLOAD_CONTENT_TYPE_KEY_PREFIX
				+ extension.toLowerCase(),
			Constants.DEFAULT_DOWNLOAD_CONTENT_TYPE);
		response.setContentType(contentType);
	}
}
