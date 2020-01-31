package com.krm.ps.framework.common.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.krm.util.Constants;

/**
 * @web.filter display-name="Request Filter" name="requestFilter"
 * @web.filter-mapping url-pattern="/*"
 */

public class RequestFilter extends HttpServlet implements Filter {
	
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 1L;
	private FilterConfig filterConfig;
    private Map map;
    
    
    //Handle the passed-in FilterConfig
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        map = Collections.synchronizedMap(new HashMap());
    }

    //Clean up resources
    public void destroy() {filterConfig = null;}

    //Process the request/response pair
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) {
        try {

            HttpServletRequest req = (HttpServletRequest)request;
            ServletContext servletContext = filterConfig.getServletContext();

            
            String id=req.getSession().getId();
            List list = null;
            if(map.containsKey(id)){
            	list = (List)map.get(id);
            }else{
                list = new ArrayList();
            }
            fetchID(req, list);
       	    map.put(req.getSession().getId(),list);
            
            servletContext.setAttribute(Constants.USER_OPER, map);
            filterChain.doFilter(request, response);
        } catch (ServletException sx) {
        	filterConfig.getServletContext().log(sx.getMessage());    
        	filterConfig.getServletContext().log("log servlet exception!!!", sx);
            Throwable rootCause = sx.getRootCause();
            if (rootCause != null)
            	filterConfig.getServletContext().log(rootCause.getMessage(), rootCause);
        } catch (IOException iox) {
        	filterConfig.getServletContext().log(iox.getMessage());
        	filterConfig.getServletContext().log("log IO exception!!!", iox);
        }
    }

	/**
	 * @param req
	 * @param list
	 */
	private void fetchID(HttpServletRequest req, List list) {
		if(req.getParameter("method")!=null){
			list.add(req.getRequestURI()+Constants.METHOD_STRING+req.getParameter("method"));
		}else{
			if(req.getRequestURI().substring(req.getRequestURI().indexOf(".")+1,req.getRequestURI().length()).equals("do")||req.getRequestURI().substring(req.getRequestURI().indexOf(".")+1,req.getRequestURI().length()).equals("jsp"))
				list.add(req.getRequestURI());
		}
	}


}
