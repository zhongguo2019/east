package com.krm.ps.sysmanage.organmanage2.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 表示机构树的一个机构节点
 * 
 */
public class OrganNode implements Cloneable {

	private int organId;//nodeId

	private String organCode;//code

	private String subTreeTag;

	private String areaId;

	private int groupId;

	private String oriName = "";

	private String alias;

	private OrganNode parent = null;

	private List children = null;

	private String beginDate;

	private String endDate;

	public static char[] tagSym = new char[18];

	static {
		int n0 = '0';
		for (int i = 0; i < 10; i++) {
			tagSym[i] = (char) (n0 + i);
		}
		int c0 = 'A';
		for (int i = 10; i < 18; i++) {
			tagSym[i] = (char) (c0 + i);
		}
	}

	/**
	 * 机构id
	 */
	public int getOrganId() {
		return organId;
	}

	public void setOrganId(int id) {
		this.organId = id;
	}

	/**
	 * 机构编码
	 */
	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	/**
	 * 机构原始名称
	 */
	public String getOriName() {
		return oriName;
	}

	public void setOriName(String oriName) {
		if(oriName!=null) {
			this.oriName = oriName;
		}
	}

	/**
	 * 机构（在机构树中的）别名
	 */
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		if(oriName.equals(alias)) {
			this.alias = null;
		}else {
			this.alias = alias;
		}
	}

	/**
	 * 机构名称
	 */
	public String getName() {
		if (alias == null || alias.equals("")) {
			return oriName;
		}
		return alias;
	}

	/**
	 * 父机构，如果没有则为null
	 */
	public OrganNode getParent() {
		return parent;
	}

	public void setParent(OrganNode parent) {
		this.parent = parent;
	}

	/**
	 * 父机构id
	 */
	public int getParentId() {
		return (parent==null)? 0:parent.organId;
	}

	/**
	 * 父机构organCode
	 */
	public String getParentCode() {
		return (parent==null)? "":parent.organCode;
	}

	/**
	 * 子机构列表，如果没有子机构则为空
	 */
	public List getChildren() {
		return children;
	}

	public void setChildren(List children) { this.children = children; }

	/**
	 * 增加子机构
	 * 
	 * @param children
	 */
	public void addChild(OrganNode child) {
		if (children == null) {
			children = new ArrayList();
		}
		children.add(child);
		child.parent=this;
	}

	/**
	 * 分组id
	 */
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * 机构所在地区id
	 */
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	/*
	 * 启用日期
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/*
	 * 截至日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 得到此机构简单信息
	 */
	public OrganSimpleInfo getOrganSimpleInfo() {
		return new OrganSimpleInfo(organId, organCode, getName(), areaId);
	}

	/**
	 * 子树编码，标记节点在树中的位置。下级机构的子树编码以父机构的子树编码开头。
	 */
	public String getSubTreeTag() {
		return subTreeTag;
	}

	/**
	 * 在保存机构树时重设此值。此属性值要求维护，查询机构树时依赖此值
	 * 
	 * @param subTreeTag
	 */
	public void setSubTreeTag(String subTreeTag) {
		this.subTreeTag = subTreeTag;
	}

	/**
	 * 根据子树编码查找树节点
	 * 
	 * @param tag
	 * @return
	 */
	OrganNode findNodeBySubTreeTag(String tag) {

		if (tag.startsWith(subTreeTag)) {
			if (tag.equals(subTreeTag)) {
				return this;
			} else {
				if (getChildren() != null) {
					Iterator it = children.iterator();
					while (it.hasNext()) {
						OrganNode subNode = (OrganNode) it.next();
						// 在子树查找
						OrganNode node=subNode.findNodeBySubTreeTag(tag);
						if(node!=null) {
							return node;
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * 根据机构id查找树节点
	 * 
	 * @param organId
	 * @return
	 */
	OrganNode findNodeByOrganId(int organId) {

		LinkedList nodes = new LinkedList();
		nodes.add(this);
		// 广度优先
		while (nodes.size() > 0) {
			OrganNode node = (OrganNode) nodes.remove(0);
			if (node.getOrganId()==organId) {
				return node;
			} else {
				if (node.getChildren() != null) {
					nodes.addAll(node.getChildren());
				}
			}
		}

		return null;
	}

	/**
	 * 根据机构编码查找树节点
	 * 
	 * @param code
	 * @return
	 */
	OrganNode findNodeByOrganCode(String code) {

		LinkedList nodes = new LinkedList();
		nodes.add(this);
		while (nodes.size() > 0) {
			OrganNode node = (OrganNode) nodes.remove(0);
			String theCode=node.getOrganCode();
			if (theCode!=null&&theCode.equals(code)) {
				return node;
			} else {
				if (node.getChildren() != null) {
					nodes.addAll(node.getChildren());
				}
			}
		}

		return null;
	}

	/**
	 * 更新子节点
	 * 
	 * @param child
	 */
	public void updateChild(OrganNode child) {

		if (children == null) {
			return;
		}
		for (int i = 0; i < children.size(); i++) {
			OrganNode node = (OrganNode) children.get(i);
			if (node.getOrganId()==child.getOrganId()) {
				children.set(i, child);
				return;
			}
		}
	}

	/**
	 * 重设子节点的子树编码。
	 * 
	 */
	void resetSubNodesTag() {

		if (children == null) {
			return;
		}
		Iterator it = children.iterator();
		for (int idx = 0; it.hasNext(); idx++) {
			OrganNode subNode = (OrganNode) it.next();
			String tag = subTreeTag;
			//如果数组越界，则需要扩展（增大tagSym）
			tag += tagSym[(idx + 1) / tagSym.length];
			tag += tagSym[(idx + 1) % tagSym.length];
			subNode.setSubTreeTag(tag);
			subNode.resetSubNodesTag();
		}
	}
	
	/*
	 * 根据可用日期判断是否失效
	 */
	boolean isValid(String date) {
		if(date==null) {
			return true;
		}
		if(beginDate==null||endDate==null) {
			return false;
		}
		if(beginDate.compareTo(date)<=0&&date.compareTo(endDate)<=0) {
			return true;
		}
		return false;
	}


	/**
	 * deep clone
	 */
	public Object clone() throws CloneNotSupportedException {
		
		OrganNode newNode=new OrganNode();
		copyPropertiesTo(newNode);
		if(children!=null) {
			Iterator it = children.iterator();
			for (int idx = 0; it.hasNext(); idx++) {
				OrganNode subNode = (OrganNode) it.next();
				newNode.addChild((OrganNode)subNode.clone());
			}
		}
		return newNode;
	}

	//对机构的撤销时间进行判断，如果已过期，将不显示这个机构，根据报表机构树的层次为四层，
	//我们只判断了四层，判断每一层下是否有子节点，如果有子节点，获取子节点对时间进行判断   2012/9/25   杨会
OrganNode cloneValid(String date) {
		
		OrganNode newNode=new OrganNode();
		copyPropertiesTo(newNode);
		if(children!=null) {
			Iterator it = children.iterator();
			for (int idx = 0; it.hasNext(); idx++) {
				OrganNode subNode = (OrganNode) it.next();
				if(subNode.isValid(date)) {
					//判断当前机构是否存在子节点
					if(subNode.getChildren()!=null){
						Iterator cits = subNode.getChildren().iterator();
						OrganNode newNode1=new OrganNode();
						subNode.copyPropertiesTo(newNode1);
						while(cits.hasNext()){
							OrganNode subNodech=(OrganNode)cits.next();
							if(subNodech.isValid(date)) {
								//判断当前机构是否存在子节点
									 if(subNodech.getChildren()!=null){
										 Iterator cit = subNodech.getChildren().iterator();
											OrganNode newNode2=new OrganNode();
											subNodech.copyPropertiesTo(newNode2);
											while(cit.hasNext()){
												OrganNode subNodeche=(OrganNode)cit.next();
												if(subNodeche.isValid(date)) {
												 newNode2.addChild(subNodeche); 
												}
											}
											
											try {
												newNode1.addChild((OrganNode)newNode2.clone());
											}catch(CloneNotSupportedException e) {
												newNode1.addChild(newNode2);
											}
									 }else{
										 newNode1.addChild(subNodech); 
									 }
							}
						}
						
						try {
							newNode.addChild((OrganNode)newNode1.clone());
						}catch(CloneNotSupportedException e) {
							newNode.addChild(newNode1);
						}
					}else{			
						try {
							newNode.addChild((OrganNode)subNode.clone());
						}catch(CloneNotSupportedException e) {
							newNode.addChild(subNode);
						}
					}
				}
			}
		}
		return newNode;
	}
	
	private void copyPropertiesTo(OrganNode newNode) {
		newNode.organId=this.organId;
		newNode.organCode=this.organCode;
		newNode.areaId=this.areaId;
		newNode.groupId=this.groupId;
		newNode.subTreeTag=this.subTreeTag;
		newNode.oriName=this.oriName;
		newNode.alias=this.alias;
		newNode.beginDate=this.beginDate;//
		newNode.endDate=this.endDate;//
	}
	
	public String toString() {
		StringBuffer s=new StringBuffer();
		s.append(organId).append("\t").append(organCode).append("\t")
		.append(getName()).append("\t").append(subTreeTag).append("\t")
		.append(beginDate).append("-").append(endDate).append("\t")
		.append(Integer.toHexString(super.hashCode())).append("\n");
		return s.toString();
	}

}
