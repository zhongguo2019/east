package com.krm.ps.sysmanage.organmanage2.services;

import java.util.List;

import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.model.OrganSimpleInfo;
import com.krm.ps.sysmanage.organmanage2.model.OrganTree;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;

/**
 * 机构管理服务。get*Organs方法返回的是{@link OrganInfo}对象的列表，从效率上考虑，如果不需要机构的详细信息，
 * 可以使用相应的get*OrgansSimpleInfo方法代替，该方法返回{@link OrganSimpleInfo}对象的列表。
 * 取机构列表和xml的方法都要求传入日期，以保证返回的每个机构都在可用期限之内
 * 
 */
public interface OrganService2 {

	
	/**
	 * 根据organCode查询nodeid
	 */
	public String getnodeid(String organCode,int idx);
	/**
	 * 获得整棵树的organCode
	 * @author LC
	 */
	public List getAllOrganCode(int organSystemId, int topOrgan, String date);
	/**
	 * 取得指定机构树的xml,用于树控件显示
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return
	 */
	public String getOrganTreeXML(int organSystemId, int topOrgan,
			String date);
	public String getOrgTreeXML(int organSystemId, int topOrgan,
			String date, Integer organType);
	public OrganTree filtOrganTree(OrganTree organTree, Integer organType, String date);
	/**
	 * 取得指定机构树的模型对象和xml
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return 对象数组，[0]是树的OrganTree对象，[1]是树的xml
	 */
	public Object[] getOrganTreeModelAndXML(int organSystemId, int topOrgan,
			String date);
	
	/**
	 * 取得指定机构树的xml,用于树控件显示
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return
	 */
	public String getOrganTreeXML(int organSystemId, String organCode,
			String date);
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
	 * @param topOrganCode
	 * @param date
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-8-21 下午12:10:24
	 */
	public List getAllLeafOrgans(int organSystemId, String topOrganCode, String date);

	/**
	 * 取得下一级机构列表
	 * 
	 * @param organSystemId 机构树结构索引(CODE_ORG_TREE.IDXID)
	 * @param organCode 要获得的机构的父机构的代码(CODE_ORG_ORGAN.CODE)
	 * @param date 日期
	 * @return {@link OrganInfo}对象列表
	 */
	public List getSubOrgans(int organSystemId, String organCode, String date);
	
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
	 * 取得所有下层机构（可能有多层）
	 * 
	 * @param organSystemId 机构树结构索引(CODE_ORG_TREE.IDXID)
	 * @param organCode 要获得的机构的父机构的代码(CODE_ORG_ORGAN.CODE)
	 * @param date 日期
	 * @return {@link OrganInfo}对象列表
	 */
	public List getAllSubOrgans(int organSystemId, String organCode, String date);
	
	/**
	 * 取得所有下层机构（可能有多层）,按照机构级别排序。
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getAllSubOrgansByGrade(int organSystemId, String organCode, String date);

	/**
	 * 取得指定层数的下级机构
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param layer
	 * @param date
	 *            层数，1表示下一级
	 * @return {@link OrganInfo}对象列表
	 */
	public List getSubOrgans(int organSystemId, int topOrgan, int layer,
			String date);
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
	 * 取得下一级机构列表
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List getSubOrgansSimpleInfo(int organSystemId, int topOrgan,
			String date);

	/**
	 * 取得下一级机构列表
	 * 
	 * @param organSystemId 机构树结构索引(CODE_ORG_TREE.IDXID)
	 * @param organCode 要获得的机构的父机构的代码(CODE_ORG_ORGAN.CODE)
	 * @param date 日期
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List getSubOrgansSimpleInfo(int organSystemId, String organCode,
			String date);

	/**
	 * 取得所有下层机构（可能有多层）
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List getAllSubOrgansSimpleInfo(int organSystemId, int topOrgan,
			String date);


	/**
	 * 取得所有下层机构（可能有多层）
	 * 
	 * @param organSystemId 机构树结构索引(CODE_ORG_TREE.IDXID)
	 * @param organCode 要获得的机构的父机构的代码(CODE_ORG_ORGAN.CODE)
	 * @param date 日期
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List getAllSubOrgansSimpleInfo(int organSystemId, String organCode,
			String date);

	/**
	 * 取得指定层数的下级机构
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param layer
	 *            层数，1表示下一级
	 * @param date
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List getSubOrgansSimpleInfo(int organSystemId, int topOrgan,
			int layer, String date);
	
	/**
	 * 取得指定层数的下级机构
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param layer
	 *            层数，1表示下一级
	 * @param date
	 * @param topLevel 上级机构层级 add by safe at 2007.11.27
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List getSubOrgansSimpleInfo(int organSystemId, int topOrgan,
			int layer, String date, int topLevel);

	/**
	 * 取得指定层数的下级机构
	 * 
	 * @param organSystemId
	 * @param organCode
	 * @param layer
	 *            层数，1表示下一级
	 * @param date
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List getSubOrgansSimpleInfo(int organSystemId, String organCode,
			int layer, String date);
	
	/**
	 * 取得指定层数的下级机构
	 * 
	 * @param organSystemId
	 * @param organCode
	 * @param layer
	 *            层数，1表示下一级
	 * @param date
	 * @param topLevel 上级机构层级 add by safe at 2007.11.27
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List getSubOrgansSimpleInfo(int organSystemId, String organCode,
			int layer, String date, int topLevel);

	/**
	 * 得到机构树管理服务
	 * 
	 * @return
	 */
	public OrganTreeManager getOrganTreeManager();
	
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
	 * 取得机构树上的非叶子节点
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
	
	/**得到上级机构
	 * @param organSystemId
	 * @param organCode
	 * @return
	 */
	public String getPreOrgan(int organSystemId,String organCode);
	
	/**
	 * <p>生成机构部门树</p> 
	 *
	 * @param organTreeIdxid
	 * @param organId
	 * @param date
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-4-16 下午04:50:35
	 */
	public String gerOrgAndDepartmentXML(int organTreeIdxid, int organId, String date);
	
	
	/**
	 * <p>返回机构树的节点列表</p> 
	 *
	 * @param organTreeIdxid
	 * @param topOrgan
	 * @param date
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-4-20 下午01:28:50
	 */
	public List getSubOrganNodeList(int organTreeIdxid, int topOrgan, String date);
	
	/**
	 * <p>得到所有部门的基本信息</p> 
	 *
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-4-20 下午03:08:34
	 */
	public List getDepartmentSimpleInfoList();
	
	/**
	 * <p>查询用户所在机构树的根结点</p> 
	 *
	 * @param organTreeIdxid
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-6 上午10:02:33
	 */
	public OrganTreeNode getRootOrganTreeNode(int organTreeIdxid);
	
	/**
	 * 表项查询科目树初始化懒加载
	 * @param organList
	 * @param organSystemId
	 * @param organId
	 * @param date
	 * @return
	 */
	public List getJSONinitlazyTreeList(List organList,int organSystemId,String organId,String date);
	
	/**
	 * 表项查询科目树懒加载
	 * @param organList
	 * @param organSystemId
	 * @param organId
	 * @param date
	 * @return
	 */
	public List getJSONlazyTreeList(List organList,int organSystemId,String organId,String date);
	
	/**
	 * 将表项查询的机构数据全部查询
	 * @param organList
	 * @param organSystemId
	 * @param organId
	 * @param date
	 * @return
	 */
	public List getItemDataExcelList(int organSystemId,String organId,String date);
	
	/**
	 * 根据报表ID获得报表类型
	 * @param reportId
	 * @return
	 */
	public String getReportType(String reportId);
	
	/**
	 * 根据机构树与机构节点ID获得父ID
	 * @param organSystemId
	 * @param topOrgan
	 * @return
	 */
	public String getTreeTagByNodeid(int organSystemId, int topOrgan);
	
	/**
	 * 根据机构PKID获取机构信息
	 * @param pkid
	 * @return
	 */
	public OrganInfo getOrganById(String pkid);
	
	public String[] getAddresseeList(String[] organIds,String roleType);
	
	public String getOrganTreeXML_temp(int organSystemId, int organId,
			String date,String treeState,String nodeid);
	
	public List getOrganTreeList_temp(int organSystemId, int organId,
			String date);
	
}
