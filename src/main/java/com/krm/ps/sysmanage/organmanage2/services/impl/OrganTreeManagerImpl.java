package com.krm.ps.sysmanage.organmanage2.services.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.krm.ps.sysmanage.organmanage2.model.OrganNode;
import com.krm.ps.sysmanage.organmanage2.model.OrganSystem;
import com.krm.ps.sysmanage.organmanage2.model.OrganTree;
import com.krm.ps.sysmanage.organmanage2.services.OrganTreeManager;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;
import com.krm.ps.sysmanage.organmanage2.dao.OrganTreeDAO;
import com.krm.ps.sysmanage.organmanage2.vo.OrganSystemInfo;

/**
 * 机构树管理类，提供对机构系统、机构树的查询、维护接口
 * 
 */
public class OrganTreeManagerImpl implements OrganTreeManager{

	private OrganTreeCache cache=new OrganTreeCache();
	
	private OrganTreeDAO organTreeDAO;
	
	public void setOrganTreeDAO(OrganTreeDAO organTreeDAO) {
		this.organTreeDAO=organTreeDAO;
	}
	
	/**
	 * 根据机构系统编码、需要的顶层机构取得机构树。注意：如果传入不存在的机构系统id将返回null
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @return
	 */
	public OrganTree getOrganTree(int organSystemId, int topOrgan) {		
	/*	OrganTree cachedTree=cache.getOrganTree(organSystemId, topOrgan);//find from cache first
		if(cachedTree!=null) {
			return cachedTree;
		}*/
		return getOrganTreeFromDB(organSystemId, topOrgan);	
	}
	
	public String getOrganTree_temp(int organSystemId, int topOrgan,String treeState,String nodeid) {		
		return organTreeDAO.listOrganTreeNodes_temp(organSystemId,topOrgan,treeState,nodeid);
	}
	
	public List getOrganTreeList_temp(int organSystemId, int topOrgan) {		
		return organTreeDAO.getOrganTreeList_temp(organSystemId,topOrgan);
	}
	
	/**
	 * 根据机构系统编码取得完整机构树
	 * 
	 * @param organSystemId
	 * @return
	 */
	public OrganTree getOrganTree(int organSystemId) {

		System.out.println("getTree "+organSystemId);
		
/*		OrganTree cachedTree=cache.getCompleteOrganTree(organSystemId);//find from cache first
		if(cachedTree!=null) {
			return cachedTree;
		}*/
		return getOrganTreeFromDB(organSystemId, -1);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage2.services.OrganTreeManager#getTopNode(int)
	 */
	public OrganTreeNode getTopNode(int organSystemId){
		
		return organTreeDAO.getTopNode(organSystemId);
	}




	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage2.services.OrganTreeManager#getLeaves(int, java.lang.String)
	 */
	public List getLeaves(int organSystemId, String subtreeRoot){
		return organTreeDAO.getLeaves(organSystemId, subtreeRoot);
	}

	private OrganTree getOrganTreeFromDB(int organSystemId, int topOrgan) {

		OrganSystem os=getOrganSystem(organSystemId);
		if(os==null) {
			return null;
		}
		/*
		 * ע�⣬listOrganTreeNodes���صĽڵ��б��ǰ�������ǰ�ӽ���ں��ź����
		 */
		List list;
		if(topOrgan!=-1) {
			list= organTreeDAO.listOrganTreeNodes(organSystemId,topOrgan);
		}else {
			list= organTreeDAO.listOrganTreeNodes(organSystemId);
		}
		if(list.size()==0) {
			return null;
		}
		//transform nodes vo to node model
		List modelList = new ArrayList();
		for(Iterator itr = list.iterator();itr.hasNext();){
			OrganTreeNode organTreeNode = (OrganTreeNode)itr.next();
			modelList.add(nodeVo2Model(organTreeNode));
		}
		OrganTree organTree = new OrganTree();
		organTree.setOrganSystem(os);
		buildTreeByModelList(modelList,organTree);
		cache.cacheOrganTree(organTree);//cache it
		
		return organTree;
	}
	
	private String getOrganTreeFromDB_temp(int organSystemId, int topOrgan) {

		/*OrganSystem os=getOrganSystem(organSystemId);
		if(os==null) {
			return null;
		}
		
		String aaa = null;
		if(topOrgan!=-1) {*/
		String aaa= organTreeDAO.listOrganTreeNodes_temp(organSystemId,topOrgan,null,null);
		/*}*/
		/*else {
			list= organTreeDAO.listOrganTreeNodes(organSystemId);
		}
		if(list.size()==0) {
			return null;
		}
		//transform nodes vo to node model
		List modelList = new ArrayList();
		for(Iterator itr = list.iterator();itr.hasNext();){
			OrganTreeNode organTreeNode = (OrganTreeNode)itr.next();
			modelList.add(nodeVo2Model(organTreeNode));
		}
		OrganTree organTree = new OrganTree();
		organTree.setOrganSystem(os);
		buildTreeByModelList(modelList,organTree);
		cache.cacheOrganTree(organTree);//cache it
		
		return organTree;*/
		return aaa;
	}
	
	protected OrganNode nodeVo2Model(OrganTreeNode organTreeNode) {
		OrganNode organNode = new OrganNode();
		organNode.setOrganId(organTreeNode.getNodeId());
		organNode.setOrganCode(organTreeNode.getOrganCode());
		organNode.setOriName(organTreeNode.getOriName());
		organNode.setAlias(organTreeNode.getAliasName());
		organNode.setSubTreeTag(organTreeNode.getSubTreeTag());	
		organNode.setBeginDate(organTreeNode.getBeginDate());
		organNode.setEndDate(organTreeNode.getEndDate());
		return organNode;
	}

	//build tree model
	//modeList节点对象按父节在前子结点在后排好序才能调用此方法
	protected void buildTreeByModelList(List modeList,OrganTree organTree) {

		Iterator itr = modeList.iterator();
		OrganNode topNode = (OrganNode)itr.next();
		organTree.setTopOrgan(topNode);
		while(itr.hasNext()){
			OrganNode node = (OrganNode)itr.next();
			String tag=node.getSubTreeTag();
			String parentTag=tag.substring(0,tag.length()-2);
			OrganNode parentNode = organTree.findNodeBySubTreeTag(parentTag);
			if(parentNode!=null) {
				parentNode.addChild(node);
			}
		}
	}

	/**
	 * 保存机构树
	 * 
	 * @param organTree
	 * @return
	 */
	public void saveOrganTree(OrganTree organTree) {

		System.out.println("saveOrganTree: ");
		long start=System.currentTimeMillis();

		organTree.resetNodesSubTreeTag();
		OrganSystem os=organTree.getOrganSystem();
		int organSystemId;
		if(os.getId()!=null) {
			organSystemId=os.getId().intValue();
			organTreeDAO.removeOrganTreeNodes(organSystemId);
		}
		saveOrganSystem(os);
		//如果是新的机构系统，调用saveOrganSystem之后os的id已经设上了
		organSystemId=os.getId().intValue();
		
		List nodeList=organTree.toNodeList();
		System.out.println("node's count: "+nodeList.size());
		List nodeVoList=nodeListToNodeVoList(nodeList,organSystemId);
		organTreeDAO.saveOrganTreeNodes(nodeVoList);
		
		OrganNode topOrgan=organTree.getTopOrgan();
		getOrganTreeFromDB(organSystemId, topOrgan.getOrganId());//需要加载机构起止日期等信息（更新缓存）
		
		long end=System.currentTimeMillis();
		//清除缓存  add by lxk 20090221
		cache.clearCache();
		System.out.println("save cost time: "+(end-start)+"ms");
		//TODO
	}
	

	protected List nodeListToNodeVoList(List modeList,int organSystemId) {
		List nodeVoList=new ArrayList();
		for(Iterator itr = modeList.iterator();itr.hasNext();){
			OrganNode organNode = (OrganNode)itr.next();
			OrganTreeNode otn=new OrganTreeNode();
			otn.setNodeId(organNode.getOrganId());
			otn.setParentId(organNode.getParentId());
			otn.setIdxId(organSystemId);
			otn.setAliasName(organNode.getAlias());
			otn.setSubTreeTag(organNode.getSubTreeTag());
			otn.setShowChild(1);//TODO:?
			nodeVoList.add(otn);
		}
		
		return nodeVoList;
	}
	
	/**
	 * 删除指定机构系统id的机构树
	 * @param organSystemId
	 * @return
	 */
	public void removeOrganTree(int organSystemId) {
		
		organTreeDAO.removeOrganSystem(organSystemId);
	}

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
	public List listOrganSystems(int userId, String date, int type) {
		
		List organSystemInfoList=organTreeDAO.listOrganSystems(userId, date, type);
		List organSystemList=new ArrayList();
		Iterator it=organSystemInfoList.iterator();
		while(it.hasNext()) {
			OrganSystemInfo osi=(OrganSystemInfo)it.next();
			OrganSystem os=new OrganSystem();
			OrganSystemVo2Model(osi,os);
			organSystemList.add(os);
		}
		
		return organSystemList;
	}	

	/**
	 * 列出指定用户可以查看、维护的机构系统，严格检查分组、地区和日期，即使是树的创建者
	 * 
	 * @param userId
	 * @param date
	 *            日期，此日期不在使用期限内的机构系统不列出来，格式yyyyMMdd
	 * @return {@link OrganSystem}对象列表
	 */
	public List listOrganSystems(int userId, String date) {
		return listOrganSystems(userId, date, 2);
	}

	/**
	 * 根据id取得机构系统
	 * 
	 * @param organSystemId
	 * @return
	 */
	public OrganSystem getOrganSystem(int organSystemId) {
		OrganSystem os=cache.getOrganSystem(organSystemId);
		if(os==null) {
			os=new OrganSystem();
			OrganSystemInfo osi= organTreeDAO.getOrganSystem(organSystemId);
			if(osi==null) {
				//TODO
			}
			OrganSystemVo2Model(osi,os);
			cache.updateOrganSystem(os);
		}
		return os;
	}
	
	private void OrganSystemVo2Model(OrganSystemInfo osi,OrganSystem os) {

		os.setId(osi.getPkid());
		os.setName(osi.getName());
		os.setGroupId(osi.getGroupId());
		os.setAreaId(osi.getAreaId());
		os.setVisibility(osi.getIsPublic());
		os.setStatus(osi.getStatus());
		os.setCreatorId(osi.getUserId());
		os.setBeginDate(osi.getBeginDate());
		os.setEndDate(osi.getEndDate());
	}
	
	private void OrganSystemModel2Vo(OrganSystem os,OrganSystemInfo osi) {

		osi.setPkid(os.getId());
		osi.setName(os.getName());
		osi.setGroupId(os.getGroupId());
		osi.setAreaId(os.getAreaId());
		osi.setIsPublic(os.getVisibility());
		osi.setStatus(os.getStatus());
		osi.setUserId(os.getCreatorId());
		osi.setBeginDate(os.getBeginDate());
		osi.setEndDate(os.getEndDate());
	}

	/**
	 * 保存机构系统信息。如果保存新的机构系统，调用之后它的id已经给设上
	 * 
	 * @param organSystem
	 * @return
	 */
	public boolean saveOrganSystem(OrganSystem organSystem) {

		OrganSystemInfo osi=new OrganSystemInfo();
		OrganSystemModel2Vo(organSystem,osi);
		organTreeDAO.saveOrganSystem(osi);
		
		if(organSystem.getId()==null) {
			organSystem.setId(osi.getPkid());
		}else {
			cache.updateOrganSystem(organSystem);
		}
		
		return true;
	}

	public void sort(String list) {
		if(null != list){
			OrganSystemInfo type = null;
			String[] orders = list.split(",");
			for(int i = 0; i < orders.length; i++){
				Object o = organTreeDAO.getObject(OrganSystemInfo.class,new Integer(orders[i]));
				if(null != o){
					type = (OrganSystemInfo)o;
					type.setShowOrder(i);
					organTreeDAO.saveObject(type);
				}
			}
		}
	}
	
	
	//==================

	
	/**
	 * 本测试用例可以根据机构树表(code_org_tree)中的nodeid和parentid的关系整理subtreetag
	 * 本测试用例包含两个子过程updateSubtreetag(int, String, String)和recoverSubtreetag(int, String, String)
	 * 首先执行recoverSubtreetag(int, String, String)，备份当前的subtreetag。
	 * 然后执行updateSubtreetag(int, String, String)，修改subtreetag关系。
	 * 执行结果将写入两个文件中（fileName_update和fileName_recover）。
	 * 在数据库中执行fileName_update，以达到更新subtreetag的目的。如果出现意外，可以执行fileName_recover以恢复原貌。
	 */
	public void buildSubtreetag(int idxid, String rootId, String pattern){
		String fileName_recover =  System.getProperty("java.io.tmpdir") + File.separator + "recover_code_org_tree_subtreetag.sql";
		System.out.println("receover sql saved at [" + fileName_recover + "]");
		String fileName_update = System.getProperty("java.io.tmpdir") + File.separator + "update_code_org_tree_subtreetag.sql";
		System.out.println("update sql saved at [" + fileName_update + "]");
		recoverSubtreetag(idxid, rootId, fileName_recover);
		updateSubtreetag(idxid, rootId, pattern, fileName_update);
		System.out.println("Finished!");
	}
	
	/**
	 * 产生更新subtreetag的sql脚本
	 * @param organSystemId	树id
	 * @param parentid	根节点id
	 * @param subtreeTag	要更新的子树模式
	 */
	private void updateSubtreetag(int organSystemId, String parentid, String subtreeTag, String fileName){
		List subNodes = organTreeDAO.getSubnode(organSystemId, parentid);
		Iterator itr = subNodes.iterator();
		int cnt = 1;
		String fileName_update = fileName;
		while(itr.hasNext()){
			OrganTreeNode node = (OrganTreeNode)itr.next();
			String st = "";
			if (cnt < 10) {
				st = subtreeTag + "0" + cnt;
			} else {
				st = subtreeTag + cnt;
			}
			try {
				FileWriter fr=new FileWriter(fileName_update, true);
				fr.write("update code_org_tree set subtreetag = '" + st + "' where pkid = " + node.getPkid() + ";\n");
				fr.close();
			} catch (IOException e) {
			}
			updateSubtreetag(organSystemId, String.valueOf(node.getNodeId()), st, fileName); 
			cnt++;
		}
	}
	
	/**
	 * 产生备份subtreetag的sql脚本
	 * @param organSystemId	树id
	 * @param parentid	根节点id
	 * @param subtreeTag	要更新的子树模式
	 */
	private void recoverSubtreetag(int organSystemId, String parentid, String fileName){
		List subNodes = organTreeDAO.getSubnode(organSystemId, parentid);
		Iterator itr = subNodes.iterator();
		int cnt = 1;
		String fileName_recover = fileName;
		while(itr.hasNext()){
			OrganTreeNode node = (OrganTreeNode)itr.next();
			try {
				FileWriter fr=new FileWriter(fileName_recover, true);
				fr.write("update code_org_tree set subtreetag = '" + node.getSubTreeTag() + "' where pkid = " + node.getPkid() + ";\n");
				fr.close();
			} catch (IOException e) {
			}
			recoverSubtreetag(organSystemId, String.valueOf(node.getNodeId()), fileName); 
			cnt++;
		}
	}

}
