package com.krm.ps.sysmanage.organmanage2.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.organmanage2.model.OrganNode;
import com.krm.ps.sysmanage.organmanage2.vo.OrganSystemInfo;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;

public interface OrganTreeDAO extends DAO{

	/**
	 * 根据机构系统编码、顶层机构取得机构树节点列表
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @return {@link OrganTreeNode}的List，是根据父节在前子结点在后排好序的
	 */
	public List listOrganTreeNodes(int organSystemId, int topOrgan);
	public String listOrganTreeNodes_temp(int organSystemId, int topOrgan,String treeState,String nodeid);

	public List getOrganTreeList_temp(int organSystemId, int topOrgan);
	/**
	 * 根据机构系统编码取得机构树节点列表
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @return {@link OrganTreeNode}的List，是根据父节在前子结点在后排好序的
	 */
	public List listOrganTreeNodes(int organSystemId);
	
	/**
	 * 取得指定机构树的根节点
	 * @param organSystemId	机构树id
	 * @return 指定机构树的根节点{@link OrganTreeNode}
	 */
	public OrganTreeNode getTopNode(int organSystemId);
	
	/**
	 * 取得指定机构树上指定节点的所有叶子节点
	 * @param organSystemId	机构树id
	 * @param subtreeRootOrgCode	要查询子树的顶点机构
	 * @return	叶子节点数组，元素为 {@link OrganTreeNode}。
	 */
	public List getLeaves(int organSystemId, String subtreeRootOrgCode);
	
	/**
	 * 删除指定机构系统id的机构树所有节点
	 * @param organSystemId
	 * @return
	 */
	public void removeOrganTreeNodes(int organSystemId);

	/**
	 * 删除指定机构树
	 * @param organSystemId
	 * @return
	 */
	public void removeOrganSystem(int organSystemId);
	
	/**
	 * 保存机构树节点列表
	 * 
	 * @param organTreeNodes {@link OrganTreeNode}的List
	 * @return 
	 */
	public void saveOrganTreeNodes(List organTreeNodes);
	

	/**
	 * 列出指定用户可以查看、维护的机构系统
	 * 
	 * @param userId
	 * @param date
	 *            日期，此日期不在使用期限内的机构系统不列出来，格式yyyyMMdd
	 * @param type
	 *            查看类型，取值为
	 *            <ul>
	 *            	1:所有自己创建的均可见
	 *            	2:严格检查分组、地区和日期
	 *            </ul>
	 * @return {@link OrganSystemInfo}对象列表
	 */
	public List listOrganSystems(int userId, String date, int type);

	/**
	 * 根据id取得机构系统
	 * 
	 * @param organSystemId
	 * @return
	 */
	public OrganSystemInfo getOrganSystem(int organSystemId);

	/**
	 * 保存机构系统信息
	 * 
	 * @param organSystem
	 * @return
	 */
	public void saveOrganSystem(OrganSystemInfo organSystem);

	/**
	 * 查询当前节点的下一级子树节点
	 * @param organSystemId 树id
	 * @param nodeId	节点id
	 */
	public List getSubnode(int organSystemId, String nodeId);

}