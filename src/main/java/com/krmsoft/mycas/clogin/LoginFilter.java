package com.krmsoft.mycas.clogin;

import com.krmsoft.mycas.util.DESPlus;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class LoginFilter
  implements Filter
{
  public static final String KRMVID = "$Id: Cache.java 130 2015-01-08 09:20:21Z lyg $";
  private static final Logger logger = Logger.getLogger(LoginFilter.class);

  private static String PARAM_USERNAME = "username";

  private static String PARAM_PASSWORD = "password";

  private static String PARAM_DATE = "date";

  private static String PARAM_LOGIN_URL = "loginurl";

  private static String PARAM_LOGOUT_PATH = "logoutPath";

  private static String PARAM_SESSIONID = "ksessionid";

  private static String PARAM_LOGIN_CLASS = "loginClass";

  private static String PARAM_SECURITY_KEY = "securitykey";

  private static String PARAM_SECURITY_KEY_SPLIT = "securitykeysplit";

  private Map<String, String> parammap = new HashMap();

  private Login login = null;

  private DESPlus des = null;

  public void destroy()
  {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
    throws IOException, ServletException
  {
    HttpServletRequest req = (HttpServletRequest)request;
    HttpServletResponse res = (HttpServletResponse)response;
    try
    {
      request.setAttribute("logouturl", this.parammap.get("logoutPath"));
      request.setAttribute((String)this.parammap.get(PARAM_SESSIONID), request.getParameter((String)this.parammap.get(PARAM_SESSIONID)));
      if (!this.login.checkLogin(req, res)) {
        buildLoginParam(req);
        this.login.doLogin(req, res);
        logger.info("登录成功!");
      }
      if (!this.login.checkLogout(req, res))
      {
        this.login.logout(req, res);
        logger.info("注销成功!");
      }
    } catch (Exception e) {
      logger.error("LoginFilter:doFilter()");
      res.sendRedirect((String)this.parammap.get(PARAM_LOGIN_URL) + "?urs=e" + e.getMessage());
      return;
    }

    filterChain.doFilter(req, res);
  }

  public void init(FilterConfig conf)
    throws ServletException
  {
    Enumeration initParams = conf.getInitParameterNames();

    while (initParams.hasMoreElements()) {
      String key = (String)initParams.nextElement();
      String value = conf.getInitParameter(key);
      this.parammap.put(key, value);
    }
    String className = (String)this.parammap.get(PARAM_LOGIN_CLASS);
    try {
      this.login = ((Login)Class.forName(className).newInstance());
    } catch (Exception e) {
      logger.error("初始化失败!PARAM_LOGIN_CLASS=" + className, e);
    }
    try {
      this.des = new DESPlus((String)this.parammap.get(PARAM_SECURITY_KEY));
    } catch (Exception e1) {
      logger.error("初始化DESPlus失败!param:" + PARAM_SECURITY_KEY_SPLIT, e1);
    }
  }

  private void buildLoginParam(ServletRequest request)
    throws Exception
  {
    String sessionid = this.des.decrypt(request.getParameter((String)this.parammap.get(PARAM_SESSIONID)));
    String[] arrayList = sessionid.split((String)this.parammap.get(PARAM_SECURITY_KEY_SPLIT));
    String name = arrayList[0];
    String pwd = arrayList[1];
    String date = arrayList[2].substring(0, 10);
    request.setAttribute((String)this.parammap.get(PARAM_USERNAME), name);
    request.setAttribute((String)this.parammap.get(PARAM_PASSWORD), pwd);
    request.setAttribute((String)this.parammap.get(PARAM_DATE), date);

    logger.info("buildLoginParam:" + name + ";" + pwd + ";" + date);
  }
}