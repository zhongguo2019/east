package com.krm.ps.sysmanage.organmanage2.model;

import java.util.List;

/**
 * 表示地区树的一个地区节点
 * 
 */
public class AreaNode {

	private String id;

	private String name;

	//private List organList;

	private AreaNode parent;

	private List children;

	/**
	 * 地区id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 地区名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 上级地区，如果没有则为null
	 */
	public AreaNode getParent() {
		return parent;
	}

	public void setParent(AreaNode parent) {
		this.parent = parent;
	}

	/**
	 * 下级地区列表，如果没有下级地区则为空
	 */
	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

//	/**
//	 * 此地区下的机构列表。List元素类型是{@link OrganSimpleInfo}
//	 */
//	public List getOrganList() {
//		return organList;
//	}
//
//	public void setOrganList(List organList) {
//		this.organList = organList;
//	}

}