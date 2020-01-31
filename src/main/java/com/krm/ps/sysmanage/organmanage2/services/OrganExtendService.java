package com.krm.ps.sysmanage.organmanage2.services;

import java.util.List;

public interface OrganExtendService {
	/**
	 * 机构信息(扩展)提供类接口
	 *直接连接数据库取机构信息
	 */
	/**
	 * 获得同级机构列表
	 * @param orgID 机构编码
	 * @return List
	 */
	public   List getSameLevelOrgList(String orgID,int organSystemId,String date);
}
