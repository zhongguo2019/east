package com.krm.ps.sysmanage.organmanage2.services.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Hibernate;

import com.krm.ps.sysmanage.organmanage2.util.DisBean;
import com.krm.ps.sysmanage.organmanage.dao.OrganInfoDAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.dao.OrganInfoDAO2;
import com.krm.ps.sysmanage.organmanage2.model.DepartmentSimpleInfo;
import com.krm.ps.sysmanage.organmanage2.model.OrganNode;
import com.krm.ps.sysmanage.organmanage2.model.OrganSimpleInfo;
import com.krm.ps.sysmanage.organmanage2.model.OrganTree;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.organmanage2.services.OrganTreeManager;
import com.krm.ps.sysmanage.organmanage2.vo.ItemTreeDataBean;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.TreeNodeAttributeReader;

/**
 * 机构管理服务。get*Organs方法返回的是{@link OrganInfo}对象的列表，从效率上考虑，如果不需要机构的详细信息，可以使用相应的get*OrgansSimpleInfo方法代替，该方法返回{@link OrganSimpleInfo}对象的列表。
 * 取机构列表和xml的方法都要求传入日期，以保证返回的每个机构都在可用期限之内
 * 
 */
public class OrganService2Impl implements OrganService2 {
	
	private OrganInfoDAO2 organInfoDAO;
	
	private OrganTreeManager organTreeManager;
	
	private OrganInfoDAO oiDAO;
	
	public void setOrganInfoDAO(OrganInfoDAO2 organInfoDAO) {
		this.organInfoDAO=organInfoDAO;
	}
	
	public void setOrganTreeManager(OrganTreeManager organTreeManager) {
		this.organTreeManager=organTreeManager;
	}
	
	public void setOiDAO(OrganInfoDAO obj){
		this.oiDAO = obj;
	}
	
	private static TreeNodeAttributeReader inar=new TreeNodeAttributeReader() {

		public String getId(Object node) {
			return ((OrganNode)node).getOrganCode();
		}
		public String getName(Object node) {
			return ((OrganNode)node).getName();
		}	
		public String getOrder(Object node) {
			return ((OrganNode)node).getSubTreeTag();
		}
		public String getParent(Object node) {
			return ((OrganNode)node).getParentCode();
		}
	};
	
	/**
	 * 根据organCode查询nodeid
	 */
	public String getnodeid(String organCode,int idx)
	{
		return this.organInfoDAO.getnodeid(organCode,idx);
		
	}
	
	/**
	 * 获得整棵树的organCode
	 * @author LC
	 */
	public List getAllOrganCode(int organSystemId, int topOrgan, String date)
	{
		OrganTree tree=organTreeManager.getOrganTree(organSystemId, topOrgan);
		if(tree!=null) {
			tree=tree.buildTreeByDate(date);
		}
		if(tree==null) {
			tree=emptyTree();
		}
		List nodeList=tree.toNodeList();
		List allOrganCode = new ArrayList();
		if(nodeList.size()!=0)
		{
			for(int i=0;i<nodeList.size();i++)
			{
				OrganNode orgnode = (OrganNode)nodeList.get(i);
				allOrganCode.add(orgnode.getOrganCode());
			}
		}
		
		return allOrganCode;
	}
	
	/**
	 * 取得指定机构树的xml,用于树控件显示
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return
	 */
	public String getOrganTreeXML(int organSystemId, int topOrgan,
			String date) {
		
		OrganTree tree=organTreeManager.getOrganTree(organSystemId, topOrgan);
		if(tree!=null) {
			tree=tree.buildTreeByDate(date);
		}
		if(tree==null) {
			tree=emptyTree();
		}
		List nodeList=tree.toNodeList();		

//		String xml=ConvertUtil.convertListToAdoXml(nodeList,new String[]{"organCode",
//			"name","subTreeTag", "parentCode" });
		String xml=ConvertUtil.convertListToAdoXml(nodeList,inar);
		
		return xml;
	}
	
	/**
	 * @see com.krm.slsint.organmanage2.services.OrganService2#getSubOrganNodeList(int, java.lang.String, java.lang.String)
	 */
	public List getSubOrganNodeList(int organTreeIdxid, int topOrgan, String date)
	{
		OrganTree tree=organTreeManager.getOrganTree(organTreeIdxid, topOrgan);
		if(tree!=null) {
			tree=tree.buildTreeByDate(date);
		}
		if(tree==null) {
			tree=emptyTree();
		}
		List nodeList=tree.toNodeList();
		return nodeList;
	}
	
	/**
	 * 取得指定机构树的xml,用于树控件显示(天津专用,不带501机构)
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return
	 */
	public String getOrgTreeXML(int organSystemId, int topOrgan,
			String date, Integer organType) {
		
		OrganTree tree=organTreeManager.getOrganTree(organSystemId, topOrgan);
		Map organMap = oiDAO.getOrganByType(organType, date);
		OrganNode root = tree.getTopOrgan();
		if(root.getChildren() != null) {
			removeOrgan(root.getChildren(), organMap);
		}
		if(tree!=null) {
			tree=tree.buildTreeByDate(date);
		}
		if(tree==null) {
			tree=emptyTree();
		}
		List nodeList=tree.toNodeList();		

//		String xml=ConvertUtil.convertListToAdoXml(nodeList,new String[]{"organCode",
//			"name","subTreeTag", "parentCode" });
		String xml=ConvertUtil.convertListToAdoXml(nodeList,inar);
		
		return xml;
	}
	
	public OrganTree filtOrganTree(OrganTree organTree, Integer organType, String date) {
		Map organMap = oiDAO.getOrganByType(organType, date);
		OrganNode root = organTree.getTopOrgan();
		if(root.getChildren() != null) {
			removeOrgan(root.getChildren(), organMap);
		}
		return organTree;
	}
	
	private void removeOrgan(List nodeList, Map organMap){
		for(int i = 0; i < nodeList.size(); i++){
			OrganNode node = (OrganNode)nodeList.get(i);
			OrganInfo organ = (OrganInfo)organMap.get(node.getOrganCode());
			if(organ != null) {
				nodeList.remove(i);
				i--;
			}else{
				if(node.getChildren() != null){
					removeOrgan(node.getChildren(), organMap);
				}
			}
		}
	}
	
	private OrganTree emptyTree() {
		OrganTree et=new OrganTree();
		OrganNode on=new OrganNode();
		on.setAlias("��û�л�");
		et.setTopOrgan(on);
		return et;
	}

	/**
	 * 取得下一级机构列表
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getSubOrgans(int organSystemId, int topOrgan, String date) {
		return organInfoDAO.getSubOrgans(organSystemId, topOrgan, date);
	}
	
	/**
	 * @see com.krm.slsint.organmanage2.services.OrganService2#gerAllLeafOrgans(int, int, java.lang.String)
	 */
	public List getAllLeafOrgans(int organSystemId, String organCode, String date){
	    OrganInfo oi = oiDAO.getOrganByCode(organCode);
        int pkid = oi.getPkid().intValue();
	    return organInfoDAO.getAllLeafOrgans(organSystemId, pkid, date);
	}

	public List getSubOrgans(int organSystemId,String organCode, String date) {
		OrganInfo oi = oiDAO.getOrganByCode(organCode);
		int pkid = oi.getPkid().intValue();
		
		return getSubOrgans(organSystemId, pkid, date);
	}

	/**
	 * 取得所有下层机构（可能有多层）
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getAllSubOrgans(int organSystemId, int topOrgan, String date) {
		return organInfoDAO.getAllSubOrgans(organSystemId, topOrgan, date);
	}

	public List getAllSubOrgans(int organSystemId, String organCode, String date) {
		OrganInfo oi = oiDAO.getOrganByCode(organCode);
		if(oi!=null){
			int pkid = oi.getPkid().intValue();
			return getAllSubOrgans(organSystemId, pkid, date);
		}else
			return new ArrayList();
	}
	
	/**
	 * 取得所有下层机构（可能有多层）,按照机构级别排序。
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getAllSubOrgansByGrade(int organSystemId, int topOrgan, String date) {
		return organInfoDAO.getAllSubOrgansByGrade(organSystemId, topOrgan, date);
	}
	public List getAllSubOrgansByGrade(int organSystemId, String organCode, String date) {
		OrganInfo oi = oiDAO.getOrganByCode(organCode);
		int pkid = oi.getPkid().intValue();
		return getAllSubOrgansByGrade(organSystemId, pkid, date);
	}


	/**
	 * ȡ��ָ��������¼���
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param layer
	 * @param date
	 *            ����1��ʾ��һ��
	 * @return {@link OrganInfo}�����б�
	 */
	public List getSubOrgans(int organSystemId, int topOrgan, int layer,
			String date) {
		return organInfoDAO.getSubOrgans(organSystemId, topOrgan, layer, date);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage2.services.OrganService2#getAppointLayerOrgans(int, int, int, java.lang.String)
	 */
	public List getAppointLayerOrgans(int organSystemId, int topOrgan, int layer, String date) {
		return organInfoDAO.getAppointLayerOrgans(organSystemId, topOrgan, layer, date);
	}
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
	public List getSubOrgansSimpleInfo(int organSystemId, int topOrgan,
			String date) {
		
		OrganTree tree=organTreeManager.getOrganTree(organSystemId, topOrgan);
		tree=tree.buildTreeByDate(date);
		if(tree==null) {
			return new ArrayList(0);
		}
		return tree.toOrganInfoList(1);//1表示下一级（包含本级）
	}

	public List getSubOrgansSimpleInfo(int organSystemId, String organCode,
			String date) {
		OrganInfo oi = oiDAO.getOrganByCode(organCode);
		int pkid = oi.getPkid().intValue();
		return getSubOrgansSimpleInfo(organSystemId, pkid, date);
	}

	/**
	 * 取得下一级机构列表
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List getAllSubOrgansSimpleInfo(int organSystemId, int topOrgan,
			String date) {
		OrganTree tree=organTreeManager.getOrganTree(organSystemId, topOrgan);
		tree=tree.buildTreeByDate(date);
		if(tree==null) {
			return new ArrayList(0);
		}
		return tree.toOrganInfoList();
	}

	public List getAllSubOrgansSimpleInfo(int organSystemId, String organCode,
			String date) {
		OrganInfo oi = oiDAO.getOrganByCode(organCode);
		int pkid = oi.getPkid().intValue();
		return getAllSubOrgansSimpleInfo(organSystemId, pkid, date);
	}
	
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
			int layer, String date) {
		return getSubOrgansSimpleInfo(organSystemId,topOrgan,layer,date,0);
	}

	/**
	 * 取得指定层数的下级机构
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param layer 层数：-1表示所有级别的下级（包含本级）,1表示下一级（包含本级）,2表示下两级（包含本级、下一级）
	 * @param date
	 * @param topLevel 上级机构层级 add by safe at 2007.11.27
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List getSubOrgansSimpleInfo(int organSystemId, int topOrgan,
			int layer, String date, int topLevel) {
		OrganTree tree=organTreeManager.getOrganTree(organSystemId, topOrgan);
		//TODO:效率
		tree=tree.buildTreeByDate(date);
		if(tree==null) {
			return new ArrayList(0);
		}
		//return tree.toOrganInfoList(layer);
		//modify by safe at 2007.11.29 for 增加了机构的N级上级机构信息
		List mainOrganList = tree.toOrganInfoList(layer);
		
		//构造N级上级机构列表
		LinkedList topOrgList = new LinkedList();
		buildTopOrganList(topOrgList,organSystemId,topOrgan,date,topLevel,null);
		
		//获得机构类型对照属性对象
		Properties orgTypePro = initOrgTypePro();
		
		if (topOrgList.size() == 0){
			//添加机构类型信息
			addOrgTypeInfo(mainOrganList,orgTypePro);
			return mainOrganList;
		}
		
		//将上级机构按照顺序放在机构列表前
		List resultList = new ArrayList();
		resultList.addAll(topOrgList);
		resultList.addAll(mainOrganList);
		
		//添加机构类型信息
		addOrgTypeInfo(resultList,orgTypePro);
		
		return resultList;
	}
	/**
	 * 构造当前机构的N级上级机构列表
	 * @param organList 机构结果列表
	 * @param organSystemId 
	 * @param orgID 
	 * @param date
	 * @param level 机构层数
	 * @param orgRltPro 机构上下级对照属性对象
	 */
	private void buildTopOrganList(LinkedList organList,int organSystemId,int orgID
			,String date, int level, Properties orgRltPro) {
		if (level < 1){
			return;
		}
		
		if (orgRltPro == null){
			orgRltPro = initOrgRelationPro();
		}
		
		//获取上级机构编码
		String topOrgID = orgRltPro.getProperty(String.valueOf(orgID));
		if (topOrgID == null){
			return;
		}
		int iTopOrgID = Integer.parseInt(topOrgID);
		
		//获取上级机构信息，放入结果列表
		OrganTree tree = organTreeManager.getOrganTree(organSystemId, iTopOrgID);
		tree = tree.buildTreeByDate(date);
		if(tree == null) {
			return;
		}
		List topOrgList = tree.toOrganInfoList(0);

		if (topOrgList.size() > 0){
			organList.addFirst(topOrgList.get(0));
		}
		
		buildTopOrganList(organList,organSystemId,iTopOrgID,date,--level,orgRltPro);
	}
	
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
			int layer, String date) {
		OrganInfo oi = oiDAO.getOrganByCode(organCode);
		int pkid = oi.getPkid().intValue();
		return getSubOrgansSimpleInfo(organSystemId, pkid,	layer, date);
	}

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
			int layer, String date, int topLevel) {
		OrganInfo oi = oiDAO.getOrganByCode(organCode);
		int pkid = oi.getPkid().intValue();
		return getSubOrgansSimpleInfo(organSystemId, pkid,	layer, date, topLevel);
	}

	/**
	 * 得到机构树管理服务
	 * 
	 * @return
	 */
	public OrganTreeManager getOrganTreeManager() {
		return organTreeManager;
	}
	
	/**
	 * 取得指定机构树的xml,用于树控件显示
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return
	 */
	public String getOrganTreeXML(int organSystemId, String organCode, String date) {
		OrganInfo oi = oiDAO.getOrganByCode(organCode);
		int pkid = oi.getPkid().intValue();
		return getOrganTreeXML(organSystemId,pkid,date);
	}

	public Object[] getOrganTreeModelAndXML(int organSystemId, int topOrgan, String date) {
		return null;
	}
	
	/**
	 * 初始化机构类型信息
	 * @return Properties
	 */
	private Properties initOrgTypePro() {
		Properties orgTypePro = new Properties();
		//获取所有机构的类型信息
		List orgTypeList = organInfoDAO.getOrganTypeList();
		if (orgTypeList == null){
			return orgTypePro;
		}
		Object [] o = null; //(机构编码,机构类型编码)
		for (int i = 0 ; i < orgTypeList.size() ; i++){
			o = (Object [])orgTypeList.get(i);
			orgTypePro.setProperty((String)o[0],((Integer)o[1]).toString());
		}
		return orgTypePro;
	}
	
	/**
	 * 初始化机构上下级关系类型信息
	 * @return Properties
	 */
	private Properties initOrgRelationPro() {
		Properties orgRelationPro = new Properties();
		//获取所有机构的上下级对照信息
		List orgRltList = organInfoDAO.getOrganRelationList();
		if (orgRltList == null){
			return orgRelationPro;
		}
		Object [] o = null; //(机构编码,上级机构编码)
		for (int i = 0 ; i < orgRltList.size() ; i++){
			o = (Object [])orgRltList.get(i);
			orgRelationPro.setProperty((String)o[0],(String)o[1]);
		}
		return orgRelationPro;
	}
	
	/**
	 * 添加机构类型信息
	 * @param orgInfoList 机构信息列表
	 * @param orgTypePro 机构类型对照信息
	 */ 
	private void addOrgTypeInfo(List orgInfoList,Properties orgTypePro) {
		OrganSimpleInfo osi = null;
		for (int i = 0 ; i < orgInfoList.size() ; i++){
			osi = (OrganSimpleInfo)orgInfoList.get(i);
			osi.setType(orgTypePro.getProperty(osi.getCode()));
		}
	}

	/**
	 * 得到最小TreeId的机构树
	 * @param date
	 * @return
	 */
	public int getMinOrganTreeId(String date)
	{
		return organInfoDAO.getMinOrganTreeId(date);
	}


	public boolean isTreeNode(Integer treeId, Long nodeId)
	{
		return organInfoDAO.isTreeNode(treeId, nodeId);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage2.services.OrganService2#getOrganByCode(java.lang.String)
	 */
	public OrganInfo getOrganByCode(String code){
		return organInfoDAO.getOrganByCode(code);
	}

	public List getLeavesOrganInfo(int organSystemId, String subtreeRootOrgCode){
		return organInfoDAO.getLeavesOrganInfo(organSystemId, subtreeRootOrgCode);
	}

	public List getTrunkOrganList(int treeId, String topOrganCode, String date){
		return organInfoDAO.getTrunkOrganList(treeId, topOrganCode, date);
	}

	public String getPreOrgan(int organSystemId, String organCode) {
		
		return organInfoDAO.getPreOrgan(organSystemId, organCode);
	}

	/**
	 * @see com.krm.slsint.organmanage2.services.OrganService2#gerOrgAndDepartmentXML(int, int, java.lang.String)
	 */
	public String gerOrgAndDepartmentXML(int organTreeIdxid, int organId, String date)
	{
		OrganTree tree=organTreeManager.getOrganTree(organTreeIdxid, organId);
		if(tree!=null) {
			tree=tree.buildTreeByDate(date);
		}
		if(tree==null) {
			tree=emptyTree();
		}
		List nodeList=tree.toNodeList();		
		// 把每个机构的编码重新进行下处理：改为O:<organCode>，同时把部门加入
		// 部门对应ID的格式为：D:<deptId>:<organCode>
		List nodeListNew = new ArrayList();
		List deptList = organInfoDAO.getDepartmentSimpleInfoList();
		int nodeSize = nodeList.size();
		OrganNode node;
		DepartmentSimpleInfo deptSimpleInfo;
		int deptSize = deptList.size();
		OrganNode tmpNode;
		String tmpOrganCode;
		for (int i = 0; i < nodeSize; i++)
		{
			node = (OrganNode)nodeList.get(i);
			tmpOrganCode = node.getOrganCode();
			node.setOrganCode("O:" + tmpOrganCode);
			nodeListNew.add(node);
			// 把部门信息加上
			for (int j = 0; j < deptSize; j++)
			{
				deptSimpleInfo = (DepartmentSimpleInfo)deptList.get(j);
				tmpNode = new OrganNode();
				tmpNode.setParent(node);
				tmpNode.setOrganCode("D:" + tmpOrganCode + ":" + deptSimpleInfo.getPkid().toString());
				tmpNode.setOriName(deptSimpleInfo.getDeptName());
				nodeListNew.add(tmpNode);
			}
		}
		String xml=ConvertUtil.convertListToAdoXml(nodeListNew,inar);
		return xml;
	}
	
	/**
	 * @see com.krm.slsint.organmanage2.services.OrganService2#getDepartmentSimpleInfoList()
	 */
	public List getDepartmentSimpleInfoList()
	{
		return organInfoDAO.getDepartmentSimpleInfoList();
	}

	/**
	 * @see com.krm.slsint.organmanage2.services.OrganService2#getRootOrganTreeNode(int)
	 */
	public OrganTreeNode getRootOrganTreeNode(int organTreeIdxid)
	{
		return organTreeManager.getTopNode(organTreeIdxid);
	}
	
	public List getJSONinitlazyTreeList(List organList,int organSystemId,String organId,String data) {
		List ans = new ArrayList();
		//判断机构list是否大于1,true则删除list中等于organId机构的对象
		if(organList.size()>1){
			for(int i=0;i<organList.size();i++){
				String[] orinfo = (String[])organList.get(i);
				if(orinfo[1].equals(organId)){
					organList.remove(i);
				}
			}
		}
		try {
			for(int i=0;i<organList.size();i++){
				String[] row = (String[])organList.get(i);
				ItemTreeDataBean bean = new ItemTreeDataBean();
				//序号
				bean.setDisid(row[0]);
				//名称
				bean.setDisname(row[2]);
				//金额
				bean.setMoney(row[3]);
				//机构编码
				bean.setOrganId(row[1]);
				//是否是叶子节点
				bean.setIsLeaf(isLeaf(organSystemId,row[1],data));
				ans.add(bean);
			}
		} catch (Exception e) {
		}
		return ans;
	}
	
	public List getJSONlazyTreeList(List organList,int organSystemId,String organId,String data) {
		List ans = new ArrayList();
		//判断机构list是否大于1,true则删除list中等于organId机构的对象
		if(organList.size()>1){
			for(int i=0;i<organList.size();i++){
				String[] orinfo = (String[])organList.get(i);
				if(orinfo[1].equals(organId)){
					organList.remove(i);
				}
			}
		}
		try {
			for(int i=0;i<organList.size();i++){
				String[] row = (String[])organList.get(i);
				ItemTreeDataBean bean = new ItemTreeDataBean();
				//序号
				bean.setDisid(row[0]);
				//名称
				bean.setDisname(row[2]);
				//设置父节点ID，organId为查询出来的机构的父ID
				bean.setDisparentId(organId);
				//金额
				bean.setMoney(row[3]);
				//机构编码
				bean.setOrganId(row[1]);
				//是否是叶子节点
				bean.setIsLeaf(isLeaf(organSystemId,row[1],data));
				ans.add(bean);
			}
		} catch (Exception e) {
		}
		return ans;
	}
	
	/**
	 * 判断是否叶子节点方法 返回1为有节点,0没有节点
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return
	 */
	public String isLeaf(int organSystemId,String organ,String date) {
		String dataDate = date.replaceAll("-", "").substring(0, 6) + "00";
		//根据机构ID获得机构信息
		OrganInfo organInfo = organInfoDAO.getOrganByCode(organ);
		//根据机构树、机构pkid、层级、日期获得机构list 
		//此方法得到的下级机构list会返回查询的机构对象本身 所以判断时要判断是否大于1
		List list = organInfoDAO.getSubOrgans(organSystemId,organInfo.getPkid().intValue(),1,dataDate);
		//如果list大于1,返回1,否则返回0
		if(list.size()>1){
			return "1";
		}else{
			return "0";
		}
	}
	
	public List getItemDataExcelList(int organSystemId, String organId, String date) {
		OrganInfo organInfo = getOrganByCode(organId);
		OrganTree tree=organTreeManager.getOrganTree(organSystemId, organInfo.getPkid().intValue());
		if(tree!=null) {
			tree=tree.buildTreeByDate(date);
		}
		if(tree==null) {
			tree=emptyTree();
		}
		List nodeList=tree.toNodeList();
		return nodeList;
	}
	
	public String getReportType(String reportId){
		return organInfoDAO.getReportType(reportId);
	}
	
	public OrganInfo getOrganById(String pkid){
		return organInfoDAO.getOrganById(pkid);
	}
	
	public String[] getAddresseeList(String[] organIds,String roleType){
		return organInfoDAO.getAddresseeList(organIds,roleType);
	}
	
	public String getTreeTagByNodeid(int organSystemId, int topOrgan){
		return organInfoDAO.getTreeTagByNodeid(organSystemId, topOrgan);
	}

	@Override
	public String getOrganTreeXML_temp(int organSystemId, int organId,
			String date,String treeState,String nodeid) {
		return organTreeManager.getOrganTree_temp(organSystemId, organId,treeState,nodeid);
	}
	
	public List getOrganTreeList_temp(int organSystemId, int organId,
			String date) {
		return organTreeManager.getOrganTreeList_temp(organSystemId, organId);
	}
	
}
