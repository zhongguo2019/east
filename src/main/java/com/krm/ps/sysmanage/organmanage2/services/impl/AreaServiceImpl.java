package com.krm.ps.sysmanage.organmanage2.services.impl;


import java.util.ArrayList;
import java.util.List;

import com.krm.ps.sysmanage.organmanage.dao.OrganInfoDAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.dao.AreaDAO;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.organmanage2.vo.Area;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.TreeNodeAttributeReader;

public class AreaServiceImpl implements AreaService{
	
	private AreaDAO areaDAO;
	
	private OrganInfoDAO organInfoDAO;
	
	public void setAreaDAO(AreaDAO areaDAO) {
		this.areaDAO = areaDAO;
	}
	
	public void setOrganInfoDAO(OrganInfoDAO organInfoDAO){
		this.organInfoDAO = organInfoDAO;
	}
	
	private static TreeNodeAttributeReader inar=new TreeNodeAttributeReader() {

		public String getId(Object area) {
			return ((Area)area).getCode();
		}
		public String getName(Object area) {
			return ((Area)area).getFullName();
		}	
		public String getOrder(Object area) {
			return ""+((Area)area).getShowOrder();
		}
		public String getParent(Object area) {
			return ((Area)area).getSuperCode();
		}
	};
	private static TreeNodeAttributeReader organinar=new TreeNodeAttributeReader() {
		
		public String getId(Object organ) {
			return ((OrganInfo)organ).getCode();
		}
		public String getName(Object organ) {
			return ((OrganInfo)organ).getShort_name();
		}	
		public String getOrder(Object organ) {
			return ""+((OrganInfo)organ).getPkid();
		}
		public String getParent(Object organ) {
			if(((OrganInfo)organ).getCode().equals(((OrganInfo)organ).getSuper_id()))
			{
				return ((OrganInfo)organ).getSuper_id()+"a";//�Լ�����parentid
			}
			else
			{
				return ((OrganInfo)organ).getSuper_id();
			}
		}
	};
	
	/**
	 * 根据organCode的机构名称
	 */
	public List getOrganName(String organCode)
	{
		return areaDAO.getOrganName(organCode);
	}
	
	/**
	 * 根据地区ID取得所在地区的机构数
	 * @param=areaId
	 */
	public String getOrganTreeXMLByArea(String areaId)
	{
		List organList = areaDAO.getOrgansByArea(areaId);
		String xml=ConvertUtil.convertListToAdoXml(organList,organinar);
		return xml;
	}
	
	/**
	 * 取得地区树的xml,用于树控件显示
	 * 
	 * @param topAreaId
	 *            需要的顶层地区id
	 * @param date
	 *            日期,保证取出的是在有效期内的地区
	 * @return
	 */
	public String getAreaTreeXML(String topAreaId, String date) {
		List areaList = areaDAO.getAreasByArea(topAreaId, date);
//		String xml = ConvertUtil.convertListToAdoXml(areaList,new String[]{"code",
//				"fullName","showOrder","superCode"});
		String xml=ConvertUtil.convertListToAdoXml(areaList,inar);
		return xml;
	}
	
	public String getAreaTreeXML_temp(String topAreaId, String date) {
		String xml = areaDAO.getAreasByArea_temp(topAreaId, date);
//		String xml = ConvertUtil.convertListToAdoXml(areaList,new String[]{"code",
//				"fullName","showOrder","superCode"});
		return xml;
	}

	
	/**
	 * 根据机构取得该机构所在地区作为顶层节点的地区树的xml,用于树控件显示
	 * 
	 * @param organId
	 *            需要的顶层地区id
	 * @param date
	 *            日期,保证取出的是在有效期内的地区
	 * @return
	 */
	public String getAreaTreeXMLByOrgan(int organId, String date) {
		
		return null;
	}
	

	/**
	 * 得到指定地区下的机构列表
	 * 
	 * @return {@link OrganInfo}对象列表
	 */
	public List getOrgansByArea(String areaId) {
		return areaDAO.getOrgansByArea(areaId);
	}
	/**
	 * 得到指定地区下的机构列表
	 * 
	 * @return {@link OrganInfo}对象列表
	 */
	public List getOrgansByArea(String areaId,String orgcode,String shortNmae) {
		return areaDAO.getOrgansByArea(areaId,orgcode,shortNmae);
	}

	
	/**
	 * 根据用户Id得到用户所在地区对象
	 * @param userId
	 * 			用户Id
	 * @return
	 */
	public Area getAreaByUser(int userId) {
		return areaDAO.getAreaByUserId(userId);
	}

	/**
	 * 根据用户Id得到用户所在地区编码
	 * @param userId
	 * 			用户Id
	 * @return
	 */
	public String getAreaCodeByUser(int userId) {
		return areaDAO.getAreaCodeByUserId(userId);
	}


	/**
	 * 根据机构Id得到该机构所属地区编码
	 * @param organId
	 * 			机构Id
	 * @return
	 */
	public String getAreaCodeByOrganId(int organId) {
		return areaDAO.getAreaCodeByOrganId(organId);
	}
	
	/**
	 * 根据机构Id得到该机构所属地区对象
	 * @param organId
	 * 			机构Id
	 * @return
	 */
	public Area getAreaByOrganId(int organId) {
		return areaDAO.getAreaByOrganId(organId);
	}
	
	/**
	 * 得到下级地区
	 * @param areaId
	 * 		地区Id
	 * @return
	 */
	public List getSubArea(String areaId) {
		return areaDAO.getSubArea(areaId);
	}
	
	/**
	 * 根据地区Id得到地区下机构列表
	 * @param areaId
	 * 			地区Id串
	 * @return
	 */
	public List getOrgansByAreas(String areaId) {
		return areaDAO.getOrgansByAreas(areaId);
	}
	
	/**
	 * 得到指定地区下的地区列表
	 * @param areaId
	 * 			地区Id
	 * @return {@ like Area}对象列表
	 */
	public List getAreasByArea(String areaId, String date) {
		return areaDAO.getAreasByArea(areaId,date);
	}
	
	/**
	 * 返回顶层地区
	 */
	public Area getTopArea(){
		return areaDAO.getTopArea();
	}
	
	/**
	 * 设置默认地区
	 */
	public void setAreaDefult(String areaCode){
		areaDAO.setAreaDefult(areaCode);
	}
	
	/**
	 * 根据当前地区编码得到地区对象
	 * @param areaCode
	 * 			地区编码
	 * @return
	 */
	public Area getAreaByareaCode(String areaCode){
		return areaDAO.getAreaByareaCode(areaCode);
	}
	
	/**
	 * 保存地区信息
	 * @param area
	 * 			地区VO对象
	 */
	public void saveArea(String oldCode, Area area){
		int id = area.getPkid();
		//if(id == 0){
			int maxOrder = areaDAO.getMaxOrder(area.getSuperCode());
			System.out.println(maxOrder);
			area.setShowOrder(maxOrder+1);
			areaDAO.saveObject(area);
		//}else{
			//updateArea(oldCode, area);
		//}
	}
	
	/**
	 * 更新地区信息
	 * @param area
	 * 			地区VO对象
	 */
	private void updateArea(String oldCode, Area area){
		String code = area.getCode();
		oldCode = area.getSuperCode().replaceAll("(00)+$","") + oldCode;
		while(oldCode.length() < 10){
			oldCode += "00";
		}
		if(oldCode.equals(code)){
			areaDAO.updateArea(area);
		}else{
			areaDAO.updateArea(area);
			organInfoDAO.updateOrganSuperId(oldCode, code);
		}
	}
	
	/**
	 * 删除地区信息
	 * @param areaId
	 * 			地区Id
	 */
	public void deleteArea(String areaCode){
		areaDAO.deleteArea(areaCode);
	}
	
	/**
	 * 取得上级地区
	 * @param areaCode
	 * @return
	 */
	public Area getSuperArea(String areaCode){
		return null;
	}
	
	/**
	 * 根据地区Id得到地区信息
	 * @param areaId
	 * 			地区Id
	 * @return
	 */
	public Area getAreaById(int areaId){
		return areaDAO.getAreaById(areaId);
	}
	
	/**
	 * 得到所有下级地区(不包含本级)
	 * @param areaCode
	 * 			地区编码
	 * @return
	 */
	public List getAllSubAreas(String areaCode){
		return areaDAO.getAllSubAreas(areaCode);
	}
	
	public void sort(String list) {
		if(null != list){
			Area type = null;
			String[] orders = list.split(",");
			for(int i = 0; i < orders.length; i++){
				Object o = areaDAO.getObject(Area.class,new Integer(orders[i]));
				if(null != o){
					type = (Area)o;
					type.setShowOrder(i);
					areaDAO.saveObject(type);
				}
			}
		}
	}

	@Override
	public List getAllSubOrgans(int organTreeIdxid, String organId, String dataDate) {
		OrganInfo oi = areaDAO.getOrganByCode(organId);
		if(oi!=null){
			int pkid = oi.getPkid().intValue();
			return getAllSubOrgans(organTreeIdxid, pkid, dataDate);
		}else
			return new ArrayList();
	}
	public List getAllSubOrgans(int organSystemId, int topOrgan, String date) {
		return areaDAO.getAllSubOrgans(organSystemId, topOrgan, date);
	}
}
