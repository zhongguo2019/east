package com.krm.ps.framework.common.vo;

import java.io.Serializable;

public class TreeElement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String text;
	private int nodeid;
	private int parentid;
	private String subtreetag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getNodeid() {
		return nodeid;
	}
	public void setNodeid(int nodeid) {
		this.nodeid = nodeid;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public String getSubtreetag() {
		return subtreetag;
	}
	public void setSubtreetag(String subtreetag) {
		this.subtreetag = subtreetag;
	}
	
	

}
