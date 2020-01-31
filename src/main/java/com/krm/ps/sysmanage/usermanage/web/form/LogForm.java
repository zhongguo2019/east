package com.krm.ps.sysmanage.usermanage.web.form;

import org.apache.struts.action.ActionForm;

/**
 * 用户登录Form
 * @struts.form name="logForm"
 */
public class LogForm  extends  ActionForm {
    /**
	 * <code></code>
	 */
	private static final long serialVersionUID = 776655544L;
	private String logonname;
	private String password;
	private String logindate;
	
	public LogForm(){
		
	}
	
	/**
	 * 
	 * @return 登录用户名
	 */
	public String getLogonname() {
		return logonname;
	}	
	/**
	 * 
	 * @param login_name 登录用户名
	 */
	public void setLogonname(String login_name) {
		this.logonname = login_name;
	}	
	/**
	 * 
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}	
	/**
	 * 
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}	
	/**
	 * 
	 * @return 登录日期
	 */
	public String getLogindate() {
		return logindate;
	}	
	/**
	 * 
	 * @param logindate 登录日期
	 */
	public void setLogindate(String logindate) {
		this.logindate = logindate;
	}
}
