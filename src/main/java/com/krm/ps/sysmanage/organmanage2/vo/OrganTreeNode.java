package com.krm.ps.sysmanage.organmanage2.vo;

import java.io.Serializable;

import com.krm.ps.util.ConvertUtil;
import com.krm.ps.framework.vo.BaseObject;

/**
 * @hibernate.class table="code_org_tree"
 */
//public class OrganTreeNode extends BaseObject implements Serializable {
	public class OrganTreeNode  implements Serializable {
	private static final long serialVersionUID = 2360918665251634961L;

	private int pkid;

	private int idxId;// idxid

	private String organCode;//code

	private String areaId;

	/*
	 * nodeId和parentId关联机构表的机构pkid
	 */
	private int nodeId;// nodeid

	private int parentId;// parentid

	private String subTreeTag;// subtreetag

	private int showChild;// isshowchild

	private String aliasName;// aliasname

	private String oriName = "";

	private String description;

	private String beginDate;

	private String endDate;

	public int getPkid() {
		return pkid;
	}

	public void setPkid(int pkid) {
		this.pkid = pkid;
	}

	public int getIdxId() {
		return idxId;
	}

	public void setIdxId(int idxId) {
		this.idxId = idxId;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getSubTreeTag() {
		return subTreeTag;
	}

	public void setSubTreeTag(String subTreeTag) {
		this.subTreeTag = subTreeTag;
	}

	public int getShowChild() {
		return showChild;
	}

	public void setShowChild(int showChild) {
		this.showChild = showChild;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getOriName() {
		return oriName;
	}

	public void setOriName(String oriName) {
		this.oriName = oriName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	
	
	
	
	/*public OrganTreeNode(){
		
	}
	
	public OrganTreeNode(OrganTreeNode node){
		try {			
			ConvertUtil.copyProperties(this, node, true);
		} catch (Exception e) {
			;
		}
	}

	*//**
	 * @hibernate.property column="nodeid"
	 *//*
	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	*//**
	 * @hibernate.property column="parentid"
	 *//*
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	*//**
	 * @hibernate.property column="aliasname"
	 *//*
	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	*//**
	 * @hibernate.property column="description"
	 *//*
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	*//**
	 * ��ԭʼ���
	 *//*
	public String getOriName() {
		return oriName;
	}

	public void setOriName(String oriName) {
		this.oriName = oriName;
	}

	*//**
	 * @hibernate.property column="idxid"
	 *//*
	public int getIdxId() {
		return idxId;
	}

	public void setIdxId(int idxId) {
		this.idxId = idxId;
	}

	*//**
	 * �����
	 *//*
	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	*//**
	 * ��������
	 *//*
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	
	 * ��������
	 
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	public String getEndDate() {
		return endDate;
	}

	
	 * ��������
	 
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	*//**
	 * @hibernate.property column="isshowchild"
	 *//*
	public int getShowChild() {
		return showChild;
	}

	public void setShowChild(int showChild) {
		this.showChild = showChild;
	}

	*//**	
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_org_tree_seq"
	 *//*
	public int getPkid() {
		return pkid;
	}

	public void setPkid(int pkid) {
		this.pkid = pkid;
	}

	*//**
	 * @hibernate.property column="subtreetag"
	 *//*
	public String getSubTreeTag() {
		return subTreeTag;
	}

	public void setSubTreeTag(String subTreeTag) {
		this.subTreeTag = subTreeTag;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(pkid).append("\t").append(idxId).append("\t").append(nodeId).append("\t")
		.append(parentId).append("\t").append(subTreeTag).append("\t")
		.append(aliasName).append("\t")
		.append("\n");
		return s.toString();
	}

	public boolean equals(Object o) {
		//TODO
		return false;
	}

	public int hashCode() {
		return pkid;
	}*/
	
}
