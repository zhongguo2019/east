package com.krm.ps.sysmanage.usermanage.web.form;

import org.apache.struts.action.ActionForm;
/**
 * 用户报表对照Form
 * @struts.form name="userReportForm"
 */
public class UserReportForm extends ActionForm {
	
	private static final long serialVersionUID = 9543314L;

	private Long userid;
	
	private String userName;
	
	private String type;
	
	private String isAll="-1";	
	
	public UserReportForm(){
		
	}

	/**
	 * 
	 * @return 全部报表
	 */
	public String getIsAll() {
		return isAll;
	}

	/**
	 * 
	 * @param isAll 全部报表
	 */
	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}

	/**
	 * 
	 * @return 类型
	 */ 
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type 类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return 用户编码
	 */
	public Long getUserid() {
		return userid;
	}

	/**
	 * 
	 * @param userid 用户编码
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}

	/**
	 * 
	 * @return 用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName 用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
		
	
}