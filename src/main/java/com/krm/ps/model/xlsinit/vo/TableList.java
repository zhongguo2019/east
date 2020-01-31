package com.krm.ps.model.xlsinit.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableList implements Serializable{

	/**
	 * 存放xml中的所有表对象
	 */
	private static final long serialVersionUID = -4814341491216625700L;
	private List tableList = new ArrayList();
	public TableList(List list) {
		super();
		// TODO Auto-generated constructor stub
		tableList = list;
	}
	public List getTableList() {
		return tableList;
	}
	public void setTableList(List tableList) {
		this.tableList = tableList;
	}
		
}
