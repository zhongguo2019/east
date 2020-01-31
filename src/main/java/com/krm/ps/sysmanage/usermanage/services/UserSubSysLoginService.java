package com.krm.ps.sysmanage.usermanage.services;

import java.util.List;

public interface UserSubSysLoginService {
	
	/**
	 * 验证用户与子系统是否有对应关系
	 */
	public List checkUserSysSub(String subsys_Flag);
	
	/**
	 * 判断用户是否有权限进入子系统
	 */
	public List getGPuser(String userId,String gpId);
	
}
