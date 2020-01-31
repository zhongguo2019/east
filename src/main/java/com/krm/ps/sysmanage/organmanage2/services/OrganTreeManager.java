package com.krm.ps.sysmanage.organmanage2.services;

import java.util.List;

import com.krm.ps.framework.common.sort.service.SortService;
import com.krm.ps.sysmanage.organmanage2.model.OrganSystem;
import com.krm.ps.sysmanage.organmanage2.model.OrganTree;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;

/**
 * 机构树管理类，提供对机构系统、机构树的查询、维护接口
 * 
 */
public interface OrganTreeManager extends SortService{
	
	/**
	 * 根据机构系统编码、需要的顶层机构取得机构树
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @return
	 */
	public OrganTree getOrganTree(int organSystemId, int topOrgan);
	
	public String getOrganTree_temp(int organSystemId, int topOrgan,String treeState,String nodeid);
	public List getOrganTreeList_temp(int organSystemId, int topOrgan);
	/**
	 * 根据机构系统编码取得完整机构树
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @return
	 */
	public OrganTree getOrganTree(int organSystemId);
	
	/**
	 * 取得指定机构树上指定节点的所有叶子节点
	 * @param organSystemId	机构树id
	 * @param subtreeRootOrgCode	要查询的子树的顶点机构代码
	 * @return	叶子节点数组，元素为 {@link OrganTreeNode}。
	 */
	public List getLeaves(int organSystemId, String subtreeRootOrgCode);
	
	/**
	 * 取得指定机构树的根节点
	 * @param organSystemId	机构树id
	 * @return 指定机构树的根节点{@link OrganTreeNode}
	 */
	public OrganTreeNode getTopNode(int organSystemId);

	/**
	 * 保存机构树
	 * 
	 * @param organTree
	 * @return
	 */
	public void saveOrganTree(OrganTree organTree);
	
	/**
	 * 删除指定机构系统id的机构树
	 * @param organSystemId
	 * @return
	 */
	public void removeOrganTree(int organSystemId);
	
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
	 * @return {@link OrganSystem}对象列表
	 */
	public List listOrganSystems(int userId, String date, int type);
	

	/**
	 * 列出指定用户可以查看、维护的机构系统，严格检查分组、地区和日期，即使是树的创建者
	 * 
	 * @param userId
	 * @param date
	 *            日期，此日期不在使用期限内的机构系统不列出来，格式yyyyMMdd
	 * @return {@link OrganSystem}对象列表
	 */
	public List listOrganSystems(int userId, String date);

	/**
	 * 根据id取得机构系统
	 * 
	 * @param organSystemId
	 * @return
	 */
	public OrganSystem getOrganSystem(int organSystemId);

	/**
	 * 保存机构系统信息
	 * 
	 * @param organSystem
	 * @return
	 */
	public boolean saveOrganSystem(OrganSystem organSystem);

	public void buildSubtreetag(int idxid, String rootId, String pattern);


}
