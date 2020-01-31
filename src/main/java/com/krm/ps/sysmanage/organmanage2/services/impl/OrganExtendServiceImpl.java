package com.krm.ps.sysmanage.organmanage2.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;
import com.krm.ps.framework.common.web.action.BaseAction;

import  com.krm.ps.sysmanage.organmanage2.services.OrganExtendService;
import com.krm.ps.sysmanage.organmanage2.dao.OrganExtendDAO;
import com.krm.ps.sysmanage.usermanage.vo.User;
public class  OrganExtendServiceImpl  implements OrganExtendService{
	
 
	private  OrganExtendDAO  organExtendDAO;
	public void setOrganExtendDAO(OrganExtendDAO organExtendDAO) {
		this.organExtendDAO =organExtendDAO;
	}
	public OrganExtendDAO getOrganExtendDAO() {
		return organExtendDAO;
	} 
//////获得同级机构的列表
	public List getSameLevelOrgList(String orgID,int organSystemId,String date){
		
		List list=null;
		 
		list=organExtendDAO.getSameLevelOrgList(orgID,organSystemId,date);
		return  list;
	}

}
