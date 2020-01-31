package com.krm.ps.sysmanage.organmanage.services.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.krm.ps.sysmanage.organmanage.dao.OrganContrastDAO;
import com.krm.ps.sysmanage.organmanage.dao.OrganInfoDAO;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.Department;
import com.krm.ps.sysmanage.organmanage.vo.OrganContrast;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage.vo.UserDeptIdx;
import com.krm.ps.sysmanage.organmanage2.dao.AreaDAO;
import com.krm.ps.sysmanage.organmanage2.vo.Area;
import com.krm.ps.sysmanage.organmanage2.vo.OrganSystemInfo;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;
import com.krm.ps.sysmanage.usermanage.dao.UserDAO;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.DBDialect;
 


public class OrganServiceImpl implements OrganService{
 
	private OrganInfoDAO odao;
	
	private UserDAO udao;
	
	private OrganContrastDAO cdao;
	
	private AreaDAO areaDAO;
	
	
 

	public OrganInfoDAO getOdao() {
		return odao;
	}

	public void setOdao(OrganInfoDAO odao) {
		this.odao = odao;
	}

	public UserDAO getUdao() {
		return udao;
	}

	public void setUdao(UserDAO udao) {
		this.udao = udao;
	}

	public OrganContrastDAO getCdao() {
		return cdao;
	}

	public void setCdao(OrganContrastDAO cdao) {
		this.cdao = cdao;
	}

	public AreaDAO getAreaDAO() {
		return areaDAO;
	}

	public void setAreaDAO(AreaDAO areaDAO){
		this.areaDAO = areaDAO;
	}
	
	public void setOrganInfoDAO(OrganInfoDAO dao){
		this.odao=dao;
	}
	
	public void setUserDAO(UserDAO dao){
		this.udao=dao;
	}
	
	public void setOrganContrastDAO(OrganContrastDAO dao){
		this.cdao=dao;
	}
    
	//得到全部机构信息
	public List getOrgans(String date){
		return odao.getOrgans(date);
	}
	
	//删除机构信息对象
	public void removeOrgan(Long pkid){
		OrganInfo organ = getOrgan(pkid);
		udao.removeUserByOrgan(organ.getCode());
		odao.removeOrgan(pkid);
	}

	//删除所有当前机构及其下级机构对照关系
	public void removeOrganByOrgan(String organid,String date){
		//List organs=getJuniorOrgans(organid,date);
		List organs=getJuniorOrgans(organid);//不看起止日期，wsx 10-22
		for(int i=0;i<organs.size();i++){
			OrganInfo organ=(OrganInfo)organs.get(i);
			List contrasts=cdao.getContrastById(organ.getCode());
			if(contrasts!=null){
				for(int j=0;j<contrasts.size();j++){
				OrganContrast contrast=(OrganContrast)contrasts.get(j);
				cdao.removeContrast(contrast.getPkid());
				}
			}
		}
	}
	
	public List getAllChildOrgans(String organId, String date){
		return odao.getAllChildOrgans(organId, date);
	}
	
	//根据机构ID得到该机构的所有下级机构列表
	public List getJuniorOrgans(String organid,String date){
		return odao.getJuniorOrgans(organid,date);
	}
	
	// 得到当前机构的下级机构列表（不带当前机构）
	public List getJuniorOrgansOnly(String organid,String date){
		return odao.getJuniorOrgansOnly(organid,date);
	}
	
	//得到当前机构的下级机构中所包含的机构类型
	public List getJuniorOrganTypes(String organid,String date){
		return odao.getJuniorOrganTypes(organid,date);
	}
	 
	//根据机构编码得到该机构对象
	public OrganInfo getOrganByCode(String code) {
	    return odao.getOrganByCode(code);
	}
	
	 //根据对照关系表的PKID得到对照关系对象
	public OrganContrast getContrast(Long pkid){
		return cdao.getContrast(pkid);
	}
	
	//根据机构ID得到所有该机构的对照关系列表
	public List getContrastById(String organid){
		return cdao.getContrastById(organid);
	}
	
	//根据机构ID得到该机构及其下级机构的所有机构对照关系
	public List getJuniorOrganContrasts(String organid,String date){
		return cdao.getJuniorOrganContrasts(organid,date);   
	   }
	
	//根据ID得到机构信息对象
	public OrganInfo getOrgan(Long pkid){
		Object o = odao.getObject(OrganInfo.class,pkid);
		if(null!=o){
			OrganInfo organ = (OrganInfo)o;
			return organ;
		}
		return null;
	}
	
	//保存机构对照关系信息
	public int saveContrast(OrganContrast contrast){
		contrast.setOuter_org_code(contrast.getOuter_org_code().trim());
		cdao.saveObject(contrast);
		return OrganService.SAVEOK;
	}
	
	//保存机构信息
	public int saveOrgan(OrganInfo organ,String codeBeforeChange){
		
	    if (odao.codeRepeat(organ.getPkid(),organ.getCode())) {
			return OrganService.CODE_REPEAT;		
		}
	    organ.setCode(organ.getCode().trim());
		Integer show =odao.getMaxOrganOrder();
		if (codeBeforeChange == null) {
			organ.setOrganOrder((new Integer(show.intValue()+1)));
		}
		else{
			organ.setOrganOrder(odao.getOrganOrder(organ.getPkid()));
		}
		if (organ.getPkid()!=null){ //新增机构时不关联机构树
			OrganTreeNode node = odao.getNode(organ.getPkid().intValue());
			if (node!=null&&!organ.getShort_name().equals(node.getAliasName())){
				node.setAliasName(organ.getShort_name());
				odao.saveObject(node);
			}
		}

		odao.saveObject(organ);



		
		//若为修改机构信息操作则判断机构编码是否发生变化,若发生变化则更新默认管理员的相关信息
		if(codeBeforeChange != null && !organ.getCode().equals(codeBeforeChange)){
			odao.updateOrganSuperId(codeBeforeChange,organ.getCode());//先看看有没有必要修改
			List users = udao.getUsers(codeBeforeChange);
			if(users!=null){
				for(int i=0;i<users.size();i++){
			        User user =(User)users.get(i);
			        user.setOrganId(organ.getCode());
			        if(user.getName().equals(codeBeforeChange)){
			           user.setLogonName(organ.getCode());
			           user.setName(organ.getCode());
			        }
			        udao.saveObject(user);
				}
			}
		}
	
		//若机构编码发生变化则更新机构对照信息
		if (codeBeforeChange != null) {
			if (!organ.getCode().equals(codeBeforeChange)) {
				List contrastlist = cdao.getContrastById(codeBeforeChange);
				if (contrastlist != null) {
					for (int i = 0; i < contrastlist.size(); i++) {
						OrganContrast contrast = (OrganContrast) contrastlist
								.get(i);
						contrast.setNative_org_id(organ.getCode());
						cdao.saveObject(contrast);
					}
				}
			}
		}
		return OrganService.SAVEOK;
	}

	//删除所有当前机构的下级机构对照关系
	public void removeOrganByOrgan(List areaList){
		String areaId = "";
		for(Iterator itr = areaList.iterator();itr.hasNext();){
			Area a = (Area)itr.next();
			if(areaId.equals(""))
				areaId = "'"+a.getCode()+"'";
			else
				areaId += ","+"'"+a.getCode()+"'";
		}
		List organList = areaDAO.getOrgansByAreas(areaId);
		for(Iterator itr = organList.iterator();itr.hasNext();){
			OrganInfo organ = (OrganInfo)itr.next();
			cdao.removeContrastByOrgan(organ.getCode());
//			List contrast = cdao.getContrastById(organ.getCode());
//			if(contrast != null){
//				for(Iterator iter = contrast.iterator();iter.hasNext();){
//					OrganContrast cont = (OrganContrast)iter.next();
//					cdao.removeContrast(cont.getPkid());
//				}
//			}
		}


	}
	
	//根据机构ID得到该机构的所有下级机构列表
	public List getJuniorOrgans(String organid){
		return odao.getJuniorOrgans(organid);
	}
	
	//2006.9.20
	public List getSelfJuniorOrgans(String organid){
		return odao.getSelfJuniorOrgans(organid);
	}
	
	//2007.1.30
	public List getSelfJuniorOrgan(String organid){
		return odao.getSelfJuniorOrgan(organid);
	}
	
	// 得到当前机构的下级机构列表（不带当前机构）
	public List getJuniorOrgansOnly(String organid){
		return odao.getJuniorOrgansOnly(organid);
	}
	
	//得到当前机构的下级机构中所包含的机构类型
	public List getJuniorOrganTypes(String organid){
		return odao.getJuniorOrganTypes(organid);
	}

	public List getOrgans() {
		return odao.getOrgans();
	}
	
	public int getOrganCount(){
		return odao.getOrganCount();
	}

	/**
	 * 取得多级机构
	 * @param organCode
	 * @param level
	 * @return
	 */
	public List listSubOrgans(String[] organCode, int level) {// wsx 11-06
		
		return odao.listSubOrgans(organCode, level);
	}
	
	// 根据外部系统号及本机构编码得到对应的机构对照关系
	public OrganContrast getContrastbyOrgan(Long system_code,String code){
		return cdao.getContrastbyOrgan(system_code,code);
	}
	   
	// 根据外部系统号及机构对照编码得到本系统机构编码
	public OrganContrast getOrganbyContrast(Long system_code,String contrast) {// wsx 12-14
		return cdao.getOrganbyContrast(system_code,contrast);
	}

	public List getOrganContrast(String organsCode) {
		return cdao.getOrgansContrast(organsCode);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.services.OrganService#getAllOrgansInArea(java.lang.String, java.lang.String)
	 */
	public List getAllOrgansInArea(String organid, String date){
		return odao.getAllOrgansInArea(organid, date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.organmanage.services.OrganService#getOrgansInArea(java.lang.String,
	 *      java.lang.String)
	 */
	public List getSubOrgansInArea(String organid, String date){
		return odao.getSubOrgansInArea(organid, date);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.services.OrganService#isOrganUsed(java.lang.String)
	 */
	public boolean isOrganUsed(String organCode){
		return odao.isOrganUsed(organCode);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.services.OrganService#isOrganInTree(java.lang.String)
	 */
	public List isOrganInTree(String organCode){
		return odao.isOrganInTree(organCode);
	}
	
	public int updateRepidByRepid(Long newRepid, Long oldRepid){
		return cdao.updateRepidByRepid(newRepid, oldRepid);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.services.OrganService#getOrgMapByContrast(java.lang.Long)
	 */
	public Map getOrgMapByContrast(Long systemId)
	{
		Map map = new HashMap();
		List list = odao.getOrgMapByContrast(systemId);
		if(list.size() != 0){
			for(Iterator itr = list.iterator(); itr.hasNext();){
				Object [] obj = (Object[])itr.next();
				map.put((String)obj[0], (String)obj[1]);
			}
		}
		return map;
	}

	/**
	 * @see com.krm.slsint.organmanage.services.OrganService#getAllDepartmentList()
	 */
	public List getAllDepartmentList()
	{
		return odao.getObjects(Department.class);
	}

	/**
	 * @see com.krm.slsint.organmanage.services.OrganService#deleteUserDeptIdxList(java.util.List)
	 */
	public boolean deleteUserDeptIdxList(List userDeptIdxList)
	{
		return odao.deleteUserDeptIdxList(userDeptIdxList);
	}

	/**
	 * @see com.krm.slsint.organmanage.services.OrganService#saveUserDeptIdxList(java.util.List)
	 */
	public boolean saveUserDeptIdxList(List userDeptIdxList)
	{
		return odao.saveUserDeptIdxList(userDeptIdxList);
	}

	/**
	 * @see com.krm.slsint.organmanage.services.OrganService#getUserDeptIdx(java.lang.Long)
	 */
	public UserDeptIdx getUserDeptIdxForUser(Long userId)
	{
		return odao.getUserDeptIdxForUser(userId);
	}

	/**
	 * @see com.krm.slsint.organmanage.services.OrganService#getDepartmentForUser(java.lang.Long)
	 */
	public Department getDepartmentForUser(Long pkid)
	{
		return odao.getDepartmentForUser(pkid);
	}

	/**
	 * @see com.krm.slsint.organmanage.services.OrganService#getContrastMap(java.lang.Long)
	 */
	public Map getContrastMap(Long systemId)
	{
		Map map = new HashMap();
		List list = odao.getContrastMap(systemId);
		if(list.size() > 0) {
			for(Iterator itr = list.iterator(); itr.hasNext();) {
				OrganContrast oc = (OrganContrast)itr.next();
				map.put(oc.getOuter_org_code(), oc);
			}
		}
		return map;
	}
	
	public String getOrganContrastByOrgansysCode(Long system_code,String organId)
	{
		return odao.getOrganConstrat(system_code,organId);
	}
	public Map getOrganMap()
	{
		return odao.getOrganMap();
	}
	 
 
}
