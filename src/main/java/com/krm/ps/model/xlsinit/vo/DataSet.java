package com.krm.ps.model.xlsinit.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataSet implements Serializable{

	/**
	 * excel数据对象
	 */
	private static final long serialVersionUID = 2970043906511704910L;
	private List xlsData = new ArrayList();
	public DataSet(List data) {
		super();
		// TODO Auto-generated constructor stub
		xlsData = data;
	}
	public List getXlsData() {
		return xlsData;
	}
	public void setXlsData(List xlsData) {
		this.xlsData = xlsData;
	}
	
	
}
