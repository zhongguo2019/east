package com.krm.ps.sysmanage.organmanage2.dao;

import java.util.List;
import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public interface OrganInfoDAO2 extends DAO {

	/**
	 * 根据organCode查询nodeid
	 */
	public String getnodeid(String organCode,int idx);
	
	/**
	 * 取得下一级机构列表
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getSubOrgans(int organSystemId, int topOrgan, String date);
	
	/**
     * 
     * <p>得到某个机构的所有叶子机构信息（不包括当前机构）</p> 
     *
     * @param organSystemId
     * @param topOrgan
     * @param date
     * @return
     * @author 皮亮
     * @version 创建时间：2010-8-21 下午12:10:24
     */
    public List getAllLeafOrgans(int organSystemId, int topOrgan, String date);

	/**
	 * 取得所有下层机构（可能有多层）
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getAllSubOrgans(int organSystemId, int topOrgan, String date);
	
	/**
	 * 取得所有下层机构（可能有多层）,按照机构级别排序。
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getAllSubOrgansByGrade(int organSystemId, int topOrgan, String date);

	/**
	 * 取得指定层数的下级机构
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param layer
	 *            层数，1表示下一级
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getSubOrgans(int organSystemId, int topOrgan,
			int layer, String date);
	/**
	 * 取得指定层的机构
	 * @param organSystemId
	 * @param topOrgan
	 * @param layer 1:返回下一级所有机构 2:返回下两级所有机构(不包括本级和上一级)
	 * @param date
	 *            层数，1表示下一级
	 * @return {@link OrganInfo}对象列表
	 */
	public List getAppointLayerOrgans(int organSystemId, int topOrgan, int layer,
		String date);
	/**
	 * 获取机构类型列表
	 * @return List
	 */
	public List getOrganTypeList();
	
	/**
	 * 获取机构上下级关系列表（机构编码，上级机构编码）
	 * @return List
	 */
	public List getOrganRelationList();
	/**
	 * 得到最小TreeId的机构树
	 * @param date
	 * @return
	 */
	public int getMinOrganTreeId(String date);
	
	public boolean isTreeNode(Integer treeId, Long nodeId);
	
	/**
	 * 根据机构代码取得机构对象
	 * @param code	机构代码
	 * @return
	 */
	public OrganInfo getOrganByCode(String code);
	
	/**
	 * 取得机构树上的叶子节点
	 * @param organSystemId	机构树id
	 * @param subtreeRootOrgCode	要取得的子树的根节点
	 * @return	
	 */
	public List getLeavesOrganInfo(int organSystemId, String subtreeRootOrgCode);
	
	/**
	 * 取得机构树上的非叶子节点
	 * @param treeId	机构树id
	 * @param topOrganCode	要取得的子树的根节点
	 * @param date	数据日期日期
	 * @return	
	 */
	public List getTrunkOrganList(int treeId, String topOrganCode, String date);
	
	/**取得上级机构
	 * @param organSystem
	 * @param organCode
	 * @return
	 */
	public String getPreOrgan(int organSystem,String organCode);

	
	/**
	 * <p>得到所有部门的基本信息，包括部门pkid，部门名称</p> 
	 *
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-4-16 下午05:38:31
	 */
	public List getDepartmentSimpleInfoList();
	
	public List getParentId(int organSystemId, int topOrgan);

	/**
	 * 根据报表ID获得报表类型 
	 * @param reportId
	 * @return
	 */
	public String getReportType(String reportId);
	
	/**
	 * 根据机构PKID获取机构信息
	 * @param pkid
	 * @return
	 */
	public OrganInfo getOrganById(String pkid);
	
	public String[] getAddresseeList(String[] organIds,String roleType);
	
	/**
	 * 根据机构树ID与机构节点ID获取结构父ID
	 * @param organSystemId
	 * @param topOrgan
	 * @return
	 */
	public String getTreeTagByNodeid(int organSystemId, int topOrgan);

}
