package com.krm.ps.sysmanage.organmanage2.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 机构层次模型，可能是数据库完整机构树的一个子树
 * 
 */
public class OrganTree implements Cloneable {

	private OrganNode topOrgan;

	private OrganSystem organSystem;

	/**
	 * 取得此机构层次的顶级机构
	 */
	public OrganNode getTopOrgan() {
		return topOrgan;
	}

	public void setTopOrgan(OrganNode topOrgan) {
		this.topOrgan = topOrgan;
	}

	/**
	 * 此机构层次所属的机构系统
	 */
	public OrganSystem getOrganSystem() {
		return organSystem;
	}

	public void setOrganSystem(OrganSystem organSystem) {
		this.organSystem = organSystem;
	}

	/**
	 * 重设节点的子树编码（树顶层节点的不变）。
	 * 
	 */
	public void resetNodesSubTreeTag() {
		String topTag=topOrgan.getSubTreeTag();
		if(topTag==null||organSystem.getId()==null) {
			//给树的顶节点设值subTreeTag,使用两位随机字符
			char[] tagSymbol=OrganNode.tagSym;
			int idx1=(int)(tagSymbol.length*Math.random());
			int idx2=(int)(tagSymbol.length*Math.random());
			topTag=""+tagSymbol[idx1]+tagSymbol[idx2];
			topOrgan.setSubTreeTag(topTag);
		}
		topOrgan.resetSubNodesTag();
	}

	/**
	 * 根据子树编码查找树节点
	 * 
	 * @param tag
	 * @return
	 */
	public OrganNode findNodeBySubTreeTag(String tag) {
		return topOrgan.findNodeBySubTreeTag(tag);
	}

	/**
	 * 根据机构id查找树节点
	 * 
	 * @param tag
	 * @return
	 */
	public OrganNode findNodeByOrganId(int organId) {
		return topOrgan.findNodeByOrganId(organId);
	}

	/**
	 * 根据机构编码查找树节点
	 * 
	 * @param code
	 * @return
	 */
	public OrganNode findNodeByOrganCode(String code) {
		return topOrgan.findNodeByOrganCode(code);
	}
	
	/**
	 * 构造树节点列表
	 * @return
	 */
	public List toNodeList(){
		List nodeList=new ArrayList();
		appendNodeList(nodeList,topOrgan);
		return nodeList;
	}

	private void appendNodeList(List nodeList,OrganNode node){
		nodeList.add(node);
		if (node.getChildren() == null) {
			return;
		}
		Iterator it = node.getChildren().iterator();
		while (it.hasNext()) {
			OrganNode subNode = (OrganNode) it.next();
			appendNodeList(nodeList,subNode);
		}
	}

	/**
	 * 返回机构信息列表
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List toOrganInfoList(){
		List organInfoList=new ArrayList();
		appendOrganInfo(organInfoList,topOrgan,100);//TODO:��ȻMO�汾�����ܻ������ƣ�100��ֻ�ܱ��ص�˵���á�Ӧ�ø�Ϊȡ�����¼���
		return organInfoList;
	}

	/**
	 * 返回机构信息列表
	 * @param layer 层数：-1表示所有级别的下级（包含本级）,1表示下一级（包含本级）,2表示下两级（包含本级、下一级）
	 * @return {@link OrganSimpleInfo}对象列表
	 */
	public List toOrganInfoList(int layer){
		List organInfoList=new ArrayList();
		if (layer == -1) {// MO版本机构级别已经不再受限，所以设置-1作为标识，表明要取所有下级
			return toOrganInfoList();
		} else {
			appendOrganInfo(organInfoList, topOrgan, layer);
			return organInfoList;
		}
	}

	private void appendOrganInfo(List organInfoList,OrganNode node,int layersRemain){
		organInfoList.add(node.getOrganSimpleInfo());
		if (layersRemain<1||node.getChildren() == null) {
			return;
		}
		Iterator it = node.getChildren().iterator();
		while (it.hasNext()) {
			OrganNode subNode = (OrganNode) it.next();
			//应将layersRemain变量设置为非递减，否则只有第一个下级机构符合条件，modify by safe at 2008.2.22
			//appendOrganInfo(organInfoList,subNode,--layersRemain);
			appendOrganInfo(organInfoList,subNode,layersRemain - 1);
		}
	}
	
	/**
	 * 考虑机构节点启用、失效日期构建一个新的机构树，如果根结点的机构已失效返回null
	 * @param date
	 * @return
	 */
	public OrganTree buildTreeByDate(String date) {
		
		if(!organSystem.isValid(date)||!topOrgan.isValid(date)) {
			return null;
		}

		OrganTree newTree=new OrganTree();
		try {
			newTree.setOrganSystem((OrganSystem)organSystem.clone());
		}catch(CloneNotSupportedException e) {
			newTree.setOrganSystem(organSystem);
		}
		newTree.setTopOrgan(topOrgan.cloneValid(date));
		return newTree;
	}

	/**
	 * 克隆机构树，深度克隆
	 */
	public Object clone() throws CloneNotSupportedException {
		
		OrganTree newTree=new OrganTree();
		newTree.setOrganSystem((OrganSystem)organSystem.clone());
		newTree.setTopOrgan((OrganNode)topOrgan.clone());
		return newTree;
	}
	
	public String toString() {
		
		StringBuffer s=new StringBuffer();		
		appendToString(s,topOrgan,0);		
		return s.toString();
	}

	private void appendToString(StringBuffer s,OrganNode node,int layer){

		s.append("|");
		for(int i=0;i<layer;i++) {
			s.append("   ");
		}
		s.append("|___");
		s.append(node.toString());
		if (node.getChildren() == null) {
			return;
		}
		Iterator it = node.getChildren().iterator();
		while (it.hasNext()) {
			OrganNode subNode = (OrganNode) it.next();
			appendToString(s,subNode,layer+1);
		}
	}

//	// Help the GC
//	public void finalize() {
//		LinkedList nodes = new LinkedList();
//		nodes.add(topOrgan);
//		while (nodes.size() > 0) {
//			OrganNode node = (OrganNode) nodes.remove(0);
//			if (node.getChildren() != null) {
//				node.setChildren(null);
//				nodes.addAll(node.getChildren());
//			}
//		}
//		this.organSystem=null;
//		this.topOrgan=null;
//	}

}