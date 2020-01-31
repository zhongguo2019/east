package com.krm.ps.sysmanage.usermanage.bo;

public class LoginUserInfo {
	
	private String sessionId;
	private String userId;
	private String userAddr;
	private String loginTime;
	private String organName;
	private String userName;
	
	public String getSessionId() {return sessionId;}
	public void setSessionId(String sessionId) {this.sessionId = sessionId;}
	
	public String getUserAddr() {return userAddr;}
	public void setUserAddr(String userAddr) {this.userAddr = userAddr;}
	
	public String getUserId() {return userId;}
	public void setUserId(String userId) {this.userId = userId;}
	
	public String getLoginTime() {return loginTime;}
	public void setLoginTime(String loginTime) {this.loginTime = loginTime;}
	
	public String getOrganName() {return organName;}
	public void setOrganName(String organName) {this.organName = organName;}
	
	public String getUserName() {return userName;}
	public void setUserName(String userName) {this.userName = userName;}
	
	
	//TODO 控制显示在线人数唯一性
//	public boolean equals(Object o) {
//        if (this == o){return true;}
//         else if(userId.equals(((LoginUserInfo)o).getUserId())){return true;}
//         else return false;
//    }
//	
//	public int hashCode(){
//		return 0;
//	}
}
