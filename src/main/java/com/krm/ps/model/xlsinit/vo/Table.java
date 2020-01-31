package com.krm.ps.model.xlsinit.vo;

import java.io.Serializable;
import java.util.List;

public class Table implements Serializable{

	/**
	 * 存放xml中的表对象
	 */
	private static final long serialVersionUID = 8685288983113236462L;
	private String tableName;
	private List field;
	
	public Table(String name,List field) {
		super();
		// TODO Auto-generated constructor stub
		this.field = field;
		tableName = name;
	}
	public List getField() {
		return field;
	}
	public void setField(List field) {
		this.field = field;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
}
