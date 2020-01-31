package com.krm.ps.sysmanage.groupmanage.web.form;

import org.apache.struts.action.ActionForm;
/**
 * 
 * @struts.form name="groupReportForm"
 */
public class GroupReportForm extends ActionForm {
	

	private Long userid;
	
	private String userName;
	
	private Long dicid;
	
	private String dicname;
	
	private String type;
	
	private String isAll="-1";	
	
	private Integer dicidCount;
		
	public Integer getDicidCount() {
		return dicidCount;
	}

	public void setDicidCount(Integer dicidCount) {
		this.dicidCount = dicidCount;
	}

	public GroupReportForm(){
		
	}

	public String getIsAll() {
		return isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getDicid() {
		return dicid;
	}

	public void setDicid(Long dicid) {
		this.dicid = dicid;
	}

	public String getDicname() {
		return dicname;
	}

	public void setDicname(String dicname) {
		this.dicname = dicname;
	}
		
	
}