package com.krm.ps.sysmanage.usermanage.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.dao.DictionaryDAO;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;

public class DictionaryServiceImpl implements DictionaryService {
	private DictionaryDAO udao;

	public void setDictionaryDAO(DictionaryDAO dao) {
		this.udao = dao;
	}
	/**
	 * 查询登陆地IP校验状态
	 * @return dicname
	 * @author LC
	 */
	public String getIpStatus()
	{
		return udao.getIpStatus();
	}

	// 获得字典一二级字典的所有列表
	public List getDics() {
		return udao.getDics();
	}
	
	//根据pid和cidid获取到列表    贡琳
	public List getDicss(int type, List list) {
		return udao.getDicss(type, list);
	}

	// 获得一个字典
	public Dictionary getDic(Long pkid) {
		Object o = udao.getObject(Dictionary.class, pkid);
		return (Dictionary) o;
	}

	// 保存一个字典
	public void saveDic(Dictionary dictionary) {
		udao.saveObject(dictionary);
	}

	// 删除一个字典
	public void removeDic(Long pkid) {
		udao.removeDic(pkid);
	}

	// 获得一个一级字典的列表
	public List getDictionarys() {
		return udao.getDics(0);
	}

	public Dictionary getDicname(Long pkid) {
		Object o = udao.getObject(Dictionary.class, pkid);
		return (Dictionary) o;
	}

	public List getEsystem() {
		return udao.getEsystem();
	}

	public List getALLsystem() {
		return udao.getALLsystem();
	}

	public List getUnitcode() {
		return udao.getUnits();
	}

//	public void setSave(RepData repData) {
//		udao.saveObject(repData);
//	}

	public List getOrgansort() {
		return udao.getDics(1001);
	}

	public List getOrganoperationsort() {
		return udao.getDics(1002);
	}

	public List getIsentityorgan() {
		return udao.getDics(1003);
	}

	public List getReportfrequency() {
		return udao.getDics(1004);
	}

	// /获得工作指引
	public List getJobdirect() {
		return udao.getDics(1006);
	}

	// 获得出处
	public List getDerivation() {
		return udao.getDics(1007);
	}

	// /获得汇总级别
	public List getCollectlevel() {
		return udao.getDics(1008);
	}

	// 获得生成级别
	public List getBuildlevel() {
		return udao.getDics(1009);
	}
	
    public List getDics(int type){
    	return udao.getDics(type);
    }

    public int nextDicid(int type){
    	return udao.maxDicid(type)+1;
    }

	public Dictionary getDictionary(int type,int dicid){
		return udao.getDictionary(type,dicid);
	}

	public List getStatExportLevel() {
		List list = udao.getStatExportLevel();
		List dicList = new ArrayList();
		for(Iterator itr = list.iterator();itr.hasNext();){
			Dictionary dic = new Dictionary();
			Object [] obj = (Object[])itr.next();
			dic.setDicid((Long)obj[0]);
			dic.setDicname((String)obj[1]);
			dicList.add(dic);
		}
		return dicList;
	}
	
	
	/**
	 * add by zhaoyi _20070330
	 * 得到用户维护中的选择组的列表（包括全部权限和无权限）
	 * @return
	 */
	public List getSelectGroups(){
		
		List sumList = new ArrayList();
		Dictionary dic1 = new Dictionary();
		dic1.setDicid(new Long(1));
		dic1.setDicname("所有报表权限");
		dic1.setParentid(new Long(2001));
		sumList.add(dic1);
		
		Dictionary dic2 = new Dictionary();
		dic2.setDicid(new Long(9));
		dic2.setDicname("此用户无效，无任何权限");
		dic2.setParentid(new Long(2001));
		
		List dicList = udao.getDictionaryByParentid("2001");
		Iterator itr = dicList.iterator();
		while(itr.hasNext()){
			Dictionary dicu = (Dictionary)itr.next();
			sumList.add(dicu);
		}
		sumList.add(dic2);
	
		return sumList;
	}

	public Dictionary getDictionary(int type, String description)
	{
		return udao.getDictionary(type, description);
	}
	
	// 获得在线帮助文件出处分类列表  2007.12.14 add by lxk	
	public List getOnlineHelpFileDerivation() {
		return udao.getDics(2010);
	}	
	
	/**
	 * 根据dicid去得某配置的父节点
	 * @param dicid
	 * @return
	 */
	public Dictionary getDictionary(int dicid) {
		return udao.getDictionary(dicid);
	}
	
	/**
	 * 根据上级节点和当前节点id,删除配置(status = 9)
	 * @param parentid
	 * @param dicid
	 */
	public void deleteDictionary(int parentid, int dicid) {
		udao.deleteDictionary(parentid, dicid);
	}
	/**
	 * 得到当前最大的业务分类id
	 * @param parentid
	 * @param dicid
	 * @return
	 */
	public int getMaxBusinessId(int parentid) {
		return udao.getMaxBusinessId(parentid);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.DictionaryService#updateVersion(int, int, java.lang.String)
	 * @author zhaoPC
	 */
	public void updateVersion(int parentId, int dicId, String version)
	{
		udao.updateVersion(parentId, dicId, version);
	}
	@Override
	public List getConfigType() {
		return udao.getDics(2014);
	}

	

}
