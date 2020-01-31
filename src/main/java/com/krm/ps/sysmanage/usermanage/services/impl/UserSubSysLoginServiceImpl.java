package com.krm.ps.sysmanage.usermanage.services.impl;

import java.util.List;

import com.krm.ps.sysmanage.usermanage.dao.UserRoleDAO;
import com.krm.ps.sysmanage.usermanage.services.UserSubSysLoginService;
import com.krm.ps.sysmanage.usermanage.vo.PlatSysGp;
import com.krm.ps.util.FuncConfig;

public class UserSubSysLoginServiceImpl implements UserSubSysLoginService{

	private UserRoleDAO urDAO;
	
	public void setUserRoleDAO(UserRoleDAO urDAO){
		this.urDAO = urDAO;
	}
	
	
	
	/**
	 * 验证用户与子系统是否有对应关系
	 */
	public List checkUserSysSub(String subsys_Flag) {
		List sysList = urDAO.getUserSys(subsys_Flag);
		return sysList;
	}
	
	/**
	 * 判断用户能否进入子系统
	 */
	public List getGPuser(String userId, String gpId) {
		return urDAO.getGPuser(userId, gpId);
	}
	
	
}
