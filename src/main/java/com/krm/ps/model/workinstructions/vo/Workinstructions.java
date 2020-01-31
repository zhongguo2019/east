package com.krm.ps.model.workinstructions.vo;

import java.io.Serializable;

public class Workinstructions implements Serializable{

	private Long pkid;
	
	private String name;
	
	private long superid;
	
	private int status; 
	
	private int px;
	
	private String type;
	
	private String backup1;
	
	private String backup2;
	
	private String backup3;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSuperid() {
		return superid;
	}

	public void setSuperid(long superid) {
		this.superid = superid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPx() {
		return px;
	}

	public void setPx(int px) {
		this.px = px;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBackup1() {
		return backup1;
	}

	public void setBackup1(String backup1) {
		this.backup1 = backup1;
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
	

	 
	
	

}
