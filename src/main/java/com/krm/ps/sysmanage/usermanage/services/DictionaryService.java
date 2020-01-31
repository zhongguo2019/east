package com.krm.ps.sysmanage.usermanage.services;

import java.util.List;

import com.krm.ps.sysmanage.usermanage.dao.DictionaryDAO;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;

public interface DictionaryService {
	
	public void setDictionaryDAO(DictionaryDAO dao);
	/**
	 * 查询登陆地IP校验状态
	 * @return dicname
	 * @author LC
	 */
	public String getIpStatus();
	
	/**
	 * 或得字典对象信息
	 * @param pkid
	 * @return Dictionary
	 */
	public Dictionary getDicname(Long pkid);
	/**
	 * 得到所有字典对象
	 * @return {@link Dictionary}对象列表
	 */
	public List getDics();
   /**
    * 得到所有一级字典列表
    * @return {@link Dictionary}对象列表
    */
	public List getDictionarys();
	//public List getSoftsys();
	/**
	 * 获得机构业务类别列表
	 * @return {@link Dictionary}对象列表
	 */
	public List getOrgansort();
	/**
	 * 获得机构是实体机构还是虚机构列表
	 * @return {@link Dictionary}对象列表
	 */
	public List getOrganoperationsort();
	/**
	 * 获得报表报送频率列表
	 * @return {@link Dictionary}对象列表
	 */
	public List getIsentityorgan(); 
	
	/**
	 * 得到报表频度列表
	 * @return {@link Dictionary}对象列表
	 */
	public List getReportfrequency();
	/**
	 * 根据id得到字典对象
	 * @param pkid
	 * @return Dictionary
	 */
	public Dictionary getDic(Long pkid);
	/**
	 * 保存字典信息
	 * @param dic
	 */
	public void saveDic(Dictionary dic);
	/**
	 * 删除字典信息
	 * @param pkid
	 */
	public void removeDic(Long pkid);
	/**
	 * 得到机构对照配置列表
	 * @return {@link Esystem}对象列表
	 */
	public List getEsystem();
	/**
	 * 得到机构对照配置列表
	 * @return {@link Esystem}对象列表
	 */
	public List getALLsystem() ;
	/**
	 * 得到单位配置对象列表
	 * @return {@link Units}对象列表
	 */
	public List getUnitcode() ;
	/**
	 * 获得工作指引
	 * @return {@link Dictionary}对象列表
	 */
    public List getJobdirect (); 
    /**
     * 获得出处 
     * @return {@link Dictionary}对象列表
     */
    public List getDerivation  (); 
 
    /**
     * 得到汇总级别
     * @return {@link Dictionary}对象列表
     */
    public List getCollectlevel();
   /**
    * 获得生成级别    
    * @return {@link Dictionary}对象列表
    */ 
    public List getBuildlevel();
    /**
     * 根据父id得到字典类表
     * @param type
     * @return {@link Dictionary}对象列表
     */
    public List getDics(int type);
    
    
    /**
     * 根据父id,cidid得到字典类表
     * @param type
     * @return {@link Dictionary}对象列表
     */
    public List getDicss(int type,List list);
    
    
    public  int nextDicid(int type);    

    /**
     * 根据父id,dic id 得到字典对象
     * @param type
     * @param dicid
     * @return Dictionary
     */
	public Dictionary getDictionary(int type,int dicid);
	/**
	 * 根据说明,类型得到字典对象
	 * @param type
	 * @param description
	 * @return Dictionary
	 */
	public Dictionary getDictionary(int type,String description);
	/**
	 * 得到上报人行统计报表导出级别
	 * @return
	 */
	public List getStatExportLevel();
	
	/**
	 * add by zhaoyi _20070330
	 * 得到用户维护中的选择组的列表（包括全部权限和无权限）
	 * @return
	 */
	public List getSelectGroups();
	
	/**
	 * add by lxk 2007.12.14
	 * 获得在线帮助文件出处分类列表
	 * @return
	 */
	public List getOnlineHelpFileDerivation();
	
	/**
	 * 根据dicid去得某配置的父节点
	 * @param dicid
	 * @return
	 */
	public Dictionary getDictionary(int dicid);
	/**
	 * 根据上级节点和当前节点id,删除配置(status = 9)
	 * @param parentid
	 * @param dicid
	 */
	public void deleteDictionary(int parentid, int dicid);
	/**
	 * 得到当前最大的业务分类id
	 * @param parentid
	 * @param dicid
	 * @return
	 */
	public int getMaxBusinessId(int parentid);
	/**
	 * 更新公式版本号
	 * @author zhaoPC
	 */
	public void updateVersion(int parentId, int dicId, String version);
	
	/**
	 * 获得配置表类型
	 * @return {@link Dictionary}对象列表
	 */
	public List getConfigType();
}
