package com.krm.ps.sysmanage.usermanage.bo;

import java.util.List;

public class Menu {

	private String id;

	private String name;

	private String resource;

	private Menu parent;
	
	private String pane;

	private List subMenus;
	
	private Long systemId;
	
	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List subMenus) {
		this.subMenus = subMenus;
	}

	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append(id);
		sb.append("\t");
		sb.append(name);
		sb.append("\t");
		sb.append(resource);
		sb.append("\n");
		sb.append(subMenus);
		sb.append("\n");
		return sb.toString();
	}

	public String getPane() {
		return pane;
	}

	public void setPane(String pane) {
		this.pane = pane;
	}
}
