package com.krm.ps.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeansUtil implements ApplicationContextAware {

	private static ApplicationContext appContext = null;

	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {
		BeansUtil.initApplicationContext(appContext);
	}

	public synchronized static void initApplicationContext(
			ApplicationContext newAppClontext) {
		if (appContext == null)
			appContext = newAppClontext;
	}

	public static Object getBean(String beanName) {
		return appContext.getBean(beanName);
	}

}
