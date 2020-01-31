package com.krm.ps.framework.common.web.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.krm.ps.sysmanage.usermanage.bo.LoginUserInfo;
import com.krm.ps.util.Constants;

/**
 * @web.listener
 */
public class StartupListener implements ServletContextListener,HttpSessionAttributeListener,HttpSessionListener
{

	private static final Log log = LogFactory.getLog(StartupListener.class);
	private ServletContext servletContext;
	private Map users;
	//private Set users;
	private Map userInfo;
		
	public void contextInitialized(ServletContextEvent event)
	{
		servletContext = event.getServletContext();
		servletContext.setAttribute(Constants.USERS_COUNT, "0");
		if (log.isDebugEnabled()){
			log.debug("initializing context...");
		}

		try{
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			event.getServletContext().setAttribute(Constants.SCHEDULER_KEY,
				scheduler);
		}
		catch (SchedulerException e){
			if (log.isDebugEnabled()){
				log.debug(e.getMessage());
			}
		}
	}

	public void contextDestroyed(ServletContextEvent event){
		Scheduler scheduler = (Scheduler) event.getServletContext()
			.getAttribute(Constants.SCHEDULER_KEY);
		if (scheduler != null){
			try{
				scheduler.shutdown(true);
			}
			catch (SchedulerException e){
				if (log.isDebugEnabled()){
					log.debug(e.getMessage());
				}
			}
		}
		servletContext = null;
	}
	
//	HttpSessionListener-->AddedSession()
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		//如果Session属性名为user,则添加用户
		if(arg0.getName().equals("loginUser")) {addUser(arg0.getValue());}
		if(userInfo == null) {userInfo = new HashMap();}
		//如果Session实例为LoginUserInfo,则将Session添加到Map中.再将Map保存到servletContext中
		if (arg0.getValue() instanceof LoginUserInfo) {
			userInfo.put(((LoginUserInfo)arg0.getValue()).getSessionId(),arg0.getSession());
		}
        servletContext.setAttribute(Constants.ADDED_SESSION, userInfo);
	}
	
//	HttpSessionListener-->RemovedSession()
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		if(arg0.getName().equals("loginUser")) {removeUser(arg0.getValue());}
//		userInfo = (Map)servletContext.getAttribute(Constants.ADDED_SESSION);
//		session.remove(((LoginUserInfo)arg0.getValue()).getSessionId());
		servletContext.setAttribute(Constants.ADDED_SESSION, userInfo);
	}
	
//	HttpSessionListener-->ReplacedSession()
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
	}
	
	//添加用户
	private void addUser(Object user) {
        if (user instanceof LoginUserInfo) {
            if(users == null){
                //users = new HashSet();
                users=new HashMap();
            }
           // users.add(user);
            users.put(((LoginUserInfo) user).getSessionId(), user);
            servletContext.setAttribute(Constants.ADDED_USER, users);
           // System.out.println("查看是users里面的值是多少？？？？？？？？？？？？？？？？？？？？？？++++++++++++++++++++"+users.size());
            servletContext.setAttribute(Constants.USERS_COUNT, String.valueOf(users.size()));
        }
//        increment();
	}
	
	//删除用户
	private void removeUser(Object user) {
		if (user instanceof LoginUserInfo) {
			//users = (Set)servletContext.getAttribute(Constants.ADDED_USER);
			users=(Map)servletContext.getAttribute(Constants.ADDED_USER);
			//users.remove(user);
			 users.remove(((LoginUserInfo) user).getSessionId());
			servletContext.setAttribute(Constants.ADDED_USER, users);
			servletContext.setAttribute(Constants.USERS_COUNT, String.valueOf(users.size()));
		}
		System.out.println(users.size());
		// TODO 删除用户和添加用户有同样的问题.
//		decrement();
	}
	/**
	//在线人数加一
	private void increment() {
		int count = Integer.parseInt((String)servletContext.getAttribute(Constants.USERS_COUNT)) + 1;
		servletContext.setAttribute(Constants.USERS_COUNT, String.valueOf(count));
		if (log.isDebugEnabled()) {
			log.debug("User Count: " + count);
		}
	}
	
	//在线人数减一
	private void decrement() {
		int count = Integer.parseInt((String)servletContext.getAttribute(Constants.USERS_COUNT)) - 1;
		if (count < 0) {count = 0;}
		servletContext.setAttribute(Constants.USERS_COUNT, String.valueOf(count));
		if (log.isDebugEnabled()) {
			log.debug("User Count: " + count);
		}
	}
	**/

	public void sessionCreated(HttpSessionEvent arg0)
	{
	}

	public void sessionDestroyed(HttpSessionEvent arg0)
	{
		arg0.getSession().removeAttribute("loginUser");
	}
}
