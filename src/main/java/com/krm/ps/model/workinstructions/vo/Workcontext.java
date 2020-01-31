package com.krm.ps.model.workinstructions.vo;

import java.io.Serializable;

public class Workcontext implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long pkid;
	
	private String title;
	
	private String displaytitle;
	
	private byte[] context;
	
	private long treeid;
	
	private String begintime;
	
	private String updatetime;
	
	private int status;
	
	private String backup2;
	
	private String backup3;
	
	private String backup4;
	
	private String backup5;
	
	private long authorid;

	
	public long getAuthorid() {
		return authorid;
	}

	public void setAuthorid(long authorid) {
		this.authorid = authorid;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getPkid() {
		return pkid;
	}

	public void setPkid(long pkid) {
		this.pkid = pkid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisplaytitle() {
		return displaytitle;
	}

	public void setDisplaytitle(String displaytitle) {
		this.displaytitle = displaytitle;
	}

	public byte[] getContext() {
		return context;
	}

	public void setContext(byte[] context) {
		this.context = context;
	}

	public long getTreeid() {
		return treeid;
	}

	public void setTreeid(long treeid) {
		this.treeid = treeid;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBackup2() {
		return backup2;
	}

	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}

	public String getBackup3() {
		return backup3;
	}

	public void setBackup3(String backup3) {
		this.backup3 = backup3;
	}

	public String getBackup4() {
		return backup4;
	}

	public void setBackup4(String backup4) {
		this.backup4 = backup4;
	}

	public String getBackup5() {
		return backup5;
	}

	public void setBackup5(String backup5) {
		this.backup5 = backup5;
	}

	
}
