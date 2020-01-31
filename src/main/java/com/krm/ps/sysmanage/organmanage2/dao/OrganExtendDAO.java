package com.krm.ps.sysmanage.organmanage2.dao;
import java.util.List;
public interface OrganExtendDAO {
	/**
	 * 根据机构编号获得同级机构列表
	 * @param orgID机构编号
	 * 			 organSystemId机构树索引
	 * @return 
	 */
	public  List getSameLevelOrgList(String orgID,int organSystemId,String date);

}
