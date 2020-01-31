package com.krm.ps.sysmanage.organmanage.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganContrast;

public interface OrganContrastDAO extends DAO {

	/**
	 * 得到所有机构对照关系
	 * @return {@link OrganContrast}对象列表
	 */
	public List getAllContrasts();
	
	/**
	 * 得到对应ID的机构对照关系
	 * @param pkid
	 * @return OrganContrast
	 */
	public OrganContrast getContrast(Long pkid);
	
	/**
	 * 根据机构ID得到对应的机构对照关系
	 * @param organid
	 * @return {@link OrganContrast}对象列表
	 */
	public List getContrastById(String organid);

	/**
	 * 根据机构ID得到该机构及其下级机构的所有机构对照关系
	 * @param organid
	 * @param date
	 * @return {@link OrganContrast}对象列表
	 */
	public List getJuniorOrganContrasts(String organid, String date);

	/**
	 *  根据外部系统号及外部机构编码得到对应的机构对照关系
	 * @param system_code
	 * @param outer_org_code
	 * @return OrganContrast
	 */
	public OrganContrast getContrastbyCode(Long system_code,
			String outer_org_code);

	/**
	 * 删除对应该机构ID的机构信息（不可恢复）
	 * @param pkid
	 */
	public void removeContrast(Long pkid);

	/**
	 * 删除一个机构的所有对照关系
	 * @param organid
	 */
	public void removeContrastByOrgan(String organid);

	/**
	 * 根据机构编码删除该机构所有下级对照关系
	 * @param organcode
	 */
	public void removeOrganAllContrasts(String organcode);

	/**
	 * 根据外部系统号及本机构编码得到对应的机构对照关系
	 * @param system_code
	 * @param code
	 * @return OrganContrast
	 */
	public OrganContrast getContrastbyOrgan(Long system_code, String code);

	/**
	 * 根据外部系统号及机构对照编码得到本系统机构编码
	 * @param system_code
	 * @param contrast
	 * @return OrganContrast
	 */
	public OrganContrast getOrganbyContrast(Long system_code, String contrast);// wsx
				
	/**
	 * 查找下级机构
	 * @param organid 机构号
	 * @param date 日期
	 * @param systemcode
	 * @return {@link OrganContrast}对象列表
	 */
	public List getJuniorOrganContrastsForStat(String organid, String date,//zpc
			Integer systemcode);
	/**
	 * 查找机构外部编码
	 * @param organId
	 * @param sysCode
	 * @return {@link OrganInfo}对象列表
	 */
	public List getJuniorOrganByCode(String organId,Long sysCode);
	/**
	 * 得到机构对照
	 * @param organsCode
	 * 			机构code
	 * @return {@link OrganContrast}对象列表
	 */
	public List getOrgansContrast(String organsCode);
	
	/**
	 * 将原报表id替换为新报表id zhouhao 20070825
	 * @param newRepid
	 * @param oldRepid
	 * @return
	 */
	public int updateRepidByRepid(Long newRepid, Long oldRepid);
	/**
	 * 得到指定类型机构对照
	 * @param systemCode
	 * @return {@link OrganContrast}对象列表
	 */
	public List getOrganContrast(Long systemCode);
	
	/**
	 * 查询机构对照
	 * @param systemCode
	 * @param organId 可以是都好分割串
	 * @return {@link OrganContrast}对象列表
	 */
	public List getOrganContrast(Long systemCode, String organId);
}
