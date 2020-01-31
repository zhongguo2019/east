package com.krm.ps.sysmanage.reportdefine.vo;

import java.io.Serializable;

public class StrdReportWhecondition implements Serializable{
	
	private Long pkid;
	private Long  tempid;      //模板id
	private Long  modeid;      //模型id
	private String whecondition ; //WHECONDITION  关联关系
    private String ismaintable;//ISMAINTABLE 是否是主表
    private String tablename;//TABLENAME  表名
    private String table;//TABLAB   别名
    
    
    
    
	public StrdReportWhecondition() {
		super();
	}
	
	
	public StrdReportWhecondition(Long tempid, Long modeid,
			String whecondition, String ismaintable, String tablename,
			String table) {
		super();
		this.tempid = tempid;
		this.modeid = modeid;
		this.whecondition = whecondition;
		this.ismaintable = ismaintable;
		this.tablename = tablename;
		this.table = table;
	}

    

	public Long getPkid() {
		return pkid;
	}


	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}


	public Long getTempid() {
		return tempid;
	}
	public void setTempid(Long tempid) {
		this.tempid = tempid;
	}
	public Long getModeid() {
		return modeid;
	}
	public void setModeid(Long modeid) {
		this.modeid = modeid;
	}
	public String getWhecondition() {
		return whecondition;
	}
	public void setWhecondition(String whecondition) {
		this.whecondition = whecondition;
	}
	public String getIsmaintable() {
		return ismaintable;
	}
	public void setIsmaintable(String ismaintable) {
		this.ismaintable = ismaintable;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
    
    
}
