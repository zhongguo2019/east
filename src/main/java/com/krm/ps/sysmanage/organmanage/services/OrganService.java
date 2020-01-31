package com.krm.ps.sysmanage.organmanage.services;

import java.util.List;
import java.util.Map;

import com.krm.ps.sysmanage.organmanage.dao.OrganContrastDAO;
import com.krm.ps.sysmanage.organmanage.dao.OrganInfoDAO;
import com.krm.ps.sysmanage.organmanage.vo.Department;
import com.krm.ps.sysmanage.organmanage.vo.OrganContrast;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage.vo.UserDeptIdx;
import com.krm.ps.sysmanage.usermanage.dao.UserDAO;
import com.krm.ps.sysmanage.usermanage.vo.User;

public interface   OrganService{
	
	public static int CODE_REPEAT = 1;
	public static int SAVEOK =2;
	
	public void setOrganInfoDAO(OrganInfoDAO dao);
	
	public void setUserDAO(UserDAO dao);
	
	public void setOrganContrastDAO(OrganContrastDAO dao);
	
	/**
	 * 取得当前机构（organid）所在地区内的各级机构
	 * @param organid 当前机构(CODE_ORG_ORGAN.CODE)
	 * @param date 有效日期
	 * @return OrganInfo对象列表
	 */
	public List getAllOrgansInArea(String organid, String date);
	
	/**
	 * 取得当前机构（organid）所在地区内的直接下级机构
	 * @param organid 当前机构(CODE_ORG_ORGAN.CODE)
	 * @param date 有效日期
	 * @return OrganInfo对象列表
	 */
	public List getSubOrgansInArea(String organid, String date);
	
	/**
	 * 得到全部机构信息
	 * @deprecated 使用{@link com.krm.slsint.organmanage2.services.OrganService2}内的方法代替。实行多机构系统之后，获得机构列表必须指定一个机构系统。
	 */
	public List getOrgans(String date);
	
	/**
	 * @deprecated 使用{@link com.krm.slsint.organmanage2.services.OrganService2}内的方法代替。实行多机构系统之后，获得机构列表必须指定一个机构系统。
	 * 根据机构ID得到该机构的所有下级机构列表(带当前机构)
	 */
	public List getJuniorOrgans(String organid,String date);
	
	/**
	 * @deprecated 使用{@link com.krm.slsint.organmanage2.services.OrganService2}内的方法代替。实行多机构系统之后，获得机构列表必须指定一个机构系统。
	 */
	public List getSelfJuniorOrgans(String organid) ;//2006.9.20

	/**
	 * @deprecated 使用{@link com.krm.slsint.organmanage2.services.OrganService2}内的方法代替。实行多机构系统之后，获得机构列表必须指定一个机构系统。
	 * 2007.1.30得到本级机构以及所有下级机构
	 */
	public List getSelfJuniorOrgan(String organid);

	/**
	 * @deprecated 使用{@link com.krm.slsint.organmanage2.services.OrganService2}内的方法代替。实行多机构系统之后，获得机构列表必须指定一个机构系统。
	 * 得到当前机构的下级机构列表（不带当前机构）
	 */
	public List getJuniorOrgansOnly(String organid,String date); 

	/**
	 * @deprecated 
	 * 得到当前机构的下级机构中所包含的机构类型
	 */
	public List getJuniorOrganTypes(String organid,String date);
	
	/**
	 * 根据对照关系表的PKID得到对照关系对象
	 * @param pkid
	 * @return OrganContrast
	 */
	public OrganContrast getContrast(Long pkid);
	
	/**
	 * 根据机构ID得到所有该机构的对照关系列表
	 * @param organid 机构号
	 * @return {@link OrganContrast}对象列表
	 */
	public List getContrastById(String organid);
	
	/**
	 * 根据机构ID得到该机构及其下级机构的所有机构对照关系
	 * @param organid 机构号
	 * @param date 日期
	 * @return {@link OrganContrast}对象列表
	 */
	public List getJuniorOrganContrasts(String organid,String date);
	
	/**
	 * 根据ID得到机构信息对象
	 * @param pkid 机构pkid
	 * @return OrganInfo
	 */
	public OrganInfo getOrgan(Long pkid);
	
	/**
	 * 根据机构编码得到该机构对象
	 * @param code 机构编码
	 * @return OrganInfo
	 */
	public OrganInfo getOrganByCode(String code);
	
	/**
	 * 保存机构信息
	 * @param organ 机构
	 * @param codeBeforeChange 保存前机构编码
	 * @return
	 */
	public int saveOrgan(OrganInfo organ,String codeBeforeChange);
	
	/**
	 * 保存机构对照关系信息
	 * @param contrast 机构对照
	 * @return
	 */
	public int saveContrast(OrganContrast contrast);
	
	/**
	 * 删除机构信息对象
	 * @param pkid 
	 */
	public void removeOrgan(Long pkid);
	
	/**
	 * 删除所有当前机构的下级机构对照关系
	 * @param organid 机构号
	 * @param date 日期
	 */
	public void removeOrganByOrgan(String organid,String date);
	
	/**
	 * 删除所有当前机构的下级机构对照关系
	 * @param areaList 机构
	 */
	public void removeOrganByOrgan(List areaList);
	
	/**
	 * @deprecated 使用{@link com.krm.slsint.organmanage2.services.OrganService2}内的方法代替。实行多机构系统之后，获得机构列表必须指定一个机构系统。
	 * 得到全部机构信息
	 */
	public List getOrgans();
	
	/**
	 * @deprecated 使用{@link com.krm.slsint.organmanage2.services.OrganService2}内的方法代替。实行多机构系统之后，获得机构列表必须指定一个机构系统。
	 * 根据机构ID得到该机构的所有下级机构列表(带当前机构)
	 */
	public List getJuniorOrgans(String organid);
	
	/**
	 * @deprecated 使用{@link com.krm.slsint.organmanage2.services.OrganService2}内的方法代替。实行多机构系统之后，获得机构列表必须指定一个机构系统。
	 * 得到当前机构的下级机构列表（不带当前机构）
	 */
	public List getJuniorOrgansOnly(String organid); 
	
	/**
	 * 得到当前机构的下级机构中所包含的机构类型
	 * @deprecated 
	 */
	public List getJuniorOrganTypes(String organid);
	
	/**
	 * 取得系统机构总数
	 * @return
	 */
	public int getOrganCount();

	/**
	 * 取得多级机构
	 * @param organCode
	 * @param level
	 * @return
	 * @deprecated 需要重新实现（要求传入机构系统编码）
	 */
	public List listSubOrgans(String[] organCode, int level) ;// wsx 11-06
	
	/**
	 * 根据外部系统号及本机构编码得到对应的机构对照关系
	 * @param system_code
	 * @param code
	 * @return OrganContrast
	 */
	public OrganContrast getContrastbyOrgan(Long system_code,String code);
	
	/**
	 * 根据外部系统号及机构对照编码得到本系统机构编码
	 * @param system_code
	 * @param contrast
	 * @return OrganContrast
	 */
	public OrganContrast getOrganbyContrast(Long system_code,String contrast);// wsx 12-14

	
	/**
	 * 得到所有（层次上）下级机构的机构列表
	 * @param organId 当前机构id
	 * @param date 当前日期（用于判断机构有效期）
	 * @return 当前机构，以及当前机构的所有层次的下级机构，按照机构id排序的列表。
	 * @deprecated
	 */
	public List getAllChildOrgans(String organId, String date);
	/**
	 * 得到机构对照
	 * @param organsCode
	 * 			机构code
	 * @return
	 */
	public List getOrganContrast(String organsCode);
	
	/**
	 * 判断机构是否被使用过，即是否存在与该机构相关的信息(数据或日志记录）
	 * @param organCode 要验证的机构的代码
	 * @return
	 */
	public boolean isOrganUsed(String organCode);
	
	/**
	 * 取得机构所在的组织形式树的名称
	 * @param organCode 机构代码
	 * @return code_user_org_idx.name
	 */
	public List isOrganInTree(String organCode);
	
	/**
	 * zhouhao 20070825
	 * 将原报表id替换为新报表id
	 * @param newRepid
	 * @param oldRepid
	 * @return
	 */
	public int updateRepidByRepid(Long newRepid, Long oldRepid);
	/**
	 * 统计导出用
	 * 根据机构对照得到机构信息
	 * @param systemId
	 * @return
	 */
	public Map getOrgMapByContrast(Long systemId);

	/**
	 * <p>返回所有的部门对象列表</p> 
	 *
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-4-16 下午04:07:51
	 */
	public List getAllDepartmentList();

	/**
	 * <p>删除List中的对象</p>
	 * 
	 *  删除标准为：人员ID、部门ID、机构ID相同
	 *
	 * @param userDeptIdxList
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-4 下午04:07:41
	 */
	public boolean deleteUserDeptIdxList(List userDeptIdxList);

	/**
	 * <p>把列表中的用户部门索引信息保存到数据库</p> 
	 *
	 * @param userDeptIdxList
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-4 下午04:28:06
	 */
	public boolean saveUserDeptIdxList(List userDeptIdxList);

	/**
	 * <p>查询用户部门索引对象</p> 
	 *
	 * @param userId 指定用户
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-6 上午11:51:26
	 */
	public UserDeptIdx getUserDeptIdxForUser(Long userId);

	/**
	 * <p>查询人员所在部门对象</p> 
	 *
	 * @param pkid
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-7 下午01:10:28
	 */
	public Department getDepartmentForUser(Long pkid);
	
	/**
	 * 得到机构对照Map
	 * @param systemId
	 * @return
	 */
	public Map getContrastMap(Long systemId);
	
	/**
	 * 根据系统编码和机构编码找出外部编码
	 * @param system_code
	 * @param organ_Id
	 * @return OrganContrast
	 */
	public String getOrganContrastByOrgansysCode(Long system_code,String organId);
	public Map getOrganMap();
 
}

