package com.krm.ps.sysmanage.organmanage2.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.vo.Area;

public interface AreaDAO extends DAO{
	
	/**
	 * 根据organCode的机构名称
	 */
	public List getOrganName(String organCode);
	
	/**
	 * 根据用户Id查询该用户所属地区编码
	 * @param userId
	 * 			用户Id
	 * @return 
	 */
	public String getAreaCodeByUserId(int userId);
	/**
	 * 得到指定地区下的机构列表
	 * 
	 * @return {@link OrganInfo}对象列表
	 */
	public List getOrgansByArea(String areaId);
	/**
	 * 得到指定地区下的机构列表
	 * 
	 * @return {@link OrganInfo}对象列表
	 */
	public List getOrgansByArea(String areaId,String orgcode,String shortNmae);
	
	
	/**
	 * 根据地区Id得到地区下机构列表
	 * @param areaId
	 * 			地区Id串
	 * @return
	 */
	public List getOrgansByAreas(String areaId);
	/**
	 * 得到指定地区下的地区列表
	 * @param areaId
	 * 			地区Id
	 * @return {@ like Area}对象列表
	 */
	public List getAreasByArea(String areaId,String date);
	
	/**
	 * 根据机构Id得到该机构所属地区编码
	 * @param organId
	 * 			机构Id
	 * @return
	 */
	public String getAreaCodeByOrganId(int organId);
	/**
	 * 根据机构Id得到该机构所属地区对象
	 * @param organId
	 * 			机构Id
	 * @return
	 */
	public Area getAreaByOrganId(int organId);
	/**
	 * 得到下级地区
	 * @param areaId
	 * 		地区Id
	 * @return
	 */
	public List getSubArea(String areaId);
	/**
	 * 根据用户Id得到地区
	 * @param userId
	 * 			用户Id
	 * @return
	 */
	public Area getAreaByUserId(int userId);
	
	/**
	 * 返回顶层地区
	 */
	public Area getTopArea();
	

	/**
	 * 设置默认地区
	 */
	public void setAreaDefult(String areaCode);
	/**
	 * 根据当前地区编码得到地区对象
	 * @param areaCode
	 * 			地区编码
	 * @return
	 */
	public Area getAreaByareaCode(String areaCode);
	
	/**
	 * 得到最大Order编码
	 */
	public int getMaxOrder(String areaCode);
	
	/**
	 * 取得上级地区
	 * @param areaCode
	 * @return
	 */
	public Area getSuperArea(String areaCode);
	
	/**
	 * 根据地区Id得到地区信息
	 * @param areaId
	 * 			地区Id
	 * @return
	 */
	public Area getAreaById(int areaId);
	
	/**
	 * 删除地区信息
	 * @param areaId
	 * 			地区Id
	 */
	public void deleteArea(String areaCode);
	
	/**
	 * 更新地区信息
	 * @param area
	 * 		地区对象
	 */
	public void updateArea(Area area);
	
	/**
	 * 得到所有下级地区(不包含本级)
	 * @param areaCode
	 * 			地区编码
	 * @return
	 */
	public List getAllSubAreas(String areaCode);

	public String getAreasByArea_temp(String topAreaId, String date);

	public OrganInfo getOrganByCode(String organId);

	public List getAllSubOrgans(int organSystemId, int topOrgan, String date);
	
}
