package com.krm.ps.framework.common.web.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.*;


public class CharacterReplaceFilter implements Filter{

	private final Logger LOG = LoggerFactory.getLogger(CharacterReplaceFilter.class);
	private final static String KEY = "filePath";

	private FilterConfig filterConfig = null;

	private static Properties props = new Properties();

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		String value = this.filterConfig.getInitParameter(KEY);
		InputStream ins = CharacterReplaceFilter.class.getClassLoader().getResourceAsStream(value);
		try {
			
			props.load(ins);
			LOG.info("敏感词文件加载成功");
	    } catch (IOException e) {
	    	LOG.error("加载敏感词文件时发生错误", e);
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		
		final String method = httpReq.getMethod();
			String basePath = httpReq.getQueryString();
			LOG.info(basePath);
			if(basePath!=null){
				String[] a = basePath.split("[,.<>/=()&]");
				int j=0;
				for (int i = 0; i < a.length; i++) {
					if(a[i]!=""){
						Enumeration en = props.propertyNames();
						while (en.hasMoreElements()) {
							String s = en.nextElement().toString();
							if (a[i].contains(s)) {
								j++;
							}
						}
					}
				}
				
				if(j!=0){
					request.getRequestDispatcher("/404.jsp").forward(request, response);
				}else{
					chain.doFilter(request, response);
				}
			}
			else {
				chain.doFilter(request, response);
			}
	}

	

	public void destroy() {
		filterConfig = null;
		props = null;
	}
	
	
}
