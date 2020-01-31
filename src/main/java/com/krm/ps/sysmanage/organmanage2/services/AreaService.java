package com.krm.ps.sysmanage.organmanage2.services;

import java.util.List;

import com.krm.ps.framework.common.sort.service.SortService;
import com.krm.ps.sysmanage.organmanage2.vo.Area;

public interface AreaService extends SortService{
	/**
	 * 根据organCode的机构名称
	 */
	public List getOrganName(String organCode);
	/**
	 * 根据地区ID取得所在地区的机构数
	 * @param=areaId
	 */
	public String getOrganTreeXMLByArea(String areaId);

	/**
	 * 取得地区树的xml,用于树控件显示
	 * 
	 * @param topAreaId
	 *            需要的顶层地区id
	 * @param date
	 *            日期,保证取出的是在有效期内的地区
	 * @return
	 */
	public String getAreaTreeXML(String topAreaId,String date);
	
	public String getAreaTreeXML_temp(String topAreaId,String date);


	/**
	 * 根据机构取得该机构所在地区作为顶层节点的地区树的xml,用于树控件显示
	 * 
	 * @param organId
	 *            需要的顶层地区id
	 * @param date
	 *            日期,保证取出的是在有效期内的地区
	 * @return
	 */
	public String getAreaTreeXMLByOrgan(int organId,String date);
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
	 * 根据用户Id得到用户所在地区对象
	 * @param userId
	 * 			用户Id
	 * @return
	 */
	public Area getAreaByUser(int userId);
	
	/**
	 * 根据用户Id得到用户所在地区编码
	 * @param userId
	 * 			用户Id
	 * @return
	 */
	public String getAreaCodeByUser(int userId);
	
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
	 * 保存地区信息
	 * @param area
	 * 			地区VO对象
	 */
	public void saveArea(String oldCode, Area area);
	
	/**
	 * 删除地区信息
	 * @param areaId
	 * 			地区Id
	 */
	public void deleteArea(String areaCode);
	
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
	 * 得到所有下级地区(不包含本级)
	 * @param areaCode
	 * 			地区编码
	 * @return
	 */
	public List getAllSubAreas(String areaCode);
	public List getAllSubOrgans(int organTreeIdxid, String organId, String dataDate);
	
}
