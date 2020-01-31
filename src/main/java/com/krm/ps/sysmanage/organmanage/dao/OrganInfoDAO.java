package com.krm.ps.sysmanage.organmanage.dao;

import java.util.List;
import java.util.Map;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.organmanage.vo.Department;
import com.krm.ps.sysmanage.organmanage.vo.OrganContrast;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage.vo.UserDeptIdx;
import com.krm.ps.sysmanage.organmanage2.vo.OrganSystemInfo;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;

public interface OrganInfoDAO extends DAO {

	/**
	 * 得到所有机构列表
	 * @param date 日期
	 * @return {@link OrganInfo}对象列表
	 */
	public List getOrgans(String date);

	/**
	 * 根据机构编码得到机构信息对象
	 * @param code 机构编码
	 * @return OrganInfo
	 */
	public OrganInfo getOrganByCode(String code);

	/**
	 * 得到当前机构的下级机构列表
	 * @param organid 机构
	 * @param date 日期
	 * @return {@link OrganInfo}对象列表
	 */
	public List getJuniorOrgans(String organid, String date);

	/**
	 * 得到当前机构的下级机构列表（不带当前机构）
	 * @param organid 机构
	 * @param date 日期
	 * @return {@link OrganInfo}对象列表
	 */
	public List getJuniorOrgansOnly(String organid, String date);

	/**
	 * 得到当前机构的下级机构中所包含的机构类型
	 * @param organid 机构
	 * @param date 日期
	 * @return {@link OrganInfo}对象列表
	 */
	public List getJuniorOrganTypes(String organid, String date);

	/**
	 * 判断机构编码是否重名
	 * @param pkid
	 * @param code
	 * @return
	 */
	public boolean codeRepeat(Long pkid, String code);

	/**
	 * 删除机构信息
	 * @param pkid
	 */
	public void removeOrgan(Long pkid);

	/**
	 * 根据父机构标识得到父机构及其下属的所有子机构.
	 * @param organId
	 * @return
	 */
	public List getAllChildOrgans(String organId, String date);

	/**
	 * 得到所有下级机构
	 * @param organId
	 * @return {@link OrganInfo}对象列表
	 */
	public List getAllChildOrgans(String organId);

	/**
	 * 得到所有下级机构(按机构级别顺序查询)
	 * @param organId 机构
	 * @param date 日期
	 * @return {@link OrganInfo}对象列表
	 */
	public String getAllChildOrganTree(String organId, String date);
	
	/**
	 * 得到所有机构
	 * @return {@link OrganInfo}对象列表
	 */
	public List getOrgans();
	
	/**
	 * 取得当前机构（organid）所在地区内的各级机构
	 * @param organid 当前机构(CODE_ORG_ORGAN.CODE)
	 * @param date 有效日期
	 * @return {@link OrganInfo}对象列表
	 */
	public List getAllOrgansInArea(String organid, String date);
	
	/**
	 * 取得当前机构（organid）所在地区内的直接下级机构
	 * @param organid 当前机构(CODE_ORG_ORGAN.CODE)
	 * @param date 有效日期
	 * @return {@link OrganInfo}对象列表
	 */
	public List getSubOrgansInArea(String organid, String date);

	/**
	 * 得到当前机构的下级机构列表
	 * @param organid
	 * @return {{@link OrganInfo}对象列表
	 */
	public List getJuniorOrgans(String organid);
	
	/**
	 * 得到所有下级机构含本级
	 * @param organid 机构
	 * @return {@link OrganInfo}对象列表
	 */
	public List getSelfJuniorOrgans(String organid);
	/**
	 * 得到所有下级机构含本级
	 * @param organid 机构
	 * @return {@link OrganInfo}对象列表
	 */
	public List getSelfJuniorOrgan(String organid);
	
	/**
	 * 得到当前机构的下级机构列表（不带当前机构）
	 * @param organid
	 * @return {@link OrganInfo}对象列表
	 */
	public List getJuniorOrgansOnly(String organid);

	/**
	 * 得到当前机构的下级机构中所包含的机构类型
	 * @param organid
	 * @return {List<String> organType}
	 */
	public List getJuniorOrganTypes(String organid);
    
	/**
	 * 得到最大机构显示序号
	 * @return
	 */
	public Integer getMaxOrganOrder();

	/**
	 * 得到机构显示序号
	 * @param organId
	 * @return
	 */
	public Integer getOrganOrder(Long organId);

	/**
	 * 得到机构等级
	 * @param code
	 * @return
	 */
	public String getLevel(String code);

	/**
	 * 删除机构
	 * @param code
	 */
	public void removeOrganByCode(String code);

	/**
	 * 更新机构上次机构号
	 * @param codeBefore
	 * @param codeNow
	 */
	public void updateOrganSuperId(String codeBefore, String codeNow);
	
	/**
	 * 得到机构数
	 * @return
	 */
	public int getOrganCount();
	
	/**
	 * 取得多级机构
	 * @param organCode
	 * @param level
	 * @return
	 */
	public String[] getDirSubOrgans(String[] organCode);	

	/**
	 * 取得多级机构
	 * @param organCode
	 * @param level
	 * @return
	 */
	public List listSubOrgans(String[] organCode, int level) ;// wsx 11-06
	
	/**
	 * 得到当前机构的待导出机构列表
	 * @param organid 要导出的机构的机构代码(CODE_ORG_ORGAN.CODE)
	 * @param date 数据日期
	 * @return {@link OrganInfo}对象列表
	 * @history 
	 * 2007年7月2日郭跃龙：修改取得下级机构的策略。通过organid取得该机构对应的地区id。然后要把该机构下所有的机构取出。（其他条件不变）
	 */
	public List getExportOrgans(String organid,String date);
	/**
	 * 根据机构号,日期查询机构信息(机构编码,机构名称,机构类型)
	 * @param organId
	 * @param date
	 * @return {List<Object[]> obj}
	 */
	public List getStatOrgans(String organId,String date);
	/**
	 * 根据机构号,日期查询下级机构
	 * @param organid
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getStatExoprtJuniorOrgans(String organid,String date);
	/**
	 * 根据机构号查询机构信息(机构编码,机构名称,机构类型)
	 * @param organId
	 * @param date
	 * @return {List<Object[]> obj}
	 */
	public List getStatOrgan(String organId);
	
	/**
	 * 得到当前机构的机构类型对照
	 * @param organType
	 * @return
	 */
	public String getOrganType(Integer organType);
	
	/**
	 * 根据机构号得到外部机构对照
	 * @param organId
	 * @return {List<Object> obj} 机构名称,外部对照
	 */
	public List getOrganInfo(String organId);

	/**
	 * 查询汇总类别
	 * @return
	 */
	public List getSumTypes();

	
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
	 * 根据机构类型带到机构
	 * @param type
	 * @return
	 */
	public Map getOrganByType(Integer type, String date);
	/**
	 * 统计导出用
	 * 根据机构对照得到机构信息
	 * @param systemId
	 * @return
	 */
	public List getOrgMapByContrast(Long systemId);

	/**
	 * <p>删除List中的对象</p>
	 * 
	 *  删除标准为：人员ID、部门ID、机构ID相同
	 *  
	 * @param userDeptIdxList
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-4 下午04:10:44
	 */
	public boolean deleteUserDeptIdxList(List userDeptIdxList);

	/**
	 * <p>把列表中的用户部门索引信息保存到数据库</p> 
	 *
	 * @param userDeptIdxList
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-4 下午04:30:02
	 */
	public boolean saveUserDeptIdxList(List userDeptIdxList);

	/**
	 * <p>查询指定用户所属部门</p> 
	 *
	 * @param userId
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-6 上午11:53:59
	 */
	public UserDeptIdx getUserDeptIdxForUser(Long userId);

	/**
	 * <p>查询用户所在部门信息</p> 
	 *
	 * @param pkid 用户ID
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-7 下午01:12:16
	 */
	public Department getDepartmentForUser(Long pkid);
	
	/**
	 * 得到机构对照Map
	 * @param systemId
	 * @return
	 */
	public List getContrastMap(Long systemId);
	
	/**
	 * 得到机构对应的外部编码
	 * @param systemId
	 * @param systemId
	 * @return
	 */
	public String getOrganConstrat(Long system_code,String organId);
	
	public Map getOrganMap();
	/**
	 * 从code_org_organ_all表里查出的数据插入code_org_tree表
	 */
	public void saveOrganTree(String date,Long pkid);
	/**
	 * 保存CODE_USER_ORG_IDX报送机构树+年月 报送机构树201307
	 * @param date
	 * @param pkid
	 */
	public long saveOrganTreeIdx(OrganSystemInfo osi);



	/**
	 * 取得指定机构的节点
	 * @param nodeId	机构树id
	 * @return 某一个节点
	 */
	public OrganTreeNode getNode(int nodeId);

}
