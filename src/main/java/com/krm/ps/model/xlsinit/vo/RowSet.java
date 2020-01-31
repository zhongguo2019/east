package com.krm.ps.model.xlsinit.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RowSet implements Serializable{
	/**
	 * 用于存放excel中的行数据，key为字段名，value为数据
	 */
	private Map rowData;
	private int cols;
	private static final long serialVersionUID = -8263644508715711855L;
	public RowSet(int cols) {
		super();
		// TODO Auto-generated constructor stub
		this.cols = cols;
		this.rowData = new HashMap(cols);
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public Map getRowData() {
		return rowData;
	}
	public void setRowData(Map rowData) {
		this.rowData = rowData;
	}    
    
}
