package com.krm.ps.framework.common.web.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.krm.common.logger.KRMLogger;
import com.krm.common.logger.KRMLoggerUtil;
import com.krm.ps.framework.common.dictionary.services.DictionaryManager;
import com.krm.ps.framework.common.services.CommonQueryService;
import com.krm.ps.framework.common.vo.CommonQueryParams;
import com.krm.ps.framework.common.web.util.BeanUtilServlet;
import com.krm.ps.framework.common.web.util.DebugUtil;
import com.krm.ps.framework.common.web.util.RequestUtil;
import com.krm.ps.model.datafill.services.impl.ReportMoveVerifyServiceImpl;
import com.krm.ps.model.reportview.services.ReportViewService;
import com.krm.ps.model.sysLog.services.SysLogService;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.Department;
import com.krm.ps.sysmanage.organmanage2.model.OrganSystem;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.organmanage2.services.OrganTreeManager;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.services.UserRoleService;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.Constants;
import com.krm.ps.util.CurrencyConverter;
import com.krm.ps.util.DateConverter;
import com.krm.ps.util.TimestampConverter;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.services.ReportFormatService;
 

/**
 * Implementation of <strong>Action</strong> that contains base methods for
 * Actions as well as determines with methods to call in subclasses. This class
 * uses the name of the button to determine which method to execute on the
 * Action. </p>
 * 
 * <p>
 * For example look at the following two buttons:
 * </p>
 * 
 * <pre>
 *           &lt;html:submit property=&quot;method.findName&quot;&gt;
 *              &lt;bean:message key=&quot;email.find&quot;/&gt;
 *           &lt;/html:submit&gt;
 *       
 *           &lt;html:submit property=&quot;method.findEmail&quot;&gt;
 *              &lt;bean:message key=&quot;email.find&quot;/&gt;
 *           &lt;/html:submit&gt;
 * </pre>
 * 
 * <p>
 * The name of the button is set with the property parameter, i.e., the name of
 * the first button is method.findName. The name of the second button is
 * method.findEmail.
 * </p>
 * 
 * <p>
 * As per HTML/HTTP, whatever submit button that is pushed causes only that
 * submit button's name to be sent as a request parameter to the action.
 * </p>
 * 
 * <p>
 * This action looks for the name by removing the prepender string "method.".
 * The remaining part of the string is the name of the method to execute, e.g.,
 * pushing the first button will execute the findName method and the second
 * button will execute the findEmail method.
 * </p>
 * 
 * <p>
 * This class extends DispatchAction and allows methods to be sent as regular
 * GETs as well, i.e., &lt;a href="emailAction.do?method=findEmail"/&gt; would
 * cause the findEmail method to be executed just as it would in a
 * DispatchAction. Thus, you configure a ButtonNameDispatchAction exactly the
 * way you configure a DispatchAction, i.e., set the mapping parameter to the
 * name of the request parameter that holds the mehtod name.
 * </p>
 * 
 * <p>
 * <a href="BaseAction.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @author Rick Hightower (based on his ButtonNameDispatchAction)
 * 
 */
public class BaseAction extends DispatchAction {
	public static final String SECURE = "secure";

	public static final String ALERT_MESSAGE = "ALERT_MESSAGE";

	private static String CURRENTUSER = "user";

	private static ApplicationContext ctx = null;

	private static Long defaultLong = null;

	static {
		ConvertUtils.register(new CurrencyConverter(), Double.class);
		ConvertUtils.register(new DateConverter(), Date.class);
		ConvertUtils.register(new DateConverter(), String.class);
		ConvertUtils.register(new LongConverter(defaultLong), Long.class);
		ConvertUtils.register(new IntegerConverter(defaultLong), Integer.class);
		ConvertUtils.register(new TimestampConverter(), Timestamp.class);
	}

	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * Override the execute method in DispatchAction to parse URLs and forward
	 * to methods without parameters. </p>
	 * <p>
	 * This is based on the following system:
	 * <p/>
	 * <ul>
	 * <li>edit*.html -> edit method</li>
	 * <li>save*.html -> save method</li>
	 * <li>view*.html -> search method</li>
	 * </ul>
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (isCancelled(request)) {
				try {
					getMethod("cancel");
					return dispatchMethod(mapping, form, request, response,
							"cancel");
				} catch (NoSuchMethodException n) {
					log.warn("No 'cancel' method found, returning null");
					return cancelled(mapping, form, request, response);
				}
			}
			String actionMethod = getActionMethodWithMapping(request, mapping);
			if (log.isDebugEnabled()) {
				log.debug("**********请求的ActionMethod================["
						+ actionMethod + "]");
			}
			return checkSessionTimeOut(mapping, form, request, response,
					actionMethod);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()
					+ "/DictionaryAction.do?method=inanition");
		}
		if (mapping.getInput() != null) {
			return mapping.findForward(mapping.getInput());
		}
		response.sendRedirect(request.getContextPath()
				+ "/DictionaryAction.do?method=inanition");
		return null;
	}

	/**
	 * Convenience method to bind objects in Actions
	 * 
	 * @param name
	 * @return
	 */
	public Object getBean(String name) {
		if (ctx == null) {
			
			if(servlet == null) {
				String str = ""+"";
				return BeanUtilServlet.getBean(name);
				
			} else {
				
				ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet
						.getServletContext());
				
			}
			
		}
		return ctx.getBean(name);
	}

	/**
	 * Convenience method to initialize messages in a subclass.
	 * 
	 * @param request
	 *            the current request
	 * @return the populated (or empty) messages
	 */
	public ActionMessages getMessages(HttpServletRequest request) {
		ActionMessages messages = null;
		HttpSession session = request.getSession();

		if (request.getAttribute(Globals.MESSAGE_KEY) != null) {
			messages = (ActionMessages) request
					.getAttribute(Globals.MESSAGE_KEY);
			saveMessages(request, messages);
		} else if (session.getAttribute(Globals.MESSAGE_KEY) != null) {
			messages = (ActionMessages) session
					.getAttribute(Globals.MESSAGE_KEY);
			saveMessages(request, messages);
			session.removeAttribute(Globals.MESSAGE_KEY);
		} else {
			messages = new ActionMessages();
		}
		return messages;
	}

	/**
	 * Gets the method name based on the mapping passed to it
	 */
	private String getActionMethodWithMapping(HttpServletRequest request,
			ActionMapping mapping) {
		return getActionMethod(request, mapping.getParameter());
	}

	/*
	 * ����getModuleService���ֵõ�ҵ������ķ���Ӧ��д�����
	 * ��������ģ�鶼�ظ�д������������private�����������Ϊprotected
	 * 
	 * private ModuleService getModuleService() { return (ModuleService)
	 * getBean("moduleService"); }
	 */

	/**
	 * Convenience method for getting an action form base on it's mapped scope.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 * @return ActionForm the form from the specifies scope, or null if nothing
	 *         found
	 */
	protected ActionForm getActionForm(ActionMapping mapping,
			HttpServletRequest request) {
		ActionForm actionForm = null;
		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				actionForm = (ActionForm) request.getAttribute(mapping
						.getAttribute());
			} else {
				HttpSession session = request.getSession();
				actionForm = (ActionForm) session.getAttribute(mapping
						.getAttribute());
			}
		}
		return actionForm;
	}

	/**
	 * Gets the method name based on the prepender passed to it.
	 */
	protected String getActionMethod(HttpServletRequest request, String prepend) {
		String name = null;
		// for backwards compatibility, try with no prepend first
		name = request.getParameter(prepend);
		if (name != null) {
			// trim any whitespace around - this might happen on buttons
			name = name.trim();
			// lowercase first letter
			return name.replace(name.charAt(0),
					Character.toLowerCase(name.charAt(0)));
		}
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String currentName = (String) e.nextElement();

			if (currentName.startsWith(prepend + ".")) {
				if (log.isDebugEnabled()) {
					log.debug("calling method: " + currentName);
				}

				String[] parameterMethodNameAndArgs = StringUtils.split(
						currentName, ".");
				name = parameterMethodNameAndArgs[1];
				break;
			}
		}
		return name;
	}

	protected String getLastNavigationInfo(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(
				Constants.SESSION_ATTR_NAVIGATION_INFO);
	}

	protected String getNavigationInfo(HttpServletRequest request) {
		String url = request.getServletPath();
		String queryString = request.getQueryString();
		if (queryString != null) {
			int idx = queryString.indexOf("&purview=");
			if (idx != -1) {
				queryString = queryString.substring(0, idx);
			}
			url += "?" + queryString;
		}
		/*
		 * String[] navigationInfo = getUserManageService().getNavigation(
		 * url.substring(1));
		 */
		String[] navigationInfo = null;
		if (navigationInfo != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(getResources(request).getMessage("navigation.prefix"));
			sb.append(getResources(request).getMessage("navigation.homepage"));
			for (int i = navigationInfo.length - 1; i >= 0; i--) {
				sb.append(getResources(request).getMessage(
						"navigation.arrowhead"));
				sb.append(navigationInfo[i]);
			}
			return sb.toString();
		}
		return null;
	}

	protected StringBuffer getRelativeUrl(HttpServletRequest request,
			String path, Map newParams) {
		Map params = new HashMap(request.getParameterMap());
		params.putAll(newParams);
		return getRelativeUrl(path, params);
	}

	protected StringBuffer getRelativeUrl(String path, Map params) {
		StringBuffer url = new StringBuffer();
		url.append(path);
		url.append("?");
		url.append(RequestUtil.createQueryStringFromMap(params, "&"));
		return url;
	}

	protected ActionForward getTreeForward(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("tree");
	}

	/**
	 * ���ɵ�����Ϣ�����ݲ�����Ự��
	 * 
	 * @param request
	 */
	protected void makeNavigationInfo(HttpServletRequest request) {
		String navigationInfo = getNavigationInfo(request);
		if (navigationInfo != null) {
			setNavigationInfo(request, navigationInfo);
		}
	}

	protected ActionForward messageBox(ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response,
			String fun, String messageKey, String okUrl, String cancelUrl) {
		request.setAttribute("fun", fun);
		request.setAttribute("messageKey", messageKey);
		request.setAttribute("okUrl", okUrl);
		request.setAttribute("cancelUrl", cancelUrl);

		return mapping.findForward("prompt");
	}

	protected void printRequestParameters(HttpServletRequest request) {
		DebugUtil.printRequestParameters(log, request);
	}

	protected void redirect(ActionMapping mapping, HttpServletRequest request,
			HttpServletResponse response, String path, String method)
			throws IOException {
		Map newParams = new HashMap();
		newParams.put(mapping.getParameter(), method);
		StringBuffer url = new StringBuffer();
		url.append(request.getContextPath());
		url.append(getRelativeUrl(request, path, newParams));
		response.sendRedirect(url.toString());
	}

	/**
	 * Convenience method for removing the obsolete form bean.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 */
	protected void removeFormBean(ActionMapping mapping,
			HttpServletRequest request) {
		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				request.removeAttribute(mapping.getAttribute());
			} else {
				HttpSession session = request.getSession();
				session.removeAttribute(mapping.getAttribute());
			}
		}
	}

	protected void setNavigationInfo(HttpServletRequest request,
			String navigationInfo) {
		request.getSession().setAttribute(
				Constants.SESSION_ATTR_NAVIGATION_INFO, navigationInfo);
	}

	protected void setTreeXML(HttpServletRequest request, String treeXML) {
		request.setAttribute(Constants.ATTR_TREE_XML, treeXML);
	}

	/**
	 * Convenience method to update a formBean in it's scope
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 * @param form
	 *            The ActionForm
	 */
	protected void updateFormBean(ActionMapping mapping,
			HttpServletRequest request, ActionForm form) {
		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				request.setAttribute(mapping.getAttribute(), form);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute(mapping.getAttribute(), form);
			}
		}
	}

	/**
	 * ���ɷ�ҳ��Ϣ��
	 * 
	 * @param request
	 * @param resultNum
	 *            �������
	 * @param pageNo
	 *            ��ǰҳ��
	 * @param pageNum
	 *            ��ҳ��
	 * @param pageSize
	 *            ÿҳ��ʾ��������Ϣ
	 * @param pageIdNum
	 *            ҳ�����г����ٸ�ҳ��
	 * @param style
	 *            �磺pageIdNum=10; �����style = 1;���Ϊ��11,12,13,14,15,16,17,18,19,20
	 *            �����style = 2;���Ϊ��2,3,4,5,6,7,8,9,10,11 �����style =
	 *            3;���Ϊ��1,2,3,4,5,6,7,8,9,10,11
	 * @return
	 */
	protected String getPageNoBar(HttpServletRequest request, int resultNum,
			int pageNo, int pageNum, int pageSize, int pageIdNum, int style) {
		// ��ʼҳ��ʹ�õ�URL��ǰ׺���֣����ڻ�ƴ�ӣ�pageNo��pageNum
		Map urlMap = filterMethodParameterFromMap(request);
		urlMap.remove(Constants.PARAM_PAGE_PAGENO);
		urlMap.remove(Constants.PARAM_PAGE_PAGENUM);
		StringBuffer sbUrlPrefix = new StringBuffer();
		sbUrlPrefix.append(request.getRequestURI());
		sbUrlPrefix.append("?");
		sbUrlPrefix.append(RequestUtil.createQueryStringFromMap(urlMap, "&"));
		String strUrlPrefix = sbUrlPrefix.toString();
		log.debug(strUrlPrefix);

		StringBuffer sbPageNoBar = new StringBuffer();
		int pageStart = 0;// ҳ����Ŀ�б�Ŀ�ʼҳ��
		int pageEnd = 0;// ҳ����Ŀ�б�Ľ�βҳ��
		int resultStart = 0;// ��ǰҳ�Ŀ�ʼ��¼��
		int resultEnd = 0;// ��ǰҳ�Ľ�����¼��
		if (pageNum > 1 && pageNum >= pageNo && pageNo > 0) {
			resultStart = (pageNo - 1) * pageSize + 1;
			resultEnd = (pageNo * pageSize) >= resultNum ? resultNum
					: (pageNo * pageSize);
			// ���ݲ�ͬ��ʾ������ɻ������� pageStart pageEnd
			if (style == Constants.DEF_PAGE_STYLE_ONE) {
				float tmp = pageNo / pageIdNum;
				String strTimes = (new Float(tmp).toString().split("\\."))[0];
				int times = Integer.parseInt(strTimes);
				pageEnd = (pageNo % pageIdNum) == 0 ? ((times + 1) * pageIdNum - 1)
						: ((times + 1) * pageIdNum);
				pageStart = (pageNo % pageIdNum) == 0 ? (pageNo) : (pageEnd
						- pageIdNum + 1);
				pageEnd = pageEnd > pageNum ? pageNum : pageEnd;
			} else if (style == Constants.DEF_PAGE_STYLE_TWO) {
				pageStart = pageNum - pageNo >= pageIdNum ? pageNo : (pageNum
						- pageIdNum + 1);
				pageEnd = pageNum - pageNo >= pageIdNum ? (pageNo - 1 + pageIdNum)
						: pageNum;
				pageStart = pageStart > 1 ? pageStart : 1;
				pageEnd = pageEnd > pageNum ? pageNum : pageEnd;
			} else if (style == Constants.DEF_PAGE_STYLE_THREE) {
				pageStart = 1;
				if (pageNum > pageIdNum) {
					pageEnd = pageNo >= pageIdNum ? (pageNo + 1) : pageIdNum;
					pageEnd = pageEnd > pageNum ? pageNum : pageEnd;
				} else {
					pageEnd = pageNum;
				}
			}
			// ���ɷ�ҳ��Ϣ��
			sbPageNoBar.append(getResources(request).getMessage(
					"page.bar.resultNum", new Integer(resultNum),
					new Integer(resultStart), new Integer(resultEnd)));
			sbPageNoBar.append("&nbsp;&nbsp;");

			if (pageNo > 1) {
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.beforFrame"));
				// ���ӵ�һҳ
				sbPageNoBar.append("<a href=");
				sbPageNoBar.append(strUrlPrefix);
				sbPageNoBar.append("&pageNo=1");
				sbPageNoBar.append("&pageNum=");
				sbPageNoBar.append(pageNum);
				sbPageNoBar
						.append(" style=text-decoration:underline;color:ff0000>");
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.first"));
				sbPageNoBar.append("</a>");
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.split"));
				// ������ҳ
				sbPageNoBar.append("<a href=");
				sbPageNoBar.append(strUrlPrefix);
				sbPageNoBar.append("&pageNo=");
				sbPageNoBar.append(pageNo - 1);
				sbPageNoBar.append("&pageNum=");
				sbPageNoBar.append(pageNum);
				sbPageNoBar
						.append(" style=text-decoration:underline;color:ff0000>");
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.previous"));
				sbPageNoBar.append("</a>");
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.afterFrame"));
			} else {
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.beforFrame"));
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.first"));
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.split"));
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.previous"));
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.afterFrame"));
			}
			for (int i = pageStart; i <= pageEnd; i++) {
				sbPageNoBar.append("&nbsp;");
				sbPageNoBar.append("<a href=");
				sbPageNoBar.append(strUrlPrefix);
				sbPageNoBar.append("&pageNo=");
				sbPageNoBar.append(i);
				sbPageNoBar.append("&pageNum=");
				sbPageNoBar.append(pageNum);
				if (i == pageNo) {
					sbPageNoBar
							.append(" style=text-decoration:underline;color:000000><b>");
				} else {
					sbPageNoBar
							.append(" style=text-decoration:underline;color:ff0000><b>");
				}
				sbPageNoBar.append(i);
				sbPageNoBar.append("</b></a>");
				sbPageNoBar.append("&nbsp;");
			}
			if (pageNo < pageNum) {
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.beforFrame"));
				// ������ҳ
				sbPageNoBar.append("<a href=");
				sbPageNoBar.append(strUrlPrefix);
				sbPageNoBar.append("&pageNo=");
				sbPageNoBar.append(pageNo + 1);
				sbPageNoBar.append("&pageNum=");
				sbPageNoBar.append(pageNum);
				sbPageNoBar
						.append(" style=text-decoration:underline;color:ff0000>");
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.next"));
				sbPageNoBar.append("</a>");
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.split"));
				// �������һҳ
				sbPageNoBar.append("<a href=");
				sbPageNoBar.append(strUrlPrefix);
				sbPageNoBar.append("&pageNo=");
				sbPageNoBar.append(pageNum);
				sbPageNoBar.append("&pageNum=");
				sbPageNoBar.append(pageNum);
				sbPageNoBar
						.append(" style=text-decoration:underline;color:ff0000>");
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.last"));
				sbPageNoBar.append("</a>");
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.afterFrame"));
			} else {
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.beforFrame"));
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.next"));
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.split"));
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.last"));
				sbPageNoBar.append(getResources(request).getMessage(
						"page.bar.afterFrame"));
			}
		}
		return sbPageNoBar.toString();
	}

	private Map filterMethodParameterFromMap(HttpServletRequest request) {
		HashMap urlMap = new HashMap(request.getParameterMap());
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String currentName = (String) e.nextElement();

			if (currentName.toLowerCase().startsWith("method.")) {
				urlMap.put(currentName, "filter");
				break;
			}
		}
		return urlMap;
	}

	protected DictionaryManager getDictionaryManager() {
		DictionaryManager mgr = (DictionaryManager) getBean("dictionaryManager");
		return mgr;
	}


	protected ReportFormatService getRepFormatService() {
		ReportFormatService mgr = (ReportFormatService) getBean("reportFormatService");
		return mgr;
	}

	protected ReportDefineService getReportDefineService() {
		ReportDefineService rds = (ReportDefineService) getBean("reportDefineService");
		return rds;
	}

	protected OrganService getOrganService() {
		OrganService os = (OrganService) getBean("organService");
		return os;
	}

	protected UserService getUserService() {
		UserService us = (UserService) getBean("userService");
		return us;
	}



	protected DictionaryService getDictionaryService() {
		DictionaryService ds = (DictionaryService) getBean("dictionaryService");
		return ds;
	}


	protected UserRoleService getUserRoleService() {
		return (UserRoleService) this.getBean("userRoleService");
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

	/**
	 * ��֤�û�
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 */
	protected boolean validateUser(ActionMapping mapping,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		;
		if (session.getAttribute(CURRENTUSER) == null) {
			ActionMessages errors = new ActionMessages();
			errors.add("sessioninvalid", new ActionMessage(
					"error.sessioninvalid"));
			saveErrors(request, errors);
			return false;
		}
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope()))
				request.removeAttribute(mapping.getAttribute());
			else
				session.removeAttribute(mapping.getAttribute());
		}
		return true;
	}

	/**
	 * �����û���Ϣ
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 */

	protected User getUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(CURRENTUSER);
		return user;
	}

	/**
	 * ���ٽ�2006-06-30���� �õ����������ļ���ʵ��·��
	 * 
	 * @return
	 */
	protected String getDeployConfigFilePath() {
		String deployConfigFile = getServlet().getServletContext().getRealPath(
				"/")
				+ "WEB-INF\\classes\\depoly.properties";
		log.debug(deployConfigFile);
		return deployConfigFile;
	}

	/**
	 * ���ٽ�2006-06-30���� �õ�ϵͳ��������
	 * 
	 * @param deployProperties
	 * @return
	 */
	protected String getSystemDeployConfig(Properties deployProperties) {
		String deployMode = null;
		try {
			deployProperties
					.load(new FileInputStream(getDeployConfigFilePath()));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		deployMode = deployProperties.getProperty("deployMode");
		return deployMode;
	}

	/**
	 * ���ٽ�2006-07-02���Ӵ��� ���Ự��ʱ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param actionMethod
	 * @return
	 * @throws Exception
	 */
	private ActionForward checkSessionTimeOut(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String actionMethod) throws Exception {
		User currentUser = getUser(request);
		if (currentUser != null) {
			return dispatchMethod(mapping, form, request, response,
					actionMethod);
		}

		String strUrl = request.getRequestURI();
		String strUrl2 = request.getRequestURI() + "?" + "method="
				+ actionMethod;
		strUrl = strUrl.substring(request.getContextPath().length() + 1,
				strUrl.length());
		strUrl2 = strUrl2.substring(request.getContextPath().length() + 1,
				strUrl2.length());
		String strFilter = getServlet().getServletContext().getInitParameter(
				"filterUrlPrefix");
		if (matches(strUrl, strFilter) > 0 || matches(strUrl2, strFilter) > 0) {
			return dispatchMethod(mapping, form, request, response,
					actionMethod);
		} else {
			response.sendRedirect(request.getContextPath() + "/timeout.jsp");
			return null;
		}
	}

	/**
	 * �ж���ĳ���ִ����Ƿ�ƥ��Ҫ���˵��Ĵ�
	 * 
	 * @param strSource
	 *            Դ��
	 * @param strFilter
	 *            Ҫ���˵��Ĵ���֧�ֶ��ŷָ�����*���磺method=*tree,uploadFile*method=*
	 * @return
	 */
	private int matches(String strSource, String strFilter) {
		int matches = 0;
		strSource = strSource.toLowerCase();
		strFilter = strFilter.toLowerCase();
		String[] filters = strFilter.split(",");
		for (int i = 0; i < filters.length; i++) {
			String strTemp = strSource;
			if (filters[i].startsWith("*")) {
				filters[i] = filters[i].substring(1);
			}
			if (filters[i].endsWith("*")) {
				filters[i] = filters[i].substring(0, filters[i].length() - 1);
			}
			String[] strArray = filters[i].split("\\*");
			boolean hasMatch = true;
			for (int j = 0; j < strArray.length; j++) {
				int index = strTemp.indexOf(strArray[j]);
				if (index != -1) {
					strTemp = strTemp.substring(index + strArray[j].length());
				} else {
					hasMatch = false;
					break;
				}
			}
			if (hasMatch) {
				matches++;
				break;
			}
		}
		log.debug("matches result:" + matches);
		return matches;
	}

	/**
	 * �����û����������������ص�����(organTreeIdxid)
	 * 
	 * @param request
	 *            HttpServletRequest����ʵ��
	 * @param date
	 *            ҵ������
	 * @param idxid
	 *            ������ϵ������(CODE_ORG_TREE.IDXID)
	 */
	protected void refreshUserInfo(HttpServletRequest request, String date,
			int idxid) {
		//���µ�¼����
		request.getSession().setAttribute("logindate", date);

		//ȡ���û�����
		User u = (User) request.getSession().getAttribute("user");
		//ȡ�÷���
		OrganService2 os = (OrganService2) getBean("organService2");
		OrganTreeManager otm = os.getOrganTreeManager();

		//�жϻ���������id�Ƿ���Ա�ʹ��
		List visableTreeList = otm.listOrganSystems(u.getPkid().intValue(),
				date, 2);
		OrganSystem organSystem = otm.getOrganSystem(idxid);
		if (visableTreeList.contains(organSystem)) {
			//�����û�����ĵ�ǰ����������id(CODE_ORG_TREE.IDXID)
			u.setOrganTreeIdxid(idxid);
		} else {
			OrganSystem orgSys = (OrganSystem) visableTreeList.get(0);
			u.setOrganTreeIdxid(orgSys.getId().intValue());
		}
	}

	/**
	 * <p>
	 * ��ȡ�����еĲ���ֵ
	 * </p>
	 * 
	 * ������ʱ������������"?"���ţ��������˷������д���
	 * 
	 * @param request
	 * @param str
	 * @return
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-5-6 ����10:21:54
	 */
	protected String getParameterValue(HttpServletRequest request, String str) {
		String value = request.getParameter(str);
		if (value == null) // �������������
			return null;
		else
			return value.replaceAll("\\?", "");
	}

	/**
	 * <p>
	 * ��ʼ����Ա�Ĳ�����Ϣ
	 * </p>
	 * 
	 * @param user
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-5-7 ����01:17:05
	 */
	protected void initUserDeptInfo(User user) {
		OrganService organService = (OrganService) getBean("organService");
		Department dept = organService.getDepartmentForUser(user.getPkid());
		if (dept != null) {
			user.setDepartmentId(dept.getPkid());
			user.setDepartmentName(user.getDepartmentName());
		}
	}

	public void commonQuery(String sqlId, String params,
			HttpServletRequest request) {
		try {
			String actionPackName = this.getClass().getPackage().getName();
			Class commonManageSQLClass = Class.forName(actionPackName
					.substring(0, actionPackName.lastIndexOf("web.action"))
					+ "sqlmanager."
					+ getSimpleName(this.getClass()).substring(
							0,
							getSimpleName(this.getClass())
									.lastIndexOf("Action")) + "SQLManager");
			try {
				Method method = commonManageSQLClass.getMethod(
						"getCommonQueryParams", new Class[] { String.class,
								String.class });
				if (sqlId == null || "".equals(sqlId))
					return;
				String[] sqlIds = sqlId.split(",");
				String[] paramsArr = new String[0];
				if (params != null)
					paramsArr = params.split(";");
				for (int i = 0; i < sqlIds.length; i++) {
					Object param = null;
					if (i < paramsArr.length)
						param = paramsArr[i];
					CommonQueryParams queryParams = (CommonQueryParams) method
							.invoke(commonManageSQLClass, new Object[] {
									sqlIds[i], param });
					CommonQueryService commonQueryService = (CommonQueryService) BeanUtilServlet
							.getBean("commonQueryService");
					request.setAttribute(sqlIds[i].replace('.', '_'),
							commonQueryService.commonQuery(queryParams.sql,
									queryParams.entities,
									queryParams.scalaries, queryParams.values));
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getSimpleName(Class clazz) {
		if (clazz != null) {
			String className = clazz.getName();
			return className.substring(className.lastIndexOf(".") + 1);
		}
		return null;
	}

	public SysLogService getSysLogService() {
		return (SysLogService) getBean("syslogService");
	}
	
	protected ReportViewService getReportViewService() {
		ReportViewService mgr = (ReportViewService) getBean("reportViewService");
		return mgr;
	}
}
