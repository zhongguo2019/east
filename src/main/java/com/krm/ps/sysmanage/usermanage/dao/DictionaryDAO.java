package com.krm.ps.sysmanage.usermanage.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;

public interface DictionaryDAO extends DAO{
	
	/**
	 * 查询登陆地IP校验状态
	 * @return dicname
	 * @author LC
	 */
	public String getIpStatus();
	
	/**
	 * 得到字典表对象列表
	 * @return {@link Dictionary}对象列表
	 */
	public List getDics();
	
	
	/**
	 * 得到字典表对象列表2
	 * @return {@link Dictionary}对象列表
	 */
	public List getDicss(int type, List list);
	
	/**
	 * 得到机构对照配置列表
	 * @return {@link Esystem}对象列表
	 */
	public List getEsystem();
	
	/**
	 * 删除字典对象
	 * @param pkid
	 */
	public void removeDic(Long pkid);
	/**
	 * 得到机构对照配置列表
	 * @return {@link Esystem}对象列表
	 */
	public List getALLsystem() ;
	
	/**
	 * 得到单位配置对象列表
	 * @return {@link Units}对象列表
	 */
	public List getUnits();
    
	/**
	 * 根据类型得到字典对象
	 * @param type 父id
	 * @return {@link Dictionary}对象列表
	 */
    public List getDics(int type);

    /**
     * 得到某一类型最大id
     * @param type 父id
     * @return
     */
    public  int maxDicid(int type);    

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
	public Dictionary getDictionary(int type, String description);
	
	/**
	 * 得到统计导出显示级别
	 * @return
	 */
	public List getStatExportLevel();
	
	/**
	 * add by zhaoyi _20070328
	 * 得到分组的vo list
	 * 根据parentid获取字典项
	 * @param parentid
	 * @return 
	 */
	public List getDictionaryByParentid(String parentid);

	/**
	 * add by zhaoyi _20070329
	 * 插入一个dictionary
	 * @param dic
	 */
	public void exeAnySql(String sql);
	
	/**
	 * add by zhaoyi _20070328
	 * 得到分组的vo list
	 * 根据parentid和dicid获取字典项
	 * @param parentid
	 * @param dicid
	 * @return
	 */
	public List getDictionaryByParentidAndDicid(String parentid,String dicid);
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
	 * 得到当前最大显示序号
	 * @param parentid
	 * @return
	 */
	public int getMaxDisporder(int parentid);
	/**
	 * 更新公式版本号
	 * @author zhaoPC
	 */
	public void updateVersion(int parentId, int dicId, String version);
	
	public Integer getDicnameCount(String dicname, String dicid);
}
